package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class UsersWBCDAO {

	public static void setValueUsersWBC(String name,	String lastName, String nikName,	String pass,  String lastName_EG, boolean acting, boolean isAdmin) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO UsersWBC (Name, LastName, NikName, Pass, LastName_EG, acting, isAdmin) VALUES (?, ?, ?, ?,?, ?,?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, nikName);
			preparedStatement.setString(4, pass);
			preparedStatement.setString(5, lastName_EG);
			preparedStatement.setBoolean(6, acting);
			preparedStatement.setBoolean(7, isAdmin);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectUsersWBCToTable(UsersWBC usersWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO UsersWBC (Name, LastName, NikName, Pass, LastName_EG, acting, isAdmin) VALUES (?, ?, ?, ?,?, ?,?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usersWBC.getName());
			preparedStatement.setString(2, usersWBC.getLastName());
			preparedStatement.setString(3, usersWBC.getNikName());
			preparedStatement.setString(4, usersWBC.getPass());
			preparedStatement.setString(5, usersWBC.getLastName_EG());
			preparedStatement.setBoolean(6, usersWBC.getActing());
			preparedStatement.setBoolean(7, usersWBC.get_isAdmin());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void updateValueUsersWBC(UsersWBC usersWBC, int id_UsersWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update usersWBC SET Name = ?, LastName = ?, NikName = ?, Pass = ?, LastName_EG = ?, acting = ? , isAdmin = ?  where UsersWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, usersWBC.getName());
			preparedStatement.setString(2, usersWBC.getLastName());
			preparedStatement.setString(3, usersWBC.getNikName());
			preparedStatement.setString(4, usersWBC.getPass());
			preparedStatement.setString(5, usersWBC.getLastName_EG());
			preparedStatement.setBoolean(6, usersWBC.getActing());
			preparedStatement.setBoolean(7, usersWBC.get_isAdmin());
			
			preparedStatement.setInt(7, id_UsersWBC);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueUsersWBC(int id_UsersWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from UsersWBC where UsersWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_UsersWBC);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}
	
	public static List<UsersWBC> getAllValueUsersWBC() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC";
		List<UsersWBC> list = new ArrayList<UsersWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				UsersWBC object = new UsersWBC();
				object.setId_Users(result.getInt("UsersWBC_ID"));
				object.setName (result.getString("Name"));
				object.setLastName(result.getString("LastName"));
				object.setNikName(result.getString("NikName"));
				object.setPass(result.getString("Pass"));
				object.setLastName_EG(result.getString("LastName_EG"));
				object.setActing(result.getBoolean("acting"));
				object.set_isAdmin(result.getBoolean("isAdmin"));
				
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
		
	public static List<UsersWBC> getValueUsersWBCSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC  ORDER BY " + sortColumnName + " ASC";
		List<UsersWBC> list = new ArrayList<UsersWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				UsersWBC object = new UsersWBC();
				object.setId_Users(result.getInt("UsersWBC_ID"));
				object.setName (result.getString("Name"));
				object.setLastName(result.getString("LastName"));
				object.setNikName(result.getString("NikName"));
				object.setPass(result.getString("Pass"));
				object.setLastName_EG(result.getString("LastName_EG"));
				object.setActing(result.getBoolean("acting"));
				object.set_isAdmin(result.getBoolean("isAdmin"));
				
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
	
	public static List<UsersWBC> getValueUsersWBCByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC  where " + columnName + " = ? ";
		
		List<UsersWBC> list = new ArrayList<UsersWBC>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				UsersWBC resultObject = new UsersWBC();
				resultObject.setId_Users(result.getInt("UsersWBC_ID"));
				resultObject.setName (result.getString("Name"));
				resultObject.setLastName(result.getString("LastName"));
				resultObject.setNikName(result.getString("NikName"));
				resultObject.setPass(result.getString("Pass"));
				resultObject.setLastName_EG(result.getString("LastName_EG"));
				resultObject.setActing(result.getBoolean("acting"));
				resultObject.set_isAdmin(result.getBoolean("isAdmin"));
				
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
	
	public static UsersWBC getValueUsersWBCByID(int id ) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC  where UsersWBC_ID = ? LIMIT 1";
		
		List<UsersWBC> list = new ArrayList<UsersWBC>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				UsersWBC resultObject = new UsersWBC();
				resultObject.setId_Users(result.getInt("UsersWBC_ID"));
				resultObject.setName (result.getString("Name"));
				resultObject.setLastName(result.getString("LastName"));
				resultObject.setNikName(result.getString("NikName"));
				resultObject.setPass(result.getString("Pass"));
				resultObject.setLastName_EG(result.getString("LastName_EG"));
				resultObject.setActing(result.getBoolean("acting"));
				resultObject.set_isAdmin(result.getBoolean("isAdmin"));
				
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
	
	public static List<String> getAllValueUsersWBCLastName_EG() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC";
		List<String> list = new ArrayList<String>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
			
				list.add(result.getString("LastName_EG"));
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	
	public static List<UsersWBC> getValueUsersWBCByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";
		
		List<UsersWBC> list = new ArrayList<UsersWBC>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				UsersWBC resultObject = new UsersWBC();
				resultObject.setId_Users(result.getInt("UsersWBC_ID"));
				resultObject.setName (result.getString("Name"));
				resultObject.setLastName(result.getString("LastName"));
				resultObject.setNikName(result.getString("NikName"));
				resultObject.setPass(result.getString("Pass"));
				resultObject.setLastName_EG(result.getString("LastName_EG"));
				resultObject.setActing(result.getBoolean("acting"));
				resultObject.set_isAdmin(result.getBoolean("isAdmin"));
				
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

	public static String[] getMasiveUserWBCNames() {
		List<UsersWBC> list = getAllValueUsersWBC();
		String[] masive = new String[list.size()];
		int i=0;
		for (UsersWBC object :list) {
			masive[i] = object.getName()+" "+object.getLastName();
			i++;
		}
		return masive;
	}



	public static List<UsersWBC> getValueUsersWBCByActing() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM UsersWBC  where acting = true ";
		
		List<UsersWBC> list = new ArrayList<UsersWBC>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				UsersWBC resultObject = new UsersWBC();
				resultObject.setId_Users(result.getInt("UsersWBC_ID"));
				resultObject.setName (result.getString("Name"));
				resultObject.setLastName(result.getString("LastName"));
				resultObject.setNikName(result.getString("NikName"));
				resultObject.setPass(result.getString("Pass"));
				resultObject.setLastName_EG(result.getString("LastName_EG"));
				resultObject.setActing(result.getBoolean("acting"));
				resultObject.set_isAdmin(result.getBoolean("isAdmin"));
				
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
