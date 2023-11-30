package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hsqldb.lib.FileUtil;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadMeasuringFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadResultFromReport;
import Aplication.ReadResultsWBCFromExcelFile;
import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import Aplication.UpdateBDataFromExcellFiles;
import BasiClassDAO.ActualExcellFilesDAO;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.KodeGenerate;
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
import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonExcellClass;
import PersonReference.PersonReferenceExportToExcell;
import ReferenceMeasuringLab.ReferenceMeasuringLabMetods;
import SaveToExcellFile.SaveToPersonelORExternalFile;

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

	static List<List<String>> checkTwoExcelFiles(String PathTo1File, String PathTo2File) {

		List<List<String>> listAllRowString = new ArrayList<>();
		List<List<Integer>> listAllRowInteger = new ArrayList<>();
		FileInputStream inputStream1;
		FileInputStream inputStream2;
		try {
			inputStream1 = new FileInputStream(PathTo1File);
			try (Workbook workbook1 = new HSSFWorkbook(inputStream1)) {
				inputStream2 = new FileInputStream(PathTo2File);
				try (Workbook workbook2 = new HSSFWorkbook(inputStream2)) {
					int lastRow1 = workbook1.getSheetAt(0).getLastRowNum();
					int lastRow2 = workbook2.getSheetAt(0).getLastRowNum();
					Sheet worksheet1;
					Sheet worksheet2;

					Row sourceRow1;
					Row sourceRow2;
					String str1 = "";
					String str2 = "";
					String str = "";
					if (lastRow1 == lastRow2) {

						for (int i = 0; i < 4; i++) {
							List<String> listRow = new ArrayList<>();
							worksheet1 = workbook1.getSheetAt(i);
							worksheet2 = workbook2.getSheetAt(i);

							for (int j = 0; j < lastRow1; j++) {

								sourceRow1 = worksheet1.getRow(j);
								sourceRow2 = worksheet2.getRow(j);

								if (sourceRow1 != null && sourceRow2 != null) {

									for (int k = 0; k < 256; k++) {

										str = ReadExcelFileWBC.getStringfromCell(sourceRow1.getCell(k));
										if (!str.isEmpty()) {
											str1 += str + " | ";
										}

										str = ReadExcelFileWBC.getStringfromCell(sourceRow2.getCell(k));
										if (!str.isEmpty()) {
											str2 += str + " | ";
										}
									}
									if (!str1.equals(str2)) {
										listRow.add(j + ": 1 - " + str1 + ": 2 - " + str2);
									}
									str1 = "";
									str2 = "";
								}
							}
							listAllRowString.add(listRow);
							
						}
						
						
						int l = 1;
						for (List<String> listInt : listAllRowString) {
							if (listInt.size() > 0) {
								System.out.println("Razliki w Sheet " + l);
								for (String integer : listInt) {
									String[] rowStr = integer.split(":");
									System.out.println("row " + rowStr[0]);
									System.out.println("fail " + rowStr[1]);
									System.out.println("fail " + rowStr[2]);

								}
							}
							l++;
						}
						
						
					} else {

						System.out.println("Ima nowi redowe file1 = " + lastRow1 + "  file2 = " + lastRow2);
						return null;
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listAllRowString;

	}

	static void movePersonInWorkplaceArea(int endRowOtdel, String egn) {

		String pathFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
		boolean isNewData = true;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(pathFile);
			Workbook workbook = new HSSFWorkbook(inputStream);

			Person person = PersonDAO.getValuePersonByEGN(egn);
			int row = 0;
			int lastRow;

			System.out.println(person.getEgn());
			String[] kode = PersonelManegementMethods.getKodeStatusByPersonFromDBase(person);

			for (int i = 0; i < 4; i++) {
				Sheet worksheet = workbook.getSheetAt(i);

				lastRow = worksheet.getLastRowNum();
				worksheet.shiftRows(endRowOtdel, lastRow, 1);
				worksheet.createRow(endRowOtdel);

				Row sourceRow = worksheet.getRow(row);

				Row newRow = worksheet.getRow(endRowOtdel);

				boolean withValues = false;
				if (isNewData) {
					sourceRow = worksheet.getRow(endRowOtdel - 1);

				} else {
					withValues = true;
				}
				SaveToPersonelORExternalFile.CopyValueFromSourseRowToNewRow(worksheet, sourceRow, newRow, withValues);
				newRow.getCell(0).setCellValue(kode[0]);
				newRow.getCell(1).setCellValue(kode[1]);
				newRow.getCell(2).setCellValue(kode[2]);
				newRow.getCell(3).setCellValue(kode[3]);
				newRow.getCell(4).setCellValue(kode[4]);
				newRow.getCell(5).setCellValue(person.getEgn());
				newRow.getCell(6).setCellValue(
						person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName());
			}

			FileOutputStream outputStream = new FileOutputStream(pathFile);
			workbook.write(outputStream);

			workbook.close();

			outputStream.flush();
			outputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list1, null, "");

		UpdateBDataFromExcellFiles.updataFromGodExcelFile();

		AplicationMetods.testGetListPersonSatatusByPersonAndDateAfterDateSet();

		JFileChooser chooiser = new JFileChooser();
		chooiser.setMultiSelectionEnabled(true);
		chooiser.showOpenDialog(null);
		File[] files = chooiser.getSelectedFiles();
		System.out.println(files.length);

		List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles(files);
		System.out.println(list.size());
		ReadResultFromReport.PrintListReportMeasurClass(list);

	}

	@SuppressWarnings("unused")
	public static void testMeasuringToResultWCB() {
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
//				if (measur.getDoze() == 0.0) {
//					System.out.println("--->>> 000 " + measur.getMeasuring_ID());
//					listresulterror.add(resultsWBC);
//				}
			}
		}

		for (ResultsWBC resultsWBC : listresulterror) {
			System.out.println(resultsWBC.getResultsWBC_ID());
		}
	}

	static void createCellComment(String egn, String commentText) throws FileNotFoundException {
		String filePath[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig2"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig2") };
		String pathFile = filePath[1];
		FileInputStream inputStream;
		HSSFWorkbook workbook = null;
		try {

			inputStream = new FileInputStream(pathFile);
			workbook = new HSSFWorkbook(inputStream);

			int rowPerson = searchRowPersonInWorkbook(workbook, egn);
			System.out.println(rowPerson);
			if (commentText.length() > 0) {

				String authorText = "katrankjievvv";
				authorText = authorText + ":";
				String commentString = authorText + "\n" + commentText;

				HSSFFont boldFont = workbook.createFont();
				boldFont.setFontName("Tahoma");
				boldFont.setFontHeightInPoints((short) 9);
				boldFont.setBold(true);

				HSSFFont commentFont = workbook.createFont();
				commentFont.setFontName("Tahoma");
				commentFont.setFontHeightInPoints((short) 9);
				commentFont.setBold(false);
				HSSFCreationHelper creationHelper = workbook.getCreationHelper();

				HSSFRichTextString richTextString = creationHelper.createRichTextString(commentString);
				richTextString.applyFont(commentFont);
				richTextString.applyFont(0, authorText.length(), boldFont);
				HSSFCell cell;

				HSSFClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short) 1, 1, (short) 6, 5);

				for (int i = 0; i < 4; i++) {

					cell = workbook.getSheetAt(i).getRow(rowPerson).getCell(6);
					try {
						// try to get the cell comment
						HSSFComment comment = cell.getCellComment();

						if (comment == null) {

							createdNewComment(richTextString, cell, anchor);

						} else {
							richTextString = updateComment(authorText, commentString, boldFont, commentFont,
									creationHelper, comment);
						}

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}

			FileOutputStream outputStream = new FileOutputStream(pathFile);
			workbook.write(outputStream);

			workbook.close();

			outputStream.flush();
			outputStream.close();

		} catch (OldExcelFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static void createdNewComment(HSSFRichTextString richTextString, HSSFCell cell, HSSFClientAnchor anchor) {
		// create a new comment
		System.out.println(cell.getStringCellValue());
		HSSFPatriarch drawing_master = cell.getSheet().createDrawingPatriarch();
		HSSFComment comment1 = (HSSFComment) drawing_master.createCellComment(anchor);
		comment1.setString(richTextString);
		cell.setCellComment(comment1);
	}

	public static void newComments33() throws IOException {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Cell comments in POI HSSF");

		HSSFPatriarch patr = sheet.createDrawingPatriarch();
		HSSFCell cell1 = sheet.createRow(3).createCell((short) 1);
		cell1.setCellValue(new HSSFRichTextString("Hello, World"));

		// anchor defines size and position of the comment in worksheet
		HSSFComment comment1 = patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short) 1, 1, (short) 6, 5));
		comment1.setString(new HSSFRichTextString("FirstComments"));
		cell1.setCellComment(comment1);
		System.out.println("Cell comments: " + cell1.getCellComment().getString());

		patr = sheet.createDrawingPatriarch();
		comment1 = patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short) 1, 1, (short) 6, 5));
		// HSSFComment comment2=patr.createComment(new HSSFClientAnchor(100, 100, 100,
		// 100, (short)1, 1, (short) 6, 5));
		HSSFCell cell2 = sheet.createRow(6).createCell((short) 1);
		cell2.setCellValue(36.6);
		comment1.setString(new HSSFRichTextString("second commetns"));
		cell2.setCellComment(comment1);
		System.out.println("Cell comments: " + cell2.getCellComment().getString());

		patr = sheet.createDrawingPatriarch();
		comment1 = patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short) 1, 1, (short) 6, 5));
		// HSSFComment comment3=patr.createComment(new HSSFClientAnchor(100, 100, 100,
		// 100, (short)1, 1, (short) 6, 5));
		cell2 = sheet.createRow(10).createCell((short) 1);
		cell2.setCellValue(150);
		comment1.setString(new HSSFRichTextString("Third commetns"));
		cell2.setCellComment(comment1);
		System.out.println("Cell comments: " + cell2.getCellComment().getString());

		FileOutputStream out = new FileOutputStream("C:/Documents and Settings/saravanan/Desktop/cellcomments.xls");
		wb.write(out);
		out.close();
	}

	private static int searchRowPersonInWorkbook(HSSFWorkbook workbook, String egn) {
		HSSFSheet sheetSpPr = workbook.getSheetAt(3);
		int maxRow = sheetSpPr.getLastRowNum();
		int row = 5;
		HSSFCell cell;
		String str_cell = "";

		while (row < maxRow) {
			if (sheetSpPr.getRow(row) != null) {

				cell = sheetSpPr.getRow(row).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					str_cell = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if (str_cell.equals(egn)) {
						return row;
					}
				}
			}
			row++;
		}
		return 0;
	}

	private static HSSFRichTextString updateComment(String authorText, String commentString, HSSFFont boldFont,
			HSSFFont commentFont, HSSFCreationHelper creationHelper, HSSFComment comment) {
		HSSFRichTextString richTextString;
		// apply author and text
//		  comment.setAuthor(authorText);
		HSSFRichTextString oldrichTextString = comment.getString();
		String oldTextStr = oldrichTextString.getString();
		int indexOldTextStr = oldTextStr.length();
		int index1Autor = oldTextStr.indexOf(":");

		String str = oldTextStr + "\n " + commentString;
		int index2Autor = str.lastIndexOf(":");
		richTextString = creationHelper.createRichTextString(str);
		int AllOldTextStr = str.length();
		System.out.println(index1Autor + " " + indexOldTextStr + " " + index2Autor + " " + AllOldTextStr);

		richTextString.applyFont(commentFont);
		richTextString.applyFont(0, index1Autor, boldFont);
		richTextString.applyFont(indexOldTextStr, index2Autor, boldFont);

		comment.setAuthor(authorText);
		comment.setString(richTextString);
		return richTextString;
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

	public static void checkWorkspaceFordublicate() {

		List<Workplace> list = WorkplaceDAO.getValueWorkplaceSortByColumnName("Otdel");
		String firstOtdelName = "";
		Workplace firstWorkplace = list.get(0);
		for (Workplace workplace : list) {
			System.out.println(firstOtdelName + " - " + workplace.getOtdel());
			if (firstOtdelName.equals(workplace.getOtdel())) {
				ChengeWorkplacefromObgects(firstWorkplace, workplace);
			}

			firstWorkplace = workplace;
			firstOtdelName = firstWorkplace.getOtdel();
		}

	}

	public static void checkWorkspaceForActual() {
		List<Workplace> listWorkplace = WorkplaceDAO.getAllValueWorkplace();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		List<String> listStr = new ArrayList<>();
		for (String filePath : excellFiles) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath);
			Sheet sheet = workbook.getSheetAt(0);
			Cell cell;

			int i = 0;
			cell = sheet.getRow(0).getCell(i);
			while (cell != null) {
				listStr.add(cell.getStringCellValue().trim());
				i++;
				cell = sheet.getRow(0).getCell(i);
			}
		}

		for (Workplace workplace : listWorkplace) {

			for (Iterator iterator = listStr.iterator(); iterator.hasNext();) {
				String strOtdelFromExcell = (String) iterator.next();

				if (strOtdelFromExcell.equals(workplace.getOtdel())
						|| strOtdelFromExcell.equals(workplace.getSecondOtdelName())) {
					workplace.setActual(true);
					WorkplaceDAO.updateValueWorkplace(workplace);
					iterator.remove();
				}
			}
		}

	}

	public static void checkWorkspaceForDelete() {

		List<Workplace> listWorkplace = WorkplaceDAO.getValueWorkplaceByObject("SecondOtdelName", "autAll");
		System.out.println(listWorkplace.size());
		for (Workplace workpl : listWorkplace) {
			List<KodeGenerate> list = KodeGenerateDAO.getValueKodeGenerateByObject("Workplace_ID", workpl);
			System.out.println(list.size());
			List<PersonStatus> listPer = PersonStatusDAO.getValuePersonStatusByWorkplace(workpl);
			System.out.println(listPer.size());
			if ((list.size() + listPer.size()) < 1) {
				WorkplaceDAO.deleteValueWorkplace(workpl);
			}

		}

	}

	static void ChengeWorkplacefromSpiusakPrilog() {

		List<Integer> listErrorID = getAllValueSpisak_Prilogenia();
		List<String> listStrErrorID = new ArrayList<>();
		List<Workplace> listWorkplace = new ArrayList<>();
		List<Spisak_Prilogenia> listSpisak_Prilogenia = new ArrayList<>();

		System.out.println("listErrorID = " + listErrorID.size());
		for (int id : listErrorID) {
			listStrErrorID.add(getValueSpisak_PrilogeniaByID(id).getWorkplace().getOtdel());
		}
		System.out.println("listStrErrorID = " + listStrErrorID.size());
		for (String string : listStrErrorID) {
			listWorkplace.add(WorkplaceDAO.getValueWorkplaceByObject("Otdel", string).get(0));
		}
		System.out.println("listWorkplace = " + listWorkplace.size());
		int i = 0;
		for (int id : listErrorID) {

			Spisak_Prilogenia spisPril = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(id);

			spisPril.setWorkplace(listWorkplace.get(i));
			System.out.println(i + "  -> " + listWorkplace.get(i).getOtdel());
			Spisak_PrilogeniaDAO.updateValueSpisak_Prilogenia(spisPril);

			i++;
		}
		int k = 0;
		for (int id : listErrorID) {
			System.out.print(Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(id).getWorkplace().getOtdel() + " <-> "
					+ listStrErrorID.get(k));
			if (Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(id).getWorkplace().getOtdel()
					.equals(listStrErrorID.get(k))) {
				System.out.println("  -> ok");
			}
			;
			k++;
		}

	}

	public static List<Integer> getAllValueSpisak_Prilogenia() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia";
		List<Integer> list = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {

				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				if (wp == null) {
					list.add(result.getInt("Spisak_Prilogenia_ID"));
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

	public static Spisak_Prilogenia getValueSpisak_PrilogeniaByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia  where Spisak_Prilogenia_ID = ? LIMIT 1";

		List<Spisak_Prilogenia> list = new ArrayList<Spisak_Prilogenia>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Spisak_Prilogenia resultObject = new Spisak_Prilogenia();

				resultObject.setSpisak_Prilogenia_ID(result.getInt("Spisak_Prilogenia_ID"));
				resultObject.setFormulyarName(result.getString("FormulyarName"));
				resultObject.setYear(result.getString("Year"));
				resultObject.setStartDate(result.getDate("StartDate"));
				resultObject.setEndDate(result.getDate("EndDate"));
				Workplace wp = getValueWorkplaceByID(result.getInt("Workplace_ID"));
				resultObject.setWorkplace(wp);
				resultObject.setZabelejka(result.getString("Zabelejka"));
				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public static boolean OptionDialog(String mesage) {
		String[] options = { "Skip", "Update" };
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);

		if (x > 0) {
			return true;
		}
		return false;
	}

	public static Workplace getValueWorkplaceByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Workplace_old  where Workplace_ID = ? LIMIT 1";

		List<Workplace> list = new ArrayList<Workplace>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Workplace resultObject = new Workplace();
				resultObject.setId_Workplace(result.getInt("Workplace_ID"));
				resultObject.setFirmName(result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));

				list.add(resultObject);
			}

			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile(e);

		}

