package PersonReference_Dokument;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;
import DatePicker.DatePicker;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;
import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonExcellClass;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.SearchFromExcellFiles;
import PersonReference.TextInAreaTextPanel;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class PersonReference_Dokument_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private Choice comboBox_Firm;
	private static Choice comboBox_Otdel;

	private static JTextArea textArea;
	private static JTextField txtDokument;
	private static JTextField txtStartDate;
	private static JTextField txtEndDate;

	private static JButton btn_SearchDBase;
	private static JButton btn_SearchFromExcel;
	private static JButton btnBackToTable;
	private int minYeare;
	private String notResults;

	ArrayList<String> listOtdelKz;
	List<String> listOtdelVO;
	List<String> listOtdelAll;
	List<String> listAdd;
	List<String> listFirm;
	Object[][] dataTable;
	
	
	private static String referencePerson_EGN = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_EGN");
	private static String referencePerson_FirstName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_FirstName");
	private static String referencePerson_SecondName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SecondName");
	private static String referencePerson_LastName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_LastName");
	private static String referencePersonMeasur_startDate = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_startDate");
	private static String referencePersonDokument = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonDokument");
	private static String referencePersonMeasur_endDate = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_endDate");
	private static String referencePerson_ID_KZ_HOG = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_ID_KZ_HOG");
	private static String referencePerson_ID_KZ_2 = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_ID_KZ_2");
	private static String referencePerson_ID_KZ_1 = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_ID_KZ_1");
	private static String referencePersonMeasur_firm = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_firm");
	private static String referencePersonMeasur_otdel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_otdel");
	
	static String curentYear = AplicationMetods.getCurentYear();
	private static JTextField textField_Year;
	private JLabel lbl_Year;
	private JLabel lblNewLabel;
	
	
	
	
	public PersonReference_Dokument_Frame(ActionIcone round, String title) {
		setTitle(title);
		setMinimumSize(new Dimension(780, 900));

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		
		
		
		notResults = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
		String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
		String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");
		String minYearInDbase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("minYearInDbase");

		try {
			minYeare = Integer.parseInt(minYearInDbase);
		} catch (Exception e) {
			MessageDialog("Year not korekt in BGTextVariable", "Error");
			System.exit(0);
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel_Search = new JPanel();
		panel_Search.setLayout(new BoxLayout(panel_Search, BoxLayout.Y_AXIS));
		contentPane.add(panel_Search, BorderLayout.NORTH);

		panel_AllSaerch = new JPanel();
		contentPane.add(panel_AllSaerch, BorderLayout.CENTER);
		panel_AllSaerch.setLayout(new BoxLayout(panel_AllSaerch, BoxLayout.Y_AXIS));

		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);

		tablePane = new JPanel();
		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablePane.add(scrollPane, BorderLayout.CENTER);

		listOtdelKz = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", AEC));
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);

		listOtdelVO = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", VO));
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
		listOtdelAll = getListStringOtdel(WorkplaceDAO.getAllValueWorkplace());
		listOtdelAll.add("");
		Collections.sort(listOtdelAll);

		listAdd = new ArrayList<>();
		listFirm = new ArrayList<>();
		listFirm.add("");
		listFirm.add(AEC);
		listFirm.add(VO);
		panel_2();
		panel_2A();
		panel_3();

		getRootPane().setDefaultButton(btn_SearchDBase);

		panel_Button();

		ActionListenerSetDateByDatePicker(txtStartDate);
		ActionListenerSetDateByDatePicker(txtEndDate);
		
		
		setSize(780, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		round.StopWindow();
	}

	private JPanel panel_2() {
		
		String referencePerson_SearchFromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SearchFromDBase");

		String referencePersonMeasur_year = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_year");
		
		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);
		
		lbl_Year = new JLabel(referencePersonMeasur_year);
		lbl_Year.setToolTipText("");
		lbl_Year.setSize(new Dimension(80, 20));
		lbl_Year.setPreferredSize(new Dimension(40, 15));
		lbl_Year.setMinimumSize(new Dimension(80, 20));
		lbl_Year.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panel2.add(lbl_Year);

		JLabel lbl_Dokument = new JLabel(referencePersonDokument);
		lbl_Dokument.setSize(new Dimension(120, 20));
		lbl_Dokument.setPreferredSize(new Dimension(68, 15));
		lbl_Dokument.setMinimumSize(new Dimension(120, 20));
		lbl_Dokument.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Dokument.setBorder(null);
		lbl_Dokument.setAlignmentX(0.5f);
		panel2.add(lbl_Dokument);

		JLabel lbl_StartDate = new JLabel(referencePersonMeasur_startDate);
		lbl_StartDate.setSize(new Dimension(80, 20));
		lbl_StartDate.setPreferredSize(new Dimension(70, 15));
		lbl_StartDate.setMinimumSize(new Dimension(80, 20));
		lbl_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_StartDate.setBorder(null);
		lbl_StartDate.setAlignmentX(0.5f);
		panel2.add(lbl_StartDate);

		JLabel lbl_EndDate = new JLabel(referencePersonMeasur_endDate);
		lbl_EndDate.setSize(new Dimension(80, 20));
		lbl_EndDate.setPreferredSize(new Dimension(70, 15));
		lbl_EndDate.setMinimumSize(new Dimension(80, 20));
		lbl_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EndDate.setBorder(null);
		lbl_EndDate.setAlignmentX(0.5f);
		panel2.add(lbl_EndDate);

		JLabel lbl_L_Firm = new JLabel(referencePersonMeasur_firm);
		lbl_L_Firm.setSize(new Dimension(70, 20));
		lbl_L_Firm.setPreferredSize(new Dimension(120, 15));
		lbl_L_Firm.setMinimumSize(new Dimension(70, 20));
		lbl_L_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Firm.setBorder(null);
		lbl_L_Firm.setAlignmentX(1.0f);
		panel2.add(lbl_L_Firm);

		JLabel lbl_L_Otdel = new JLabel(referencePersonMeasur_otdel);
		lbl_L_Otdel.setSize(new Dimension(70, 20));
		lbl_L_Otdel.setPreferredSize(new Dimension(200, 15));
		lbl_L_Otdel.setMinimumSize(new Dimension(70, 20));
		lbl_L_Otdel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Otdel.setBorder(null);
		lbl_L_Otdel.setAlignmentX(1.0f);
		panel2.add(lbl_L_Otdel);
				
		JLabel lblNewLabel_1_1 = new JLabel("");
		panel2.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setPreferredSize(new Dimension(20, 14));
		
		btn_SearchDBase = new JButton(referencePerson_SearchFromDBase);
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel2.add(btn_SearchDBase);
		btn_SearchDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase.setIconTextGap(1);
		btn_SearchDBase.setPreferredSize(new Dimension(125, 23));

		ActionListenerbBtn_SearchDBase();
		return panel2;
	}

	private JPanel panel_2A() {
		
		String referencePerson_SearchFromExcel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SearchFromExcel");
		
		JPanel panel2A = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel2A.getLayout();
		flowLayout_2.setVgap(2);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel2A.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel2A);
		
		textField_Year = new JTextField();
		textField_Year.setText(curentYear);
		textField_Year.setPreferredSize(new Dimension(5, 20));
		textField_Year.setMinimumSize(new Dimension(5, 20));
		textField_Year.setColumns(4);
		panel2A.add(textField_Year);

		TextFieldJustNumbers(textField_Year);
		ActionListenertextField_Year();
		
		txtDokument = new JTextField();
		txtDokument.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtDokument.setPreferredSize(new Dimension(5, 20));
		txtDokument.setMinimumSize(new Dimension(5, 20));
		txtDokument.setColumns(8);
		panel2A.add(txtDokument);

		txtStartDate = new JTextField("01.01."+curentYear);
		txtStartDate.setPreferredSize(new Dimension(8, 20));
		txtStartDate.setMinimumSize(new Dimension(5, 20));
		txtStartDate.setColumns(8);
		panel2A.add(txtStartDate);

		txtEndDate = new JTextField("31.12."+curentYear);
		txtEndDate.setPreferredSize(new Dimension(5, 20));
		txtEndDate.setMinimumSize(new Dimension(5, 20));
		txtEndDate.setColumns(8);
		panel2A.add(txtEndDate);

		comboBox_Firm = new Choice();
		comboBox_Firm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Firm.setPreferredSize(new Dimension(120, 20));
		panel2A.add(comboBox_Firm);

		comboBox_Otdel = new Choice();
		comboBox_Otdel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Otdel.setPreferredSize(new Dimension(200, 20));
		panel2A.add(comboBox_Otdel);

		ActionListenerComboBox_Firm();

		addItem(comboBox_Firm, listFirm);
		addItem(comboBox_Otdel, listOtdelAll);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setPreferredSize(new Dimension(20, 14));
		panel2A.add(lblNewLabel_1_1);

		btn_SearchFromExcel = new JButton(referencePerson_SearchFromExcel);
		btn_SearchFromExcel.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchFromExcel.setPreferredSize(new Dimension(125, 23));
		panel2A.add(btn_SearchFromExcel);

		ActionListenerbBtn_SearchFromExcel();

		checkorektDate(txtStartDate);
		checkorektDate(txtEndDate);
		return panel2A;
	}

	private JPanel panel_3() {

		String referencePerson_BackToTable = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_BackToTable");
		
		JPanel panel3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel3.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel3);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setPreferredSize(new Dimension(637, 14));
		panel3.add(lblNewLabel);

