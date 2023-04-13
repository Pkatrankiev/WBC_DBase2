package PersonManagement;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.Spisak_Prilogenia;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.TextInAreaTextPanel;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class PersonelManegementFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private static Choice comboBox_Firm;
	private static Choice comboBox_Otdel;
	private static Choice comboBox_Results;
	private static Choice comboBox_savePerson_Firm;
	private static Choice comboBox_savePerson_Otdel;

	private static JTextArea textArea;
	private static JTextField textField_EGN;
	private static JTextField textField_FName;
	private static JTextField textField_SName;
	private static JTextField textField_LName;

	private static JButton btn_SearchPerson;
	private static JButton btnBackToTable;
	private static JButton btn_Clear;
	private static JButton btn_savePerson_Insert;
	private static JButton btn_Spisak;
	private static JButton btn_SearchFreeKode;

	private static JRadioButton rdbtn_KodKZ1;
	private static JRadioButton rdbtn_KodKZ2;
	private static JRadioButton rdbtn_KodKZHOG;
	private static JRadioButton rdbtn_KodTerit_1;
	private static JRadioButton rdbtn_KodTerit_2;

	String[][] dataTable;
	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
	String labelCheckForSearch = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelCheckForSearch");
	static List<Spisak_Prilogenia> listSpisak_Prilogenia;
	
	static String oldOtdelPerson;

	private static JTextField textField_svePerson_EGN;
	private static JTextField textField_svePerson_FName;
	private static JTextField textField_svePerson_SName;
	private static JTextField textField_svePerson_LName;
	private static JTextField textField_svePerson_KodKZ_1;
	private static JTextField textField_svePersonKodKZ_2;
	private static JTextField textField_svePersonKodKZ_HOG;
	private static JTextField textField_svePersonKodKZ_Terit_1;
	private static JTextField textField_svePersonKodKZ_Terit_2;
	private static JTextField textField_svePerson_Spisak;
	private static JTextField textField_savePerson_StartDate;
	private static JTextField textField_savePerson_EndDate;
	
	private static JLabel lbl_svePerson_Text_Check_EnterInZone;
	private JPanel personSave_Panel;
	

	public PersonelManegementFrame(ActionIcone round) {
		setTitle("Person Manegement");

		setMinimumSize(new Dimension(730, 900));

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

		textArea.setEditable(false);
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

		personLabel_Panel_1();
		personField_Panel_1A();
		personText_Panel_2();
		kodeLabel_Panel_3();
		kodeRadioButtons_Panel_3A();
		panel_4();

		save_Panel();

		setSize(750, 900);
		setLocationRelativeTo(null);
		
		PersonelManegementMethods.generateListOtdels();
		PersonelManegementMethods.addItemFirm(comboBox_Firm);
		PersonelManegementMethods.setitemInChoise(comboBox_Firm, comboBox_Otdel);
		PersonelManegementMethods.ActionListener_ComboBox_Firm(comboBox_Firm, comboBox_Otdel);
		
		PersonelManegementMethods.addItemFirm(comboBox_savePerson_Firm);
		PersonelManegementMethods.setitemInChoise(comboBox_savePerson_Firm, comboBox_savePerson_Otdel);
		PersonelManegementMethods.ActionListener_ComboBox_Firm(comboBox_savePerson_Firm, comboBox_savePerson_Otdel);

		PersonelManegementMethods.ActionListener_ComboBox_savePerson_Otdel(comboBox_savePerson_Otdel);
		
		
		
		PersonelManegementMethods.ActionListener_Btn_Clear(btn_savePerson_Insert, btn_Clear, textArea);

		PersonelManegementMethods.ActionListener_TextArea(btn_savePerson_Insert, textArea, panel_AllSaerch);

		PersonelManegementMethods.ActionListener_Btn_SearchPerson(btn_SearchPerson, panel_AllSaerch, textArea, btn_savePerson_Insert);

		PersonelManegementMethods.ActionListener_Btn_savePerson_Insert(btn_savePerson_Insert, panel_AllSaerch, textArea);

		PersonelManegementMethods.ActionListener_Btn_Spisak(btn_Spisak);

		PersonelManegementMethods.ActionListener_Btn_SearchFreeKode(btn_SearchFreeKode, comboBox_Otdel);

		PersonelManegementMethods.checkorektDate(textField_savePerson_StartDate);
		PersonelManegementMethods.checkorektDate(textField_savePerson_EndDate);
		
		PersonelManegementMethods.checInsertNewPerson();
		
		setVisible(true);
		round.StopWindow();
		
		
	}

	private JPanel personLabel_Panel_1() {
		JPanel personLabel_Panel_1 = new JPanel();
		FlowLayout fl_personLabel_Panel_1 = (FlowLayout) personLabel_Panel_1.getLayout();
		fl_personLabel_Panel_1.setVgap(0);
		fl_personLabel_Panel_1.setAlignment(FlowLayout.LEFT);
		panel_Search.add(personLabel_Panel_1);

		JLabel lbl_EGN = new JLabel("EGN");
		lbl_EGN.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EGN.setPreferredSize(new Dimension(85, 20));
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(Component.CENTER_ALIGNMENT);
		personLabel_Panel_1.add(lbl_EGN);

		JLabel distantion = new JLabel();
		distantion.setPreferredSize(new Dimension(30, 14));
		personLabel_Panel_1.add(distantion);

		JLabel lbl_FirstName = new JLabel("First Name");
		lbl_FirstName.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_FirstName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_FirstName.setPreferredSize(new Dimension(126, 20));
		lbl_FirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_FirstName.setBorder(null);
		personLabel_Panel_1.add(lbl_FirstName);

		JLabel lbl_L_SecondName = new JLabel("Second Name");
		lbl_L_SecondName.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_L_SecondName.setPreferredSize(new Dimension(126, 20));
		lbl_L_SecondName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_SecondName.setBorder(null);
		lbl_L_SecondName.setAlignmentX(1.0f);
		personLabel_Panel_1.add(lbl_L_SecondName);

		JLabel lbl_L_LastName = new JLabel("Last Name");
		lbl_L_LastName.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_L_LastName.setPreferredSize(new Dimension(126, 20));
		lbl_L_LastName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_LastName.setBorder(null);
		lbl_L_LastName.setAlignmentX(1.0f);
		personLabel_Panel_1.add(lbl_L_LastName);
		return personLabel_Panel_1;
	}

	private JPanel personField_Panel_1A() {
		JPanel personField_Panel_1A = new JPanel();
		personField_Panel_1A.setPreferredSize(new Dimension(10, 30));
		FlowLayout fl_personField_Panel_1A = (FlowLayout) personField_Panel_1A.getLayout();
		fl_personField_Panel_1A.setVgap(2);
		fl_personField_Panel_1A.setAlignment(FlowLayout.LEFT);
		panel_Search.add(personField_Panel_1A);

		textField_EGN = new JTextField();
		textField_EGN.setPreferredSize(new Dimension(5, 20));
		personField_Panel_1A.add(textField_EGN);
		textField_EGN.setColumns(10);

//		TextFieldJustNumbers(textField_EGN);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setPreferredSize(new Dimension(29, 14));
		personField_Panel_1A.add(lblNewLabel_1);

		textField_FName = new JTextField();
		textField_FName.setPreferredSize(new Dimension(5, 20));
		textField_FName.setColumns(15);
		personField_Panel_1A.add(textField_FName);

		textField_SName = new JTextField();
		textField_SName.setPreferredSize(new Dimension(5, 20));
		textField_SName.setColumns(15);
		personField_Panel_1A.add(textField_SName);

		textField_LName = new JTextField();
		textField_LName.setPreferredSize(new Dimension(5, 20));
		textField_LName.setColumns(15);
		personField_Panel_1A.add(textField_LName);

		btn_Clear = new JButton("Clear");
		btn_Clear.setPreferredSize(new Dimension(60, 23));
		btn_Clear.setMargin(new Insets(2, 5, 2, 5));
		btn_Clear.setIconTextGap(1);
		personField_Panel_1A.add(btn_Clear);

		btn_SearchPerson = new JButton("Search Person");
		btn_SearchPerson.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchPerson.setIconTextGap(1);
		btn_SearchPerson.setPreferredSize(new Dimension(110, 23));
		personField_Panel_1A.add(btn_SearchPerson);

		return personField_Panel_1A;
	}

	private JPanel kodeLabel_Panel_3() {

		JPanel kodeLabel_Panel_3 = new JPanel();
		FlowLayout fl_kodeLabel_Panel_3 = (FlowLayout) kodeLabel_Panel_3.getLayout();
		fl_kodeLabel_Panel_3.setVgap(0);
		fl_kodeLabel_Panel_3.setAlignment(FlowLayout.LEFT);
		panel_Search.add(kodeLabel_Panel_3);

		JLabel lbl_KodKZ1 = new JLabel("ID KZ-1");
		lbl_KodKZ1.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_KodKZ1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodKZ1.setPreferredSize(new Dimension(55, 25));
		lbl_KodKZ1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ1.setBorder(null);
		lbl_KodKZ1.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodKZ1);

		JLabel lbl_KodKZ2 = new JLabel("ID KZ-2");
		lbl_KodKZ2.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_KodKZ2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodKZ2.setPreferredSize(new Dimension(55, 25));
		lbl_KodKZ2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ2.setBorder(null);
		lbl_KodKZ2.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodKZ2);

		JLabel lbl_KodKZHOG = new JLabel("ID KZ-HOG");
		lbl_KodKZHOG.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodKZHOG.setPreferredSize(new Dimension(55, 25));
		lbl_KodKZHOG.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZHOG.setBorder(null);
		lbl_KodKZHOG.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodKZHOG);

		JLabel lbl_KodTerit_1 = new JLabel("ID Terit-1");
		lbl_KodTerit_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodTerit_1.setPreferredSize(new Dimension(55, 25));
		lbl_KodTerit_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodTerit_1.setBorder(null);
		lbl_KodTerit_1.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodTerit_1);

		JLabel lbl_KodTerit_2 = new JLabel(" ID Terit-2");
		lbl_KodTerit_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodTerit_2.setPreferredSize(new Dimension(55, 25));
		lbl_KodTerit_2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodTerit_2.setBorder(null);
		lbl_KodTerit_2.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodTerit_2);

		JLabel lbl_Firm = new JLabel("Firm");
		lbl_Firm.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Firm.setSize(new Dimension(70, 20));
		lbl_Firm.setPreferredSize(new Dimension(130, 25));
		lbl_Firm.setMinimumSize(new Dimension(70, 20));
		lbl_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Firm.setBorder(null);
		lbl_Firm.setAlignmentX(1.0f);
		kodeLabel_Panel_3.add(lbl_Firm);

		JLabel lbl_Otdel = new JLabel("Otdel");
		lbl_Otdel.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_Otdel.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Otdel.setSize(new Dimension(70, 20));
		lbl_Otdel.setPreferredSize(new Dimension(142, 25));
		lbl_Otdel.setMinimumSize(new Dimension(70, 20));
		lbl_Otdel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Otdel.setBorder(null);
		lbl_Otdel.setAlignmentX(1.0f);
		kodeLabel_Panel_3.add(lbl_Otdel);

		btn_SearchFreeKode = new JButton("Search Free Kode");
		kodeLabel_Panel_3.add(btn_SearchFreeKode);
		btn_SearchFreeKode.setPreferredSize(new Dimension(110, 25));
		btn_SearchFreeKode.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchFreeKode.setIconTextGap(1);

		return kodeLabel_Panel_3;
	}

	private JPanel kodeRadioButtons_Panel_3A() {
		JPanel kodeRadioButtons_Panel_3A = new JPanel();
		FlowLayout fl_kodeRadioButtons_Panel_3A = (FlowLayout) kodeRadioButtons_Panel_3A.getLayout();
		fl_kodeRadioButtons_Panel_3A.setVgap(0);
		fl_kodeRadioButtons_Panel_3A.setAlignment(FlowLayout.LEFT);
		kodeRadioButtons_Panel_3A.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(kodeRadioButtons_Panel_3A);

		rdbtn_KodKZ1 = new JRadioButton("");
		rdbtn_KodKZ1.setSelected(true);
		rdbtn_KodKZ1.setPreferredSize(new Dimension(55, 21));
		rdbtn_KodKZ1.setHorizontalAlignment(SwingConstants.CENTER);
		kodeRadioButtons_Panel_3A.add(rdbtn_KodKZ1);

		rdbtn_KodKZ2 = new JRadioButton("");
		rdbtn_KodKZ2.setPreferredSize(new Dimension(55, 21));
		rdbtn_KodKZ2.setHorizontalAlignment(SwingConstants.CENTER);
		kodeRadioButtons_Panel_3A.add(rdbtn_KodKZ2);

		rdbtn_KodKZHOG = new JRadioButton("");
		rdbtn_KodKZHOG.setPreferredSize(new Dimension(56, 21));
		rdbtn_KodKZHOG.setHorizontalAlignment(SwingConstants.CENTER);
		kodeRadioButtons_Panel_3A.add(rdbtn_KodKZHOG);

		rdbtn_KodTerit_1 = new JRadioButton("");
		rdbtn_KodTerit_1.setPreferredSize(new Dimension(55, 21));
		rdbtn_KodTerit_1.setHorizontalAlignment(SwingConstants.CENTER);
		kodeRadioButtons_Panel_3A.add(rdbtn_KodTerit_1);

		rdbtn_KodTerit_2 = new JRadioButton("");
		rdbtn_KodTerit_2.setPreferredSize(new Dimension(55, 21));
		rdbtn_KodTerit_2.setHorizontalAlignment(SwingConstants.CENTER);
		kodeRadioButtons_Panel_3A.add(rdbtn_KodTerit_2);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtn_KodKZ1);
		bg.add(rdbtn_KodKZ2);
		bg.add(rdbtn_KodKZHOG);
		bg.add(rdbtn_KodTerit_1);
		bg.add(rdbtn_KodTerit_2);

		comboBox_Firm = new Choice();
		comboBox_Firm.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			}
		});
		comboBox_Firm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		comboBox_Firm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Firm.setPreferredSize(new Dimension(130, 20));
		kodeRadioButtons_Panel_3A.add(comboBox_Firm);

		comboBox_Otdel = new Choice();
		comboBox_Otdel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Otdel.setPreferredSize(new Dimension(255, 20));
		kodeRadioButtons_Panel_3A.add(comboBox_Otdel);

		return kodeRadioButtons_Panel_3A;
	}

	private JPanel personText_Panel_2() {
		JPanel personText_Panel_2 = new JPanel();
		personText_Panel_2.setPreferredSize(new Dimension(10, 20));
		FlowLayout fl_personText_Panel_2 = (FlowLayout) personText_Panel_2.getLayout();
		fl_personText_Panel_2.setVgap(2);
		fl_personText_Panel_2.setAlignment(FlowLayout.LEFT);
		panel_Search.add(personText_Panel_2);

		JLabel lbl_personText_Panel_2 = new JLabel(labelCheckForSearch);
		lbl_personText_Panel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_personText_Panel_2.setPreferredSize(new Dimension(700, 20));
		lbl_personText_Panel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_personText_Panel_2.setBorder(null);
		lbl_personText_Panel_2.setAlignmentX(0.5f);
		personText_Panel_2.add(lbl_personText_Panel_2);
		return personText_Panel_2;
	}

	private JPanel panel_4() {

		JPanel panel_4 = new JPanel();
		FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
		fl_panel_4.setAlignment(FlowLayout.LEFT);
		panel_4.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel_4);

		comboBox_Results = new Choice();
		comboBox_Results.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Results.setPreferredSize(new Dimension(590, 20));
		panel_4.add(comboBox_Results);

