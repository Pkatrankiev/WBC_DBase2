package TestDatePicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;



public class ExampleDatePicker extends JFrame
{
	private static final long serialVersionUID = 1L;

	private    JLabel                lblFilter        = null;
	private    JLabel                lblFrom          = null;
	private    JLabel                lblTo            = null;
	private    JDatePickerImpl       dtFrom           = null;
	private    JDatePickerImpl       dtTo             = null;

	private    Locale                locale           = null;
	private    final  String         SPAN_normal      = "<html><span style=\"font-weight:normal\">";
	private    final  String         PATTERN_DATE     = "dd.MM.yyyy";

	private    final  String[]       LOCALIZATION     = {"Локализация", "Localization", "Lokalisierung"};
	private    final  String[]       CHOOSE           = {"Выбор"      , "Choose"      , "Auswahl"};
	private           int            FIRST_DAY_IDX    = 1;
	private    final  String[]       FIRST_DAY        = {"Неделя с &lt;%s>", "Week from &lt;%s>", "Woche ab &lt;%s>"};
	private    final  String[][]     FIRST_DAYS       = {{"По", "Вс"},
			                                             {"Mo", "Su"},
			                                             {"Mo", "So"}};
	
	private    final  Locale[]       locales          = {new Locale("bg"), new Locale("en"), new Locale("de")};
	private           int            LOCALE_idx       = 0;	
		
	

//	String[]   FIRST_DAY       = {"Неделя с <%s>", "Week from <%s>", "Woche ab <%s>"};
//	String[][] FIRST_DAYS      = {{"Вс", "По"}, {"Su", "Mo"}, {"Mo", "So"}};
//	String[]   LOCALIZATION    = {"Локализация"   , "Localization"  ,  "Lokalisierung"};
//	String[]   CHOOSE          = {"Выбор"   ,  "Choose"  , "Auswahl"      };
//	Locale     locale          = null;
//	Locale[]   locales         = {new Locale("ru"), new Locale("en"), new Locale("de")};
//	int        FIRST_DAY_IDX   = 0;
//	int        LOCALE_idx      = 0;

//	String     PATTERN_DATE    = "dd.MM.yyyy";

//	String  SPAN_normal  =  "<html><span style=\"font-weight:normal\">";
	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public ExampleDatePicker()
	{
		super("Form example");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(440, 180);
		locale = locales[LOCALE_idx];
		createGUI();
		setVisible(true);
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private void createGUI()
	{
    	final int WIDTH_PANEL = 268;
    	JPanel  pnlFilter = new JPanel();
    	pnlFilter.setLayout(new BorderLayout());
    	pnlFilter.setPreferredSize(new Dimension(WIDTH_PANEL, 80));
    	Border border = BorderFactory.createCompoundBorder(
		                      BorderFactory.createEtchedBorder(),
		                      BorderFactory.createEmptyBorder(15, 10, 15, 4));
    	pnlFilter.setBorder(border);

    	lblFilter = new JLabel();
    	lblFilter.setText(SPAN_normal + "Период времени");
    	
    	// Определение параметров компонентов дат
        UtilDateModel       modelFrom    ;
        UtilCalendarModel   modelTo      ;
        JDatePanelImpl      datePanelFrom;
        JDatePanelImpl      datePanelTo  ;
        DateLabelFormatterUser  dlf          ;

        modelFrom     = new UtilDateModel    ();
        modelTo       = new UtilCalendarModel();
//        datePanelFrom = new JDatePanelImpl(modelFrom, locale);
//        datePanelTo   = new JDatePanelImpl(modelTo  , locale);
//        dlf           = new DateLabelFormatter(PATTERN_DATE);
        //-----------------------------------------------------
        Dimension dim      = new Dimension(100, 20);
        Dimension dimLabel = new Dimension( 30, 20);
        //-----------------------------------------------------
        lblFrom = new JLabel();
    	lblFrom.setText(SPAN_normal + "c");
    	lblFrom.setPreferredSize(dimLabel);

//    	dtFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter(PATTERN_DATE));
		dtFrom.setPreferredSize(new Dimension(145, 20));
		dtFrom.getJFormattedTextField().setBackground(Color.WHITE);
		dtFrom.setTextEditable(true);
    	
		SpringLayout layoutFilter = new SpringLayout();		
		JPanel pnlFrom = new JPanel(layoutFilter);
		
		pnlFrom.add(lblFrom);
		pnlFrom.add(dtFrom);
		pnlFrom.setPreferredSize(dim);
		pnlFrom.setSize         (dim);
		layoutFilter.putConstraint(SpringLayout.WEST, dtFrom, 30, SpringLayout.EAST, lblFrom);
		//-----------------------------------------------------
		lblTo = new JLabel();
		lblTo.setText(SPAN_normal + "по");
		lblTo.setPreferredSize(dimLabel);

//    	dtTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter(PATTERN_DATE));
    	dtTo.setPreferredSize(new Dimension(145, 20));
    	dtTo.getJFormattedTextField().setBackground(Color.WHITE);
    	
		JPanel pnlTo = new JPanel(layoutFilter);
		
		pnlTo.add(lblTo);
		pnlTo.add(dtTo);
		pnlTo.setPreferredSize(dim);
		pnlTo.setSize         (dim);
		layoutFilter.putConstraint(SpringLayout.WEST, dtTo, 30, SpringLayout.EAST, lblTo);
		//-----------------------------------------------------
		pnlFilter.add(pnlFrom, BorderLayout.NORTH);
		pnlFilter.add(pnlTo  , BorderLayout.SOUTH);
		//-----------------------------------------------------
        JButton btnFirstDay = new JButton(SPAN_normal + String.format(FIRST_DAY[LOCALE_idx], 
        		                                                      FIRST_DAYS[LOCALE_idx][FIRST_DAY_IDX]));        
        JButton btnLocale   = new JButton(SPAN_normal + LOCALIZATION[LOCALE_idx]);
		JButton btnFilter   = new JButton(SPAN_normal + CHOOSE      [LOCALE_idx]);

		final int WIDTH_BUTTON = 130;
		dim = new Dimension (WIDTH_BUTTON, 28);
		btnFirstDay.setPreferredSize(dim);
		btnLocale  .setPreferredSize(dim);
		btnFilter  .setPreferredSize(dim);

        // Панель кнопок управления
		JPanel pnlButtons = new JPanel(); // new GridLayout(3, 1, 5, 12));
		pnlButtons.add(btnFirstDay);
		pnlButtons.add(btnLocale  );
		pnlButtons.add(btnFilter  );
        
		btnFirstDay.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            changeFirstWeekDay();
	            String caption = SPAN_normal + 
	                     String.format(FIRST_DAY[LOCALE_idx],
	                     FIRST_DAYS[LOCALE_idx][FIRST_DAY_IDX]);
	            btnFirstDay.setText(caption);
	        }
	    });
		btnLocale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (++LOCALE_idx >= locales.length)
					LOCALE_idx = 0;
		    	dtFrom.setLocale(locales[LOCALE_idx]);
		    	dtTo  .setLocale(locales[LOCALE_idx]);
		    	btnLocale  .setText(SPAN_normal + LOCALIZATION[LOCALE_idx]);
		    	btnFilter  .setText(SPAN_normal + CHOOSE[LOCALE_idx]);
		    	btnFirstDay.setText(SPAN_normal + String.format(FIRST_DAY[LOCALE_idx], 
                                                                FIRST_DAYS[LOCALE_idx][FIRST_DAY_IDX]));
			}
		});

		btnFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChoosedDates();
			}
		});
		getContentPane().add(lblFilter , BorderLayout.NORTH );
        getContentPane().add(pnlFilter , BorderLayout.CENTER);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
	}
	
	
	private void createGUI1()
	{
	    JPanel  pnlFilter = new JPanel();
	    pnlFilter.setLayout(new BorderLayout());
	    pnlFilter.setPreferredSize(new Dimension(268, 80));
	    Border border = BorderFactory.createCompoundBorder(
	            BorderFactory.createEtchedBorder(),
	            BorderFactory.createEmptyBorder(15, 10, 15, 4));
	    pnlFilter.setBorder(border);

	    lblFilter = new JLabel();
	    lblFilter.setText(SPAN_normal + "Период времени");

	    // Определение параметров компонентов дат
	    UtilDateModel       modelFrom    ;
	    UtilCalendarModel   modelTo      ;
	    JDatePanelImpl      datePanelFrom;
	    JDatePanelImpl      datePanelTo  ;
//	    DateLabelFormatter  dlf          ;
//
//	    modelFrom     = new UtilDateModel    ();
//	    modelTo       = new UtilCalendarModel();
//	    datePanelFrom = new JDatePanelImpl(modelFrom, locale);
//	    datePanelTo   = new JDatePanelImpl(modelTo  , locale);
//	    dlf           = new DateLabelFormatter(PATTERN_DATE);
	    //-----------------------------------------------------
	    Dimension dim      = new Dimension(100, 20);
	    Dimension dimLabel = new Dimension( 30, 20);
	    //-----------------------------------------------------
	    lblFrom = new JLabel();
	    lblFrom.setText(SPAN_normal + "c");
	    lblFrom.setPreferredSize(dimLabel);

//	    dtFrom = new JDatePickerImpl(datePanelFrom, dlf);
	    dtFrom.setPreferredSize(new Dimension(145, 20));
	    dtFrom.getJFormattedTextField().setBackground(Color.WHITE);

	    SpringLayout layoutFilter = new SpringLayout();
	    JPanel pnlFrom = new JPanel(layoutFilter);

	    pnlFrom.add(lblFrom);
	    pnlFrom.add(dtFrom);
	    pnlFrom.setPreferredSize(dim);
	    pnlFrom.setSize         (dim);
	    layoutFilter.putConstraint(SpringLayout.WEST, dtFrom, 30,
	                               SpringLayout.EAST, lblFrom);
	    //-----------------------------------------------------
	    lblTo = new JLabel();
	    lblTo.setText(SPAN_normal + "по");
	    lblTo.setPreferredSize(dimLabel);

//	    dtTo = new JDatePickerImpl(datePanelTo, dlf);
	    dtTo.setPreferredSize(new Dimension(145, 20));
	    dtTo.getJFormattedTextField().setBackground(Color.WHITE);

	    JPanel pnlTo = new JPanel(layoutFilter);

	    pnlTo.add(lblTo);
	    pnlTo.add(dtTo);
	    pnlTo.setPreferredSize(dim);
	    pnlTo.setSize         (dim);
	    layoutFilter.putConstraint(SpringLayout.WEST, dtTo, 30,
	                               SpringLayout.EAST, lblTo);
	    //-----------------------------------------------------
	    pnlFilter.add(pnlFrom, BorderLayout.NORTH);
	    pnlFilter.add(pnlTo  , BorderLayout.SOUTH);
	    //-----------------------------------------------------
	    String caption = SPAN_normal + 
	                     String.format(FIRST_DAY[LOCALE_idx],
	                     FIRST_DAYS[LOCALE_idx][FIRST_DAY_IDX]);
	    JButton btnFirstDay = new JButton(caption);

	    String caption1 = SPAN_normal + LOCALIZATION[LOCALE_idx];
	    JButton btnLocale   = new JButton(caption1);

	    String caption2 = SPAN_normal + CHOOSE[LOCALE_idx];
	    JButton btnFilter   = new JButton(caption2);

	    final int WIDTH_BUTTON = 130;
	    dim = new Dimension (WIDTH_BUTTON, 28);
	    btnFirstDay.setPreferredSize(dim);
	    btnLocale  .setPreferredSize(dim);
	    btnFilter  .setPreferredSize(dim);

	    // Панель кнопок управления
	    JPanel pnlButtons = new JPanel();
	    pnlButtons.add(btnFirstDay);
	    pnlButtons.add(btnLocale  );
	    pnlButtons.add(btnFilter  );
	        
	    btnFirstDay.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            changeFirstWeekDay();
	            String caption = SPAN_normal + 
	                     String.format(FIRST_DAY[LOCALE_idx],
	                     FIRST_DAYS[LOCALE_idx][FIRST_DAY_IDX]);
	            btnFirstDay.setText(caption);
	        }
	    });
	    btnLocale.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            changeLocale();
	            btnLocale  .setText(SPAN_normal + 
	                                LOCALIZATION[LOCALE_idx]);
	            btnFilter  .setText(SPAN_normal + 
	                                CHOOSE[LOCALE_idx]);
	            btnFirstDay.setText(SPAN_normal + 
	                    String.format(FIRST_DAY[LOCALE_idx],
	                    FIRST_DAYS[LOCALE_idx][FIRST_DAY_IDX]));
	        }
	    });

	    btnFilter.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ChoosedDates();
	        }
	    });
	    getContentPane().add(lblFilter , BorderLayout.NORTH );
	    getContentPane().add(pnlFilter , BorderLayout.CENTER);
	    getContentPane().add(pnlButtons, BorderLayout.SOUTH);
	}
	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private void ChoosedDates()
	{
		// Выбранные строки
		String msg   = "Вы выбрали период с '%s' по '%s'";
		String title = "Выбор";
		String from  = "null";
		String to    = "null";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (dtFrom.getModel().getValue() != null)
			from = sdf.format(dtFrom.getModel().getValue());
		if (dtTo.getModel().getValue() != null)
			to = sdf.format(dtTo.getModel().getValue());
		msg = String.format(msg, from, to);
		if (msg.length() > 0)
			JOptionPane.showMessageDialog(
					ExampleDatePicker.this, msg,
                   title, JOptionPane.INFORMATION_MESSAGE);
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void main(String[] args) {
		new ExampleDatePicker();
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private void changeLocale()
	{
	    if (++LOCALE_idx >= locales.length)
	        LOCALE_idx = 0;
	    dtFrom.setLocale(locales[LOCALE_idx]);
	    dtTo  .setLocale(locales[LOCALE_idx]);
	   

	}

	private void changeFirstWeekDay()
	{
	    if (++FIRST_DAY_IDX == 2)
	        FIRST_DAY_IDX = 0;
//	    dtFrom.setFirstDayMonday (!dtFrom.isFirstDayMonday());
//	    dtTo.setFirstDayMonday   (!dtTo  .isFirstDayMonday());
	}

}