//		if(list.size()<=0) return null;
		return list.get(0);
	}

	public static void updateFromExcel() {
		String textIcon = "<html><center>Update " + " (" + 55 + "/7)" + "<br>" + "rrrrrrrr ";
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String pathFile : excellFiles) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			List<Measuring> listMeasuring = null;
//			try {
			listMeasuring = ReadMeasuringFromExcelFile.generateListFromMeasuringFromExcelFile(pathFile,
					new ActionIcone("            "), textIcon);
			for (Measuring string : listMeasuring) {
				System.out.println(string.getPerson().getEgn() + " " + string.getDoze());
			}
//				ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
			System.out.println("--> " + listMeasuring.size());
//			} catch (Exception e) {
//				save = OptionDialog(errorText);
//			
//			}	

//		boolean save = true;
//		// read and set ResultsWBC
//		List<ResultsWBC> listResultsWBC = null;
//		
//		
//		
////		try {
//			listResultsWBC = ReadResultsWBCFromExcelFile
//					.generateListFromResultsWBCFromExcelFile(pathFile, new ActionIcone("            "), textIcon);
////			ReadResultsWBCFromExcelFile.ListResultsWBCToBData(listResultsWBC);
//			System.out.println("--> " + listResultsWBC.size());
////		} catch (Exception e) {
////			e.printStackTrace();
////			OptionDialog("errorr");
////			save = false;
////		}
////		if (save) {
////			ReadResultsWBCFromExcelFile.setListResultsWBCToBData(listResultsWBC,null, "");
////			System.out.println("Save set ResultsWBC " + firmName);
////		}
		}
	}

	public static boolean isFileClosed() {

		String fileName = "d:\\PERSONNEL.xls";
		File file = new File(fileName);
		BufferedReader reader;
		Process plsof;
		try {
			plsof = new ProcessBuilder(new String[] { "lsof", "|", "grep", file.getAbsolutePath() }).start();
			reader = new BufferedReader(new InputStreamReader(plsof.getInputStream()));
			reader.close();
			plsof.destroy();
			String line;
			System.out.println("try");
			while ((line = reader.readLine()) != null) {
				System.out.println("l");
				if (line.contains(file.getAbsolutePath())) {
					reader.close();
					plsof.destroy();
					return false;
				}
			}
		} catch (Exception ex) {
			System.out.println("exseptrion");
		}

		return true;
	}

	public static void CheckMontToBDate() throws ParseException {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = sdfrmt.parse("01.11.2023");
		Date dateEnd = sdfrmt.parse("30.11.2023");
		List<Measuring> listMeasyrByMounth = MeasuringDAO.getValueMeasuringByStartdate_EndDate(dateStart, dateEnd);
		for (Measuring measuring : listMeasyrByMounth) {
			System.out.println(measuring.getPerson().getEgn() + " " + sdfrmt.format(measuring.getDate()) + " "
					+ measuring.getReportFileName() + " " + measuring.getExcelPosition());

			if (measuring.getExcelPosition().length() > 25) {

			}

		}
	}

	static void RemoveWorkplace_54_101_FromPersonStatus(int idWorkplace, String egn) {

		Workplace workplace54 = WorkplaceDAO.getValueWorkplaceByID(idWorkplace);
		List<PersonStatus> listPersonStatus54 = PersonStatusDAO.getValuePersonStatusByWorkplace_Year(workplace54,
				"2023");
		PersonStatus lastPersonStat = new PersonStatus();
		PersonStatus drPersonStat = new PersonStatus();
		System.out.println("listPersonStatus54_1 = " + listPersonStatus54.size());

		int k = 0;
		for (PersonStatus perStat : listPersonStatus54) {
			System.out.println(k + "-" + perStat.getPerson().getEgn());
//			k++;
//			List<PersonStatus> list = PersonStatusDAO.getValuePersonStatusByObjectSortByColumnName("Person_ID", perStat.getPerson(),
//					"DateSet");
//			List<PersonStatus> sortedReversPeStatList = list.stream()
//					.sorted(Comparator.comparing(PersonStatus::getPersonStatus_ID).reversed()).collect(Collectors.toList());
//			if (sortedReversPeStatList.size() > 2) {
//				lastPersonStat = sortedReversPeStatList.get(0);
//				drPersonStat = sortedReversPeStatList.get(1);
//			if(lastPersonStat.getWorkplace().getId_Workplace() == idWorkplace) {
//				if(!lastPersonStat.getZabelejka().isEmpty()) {
//					if(drPersonStat.getZabelejka().isEmpty()) {
//						drPersonStat.setZabelejka(lastPersonStat.getZabelejka());
//					}
//				}
//				PersonStatusDAO.deleteValuePersonStatus(lastPersonStat);	
//			}
//			}

			if (perStat.getPerson().getEgn().equals(egn)) {
				PersonStatusDAO.deleteValuePersonStatus(perStat);
			}

		}
		listPersonStatus54 = PersonStatusDAO.getValuePersonStatusByWorkplace_Year(workplace54, "2023");

		System.out.println("listPersonStatus54_2 = " + listPersonStatus54.size());

	}

	public static PersonStatus getLastPersonStatusByPerson(Person person) {
		List<PersonStatus> list = PersonStatusDAO.getValuePersonStatusByObjectSortByColumnName("Person_ID", person,
				"DateSet");
		List<PersonStatus> sortedReversPeStatList = list.stream()
				.sorted(Comparator.comparing(PersonStatus::getPersonStatus_ID).reversed()).collect(Collectors.toList());
		if (sortedReversPeStatList.size() > 0) {
			return sortedReversPeStatList.get(0);
		} else {
			return null;
		}
	}

	public static void MountlyreportMeasuring(int mount) {
		List<Laboratory> listLab = LaboratoryDAO.getAllValueLaboratory();
		int countlab = listLab.size();
		long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		String strMount = mount + "";

		int brMeasur = 0;
		int brNadMDA = 0;
		double doza = 0;
		double dozaMAX = 0;
		double dozaMIN = 0;
		double dozaSUM = 0;

		int allBrMeasur = 0;
		int allBrNadMDA = 0;
		double allDozaMAX = 0, allDozaMIN = 0, allDozaSUM = 0;

		int globBrMeasur[] = new int[countlab];
		int globBrNadMDA[] = new int[countlab];
		double globDozaMAX[] = new double[countlab];
		;
		double globDozaMIN[] = new double[countlab];
		;
		double globDozaSUM[] = new double[countlab];

		int index = (countlab * 5) + 6;
		System.out.println(index);

		List<Object[]> listMasive = new ArrayList<Object[]>();

		if (mount < 10) {
			strMount = "0" + mount;
		}
		Date dateStart = null, dateEnd = null, date = null;
		try {
			dateStart = sdfrmt.parse("01." + strMount + "." + curentYear);
			dateEnd = sdfrmt.parse("31." + strMount + "." + curentYear);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		int indexMasive = 0;
		date = dateStart;
		do {
			Object[] masive = new Object[index];
			indexMasive = 0;
			masive[indexMasive] = sdfrmt.format(date);

			allDozaMAX = 0;
			allDozaMIN = 0;
			allDozaSUM = 0;
			allBrMeasur = 0;
			allBrNadMDA = 0;
			for (int i = 0; i < countlab; i++) {

				brNadMDA = 0;
				brMeasur = 0;
				doza = 0;
				dozaMAX = 0;
				dozaMIN = 0;
				dozaSUM = 0;

				List<Measuring> listmeasur = MeasuringDAO.getValueMeasuringByLab_Date(listLab.get(i).getLab_ID(), date);
				brMeasur = listmeasur.size();
				if (brMeasur > 0) {
					for (Measuring measuring : listmeasur) {

						doza = measuring.getDoze();
						if (doza > 0) {
							brNadMDA++;
							if (doza > 0.05) {
								dozaSUM += doza;
								allDozaSUM += doza;
								globDozaSUM[i] += doza;
							}

							if (doza > dozaMAX) {
								dozaMAX = doza;
							}
							if (doza > allDozaMAX) {
								allDozaMAX = doza;
							}
							if (doza > globDozaMAX[i]) {
								globDozaMAX[i] = doza;
							}

							if (doza < dozaMIN) {
								dozaMIN = doza;
							}
							if (doza < allDozaMIN) {
								allDozaMIN = doza;
							}

							if (doza < globDozaMIN[i]) {
								globDozaMIN[i] = doza;
							}

						}
					}
				}
				allBrMeasur += brMeasur;
				globBrMeasur[i] += brMeasur;

				allBrNadMDA += brNadMDA;
				globBrNadMDA[i] += brNadMDA;

				indexMasive++;
				masive[indexMasive] = brMeasur;
				indexMasive++;
				masive[indexMasive] = brNadMDA;
				indexMasive++;
				masive[indexMasive] = dozaMAX;
				indexMasive++;
				masive[indexMasive] = dozaMIN;
				indexMasive++;
				masive[indexMasive] = dozaSUM;
			}

			if (allBrMeasur > 0) {
				indexMasive++;
				masive[indexMasive] = allBrMeasur;
				indexMasive++;
				masive[indexMasive] = allBrNadMDA;
				indexMasive++;
				masive[indexMasive] = allDozaMAX;
				indexMasive++;
				masive[indexMasive] = allDozaMIN;
				indexMasive++;
				masive[indexMasive] = allDozaSUM;

				listMasive.add(masive);
			}
			date = new Date(date.getTime() + MILLIS_IN_A_DAY);

		} while (date.before(dateEnd));

		Object[] masive = new Object[index];
		indexMasive = 0;

		allBrMeasur = 0;
		allBrNadMDA = 0;
		allDozaMAX = 0;
		allDozaMIN = 0;
		allDozaSUM = 0;

		masive[0] = "Общо: ";
		for (int i = 0; i < countlab; i++) {

			indexMasive++;
			masive[indexMasive] = globBrMeasur[i];
			indexMasive++;
			masive[indexMasive] = globBrNadMDA[i];
			indexMasive++;
			masive[indexMasive] = globDozaMAX[i];
			indexMasive++;
			masive[indexMasive] = globDozaMIN[i];
			indexMasive++;
			masive[indexMasive] = globDozaSUM[i];

			allBrMeasur += globBrMeasur[i];
			allBrNadMDA += globBrNadMDA[i];
			if (globDozaSUM[i] > 0.05) {
				allDozaSUM += globDozaSUM[i];
			}
			if (globDozaMAX[i] > allDozaMAX) {
				allDozaMAX = globDozaMAX[i];
			}

			if (globDozaMIN[i] < allDozaMIN) {
				allDozaMIN = globDozaMIN[i];
			}

		}

		indexMasive++;
		masive[indexMasive] = allBrMeasur;
		indexMasive++;
		masive[indexMasive] = allBrNadMDA;
		indexMasive++;
		masive[indexMasive] = allDozaMAX;
		indexMasive++;
		masive[indexMasive] = allDozaMIN;
		indexMasive++;
		masive[indexMasive] = allDozaSUM;

		listMasive.add(masive);

		Object[][] allMasive = new Object[listMasive.size()][index];
		int k = 0;
		for (Object[] objects : listMasive) {

			for (int i = 0; i < objects.length; i++) {
				allMasive[k][i] = objects[i];
				System.out.print(objects[i] + "| ");
			}
			k++;
			System.out.println();
		}

	}

	private static void ChengeWorkplacefromObgects(Workplace firstWorkplace, Workplace workplace) {
		List<KodeGenerate> list = KodeGenerateDAO.getValueKodeGenerateByObject("Workplace_ID", workplace);

		for (KodeGenerate kodeGenerate : list) {
			kodeGenerate.setWorkplace(firstWorkplace);
			KodeGenerateDAO.updateValueKodeGenerate(kodeGenerate);
		}

		List<PersonStatus> listPer = PersonStatusDAO.getValuePersonStatusByWorkplace(workplace);
		for (PersonStatus perStat : listPer) {
			perStat.setWorkplace(firstWorkplace);
			PersonStatusDAO.updateValuePersonStatus(perStat);
		}
		workplace.setSecondOtdelName("autAll");
		WorkplaceDAO.updateValueWorkplace(workplace);
		System.out.println(workplace.getSecondOtdelName());
	}

	static String[][] MasiveFromMonthCheckMeasurLab(Laboratory laborat) {
		List<Workplace> listWorkplace = new ArrayList<>();
		int IndexLab = laborat.getLab_ID();
		listWorkplace = WorkplaceDAO.getAllValueWorkplaceByLab(laborat);

		String filePathMont[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig") };

		Workbook workbookMont[] = { ReadExcelFileWBC.openExcelFile(filePathMont[0]),
				ReadExcelFileWBC.openExcelFile(filePathMont[1]) };

		String otdel, name, EGN, kode, doze, data, lab;
		Cell cell;
		List<String> listIndex = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			String[][][] masiveStrMonth = new String[12][500][7];

			for (int m = 0; m < 12; m++) {

				Sheet sheetMont = workbookMont[i].getSheetAt(m);
				int row = 0;
				for (int l = 6; l <= sheetMont.getLastRowNum(); l++) {

					if (sheetMont.getRow(l) != null) {
						cell = sheetMont.getRow(l).getCell(3);
						if (ReadExcelFileWBC.CellNOEmpty(cell)) {
							EGN = ReadExcelFileWBC.getStringfromCell(cell);

							cell = sheetMont.getRow(l).getCell(1);
							otdel = ReadExcelFileWBC.getStringfromCell(cell);

							cell = sheetMont.getRow(l).getCell(2);
							name = ReadExcelFileWBC.getStringfromCell(cell);

							cell = sheetMont.getRow(l).getCell(4);
							kode = ReadExcelFileWBC.getStringfromCell(cell);

							cell = sheetMont.getRow(l).getCell(5);
							doze = ReadExcelFileWBC.getStringfromCell(cell);

							cell = sheetMont.getRow(l).getCell(6);
							data = ReadExcelFileWBC.getStringfromCell(cell);

							cell = sheetMont.getRow(l).getCell(9);
							lab = "";
							if (ReadExcelFileWBC.CellNOEmpty(cell)) {
								lab = ReadExcelFileWBC.getStringfromCell(cell);
							}

							masiveStrMonth[m][row][0] = otdel;
							masiveStrMonth[m][row][1] = name;
							masiveStrMonth[m][row][2] = EGN;
							masiveStrMonth[m][row][3] = kode;
							masiveStrMonth[m][row][4] = doze;
							masiveStrMonth[m][row][5] = data;
							masiveStrMonth[m][row][6] = lab;

							row++;
						}
					}
				}
			}

			for (int m = 0; m < 12; m++) {
				for (int k = 0; k < masiveStrMonth[m].length; k++) {
					if (masiveStrMonth[m][k][2] != null && !masiveStrMonth[m][k][6].equals(IndexLab + "")) {
						for (Workplace workplace : listWorkplace) {
//						System.out.println(workplace.getOtdel()+" - "+(masiveStrMonth[m][k][0]));
							if (workplace.getOtdel().equals(masiveStrMonth[m][k][0])) {
								listIndex.add(masiveStrMonth[m][k][0] + "@" + masiveStrMonth[m][k][1] + "@"
										+ masiveStrMonth[m][k][2] + "@" + masiveStrMonth[m][k][3] + "@"
										+ masiveStrMonth[m][k][4] + "@" + masiveStrMonth[m][k][5] + "@"
										+ masiveStrMonth[m][k][6]);
							}
						}
					}
				}
			}
		}
		int count = 0;
		String[][] masive = new String[listIndex.size()][7];
		for (String string : listIndex) {
			String[] ms = string.split("@", 7);
			masive[count] = ms;
			count++;
		}
		return masive;

	}

}