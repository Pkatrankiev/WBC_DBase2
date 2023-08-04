package PersonReference;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasicClassAccessDbase.Person;

public class PersonReferenceExportToExcell {

	public static void btnExportTableToExcell(Object[][] dataTable, String[] columnNames, JPanel panel_Btn) {

		GeneralMethods.setWaitCursor(panel_Btn);
	
		String excelFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir")
				+ "exportTable.xls";
		try {
			if (dataTable.length * dataTable[0].length < 4000) {

				Workbook workbook = new HSSFWorkbook();
				Sheet sheet = workbook.createSheet("PersonReference");

				Cell cell = null;

				Font fontHeader = workbook.createFont();
				fontHeader.setColor(IndexedColors.BLACK.getIndex());
				fontHeader.setBold(true);

				CellStyle cellStyleHeader = cellStyleHeader(workbook);
				insertBorder(cellStyleHeader);

//				Create header column
				Row row = sheet.createRow(0);

				int excelColumnCount = 0;
				for (int tableColumCount = 0; tableColumCount < columnNames.length; tableColumCount++) {

					cell = row.createCell(excelColumnCount, CellType.STRING);
					cell.setCellStyle(cellStyleHeader);
					cell.setCellValue(columnNames[tableColumCount]);
					sheet.autoSizeColumn(excelColumnCount);
					sheet.setColumnWidth(excelColumnCount, sheet.getColumnWidth(excelColumnCount) + 1000);

					excelColumnCount++;

				}

//				Create column	
				DataFormat format = workbook.createDataFormat();
				CellStyle cellStyleDouble = workbook.createCellStyle();
				cellStyleDouble.setDataFormat(format.getFormat("0.00E+00"));

				for (int excellrow = 0; excellrow < dataTable.length; excellrow++) {
					row = sheet.createRow(excellrow + 1);
					for (int excellColumn = 0; excellColumn < dataTable[0].length; excellColumn++) {

						cell = row.createCell(excellColumn, CellType.STRING);
						cell.setCellValue(dataTable[excellrow][excellColumn].toString());

						setBordrCell(cell, workbook);

					}

				}

				sheet.createFreezePane(0, 1);
				sheet.setAutoFilter(new CellRangeAddress(0, 1, 0, excelColumnCount - 1));

				FileOutputStream outFile = new FileOutputStream(new File(excelFilePath));
				workbook.write(outFile);
				outFile.close();

				openWordDoc(excelFilePath);
			} else {
				MessageDialog(ReadFileBGTextVariable.getGlobalTextVariableMap().get("cell_maximum_number_exceeded"),
						"файлова грешка");
			}

		} catch (FileNotFoundException e) {
			ResourceLoader.appendToFile(e);
			MessageDialog(e.toString(), "файлова грешка");
		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		
		GeneralMethods.setDefaultCursor(panel_Btn);
	}

	public static void btnExportInfoPersonToExcell(Person person, String[] masivePersonStatusName, String[][] masivePersonStatus,
			String[] zoneNameMasive, String[][] masiveKode,  String[] masiveMeasurName, String[][] masiveMeasur, JPanel panel_Btn) {

		GeneralMethods.setWaitCursor(panel_Btn);
	
		String excelFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir")
				+ "exportInfoPerson.xls";
		try {
			int size =0;
			if(masivePersonStatus.length>0)
			size = masivePersonStatus.length * masivePersonStatus[0].length;
			if(masiveKode.length>0)
			size = size +masiveKode.length * masiveKode[0].length;
			if(masiveMeasur.length>0)
			size = size +masiveMeasur.length * masiveMeasur[0].length;
			if ( size < 4000) {

				Workbook workbook = new HSSFWorkbook();
				Sheet sheet = workbook.createSheet("PersonReference");

				CellStyle cellStyleBold = cellStyleBold(workbook);
				int endRow = 0;
				Row row = sheet.createRow(endRow);
				sheet.addMergedRegion(new CellRangeAddress(endRow, endRow, 0, 1));
				Cell cell = row.createCell(0, CellType.STRING);
				cell.setCellStyle(cellStyleBold);
				cell.setCellValue(person.getEgn());
				
				sheet.addMergedRegion(new CellRangeAddress(endRow, endRow, 2, 5));
				cell = row.createCell(2, CellType.STRING);
				cell.setCellStyle(cellStyleBold);
				cell.setCellValue(InsertMeasurToExcel.getNamePerson(person));
				endRow++;
				endRow++;
				
				row = sheet.createRow(endRow);
				sheet.addMergedRegion(new CellRangeAddress(endRow, endRow, 0, 1));
				cell = row.createCell(0, CellType.STRING);
				cell.setCellStyle(cellStyleBold);
				cell.setCellValue(ReadFileBGTextVariable.getGlobalTextVariableMap().get("code"));
				endRow++;
								
				endRow = writeCells(sheet, cellStyleBold, zoneNameMasive, masiveKode, endRow, false);
				endRow++;
								
				row = sheet.createRow(endRow);
				sheet.addMergedRegion(new CellRangeAddress(endRow, endRow, 0, 1));
				cell = row.createCell(0, CellType.STRING);
				cell.setCellStyle(cellStyleBold);
				cell.setCellValue(ReadFileBGTextVariable.getGlobalTextVariableMap().get("orders"));
				endRow++;
								
				endRow = writeCells(sheet, cellStyleBold,masivePersonStatusName, masivePersonStatus, endRow, false);
				endRow++;
								
				row = sheet.createRow(endRow);
				sheet.addMergedRegion(new CellRangeAddress(endRow, endRow, 0, 1));
				cell = row.createCell(0, CellType.STRING);
				cell.setCellStyle(cellStyleBold);
				cell.setCellValue(ReadFileBGTextVariable.getGlobalTextVariableMap().get("measurSICH"));
				endRow++;
								
				endRow = writeCells(sheet, cellStyleBold, masiveMeasurName, masiveMeasur, endRow, false);

				
				FileOutputStream outFile = new FileOutputStream(new File(excelFilePath));
				workbook.write(outFile);
				outFile.close();

				openWordDoc(excelFilePath);
			} else {
				MessageDialog(ReadFileBGTextVariable.getGlobalTextVariableMap().get("cell_maximum_number_exceeded"),
						"файлова грешка");
			}

		} catch (FileNotFoundException e) {
			ResourceLoader.appendToFile(e);
			MessageDialog(e.toString(), "файлова грешка");
		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		
		GeneralMethods.setDefaultCursor(panel_Btn);
	}

	private static CellStyle cellStyleHeader(Workbook workbook) {
		Font fontHeader = workbook.createFont();
		fontHeader.setColor(IndexedColors.BLACK.getIndex());
		fontHeader.setBold(true);

		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(fontHeader);
		cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
		cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyleHeader.setWrapText(true);
		return cellStyleHeader;
	}

	public static CellStyle cellStyleBold(Workbook workbook) {
		Font fontHeader = workbook.createFont();
		fontHeader.setColor(IndexedColors.BLACK.getIndex());
		fontHeader.setBold(true);

		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(fontHeader);
		cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
		cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleHeader.setWrapText(true);
		return cellStyleHeader;
	}
	
	public static int writeCells(Sheet sheet, CellStyle cellStyleHeader, String[] columnNames, String[][] dataTable,  int startRow, boolean fromMeasuringReference) {
		Cell cell;
		//				Create header column
						Row row = sheet.createRow(startRow);
		
						int excelColumnCount = 0;
						for (int tableColumCount = 0; tableColumCount < columnNames.length; tableColumCount++) {
		
							cell = row.createCell(excelColumnCount, CellType.STRING);
							cell.setCellStyle(cellStyleHeader);
							cell.setCellValue(columnNames[tableColumCount]);
							sheet.autoSizeColumn(excelColumnCount);
							sheet.setColumnWidth(excelColumnCount, sheet.getColumnWidth(excelColumnCount) + 1000);
		
							excelColumnCount++;
		
						}
		
		//				Create column	
					
						startRow++;
						int cc = dataTable[0].length;
						for (int tableRow = 0; tableRow < dataTable.length; tableRow++) {
							row = sheet.createRow(startRow);
							for (int tableColumCount = 0; tableColumCount < cc; tableColumCount++) {
		
								if(fromMeasuringReference && tableColumCount == cc-1) {
									String[] str = dataTable[tableRow][tableColumCount].split(" ");
									int k = tableColumCount;
									for (String string : str) {
										cell = row.createCell(k, CellType.STRING);
										cell.setCellValue(string);
										k++;
									}
								}else {
								cell = row.createCell(tableColumCount, CellType.STRING);
								cell.setCellValue(dataTable[tableRow][tableColumCount]);
								}
		
							}
							startRow++;	
						}
						
		return startRow;
	}

	
	public static void openWordDoc(String destinationDir) throws IOException {
		// Process p = Runtime.getRuntime().exec("rundll32
		// url.dll,FileProtocolHandler"+destinationDir);
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(new File(destinationDir));
			}
			// p.waitFor();
		} catch (IOException ioe) {
			ResourceLoader.appendToFile(ioe);
			ioe.printStackTrace();
		}

		System.out.println(destinationDir);
	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	public static Cell setBordrCell(Cell cell, Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		cell.setCellStyle(style);

		return cell;

	}

	public static CellStyle insertBorder(CellStyle style) {

		style.setBorderBottom(BorderStyle.DOUBLE);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		return style;

	}

	
}
