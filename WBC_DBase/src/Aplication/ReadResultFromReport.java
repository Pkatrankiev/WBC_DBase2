package Aplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;

public class ReadResultFromReport {

	

	public static List<ReportMeasurClass> getListReadGamaFiles(){
		
		 List<ReportMeasurClass> list = new  ArrayList<>();
		 JFileChooser chooiser = new JFileChooser();
		 chooiser.setMultiSelectionEnabled(true);
		 chooiser.showOpenDialog(null);
		 File[] files = chooiser.getSelectedFiles();
		System.out.println(files.length);
		
		for (File file : files) {
			ReportMeasurClass reportMeasurClass = getReadGamaFile(file);
			
			if(reportMeasurClass!=null) {
			list.add(reportMeasurClass);
			}
		}
			
		return list;
	}


	public static void PrintListReportMeasurClass(List<ReportMeasurClass> list) {
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		for (ReportMeasurClass reportMeasur : list) {
			if(reportMeasur.getToExcell()) {
			InsertMeasurToExcel.InsertMeasurToExcel(reportMeasur);
			}
			System.out.println(sdf.format(reportMeasur.getMeasur().getDate()) +
				" - " +	reportMeasur.getMeasur().getPerson().getFirstName() +
				 " - " + reportMeasur.getMeasur().getDoze() +
				 " - " + reportMeasur.getMeasur().getDoseDimension().getDimensionName()+
				 " - " +  reportMeasur.getMeasur().getLab().getLab()+ 
				 " - " +  reportMeasur.getMeasur().getUser().getLastName()+
				 " - " +  reportMeasur.getMeasur().getTypeMeasur().getKodeType()+
				 " - " +  reportMeasur.getToExcell()+
				 " - " +  reportMeasur.getKoment());
		 
		 
		for (String string : reportMeasur.getListNuclideData()) {
			System.out.println(string);
		}
		}
	}
	
	
	public static ReportMeasurClass getReadGamaFile(File file) {

		Date dat�M = null ;
		String  egn = "", operatorName = "", wbc = "";

		String[] stringArray = CreadMasiveFromReadFile(file);
		int countLine = stringArray.length;
		boolean flagNuclidy = true;
		boolean flagEnd = true;
		int countLineToNuclide = 0;
		
		ReportMeasurClass reportMeasurClass = new ReportMeasurClass();
	
		String[][] stringLine = new String[countLine][];
		Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(1);
		Person per = new Person();
		UsersWBC usersWBC = new UsersWBC();
		DimensionWBC dozeDim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(1);
		List<String> listNuclideData = new ArrayList<>();
		
		for (int j = 0; j < countLine; j++) {

			if (stringArray[j].contains("I N T E R F E R E N C E   C O R R E C T E D   R E P O R T")) {
				flagNuclidy = false;
			}

			if (flagNuclidy) {
				if (stringArray[j].contains(":")) {
					stringLine[j] = StringUtils.split(stringArray[j], ":");

					if (stringLine[j].length > 0) {

						if (stringLine[j][0].contains("In-vivo Report")) {
							dat�M = reformarDateMeasur(StringUtils.split(stringLine[j][1])[0]);
							
						}

						if (stringLine[j][0].contains("Identification Numbe")) {
							egn = stringLine[j][1].trim();
							per = PersonDAO.getValuePersonByEGN(egn);
						}

						if (stringLine[j][0].contains("Operator Name")) {
							operatorName = StringUtils.split(stringLine[j][1])[1].trim();
							 if(UsersWBCDAO.getValueUsersWBCByObject("LastName_EG", operatorName).size()<1) {
									String str = "във файл: "+ file.getName()+" Not Operator Name: "+ operatorName;
												 MessageDialog(str);
										 }else {
							usersWBC = UsersWBCDAO.getValueUsersWBCByObject("LastName_EG", operatorName).get(0);
										 }
						}

						if (stringLine[j][0].contains("Counter")) {
							wbc = stringLine[j][1];
							if (wbc.contains("3")) {
								lab = LaboratoryDAO.getValueLaboratoryByID(3);
							}
						}
					}
				}
			} else {

				if (stringArray[j].length() > 0) {
					if (stringArray[j].contains(" ? = nuclide is part of an undetermined solution")) {
						flagEnd = false;
					}
					countLineToNuclide++;
					if (countLineToNuclide > 5 && flagEnd) {
						if(!stringArray[j].contains("K-40")) {
						listNuclideData.add(stringArray[j]);
						}
					}
				}
			}
		}
							
		
		
		 Measuring measur = new Measuring(per, dat�M, 0.0, dozeDim, lab, usersWBC, type, file.getName());
		 reportMeasurClass.setMeasur(measur);
		 reportMeasurClass.setListNuclideData(listNuclideData);
		 reportMeasurClass.setToExcell(true);
		 reportMeasurClass.setKoment("");
		 
		 if(per==null) {
			 if(!egn.equals("001") && !egn.equals("002") && !egn.equals("05") && !egn.equals("6")) {
		String str = "във файл: "+ file.getName()+" Identification Number: " +egn;
					 MessageDialog(str);
			 }
	  reportMeasurClass=null;
			}
				 
		return reportMeasurClass;
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
			JOptionPane.showMessageDialog(null, "Преформатиране на - Датата", "Грешка в данните",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}
		
		if (date==null) {
			String str = "nekorektna data "+origin_date; 
			MessageDialog(str);
		}
		return date;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	
	
}
