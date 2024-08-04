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
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class NuclideWBCDAO {

	

	public static void setValueNuclideWBC(String name_bg, String name_en, String symbol, Double half_life) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO NuclideWBC (Name_bg, Name_en, Symbol, Half_life) VALUES (?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name_bg);
			preparedStatement.setString(2, name_en);
			preparedStatement.setString(3, symbol);
			preparedStatement.setDouble(4, half_life);
			

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

	public static void setObjectNuclideWBCToTable(NuclideWBC nuclideWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO NuclideWBC (Name_bg, Name_en, Symbol, Half_life) VALUES (?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, nuclideWBC.getName_bg_nuclide());
			preparedStatement.setString(2, nuclideWBC.getName_en_nuclide());
			preparedStatement.setString(3, nuclideWBC.getSymbol_nuclide());
			preparedStatement.setDouble(4, nuclideWBC.getHalf_life_nuclide());
			

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	public static void updateValueNuclideWBC(NuclideWBC nuclideWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update NuclideWBC SET Name_bg = ?, Name_en = ?, Symbol = ?, Half_life = ?  where NuclideWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, nuclideWBC.getName_bg_nuclide());
			preparedStatement.setString(2, nuclideWBC.getName_en_nuclide());
			preparedStatement.setString(3, nuclideWBC.getSymbol_nuclide());
			preparedStatement.setDouble(4, nuclideWBC.getHalf_life_nuclide());

			preparedStatement.setInt(5, nuclideWBC.getId_nuclide());

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static void deleteValueNuclideWBC(int Id_NuclideWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from NuclideWBC where NuclideWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, Id_NuclideWBC);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<NuclideWBC> getAllValueNuclideWBC() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM NuclideWBC";
		List<NuclideWBC> listNuclideWBC = new ArrayList<NuclideWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				NuclideWBC nuclideWBC = new NuclideWBC();
				
				nuclideWBC.setNuclideWBC_ID(result.getInt("NuclideWBC_ID"));
				nuclideWBC.setName_bg_nuclide(result.getString("Name_bg"));
				nuclideWBC.setName_en_nuclide(result.getString("Name_en"));
				nuclideWBC.setSymbol_nuclide(result.getString("Symbol"));
				nuclideWBC.setHalf_life_nuclide(result.getDouble("Half_life"));
				
				
				listNuclideWBC.add(nuclideWBC);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listNuclideWBC;
	}

	public static List<NuclideWBC> getAllValueNuclideWBCSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM NuclideWBC ORDER BY " + sortColumnName + " ASC";
		List<NuclideWBC> listNuclideWBC = new ArrayList<NuclideWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				NuclideWBC nuclideWBC = new NuclideWBC();

				nuclideWBC.setNuclideWBC_ID(result.getInt("NuclideWBC_ID"));
				nuclideWBC.setName_bg_nuclide(result.getString("Name_bg"));
				nuclideWBC.setName_en_nuclide(result.getString("Name_en"));
				nuclideWBC.setSymbol_nuclide(result.getString("Symbol"));
				nuclideWBC.setHalf_life_nuclide(result.getDouble("Half_life"));
				
				listNuclideWBC.add(nuclideWBC);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listNuclideWBC;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<NuclideWBC> getValueNuclideWBCByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM NuclideWBC where " + columnName + " = ? ";

		List<NuclideWBC> listNuclideWBC = new ArrayList<NuclideWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				NuclideWBC nuclideWBC = new NuclideWBC();

				nuclideWBC.setNuclideWBC_ID(result.getInt("NuclideWBC_ID"));
				nuclideWBC.setName_bg_nuclide(result.getString("Name_bg"));
				nuclideWBC.setName_en_nuclide(result.getString("Name_en"));
				nuclideWBC.setSymbol_nuclide(result.getString("Symbol"));
				nuclideWBC.setHalf_life_nuclide(result.getDouble("Half_life"));
				
				listNuclideWBC.add(nuclideWBC);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listNuclideWBC;
	}

			

	public static NuclideWBC getValueNuclideWBCByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM NuclideWBC where NuclideWBC_ID = ? LIMIT 1";

		List<NuclideWBC> listNuclideWBC = new ArrayList<NuclideWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				NuclideWBC nuclideWBC = new NuclideWBC();

				nuclideWBC.setNuclideWBC_ID(result.getInt("NuclideWBC_ID"));
				nuclideWBC.setName_bg_nuclide(result.getString("Name_bg"));
				nuclideWBC.setName_en_nuclide(result.getString("Name_en"));
				nuclideWBC.setSymbol_nuclide(result.getString("Symbol"));
				nuclideWBC.setHalf_life_nuclide(result.getDouble("Half_life"));
				
				listNuclideWBC.add(nuclideWBC);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listNuclideWBC.get(0);
	}

	public static NuclideWBC getValueNuclideWBCBySymbol(String symbol) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM NuclideWBC where Symbol = ? LIMIT 1";

		List<NuclideWBC> listNuclideWBC = new ArrayList<NuclideWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, symbol);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				NuclideWBC nuclideWBC = new NuclideWBC();

				nuclideWBC.setNuclideWBC_ID(result.getInt("NuclideWBC_ID"));
				nuclideWBC.setName_bg_nuclide(result.getString("Name_bg"));
				nuclideWBC.setName_en_nuclide(result.getString("Name_en"));
				nuclideWBC.setSymbol_nuclide(result.getString("Symbol"));
				nuclideWBC.setHalf_life_nuclide(result.getDouble("Half_life"));
				
				listNuclideWBC.add(nuclideWBC);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listNuclideWBC.get(0);
	}

	
	public static String[] getMasiveSimbolNuclide() {
		List<NuclideWBC> list =getAllValueNuclideWBC();
		String[] masive = new String[list.size()];
		int i=0;
		for (NuclideWBC object :list) {
			masive[i] = object.getSymbol_nuclide();
			i++;
		}
		return masive;
	}


}
