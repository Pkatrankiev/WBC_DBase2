package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JProgressBar;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Instance;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class InstanceDAO {

	public static void setValueInstance(String table, String source, String command, String textInstance, UsersWBC userWBC) {

		Date dateSet = Calendar.getInstance().getTime();
		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Instance (classTable, source, command, textInstance, UsersWBC_ID, DateSet) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, table);
			preparedStatement.setString(2, source);
			preparedStatement.setString(3, command);
			preparedStatement.setString(4, textInstance);
			preparedStatement.setInt(5, userWBC.getId_Users());
			preparedStatement.setDate(6, convertDate(dateSet));
			
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

	public static boolean setObjectInstanceToTable(Instance instanceObject) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Instance (classTable, source, command, textInstance, UsersWBC_ID, "
				+ "DateSet) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, instanceObject.getTable());
			preparedStatement.setString(2, instanceObject.getSource());
			preparedStatement.setString(3, instanceObject.getCommand());
			preparedStatement.setString(4, instanceObject.getTextInstance());
			preparedStatement.setInt(5, instanceObject.getUserWBC().getId_Users());
			preparedStatement.setDate(6, convertDate(instanceObject.getDateSet()));

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

	public static  java.util.Date convertDateUntil(Date data) {
		java.util.Date newDate = new java.sql.Date(data.getTime());
		return newDate;
	}

	public static java.sql.Date convertDate(Date data) {
		java.sql.Date newDate = new java.sql.Date(data.getTime());
		return newDate;
	}
	
	public static List<Instance> getValueInstanceByYear(String year) {
		
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Instance where  DateSet >= ? AND DateSet <= ?";

		
		List<Instance> listPersonStatusNew = new ArrayList<Instance>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, sdfrmt.parse("01.01."+year));
			preparedStatement.setObject(2, sdfrmt.parse("31.12."+year));
			

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {

				listPersonStatusNew.add(setInstance(result));

			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException  e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return listPersonStatusNew;
	}
	
	private static Instance setInstance(ResultSet result) throws SQLException {
	Instance instanceObject = new Instance();
	instanceObject.setInstance_ID(result.getInt("instance_ID"));
	instanceObject.setTable(result.getString("classTable"));
	instanceObject.setSource(result.getString("source"));
	instanceObject.setCommand(result.getString("command"));
	instanceObject.setTextInstance(result.getString("textInstance"));
	
	UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
	instanceObject.setUserWBC(userWBC);
	instanceObject.setDateSet(result.getTimestamp("DateSet"));
	return instanceObject;
}
	
public static List<Instance> getValueInstanceByYearWithProgressBar(String year, JProgressBar fProgressBar, double stepForProgressBar, double startProgressBar) {
		
	SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		double ProgressBarSize = startProgressBar;
		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Instance where  DateSet >= ? AND DateSet <= ?";
	
		List<Instance> listPersonStatusNew = new ArrayList<Instance>();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setObject(1, sdfrmt.parse("01.01."+year));
			preparedStatement.setObject(2, sdfrmt.parse("31.12."+year));
			
			ResultSet result = preparedStatement.executeQuery();
			int k=0;
			while (result.next()) {
			k++;	
			}
			stepForProgressBar = stepForProgressBar / k;
			result = preparedStatement.executeQuery();
			while (result.next()) {
				fProgressBar.setValue((int) ProgressBarSize);
				listPersonStatusNew.add(setInstance(result));
				ProgressBarSize += stepForProgressBar;
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listPersonStatusNew;
	}	
	
	
	
	
}
