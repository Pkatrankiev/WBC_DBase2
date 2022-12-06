package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;

public class WorkplaceDAO {

	public static void setValueWorkplace(String firmName,	String otdel, String secondOtdelName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Workplace (FirmName, Otdel, SecondOtdelName) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, firmName);
			preparedStatement.setString(2, otdel);
			preparedStatement.setString(3, secondOtdelName);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectWorkplaceToTable(Workplace workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Workplace (FirmName, Otdel, SecondOtdelName) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, workplace.getFirmName());
			preparedStatement.setString(2, workplace.getOtdel());
			preparedStatement.setString(3,workplace.getSecondOtdelName());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void updateValueWorkplace(Workplace workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Workplace SET FirmName = ?, Otdel = ?, SecondOtdelName = ?  where Workplace_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, workplace.getFirmName());
			preparedStatement.setString(2, workplace.getOtdel());
			preparedStatement.setString(3,workplace.getSecondOtdelName());
			
			preparedStatement.setInt(4, workplace.getId_Workplace());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueWorkplace(int id_Workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Workplace where Workplace_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_Workplace);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}
	
	public static List<Workplace> getAllValueWorkplace() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace";
		List<Workplace> list = new ArrayList<Workplace>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Workplace object = new Workplace();
				object.setId_Workplace(result.getInt("Workplace_ID"));
				object.setFirmName (result.getString("FirmName"));
				object.setOtdel(result.getString("Otdel"));
				object.setSecondOtdelName(result.getString("SecondOtdelName"));
				
				list.add(object);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
		
	public static List<Workplace> getValueWorkplaceSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  ORDER BY " + sortColumnName + " ASC";
		List<Workplace> list = new ArrayList<Workplace>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Workplace object = new Workplace();
				object.setId_Workplace(result.getInt("Workplace_ID"));
				object.setFirmName (result.getString("FirmName"));
				object.setOtdel(result.getString("Otdel"));
				object.setSecondOtdelName(result.getString("SecondOtdelName"));
				
				list.add(object);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static List<Workplace> getValueWorkplaceByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where " + columnName + " = ? ";
		
		List<Workplace> list = new ArrayList<Workplace>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Workplace resultObject = new Workplace();
				resultObject.setId_Workplace(result.getInt("Workplace_ID"));
				resultObject.setFirmName (result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static Workplace getValueWorkplaceByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Workplace_ID = ? ";
		
		List<Workplace> list = new ArrayList<Workplace>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Workplace resultObject = new Workplace();
				resultObject.setId_Workplace(result.getInt("Workplace_ID"));
				resultObject.setFirmName (result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list.get(0);
	}
	
	
	public static List<Workplace> getValueWorkplaceByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";
		
		List<Workplace> list = new ArrayList<Workplace>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Workplace resultObject = new Workplace();
				resultObject.setId_Workplace(result.getInt("Workplace_ID"));
				resultObject.setFirmName (result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	
	
}
