package SaveToExcellFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

	private static String filePath[] = {
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig2"),
			ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig2") };

	private static SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");

	private static int firstAndLastRowForOtdel[];
	static int curentYear = Calendar.getInstance().get(Calendar.YEAR);
	static UsersWBC workingUser;
	static String commentText;

	public static void saveInfoPersonToExcelFile(Person person, String firmName, Spisak_Prilogenia spPril,
			UsersWBC user, String comment, Workplace workplace) {
		String falseData = ReadFileBGTextVariable.getGlobalTextVariableMap().get("falseData");
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String notSelectFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notSelectFile");

		workingUser = user;
		commentText = comment;

		String pathFile = filePath[1];
		if (firmName.equals("АЕЦ Козлодуй")) {
			pathFile = filePath[0];
		}

		try {
			FileInputStream inputStream = new FileInputStream(pathFile);
			Workbook workbook = new HSSFWorkbook(inputStream);

			firstAndLastRowForOtdel = getAreaOtdel(workplace, workbook);
			
			writePersonToOtdel(person, workbook);

			if (spPril != null) {
				writeFormulyarNameToOtdel(firmName, spPril, workbook);

				writeFormulyarNameToPersonRow(workbook, spPril, person);
			}

			createCellComment(workbook, person);

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

	static void writeFormulyarNameToOtdel(String firmName, Spisak_Prilogenia spPril, Workbook workbook) {

		Sheet sheetSpPr = workbook.getSheetAt(3);

		firstAndLastRowForOtdel = getAreaOtdel(spPril.getWorkplace(), workbook);
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
			CopyValueFromOldCellToNewcell(worksheet, sourceRow, newRow, withValues);
			newRow.getCell(0).setCellValue(kode[0]);
			newRow.getCell(1).setCellValue(kode[1]);
			newRow.getCell(2).setCellValue(kode[2]);
			newRow.getCell(3).setCellValue(kode[3]);
			newRow.getCell(4).setCellValue(kode[4]);
			newRow.getCell(5).setCellValue(person.getEgn());
			newRow.getCell(6)
					.setCellValue(person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName());
		}

	}

	
	private static void CopyValueFromOldCellToNewcell(Sheet worksheet, Row sourceRow, Row newRow,
			boolean withValues) {
		int coldiff;
		int rowdiff;
		int columnOfNewFormulaCell;
		int columnOfOldFormulaCell;
		int rowOfNewFormulaCell;
		int rowOfOldFormulaCell;
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

			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = worksheet.getWorkbook().createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
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

				columnOfNewFormulaCell = newCell.getColumnIndex();
				columnOfOldFormulaCell = oldCell.getColumnIndex();
				rowOfNewFormulaCell = newCell.getRowIndex();
				rowOfOldFormulaCell = oldCell.getRowIndex();

				coldiff = columnOfNewFormulaCell - columnOfOldFormulaCell;
				rowdiff = rowOfNewFormulaCell - rowOfOldFormulaCell;

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

	}

	private static String copyFormula(Sheet sheet, String formula, int coldiff, int rowdiff) {

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
			infoForPerson[i].getCell(3).setCellValue(kode[3]);
			infoForPerson[i].getCell(4).setCellValue(kode[4]);
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
			ClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5);
			
			
			for (int i = 0; i < 4; i++) {

				cell = workbook.getSheetAt(i).getRow(rowPerson).getCell(6);
				try {
					// try to get the cell comment
					Comment comment = cell.getCellComment();
					
					if (comment == null) {
						// create a new comment
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
	}

	private static void createdNewComment(RichTextString richTextString, Cell cell, ClientAnchor anchor) {
		// create a new comment
		System.out.println(cell.getStringCellValue());
		@SuppressWarnings("rawtypes")
		Drawing drawing_master = cell.getSheet().createDrawingPatriarch();
	    Comment comment1 = (HSSFComment) drawing_master.createCellComment(anchor);
		   comment1.setString(richTextString);
		  cell.setCellComment(comment1);
	}

	
	
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

	static void createCellComment2(HSSFWorkbook workbook, String authorText, HSSFRichTextString richTextString,
			HSSFCell cell) {
//	Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

		HSSFCreationHelper factory = workbook.getCreationHelper();

		HSSFPatriarch drawing = cell.getSheet().createDrawingPatriarch();

		// When the comment box is visible, have it show in a 1x3 space

		HSSFClientAnchor anchor = factory.createClientAnchor();

		anchor.setCol1(cell.getColumnIndex());

		anchor.setCol2(cell.getColumnIndex() + 1);

		anchor.setRow1(cell.getRow().getRowNum());

		anchor.setRow2(cell.getRow().getRowNum() + 3);

		// Create the comment and set the text+author

		HSSFComment comment = drawing.createCellComment(anchor);

		HSSFRichTextString str = factory.createRichTextString("Hello, World!");

		comment.setString(str);

		comment.setAuthor("Apache POI");

		// Assign the comment to the cell

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

}
