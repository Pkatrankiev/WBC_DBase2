package Aplication;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import FrameViewClass.MainWindowWBC_DBase;
import PersonManagement.PersonelManegementMethods;
import SearchFreeKode.SearchFreeKodeFrame;


public class Main_ClassAplication {
 
public static void main(String[] args) {

	
	
//	******************************* MainFarameWindow ****************************	
	
	if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
//		UpdateBDataFromExcellFiles.updataFromGodExcelFile();
//		new MainWindowWBC_DBase();
//		GeneralMethods.cerateDestinationDir();
		
		
//		PersonelManegementMethods.getMasiveKodeStatusFromExcelFiles();
		
		List<Measuring> List = MeasuringDAO.getAllValueMeasuring1();
		System.out.println("333333333333333333333333333333333333333333333333333333");
		int k=0;
		for (Measuring measuring : List) {
			System.out.println("-> "+k);
			MeasuringDAO.setObjectMeasuringToTable(measuring);
			k++;
		}
		
		}
	
//	******************************* MainFarameWindow ****************************	
		
	
//	ReadExcelFileWBC.test ();
//	List<PersonStatus> list = ReadPersonStatusFromExcelFile.getListPersonStatusWithoutSpisak_Prilogenia("7906113205");
//	ReadPersonStatusFromExcelFile.ListPersonStatus(list);
//	
//	ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list);
	
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
	
	
	
	
//	UpdateBDataFromExcellFiles.updataFromGodExcelFile();
	
	
	
//	AplicationMetods.testGetListPersonSatatusByPersonAndDateAfterDateSet();
	
	
//	List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
//	System.out.println(list.size());
//	ReadResultFromReport.PrintListReportMeasurClass();



	
}




}
