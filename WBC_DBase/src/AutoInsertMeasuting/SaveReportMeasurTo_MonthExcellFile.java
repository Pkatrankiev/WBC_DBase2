package AutoInsertMeasuting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.PersonStatusNew;

public class SaveReportMeasurTo_MonthExcellFile {

	public static void SaveListReportMeasurClassToMonthExcellFile(List<ReportMeasurClass> list,
			boolean forPersonalExcellFile) {

		String falseData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("falseData");
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String notSelectFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSelectFile");


		String filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig"); 
		String filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig"); 
				
				
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
			filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig_test"); 
			filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig_test");
		}
		

		String pathFile = "";
		if (forPersonalExcellFile) {
			pathFile = filePathMonthExternal;
		} else {
			pathFile = filePathMonthPersonel;

		}

		try {
//			System.out.println("pathFile " + pathFile);
			for (ReportMeasurClass reportMeasur : list) {
			FileInputStream inputStream = new FileInputStream(pathFile);
			Workbook workbook = new HSSFWorkbook(inputStream);

			
				if (reportMeasur.getToExcell()) {
//					System.out.println("EGN " + reportMeasur.getMeasur().getPerson().getEgn());
					SaveMeasurToMonthExcel(reportMeasur, workbook);
				}
			

			FileOutputStream fileOut = new FileOutputStream(pathFile);
			workbook.write(fileOut);
			fileOut.close();
			}
		} catch (FileNotFoundException | OldExcelFormatException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, fileIsOpen, falseData, JOptionPane.ERROR_MESSAGE);

		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, notSelectFile, falseData, JOptionPane.ERROR_MESSAGE);
		}

	
	}

	private static void SaveMeasurToMonthExcel(ReportMeasurClass reportMeasur, Workbook workbook) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

		String DateOfCurrentMeasurement = sdf.format(reportMeasur.getMeasur().getDate());

//		System.out.println("DateOfCurrentMeasurement " + DateOfCurrentMeasurement);

		int monthPage = SaveReportMeasurTo_PersonelORExternalExcelFile.getMonthFromDate(DateOfCurrentMeasurement);

//		System.out.println("monthPage " + monthPage);

		Sheet sheet = workbook.getSheetAt(monthPage);
		saveDataToMonthExcelFile(sheet, reportMeasur);

	}

	private static void saveDataToMonthExcelFile(Sheet sheet, ReportMeasurClass reportMeasur) {
		Cell cellEGN, cellDoze, cellDate;
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
		boolean flDublicate = false;
		String[] masiveDataForSaveMonthRow = generateMasiveDataForSaveMonthRow(reportMeasur);
		int row = 6;
		while (row < sheet.getLastRowNum() && !flDublicate) {
			

			cellEGN = SaveReportMeasurTo_PersonelORExternalExcelFile.getCell(sheet, row, 3);
			cellDoze = SaveReportMeasurTo_PersonelORExternalExcelFile.getCell(sheet, row, 5);
			cellDate = SaveReportMeasurTo_PersonelORExternalExcelFile.getCell(sheet, row, 6);
			String dozeStr = SaveReportMeasurTo_PersonelORExternalExcelFile.convertDozeCellToString(cellDoze);
			
			System.out.println("************************");
			System.out.println(SaveReportMeasurTo_PersonelORExternalExcelFile.getValueFromCellToString(cellEGN)+" -> "+(masiveDataForSaveMonthRow[2]));
			System.out.println(dozeStr+" -> "+(masiveDataForSaveMonthRow[4]));
			System.out.println(sdf1.format(ReadExcelFileWBC.readCellToDate(cellDate))+" -> "+(masiveDataForSaveMonthRow[5]));
			
				if (SaveReportMeasurTo_PersonelORExternalExcelFile.getValueFromCellToString(cellEGN).equals(masiveDataForSaveMonthRow[2])
						&& dozeStr.equals(masiveDataForSaveMonthRow[4])
						&& sdf1.format(ReadExcelFileWBC.readCellToDate(cellDate)).equals(masiveDataForSaveMonthRow[5])) {
					flDublicate = true;
				}
				
				if(SaveReportMeasurTo_PersonelORExternalExcelFile.getValueFromCellToString(cellEGN).trim().isEmpty()) {
					row = sheet.getLastRowNum();
				}
				row++;
		}

		System.out.println("flDublicate " + flDublicate);
		Row roww;
		boolean flNotDublicateData = SaveReportMeasurTo_PersonelORExternalExcelFile.getFlNotDublicateData();
		if (!flDublicate || flNotDublicateData) {
			
			Cell lastCell = getFirstCellFromLastRow(sheet);
			
			if (lastCell != null && lastCell.getRowIndex()>5) {
				
				roww = sheet.createRow(lastCell.getRowIndex() + 1);
				System.out.println("roww " + roww);
				Cell cell = roww.createCell(0,  CellType.NUMERIC);
				cell.setCellValue(lastCell.getNumericCellValue() + 1);
			} else {
				roww = sheet.createRow(6);
				roww.createCell(0).setCellValue(1);
			}
			for (int i = 0; i < 9; i++) {
				roww.createCell(i + 1).setCellValue(masiveDataForSaveMonthRow[i]);
				if (i == 2 || i == 8) {
					if (masiveDataForSaveMonthRow[i].substring(0, 1).equals("0")) {
						roww.createCell(i + 1).setCellValue(masiveDataForSaveMonthRow[i]);
					} else {
						roww.createCell(i + 1).setCellValue(Long.parseLong(masiveDataForSaveMonthRow[i]));
					}
				}
				if (i == 4) {
					double dozeMeasurDouble = 0 ;
					try {
					dozeMeasurDouble = Double.parseDouble(masiveDataForSaveMonthRow[i]);
					roww.createCell(i+1,  CellType.NUMERIC).setCellValue(dozeMeasurDouble);
					}catch (Exception e) {
						roww.createCell(i+1).setCellValue(masiveDataForSaveMonthRow[i]);
					}
					
				}
			}

		}
	}
	
	private static Cell getFirstCellFromLastRow(Sheet sheet) {
		Cell cell = null;
		for (int row = 6; row < sheet.getLastRowNum() + 1; row++) {
			cell = SaveReportMeasurTo_PersonelORExternalExcelFile.getCell(sheet, row, 0);
			if (SaveReportMeasurTo_PersonelORExternalExcelFile.getValueFromCellToString(cell).isEmpty()) {
				return SaveReportMeasurTo_PersonelORExternalExcelFile.getCell(sheet, row - 1, 0);
			}
		}
		return cell;
	}


	private static String[] generateMasiveDataForSaveMonthRow(ReportMeasurClass reportMeasur) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
		String[] masiveDataForSaveMonthRow = new String[10];
		String year = sdf.format(reportMeasur.getMeasur().getDate()).substring(6);
