package Test;

import java.util.List;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Workplace;
import PersonReference.TextInAreaTextPanel;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;

public class TestMainClass {

	public static void main(String[] args) {
		if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
//			ActionIcone round = new ActionIcone();
//			 final Thread thread = new Thread(new Runnable() {
//			     @Override
//			     public void run() {
//			    	 
//			    	 new Reference_PersonMeasur_Frame(round, "");
//			    	     	
//			     }
//			    });
//			    thread.start();
		
//			ExtractSpectriWithDozaFromSICH2.getFile();
//			    TestClasess.UpDataFromArhiveExcellFile();
		
			
//			TestClasess.CheckCorrectionAllRowInSheets();
			
 
			TestClasess.ChengeWorkplacefromSpiusakPrilog();
			
			
			
//			String [][] masiveStrMonth = TestClasess.MasiveFromMonthCheckMeasurLab(LaboratoryDAO.getValueLaboratoryByID(1));
//						
//			int[] max = new int[7];
//			for (int i = 0; i < masiveStrMonth.length; i++) {
//				for (int j = 0; j < 7; j++) {
//				if(max[j]<masiveStrMonth[i][j].length()) {
//					max[j] = masiveStrMonth[i][j].length();
//				}
//				}
//			}
//				
//				
//			String kodeString = "";
//				for (int k = 0; k < masiveStrMonth.length; k++) {
//					for (int j = 0; j < 7; j++) {
//					kodeString += TextInAreaTextPanel.getAddSpace(max[j]+3, masiveStrMonth[k][j]) + masiveStrMonth[k][j];
//					
//	
//				}		
//					kodeString += "\n";	
//		}
//				System.out.println(kodeString);
				
	
				
		}
	}

}
