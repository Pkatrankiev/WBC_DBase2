package SaveToExcellFile;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaRenderer;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.AreaPtgBase;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.formula.ptg.RefPtgBase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.*;

import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ResourceLoader;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import PersonManagement.PersonelManegementMethods;

public class SaveToPersonelORExternalFile {

	private static String filePath[] = getmasive();

	private static SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");

	private static int firstAndLastRowForOtdel[];
	static int curentYear = Calendar.getInstance().get(Calendar.YEAR);
	static UsersWBC workingUser;
	static String commentText;
	static String defaltAreaName;

	
	public static void saveInfoFromPersonManegementToExcelFile(Person person, String firmName, Spisak_Prilogenia spPril,
			UsersWBC user, String comment, Workplace workplace, String oldWorkplace, boolean checkbx_EnterInZone, boolean checkbx_EnterInListChengeKode, boolean obhodenList) {
	
		String falseData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("falseData");
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String notSelectFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSelectFile");

		workingUser = user;
		commentText = comment;

		String pathFile = filePath[1];
		defaltAreaName = "Шаблон";
		if (firmName.equals("АЕЦ Козлодуй")) {
			pathFile = filePath[0];
			defaltAreaName = "Свободен";
		}

		try {
			PersonelManegementMethods.copyExcelFileToDestDir(pathFile, null);
			FileInputStream inputStream = new FileInputStream(pathFile);
			Workbook workbook = new HSSFWorkbook(inputStream);

			
			firstAndLastRowForOtdel = getAreaOtdel(workplace, workbook);
			
			writePersonToOtdel(person, workbook);

			if (spPril != null && !obhodenList) {
				writeFormulyarNameToOtdel(firmName, spPril, workbook, workplace);

				writeFormulyarNameToPersonRow(workbook, spPril, person);
			}

			createCellComment(workbook, person);

			saveInfoByObhodenList(obhodenList, spPril, workbook, person, firmName);
			
			saveInfoByInListChangeKode(checkbx_EnterInListChengeKode, oldWorkplace, spPril, workbook, person, firmName, workplace);
			
			reformatSheetSpPr(workbook);
			reformatSheetIzmervane( workbook);
			
			FileOutputStream outputStream = new FileOutputStream(pathFile);
			workbook.write(outputStream);

			workbook.close();

			outputStream.flush();
			outputStream.close();
			
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


	
	
	

	public 	static void reformatSheetSpPr(Workbook workbook) {
		
		
		Sheet sheetSpPr = workbook.getSheetAt(3);
		int maxRow = sheetSpPr.getLastRowNum();
		
		CellStyle cellStyle0, cellStyle1, cellStyle2;
		
		CellStyle cellStyleYelow0 = sheetSpPr.getRow(5).getCell(7).getCellStyle();
		CellStyle cellStyleYelow1 = sheetSpPr.getRow(5).getCell(8).getCellStyle();
		CellStyle cellStyleYelow2 = sheetSpPr.getRow(5).getCell(9).getCellStyle();
		
		CellStyle cellStyleUser0 = sheetSpPr.getRow(6).getCell(7).getCellStyle();
		CellStyle cellStyleUser1 = sheetSpPr.getRow(6).getCell(8).getCellStyle();
		CellStyle cellStyleUser2 = sheetSpPr.getRow(6).getCell(9).getCellStyle();
		
		int row = 5;
		Cell cell, cell1 ;

		while (row < maxRow) {
			if (sheetSpPr.getRow(row) != null) {
				
				cellStyle0 = cellStyleUser0;
				cellStyle1 = cellStyleUser1;
				cellStyle2 = cellStyleUser2;
				
				cell = sheetSpPr.getRow(row).getCell(5);
				cell1 = sheetSpPr.getRow(row).getCell(6);
				if (ReadExcelFileWBC.CellNOEmpty(cell) || ReadExcelFileWBC.CellNOEmpty(cell1)) {
					
				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					cellStyle0 = cellStyleYelow0;
					cellStyle1 = cellStyleYelow1;
					cellStyle2 = cellStyleYelow2;
				}
				
//				System.out.println(row );
				for (int m = 7; m <= 254; m += 3) {
					
						cell = sheetSpPr.getRow(row).getCell(m);
						if (cell == null) cell = sheetSpPr.getRow(row).createCell(m);
						if (!ReadExcelFileWBC.CellNOEmpty(cell)) cell.setCellStyle(cellStyle0);
						cell = sheetSpPr.getRow(row).getCell(m+1);
						if (cell == null) cell = sheetSpPr.getRow(row).createCell(m+1);
						if (!ReadExcelFileWBC.CellNOEmpty(cell))cell.setCellStyle(cellStyle1);
						cell = sheetSpPr.getRow(row).getCell(m+2);
						if (cell == null) cell = sheetSpPr.getRow(row).createCell(m+2);
						if (!ReadExcelFileWBC.CellNOEmpty(cell))cell.setCellStyle(cellStyle2);
					
				}
				}
			}
			row++;
		}
	}


	public 	static void reformatSheetIzmervane(Workbook workbook) {
		
		for (int k = 1; k <= 2; k++) {
			
		
		Sheet sheetSpPr = workbook.getSheetAt(k);
		int maxRow = sheetSpPr.getLastRowNum();
				
		CellStyle cellStyleDate = sheetSpPr.getRow(5).getCell(7).getCellStyle();
		CellStyle cellStyleLab = sheetSpPr.getRow(5).getCell(8).getCellStyle();
		CellStyle cellStyleNumber = sheetSpPr.getRow(5).getCell(9).getCellStyle();
				
		
		int row = 5;
		Cell cell;

		while (row < maxRow) {
			if (sheetSpPr.getRow(row) != null) {
			
				for (int m = 7; m <= 254; m += 19) {
					
						cell = sheetSpPr.getRow(row).getCell(m);
						if (cell == null) cell = sheetSpPr.getRow(row).createCell(m);
						if (!ReadExcelFileWBC.CellNOEmpty(cell)) cell.setCellStyle(cellStyleDate);
						cell = sheetSpPr.getRow(row).getCell(m+1);
						if (cell == null) cell = sheetSpPr.getRow(row).createCell(m+1);
						if (!ReadExcelFileWBC.CellNOEmpty(cell))cell.setCellStyle(cellStyleLab);
						for (int i = 2; i < 19; i++) {
							if(m+i < 255) {
							cell = sheetSpPr.getRow(row).getCell(m+i);
							if (cell == null) cell = sheetSpPr.getRow(row).createCell(m+i);
							if (!ReadExcelFileWBC.CellNOEmpty(cell))cell.setCellStyle(cellStyleNumber);	
							}
						}
				}
				}
			row++;
			}
			
		}
		}





	private static void saveInfoByInListChangeKode(boolean checkbx_EnterInListChangeKode, String oldWorkplace, Spisak_Prilogenia spPril,
			Workbook workbook, Person person, String firmName, Workplace workplace) {
		System.out.println("checkbx_EnterInListChangeKode "+checkbx_EnterInListChangeKode);
		if(checkbx_EnterInListChangeKode) {

			String text = commentText;
			int intRrowPerson = searchRowPersonInWorkbook(workbook, person);
			Sheet sheet5 = workbook.getSheetAt(5);
			Sheet sheet3 = workbook.getSheetAt(3);
			int lastRow5 = sheet5.getLastRowNum()+1;
			Row lastRow = sheet5.getRow(lastRow5-1);
			Row newRow = sheet5.createRow(lastRow5);
			Row rowPerson = sheet3.getRow(intRrowPerson);
			String newWorkplace = workplace.getOtdel();
			System.out.println("lastRow "+lastRow.getRowNum()+"lastRow4 "+lastRow5+" newRow "+newRow.getRowNum()+" rowPerson "+rowPerson.getRowNum()+" oldWorkplace "+oldWorkplace);
		
				if (firmName.equals("АЕЦ Козлодуй")) {
				
					setChangeKodeToPERSONELSheet5(lastRow, newWorkplace, oldWorkplace, text, newRow, rowPerson);;
				}else {
					setChangeKodeToEXTERNALSheet5(lastRow, newWorkplace, oldWorkplace, text, newRow, rowPerson);
				}
			
			
			
			
		}
		
	}

	private static void setChangeKodeToEXTERNALSheet5(Row lastRow, String newWorkplace,String oldWorkplace, String text, Row newRow, Row rowPerson) {
		
		
		
		Cell newCel = newRow.createCell(1);
		Cell oldCel = rowPerson.getCell(0);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 1);
		
		newCel = newRow.createCell(2);
		oldCel = rowPerson.getCell(1);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 2);
		
