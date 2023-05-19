package AutoInsertMeasuting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;

import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonStatusDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;

public class InsertMeasurToExcel {

	public static void SaveListReportMeasurClassToExcellFile(List<ReportMeasurClass> list,
			boolean forPersonalExcellFile) {

		String falseData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("falseData");
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String notSelectFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSelectFile");
		String[] filepat = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal_test();
		String filePathExternal = filepat[0]; 
		String filePathPersonel =  filepat[1];  
		List<ReportMeasurClass> listForSaveMeasurToMont = new ArrayList<>();
		List<ReportMeasurClass> listForNotSaveMeasur = new ArrayList<>();
		String pathFile = "";
		if (forPersonalExcellFile) {
			pathFile = filePathExternal;
		} else {
			pathFile = filePathPersonel;
		}

		try {
			for (ReportMeasurClass reportMeasur : list) {
			FileInputStream inputStream = new FileInputStream(pathFile);
			Workbook workbook = new HSSFWorkbook(inputStream);

			
				if (reportMeasur.getToExcell()) {
					if (!SaveMeasurToExcel(reportMeasur, workbook)) {
						listForNotSaveMeasur.add(reportMeasur);
					} else {
						listForSaveMeasurToMont.add(reportMeasur);
					}
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

		SaveListReportMeasurClassToMonthExcellFile(listForSaveMeasurToMont, forPersonalExcellFile);

		if (forPersonalExcellFile) {
			if (listForNotSaveMeasur.size() > 0) {
				String reportNotSaveMesur = "<html>"
						+ ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSaveMeasur");
				for (ReportMeasurClass reportMeasur : listForNotSaveMeasur) {
					reportNotSaveMesur = reportNotSaveMesur + getNamePersonAndEGN(reportMeasur) + "<br>";
				}
				reportNotSaveMesur = reportNotSaveMesur + "</html>";
				MessageDialog(reportNotSaveMesur, "");
			}
		} else {
			forPersonalExcellFile = true;
			SaveListReportMeasurClassToExcellFile(listForNotSaveMeasur, forPersonalExcellFile);
		}

	}

	public static void SaveListReportMeasurClassToMonthExcellFile(List<ReportMeasurClass> list,
			boolean forPersonalExcellFile) {

		String falseData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("falseData");
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String notSelectFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSelectFile");
		String filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_test"); 
		String filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_test"); 

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

		int monthPage = getMonthFromDate(DateOfCurrentMeasurement);

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
			

			cellEGN = getCell(sheet, row, 3);
			cellDoze = getCell(sheet, row, 5);
			cellDate = getCell(sheet, row, 6);
			String dozeStr = convertDozeCellToString(cellDoze); 
			
			System.out.println("************************");
			System.out.println(getValueFromCellToString(cellEGN)+" -> "+(masiveDataForSaveMonthRow[2]));
			System.out.println(dozeStr+" -> "+(masiveDataForSaveMonthRow[4]));
			System.out.println(sdf1.format(ReadExcelFileWBC.readCellToDate(cellDate))+" -> "+(masiveDataForSaveMonthRow[5]));
			
				if (getValueFromCellToString(cellEGN).equals(masiveDataForSaveMonthRow[2])
						&& dozeStr.equals(masiveDataForSaveMonthRow[4])
						&& sdf1.format(ReadExcelFileWBC.readCellToDate(cellDate)).equals(masiveDataForSaveMonthRow[5])) {
					flDublicate = true;
				}
				
				if(getValueFromCellToString(cellEGN).trim().isEmpty()) {
					row = sheet.getLastRowNum();
				}
				row++;
		}

		System.out.println("flDublicate " + flDublicate);
		Row roww;
		if (!flDublicate) {
			
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
					roww.createCell(i + 1).setCellValue(Double.parseDouble(masiveDataForSaveMonthRow[i].replaceAll(",", ".")));
				}
			}

		}
	}

	private static String convertDozeCellToString(Cell cellDoze) {
		String dozeStr = getValueFromCellToString(cellDoze);
		DecimalFormat formatter = new DecimalFormat("0.00");
		try {
			double dob = Double.parseDouble(getValueFromCellToString(cellDoze));
			dozeStr = formatter.format(dob);
		} catch (NumberFormatException e) {
		
		}
		return dozeStr;
	}

	public static String getValueFromCellToString(Cell cell) {
		String str = "";

		String type = cell.getCellType().toString();
		
		switch (type) {
		case "STRING": {
			str = cell.getStringCellValue();
		}
			break;
		case "BLANG": {
			str = "";
		}
			break;
		case "DATA":
		case "NUMERIC": {
			str = NumberToTextConverter.toText(cell.getNumericCellValue());
			  
		}
			break;
		}
		return str;
	}

	private static Cell getFirstCellFromLastRow(Sheet sheet) {
		Cell cell = null;
		for (int row = 6; row < sheet.getLastRowNum() + 1; row++) {
			cell = getCell(sheet, row, 0);
			if (getValueFromCellToString(cell).isEmpty()) {
				return getCell(sheet, row - 1, 0);
			}
		}
		return cell;
	}

