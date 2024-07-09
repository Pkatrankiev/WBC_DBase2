package TestDatePicker;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField.AbstractFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
 

public class DateLabelFormatterUser extends AbstractFormatter  {
	 
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
     
    @FXML
    private DatePicker date;

    
    
    //In the initialize method you can use the following code:

    public void initialize(URL location, ResourceBundle resources) {
        date.setOnShowing(e -> Locale.setDefault(Locale.Category.FORMAT, Locale.UK));
        //...
    }
    
    
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }
 
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
         
        return "";
    }


	public void setOnShowing(Object object) {
		// TODO Auto-generated method stub
		
	}




	
   
    
}