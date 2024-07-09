package TestDatePicker;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;


import javafx.scene.control.DatePicker;
import net.sourceforge.jdatepicker.JDatePanel;

@SuppressWarnings("serial")
public class DatePickerController extends JDatePickerDemo  {
   
    public DatePickerController(JDatePanel datePanel, AbstractFormatter formatter) {
		super();
		// TODO Auto-generated constructor stub
	}


	private DatePicker datePicker;
   
    

    
    String datePicker(ActionEvent evt){
        LocalDate localDate = datePicker.getValue();
        String pattern = "MMMM dd, yyyy";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        System.out.println("*77777*"+ datePattern);
		return datePattern;
    }

}

