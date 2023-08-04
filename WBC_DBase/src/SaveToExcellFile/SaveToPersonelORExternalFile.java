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

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.EvaluationWorkbook;
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
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;

import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import BasiClassDAO.KodeStatusDAO;
import BasicClassAccessDbase.KodeStatus;
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

			writePersonToOtdel2(person, workbook);

			if(spPril != null) {
			writeFormulyarNameToOtdel(firmName, spPril, workbook);

			writeFormulyarNameToPersonRow(workbook, spPril, person);
			}
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

	static void writePersonToOtdel(Spisak_Prilogenia spPril, Person person, Workbook workbook) {

		int firstRowOtdel = firstAndLastRowForOtdel[0], endRowOtdel = firstAndLastRowForOtdel[1];
		Sheet sheetSpPr = workbook.getSheetAt(3);
		int maxRow = sheetSpPr.getLastRowNum();
		int row = 5;
		boolean fl = false;
		Cell cell;
		String str_cell = "";
		String egn = person.getEgn();
		while (row < maxRow) {
			if (sheetSpPr.getRow(row) != null) {

				cell = sheetSpPr.getRow(row).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					str_cell = ReadExcelFileWBC.getStringfromCell(cell);
					System.out.println(str_cell + " - " + egn);
					if (str_cell.equals(egn)) {
						fl = true;
						System.out.println("row1 " + row);
						Row[] infoPersonFromExcell = saveRowsWithInfoPreson(workbook, row);
						String[] kode = PersonelManegementMethods.getKodeStatusByPersonFromDBase(person);

						saveBasicInfoPersonInExcelFile(infoPersonFromExcell, kode, person);

						if (row < firstRowOtdel || row > endRowOtdel) {
							System.out.println("ЛИЦЕТО НЕ Е В ОБЛАСТТА НА ФИРМАТА И ТРЯБВА ДА СЕ ПРЕМЕСТИ");
//						ЛИЦЕТО НЕ Е В ОБЛАСТТА НА ФИРМАТА И ТРЯБВА ДА СЕ ПРЕМЕСТИ
							System.out.println("row2 " + row);
							if (row > endRowOtdel) {
								row++;
							}
							movePersonInWorkplaceArea(workbook, row, endRowOtdel, false, person);
							colorInYelloFirst7cell(workbook, endRowOtdel);
							removeOldRow(workbook, row);
						}

						row = maxRow;
					}
				}
			}
			row++;
		}
		if (!fl) {
			System.out.println("nowo ЛИЦЕТО ");
			movePersonInWorkplaceArea(workbook, row, endRowOtdel, true, person);

		}

	}

	static void writePersonToOtdel2(Person person, Workbook workbook) {
		int firstRowOtdel = firstAndLastRowForOtdel[0];
		int endRowOtdel = firstAndLastRowForOtdel[1];

		int row = searchRowPersonInWorkbook(workbook, person);

		boolean movePerson = false;
		boolean isNewData = true;
		if (row > 0) {
			isNewData = false;
			saveNewInfoByPersonInExcelFile(person, workbook, row);

			if (row < firstRowOtdel || row > endRowOtdel) {
				movePerson = true;
				System.out.println("ЛИЦЕТО НЕ Е В ОБЛАСТТА НА ФИРМАТА И ТРЯБВА ДА СЕ ПРЕМЕСТИ");
				if (row > endRowOtdel) {
					row++;
				}
			}
		}

		if(movePerson) {
		movePersonInWorkplaceArea(workbook, row, endRowOtdel, isNewData, person);
		if(!isNewData) {
		colorInYelloFirst7cell(workbook, endRowOtdel);
		removeOldRow(workbook, row);
		}
		}
	}

	private static void saveNewInfoByPersonInExcelFile(Person person, Workbook workbook, int row) {
		Row[] rowsWithInfoPersonFromExcell = saveRowsWithInfoPreson(workbook, row);

		String[] kode = PersonelManegementMethods.getKodeStatusByPersonFromDBase(person);

		if (commentText.length() > 0) {
			createCellComment(workbook, rowsWithInfoPersonFromExcell);
		}

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
					str_cell = ReadExcelFileWBC.getStringfromCell(cell);
					System.out.println(str_cell + " - " + egn);
					if (str_cell.equals(egn)) {
						return row;
					}
				}
			}
			row++;
		}
		return 0;
	}

	private static void colorInYelloFirst7cell(Workbook workbook, int row) {
		Cell cell;
		CellStyle style;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				cell = workbook.getSheetAt(i).getRow(row).getCell(j);
				System.out.println(row + "/" + i + "/" + j + " style " + cell);
				style = cell.getCellStyle();
				style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
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
			newRow.getCell(6).setCellValue(
					person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName());
		}

	}

	private static void CopyValueFromOldCellToNewcell(Sheet worksheet, Row sourceRow, Row newRow, boolean withValues) {
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
		EvaluationWorkbook evaluationWorkbook = null;

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

	@SuppressWarnings("unused")
	private static Comment createComment(Workbook workbook, String textComment, int sheetIndex, Cell cel) {

		int row = cel.getRowIndex();
		int cellColumn = cel.getColumnIndex();
		HSSFPatriarch hpt = (HSSFPatriarch) workbook.getSheetAt(sheetIndex).createDrawingPatriarch();
		HSSFCell cell1 = (HSSFCell) workbook.getSheetAt(4).createRow(row).createCell(cellColumn);
		cell1.setCellValue("Excel Comment Example");
		// Setting size and position of the comment in worksheet
		HSSFComment comment = hpt.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// Setting comment text
		comment.setString(new HSSFRichTextString(textComment));
		// Associating comment to the cell
		cell1.setCellComment(comment);

		comment.setAuthor(comment.getAuthor());
		return comment;
	}

	static void createCellComment(Workbook workbook, Row[] infoForPerson) {

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
		for (int i = 0; i < 4; i++) {
			cell = infoForPerson[i].getCell(6);
			try {

				// try to get the cell comment
				Comment comment = cell.getCellComment();
				if (comment == null) { // create a new comment
					HSSFPatriarch hpt = (HSSFPatriarch) cell.getSheet().createDrawingPatriarch();
					// Setting size and position of the comment in worksheet
					System.out.println("comment " + cell.getSheet().getSheetName() + " - " + cell.getColumnIndex() + "/"
							+ cell.getRowIndex());
					HSSFClientAnchor klA = new HSSFClientAnchor(0, 0, 1, 1, (short) (cell.getColumnIndex() + 1),
							cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 4),
							(cell.getRow().getRowNum() + 3));
					HSSFComment commentH = hpt.createComment(klA);
					// Setting comment text
					commentH.setAuthor(authorText);
					commentH.setString(richTextString);
					// Associating comment to the cell
					cell.setCellComment(commentH);
				} else {
					// apply author and text
//		  comment.setAuthor(authorText);
					comment.setString(richTextString);
				}
			} catch (IllegalArgumentException e) {
			}
		}
	}

	static void create2CellComment(Workbook workbook, Cell cell, String authorText, String commentText) {

		int offsetX = 0;
		int offsetY = 0;

		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		Font commentFont = boldFont;
		commentFont.setFontName("Tahoma");
		commentFont.setFontHeightInPoints((short) 9);

		boldFont.setFontName("Tahoma");
		boldFont.setFontHeightInPoints((short) 9);
		boldFont.setBold(true);

		// create the text content
		CreationHelper creationHelper = cell.getSheet().getWorkbook().getCreationHelper();
		String commentString = authorText + "\n" + commentText;
		RichTextString richTextString = creationHelper.createRichTextString(commentString);
		richTextString.applyFont(commentFont);
		richTextString.applyFont(0, authorText.length(), boldFont);
		// try to get the cell comment
		Comment comment = cell.getCellComment();
		if (comment == null) { // create a new comment
			// create the anchor
			ClientAnchor anchor = creationHelper.createClientAnchor();
			anchor.setCol1(cell.getColumnIndex());
			anchor.setCol2(cell.getColumnIndex() + offsetX);
			anchor.setRow1(cell.getRow().getRowNum());
			anchor.setRow2(cell.getRow().getRowNum() + offsetY);
			// create the comment and set it to the cell
			@SuppressWarnings("rawtypes")
			Drawing drawing = cell.getSheet().createDrawingPatriarch();
			comment = drawing.createCellComment(anchor);
			cell.setCellComment(comment);
		}
		// apply author and text
//		  comment.setAuthor(authorText);
		comment.setString(richTextString);
	}

	static void duplicateCellComment(Workbook workbook, Cell cell, String authorText, String commentText) {

		Font boldFont = workbook.getFontAt(0);
		Font commentFont = workbook.getFontAt(0);
		commentFont.setFontName("Tahoma");
		commentFont.setFontHeightInPoints((short) 9);

		boldFont.setFontName("Tahoma");
		boldFont.setFontHeightInPoints((short) 9);
		boldFont.setBold(true);

		Cell originCel = workbook.getSheetAt(3).getRow(0).getCell(0);
		// create the text content
		CreationHelper creationHelper = cell.getSheet().getWorkbook().getCreationHelper();
		authorText = authorText + ":";
		String commentString = authorText + "\n" + commentText;
		RichTextString richTextString = creationHelper.createRichTextString(commentString);
		richTextString.applyFont(commentFont);
		richTextString.applyFont(0, authorText.length(), boldFont);
		// try to get the cell comment
		Comment comment = cell.getCellComment();
		Comment commentoriginal;
		if (comment == null) { // create a new comment
			commentoriginal = originCel.getCellComment();
			comment = commentoriginal;
			originCel.setCellComment(commentoriginal);
			cell.setCellComment(comment);
		}
		// apply author and text
		comment.setAuthor(authorText);
		comment.setString(richTextString);
	}

	
	private static Row[] saveRowsWithInfoPreson(Workbook workbook, int row) {
		Row[] infoForPerson = new Row[4];
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

					if (str_cell.equals(workplace.getOtdel())
							|| str_cell.equals(workplace.getSecondOtdelName())) {
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
						&& str_cell2.equals(sdfrmt.format(spPril.getStartDate()))) {
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
