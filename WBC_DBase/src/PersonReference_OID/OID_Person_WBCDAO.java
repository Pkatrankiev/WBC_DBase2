package PersonReference_OID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.conectToAccessDB;

public class OID_Person_WBCDAO {
	
	public static List<Person> getlistAllOID_Person_WBC() {
	List<Person> listPerson = new ArrayList<>();
	
	Connection connection = conectToAccessDB.conectionBDtoAccessOID();

	String sql;
	try {
		
		sql = "SELECT * FROM tblMain";	
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
	
		ResultSet result = preparedStatement.executeQuery();
		
		
		while (result.next()) {
			
				Person peson = new Person();
					
				peson.setEgn (result.getString("ЕГН"));
				peson.setFirstName(result.getString("name"));
				peson.setSecondName(result.getString("surname"));
				peson.setLastName(result.getString("lastname"));
				
				listPerson.add(peson);
				
			
		}
	} catch (SQLException e) {
		ResourceLoader.appendToFile( e);
		e.printStackTrace();
	}
	
		return listPerson;
	
	}
	
	
	public static List<OID_Person_WBC> getlist_OID_Person_WBCByEGN(String egn) {
		List<OID_Person_WBC> listKodeStatus = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();
		System.out.println("conectionBDtoAccessOID "+ connection.TRANSACTION_READ_COMMITTED);
		String sql;
		try {
			
		
			sql = "SELECT * FROM tblMain WHERE ЕГН = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, egn);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				OID_Person_WBC peson = new OID_Person_WBC();
				
				peson.setEgn (result.getString("ЕГН"));
				peson.setFirstName(result.getString("name"));
				peson.setSecondName(result.getString("surname"));
				peson.setLastName(result.getString("lastname"));
				peson.setZsr1(result.getString("zsr1"));
				peson.setZsr1n(result.getInt("zsr1n"));
				peson.setZsr1b(result.getString("zsr1b"));
				peson.setZsr2(result.getString("zsr2"));
				peson.setZsr2n(result.getInt("zsr2n"));
				peson.setZsr2b(result.getString("zsr2b"));
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
	
	public static List<OID_Person_WBC> getlist_All_OID_Person_WBC() {
		List<OID_Person_WBC> listKodeStatus = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();
		System.out.println("conectionBDtoAccessOID "+ connection.TRANSACTION_READ_COMMITTED);
		String sql;
		try {
			
		
			sql = "SELECT * FROM tblMain";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				OID_Person_WBC peson = new OID_Person_WBC();
				
				peson.setEgn (result.getString("ЕГН"));
				peson.setFirstName(result.getString("name"));
				peson.setSecondName(result.getString("surname"));
				peson.setLastName(result.getString("lastname"));
				peson.setZsr1(result.getString("zsr1"));
				peson.setZsr1n(result.getInt("zsr1n"));
				peson.setZsr1b(result.getString("zsr1b"));
				peson.setZsr2(result.getString("zsr2"));
				peson.setZsr2n(result.getInt("zsr2n"));
				peson.setZsr2b(result.getString("zsr2b"));
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
