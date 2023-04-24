package Aplication;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;


public class AplicationMetods {

	public static void readInfoFromGodExcelFile(String year, String key, boolean save) {
	
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
			List<Person> listPerson = ReadPersonFromExcelFile.updatePersonFromExcelFile(pathFile);
			System.out.println("--> "+listPerson.size());
			if(save) {
			ReadPersonFromExcelFile.setToDBaseListPerson(listPerson);
			System.out.println("Save "+firmName);
			}
		}
		break;
		
		case "Spisak_Prilogenia": {
			// read and set Spisak_Prilogenia
			List<Spisak_Prilogenia> Spisak_PrilogeniaList = ReadSpisak_PrilogeniaFromExcelFile
					.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName, year);
			
			ReadSpisak_PrilogeniaFromExcelFile.ListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
			System.out.println("--> "+Spisak_PrilogeniaList.size());
			if(save) {
			ReadSpisak_PrilogeniaFromExcelFile.setListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
			System.out.println("Save "+firmName);
			}
		}
		break;
		
		case "PersonStatus": {
			// read and set PersonStatus
			
			List<PersonStatus> list = ReadPersonStatusFromExcelFile.getListPersonStatusFromExcelFile(pathFile, firmName,
					year);
			ReadPersonStatusFromExcelFile.ListPersonStatus(list);
			System.out.println("--> "+list.size());
			if(save) {
			ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list);
			System.out.println("Save "+firmName);
			}
		}
		break;
		
		case "KodeStatus": {
			// read and set KodeStatus
			List<KodeStatus> listKodeStatus = ReadKodeStatusFromExcelFile.getListKodeStatusFromExcelFile(pathFile,
					firmName, year);
			ReadKodeStatusFromExcelFile.ListKodeStatus(listKodeStatus);
			System.out.println("--> "+listKodeStatus.size());
			if(save) {
			ReadKodeStatusFromExcelFile.setToDBaseListKodeStatus(listKodeStatus);
			System.out.println("Save "+firmName);
			}

		}
		break;
		case "Measuring": {
			// read and set Measuring
			List<Measuring>  listMeasuring = ReadMeasuringFromExcelFile.generateListFromMeasuringFromExcelFile(pathFile);
			ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
			System.out.println("--> "+listMeasuring.size());
			if(save) {
			ReadMeasuringFromExcelFile.setListMeasuringToBData(listMeasuring);
			System.out.println("Save "+firmName);
			}

		}
		break;
		case "ResultsWBC": {
			// read and set ResultsWBC
			List<ResultsWBC>  listResultsWBC = ReadResultsWBCFromExcelFile.generateListFromResultsWBCFromExcelFile(pathFile);
			ReadResultsWBCFromExcelFile.ListResultsWBCToBData(listResultsWBC);
			System.out.println("--> "+listResultsWBC.size());
			if(save) {
			ReadResultsWBCFromExcelFile.setListResultsWBCToBData(listResultsWBC);
			System.out.println("Save "+firmName);
			}
		}
		break;
		
		
		}
		}
	}

	public static String[] getDataBaseFilePat_ArhivePersonalAndExternal() {
		String dataBaseFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dataBaseFilePath");
		String filePathArhivePersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhivePersonel");
		String filePathArhiveExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhiveExternal");
		String[] excellFiles = {dataBaseFilePath+filePathArhivePersonel, dataBaseFilePath+filePathArhiveExternal};
		return excellFiles;
	}

	
	public static String[] getDataBaseFilePat_ActualPersonalAndExternal() {
		String dataBaseFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dataBaseFilePath");
		String filePathActualPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathActualPersonel");
		String filePathActualExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathActualExternal");
		String[] excellFiles = {dataBaseFilePath+filePathActualPersonel, dataBaseFilePath+filePathActualExternal};
		return excellFiles;
	}
	
	
	public static String[] getDataBaseFilePat_OriginalPersonalAndExternal() {
	
		String filePathExternal_orig = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel_orig = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
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

	public static boolean incorrectDate(String date) {
		
		boolean corDate = false;
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		try {
			LocalDate.parse(date, sdf);

		} catch (DateTimeParseException e) {
			return corDate = true;
		}
		return corDate;
	}

	public static String getCurentYear() {
	return Calendar.getInstance().get(Calendar.YEAR) + "";
	}


	@SuppressWarnings("unused")
	static void testGetListPersonSatatusByPersonAndDateAfterDateSet() {
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
