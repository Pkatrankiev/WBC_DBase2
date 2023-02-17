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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
	String key = "PersonStatus";
//	String key = "KodeStatus";
//	String key = "Measuring";
//	String key = "ResultsWBC";
	
	String year = "2009";
	
	boolean save = true;
//	boolean save = false;
	
//	AplicationMetods.readInfoFromGodExcelFile(year,  key, save);
	

//	******************************* first add Data in AccessDBase ****************************
	
	
//	FromExcelFile();
	
//	List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
//	System.out.println(list.size());
//	ReadResultFromReport.PrintListReportMeasurClass();

}

public static void FromExcelFile() {
	
	Connection connection = conectToAccessDB.conectionBDtoAccess();
	String sql = "SELECT * FROM PersonStatus";
	List<Integer> listPersonStatus = new ArrayList<Integer>();

	try {
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);

		while (result.next()) {
			
			if(Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"))==null){
				listPersonStatus.add(result.getInt("PersonStatus_ID"));
				System.out.println(result.getInt("PersonStatus_ID")+"  "+result.getInt("Spisak_Prilogenia_ID")+"  "+result.getInt("Person_ID")+"  "+result.getDate("DateSet"));
			}
			
			
			statement.close();
			connection.close();
			
		}
	} catch (SQLException e) {
		e.printStackTrace();
		ResourceLoader.appendToFile( e);
	}
		
//	for (Integer integer : listPersonStatus) {
//		System.out.println(integer);
//		PersonStatusDAO.deleteValuePersonStatus(integer);
//	}
	
	
}


}
