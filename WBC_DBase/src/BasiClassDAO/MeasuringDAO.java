package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class MeasuringDAO {

	public static void setValueMeasuring(Person person, Date date, double doze, DimensionWBC dozeDimension,
			Laboratory lab, UsersWBC user, TypeMeasur typeMeasur, String measurKoment, String reportFileName) {
		String mesage = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dublicateRepFileInDBase");
		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Measuring (Person_ID, Date, Doze, DozeDimension_ID, Lab_ID, UsersWBC_ID, TypeMeasur_ID, MeasurKoment, ReportFileName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, date);
			preparedStatement.setObject(3, doze);
			preparedStatement.setObject(4, dozeDimension.getDimensionWBC_ID());
			preparedStatement.setObject(5, lab.getLab_ID());
			preparedStatement.setObject(6, user.getId_Users());
			preparedStatement.setObject(7, typeMeasur.getId_TypeMeasur());
			preparedStatement.setObject(8, measurKoment);
			preparedStatement.setObject(9, reportFileName);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
//			if (e.toString().indexOf("unique constraint or index violation") > 0) {
//				MessageDialog(mesage + " " + reportFileName);
//				e.printStackTrace();
//				ResourceLoader.appendToFile(e);
//			}
		}
	}

	public static void setObjectMeasuringToTable(Measuring measuring) {
//		String mesage = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dublicateRepFileInDBase");

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Measuring (Person_ID, Date, Doze, DozeDimension_ID, Lab_ID, UsersWBC_ID, TypeMeasur_ID, MeasurKoment, ReportFileName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, measuring.getPerson().getId_Person());
			preparedStatement.setObject(2, measuring.getDate());
			preparedStatement.setObject(3, measuring.getDoze());
			preparedStatement.setObject(4, measuring.getDoseDimension().getDimensionWBC_ID());
			preparedStatement.setObject(5, measuring.getLab().getLab_ID());
			preparedStatement.setObject(6, measuring.getUser().getId_Users());
			preparedStatement.setObject(7, measuring.getTypeMeasur().getId_TypeMeasur());
			String koment = "";
			if(measuring.getMeasurKoment()!= null) {
				koment = measuring.getMeasurKoment();
			}
			preparedStatement.setObject(8, koment);
			preparedStatement.setObject(9, measuring.getReportFileName());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
//			e.printStackTrace();
//			if (e.toString().indexOf("unique constraint or index violation") > 0) {
//				System.out.println(measuring.getPerson().getEgn()+" "+measuring.getLab().getLab_ID()+" "+measuring.getDoze()+" "+measuring.getDate());
				
//				MessageDialog(mesage + " " + measuring.getReportFileName());
				
//				ResourceLoader.appendToFile(e);
//			}
		}
	}

	public static void updateValueMeasuring(Measuring measuring) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Measuring SET Person_ID = ?, Date = ?, Doze = ?, DozeDimension_ID = ?, Lab_ID = ?, UsersWBC_ID = ?,"
				+ " TypeMeasur_ID = ?, MeasurKoment = ?, ReportFileName = ?  where Measuring_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setObject(1, measuring.getPerson().getId_Person());
			preparedStatement.setObject(2, measuring.getDate());
			preparedStatement.setObject(3, measuring.getDoze());
			preparedStatement.setObject(4, measuring.getDoseDimension().getDimensionWBC_ID());
			preparedStatement.setObject(5, measuring.getLab().getLab_ID());
			preparedStatement.setObject(6, measuring.getUser().getId_Users());
			preparedStatement.setObject(7, measuring.getTypeMeasur().getId_TypeMeasur());
			preparedStatement.setObject(8, measuring.getMeasurKoment());
			preparedStatement.setObject(9, measuring.getReportFileName());

			preparedStatement.setInt(10, measuring.getMeasuring_ID());
			
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}

	}

	public static void deleteValueMeasuring(Measuring measuring) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Measuring where Measuring_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, measuring.getMeasuring_ID());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
	}
	
	public static List<Measuring> getAllValueMeasuring() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring object = new Measuring();
				object.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				object.setPerson(person);
				object.setDate(result.getDate("Date"));
				object.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				object.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				object.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				object.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				object.setTypeMeasur(type);
				object.setMeasurKoment(result.getString("MeasurKoment"));
				object.setReportFileName(result.getString("ReportFileName"));

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

	public static Measuring getLastMeasuring() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring ORDER BY Measuring_ID DESC LIMIT 1";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring object = new Measuring();
				object.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				object.setPerson(person);
				object.setDate(result.getDate("Date"));
				object.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				object.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				object.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				object.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				object.setTypeMeasur(type);
				object.setMeasurKoment(result.getString("MeasurKoment"));
				object.setReportFileName(result.getString("ReportFileName"));

				list.add(object);
			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list.get(0);
	}

	public static List<Measuring> getValueMeasuringSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  ORDER BY " + sortColumnName + " ASC";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring object = new Measuring();
				object.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				object.setPerson(person);
				object.setDate(result.getDate("Date"));
				object.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				object.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				object.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				object.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				object.setTypeMeasur(type);
				object.setMeasurKoment(result.getString("MeasurKoment"));
				object.setReportFileName(result.getString("ReportFileName"));

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

	public static List<Measuring> getValueMeasuringByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where " + columnName + " = ? ";

		List<Measuring> list = new ArrayList<Measuring>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
				break;
			case "DozeDimension_ID": {
				preparedStatement.setObject(1, ((DimensionWBC) object).getDimensionWBC_ID());
			}
				break;
			case "Lab_ID": {
				preparedStatement.setObject(1, ((Laboratory) object).getLab_ID());
			}
				break;
			case "UsersWBC_ID": {
				preparedStatement.setObject(1, ((UsersWBC) object).getId_Users());
			}
				break;
			case "TypeMeasur_ID": {
				preparedStatement.setObject(1, ((TypeMeasur) object).getId_TypeMeasur());
			}
				break;
			default:
				preparedStatement.setObject(1, object);
			}

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				resultObject.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				resultObject.setMeasurKoment(result.getString("MeasurKoment"));
				resultObject.setReportFileName(result.getString("ReportFileName"));

				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static List<Measuring> getValueMeasuringByObjectSortByColumnName(String columnName, Object object,
			String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";

		List<Measuring> list = new ArrayList<Measuring>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
				break;
			case "DozeDimension_ID": {
				preparedStatement.setObject(1, ((DimensionWBC) object).getDimensionWBC_ID());
			}
				break;
			case "Lab_ID": {
				preparedStatement.setObject(1, ((Laboratory) object).getLab_ID());
			}
				break;
			case "UsersWBC_ID": {
				preparedStatement.setObject(1, ((UsersWBC) object).getId_Users());
			}
				break;
			case "TypeMeasur_ID": {
				preparedStatement.setObject(1, ((TypeMeasur) object).getId_TypeMeasur());
			}
				break;
			default:
				preparedStatement.setObject(1, object);
			}

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				resultObject.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				resultObject.setMeasurKoment(result.getString("MeasurKoment"));
				resultObject.setReportFileName(result.getString("ReportFileName"));

				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static Measuring getValueMeasuringByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where Measuring_ID = ? ";

		List<Measuring> list = new ArrayList<Measuring>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				resultObject.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				resultObject.setMeasurKoment(result.getString("MeasurKoment"));
				resultObject.setReportFileName(result.getString("ReportFileName"));

				list.add(resultObject);
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

	public static Measuring getValueMeasuringByPersonDozeDate(Person person, Date date, Double doze, Laboratory lab) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where Person_ID = ? and Date = ? and Doze = ? and Lab_ID = ?";

		List<Measuring> list = new ArrayList<Measuring>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, date);
			preparedStatement.setObject(3, doze);
			preparedStatement.setObject(4, lab.getLab_ID());

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				resultObject.setDoseDimension(dim);
				lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				resultObject.setMeasurKoment(result.getString("MeasurKoment"));
				resultObject.setReportFileName(result.getString("ReportFileName"));

				list.add(resultObject);
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

	public static void MessageDialog(String mesage) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, mesage, "Info", JOptionPane.PLAIN_MESSAGE, otherIcon);

	}


	public static List<Measuring> getAllValueMeasuring1() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring1";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
int k=0;
			while (result.next()) {
				
				Measuring object = new Measuring();
				object.setMeasuring_ID(result.getInt("Measuring_ID"));
				System.out.println(k+" - "+result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				object.setPerson(person);
				object.setDate(result.getDate("Date"));
				object.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				object.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				object.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				object.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				object.setTypeMeasur(type);
				object.setMeasurKoment(result.getString("MeasurKoment"));
				object.setReportFileName(result.getString("ReportFileName"));

				list.add(object);
				k++;
			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	public static List<Measuring> getValueMeasuringByPersonAndYear(Person person, Date dateStart, Date dateEnd) {
	
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where Person_ID = ? AND Date >= ? AND Date <= ?";

		List<Measuring> list = new ArrayList<Measuring>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateStart);
			preparedStatement.setObject(3, dateEnd);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(result.getInt("DozeDimension_ID"));
				resultObject.setDoseDimension(dim);
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				resultObject.setMeasurKoment(result.getString("MeasurKoment"));
				resultObject.setReportFileName(result.getString("ReportFileName"));

				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		
		return list;
	}

	
	
}
