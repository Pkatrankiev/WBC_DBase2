package AutoInsertMeasuting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;

import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReportMeasurClass;
import Aplication.ResourceLoader;
import BasiClassDAO.NuclideWBCDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;

public class SaveReportMeasurTo_PersonelORExternalExcelFile {

	static boolean flNotDublicateData;
	static List<ReportMeasurClass> listReportMeasurClass = new ArrayList<>();
	
	public static void SaveListReportMeasurClassToExcellFile(List<ReportMeasurClass> list,
			boolean forPersonalExcellFile) {

		String falseData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("falseData");
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String notSelectFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSelectFile");
		
//		String[] filepat = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal_test();
		
		
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig2");
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig2");
		 
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
					String excelPosition = SaveMeasurToExcel(reportMeasur, workbook);
					if (!excelPosition.isEmpty()) {
						Measuring measur = reportMeasur.getMeasur();
						String reportFile = "Excel-"+measur.getPerson().getEgn()+"/"+excelPosition;
						measur.setExcelPosition(reportFile);
						reportMeasur.setMeasur(measur);
						listForSaveMeasurToMont.add(reportMeasur);
						listReportMeasurClass.add(reportMeasur);
					} else {
						if(flNotDublicateData)	listForNotSaveMeasur.add(reportMeasur);
						
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

		SaveReportMeasurTo_MonthExcellFile.SaveListReportMeasurClassToMonthExcellFile(listForSaveMeasurToMont, forPersonalExcellFile);

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

	
	public static String convertDozeCellToString(Cell cellDoze) {
		String dozeStr = getValueFromCellToString(cellDoze);
		DecimalFormat formatter = new DecimalFormat("0.00");
		try {
			double dob = Double.parseDouble(getValueFromCellToString(cellDoze));
			dozeStr = formatter.format(dob).replace(",",".");
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
	
	
	public static String getNamePerson(Person person) {
		return person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName();
	}

	private static String getNamePersonAndEGN(ReportMeasurClass reportMeasur) {
		return getNamePerson(reportMeasur.getMeasur().getPerson()) + " s EGN: "
				+ reportMeasur.getMeasur().getPerson().getEgn();
	}

	public static String SaveMeasurToExcel(ReportMeasurClass reportMeasurClassToSave, Workbook workbook) {

		String excelPosition;
		String egnMeasur = reportMeasurClassToSave.getMeasur().getPerson().getEgn();
		Sheet sheet = workbook.getSheetAt(1);
		int row = getRowByEgnInSheet(sheet, egnMeasur);
		System.out.println("egn " + egnMeasur+" row "+row);
		if (row < 0) {
			return "";
		} else {
			excelPosition = saveNuclideDataToExcelFile(workbook, row, reportMeasurClassToSave);
		}
		return excelPosition;

	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	
	private static String saveNuclideDataToExcelFile(Workbook workbook, int row,
			ReportMeasurClass reportMeasurClassToSave) {
		String reportFile = "";
		int index = 0;
		Cell cell;
		Sheet sheet = workbook.getSheetAt(1);
		int increment = 1, corek = 1;
		flNotDublicateData = true;
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
				Cell cell2 = getCell(sheet, row, index + 18);
				flNotDublicateData = checkNotDublicate(row, index, reportMeasurClassToSave, sheet);
								
				System.out.println("flNotDublicateData "+flNotDublicateData);
				System.out.println(""+ReadExcelFileWBC.CellNOEmpty(cell) +" "+  
						ReadExcelFileWBC.CellNOEmpty(cell1) +" "+ 
						ReadExcelFileWBC.CellNOEmpty(cell2));
				if (!ReadExcelFileWBC.CellNOEmpty(cell) || 
						!ReadExcelFileWBC.CellNOEmpty(cell1) || 
						!ReadExcelFileWBC.CellNOEmpty(cell2)) {
//					System.out.println(cell.getStringCellValue());
					int excelPosition = saveDataToExcelFile(row, index, reportMeasurClassToSave, sheet);
					reportFile = "Excel-"+reportMeasurClassToSave.getMeasur().getPerson().getEgn()+"/"+excelPosition;
					saveSumDozeToExcelFile(row, reportMeasurClassToSave, workbook.getSheetAt(0));
					increment = 30;
				}
			}

			increment++;

		}
		return reportFile;

	}

	private static void saveSumDozeToExcelFile(int row, ReportMeasurClass reportMeasur, Sheet sheet) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		String DateOfCurrentMeasurement = sdf.format(reportMeasur.getMeasur().getDate());
		int column = getMonthFromDate(DateOfCurrentMeasurement)+77;
		
		double dozeMeasurDouble = reportMeasur.getMeasur().getDoze();
		String type = reportMeasur.getMeasur().getTypeMeasur().getKodeType();
		String dozeMeasurStr = convertDozeToString(dozeMeasurDouble, type);
		Cell cellDoze = getCell(sheet, row, column);
	
		String strCellDoze = getValueFromCellToString(cellDoze);
		System.out.println("*********************************strCellDoze "+strCellDoze);
		if(strCellDoze.isEmpty()||(!strCellDoze.equals("<0.10") && strCellDoze.equals("0"))){
			saveDozeToCell(cellDoze, dozeMeasurStr);
		}
		if(strCellDoze.equals("<0.10") && dozeMeasurDouble >= 0.1){
			saveDozeToCell(cellDoze, dozeMeasurStr);
		}
		try {
			double cellDozeDouble = Double.parseDouble(strCellDoze);
			if(cellDozeDouble >= 0.10 && dozeMeasurDouble >= 0.1){
				saveDozeToCell(cellDoze,convertDozeToString((cellDozeDouble + dozeMeasurDouble), type));
		}
		} catch (Exception e) {
			
		}
	
	}

	private static void saveDozeToCell(Cell cellDoze, String dozeMeasurStr) {
		
		try {
			double doubleDozeMeasur = Double.parseDouble(dozeMeasurStr);
			cellDoze.setCellValue(doubleDozeMeasur);
		} catch (Exception e) {
			cellDoze.setCellValue(dozeMeasurStr);
		}
		
	}

	private static boolean checkNotDublicate(int row, int column, ReportMeasurClass reportMeasurClassToSave, Sheet sheet) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		String inExcelFileExistData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("inExcelFileExistData");
		inExcelFileExistData =  "<html>" + inExcelFileExistData + "<br>";
		
		String egn = reportMeasurClassToSave.getMeasur().getPerson().getEgn();
		String date = sdfrmt.format(reportMeasurClassToSave.getMeasur().getDate());
		String lab = reportMeasurClassToSave.getMeasur().getLab().getLab().toLowerCase();
		
		double dozeMeasurDouble = reportMeasurClassToSave.getMeasur().getDoze();
		String type = reportMeasurClassToSave.getMeasur().getTypeMeasur().getKodeType();
		String doseString = convertDozeToString(dozeMeasurDouble, type);
		
		inExcelFileExistData = inExcelFileExistData+"  " +egn+"  " + date+"  "+lab+"  "+doseString+ "</html>";
				
		Cell cellDate = getCell(sheet, row, column);
		Cell cellLab = getCell(sheet, row, column+1);
		Cell cellDoze = getCell(sheet, row, column + 18);
		
		String dozeStr = convertDozeCellToString(cellDoze); 
		
		System.out.println(sdfrmt.format(ReadExcelFileWBC.readCellToDate(cellDate))+" <-ND> "+(date));
		System.out.println(getValueFromCellToString(cellLab)+" <-ND> "+(lab));
		System.out.println(dozeStr+" <-ND> "+(doseString));
		
		if(sdfrmt.format(ReadExcelFileWBC.readCellToDate(cellDate)).equals(date)
			&& getValueFromCellToString(cellLab).equals(lab)
			&& dozeStr.equals(doseString)) {
			
			return OptionDialog(inExcelFileExistData);
		}
		return true;
	}

	public static boolean OptionDialog(String mesage) {
		String[] options = {"NotSave", "Save"};
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);
		
		if (x > 0) {
			return true;
		}
		return false;
	}
	
