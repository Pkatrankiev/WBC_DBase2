package TestDatePicker;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import DatePicker.DatePicker;
import javafx.scene.control.ComboBoxBase;

import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;




/**
 * This program demonstrates how to use JDatePicker to display a calendar 
 * component in a Swing program.
 * @author www.codejava.net
 *
 */
public class JDatePickerDemo extends JFrame implements ActionListener {
	
	private DatePicker datePicker;
	private JLabel lab;
	
	@SuppressWarnings("static-access")
	public JDatePickerDemo() {
		super("Calendar Component Demo");
		setLayout(new FlowLayout());
		lab = new JLabel("12.06.2024");
		add(lab);
//		datePicker.setDefaultLocale(Locale.PRC);
		UtilCalendarModel model = new UtilCalendarModel();
//		model.setDate(1990, 8, 24);
		model.setSelected(true);
		
		Properties p = new Properties();
		
//		UtilCalendarModel model = new UtilCalendarModel();
//		SqlDateModel model = new SqlDateModel();
		
//		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
//		datePicker = new JDatePickerImpl(datePanel);
		
//		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatterUser());
		
		
		
		
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(this);
		
		add(buttonOK);
		
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JDatePickerDemo().setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		Point pointFrame = getLocation();
		
		
		
		final JFrame f = new JFrame();
		DatePicker dPicer = new DatePicker(f, false, lab.getText(), pointFrame);
		lab.setText(dPicer.setPickedDate(false));
		
		
		
		
		
		
		
		
		
		// for UtilDateModel, the value returned is of type java.util.Date
//		Date selectedDate = (Date) datePicker.getModel().getValue();
		
		// for UtilCalendarModel, the value returned is of type java.util.Calendar
//		Calendar selectedValue = (Calendar) datePicker.getModel().getValue();
//		Date selectedDate = selectedValue.getTime();

		// for SqlDateModel, the value returned is of type java.sql.Date
//		java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
		
//		JOptionPane.showMessageDialog(this, "The selected date is2 " + selectedDate);
	}

}