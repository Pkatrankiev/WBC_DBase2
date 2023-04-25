package BasicClassAccessDbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Aplication.ReadFileBGTextVariable;

public class conectToAccessDB {

	public static Connection conectionBDtoAccess() {
		
		
		String databaseURL = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseURL");
		String databaseEncript = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseEncript");
	Connection connection = null;
	 try  {
		connection = DriverManager.getConnection(databaseURL, "", databaseEncript);
		
	 } catch (SQLException ex) {
         ex.printStackTrace();
     }
	return connection;
	}
	
	public static Connection conectionBDtoAccessOID() {
		
		
		String databaseURL = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseOID");
		@SuppressWarnings("unused")
		String databaseEncript = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseEncript");
	Connection connection = null;
	 try  {
		connection = DriverManager.getConnection(databaseURL);
		
	 } catch (SQLException ex) {
         ex.printStackTrace();
     }
	return connection;
	}
	
}
