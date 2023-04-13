package PersonReference;

import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import AutoInsertMeasuting.InsertMeasurToExcel;

import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;
import PersonManagement.PersonelManegementFrame;

import javax.swing.SwingConstants;

import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JTable;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class PersonReferenceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private Choice comboBox_Firm;
	private static Choice comboBox_Otdel;
	private static Choice comboBox_Results;

	private static JTextArea textArea;
	private static JTextField textField_EGN;
	private static JTextField textField_FName;
	private static JTextField textField_SName;
	private static JTextField textField_LName;
	private static JTextField textField_KZ1;
	private static JTextField textField_KZ2;
	private static JTextField textField_KZHOG;
	private static JTextField textField_Year;

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
	String[][] dataTable;
	
	static String curentYear = AplicationMetods.getCurentYear();
	public PersonReferenceFrame(ActionIcone round) {

		setMinimumSize(new Dimension(730, 900));

		String minYearInDbase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("minYearInDbase");
		notResults = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
		String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
		String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");

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

		panel_1();
		panel_1A();
		panel_2();
		panel_2A();
		panel_3();

		getRootPane().setDefaultButton(btn_SearchDBase);

		panel_Button();

		setSize(737, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		round.StopWindow();
	}

	private JPanel panel_1() {
		JPanel panel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel1.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1);

		JLabel lbl_EGN = new JLabel("EGN");
		lbl_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EGN.setSize(new Dimension(80, 20));
		lbl_EGN.setPreferredSize(new Dimension(85, 15));
		lbl_EGN.setMinimumSize(new Dimension(80, 20));
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(lbl_EGN);

		JLabel distantion = new JLabel();
		distantion.setPreferredSize(new Dimension(30, 14));
		panel1.add(distantion);

		JLabel lbl_FirstName = new JLabel("First Name");
		lbl_FirstName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_FirstName.setSize(new Dimension(220, 20));
		lbl_FirstName.setPreferredSize(new Dimension(126, 15));
		lbl_FirstName.setMinimumSize(new Dimension(220, 20));
		lbl_FirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_FirstName.setBorder(null);
		panel1.add(lbl_FirstName);

		JLabel lbl_L_SecondName = new JLabel("Second Name");
		lbl_L_SecondName.setSize(new Dimension(70, 20));
		lbl_L_SecondName.setPreferredSize(new Dimension(126, 15));
		lbl_L_SecondName.setMinimumSize(new Dimension(70, 20));
		lbl_L_SecondName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_SecondName.setBorder(null);
		lbl_L_SecondName.setAlignmentX(1.0f);
		panel1.add(lbl_L_SecondName);

		JLabel lbl_L_LastName = new JLabel("Last Name");
		lbl_L_LastName.setSize(new Dimension(70, 20));
		lbl_L_LastName.setPreferredSize(new Dimension(126, 15));
		lbl_L_LastName.setMinimumSize(new Dimension(70, 20));
		lbl_L_LastName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_LastName.setBorder(null);
		lbl_L_LastName.setAlignmentX(1.0f);
		panel1.add(lbl_L_LastName);
		return panel1;
	}

	private JPanel panel_1A() {
		JPanel panel1A = new JPanel();
		panel1A.setPreferredSize(new Dimension(10, 30));
		FlowLayout fl_panel1A = (FlowLayout) panel1A.getLayout();
		fl_panel1A.setVgap(2);
		fl_panel1A.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1A);

		textField_EGN = new JTextField();
		textField_EGN.setPreferredSize(new Dimension(5, 20));
		textField_EGN.setMinimumSize(new Dimension(5, 20));
		panel1A.add(textField_EGN);
		textField_EGN.setColumns(10);

		TextFieldJustNumbers(textField_EGN);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setPreferredSize(new Dimension(29, 14));
		panel1A.add(lblNewLabel_1);

		textField_FName = new JTextField();
		textField_FName.setPreferredSize(new Dimension(5, 20));
		textField_FName.setMinimumSize(new Dimension(5, 20));
		textField_FName.setColumns(15);
		panel1A.add(textField_FName);

		textField_SName = new JTextField();
		textField_SName.setPreferredSize(new Dimension(5, 20));
		textField_SName.setMinimumSize(new Dimension(5, 20));
		textField_SName.setColumns(15);
		panel1A.add(textField_SName);

		textField_LName = new JTextField();
		textField_LName.setPreferredSize(new Dimension(5, 20));
		textField_LName.setMinimumSize(new Dimension(5, 20));
		textField_LName.setColumns(15);
		panel1A.add(textField_LName);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setPreferredSize(new Dimension(50, 14));
		panel1A.add(lblNewLabel_1_1);

		btn_SearchDBase = new JButton("Search from DBase");
		btn_SearchDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase.setIconTextGap(1);
		btn_SearchDBase.setPreferredSize(new Dimension(110, 23));
		panel1A.add(btn_SearchDBase);

		ActionListenerbBtn_SearchDBase();

		return panel1A;
	}

	private JPanel panel_2() {
		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);

		JLabel lbl_Year = new JLabel("Year");
		lbl_Year.setToolTipText("");
		lbl_Year.setSize(new Dimension(80, 20));
		lbl_Year.setPreferredSize(new Dimension(38, 15));
		lbl_Year.setMinimumSize(new Dimension(80, 20));
		lbl_Year.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panel2.add(lbl_Year);

		JLabel lbl_KodKZ1 = new JLabel("ID KZ-1");
		lbl_KodKZ1.setSize(new Dimension(80, 20));
		lbl_KodKZ1.setPreferredSize(new Dimension(53, 15));
		lbl_KodKZ1.setMinimumSize(new Dimension(80, 20));
		lbl_KodKZ1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ1.setBorder(null);
		lbl_KodKZ1.setAlignmentX(0.5f);
		panel2.add(lbl_KodKZ1);

		JLabel lbl_KodKZ2 = new JLabel("ID KZ-2");
		lbl_KodKZ2.setSize(new Dimension(80, 20));
		lbl_KodKZ2.setPreferredSize(new Dimension(55, 15));
		lbl_KodKZ2.setMinimumSize(new Dimension(80, 20));
		lbl_KodKZ2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ2.setBorder(null);
		lbl_KodKZ2.setAlignmentX(0.5f);
		panel2.add(lbl_KodKZ2);

		JLabel lbl_KodKZHOG = new JLabel("ID KZ-HOG");
		lbl_KodKZHOG.setSize(new Dimension(80, 20));
		lbl_KodKZHOG.setPreferredSize(new Dimension(53, 15));
		lbl_KodKZHOG.setMinimumSize(new Dimension(80, 20));
		lbl_KodKZHOG.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZHOG.setBorder(null);
		lbl_KodKZHOG.setAlignmentX(0.5f);
		panel2.add(lbl_KodKZHOG);

		JLabel lbl_L_Firm = new JLabel("Firm");
		lbl_L_Firm.setSize(new Dimension(70, 20));
		lbl_L_Firm.setPreferredSize(new Dimension(120, 15));
		lbl_L_Firm.setMinimumSize(new Dimension(70, 20));
		lbl_L_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Firm.setBorder(null);
		lbl_L_Firm.setAlignmentX(1.0f);
		panel2.add(lbl_L_Firm);

		JLabel lbl_L_Otdel = new JLabel("Otdel");
		lbl_L_Otdel.setSize(new Dimension(70, 20));
		lbl_L_Otdel.setPreferredSize(new Dimension(200, 15));
		lbl_L_Otdel.setMinimumSize(new Dimension(70, 20));
		lbl_L_Otdel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Otdel.setBorder(null);
		lbl_L_Otdel.setAlignmentX(1.0f);
		panel2.add(lbl_L_Otdel);

		return panel2;
	}

	private JPanel panel_2A() {
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

		textField_KZ1 = new JTextField();
		textField_KZ1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_KZ1.setPreferredSize(new Dimension(5, 20));
		textField_KZ1.setMinimumSize(new Dimension(5, 20));
		textField_KZ1.setColumns(6);
		panel2A.add(textField_KZ1);

		textField_KZ2 = new JTextField();
		textField_KZ2.setPreferredSize(new Dimension(5, 20));
		textField_KZ2.setMinimumSize(new Dimension(5, 20));
		textField_KZ2.setColumns(6);
		panel2A.add(textField_KZ2);

		textField_KZHOG = new JTextField();
		textField_KZHOG.setPreferredSize(new Dimension(5, 20));
		textField_KZHOG.setMinimumSize(new Dimension(5, 20));
		textField_KZHOG.setColumns(6);
		panel2A.add(textField_KZHOG);

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

		btn_SearchFromExcel = new JButton("Search from Excell");
		btn_SearchFromExcel.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchFromExcel.setPreferredSize(new Dimension(110, 23));
		panel2A.add(btn_SearchFromExcel);

		ActionListenerbBtn_SearchFromExcel();

		return panel2A;
	}

	private JPanel panel_3() {

		JPanel panel3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel3.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel3);

		comboBox_Results = new Choice();
		comboBox_Results.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Results.setPreferredSize(new Dimension(590, 20));
		panel3.add(comboBox_Results);

		ActionListenerComboBox_Results();

		btnBackToTable = new JButton("BackToTable");
		btnBackToTable.setEnabled(false);
		btnBackToTable.setMargin(new Insets(2, 2, 2, 2));
		btnBackToTable.setPreferredSize(new Dimension(90, 20));
		panel3.add(btnBackToTable);

		ActionListenerBtnBackToTable();

		return panel3;
	}

	public static void addListStringSelectionPersonToComboBox(List<Person> listSelectionPerson,
			Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (Person person : listSelectionPerson) {
			list.add(person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}

	protected static String[][] addListStringSelectionPersonToComboBox(List<Person> listSelectionPerson) {

		String[][] dataTable = new String[listSelectionPerson.size()][8];

//				"EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod KZ-1",	"Kod KZ-2",	"Kod Hog"	

		int k = 0;
		for (Person person : listSelectionPerson) {
			System.out.println("egn " + person.getEgn());
			dataTable[k][0] = person.getEgn();
			dataTable[k][1] = person.getFirstName();
			dataTable[k][2] = person.getSecondName();
			dataTable[k][3] = person.getLastName();
			dataTable[k][4] = getLastWorkplaceByPerson(person);
			dataTable[k][5] = getLastKodeByPersonAndZone(person, 1);
			dataTable[k][6] = getLastKodeByPersonAndZone(person, 2);
			dataTable[k][7] = getLastKodeByPersonAndZone(person, 3);

			k++;
		}

		return dataTable;

	}

	private static String getLastWorkplaceByPerson(Person person) {
		List<PersonStatus> list = PersonStatusDAO.getValuePersonStatusByObjectSortByColumnName("Person_ID", person,
				"DateSet");
		List<PersonStatus> sortedReversPeStatList = list.stream()
				.sorted(Comparator.comparing(PersonStatus::getPersonStatus_ID).reversed()).collect(Collectors.toList());
		if (sortedReversPeStatList.size() > 0) {
			return sortedReversPeStatList.get(0).getWorkplace().getOtdel();
		} else {
			return "";
		}
	}

	private static String getLastKodeByPersonAndZone(Person person, int zonaID) {
		List<KodeStatus> list = KodeStatusDAO.getKodeStatusByPersonZone(person, zonaID);
		if (list != null) {
			List<KodeStatus> sortedReversKodeStatList = list.stream()
					.sorted(Comparator.comparing(KodeStatus::getYear).reversed()).collect(Collectors.toList());
			return sortedReversKodeStatList.get(0).getKode();
		}
		return "-";
	}

	protected String createStringToInfoPanel(List<Person> listSelectionPerson) {
		String str = "";
		for (Person person : listSelectionPerson) {
			str = str + person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person) + "\n";
		}
		return str;
	}

	public static List<Person> getListSearchingPerson(boolean fromManegementFrame) {
		List<Person> listSelectionPersonEGN = new ArrayList<>();
		List<Person> listSelectionPersonFName = new ArrayList<>();
		List<Person> listSelectionPersonSName = new ArrayList<>();
		List<Person> listSelectionPersonLName = new ArrayList<>();
		List<Person> listSelectionPersonKZ1 = new ArrayList<>();
		List<Person> listSelectionPersonKZ2 = new ArrayList<>();
		List<Person> listSelectionPersonKZHog = new ArrayList<>();
		List<Person> listSelectionPersonOtdel = new ArrayList<>();

		String egn = "";
		String firstName = "";
		String secontName = "";
		String lastName = "";
		String kz1 = "";
		String kz2 = "";
		String kzHog = "";
		String year = "";
		String otdel = "";

		if (fromManegementFrame) {
			egn = PersonReferenceFrame.getTextField_EGN().getText();
			firstName = PersonReferenceFrame.getTextField_FName().getText();
			secontName = PersonReferenceFrame.getTextField_SName().getText();
			lastName = PersonReferenceFrame.getTextField_LName().getText();
			kz1 = PersonReferenceFrame.getTextField_KZ1().getText();
			kz2 = PersonReferenceFrame.getTextField_KZ2().getText();
			kzHog = PersonReferenceFrame.getTextField_KZHOG().getText();
			year = PersonReferenceFrame.getTextField_Year().getText();
			otdel = PersonReferenceFrame.getComboBox_Otdel().getSelectedItem();
		} else {
			egn = PersonelManegementFrame.getTextField_EGN().getText();
			firstName = PersonelManegementFrame.getTextField_FName().getText();
			secontName = PersonelManegementFrame.getTextField_SName().getText();
			lastName = PersonelManegementFrame.getTextField_LName().getText();
		}
		
		KodeStatus kodeStatusNull = null;
		
		List<Person> listAllPerson = PersonDAO.getAllValuePerson();

		if (!egn.trim().isEmpty()) {
			for (Person person : listAllPerson) {
				if (person.getEgn().contains(egn)) {
					listSelectionPersonEGN.add(person);
				}
			}
		} else {
			listSelectionPersonEGN = listAllPerson;
		}
		System.out.println("listSelectionPersonEGN = " + listSelectionPersonEGN.size());

		if (!firstName.trim().isEmpty()) {
			for (Person person : listSelectionPersonEGN) {
				if (SearchFromExcellFiles.checkContainsStrings(person.getFirstName(), firstName)) {
					listSelectionPersonFName.add(person);
				}
			}
		} else {
			listSelectionPersonFName = listSelectionPersonEGN;
		}
		System.out.println("listSelectionPersonFName = " + listSelectionPersonFName.size());
		if (!secontName.trim().isEmpty()) {
			for (Person person : listSelectionPersonFName) {
				if (SearchFromExcellFiles.checkContainsStrings(person.getSecondName(), secontName)) {
					listSelectionPersonSName.add(person);
				}
			}
		} else {
			listSelectionPersonSName = listSelectionPersonFName;
		}
		System.out.println("listSelectionPersonSName = " + listSelectionPersonSName.size());

		if (!lastName.trim().isEmpty()) {
			for (Person person : listSelectionPersonSName) {
				if (SearchFromExcellFiles.checkContainsStrings(person.getLastName(), lastName)) {
					listSelectionPersonLName.add(person);
				}
			}
		} else {
			listSelectionPersonLName = listSelectionPersonSName;
		}
		System.out.println("listSelectionPersonLName = " + listSelectionPersonLName.size());
		kodeStatusNull = setNullKodeStatus();
		KodeStatus kodeStat;
		if (!kz1.trim().isEmpty()) {
			if (year.isEmpty()) {
				List<KodeStatus> listKodeStst = KodeStatusDAO.getKodeStatusByZoneAndKode(1, kz1);
				if (listKodeStst != null) {
					for (Person person : listSelectionPersonLName) {
						for (KodeStatus kodeStatus : listKodeStst) {
							if (kodeStatus != null && kodeStatus.getPerson().getId_Person() == person.getId_Person()) {
								if(!kodeStatusNull.getPerson().getEgn().equals(kodeStatus.getPerson().getEgn()))
									listSelectionPersonKZ1.add(kodeStatus.getPerson());
									kodeStatusNull = kodeStatus;
							}
						}
					}
				}
			} else {
				kodeStat = KodeStatusDAO.getKodeStatusByZoneYaerAndKode(1, year, kz1);
				for (Person person : listSelectionPersonLName) {
					if (kodeStat != null && kodeStat.getPerson().getId_Person() == person.getId_Person()) {
						listSelectionPersonKZ1.add(kodeStat.getPerson());
					}
				}
			}
		} else {
			listSelectionPersonKZ1 = listSelectionPersonLName;
		}
		System.out.println("listSelectionPersonKZ1 = " + listSelectionPersonKZ1.size());
		kodeStatusNull = setNullKodeStatus();
		if (!kz2.trim().isEmpty()) {
			
			if (year.isEmpty()) {
				List<KodeStatus> listKodeStst = KodeStatusDAO.getKodeStatusByZoneAndKode(2, kz2);
				if (listKodeStst != null) {
					
					for (Person person : listSelectionPersonKZ1) {
						
						for (KodeStatus kodeStatus : listKodeStst) {
							if (kodeStatus != null && kodeStatus.getPerson().getId_Person() == person.getId_Person()) {
								if(!kodeStatusNull.getPerson().getEgn().equals(kodeStatus.getPerson().getEgn()))
								listSelectionPersonKZ2.add(kodeStatus.getPerson());
								kodeStatusNull = kodeStatus;
							}
						}
					}
				}
			} else {
				kodeStat = KodeStatusDAO.getKodeStatusByZoneYaerAndKode(2, year, kz2);
				for (Person person : listSelectionPersonKZ1) {
					if (kodeStat != null && kodeStat.getPerson().getId_Person() == person.getId_Person()) {
						listSelectionPersonKZ2.add(kodeStat.getPerson());
					}
				}
			}
		} else {
			listSelectionPersonKZ2 = listSelectionPersonKZ1;
		}
		System.out.println("listSelectionPersonKZ2 = " + listSelectionPersonKZ2.size());
		kodeStatusNull = setNullKodeStatus();
		if (!kzHog.trim().isEmpty()) {
		
				if (year.isEmpty()) {
					List<KodeStatus> listKodeStst = KodeStatusDAO.getKodeStatusByZoneAndKode(3, kzHog);
					if (listKodeStst != null) {
						for (Person person : listSelectionPersonKZ2) {
							for (KodeStatus kodeStatus : listKodeStst) {
								if (kodeStatus != null && kodeStatus.getPerson().getId_Person() == person.getId_Person()) {
									if(!kodeStatusNull.getPerson().getEgn().equals(kodeStatus.getPerson().getEgn()))
										listSelectionPersonKZHog.add(kodeStatus.getPerson());
										kodeStatusNull = kodeStatus;
								}
							}
						}
					}
				} else {
					kodeStat = KodeStatusDAO.getKodeStatusByZoneYaerAndKode(3, year, kzHog);
					for (Person person : listSelectionPersonKZ2) {
						if (kodeStat != null && kodeStat.getPerson().getId_Person() == person.getId_Person()) {
							listSelectionPersonKZHog.add(kodeStat.getPerson());
					}
				}
			}
				
		} else {
			listSelectionPersonKZHog = listSelectionPersonKZ2;
		}
		System.out.println("listSelectionPersonKZHog = " + listSelectionPersonKZHog.size());

		List<PersonStatus> listAllPerStat = new ArrayList<>();
		if (otdel != null && !otdel.trim().isEmpty()) {
			Workplace workplace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", otdel).get(0);
			for (Person person : listSelectionPersonKZHog) {
				List<PersonStatus> listPerStat = PersonStatusDAO.getValuePersonStatusByPersonAndWorkplace(person,
						workplace);
				if (listPerStat.size() > 0) {
					listAllPerStat.addAll(listPerStat);

					System.out.println(person.getEgn() + " " + listPerStat.size());
				}

			}
			System.out.println("listSelectionPersonEGN = " + listSelectionPersonEGN.size());
			if (!year.trim().isEmpty()) {

				for (Spisak_Prilogenia spPr : Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByObject("Year", year)) {
					for (PersonStatus prStat : listAllPerStat) {

						if (prStat.getSpisak_prilogenia().getSpisak_Prilogenia_ID() == spPr.getSpisak_Prilogenia_ID()) {
							listSelectionPersonOtdel.add(prStat.getPerson());
						}
					}
				}
			} else {
				for (PersonStatus prStat : listAllPerStat) {
					listSelectionPersonOtdel.add(prStat.getPerson());
				}
			}

		} else {
			listSelectionPersonOtdel = listSelectionPersonKZHog;
		}
		System.out.println("listSelectionPersonOtdel = " + listSelectionPersonOtdel.size());
		return RemouveDublikateFromList.removeDuplicates(new ArrayList<Person>(listSelectionPersonOtdel));
	}

	private static KodeStatus setNullKodeStatus() {
		Person newPerson = new Person();
		newPerson.setEgn("9000009");
		KodeStatus kodeStatusNull = new KodeStatus();
		kodeStatusNull.setPerson(newPerson);
		return kodeStatusNull;
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
					comboBox_Results.removeAll();
					boolean fromManegementFrame = true;
					List<Person> listSelectionPerson = getListSearchingPerson(fromManegementFrame);
					addListStringSelectionPersonToComboBox(listSelectionPerson, comboBox_Results);

					if (listSelectionPerson.size() == 0) {
						textArea.setText(notResults);
						viewInfoPanel();

					}

					if (listSelectionPerson.size() == 1) {
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_Year.getText(),
								listSelectionPerson.get(0), false));
						viewInfoPanel();
					}

					if (listSelectionPerson.size() > 1) {
						System.out.println("***** " + listSelectionPerson.size());
						dataTable = addListStringSelectionPersonToComboBox(listSelectionPerson);
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

				if (!allFieldsEmnty() && !textField_Year.getText().trim().isEmpty()) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					dataTable = null;
					textArea.setText("");
					comboBox_Results.removeAll();
					List<PersonExcellClass> listSelectionPerson = SearchFromExcellFiles.getListSearchingPerson();
					SearchFromExcellFiles.addListStringSelectionPersonToComboBox(listSelectionPerson, comboBox_Results);

					if (listSelectionPerson.size() == 0) {
						textArea.setText(notResults);
						viewInfoPanel();

					}

					if (listSelectionPerson.size() == 1) {
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_Year.getText(),
								listSelectionPerson.get(0).getPerson(), true));
						viewInfoPanel();
					}

					if (listSelectionPerson.size() > 1) {
						System.out.println("***** " + listSelectionPerson.size());
						dataTable = SearchFromExcellFiles
								.addListStringSelectionPersonExcellClassToComboBox(listSelectionPerson);
						panel_infoPanelTablePanel(dataTable);
						viewTablePanel();
					}

					GeneralMethods.setDefaultCursor(panel_AllSaerch);

				}
			}
		});

	}

	private void ActionListenerComboBox_Results() {

		comboBox_Results.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textArea.setText("");
					repaint();
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					String str = comboBox_Results.getSelectedItem();
					int index = str.indexOf(" ");
					System.out.println("--->> " + str.substring(0, index));
					Person person = PersonDAO.getValuePersonByEGN(str.substring(0, index));
					textArea.setText(
							TextInAreaTextPanel.createInfoPanelForPerson(textField_Year.getText(), person, false));
					viewInfoPanel();
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}
		});

	}

	private void ActionListenertextField_Year() {
		textField_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				textField_Year.setForeground(Color.BLACK);
				btn_SearchFromExcel.setEnabled(true);
				if (!textField_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_Year.getText());
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)) {
							textField_Year.setForeground(Color.RED);
							btn_SearchFromExcel.setEnabled(false);
						}
					} catch (Exception e) {
						textField_Year.setForeground(Color.RED);
						btn_SearchFromExcel.setEnabled(false);
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
		return (textField_EGN.getText().trim().isEmpty() && textField_FName.getText().trim().isEmpty()
				&& textField_SName.getText().trim().isEmpty() && textField_LName.getText().trim().isEmpty()
				&& textField_KZ1.getText().trim().isEmpty() && textField_KZ2.getText().trim().isEmpty()
				&& textField_KZHOG.getText().trim().isEmpty() && comboBox_Otdel.getSelectedItem().trim().isEmpty());
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

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton btn_Export = new JButton("Export");
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("888888888888888888888888888888888888");
				if (btnBackToTable.isEnabled() || dataTable == null) {

					PersonReferenceExportToExcell.btnExportInfoPersonToExcell(TextInAreaTextPanel.getPerson(),
							TextInAreaTextPanel.getMasivePersonStatusName(),
							TextInAreaTextPanel.getMasivePersonStatus(), TextInAreaTextPanel.getZoneNameMasive(),
							TextInAreaTextPanel.getMasiveKode(), TextInAreaTextPanel.getMasiveMeasurName(),
							TextInAreaTextPanel.getMasiveMeasur(), buttonPanel);
				} else {
					PersonReferenceExportToExcell.btnExportTableToExcell(dataTable, getTabHeader(), buttonPanel);

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

	private void panel_infoPanelTablePanel(String[][] dataTable) {
		String[] columnNames = getTabHeader();
		int egn_code_Colum = 0;

		DefaultTableModel dtm = new DefaultTableModel();
		final JTable table = new JTable(dtm);

		dtm = new DefaultTableModel(dataTable, columnNames) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return dataTable[row][col];
			}

			@SuppressWarnings("unused")
			public void setValueAt(String value, int row, int col) {

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
							TextInAreaTextPanel.createInfoPanelForPerson(textField_Year.getText(), person, false));
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
		String[] tableHeader = { "EGN", "FirstName", "SecondName", "LastName", "Otdel", "Kod KZ-1", "Kod KZ-2",
				"Kod Hog" };
		return tableHeader;
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

	public static JTextField getTextField_EGN() {
		return textField_EGN;
	}

	public static JTextField getTextField_FName() {
		return textField_FName;
	}

	public static JTextField getTextField_SName() {
		return textField_SName;
	}

	public static JTextField getTextField_LName() {
		return textField_LName;
	}

	public static JTextField getTextField_KZ1() {
		return textField_KZ1;
	}

	public static JTextField getTextField_KZ2() {
		return textField_KZ2;
	}

	public static JTextField getTextField_KZHOG() {
		return textField_KZHOG;
	}

	public static JTextField getTextField_Year() {
		return textField_Year;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public static JButton getBtn_SearchFromExcel() {
		return btn_SearchFromExcel;
	}

	public static Choice getComboBox_Results() {
		return comboBox_Results;
	}
}