	private static String[] generateMasiveDataForSaveMonthRow(ReportMeasurClass reportMeasur) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
		DecimalFormat formatter = new DecimalFormat("0.00");
		String[] masiveDataForSaveMonthRow = new String[9];
		String year = sdf.format(reportMeasur.getMeasur().getDate()).substring(6);
//		System.out.println("year " + year);
		Person person = reportMeasur.getMeasur().getPerson();
		masiveDataForSaveMonthRow[0] = getLastWorkplace(person);
		masiveDataForSaveMonthRow[1] = getNamePerson(person);
		masiveDataForSaveMonthRow[2] = person.getEgn();
		masiveDataForSaveMonthRow[3] = getKodeStatus(person, 1, year);
		masiveDataForSaveMonthRow[4] = formatter.format(reportMeasur.getMeasur().getDoze());
		masiveDataForSaveMonthRow[5] = sdf1.format(reportMeasur.getMeasur().getDate());
		masiveDataForSaveMonthRow[6] = getKodeStatus(person, 2, year);
		masiveDataForSaveMonthRow[7] = reportMeasur.getMeasur().getLab().getLab().toLowerCase();
		masiveDataForSaveMonthRow[8] = reportMeasur.getMeasur().getLab().getLab_ID() + "";
		for (int i = 0; i < masiveDataForSaveMonthRow.length; i++) {
//			System.out.println(i + " - " + masiveDataForSaveMonthRow[i]);

		}

