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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;

public class ReadResultFromReport {

	

	public static List<ReportMeasurClass> getListReadGamaFiles(File[] files){
		
		 List<ReportMeasurClass> list = new  ArrayList<>();
		
		
		DimensionWBC dozeDim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(1);
		List<String> listLastName_EG_Users = UsersWBCDAO.getAllValueUsersWBCLastName_EG();
		
		for (File file : files) {
			System.out.println("***************************** "+file.getName());
			if(file.getName().toLowerCase().contains(".rpt")) {
			ReportMeasurClass reportMeasurClass = getReadGamaFile(file, dozeDim, type, listLastName_EG_Users);
			System.out.println(reportMeasurClass!=null?"Person "+reportMeasurClass.getMeasur().getPerson().getEgn()
					+" "+reportMeasurClass.getMeasur().getDate():"null Person");
			if(reportMeasurClass!=null) {
			list.add(reportMeasurClass);
			}
			}
		}
		sortByDate(list);
		return list;
	}

	 public static void sortByDate( List<ReportMeasurClass> perStat)
	    {	    		    	

		Collections.sort(perStat, new Comparator<ReportMeasurClass>() {
		 
		@Override
		public int compare(ReportMeasurClass o1, ReportMeasurClass o2) {
			 return UpdateBDataFromExcellFiles.extractedDateTimeFromFile(o1.getReportFile().getPath()).
					 compareTo(UpdateBDataFromExcellFiles.extractedDateTimeFromFile(o2.getReportFile().getPath()));
		}
		});
	
	    }
	
	
	
