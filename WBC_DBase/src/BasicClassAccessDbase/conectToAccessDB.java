package BasicClassAccessDbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectToAccessDB {

	public static Connection conectionBDtoAccess() {
		
		String databaseURL = "jdbc:ucanaccess://d://WBC_DBase.mdb";
	Connection connection = null;
	 try  {
		connection = DriverManager.getConnection(databaseURL);
		
	 } catch (SQLException ex) {
         ex.printStackTrace();
     }
	return connection;
	}
}
