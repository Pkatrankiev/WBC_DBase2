package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import InsertMeasuting.LockedExcelFileWriter;

/**
 * Ръчен тест (не JUnit - в проекта няма JUnit на classpath): пуска два
 * "потребителя" (нишки), които едновременно се опитват да запишат в един и
 * същ .xls файл през LockedExcelFileWriter, и проверява че (1) критичните им
 * секции не се застъпват във времето и (2) не се губят данни от нито един от
 * двата записа.
 */
public class TestFileLockConcurrentWrite {

	public static void main(String[] args) throws Exception {
		File tempFile = File.createTempFile("test_wbc_month_lock_", ".xls");
		tempFile.deleteOnExit();
		createFixtureWorkbook(tempFile);

		System.out.println("Тестов файл: " + tempFile.getAbsolutePath());

		CountDownLatch startLatch = new CountDownLatch(1);
		AtomicReference<long[]> windowA = new AtomicReference<>();
		AtomicReference<long[]> windowB = new AtomicReference<>();
		AtomicReference<Exception> errorA = new AtomicReference<>();
		AtomicReference<Exception> errorB = new AtomicReference<>();

		Thread writerA = new Thread(
				() -> runWriter(tempFile.getAbsolutePath(), "Potrebitel-A", startLatch, windowA, errorA));
		Thread writerB = new Thread(
				() -> runWriter(tempFile.getAbsolutePath(), "Potrebitel-B", startLatch, windowB, errorB));

		writerA.start();
		writerB.start();

		Thread.sleep(200);
		startLatch.countDown();

		writerA.join();
		writerB.join();

		if (errorA.get() != null) {
			System.out.println("Potrebitel-A hvarli greshka: " + errorA.get());
		}
		if (errorB.get() != null) {
			System.out.println("Potrebitel-B hvarli greshka: " + errorB.get());
		}

		long[] a = windowA.get();
		long[] b = windowB.get();

		if (a == null || b == null) {
			System.out.println("PROVAL: единият от записите не завърши успешно.");
			return;
		}

		System.out.println("Prozorec na zapis A: [" + a[0] + " ; " + a[1] + "]");
		System.out.println("Prozorec na zapis B: [" + b[0] + " ; " + b[1] + "]");

		boolean overlap = a[0] < b[1] && b[0] < a[1];
		System.out.println(overlap ? "PROVAL: kriticchnite sektsii se zastapvat - zakluchvaneto NE raboti!"
				: "OK: kriticchnite sektsii NE se zastapvat - zakluchvaneto raboti.");

		boolean hasA = fileContainsLabel(tempFile, "Potrebitel-A");
		boolean hasB = fileContainsLabel(tempFile, "Potrebitel-B");
		System.out.println("Red ot Potrebitel-A prisastva: " + hasA);
		System.out.println("Red ot Potrebitel-B prisastva: " + hasB);
		System.out.println((hasA && hasB) ? "OK: nyama izgubeni danni (lost update)."
				: "PROVAL: izgubeni sa danni ot ednia zapis!");

		cleanupFileAndBackups(tempFile);
	}

	private static void cleanupFileAndBackups(File tempFile) {
		File dir = tempFile.getParentFile();
		String prefix = tempFile.getName().replaceFirst("\\.xls$", "-");
		File[] backups = dir.listFiles((d, name) -> name.startsWith(prefix) && name.endsWith(".xls"));
		if (backups != null) {
			for (File backup : backups) {
				backup.delete();
			}
		}
		tempFile.delete();
	}

	private static void runWriter(String path, String label, CountDownLatch startLatch, AtomicReference<long[]> window,
			AtomicReference<Exception> error) {
		try {
			startLatch.await();
			LockedExcelFileWriter.modifyWorkbookFileWithLock(path, workbook -> {
				long start = System.currentTimeMillis();
				System.out.println(label + " vleze v kriticchnata sektsia v " + start);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				Sheet sheet = workbook.getSheetAt(0);
				Row row = sheet.createRow(sheet.getLastRowNum() + 1);
				row.createCell(0).setCellValue(label);
				long end = System.currentTimeMillis();
				System.out.println(label + " izleze ot kriticchnata sektsia v " + end);
				window.set(new long[] { start, end });
				return null;
			});
		} catch (Exception e) {
			error.set(e);
		}
	}

	private static void createFixtureWorkbook(File file) throws Exception {
		try (Workbook workbook = new HSSFWorkbook()) {
			workbook.createSheet("Sheet1");
			try (FileOutputStream out = new FileOutputStream(file)) {
				workbook.write(out);
			}
		}
	}

	private static boolean fileContainsLabel(File file, String label) throws Exception {
		try (FileInputStream in = new FileInputStream(file); Workbook workbook = new HSSFWorkbook(in)) {
			Sheet sheet = workbook.getSheetAt(0);
			for (int r = 0; r <= sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				org.apache.poi.ss.usermodel.Cell cell = row.getCell(0);
				if (cell != null && label.equals(cell.getStringCellValue())) {
					return true;
				}
			}
			return false;
		}
	}
}
