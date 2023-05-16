package Aplication;

import MenuClasses.WBC_MainWindowFrame;

public class WBC_Main_ClassAplication {
 
public static void main(String[] args) {
	
	if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
		UpdateBDataFromExcellFiles.updataFromGodExcelFile();
		new WBC_MainWindowFrame();
		GeneralMethods.cerateDestinationDir();
	
		}

}

}