	private static int saveDataToExcelFile(int row, int column, ReportMeasurClass reportMeasurClassToSave,
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
		double dozeDouble = reportMeasurClassToSave.getMeasur().getDoze();
		String type = reportMeasurClassToSave.getMeasur().getTypeMeasur().getKodeType();
		String doseString = convertDozeToString(dozeDouble, type);
		
		try {
			System.out.println("doseString = "+doseString);
			
			cell.setCellValue(Double.parseDouble(doseString));
		} catch (Exception e) {
			cell.setCellValue(doseString);
		}
		return cell.getColumnIndex(); 
		

	}

	public static String convertDozeToString(double dozeDouble, String type) {
			
		DecimalFormat formatter = new DecimalFormat("0.00");
		String doseString = formatter.format(dozeDouble).replace(",", ".");
		
		if (dozeDouble < 0.1 && !doseString.equals("0.00")) {
			doseString = "<0.10";
		}
		if (type.equals("M")) {
			doseString = "M";
		}
		
//		if(doseString.equals("0,00")) {
//			doseString = "0";
//		}
		
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
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
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

	public static List<ReportMeasurClass> getListReportMeasurClass() {
		return listReportMeasurClass;
	}

	public static void setListReportMeasurClass(List<ReportMeasurClass> listReportMeasurClass) {
		SaveReportMeasurTo_PersonelORExternalExcelFile.listReportMeasurClass = listReportMeasurClass;
	}


	
	public static boolean getFlNotDublicateData() {
		return flNotDublicateData;
	}


	public static void setFlNotDublicateData(boolean flNotDublicateData) {
		SaveReportMeasurTo_PersonelORExternalExcelFile.flNotDublicateData = flNotDublicateData;
	}

}
