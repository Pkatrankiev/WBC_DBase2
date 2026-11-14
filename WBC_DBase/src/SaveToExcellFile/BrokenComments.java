package SaveToExcellFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFAnchor;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellReference;

public class BrokenComments {

    @SuppressWarnings("unchecked")
	public static String BrokenCommentCells(HSSFWorkbook wb) {
    	System.out.println("Workbook "+wb.getNameName(0));
    	String text = "";
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
        	
            HSSFSheet sheet = wb.getSheetAt(i);
       HSSFPatriarch patriarch = sheet.getDrawingPatriarch();
        
        if (patriarch == null) {
        	return "Няма коментари в листа";
            
        }

        try {
            Field shapesField;
            try {
                shapesField = HSSFPatriarch.class.getDeclaredField("_shapes");
            } catch (NoSuchFieldException e) {
                shapesField = HSSFPatriarch.class.getDeclaredField("shapes");
            }
            shapesField.setAccessible(true);
            List<HSSFShape> mutableList = (List<HSSFShape>) shapesField.get(patriarch);

            for (HSSFShape shape : new ArrayList<>(mutableList)) {
                if (shape instanceof HSSFComment) {
                    HSSFComment comment = (HSSFComment) shape;
                    boolean broken = false;
                    try {
                    	comment.getRow();
                       
                    } catch (Exception ex) {
                        broken = true;
                    }

                    if (broken) {
                        // 1. Опит 1 - през Anchor-а - там е истинската позиция
                        int row = -1, col = -1;
                        try {
                            HSSFAnchor anchor = comment.getAnchor();
                            if (anchor instanceof HSSFClientAnchor) {
                                HSSFClientAnchor ca = (HSSFClientAnchor) anchor;
                                row = ca.getRow1();
                                col = ca.getCol1();
                            }
                        } catch (Exception ignore) {}

                        String addr = "UNKNOWN";
                        if (row >= 0 && col >= 0) {
                            addr = new CellReference(row, col).formatAsString();
                            text += "Счупен коментар на клетка: " + addr + 
                                               " [row=" + row + ", col=" + col + "]" +
                                               " Author=" + safeGet(comment)+"/n";
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
		return text;
    }

    private static String safeGet(HSSFComment c) {
        try { return c.getAuthor(); } catch (Exception e) { return "unknown"; }
    } 
 
}