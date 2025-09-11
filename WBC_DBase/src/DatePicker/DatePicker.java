package DatePicker;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import Aplication.ResourceLoader;


public class DatePicker {

	// define variables
	static int month = Calendar.getInstance().get(Calendar.MONTH);
	static int year = Calendar.getInstance().get(Calendar.YEAR);
	static int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	static int minute = Calendar.getInstance().get(Calendar.MINUTE);
	static int dayOfMont = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	static int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	final Calendar cal_time = Calendar.getInstance();
	private Font font = new Font("Tahoma", Font.PLAIN, 14);
	private Font fontButt = new Font("Tahoma", Font.PLAIN, 12);

	private static String FORMAT_DATE = "dd.MM.yyyy";
	private static String FORMAT_DATE_TIME = "dd.MM.yyyy HH:mm";
	private static String FORMAT_DATE_TABLE = "yyyy-MM-dd";

	// private String oldDate;

	// create object of JLabel with alignment
	JLabel l = new JLabel("", JLabel.CENTER);
	// define variable
	String day = "", old_day = "";
	// declaration
	JDialog d;
	// create object of JButton
	JButton[] button = new JButton[49];

	JLabel lab = new JLabel();

	public DatePicker(JFrame parent, Boolean inTime, String old_date, Point pointFrame){

		int with = 430;
		int higth = 120;
		// create object
		d = new JDialog();

		d.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				old_day = old_date;
			}

		});

		// set modal true
		d.setModal(true);
		// define string
		String[] header = { "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд" };
		// create JPanel object and set layout
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		// set size
		p1.setPreferredSize(new Dimension(with, higth));
		// for loop condition
		for (int x = 0; x < button.length; x++) {
			// define variable
			final int selection = x;
			// create object of JButton
			button[x] = new JButton();
			button[x].setFont(font);
			// set focus painted false
			button[x].setFocusPainted(false);
			// set background colour
			button[x].setBackground(Color.white);
			// if loop condition
			if (x > 6) {

				// add action listener
				button[x].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (!button[selection].getText().equals("")) {
							day = button[selection].getActionCommand();
							// call dispose() method
							d.dispose();
						}
					}
				});

			}
			if (x < 7)// if loop condition
			{
				button[x].setText(header[x]);
				button[x].setFont(new Font("Tahoma", Font.BOLD, 12));
				button[x].setForeground(Color.red);
				if (x < 5)
					button[x].setForeground(Color.black);
				button[x].setContentAreaFilled(false);
				button[x].setBackground(SystemColor.controlShadow);
				button[x].setEnabled(false);
				button[x].setBorder(UIManager.getBorder("CheckBox.border"));
				button[x].setOpaque(true);
			}

			p1.add(button[x]);// add button

		}
		// create JPanel object with grid layout
		JPanel p2 = new JPanel(new GridLayout(1, 3));

		
		// create object of button for previous Year --------------------------------------------
		JButton data_previousYear = new JButton("<< Год.");
		data_previousYear.setFont(fontButt);
		// add action command
		data_previousYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// decrement month by 1
				
				year--;

				// call method
				displayDate();
			}
		});
		p2.add(data_previousYear);// add button
		// create object of button for previous Year --------------------------------------------	
		
		
		// create object of button for previous month --------------------------------------------
				JButton data_previous = new JButton("<< Мес.");
				data_previous.setFont(fontButt);
				// add action command
				data_previous.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// decrement month by 1
						
						
						month--;
						// call method
						displayDate();
					}
				});
				p2.add(data_previous);// add button
				p2.add(l);// add label
				// create object of button for previous month --------------------------------------------	
				
				
		// create object of button for next month +++++++++++++++++++++++++++++++
				JButton data_next = new JButton("Мес. >>");
				data_next.setFont(fontButt);
				// add action command
				data_next.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// increment month by 1
						
						month++;
						// call method
						displayDate();
					}
				});
				p2.add(data_next);// add next button
				// create object of button for next month +++++++++++++++++++++++++++++++
				
				// create object of button for next  +++++++++++++++++++++++++++++++
				JButton data_nextYear = new JButton("Год. >>");
				data_nextYear.setFont(fontButt);
				// add action command
				data_nextYear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// increment month by 1
						
						year++;

						// call method
						displayDate();
					}
				});
				p2.add(data_nextYear);// add next button
				// create object of button for next Year +++++++++++++++++++++++++++++++
						
				
				

		JPanel panel = new JPanel();
		d.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		if (inTime)
			getTimeModule(panel);

		// set border alignment
		d.getContentPane().add(p1, BorderLayout.CENTER);
		d.getContentPane().add(p2, BorderLayout.SOUTH);
		d.pack();
		// set location
