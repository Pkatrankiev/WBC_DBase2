package SpisakPersonManagement;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.UsersWBC;
import PersonReference.PersonReferenceFrame;

public class SpisakPersonelManegementFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel save_Panel;
	private static JPanel infoPanel;
	private static JPanel tablePane;
	private static JScrollPane scrollPane;
	private static Choice comboBox_Firm;
	private static Choice comboBox_Otdel;

	private static JTextArea textArea;
	private static JButton btn_ReadFileListPerson;
	private static JButton btn_Spisak;
	private static JButton btn_GenerateSpisak;

	private static JButton btn_SaveToExcelFile;
	private static JButton btn_Export;
	
	private static JRadioButton rdbtn_KodKZ1;
	private static JRadioButton rdbtn_KodKZ2;
	private static JRadioButton rdbtn_KodKZHOG;
	private static JRadioButton rdbtn_KodTerit_1;
	private static JRadioButton rdbtn_KodTerit_2;

	
	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
	String labelCheckForSearch = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelCheckForSearch");
//	static List<Spisak_Prilogenia> listSpisak_Prilogenia;
	
	static String oldOtdelPerson;
	static Border defoutBorder;
	private static JTextField textField_svePerson_Spisak;
	private static JTextField textField_savePerson_StartDate;
	private static JTextField textField_savePerson_EndDate;
	private static JTextField textField_svePerson_Year;
	private JPanel personSave_Panel;
	private JTextField textField;
	

