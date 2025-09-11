package ResultFromMeasuringReference;


import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class DoubleCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat formatta = new  DecimalFormat("#0.00");
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 if( value instanceof Double) {
			System.out.println("2");
			Double d = (Double) value;
			value = formatta.format(d).replace(",", ".");
			if(column > 5) {
			if(d.isNaN() || d == 0.0  ) {
				value = "";
			}else {
				System.out.println("3");
			value = formatta.format(d).replace(",", ".");
			}
			}
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
	}
}
