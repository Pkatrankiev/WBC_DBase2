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

public class OID_Person_VODAO {

	public static OID_Person_VO getOID_Person_VOByEGN(String egn) {
		List<OID_Person_VO> listOID_Person_VO = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			sql = "SELECT * FROM personalV WHERE prs_egn = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, egn);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				OID_Person_VO peson = new OID_Person_VO();
				
				peson.setEgn (result.getString("prs_egn"));
				peson.setFirstName(result.getString("prs_name"));
				peson.setSecondName(result.getString("prs_prezname"));
				peson.setLastName(result.getString("prs_familia"));
				peson.setPredID(result.getInt("pred_id"));
				
				listOID_Person_VO.add(peson);
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		if(listOID_Person_VO.size()>0) {
		return listOID_Person_VO.get(0);
		}else {
			return null;
		}
	}
	
	public static String getPredptiqtieByPred_ID(int Pred_ID) {
		
		String peson = "";
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			sql = "SELECT * FROM predprT WHERE id = ?";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Pred_ID);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {
				peson = result.getString("predpriatie");
			
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		return peson;
		
	}

	public static List<Person> getAllOID_Person_VO() {
		List<Person> listPerson = new ArrayList<>();
		
		Connection connection = conectToAccessDB.conectionBDtoAccessOID();

		String sql;
		try {
			sql = "SELECT * FROM personalV ";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet result = preparedStatement.executeQuery();
			
			
			while (result.next()) {

				Person peson = new Person();
					
				peson.setEgn (result.getString("prs_egn"));
				peson.setFirstName(result.getString("prs_name"));
				peson.setSecondName(result.getString("prs_prezname"));
				peson.setLastName(result.getString("prs_familia"));
				
				listPerson.add(peson);
				
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
			return listPerson;
		
	}
	
	
}