	public static void PrintListReportMeasurClass(List<ReportMeasurClass> list) {
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		for (ReportMeasurClass reportMeasur : list) {
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
	
	
	public static ReportMeasurClass getReadGamaFile(File file, DimensionWBC dozeDim, TypeMeasur type, List<String> listLastName_EG_Users) {

		

		String[] stringArray = CreadMasiveFromReadFile(file);
		
		ReportMeasurClass reportMeasurClass = new ReportMeasurClass();
		
		if(stringArray[0].contains("WBC2")) {
			reportMeasurClass = extractedReportMeasurFromFileRENAISANCE(file,  dozeDim, type, stringArray, listLastName_EG_Users);	
		}else {
		 reportMeasurClass = extractedReportMeasurFromFileABACOSS(file,  dozeDim, type, stringArray);
		}		 
		return reportMeasurClass;
	}

	private static ReportMeasurClass extractedReportMeasurFromFileABACOSS(File file, DimensionWBC dozeDim, TypeMeasur type,
			String[] stringArray) {
		ReportMeasurClass reportMeasurClass = new ReportMeasurClass();
		
		if(stringArray[1].contains("W H O L E   B O D Y  C O U N T I N G   A N A L Y S I S")) {
			
		Date dateM = null ;
		String  egn = "", operatorName = "", wbc = "";
		int countLine = stringArray.length;
		boolean flagNuclidy = true;
		boolean flagEnd = true;
		int countLineToNuclide = 0;
		String[][] stringLine = new String[countLine][];
		
		List<String> listNuclideData = new ArrayList<>();
		Person per = new Person();
		UsersWBC usersWBC = new UsersWBC();
		Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(1);
		
		for (int j = 0; j < countLine; j++) {

			if (stringArray[j].contains("I N T E R F E R E N C E   C O R R E C T E D   R E P O R T")) {
				flagNuclidy = false;
			}

			if (flagNuclidy) {
				if (stringArray[j].contains(":")) {
					stringLine[j] = StringUtils.split(stringArray[j], ":");

					if (stringLine[j].length > 0) {

						if (stringLine[j][0].contains("In-vivo Report")) {
							dateM = reformarDateMeasur(StringUtils.split(stringLine[j][1])[0]);
							
						}

						if (stringLine[j][0].contains("Identification Numbe")) {
							egn = stringLine[j][1].trim();
							per = PersonDAO.getValuePersonByEGN(egn);
						}

						if (stringLine[j][0].contains("Operator Name")) {
							operatorName = stringLine[j][1];
							if(StringUtils.split(stringLine[j][1]).length > 1) {
							operatorName = StringUtils.split(stringLine[j][1])[1].trim();
							}
							 if(UsersWBCDAO.getValueUsersWBCByObject("LastName_EG", operatorName).size()<1) {
									String str = "In file: "+ file.getName()+" Not Operator Name: "+ operatorName;
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
							
		
		
		 Measuring measur = new Measuring(per, dateM, 0.0, dozeDim, lab, usersWBC, type, "", file.getName(), egn);
		 reportMeasurClass.setMeasur(measur);
		 reportMeasurClass.setListNuclideData(listNuclideData);
		 reportMeasurClass.setToExcell(true);
		 reportMeasurClass.setKoment("");
		 reportMeasurClass.setReportFile(file);
		 
		 if(per==null) {
			 if(!egn.equals("001") && !egn.equals("002") && !egn.equals("05") && !egn.equals("6")) {
		String str = "In file: "+ file.getName()+" Identification Number: " +egn;
					 MessageDialog(str);
			 }
	  reportMeasurClass=null;
			}
		}else {
			 reportMeasurClass=null;
		}
		return reportMeasurClass;
	}



	private static ReportMeasurClass extractedReportMeasurFromFileRENAISANCE(File file, DimensionWBC dozeDim, TypeMeasur type,
			String[] stringArray, List<String> listLastName_EG_Users) {
		
		
		Date dateM = null ;
		String  egn = "", operatorName = "", str = "";
		int countLine = stringArray.length;
		boolean flagNuclidy = true;
		boolean flagEnd = true;
		int countLineToNuclide = 0;
		ReportMeasurClass reportMeasurClass = new ReportMeasurClass();
		List<String> listNuclideData = new ArrayList<>();
		Person per = new Person();
		UsersWBC usersWBC = new UsersWBC();
		Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(2);
		int index = 0;
		for (int j = 0; j < countLine; j++) {

			if (stringArray[j].contains("Summary Report")) {
				flagNuclidy = false;
			}

			if (flagNuclidy) {
				if (stringArray[j].contains(":")) {
					
					index = stringArray[j].indexOf("Acq Date:");
					if (index >0) {
						System.out.println("Acq Date:"+str+" "+index);
						str = stringArray[j].substring(index+9, stringArray[j].length()).trim();
						System.out.println("Acq Date:"+str+" "+index);
						str = str.substring(0, 10).replace("г","").trim();
						System.out.println("Acq Date:"+str);
							dateM = reformarDateMeasur(str);
					}

					index = stringArray[j].indexOf("SSN:");
					if (index >0) {
							egn = stringArray[j].substring(index+4, stringArray[j].length()).trim();
							System.out.println("SSN:"+egn);
							per = PersonDAO.getValuePersonByEGN(egn);
						}

					index = stringArray[j].indexOf("Operator:");	
					if (index >0) {
						operatorName = stringArray[j].substring(index+9, stringArray[j].length()).trim();
						System.out.println("Operator:"+operatorName);
						usersWBC = getUserByOperatorName(operatorName, listLastName_EG_Users);
						if(usersWBC == null) {
						usersWBC = choisePerson(operatorName);
				 }
					}

					
				}
			} else {

				if (stringArray[j].length() > 0) {
					if (stringArray[j].contains("Total Activity:")) {
						flagEnd = false;
					}
					countLineToNuclide++;
					if (countLineToNuclide > 5 && flagEnd) {
						if(!stringArray[j].contains("K-40") && !stringArray[j].contains("_")) {
							if(ifNotNulActivityAndUncert(stringArray[j],60))
						listNuclideData.add(stringArray[j]);
						}
					}
				}
			}
		}
							
		System.out.println(per!=null?per.getEgn():"null"+" "+ dateM+" "+ dozeDim.getDimensionName()+" "+ lab.getLab()+" "+ usersWBC.getLastName_EG()+" "+ type.getNameType()+" "+file.getName());
		
		 Measuring measur = new Measuring(per, dateM, 0.0, dozeDim, lab, usersWBC, type, "", file.getName(), egn);
		 reportMeasurClass.setMeasur(measur);
		 reportMeasurClass.setListNuclideData(listNuclideData);
		 reportMeasurClass.setToExcell(true);
		 reportMeasurClass.setKoment("");
		 reportMeasurClass.setReportFile(file);
		 
		 if(per==null) {
			 if(!egn.equals("001") && !egn.equals("002") && !egn.equals("05") && !egn.equals("6")) {
		str = "In file: "+ file.getName()+" Identification Number: " +egn;
					 MessageDialog(str);
			 }
	  reportMeasurClass=null;
			}
		return reportMeasurClass;
	}

	private static boolean ifNotNulActivityAndUncert(String string, int uncert) {
	
		String[] value = StringUtils.split(string);
		for (String str : value) {
			System.out.print(" - "+str);
		}
		System.out.println();
			double db = Double.parseDouble(value[2].trim());
			double activDB = Double.parseDouble(value[1].trim());
			double mda = Double.parseDouble(value[3].trim());
			
			if(db < uncert && activDB > 0 && activDB > mda) {
			return true;
			}
		
		return false;
	}


	private static UsersWBC getUserByOperatorName(String operatorName, List<String> listLastName_EG_Users) {
		 for (String lastName_EG : listLastName_EG_Users) {
				if(lastName_EG.toLowerCase().contains(operatorName.toLowerCase())) {
				return UsersWBCDAO.getValueUsersWBCByObject("LastName_EG", lastName_EG).get(0);
							 }
		}
		return null;
	}

	private static UsersWBC choisePerson(String operatorName) {

		
		String[] masiveUsers = UsersWBCDAO.getMasiveUserWBCNames();
		String str = (String) JOptionPane.showInputDialog(null, operatorName, operatorName,
				JOptionPane.QUESTION_MESSAGE, null, masiveUsers, masiveUsers[2]);

		if (str != null && str.length() > 0) {
			String[] masiveStr = str.split(" ");
			return UsersWBCDAO.getValueUsersWBCByObject("LastName", masiveStr[1]).get(0);
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

		SimpleDateFormat sdfrmt = AplicationMetods.extractedSimleDateFormatFromDate(origin_date);
		Date date = new Date();

		try {
			date = sdfrmt.parse(origin_date);
			
			System.out.println("origin_date.length() "+origin_date.length()+" "+date);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "nekorektna date", "Error Data",
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
		JOptionPane.showMessageDialog(jf, text, "Error Data", JOptionPane.ERROR_MESSAGE);
	}

	
	
}
