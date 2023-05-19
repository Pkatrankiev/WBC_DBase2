package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import Aplication.AplicationMetods;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import Aplication.UpdateBDataFromExcellFiles;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import PersonReference.PersonExcellClass;

public class TestClasess {

	public static void  UpDataFromArhiveExcellFile() {
	
//	String key ="";
//	String key = "Person";
//	String key = "Spisak_Prilogenia";
//	String key = "PersonStatus";
//	String key = "KodeStatus";
//	String key = "Measuring";
//	String key = "ResultsWBC";
	
//	String year = "2010";
	
	boolean save = true;
//	boolean save = false;
	
	String[] keyM = {"Measuring", "ResultsWBC"};;
	for (int i = 2011; i < 2023; i++) {
	
	for (String key : keyM) {
		
		AplicationMetods.readInfoFromGodExcelFile(i+"",  key, save);
		
	}
	}
	
	
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void  testDelNoInfo() {
	
		List<PersonStatus> list = getValuePersonStatusByPersonAndDateSet();
		System.out.println("list.size() "+list.size());
		
		List<PersonStatus> listMOOR =  getValuePersonStatus() ;
		System.out.println("listMOOR.size() "+listMOOR.size());
		
		int k =0;
		for (PersonStatus personStatusMOOR : listMOOR) {
			
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PersonStatus personStatus = (PersonStatus) iterator.next();
		
			if(personStatus.getPersonStatus_ID() != personStatusMOOR.getPersonStatus_ID() 
					&& personStatus.getPerson().getId_Person() == personStatusMOOR.getPerson().getId_Person() 
					&& personStatus.getWorkplace().getId_Workplace() == personStatusMOOR.getWorkplace().getId_Workplace()) {
				PersonStatusDAO.deleteValuePersonStatus( personStatus);
				iterator.remove();
				System.out.println(k);
				k++;
			}
		}
		}
		
	
		
		
		
	}
	
	public static List<PersonStatus>  getValuePersonStatusByPersonAndDateSet() {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		
		Date dateSet = null;
		try {
			 dateSet = sdfrmt.parse("31.12.2022" );
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Spisak_Prilogenia_ID = 546  AND  DateSet = ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	public static List<PersonStatus>  getValuePersonStatus() {

SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		
		Date dateSet = null;
		try {
			 dateSet = sdfrmt.parse("31.12.2022" );
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where DateSet = ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	
	public static void  test() {
		
	
	List<PersonStatus> list1 = ReadPersonStatusFromExcelFile.getListPersonStatusWithoutSpisak_Prilogenia("7906113205");
	ReadPersonStatusFromExcelFile.ListPersonStatus(list1);
	
	ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list1);
	
	
	UpdateBDataFromExcellFiles.updataFromGodExcelFile();
	
	
	
	AplicationMetods.testGetListPersonSatatusByPersonAndDateAfterDateSet();
	
	
	List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
	System.out.println(list.size());
	ReadResultFromReport.PrintListReportMeasurClass(list);
	
	}


	@SuppressWarnings("unused")
	private static void testMeasuringToResultWCB() {
		Measuring measur;
		List<ResultsWBC> listresulterror = new ArrayList<>();
		List<ResultsWBC> listresult = ResultsWBCDAO.getAllValueResultsWBC();
		for (ResultsWBC resultsWBC : listresult) {
			System.out.println("-> "+resultsWBC.getResultsWBC_ID());
			measur = resultsWBC.getMeasuring();
			if(measur==null) {
				
				System.out.println("->>> "+resultsWBC.getResultsWBC_ID());
				listresulterror.add(resultsWBC);
			}else {
				if(measur.getDoze()==0.0) {
					System.out.println("--->>> 000 "+measur.getMeasuring_ID());
					listresulterror.add(resultsWBC);
				}
			}
		}
		
		for (ResultsWBC resultsWBC : listresulterror) {
			System.out.println(resultsWBC.getResultsWBC_ID());
		}
	}

	



	
	
	
}
