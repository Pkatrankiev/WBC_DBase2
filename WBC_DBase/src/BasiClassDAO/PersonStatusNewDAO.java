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
import javax.swing.JProgressBar;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;

public class PersonStatusNewDAO {

	public static void setValuePersonStatusNew(Person person, Workplace workplace, String formulyarName, Date startDate,
			Date endDate, String year, UsersWBC userWBC, Date dateSet, String zabelejka) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO PersonStatusNew (Person_ID, Workplace_ID, FormulyarName, StartDate, EndDate, Year, UsersWBC_ID, "
				+ "DateSet, Zabelejka) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, person.getId_Person());
			preparedStatement.setInt(2, workplace.getId_Workplace());
			preparedStatement.setString(3, formulyarName);
			preparedStatement.setDate(4, convertDate(startDate));
			preparedStatement.setDate(5, convertDate(endDate));
			preparedStatement.setString(6, year);
			preparedStatement.setInt(7, userWBC.getId_Users());
			preparedStatement.setDate(8, convertDate(dateSet));
			preparedStatement.setString(9, zabelejka);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (e.toString().contains("unique")) {
//				String str = "Съдържа повтарящи се полета";
//				MessageDialog(str);
			} else {
				e.printStackTrace();
				ResourceLoader.appendToFile(e);
			}
		}
	}

	public static boolean setObjectPersonStatusNewToTable(PersonStatusNew PersonStatusNew) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO PersonStatusNew (Person_ID, Workplace_ID, FormulyarName, StartDate, EndDate, Year, UsersWBC_ID,"
				+ "DateSet, Zabelejka) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, PersonStatusNew.getPerson().getId_Person());
			preparedStatement.setInt(2, PersonStatusNew.getWorkplace().getId_Workplace());
			preparedStatement.setString(3, PersonStatusNew.getFormulyarName());
			preparedStatement.setDate(4, convertDate(PersonStatusNew.getStartDate()));
			preparedStatement.setDate(5, convertDate(PersonStatusNew.getEndDate()));
			preparedStatement.setString(6, PersonStatusNew.getYear());
			preparedStatement.setInt(7, PersonStatusNew.getUserWBC().getId_Users());
			preparedStatement.setDate(8, convertDate(PersonStatusNew.getDateSet()));
			preparedStatement.setString(9, PersonStatusNew.getZabelejka());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
			

		} catch (SQLException e) {
			if (e.toString().indexOf("unique") > 0) {
				return false;
			}else {
				e.printStackTrace();
				ResourceLoader.appendToFile(e);
			}
		}
		return true;
	}

	public static java.sql.Date convertDate(Date data) {
		java.sql.Date newDate = new java.sql.Date(data.getTime());
		return newDate;
	}

	public static boolean updateValuePersonStatusNew(PersonStatusNew PersonStatusNew) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update PersonStatusNew SET Workplace_ID = ? , FormulyarName = ? , StartDate = ? , EndDate = ? , Year = ? , UsersWBC_ID = ? , DateSet = ? "
				+ ", Zabelejka = ?   where PersonStatusNew_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, PersonStatusNew.getWorkplace().getId_Workplace());
			preparedStatement.setString(2, PersonStatusNew.getFormulyarName());
			preparedStatement.setDate(3, convertDate(PersonStatusNew.getStartDate()));
			preparedStatement.setDate(4, convertDate(PersonStatusNew.getEndDate()));
			preparedStatement.setString(5, PersonStatusNew.getYear());
			preparedStatement.setInt(6, PersonStatusNew.getUserWBC().getId_Users());
			preparedStatement.setDate(7, convertDate(PersonStatusNew.getDateSet()));
			preparedStatement.setString(8, PersonStatusNew.getZabelejka());

			preparedStatement.setInt(9, PersonStatusNew.getPersonStatusNew_ID());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (e.toString().indexOf("unique") > 0) {
				return false;
			}else {
				e.printStackTrace();
				ResourceLoader.appendToFile(e);
			}
		}
		return true;
	}

	public static boolean deleteValuePersonStatusNew(PersonStatusNew PersonStatusNew) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from PersonStatusNew where PersonStatusNew_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, PersonStatusNew.getPersonStatusNew_ID());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static PersonStatusNew setPersonStatusNew(ResultSet result) throws SQLException {
		PersonStatusNew PersonStatusNew = new PersonStatusNew();

		PersonStatusNew.setPersonStatusNew_ID(result.getInt("PersonStatusNew_ID"));
		Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
		PersonStatusNew.setPerson(person);
		Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
		PersonStatusNew.setWorkplace(workplace);
		PersonStatusNew.setFormulyarName(result.getString("FormulyarName"));
		PersonStatusNew.setStartDate(result.getDate("StartDate"));
		PersonStatusNew.setEndDate(result.getDate("EndDate"));
		PersonStatusNew.setYear(result.getString("Year"));
		UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
		PersonStatusNew.setUserWBC(userWBC);
		PersonStatusNew.setDateSet(result.getDate("DateSet"));
		PersonStatusNew.setZabelejka(result.getString("Zabelejka"));
		return PersonStatusNew;
	}

	public static List<PersonStatusNew> getAllValuePersonStatusNew() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew";
		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

				statement.close();
				connection.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getAllValuePersonStatusNewSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew ORDER BY " + sortColumnName + " ASC";
		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where " + columnName + " = ? ";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

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

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByObjectSortByColumnName(String columnName,
			Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where " + columnName + " = ? ORDER BY " + sortColumnName
				+ ", PersonStatusNew_ID";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

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

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static PersonStatusNew getValuePersonStatusNewByPersonStatusNew(PersonStatusNew PersonStatusNew) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ? AND Workplace_ID = ? and FormulyarName = ? "
				+ "and StartDate = ? and EndDate = ? and Year = ? LIMIT 1";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, PersonStatusNew.getPerson().getId_Person());
			preparedStatement.setObject(2, PersonStatusNew.getWorkplace().getId_Workplace());
			preparedStatement.setObject(3, PersonStatusNew.getFormulyarName());
			preparedStatement.setObject(4, PersonStatusNew.getStartDate());
			preparedStatement.setObject(5, PersonStatusNew.getEndDate());
			preparedStatement.setObject(6, PersonStatusNew.getYear());
			
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew.get(0);
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByPersonAndWorkplace(Person person,
			Workplace workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  AND  Workplace_ID = ?  ORDER BY StartDate DESC";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, workplace.getId_Workplace());
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByPerson_Workplace_DateSetInYear(Person person,
			Workplace workplace, String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  AND  Workplace_ID = ? and Year = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();
		System.out.println(person.getId_Person()+" "+ workplace.getId_Workplace()+" "+ year);
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, workplace.getId_Workplace());
			preparedStatement.setObject(3, year);
			
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByPerson_FormuliarName_DateSetInYear(Person person,
			String formulyarName, String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  AND  FormulyarName = ? and Year = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, formulyarName);
			preparedStatement.setObject(3, year);
			
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}
	
	public static List<PersonStatusNew> getValuePersonStatusNewByWorkplace(Workplace workplace) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Workplace_ID = ? ";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, workplace.getId_Workplace());

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByWorkplace_DateStart_DateEnd(Workplace workplace,
			Date dateStart, Date dateEnd) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Workplace_ID = ? AND StartDate >= ? AND EndDate <= ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, workplace.getId_Workplace());
			preparedStatement.setObject(2, dateStart);
			preparedStatement.setObject(3, dateEnd);

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static PersonStatusNew getPersonStatusNewByPerson_Workplace_DateStart_DateEnd_Year(Person person, Workplace workplace,
			Date dateStart, Date dateEnd, String year) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Person_ID = ? AND Workplace_ID = ? AND StartDate = ? AND EndDate = ? and Year = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, workplace.getId_Workplace());
			preparedStatement.setObject(3, dateStart);
			preparedStatement.setObject(4, dateEnd);
			preparedStatement.setObject(5, year);

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew.size()>0 ? listPersonStatusNew.get(0) : null;
	}

	public static PersonStatusNew getPersonStatusNewByWorkplace_FormulyarName_DateStart_DateEnd_Year(String formulyarName, Workplace workplace,
			Date dateStart, Date dateEnd, String year) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  FormulyarName = ? AND Workplace_ID = ? AND StartDate = ? AND EndDate = ? and Year = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, formulyarName);
			preparedStatement.setObject(2, workplace.getId_Workplace());
			preparedStatement.setObject(3, dateStart);
			preparedStatement.setObject(4, dateEnd);
			preparedStatement.setObject(5, year);

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}  
		return listPersonStatusNew.size()>0 ? listPersonStatusNew.get(0) : null;
	}

	

	public static List<PersonStatusNew> getValuePersonStatusNewByWorkplace_Year(Workplace workplace, String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Workplace_ID = ? and Year = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, workplace.getId_Workplace());
			preparedStatement.setObject(2, year);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByPerson_Year(Person person, String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Person_ID = ? and Year = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, year);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}
	
	public static PersonStatusNew getLastPersonStatusNewByPerson_Year2(Person person, String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Person_ID = ? and Year = ? ORDER BY personStatusNew_ID DESC";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, year);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew.get(0);
	}
	
	
	public static List<PersonStatusNew> getValuePersonStatusNewByPersonAndDateSet(Person person, Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  AND  DateSet = ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByPersonAndDateAfterDateSet(Person person,
			Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  AND  DateSet > ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static PersonStatusNew getValuePersonStatusNewByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where PersonStatusNew_ID = ? LIMIT 1";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew.get(0);
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByPersonAndDateSetSortByID(Person person, Date dateSet) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  AND  DateSet = ? ORDER BY PersonStatusNew_ID ASC";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static PersonStatusNew getLastValuePersonStatusNewByPerson(Person person) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ?  ORDER BY StartDate DESC";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}

		if (listPersonStatusNew.size() == 0)
			return null;

		return listPersonStatusNew.get(0);
	}



	public static List<PersonStatusNew> getValuePersonStatusNewByPerson(Person person) {
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where Person_ID = ? ";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByYear(String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Year = ? ";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, year);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}
	
	
public static List<PersonStatusNew> getValuePersonStatusNewByYear(String year, JProgressBar progressBar) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Year = ? ";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, year);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static List<PersonStatusNew> getValuePersonStatusNewByYearWithProgressBar(String year, Person person, JProgressBar fProgressBar, double stepForProgressBar) {
		

		double ProgressBarSize = 0;
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Year = "+ year;
		if(year.isEmpty()) {
			sql = "SELECT * FROM PersonStatusNew";
			}
		
		if(person != null && !year.isEmpty()) {
				sql = "SELECT * FROM PersonStatusNew where  Person_ID = "+person.getId_Person()+"  AND Year = "+ year;
				}
			if(person != null && year.isEmpty()) {
				sql = "SELECT * FROM PersonStatusNew where  Person_ID = "+person.getId_Person();
				}
			
		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();
		
		

		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setObject(1, year);
