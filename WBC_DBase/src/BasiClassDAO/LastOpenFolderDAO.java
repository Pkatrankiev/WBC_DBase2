package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.LastOpenFolder;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class LastOpenFolderDAO {

	public static void setLastOpenFolder(String action, UsersWBC userAction, String nameOpenFolder) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		System.out.println("----------------* "+action+" "+nameOpenFolder);
		String sql = "INSERT INTO LastOpenFolder (Action, UserAction, NameOpenFolder) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, action);
			preparedStatement.setInt(2, userAction.getId_Users());
			preparedStatement.setString(3, nameOpenFolder);
		
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
			
			
		} catch (SQLException e) {
//			if (!e.toString().contains("unique")) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
//			}
		}
	}

	public static LastOpenFolder setObjectLastOpenFolder(LastOpenFolder lastOpenFolder) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO LastOpenFolder (Action, UserAction, NameOpenFolder) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, lastOpenFolder.getAction());
			preparedStatement.setInt(2, lastOpenFolder.getUserAction().getId_Users());
			preparedStatement.setString(3, lastOpenFolder.getNameOpenFolder());
			

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
			
			
		} catch (SQLException e) {
			if (e.toString().contains("unique")) {
				return null;
			}
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
			
		}
		return lastOpenFolder;
	}

	public static void updateValueLastOpenFolder(LastOpenFolder lastOpenFolder) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update LastOpenFolder SET Action = ? , UserAction = ? , NameOpenFolder = ?  where LastOpenFolder_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, lastOpenFolder.getAction());
			preparedStatement.setInt(2, lastOpenFolder.getUserAction().getId_Users());
			preparedStatement.setString(3, lastOpenFolder.getNameOpenFolder());
			
			preparedStatement.setInt(4, lastOpenFolder.getLastOpenFolder_ID());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
			
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValueLastOpenFolder(int id_lastOpenFolder) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from LastOpenFolder where LastOpenFolder_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_lastOpenFolder);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<LastOpenFolder> getAllValueLastOpenFolder() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM LastOpenFolder";
		List<LastOpenFolder> listLastOpenFolder = new ArrayList<LastOpenFolder>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				LastOpenFolder lastOpenFolder = new LastOpenFolder();
				lastOpenFolder.setLastOpenFolder_ID(result.getInt("LastOpenFolder_ID"));
				lastOpenFolder.setAction(result.getString("Action"));
				int kk = 1;
				if(result.getInt("UserAction")>1) {
					kk = result.getInt("UserAction");
				}
				UsersWBC userAction = UsersWBCDAO.getValueUsersWBCByID(kk);
				lastOpenFolder.setUserAction(userAction);
				lastOpenFolder.setNameOpenFolder(result.getString("NameOpenFolder"));
				
				listLastOpenFolder.add(lastOpenFolder);
			}
			

			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listLastOpenFolder;
	}

	public static LastOpenFolder getLastOpenFolderByAction_User(String action, UsersWBC userAction) {
		List<LastOpenFolder> listLastOpenFolder = new ArrayList<LastOpenFolder>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			sql = "SELECT * FROM LastOpenFolder where Action = ? and UserAction = ? LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, action);
			preparedStatement.setInt(2, userAction.getId_Users() );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				LastOpenFolder lastOpenFolder = new LastOpenFolder();
				lastOpenFolder.setLastOpenFolder_ID(result.getInt("LastOpenFolder_ID"));
				lastOpenFolder.setAction(result.getString("Action"));
				int kk = 1;
				if(result.getInt("UserAction")>1) {
					kk = result.getInt("UserAction");
				}
				UsersWBC userActionResult = UsersWBCDAO.getValueUsersWBCByID(kk);
				lastOpenFolder.setUserAction(userActionResult);
				lastOpenFolder.setNameOpenFolder(result.getString("NameOpenFolder"));
				
				listLastOpenFolder.add(lastOpenFolder);
			}
			

			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		if(listLastOpenFolder.size()>0) {
			return listLastOpenFolder.get(0);
			}else {
				return null;
			}
	}	
	
	
	public static String getNameOpenFolderByActionUser(String action, UsersWBC userAction) {
		List<LastOpenFolder> listLastOpenFolder = new ArrayList<LastOpenFolder>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			sql = "SELECT * FROM LastOpenFolder where Action = ? and UserAction = ? LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, action);
			preparedStatement.setInt(2, userAction.getId_Users() );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				LastOpenFolder lastOpenFolder = new LastOpenFolder();
				lastOpenFolder.setLastOpenFolder_ID(result.getInt("LastOpenFolder_ID"));
				lastOpenFolder.setAction(result.getString("Action"));
				int kk = 1;
				if(result.getInt("UserAction")>1) {
					kk = result.getInt("UserAction");
				}
				UsersWBC userActionResult = UsersWBCDAO.getValueUsersWBCByID(kk);
				lastOpenFolder.setUserAction(userActionResult);
				lastOpenFolder.setNameOpenFolder(result.getString("NameOpenFolder"));
				
				listLastOpenFolder.add(lastOpenFolder);
			}
			

			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		if(listLastOpenFolder.size()>0) {
			return listLastOpenFolder.get(0).getNameOpenFolder();
			}else {
				return "";
			}
	}	
	
	
}
