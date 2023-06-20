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

		String sql = "INSERT INTO Workplace (FirmName, Otdel, SecondOtdelName) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, workplace.getFirmName());
			preparedStatement.setString(2, workplace.getOtdel());
			preparedStatement.setString(3,workplace.getSecondOtdelName());
			preparedStatement.setBoolean(4, workplace.getActual());
			preparedStatement.setString(5,workplace.getNapOtdelSector());
			
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

		String sqlUpdate = "Update Workplace SET FirmName = ?, Otdel = ?, SecondOtdelName = ?, Actual = ?, NapOtdelSector = ?  where Workplace_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, workplace.getFirmName());
			preparedStatement.setString(2, workplace.getOtdel());
			preparedStatement.setString(3,workplace.getSecondOtdelName());
			preparedStatement.setBoolean(4, workplace.getActual());
			preparedStatement.setString(5,workplace.getNapOtdelSector());
			
			preparedStatement.setInt(6, workplace.getId_Workplace());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueWorkplace(Workplace workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Workplace where Workplace_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, workplace.getId_Workplace());

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
				object.setActual(result.getBoolean("Actual"));
				object.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
	
	public static List<Workplace> getAllValueWorkplaceInSICH2() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Actual = true and NapOtdelSector is Not Null and NapOtdelSector Not Like '*(1)*'";
//		String sql = "SELECT * FROM Workplace";
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
				object.setActual(result.getBoolean("Actual"));
				object.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
	
	public static List<Workplace> getAllValueWorkplaceInSICH1() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Actual = true and  NapOtdelSector  Like '*(1)*'";
//		String sql = "SELECT * FROM Workplace";
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
				object.setActual(result.getBoolean("Actual"));
				object.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
	
	public static List<Workplace> getAllValueWorkplaceInSICH3() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Actual = true and NapOtdelSector is Null ";
//		String sql = "SELECT * FROM Workplace";
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
				object.setActual(result.getBoolean("Actual"));
				object.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
	
	
	
	public static List<Workplace> getAllValueWorkplaceNOTInSICH2() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Actual = true and NapOtdelSector is Null or NapOtdelSector Like '*(1)*'";
//		String sql = "SELECT * FROM Workplace";
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
				object.setActual(result.getBoolean("Actual"));
				object.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
	
	
	
	public static List<Workplace> getAllActualValueWorkplace() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Actual = true ";
		
		List<Workplace> list = new ArrayList<Workplace>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Workplace resultObject = new Workplace();
				resultObject.setId_Workplace(result.getInt("Workplace_ID"));
				resultObject.setFirmName (result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));
				resultObject.setActual(result.getBoolean("Actual"));
				resultObject.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
				resultObject.setActual(result.getBoolean("Actual"));
				resultObject.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
				object.setActual(result.getBoolean("Actual"));
				object.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
	
	public static List<Workplace> getActualValueWorkplaceByFirm(String firmName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace  where Actual = true and FirmName = ? ";
		
		List<Workplace> list = new ArrayList<Workplace>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, firmName);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Workplace resultObject = new Workplace();
				resultObject.setId_Workplace(result.getInt("Workplace_ID"));
				resultObject.setFirmName (result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));
				resultObject.setActual(result.getBoolean("Actual"));
				resultObject.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
		String sql = "SELECT * FROM Workplace  where Workplace_ID = ? LIMIT 1";
		
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
				resultObject.setActual(result.getBoolean("Actual"));
				resultObject.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
				resultObject.setActual(result.getBoolean("Actual"));
				resultObject.setNapOtdelSector(result.getString("NapOtdelSector"));
				
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
