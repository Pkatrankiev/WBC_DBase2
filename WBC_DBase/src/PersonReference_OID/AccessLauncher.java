package PersonReference_OID;

import java.io.File;
import java.io.IOException;

import Aplication.ReadFileBGTextVariable;

public class AccessLauncher {

    // Стандартни пътища до Access
    private static final String[] possiblePaths = {
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

    /**
     * Намира MSACCESS.EXE в стандартните директории
     */
    public static String findAccess() {
        for (String path : possiblePaths) {
            File f = new File(path);
            if (f.exists()) {
                return "\"" + f.getAbsolutePath() + "\""; // с кавички за безопасност
            }
        }
        return null;
    }

    /**
     * Стартира база данни в Access (ще изпълни AutoExec, ако има)
     */
    public static void runDatabase1(String dbPath) throws IOException {
        String accessExe = findAccess();
        if (accessExe == null) {
            throw new IOException("MS Access не е намерен в стандартните пътища!");
        }
        String command = accessExe + " \"" + dbPath + "\"";
        Runtime.getRuntime().exec(command);
        System.out.println("Стартирана база: " + dbPath);
    }

    public static void runDatabase() throws IOException {
    	   String accessExe =  "c:\\Program Files (x86)\\Access2003\\OFFICE11\\MSACCESS.EXE";
    	  
    	   
//       	String db = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseOID").replace("jdbc:ucanaccess://", "");
    	   String db = "jdbc:ucanaccess:\\k:\\Docs\\Д-я БиК\\КЦ ПД\\Обща\\04 Поща\\Петър\\DBaseApp\\NewDB.mdb";
       			db= db.replace("jdbc:ucanaccess:\\", "");
       	System.out.println(db);   
    	   
    	String workgroupFile = "t:\\Doznar\\Sys\\RZIK2.MDW";
    	String user = "idk2_3";
    	String password = "qwer1234";

    	ProcessBuilder pb = new ProcessBuilder(accessExe, db, "/wrkgrp", workgroupFile, "/user", user, "/pwd", password);
    	pb.start();

    } 
    
    
    
    
    /**
     * Стартира конкретен макрос в Access (/x MacroName)
     */
    public static void runMacro(String dbPath, String macroName) throws IOException {
        String accessExe = findAccess();
        if (accessExe == null) {
            throw new IOException("MS Access не е намерен в стандартните пътища!");
        }
        String command = accessExe + " \"" + dbPath + "\" /x " + macroName;
        Runtime.getRuntime().exec(command);
        System.out.println("Стартирана база: " + dbPath + " с макрос: " + macroName);
    }

    // Пример за използване
    public static void main(String[] args) {
        try {
        	String db = "jdbc:ucanaccess://k://Docs//Д-я БиК//КЦ ПД//Обща//04 Поща//Петър//DBaseApp//NewDB.mdb";
//        	String db = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseOID").replace("jdbc:ucanaccess://", "");
        			db= db.replace("jdbc:ucanaccess://", "");
        	System.out.println(db);
//            String db = "C:\\Users\\User\\Documents\\myDatabase.accdb";

            // Вариант 1: стартира с AutoExec макрос
            runDatabase();

            // Вариант 2: стартира конкретен макрос
            // runMacro(db, "MyMacroName");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