//	static UsersWBC user = WBCUsersLogin.getCurentUser();
	static UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(1);
	private JLabel lbl_svePerson_Spisak_1;
	private static JLabel lblNewLabel_1;
	private static JButton btnBackToTable;
	



	private static JLabel lbl_distance_1;
	private static JLabel lbl_Icon_DateStart;
	private static JLabel lbl_Icon_DateEnd;
	private static JLabel lblNewLabel;
	private static JLabel lblNewLabel_2;
	
	String iconn1 = ReadFileBGTextVariable.getGlobalTextVariableMap().get("calendarIcon");
	private ImageIcon picData = new ImageIcon(getClass().getClassLoader().getResource(iconn1));


	public SpisakPersonelManegementFrame(ActionIcone round) {
		
		String Person_Manegement_Spisaci = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Person_Manegement_Spisaci");
		
		setTitle(Person_Manegement_Spisaci+" -> "+user.getLastName());
		 System.out.println("555555555555555555555555555555555555555");
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		
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
	
		kodeLabel_Panel_3();
		kodeRadioButtons_Panel_3A();
		panel_4();
		panel_Label();
		save_Panel();

		setSize(750, 900);
		setLocationRelativeTo(null);
		
		
		SpisakPersonelManegementMethods.setDataTable(null);
		
		SpisakPersonelManegementMethods.generateListOtdels();
		SpisakPersonelManegementMethods.addItemFirm(comboBox_Firm);
		SpisakPersonelManegementMethods.setitemInChoise(comboBox_Firm, comboBox_Otdel);
		SpisakPersonelManegementMethods.ActionListener_ComboBox_Firm(comboBox_Firm, comboBox_Otdel);

		SpisakPersonelManegementMethods.ActionListenerSetDateByDatePicker(lbl_Icon_DateEnd, textField_savePerson_EndDate);
		SpisakPersonelManegementMethods.ActionListenerSetDateByDatePicker(lbl_Icon_DateStart, textField_savePerson_StartDate);
		
		SpisakPersonelManegementMethods.ActionListener_ComboBox_Otdel(comboBox_Otdel, btn_ReadFileListPerson);
	
		SpisakPersonelManegementMethods.ActionListenerGenerateNewSpisak(btn_GenerateSpisak,
				 panel_AllSaerch,  textField_svePerson_Year );
		
//		SpisakPersonelManegementMethods.ActionListener_TextArea(btn_savePerson_Insert, textArea, panel_AllSaerch);


		SpisakPersonelManegementMethods.ActionListenerBtnBackToTable(btnBackToTable, textArea,  tablePane,
				 panel_AllSaerch,  scrollPane,  textField_svePerson_Year);
		
		
		SpisakPersonelManegementMethods.ActionListener_Btn_Spisak(btn_Spisak);
		
		SpisakPersonelManegementMethods.ActionListener_Btn_Export(btn_Export, save_Panel, save_Panel); 
		
//		SpisakPersonelManegementMethods.ActionListener_chckbx_svePerson__isEnterInZone(chckbx_svePerson__isEnterInZone);

		SpisakPersonelManegementMethods.checkorektDate(textField_savePerson_StartDate);
		SpisakPersonelManegementMethods.checkorektDate(textField_savePerson_EndDate);


		
//		SpisakPersonelManegementMethods.ActionListener_JTextField_svePerson_Spisak(textField_svePerson_Spisak);
		

		
		SpisakPersonelManegementMethods.ActionListener_textField_svePerson_Year(textField_svePerson_Year, btn_SaveToExcelFile);
		
		SpisakPersonelManegementMethods.ActionListener_Btn_SaveToExcelFile(panel_AllSaerch, btn_SaveToExcelFile, textArea);
		
		SpisakPersonelManegementMethods.ActionListener_Btn_ReadFileListPerson( btn_ReadFileListPerson,  textArea,  
				 infoPanel, tablePane,  panel_AllSaerch,  scrollPane, textField_svePerson_Year, textField, btnBackToTable);
		
		PersonReferenceFrame.TextFieldJustNumbers(textField_svePerson_Year);
		System.out.println("6666666666666666666666666666666");
		setVisible(true);
		round.StopWindow();
		
		
	}

	

	private JPanel kodeLabel_Panel_3() {

		JPanel kodeLabel_Panel_3 = new JPanel();
		FlowLayout fl_kodeLabel_Panel_3 = (FlowLayout) kodeLabel_Panel_3.getLayout();
		fl_kodeLabel_Panel_3.setVgap(0);
		fl_kodeLabel_Panel_3.setAlignment(FlowLayout.LEFT);
		panel_Search.add(kodeLabel_Panel_3);

		JLabel lbl_KodKZ1 = new JLabel("ИД КЗ-1");
		lbl_KodKZ1.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_KodKZ1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodKZ1.setPreferredSize(new Dimension(55, 25));
		lbl_KodKZ1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ1.setBorder(null);
		lbl_KodKZ1.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodKZ1);

		JLabel lbl_KodKZ2 = new JLabel("ИД КЗ-2");
		lbl_KodKZ2.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_KodKZ2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodKZ2.setPreferredSize(new Dimension(55, 25));
		lbl_KodKZ2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ2.setBorder(null);
		lbl_KodKZ2.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodKZ2);

		JLabel lbl_KodKZHOG = new JLabel("ИД КЗ-ХОГ");
		lbl_KodKZHOG.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodKZHOG.setPreferredSize(new Dimension(55, 25));
		lbl_KodKZHOG.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZHOG.setBorder(null);
		lbl_KodKZHOG.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodKZHOG);

		JLabel lbl_KodTerit_1 = new JLabel("ИД Тер-1");
		lbl_KodTerit_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodTerit_1.setPreferredSize(new Dimension(55, 25));
		lbl_KodTerit_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodTerit_1.setBorder(null);
		lbl_KodTerit_1.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodTerit_1);

		JLabel lbl_KodTerit_2 = new JLabel("ИД Тер-1");
		lbl_KodTerit_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_KodTerit_2.setPreferredSize(new Dimension(55, 25));
		lbl_KodTerit_2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodTerit_2.setBorder(null);
		lbl_KodTerit_2.setAlignmentX(0.5f);
		kodeLabel_Panel_3.add(lbl_KodTerit_2);

		JLabel lbl_Firm = new JLabel("Фирма");
		lbl_Firm.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Firm.setSize(new Dimension(70, 20));
		lbl_Firm.setPreferredSize(new Dimension(130, 25));
		lbl_Firm.setMinimumSize(new Dimension(70, 20));
		lbl_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Firm.setBorder(null);
		lbl_Firm.setAlignmentX(1.0f);
		kodeLabel_Panel_3.add(lbl_Firm);

		JLabel lbl_Otdel = new JLabel("Отдел");
		lbl_Otdel.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_Otdel.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Otdel.setSize(new Dimension(70, 20));
		lbl_Otdel.setPreferredSize(new Dimension(142, 25));
		lbl_Otdel.setMinimumSize(new Dimension(70, 20));
		lbl_Otdel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Otdel.setBorder(null);
		lbl_Otdel.setAlignmentX(1.0f);
		kodeLabel_Panel_3.add(lbl_Otdel);

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

	private JPanel panel_Label() {
		
	JPanel panel = new JPanel();
		panel_Search.add(panel);
		
		lblNewLabel_1 = new JLabel("");
		panel.add(lblNewLabel_1);
		return panel;
	
}
	
	private JPanel panel_4() {
		
		

		JPanel panel_4 = new JPanel();
		FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
		fl_panel_4.setAlignment(FlowLayout.LEFT);
		panel_4.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel_4);
		
		textField = new JTextField();
		panel_4.add(textField);
		textField.setColumns(41);