//		ActionListenerComboBox_Results();

		btnBackToTable = new JButton("BackToTable");
		btnBackToTable.setEnabled(false);
		btnBackToTable.setMargin(new Insets(2, 2, 2, 2));
		btnBackToTable.setPreferredSize(new Dimension(90, 20));
		panel_4.add(btnBackToTable);

//		ActionListenerBtnBackToTable();

		return panel_4;
	}

	private JPanel save_Panel() {

		JPanel save_Panel = new JPanel();
		getContentPane().add(save_Panel, BorderLayout.SOUTH);
		save_Panel.setLayout(new BoxLayout(save_Panel, BoxLayout.Y_AXIS));

		personSave_Panel(save_Panel);

		button_Panel(save_Panel);

		return save_Panel;
	}

	private JPanel personSave_Panel(JPanel save_Panel) {
		personSave_Panel = new JPanel();
		save_Panel.add(personSave_Panel, BorderLayout.SOUTH);
		personSave_Panel.setLayout(new BoxLayout(personSave_Panel, BoxLayout.Y_AXIS));

		JPanel personSave_Panel_1 = new JPanel();
		personSave_Panel.add(personSave_Panel_1);

		personSave_Personel_2_LabelPanel(personSave_Panel);
		
		personSave_Personel_2_FildPanel(personSave_Panel);

		personSave_Kode_Panel_3(personSave_Panel);

		personSave_OtdelSpisakLabel_Panel_4(personSave_Panel);

		personSave_OtdelSpisakField_Panel_4A(personSave_Panel);

		return personSave_Panel_5(personSave_Panel);
	}

	private JPanel personSave_Personel_2_LabelPanel(JPanel personSave_Panel) {
		
		JPanel personSave_Personel_2_LabelPanel = new JPanel();
		FlowLayout fl_personSave_Personel_2_LabelPanel = (FlowLayout) personSave_Personel_2_LabelPanel.getLayout();
		fl_personSave_Personel_2_LabelPanel.setVgap(0);
		fl_personSave_Personel_2_LabelPanel.setAlignment(FlowLayout.LEFT);
		personSave_Panel.add(personSave_Personel_2_LabelPanel);
		
		JLabel lbl_EGN = new JLabel("EGN");
		lbl_EGN.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_EGN.setPreferredSize(new Dimension(85, 20));
		lbl_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(0.5f);
		personSave_Personel_2_LabelPanel.add(lbl_EGN);
		
		JLabel distantion = new JLabel();
		distantion.setPreferredSize(new Dimension(30, 14));
		personSave_Personel_2_LabelPanel.add(distantion);
		
		JLabel lbl_svePerson_FirstName = new JLabel("First Name");
		lbl_svePerson_FirstName.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_FirstName.setPreferredSize(new Dimension(126, 20));
		lbl_svePerson_FirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_FirstName.setBorder(null);
		lbl_svePerson_FirstName.setAlignmentX(0.5f);
		personSave_Personel_2_LabelPanel.add(lbl_svePerson_FirstName);
		
		JLabel lbl_svePerson_SecondName = new JLabel("Second Name");
		lbl_svePerson_SecondName.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_SecondName.setPreferredSize(new Dimension(126, 20));
		lbl_svePerson_SecondName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_SecondName.setBorder(null);
		lbl_svePerson_SecondName.setAlignmentX(1.0f);
		personSave_Personel_2_LabelPanel.add(lbl_svePerson_SecondName);
		
		JLabel lbl_svePerson_LastName = new JLabel("Last Name");
		lbl_svePerson_LastName.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_LastName.setPreferredSize(new Dimension(126, 20));
		lbl_svePerson_LastName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_LastName.setBorder(null);
		lbl_svePerson_LastName.setAlignmentX(1.0f);
		personSave_Personel_2_LabelPanel.add(lbl_svePerson_LastName);
		
		return personSave_Personel_2_LabelPanel;
	}
	
	private JPanel personSave_Personel_2_FildPanel(JPanel personSave_Panel) {	
		JPanel personSave_Personel_2_FildPanel = new JPanel();
		FlowLayout fl_personSave_Personel_2_FildPanel = (FlowLayout) personSave_Personel_2_FildPanel.getLayout();
		fl_personSave_Personel_2_FildPanel.setVgap(2);
		fl_personSave_Personel_2_FildPanel.setAlignment(FlowLayout.LEFT);
		personSave_Personel_2_FildPanel.setPreferredSize(new Dimension(10, 30));
		personSave_Panel.add(personSave_Personel_2_FildPanel);
		
		textField_svePerson_EGN = new JTextField();
		personSave_Personel_2_FildPanel.add(textField_svePerson_EGN);
		textField_svePerson_EGN.setPreferredSize(new Dimension(5, 20));
		textField_svePerson_EGN.setMinimumSize(new Dimension(5, 20));
		textField_svePerson_EGN.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setPreferredSize(new Dimension(29, 14));
		personSave_Personel_2_FildPanel.add(lblNewLabel_1);
		
		textField_svePerson_FName = new JTextField();
		textField_svePerson_FName.setPreferredSize(new Dimension(5, 20));
		textField_svePerson_FName.setColumns(15);
		personSave_Personel_2_FildPanel.add(textField_svePerson_FName);
		
		textField_svePerson_SName = new JTextField();
		textField_svePerson_SName.setPreferredSize(new Dimension(5, 20));
		textField_svePerson_SName.setColumns(15);
		personSave_Personel_2_FildPanel.add(textField_svePerson_SName);
		
		textField_svePerson_LName = new JTextField();
		textField_svePerson_LName.setPreferredSize(new Dimension(5, 20));
		textField_svePerson_LName.setColumns(15);
		personSave_Personel_2_FildPanel.add(textField_svePerson_LName);
		
		btn_savePerson_Insert = new JButton("Insert");
		personSave_Personel_2_FildPanel.add(btn_savePerson_Insert);
		btn_savePerson_Insert.setEnabled(false);
				
	return personSave_Personel_2_FildPanel;
	
	}

	private JPanel personSave_Kode_Panel_3(JPanel personSave_Panel) {
		JPanel personSave_Kode_Panel_3 = new JPanel();
		FlowLayout fl_personSave_Kode_Panel_3 = (FlowLayout) personSave_Kode_Panel_3.getLayout();
		fl_personSave_Kode_Panel_3.setAlignment(FlowLayout.LEFT);
		personSave_Kode_Panel_3.setPreferredSize(new Dimension(10, 30));
		personSave_Panel.add(personSave_Kode_Panel_3);

		JLabel lbl_svePerson_KodKZ_1 = new JLabel("ID KZ-1");
		lbl_svePerson_KodKZ_1.setSize(new Dimension(80, 20));
		lbl_svePerson_KodKZ_1.setPreferredSize(new Dimension(53, 15));
		lbl_svePerson_KodKZ_1.setMinimumSize(new Dimension(80, 20));
		lbl_svePerson_KodKZ_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_svePerson_KodKZ_1.setBorder(null);
		lbl_svePerson_KodKZ_1.setAlignmentX(0.5f);
		personSave_Kode_Panel_3.add(lbl_svePerson_KodKZ_1);

		textField_svePerson_KodKZ_1 = new JTextField();
		textField_svePerson_KodKZ_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_svePerson_KodKZ_1.setColumns(6);
		personSave_Kode_Panel_3.add(textField_svePerson_KodKZ_1);

		JLabel lbl_svePersonKodKZ_2 = new JLabel("ID KZ-2");
		lbl_svePersonKodKZ_2.setSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_2.setPreferredSize(new Dimension(53, 15));
		lbl_svePersonKodKZ_2.setMinimumSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_svePersonKodKZ_2.setBorder(null);
		lbl_svePersonKodKZ_2.setAlignmentX(0.5f);
		personSave_Kode_Panel_3.add(lbl_svePersonKodKZ_2);

		textField_svePersonKodKZ_2 = new JTextField();
		textField_svePersonKodKZ_2.setColumns(6);
		personSave_Kode_Panel_3.add(textField_svePersonKodKZ_2);

		JLabel lbl_svePersonKodKZ_HOG = new JLabel("ID KZ-HOG");
		lbl_svePersonKodKZ_HOG.setSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_HOG.setPreferredSize(new Dimension(70, 15));
		lbl_svePersonKodKZ_HOG.setMinimumSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_HOG.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_svePersonKodKZ_HOG.setBorder(null);
		lbl_svePersonKodKZ_HOG.setAlignmentX(0.5f);
		personSave_Kode_Panel_3.add(lbl_svePersonKodKZ_HOG);

		textField_svePersonKodKZ_HOG = new JTextField();
		textField_svePersonKodKZ_HOG.setColumns(6);
		personSave_Kode_Panel_3.add(textField_svePersonKodKZ_HOG);

		JLabel lbl_svePersonKodKZ_Terit_1 = new JLabel("ID Terit-1");
		lbl_svePersonKodKZ_Terit_1.setSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_Terit_1.setPreferredSize(new Dimension(60, 15));
		lbl_svePersonKodKZ_Terit_1.setMinimumSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_Terit_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_svePersonKodKZ_Terit_1.setBorder(null);
		lbl_svePersonKodKZ_Terit_1.setAlignmentX(0.5f);
		personSave_Kode_Panel_3.add(lbl_svePersonKodKZ_Terit_1);

		textField_svePersonKodKZ_Terit_1 = new JTextField();
		textField_svePersonKodKZ_Terit_1.setColumns(6);
		personSave_Kode_Panel_3.add(textField_svePersonKodKZ_Terit_1);

		JLabel lbl_svePersonKodKZ_Terit_2 = new JLabel("ID Terit-2");
		lbl_svePersonKodKZ_Terit_2.setSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_Terit_2.setPreferredSize(new Dimension(60, 15));
		lbl_svePersonKodKZ_Terit_2.setMinimumSize(new Dimension(80, 20));
		lbl_svePersonKodKZ_Terit_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_svePersonKodKZ_Terit_2.setBorder(null);
		lbl_svePersonKodKZ_Terit_2.setAlignmentX(0.5f);
		personSave_Kode_Panel_3.add(lbl_svePersonKodKZ_Terit_2);

		textField_svePersonKodKZ_Terit_2 = new JTextField();
		textField_svePersonKodKZ_Terit_2.setColumns(6);
		personSave_Kode_Panel_3.add(textField_svePersonKodKZ_Terit_2);

		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setPreferredSize(new Dimension(20, 14));
		personSave_Kode_Panel_3.add(lblNewLabel_1_1_1);
		return personSave_Kode_Panel_3;
	}

	private JPanel personSave_OtdelSpisakLabel_Panel_4(JPanel personSave_Panel) {
		JPanel personSave_OtdelSpisakLabel_Panel_4 = new JPanel();
		FlowLayout fl_personSave_OtdelSpisakLabel_Panel_4 = (FlowLayout) personSave_OtdelSpisakLabel_Panel_4
				.getLayout();
		fl_personSave_OtdelSpisakLabel_Panel_4.setVgap(0);
		fl_personSave_OtdelSpisakLabel_Panel_4.setAlignment(FlowLayout.LEFT);
		personSave_Panel.add(personSave_OtdelSpisakLabel_Panel_4);

		JLabel lbl_svePerson_Firm = new JLabel("Firm");
		lbl_svePerson_Firm.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_Firm.setPreferredSize(new Dimension(130, 25));
		lbl_svePerson_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_Firm.setBorder(null);
		lbl_svePerson_Firm.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_Firm);

		JLabel lbl_svePerson_Otdel = new JLabel("Otdel");
		lbl_svePerson_Otdel.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_Otdel.setPreferredSize(new Dimension(290, 25));
		lbl_svePerson_Otdel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_Otdel.setBorder(null);
		lbl_svePerson_Otdel.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_Otdel);

		JLabel lbl_btn_Spisak = new JLabel("");
		lbl_btn_Spisak.setPreferredSize(new Dimension(21, 20));
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_btn_Spisak);

		JLabel lbl_svePerson_Spisak = new JLabel("Spisak");
		lbl_svePerson_Spisak.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_Spisak.setPreferredSize(new Dimension(85, 25));
		lbl_svePerson_Spisak.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_Spisak.setBorder(null);
		lbl_svePerson_Spisak.setAlignmentX(0.5f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_Spisak);

		JLabel lbl_svePerson_StartDate = new JLabel("Start date");
		lbl_svePerson_StartDate.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_StartDate.setPreferredSize(new Dimension(68, 25));
		lbl_svePerson_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_StartDate.setBorder(null);
		lbl_svePerson_StartDate.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_StartDate);

		JLabel lbl_svePerson_EndDate = new JLabel("End Date");
		lbl_svePerson_EndDate.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_EndDate.setPreferredSize(new Dimension(68, 25));
		lbl_svePerson_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_EndDate.setBorder(null);
		lbl_svePerson_EndDate.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_EndDate);
		return personSave_OtdelSpisakLabel_Panel_4;
	}

	private JPanel personSave_OtdelSpisakField_Panel_4A(JPanel personSave_Panel) {
		JPanel personSave_OtdelSpisakField_Panel_4A = new JPanel();
		FlowLayout fl_personSave_OtdelSpisakField_Panel_4A = (FlowLayout) personSave_OtdelSpisakField_Panel_4A
				.getLayout();
		fl_personSave_OtdelSpisakField_Panel_4A.setVgap(0);
		fl_personSave_OtdelSpisakField_Panel_4A.setAlignment(FlowLayout.LEFT);
		personSave_OtdelSpisakField_Panel_4A.setPreferredSize(new Dimension(10, 30));
		personSave_Panel.add(personSave_OtdelSpisakField_Panel_4A);

		comboBox_savePerson_Firm = new Choice();
		comboBox_savePerson_Firm.setPreferredSize(new Dimension(130, 20));
		comboBox_savePerson_Firm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		personSave_OtdelSpisakField_Panel_4A.add(comboBox_savePerson_Firm);

		comboBox_savePerson_Otdel = new Choice();
		comboBox_savePerson_Otdel.setPreferredSize(new Dimension(290, 20));
		comboBox_savePerson_Otdel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		personSave_OtdelSpisakField_Panel_4A.add(comboBox_savePerson_Otdel);

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("modifiIcon");
		ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
		btn_Spisak = new JButton(pic);
		btn_Spisak.setPreferredSize(new Dimension(21, 21));
		personSave_OtdelSpisakField_Panel_4A.add(btn_Spisak);

		textField_svePerson_Spisak = new JTextField();
		textField_svePerson_Spisak.setPreferredSize(new Dimension(5, 20));
		textField_svePerson_Spisak.setColumns(10);
		personSave_OtdelSpisakField_Panel_4A.add(textField_svePerson_Spisak);

		textField_savePerson_StartDate = new JTextField();
		textField_savePerson_StartDate.setPreferredSize(new Dimension(5, 20));
		textField_savePerson_StartDate.setColumns(8);
		personSave_OtdelSpisakField_Panel_4A.add(textField_savePerson_StartDate);

		textField_savePerson_EndDate = new JTextField();
		textField_savePerson_EndDate.setPreferredSize(new Dimension(5, 20));
		textField_savePerson_EndDate.setColumns(8);
		personSave_OtdelSpisakField_Panel_4A.add(textField_savePerson_EndDate);

		

		return personSave_OtdelSpisakField_Panel_4A;
	}

	

	private JPanel personSave_Panel_5(JPanel personSave_Panel) {
		JPanel personSave_Panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) personSave_Panel_5.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		personSave_Panel.add(personSave_Panel_5);

		JLabel lbl_svePerson_Text_Check_EnterInZone = new JLabel("New label");
		lbl_svePerson_Text_Check_EnterInZone.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_svePerson_Text_Check_EnterInZone.setPreferredSize(new Dimension(646, 14));
		personSave_Panel_5.add(lbl_svePerson_Text_Check_EnterInZone);

		JCheckBox chckbx_svePerson_EnterInZone = new JCheckBox("no");
		personSave_Panel_5.add(chckbx_svePerson_EnterInZone);
		return personSave_Panel_5;
	}

	private JPanel button_Panel(JPanel save_Panel) {
		JPanel button_Panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) button_Panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		save_Panel.add(button_Panel);

		JButton btn_SearchDBase_1 = new JButton("Search from DBase");
		button_Panel.add(btn_SearchDBase_1);
		btn_SearchDBase_1.setPreferredSize(new Dimension(110, 23));
		btn_SearchDBase_1.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase_1.setIconTextGap(1);

		JButton btn_Export = new JButton("Export");
		button_Panel.add(btn_Export);
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnBackToTable.isEnabled() || dataTable == null) {

					PersonReferenceExportToExcell.btnExportInfoPersonToExcell(TextInAreaTextPanel.getPerson(),
							TextInAreaTextPanel.getMasivePersonStatusName(),
							TextInAreaTextPanel.getMasivePersonStatus(), TextInAreaTextPanel.getZoneNameMasive(),
							TextInAreaTextPanel.getMasiveKode(), TextInAreaTextPanel.getMasiveMeasurName(),
							TextInAreaTextPanel.getMasiveMeasur(), save_Panel);
				} else {
//				PersonReferenceExportToExcell.btnExportTableToExcell(dataTable, getTabHeader(), buttonPanel);

				}
			}
		});
		return button_Panel;
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

	public static String getOldOtdelPerson() {
		return oldOtdelPerson;
	}

	public static void setOldOtdelPerson(String oldOtdelPerson) {
		PersonelManegementFrame.oldOtdelPerson = oldOtdelPerson;
	}

	public static List<Spisak_Prilogenia> getListSpisak_Prilogenia() {
		return listSpisak_Prilogenia;
	}

	public static void setListSpisak_Prilogenia(List<Spisak_Prilogenia> listSpisak_Prilogenia) {
		PersonelManegementFrame.listSpisak_Prilogenia = listSpisak_Prilogenia;
	}

	public static JRadioButton getRdbtn_KodKZ1() {
		return rdbtn_KodKZ1;
	}

	public static void setRdbtn_KodKZ1(JRadioButton rdbtn_KodKZ1) {
		PersonelManegementFrame.rdbtn_KodKZ1 = rdbtn_KodKZ1;
	}

	public static JRadioButton getRdbtn_KodKZ2() {
		return rdbtn_KodKZ2;
	}

	public static void setRdbtn_KodKZ2(JRadioButton rdbtn_KodKZ2) {
		PersonelManegementFrame.rdbtn_KodKZ2 = rdbtn_KodKZ2;
	}

	public static JRadioButton getRdbtn_KodKZHOG() {
		return rdbtn_KodKZHOG;
	}

	public static void setRdbtn_KodKZHOG(JRadioButton rdbtn_KodKZHOG) {
		PersonelManegementFrame.rdbtn_KodKZHOG = rdbtn_KodKZHOG;
	}

	public static JRadioButton getRdbtn_KodTerit_1() {
		return rdbtn_KodTerit_1;
	}

	public static void setRdbtn_KodTerit_1(JRadioButton rdbtn_KodTerit_1) {
		PersonelManegementFrame.rdbtn_KodTerit_1 = rdbtn_KodTerit_1;
	}

	public static JRadioButton getRdbtn_KodTerit_2() {
		return rdbtn_KodTerit_2;
	}

	public static void setRdbtn_KodTerit_2(JRadioButton rdbtn_KodTerit_2) {
		PersonelManegementFrame.rdbtn_KodTerit_2 = rdbtn_KodTerit_2;
	}

	public static JLabel getLbl_svePerson_Text_Check_EnterInZone() {
		return lbl_svePerson_Text_Check_EnterInZone;
	}

	public static void setLbl_svePerson_Text_Check_EnterInZone(JLabel lbl_svePerson_Text_Check_EnterInZone) {
		PersonelManegementFrame.lbl_svePerson_Text_Check_EnterInZone = lbl_svePerson_Text_Check_EnterInZone;
	}

	public static Choice getComboBox_Firm() {
		return comboBox_Firm;
	}

	public static void setComboBox_Firm(Choice comboBox_Firm, String text) {
		comboBox_Firm.select(text);
		PersonelManegementFrame.comboBox_Firm = comboBox_Firm;
	}

	public static Choice getComboBox_Otdel() {
		return comboBox_Otdel;
	}

	public static void setComboBox_Otdel(Choice comboBox_Otdel, String text) {
		comboBox_Otdel.select(text);
		PersonelManegementFrame.comboBox_Otdel = comboBox_Otdel;
	}

	public static Choice getComboBox_savePerson_Firm() {
		return comboBox_savePerson_Firm;
	}

	public static void setComboBox_savePerson_Firm(Choice comboBox_savePerson_Firm, String text) {
		comboBox_savePerson_Firm.select(text);
		PersonelManegementFrame.comboBox_savePerson_Firm = comboBox_savePerson_Firm;
	}

	public static Choice getComboBox_savePerson_Otdel() {
		return comboBox_savePerson_Otdel;
	}

	public static void setComboBox_savePerson_Otdel(Choice comboBox_savePerson_Otdel, String text) {
		comboBox_savePerson_Otdel.select(text);
		PersonelManegementFrame.comboBox_savePerson_Otdel = comboBox_savePerson_Otdel;
	}
	
	public static JTextField getTextField_svePerson_EGN() {
		return textField_svePerson_EGN;
	}

	public void setTextField_svePerson_EGN(JTextField textField_svePerson_EGN, String text) {
		textField_svePerson_EGN.setText(text);
		PersonelManegementFrame.textField_svePerson_EGN = textField_svePerson_EGN;
	}

	public static JTextField getTextField_svePerson_KodKZ_1() {
		return textField_svePerson_KodKZ_1;
	}

	public void setTextField_svePerson_KodKZ_1(JTextField textField_svePerson_KodKZ_1, String text) {
		textField_svePerson_KodKZ_1.setText(text);
		PersonelManegementFrame.textField_svePerson_KodKZ_1 = textField_svePerson_KodKZ_1;
	}

	public static JTextField getTextField_svePersonKodKZ_2() {
		return textField_svePersonKodKZ_2;
	}

	public void setTextField_svePersonKodKZ_2(JTextField textField_svePersonKodKZ_2, String text) {
		textField_svePersonKodKZ_2.setText(text);
		PersonelManegementFrame.textField_svePersonKodKZ_2 = textField_svePersonKodKZ_2;
	}

	public static JTextField getTextField_svePersonKodKZ_HOG() {
		return textField_svePersonKodKZ_HOG;
	}

	public void setTextField_svePersonKodKZ_HOG(JTextField textField_svePersonKodKZ_HOG, String text) {
		textField_svePersonKodKZ_HOG.setText(text);
		PersonelManegementFrame.textField_svePersonKodKZ_HOG = textField_svePersonKodKZ_HOG;
	}

	public static JTextField getTextField_svePersonKodKZ_Terit_1() {
		return textField_svePersonKodKZ_Terit_1;
	}

	public void setTextField_svePersonKodKZ_Terit_1(JTextField textField_svePersonKodKZ_Terit_1, String text) {
		textField_svePersonKodKZ_Terit_1.setText(text);
		PersonelManegementFrame.textField_svePersonKodKZ_Terit_1 = textField_svePersonKodKZ_Terit_1;
	}

	public static JTextField getTextField_svePersonKodKZ_Terit_2() {
		return textField_svePersonKodKZ_Terit_2;
	}

	public void setTextField_svePersonKodKZ_Terit_2(JTextField textField_svePersonKodKZ_Terit_2, String text) {
		textField_svePersonKodKZ_Terit_2.setText(text);
		PersonelManegementFrame.textField_svePersonKodKZ_Terit_2 = textField_svePersonKodKZ_Terit_2;
	}

	public static JTextField getTextField_svePerson_Spisak() {
		return textField_svePerson_Spisak;
	}

	public void setTextField_svePerson_Spisak(JTextField textField_svePerson_Spisak, String text) {
		textField_svePerson_Spisak.setText(text);
		PersonelManegementFrame.textField_svePerson_Spisak = textField_svePerson_Spisak;
	}

	public static JTextField getTextField_savePerson_StartDate() {
		return textField_savePerson_StartDate;
	}

	public void setTextField_savePerson_StartDate(JTextField textField_savePerson_StartDate, String text) {
		textField_savePerson_StartDate.setText(text);
		PersonelManegementFrame.textField_savePerson_StartDate = textField_savePerson_StartDate;
	}

	public static JTextField getTextField_savePerson_EndDate() {
		return textField_savePerson_EndDate;
	}

	public void setTextField_savePerson_EndDate(JTextField textField_savePerson_EndDate, String text) {
		textField_savePerson_EndDate.setText(text);
		PersonelManegementFrame.textField_savePerson_EndDate = textField_savePerson_EndDate;
	}

	public static void setTextField_EGN(JTextField textField_EGN, String text) {
		textField_EGN.setText(text);
		PersonelManegementFrame.textField_EGN = textField_EGN;
		
	}

	public static void setTextField_FName(JTextField textField_FName, String text) {
		textField_FName.setText(text);
		PersonelManegementFrame.textField_FName = textField_FName;
	}

	public static void setTextField_SName(JTextField textField_SName, String text) {
		textField_SName.setText(text);
		PersonelManegementFrame.textField_SName = textField_SName;
	}

	public static void setTextField_LName(JTextField textField_LName, String text) {
		textField_LName.setText(text);
		PersonelManegementFrame.textField_LName = textField_LName;
	}

	public static JTextField getTextField_svePerson_FName() {
		return textField_svePerson_FName;
	}

	public static void setTextField_svePerson_FName(JTextField textField_svePerson_FName) {
		PersonelManegementFrame.textField_svePerson_FName = textField_svePerson_FName;
	}

	public static JTextField getTextField_svePerson_SName() {
		return textField_svePerson_SName;
	}

	public static void setTextField_svePerson_SName(JTextField textField_svePerson_SName) {
		PersonelManegementFrame.textField_svePerson_SName = textField_svePerson_SName;
	}

	public static JTextField getTextField_svePerson_LName() {
		return textField_svePerson_LName;
	}

	public static void setTextField_svePerson_LName(JTextField textField_svePerson_LName) {
		PersonelManegementFrame.textField_svePerson_LName = textField_svePerson_LName;
	}

	public static void setTextField_svePerson_EGN(JTextField textField_svePerson_EGN) {
		PersonelManegementFrame.textField_svePerson_EGN = textField_svePerson_EGN;
	}

}
