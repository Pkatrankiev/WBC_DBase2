package Aplication;


import java.util.List;

import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;


public class AplicationMetods {

	public static void readInfoFromGodExcelFile(String year, String key, boolean save) {
	
		String filePathArhivePersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhivePersonel");
		String filePathArhiveExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhiveExternal");
		String[] excellFiles = {filePathArhivePersonel, filePathArhiveExternal};	
		
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
			List<KodeStatus> listKodeStatus = ReadKodeStatusFromExcelFile.getListPersonStatusFromExcelFile(pathFile,
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

	


	
}
