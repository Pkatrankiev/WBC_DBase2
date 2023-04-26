package BasicClassAccessDbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;

public class conectToAccessDB {

	public static Connection conectionBDtoAccess() {
		
		
		String databaseURL = ReadFileBGTextVariable.getGlobalTextVariableMap().get("databaseURL");
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