//		ActionListenerComboBox_Results();

		btnBackToTable = new JButton(referencePerson_BackToTable);
		btnBackToTable.setEnabled(false);
		btnBackToTable.setMargin(new Insets(2, 2, 2, 2));
		btnBackToTable.setPreferredSize(new Dimension(105, 20));
		panel3.add(btnBackToTable);

		ActionListenerBtnBackToTable();

		return panel3;
	}

	public static void addListStringSelectionPersonToComboBox(List<PersonExcellClass> listSelectionPerson,
			Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (PersonExcellClass person : listSelectionPerson) {
			list.add(person.getPerson().getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person.getPerson()));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}

	public static void addListStringSelectionPersonToComboBoxPerson(List<Person> listSelectionPerson,
			Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (Person person : listSelectionPerson) {
			list.add(person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}
	
	public static void checkorektDate(JTextField textFieldDate) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SearchDBase.setEnabled(false);
					btn_SearchFromExcel.setEnabled(false);
					
				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SearchDBase.setEnabled(true);
					btn_SearchFromExcel.setEnabled(true);
				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}
	
	protected static Object[][] createDataTableFromListPerson(List<Person> listSelectionPerson) {

		Object[][] dataTable = new Object[listSelectionPerson.size()][9];

//				"EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod KZ-1",	"Kod KZ-2",	"Kod Hog"	

		int k = 0;
		for (Person person : listSelectionPerson) {
			System.out.println("egn " + person.getEgn());
			dataTable[k][0] = k+1;
			dataTable[k][1] = person.getEgn();
			dataTable[k][2] = person.getFirstName();
			dataTable[k][3] = person.getSecondName();
			dataTable[k][4] = person.getLastName();
			dataTable[k][5] = getLastWorkplaceByPerson(person);
			dataTable[k][6] = getLastKodeByPersonAndZone(person, 1);
			dataTable[k][7] = getLastKodeByPersonAndZone(person, 2);
			dataTable[k][8] = getLastKodeByPersonAndZone(person, 3);

			k++;
		}

		return dataTable;

	}

public static Object[][] createDataTableFromPersonExcellClass(List<PersonExcellClass> listSelectionPerson) {
		
	Object[][] dataTable = new Object[listSelectionPerson.size()][9];
	
//				"EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod KZ-1",	"Kod KZ-2",	"Kod Hog"	
				
		int k = 0;	
		for (PersonExcellClass person : listSelectionPerson) {
			dataTable[k][0] = k+1;
			dataTable[k][1] = person.getPerson().getEgn() ;
			dataTable[k][2] = person.getPerson().getFirstName();
			dataTable[k][3] = person.getPerson().getSecondName();
			dataTable[k][4] = person.getPerson().getLastName();
			dataTable[k][5] = person.getOtdel();
			dataTable[k][6] = person.getKz1();
			dataTable[k][7] = person.getKz2();
			dataTable[k][8] = person.getHog();
			
			k++;
		}
		
		return dataTable;

	}
	
	
	
	public static String getLastWorkplaceByPerson(Person person) {
				
		PersonStatusNew per = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person);
		
		return per == null? "":per.getWorkplace().getOtdel();
	}

	public static String getLastKodeByPersonAndZone(Person person, int zonaID) {
		List<KodeStatus> list = KodeStatusDAO.getKodeStatusByPersonZone(person, zonaID);
		if (list != null) {
			List<KodeStatus> sortedReversKodeStatList = list.stream()
					.sorted(Comparator.comparing(KodeStatus::getKodeStatus_ID).reversed()).collect(Collectors.toList());
			return sortedReversKodeStatList.get(0).getKode();
		}
		return "-";
	}

	protected String createStringToInfoPanel(List<Person> listSelectionPerson) {
		String str = "";
		for (Person person : listSelectionPerson) {
			str = str + person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person) + "\n";
		}
		return str;
	}

	public static List<Person> getListSearchingPerson() {
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		
		
		List<PersonStatusNew> listSelectionPersonDokument = new ArrayList<>();
		List<PersonStatusNew> listSelectionPersonStartDate = new ArrayList<>();
		List<PersonStatusNew> listSelectionPersonEndDate = new ArrayList<>();
		List<PersonStatusNew> listSelectionPersonOtdel = new ArrayList<>();
		
	
		String dokument = PersonReference_Dokument_Frame.getTextField_Dokument().getText();
		String startDate = PersonReference_Dokument_Frame.getTextField_StartDate().getText();
		String endDate = PersonReference_Dokument_Frame.getTextField_EndDate().getText();
		Date stDate = null, enDate = null;
		
		
		String otdel = PersonReference_Dokument_Frame.getComboBox_Otdel().getSelectedItem();
		
		
		List<PersonStatusNew> listAllPerson = PersonStatusNewDAO.getValuePersonStatusNewByYear(textField_Year.getText());
		
		List<Person> listSelectionPerson = new ArrayList<>();

		if (!dokument.trim().isEmpty()) {
			for (PersonStatusNew person : listAllPerson) {
				if (person.getFormulyarName().contains(dokument)) {
					listSelectionPersonDokument.add(person);
				}
			}
		} else {
			listSelectionPersonDokument = listAllPerson;
		}
		System.out.println("listSelectionPersonEGN = " + listSelectionPersonDokument.size());

		if (!startDate.trim().isEmpty()) {
			try {
				stDate = sdf2.parse(startDate);
			} catch (ParseException e) {
			e.printStackTrace();
			}
			for (PersonStatusNew excellPerson : listSelectionPersonDokument) {
				if (excellPerson.getStartDate().equals(stDate)) {
					listSelectionPersonStartDate.add(excellPerson);
				}
			}
		} else {
			listSelectionPersonStartDate = listSelectionPersonDokument;
		}

		if (!endDate.trim().isEmpty()) {
			try {
				enDate = sdf2.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (PersonStatusNew excellPerson : listSelectionPersonStartDate) {
				if (excellPerson.getEndDate().equals(enDate)) {
					listSelectionPersonEndDate.add(excellPerson);
				}
			}
		} else {
			listSelectionPersonEndDate = listSelectionPersonStartDate;
		}
		System.out.println("listSelectionPersonKZHog = " + listSelectionPersonEndDate.size());
		
		if (otdel != null && !otdel.trim().isEmpty()) {
			for (PersonStatusNew person : listSelectionPersonEndDate) {
				if (person.getWorkplace().getOtdel().contains(otdel)) {
					listSelectionPersonOtdel.add(person);
				}
			}

		} else {
			listSelectionPersonOtdel = listSelectionPersonEndDate;
		}
		
		System.out.println("listSelectionPersonLast = " + listSelectionPersonOtdel.size());
		
		for (PersonStatusNew personDokumentExcellClass : listSelectionPersonOtdel) {
			listSelectionPerson.add(personDokumentExcellClass.getPerson());
		}
		
		return RemouveDublikateFromList.removeDuplicates(new ArrayList<Person>(listSelectionPerson));
	}


	private void ActionListenerComboBox_Firm() {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("/////////////////////////");
					setitemInChoise();
				}
			}
		});

	}

	private void ActionListenerbBtn_SearchDBase() {
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!allFieldsEmnty()) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					dataTable = null;
					textArea.setText("");
