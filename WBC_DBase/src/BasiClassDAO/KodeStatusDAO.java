package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Zone;
import BasicClassAccessDbase.conectToAccessDB;

public class KodeStatusDAO {


	public static void setValueKodeStatus(Person Person,
			String kode,
			int zone_ID,
			boolean freeKode,
			String year,
			String zabelejkaKodeStatus,
			UsersWBC setData_UsersWBC,
			Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO KodeStatus (Person_ID, Kode, Zone_ID, FreeKode, Year, zabelejkaKodeStatus, SetData_UsersWBC_ID, DateSet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Person.getId_Person());
			preparedStatement.setString(2, kode);
			preparedStatement.setInt(3, zone_ID);
			preparedStatement.setBoolean(4, freeKode);
			preparedStatement.setString(5, year);
			preparedStatement.setString(6, zabelejkaKodeStatus);
			preparedStatement.setInt(7, setData_UsersWBC.getId_Users());
			preparedStatement.setDate(8, convertDate(dateSet));
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (!e.toString().contains("unique")) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
			}
		}
	}

	public static KodeStatus setObjectKodeStatusToTable(KodeStatus kodeStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO KodeStatus (Person_ID, Kode, Zone_ID, FreeKode, Year, zabelejkaKodeStatus, SetData_UsersWBC_ID, DateSet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, kodeStatus.getPerson().getId_Person());
			preparedStatement.setString(2, kodeStatus.getKode());
			preparedStatement.setInt(3, kodeStatus.getZone().getId_Zone());
			preparedStatement.setBoolean(4, kodeStatus.getisFreeKode());
			preparedStatement.setString(5, kodeStatus.getYear());
			preparedStatement.setString(6, kodeStatus.getZabelejkaKodeStatus());
			preparedStatement.setInt(7, kodeStatus.getSetData_UsersWBC().getId_Users());
			preparedStatement.setDate(8, convertDate(kodeStatus.getDateSet()));
			

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
		return kodeStatus;
	}

	public static void updateValueKodeStatus(KodeStatus kodeStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update KodeStatus SET Person_ID = ? , Kode = ? , Zone_ID = ? , FreeKode = ? , Year = ?, zabelejkaKodeStatus = ?, SetData_UsersWBC_ID = ?, DateSet = ?  where KodeStatus_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, kodeStatus.getPerson().getId_Person());
			preparedStatement.setString(2, kodeStatus.getKode());
			preparedStatement.setInt(3, kodeStatus.getZone().getId_Zone());
			preparedStatement.setBoolean(4, kodeStatus.getisFreeKode());
			preparedStatement.setString(5, kodeStatus.getYear());
			preparedStatement.setString(6, kodeStatus.getZabelejkaKodeStatus());
			preparedStatement.setInt(7, kodeStatus.getSetData_UsersWBC().getId_Users());
