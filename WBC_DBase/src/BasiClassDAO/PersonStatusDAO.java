package BasiClassDAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;

public class PersonStatusDAO {
	
	
	public static void setValuePersonStatus(Person person,
			Workplace workplace,
			 Spisak_Prilogenia spisak_prilogenia,
			 UsersWBC userWBC,
			 Date dateSet,
			 String zabelejka) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO PersonStatus (Person_ID, Workplace_ID, Spisak_Prilogenia_ID, UsersWBC_ID, DateSet, Zabelejka) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, person.getId_Person());
			preparedStatement.setInt(2, workplace.getId_Workplace());
			preparedStatement.setInt(3, spisak_prilogenia.getSpisak_Prilogenia_ID());
			preparedStatement.setInt(4, userWBC.getId_Users());
			preparedStatement.setDate(5, convertDate(dateSet));
			preparedStatement.setString(6, zabelejka);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			if (e.toString().contains("unique")) {
				String str = "Съдържа повтарящи се полета";
//				MessageDialog(str);
			}else {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
			}
		}
	}

	public static void setObjectPersonStatusToTable(PersonStatus personStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO PersonStatus (Person_ID, Workplace_ID, Spisak_Prilogenia_ID, UsersWBC_ID, DateSet, Zabelejka) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, personStatus.getPerson().getId_Person());
			preparedStatement.setInt(2, personStatus.getWorkplace().getId_Workplace());
			preparedStatement.setInt(3, personStatus.getSpisak_prilogenia().getSpisak_Prilogenia_ID());
			preparedStatement.setInt(4, personStatus.getUserWBC().getId_Users());
			preparedStatement.setDate(5, convertDate(personStatus.getDateSet()));
			preparedStatement.setString(6, personStatus.getZabelejka());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (e.toString().indexOf("unique")>0) {
				System.out.println(personStatus.getPerson().getEgn() + " " + personStatus.getWorkplace().getOtdel() + " "
						+ personStatus.getSpisak_prilogenia().getFormulyarName() + " "
						+ personStatus.getUserWBC().getLastName() + " " + personStatus.getZabelejka().toString() + " "
						+ personStatus.getDateSet().toString());
//				MessageDialog(str);
			}else {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
			}
		}
	}

	public static  java.sql.Date convertDate(Date data){
	java.sql.Date newDate = new java.sql.Date(data.getTime());
	return newDate;
	}
	
	
	public static void updateValuePersonStatus(PersonStatus personStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update PersonStatus SET Workplace_ID = ? , Spisak_Prilogenia_ID = ? , UsersWBC_ID = ? , DateSet = ? "
				+ ", Zabelejka = ?   where PersonStatus_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			
			preparedStatement.setInt(1, personStatus.getWorkplace().getId_Workplace());
			preparedStatement.setInt(2, personStatus.getSpisak_prilogenia().getSpisak_Prilogenia_ID());
			preparedStatement.setInt(3, personStatus.getUserWBC().getId_Users());
			preparedStatement.setDate(4, convertDate(personStatus.getDateSet()));
			preparedStatement.setString(5, personStatus.getZabelejka());

			preparedStatement.setInt(6, personStatus.getPersonStatus_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (e.toString().indexOf("unique")>0) {
				deleteValuePersonStatus(personStatus);
			}else {
			
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
			}
		}
	}

	public static void deleteValuePersonStatus(PersonStatus personStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from PersonStatus where PersonStatus_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, personStatus.getPersonStatus_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<PersonStatus> getAllValuePersonStatus() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus";
		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();
				
				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				
				listPersonStatus.add(PersonStatus);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listPersonStatus;
	}

	public static List<PersonStatus> getAllValuePersonStatusSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus ORDER BY " + sortColumnName + " ASC";
		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<PersonStatus> getValuePersonStatusByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where " + columnName + " = ? ";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
			break;
			case "Workplace_ID": {
				preparedStatement.setObject(1, ((Workplace) object).getId_Workplace());
			}
			case "Spisak_Prilogenia_ID": {
				preparedStatement.setObject(1, ((Spisak_Prilogenia) object).getSpisak_Prilogenia_ID());
			}
			break;
			case "UsersWBC_ID": {
				preparedStatement.setObject(1, ((UsersWBC) object).getId_Users());
			}
			break;
			default:
				preparedStatement.setObject(1, object);
			}
			
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}

	public static List<PersonStatus> getValuePersonStatusByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where " + columnName + " = ? ORDER BY " + sortColumnName + ", PersonStatus_ID";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
			break;
			case "Workplace_ID": {
				preparedStatement.setObject(1, ((Workplace) object).getId_Workplace());
			}
			case "Spisak_Prilogenia_ID": {
				preparedStatement.setObject(1, ((Spisak_Prilogenia) object).getSpisak_Prilogenia_ID());
			}
			break;
			case "UsersWBC_ID": {
				preparedStatement.setObject(1, ((UsersWBC) object).getId_Users());
			}
			break;
			default:
				preparedStatement.setObject(1, object);
			}
			
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}

	
	public static PersonStatus getValuePersonStatusByPersonStatus(PersonStatus personStatus) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ? AND Workplace_ID = ? and Spisak_Prilogenia_ID = ? LIMIT 1";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, personStatus.getPerson().getId_Person());
			preparedStatement.setObject(2, personStatus.getWorkplace().getId_Workplace());
			preparedStatement.setObject(3, personStatus.getSpisak_prilogenia().getSpisak_Prilogenia_ID());
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus.get(0);
	}

	public static List<PersonStatus>  getValuePersonStatusByPersonAndWorkplace(Person person, Workplace workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ?  AND  Workplace_ID = ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, workplace.getId_Workplace());
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				PersonStatus.setPerson(person);
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	public static List<PersonStatus>  getValuePersonStatusByWorkplace( Workplace workplace) {
	
		
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where  Workplace_ID = ? ";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setObject(1, workplace.getId_Workplace());

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	public static List<PersonStatus>  getValuePersonStatusByWorkplace_DateStart_DateEnd( Workplace workplace, Date dateStart, Date dateEnd) {
	
	
		
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where  Workplace_ID = ? AND DateSet >= ? AND DateSet <= ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setObject(1, workplace.getId_Workplace());
			preparedStatement.setObject(2, dateStart);
			preparedStatement.setObject(3, dateEnd);

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	





	public static List<PersonStatus>  getValuePersonStatusByWorkplace_Year( Workplace workplace, String year) {
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	Date dateStart = null;
	Date dateEnd = null;
	try {
		dateStart = sdf.parse("01.01."+year);
		dateEnd = sdf.parse("31.12."+year);
	} catch (ParseException e1) {
		e1.printStackTrace();
	}
	
	
	Connection connection = conectToAccessDB.conectionBDtoAccess();
	String sql = "SELECT * FROM PersonStatus where  Workplace_ID = ? AND DateSet >= ? AND DateSet <= ?";

	List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

	try {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setObject(1, workplace.getId_Workplace());
		preparedStatement.setObject(2, dateStart);
		preparedStatement.setObject(3, dateEnd);

		ResultSet result = preparedStatement.executeQuery();

		while (result.next()) {
			PersonStatus PersonStatus = new PersonStatus();

			PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
			Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
			PersonStatus.setPerson(person);
			PersonStatus.setWorkplace(workplace);
			Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
			PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
			UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
			PersonStatus.setUserWBC(userWBC);
			PersonStatus.setDateSet(result.getDate("DateSet"));
			PersonStatus.setZabelejka(result.getString("Zabelejka"));
			listPersonStatus.add(PersonStatus);
		}
		
		preparedStatement.close();
		connection.close();
		
	} catch (SQLException e) {
		ResourceLoader.appendToFile( e);
		e.printStackTrace();
	}
	return listPersonStatus;
}
	
	
	public static List<PersonStatus>  getValuePersonStatusByPersonAndDateSet(Person person, Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ?  AND  DateSet = ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	public static List<PersonStatus>  getValuePersonStatusByPersonAndDFateAfterDateSet(Person person, Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ?  AND  DateSet > ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	
	public static PersonStatus getValuePersonStatusByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where PersonStatus_ID = ? LIMIT 1";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus.get(0);
	}

	public static List<PersonStatus>  getValuePersonStatusByPersonAndDateSetSortByID(Person person, Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ?  AND  DateSet = ? ORDER BY PersonStatus_ID ASC";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}
	
	
	public static PersonStatus  getLastValuePersonStatusByPerson(Person person) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ?  ORDER BY Spisak_Prilogenia_ID DESC";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		if(listPersonStatus.size()==0)
			return null;
		
		return listPersonStatus.get(0);
	}	
	
	
	public static boolean  PersonWithObhodenList(Person person) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Person_ID = ?  AND  PersonStatus_ID > 225103";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
		
		
		return listPersonStatus.size()>0;
	}
	
	
	
	

}
