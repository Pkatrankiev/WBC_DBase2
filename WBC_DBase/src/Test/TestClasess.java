package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadResultFromReport;
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

	static void createCellComment(String egn, String commentText ) throws FileNotFoundException {
		String filePath[] = {
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig2"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig2") };
		String pathFile = filePath[1];
		FileInputStream inputStream;
		HSSFWorkbook workbook = null;
		try {
						
			inputStream = new FileInputStream(pathFile);
			 workbook = new HSSFWorkbook(inputStream);
			 
		
		int rowPerson = searchRowPersonInWorkbook( workbook,  egn);
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
	
			HSSFClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5);
			
			
			
			
			for (int i = 0; i < 4; i++) {

				cell = workbook.getSheetAt(i).getRow(rowPerson).getCell(6);
				try {
					// try to get the cell comment
					HSSFComment comment = cell.getCellComment();
					
					if (comment == null) {
						
						
					     createdNewComment(richTextString, cell, anchor);
						

					} else {
						richTextString = updateComment(authorText, commentString, boldFont, commentFont, creationHelper,
								comment);
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
		
		
		} catch (OldExcelFormatException |IOException e1) {
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
	
	public static void newComments33() throws IOException  {

	      HSSFWorkbook wb = new HSSFWorkbook();
	      HSSFSheet sheet = wb.createSheet("Cell comments in POI HSSF");


	      HSSFPatriarch patr = sheet.createDrawingPatriarch();
	      HSSFCell cell1 = sheet.createRow(3).createCell((short)1);
	      cell1.setCellValue(new HSSFRichTextString("Hello, World"));

	      //anchor defines size and position of the comment in worksheet
	      HSSFComment comment1 = patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5));
	      comment1.setString(new HSSFRichTextString("FirstComments"));
	      cell1.setCellComment(comment1);
	      System.out.println("Cell comments: "+cell1.getCellComment().getString());

	      patr = sheet.createDrawingPatriarch();
	      comment1 = patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5));
	      //HSSFComment comment2=patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5));
	      HSSFCell cell2 = sheet.createRow(6).createCell((short)1);
	      cell2.setCellValue(36.6);
	      comment1.setString(new HSSFRichTextString("second commetns"));
	      cell2.setCellComment(comment1);
	      System.out.println("Cell comments: "+cell2.getCellComment().getString());

	      patr = sheet.createDrawingPatriarch();
	      comment1 = patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5));
	      //HSSFComment comment3=patr.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5));
	      cell2 = sheet.createRow(10).createCell((short)1);
	      cell2.setCellValue(150);
	      comment1.setString(new HSSFRichTextString("Third commetns"));
	      cell2.setCellComment(comment1);
	      System.out.println("Cell comments: "+cell2.getCellComment().getString());

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
			System.out.println(firstOtdelName+" - "+workplace.getOtdel());
			if(firstOtdelName.equals(workplace.getOtdel())) {
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
			while (cell!=null) {
				listStr.add(cell.getStringCellValue().trim());
				i++;
				cell = sheet.getRow(0).getCell(i);
		}
		}	
	
		for (Workplace workplace : listWorkplace) {

			for (Iterator iterator = listStr.iterator(); iterator.hasNext();) {
				String strOtdelFromExcell = (String) iterator.next();

				if (strOtdelFromExcell.equals(workplace.getOtdel()) || strOtdelFromExcell.equals(workplace.getSecondOtdelName())) {
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
		if((list.size()+listPer.size())<1) {
			WorkplaceDAO.deleteValueWorkplace(workpl);
		}


	}
	
	}
	
	
	
	static void ChengeWorkplacefromSpiusakPrilog() {
	
	List<Integer> listErrorID = getAllValueSpisak_Prilogenia();
	List<String> listStrErrorID = new ArrayList<>();
	List<Workplace> listWorkplace = new ArrayList<>();
	List<Spisak_Prilogenia> listSpisak_Prilogenia = new ArrayList<>();

		System.out.println("listErrorID = "+listErrorID.size());
		for (int id : listErrorID) {
			listStrErrorID.add(getValueSpisak_PrilogeniaByID( id).getWorkplace().getOtdel());
		}
		System.out.println("listStrErrorID = "+listStrErrorID.size());
		for (String string : listStrErrorID) {
			listWorkplace.add(WorkplaceDAO.getValueWorkplaceByObject("Otdel", string).get(0));
		}
		System.out.println("listWorkplace = "+listWorkplace.size());
		int i = 0;
		for (int id : listErrorID) {
			
			Spisak_Prilogenia  spisPril = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID( id);
			
			spisPril.setWorkplace(listWorkplace.get(i));
			System.out.println(i+"  -> "+listWorkplace.get(i).getOtdel());
			Spisak_PrilogeniaDAO.updateValueSpisak_Prilogenia(spisPril);
			
			i++;
		}
		int k =0;
		for (int id : listErrorID) {
			System.out.print (Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID( id).getWorkplace().getOtdel()+" <-> "+listStrErrorID.get(k));
			if(Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID( id).getWorkplace().getOtdel().equals(listStrErrorID.get(k))) {
				System.out.println("  -> ok");
			};
			k++;
		}
		
	}
	
	
	public static List<Integer> getAllValueSpisak_Prilogenia() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Spisak_Prilogenia";
		List<Integer>  list = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				
				Workplace wp = WorkplaceDAO.getValueWorkplaceByID(result.getInt("Workplace_ID"));
				if(wp==null) {
					list.add(result.getInt("Spisak_Prilogenia_ID"))	;
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
		if(list.size()>0) {
		return list.get(0);
		}else {
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
				resultObject.setFirmName (result.getString("FirmName"));
				resultObject.setOtdel(result.getString("Otdel"));
				resultObject.setSecondOtdelName(result.getString("SecondOtdelName"));
			
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
			
		}
		
//		if(list.size()<=0) return null;
		return list.get(0);
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
		listWorkplace = WorkplaceDAO.getAllValueWorkplaceByLab(laborat)	;	

		
		
		String filePathMont[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig") };

		Workbook workbookMont[] = { ReadExcelFileWBC.openExcelFile(filePathMont[0]),
				ReadExcelFileWBC.openExcelFile(filePathMont[1]) };
		
		String otdel,name,EGN, kode, doze, data, lab;
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
						lab ="";
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
				if (masiveStrMonth[m][k][2]!=null && !masiveStrMonth[m][k][6].equals(IndexLab+"")) {
					for (Workplace workplace : listWorkplace) {
//						System.out.println(workplace.getOtdel()+" - "+(masiveStrMonth[m][k][0]));
						if(workplace.getOtdel().equals(masiveStrMonth[m][k][0])) {
							listIndex.add(masiveStrMonth[m][k][0]+"@"+masiveStrMonth[m][k][1]+"@"
									+masiveStrMonth[m][k][2]+"@"+masiveStrMonth[m][k][3]+"@"
									+masiveStrMonth[m][k][4]+"@"+masiveStrMonth[m][k][5]+"@"+masiveStrMonth[m][k][6]);
						}
					}	
				}
			}
		}
		}
		int count=0;
		String[][] masive = new String[listIndex.size()][7];
		for (String string : listIndex) {
			String[] ms = string.split("@",7);
			masive[count] = ms;
			count++;
		}
		return masive;
		
		
		}
		
	

	
	
	
	}