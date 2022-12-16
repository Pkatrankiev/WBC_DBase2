package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.conectToAccessDB;

public class LaboratoryDAO {

	public static void setValueLaboratory(String lab) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Laboratory (LAB) VALUES (?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, lab);
			
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectLaboratoryToTable(Laboratory laboratory) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Laboratory (LAB) VALUES (?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, laboratory.getLab());
			
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void updateValueLaboratory(Laboratory laboratory, int id_Laboratory) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Laboratory SET LAB = ? where Lab_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, laboratory.getLab());
			
			preparedStatement.setInt(2, id_Laboratory);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueLaboratory(int id_Laboratory) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Laboratory where Lab_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_Laboratory);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}
	
	public static List<Laboratory> getAllValueLaboratory() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Laboratory";
		List<Laboratory> list = new ArrayList<Laboratory>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Laboratory object = new Laboratory();
				object.setLab_ID(result.getInt("Lab_ID"));  
				object.setLab(result.getString("LAB"));
								
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
		
	public static List<Laboratory> getValueLaboratorySortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Laboratory  ORDER BY " + sortColumnName + " ASC";
		List<Laboratory> list = new ArrayList<Laboratory>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Laboratory object = new Laboratory();
				object.setLab_ID(result.getInt("Lab_ID"));  
				object.setLab(result.getString("LAB"));
				
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
	
	public static List<Laboratory> getValueLaboratoryByName(String name) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Laboratory  where LAB = ? ";
		
		List<Laboratory> list = new ArrayList<Laboratory>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, name);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Laboratory resultObject = new Laboratory();
				resultObject.setLab_ID(result.getInt("Lab_ID"));  
				resultObject.setLab(result.getString("LAB"));
				
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
	
	public static Laboratory getValueLaboratoryByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Laboratory  where Lab_ID = ? ";
		
		List<Laboratory> list = new ArrayList<Laboratory>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Laboratory resultObject = new Laboratory();
				resultObject.setLab_ID(result.getInt("Lab_ID"));  
				resultObject.setLab(result.getString("LAB"));
				
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
	
	
	public static List<Laboratory> getValueLaboratoryByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Laboratory  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";
		
		List<Laboratory> list = new ArrayList<Laboratory>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Laboratory resultObject = new Laboratory();
				resultObject.setLab_ID(result.getInt("Lab_ID"));  
				resultObject.setLab(result.getString("LAB"));
				
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

	public static String[] getMasiveLaboratory() {
		List<Laboratory> list =getAllValueLaboratory();
		String[] masive = new String[list.size()];
		int i=0;
		for (Laboratory object : list) {
			masive[i] = object.getLab();
			i++;
		}
		return masive;
	}
	
	
}
