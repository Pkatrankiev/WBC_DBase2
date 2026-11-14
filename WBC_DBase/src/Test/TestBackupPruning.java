package Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import InsertMeasuting.LockedExcelFileWriter;

/**
 * Ръчен тест (не JUnit): проверява, че LockedExcelFileWriter (1) създава
 * резервно копие на файла в същата директория с формат на името
 * "<baseName>-ddMMyyHHmm.<ext>" и (2) след запис пази само последните 3 копия
 * по РЕАЛНА хронология (не по лексикографско сравнение на низа, което би било
 * грешно, защото името започва с деня - напр. "31" текстово е "по-голямо" от
 * "01", въпреки че 31-ви декември е ПРЕДИ 1-ви януари следващата година).
 */
public class TestBackupPruning {

	public static void main(String[] args) throws Exception {
		File dir = Files.createTempDirectory("wbc_backup_test_").toFile();
		File dataFile = new File(dir, "wbc_month_test.xls");
		createFixtureWorkbook(dataFile);

		System.out.println("Тестова директория: " + dir.getAbsolutePath());

		// Изкуствени "стари" резервни копия с нарочно объркващ ред на датите,
		// за да проверим, че подрязването сортира по истинска дата, а не по низ.
		String[] fakeOldBackups = {
				"wbc_month_test-3112250900.xls", // 31.12.2025 09:00 - най-стара хронологично
				"wbc_month_test-0101261000.xls", // 01.01.2026 10:00
				"wbc_month_test-1501261100.xls", // 15.01.2026 11:00
				"wbc_month_test-2001261200.xls", // 20.01.2026 12:00 - най-новата от изкуствените
		};
		for (String name : fakeOldBackups) {
			new FileOutputStream(new File(dir, name)).close();
		}

		System.out.println("Създадени изкуствени стари копия: " + fakeOldBackups.length);

		// Един реален запис - трябва да създаде ново копие (с днешен timestamp) и
		// да свие всички копия на този файл до последните 3.
		LockedExcelFileWriter.modifyWorkbookFileWithLock(dataFile.getAbsolutePath(), workbook -> null);

		List<String> remainingBackups = Arrays.stream(dir.listFiles())
				.map(File::getName)
				.filter(n -> n.startsWith("wbc_month_test-") && n.endsWith(".xls"))
				.sorted()
				.collect(Collectors.toList());

		System.out.println("Оставащи копия (" + remainingBackups.size() + "): ");
		remainingBackups.forEach(n -> System.out.println("  " + n));

		boolean countOk = remainingBackups.size() == 3;
		boolean oldestTwoGone = !remainingBackups.contains("wbc_month_test-3112250900.xls")
				&& !remainingBackups.contains("wbc_month_test-0101261000.xls");
		boolean newestTwoKept = remainingBackups.contains("wbc_month_test-1501261100.xls")
				&& remainingBackups.contains("wbc_month_test-2001261200.xls");

		System.out.println("Точно 3 копия останаха: " + countOk);
		System.out.println("Двете най-стари (31.12.25 и 01.01.26) са изтрити: " + oldestTwoGone);
		System.out.println("Двете по-нови изкуствени (15.01.26 и 20.01.26) са запазени: " + newestTwoKept);
		System.out.println((countOk && oldestTwoGone && newestTwoKept)
				? "OK: подрязването пази последните 3 по РЕАЛНА хронология."
				: "PROVAL: подрязването не работи коректно.");

		deleteRecursively(dir);
	}

	private static void createFixtureWorkbook(File file) throws Exception {
		try (Workbook workbook = new HSSFWorkbook()) {
			workbook.createSheet("Sheet1");
			try (FileOutputStream out = new FileOutputStream(file)) {
				workbook.write(out);
			}
		}
	}

	private static void deleteRecursively(File file) {
		File[] children = file.listFiles();
		if (children != null) {
			for (File child : children) {
				deleteRecursively(child);
			}
		}
		file.delete();
	}
}
