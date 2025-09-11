package Test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

import java.io.IOException;

public class ExtractCellStyles {

    public static void main(String[] args) {
        String excelFilePath = "d:\\0_LB\\EXTERNAL.xls";  // Път към твоя Excel файл

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new HSSFWorkbook(fis)) {

            extractCellStyle(workbook);
//            addNewRowInFile( workbook, excelFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
	private static void extractCellStyle(Workbook workbook) {
		// Извличане на броя на стиловете в workbook-а
		int numberOfStyles = workbook.getNumCellStyles();

		System.out.println("Total Number of Styles: " + numberOfStyles);

		// Преглед на всеки стил
//		for (int i = 0; i < numberOfStyles; i++) {
//		    CellStyle style = workbook.getCellStyleAt(i);
//		    System.out.println("Style " + i + ":");
//
//		    // Извличане и извеждане на информация за шрифта
//		    Font font = workbook.getFontAt(style.getFontIndexAsInt());
//		    System.out.println("  Font:");
//		    System.out.println("    Font Name: " + font.getFontName());
//		    System.out.println("    Font Size: " + font.getFontHeightInPoints());
//		    System.out.println("    Bold: " + font.getBold());
//		    System.out.println("    Italic: " + font.getItalic());
//		    System.out.println("    Underline: " + font.getUnderline());
//		    System.out.println("    Font Color: " + font.getColor());
//
//		    // Извеждане на други стилови атрибути
//		    System.out.println("  Alignment: " + style.getAlignment());
//		    System.out.println("  Vertical Alignment: " + style.getVerticalAlignment());
//		    System.out.println("  Fill Foreground Color: " + style.getFillForegroundColor());
//		    System.out.println("  Fill Background Color: " + style.getFillBackgroundColor());
//		    System.out.println("  Border Top: " + style.getBorderTop());
//		    System.out.println("  Border Bottom: " + style.getBorderBottom());
//		    System.out.println("  Border Left: " + style.getBorderLeft());
//		    System.out.println("  Border Right: " + style.getBorderRight());
//
//		    System.out.println();
//		}
	}
}