		newCel = newRow.createCell(3);
		newCel.setCellValue(oldWorkplace);
		setCelSityleFromPreviosCell(lastRow, newCel, 3);
		
		newCel = newRow.createCell(4);
		oldCel = rowPerson.getCell(5);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 4);
		
		
		newCel = newRow.createCell(5);
		oldCel = rowPerson.getCell(6);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 5);
		
		newCel = newRow.createCell(6);
		newCel.setCellValue(newWorkplace);
		setCelSityleFromPreviosCell(lastRow, newCel, 6);
		
		newCel = newRow.createCell(10);
		newCel.setCellValue(text);
		setCelSityleFromPreviosCell(lastRow, newCel, 10);
	}

	private static void setChangeKodeToPERSONELSheet5(Row lastRow, String newWorkplace,String oldWorkplace, String text, Row newRow, Row rowPerson) {
		
		
		
		Cell newCel = newRow.createCell(1);
		Cell oldCel = rowPerson.getCell(0);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 1);
		
		newCel = newRow.createCell(2);
		oldCel = rowPerson.getCell(1);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 2);
		
		newCel = newRow.createCell(3);
		oldCel = rowPerson.getCell(2);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 3);
		
		
		
		
		newCel = newRow.createCell(4);
		newCel.setCellValue(oldWorkplace);
		setCelSityleFromPreviosCell(lastRow, newCel, 4);
		
		newCel = newRow.createCell(5);
		oldCel = rowPerson.getCell(5);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 5);
		
		
		newCel = newRow.createCell(6);
		oldCel = rowPerson.getCell(6);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		setCelSityleFromPreviosCell(lastRow, newCel, 6);
		
		newCel = newRow.createCell(7);
		newCel.setCellValue(newWorkplace);
		setCelSityleFromPreviosCell(lastRow, newCel, 7);
		
		newCel = newRow.createCell(11);
		newCel.setCellValue(text);
		setCelSityleFromPreviosCell(lastRow, newCel, 11);
	}

	
	private static void setCelSityleFromPreviosCell(Row lastRow, Cell newCel, int celcolumn) {
		Cell cel = lastRow.getCell(celcolumn);
		if(cel != null) {
			newCel.setCellStyle(cel.getCellStyle());
		}
	}

	public static void saveInfoByObhodenList(boolean obhodenList, Spisak_Prilogenia spPril, Workbook workbook, Person person, String firmName) {
		System.out.println("obhodenList "+obhodenList);
		if(obhodenList) {
			Cell cel;
			
			String text = commentText;
			int[] rowByBefautStyle = getAreaOtdelSvoboden(  workbook);
			int intRrowPerson = searchRowPersonInWorkbook(workbook, person);
			Sheet sheet4 = workbook.getSheetAt(4);
			Sheet sheet3 = workbook.getSheetAt(3);
			int lastRow4 = sheet4.getLastRowNum()+1;
			Row newRow = sheet4.createRow(lastRow4);
			Row rowPerson = sheet3.getRow(intRrowPerson);
			
		if (text.isEmpty()) {
			text = "Обходен лист от "+sdfrmt.format(spPril.getStartDate())+"г.";
		}
			
				for (int j = 0; j < 4; j++) {
					for (int i = 0; i < 7; i++) {
				cel = workbook.getSheetAt(j).getRow(intRrowPerson).getCell(i);
//				setYELLOWandREDCellStyle(cel); // да се промени
				System.out.println(rowByBefautStyle[1]+"/////////////////////////////////////////////////////////////");
				setDefautCellStyle(cel, rowByBefautStyle[1], workbook);
				}
			}
				setInfoToPERSONELSheet4(text, newRow, rowPerson);
//				if (firmName.equals("АЕЦ Козлодуй")) {
//				
//				
//				}else {
//					setInfoToEXTERNALSheet4(text, newRow, rowPerson);
//				}
				
				
		}	
		
	}

