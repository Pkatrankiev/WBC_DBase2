package PersonReference;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTextField;

import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Zone;

public class TextInAreaTextPanel {

	private static int columnSizeOtdel;
	private static Person person;
	private static String[] masivePersonStatusName = {"Year","Otdel","Spisak","start Date","end Date","Koment"};
	private static String[][] masivePersonStatus;
	private static String[] masiveZoneName;
	private static String[][] masiveKode;
	private static String[] masiveMeasurName = {"Year","Date","Doze[mSv]","Lab","Nuc.","Act.[Bq]","Inc.[%]","GGP[%]","Doze[%]"};
	private static String[][] masiveMeasur;
	
	protected static String createInfoPanelForPerson(JTextField textField_Year, Person personInport) {
		
		String year = textField_Year.getText();
		person = personInport;
		List<PersonStatus> listP = PersonStatusDAO.getValuePersonStatusByObjectSortByColumnName("Person_ID", person,"DateSet");
		masivePersonStatus = generateMasivePersonStatus(year, listP);
		String textSpis = setTextInfoPersonStatus(masivePersonStatus, masivePersonStatusName);
		
		masiveZoneName = getMasiveZoneName();
		List<KodeStatus> listK = KodeStatusDAO.getValueKodeStatusByObjectSortByColumnName("Person_ID", person, "Year");
		masiveKode = generateMasiveKodeStatus(year, listK, masivePersonStatus);
		String textKode = setTextInfoKode(masiveKode, masiveZoneName);
		
		
		List<Measuring> listM = MeasuringDAO.getValueMeasuringByObject("Person_ID", person);
		masiveMeasur = generateMasiveMeasur(year, listM);
		String textMeasur = setTextInfoMeasur(masiveMeasur, masiveMeasurName);
		
		String str = person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person) + "\n";
		
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
		int[] max = {5,12,11,7,12,8,8,8,8};
		boolean fl=false;
		for (int i = 0; i < masiveMeasur.length; i++) {
			if(masiveMeasur[i][4]!=null && !masiveMeasur[i][4].isEmpty()) {
				fl = true;
			}
		}
		
		
		int countColumn = 4;
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
		String[][] masiveMeasur = new String[listM.size()*3][9];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		boolean fl;
		String data;
		int i = 0;
		for (Measuring measur : listM) {
			fl = false;
			List<ResultsWBC> listR = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
			data = sdf.format(measur.getDate()).substring(6);
			System.out.println(data);
			if (year.trim().isEmpty() || data.equals(year)) {
				masiveMeasur[i][0] =  data;
				masiveMeasur[i][1] = sdf.format(measur.getDate());
				masiveMeasur[i][2] = measur.getDoze()+""; 
				masiveMeasur[i][3] =  measur.getLab().getLab();
				
				for (ResultsWBC result : listR) {
					masiveMeasur[i][4] = result.getNuclideWBC().getSymbol_nuclide();
					masiveMeasur[i][5] = result.getActivity()+"" ;
					masiveMeasur[i][6] = result.getPostaplenie()+"" ;
					masiveMeasur[i][7] = result.getGgp()+"" ;
					masiveMeasur[i][8] = result.getNuclideDoze()+"" ;
					i++;
					fl = true;
				}
				if(fl) {
					i--;
				}
				i++;
			
		}
	}
		String[][] newMasiveMeasur = new String[i][9];
		for (int j = 0; j < newMasiveMeasur.length; j++) {
			newMasiveMeasur[j] = masiveMeasur[j];
			
		}
		
