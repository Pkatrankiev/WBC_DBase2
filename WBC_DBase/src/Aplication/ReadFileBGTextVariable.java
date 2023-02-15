package Aplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;


public class ReadFileBGTextVariable {
	
	private static 	Map<String, String> globalTextVariableMap;

	
	public static boolean  CreadMasiveFromReadFile() {
		BufferedReader br = null;
		globalTextVariableMap = new HashMap<String, String>();
		try {
			String respath = "BGTextVariable.txt";
			try {	
			File fileDir = new File(respath);
			InputStream in = new FileInputStream(fileDir);
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String sCurrentLine;
			int index;
			while ((sCurrentLine = br.readLine()) != null) {
				index = sCurrentLine.indexOf("=");
				
				if(index >0){
				globalTextVariableMap.put(sCurrentLine.substring(0, index).trim(), sCurrentLine.substring(index+1).trim());
				
			System.out.println(sCurrentLine.substring(0, index)+"  -  "+sCurrentLine.substring(index+1).trim());
				}
			}
			return true;
			} catch (FileNotFoundException e) {
				ResourceLoader.appendToFile(e);
				JOptionPane.showMessageDialog(null, "Не намирам: D:\\BGTextVariable.txt", "Грешка в данните",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}
	} catch (IOException  e) {
		ResourceLoader.appendToFile(e);
		e.printStackTrace();
	} finally {
		try {
			if (br != null)
				br.close();
			
		} catch (IOException ex) {
			ResourceLoader.appendToFile(ex);
			ex.printStackTrace();
		}
	}
		return false;
	
	}
	
	public static Map<String, String> getGlobalTextVariableMap() {
		return globalTextVariableMap;
	}	
	
	
}