//		System.out.println("year " + year);
		Person person = reportMeasur.getMeasur().getPerson();
		masiveDataForSaveMonthRow[0] = getLastWorkplace(person);
		masiveDataForSaveMonthRow[1] = SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person);
		masiveDataForSaveMonthRow[2] = person.getEgn();
		masiveDataForSaveMonthRow[3] = getKodeStatus(person, 1, year);
		masiveDataForSaveMonthRow[4] = SaveReportMeasurTo_PersonelORExternalExcelFile.convertDozeToString(reportMeasur.getMeasur().getDoze(), reportMeasur.getMeasur().getTypeMeasur().getKodeType());
		masiveDataForSaveMonthRow[5] = sdf1.format(reportMeasur.getMeasur().getDate());
		masiveDataForSaveMonthRow[6] = getKodeStatus(person, 2, year);
		masiveDataForSaveMonthRow[7] = reportMeasur.getMeasur().getLab().getLab().toLowerCase();
		masiveDataForSaveMonthRow[8] = reportMeasur.getMeasur().getLab().getLab_ID() + "";
		masiveDataForSaveMonthRow[9] = reportMeasur.getMeasur().getTypeMeasur().getKodeType();
		for (int i = 0; i < masiveDataForSaveMonthRow.length; i++) {
//			System.out.println(i + " - " + masiveDataForSaveMonthRow[i]);

		}

		return masiveDataForSaveMonthRow;
	}

	private static String getKodeStatus(Person person, int zoneID, String year) {
		String kode = "";
		KodeStatus kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, zoneID, year);
		if (kodeStat != null) {
			kode = kodeStat.getKode();
		} else {
			if (zoneID == 1) {
				kode = "ЕП-2";
			} else {
				kode = "н";
			}
		}
		return kode;
	}

	private static String getLastWorkplace(Person person) {
//		System.out.println("otdel " + getLastPersonStatus(person).getWorkplace().getOtdel());
		String PerStatNewSet = ReadFileBGTextVariable.getGlobalTextVariableMap().get("PerStatNewSet");
		if(PerStatNewSet.equals("1")) {
			return getLastPersonStatusNew(person).getWorkplace().getOtdel();
		}else {
		
		return getLastPersonStatus(person).getWorkplace().getOtdel();
		}
	}
	
	private static PersonStatus getLastPersonStatus(Person person) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		
		Date lastDate = null;
		PersonStatus lastPersonStatus = null;
		try {
			lastDate = sdf.parse("01.01.2000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PersonStatus> list = PersonStatusDAO.getValuePersonStatusByObject("Person_ID", person);
//		System.out.println("List<PersonStatus> listSize " + list.size());
		for (PersonStatus personStatus : list) {

			Date endDate = personStatus.getSpisak_prilogenia().getEndDate();
//			System.out.println("endDate " + endDate);
			if (lastDate.before(endDate)) {
				lastDate = endDate;
				lastPersonStatus = personStatus;
//				System.out.println("lastDate " + lastDate);
			}
		}

		return lastPersonStatus;
	}

	private static PersonStatusNew getLastPersonStatusNew(Person person) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		
		Date lastDate = null;
		PersonStatusNew lastPersonStatus = null;
		try {
			lastDate = sdf.parse("01.01.2000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PersonStatusNew> list = PersonStatusNewDAO.getValuePersonStatusNewByObject("Person_ID", person);
//		System.out.println("List<PersonStatus> listSize " + list.size());
		for (PersonStatusNew personStatus : list) {

			Date endDate = personStatus.getStartDate();
//			System.out.println("endDate " + endDate);
			if (lastDate.before(endDate)) {
				lastDate = endDate;
				lastPersonStatus = personStatus;
//				System.out.println("lastDate " + lastDate);
			}
		}

		return lastPersonStatus;
	}
}
