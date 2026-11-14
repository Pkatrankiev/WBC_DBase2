package Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ReadFileBGTextVariable;
import InsertMeasuting.LockedExcelFileWriter;

/**
 * Ръчен тест (не JUnit): зарежда реалния BGTextVariable.txt (трябва да се
 * пусне с работна директория = корена на проекта, откъдето този файл се
 * зарежда) и проверява, че LockedExcelFileWriter записва резервните копия в
 * директорията, зададена от "destinationDir", а НЕ в директорията на
 * оригиналния excel файл.
 */
public class TestBackupDestinationDir {

	public static void main(String[] args) throws Exception {
		boolean loaded = ReadFileBGTextVariable.CreadMasiveFromReadFile();
		System.out.println("BGTextVariable.txt zareden: " + loaded);

		String destinationDir = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir");
		System.out.println("destinationDir ot konfiguraciata: " + destinationDir);

		File projectDir = new File(System.getProperty("user.dir")).getAbsoluteFile();
		File expectedBackupDir = new File(destinationDir).getAbsoluteFile();
		System.out.println("Direktoria na proekta (rabotna direktoria): " + projectDir);
		System.out.println("Ochakvana (razreshena) direktoria za kopia: " + expectedBackupDir);

		File originalDir = new File(System.getProperty("java.io.tmpdir"), "test_original_dir_" + System.nanoTime());
		originalDir.mkdirs();
		File dataFile = new File(originalDir, "wbc_month_dest_test.xls");
		createFixtureWorkbook(dataFile);

		System.out.println("Originalen fail (direktoria, kadeto NE trqbva da otide kopieto): " + originalDir);

		LockedExcelFileWriter.modifyWorkbookFileWithLock(dataFile.getAbsolutePath(), workbook -> null);

		File[] backupsInOriginalDir = originalDir
				.listFiles((d, name) -> name.startsWith("wbc_month_dest_test-") && name.endsWith(".xls"));
		File[] backupsInConfiguredDir = expectedBackupDir
				.listFiles((d, name) -> name.startsWith("wbc_month_dest_test-") && name.endsWith(".xls"));

		boolean noneInOriginalDir = backupsInOriginalDir == null || backupsInOriginalDir.length == 0;
		boolean oneInConfiguredDir = backupsInConfiguredDir != null && backupsInConfiguredDir.length == 1;

		System.out.println("Kopia v originalnata direktoria (ochakvano 0): "
				+ (backupsInOriginalDir == null ? 0 : backupsInOriginalDir.length));
		System.out.println("Kopia v konfigurirata direktoria (ochakvano 1): "
				+ (backupsInConfiguredDir == null ? 0 : backupsInConfiguredDir.length));

		System.out.println((noneInOriginalDir && oneInConfiguredDir)
				? "OK: kopieto otide v konfiguriranata destinationDir, ne v originalnata direktoria."
				: "PROVAL: kopieto NE e tam, kadeto se ochakva.");

		// почистване
		dataFile.delete();
		originalDir.delete();
		if (backupsInConfiguredDir != null) {
			Arrays.stream(backupsInConfiguredDir).forEach(File::delete);
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
}
