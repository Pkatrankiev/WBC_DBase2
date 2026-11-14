package PersonReference_OID;

import java.io.File;
import java.io.IOException;

public class AccessRunnerWait {

    private static String[] paths = {
        "C:\\Program Files\\Microsoft Office\\root\\Office16\\MSACCESS.EXE",
        "C:\\Program Files (x86)\\Microsoft Office\\root\\Office16\\MSACCESS.EXE",
        "C:\\Program Files\\Microsoft Office\\Office15\\MSACCESS.EXE",
        "C:\\Program Files (x86)\\Microsoft Office\\Office15\\MSACCESS.EXE",
        "C:\\Program Files\\Microsoft Office\\Office14\\MSACCESS.EXE",
        "C:\\Program Files (x86)\\Microsoft Office\\Office14\\MSACCESS.EXE",
        "C:\\Program Files\\Microsoft Office\\Office12\\MSACCESS.EXE",
        "C:\\Program Files (x86)\\Microsoft Office\\Office12\\MSACCESS.EXE",
        "c:\\Program Files (x86)\\Access2003\\OFFICE11\\MSACCESS.EXE"
    };

    private static String findAccess() {
        for (String path : paths) {
            if (new File(path).exists()) {
                return path;
            }
        }
        return null;
    }

    /**
     * Стартира Access, подава парола и макрос, и чака да приключи процеса.
     */
    public static void runAndWait(String dbPath, String password, String macro) throws IOException, InterruptedException {
        String accessExe = findAccess();
        if (accessExe == null) throw new IOException("MS Access не е намерен!");

        ProcessBuilder pb;
        if (password != null && !password.isEmpty()) {
            if (macro != null && !macro.isEmpty()) {
                pb = new ProcessBuilder(accessExe, dbPath, "/pwd", password, "/x", macro);
            } else {
                pb = new ProcessBuilder(accessExe, dbPath, "/pwd", password);
            }
        } else {
            if (macro != null && !macro.isEmpty()) {
                pb = new ProcessBuilder(accessExe, dbPath, "/x", macro);
            } else {
                pb = new ProcessBuilder(accessExe, dbPath);
            }
        }

        Process proc = pb.start();
        System.out.println("Access стартиран. Изчакване да приключи...");

        int exitCode = proc.waitFor();  // Тук Java блокира докато Access процеса приключи

        System.out.println("Access приключи с код: " + exitCode);
    }

    public static void main(String[] args) {
        try {
      //        	String db = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseOID").replace("jdbc:ucanaccess://", "");
        	String db = "k:\\Docs\\Д-я БиК\\КЦ ПД\\Обща\\04 Поща\\Петър\\DBaseApp\\NewDB.mdb";	
        runAndWait(db, "qwer1234", "");  // чака Access да свърши
            System.out.println("Сега Java продължава работа...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
