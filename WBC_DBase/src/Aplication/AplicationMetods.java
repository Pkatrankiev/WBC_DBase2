package Aplication;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;

import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;


public class AplicationMetods {

	public static void readInfoFromGodExcelFile(String year, String key, boolean save) {
	
		String PerStatNewSet = ReadFileBGTextVariable.getGlobalTextVariableMap().get("PerStatNewSet");
		String[] excellFiles = getDataBaseFilePat_ArhivePersonalAndExternal();	
		 
		for (String pathFile : excellFiles) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";	
			}
			
			pathFile = pathFile+year+".xls";

		switch (key) {
		case "Person": {
			// read and set Person
			List<Person> listPerson = ReadPersonFromExcelFile.updatePersonFromExcelFile(pathFile, null, "", null);
			System.out.println("--> "+listPerson.size());
			if(save) {
			ReadPersonFromExcelFile.setToDBaseListPerson(listPerson, null, "");
			System.out.println("Save "+firmName);
			}
		}
		break;
		
		case "Spisak_Prilogenia": {
			// read and set Spisak_Prilogenia
			List<Spisak_Prilogenia> Spisak_PrilogeniaList = ReadSpisak_PrilogeniaFromExcelFile
					.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName, year, null, "", null, null);
			
			ReadSpisak_PrilogeniaFromExcelFile.ListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
			System.out.println("--> "+Spisak_PrilogeniaList.size());
			if(save) {
			ReadSpisak_PrilogeniaFromExcelFile.setListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList, null, "");
			System.out.println("Save "+firmName);
			}
		}
		break;
		
		case "PersonStatus": {
			// read and set PersonStatus
			if(PerStatNewSet.equals("1")) {
				List<PersonStatusNew> list = ReadPersonStatusFromExcelFile.getListPersonStatusNewFromExcelFile(pathFile, firmName,
						year, null, "", null, null);
				ReadPersonStatusFromExcelFile.ListPersonStatusNew(list);
				System.out.println("--> "+list.size());
				if(save) {
				ReadPersonStatusFromExcelFile.setToBDateListPersonStatusNew(list, null, "");
				System.out.println("Save "+firmName);
				}	
			}
		}
		break;
		
		case "KodeStatus": {
			// read and set KodeStatus
			List<KodeStatus> listKodeStatus = ReadKodeStatusFromExcelFile.getListKodeStatusFromExcelFile(pathFile,
					firmName, year, null, "", null);
			ReadKodeStatusFromExcelFile.ListKodeStatus(listKodeStatus);
			System.out.println("--> "+listKodeStatus.size());
			if(save) {
			ReadKodeStatusFromExcelFile.setToDBaseListKodeStatus(listKodeStatus, null, "");
			System.out.println("Save "+firmName);
			}

		}
		break;
		case "Measuring": {
			// read and set Measuring
			List<Measuring>  listMeasuring = ReadMeasuringFromExcelFile.generateListFromMeasuringFromExcelFile(pathFile, null, "", null);
			ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
			System.out.println("--> "+listMeasuring.size());
			if(save) {
			ReadMeasuringFromExcelFile.setListMeasuringToBData(listMeasuring, null, "");
			System.out.println("Save "+firmName);
			}

		}
		break;
		case "ResultsWBC": {
			// read and set ResultsWBC
			List<ResultsWBC>  listResultsWBC = ReadResultsWBCFromExcelFile.generateListFromResultsWBCFromExcelFile(pathFile, null, "", null);
			ReadResultsWBCFromExcelFile.ListResultsWBCToBData(listResultsWBC);
			System.out.println("--> "+listResultsWBC.size());
			if(save) {
			ReadResultsWBCFromExcelFile.setListResultsWBCToBData(listResultsWBC, null, "");
			System.out.println("Save "+firmName);
			}
		}
		break;
		case "ObhodenList": {
			// read and set ObhodenList in PersonStatus
			
			if(PerStatNewSet.equals("1")) {
				List<PersonStatusNew> list = ReadPersonStatusFromExcelFile.getObhodenListPersonStatusNewFromExcelFile(pathFile, firmName,
						year, null, "");
				ReadPersonStatusFromExcelFile.ListPersonStatusNew(list);
				System.out.println(year+ " --> "+list.size());
				if(save) {
				ReadPersonStatusFromExcelFile.setToBDateListPersonStatusNew(list, null, "");
				System.out.println("Save "+firmName);
				}	
				
				
			}else {
			List<PersonStatus> list = ReadPersonStatusFromExcelFile.getObhodenListPersonStatusFromExcelFile(pathFile, firmName,
					year, null, "");
			ReadPersonStatusFromExcelFile.ListPersonStatus(list);
			System.out.println(year+ " --> "+list.size());
			if(save) {
			ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list, null, "");
			System.out.println("Save "+firmName);
			}
		
			
		}
		}
		}
		}
	}

	public static String[] getDataBaseFilePat_ArhivePersonalAndExternal() {
		String dataArhiveExcelFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dataArhiveExcelFilePath");
		String filePathArhivePersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhivePersonel");
		String filePathArhiveExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhiveExternal");
		String[] excellFiles = {dataArhiveExcelFilePath+filePathArhivePersonel, dataArhiveExcelFilePath+filePathArhiveExternal};
		return excellFiles;
	}

	
	
	
	public static String[] getDataBaseFilePat_OriginalPersonalAndExternal() {
		
		
		String filePathExternal_orig = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel_orig = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
		
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathExternal_orig = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
		filePathPersonel_orig = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
		String[] excellFiles = {filePathPersonel_orig, filePathExternal_orig };
		return excellFiles;
	}

	
	public static int[] getCurentKoordinates(int[] size) {
		int[] koordinates = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int koordWidth = gd.getDisplayMode().getWidth();
		int koorHeight = gd.getDisplayMode().getHeight();

		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX() + 10;
		int y = (int) b.getY() + 10;

		if ((x + size[0]) > koordWidth)
			x = x - size[0];
		if (x < 0) {
			x = 0;
		}
		if ((y + size[1]) > koorHeight)
			y = y - size[1];
		if (y < 0) {
			y = 0;
		}
		koordinates[0] = x;
		koordinates[1] = y;
		return koordinates;
	}
	
	public static void copyToClipboard(String text) {
	    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
	        .setContents(new java.awt.datatransfer.StringSelection(text), null);
	}
	
	public static String transliterate(String message){
	    char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ',
	    		'ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ',
	    		'Ъ','Ы','Ь','Э','Ю','Я'};
	    String[] abcLat = {" ","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sht", 
	    		"a","i", "y","e","yu","ya","A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch","Sh","Sch",
	    		"A","I", "Y","E","Yu","Ya"};
	    StringBuilder builder = new StringBuilder();
	    boolean fl;
	    for (int i = 0; i < message.length(); i++) {
	    	fl=true;
	        for (int x = 0; x < abcCyr.length; x++ ) {
	            if (message.charAt(i) == abcCyr[x]) {
	                builder.append(abcLat[x]);
	                x = abcCyr.length;
	                fl=false;
	            }
	        }
	        if(fl) {
	        	 builder.append(message.charAt(i));
	        }
	    }
	    return builder.toString();
	}

	public static String literate(String message){
		String[] abcCyr =   {" ","а","б","в","г","д","е", "ж","з","и","й","к","л","м","н","о","п","р","с","т","у","ф","х", "ц","ч", "ш","щ",
	    		"ъ","ь", "ю","я","А","Б","В","Г","Д","Е", "Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х", "Ц", "Ч","Ш", "Щ",
	    		"Ъ","Ь","Ю","Я"};
		char[] abcLat = {' ','a','b','w','g','d','e','v','z','i','j','k','l','m','n','o','p','r','s','t','u','f','h','c','`','[',']', 
	    		'y','x','\\','q','A','B','W','G','D','E','V','Z','I','J','K','L','M','N','O','P','R','S','T','U','F','H','C','~','{','}',
	    		'Y','X', '|','Q'};
	    StringBuilder builder = new StringBuilder();
	    boolean fl;
	    for (int i = 0; i < message.length(); i++) {
	    	fl=true;
	        for (int x = 0; x < abcLat.length; x++ ) {
	            if (message.charAt(i) == abcLat[x]) {
	                builder.append(abcCyr[x]);
	                x = abcLat.length;
	                fl=false;
	            }
	        }
	        if(fl) {
	        	 builder.append(message.charAt(i));
	        }
	    }
	    return builder.toString();
	}
	
	public static boolean incorrectDate(String date) {
		
		boolean corDate = false;
		if (checkDate(date)) {
		DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(date);

		} catch (Exception e) {
			return corDate = true;
		}
		 }else {
			 return true;
		 }
		return corDate;
	}

	static boolean checkDate(String date) {
	    String pattern = "^\\d{2}.\\d{2}.\\d{4}$";
	    boolean flag = false;
	    if (date.matches(pattern)) {
	      flag = true;
	    }
	    return flag;
	  }
	
	public static SimpleDateFormat extractedSimleDateFormatFromDate(String strDate) {
		String strD = strDate.replace(".", ":");
		
		String[] val = strD.split(":");
		
		String sdfrmtSTR="";
		for (int i = 0; i < val[0].length(); i++) {
			sdfrmtSTR += "d";
		}
		sdfrmtSTR += ".";
		for (int i = 0; i < val[1].length(); i++) {
			sdfrmtSTR += "M";
		}
		sdfrmtSTR += ".";
		for (int i = 0; i < val[2].length(); i++) {
			sdfrmtSTR += "y";
		}
		SimpleDateFormat sdfrmt = new SimpleDateFormat(sdfrmtSTR);
		return sdfrmt;
	}
	
	public static String getCurentYear() {
	return Calendar.getInstance().get(Calendar.YEAR) + "";
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}


	public static void testGetListPersonSatatusByPersonAndDateAfterDateSet() {
		Person per = PersonDAO.getValuePersonByEGN("6902121962");
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date startDate;
		List<Spisak_Prilogenia> listSpisPril = new ArrayList<>();
		try {
			startDate = sdfrmt.parse("09.05.2022");
			List<PersonStatus> list =  PersonStatusDAO.getValuePersonStatusByPersonAndDFateAfterDateSet(per, startDate);
			for (PersonStatus personStatus : list) {
				listSpisPril.add(personStatus.getSpisak_prilogenia());
				System.out.println(personStatus.getSpisak_prilogenia().getStartDate());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
