package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class ExtractSpectriWithDozaFromSICH2 {

	private static SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

	public static void getFile() {
		String destinationPath = "d:\\PKatrankiev\\Spectri SICH2\\";
		String sourcePath = "d:\\PKatrankiev\\Spectri SICH2\\";
		List<File> listFile = new ArrayList<File>();
		System.out.println("gooooo");
		List<Measuring> listMeasurWithDoze = getAllValueMeasuringWithDozeFromSICH2();

		String[][] masiveEGN_Date = getEGNFromMeasurWitchDoze(listMeasurWithDoze);
		System.out.println(masiveEGN_Date.length);
		for (int i = 0; i < masiveEGN_Date.length; i++) {
			System.out.println(masiveEGN_Date[i][0]+"  "+masiveEGN_Date[i][1]);
			String year = masiveEGN_Date[i][1].substring(6);
			
			String filePath = sourcePath + year;
			System.out.println(filePath);
			File file = generateFilesList(filePath, masiveEGN_Date[i]);
			System.out.println("---------------"+file.getName());
			
			File[] files = new File(filePath).listFiles();
			String sourceFileStr = file.getName().replace(".rpt", "");
			for (File newfile : files) {
				
			if(newfile.getName().contains(sourceFileStr)) {	
				listFile.add(newfile);
			}
			
			}
			
		}
		
		CopyExistsWithSameContents( listFile,  destinationPath);
			int k=0;
			for (File kfile : listFile) {
				System.out.println(k+" "+kfile.getName());
				k++;
			}
		

	}

	public static void CopyExistsWithSameContents(List<File> files, String destinationPath)  {
		
		
		for(File file : files) {
		    try {
				Files.copy(file.toPath(),
				    (new File(destinationPath + file.getName())).toPath(),
				    StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			}
	
	
	public static List<Measuring> getAllValueMeasuringWithDozeFromSICH2() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring where Lab_ID = 2 ORDER BY DOZE DESC";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				if (result.getDouble("Doze") > 0) {
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
			}

			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		return list;
	}

	private static String[][] getEGNFromMeasurWitchDoze(List<Measuring> listMeasurWithDoze) {
		String[][] masiveEGN_Date = new String[listMeasurWithDoze.size()][2];

		int i = 0;
		for (Measuring measur : listMeasurWithDoze) {
			String date = sdfrmt.format(measur.getDate());
			int year = Integer.parseInt(date.substring(6));
			System.out.println("yyyyyyyyyyyyyyyyyy  "+year);
			if(year >= 2018 && year < 2023) {
			masiveEGN_Date[i][0] = measur.getPerson().getEgn();
			masiveEGN_Date[i][1] = date;
			i++;
		}
		}
		String[][] newMasive = new String[i][2];
		for (int j = 0; j < newMasive.length; j++) {
			newMasive[j] = masiveEGN_Date[j];
		}
		
		return newMasive;
	}

	private static File generateFilesList(String filePath, String[] masiveEGN_Date) {
		
		File[] files = new File(filePath).listFiles();

		for (File file : files) {
			if (file.isFile()) {
				BasicFileAttributes attributes;
				try {
					attributes = Files.readAttributes(Paths.get(file.toURI()), BasicFileAttributes.class);
					String egn = getEGNFromFile(file);
					FileTime fileTime = attributes.lastModifiedTime();
					Date date = new Date(fileTime.toMillis());
					if( getFileExtension(file).equals("rpt")) {
//					System.out.println(sdfrmt.format(date) + " - " + masiveEGN_Date[1]);
//					System.out.println(egn+"  "+masiveEGN_Date[0]);
					if (sdfrmt.format(date).equals(masiveEGN_Date[1]) 
							&& egn.equals(masiveEGN_Date[0])) {
						return file;
					}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	static String getFileExtension(File file) {
		if (file == null) {
			return "";
		}
		String name = file.getName();
		int i = name.lastIndexOf('.');
		String ext = i > 0 ? name.substring(i + 1) : "";
		return ext;
	}



	public static String getEGNFromFile(File file) {

		String[] stringArray = CreadMasiveFromReadFile(file);
		int countLine = stringArray.length;

		String[][] stringLine = new String[countLine][];

		for (int j = 0; j < countLine; j++) {

			if (stringArray[j].contains(":")) {
				stringLine[j] = StringUtils.split(stringArray[j]);

				if (stringLine[j].length > 0) {

					if (stringLine[j][0].contains("Name:")) {
						for (int i = 0; i < stringLine[j].length; i++) {
							if (stringLine[j][i].equals("SSN:")) {
								return stringLine[j][i + 1].trim();
							}
						}

					}

				}
			}

		}

		return null;
	}

	static String[] CreadMasiveFromReadFile(File file) {
		BufferedReader br = null;
		FileReader fr = null;
		List<String> listString = new ArrayList<String>();
		String[] newStringArray = null;
		try {
//			File fileDir = new File(FILENAME);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"));
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				listString.add(sCurrentLine);
			}

			int countLine = listString.size();
			newStringArray = new String[countLine];
			for (int i = 0; i < newStringArray.length; i++) {
				newStringArray[i] = listString.get(i);
			}
		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ResourceLoader.appendToFile(ex);
				ex.printStackTrace();
			}
		}
		return newStringArray;
	}

	public static Date reformarDateMeasur(String origin_date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdf6 = new SimpleDateFormat("d.M.yy");
		SimpleDateFormat sdf7 = new SimpleDateFormat("dd.M.yy");
		SimpleDateFormat sdf8 = new SimpleDateFormat("dd.MM.yy");
		Date date = new Date();

		try {
			if (origin_date.length() == 6) {
				date = sdf6.parse(origin_date);
			}
			if (origin_date.length() == 7) {
				date = sdf7.parse(origin_date);
			}
			if (origin_date.length() == 8) {
				date = sdf8.parse(origin_date);
			}
			if (origin_date.length() == 10) {
				date = sdf.parse(origin_date);
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "nekorektna date", "Error Data", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}

		if (date == null) {
			String str = "nekorektna data " + origin_date;
			MessageDialog(str);
		}
		return date;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Error Data", JOptionPane.ERROR_MESSAGE);
	}

}