//					comboBox_Results.removeAll();
				
					List<Person> listSelectionPerson = getListSearchingPerson();
//					addListStringSelectionPersonToComboBoxPerson(listSelectionPerson, comboBox_Results);

					if (listSelectionPerson.size() == 0) {
						textArea.setText(notResults);
						viewInfoPanel();

					}

					if (listSelectionPerson.size() == 1) {
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_Year.getText(),
								listSelectionPerson.get(0), false, 0));
						viewInfoPanel();
					}

					if (listSelectionPerson.size() > 1) {
						System.out.println("***** " + listSelectionPerson.size());
						dataTable = createDataTableFromListPerson(listSelectionPerson);
						panel_infoPanelTablePanel(dataTable);
						viewTablePanel();
					}

					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}

		});

	}

	private void ActionListenerbBtn_SearchFromExcel() {

		btn_SearchFromExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (!allFieldsEmnty()){
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					dataTable = null;
					textArea.setText("");
//					comboBox_Results.removeAll();
					List<PersonExcellClass> listSelectionPerson = SearchInExcellFilesByDokument.getListSearchingPerson();
					
					System.out.println("listSelectionPerson " + listSelectionPerson.size());
					
//					addListStringSelectionPersonToComboBox(listSelectionPerson, comboBox_Results);

					if (listSelectionPerson.size() == 0) {
						textArea.setText(notResults);
						viewInfoPanel();

					}

					if (listSelectionPerson.size() == 1) {
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(curentYear,
								listSelectionPerson.get(0).getPerson(), true, 0));
						viewInfoPanel();
					}

					if (listSelectionPerson.size() > 1) {
						System.out.println("***** " + listSelectionPerson.size());
						dataTable = createDataTableFromPersonExcellClass(listSelectionPerson);
						panel_infoPanelTablePanel(dataTable);
						viewTablePanel();
					}

					GeneralMethods.setDefaultCursor(panel_AllSaerch);

				}
			}
		});

	}

	
	public static void ActionListenerSetDateByDatePicker(JTextField textField_StartDate2) {

		textField_StartDate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e1) {
				if (e1.getClickCount() == 2) {
				Point pointFrame = textField_StartDate2.getLocationOnScreen();
				final JFrame f = new JFrame();
				DatePicker dPicer = new DatePicker(f, false, textField_StartDate2.getText(), pointFrame);
				String str = dPicer.setPickedDate(false);

				textField_StartDate2.setText(str);
				checkorektDate(textField_StartDate2);
				}
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkorektDate(textField_StartDate2);
				
			}

		});

	}
	
	

	private void ActionListenertextField_Year() {
		textField_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				textField_Year.setForeground(Color.BLACK);
				btn_SearchFromExcel.setEnabled(true);
				btn_SearchDBase.setEnabled(true);
				if (!textField_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_Year.getText());
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)+1) {
							textField_Year.setForeground(Color.RED);
							btn_SearchFromExcel.setEnabled(false);
							btn_SearchDBase.setEnabled(false);
						}
					} catch (Exception e) {
						textField_Year.setForeground(Color.RED);
						btn_SearchFromExcel.setEnabled(false);
						btn_SearchDBase.setEnabled(false);
					}
				}
			}
		});

	}

	private void ActionListenerBtnBackToTable() {

		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataTable!=null) {
				panel_infoPanelTablePanel(dataTable);
				viewTablePanel();
				}
			}

		});

	}

	protected static boolean allFieldsEmnty() {
		return (txtDokument.getText().trim().isEmpty() && txtStartDate.getText().trim().isEmpty()
				&& txtEndDate.getText().trim().isEmpty() && comboBox_Otdel.getSelectedItem().trim().isEmpty());
	}

	public static void TextFieldJustNumbers(JTextField field) {
		((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
	}

	private JPanel panel_Button() {

		String referencePerson_Export = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Export");
		
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton btn_Export = new JButton(referencePerson_Export);
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnBackToTable.isEnabled() || dataTable == null) {

					PersonReferenceExportToExcell.btnExportInfoPersonToExcell(TextInAreaTextPanel.getPerson(),
							TextInAreaTextPanel.getMasivePersonStatusName(),
							TextInAreaTextPanel.getMasivePersonStatus(), TextInAreaTextPanel.getZoneNameMasive(),
							TextInAreaTextPanel.getMasiveKode(), TextInAreaTextPanel.getMasiveMeasurName(),
							TextInAreaTextPanel.getMasiveMeasur(), buttonPanel);
				} else {
					PersonReferenceExportToExcell.btnExportTableToExcell(dataTable, getTabHeader(), buttonPanel, "PersonReference");

				}
			}
		});
		buttonPanel.add(btn_Export);

		return buttonPanel;
	}

	private void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		btnBackToTable.setEnabled(false);

		repaint();
		revalidate();
	}

	void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		if (dataTable != null) {
			btnBackToTable.setEnabled(true);
		}
		repaint();
		revalidate();
	}

	private void panel_infoPanelTablePanel(Object[][] dataTable) {
		String[] columnNames = getTabHeader();
		@SuppressWarnings("rawtypes")
		Class[] types = getCulumnClass();
		int egn_code_Colum = 1;

		DefaultTableModel dtm = new DefaultTableModel();
		final JTable table = new JTable(dtm);

		dtm = new DefaultTableModel(dataTable, columnNames) {

			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			private Class[] types2 = types;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return this.types2[columnIndex];
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return dataTable[row][col];
			}

			@SuppressWarnings("unused")
			public void setValueAt(Object value, int row, int col) {

				if (!dataTable[row][col].equals(value)) {
					dataTable[row][col] = value;
					fireTableCellUpdated(row, col);

				}
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				return dataTable.length;
			}

		};

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if (table.getSelectedColumn() == egn_code_Colum) {
					table.rowAtPoint(e.getPoint());
					table.columnAtPoint(e.getPoint());
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();

					System.out.println(reqCodeStr);

				}

				if (e.getClickCount() == 2 && getSelectedModelRow(table) != -1) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(
							TextInAreaTextPanel.createInfoPanelForPerson(curentYear, person, false, 0));
					viewInfoPanel();
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);

		dtm.fireTableDataChanged();
		table.setModel(dtm);
		table.setFillsViewportHeight(true);
		table.repaint();
		System.out.println("+++++++++++++ " + dataTable.length);

		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);

	}

	private int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	private String[] getTabHeader() {
		String[] tableHeader = {"№", referencePerson_EGN, referencePerson_FirstName, referencePerson_SecondName, referencePerson_LastName,
				referencePersonMeasur_otdel, referencePerson_ID_KZ_1, referencePerson_ID_KZ_2, referencePerson_ID_KZ_HOG };
		return tableHeader;
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass() {
		Class[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class};
		return types;
	}
	
	public static ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
		ArrayList<String> list = new ArrayList<String>();
		for (Workplace workplace : valueWorkplaceByObject) {
			list.add(workplace.getOtdel());
		}
		return list;
	}

	private void setitemInChoise() {
		listAdd = listOtdelVO;
		if (((String) comboBox_Firm.getSelectedItem()).trim().isEmpty()) {
			listAdd = listOtdelAll;
		} else {
			if (((String) comboBox_Firm.getSelectedItem()).trim().equals("АЕЦ Козлодуй")) {
				listAdd = listOtdelKz;
			}
		}
		addItem(comboBox_Otdel, listAdd);
	}

	private void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	public static Choice getComboBox_Otdel() {
		return comboBox_Otdel;
	}


	public static JTextField getTextField_Dokument() {
		return txtDokument;
	}

	public static JTextField getTextField_StartDate() {
		return txtStartDate;
	}

	public static JTextField getTextField_EndDate() {
		return txtEndDate;
	}

	
	public static JTextArea getTextArea() {
		return textArea;
	}

	public static JButton getBtn_SearchFromExcel() {
		return btn_SearchFromExcel;
	}

	

}
