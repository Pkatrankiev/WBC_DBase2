package Aplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;

import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;


import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;

public class ReadMeasuringFromExcelFile {

	public static List<Measuring> generateListFromMeasuringFromExcelFile(String pathFile ) {
				
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<Measuring> listMeasuring = new ArrayList<>();
		if(workbook.getNumberOfSheets()>2) {
			listMeasuring = generateListFromMeasuringFromBigExcelFile(workbook);
				
		}else {
			listMeasuring = getListMeasuringFromSmalExcelFile(workbook);	
		}
		return listMeasuring;
	}
		
		
		
	private static List<Measuring> getListMeasuringFromSmalExcelFile(Workbook workbook) {
		String EGN = "", lab;
		Date date;
	
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1;
		
		List<Measuring> listMeasuring = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

				Person person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person != null) {
						System.out.println("++++++++++++++++++++"+EGN);
						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						
						while (ReadExcelFileWBC.CellNOEmpty(cell1)) {
							date = ReadExcelFileWBC.readCellToDate(cell1);
							lab = "wbc-1";
							k = k+17;
							Measuring meas = createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
							listMeasuring.add(meas);
						k++;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						
						
						}
					}
				}
			}

		}
		return listMeasuring;
	}



	public static List<Measuring> generateListFromMeasuringFromBigExcelFile(Workbook workbook) {	
		String EGN = "", lab;
		Date date;
		
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
		List<Measuring> listMeasuring = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

				Person person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person != null) {
						System.out.println("++++++++++++++++++++"+EGN);
						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
						date = ReadExcelFileWBC.readCellToDate(cell1);
						lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
						
						k = k+17;
						Measuring meas = createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
						listMeasuring.add(meas);
								
						if(k>253) {
							k=6;
							sheet = workbook.getSheetAt(2);
						}
						
						k++;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						
						
						}
					}
				}
			}

		}
		return listMeasuring;
	}
	
	public static  Measuring createMeasur(Person person, DimensionWBC dim, UsersWBC userSet, Sheet sheet, int row, int k, TypeMeasur tipeM_R, Date date, String lab)	{
		double doze = 0;
		TypeMeasur tipeM;
	
	int indexLab = Integer.parseInt(lab.substring(lab.length()-1));
	Laboratory labor = LaboratoryDAO.getValueLaboratoryByID(indexLab);
	
	System.out.println(k);
	tipeM = tipeM_R;
	Cell cell = sheet.getRow(row).getCell(k);
	doze = 0.0;
		if(cell!=null) {				
	String type = cell.getCellType().toString();
	switch (type) {
	case "STRING": {
		
		String str = cell.getStringCellValue();
		System.out.println(str+" -> "+row);
		if(str.contains("<")) {
			doze = 0.05;
		}else {
		
		if( TypeMeasurDAO.getValueTypeMeasurByObject("KodeType", str).size()>0) {
			doze = 0.0;
		}
		
		try {
			doze = Double.parseDouble(str);
		} catch (Exception e) {
			doze = -1;
		}
		
		if(doze==-1) {
			str = cell.getStringCellValue();
			str = AplicationMetods.transliterate(str).toUpperCase();
			tipeM = TypeMeasurDAO.getValueTypeMeasurByObject("KodeType", str).get(0);
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
		String coment = "";
	if(doze==0.05) {
	coment = "Doze < 0.10 mSv";	
	}
	String reportFile = "Excel-"+row+"/"+k;
	return new Measuring(person, date, doze, dim, labor, userSet, tipeM, coment, reportFile);	
	
	}
	
	
	public static void ListMeasuringToBData(List<Measuring> MeasuringList) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		String reportFile = "Excel-";
		String date;
		int k = 0;
		for (Measuring spPr : MeasuringList) {
			date = sdfrmt.format(spPr.getDate());
			spPr.setReportFileName(reportFile+date+"-"+k+"-"+spPr.getPerson().getId_Person());
			System.out.println( spPr.getPerson().getEgn()+ " - "
						+ date + " - "
						 + " - "+spPr.getDoze()+" - "+spPr.getDoseDimension().getDimensionName()+
						 " - "+spPr.getLab().getLab()+" - "+spPr.getUser().getLastName()+
						 " - "+spPr.getTypeMeasur().getKodeType()+" - "+spPr.getReportFileName());
			k++;
		}
	}
	
	public static void setListMeasuringToBData(List<Measuring> MeasuringList) {
		for (Measuring spPr : MeasuringList) {
			MeasuringDAO.setObjectMeasuringToTable(spPr);
		}
	}


	
}
