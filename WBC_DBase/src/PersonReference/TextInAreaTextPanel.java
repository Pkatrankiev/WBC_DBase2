package PersonReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Zone;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;

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
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Doza"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_MeasurKoment")};
	private static String[][] masiveMeasur;
	private static int countIteration;
	
	public static String createInfoPanelForPerson(String year, Person personInport, boolean fromExcell, int inputCountIteration) {
		List<KodeStatus> listKodeStatus = null;
		List<PersonStatusNew> listPersonstatusNew = null;
		List<Measuring> listMeasuring = null;
		
		countIteration = inputCountIteration;
		
		person = PersonDAO.getValuePersonByEGN(personInport.getEgn());
		String str = "";
		if(person != null) {
			
			if(fromExcell) {
				listPersonstatusNew =SearchFromExcellFiles.getListPersonStatusNewFromExcelFile(year, person);
			}else{
				listPersonstatusNew = PersonStatusNewDAO.getValuePersonStatusNewByObjectSortByColumnName("Person_ID", person,"StartDate");
			}
			masivePersonStatus = null;
			if(listPersonstatusNew.size()>0) {
			masivePersonStatus = generateMasivePersonStatusNew(year, listPersonstatusNew);
			masivePersonStatus = remoteNullFromArray(masivePersonStatus);
			sortbyStartDateColumn(masivePersonStatus);
			}
		
		
		String textSpis ="";
				if(masivePersonStatus!=null && masivePersonStatus.length>0) {
		textSpis = setTextInfoPersonStatus(masivePersonStatus, masivePersonStatusName);
		}
		
		masiveZoneName = getMasiveZoneName();
		if(fromExcell) {
			listKodeStatus = SearchFromExcellFiles.getListKodeStatusFromExcelFile(year, personInport);
		}else{
		listKodeStatus = KodeStatusDAO.getValueKodeStatusByObjectSortByColumnName("Person_ID", person, "Year");
		}
					
			masiveKode = generateMasiveKodeStatusNew(year, listKodeStatus, listPersonstatusNew,  personInport, fromExcell);
		
		String textKode ="";
		if(masiveKode.length>0) {
		textKode = setTextInfoKode(masiveKode, masiveZoneName);
		}
		
		if(fromExcell) {
			masiveMeasur = SearchFromExcellFiles.generateMasiveMeasurFromExcelFile( year, person);
		}else{
		listMeasuring = MeasuringDAO.getValueMeasuringByObjectSortByColumnName("Person_ID", person, "Date");
		masiveMeasur = generateMasiveMeasur(year, listMeasuring);
		}
		
		String textMeasur ="";
		if(masiveMeasur.length>0) {
		textMeasur = setTextInfoMeasur(masiveMeasur, masiveMeasurName);
		}
		
		
		str = person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person) + "\n";
		
		str = str + year + "\n";
		str = str + ReadFileBGTextVariable.getGlobalTextVariableMap().get("code") + "\n";
		str = str + textKode;
			
		str = str + "\n";
		str = str + ReadFileBGTextVariable.getGlobalTextVariableMap().get("orders") +"\n";
		str = str + textSpis;
		
	
				
		str = str + "\n";
		str = str + ReadFileBGTextVariable.getGlobalTextVariableMap().get("measurSICH") + "\n";
		str = str + textMeasur;
		}else {
			str = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_NotInBase");
		}
		
		
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
		boolean fl_result=false;
		boolean fl_Koment=false;
		for (int i = 0; i < masiveMeasur.length; i++) {
			if(masiveMeasur[i][5]!=null && !masiveMeasur[i][5].isEmpty()) {
				fl_result = true;
			}
			if(masiveMeasur[i][10]!=null && !masiveMeasur[i][10].isEmpty()) {
				fl_Koment = true;
			}
		}
		
		
		int countColumn = 5;
		String measurString = "";
		String koment = "";
		if(fl_result) {
			countColumn = masiveMeasurName.length-1;
		}
		
		
		for (int i = 0; i < countColumn; i++) {
			measurString = measurString + getAddSpace(max[i], masiveMeasurName[i]) + masiveMeasurName[i];
		}
		if(fl_Koment) {
			measurString = measurString +"   "+ masiveMeasurName[10];
		}
		measurString = measurString + "\n";	
		
		for (int i = 0; i < masiveMeasur.length; i++) {
			for (int j = 0; j < countColumn; j++) {
				if(masiveMeasur[i][j]==null) {
					masiveMeasur[i][j] = "";
				}
			measurString = measurString + getAddSpace(max[j], masiveMeasur[i][j]) + masiveMeasur[i][j];
			}
			if(fl_Koment) {
				koment = masiveMeasur[i][10];
				if(koment == null) {
					koment = "";
				}
				measurString = measurString +"   " + koment;
			}
			measurString = measurString + "\n";
			
		}
		
		
		return measurString;
	}

	private static String[][] generateMasiveMeasur(String year, List<Measuring> listM) {
		String[][] masiveMeasur = new String[listM.size()*3][11];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		boolean fl;
		String data;
		int i = 0, index =0;
		for (Measuring measur : listM) {
			fl = false;
			masiveMeasur[i][10] = measur.getMeasurKoment();
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
		String[][] newMasiveMeasur = new String[i][11];
		for (int j = 0; j < newMasiveMeasur.length; j++) {
			newMasiveMeasur[j] = masiveMeasur[j];
			
		}
		
		return newMasiveMeasur;
	}

	
	
	
	private static String setTextInfoPersonStatus(String[][] masivePersonStatus, String[] masivePersonStatusName) {
		String personStatusString = "";
		String[][] masivePersonStatusNew = masivePersonStatus;
		int[] max = {5,7,8,15,15};
		int[] columnSize = getMaxSizecolumn(masivePersonStatus, max);
		Set<String> listYear = new HashSet<String>();
		for (int i = 0; i < masivePersonStatus.length; i++) {
			if(masivePersonStatus[i][0]!=null ) {
				listYear.add(masivePersonStatus[i][0]);
			}
		}
	
		for (String stringYear : listYear) {
			for (int i = 0; i < masivePersonStatus.length; i++) {
				if(masivePersonStatus[i][0]!=null && masivePersonStatus[i][0].equals(stringYear) ) {
				masivePersonStatusNew[i] = masivePersonStatus[i];
			}
		}
		}
		
		for (int j = 0; j < 5; j++) {
			personStatusString = personStatusString + getAddSpace(columnSize[j], masivePersonStatusName[j]) +masivePersonStatusName[j];	
		}
		personStatusString = personStatusString + "     "+ masivePersonStatusName[5]+ "\n";	
		
		for (int i = 0; i < masivePersonStatus.length; i++) {
			if(masivePersonStatus[i][0]!=null ) {
			for (int j = 0; j < 5; j++) {
				personStatusString = personStatusString + getAddSpace(columnSize[j], masivePersonStatusNew[i][j]) + masivePersonStatusNew[i][j];	
			}
			personStatusString = personStatusString + " "+ masivePersonStatusNew[i][5]+ "\n";	
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


	private static String[][] generateMasivePersonStatusNew(String year, List<PersonStatusNew> listP) {
		String[][] masivePersonStatus = new String[listP.size()][6];
		if(!year.trim().isEmpty()) {
			int k=0;
			for (PersonStatusNew perStat : listP) {

				String yyy = perStat.getYear();
				if (yyy.equals(year)) {
					masivePersonStatus[k] = generateRowByMasive( perStat);
					k++;
				}

				}
		}else {
			int k=0;
			for (PersonStatusNew perStat : listP) {
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
		masivePersonStatus[5] = "";
		if(perStat.getZabelejka()!=null) {
		masivePersonStatus[5] =  perStat.getZabelejka().replaceAll("\n", " ");
		}
		
		return masivePersonStatus;
	}
	
	static String[] generateRowByMasive( PersonStatusNew perStat) {
		
		String[] masivePersonStatus = new String[6];
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
		masivePersonStatus[0] = perStat.getYear() ;
		masivePersonStatus[1] = perStat.getWorkplace().getOtdel();
		masivePersonStatus[2] = perStat.getFormulyarName();
		masivePersonStatus[3] =  sdf.format(perStat.getStartDate());
		masivePersonStatus[4] =  sdf.format(perStat.getEndDate());
		masivePersonStatus[5] = "";
		if(perStat.getZabelejka() != null) {
		masivePersonStatus[5] =  perStat.getZabelejka().replaceAll("\n", " ");
		}
		
		return masivePersonStatus;
	}
	
	private static String[][] generateMasiveKodeStatusNew(String year, List<KodeStatus> listK, List<PersonStatusNew> listP, Person personInport, boolean fromExcell) {
		String yearKode="";
		String otdell = "";
		int index = -1;
		boolean isNewPersonStatusNewOject = false;
			String[][] masiveKode = new String[listK.size()][7];
		masiveKode = setDefoutValueInMasive(masiveKode);
			for (KodeStatus kodeStat : listK) {
				if (year.trim().isEmpty() || kodeStat.getYear().equals(year)) {
					
					if(!kodeStat.getYear().equals(yearKode)) {
					yearKode = kodeStat.getYear();
					index++;
					}
					masiveKode[index][0] = yearKode;
//					otdell = getOtdelByYearNew(yearKode, listP);
					otdell = "";
					PersonStatusNew perStat = PersonStatusNewDAO.getLastPersonStatusNewByPerson_YearSortByStartDate(personInport, yearKode);
					if(perStat != null) {
					otdell = perStat.getWorkplace().getOtdel();
					}
					System.out.println(listP.size()+" "+yearKode+" /*/*/ "+otdell);
					if(otdell.isEmpty() && countIteration < 2) {
						isNewPersonStatusNewOject = true;
						countIteration++;
					otdell = searchANDsetPrsonStatusNewForYear(personInport, yearKode);
					
					System.out.println(yearKode+" - "+otdell);
					}
					masiveKode[index][1] = otdell ;
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
			if(isNewPersonStatusNewOject) {
				createInfoPanelForPerson( year, personInport, fromExcell, countIteration);
			}
		return newMasiveKode;
	}
	
	
	private static String searchANDsetPrsonStatusNewForYear(Person person, String yearKode) {
		String otdel = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		PersonStatusNew PerStat = SearchFromExcellFiles.getPersonStatusNewFromExcelFileIntoPersonReference(yearKode, person);
		if(PerStat != null) {
			String str = PerStat.getPerson().getEgn()+" - "+PerStat.getFormulyarName()+" - "+PerStat.getYear()+" - "+
					sdf.format(PerStat.getStartDate())+" - "+sdf.format(PerStat.getEndDate())+" - "+sdf.format(PerStat.getDateSet())+" - "+PerStat.getWorkplace().getOtdel()+" - "+
					PerStat.getZabelejka()+" - "+PerStat.getUserWBC().getLastName();
			PersonStatusNewDAO.setObjectPersonStatusNewToTable(PerStat);
			System.out.println(str);
			otdel = PerStat.getWorkplace().getOtdel();
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
     	int columnYeare = 0;
     	Arrays.sort(arr, new Comparator<String[]>() {

	        
				@Override
				public int compare(String[] entry1, String[] entry2) {
					
					Integer y1 = Integer.parseInt(entry1[columnYeare]);
					Integer y2 = Integer.parseInt(entry2[columnYeare]);
					
					int sComp = y1.compareTo(y2);	
		        	 
		        	 if(sComp != 0) {
		        		 return sComp;
		        	 }
					
					if(entry1[0].equals("-")) {
						entry1[0] = "01.01.2000";
					}
					if(entry2[0].equals("-")) {
						entry2[0] = "01.01.2000";
					}
					
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
