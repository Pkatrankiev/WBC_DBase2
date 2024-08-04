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
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class ResultsWBCDAO {


	public static void setValueResultsWBC(Measuring measuring, NuclideWBC nuclideWBC, Double activity, Double postaplenie, Double ggp, Double nuclideDoze) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO ResultsWBC (Measuring_ID, Nuclide_ID, Activity_Bq, Postaplenie_Bq, GGP_Percent, NuclideDoze_mSv) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, measuring.getMeasuring_ID());
			preparedStatement.setInt(2, nuclideWBC.getId_nuclide());
			preparedStatement.setDouble(3, activity);
			preparedStatement.setDouble(4, postaplenie);
			preparedStatement.setDouble(5, ggp);
			preparedStatement.setDouble(6, nuclideDoze);
			
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

	public static void setObjectResultsWBCToTable(ResultsWBC resultsWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO ResultsWBC (Measuring_ID, Nuclide_ID, Activity_Bq, Postaplenie_Bq, GGP_Percent, NuclideDoze_mSv) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, resultsWBC.getMeasuring().getMeasuring_ID());
			preparedStatement.setInt(2, resultsWBC.getNuclideWBC().getId_nuclide());
			preparedStatement.setDouble(3, resultsWBC.getActivity());
			preparedStatement.setDouble(4, resultsWBC.getPostaplenie());
			preparedStatement.setDouble(5, resultsWBC.getGgp());
			preparedStatement.setDouble(6, resultsWBC.getNuclideDoze());
			
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

	public static void updateValueResultsWBC(ResultsWBC resultsWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update ResultsWBC SET  Measuring_ID = ? , Nuclide_ID = ? , Activity_Bq = ? , Postaplenie_Bq = ? , GGP_Percent = ? , NuclideDoze_mSv = ? where ResultsWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, resultsWBC.getMeasuring().getMeasuring_ID());
			preparedStatement.setInt(2, resultsWBC.getNuclideWBC().getId_nuclide());
			preparedStatement.setDouble(3, resultsWBC.getActivity());
			preparedStatement.setDouble(4, resultsWBC.getPostaplenie());
			preparedStatement.setDouble(5, resultsWBC.getGgp());
			preparedStatement.setDouble(6, resultsWBC.getNuclideDoze());
			
			preparedStatement.setInt(7, resultsWBC.getResultsWBC_ID());

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

	public static void deleteValueResultsWBC(int id_ResultsWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from ResultsWBC where ResultsWBC_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_ResultsWBC);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}

	public static List<ResultsWBC> getAllValueResultsWBC() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ResultsWBC";
		List<ResultsWBC> listResultsWBC = new ArrayList<ResultsWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				ResultsWBC resultsWBC = new ResultsWBC();
				
				resultsWBC.setResultsWBC_ID(result.getInt("ResultsWBC_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				resultsWBC.setMeasuring(measuring);
				NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCByID(result.getInt("Nuclide_ID"));
				resultsWBC.setNuclideWBC(nuclideWBC);
				resultsWBC.setActivity(result.getDouble("Activity_Bq"));
				resultsWBC.setPostaplenie(result.getDouble("Postaplenie_Bq"));
				resultsWBC.setGgp(result.getDouble("GGP_Percent"));
				resultsWBC.setNuclideDoze(result.getDouble("NuclideDoze_mSv"));
				
				listResultsWBC.add(resultsWBC);
				
				statement.close();
				connection.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return listResultsWBC;
	}

	public static List<ResultsWBC> getAllValueResultsWBCSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ResultsWBC ORDER BY " + sortColumnName + " ASC";
		List<ResultsWBC> listResultsWBC = new ArrayList<ResultsWBC>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				ResultsWBC resultsWBC = new ResultsWBC();

				resultsWBC.setResultsWBC_ID(result.getInt("ResultsWBC_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				resultsWBC.setMeasuring(measuring);
				NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCByID(result.getInt("Nuclide_ID"));
				resultsWBC.setNuclideWBC(nuclideWBC);
				resultsWBC.setActivity(result.getDouble("Activity_Bq"));
				resultsWBC.setPostaplenie(result.getDouble("Postaplenie_Bq"));
				resultsWBC.setGgp(result.getDouble("GGP_Percent"));
				resultsWBC.setNuclideDoze(result.getDouble("NuclideDoze_mSv"));
				
				listResultsWBC.add(resultsWBC);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listResultsWBC;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static List<ResultsWBC> getValueResultsWBCByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ResultsWBC where " + columnName + " = ? ";

		List<ResultsWBC> listResultsWBC = new ArrayList<ResultsWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Measuring_ID": {
				preparedStatement.setObject(1, ((Measuring) object).getMeasuring_ID());
			}
			break;
			case "Nuclide_ID": {
				preparedStatement.setObject(1, ((NuclideWBC) object).getId_nuclide());
			}
			break;
					
			default:
				preparedStatement.setObject(1, object);
			}
			
		
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				ResultsWBC resultsWBC = new ResultsWBC();

				resultsWBC.setResultsWBC_ID(result.getInt("ResultsWBC_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				resultsWBC.setMeasuring(measuring);
				NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCByID(result.getInt("Nuclide_ID"));
				resultsWBC.setNuclideWBC(nuclideWBC);
				resultsWBC.setActivity(result.getDouble("Activity_Bq"));
				resultsWBC.setPostaplenie(result.getDouble("Postaplenie_Bq"));
				resultsWBC.setGgp(result.getDouble("GGP_Percent"));
				resultsWBC.setNuclideDoze(result.getDouble("NuclideDoze_mSv"));
				
				listResultsWBC.add(resultsWBC);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listResultsWBC;
	}

	public static ResultsWBC getValueResultsWBCByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM ResultsWBC where ResultsWBC_ID = ? LIMIT 1";

		List<ResultsWBC> listResultsWBC = new ArrayList<ResultsWBC>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				ResultsWBC resultsWBC = new ResultsWBC();

				resultsWBC.setResultsWBC_ID(result.getInt("ResultsWBC_ID"));
				Measuring measuring = MeasuringDAO.getValueMeasuringByID(result.getInt("Measuring_ID"));
				resultsWBC.setMeasuring(measuring);
				NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCByID(result.getInt("Nuclide_ID"));
				resultsWBC.setNuclideWBC(nuclideWBC);
				resultsWBC.setActivity(result.getDouble("Activity_Bq"));
				resultsWBC.setPostaplenie(result.getDouble("Postaplenie_Bq"));
				resultsWBC.setGgp(result.getDouble("GGP_Percent"));
				resultsWBC.setNuclideDoze(result.getDouble("NuclideDoze_mSv"));
				
				listResultsWBC.add(resultsWBC);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		return listResultsWBC.get(0);
	}

	public static void moveAllValueResultsWBC() {

		List<ResultsWBC> listResultsWBC = getAllValueResultsWBC();
		System.out.println(listResultsWBC.size());
//	int k =0;
//		for (ResultsWBC resultsWBC : listResultsWBC) {
//			setNewObjectResultsWBCToTable(resultsWBC);
//			System.out.println(k);
//			k++;
//		}		
//			
		
		
	}
	
	public static void setNewObjectResultsWBCToTable(ResultsWBC resultsWBC) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Results (Measuring_ID, Nuclide_ID, Activity_Bq, Postaplenie_Bq, GGP_Percent, NuclideDoze_mSv) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, resultsWBC.getMeasuring().getMeasuring_ID());
			preparedStatement.setInt(2, resultsWBC.getNuclideWBC().getId_nuclide());
			preparedStatement.setDouble(3, resultsWBC.getActivity());
			preparedStatement.setDouble(4, resultsWBC.getPostaplenie());
			preparedStatement.setDouble(5, resultsWBC.getGgp());
			preparedStatement.setDouble(6, resultsWBC.getNuclideDoze());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}

	
}
