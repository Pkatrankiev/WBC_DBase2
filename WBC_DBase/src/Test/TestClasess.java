package Test;

import java.io.File;
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
		switch (IndexLab) {
		case 1: {
			listWorkplace = WorkplaceDAO.getAllValueWorkplaceInSICH1();			
		}
		break;
		case 2: {
					
			listWorkplace = WorkplaceDAO.getAllValueWorkplaceInSICH2();			
				}
		break;
		case 3: {
			listWorkplace = WorkplaceDAO.getAllValueWorkplaceInSICH3();				
		}
		break;
		
		}
		
		
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