		return newMasiveMeasur;
	}

	
	
	
	private static String setTextInfoPersonStatus(String[][] masivePersonStatus, String[] masivePersonStatusName) {
		String personStatusString = "";
		int[] columnSize = getMaxSize2and3column(masivePersonStatus);
		
		for (int j = 0; j < 5; j++) {
			System.out.println(columnSize[j]+" "+masivePersonStatusName[j]);
			personStatusString = personStatusString + getAddSpace(columnSize[j], masivePersonStatusName[j]) +masivePersonStatusName[j];	
		}
		personStatusString = personStatusString + "     "+ masivePersonStatusName[5]+ "\n";	
		
		for (int i = 0; i < masivePersonStatus.length; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.println(columnSize[j]+" "+masivePersonStatus[i][j]);
				personStatusString = personStatusString + getAddSpace(columnSize[j], masivePersonStatus[i][j]) + masivePersonStatus[i][j];	
			}
			personStatusString = personStatusString + " "+ masivePersonStatus[i][5]+ "\n";		
		}
		
	
		return personStatusString;
	}

	private static int[] getMaxSize2and3column(String[][] masivePersonStatus) {
		int[] max = {5,0,0,15,15};
		for (int i = 0; i < masivePersonStatus.length; i++) {
			if(masivePersonStatus[i][1].length()>max[1]) {
				max[1] = masivePersonStatus[i][1].length();
			}
			if(masivePersonStatus[i][2].length()>max[2]) {
				max[2] = masivePersonStatus[i][2].length();
			}
		}
		max[1] = max[1] +2;
		max[2] = max[2] +2;
		columnSizeOtdel = max[1];
		return max;
	}

	private static String[][] generateMasivePersonStatus(String year, List<PersonStatus> listP) {
		String[][] masivePersonStatus = new String[listP.size()][6];
		if(!year.trim().isEmpty()) {
			int k=0;
			for (PersonStatus perStat : listP) {
				List<Spisak_Prilogenia> listS =	Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByObject("Year", year );
				for (Spisak_Prilogenia spPr : listS) {
				if (perStat.getSpisak_prilogenia().getSpisak_Prilogenia_ID() == spPr.getSpisak_Prilogenia_ID()) {
					masivePersonStatus[k] = generateRowByMasive( perStat);
					k++;
				}
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
	
	
	
	private static String[][] generateMasiveKodeStatus(String year, List<KodeStatus> listK, String[][] masivePersonStatus) {
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
					masiveKode[index][1] = getOtdelByYear(yearKode, masivePersonStatus);
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
	
	private static String getOtdelByYear(String yearKode, String[][] masivePersonStatus) {
		String otdel = "";
		for (int i = 0; i < masivePersonStatus.length; i++) {
			if(masivePersonStatus[i][0].equals(yearKode)) {
				return masivePersonStatus[i][1];
			}
		}
		return otdel;
	}

	private static String[][] setDefoutValueInMasive(String[][] masiveKode) {
		for (int i = 0; i < masiveKode.length; i++) {
			masiveKode[i][0] = "-";
			masiveKode[i][1] = "-";
			masiveKode[i][2] = "-";
			masiveKode[i][3] = "-";
			masiveKode[i][4] = "-";
			masiveKode[i][5] = "-";
			masiveKode[i][6] = "-";
		}
		return masiveKode;
	}

	private static String setTextInfoKode(String[][] masiveKode, String[] masiveZoneName) {
		int[] max = {5, columnSizeOtdel, 7, 7, 7, 13, 13};
		String kodeString = "";
		
		for (int i = 0; i < masiveZoneName.length; i++) {
			kodeString = kodeString + getAddSpace(max[i], masiveZoneName[i]) + masiveZoneName[i];
		}
		kodeString = kodeString + "\n";	
		
		for (int i = 0; i < masiveKode.length; i++) {
//			if(!masiveKode[i][0].equals("-")) {
			for (int j = 0; j < masiveZoneName.length; j++) {
			kodeString = kodeString + getAddSpace(max[j], masiveKode[i][j]) + masiveKode[i][j];
		}
			kodeString = kodeString + "\n";
//			}
		}
		return kodeString;
	}

	private static String[] getMasiveZoneName() {
		String[] masiveZoneName = new String[7]; 
		masiveZoneName[0] = "Year";
		masiveZoneName[1] = "Otdel";
		int i=2;
		for (Zone zone : ZoneDAO.getAllValueZone()) {
			masiveZoneName[i] = zone.getNameTerritory();
			i++;
		}
		return masiveZoneName;
	}
	
	
	
	private static String getAddSpace(int space, String kodeString) {
		 String addString = "";
		
		for (int m = 0; m < space-kodeString.length(); m++) {
			addString = addString+" ";	
		}
		return addString;
	}
	
}
