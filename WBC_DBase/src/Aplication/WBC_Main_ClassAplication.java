package Aplication;

import MenuClasses.WBC_MainWindowFrame;

public class WBC_Main_ClassAplication {
 
public static void main(String[] args) {
	
	if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
//		UpdateBDataFromExcellFiles.updataFromGodExcelFile();
		new WBC_MainWindowFrame();
		
		boolean afterExcelUpdate = false;
		WBC_MainWindowFrame.updateLastActualsDBaseFromExcelFile(afterExcelUpdate);
		
		GeneralMethods.cerateDestinationDir();
	
		}

}

}
