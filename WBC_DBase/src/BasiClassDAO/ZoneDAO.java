package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Zone;
import BasicClassAccessDbase.conectToAccessDB;

public class ZoneDAO {

	public static void setValueZone(String nameTerritory) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Zone (NameTerritory) VALUES (?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, nameTerritory);
			
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectZoneToTable(Zone zone) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Zone (zone) VALUES (?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, zone.getNameTerritory());
			
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void updateValueZone(Zone zone, int id_Zone) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Zone SET NameTerritory = ? where Zone_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, zone.getNameTerritory());
			
			preparedStatement.setInt(2, id_Zone);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueZone(int id_Zone) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Zone where Zone_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_Zone);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}
	
	public static List<Zone> getAllValueZone() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Zone";
		List<Zone> list = new ArrayList<Zone>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Zone object = new Zone();
				object.setId_Zone(result.getInt("Zone_ID"));  
				object.setNameTerritory(result.getString("NameTerritory"));
								
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
		
	public static List<Zone> getValueZoneSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Zone  ORDER BY " + sortColumnName + " ASC";
		List<Zone> list = new ArrayList<Zone>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Zone object = new Zone();
				object.setId_Zone(result.getInt("Zone_ID"));  
				object.setNameTerritory(result.getString("NameTerritory"));
				
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
	
	public static List<Zone> getValueZoneByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Zone  where " + columnName + " = ? ";
		
		List<Zone> list = new ArrayList<Zone>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Zone resultObject = new Zone();
				resultObject.setId_Zone(result.getInt("Zone_ID"));  
				resultObject.setNameTerritory(result.getString("NameTerritory"));
				
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
	
	public static Zone getValueZoneByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Zone  where Zone_ID = ? LIMIT 1";
		
		List<Zone> list = new ArrayList<Zone>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Zone resultObject = new Zone();
				resultObject.setId_Zone(result.getInt("Zone_ID"));  
				resultObject.setNameTerritory(result.getString("NameTerritory"));
				
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
	
	public static Zone getValueZoneByNameTerritory(String nameTerritory) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Zone  where NameTerritory = ? LIMIT 1";
		
		List<Zone> list = new ArrayList<Zone>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, nameTerritory);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Zone resultObject = new Zone();
				resultObject.setId_Zone(result.getInt("Zone_ID"));  
				resultObject.setNameTerritory(result.getString("NameTerritory"));
				
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
	
	
	public static List<Zone> getValueZoneByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Zone  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";
		
		List<Zone> list = new ArrayList<Zone>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Zone resultObject = new Zone();
				resultObject.setId_Zone(result.getInt("Zone_ID"));  
				resultObject.setNameTerritory(result.getString("NameTerritory"));
				
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
