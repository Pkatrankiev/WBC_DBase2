package PersonReference_OID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.conectToAccessDB;

public class VlizaniqDAO {

	public static List<Vlizaniq> getlist_VlizaniqByEGN(String egn) {
		List<Vlizaniq> listKodeStatus = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM tblDnevnik WHERE egn = ? ORDER BY datain ASC";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, egn);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				Vlizaniq peson = new Vlizaniq();
				
				peson.setEgn (result.getString("egn"));
				peson.setDateIn(result.getDate("datain"));
				peson.setTimeIn(result.getTime("timein"));
				peson.setTimeOut(result.getTime("timeout"));
				peson.setDoza(result.getDouble("doza"));
				peson.setZona(result.getInt("zsr"));
				peson.setUserKod(result.getString("usern"));
				peson.setDateSet(result.getDate("moment"));
				
				listKodeStatus.add(peson);
				
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