		return masiveDataForSaveMonthRow;
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
		return getLastPersonStatus(person).getWorkplace().getOtdel();
	}

	public static String getNamePerson(Person person) {
		return person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName();
	}

	private static String getNamePersonAndEGN(ReportMeasurClass reportMeasur) {
		return getNamePerson(reportMeasur.getMeasur().getPerson()) + " s EGN: "
				+ reportMeasur.getMeasur().getPerson().getEgn();
	}

	public static boolean SaveMeasurToExcel(ReportMeasurClass reportMeasurClassToSave, Workbook workbook) {

		String egnMeasur = reportMeasurClassToSave.getMeasur().getPerson().getEgn();
		Sheet sheet = workbook.getSheetAt(1);
		int row = getRowByEgnInSheet(sheet, egnMeasur);
		System.out.println("egn " + egnMeasur+" row "+row);
		if (row < 0) {
			return false;
		} else {
			saveNuclideDataToExcelFile(workbook, row, reportMeasurClassToSave);
		}
		return true;

	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	private static void saveNuclideDataToExcelFile(Workbook workbook, int row,
			ReportMeasurClass reportMeasurClassToSave) {
		int index = 0;
		Cell cell;
		Sheet sheet = workbook.getSheetAt(1);
		int increment = 1, corek = 1;
		boolean flNotDublicateData = true;
		while (increment <= 26 && flNotDublicateData) {
			System.out.println("increment " + increment);
			if (increment > 13) {
				sheet = workbook.getSheetAt(2);
				corek = 14;
			}
			if (sheet.getRow(row) != null) {
				index = 19 * (increment - corek) + 7;
//				System.out.println(index);
				cell = getCell(sheet, row, index);
				Cell cell1 = getCell(sheet, row, index+1);
				Cell cell2 = getCell(sheet, row, index + 17);
				flNotDublicateData = checkNotDublicate(row, index, reportMeasurClassToSave, sheet);
//				System.out.println("flNotDublicateData "+flNotDublicateData);
				if (!ReadExcelFileWBC.CellNOEmpty(cell) || 
						!ReadExcelFileWBC.CellNOEmpty(cell1) || 
						!ReadExcelFileWBC.CellNOEmpty(cell2)) {
//					System.out.println(cell.getStringCellValue());
					saveDataToExcelFile(row, index, reportMeasurClassToSave, sheet);
					saveSumDozeToExcelFile(row, reportMeasurClassToSave, workbook.getSheetAt(0));
					increment = 30;
				}
			}

			increment++;

		}

	}

	private static void saveSumDozeToExcelFile(int row, ReportMeasurClass reportMeasur, Sheet sheet) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		String DateOfCurrentMeasurement = sdf.format(reportMeasur.getMeasur().getDate());
		int column = getMonthFromDate(DateOfCurrentMeasurement)+77;
		
		double dozeMeasurDouble = reportMeasur.getMeasur().getDoze();
		String dozeMeasurStr = convertDozeToString(reportMeasur);
		Cell cellDoze = getCell(sheet, row, column);
	
		System.out.println(dozeMeasurDouble+" - "+dozeMeasurStr);
		try {
		Double.parseDouble(dozeMeasurStr.replaceAll(",", "."));
		//dozata ot izmervaneto e chislo
		System.out.println("dozata ot izmervaneto e chislo");
		if(cellDoze.getCellType().toString().equals("STRING")) {
			//dozata ot excelFile e string
			System.out.println("dozata ot excelFile e string");
			cellDoze.setCellValue(dozeMeasurDouble);	
		}else {
			//dozata ot excelFile e chislo
			System.out.println("dozata ot excelFile e chislo");
			dozeMeasurDouble = dozeMeasurDouble + cellDoze.getNumericCellValue();
			cellDoze.setCellValue(dozeMeasurDouble);	
		}
		
	} catch (Exception e) {
		//dozata ot izmervaneto e string
		System.out.println("dozata ot izmervaneto e string");
		if(getValueFromCellToString(cellDoze).isEmpty() || cellDoze.getCellType().toString().equals("STRING")) {
			//dozata ot excelFile e prazna ili string
			System.out.println("dozata ot excelFile e string");
			cellDoze.setCellValue(dozeMeasurStr);	
		}
	}

	}

	private static boolean checkNotDublicate(int row, int column, ReportMeasurClass reportMeasurClassToSave, Sheet sheet) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		
		String date = sdfrmt.format(reportMeasurClassToSave.getMeasur().getDate());
		String lab = reportMeasurClassToSave.getMeasur().getLab().getLab().toLowerCase();
		String doseString = convertDozeToString(reportMeasurClassToSave);
		
		Cell cellDate = getCell(sheet, row, column);
		Cell cellLab = getCell(sheet, row, column+1);
		Cell cellDoze = getCell(sheet, row, column + 18);
		
		String dozeStr = convertDozeCellToString(cellDoze); 
		
		System.out.println(sdfrmt.format(ReadExcelFileWBC.readCellToDate(cellDate))+" <-> "+(date));
		System.out.println(getValueFromCellToString(cellLab)+" <-> "+(lab));
		System.out.println(dozeStr+" <-> "+(doseString));
		
		if(sdfrmt.format(ReadExcelFileWBC.readCellToDate(cellDate)).equals(date)
			&& getValueFromCellToString(cellLab).equals(lab)
			&& dozeStr.equals(doseString)) {
			return false;
		}
		return true;
	}

	private static void saveDataToExcelFile(int row, int column, ReportMeasurClass reportMeasurClassToSave,
			Sheet sheet) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		
		
		
		Cell cell = getCell(sheet, row, column);
		cell.setCellValue(sdfrmt.format(reportMeasurClassToSave.getMeasur().getDate()));
		column++;
		cell = getCell(sheet, row, column);
		cell.setCellValue(reportMeasurClassToSave.getMeasur().getLab().getLab().toLowerCase());
		List<String> listString = reportMeasurClassToSave.getListNuclideData();
		if (listString.size() > 0) {
			for (String nuclideString : listString) {
				nuclideString = nuclideString.replaceAll("##", "");
//				System.out.println(nuclideString);
				String[] masiveStrNuclide = nuclideString.split(":");
//				System.out.println("nnnnnnnnnnnn " + masiveStrNuclide[1]);
				int index = NuclideWBCDAO.getValueNuclideWBCByObject("Symbol", masiveStrNuclide[1].trim()).get(0)
						.getId_nuclide();
				cell = getCell(sheet, row, column + index);
				cell.setCellValue(Double.parseDouble(masiveStrNuclide[4]));

			}
		}
		
		cell = getCell(sheet, row, column + 17);
				
		String doseString = convertDozeToString(reportMeasurClassToSave);
		try {
			Double.parseDouble(doseString);
			cell.setCellValue(reportMeasurClassToSave.getMeasur().getDoze());
		} catch (Exception e) {
			cell.setCellValue(doseString);
		} 
		

	}

	private static String convertDozeToString(ReportMeasurClass reportMeasurClassToSave) {
		DecimalFormat formatter = new DecimalFormat("0.00");
		
		double dozeDouble = reportMeasurClassToSave.getMeasur().getDoze();
		String doseString = formatter.format(dozeDouble);
		if (dozeDouble < 0.1 && !doseString.equals("0,00")) {
			doseString = "<0.10";
		}
		if (reportMeasurClassToSave.getMeasur().getTypeMeasur().getKodeType().equals("M")) {
			doseString = "M";
		}
		return doseString;
	}

	private static int getRowByEgnInSheet(Sheet sheet, String egnMeasur) {
		String EGN;
		Cell cell;
		int row = 4;
		while (row <= sheet.getLastRowNum()) {
			row++;
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
					if (egnMeasur.equals(EGN)) {
						return row;
					}
				}
			}
		}

		return -1;

	}

	public static int getMonthFromDate(String DateOfCurrentMeasurement) {
		int getMonthFromDate = -1;
		try {
			getMonthFromDate = Integer.parseInt(DateOfCurrentMeasurement.substring(3, 5)) - 1;
		} catch (Exception e) {

		}
		return getMonthFromDate;
	}

	public static Cell getCell(Sheet sheet, int row, int column) {
		Cell cell = null;
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		cell = sheet.getRow(row).getCell(column);
		if (cell == null) {
			cell = sheet.getRow(row).createCell(column);
			cell.setBlank();

		}

		return cell;

	}

}
