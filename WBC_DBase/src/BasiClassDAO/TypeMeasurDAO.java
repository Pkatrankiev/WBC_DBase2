package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.conectToAccessDB;

public class TypeMeasurDAO {

	public static void setValueTypeMeasur(String kodeType,	String nameType) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO TypeMeasur (KodeType, NameType) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, kodeType);
			preparedStatement.setString(2, nameType);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectTypeMeasurToTable(TypeMeasur typeMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO TypeMeasur (KodeType, NameType) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, typeMeasur.getKodeType());
			preparedStatement.setString(2, typeMeasur.getNameType());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void updateValueTypeMeasur(TypeMeasur typeMeasur, int id_TypeMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update TypeMeasur SET KodeType = ?, NameType = ?  where TypeMeasur_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, typeMeasur.getKodeType());
			preparedStatement.setString(2, typeMeasur.getNameType());
			
			preparedStatement.setInt(3, id_TypeMeasur);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueTypeMeasur(int id_TypeMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from TypeMeasur where TypeMeasur_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_TypeMeasur);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}
	
	public static List<TypeMeasur> getAllValueTypeMeasur() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM TypeMeasur";
		List<TypeMeasur> list = new ArrayList<TypeMeasur>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				TypeMeasur object = new TypeMeasur();
				object.setId_TypeMeasur(result.getInt("TypeMeasur_ID"));
				object.setKodeType (result.getString("KodeType"));
				object.setNameType(result.getString("NameType"));
				
				list.add(object);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
		
	public static List<TypeMeasur> getValueTypeMeasurSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM TypeMeasur  ORDER BY " + sortColumnName + " ASC";
		List<TypeMeasur> list = new ArrayList<TypeMeasur>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				TypeMeasur object = new TypeMeasur();
				object.setId_TypeMeasur(result.getInt("TypeMeasur_ID"));
				object.setKodeType (result.getString("KodeType"));
				object.setNameType(result.getString("NameType"));
				
				list.add(object);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static List<TypeMeasur> getValueTypeMeasurByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM TypeMeasur  where " + columnName + " = ? ";
		
		List<TypeMeasur> list = new ArrayList<TypeMeasur>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				TypeMeasur resultObject = new TypeMeasur();
				resultObject.setId_TypeMeasur(result.getInt("TypeMeasur_ID"));
				resultObject.setKodeType (result.getString("KodeType"));
				resultObject.setNameType(result.getString("NameType"));
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static TypeMeasur getValueTypeMeasurByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM TypeMeasur  where TypeMeasur_ID = ? LIMIT 1";
		
		List<TypeMeasur> list = new ArrayList<TypeMeasur>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				TypeMeasur resultObject = new TypeMeasur();
				resultObject.setId_TypeMeasur(result.getInt("TypeMeasur_ID"));
				resultObject.setKodeType (result.getString("KodeType"));
				resultObject.setNameType(result.getString("NameType"));
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list.get(0);
	}
	
	
	public static List<TypeMeasur> getValueTypeMeasurByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM TypeMeasur  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";
		
		List<TypeMeasur> list = new ArrayList<TypeMeasur>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				TypeMeasur resultObject = new TypeMeasur();
				resultObject.setId_TypeMeasur(result.getInt("TypeMeasur_ID"));
				resultObject.setKodeType (result.getString("KodeType"));
				resultObject.setNameType(result.getString("NameType"));
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}

	public static String[] getMasiveTypeMeasur() {
		List<TypeMeasur> list = getAllValueTypeMeasur() ;
		String[] masive = new String[list.size()];
		int i=0;
		for (TypeMeasur object :list) {
			masive[i] = object.getKodeType();
			i++;
		}
		return masive;
	}
	
	public static String[] getMasiveNameTypeMeasur() {
		List<TypeMeasur> list = getAllValueTypeMeasur() ;
		String[] masive = new String[list.size()];
		int i=0;
		for (TypeMeasur object :list) {
			masive[i] = object.getNameType();
			i++;
		}
		return masive;
	}
	
	
}
