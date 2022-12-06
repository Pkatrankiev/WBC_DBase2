package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;
import BasicClassAccessDbase.conectToAccessDB;

public class KodeGenerateDAO {

	public static void setValueKodeGenerate(Workplace workplace, Zone zone, String letter_L, String letter_R, int startCount, int endCount) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO KodeGenerate (Workplace_ID, Zone_ID, Letter_L, Letter_R, StartCount, EndCount) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, workplace.getId_Workplace());
			preparedStatement.setInt(2, zone.getId_Zone());
			preparedStatement.setString(3, letter_L);
			preparedStatement.setString(4, letter_R);
			preparedStatement.setInt(5, startCount);
			preparedStatement.setInt(6, endCount);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void setObjectKodeGenerateToTable(KodeGenerate kodeGenerate) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO KodeGenerate (Workplace_ID, Zone_ID, Letter_L, Letter_R, StartCount, EndCount) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, kodeGenerate.getWorkplace().getId_Workplace());
			preparedStatement.setInt(2,  kodeGenerate.getZone().getId_Zone());
			preparedStatement.setString(3,  kodeGenerate.getLetter_L());
			preparedStatement.setString(4,  kodeGenerate.getLetter_R());
			preparedStatement.setInt(5,  kodeGenerate.getStartCount());
			preparedStatement.setInt(6,  kodeGenerate.getEndCount());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void updateValueKodeGenerate(KodeGenerate kodeGenerate, int id_KodeGenerate) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update KodeGenerate SET Workplace_ID = ? , Zone_ID = ? , Letter_L = ? , Letter_R = ? , StartCount = ? , EndCount = ?  where KodeGenerate_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, kodeGenerate.getWorkplace().getId_Workplace());
			preparedStatement.setInt(2,  kodeGenerate.getZone().getId_Zone());
			preparedStatement.setString(3,  kodeGenerate.getLetter_L());
			preparedStatement.setString(4,  kodeGenerate.getLetter_R());
			preparedStatement.setInt(5,  kodeGenerate.getStartCount());
			preparedStatement.setInt(6,  kodeGenerate.getEndCount());

			preparedStatement.setInt(7, id_KodeGenerate);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValueKodeGenerate(int id_KodeGenerate) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from KodeGenerate where KodeGenerate_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_KodeGenerate);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<KodeGenerate> getAllValueKodeGenerate() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeGenerate";
		List<KodeGenerate> listKodeGenerate = new ArrayList<KodeGenerate>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				KodeGenerate kodeGenerate = new KodeGenerate();
				kodeGenerate.setKodeGenerate_ID(result.getInt("KodeGenerate_ID"));
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				kodeGenerate.setWorkplace(workplace);
				Zone zone = ZoneDAO.getValueZoneByID((result.getInt("Zone_ID")));
				kodeGenerate.setZone(zone);
				kodeGenerate.setLetter_L(result.getString("Letter_L"));
				kodeGenerate.setLetter_R(result.getString("Letter_R"));
				kodeGenerate.setStartCount(result.getInt("StartCount"));
				kodeGenerate.setEndCount(result.getInt("EndCount"));
				listKodeGenerate.add(kodeGenerate);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listKodeGenerate;
	}

	public static List<KodeGenerate> getAllValueKodeGenerateSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeGenerate ORDER BY " + sortColumnName + " ASC";
		List<KodeGenerate> listKodeGenerate = new ArrayList<KodeGenerate>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				KodeGenerate kodeGenerate = new KodeGenerate();
				kodeGenerate.setKodeGenerate_ID(result.getInt("KodeGenerate_ID"));
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				kodeGenerate.setWorkplace(workplace);
				Zone zone = ZoneDAO.getValueZoneByID((result.getInt("Zone_ID")));
				kodeGenerate.setZone(zone);
				kodeGenerate.setLetter_L(result.getString("Letter_L"));
				kodeGenerate.setLetter_R(result.getString("Letter_R"));
				kodeGenerate.setStartCount(result.getInt("StartCount"));
				kodeGenerate.setEndCount(result.getInt("EndCount"));
				listKodeGenerate.add(kodeGenerate);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeGenerate;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<KodeGenerate> getValueKodeGenerateByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeGenerate where " + columnName + " = ? ";

		List<KodeGenerate> listKodeGenerate = new ArrayList<KodeGenerate>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Workplace_ID": {
				preparedStatement.setObject(1, ((Workplace) object).getId_Workplace());
			}
			case "Zone_ID": {
				preparedStatement.setObject(1, ((Zone) object).getId_Zone());
			}
			
			default:
				preparedStatement.setObject(1, object);
			}
						
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				KodeGenerate kodeGenerate = new KodeGenerate();
				kodeGenerate.setKodeGenerate_ID(result.getInt("KodeGenerate_ID"));
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				kodeGenerate.setWorkplace(workplace);
				Zone zone = ZoneDAO.getValueZoneByID((result.getInt("Zone_ID")));
				kodeGenerate.setZone(zone);
				kodeGenerate.setLetter_L(result.getString("Letter_L"));
				kodeGenerate.setLetter_R(result.getString("Letter_R"));
				kodeGenerate.setStartCount(result.getInt("StartCount"));
				kodeGenerate.setEndCount(result.getInt("EndCount"));
				listKodeGenerate.add(kodeGenerate);
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeGenerate;
	}

	public static KodeGenerate getValueKodeGenerateByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM KodeGenerate where KodeGenerate_ID = ? ";

		List<KodeGenerate> listKodeGenerate = new ArrayList<KodeGenerate>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				KodeGenerate kodeGenerate = new KodeGenerate();
				kodeGenerate.setKodeGenerate_ID(result.getInt("KodeGenerate_ID"));
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				kodeGenerate.setWorkplace(workplace);
				Zone zone = ZoneDAO.getValueZoneByID((result.getInt("Zone_ID")));
				kodeGenerate.setZone(zone);
				kodeGenerate.setLetter_L(result.getString("Letter_L"));
				kodeGenerate.setLetter_R(result.getString("Letter_R"));
				kodeGenerate.setStartCount(result.getInt("StartCount"));
				kodeGenerate.setEndCount(result.getInt("EndCount"));
				listKodeGenerate.add(kodeGenerate);
			}
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listKodeGenerate.get(0);
	}

	
	
}
