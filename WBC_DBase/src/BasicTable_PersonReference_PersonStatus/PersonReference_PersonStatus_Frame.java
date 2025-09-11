package BasicTable_PersonReference_PersonStatus;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import WBCUsersLogin.WBCUsersLogin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;

import java.awt.Label;
import java.awt.Component;
import javax.swing.JProgressBar;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class PersonReference_PersonStatus_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel save_Panel;
	private static JPanel infoPanel;
	private static JPanel tablePane;
	private JScrollPane scrollPane;

	private static JProgressBar progressBar;
	private static JTextArea textArea;
	private static JButton btn_Export;

	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";

	
	static Border defoutBorder;
	static UsersWBC operatorUser;

	private static JTextField textField_EGN;
	private static JTextField textField_EndDate;
	private static JTextField textField_StartDate;
	private static JTextField textField_Year;
	private static JTextField textField_Dokument;
	private static JTextField textField_Zabelejka;

	private JPanel personSave_Panel;

	private JButton btn_StartGenerateTable;

	private static JLabel lbl_Input_Name;

	private static JCheckBox chckbx_Editing;

	private static Choice choice_Firm;
	private static Choice choice_Otdel;

	private static JLabel lbl_Icon_StartDate;
	private static JLabel lbl_Icon_EndDate;
	private static JTextField textField_Import_Year;
	private Label label;

	List<String> listOtdelKz;
	List<String> listOtdelVO;
	List<String> listAdd;
	List<String> listFirm;

	private static JButton btn_Edit;
	private JPanel panel_Btn_Delete;
	private static JButton btn_Delete;
	private JPanel panel_Btn_Add;
	private static JButton btn_Add;
	private JLabel lblNewLabel;
	private JLabel lbl_EGN;
	private static JTextField textEGN;

	public PersonReference_PersonStatus_Frame(String dokumentStatusUser, ActionIcone round) {

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		setTitle(dokumentStatusUser);

		setMinimumSize(new Dimension(1120, 900));

		String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
		String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");

		listOtdelKz = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", AEC));
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);

		listOtdelVO = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", VO));
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
		
		operatorUser =  WBCUsersLogin.getCurentUser();

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

		listFirm = new ArrayList<>();
		listFirm.add("");
		listFirm.add(AEC);
		listFirm.add(VO);

		InsertStartPanel();
		
		ProgrressBarPanel();

		save_Panel();

		setSize(1120, 900);
		setLocationRelativeTo(null);

		PersonReference_PersonStatus_Methods.ActionListenertextField_Year(textField_Year, btn_StartGenerateTable);
		PersonReference_PersonStatus_Methods.ActionListenertextField_Year(textField_Import_Year);
		
		PersonReference_PersonStatus_Methods.ActionListenerCheckCorectDate(textField_StartDate);
		PersonReference_PersonStatus_Methods.ActionListenerCheckCorectDate(textField_EndDate);
		
		PersonReference_PersonStatus_Methods.ActionListenerGetPersonByEGN(textField_EGN, contentPane);
		
		PersonReference_PersonStatus_Methods.ActionListenerIsSelectOtdel(choice_Otdel);
		
		PersonReference_PersonStatus_Methods.ActionListener_Btn_StartGenerateTable_PersonStatus(btn_StartGenerateTable, panel_AllSaerch, tablePane, sp, operatorUser, chckbx_Editing, progressBar);
		PersonReference_PersonStatus_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_StartDate, textField_StartDate);
		PersonReference_PersonStatus_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_EndDate, textField_EndDate);
		PersonReference_PersonStatus_Methods.ActionListener_Btn_Add_PersonStatus(btn_Add, panel_AllSaerch, tablePane, sp, operatorUser);
		PersonReference_PersonStatus_Methods.ActionListener_Btn_Editing_PersonStatus(btn_Edit, panel_AllSaerch, tablePane, sp, operatorUser);
		PersonReference_PersonStatus_Methods.ActionListener_Btn_Delete_PersonStatus(btn_Delete, panel_AllSaerch, tablePane, sp, operatorUser);
		PersonReference_PersonStatus_Methods.ActionListener_Btn_ExportToExcel(btn_Export, tablePane);
		
		setVisible(true);
		round.StopWindow();

	}

	private JPanel InsertStartPanel() {
		JPanel panel_4 = new JPanel();
		FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
		fl_panel_4.setAlignment(FlowLayout.LEFT);
		panel_4.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel_4);

		JLabel lbl_textField_Year = new JLabel("Година");
		lbl_textField_Year.setPreferredSize(new Dimension(50, 20));
		lbl_textField_Year.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_textField_Year.setBorder(null);
		lbl_textField_Year.setAlignmentX(0.5f);
		panel_4.add(lbl_textField_Year);

		textField_Year = new JTextField(curentYear);
		panel_4.add(textField_Year);
		textField_Year.setColumns(4);

		TextFieldJustNumbers(textField_Year);
		
		lbl_EGN = new JLabel("ЕГН");
		lbl_EGN.setPreferredSize(new Dimension(50, 20));
		lbl_EGN.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(0.5f);
		panel_4.add(lbl_EGN);
		
		textEGN = new JTextField();
		textEGN.setColumns(8);
		panel_4.add(textEGN);
		TextFieldJustNumbers(textEGN);
		
		
		JLabel lblNewLabel_3 = new JLabel("Фирма");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setPreferredSize(new Dimension(100, 20));
		panel_4.add(lblNewLabel_3);

		choice_Firm = new Choice();
		choice_Firm.setPreferredSize(new Dimension(150, 20));
		choice_Firm.add("АЕЦ Козлодуй");
		choice_Firm.add("Външни организации");
		panel_4.add(choice_Firm);

		ActionListenerComboBox_Firm(choice_Firm);

		addItem(choice_Firm, listFirm);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setPreferredSize(new Dimension(45, 10));
		panel_4.add(lblNewLabel_6);

		btn_StartGenerateTable = new JButton("Старт");
		panel_4.add(btn_StartGenerateTable);
		btn_StartGenerateTable.setPreferredSize(new Dimension(80, 20));
		btn_StartGenerateTable.setMargin(new Insets(2, 2, 2, 2));

		JLabel lblNewLabel_4 = new JLabel("редактиране");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_4.setMinimumSize(new Dimension(200, 14));
		lblNewLabel_4.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setPreferredSize(new Dimension(400, 14));
		panel_4.add(lblNewLabel_4);

		chckbx_Editing = new JCheckBox();
		panel_4.add(chckbx_Editing);
		chckbx_Editing.setEnabled(false);
		if (operatorUser != null && operatorUser.get_isAdmin()) {
			chckbx_Editing.setEnabled(true);
		}

		return panel_4;
	}

	private JPanel ProgrressBarPanel() {
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 20));
		panel_Search.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		progressBar = new JProgressBar(0,100);
		 progressBar.setValue(0);
	     progressBar.setStringPainted(true);
		
		panel_5.add(progressBar);
		return panel_5;
	}

	public void updateBar(int newValue) {
		progressBar.setValue(newValue);
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

		JPanel personSave_Panel_1 = new JPanel();
		personSave_Panel.add(personSave_Panel_1);

		personSave_Personel_2_LabelPanel(personSave_Panel);

		personSave_Personel_2_FildPanel(personSave_Panel);

		personSave_Kode_Panel_2A(personSave_Panel);

		personPrint_Otdel_Panel_2B(personSave_Panel);

		return personSave_Panel;

	}

	private JPanel personSave_Personel_2_LabelPanel(JPanel personSave_Panel) {

		JPanel personSave_Personel_2_LabelPanel = new JPanel();
		FlowLayout fl_personSave_Personel_2_LabelPanel = (FlowLayout) personSave_Personel_2_LabelPanel.getLayout();
		fl_personSave_Personel_2_LabelPanel.setVgap(0);
		fl_personSave_Personel_2_LabelPanel.setAlignment(FlowLayout.LEFT);
		personSave_Panel.add(personSave_Personel_2_LabelPanel);

		JLabel lbl_EGN = new JLabel("ЕГН");
		lbl_EGN.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_EGN.setPreferredSize(new Dimension(85, 20));
		lbl_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(0.5f);
		personSave_Personel_2_LabelPanel.add(lbl_EGN);

		JLabel lbl_Name = new JLabel("Име");
		lbl_Name.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Name.setPreferredSize(new Dimension(300, 20));
		lbl_Name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Name.setBorder(null);
		lbl_Name.setAlignmentX(0.5f);
		personSave_Personel_2_LabelPanel.add(lbl_Name);

		JLabel lbl_Otdel = new JLabel("Отдел");
		personSave_Personel_2_LabelPanel.add(lbl_Otdel);
		lbl_Otdel.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Otdel.setPreferredSize(new Dimension(215, 20));
		lbl_Otdel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Otdel.setBorder(null);
		lbl_Otdel.setAlignmentX(1.0f);

		JLabel lbl_textField_Yeare = new JLabel("Година");
		lbl_textField_Yeare.setPreferredSize(new Dimension(252, 20));
		lbl_textField_Yeare.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_textField_Yeare.setBorder(null);
		lbl_textField_Yeare.setAlignmentX(0.5f);
		personSave_Personel_2_LabelPanel.add(lbl_textField_Yeare);

		return personSave_Personel_2_LabelPanel;
	}

	private JPanel personSave_Personel_2_FildPanel(JPanel personSave_Panel) {
		JPanel personSave_Personel_2_FildPanel = new JPanel();
		FlowLayout fl_personSave_Personel_2_FildPanel = (FlowLayout) personSave_Personel_2_FildPanel.getLayout();
		fl_personSave_Personel_2_FildPanel.setVgap(2);
		fl_personSave_Personel_2_FildPanel.setAlignment(FlowLayout.LEFT);
		personSave_Personel_2_FildPanel.setPreferredSize(new Dimension(10, 30));
		personSave_Panel.add(personSave_Personel_2_FildPanel);

		textField_EGN = new JTextField();
		personSave_Personel_2_FildPanel.add(textField_EGN);
		textField_EGN.setPreferredSize(new Dimension(5, 20));
		textField_EGN.setMinimumSize(new Dimension(5, 20));
		textField_EGN.setColumns(9);
		TextFieldJustNumbers(textField_EGN);

		lbl_Input_Name = new JLabel("");
		lbl_Input_Name.setPreferredSize(new Dimension(310, 14));
		personSave_Personel_2_FildPanel.add(lbl_Input_Name);

		choice_Otdel = new Choice();
		choice_Otdel.setPreferredSize(new Dimension(420, 0));
		personSave_Personel_2_FildPanel.add(choice_Otdel);

		addItem(choice_Otdel, listOtdelKz);
		
		label = new Label("");
		label.setPreferredSize(new Dimension(5, 0));
		personSave_Personel_2_FildPanel.add(label);

		textField_Import_Year = new JTextField();
		textField_Import_Year.setColumns(4);
		personSave_Personel_2_FildPanel.add(textField_Import_Year);
		TextFieldJustNumbers(textField_Import_Year);
		
		JLabel lbl_Dim1B = new JLabel("");
		lbl_Dim1B.setPreferredSize(new Dimension(70, 14));
		personSave_Personel_2_FildPanel.add(lbl_Dim1B);
		
		panel_Btn_Add = new JPanel();
		personSave_Personel_2_FildPanel.add(panel_Btn_Add);
		
		btn_Add = new JButton("Добавяне");
		btn_Add.setPreferredSize(new Dimension(85, 23));
		panel_Btn_Add.add(btn_Add);
		btn_Add.setEnabled(false);
		if (operatorUser != null && operatorUser.get_isAdmin()) {
			btn_Add.setEnabled(true);
		}
		
		
		
		return personSave_Personel_2_FildPanel;

	}

	private JPanel personSave_Kode_Panel_2A(JPanel personSave_Panel) {
		JPanel personSave_OtdelSpisakLabel_Panel_4 = new JPanel();
		personSave_OtdelSpisakLabel_Panel_4.setAlignmentY(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.setPreferredSize(new Dimension(10, 30));
		FlowLayout fl_personSave_OtdelSpisakLabel_Panel_4 = (FlowLayout) personSave_OtdelSpisakLabel_Panel_4
				.getLayout();
		fl_personSave_OtdelSpisakLabel_Panel_4.setVgap(7);
		fl_personSave_OtdelSpisakLabel_Panel_4.setAlignment(FlowLayout.LEFT);
		personSave_Panel.add(personSave_OtdelSpisakLabel_Panel_4);

		JLabel lbl_Dokument = new JLabel("Документ");
		lbl_Dokument.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Dokument.setPreferredSize(new Dimension(70, 20));
		lbl_Dokument.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Dokument.setBorder(null);
		lbl_Dokument.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_Dokument);

		JLabel lbl_Dim1 = new JLabel("");
		lbl_Dim1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Dim1.setPreferredSize(new Dimension(40, 20));
		lbl_Dim1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Dim1.setBorder(null);
		lbl_Dim1.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_Dim1);

		JLabel lbl_StartDate = new JLabel("Начална дата");
		lbl_StartDate.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_StartDate.setPreferredSize(new Dimension(75, 20));
		lbl_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_StartDate.setBorder(null);
		lbl_StartDate.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_StartDate);

		JLabel lbl_Dim2 = new JLabel("");
		lbl_Dim2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Dim2.setPreferredSize(new Dimension(42, 20));
		lbl_Dim2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Dim2.setBorder(null);
		lbl_Dim2.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_Dim2);

		JLabel lbl_EndDate = new JLabel("Крайна дата");
		lbl_EndDate.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_EndDate.setPreferredSize(new Dimension(75, 20));
		lbl_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EndDate.setBorder(null);
		lbl_EndDate.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_EndDate);

		JLabel lbl_Dim1_1 = new JLabel("");
		lbl_Dim1_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Dim1_1.setPreferredSize(new Dimension(20, 20));
		lbl_Dim1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Dim1_1.setBorder(null);
		lbl_Dim1_1.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_Dim1_1);

		JLabel lbl_Zabelejka = new JLabel("Забележка");
		lbl_Zabelejka.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Zabelejka.setPreferredSize(new Dimension(550, 20));
		lbl_Zabelejka.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Zabelejka.setBorder(null);
		lbl_Zabelejka.setAlignmentX(1.0f);
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_Zabelejka);
		
		JLabel lbl_Dim1B = new JLabel();
		lbl_Dim1B.setPreferredSize(new Dimension(45, 14));
		personSave_OtdelSpisakLabel_Panel_4.add(lbl_Dim1B);
		
		btn_Edit = new JButton("Промяна");
		btn_Edit.setPreferredSize(new Dimension(85, 23));
		personSave_OtdelSpisakLabel_Panel_4.add(btn_Edit);
		btn_Edit.setEnabled(false);
		if (operatorUser != null && operatorUser.get_isAdmin()) {
			btn_Edit.setEnabled(true);
		}
		
		return personSave_OtdelSpisakLabel_Panel_4;
	}

	private JPanel personPrint_Otdel_Panel_2B(JPanel personSave_Panel) {
		JPanel personPrint_OtdelField_Panel_4A = new JPanel();
		FlowLayout fl_personSave_OtdelSpisakField_Panel_4A = (FlowLayout) personPrint_OtdelField_Panel_4A.getLayout();
		fl_personSave_OtdelSpisakField_Panel_4A.setVgap(0);
		fl_personSave_OtdelSpisakField_Panel_4A.setAlignment(FlowLayout.LEFT);
		personPrint_OtdelField_Panel_4A.setPreferredSize(new Dimension(10, 30));
		personSave_Panel.add(personPrint_OtdelField_Panel_4A);

		textField_Dokument = new JTextField();
		textField_Dokument.setPreferredSize(new Dimension(5, 20));
		textField_Dokument.setHorizontalAlignment(SwingConstants.LEFT);
		textField_Dokument.setColumns(10);
		personPrint_OtdelField_Panel_4A.add(textField_Dokument);

		JLabel lbl_Dim1A = new JLabel("");
		lbl_Dim1A.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_Dim1A.setPreferredSize(new Dimension(20, 20));
		lbl_Dim1A.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Dim1A.setBorder(null);
		lbl_Dim1A.setAlignmentX(1.0f);
		personPrint_OtdelField_Panel_4A.add(lbl_Dim1A);

		textField_StartDate = new JTextField();
		textField_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StartDate.setPreferredSize(new Dimension(5, 20));
		textField_StartDate.setColumns(9);
		personPrint_OtdelField_Panel_4A.add(textField_StartDate);

		
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("calendarIcon");
//		String iconn ="ICONS/Calendar1.png";
		ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
		
		lbl_Icon_StartDate = new JLabel(pic);
		lbl_Icon_StartDate.setVerticalTextPosition(SwingConstants.TOP);
		lbl_Icon_StartDate.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_Icon_StartDate.setPreferredSize(new Dimension(40, 20));
		lbl_Icon_StartDate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Icon_StartDate.setBorder(null);
		lbl_Icon_StartDate.setAlignmentX(1.0f);
		personPrint_OtdelField_Panel_4A.add(lbl_Icon_StartDate);
		
		

		textField_EndDate = new JTextField();
		textField_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		textField_EndDate.setPreferredSize(new Dimension(5, 20));
		textField_EndDate.setColumns(9);
		personPrint_OtdelField_Panel_4A.add(textField_EndDate);

		lbl_Icon_EndDate = new JLabel(pic);
		lbl_Icon_EndDate.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_EndDate.setBorder(null);
		lbl_Icon_EndDate.setAlignmentX(1.0f);
		personPrint_OtdelField_Panel_4A.add(lbl_Icon_EndDate);

		textField_Zabelejka = new JTextField();
		textField_Zabelejka.setPreferredSize(new Dimension(5, 20));
		textField_Zabelejka.setHorizontalAlignment(SwingConstants.LEFT);
		textField_Zabelejka.setColumns(70);
		personPrint_OtdelField_Panel_4A.add(textField_Zabelejka);
		
		JLabel lbl_Dim1B = new JLabel();
		lbl_Dim1B.setPreferredSize(new Dimension(25, 14));
		personPrint_OtdelField_Panel_4A.add(lbl_Dim1B);
		
		panel_Btn_Delete = new JPanel();
		panel_Btn_Delete.setBounds(new Rectangle(5, 0, 0, 0));
		panel_Btn_Delete.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_Btn_Delete.setAlignmentX(Component.LEFT_ALIGNMENT);
		personPrint_OtdelField_Panel_4A.add(panel_Btn_Delete);
		
		btn_Delete = new JButton("Изтиване");
		btn_Delete.setAlignmentY(Component.TOP_ALIGNMENT);
		btn_Delete.setPreferredSize(new Dimension(85, 23));
		panel_Btn_Delete.add(btn_Delete);
		btn_Delete.setEnabled(false);
		if (operatorUser != null && operatorUser.get_isAdmin()) {
			btn_Delete.setEnabled(true);
		}
		return personPrint_OtdelField_Panel_4A;
	}

	

	private JPanel button_Panel(JPanel save_Panel) {

		JPanel button_Panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) button_Panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		save_Panel.add(button_Panel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(85, 23));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				PersonelManegementMethods.setChoisePerson("");
