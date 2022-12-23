package Aplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonDAO;


public class InsertMeasurToExcel {

	public static void InsertMeasurToExcel( ReportMeasurClass reportMeasurClassToSave) {
		
		String pathFile = "D:\\EXTERNAL_2020_12.xls"; // пътя и името до ексел файла
		File xlsFile = new File(pathFile);
		
		try {
			FileInputStream inputStream = new FileInputStream(xlsFile);
			Workbook workbook = new HSSFWorkbook(inputStream);


		Sheet sheet = workbook.getSheetAt(2);
						
//		String DateOfCurrentMeasurement = sdfrmt.format(reportMeasurClassToSave.getMeasur().getDate());
		String egnMeasur = reportMeasurClassToSave.getMeasur().getPerson().getEgn();
//		int monthPage = getMonthFromDate(DateOfCurrentMeasurement);
		
		int row = getRowByEgnInSheet(sheet, egnMeasur);
			System.out.println(row);		
		int index = saveNuclideDataToExcelFile(workbook, row, reportMeasurClassToSave); 	
		
		inputStream.close();
		
		
		FileOutputStream outFile = new FileOutputStream(xlsFile);
				workbook.write(outFile);
				workbook.close();
				outFile.close();
		} catch (FileNotFoundException | OldExcelFormatException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Excel файлът трябва да е версия 97/2000/XP/2003", "Грешни данни",
					JOptionPane.ERROR_MESSAGE);

		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Не е избран excel файл", "Грешни данни", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}
	
	
	
	
	private static int saveNuclideDataToExcelFile(Workbook workbook, int row, ReportMeasurClass reportMeasurClassToSave) {
		int index =0;
		Cell cell;
		Sheet sheet = workbook.getSheetAt(1);
		int increment = 1, corek = 1;
		while (increment <= 26) {
			System.out.println("increment "+increment);
			if(increment>13) {
				sheet = workbook.getSheetAt(2);	
				corek = 14;
			}
			if (sheet.getRow(row) != null) {
				index = 19*(increment-corek)+7;
				System.out.println(index);
				cell = sheet.getRow(row).getCell(index);
				System.out.println(cell.getColumnIndex()+" - "+cell.getRowIndex());
				System.out.println(cell.getStringCellValue());
				if (!ReadExcelFileWBC.CellNOEmpty(cell)) {
					System.out.println(cell.getStringCellValue());
					saveDataToExcelFile (row, index, reportMeasurClassToSave, sheet);
					return row;
				}
				}
			
			increment++;
		
		}
		
		return -1;
	}

	
	
	
	private static void saveDataToExcelFile(int row, int column, ReportMeasurClass reportMeasurClassToSave, Sheet sheet) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		DecimalFormat formatter = new DecimalFormat("0.00");
		String doseString;
		double dozeDouble;
		Cell cell = sheet.getRow(row).getCell(column);
		cell.setCellValue(sdfrmt.format(reportMeasurClassToSave.getMeasur().getDate()));
		column++;
		cell = sheet.getRow(row).getCell(column);
		cell.setCellValue(reportMeasurClassToSave.getMeasur().getLab().getLab());
		List<String> listString = reportMeasurClassToSave.getListNuclideData();
		if(listString.size()>0) {
			for (String nuclideString : listString) {
			nuclideString = nuclideString.replaceAll("##", "");
			System.out.println(nuclideString);
			String [] masiveStrNuclide = nuclideString.split(":");
			System.out.println("nnnnnnnnnnnn "+masiveStrNuclide[1]);
			int index = NuclideWBCDAO.getValueNuclideWBCByObject("Symbol", masiveStrNuclide[1].trim()).get(0).getId_nuclide();
			cell = sheet.getRow(row).getCell(column+index);
			cell.setCellValue(masiveStrNuclide[4]);
			
			}
		}
		dozeDouble = reportMeasurClassToSave.getMeasur().getDoze();
		doseString = formatter.format(dozeDouble);

		if (dozeDouble < 0.1) {
			doseString = "<0.10";
		}
		if (reportMeasurClassToSave.getMeasur().getTypeMeasur().getKodeType().equals("M")) {
			doseString = "M";
		}
		cell = sheet.getRow(row).getCell(column+26);
		cell.setCellValue(doseString);
		
		
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
					if (egnMeasur.equals(EGN)) {
						return row;
					}
				}
			}
		}
		
		
			return -1;	
		
		
	}

	public int getMonthFromDate(String DateOfCurrentMeasurement) {
		int getMonthFromDate = -1;
		try {
			getMonthFromDate = Integer.parseInt(DateOfCurrentMeasurement.substring(3, 4));
		} catch (Exception e) {

		}
		return getMonthFromDate;
	}

}