//		ActionListenerComboBox_Results();

		btn_ReadFileListPerson = new JButton("Отвори Списък");
		btn_ReadFileListPerson.setMargin(new Insets(2, 2, 2, 2));
		btn_ReadFileListPerson.setPreferredSize(new Dimension(90, 20));
		btn_ReadFileListPerson.setEnabled(false);
		panel_4.add(btn_ReadFileListPerson);
		
		JLabel lbl_distance = new JLabel("");
		lbl_distance.setPreferredSize(new Dimension(30, 14));
		panel_4.add(lbl_distance);
		
		btn_GenerateSpisak = new JButton("Нов списък");
		panel_4.add(btn_GenerateSpisak);
		
		lbl_distance_1 = new JLabel("");
		lbl_distance_1.setPreferredSize(new Dimension(30, 14));
		panel_4.add(lbl_distance_1);
		
		btnBackToTable = new JButton("Към таблицата");
		panel_4.add(btnBackToTable);
		btnBackToTable.setEnabled(false);
		

		return panel_4;
	}

	private JPanel save_Panel() {

		save_Panel = new JPanel();
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

		personSave_OtdelSpisakLabel_Panel_4(personSave_Panel);

		personSave_OtdelSpisakField_Panel_4A(personSave_Panel);
		
		return personSave_Panel;
		
	}


	private JPanel personSave_OtdelSpisakLabel_Panel_4(JPanel personSave_Panel) {
		JPanel personSave_OtdelSpisakLabel_Panel_4 = new JPanel();
		FlowLayout fl_personSave_OtdelSpisakLabel_Panel_4 = (FlowLayout) personSave_OtdelSpisakLabel_Panel_4
				.getLayout();
		fl_personSave_OtdelSpisakLabel_Panel_4.setVgap(0);
		fl_personSave_OtdelSpisakLabel_Panel_4.setAlignment(FlowLayout.LEFT);
		personSave_Panel.add(personSave_OtdelSpisakLabel_Panel_4);
		
		lbl_svePerson_Spisak_1 = new JLabel("Година");
		lbl_svePerson_Spisak_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_Spisak_1.setPreferredSize(new Dimension(55, 25));
		lbl_svePerson_Spisak_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_Spisak_1.setBorder(null);
		lbl_svePerson_Spisak_1.setAlignmentX(0.5f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_Spisak_1);

		JLabel lbl_btn_Spisak = new JLabel("");
		lbl_btn_Spisak.setPreferredSize(new Dimension(21, 20));
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_btn_Spisak);

		JLabel lbl_svePerson_Spisak = new JLabel("Списак");
		lbl_svePerson_Spisak.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_Spisak.setPreferredSize(new Dimension(85, 25));
		lbl_svePerson_Spisak.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_Spisak.setBorder(null);
		lbl_svePerson_Spisak.setAlignmentX(0.5f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_Spisak);

		JLabel lbl_svePerson_StartDate = new JLabel("Старт Дата");
		lbl_svePerson_StartDate.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_svePerson_StartDate.setPreferredSize(new Dimension(70, 25));
		lbl_svePerson_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_svePerson_StartDate.setBorder(null);
		lbl_svePerson_StartDate.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_svePerson_StartDate);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setPreferredSize(new Dimension(35, 14));
		personSave_OtdelSpisakLabel_Panel_4.add(lblNewLabel_2);

		JLabel lbl_svePerson_EndDate = new JLabel("Крайна Дата");
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

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("modifiIcon");
		ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
		
		textField_svePerson_Year = new JTextField(curentYear);
		personSave_OtdelSpisakField_Panel_4A.add(textField_svePerson_Year);
		textField_svePerson_Year.setColumns(6);
		
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
		
		lbl_Icon_DateStart = new JLabel(picData);
		lbl_Icon_DateStart.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_DateStart.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_DateStart.setBorder(null);
		lbl_Icon_DateStart.setAlignmentX(1.0f);
		personSave_OtdelSpisakField_Panel_4A.add(lbl_Icon_DateStart);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setPreferredSize(new Dimension(10, 14));
		personSave_OtdelSpisakField_Panel_4A.add(lblNewLabel);

		textField_savePerson_EndDate = new JTextField();
		textField_savePerson_EndDate.setPreferredSize(new Dimension(5, 20));
		textField_savePerson_EndDate.setColumns(8);
		personSave_OtdelSpisakField_Panel_4A.add(textField_savePerson_EndDate);
		
		lbl_Icon_DateEnd = new JLabel(picData);
		lbl_Icon_DateEnd.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_DateEnd.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_DateEnd.setBorder(null);
		lbl_Icon_DateEnd.setAlignmentX(1.0f);
		personSave_OtdelSpisakField_Panel_4A.add(lbl_Icon_DateEnd);

		

		return personSave_OtdelSpisakField_Panel_4A;
	}

	
	
	
	private JPanel button_Panel(JPanel save_Panel) {
		
		
		JPanel button_Panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) button_Panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		save_Panel.add(button_Panel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(65, 23));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpisakPersonelManegementMethods.setChoisePerson("");
				dispose(); // Destroy the JFrame object
			}
		});
		button_Panel.add(cancelButton);
		
		

		btn_SaveToExcelFile = new JButton("Save");
		button_Panel.add(btn_SaveToExcelFile);
		btn_SaveToExcelFile.setPreferredSize(new Dimension(65, 23));
		btn_SaveToExcelFile.setMargin(new Insets(2, 5, 2, 5));
		btn_SaveToExcelFile.setIconTextGap(1);
	
		
		btn_Export = new JButton("Export");
		button_Panel.add(btn_Export);
		
		return button_Panel;
	}



	public static String getOldOtdelPerson() {
		return oldOtdelPerson;
	}

	public static void setOldOtdelPerson(String oldOtdelPerson1) {
		oldOtdelPerson = oldOtdelPerson1;
	}

	public static JRadioButton getRdbtn_KodKZ1() {
		return rdbtn_KodKZ1;
	}

	public static void setRdbtn_KodKZ1(JRadioButton rdbtn_KodKZ1_1) {
		rdbtn_KodKZ1 = rdbtn_KodKZ1_1;
	}

	public static JRadioButton getRdbtn_KodKZ2() {
		return rdbtn_KodKZ2;
	}

	public static void setRdbtn_KodKZ2(JRadioButton rdbtn_KodKZ2_1) {
		rdbtn_KodKZ2 = rdbtn_KodKZ2_1;
	}

	public static JRadioButton getRdbtn_KodKZHOG() {
		return rdbtn_KodKZHOG;
	}

	public static void setRdbtn_KodKZHOG(JRadioButton rdbtn_KodKZHOG_1) {
		rdbtn_KodKZHOG = rdbtn_KodKZHOG_1;
	}

	public static JRadioButton getRdbtn_KodTerit_1() {
		return rdbtn_KodTerit_1;
	}

	public static void setRdbtn_KodTerit_1(JRadioButton rdbtn_KodTerit_1_1) {
		rdbtn_KodTerit_1 = rdbtn_KodTerit_1_1;
	}

	public static JRadioButton getRdbtn_KodTerit_2() {
		return rdbtn_KodTerit_2;
	}

	public static void setRdbtn_KodTerit_2(JRadioButton rdbtn_KodTerit_2_1) {
		rdbtn_KodTerit_2 = rdbtn_KodTerit_2_1;
	}

	

	public static Choice getComboBox_Firm() {
		return comboBox_Firm;
	}

	public static void setComboBox_Firm(Choice comboBox_Firm_1, String text) {
		comboBox_Firm.select(text);
		comboBox_Firm = comboBox_Firm_1;
	}

	public static Choice getComboBox_Otdel() {
		return comboBox_Otdel;
	}

	public static void setComboBox_Otdel(Choice comboBox_Otdel_1, String text) {
		comboBox_Otdel.select(text);
		comboBox_Otdel = comboBox_Otdel_1;
	}

	

	

	public static JTextField getTextField_svePerson_Spisak() {
		return textField_svePerson_Spisak;
	}

	public void setTextField_svePerson_Spisak(JTextField textField_svePerson_Spisak_1, String text) {
		textField_svePerson_Spisak.setText(text);
		textField_svePerson_Spisak = textField_svePerson_Spisak_1;
	}

	public static JTextField getTextField_savePerson_StartDate() {
		return textField_savePerson_StartDate;
	}

	public void setTextField_savePerson_StartDate(JTextField textField_savePerson_StartDate_1, String text) {
		textField_savePerson_StartDate.setText(text);
		textField_savePerson_StartDate = textField_savePerson_StartDate_1;
	}

	public static JTextField getTextField_savePerson_EndDate() {
		return textField_savePerson_EndDate;
	}

	public void setTextField_savePerson_EndDate(JTextField textField_savePerson_EndDate_1, String text) {
		textField_savePerson_EndDate.setText(text);
		textField_savePerson_EndDate = textField_savePerson_EndDate_1;
	}

	
	

	public static JButton getBtn_SaveToExcelFile() {
		return btn_SaveToExcelFile;
	}

	public static void setBtn_SaveToExcelFile(JButton btn_SaveToExcelFile_1) {
		btn_SaveToExcelFile = btn_SaveToExcelFile_1;
	}

	
	
	public static JTextField getTextField_svePerson_Year() {
		return textField_svePerson_Year;
	}
	

	static void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		

		contentPane.repaint();
		contentPane.revalidate();
	}

	public static void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		
		contentPane.repaint();
		contentPane.revalidate();
	}

	
	
	public static Border getDefoutBorder() {
		return defoutBorder;
	}



	public static JLabel getLbl_OtdelKodeArea() {
		return lblNewLabel_1;
	}

	public static JButton getBtnBackToTable() {
		return btnBackToTable;
	}



	public static JButton getBtn_Export() {
		return btn_Export;
	}



	public static JTextArea getTextArea() {
		return textArea;
	}



	public static JPanel getTablePane() {
		return tablePane;
	}



	public static JPanel getPanel_AllSaerch() {
		return panel_AllSaerch;
	}



	public static JScrollPane getScrollPane() {
		return scrollPane;
	}

	
	
}

