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

	public static List<Measuring> generateListFromMeasuringFromExcelFile(String pathFile, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
				
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<Measuring> listMeasuring = new ArrayList<>();
		if(workbook.getNumberOfSheets()>2) {
			listMeasuring = generateListFromMeasuringFromBigExcelFile(workbook, round, textIcon, listDiferentRow);
				
		}else {
			listMeasuring = getListMeasuringFromSmalExcelFile(workbook);	
		}
		return listMeasuring;
	}
		
		
		
	private static List<Measuring> getListMeasuringFromSmalExcelFile(Workbook workbook) {
		String lab;
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
//						System.out.println("++++++++++++++++++++"+EGN);
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



	public static List<Measuring> generateListFromMeasuringFromBigExcelFile(Workbook workbook, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {	
		String lab, FirstName;
		Date date;
		
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		
		
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
		List<Measuring> listMeasuring = new ArrayList<>();
		
		int StartRow = 0;
		int endRow = 0;
		
		if(listDiferentRow != null) {
			StartRow = 0;
			endRow = listDiferentRow.size();
		}else {
			StartRow = 5;
			endRow = sheet.getLastRowNum();
		}
		int row = 0;
		for (int index = StartRow; index < endRow ; index += 1) {

			if(listDiferentRow != null) {
				row = listDiferentRow.get(index);
			}else {
				row  = index;
			}
			

			if (sheet.getRow(row) != null && row > 0) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

				Person person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
				
				FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
					if (person != null && !FirstName.contains("АЕЦ")) {
						
						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
						date = ReadExcelFileWBC.readCellToDate(cell1);
						lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
						if(true) {
							
						}
						k = k+17;
						Measuring meas = createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
						
						listMeasuring.add(meas);
						if(k>252) {
							k=6;
							sheet = workbook.getSheetAt(2);
						}
						
						k++;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						
						
						}
						sheet = workbook.getSheetAt(1);
					}
				}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}
		return listMeasuring;
	}
	
	public static  Measuring createMeasur(Person person, DimensionWBC dim, UsersWBC userSet, Sheet sheet, int row, int k, TypeMeasur tipeM_R, Date date, String lab)	{
		double doze = 0;
		TypeMeasur tipeM;
	
	int indexLab = Integer.parseInt(lab.substring(lab.length()-1));
	Laboratory labor = LaboratoryDAO.getValueLaboratoryByID(indexLab);
	
//	System.out.println(k);
	tipeM = tipeM_R;
	Cell cell = sheet.getRow(row).getCell(k);
	doze = 0.0;
		if(cell!=null) {				
	String type = cell.getCellType().toString();
	switch (type) {
	case "STRING": {
		
		String str = cell.getStringCellValue();
//		System.out.println(str+" -> row "+row+" col "+k);
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
	String reportFile = "Excel-"+person.getEgn()+"/"+k;
//	System.out.println(reportFile);
	return new Measuring(person, date, doze, dim, labor, userSet, tipeM, coment, reportFile, reportFile);	
	
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
	
	public static void setListMeasuringToBData(List<Measuring> MeasuringList, ActionIcone round,  String textIcon) {
		int k=0;
		int l=MeasuringList.size();
		for (Measuring measur : MeasuringList) {
			if(checkForMeasurInDBase(measur)==null) {
				MeasuringDAO.setObjectMeasuringToTable(measur);	
			}
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
			k++;
		}
	}



	private static Measuring checkForMeasurInDBase(Measuring measur) {
		return MeasuringDAO.getValueMeasuringFromExcelByPerson_Date_ExcelPozition_Lab(measur.getPerson(), measur.getDate(),measur.getLab(), measur.getExcelPosition());
		 
	}


	
}