//			
			ResultSet result = preparedStatement.executeQuery();
			int k=0;
			while (result.next()) {
			k++;	
			}
			stepForProgressBar = stepForProgressBar / k;
			result = preparedStatement.executeQuery();
			while (result.next()) {
				fProgressBar.setValue((int) ProgressBarSize);
				listPersonStatusNew.add(setPersonStatusNew(result));
				ProgressBarSize += stepForProgressBar;
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}
	
	public static List<PersonStatusNew> getValuePersonStatusNewByTausenObjectStartedBy(int startID, int endID) {
		
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where PersonStatusNew_ID >= ? and  PersonStatusNew_ID <= ?";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, startID);
			preparedStatement.setObject(2, endID);

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}

	public static PersonStatusNew getLastPersonStatusNewByPerson_YearSortByStartDate(Person person, String year) {
		

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where  Person_ID = ? and Year = ? ORDER BY StartDate DESC";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, year);
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setPersonStatusNew(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		if (listPersonStatusNew.size() == 0)
			return null;
		
		if (listPersonStatusNew.size() > 1) {
			
			if(listPersonStatusNew.get(0).getStartDate().equals(listPersonStatusNew.get(1).getStartDate())) 
				if(	listPersonStatusNew.get(0).getPersonStatusNew_ID() > listPersonStatusNew.get(1).getPersonStatusNew_ID()) {
				return listPersonStatusNew.get(0);	
			}else {
				return listPersonStatusNew.get(1);	
			}
		}
		
		return listPersonStatusNew.get(0);
	}
	
	public static PersonStatusNew getValuePersonStatusNewByID_New(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatusNew where PersonStatusNew_ID = ? LIMIT 1";

		List<PersonStatusNew> listPersonStatusNew = new ArrayList<PersonStatusNew>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				PersonStatusNew PersonStatusNew = new PersonStatusNew();

				PersonStatusNew.setPersonStatusNew_ID(result.getInt("PersonStatusNew_ID"));
				
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatusNew.setPerson(person);
				
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatusNew.setWorkplace(workplace);
				
				PersonStatusNew.setFormulyarName(result.getString("FormulyarName"));
				PersonStatusNew.setStartDate(result.getDate("StartDate"));
				PersonStatusNew.setEndDate(result.getDate("EndDate"));
				PersonStatusNew.setYear(result.getString("Year"));
				
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatusNew.setUserWBC(userWBC);
				
				PersonStatusNew.setDateSet(result.getDate("DateSet"));
				PersonStatusNew.setZabelejka(result.getString("Zabelejka"));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatusNew.get(0);
	}

	
}
