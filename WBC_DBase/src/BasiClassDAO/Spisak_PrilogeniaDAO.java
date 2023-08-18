package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import Aplication.ObrabDublicateSpisak_Prilogenie;
import Aplication.ResourceLoader;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;


public class Spisak_PrilogeniaDAO {

	public static void setValueSpisak_Prilogenia(String formulyarName, String year, Date startDate, Date endDate,
			Workplace workplace, String zabelejka) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Spisak_Prilogenia (FormulyarName, Year, StartDate, EndDate, Workplace_ID, Zabelejka) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, formulyarName);
			preparedStatement.setObject(2, year);
			preparedStatement.setObject(3, startDate);
			preparedStatement.setObject(4, endDate);
			preparedStatement.setObject(5, workplace.getId_Workplace());
			preparedStatement.setObject(6, zabelejka);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
	}

	public static void setObjectSpisak_PrilogeniaToTable(Spisak_Prilogenia Spisak_Prilogenia) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Spisak_Prilogenia (FormulyarName, Year, StartDate, EndDate, Workplace_ID, Zabelejka) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, Spisak_Prilogenia.getFormulyarName());
			preparedStatement.setObject(2, Spisak_Prilogenia.getYear());
			preparedStatement.setObject(3, Spisak_Prilogenia.getStartDate());
			preparedStatement.setObject(4, Spisak_Prilogenia.getEndDate());
			preparedStatement.setObject(5, Spisak_Prilogenia.getWorkplace().getId_Workplace());
			preparedStatement.setObject(6, Spisak_Prilogenia.getZabelejka());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
