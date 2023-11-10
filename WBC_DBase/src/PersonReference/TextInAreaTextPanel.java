package PersonReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Zone;

public class TextInAreaTextPanel {

	private static Person person;
	private static String[] masivePersonStatusName = {
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_year"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_otdel"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Sp_Pr"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_startDate"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_endDate"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Koment")};
	private static String[][] masivePersonStatus;
	private static String[] masiveZoneName;
	private static String[][] masiveKode;
	private static String[] masiveMeasurName = {
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_year"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Date"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Doza"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Lab"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Type"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Nuclid"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Activ"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Inc"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_GGP"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Doza")};
	private static String[][] masiveMeasur;
	
	public static String createInfoPanelForPerson(String year, Person personInport, boolean fromExcell) {
		List<KodeStatus> listK = null;
		List<PersonStatus> listP = null;
		List<Measuring> listM = null;
		person = personInport;
		
		if(fromExcell) {
			listP =SearchFromExcellFiles.getListPersonStatusFromExcelFile(year, person);
		}else{
		listP = PersonStatusDAO.getValuePersonStatusByObjectSortByColumnName("Person_ID", person,"DateSet");
		}
		masivePersonStatus = null;
		if(listP.size()>0) {
		masivePersonStatus = generateMasivePersonStatus(year, listP);
		masivePersonStatus = remoteNullFromArray(masivePersonStatus);
		sortbyStartDateColumn(masivePersonStatus);
		}
		String textSpis ="";
				if(masivePersonStatus!=null && masivePersonStatus.length>0) {
		textSpis = setTextInfoPersonStatus(masivePersonStatus, masivePersonStatusName);
		}
		
		masiveZoneName = getMasiveZoneName();
		if(fromExcell) {
			listK = SearchFromExcellFiles.getListKodeStatusFromExcelFile(year, personInport);
		}else{
		listK = KodeStatusDAO.getValueKodeStatusByObjectSortByColumnName("Person_ID", person, "Year");
		}
		
		masiveKode = generateMasiveKodeStatus(year, listK, listP);
		String textKode ="";
		if(masiveKode.length>0) {
		textKode = setTextInfoKode(masiveKode, masiveZoneName);
		}
		
		if(fromExcell) {
			masiveMeasur = SearchFromExcellFiles.generateMasiveMeasurFromExcelFile( year, person);
		}else{
		listM = MeasuringDAO.getValueMeasuringByObjectSortByColumnName("Person_ID", person, "Date");
		masiveMeasur = generateMasiveMeasur(year, listM);
		}
		
		String textMeasur ="";
		if(masiveMeasur.length>0) {
		textMeasur = setTextInfoMeasur(masiveMeasur, masiveMeasurName);
		}
		
		
		String str = person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person) + "\n";
		
		str = str + year + "\n";
		str = str + ReadFileBGTextVariable.getGlobalTextVariableMap().get("code") + "\n";
		str = str + textKode;
			
		str = str + "\n";
		str = str + ReadFileBGTextVariable.getGlobalTextVariableMap().get("orders") +"\n";
		str = str + textSpis;
		
	
				
		str = str + "\n";
		str = str + ReadFileBGTextVariable.getGlobalTextVariableMap().get("measurSICH") + "\n";
		str = str + textMeasur;
	
		
		
		return str;
	}


	
	public static Person getPerson() {
		return person;
	}

	public static String[] getMasivePersonStatusName() {
		return masivePersonStatusName;
	}

	public static String[][] getMasivePersonStatus() {
		return masivePersonStatus;
	}

	public static String[][] getMasiveKode() {
		return masiveKode;
	}

	public static String[] getZoneNameMasive() {
		return masiveZoneName;
	}
	
	public static String[] getMasiveMeasurName() {
		return masiveMeasurName;
	}

	public static String[][] getMasiveMeasur() {
		return masiveMeasur;
	}

	private static String setTextInfoMeasur(String[][] masiveMeasur, String[] masiveMeasurName) {
		int[] max = {5,12,9,7,3,9,10,10,6,8};
		boolean fl=false;
		for (int i = 0; i < masiveMeasur.length; i++) {
			if(masiveMeasur[i][5]!=null && !masiveMeasur[i][5].isEmpty()) {
				fl = true;
			}
		}
		
		
		int countColumn = 5;
		String measurString = "";
		if(fl) {
			countColumn = masiveMeasurName.length;
		}
		for (int i = 0; i < countColumn; i++) {
			measurString = measurString + getAddSpace(max[i], masiveMeasurName[i]) + masiveMeasurName[i];
		}
		measurString = measurString + "\n";	
		
		for (int i = 0; i < masiveMeasur.length; i++) {
			for (int j = 0; j < countColumn; j++) {
				if(masiveMeasur[i][j]==null) {
					masiveMeasur[i][j] = "";
				}
			measurString = measurString + getAddSpace(max[j], masiveMeasur[i][j]) + masiveMeasur[i][j];
			}
			measurString = measurString + "\n";
			
		}
		
		
		return measurString;
	}

	private static String[][] generateMasiveMeasur(String year, List<Measuring> listM) {
		String[][] masiveMeasur = new String[listM.size()*3][10];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		boolean fl;
		String data;
		int i = 0, index =0;
		for (Measuring measur : listM) {
			fl = false;
			List<ResultsWBC> listR = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
			data = sdf.format(measur.getDate()).substring(6);
				if (year.trim().isEmpty() || data.equals(year)) {
				masiveMeasur[i][0] =  data;
				masiveMeasur[i][1] = sdf.format(measur.getDate());
				masiveMeasur[i][2] = measur.getDoze()+""; 
				if(masiveMeasur[i][2].equals("0.05")) masiveMeasur[i][2] = "<0.10";
				masiveMeasur[i][3] =  measur.getLab().getLab();
				masiveMeasur[i][4] =  measur.getTypeMeasur().getKodeType();
				index = i;
				for (ResultsWBC result : listR) {
					masiveMeasur[i][5] = result.getNuclideWBC().getSymbol_nuclide();
					masiveMeasur[i][6] = result.getActivity()+"" ;
					masiveMeasur[i][7] = result.getPostaplenie()+"" ;
					masiveMeasur[i][8] = result.getGgp()+"" ;
					masiveMeasur[i][9] = result.getNuclideDoze()+"" ;
					
					if(result.getActivity() > 0) masiveMeasur[index][2] = measur.getDoze()+"";
					
					i++;
					fl = true;
				}
				if(fl) {
					i--;
				}
				i++;
			
		}
	}
		String[][] newMasiveMeasur = new String[i][10];
		for (int j = 0; j < newMasiveMeasur.length; j++) {
			newMasiveMeasur[j] = masiveMeasur[j];
			
		}
		
		return newMasiveMeasur;
	}

	
	
	
	private static String setTextInfoPersonStatus(String[][] masivePersonStatus, String[] masivePersonStatusName) {
		String personStatusString = "";
		
		int[] max = {5,7,8,15,15};
		int[] columnSize = getMaxSizecolumn(masivePersonStatus, max);
		
		for (int j = 0; j < 5; j++) {
			personStatusString = personStatusString + getAddSpace(columnSize[j], masivePersonStatusName[j]) +masivePersonStatusName[j];	
		}
		personStatusString = personStatusString + "     "+ masivePersonStatusName[5]+ "\n";	
		
		for (int i = 0; i < masivePersonStatus.length; i++) {
			if(masivePersonStatus[i][0]!=null ) {
			for (int j = 0; j < 5; j++) {
				personStatusString = personStatusString + getAddSpace(columnSize[j], masivePersonStatus[i][j]) + masivePersonStatus[i][j];	
			}
			personStatusString = personStatusString + " "+ masivePersonStatus[i][5]+ "\n";	
			}
		}
		
	
		return personStatusString;
	}

	public static int[] getMaxSizecolumn(String[][] masivePersonStatus, int[] max) {
		
		for (int i = 0; i < masivePersonStatus.length; i++) {
			for (int j = 0; j < max.length; j++) {
			 if(masivePersonStatus[i][j]!=null && masivePersonStatus[i][j].length()>max[j]) {
				max[j] = masivePersonStatus[i][j].length();
			 }
			}
		}
	
		return max;
	}

	private static String[][] generateMasivePersonStatus(String year, List<PersonStatus> listP) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String[][] masivePersonStatus = new String[listP.size()][6];
		if(!year.trim().isEmpty()) {
			int k=0;
			for (PersonStatus perStat : listP) {

				String yyy = sdf.format(perStat.getDateSet()).substring(6);
				if (yyy.equals(year)) {
					masivePersonStatus[k] = generateRowByMasive( perStat);
					k++;
				}

				}
		}else {
			int k=0;
			for (PersonStatus perStat : listP) {
				masivePersonStatus[k] = generateRowByMasive( perStat);
				k++;
				}
			}
		return masivePersonStatus;
	}

	static String[] generateRowByMasive( PersonStatus perStat) {
		
		String[] masivePersonStatus = new String[6];
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String yaer = sdf.format(perStat.getDateSet()).substring(6);
		masivePersonStatus[0] = yaer ;
		masivePersonStatus[1] = perStat.getWorkplace().getOtdel();
		masivePersonStatus[2] = perStat.getSpisak_prilogenia().getFormulyarName();
		masivePersonStatus[3] =  sdf.format(perStat.getSpisak_prilogenia().getStartDate());
		masivePersonStatus[4] =  sdf.format(perStat.getSpisak_prilogenia().getEndDate());
		masivePersonStatus[5] =  perStat.getZabelejka().replaceAll("\n", " ");
		
		return masivePersonStatus;
	}
	
	
	
	private static String[][] generateMasiveKodeStatus(String year, List<KodeStatus> listK, List<PersonStatus> listP) {
		String yearKode="";
		int index = -1;
			String[][] masiveKode = new String[listK.size()][7];
		masiveKode = setDefoutValueInMasive(masiveKode);
			for (KodeStatus kodeStat : listK) {
				if (year.trim().isEmpty() || kodeStat.getYear().equals(year)) {
					
					if(!kodeStat.getYear().equals(yearKode)) {
					yearKode = kodeStat.getYear();
					index++;
					}
					masiveKode[index][0] = yearKode;
					masiveKode[index][1] = getOtdelByYear(yearKode, listP);
					switch (kodeStat.getZone().getId_Zone()) {
					case 1: {
						masiveKode[index][2] =  kodeStat.getKode();
					}
					break;
					
					case 2: {
						masiveKode[index][3] =  kodeStat.getKode();
					}
					break;
					case 3: {
						masiveKode[index][4] =  kodeStat.getKode();
					}
					break;
					case 4: {
						masiveKode[index][5] =  kodeStat.getKode();
					}
					break;
					case 5: {
						masiveKode[index][6] =  kodeStat.getKode();
					}
					break;
					
					}	
			
					
				}
				
		}
			String[][] newMasiveKode = new String[index+1][7];
			for (int i = 0; i < newMasiveKode.length; i++) {
				newMasiveKode[i] = masiveKode[i];
			}
			
		return newMasiveKode;
	}
	
	private static String getOtdelByYear(String yearKode, List<PersonStatus> listP) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String otdel = "";
		String date;
		for (int i = 0; i < listP.size(); i++) {
			date = sdf.format(listP.get(i).getDateSet()).substring(6);
			if(date.equals(yearKode)) {
				return listP.get(i).getWorkplace().getOtdel();
			}
		}
		return otdel;
	}

	public static String[][] setDefoutValueInMasive(String[][] masiveKode) {
		for (int i = 0; i < masiveKode.length; i++) {
			for (int j = 0; j < masiveKode[0].length; j++) {
			masiveKode[i][j] = "-";
			}
		}
		return masiveKode;
	}

	private static String setTextInfoKode(String[][] masiveKode, String[] masiveZoneName) {
		String kodeString = "";
				
		int[] max = {5, 7, 7, 7, 7, 13, 13};
		
		int[] columnSize = getMaxSizecolumn(masiveKode, max);
		
		for (int i = 0; i < masiveZoneName.length; i++) {
			kodeString = kodeString + getAddSpace(columnSize[i], masiveZoneName[i]) + masiveZoneName[i];
		}
		kodeString = kodeString + "\n";	
		
		for (int i = 0; i < masiveKode.length; i++) {
//			if(!masiveKode[i][0].equals("-")) {
			for (int j = 0; j < masiveZoneName.length; j++) {
			kodeString = kodeString + getAddSpace(columnSize[j], masiveKode[i][j]) + masiveKode[i][j];
		}
			kodeString = kodeString + "\n";
//			}
		}
		return kodeString;
	}

	private static String[] getMasiveZoneName() {
		String[] masiveZoneName = new String[7]; 
		masiveZoneName[0] = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_year");
		masiveZoneName[1] = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_otdel");
		int i=2;
		for (Zone zone : ZoneDAO.getAllValueZone()) {
			masiveZoneName[i] = zone.getNameTerritory();
			i++;
		}
		return masiveZoneName;
	}
	
	
	
	public static String getAddSpace(int space, String kodeString) {
		 String addString = "";
		
		for (int m = 0; m < space+2-kodeString.length(); m++) {
			addString +=" ";	
		}
		return addString;
	}

	

	 public static void sortbyStartDateColumn(String[][]arr)
	    {	    		    	
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
     	int columnStartDate = 3;
     	Arrays.sort(arr, new Comparator<String[]>() {

	        
				@Override
				public int compare(String[] entry1, String[] entry2) {
					
					if(entry1[0].equals("-")) {
						entry1[0] = "01.01.2000";
					}
					if(entry2[0].equals("-")) {
						entry2[0] = "01.01.2000";
					}
					if( entry1[0].equals(entry2[0])){
						  Date dateSet1 = null;
			        	  Date dateSet2 = null;
			        	 
			        	  try {
//			        		  replace("01.01.2000", "-")
			        	 	dateSet1 = sdf.parse(entry1[columnStartDate]);
							dateSet2 = sdf.parse(entry2[columnStartDate]);
						} catch (ParseException e) {
							e.printStackTrace();
						}
			        	 
			        	  return dateSet1.compareTo(dateSet2);	
					}
					
					return 0;
				}
	        });
	    }
	  

	 
	private static String[][] remoteNullFromArray(String[][] arr) {
		List<String[]> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			if(arr[i][0]!=null) {
				list.add(arr[i]);
			}
			}
		String[][] masive = new String[list.size()][arr[0].length];
		int k =0;
		for (String[] strings : list) {
			masive[k] = strings;
			k++;
		}
		return masive;
	}
	
}
