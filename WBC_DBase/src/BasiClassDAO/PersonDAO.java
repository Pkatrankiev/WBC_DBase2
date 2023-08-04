package BasiClassDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.conectToAccessDB;

import java.sql.Connection;

public class PersonDAO {

	public static void setValuePerson(String egn, String firstName, String secondName, String lastName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Person (EGN, FirstName, SecondName, LastName) VALUES (?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, egn);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, secondName);
			preparedStatement.setString(4, lastName);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (e.toString().contains("unique")) {
				String str = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorDialogTextDublicateFields");
				AplicationMetods.MessageDialog(str);
			}
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void setObjectPersonToTable(Person person) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Person (EGN, FirstName, SecondName, LastName) VALUES (?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, person.getEgn());
			preparedStatement.setString(2, person.getFirstName());
			preparedStatement.setString(3, person.getSecondName());
			preparedStatement.setString(4, person.getLastName());

			preparedStatement.executeUpdate();
			
//			preparedStatement.close();
//			connection.close();

		} catch (SQLException e) {
//			if (e.toString().contains("unique")) {
//				String str = "Съдържа повтарящи се полета";
//				MessageDialog(str);
//			}
//			e.printStackTrace();
//			ResourceLoader.appendToFile( e);
		}
	}

	public static void updateValuePerson(Person person) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Person SET EGN = ?, FirstName = ?, SecondName = ?, LastName = ?  where Person_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, person.getEgn());
			preparedStatement.setString(2, person.getFirstName());
			preparedStatement.setString(3, person.getSecondName());
			preparedStatement.setString(4, person.getLastName());

			preparedStatement.setInt(5, person.getId_Person());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValuePerson(Person person) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Person where Person_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, person.getId_Person());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<Person> getAllValuePerson() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Person";
		List<Person> listPerson = new ArrayList<Person>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Person person = new Person();
				person.setId_Person(result.getInt("Person_ID"));
				person.setEgn(result.getString("EGN"));
				person.setFirstName(result.getString("FirstName"));
				person.setSecondName(result.getString("SecondName"));
				person.setLastName(result.getString("LastName"));
				listPerson.add(person);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listPerson;
	}

	public static List<Person> getAllValuePersonSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Person ORDER BY " + sortColumnName + " ASC";
		List<Person> listPerson = new ArrayList<Person>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Person person = new Person();
				person.setId_Person(result.getInt("Person_ID"));
				person.setEgn(result.getString("EGN"));
				person.setFirstName(result.getString("FirstName"));
				person.setSecondName(result.getString("SecondName"));
				person.setLastName(result.getString("LastName"));
				listPerson.add(person);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPerson;
	}

	
	public static List<Person> getValuePersonByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Person where " + columnName + " = ? ";

		List<Person> listPerson = new ArrayList<Person>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Person person = new Person();
				person.setId_Person(result.getInt("Person_ID"));
				person.setEgn(result.getString("EGN"));
				person.setFirstName(result.getString("FirstName"));
				person.setSecondName(result.getString("SecondName"));
				person.setLastName(result.getString("LastName"));
				listPerson.add(person);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPerson;
	}

	public static Person getValuePersonByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Person where Person_ID = ? LIMIT 1";

		List<Person> listPerson = new ArrayList<Person>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Person person = new Person();
				person.setId_Person(result.getInt("Person_ID"));
				person.setEgn(result.getString("EGN"));
				person.setFirstName(result.getString("FirstName"));
				person.setSecondName(result.getString("SecondName"));
				person.setLastName(result.getString("LastName"));
				listPerson.add(person);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPerson.get(0);
	}
	
	public static Person getValuePersonByEGN(String EGN) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Person where EGN = ? LIMIT 1";

		List<Person> listPerson = new ArrayList<Person>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, EGN);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Person person = new Person();
				person.setId_Person(result.getInt("Person_ID"));
				person.setEgn(result.getString("EGN"));
				person.setFirstName(result.getString("FirstName"));
				person.setSecondName(result.getString("SecondName"));
				person.setLastName(result.getString("LastName"));
				listPerson.add(person);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		if(listPerson.size()>0) {
		return  listPerson.get(0); 
		}else return null;
	}
	
	public static List<Person> getValuePersonByAllName(String FName, String SName, String LName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Person where FirstName = ? and  SecondName = ? and LastName = ?";

		List<Person> listPerson = new ArrayList<Person>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, FName);
			preparedStatement.setObject(2, SName);
			preparedStatement.setObject(3, LName);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Person person = new Person();
				person.setId_Person(result.getInt("Person_ID"));
				person.setEgn(result.getString("EGN"));
				person.setFirstName(result.getString("FirstName"));
				person.setSecondName(result.getString("SecondName"));
				person.setLastName(result.getString("LastName"));
				listPerson.add(person);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listPerson;
	}

}
