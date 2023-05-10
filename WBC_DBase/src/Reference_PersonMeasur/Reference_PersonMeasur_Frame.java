package Reference_PersonMeasur;

import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Choice;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;


import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.InsertMeasurToExcel;

import BasicClassAccessDbase.Person;

import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonReferenceFrame;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.awt.Font;
import java.awt.Insets;

public class Reference_PersonMeasur_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private static Choice comboBox_Firm;
	private static Choice comboBox_Otdel;

	private static JTextArea textArea;
	private static JTextField textField_StartDate;
	private static JTextField textField_EndDate;
	private static JTextField textField_Year;
	private static JButton btn_Search;
	private static JButton btn_Export;

	static String curentYear = AplicationMetods.getCurentYear();
	String referencePersonMeasurTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasurTipText");
	
	public Reference_PersonMeasur_Frame(ActionIcone round, String referencePersonMeasur) {
		setTitle(referencePersonMeasur);

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

		panel_1();
		panel_2();
		panel_2A();

		panel_Button();

		setSize(737, 900);
		setLocationRelativeTo(null);

		PersonelManegementMethods.generateListOtdels();
		PersonelManegementMethods.addItemFirm(comboBox_Firm);
		PersonelManegementMethods.setitemInChoise(comboBox_Firm, comboBox_Otdel);
		PersonelManegementMethods.ActionListener_ComboBox_Firm(comboBox_Firm, comboBox_Otdel);

		Metods.checkorektDate(textField_StartDate);
		Metods.checkorektDate(textField_EndDate);

		PersonReferenceFrame.TextFieldJustNumbers(textField_Year);
		Metods.ActionListenertextField_Year(textField_Year, btn_Search);
		Metods.ActionListener_ComboBox_Otdel(comboBox_Otdel, btn_Search);

		Metods.ActionListenerbBtn_Search(panel_Search);
		Metods.ActionListenerComboBox_Firm();
		Metods.ActionListenerBtnExportToExcell(panel_Search);

		Metods.setitemInChoise();

		setVisible(true);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		round.StopWindow();
	}

	private JPanel panel_1() {
		JPanel panel1 = new JPanel();
		FlowLayout fl_panel1 = (FlowLayout) panel1.getLayout();
		fl_panel1.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1);

		JLabel lbl_Info_1 = new JLabel(referencePersonMeasurTipText);
		lbl_Info_1.setToolTipText("");
		lbl_Info_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Info_1.setBorder(null);
		lbl_Info_1.setAlignmentX(0.5f);
		panel1.add(lbl_Info_1);

		return panel1;
	}

	private JPanel panel_2() {

		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);

		JLabel lbl_Year = new JLabel("Year");
		lbl_Year.setToolTipText("");

		lbl_Year.setPreferredSize(new Dimension(38, 15));

		lbl_Year.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panel2.add(lbl_Year);

		JLabel lbl_KodKZ1 = new JLabel("Start Date");
		lbl_KodKZ1.setPreferredSize(new Dimension(69, 15));
		lbl_KodKZ1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ1.setBorder(null);
		lbl_KodKZ1.setAlignmentX(0.5f);
		panel2.add(lbl_KodKZ1);

		JLabel lbl_KodKZ2 = new JLabel("End Date");
		lbl_KodKZ2.setPreferredSize(new Dimension(69, 15));
		lbl_KodKZ2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ2.setBorder(null);
		lbl_KodKZ2.setAlignmentX(0.5f);
		panel2.add(lbl_KodKZ2);

		JLabel lbl_L_Firm = new JLabel("Firm");
		lbl_L_Firm.setPreferredSize(new Dimension(120, 15));
		lbl_L_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Firm.setBorder(null);
		lbl_L_Firm.setAlignmentX(1.0f);
		panel2.add(lbl_L_Firm);

		JLabel lbl_L_Otdel = new JLabel("Otdel");
		lbl_L_Otdel.setPreferredSize(new Dimension(200, 15));
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

		textField_StartDate = new JTextField();
		textField_StartDate.setText("01.01." + curentYear);
		textField_StartDate.setPreferredSize(new Dimension(5, 20));
		textField_StartDate.setMinimumSize(new Dimension(5, 20));
		textField_StartDate.setColumns(8);
		panel2A.add(textField_StartDate);

		textField_EndDate = new JTextField("31.12." + curentYear);
		textField_EndDate.setPreferredSize(new Dimension(5, 20));
		textField_EndDate.setMinimumSize(new Dimension(5, 20));
		textField_EndDate.setColumns(8);
		panel2A.add(textField_EndDate);

		comboBox_Firm = new Choice();
		comboBox_Firm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Firm.setPreferredSize(new Dimension(120, 20));
		panel2A.add(comboBox_Firm);

		comboBox_Otdel = new Choice();
		comboBox_Otdel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Otdel.setPreferredSize(new Dimension(200, 20));
		panel2A.add(comboBox_Otdel);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setPreferredSize(new Dimension(20, 14));
		panel2A.add(lblNewLabel_1_1);

		btn_Search = new JButton("Search");
		btn_Search.setMargin(new Insets(2, 5, 2, 5));
		btn_Search.setPreferredSize(new Dimension(110, 23));
		panel2A.add(btn_Search);
		btn_Search.setEnabled(false);

		return panel2A;
	}

	private JPanel panel_Button() {

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		btn_Export = new JButton("Export");
		buttonPanel.add(btn_Export);

		btn_Export.setEnabled(false);

		return buttonPanel;
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

	protected String createStringToInfoPanel(List<Person> listSelectionPerson) {
		String str = "";
		for (Person person : listSelectionPerson) {
			str = str + person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person) + "\n";
		}
		return str;
	}

	public static Choice getComboBox_Otdel() {
		return comboBox_Otdel;
	}

	public static JTextField getTextField_StartDate() {
		return textField_StartDate;
	}

	public static JTextField getTextField_EndDate() {
		return textField_EndDate;
	}

	public static JTextField getTextField_Year() {
		return textField_Year;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public static JButton getBtn_Search() {
		return btn_Search;
	}

	public static JButton getBtn_Export() {
		return btn_Export;
	}

	public static Choice getComboBox_Firm() {
		return comboBox_Firm;
	}
}
