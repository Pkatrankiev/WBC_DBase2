package MeasurInOderLabCheck;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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


public class MeasurInOderLabFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JLayeredPane panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel save_Panel;
	private static JPanel infoPanel;
	private static JPanel tablePane;
	private static JScrollPane scrollPane;

	private static JTextArea textArea;
	private static JButton btn_ReadMeasurInOderLab;

	private static JButton btn_SaveChangeToDBase;
	private static JButton btn_Export;
	
	private static JRadioButton rdbtn_KodKZ1;
	private static JRadioButton rdbtn_KodKZ2;
	private static JRadioButton rdbtn_KodKZHOG;
	
	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
	
	static String oldOtdelPerson;
	static Border defoutBorder;
	private JTextField textFieldGodina;
	
	static UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(1);
	private static JLabel lblNewLabel_1;
	private static JButton btnBackToTable;
	



	private static JLabel lbl_distance_1;
	
	String iconn1 = ReadFileBGTextVariable.getGlobalTextVariableMap().get("calendarIcon");
	
	private JLabel lblGodina;


	public MeasurInOderLabFrame(ActionIcone round) {
		
		String labelMeasurInOderLabFrame = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelMeasurInOderLabFrame");
		
		setTitle(labelMeasurInOderLabFrame+" -> "+user.getLastName());
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
		panel_AllSaerch = new JLayeredPane();
		contentPane.add(panel_AllSaerch, BorderLayout.CENTER);
		panel_AllSaerch.setLayout(null);
		
		
		tablePane = new JPanel();
		tablePane.setBounds(0, 0, 880, 723);
		panel_AllSaerch.setLayer(tablePane, 1);
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablePane.add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		
		infoPanel = new JPanel();
		infoPanel.setBounds(0, 0, 880, 723);
		panel_AllSaerch.setLayer(infoPanel, 4);
		infoPanel.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		
		textArea = new JTextArea();
		
		textArea.setEditable(false);
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);
		
	
		kodeLabel_Panel_3();
		kodeRadioButtons_Panel_3A();
		panel_4();
		panel_Label();
		save_Panel();

		setSize(909, 900);
		setLocationRelativeTo(null);
		
		MeasurInOderLabMetods.setDataTable(null);
		MeasurInOderLabMetods.ActionListener_Btn_ReadMeasurInDifLab( btn_ReadMeasurInOderLab,  textArea,  
												 infoPanel, tablePane,  panel_AllSaerch,  scrollPane, textFieldGodina, btnBackToTable);
		MeasurInOderLabMetods.ActionListenerBtnBackToTable(btnBackToTable, textArea, tablePane,
									panel_AllSaerch, scrollPane, textFieldGodina);
		
		MeasurInOderLabMetods.ActionListenerBtnSaveChangeToDBase(btn_SaveChangeToDBase,  tablePane);
		
		MeasurInOderLabMetods.ActionListener_Btn_Export(btn_Export, save_Panel, save_Panel);
		
		MeasurInOderLabMetods.ActionListenerChengePanelSizeByFrameSize(this, tablePane, infoPanel);
		
		
		setVisible(true);
		round.StopWindow();
		
		
	}

	
	
	private JPanel kodeLabel_Panel_3() {

		JPanel kodeLabel_Panel_3 = new JPanel();
		FlowLayout fl_kodeLabel_Panel_3 = (FlowLayout) kodeLabel_Panel_3.getLayout();
		fl_kodeLabel_Panel_3.setVgap(0);
		fl_kodeLabel_Panel_3.setAlignment(FlowLayout.LEFT);
		panel_Search.add(kodeLabel_Panel_3);
		
				JLabel lbl_ToRDButtons1 = new JLabel("Код който да се");
				lbl_ToRDButtons1.setVerticalTextPosition(SwingConstants.BOTTOM);
				lbl_ToRDButtons1.setVerticalAlignment(SwingConstants.BOTTOM);
				lbl_ToRDButtons1.setSize(new Dimension(70, 20));
				lbl_ToRDButtons1.setPreferredSize(new Dimension(142, 25));
				lbl_ToRDButtons1.setMinimumSize(new Dimension(70, 20));
				lbl_ToRDButtons1.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_ToRDButtons1.setBorder(null);
				lbl_ToRDButtons1.setAlignmentX(1.0f);
				kodeLabel_Panel_3.add(lbl_ToRDButtons1);

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

		return kodeLabel_Panel_3;
	}

	private JPanel kodeRadioButtons_Panel_3A() {
		JPanel kodeRadioButtons_Panel_3A = new JPanel();
		FlowLayout fl_kodeRadioButtons_Panel_3A = (FlowLayout) kodeRadioButtons_Panel_3A.getLayout();
		fl_kodeRadioButtons_Panel_3A.setVgap(0);
		fl_kodeRadioButtons_Panel_3A.setAlignment(FlowLayout.LEFT);
		kodeRadioButtons_Panel_3A.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(kodeRadioButtons_Panel_3A);
		
		JLabel lbl_toRDButons2 = new JLabel("изведе в списъка");
		lbl_toRDButons2.setVerticalTextPosition(SwingConstants.BOTTOM);
		lbl_toRDButons2.setVerticalAlignment(SwingConstants.TOP);
		lbl_toRDButons2.setSize(new Dimension(70, 20));
		lbl_toRDButons2.setPreferredSize(new Dimension(142, 25));
		lbl_toRDButons2.setMinimumSize(new Dimension(70, 20));
		lbl_toRDButons2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_toRDButons2.setBorder(null);
		lbl_toRDButons2.setAlignmentX(1.0f);
		kodeRadioButtons_Panel_3A.add(lbl_toRDButons2);

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

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtn_KodKZ1);
		bg.add(rdbtn_KodKZ2);
		bg.add(rdbtn_KodKZHOG);

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
		
		lblGodina = new JLabel("Година");
		lblGodina.setPreferredSize(new Dimension(50, 14));
		panel_4.add(lblGodina);
		
		textFieldGodina = new JTextField();
		textFieldGodina.setText(curentYear);
		panel_4.add(textFieldGodina);
		textFieldGodina.setColumns(4);
		
		JLabel lbl_distance = new JLabel("");
		lbl_distance.setPreferredSize(new Dimension(30, 14));
		panel_4.add(lbl_distance);
		
		btn_ReadMeasurInOderLab = new JButton("Старт");
		panel_4.add(btn_ReadMeasurInOderLab);
		
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

		button_Panel(save_Panel);

		return save_Panel;
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
				dispose(); // Destroy the JFrame object
			}
		});
		button_Panel.add(cancelButton);
		
		

		btn_SaveChangeToDBase = new JButton("Save");
		button_Panel.add(btn_SaveChangeToDBase);
		btn_SaveChangeToDBase.setPreferredSize(new Dimension(65, 23));
		btn_SaveChangeToDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SaveChangeToDBase.setIconTextGap(1);
	
		
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
	
	public static JButton getBtn_SaveChangeToDBase() {
		return btn_SaveChangeToDBase;
	}

	public static void setBtn_SaveChangeToDBase(JButton btn_SaveToExcelFile_1) {
		btn_SaveChangeToDBase = btn_SaveToExcelFile_1;
	}

	static void viewTablePanel() {
		
		panel_AllSaerch.setLayer(tablePane, 4, 0);
		panel_AllSaerch.setLayer(infoPanel, 2, 0);
		infoPanel.setVisible(false);
		tablePane.setVisible(true);
		
	}

	static void viewInfoPanel() {
		
		panel_AllSaerch.setLayer(infoPanel, 4, 0);
		panel_AllSaerch.setLayer(tablePane, 1, 0);
		tablePane.setVisible(false);
		infoPanel.setVisible(true);
		
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



	public static JLayeredPane getPanel_AllSaerch() {
		return panel_AllSaerch;
	}



	public static JScrollPane getScrollPane() {
		return scrollPane;
	}
}
