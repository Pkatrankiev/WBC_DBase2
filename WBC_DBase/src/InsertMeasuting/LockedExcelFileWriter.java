package InsertMeasuting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ReadFileBGTextVariable;

/**
 * Чете и записва общи (мрежови) excel файлове под ексклузивно заключване
 * (FileLock), държано за цялото времетраене на четене-модификация-запис, за
 * да се избегне загуба на информация при едновременен запис от друг
 * потребител/инстанция на приложението върху същия файл.
 */
public final class LockedExcelFileWriter {

	private static final int LOCK_TIMEOUT_MS = 20000;
	private static final int LOCK_RETRY_DELAY_MS = 300;

	private static final DateTimeFormatter BACKUP_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("ddMMyyHHmm");
	private static final int MAX_BACKUPS_PER_FILE = 3;

	private LockedExcelFileWriter() {
	}

	public static <T> T modifyWorkbookFileWithLock(String pathFile, Function<Workbook, T> modifier)
			throws IOException {
		try (RandomAccessFile raf = new RandomAccessFile(pathFile, "rw");
				FileChannel channel = raf.getChannel()) {

			FileLock lock = acquireExclusiveLock(channel);
			if (lock == null) {
				throw new FileLockTimeoutException(pathFile);
			}
			try {
				byte[] data = new byte[(int) channel.size()];
				ByteBuffer buffer = ByteBuffer.wrap(data);
				channel.position(0);
				while (buffer.hasRemaining()) {
					if (channel.read(buffer) < 0) {
						break;
					}
				}

				backupFileKeepLastN(pathFile, data);

				Workbook workbook;
				try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
					workbook = new HSSFWorkbook(bais);
				}

				T result = modifier.apply(workbook);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				workbook.write(baos);
				workbook.close();

				channel.position(0);
				channel.truncate(0);
				channel.write(ByteBuffer.wrap(baos.toByteArray()));
				channel.force(true);

				return result;
			} finally {
				lock.release();
			}
		}
	}

	private static FileLock acquireExclusiveLock(FileChannel channel) throws IOException {
		long deadline = System.currentTimeMillis() + LOCK_TIMEOUT_MS;
		while (true) {
			try {
				FileLock lock = channel.tryLock();
				if (lock != null) {
					return lock;
				}
			} catch (OverlappingFileLockException e) {
				// друга нишка/операция в същия процес вече държи заключването - изчакваме
			}
			if (System.currentTimeMillis() >= deadline) {
				return null;
			}
			try {
				Thread.sleep(LOCK_RETRY_DELAY_MS);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
				return null;
			}
		}
	}

	/**
	 * Прави резервно копие на текущото (преди-модификация) съдържание на файла в
	 * директорията, зададена от променливата "destinationDir" в
	 * BGTextVariable.txt (на същото ниво, където е проектът/приложението), с
	 * добавен към името времеви печат "-ddMMyyHHmm", и оставя само последните
	 * {@link #MAX_BACKUPS_PER_FILE} копия за този файл - по-старите се
	 * изтриват. Ако "destinationDir" не е зададена (напр. при самостоятелно
	 * тестване без зареден BGTextVariable.txt), се пада обратно към същата
	 * директория, в която е оригиналният файл. Грешка тук е второстепенна
	 * спрямо самия запис и не бива да го блокира.
	 */
	private static void backupFileKeepLastN(String pathFile, byte[] currentData) {
		try {
			File original = new File(pathFile).getAbsoluteFile();
			String name = original.getName();
			int dotIndex = name.lastIndexOf('.');
			String baseName = dotIndex >= 0 ? name.substring(0, dotIndex) : name;
			String ext = dotIndex >= 0 ? name.substring(dotIndex) : "";

			File backupDir = resolveBackupDirectory(original);
			if (!backupDir.exists()) {
				backupDir.mkdirs();
			}

			String timestamp = LocalDateTime.now().format(BACKUP_TIMESTAMP_FORMAT);
			File backupFile = new File(backupDir, baseName + "-" + timestamp + ext);
			try (FileOutputStream out = new FileOutputStream(backupFile)) {
				out.write(currentData);
			}

			pruneOldBackups(backupDir, baseName, ext);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Разрешава директорията за резервни копия от конфигурационната променлива
	 * "destinationDir" (BGTextVariable.txt), взета спрямо работната директория
	 * на приложението (т.е. същото място, откъдето се чете и самия
	 * BGTextVariable.txt) - НЕ спрямо директорията на оригиналния excel файл.
	 * Ако променливата липсва, се пада обратно към директорията на оригиналния
	 * файл.
	 */
	private static File resolveBackupDirectory(File originalFile) {
		Map<String, String> vars = ReadFileBGTextVariable.getGlobalTextVariableMap();
		String destinationDir = vars != null ? vars.get("destinationDir") : null;
		if (destinationDir == null || destinationDir.trim().isEmpty()) {
			return originalFile.getParentFile();
		}
		return new File(destinationDir).getAbsoluteFile();
	}

	private static void pruneOldBackups(File dir, String baseName, String ext) {
		if (dir == null) {
			return;
		}
		File[] candidates = dir.listFiles();
		if (candidates == null) {
			return;
		}

		Pattern backupPattern = Pattern.compile(Pattern.quote(baseName) + "-(\\d{10})" + Pattern.quote(ext));
		List<File> backups = new ArrayList<>();
		Map<File, LocalDateTime> timestamps = new HashMap<>();
		for (File f : candidates) {
			Matcher m = backupPattern.matcher(f.getName());
			if (m.matches()) {
				try {
					timestamps.put(f, LocalDateTime.parse(m.group(1), BACKUP_TIMESTAMP_FORMAT));
					backups.add(f);
				} catch (DateTimeParseException e) {
					// файл с формално съвпадащо име, но нечетим времеви печат - пропускаме го
				}
			}
		}

		if (backups.size() <= MAX_BACKUPS_PER_FILE) {
			return;
		}

		backups.sort(Comparator.comparing(timestamps::get).reversed());

		for (int i = MAX_BACKUPS_PER_FILE; i < backups.size(); i++) {
			backups.get(i).delete();
		}
	}

	public static class FileLockTimeoutException extends IOException {
		private static final long serialVersionUID = 1L;

		public FileLockTimeoutException(String pathFile) {
			super("Файлът е зает от друг потребител и не можа да бъде заключен за запис: " + pathFile);
		}
	}
}
