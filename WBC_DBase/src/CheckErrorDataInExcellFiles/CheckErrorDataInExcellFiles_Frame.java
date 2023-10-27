package CheckErrorDataInExcellFiles;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasicClassAccessDbase.Person;

public class CheckErrorDataInExcellFiles_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;

	private static JTextArea textArea;
	private static JButton btn_Search;
	private static JButton btn_SearchAllColumn;
	private static JButton btn_CheckDBase;
	private static JButton btn_CheckDBase_Clear;



	private static JButton btn_Export;

	static String curentYear = AplicationMetods.getCurentYear();
	String checkCorrectinDataInExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell");
	String checkCorrectinDataInExcell_labelErrorColumn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorColumn");
	String checkCorrectinDataInExcell_labelErrorExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorExcell");
	String checkCorrectinDataInExcell_labelCheckDBaseToMouthFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelCheckDBaseToMouthFile");
	
	
	
	public CheckErrorDataInExcellFiles_Frame() {
		setTitle(checkCorrectinDataInExcell);

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
		
		panel_1();
		panel_2();
		panel_3();

		panel_Button();

		setSize(737, 900);
		setLocationRelativeTo(null);

		

		CheckErrorDataInExcellFiles_Methods.ActionListener_Btn_SearchError(panel_Search, btn_Search, textArea);
		CheckErrorDataInExcellFiles_Methods.ActionListener_Btn_SearchAllColumn(panel_Search, btn_SearchAllColumn, textArea);
		CheckErrorDataInExcellFiles_Methods.ActionListener_Btn_CheckDBaseToMounthFile(panel_Search, btn_CheckDBase, textArea);
		CheckErrorDataInExcellFiles_Methods.ActionListener_Btn_CheckDBase_Clear(panel_Search, btn_CheckDBase_Clear, textArea);

		setVisible(true);
		
		
	}

	

	private JPanel panel_1() {
		
		JPanel panel1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel1);
		
		JLabel lbl_Year_1 = new JLabel(checkCorrectinDataInExcell_labelErrorColumn);
		
		lbl_Year_1.setPreferredSize(new Dimension(550, 15));
		lbl_Year_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year_1.setBorder(null);
		lbl_Year_1.setAlignmentX(0.5f);
		panel1.add(lbl_Year_1);
		
		btn_SearchAllColumn = new JButton("Search");
		panel1.add(btn_SearchAllColumn);
		btn_SearchAllColumn.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchAllColumn.setPreferredSize(new Dimension(110, 23));
		
		
		return panel1;
	}
		
	private JPanel panel_2() {
			
		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);

		JLabel lbl_Year = new JLabel(checkCorrectinDataInExcell_labelErrorExcell);
				
		lbl_Year.setPreferredSize(new Dimension(550, 15));
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panel2.add(lbl_Year);
		
				btn_Search = new JButton("Search");
				panel2.add(btn_Search);
				btn_Search.setMargin(new Insets(2, 5, 2, 5));
				btn_Search.setPreferredSize(new Dimension(110, 23));
				

		return panel2;
	}

	private JPanel panel_3() {
		
		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);

		JLabel lbl_Year = new JLabel(checkCorrectinDataInExcell_labelCheckDBaseToMouthFile);
				
		lbl_Year.setPreferredSize(new Dimension(515, 15));
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panel2.add(lbl_Year);
		
		btn_CheckDBase = new JButton("Search");
		panel2.add(btn_CheckDBase);
		btn_CheckDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_CheckDBase.setPreferredSize(new Dimension(70, 23));
		
		btn_CheckDBase_Clear = new JButton("Clear");
		btn_CheckDBase_Clear.setEnabled(false);
		btn_CheckDBase_Clear.setPreferredSize(new Dimension(70, 23));
		btn_CheckDBase_Clear.setMargin(new Insets(2, 5, 2, 5));
		panel2.add(btn_CheckDBase_Clear);
				

		return panel2;
	}

	
	
	private JPanel panel_Button() {

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		btn_Export = new JButton("Export");
		buttonPanel.add(btn_Export);


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



	public static JTextArea getTextArea() {
		return textArea;
	}

	public static JButton getBtn_Search() {
		return btn_Search;
	}
	
	public static JButton getBtn_Search_1() {
		return btn_SearchAllColumn;
	}

	public static JButton getBtn_Export() {
		return btn_Export;
	}



	public static JButton getBtn_CheckDBase() {
		return btn_CheckDBase;
	}



	public static void setBtn_CheckDBase(JButton btn_CheckDBase) {
		CheckErrorDataInExcellFiles_Frame.btn_CheckDBase = btn_CheckDBase;
	}



	public static JButton getBtn_CheckDBase_Clear() {
		return btn_CheckDBase_Clear;
	}



	public static void setBtn_CheckDBase_Clear(JButton btn_CheckDBase_Clear) {
		CheckErrorDataInExcellFiles_Frame.btn_CheckDBase_Clear = btn_CheckDBase_Clear;
	}

	
}
