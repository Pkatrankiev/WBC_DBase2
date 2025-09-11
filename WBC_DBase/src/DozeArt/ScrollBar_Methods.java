package DozeArt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class ScrollBar_Methods {

	private static int max_countToPrePrevios;
	private static int max_countToPrevios;
	private static  int max;
	private static  int min;
	private static String firstData;
	
	
	static String setDaysAndCountDays() {

		String errorStr = "";

		int max;
		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();

		String count_DayToPrevios = "";
		String count_DayToPrePrevios = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date date_MeasurNaw = null;
		Date dateMeasurPrev = null;
		Date dateMeasurPrePrev = null;
		String Date_MeasurNawStr = textField_Date_MeasurNaw.getText();
		String dateMeasurPrevStr = textField_dateMeasurPrev.getText();
		String dateMeasurPrePrevStr = textField_dateMeasurPrePrev.getText();

		long diff = 0;
		long daysBetweenLong = 0;
		
//		peroid Naw to Previos
		
		JLabel lbl_DateNawToPrevios = DozeArtFrame.getLbl_DateNawToPrevios();
		JLabel lbl_DateToPrevios = DozeArtFrame.getLbl_DateToPrevios();
		JLabel lbl_Count_DayToPrevios = DozeArtFrame.getLbl_Count_DayToPrevios();
		
		if (!Date_MeasurNawStr.isEmpty() && !dateMeasurPrevStr.isEmpty()) {
			try {
				date_MeasurNaw = sdf.parse(Date_MeasurNawStr);
				dateMeasurPrev = sdf.parse(dateMeasurPrevStr);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			diff = date_MeasurNaw.getTime() - dateMeasurPrev.getTime();
			daysBetweenLong = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			count_DayToPrevios = Long.toString(daysBetweenLong);

			if (daysBetweenLong > 700) {
				errorStr = count_DayToPrevios;
			}

		}

		lbl_DateNawToPrevios.setText(Date_MeasurNawStr);
		lbl_DateToPrevios.setText(dateMeasurPrevStr);
		lbl_Count_DayToPrevios.setText(count_DayToPrevios);
		
		if(!count_DayToPrevios.isEmpty()) {
			 max =  Integer.parseInt(count_DayToPrevios);
				max_countToPrevios = max;
			}
		
		System.out.println("*********************************************");	
		System.out.println("Date_MeasurNawStr "+Date_MeasurNawStr);	
		System.out.println("date_MeasurNaw "+date_MeasurNaw);
		System.out.println("dateMeasurPrevStr "+dateMeasurPrevStr);
		System.out.println("dateMeasurPrev "+dateMeasurPrev);
		System.out.println("diff "+diff);
		System.out.println("daysBetweenLong "+daysBetweenLong);
		System.out.println("count_DayToPrevios "+count_DayToPrevios);
		System.out.println("daysBetweenLong "+daysBetweenLong);
		System.out.println("*********************************************");
		
		
//		peroid Naw to Previos	
		JLabel lbl_DatePreviosToPrePrevios = DozeArtFrame.getLbl_DateNawToPrePrevios();
		JLabel lbl_DateToPrePrevios = DozeArtFrame.getLbl_DateToPrePrevios();
		JLabel lbl_Count_DayToPrePrevios = DozeArtFrame.getLbl_Count_DayToPrePrevios();
		
		if (!Date_MeasurNawStr.isEmpty() && !dateMeasurPrePrevStr.isEmpty()) {
			try {
				dateMeasurPrev = sdf.parse(Date_MeasurNawStr);
				dateMeasurPrePrev = sdf.parse(dateMeasurPrePrevStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			diff = dateMeasurPrev.getTime() - dateMeasurPrePrev.getTime();
			daysBetweenLong = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			count_DayToPrePrevios = Long.toString(daysBetweenLong);
			if (daysBetweenLong > 700) {
				errorStr = count_DayToPrePrevios;
			}

		}

		
		lbl_DatePreviosToPrePrevios.setText(Date_MeasurNawStr);
		lbl_DateToPrePrevios.setText(dateMeasurPrePrevStr);
		lbl_Count_DayToPrePrevios.setText(count_DayToPrePrevios);
		
		if(!count_DayToPrePrevios.isEmpty()) {
			max =  Integer.parseInt(count_DayToPrePrevios);
		max_countToPrePrevios = max;
	}
		
		System.out.println("dateMeasurPrevStr "+dateMeasurPrevStr);	
		System.out.println("lbl_DateToPrevios "+lbl_DateToPrevios.getText());
		System.out.println("count_DayToPrevios "+count_DayToPrevios);
		System.out.println("lbl_Count_DayToPrevios "+lbl_Count_DayToPrevios.getText());
		System.out.println("*********************************************");
	
		return errorStr;

	}
	
	public static void scrollBarToPreviosListener(JButton buttonToPreviosMinus, JButton buttonToPreviosPlus, JLabel lbl_Date, JLabel lbl_Count_Day, boolean toPrevios) {
		
		buttonToPreviosMinus.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		min = max_countToPrevios;
    		if(toPrevios) {
    			min = 0; 
    		}
			int count = Integer.parseInt(lbl_Count_Day.getText());
			if(count > min) {
				count-=1;
				try {
					String output = setNewDate(count);
					 lbl_Date.setText(output);
					 lbl_Count_Day.setText(count+"");
					 
				} catch (ParseException e1) {
					e1.printStackTrace();
				}	
			}
		}

		
    }); 

		buttonToPreviosPlus.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		max = max_countToPrePrevios;
	    		if(toPrevios) {
	    			max = max_countToPrevios;
	    		}
				int count = Integer.parseInt(lbl_Count_Day.getText());
				if(count < max) {
					count+=1;
					try {
						String output = setNewDate(count);
						 lbl_Date.setText(output);
						 lbl_Count_Day.setText(count+"");
						 
					} catch (ParseException e1) {
						e1.printStackTrace();
					}	
				}
			}

			
	    });	
		
	}
	
	
	
	
	private static String setNewDate( int value)
			throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		
//		System.out.println("value "+value);
		Date date = sdf.parse(textField_Date_MeasurNaw.getText());
//		System.out.println("date "+date);
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
//			System.out.println("cal "+sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, -value);
		 String output = sdf.format(cal.getTime());
		 System.out.println("value "+value + " Indate "+date + " cal "+sdf.format(cal.getTime())+ " output "+output);
		return output;
	}

	
	
	
	public static int getMax_scrollBarToPrePrevios() {
		return max_countToPrePrevios;
	}


	public static int getMax_scrollBarToPrevios() {
		return max_countToPrevios;
	}


	public static void setMax_scrollBarToPrePrevios(int max_scrollBarToPrePrevios_N) {
		max_countToPrePrevios = max_scrollBarToPrePrevios_N;
	}


	public static void setMax_scrollBarToPrevios(int max_scrollBarToPrevios_N) {
		max_countToPrevios = max_scrollBarToPrevios_N;
	} 
	
	
}
