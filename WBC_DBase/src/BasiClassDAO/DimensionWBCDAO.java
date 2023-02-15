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
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class DimensionWBCDAO {

	public static void setValueDimensionWBC(String dimensionName, double dimensionScale) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO DimensionWBC (DimensionName, DimensionScale) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dimensionName);
			preparedStatement.setDouble(2, dimensionScale);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			if (e.toString().contains("unique")) {
				String str = "Съдържа повтарящи се полета";
				MessageDialog(str);
			}
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void setObjectDimensionWBCToTable(DimensionWBC dimensionWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO DimensionWBC (DimensionName, DimensionScale) VALUES (?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dimensionWBC.getDimensionName());
			preparedStatement.setDouble(2, dimensionWBC.getDimensionScale());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void updateValueDimensionWBC(DimensionWBC dimensionWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update DimensionWBC SET DimensionName = ? , DimensionScale = ?  where DimensionWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, dimensionWBC.getDimensionName());
			preparedStatement.setDouble(2, dimensionWBC.getDimensionScale());
			
			preparedStatement.setInt(3,  dimensionWBC.getDimensionWBC_ID());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValueDimensionWBC(int Id_DimensionWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from DimensionWBC where DimensionWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, Id_DimensionWBC);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<DimensionWBC> getAllValueDimensionWBC() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DimensionWBC";
		List<DimensionWBC> listDimensionWBC = new ArrayList<DimensionWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				DimensionWBC dimensionWBC = new DimensionWBC();
				dimensionWBC.setDimensionWBC_ID(result.getInt("DimensionWBC_ID"));
				dimensionWBC.setDimensionName(result.getString("DimensionName"));
				dimensionWBC.setDimensionScale(result.getDouble("DimensionScale"));
				
				listDimensionWBC.add(dimensionWBC);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listDimensionWBC;
	}

	public static List<DimensionWBC> getAllValueDimensionWBCSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DimensionWBC ORDER BY " + sortColumnName + " ASC";
		List<DimensionWBC> listDimensionWBC = new ArrayList<DimensionWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				DimensionWBC dimensionWBC = new DimensionWBC();
				dimensionWBC.setDimensionWBC_ID(result.getInt("DimensionWBC_ID"));
				dimensionWBC.setDimensionName(result.getString("DimensionName"));
				dimensionWBC.setDimensionScale(result.getDouble("DimensionScale"));
				
				listDimensionWBC.add(dimensionWBC);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listDimensionWBC;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<DimensionWBC> getValueDimensionWBCByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DimensionWBC where " + columnName + " = ? ";

		List<DimensionWBC> listDimensionWBC = new ArrayList<DimensionWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				DimensionWBC dimensionWBC = new DimensionWBC();
				dimensionWBC.setDimensionWBC_ID(result.getInt("DimensionWBC_ID"));
				dimensionWBC.setDimensionName(result.getString("DimensionName"));
				dimensionWBC.setDimensionScale(result.getDouble("DimensionScale"));
				
				listDimensionWBC.add(dimensionWBC);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listDimensionWBC;
	}

	public static DimensionWBC getValueDimensionWBCByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DimensionWBC where DimensionWBC_ID = ? LIMIT 1";

		List<DimensionWBC> listDimensionWBC = new ArrayList<DimensionWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				DimensionWBC dimensionWBC = new DimensionWBC();
				dimensionWBC.setDimensionWBC_ID(result.getInt("DimensionWBC_ID"));
				dimensionWBC.setDimensionName(result.getString("DimensionName"));
				dimensionWBC.setDimensionScale(result.getDouble("DimensionScale"));
				
				listDimensionWBC.add(dimensionWBC);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listDimensionWBC.get(0);
	}
	
	public static DimensionWBC getValueDimensionWBCByDimensionName(String dimensionName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM DimensionWBC where DimensionName = ? LIMIT 1";

		List<DimensionWBC> listDimensionWBC = new ArrayList<DimensionWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, dimensionName);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				DimensionWBC dimensionWBC = new DimensionWBC();
				dimensionWBC.setDimensionWBC_ID(result.getInt("DimensionWBC_ID"));
				dimensionWBC.setDimensionName(result.getString("DimensionName"));
				dimensionWBC.setDimensionScale(result.getDouble("DimensionScale"));
				
				listDimensionWBC.add(dimensionWBC);
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		if(listDimensionWBC.size()>0) {
		return  listDimensionWBC.get(0); 
		}else return null;
	}
	
	
	
	
}
