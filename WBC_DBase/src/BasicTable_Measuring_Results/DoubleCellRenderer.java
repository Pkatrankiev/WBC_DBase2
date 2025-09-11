package BasicTable_Measuring_Results;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DoubleCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat formatta = new  DecimalFormat("#0.00");
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 if( value instanceof Double) {
			Double d = (Double) value;
			value = formatta.format(d).replace(",", ".");
			if(column > 5) {
			if(d.isNaN() || d == 0.0  ) {
				value = "";
			}else {
			value = formatta.format(d).replace(",", ".");
			}
			}
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
	}
}