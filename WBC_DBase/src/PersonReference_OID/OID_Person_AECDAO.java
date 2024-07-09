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

public class OID_Person_AECDAO {

	public static List<Person> getAllOID_Person_AEC() {
		List<Person> listPerson = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_person_new ";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				Person peson = new Person();
					
				peson.setEgn(result.getString("PRS_EGN"));
				peson.setFirstName(result.getString("PRS_NAME"));
				peson.setSecondName(result.getString("PRS_SURNAME"));
				peson.setLastName(result.getString("PRS_LASTNAME"));
				
				listPerson.add(peson);
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPerson;
		
	}
	
	
	
	public static OID_Person_AEC getOID_Person_AECByEGN(String egn) {
		List<OID_Person_AEC> listKodeStatus = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_person_new WHERE PRS_EGN = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, egn);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				OID_Person_AEC peson = new OID_Person_AEC();
				
				peson.setOid_Person_AEC_ID(result.getInt("PRS_ID"));
				peson.setEgn(result.getString("PRS_EGN"));
				peson.setFirstName(result.getString("PRS_NAME"));
				peson.setSecondName(result.getString("PRS_SURNAME"));
				peson.setLastName(result.getString("PRS_LASTNAME"));
				peson.setDateSet(result.getDate("PRS_MODIF_DATE"));
				
				listKodeStatus.add(peson);
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		if(listKodeStatus.size()>0) {
		return listKodeStatus.get(0);
		}else {
			return null;
		}
	}
	
	public static int get_ORG_STR_ID_By_Oid_Person_AEC_ID(int  Oid_Person_AEC_ID) {
		int id = 0;
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_V_PRS_CONTR_new WHERE PRS_ID = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Oid_Person_AEC_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				id = result.getInt("ORG_STR_ID");
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	
			return id;
		
	}
	
	public static int get_DEP_ID_By_ORG_STR_ID(int  ORG_STR_ID) {
		int id = 0;
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_org_struct_new WHERE ORG_STR_ID = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, ORG_STR_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				id = result.getInt("DEPT_ID");
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	
			return id;
		
	}
	
	public static String get_OS_POS_NAME_By_ORG_STR_ID(int  ORG_STR_ID) {
		String posName = "";
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_org_struct_new WHERE ORG_STR_ID = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, ORG_STR_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				posName = result.getString("OS_POS_NAME");
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	
			return posName;
		
	}
	
	
	
	
	
	public static String get_D_HCODE_By_DEP_ID(int  DEP_ID) {
		String hCode = "";
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_department_new WHERE DEPT_ID = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, DEP_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				hCode = result.getString("D_HCODE");
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	
			return hCode;
		
	}
	
	public static String get_DEPT_CODE_By_DEP_ID(int  DEP_ID) {
		String hCode = "";
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_department_new WHERE DEPT_ID = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, DEP_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				hCode = result.getString("DEPT_KOD");
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	
			return hCode;
		
	}
	
	public static String get_D_Name_By_DEP_ID(int  DEP_ID) {
		String hCode = "";
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			
		
			sql = "SELECT * FROM dbo_department_new WHERE DEPT_ID = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, DEP_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				hCode = result.getString("D_NAME");
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	
			return hCode;
		
	}



	
	
}