//		d.setLocationRelativeTo(parent);
		
		d.setLocationRelativeTo(null);

		Point point = getCurentKoordinates( with, higth, pointFrame);
		
		if (point != null) {
			d.setLocation((int) point.getX(), (int)point.getY());
		}
		// call method
		displayDate();
		// set visible true
		d.setVisible(true);
	}

	
	public static Point getCurentKoordinates(int width, int height, Point b) {
		int x = 100;
		int y = 100;
		if (b != null) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int koordWidth = gd.getDisplayMode().getWidth();
		int koorHeight = gd.getDisplayMode().getHeight()-100;
		int mx = (int) b.getX();
		int my = (int) b.getY();

		x = mx-width/2;
		
		if(mx + width/2 > koordWidth) x=mx-width;
		
		if(mx-width/2 < 0) x=mx;
		
		y = my+25;
//		System.out.println("y+height="+ (y+height));
		if(y+height > koorHeight) y=my-height-60;
		
//		System.out.println("x="+x+" y="+y);
	}
		return new Point(x, y);
	}
	
	
	
	public void onExit(String date, boolean inTime) {
		old_day = date;
	}

	private void getTimeModule(JPanel panel) {
		// Date data_time = null;
		final SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
		String time = hour + ":00";
		try {
			cal_time.setTime(sdf_time.parse(time));
		} catch (ParseException e1) {
			ResourceLoader.appendToFile(e1);
			e1.printStackTrace();
		}
		minute = cal_time.get(Calendar.MINUTE);
		JTextField label = new JTextField(time, SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		label.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {
				incorrectTime(label.getText(), label);
			}

			@Override
			public void keyReleased(KeyEvent event) {

				try {
					cal_time.setTime(sdf_time.parse(label.getText()));
				} catch (ParseException e) {
					ResourceLoader.appendToFile(e);
					JOptionPane.showMessageDialog(null, "Некоректен час!", "Грешни данни", JOptionPane.ERROR_MESSAGE);
				}

				hour = cal_time.get(Calendar.HOUR_OF_DAY);
				minute = cal_time.get(Calendar.MINUTE);
				String new_time = sdf_time.format(cal_time.getTime());
				label.setText(new_time);
				incorrectTime(label.getText(), label);

			}

			@Override
			public void keyPressed(KeyEvent event) {
				incorrectTime(label.getText(), label);
			}
		});

		JButton previous_time_btn = new JButton("<< T Назад");
		previous_time_btn.setFont(fontButt);
		previous_time_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// decrement time by 10
				cal_time.add(Calendar.MINUTE, -10);
				hour = cal_time.get(Calendar.HOUR_OF_DAY);
				minute = cal_time.get(Calendar.MINUTE);
				String new_time = sdf_time.format(cal_time.getTime());
				label.setText(new_time);

			}
		});

		panel.add(previous_time_btn);

		panel.add(label);

		JButton next_time_btn = new JButton("Напред T >>");
		next_time_btn.setFont(fontButt);
		next_time_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// increment time by 10
				cal_time.add(Calendar.MINUTE, 10);
				hour = cal_time.get(Calendar.HOUR_OF_DAY);
				minute = cal_time.get(Calendar.MINUTE);
				String new_time = sdf_time.format(cal_time.getTime());
				label.setText(new_time);

			}
		});
		panel.add(next_time_btn);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	@Override
	public boolean equals(Object arg0) {

		return super.equals(arg0);
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public String toString() {

		return super.toString();
	}

	public void displayDate() {

		for (int x = 7; x < button.length; x++) {// for loop
			button[x].setText("");// set text
			button[x].setBackground(Color.white);
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM-yyyy");
		// create object of SimpleDateFormat
		Calendar cal = Calendar.getInstance();
		Calendar calDay = Calendar.getInstance();
		// create object of java.util.Calendar
		cal.set(year, month, 1); // set year, month and date
		// define variables
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// condition
		if (dayOfWeek == 1) {
			dayOfWeek = 7;
		} else
			dayOfWeek = dayOfWeek - 1;
		SimpleDateFormat sdfDay = new SimpleDateFormat(FORMAT_DATE);
		for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++) { // set
																				// text
			button[x].setText("" + day);
			calDay.set(year, month, day);
			if (calDay.get(Calendar.DAY_OF_WEEK) == 7 || calDay.get(Calendar.DAY_OF_WEEK) == 1)
				button[x].setForeground(Color.red);

			if (DateNaw(false).equals(sdfDay.format(calDay.getTime()))) {
//				System.out.println(DateNaw(false) + " " + sdfDay.format(calDay.getTime()));
				button[x].setBackground(Color.CYAN);
			}
		}
		for (int x = 7; x < button.length; x++) {
			if (button[x].getText().equals("")) {
				button[x].setEnabled(false);
				button[x].setOpaque(false);
				button[x].setContentAreaFilled(false);
				button[x].setBorderPainted(false);
			} else {
				button[x].setEnabled(true);
				button[x].setOpaque(true);
				button[x].setContentAreaFilled(true);
				button[x].setBorderPainted(true);
			}
		}
		l.setText(sdf.format(cal.getTime()));
		// set title
		d.setTitle("Избор на дата");

	}

	public static String DateNaw(Boolean whiteTime) {
		String dateNaw = null;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		if (whiteTime)
			sdf = new SimpleDateFormat(FORMAT_DATE_TIME);

		dateNaw = sdf.format(Calendar.getInstance().getTime());

		return dateNaw;
	}
	
	public static Boolean checkStringDateAfterCurentYear(int startYear, String curentDate) {
		DateFormat df = new SimpleDateFormat("dd.mm.yyyy");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(df.parse(curentDate));

		} catch (ParseException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		if (startYear <= cal.get(Calendar.YEAR)) {
			return true;
		}
		return false;
	}

	public static int getActualyMonth() {
		return month;
	}

	public String setPickedDate(Boolean inTime) {
		if (!old_day.equals(""))
			return old_day;
		if (day.equals(""))
			return day;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
		Calendar cal = Calendar.getInstance();
		if (inTime) {
			cal.set(year, month, Integer.parseInt(day), hour, minute);
		} else {
			sdf = new SimpleDateFormat(FORMAT_DATE);

			cal.set(year, month, Integer.parseInt(day));
		}
		return sdf.format(cal.getTime());
	}

	public static String getCurentDate(Boolean inTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		Calendar cal = Calendar.getInstance();
		if (inTime) {
			sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
		}
		return sdf.format(cal.getTime());
	}

	public static String getCurentDate2() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
		return sdf.format(cal.getTime());
	}

	private static long getSecondDuration(LocalDateTime t) {
		long h = t.getHour();
		long m = t.getMinute();
		long s = t.getSecond();
		return (h * 3600) + (m * 60) + s;
	}

	public static String getReferenceDate(String startStrDate, String endStrDate) {
		String meanStrPeriod = "";
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);

		if (!startStrDate.equals("")) {
			if (endStrDate.equals("")) {
				meanStrPeriod = startStrDate;
				try {
					LocalDateTime.parse(startStrDate, sdf);
				} catch (Exception e) {
					return meanStrPeriod = "Некоректни дати";

				}
			} else {
				// sredata na perioda
				LocalDateTime locStartDate = null;
				LocalDateTime locEndDate = null;
				LocalDateTime refDate = null;
				try {
					locStartDate = LocalDateTime.parse(startStrDate, sdf);
					locEndDate = LocalDateTime.parse(endStrDate, sdf);
				} catch (Exception e) {
					return meanStrPeriod = "Некоректни дати";

				}
				Duration duration = Duration.between(locStartDate, locEndDate);

				getSecondDuration(locStartDate);
				long refTimeInSec = duration.getSeconds() / 2;
				refDate = locStartDate.plusSeconds(refTimeInSec);
				if (refTimeInSec < 0) {
					meanStrPeriod = "Некоректен период";
				} else
					meanStrPeriod = refDate.format(sdf);

			}
		} else {
			meanStrPeriod = "";
		}

		return meanStrPeriod;
	}

	public static Boolean isDate2AfterDate1(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date locDate1 = null;
		Date locDate2 = null;
		try {
			 locDate1 = sdf.parse(date1);
			 locDate2 = sdf.parse(date2);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Некоректни дати", "Грешка в данните", JOptionPane.ERROR_MESSAGE);
		}
		return locDate2.after(locDate1);
	}

	public static String displacementDate(String origin_date, int displacement ) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date dt = null;
		try {
			dt = sdf.parse(origin_date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt); 
			cal.add(Calendar.DATE, displacement);
			dt = cal.getTime();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Некоректни дати", "Грешка в данните", JOptionPane.ERROR_MESSAGE);
		}
	
		return sdf.format(dt);
	}
	
	public static String reformarDateMeasur(String origin_date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdf6 = new SimpleDateFormat("d.M.yy");
		SimpleDateFormat sdf7 = new SimpleDateFormat("dd.M.yy");
		SimpleDateFormat sdf8 = new SimpleDateFormat("dd.MM.yy");
		Date date = new Date();

		try {
			if (origin_date.length() == 6) {
				date = sdf6.parse(origin_date);
			}
			if (origin_date.length() == 7) {
				date = sdf7.parse(origin_date);
			}
			if (origin_date.length() == 8) {
				date = sdf8.parse(origin_date);
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Преформатиране на - Датата", "Грешка в данните",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}
		return sdf.format(date);
	}

	public static String chec_current_period(String startStrDate, String endStrDate) {
		String meanStrPeriod = startStrDate;
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(FORMAT_DATE);
		LocalDate locStartDate = null;
		LocalDate locEndDate = null;
		if (!startStrDate.equals("")) {
			if (endStrDate.equals("")) {
				meanStrPeriod = startStrDate;
				try {
					LocalDate.parse(startStrDate, sdf);
				} catch (Exception e) {
					return meanStrPeriod = "Некоректни датa";

				}
			} else {
				try {
					locStartDate = LocalDate.parse(startStrDate, sdf);
					locEndDate = LocalDate.parse(endStrDate, sdf);
				} catch (Exception e) {
					return meanStrPeriod = "Некоректни дати";

				}
				if (locStartDate.isAfter(locEndDate)) {
					meanStrPeriod = "Некоректен период";
				}
			}
		}

		// SimpleDateFormat s_sdf = new SimpleDateFormat(FORMAT_DATE);
		// Date s_locStartDate = null;
		// Date s_locEndDate = null;
		//
		// try {
		// s_locStartDate = s_sdf.parse(startStrDate);
		// s_locEndDate = s_sdf.parse(endStrDate);
		// } catch (Exception e) {
		// return meanStrPeriod = "Некоректни дати2";
		// }
		// if (s_locStartDate.after(s_locEndDate)) {
		// meanStrPeriod = "Некоректен период";
		// }

		return meanStrPeriod;
	}

	public static Boolean incorrectTime(String time, JTextField label) {
		SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
		try {
			sdf_time.parse(time);
			label.setForeground(Color.BLACK);
			return true;
		} catch (ParseException e) {
			label.setForeground(Color.RED);
			return false;
		}
	}

	public static Boolean incorrectTableDate(String date) {
		Boolean corDate = false;
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(FORMAT_DATE_TABLE);
		String origDate = date;

		try {
			LocalDate data_time = LocalDate.parse(date, sdf);
			if (!data_time.format(sdf).equals(origDate))
				return true;
		} catch (DateTimeParseException e) {
			return corDate = true;
		}
		return corDate;
	}

	public static Boolean correctDate(String date, SimpleDateFormat sdf) {
		boolean fl = true;
		try {
			
			Date datAfter = sdf.parse("01.01.2000");
			Date dat = sdf.parse(date);
				if(dat.before(datAfter)) fl = false;
				
		} catch (ParseException e) {
			return  false;
		}
//		System.out.println(date+" - "+fl);
		return fl;
		
	}
	
	
	public static Boolean incorrectDate(String date, Boolean inTime) {
		Boolean corDate = false;
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(FORMAT_DATE);

		if (inTime) {
			sdf = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);
		}

		try {
			LocalDate.parse(date, sdf);

		} catch (DateTimeParseException e) {
			return corDate = true;
		}
		return corDate;
	}

	public static Boolean incorrectPeriodOrDate(String date) {
		Boolean fl1 = false, fl2 = false;
		String date1 = date;
		if (date.indexOf("÷") > 0) {
			date1 = date.substring(0, date.indexOf("")).trim();
			String date2 = date.substring(date.indexOf("÷")).trim();
			fl2 = incorrectDate(date2, false);
		}
		fl1 = incorrectDate(date1, false);

		return fl1 && fl2;
	}

	public static String reformatFromTabDate(String origin_date, Boolean withTime) {
		String TAB_FORMAT_DATE = "yyyy-MM-dd";
		String TAB_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
		String FORMAT_DATE = "dd-MM-yyyy";
		String FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm";
		String globalSeparator = ".";
		SimpleDateFormat sdf;
		SimpleDateFormat table_sdf;
		char separator = '.';
		char separ = globalSeparator.charAt(0);
		if (origin_date.contains("-")) {
			separator = '-';
		}
		if (separator != separ) {
			FORMAT_DATE_TIME = FORMAT_DATE_TIME.replace(separ, separator);
			FORMAT_DATE = FORMAT_DATE.replace(separ, separator);
		}

		if (withTime) {
			sdf = new SimpleDateFormat(TAB_FORMAT_DATE_TIME);
			table_sdf = new SimpleDateFormat(FORMAT_DATE_TIME);

		} else {
			sdf = new SimpleDateFormat(TAB_FORMAT_DATE);
			table_sdf = new SimpleDateFormat(FORMAT_DATE);
		}

		Date date = new Date();

		try {
			date = sdf.parse(origin_date);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Преформатиране на - Датата", "Грешка в данните",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return table_sdf.format(date);
	}

	public static String formatToTabDate(String origin_date, Boolean withTime) {
		String TAB_FORMAT_DATE = "yyyy-MM-dd";
		String TAB_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
		String FORMAT_DATE = "dd-MM-yyyy";
		String FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm";
		String globalSeparator = ".";
		SimpleDateFormat sdf;
		SimpleDateFormat table_sdf;
		char separator = '.';
		char separ = globalSeparator.charAt(0);
		if (origin_date.contains("-")) {
			separator = '-';
		}

//		if (origin_date.length() > 3 && !origin_date.substring(0, 3).contains(".")) {
//			return origin_date;
//		}

		if (separator != separ) {
			FORMAT_DATE_TIME = FORMAT_DATE_TIME.replace(separ, separator);
			FORMAT_DATE = FORMAT_DATE.replace(separ, separator);
		}

		if (withTime) {
			sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
			table_sdf = new SimpleDateFormat(TAB_FORMAT_DATE_TIME);

		} else {
			sdf = new SimpleDateFormat(FORMAT_DATE);
			table_sdf = new SimpleDateFormat(TAB_FORMAT_DATE);
		}

		Date date = new Date();

		try {
			if (origin_date.length() > 3) {
				date = sdf.parse(origin_date);
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Преформатиране на Датата", "Грешка в данните",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		// date = sdf.parse(origin_date);
		return table_sdf.format(date);
	}

	public static String formatToProtokolDate(String origin_date) throws ParseException {
		String FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm";
		String DOC_FORMAT_DATE_TIME = "dd.MM.yyyy / HH:mm";
		String globalSeparator =  ".";
		SimpleDateFormat sdf;
		SimpleDateFormat table_sdf;
		char separator = '.';
		char separ = globalSeparator.charAt(0);
		if (origin_date.contains("-")) {
			separator = '-';
		}

		if (!origin_date.substring(0, 3).contains(".")) {
			return origin_date;
		}

		if (separator != separ) {
			FORMAT_DATE_TIME = FORMAT_DATE_TIME.replace(separ, separator);
			FORMAT_DATE = FORMAT_DATE.replace(separ, separator);
		}

		sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
		table_sdf = new SimpleDateFormat(DOC_FORMAT_DATE_TIME);

		Date date = new Date();

		try {
//			System.out.println("--                  --" + origin_date);
			date = sdf.parse(origin_date);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Преформатиране на Датата", "Грешка в данните",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		date = sdf.parse(origin_date);
		return table_sdf.format(date);
	}
}
