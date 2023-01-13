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
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Measuring_Koment;
import BasicClassAccessDbase.conectToAccessDB;

public class Measuring_KomentDAO {

	public static void setValueMeasuring_Koment(Measuring measuring, String measurKoment) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Measuring_Koment (Measuring_ID, MeasurKoment) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, measuring.getMeasuring_ID());
			preparedStatement.setString(2, measurKoment);
			
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void setObjectMeasuring_KomentToTable(Measuring_Koment measuring_Koment) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Measuring_Koment (Measuring_ID, MeasurKoment) VALUES (?, ?)";


		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, measuring_Koment.getMeasuring().getMeasuring_ID());
			preparedStatement.setString(2, measuring_Koment.getMeasurKoment());
			
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void updateValueMeasuring_Koment(Measuring_Koment measuring_Koment) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Measuring_Koment SET  Measuring_ID = ? , MeasurKoment = ?  where Measuring_Koment_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, measuring_Koment.getMeasuring().getMeasuring_ID());
			preparedStatement.setString(2, measuring_Koment.getMeasurKoment());
			
			
			preparedStatement.setInt(3, measuring_Koment.getMeasuring_Koment_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValueMeasuring_Koment(int id_Measuring_Koment) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Measuring_Koment where Measuring_Koment_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_Measuring_Koment);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<Measuring_Koment> getAllValueMeasuring_Koment() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring_Koment";
		List<Measuring_Koment> listMeasuring_Koment = new ArrayList<Measuring_Koment>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring_Koment measuring_Koment = new Measuring_Koment();
				
				measuring_Koment.setMeasuring_Koment_ID(result.getInt("Measuring_Koment_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				measuring_Koment.setMeasuring(measuring);
				measuring_Koment.setMeasurKoment(result.getString("MeasurKoment"));
				
				listMeasuring_Koment.add(measuring_Koment);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listMeasuring_Koment;
	}

	public static List<Measuring_Koment> getAllValueMeasuring_KomentSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring_Koment ORDER BY " + sortColumnName + " ASC";
		List<Measuring_Koment> listMeasuring_Koment = new ArrayList<Measuring_Koment>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring_Koment measuring_Koment = new Measuring_Koment();

				measuring_Koment.setMeasuring_Koment_ID(result.getInt("Measuring_Koment_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				measuring_Koment.setMeasuring(measuring);
				measuring_Koment.setMeasurKoment(result.getString("MeasurKoment"));
				
				listMeasuring_Koment.add(measuring_Koment);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listMeasuring_Koment;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<Measuring_Koment> getValueMeasuring_KomentByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring_Koment where " + columnName + " = ? ";

		List<Measuring_Koment> listMeasuring_Koment = new ArrayList<Measuring_Koment>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Measuring_ID": {
				preparedStatement.setObject(1, ((Measuring) object).getMeasuring_ID());
			}
			break;
			
					
			default:
				preparedStatement.setObject(1, object);
			}
			
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring_Koment measuring_Koment = new Measuring_Koment();

				measuring_Koment.setMeasuring_Koment_ID(result.getInt("Measuring_Koment_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				measuring_Koment.setMeasuring(measuring);
				measuring_Koment.setMeasurKoment(result.getString("MeasurKoment"));
				
				listMeasuring_Koment.add(measuring_Koment);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listMeasuring_Koment;
	}


	public static Measuring_Koment getValueMeasuring_KomentByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring_Koment where Measuring_Koment_ID = ? LIMIT 1";

		List<Measuring_Koment> listMeasuring_Koment = new ArrayList<Measuring_Koment>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring_Koment measuring_Koment = new Measuring_Koment();

				measuring_Koment.setMeasuring_Koment_ID(result.getInt("Measuring_Koment_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				measuring_Koment.setMeasuring(measuring);
				measuring_Koment.setMeasurKoment(result.getString("MeasurKoment"));
				
				listMeasuring_Koment.add(measuring_Koment);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listMeasuring_Koment.get(0);
	}

	
	
	
	
}
