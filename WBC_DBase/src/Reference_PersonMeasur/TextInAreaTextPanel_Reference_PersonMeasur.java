package Reference_PersonMeasur;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.Zone;
import PersonReference.TextInAreaTextPanel;

public class TextInAreaTextPanel_Reference_PersonMeasur {
	
	private static int maxNameLength = 0;
	private static String[][] masiveForInfoPanel;
	private static String[] masiveZoneName;

	public static String createInfoPanelForPerson(List<Person> listPersonInport, String year, Date dateStart, Date dateEnd) {
		
		masiveZoneName = createMasiveZoneName();
		int column = masiveZoneName.length;
		masiveForInfoPanel = kreateMasiveForInfoPanel(listPersonInport, year, dateStart, dateEnd,  column);
		
		String kodeString = generateTextForInfoPanel( masiveZoneName, masiveForInfoPanel);
		System.out.println(kodeString);
		
		return kodeString;
		
	}
	

	private static String[][] kreateMasiveForInfoPanel(List<Person> listPersonInport, String year, Date dateStart,
			Date dateEnd,  int column) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String  measur = "";
		
		int index = 0;
		String[][] masiveKode = new String[listPersonInport.size()][column];
		masiveKode = TextInAreaTextPanel.setDefoutValueInMasive(masiveKode);
		for (Person person : listPersonInport) {
			List<KodeStatus> listKodeStst = KodeStatusDAO.getKodeStatusByPersonYear(person, year);
			
			if(listKodeStst != null && listKodeStst.size()>0) {
				for (KodeStatus kodeStat : listKodeStst) {
					masiveKode[index][0] = (index + 1)+"";
				masiveKode[index][1] = person.getFirstName()+" "+person.getSecondName()+ " " +person.getLastName();
			
			
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
				
				measur = "-";
				
				List<Measuring>  listMeasur = MeasuringDAO.getValueMeasuringByPersonAndYear( person, dateStart, dateEnd);
				if(listMeasur.size()>0) {
					measur = "";
					for (Measuring meas : listMeasur) {
						measur += sdf.format(meas.getDate())+" ";
					}
					
				}
				masiveKode[index][7] =  measur;
				
				}
			
			}
			index++;
		}
		
		return masiveKode;
	}

	private static String generateTextForInfoPanel(String[] masiveZoneName, String[][] masiveKode) {
		
		String kodeString = "";
		if(masiveKode.length>0) {
			
			maxNameLength =  getMaxName(masiveKode);
			sortbyStartDateColumn(masiveKode, 1);
			int[] max = {5, maxNameLength,7, 7, 7, 13, 13, 20};
			for (int i = 0; i < masiveZoneName.length; i++) {
				if(i==0) {
				kodeString +=  masiveZoneName[i] + TextInAreaTextPanel.getAddSpace(max[i], masiveZoneName[i]);
				}else {
					kodeString += TextInAreaTextPanel.getAddSpace(max[i], masiveZoneName[i]) + masiveZoneName[i];	
				}
			}
			kodeString += "\n";		
			kodeString += "\n";		
			System.out.println(kodeString);
			
		for (int i = 0; i < masiveKode.length; i++) {
			masiveKode[i][0] = " "+(i+1)+"";
			for (int j = 0; j < masiveZoneName.length; j++) {
				
				if(j==7) {
					kodeString += "          "+  masiveKode[i][j] ;	
				}else
				if(j<=1) {
				kodeString +=   masiveKode[i][j] + TextInAreaTextPanel.getAddSpace(max[j], masiveKode[i][j]);
				}else {
					kodeString += TextInAreaTextPanel.getAddSpace(max[j], masiveKode[i][j]) + masiveKode[i][j];	
				}
			}
			kodeString += "\n";
			
		}
		
		
			}
		return kodeString;
	}
	
	private static String[] createMasiveZoneName() {
		String[] masiveZoneName = new String[8]; 
		masiveZoneName[0] = " №   ";
		masiveZoneName[1] = "Име презиме фамилия";
		masiveZoneName[7] = "Izmervane SICh";
		int i=2;
		for (Zone zone : ZoneDAO.getAllValueZone()) {
			masiveZoneName[i] = zone.getNameTerritory();
			i++;
		}
		return masiveZoneName;
	}
	
	 public static void sortbyStartDateColumn(String[][]arr, int columnSort)
	    {	    		    	
	       
	        Arrays.sort(arr, new Comparator<String[]>() {
	        	
	         
	          // Compare values according to columns
	          public int compare(String[] entry1, String[] entry2) {
	        	  String dateSet1 = entry1[columnSort];
	        	  String dateSet2 = entry2[columnSort];
	        	  
	          
			return dateSet1.compareTo(dateSet2);
	          }
	  });  // End of function call sort().
	        
//	      printArray(arr);  
	        
	    }
	 
	 public static int getMaxName(String[][]arr) {
		int max=0;
		 for (String[] strings : arr) {
			if(max < strings[1].length()) {
				max = strings[1].length();
			}
		}
		return max;
	 }


	 public static String[][] getMasiveForInfoPanel() {
		return masiveForInfoPanel;
	}


	 public static void setMasiveForInfoPanel(String[][] masiveForInfoPanel) {
		TextInAreaTextPanel_Reference_PersonMeasur.masiveForInfoPanel = masiveForInfoPanel;
	}

	 
	 public static void setMasiveZoneName(String[] masiveZoneName) {
		TextInAreaTextPanel_Reference_PersonMeasur.masiveZoneName = masiveZoneName;
	}

	public static String[] getMasiveZoneName() {
		return masiveZoneName;
	}
	 
	 
}
