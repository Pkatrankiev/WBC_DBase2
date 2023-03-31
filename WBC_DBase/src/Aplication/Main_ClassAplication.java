package Aplication;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import java.util.List;


import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import FrameViewClass.MainWindowWBC_DBase;


public class Main_ClassAplication {
 
public static void main(String[] args) {

	
	
//	******************************* MainFarameWindow ****************************	
	
	if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
		new MainWindowWBC_DBase();
		GeneralMethods.cerateDestinationDir();
		
	}
	
//	******************************* MainFarameWindow ****************************	
		
	
//	ReadExcelFileWBC.test ();
	
	
	
//	******************************* first add Data in AccessDBase ****************************
	
//	String key ="";
//	String key = "Person";
//	String key = "Spisak_Prilogenia";
//	String key = "PersonStatus";
//	String key = "KodeStatus";
//	String key = "Measuring";
//	String key = "ResultsWBC";
	
//	String year = "2022";
	
//	boolean save = true;
//	boolean save = false;
	
//	AplicationMetods.readInfoFromGodExcelFile(year,  key, save);
	

//	******************************* first add Data in AccessDBase ****************************
	
	

	
//	List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
//	System.out.println(list.size());
//	ReadResultFromReport.PrintListReportMeasurClass();



	
}


}
