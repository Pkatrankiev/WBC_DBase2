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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;

import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Insets;

public class PersonReferenceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_Search;
	private JScrollPane scrollPane;
	private Choice comboBox_Firm;
	private Choice comboBox_Otdel;
	private Choice comboBox_Results;

	private JTextField textField_EGN;
	private JTextField textField_FName;
	private JTextField textField_SName;
	private JTextField textField_LName;
	private JTextField textField_KZ1;
	private JTextField textField_KZ2;
	private JTextField textField_KZHOG;
	private JTextField textField_Year;
	private JTextArea textArea;
	
	private JButton btn_SearchDBase;
	private JButton btn_SearchFromExcel;
	
	private int minYeare;
	private String notResults;

	ArrayList<String> listOtdelKz;
	List<String> listOtdelVO;
	List<String> listOtdelAll;
	List<String> listAdd;
	List<String> listFirm;

	public PersonReferenceFrame() {
		
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
		
		
		setResizable(false);
		int y = 900, x = 730;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel_Search = new JPanel();
		panel_Search.setLayout(new BoxLayout(panel_Search, BoxLayout.Y_AXIS));
		contentPane.add(panel_Search, BorderLayout.NORTH);
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

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
		
		panel_infoPanel();

		panel_Button();

		setSize(x, y);
		setLocationRelativeTo(null);
		setVisible(true);

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
		textField_Year.setText("2023");
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

	protected void addListStringSelectionPersonToComboBox(List<Person> listSelectionPerson, Choice comboBox_Results) {
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

	protected String createStringToInfoPanel(List<Person> listSelectionPerson) {
		String str = "";
		for (Person person : listSelectionPerson) {
			str = str + person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person) + "\n";
		}
		return str;
	}

	protected String createInfoPanelForPerson(Person person) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		String year = textField_Year.getText(); 
		String str = person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person) + "\n";
		str = str + year + "\n";
		str = str + "Кодове: \n";
		List<KodeStatus> listK = KodeStatusDAO.getValueKodeStatusByObjectSortByColumnName("Person_ID", person, "Year");
		
			for (KodeStatus kodeStat : listK) {
				if (year.trim().isEmpty() || kodeStat.getYear().equals(year)) {
					
					str = str + kodeStat.getYear() + "  " + kodeStat.getZone().getNameTerritory() + " - " + kodeStat.getKode() + " "
							+ kodeStat.getZabelejkaKodeStatus() + "\n";
				}
			
		}
			
		str = str + "\n";
		str = str + "Заповеди \n";
		List<PersonStatus> listP = PersonStatusDAO.getValuePersonStatusByObject("Person_ID", person);
		if(!year.trim().isEmpty()) {
			for (PersonStatus perStat : listP) {
				List<Spisak_Prilogenia> listS =	Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByObject("Year", year);
				for (Spisak_Prilogenia spPr : listS) {
				if (perStat.getSpisak_prilogenia().getSpisak_Prilogenia_ID() == spPr.getSpisak_Prilogenia_ID()) {
					str = str +generateString( perStat);
				}
			}
				}
		}else {
			for (PersonStatus perStat : listP) {
				str = str +generateString( perStat);
				}
			}
				
		str = str + "\n";
		str = str + "Измервания СИЧ \n";
		List<Measuring> listM = MeasuringDAO.getValueMeasuringByObject("Person_ID", person);
		String data;
		
			for (Measuring measur : listM) {
				List<ResultsWBC> listR = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
				data = sdf.format(measur.getDate()).substring(6);
				System.out.println(data);
				if (year.trim().isEmpty() || data.equals(year)) {
					str = str + data + "  " + sdf.format(measur.getDate()) +" "+ measur.getDoze() 
							+" "+ measur.getDoseDimension().getDimensionName() 
							+" "+ measur.getLab().getLab()+ "\n";
					for (ResultsWBC result : listR) {
						str = str + result.getNuclideWBC().getSymbol_nuclide() 
						+" "+ result.getActivity() +" "+  DimensionWBCDAO.getValueDimensionWBCByID(4).getDimensionName()
						+" "+ result.getPostaplenie()  +" "+  DimensionWBCDAO.getValueDimensionWBCByID(4).getDimensionName() 
						+" "+ result.getGgp()   +" "+  DimensionWBCDAO.getValueDimensionWBCByID(7).getDimensionName()
						+" "+ result.getNuclideDoze()  +" "+  DimensionWBCDAO.getValueDimensionWBCByID(2).getDimensionName()
						+ "\n";	
					}
				
				
			}
		}
		
		
		return str;
	}

	private String generateString( PersonStatus perStat) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return 	 perStat.getSpisak_prilogenia().getYear() +"  "+ perStat.getWorkplace().getOtdel()+"  "+ perStat.getSpisak_prilogenia().getFormulyarName()
					+"  от "+ sdf.format(perStat.getSpisak_prilogenia().getStartDate())
					+"  до "+ sdf.format(perStat.getSpisak_prilogenia().getEndDate())
					+" "+ perStat.getZabelejka() + "\n";
		
	}

	protected List<Person> getListSearchingPerson() {
		List<Person> listSelectionPersonEGN = new ArrayList<>();
		List<Person> listSelectionPersonFName = new ArrayList<>();
		List<Person> listSelectionPersonSName = new ArrayList<>();
		List<Person> listSelectionPersonLName = new ArrayList<>();
		List<Person> listSelectionPersonKZ1 = new ArrayList<>();
		List<Person> listSelectionPersonKZ2 = new ArrayList<>();
		List<Person> listSelectionPersonKZHog = new ArrayList<>();
		List<Person> listSelectionPersonOtdel = new ArrayList<>();

		String egn = textField_EGN.getText();
		String firstName = textField_FName.getText();
		String secontName = textField_SName.getText();
		String lastName = textField_LName.getText();
		String kz1 = textField_KZ1.getText();
		String kz2 = textField_KZ2.getText();
		String kzHog = textField_KZHOG.getText();
		String year = textField_Year.getText();
		String otdel = comboBox_Otdel.getSelectedItem();
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

		if (!firstName.trim().isEmpty()) {
			for (Person person : listSelectionPersonEGN) {
				if (checkContainsStrings(person.getFirstName(),firstName)) {
					listSelectionPersonFName.add(person);
				}
			}
		} else {
			listSelectionPersonFName = listSelectionPersonEGN;
		}

		if (!secontName.trim().isEmpty()) {
			for (Person person : listSelectionPersonFName) {
				if (checkContainsStrings(person.getSecondName(),secontName)) {
					listSelectionPersonSName.add(person);
				}
			}
		} else {
			listSelectionPersonSName = listSelectionPersonFName;
		}

		if (!lastName.trim().isEmpty()) {
			for (Person person : listSelectionPersonSName) {
				if (checkContainsStrings(person.getLastName(),lastName)) {
					listSelectionPersonLName.add(person);
				}
			}
		} else {
			listSelectionPersonLName = listSelectionPersonSName;
		}

		KodeStatus kodeStat;
		if (!kz1.trim().isEmpty()) {
			for (Person person : listSelectionPersonLName) {
				kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, 1, year);
				if (kodeStat != null && kodeStat.getKode().contains(kz1)) {
					listSelectionPersonKZ1.add(kodeStat.getPerson());
				}
			}
		} else {
			listSelectionPersonKZ1 = listSelectionPersonLName;
		}

		if (!kz2.trim().isEmpty()) {
			for (Person person : listSelectionPersonKZ1) {
				kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, 2, year);
				if (kodeStat != null && kodeStat.getKode().contains(kz2)) {
					listSelectionPersonKZ2.add(kodeStat.getPerson());
				}
			}
		} else {
			listSelectionPersonKZ2 = listSelectionPersonKZ1;
		}

		if (!kzHog.trim().isEmpty()) {
			for (Person person : listSelectionPersonKZ2) {
				kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, 3, year);
				if (kodeStat != null && kodeStat.getKode().contains(kzHog)) {
					listSelectionPersonKZHog.add(kodeStat.getPerson());
				}
			}
		} else {
			listSelectionPersonKZHog = listSelectionPersonKZ2;
		}
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

		return RemouveDublikateFromList.removeDuplicates(new ArrayList<Person>(listSelectionPersonOtdel));
	}

	private boolean checkContainsStrings(String str1, String str2) {
		str1 = str1.toLowerCase().trim();
		str2 = str2.toLowerCase().trim();
	return str1.contains(str2);
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
 	
 	private void ActionListenerbBtn_SearchFromExcel() {
 		btn_SearchFromExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!allFieldsEmnty() && !textField_Year.getText().trim().isEmpty()){
				textArea.setText("");
				repaint();
				comboBox_Results.removeAll();
				List<Person> listSelectionPerson = getListSearchingPerson();
				addListStringSelectionPersonToComboBox(listSelectionPerson, comboBox_Results);
				if(listSelectionPerson.size()==0) {
					textArea.setText(notResults);
				}
				
				if(listSelectionPerson.size()==1) {
				textArea.setText(createInfoPanelForPerson(listSelectionPerson.get(0)));
			}
				}
			}
		});

	}
	
	private void ActionListenerbBtn_SearchDBase() {
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!allFieldsEmnty()){
				textArea.setText("");
				repaint();
				comboBox_Results.removeAll();
				List<Person> listSelectionPerson = getListSearchingPerson();
				addListStringSelectionPersonToComboBox(listSelectionPerson, comboBox_Results);
				if(listSelectionPerson.size()==0) {
					textArea.setText(notResults);
				}
				
				if(listSelectionPerson.size()==1) {
				textArea.setText(createInfoPanelForPerson(listSelectionPerson.get(0)));
			}
			}
			}
		});

	}
	 	
	private void ActionListenerComboBox_Results() {

		comboBox_Results.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textArea.setText("12");
					repaint();
					String str = comboBox_Results.getSelectedItem();
					int index = str.indexOf(" ");
					System.out.println(str.substring(0,index));
					Person person = PersonDAO.getValuePersonByEGN(str.substring(0,index));
					textArea.setText(createInfoPanelForPerson(person));
				}
			}
		});

	}
	
	private void ActionListenertextField_Year() {
		textField_Year.addKeyListener(new KeyAdapter() {

	        public void keyReleased(KeyEvent evt) {
	        	 textField_Year.setForeground(Color.BLACK);
	                btn_SearchFromExcel.setEnabled(true);
	        	if(!textField_Year.getText().isEmpty()) {
	            try {
	                long number = Long.parseLong(textField_Year.getText());
	                if(number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)) {
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
	
	protected boolean allFieldsEmnty() {
		return (textField_EGN.getText().trim().isEmpty() && textField_FName.getText().trim().isEmpty() &&
				textField_SName.getText().trim().isEmpty() && textField_LName.getText().trim().isEmpty() &&
				textField_KZ1.getText().trim().isEmpty() && textField_KZ2.getText().trim().isEmpty() &&
				textField_KZHOG.getText().trim().isEmpty() && comboBox_Otdel.getSelectedItem().trim().isEmpty());
	}
	
	private void TextFieldJustNumbers(JTextField field) {
	((AbstractDocument)field.getDocument()).setDocumentFilter(new DocumentFilter(){
        Pattern regEx = Pattern.compile("\\d*");

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {          
            Matcher matcher = regEx.matcher(text);
            if(!matcher.matches()){
                return;
            }
            super.replace(fb, offset, length, text, attrs);
        }
    });
	}
	
	private JPanel panel_3() {
		JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel3);

		comboBox_Results = new Choice();
		comboBox_Results.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Results.setPreferredSize(new Dimension(670, 20));
		panel3.add(comboBox_Results);
		
		ActionListenerComboBox_Results();
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setPreferredSize(new Dimension(20, 14));
		panel3.add(lblNewLabel_1_1_1);
		return panel3;
	}

	private JPanel panel_Button() {
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton btn_Export = new JButton("Export");
		buttonPanel.add(btn_Export);
		return buttonPanel;
	}

	private JPanel panel_infoPanel() {

		JPanel infoPanel = new JPanel();
		scrollPane.setViewportView(infoPanel);
		infoPanel.setLayout(new BorderLayout(5, 5));

		textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoPanel.add(textArea, BorderLayout.CENTER);
		return infoPanel;

	}

	private ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
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
	
}
