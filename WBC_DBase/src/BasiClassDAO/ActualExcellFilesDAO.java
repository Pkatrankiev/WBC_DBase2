package BasiClassDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.conectToAccessDB;

public class ActualExcellFilesDAO {

	public static void setValueActualExcellFiles(String actualExcellFiles_Name, Date actualExcellFiles_Date) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO ActualExcellFiles (ActualExcellFiles_Name, ActualExcellFiles_Date) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, actualExcellFiles_Name);
			preparedStatement.setDate(2, actualExcellFiles_Date);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectActualExcellFilesToTable(ActualExcellFiles actualExcellFiles) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO ActualExcellFiles (ActualExcellFiles_Name, ActualExcellFiles_Date) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, actualExcellFiles.getActualExcellFiles_Name());
			preparedStatement.setDate(2, actualExcellFiles.getActualExcellFiles_Date());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void updateValueActualExcellFiles(ActualExcellFiles actualExcellFiles) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update ActualExcellFiles SET ActualExcellFiles_Name = ?, ActualExcellFiles_Date = ?  where ActualExcellFiles_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, actualExcellFiles.getActualExcellFiles_Name());
			preparedStatement.setDate(2, actualExcellFiles.getActualExcellFiles_Date());
			
			preparedStatement.setInt(3, actualExcellFiles.getActualExcellFiles_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	
	public static List<ActualExcellFiles> getAllValueActualExcellFiles() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ActualExcellFiles";
		List<ActualExcellFiles> list = new ArrayList<ActualExcellFiles>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				ActualExcellFiles object = new ActualExcellFiles();
				object.setActualExcellFiles_ID(result.getInt("ActualExcellFiles_ID"));
				object.setActualExcellFiles_Name( result.getString("ActualExcellFiles_Name"));
				object.setActualExcellFiles_Date(result.getDate("ActualExcellFiles_Date"));
				
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

	public static ActualExcellFiles getValueActualExcellFilesByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ActualExcellFiles  where ActualExcellFiles_ID = ? LIMIT 1";
		
		List<ActualExcellFiles> list = new ArrayList<ActualExcellFiles>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				ActualExcellFiles object = new ActualExcellFiles();
				object.setActualExcellFiles_ID(result.getInt("ActualExcellFiles_ID"));
				object.setActualExcellFiles_Name( result.getString("ActualExcellFiles_Name"));
				object.setActualExcellFiles_Date(result.getDate("ActualExcellFiles_Date"));
				
				list.add(object);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list.get(0);
	}
	
	public static ActualExcellFiles getValueActualExcellFilesByName(String actualExcellFiles_Name) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ActualExcellFiles  where ActualExcellFiles_Name = ? LIMIT 1";
		
		List<ActualExcellFiles> list = new ArrayList<ActualExcellFiles>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, actualExcellFiles_Name);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				ActualExcellFiles object = new ActualExcellFiles();
				object.setActualExcellFiles_ID(result.getInt("ActualExcellFiles_ID"));
				object.setActualExcellFiles_Name( result.getString("ActualExcellFiles_Name"));
				object.setActualExcellFiles_Date(result.getDate("ActualExcellFiles_Date"));
				
				list.add(object);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list.get(0);
	}
	
	
	
	
}
