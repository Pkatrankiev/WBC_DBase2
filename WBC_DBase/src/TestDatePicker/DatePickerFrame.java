package TestDatePicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JWindow;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


public class DatePickerFrame {

	public static String datePickerr() {

		String dat = "";
		JWindow frame = new JWindow();
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		JPanel pan = new JPanel();
		pan.setOpaque(false);
		frame.setContentPane(pan);
		
		String PATTERN_DATE  = "dd.MM.yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Locale locale = new Locale("bg");
		UtilDateModel model = new UtilDateModel();
		
		model.setDate(2024, 05, 25 );
// Need this...
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
				

		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatterUser() );
		datePicker.setPreferredSize(new Dimension(105, 25));
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.setTextEditable(true);


		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date selectedDate =  (Date) datePicker.getModel().getValue();
				String dat = "";
				System.out.println("*44*"+ selectedDate);	
				if(selectedDate != null ) dat = sdf.format(selectedDate);
				System.out.println("**"+ dat);
				frame.dispose();
				}

			});
		
		frame.getContentPane().add(datePicker);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		
		
		
		
	
		
		return dat;
	}

}