//				choisePerson = NewChoisePerson;
				dispose(); // Destroy the JFrame object
			}
		});
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setPreferredSize(new Dimension(863, 10));
		button_Panel.add(lblNewLabel);
		button_Panel.add(cancelButton);

		btn_Export = new JButton("Export");
		btn_Export.setPreferredSize(new Dimension(85, 23));
		button_Panel.add(btn_Export);
		btn_Export.setEnabled(false);

		return button_Panel;
	}

	
	public static JTextField getTextField_svePerson_EGN() {
		return textField_EGN;
	}

	public void setTextField_svePerson_EGN(JTextField textField_svePerson_EGN, String text) {
		textField_svePerson_EGN.setText(text);
		PersonReference_PersonStatus_Frame.textField_EGN = textField_svePerson_EGN;
	}

	public static Border getDefoutBorder() {
		return defoutBorder;
	}

	

	static void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));

		contentPane.repaint();
		contentPane.revalidate();
	}

	static void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));

		contentPane.repaint();
		contentPane.revalidate();
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


	
	private void ActionListenerComboBox_Firm(Choice choice_Firm) {

		choice_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setitemInChoise();
				}
			}
		});

	}

	private void setitemInChoise() {
		listAdd = listOtdelVO;
		if (((String) choice_Firm.getSelectedItem()).trim().equals("АЕЦ Козлодуй")) {
			listAdd = listOtdelKz;
		}

		addItem(choice_Otdel, listAdd);
	}

	private void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	public static ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
		ArrayList<String> list = new ArrayList<String>();
		for (Workplace workplace : valueWorkplaceByObject) {
			list.add(workplace.getOtdel());
		}
		return list;
	}

	public static JTextField getTextField_printPerson_Firm() {
		return textField_StartDate;
	}

	public static JTextField getTextField_Year() {
		return textField_Year;
	}

	public static JCheckBox getChckbx_Editing() {
		return chckbx_Editing;
	}

	public static Choice getChoice_Firm() {
		return choice_Firm;
	}

	public static JTextField getTextField_EGN() {
		return textField_EGN;
	}

	public static JTextField getTextField_EndDate() {
		return textField_EndDate;
	}

	public static JTextField getTextField_StartDate() {
		return textField_StartDate;
	}

	public static JTextField getTextField_Dokument() {
		return textField_Dokument;
	}

	public static JTextField getTextField_Zabelejka() {
		return textField_Zabelejka;
	}

	public static JLabel getLbl_Input_Name() {
		return lbl_Input_Name;
	}

	public static Choice getChoice_Otdel() {
		return choice_Otdel;
	}

	public static JLabel getLbl_Icon_StartDate() {
		return lbl_Icon_StartDate;
	}

	public static JLabel getLbl_Icon_EndDate() {
		return lbl_Icon_EndDate;
	}

	public static JTextField getTextField_Import_Year() {
		return textField_Import_Year;
	}

	public static JProgressBar getProgressBar() {
		return progressBar;
	}

	public static void setProgressBar(JProgressBar progressBar) {
		PersonReference_PersonStatus_Frame.progressBar = progressBar;
	}

	public static JButton getBtn_Edit() {
		return btn_Edit;
	}

	public static JButton getBtn_Delete() {
		return btn_Delete;
	}

	public static JButton getBtn_Add() {
		return btn_Add;
	}
	public static JButton getBtn_Export() {
		return btn_Export;
	}

	public static JTextField getTextEGN() {
		return textEGN;
	}
}
