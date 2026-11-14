package PersonReference_OID;

import java.io.File;
import java.io.IOException;

import Aplication.ReadFileBGTextVariable;

public class AccessRunner {

    /**
     * Стартира Access база със security (workgroup + user + pwd + macro)
     */
    public static  ProcessBuilder runWithWorkgroup() throws IOException, InterruptedException {
       
    	 String accessPath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("accessPath");
    	
    	 String dbOID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dbOID");
         String workgroupFileOID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("workgroupFileOID");
         String userOID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("userOID");
     	String passwordOID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("passwordOID");
     	String nameMacrosFortransfer = ReadFileBGTextVariable.getGlobalTextVariableMap().get("nameMacrosFortransfer");
    	 
    	 
     	String accessExe = findAccess(accessPath);
        if (accessExe == null) throw new IOException("MS Access не е намерен!");
        
        ProcessBuilder pb;
     
            pb = new ProcessBuilder(accessPath, dbOID,
                    "/wrkgrp", workgroupFileOID,
                    "/user", userOID,
                    "/pwd", passwordOID,
                    "/x", nameMacrosFortransfer);
      
        return pb;
    }
  
    private static String findAccess(String path) {
      
            if (new File(path).exists()) {
                return path;
            }
        
        return null;
    }
    
}

