package Aplication;

import MenuClasses.WBC_MainWindowFrame;
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;

public class WBC_Main_ClassAplication {
 
public static void main(String[] args) {
	
	if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
//		UpdateBDataFromExcellFiles.updataFromGodExcelFile();
		 String version = "140324";
		new WBC_MainWindowFrame(version);
		
		boolean afterExcelUpdate = false;
		UpdateBDataFromExcellFiles.updateLastActualsDBaseFromExcelFile(afterExcelUpdate);
		
		GeneralMethods.cerateDestinationDir();
	
		}

}

}
