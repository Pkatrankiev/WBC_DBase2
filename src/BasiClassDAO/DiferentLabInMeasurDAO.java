package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.DiferentLabInMeasur;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;

public class DiferentLabInMeasurDAO {

	public static void setValueDiferentLabInMeasur(Laboratory labWorkplace, Measuring measur, Workplace workplace, String year, boolean check) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO DiferentLabInMeasur (labWorkplace, measur, workplace, year, check) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, labWorkplace.getLab_ID());
			preparedStatement.setObject(2, measur.getMeasuring_ID());
			preparedStatement.setObject(3, workplace.getId_Workplace());
			preparedStatement.setString(4, year);
			preparedStatement.setBoolean(5, check);
			
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
	
	public static void setObjectDiferentLabInMeasur(DiferentLabInMeasur diferentLabInMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO DiferentLabInMeasur (labWorkplace, measur, workplace, year, check) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, diferentLabInMeasur.getLabWorkplace().getLab_ID());
			preparedStatement.setObject(2, diferentLabInMeasur.getMeasur().getMeasuring_ID());
			preparedStatement.setObject(3, diferentLabInMeasur.getWorkplace().getId_Workplace());
			preparedStatement.setString(4, diferentLabInMeasur.getYear());
			preparedStatement.setBoolean(5, diferentLabInMeasur.getCheck());
			
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
	
	public static void updateValueDiferentLabInMeasur(DiferentLabInMeasur diferentLabInMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "Update DiferentLabInMeasur SET labWorkplace = ?, measur = ?, workplace = ?, year = ?, check = ?  where diferentLabInMeasur_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			
			preparedStatement.setObject(1, diferentLabInMeasur.getLabWorkplace().getLab_ID());
			preparedStatement.setObject(2, diferentLabInMeasur.getMeasur().getMeasuring_ID());
			preparedStatement.setObject(3, diferentLabInMeasur.getWorkplace().getId_Workplace());
			preparedStatement.setString(4, diferentLabInMeasur.getYear());
			preparedStatement.setBoolean(5, diferentLabInMeasur.getCheck());
			
			preparedStatement.setInt(6, diferentLabInMeasur.getDiferentLabInMeasur_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}
	
	public static void deleteValueDiferentLabInMeasur(DiferentLabInMeasur diferentLabInMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "delete from DiferentLabInMeasur where diferentLabInMeasur_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, diferentLabInMeasur.getDiferentLabInMeasur_ID());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
	}
	
	public static List<DiferentLabInMeasur> getAllValueDiferentLabInMeasur() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DiferentLabInMeasur";
		List<DiferentLabInMeasur> list = new ArrayList<DiferentLabInMeasur>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				DiferentLabInMeasur object = new DiferentLabInMeasur();
				
				object.setDiferentLabInMeasur_ID(result.getInt("diferentLabInMeasur_ID"));
				object.setLabWorkplace(LaboratoryDAO.getValueLaboratoryByID(result.getInt("labWorkplace")));
				object.setMeasur(MeasuringDAO.getValueMeasuringByID(result.getInt("measur")));
				object.setWorkplace(WorkplaceDAO.getValueWorkplaceByID(result.getInt("workplace")));
				object.setYear(result.getString("year"));
				object.setCheck(result.getBoolean("check"));
				
				list.add(object);
			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}
	
	public static List<DiferentLabInMeasur> getListDiferentLabInMeasurByLabAndYear(Laboratory lab, String year) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DiferentLabInMeasur  where labWorkplace = ? and year = ? ";
		List<DiferentLabInMeasur> list = new ArrayList<DiferentLabInMeasur>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, lab.getLab_ID());
			preparedStatement.setObject(2, year);

			ResultSet result = preparedStatement.executeQuery();

			
			while (result.next()) {
				DiferentLabInMeasur object = new DiferentLabInMeasur();
				
				object.setDiferentLabInMeasur_ID(result.getInt("diferentLabInMeasur_ID"));
				object.setLabWorkplace(LaboratoryDAO.getValueLaboratoryByID(result.getInt("labWorkplace")));
				object.setMeasur(MeasuringDAO.getValueMeasuringByID(result.getInt("measur")));
				object.setWorkplace(WorkplaceDAO.getValueWorkplaceByID(result.getInt("workplace")));
				object.setYear(result.getString("year"));
				object.setCheck(result.getBoolean("check"));
				
				list.add(object);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}
	
	public static List<DiferentLabInMeasur> getListDiferentLabInMeasurByYear(String year) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DiferentLabInMeasur  where year = ? ";
		List<DiferentLabInMeasur> list = new ArrayList<DiferentLabInMeasur>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setObject(1, year);

			ResultSet result = preparedStatement.executeQuery();

			
			while (result.next()) {
				DiferentLabInMeasur object = new DiferentLabInMeasur();
				
				object.setDiferentLabInMeasur_ID(result.getInt("diferentLabInMeasur_ID"));
				object.setLabWorkplace(LaboratoryDAO.getValueLaboratoryByID(result.getInt("labWorkplace")));
				object.setMeasur(MeasuringDAO.getValueMeasuringByID(result.getInt("measur")));
				object.setWorkplace(WorkplaceDAO.getValueWorkplaceByID(result.getInt("workplace")));
				object.setYear(result.getString("year"));
				object.setCheck(result.getBoolean("check"));
				
				list.add(object);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}
	
	
	public static DiferentLabInMeasur getListDiferentLabInMeasurByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DiferentLabInMeasur  where diferentLabInMeasur_ID = ? ";
		List<DiferentLabInMeasur> list = new ArrayList<DiferentLabInMeasur>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			
			ResultSet result = preparedStatement.executeQuery();

			
			while (result.next()) {
				DiferentLabInMeasur object = new DiferentLabInMeasur();
				
				object.setDiferentLabInMeasur_ID(result.getInt("diferentLabInMeasur_ID"));
				object.setLabWorkplace(LaboratoryDAO.getValueLaboratoryByID(result.getInt("labWorkplace")));
				object.setMeasur(MeasuringDAO.getValueMeasuringByID(result.getInt("measur")));
				object.setWorkplace(WorkplaceDAO.getValueWorkplaceByID(result.getInt("workplace")));
				object.setYear(result.getString("year"));
				object.setCheck(result.getBoolean("check"));
				
				list.add(object);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}