//			e.printStackTrace();
//			if(e.toString().indexOf("unique constraint or index violation")>0) {
//				obrabDublicateSpisak_Prilogenia(Spisak_Prilogenia);
//			}
//				
//			ResourceLoader.appendToFile(e);
		}
	}
	
	public static void obrabDublicateSpisak_Prilogenia(Spisak_Prilogenia dublicateSpPr) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		Spisak_Prilogenia newSpPr = new Spisak_Prilogenia();
		String year = dublicateSpPr.getYear();
		List<Spisak_Prilogenia> listSpPr = getValueSpisak_PrilogeniaByObject("FormulyarName", dublicateSpPr.getFormulyarName());
		
		for (Spisak_Prilogenia spisak_Prilogenia : listSpPr) {
			if(year.equals(spisak_Prilogenia.getYear())) {
				newSpPr = spisak_Prilogenia;
			}
		}
		sdfrmt.format(newSpPr.getStartDate());
		String strDublObject = dublicateSpPr.getFormulyarName()+", "+ dublicateSpPr.getYear()+", "+ sdfrmt.format(dublicateSpPr.getStartDate())+", "+
					sdfrmt.format(dublicateSpPr.getEndDate())+", "+ dublicateSpPr.getWorkplace().getFirmName()+", "+ dublicateSpPr.getZabelejka();
	
		String pol1_DublObject = newSpPr.getFormulyarName(); 
		String pol2_DublObject = newSpPr.getYear();
		
		String strNewObject = newSpPr.getFormulyarName()+", "+ newSpPr.getYear()+", "+ sdfrmt.format(newSpPr.getStartDate())+", "+
				sdfrmt.format(newSpPr.getEndDate())+", "+ newSpPr.getWorkplace().getFirmName()+", "+ newSpPr.getZabelejka(); 
		
		String pol1_NewObject = newSpPr.getFormulyarName();  
		String pol2_NewObject = newSpPr.getYear();
		
		if(!strDublObject.equals(strNewObject)) {
		final JFrame frame = new JFrame();
		
		ObrabDublicateSpisak_Prilogenie obrabDublicateSpisak_Prilogenie = new ObrabDublicateSpisak_Prilogenie(frame, strDublObject, pol1_DublObject, pol2_DublObject,
				strNewObject, pol1_NewObject, pol2_NewObject);
		
		obrabDublicateSpisak_Prilogenie.setVisible(true);
		String[] str = ObrabDublicateSpisak_Prilogenie.get_DublicatePole();
		
		if(str!= null) {
			newSpPr.setFormulyarName(str[0]);
			newSpPr.setYear(str[1]);
			setObjectSpisak_PrilogeniaToTable(newSpPr);
		}
		
		}
		
	}
	

	public static void updateValueSpisak_Prilogenia(Spisak_Prilogenia Spisak_Prilogenia) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Spisak_Prilogenia SET FormulyarName = ?, Year = ?, StartDate = ?, EndDate = ?, Workplace_ID = ?, Zabelejka = ?   where Spisak_Prilogenia_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setObject(1, Spisak_Prilogenia.getFormulyarName());
			preparedStatement.setObject(2, Spisak_Prilogenia.getYear());
			preparedStatement.setObject(3, Spisak_Prilogenia.getStartDate());
			preparedStatement.setObject(4, Spisak_Prilogenia.getEndDate());
			preparedStatement.setObject(5, Spisak_Prilogenia.getWorkplace().getId_Workplace());
			preparedStatement.setObject(6, Spisak_Prilogenia.getZabelejka());

			preparedStatement.setObject(7, Spisak_Prilogenia.getSpisak_Prilogenia_ID());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}

	}

	public static void deleteValueSpisak_Prilogenia(int id_Spisak_Prilogenia) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Spisak_Prilogenia where Spisak_Prilogenia_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_Spisak_Prilogenia);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
	}

	public static List<Spisak_Prilogenia> getAllValueSpisak_Prilogenia() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia";
		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Spisak_Prilogenia object = new Spisak_Prilogenia();
				object.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				object.setFormulyarName(result.getString("FormulyarName"));
				object.setYear(result.getString("Year"));
				object.setStartDate(result.getDate("StartDate"));
				object.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				object.setWorkplace(wp);
				object.setZabelejka(result.getString("Zabelejka"));

				list.add(object);
			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static List<Spisak_Prilogenia> getValueSpisak_PrilogeniaSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  ORDER BY " + sortColumnName + " ASC";
		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Spisak_Prilogenia object = new Spisak_Prilogenia();
				object.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				object.setFormulyarName(result.getString("FormulyarName"));
				object.setYear(result.getString("Year"));
				object.setStartDate(result.getDate("StartDate"));
				object.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				object.setWorkplace(wp);
				object.setZabelejka(result.getString("Zabelejka"));

				list.add(object);
			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static List<Spisak_Prilogenia> getValueSpisak_PrilogeniaByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  where " + columnName + " = ? ";

		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			switch (columnName) {
			case "Workplace_ID": {
				preparedStatement.setObject(1, ((Workplace) object).getId_Workplace());
			}
			break;
			default:
				preparedStatement.setObject(1, object);
				break;
			}
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Spisak_Prilogenia resultObject = new Spisak_Prilogenia();

				resultObject.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				resultObject.setFormulyarName(result.getString("FormulyarName"));
				resultObject.setYear(result.getString("Year"));
				resultObject.setStartDate(result.getDate("StartDate"));
				resultObject.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				resultObject.setWorkplace(wp);
				resultObject.setZabelejka(result.getString("Zabelejka"));

				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static List<Spisak_Prilogenia> getValueSpisak_PrilogeniaByObjectSortByColumnName(String columnName,
			Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  where " + columnName + " = ?  ORDER BY " + sortColumnName
				+ " ASC";

		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			switch (columnName) {
			case "Workplace_ID": {
				preparedStatement.setObject(1, ((Workplace) object).getId_Workplace());
			}
			break;
			default:
			preparedStatement.setObject(1, object);
			break;
			}
			
			
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Spisak_Prilogenia resultObject = new Spisak_Prilogenia();

				resultObject.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				resultObject.setFormulyarName(result.getString("FormulyarName"));
				resultObject.setYear(result.getString("Year"));
				resultObject.setStartDate(result.getDate("StartDate"));
				resultObject.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				resultObject.setWorkplace(wp);
				resultObject.setZabelejka(result.getString("Zabelejka"));

				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static Spisak_Prilogenia getValueSpisak_PrilogeniaByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  where Spisak_Prilogenia_ID = ? LIMIT 1";

		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Spisak_Prilogenia resultObject = new Spisak_Prilogenia();

				resultObject.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				resultObject.setFormulyarName(result.getString("FormulyarName"));
				resultObject.setYear(result.getString("Year"));
				resultObject.setStartDate(result.getDate("StartDate"));
				resultObject.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				resultObject.setWorkplace(wp);
				resultObject.setZabelejka(result.getString("Zabelejka"));
				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		if(list.size()>0) {
		return list.get(0);
		}else {
			return null;	
		}
	}

	public static Spisak_Prilogenia getLastSaveObjectFromValueSpisak_PrilogeniaByYear_Workplace_StartDate(String year, Date StartDate, int Workplace_ID) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  where Year = ? and StartDate = ? and Workplace_ID = ? ORDER BY Spisak_Prilogenia_ID  DESC";

		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, year);
			preparedStatement.setObject(2, StartDate);
			preparedStatement.setObject(3, Workplace_ID);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Spisak_Prilogenia resultObject = new Spisak_Prilogenia();

				resultObject.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				resultObject.setFormulyarName(result.getString("FormulyarName"));
				resultObject.setYear(result.getString("Year"));
				resultObject.setStartDate(result.getDate("StartDate"));
				resultObject.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				resultObject.setWorkplace(wp);
				resultObject.setZabelejka(result.getString("Zabelejka"));
				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		if(list.size()>0) {
		return list.get(0);
		}else {
			return null;	
		}
	}
	
	public static List<Spisak_Prilogenia>  getListSpisak_PrilogeniaByYear_Workplace(String year, int Workplace_ID) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  where Year = ?  and Workplace_ID = ? ORDER BY Spisak_Prilogenia_ID  DESC";

		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, year);
			preparedStatement.setObject(2, Workplace_ID);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Spisak_Prilogenia resultObject = new Spisak_Prilogenia();

				resultObject.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				resultObject.setFormulyarName(result.getString("FormulyarName"));
				resultObject.setYear(result.getString("Year"));
				resultObject.setStartDate(result.getDate("StartDate"));
				resultObject.setEndDate(result.getDate("EndDate"));
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				resultObject.setWorkplace(wp);
				resultObject.setZabelejka(result.getString("Zabelejka"));
				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		
			return list;	
		
	}
	
	
}
