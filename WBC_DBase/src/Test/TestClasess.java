package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import Aplication.UpdateBDataFromExcellFiles;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import PersonReference.PersonExcellClass;

public class TestClasess {

	public static void UpDataFromArhiveExcellFile() {

//	String key ="";
//	String key = "Person";
//	String key = "Spisak_Prilogenia";
//	String key = "PersonStatus";
//	String key = "KodeStatus";
//	String key = "Measuring";
//	String key = "ResultsWBC";
		String key = "ObhodenList";

		String year = "2022";

		boolean save = true;
//	boolean save = false;

//	String[] keyM = {"Measuring", "ResultsWBC"};;
		for (int i = 20; i < 23; i++) {

//	for (String key : key) {
			String yy = "20" + i;
			System.out.println(yy);
			AplicationMetods.readInfoFromGodExcelFile(yy, key, save);

		}
//	}

	}

	@SuppressWarnings("rawtypes")
	public static void testDelNoInfo() {

		List<PersonStatus> list = getValuePersonStatusByPersonAndDateSet();
		System.out.println("list.size() " + list.size());

		List<PersonStatus> listMOOR = getValuePersonStatus();
		System.out.println("listMOOR.size() " + listMOOR.size());

		int k = 0;
		for (PersonStatus personStatusMOOR : listMOOR) {

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PersonStatus personStatus = (PersonStatus) iterator.next();

				if (personStatus.getPersonStatus_ID() != personStatusMOOR.getPersonStatus_ID()
						&& personStatus.getPerson().getId_Person() == personStatusMOOR.getPerson().getId_Person()
						&& personStatus.getWorkplace().getId_Workplace() == personStatusMOOR.getWorkplace()
								.getId_Workplace()) {
					PersonStatusDAO.deleteValuePersonStatus(personStatus);
					iterator.remove();
					System.out.println(k);
					k++;
				}
			}
		}

	}

	public static List<PersonStatus> getValuePersonStatusByPersonAndDateSet() {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

		Date dateSet = null;
		try {
			dateSet = sdfrmt.parse("31.12.2022");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where Spisak_Prilogenia_ID = 546  AND  DateSet = ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO
						.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}

	public static List<PersonStatus> getValuePersonStatus() {

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

		Date dateSet = null;
		try {
			dateSet = sdfrmt.parse("31.12.2022");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM PersonStatus where DateSet = ?";

		List<PersonStatus> listPersonStatus = new ArrayList<PersonStatus>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, dateSet);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				PersonStatus PersonStatus = new PersonStatus();

				PersonStatus.setPersonStatus_ID(result.getInt("PersonStatus_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				PersonStatus.setPerson(person);
				Workplace workplace = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				PersonStatus.setWorkplace(workplace);
				Spisak_Prilogenia spisak_Prilogenia = Spisak_PrilogeniaDAO
						.getValueSpisak_PrilogeniaByID(result.getInt("Spisak_Prilogenia_ID"));
				PersonStatus.setSpisak_prilogenia(spisak_Prilogenia);
				UsersWBC userWBC = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				PersonStatus.setUserWBC(userWBC);
				PersonStatus.setDateSet(result.getDate("DateSet"));
				PersonStatus.setZabelejka(result.getString("Zabelejka"));
				listPersonStatus.add(PersonStatus);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		return listPersonStatus;
	}

	public static void test() {

		List<PersonStatus> list1 = ReadPersonStatusFromExcelFile
				.getListPersonStatusWithoutSpisak_Prilogenia("7906113205");
		ReadPersonStatusFromExcelFile.ListPersonStatus(list1);

		ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list1);

		UpdateBDataFromExcellFiles.updataFromGodExcelFile();

		AplicationMetods.testGetListPersonSatatusByPersonAndDateAfterDateSet();

		List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
		System.out.println(list.size());
		ReadResultFromReport.PrintListReportMeasurClass(list);

	}

	@SuppressWarnings("unused")
	private static void testMeasuringToResultWCB() {
		Measuring measur;
		List<ResultsWBC> listresulterror = new ArrayList<>();
		List<ResultsWBC> listresult = ResultsWBCDAO.getAllValueResultsWBC();
		for (ResultsWBC resultsWBC : listresult) {
			System.out.println("-> " + resultsWBC.getResultsWBC_ID());
			measur = resultsWBC.getMeasuring();
			if (measur == null) {

				System.out.println("->>> " + resultsWBC.getResultsWBC_ID());
				listresulterror.add(resultsWBC);
			} else {
				if (measur.getDoze() == 0.0) {
					System.out.println("--->>> 000 " + measur.getMeasuring_ID());
					listresulterror.add(resultsWBC);
				}
			}
		}

		for (ResultsWBC resultsWBC : listresulterror) {
			System.out.println(resultsWBC.getResultsWBC_ID());
		}
	}

	public static void CheckCorrectionAllRowInSheets() {
		boolean fl = true;
		String filePath[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig") };

		String fileName[] = { "Personel", "External" };

		for (int i = 0; i < filePath.length; i++) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[i]);
			String str1 = "", str2 = "", str3 = "", str4 = "";
			Sheet sheet1 = workbook.getSheetAt(0);
			Sheet sheet2 = workbook.getSheetAt(1);
			Sheet sheet3 = workbook.getSheetAt(2);
			Sheet sheet4 = workbook.getSheetAt(3);

			Cell cell1, cell2, cell3, cell4;
			for (int row = 5; row <= sheet1.getLastRowNum(); row += 1) {

				if (sheet1.getRow(row) != null) {

					for (int col = 0; col <= 6; col++) {
//					System.out.println(row);

						if (sheet1.getRow(row) != null && sheet2.getRow(row) != null && sheet3.getRow(row) != null
								&& sheet4.getRow(row) != null) {

							cell1 = sheet1.getRow(row).getCell(col);
							cell2 = sheet2.getRow(row).getCell(col);
							cell3 = sheet3.getRow(row).getCell(col);
							cell4 = sheet4.getRow(row).getCell(col);

							if (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)
									&& ReadExcelFileWBC.CellNOEmpty(cell3) && ReadExcelFileWBC.CellNOEmpty(cell4)) {

								str1 = ReadExcelFileWBC.getStringfromCell(cell1);
								str2 = ReadExcelFileWBC.getStringfromCell(cell2);
								str3 = ReadExcelFileWBC.getStringfromCell(cell3);
								str4 = ReadExcelFileWBC.getStringfromCell(cell4);

								if (str1.equals(str2) && str2.equals(str3) && str3.equals(str4)) {
//						System.out.println(row+" OK "+str1+" "+str2+" "+str3+" "+str4);
								} else {
									fl = false;
									System.out.println(fileName[i] + " : " + row + " NO " + str1 + " " + str2 + " "
											+ str3 + " " + str4);
								}
							}
						}
					}
				}
			}
		}
		System.out.println();
		if (fl) {
			System.out.println(" ++++++++++++++++++++ OK ++++++++++++++++++++++++ ");
		} else {
			System.out.println("******************* NOT OK *************************");
		}
	}

	@SuppressWarnings("deprecation")
	public static void CheckForCorrectionMeasuringDataInSheet0AndSeet1() {
		boolean fl = true;
		String filePath[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig") };

		String fileName[] = { "Personel", "External" };

		for (int i = 0; i < filePath.length; i++) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[i]);

			Date date;
			int mont = 0;
			Sheet sheet = workbook.getSheetAt(1);
			Sheet sheet0;
			double doze0, doze;
			Cell cell_date, cell_Lab;
			for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

				if (sheet.getRow(row) != null) {

					int k = 7;
					cell_date = sheet.getRow(row).getCell(k);
					k++;
					cell_Lab = sheet.getRow(row).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell_date) && ReadExcelFileWBC.CellNOEmpty(cell_Lab)) {
						date = ReadExcelFileWBC.readCellToDate(cell_date);
						k = k + 17;
						doze = createDoze(sheet, row, k);
						mont = date.getMonth();
						sheet0 = workbook.getSheetAt(0);

						mont = mont + 77;

						doze0 = createDoze(sheet0, row, mont);

						if (doze0 != doze) {
							System.out.println(fileName[i] + " : " + row + " - " + doze0 + " - " + doze + " - " + mont);
							fl = false;
						}
						if (k > 253) {
							k = 6;
							sheet = workbook.getSheetAt(2);
						}

						k++;
						cell_date = sheet.getRow(row).getCell(k);
						k++;
						cell_Lab = sheet.getRow(row).getCell(k);

					}

				}

			}
		}
		System.out.println();
		if (fl) {
			System.out.println(" ++++++++++++++++++++ OK ++++++++++++++++++++++++ ");
		} else {
			System.out.println("******************* NOT OK *************************");
		}
	}

	public static double createDoze(Sheet sheet, int row, int k) {
		double doze = 0;

		Cell cell = sheet.getRow(row).getCell(k);
		doze = 0.0;
		if (cell != null) {
			String type = cell.getCellType().toString();
			switch (type) {
			case "STRING": {

				String str = cell.getStringCellValue();
				if (str.contains("<")) {
					doze = 0.05;
				} else {

					if (TypeMeasurDAO.getValueTypeMeasurByObject("KodeType", str).size() > 0) {
						doze = 0.0;
					}

					try {
						doze = Double.parseDouble(str);
					} catch (Exception e) {
						doze = -1;
					}

					if (doze == -1) {
						str = cell.getStringCellValue();
						str = AplicationMetods.transliterate(str).toUpperCase();
						doze = 0.0;
					}
				}
			}
				break;
			case "NUMERIC": {
				doze = cell.getNumericCellValue();
			}
				break;
			}
		}

		return doze;

	}

	public static String getDozeToString(Sheet sheet, int row, int k) {
		String str = "";

		Cell cell = sheet.getRow(row).getCell(k);

		if (cell != null) {
			String type = cell.getCellType().toString();
			switch (type) {
			case "STRING": {

				str = cell.getStringCellValue();

			}
				break;
			case "NUMERIC": {

				str = cell.getNumericCellValue() + "";
			}
				break;
			}
		}

		return str;

	}

	public static void CheckForCorrectionMeasuringInSheet0AndInMonth() {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

//		boolean fl = true;

		String filePath[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig") };

		String filePathMont[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig") };

		Workbook workbookMont[] = { ReadExcelFileWBC.openExcelFile(filePathMont[0]),
				ReadExcelFileWBC.openExcelFile(filePathMont[1]) };

		Workbook workbook[] = { ReadExcelFileWBC.openExcelFile(filePath[0]),
				ReadExcelFileWBC.openExcelFile(filePath[1]) };

		String fileName[] = { "Personel", "External" };

		for (int i = 0; i < 1; i++) {

			String[][][] masiveStrMonth = MasiveFromMonth(sdfrmt, workbookMont[i]);
			String[][][] masuveMeasur = masuveMeasur(sdfrmt, workbook[i]);
			String[][][] masiveDoze = masiveDoze(workbook[i]);

			String[][][] masuveMeasur2 =  masuveMeasur(sdfrmt, workbook[i]);
			
						
			
			for (int mont = 0; mont < 12; mont++) {
				
				
				for (int d = 0; d < masiveDoze[mont].length; d++) {
					if (masiveDoze[mont][d][0] != null) {
				double sumDoze = 0.0, doubDoze= 0.0;
				String sumStrDoze = "", strDoze = "";
				boolean isString = false, fl2 = false;
				List<Integer> list = new ArrayList<>();
						for (int k = 0; k < masuveMeasur[mont].length; k++) {
							if (masuveMeasur[mont][k][0] != null) {
								if (masuveMeasur[mont][k][0].equals(masiveDoze[mont][d][0])){
									list.add(k);
									try {
										sumDoze =+ Double.parseDouble(masuveMeasur[mont][k][1]);
										
									} catch (Exception e) {
										sumStrDoze = masuveMeasur[mont][k][1];
									} 
									
								}
							}
						}
						
						try {
							doubDoze =+ Double.parseDouble(masiveDoze[mont][d][1]);
						} catch (Exception e) {
							strDoze = masiveDoze[mont][d][1];
							isString=true;
						} 
								if(isString) {
									if(sumStrDoze.equals(strDoze)) fl2=true;
								}else {
									if(sumDoze==doubDoze) fl2=true;	
								}
											 
								if(fl2) {
									masiveDoze[mont][d][0] = null;
									for (int index : list) {
										masuveMeasur[mont][index][0] = null;	
									}
									
								}
							
									
							}
						}
								
			
				System.out.println(mont);
				for (int m = 0; m < masiveStrMonth[mont].length; m++) {
					if (masiveStrMonth[mont][m][0] != null) {
						
						for (int k = 0; k < masuveMeasur2[mont].length; k++) {
							if (masuveMeasur2[mont][k][0] != null) {
								if (masiveStrMonth[mont][m][0] != null
										&& masiveStrMonth[mont][m][0].equals(masuveMeasur2[mont][k][0])
										&& masiveStrMonth[mont][m][1].equals(masuveMeasur2[mont][k][1])
										&& masiveStrMonth[mont][m][2].equals(masuveMeasur2[mont][k][2])
										&& masiveStrMonth[mont][m][3].equals(masuveMeasur2[mont][k][3])
										) {
								
									masiveStrMonth[mont][m][0] = null;
									masuveMeasur2[mont][k][0] = null;
									
							
								}
							}
						}

					}
				}
			}
		
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");			
			
			
			for (int m = 0; m < 12; m++) {
				System.out.println();
				System.out.println(" За месец: "+(m+1));
				
				
				boolean fl =true;
				for (int j = 0; j < masiveStrMonth[m].length; j++) {
					if (masiveStrMonth[m][j][0] != null) {
						if(fl) {
							System.out.println();
						System.out.println(" Има го в Month "+ fileName[i]+",но го няма в "+ fileName[i]+" Изм СИЧ");
						fl = false;
						}
						System.out.println(j + " - " + masiveStrMonth[m][j][0] + " " + masiveStrMonth[m][j][1] + " "
								+ masiveStrMonth[m][j][2] + " " + masiveStrMonth[m][j][3]);
					
				}
			}
			 fl =true;
					
					for (int j = 0; j < masuveMeasur2[m].length; j++) {
						if (masuveMeasur2[m][j][0] != null) {
							if(fl) {
								System.out.println();
								System.out.println(" Има го в "+ fileName[i]+" Изм СИЧ,но го няма в Month "+ fileName[i]);
								fl = false;
								}
							System.out.println(j + " - " + masuveMeasur2[m][j][0] + " " + masuveMeasur2[m][j][1] + " "
									+ masuveMeasur2[m][j][2] + " " + masuveMeasur2[m][j][3]);
						}
					
				}
				
						
				
					 fl =true;
				
				for (int j = 0; j < masuveMeasur[m].length; j++) {
					if (masuveMeasur[m][j][0] != null) {
						if(fl) {
							System.out.println();
							System.out.println(" Има го в "+ fileName[i]+" Изм СИЧ,но го няма в ДОЗИ");
							fl = false;
							}
						System.out.println(j + " - " + masuveMeasur[m][j][0] + " " + masuveMeasur[m][j][1] + " "
								+ masuveMeasur[m][j][2] + " " + masuveMeasur[m][j][3]);
					}
				
			}


				 fl =true;
			
				for (int j = 0; j < masiveDoze[m].length; j++) {
					if (masiveDoze[m][j][0] != null) {
						if(fl) {
							System.out.println();
							System.out.println(" Има го в "+ fileName[i]+" ДОЗИ,но го няма в Изм СИЧ");
							fl = false;
							}
						System.out.println(j + " - " + masiveDoze[m][j][0] + " " + masiveDoze[m][j][1]);
					}
				
			}
			}	
			
		}	
	
		
	}

	@SuppressWarnings("null")
	private static String[][][] masiveDoze(Workbook workbook) {
		String EGN = "", doze = "";

		Cell cell_EGN, cell_Doze;

		Sheet sheet0 = workbook.getSheetAt(0);

		String[][][] masiveStr = new String[12][500][2];
		int[] maxindexMonth = new int[12];
		for (int i = 0; i < maxindexMonth.length; i++) {
			maxindexMonth[i] = 0;
		}
		for (int row = 5; row <= sheet0.getLastRowNum(); row++) {

			if (sheet0.getRow(row) != null) {

				cell_EGN = sheet0.getRow(row).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell_EGN);
					if (EGN.contains("*"))
						EGN = EGN.substring(0, EGN.length() - 1);

					for (int mont = 0; mont < 12; mont++) {
						cell_Doze = sheet0.getRow(row).getCell(mont + 77);
						doze = ReadExcelFileWBC.getStringfromCell(cell_Doze);
//						System.out.println(mont + " " + maxindexMonth[mont] + " " + EGN);
						if (!doze.isEmpty()) {
							masiveStr[mont][maxindexMonth[mont]][0] = EGN;
							masiveStr[mont][maxindexMonth[mont]][1] = doze;
							maxindexMonth[mont]++;
						}
					}
				}
			}
		}

		

		return masiveStr;
	}

	private static String[][][] masuveMeasur(SimpleDateFormat sdfrmt, Workbook workbook) {
		Date date;
		int mont;
		String EGN = "", strDate = "", doze = "", lab = "";

		Cell cell_EGN, cell_date, cell_Doze, cell_Lab;

		String[][][] masiveStr0 = new String[12][500][4];
		Sheet sheet1 = workbook.getSheetAt(1);
		int[] maxindexMonth = new int[12];
		for (int i = 0; i < maxindexMonth.length; i++) {
			maxindexMonth[i] = 0;
		}
		for (int row0 = 5; row0 <= sheet1.getLastRowNum(); row0++) {

			if (sheet1.getRow(row0) != null) {

				cell_EGN = sheet1.getRow(row0).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell_EGN);
					if (EGN.contains("*"))
						EGN = EGN.substring(0, EGN.length() - 1);

					int k = 7;
					cell_date = sheet1.getRow(row0).getCell(k);
					k++;
					cell_Lab = sheet1.getRow(row0).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell_date) && ReadExcelFileWBC.CellNOEmpty(cell_Lab)) {

						
						date = ReadExcelFileWBC.readCellToDate(cell_date);
						strDate = sdfrmt.format(date);
						
						lab = ReadExcelFileWBC.getStringfromCell(cell_Lab);

						mont = date.getMonth();

						k = k + 17;
						cell_Doze = sheet1.getRow(row0).getCell(k);
						doze = ReadExcelFileWBC.getStringfromCell(cell_Doze);

						masiveStr0[mont][maxindexMonth[mont]][0] = EGN;
						masiveStr0[mont][maxindexMonth[mont]][1] = doze;
						masiveStr0[mont][maxindexMonth[mont]][2] = strDate;
						masiveStr0[mont][maxindexMonth[mont]][3] = lab;

						maxindexMonth[mont]++;

						if (k > 253) {
							k = 6;
							sheet1 = workbook.getSheetAt(2);
						}

						k++;
						cell_date = sheet1.getRow(row0).getCell(k);
						k++;
						cell_Lab = sheet1.getRow(row0).getCell(k);

					}
				}

			}

		}

	

		return masiveStr0;
	}

	private static String[][][] MasiveFromMonth(SimpleDateFormat sdfrmt, Workbook workbookMont) {
		Date date;
		String EGN;
		String strDate;
		String doze;
		String lab;
		Cell cell_EGN;
		Cell cell_date;
		Cell cell_Doze;
		Cell cell_Lab;

		String[][][] masiveStrMonth = new String[12][500][4];

		for (int m = 0; m < 12; m++) {

			Sheet sheetMont = workbookMont.getSheetAt(m);
			int row = 0;
			for (int l = 6; l <= sheetMont.getLastRowNum(); l++) {

				if (sheetMont.getRow(l) != null) {
					cell_EGN = sheetMont.getRow(l).getCell(3);
					if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
						EGN = ReadExcelFileWBC.getStringfromCell(cell_EGN);

						cell_Doze = sheetMont.getRow(l).getCell(5);
						doze = ReadExcelFileWBC.getStringfromCell(cell_Doze);

						cell_date = sheetMont.getRow(l).getCell(6);
						date = ReadExcelFileWBC.readCellToDate(cell_date);
						strDate = sdfrmt.format(date);

						cell_Lab = sheetMont.getRow(l).getCell(8);
						lab = ReadExcelFileWBC.getStringfromCell(cell_Lab);

						masiveStrMonth[m][row][0] = EGN;
						masiveStrMonth[m][row][1] = doze;
						masiveStrMonth[m][row][2] = strDate;
						masiveStrMonth[m][row][3] = lab;

						row++;
					}
				}
			}
		}
	
		return masiveStrMonth;
	}

}