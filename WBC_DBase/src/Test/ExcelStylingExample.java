package Test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


import java.util.HashMap;
import java.util.Map;

public class ExcelStylingExample {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("D:\\PERSONNEL.xls");

        // Кеш за стилове
        Map<String, CellStyle> styleCache = new HashMap<>();

        // Дефиниране на стил (ако още не съществува)
        String key = "BOLD";
        CellStyle style = styleCache.get(key);
        if (style == null) {
            style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            styleCache.put(key, style);
        }

        // Използване на кеширан стил
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Bold Text");
        cell.setCellStyle(style);

        // Създаване на още една клетка със същия стил
        Cell anotherCell = row.createCell(1);
        anotherCell.setCellValue("Another Bold Text");
        anotherCell.setCellStyle(style);

        // Продължи с другите операции по създаване на Excel файла...

        // Запиши файла и освободи ресурси...

    }
}