//			preparedStatement.setDate(8, (java.sql.Date) kodeStatus.getDateSet());
			if(kodeStatus.getDateSet() != null) {
			preparedStatement.setDate(8, convertDate(kodeStatus.getDateSet()));
			}

			preparedStatement.setInt(9, kodeStatus.getKodeStatus_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValueKodeStatus(int id_KodeStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from KodeStatus where KodeStatus_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_KodeStatus);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<KodeStatus> getAllValueKodeStatus() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeStatus";
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
							
				listKodeStatus.add(KodeStatus);
				
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listKodeStatus;
	}

	

	public static List<KodeStatus> getAllValueKodeStatusSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeStatus ORDER BY " + sortColumnName + " ASC";
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {

				KodeStatus KodeStatus = setKodeStatus(result);
				
				listKodeStatus.add(KodeStatus);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeStatus;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<KodeStatus> getValueKodeStatusByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeStatus where " + columnName + " = ? ";

		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
			break;
			case "Zone_ID": {
				preparedStatement.setObject(1, ((Zone) object).getId_Zone());
			}
			break;
			default:
				preparedStatement.setObject(1, object);
				break;
			}
						
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeStatus;
	}

	public static List<KodeStatus> getValueKodeStatusByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeStatus where " + columnName + " = ? ORDER BY " + sortColumnName + " ASC";

		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
			break;
			case "Zone_ID": {
				preparedStatement.setObject(1, ((Zone) object).getId_Zone());
			}
			break;
			default:
				preparedStatement.setObject(1, object);
				break;
			}
						
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeStatus;
	}

	public static List<KodeStatus> getKodeStatusByPersonZone(Person person, int zoneID) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			
		
			sql = "SELECT * FROM KodeStatus where Person_ID = ? and Zone_ID = ? ";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, person.getId_Person());
		
			preparedStatement.setInt(2, zoneID );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
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
	
	public static List<KodeStatus> getKodeStatusByPersonYear(Person person, String year) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			
		
			sql = "SELECT * FROM KodeStatus where Person_ID = ? and Year = ? ";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, person.getId_Person());
		
			preparedStatement.setString(2, year);
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
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
	
	
	public static List<KodeStatus> getKodeStatusByZoneAndKode(int zoneID, String kod) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			
		
			sql = "SELECT * FROM KodeStatus where Kode = ? and Zone_ID = ? ";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, kod);
		
			preparedStatement.setInt(2, zoneID );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
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
	
	public static KodeStatus getKodeStatusByZoneYaerAndKode(int zoneID, String year, String kod) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			sql = "SELECT * FROM KodeStatus where Year = ? and Kode = ? and Zone_ID = ? LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, year);
			preparedStatement.setString(2, kod);
			preparedStatement.setInt(3, zoneID );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
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
	
	public static List<KodeStatus> getListKodeStatusByZoneYaerAndKode(int zoneID, String year, String kod) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			sql = "SELECT * FROM KodeStatus where Year = ? and Kode = ? and Zone_ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, year);
			preparedStatement.setString(2, kod);
			preparedStatement.setInt(3, zoneID );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
			return listKodeStatus;
			
	}
	
	
	public static List<KodeStatus> getKodeStatusByYearZone(String year, int zoneID) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			
		
			sql = "SELECT * FROM KodeStatus where Year = ? and Zone_ID = ? ";	
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, year);
			preparedStatement.setInt(2, zoneID );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
			
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
	
	
	public static KodeStatus getKodeStatusByPersonZoneYear(Person person, int zoneID, String year) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {

			sql = "SELECT * FROM KodeStatus where Person_ID = ? and Zone_ID = ? and Year = ?  ORDER BY KodeStatus_ID DESC";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, person.getId_Person());
		
			preparedStatement.setInt(2,zoneID );
	
			preparedStatement.setString(3, year);
			
			result = preparedStatement.executeQuery();
		
		

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
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
	
	public static List<KodeStatus> getListKodeStatusByPersonZoneYear(Person person, int zoneID, String year) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {

			sql = "SELECT * FROM KodeStatus where Person_ID = ? and Zone_ID = ? and Year = ?  ORDER BY KodeStatus_ID DESC";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, person.getId_Person());
		
			preparedStatement.setInt(2,zoneID );
	
			preparedStatement.setString(3, year);
			
			result = preparedStatement.executeQuery();
		
		

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
			return listKodeStatus;
		
	}
	
	public static KodeStatus getKodeStatusByPersonZoneYaerAndKode(Person person, int zoneID, String year, String kod) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {
			sql = "SELECT * FROM KodeStatus where Person_ID = ? and Year = ? and Kode = ? and Zone_ID = ? LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, person.getId_Person());
			preparedStatement.setString(2, year);
			preparedStatement.setString(3, kod);
			preparedStatement.setInt(4, zoneID );
			
			result = preparedStatement.executeQuery();
	
			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
			
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
	
	
	
	public static List<KodeStatus> getKodeStatusByZoneAndYear(int zoneID, String year) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {

			sql = "SELECT * FROM KodeStatus where Zone_ID = ? and Year = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1,zoneID );
	
			preparedStatement.setString(2, year);
			
			result = preparedStatement.executeQuery();
		
		

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		return listKodeStatus;
		
	}
	
	
	public static KodeStatus getValueKodeStatusByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeStatus where KodeStatus_ID = ? LIMIT 1";

		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);;
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeStatus.size()>0?listKodeStatus.get(0):null;
	}
	
	private static KodeStatus setKodeStatus(ResultSet result) throws SQLException {
		KodeStatus KodeStatus = new KodeStatus();
		KodeStatus.setKodeStatus_ID(result.getInt("KodeStatus_ID"));
		Person Person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
		KodeStatus.setPerson(Person);
		KodeStatus.setKode(result.getString("Kode"));
		Zone zone = ZoneDAO.getValueZoneByID((result.getInt("Zone_ID")));
		KodeStatus.setZone(zone);
		KodeStatus.setisFreeKode(result.getBoolean("FreeKode"));
		KodeStatus.setYear(result.getString("Year"));
		KodeStatus.setZabelejkaKodeStatus(result.getString("zabelejkaKodeStatus"));
		int kk = 1;
		if(result.getInt("SetData_UsersWBC_ID")>1) {
			kk = result.getInt("SetData_UsersWBC_ID");
		}
		UsersWBC setData_UsersWBC = UsersWBCDAO.getValueUsersWBCByID(kk);
		KodeStatus.setSetData_UsersWBC(setData_UsersWBC);
		KodeStatus.setDateSet(result.getDate("DateSet"));
		return KodeStatus;
	}
	
	public static List<KodeStatus> getKodeStatusByYear(String year) {
		List<KodeStatus> listKodeStatus = new ArrayList<KodeStatus>();
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		ResultSet result;
		String sql;
		try {

			sql = "SELECT * FROM KodeStatus where Year = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, year);
			
			result = preparedStatement.executeQuery();
		
		

			while (result.next()) {
				KodeStatus KodeStatus = setKodeStatus(result);
				listKodeStatus.add(KodeStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		return listKodeStatus;
		
	}
	
	

	public static java.sql.Date convertDate(Date data) {
		java.sql.Date newDate = new java.sql.Date(data.getTime());
		return newDate;
	}

}
