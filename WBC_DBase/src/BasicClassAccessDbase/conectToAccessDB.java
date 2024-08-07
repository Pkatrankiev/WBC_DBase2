package BasicClassAccessDbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;

public class conectToAccessDB {

	public static Connection conectionBDtoAccess() {
		
		String databaseURL = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseURL");
		
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
			databaseURL = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseURL_test");
		}
		
		
		
		
		String databaseEncript = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseEncript");
	Connection connection = null;
	 try  {
		connection = DriverManager.getConnection(databaseURL, "", databaseEncript);
		
	 } catch (SQLException e) {
			
		 String str = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorTextDialogNotDBaseFile");
				AplicationMetods.MessageDialog(str+"\n"+databaseURL.substring(18).replace("//", "\\"));
			
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
			System.exit(0);
     }
	return connection;
	}
	
	public static Connection conectionBDtoAccessOID() {
		
		
		String databaseURL = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseOID");
		@SuppressWarnings("unused")
		String databaseEncriptOID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseEncriptOID");
	Connection connection = null;
	
	 try  {
	
		connection = DriverManager.getConnection(databaseURL);
		
	 } catch (SQLException ex) {
         ex.printStackTrace();
     }
	return connection;
	}
	
		
	
	
	
	
	public static List<String> getKodeStatusByPersonZone(String egn) {
		List<String> listKodeStatus = new ArrayList<>();
		System.out.println("4444444444444444");
		Connection connection = conectionBDtoAccessOID();
//		ResultSet result;
		String sql;
		System.out.println("555555555555555555555555555");
		try {
			
		
			sql = "SELECT * FROM tblMain WHERE ЕГН = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			
			preparedStatement.setString(1, egn);
//		
			
			ResultSet result = preparedStatement.executeQuery();
			
//			result = preparedStatement.executeQuery();
	
//			Statement statement = connection.createStatement();
//			ResultSet result = statement.executeQuery(sql);
			
			
			
			while (result.next()) {
			
				listKodeStatus.add(result.getString("name"));
				listKodeStatus.add(result.getString("surname"));
				listKodeStatus.add(result.getString("lastname"));
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		if(listKodeStatus.size()>0) {
		return listKodeStatus;
		}else {
			return null;
		}
	}
	
	
	
}