//	private static void setYELLOWandREDCellStyle( Cell cel) {
//		CellStyle style;
//		Font font = cel.getSheet().getWorkbook().createFont();
//		font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
//		font.setFontName("Times New Roman");
//		font.setBold(true);
//		font.setCharSet(10);
//		style = cel.getSheet().getWorkbook().createCellStyle();
//		style = cel.getCellStyle();
////		style.cloneStyleFrom(cel.getCellStyle());
//		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		style.setFont(font);
//		cel.setCellStyle(style);
//	}
	
//	public static void setWHITEandBLACKCellStyle(Cell cel) {
//		CellStyle style;
//		Font font = cel.getSheet().getWorkbook().createFont();
//		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
//		font.setFontName("Times New Roman");
//		font.setBold(true);
//		font.setCharSet(10);
//		style = cel.getSheet().getWorkbook().createCellStyle();
//		style = cel.getCellStyle();
////		style.cloneStyleFrom(cel.getCellStyle());
//		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		style.setFont(font);
//		cel.setCellStyle(style);
//	}

//	private static void setInfoToEXTERNALSheet4(String text, Row newRow, Row rowPerson) {
//		
//		Cell newCel = newRow.createCell(1);
//		Cell oldCel = rowPerson.getCell(0);
//		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
//		
//		
//		newCel = newRow.createCell(2);
//		oldCel = rowPerson.getCell(1);
//		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
//		
//		
////		newCel = newRow.createCell(3);
////		oldCel = rowPerson.getCell(2);
////		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
//		
//		
//		newCel = newRow.createCell(3);
//		oldCel = rowPerson.getCell(5);
//		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
//		
//		
//		newCel = newRow.createCell(4);
//		oldCel = rowPerson.getCell(6);
//		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
//		
//		
//		newCel = newRow.createCell(6);
//		newCel.setCellValue(text);
//	}
//	
	private static void setInfoToPERSONELSheet4(String text, Row newRow, Row rowPerson) {
		
		Cell oldCel = rowPerson.getCell(0);
		Cell newCel = newRow.createCell(1);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		
		
		oldCel = rowPerson.getCell(1);
		newCel = newRow.createCell(2);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
				
		
		oldCel = rowPerson.getCell(5);
		newCel = newRow.createCell(3);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		
		oldCel = rowPerson.getCell(6);
		newCel = newRow.createCell(4);
		CopyValueFromOldCellToNewcell( oldCel, newCel,  true);
		
		newCel = newRow.createCell(6);
		newCel.setCellValue(text);
	}

	static void writeFormulyarNameToOtdel(String firmName, Spisak_Prilogenia spPril, Workbook workbook, Workplace workplace) {

		Sheet sheetSpPr = workbook.getSheetAt(3);

		firstAndLastRowForOtdel = getAreaOtdel(workplace, workbook);
		int firstRowOtdel = firstAndLastRowForOtdel[0], endRowOtdel = firstAndLastRowForOtdel[1];
		System.out.println(firstRowOtdel + "  " + endRowOtdel);
//		 ПРОВЕРКА ДАЛИ ЗАПОВЕДТА Е ВЪВЕДЕНА ПРИ ЗАГЛАВИЕТО ИЛИ ПРИ КРАЯ НА ФИРМАТА
		if (!checkSpisPrilInRow(spPril, sheetSpPr, firstRowOtdel)
				&& !checkSpisPrilInRow(spPril, sheetSpPr, endRowOtdel)) {

//		ЗАПОВЕДТА НЕ Е ВЪВЕДЕНА И ТЪРСИМ ПЪРВОТО ПРАЗНО МЯСТО ЗА ВЪВЕЖДАНЕТО Й ПРИ ЗАГЛАВИЕТО НА ФИРМАТА

			if (!writeSpisPrilInRow(spPril, sheetSpPr, firstRowOtdel)) {
//		НЯМА МЯСТО В НАЧАЛОТО И ТЪРСИМ ПЪРВОТО ПРАЗНО МЯСТО ЗА ВЪВЕЖДАНЕТО Й ПРИ КРАЯ НА ФИРМАТА
				writeSpisPrilInRow(spPril, sheetSpPr, endRowOtdel);
			}

		}

	}

	private static void writeFormulyarNameToPersonRow(Workbook workbook, Spisak_Prilogenia spPril, Person person) {
//		 ТЪРСИМ РЕДА НА СЕЛЕКТИРАНОТО ЛИЦЕ В СПИСЪКА НА ПЕРСОНАЛА НА ФИРМАТА И ВЪВЕЖДАНЕ НА ЗАПОВЕДТА ЗА ЛИЦЕТО
		int firstRowOtdel = firstAndLastRowForOtdel[0], endRowOtdel = firstAndLastRowForOtdel[1];
		Sheet sheetSpPr = workbook.getSheetAt(3);
		Cell cell;
		String str_cell = "";
		String egn = person.getEgn();
		for (int i = firstRowOtdel; i <= endRowOtdel + 1; i++) {
			if (sheetSpPr.getRow(i) != null) {

				cell = sheetSpPr.getRow(i).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					str_cell = ReadExcelFileWBC.getStringfromCell(cell);
					if (str_cell.equals(egn)) {
						if (!checkSpisPrilInRow(spPril, sheetSpPr, i)) {
							writeSpisPrilInRow(spPril, sheetSpPr, i);
						}
					}

				}
			}

		}

	}
	
	static void writePersonToOtdel(Person person, Workbook workbook) {
		int firstRowOtdel = firstAndLastRowForOtdel[0];
		int endRowOtdel = firstAndLastRowForOtdel[1];

		int row = searchRowPersonInWorkbook(workbook, person);

		boolean isNewData = true;
		if (row > 0) {
			System.out.println(firstRowOtdel +" "+ row +" "+endRowOtdel);
			if (firstRowOtdel < row && row < endRowOtdel) {
				System.out.println("ЛИЦЕТО Е ОТ ОТДЕЛА");
				saveNewInfoByPersonInExcelFile(person, workbook, row);
			}
			if (firstRowOtdel > row   || row > endRowOtdel) {
				System.out.println("ЛИЦЕТО НЕ Е В ОБЛАСТТА НА ОТДЕЛА И ТРЯБВА ДА СЕ ПРЕМЕСТИ");
				if (row > endRowOtdel) {
					row++;
				}
				isNewData = false;
				movePersonInWorkplaceArea(workbook, row, endRowOtdel, isNewData, person);
				removeOldRow(workbook, row);
			}
		} else {
			System.out.println("ЛИЦЕТО НЕ Е В ТОЗИ ФАЙЛ");
			isNewData = true;
			movePersonInWorkplaceArea(workbook, row, endRowOtdel, isNewData, person);
		}

//					colorInYelloFirst7cell(workbook, endRowOtdel);

	}

	private static void saveNewInfoByPersonInExcelFile(Person person, Workbook workbook, int row) {
		Row[] rowsWithInfoPersonFromExcell = saveRowsWithInfoPreson(workbook, row);

		String[] kode = PersonelManegementMethods.getKodeStatusByPersonFromDBase(person);

		saveBasicInfoPersonInExcelFile(rowsWithInfoPersonFromExcell, kode, person);
	}

	private static int searchRowPersonInWorkbook(Workbook workbook, Person person) {
		Sheet sheetSpPr = workbook.getSheetAt(3);
		int maxRow = sheetSpPr.getLastRowNum();
		int row = 5;
		Cell cell;
		String str_cell = "";
		String egn = person.getEgn();

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

	@SuppressWarnings("unused")
	private static void colorInYelloFirst7cell(HSSFWorkbook workbook, int row) {
		HSSFCell cell;
		HSSFCellStyle style;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				cell = workbook.getSheetAt(i).getRow(row).getCell(j);
				System.out.println(row + "/" + i + "/" + j + " style " + cell);
				style = cell.getCellStyle();
				style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
				// and solid fill pattern produces solid grey cell fill
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//				style.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.index);
//				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cell.setCellStyle(style);
			}
		}

	}

	public static void removeOldRow(Workbook workbook, int rowIndex) {
		for (int i = 0; i < 4; i++) {
			Sheet sheet = workbook.getSheetAt(i);

			int lastRowNum = sheet.getLastRowNum();
			if (rowIndex >= 0 && rowIndex < lastRowNum) {
				sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
			}
			if (rowIndex == lastRowNum) {
				Row removingRow = sheet.getRow(rowIndex);
				if (removingRow != null) {
					sheet.removeRow(removingRow);
				}
			}
		}
	}

	private static void movePersonInWorkplaceArea(Workbook workbook, int row, int endRowOtdel, boolean isNewData,
			Person person) {
		int lastRow;

		System.out.println(person.getEgn());
		String[] kode = PersonelManegementMethods.getKodeStatusByPersonFromDBase(person);
		int[] rowByBefautStyle = getAreaOtdelSvoboden(  workbook);

		for (int i = 0; i < 4; i++) {
			Sheet worksheet = workbook.getSheetAt(i);

			lastRow = worksheet.getLastRowNum();
			worksheet.shiftRows(endRowOtdel, lastRow, 1);
			worksheet.createRow(endRowOtdel);

			Row sourceRow = worksheet.getRow(row);
			if(sourceRow == null) sourceRow = worksheet.createRow(row);
			Row newRow = worksheet.getRow(endRowOtdel);

			boolean withValues = false;
			if (isNewData) {
				sourceRow = worksheet.getRow(endRowOtdel - 1);
				if(sourceRow == null) sourceRow = worksheet.createRow(endRowOtdel - 1);

			} else {
				withValues = true;
			}
			CopyValueFromSourseRowToNewRow(worksheet, sourceRow, newRow, withValues, rowByBefautStyle); //да се промени
			newRow.getCell(0).setCellValue(kode[0]);
			newRow.getCell(1).setCellValue(kode[1]);
			newRow.getCell(2).setCellValue(kode[2]);
			newRow.getCell(3).setCellValue(kode[4]);
			newRow.getCell(4).setCellValue(kode[3]);
			newRow.getCell(5).setCellValue(person.getEgn());
			newRow.getCell(6)
					.setCellValue(person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName());
		}

	}

	
	public static void CopyValueFromSourseRowToNewRow(Sheet worksheet, Row sourceRow, Row newRow,
			boolean withValues, int[] rowByBefautStyle) {

		Cell newCell;
		for (int m = 0; m < sourceRow.getLastCellNum(); m++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(m);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				continue;
			} else {
				 newCell = newRow.createCell(m);
			}
			
			CopyValueFromOldCellToNewcell(oldCell,  newCell, withValues);
			System.out.println(rowByBefautStyle[1]+"/////////////////////////////////////////////////////////////");
			if(newCell.getColumnIndex()<7) {
				setDefautCellStyle(newCell,rowByBefautStyle[0], worksheet.getWorkbook());
			}
			}
		

	}

	public static void CopyValueFromOldCellToNewcell(Cell oldCell, Cell newCell, boolean withValues) {

			
			Sheet worksheet = newCell.getSheet();
			System.out.println("Copy style from");
			// Copy style from old cell and apply to new cell
//			CellStyle style = worksheet.getWorkbook().createCellStyle();
//			style = oldCell.getCellStyle();
//			style.cloneStyleFrom(oldCell.getCellStyle());
			
			newCell.setCellStyle(oldCell.getCellStyle());

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				if (withValues)
				copyCommentFromOldCellToNewcell(oldCell, newCell);
				
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				if (withValues)
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
//			        newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType().toString()) {
			case "BLANK":
				break;
			case "BOOLEAN":
				if (withValues)
					newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case "ERROR":
				if (withValues)
					newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case "FORMULA":

				int columnOfNewFormulaCell = newCell.getColumnIndex();
				int columnOfOldFormulaCell = oldCell.getColumnIndex();
				int rowOfNewFormulaCell = newCell.getRowIndex();
				int rowOfOldFormulaCell = oldCell.getRowIndex();

				int coldiff = columnOfNewFormulaCell - columnOfOldFormulaCell;
				int rowdiff = rowOfNewFormulaCell - rowOfOldFormulaCell;

				newCell.setCellFormula(copyFormula(worksheet, oldCell.getCellFormula(), coldiff, rowdiff));

				break;
			case "NUMERIC":
				if (withValues)
					newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case "STRING":
				if (withValues)
					newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			default:
				break;
			}
		

	}

	
	public static void copyCommentFromOldCellToNewcell(Cell oldCell, Cell newCell) {
		
		if(oldCell.getSheet().equals(newCell.getSheet())) {		
		newCell.setCellComment(oldCell.getCellComment());
		}else {
			Comment comment = oldCell.getCellComment();
			RichTextString oldrichTextString = comment.getString();
			createCellComment(oldrichTextString, newCell);
		}
		
	}

	public static String copyFormula(Sheet sheet, String formula, int coldiff, int rowdiff) {

		Workbook workbook = sheet.getWorkbook();
		HSSFEvaluationWorkbook evaluationWorkbook = null;

		evaluationWorkbook = HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook);

		Ptg[] ptgs = FormulaParser.parse(formula, (FormulaParsingWorkbook) evaluationWorkbook, FormulaType.CELL,
				sheet.getWorkbook().getSheetIndex(sheet));

		for (int i = 0; i < ptgs.length; i++) {
			if (ptgs[i] instanceof RefPtgBase) { // base class for cell references
				RefPtgBase ref = (RefPtgBase) ptgs[i];
				if (ref.isColRelative())
					ref.setColumn(ref.getColumn() + coldiff);
				if (ref.isRowRelative())
					ref.setRow(ref.getRow() + rowdiff);
			} else if (ptgs[i] instanceof AreaPtgBase) { // base class for range references
				AreaPtgBase ref = (AreaPtgBase) ptgs[i];
				if (ref.isFirstColRelative())
					ref.setFirstColumn(ref.getFirstColumn() + coldiff);
				if (ref.isLastColRelative())
					ref.setLastColumn(ref.getLastColumn() + coldiff);
				if (ref.isFirstRowRelative())
					ref.setFirstRow(ref.getFirstRow() + rowdiff);
				if (ref.isLastRowRelative())
					ref.setLastRow(ref.getLastRow() + rowdiff);
			}
		}

		formula = FormulaRenderer.toFormulaString((FormulaRenderingWorkbook) evaluationWorkbook, ptgs);
		return formula;
	}

	private static void saveBasicInfoPersonInExcelFile(Row[] infoForPerson, String[] kode, Person person) {

		for (int i = 0; i < 4; i++) {
			infoForPerson[i].getCell(0).setCellValue(kode[0]);
			infoForPerson[i].getCell(1).setCellValue(kode[1]);
			infoForPerson[i].getCell(2).setCellValue(kode[2]);
			infoForPerson[i].getCell(3).setCellValue(kode[4]);
			infoForPerson[i].getCell(4).setCellValue(kode[3]);
			infoForPerson[i].getCell(6)
					.setCellValue(person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName());

		}

	}

	static void createCellComment(Workbook workbook, Person peson) {

		if (commentText.length() > 0) {
			int rowPerson = searchRowPersonInWorkbook(workbook, peson);

			String authorText = workingUser.getNikName();
			authorText = authorText + ":";
			String commentString = authorText + "\n" + commentText;

			Font boldFont = workbook.createFont();
			boldFont.setFontName("Tahoma");
			boldFont.setFontHeightInPoints((short) 9);
			boldFont.setBold(true);

			Font commentFont = workbook.createFont();
			commentFont.setFontName("Tahoma");
			commentFont.setFontHeightInPoints((short) 9);
			commentFont.setBold(false);
			CreationHelper creationHelper = workbook.getCreationHelper();

			RichTextString richTextString = creationHelper.createRichTextString(commentString);
			richTextString.applyFont(commentFont);
			richTextString.applyFont(0, authorText.length(), boldFont);
			Cell cell;
//			ClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5);
			
			
			for (int i = 0; i < 4; i++) {

				cell = workbook.getSheetAt(i).getRow(rowPerson).getCell(6);
				try {
					// try to get the cell comment
					Comment comment = cell.getCellComment();
					
					if (comment == null) {
						// create a new comment
//						createdNewComment(richTextString, cell, anchor);
						createCellComment( richTextString, cell);

					} else {
						richTextString = updateComment(authorText, commentString, boldFont, commentFont, creationHelper,
								comment);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private static void createdNewComment(RichTextString richTextString, Cell cell, ClientAnchor anchor) {
		// create a new comment
		System.out.println(cell.getStringCellValue());
		@SuppressWarnings("rawtypes")
		Drawing drawing_master = cell.getSheet().createDrawingPatriarch();
	    Comment comment1 = (HSSFComment) drawing_master.createCellComment(anchor);
		   comment1.setString(richTextString);
		  cell.setCellComment(comment1);
	}

	
	
	@SuppressWarnings("unused")
	private static int getRowPerson(Workbook workbook, Person peson) {
		Cell cell;
		String str_cell;
		String egn = peson.getEgn();
		Sheet sheetSpPr = workbook.getSheetAt(0);
		int lastRow = sheetSpPr.getLastRowNum();
		int row = 0;
		for (int i = 5; i <= lastRow; i++) {
			if (sheetSpPr.getRow(i) != null) {
				cell = sheetSpPr.getRow(i).getCell(5);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					str_cell = ReadExcelFileWBC.getStringfromCell(cell);
					if (str_cell.equals(egn)) {
						row = i;
						i = lastRow;

					}
				}
			}
		}
		return row;
	}

	static void createCellComment( RichTextString richTextString, Cell cell) {
//	Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

		CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();

		Drawing<?> drawing = cell.getSheet().createDrawingPatriarch();

		// When the comment box is visible, have it show in a 1x3 space

		ClientAnchor anchor = factory.createClientAnchor();

		anchor.setCol1(cell.getColumnIndex());

		anchor.setCol2(cell.getColumnIndex() + 1);

		anchor.setRow1(cell.getRow().getRowNum());

		anchor.setRow2(cell.getRow().getRowNum() + 3);

		Comment comment = drawing.createCellComment(anchor);

		comment.setString(richTextString);

		cell.setCellComment(comment);

	}

	public static void setComment(String text, HSSFCell cell) {
		final Map<HSSFSheet, HSSFPatriarch> drawingPatriarches = new HashMap<HSSFSheet, HSSFPatriarch>();

		HSSFCreationHelper createHelper = cell.getSheet().getWorkbook().getCreationHelper();
		HSSFSheet sheet = (HSSFSheet) cell.getSheet();
		HSSFPatriarch drawingPatriarch = drawingPatriarches.get(sheet);
		if (drawingPatriarch == null) {
			drawingPatriarch = sheet.createDrawingPatriarch();
			drawingPatriarches.put(sheet, drawingPatriarch);
		}

		HSSFComment comment = drawingPatriarch
				.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		comment.setString(createHelper.createRichTextString(text));
		cell.setCellComment(comment);
	}

	private static RichTextString updateComment(String authorText, String commentString, Font boldFont,
			Font commentFont, CreationHelper creationHelper, Comment comment) {
		RichTextString richTextString;
		// apply author and text
//		  comment.setAuthor(authorText);
		RichTextString oldrichTextString = comment.getString();
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
	
	private static Row[] saveRowsWithInfoPreson(Workbook workbook, int row) {
		Row[] infoForPerson = new HSSFRow[4];
		for (int i = 0; i < 4; i++) {
			infoForPerson[i] = workbook.getSheetAt(i).getRow(row);

		}
		return infoForPerson;
	}

	private static int[] getAreaOtdel(Workplace workplace, Workbook workbook) {

		Sheet sheetSpPr = workbook.getSheetAt(3);
		int maxRow = sheetSpPr.getLastRowNum();
		int row = 5;
		int masiveRowOtdel[] = new int[2];
		boolean fl = false;
		Cell cell;
		String str_cell = "";
		while (row < maxRow) {

			if (sheetSpPr.getRow(row) != null) {

				cell = sheetSpPr.getRow(row).getCell(6);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					str_cell = ReadExcelFileWBC.getStringfromCell(cell);

					if (str_cell.equals(workplace.getOtdel()) || str_cell.equals(workplace.getSecondOtdelName())) {
						masiveRowOtdel[0] = row;
						fl = true;
					}

					if (fl && str_cell.equals("край")) {
						masiveRowOtdel[1] = row;
						row = maxRow;
					}
				}
			}
			row++;
		}
		return masiveRowOtdel;
	}

	private static boolean writeSpisPrilInRow(Spisak_Prilogenia spPril, Sheet sheetSpPr, int row) {
		boolean fl = false;
		int k = row;
		Cell cell = sheetSpPr.getRow(row).getCell(7);
		if (cell == null) {
			while (cell == null) {
				cell = sheetSpPr.getRow(k).getCell(7);
				k--;
			}
		}
		CellStyle newCellStyle = cell.getCellStyle();
		for (int m = 7; m <= 254; m += 3) {
			cell = sheetSpPr.getRow(row).getCell(m);

			if (cell == null || ReadExcelFileWBC.getStringfromCell(cell).trim().isEmpty()) {
				cell = sheetSpPr.getRow(row).createCell(m, CellType.STRING);
				cell.setCellValue(spPril.getFormulyarName());
				cell.setCellStyle(newCellStyle);
				cell = sheetSpPr.getRow(row).createCell(m + 1, CellType.STRING);
				cell.setCellValue(sdfrmt.format(spPril.getStartDate()));
				cell.setCellStyle(newCellStyle);
				cell = sheetSpPr.getRow(row).createCell(m + 2, CellType.STRING);
				cell.setCellValue(sdfrmt.format(spPril.getEndDate()));
				cell.setCellStyle(newCellStyle);
				fl = true;
				m = 255;

			}
		}
		return fl;
	}

	private static boolean checkSpisPrilInRow(Spisak_Prilogenia spPril, Sheet sheetSpPr, int row) {
		boolean fl = false;
		Cell cell, cell1, cell2;
		String str_cell, str_cell1, str_cell2;
		for (int m = 7; m <= 254; m += 3) {
			cell = sheetSpPr.getRow(row).getCell(m);
			cell1 = sheetSpPr.getRow(row).getCell(m + 1);
			cell2 = sheetSpPr.getRow(row).getCell(m + 2);
			if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)
					&& ReadExcelFileWBC.CellNOEmpty(cell2)) {
				str_cell = ReadExcelFileWBC.getStringfromCell(cell);
				str_cell1 = reformatDate(ReadExcelFileWBC.getStringfromCell(cell1));
				str_cell2 = reformatDate(ReadExcelFileWBC.getStringfromCell(cell2));
				if (str_cell.equals(spPril.getFormulyarName()) && str_cell1.equals(sdfrmt.format(spPril.getStartDate()))
						&& str_cell2.equals(sdfrmt.format(spPril.getEndDate()))) {
					fl = true;
					m = 255;
				}
			}
		}
		return fl;
	}

	private static String reformatDate(String stringfromCell) {
		String dateStr = "";
		Date date;
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		SimpleDateFormat sdfrmtt = new SimpleDateFormat("dd.MM.yyyy");
		try {
			if (stringfromCell.length() == 10) {
				date = sdfrmtt.parse(stringfromCell);
			} else {
				date = sdfrmt.parse(stringfromCell);
			}
			dateStr = sdfrmt.format(date);

		} catch (ParseException e) {
			System.out.println(stringfromCell);
			e.printStackTrace();
		}
		return dateStr;
	}
	
	static  String[] getmasive(){
		 String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		 String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
				
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
		filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
			
		 String filePath[] = {filePathPersonel, filePathExternal};
		return filePath;
	}

	
	private static int[] getAreaOtdelSvoboden( Workbook workbook) {
		
		
		Sheet sheetSpPr = workbook.getSheetAt(0);
		int maxRow = sheetSpPr.getLastRowNum();
		int row = 5;
		int masiveRowOtdel[] = new int[2];
		boolean fl = false;
		Cell cell;
		String str_cell = "";
		while (row < maxRow) {

			if (sheetSpPr.getRow(row) != null) {

				cell = sheetSpPr.getRow(row).getCell(6);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					str_cell = ReadExcelFileWBC.getStringfromCell(cell);

					if (str_cell.equals(defaltAreaName)) {
						masiveRowOtdel[0] = row+2;
						fl = true;
					}

					if (fl && str_cell.equals("край")) {
						masiveRowOtdel[1] = row-1;
						row = maxRow;
					}
				}
			}
			row++;
		}
		return masiveRowOtdel;
	}
	
	 private static void setDefautCellStyle(Cell newCell, int rowByBefautBWStyle, Workbook workbook) {
		 int column = newCell.getColumnIndex();
		 CellStyle style = workbook.getSheetAt(0).getRow(rowByBefautBWStyle).getCell(column).getCellStyle();
		 newCell.setCellStyle(style);
	}
	
	
}
