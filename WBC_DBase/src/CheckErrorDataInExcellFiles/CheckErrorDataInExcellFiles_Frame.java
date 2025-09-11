package CheckErrorDataInExcellFiles;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
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
import BasicClassAccessDbase.Person;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;

import javax.swing.JProgressBar;

public class CheckErrorDataInExcellFiles_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;

	private static JTextArea textArea;
	private static JButton btn_Search;
	private static JButton btn_Search_CheckPersonStatus;
	private static JButton btn_Search_KodeAndNameKD;
	private static JButton btn_SearchAllColumn;
	private static JButton btn_CheckDBase;
	private static JButton btn_CheckDBase_Clear;
	private static JButton btn_CheckDBaseNameKodeStat;
	private static JButton btn_CheckDBaseNameKodeStat_Clear;
	private static JButton btn_CheckCurentDataInExcelFiles;
	private static List<String> listMoveEGN = null;
	



	private static JButton btn_Export;

	static String curentYear = AplicationMetods.getCurentYear();
	String checkCorrectinDataInExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell");
	String checkCorrectinDataInExcell_labelCheckCurentDataInExcelFiles = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelCheckCurentDataInExcelFiles");
	String checkCorrectinDataInExcell_labelErrorColumn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorColumn");
	String checkCorrectinDataInExcell_labelErrorExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorExcell");
	String checkCorrectinDataInExcell_labelCheckDBaseToMouthFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelCheckDBaseToMouthFile");
	String checkCorrectinDataInExcell_labelCheckName_KodestatDBaseToMouthFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelCheckName_KodestatDBaseToMouthFile");
	String CheckPersonStatus_labelError = ReadFileBGTextVariable.getGlobalTextVariableMap().get("CheckPersonStatus_labelError");
	String KodeAndNameKD_labelError = ReadFileBGTextVariable.getGlobalTextVariableMap().get("KodeAndNameKD_labelError");
	
	
	private JProgressBar progressBar;
	

	
	
	public CheckErrorDataInExcellFiles_Frame() {
		setTitle(checkCorrectinDataInExcell);

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
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);
		
		panel_DataError();
		panel_ColumnIn5Sheet();
		panel_MonthError();
		panel_OtdelPerson();
		panel_KodeAndNameKD();
		panel_MeasurDBInMonth();
		panel_KodeAndNameerror();
		
		panel_Button();

		progressBar = new JProgressBar(0,100);
		 progressBar.setValue(0);
	     progressBar.setStringPainted(true);
		panel_Search.add(progressBar);
		
		 
		setSize(737, 900);
		setLocationRelativeTo(null);

		

		CheckDataBethwinExcelFilesAndMonth.ActionListener_Btn_SearchError(panel_Search, btn_Search, textArea, progressBar);
		CheckEqualsForFirst5Column.ActionListener_Btn_SearchAllColumn(progressBar,panel_Search, btn_SearchAllColumn, textArea);
		CheckMeasurDBaseToMounthFile.ActionListener_Btn_CheckDBaseToMounthFile(progressBar,panel_Search, btn_CheckDBase, textArea);
		CheckMeasurDBaseToMounthFile.ActionListener_Btn_CheckDBase_Clear(panel_Search, btn_CheckDBase_Clear, textArea);
		CheckPersonName_KodeStatus_DBseToExcelFiles.ActionListener_Btn_CheckDBaseNameKodeStat(panel_Search, btn_CheckDBaseNameKodeStat, textArea, progressBar);
		CheckPersonName_KodeStatus_DBseToExcelFiles.ActionListener_Btn_CheckDBaseNameKodeStat_Clear(panel_Search, btn_CheckDBaseNameKodeStat_Clear, textArea);
		CheckCurentDataInExcelFilesMetod.ActionListener_Btn_CheckCurentDataInExcelFilesWithProgresBar(progressBar, panel_Search, btn_CheckCurentDataInExcelFiles, textArea);
		CheckPersonStatus.ActionListener_Btn_CheckPersonStatus(panel_Search, btn_Search_CheckPersonStatus, textArea, progressBar);
		CheckDataExcellFilesAndKD.ActionListener_Btn_KodeAndNameKD(panel_Search, btn_Search_KodeAndNameKD, textArea, progressBar);
		
		
		setVisible(true);
		
					
	}
	
	public void updateBar(int newValue) {
		progressBar.setValue(newValue);
	  }


	private JPanel panel_DataError() {
		
		JPanel panelDataError = new JPanel();
		FlowLayout fl_panelDataError = (FlowLayout) panelDataError.getLayout();
		fl_panelDataError.setAlignment(FlowLayout.LEFT);
		fl_panelDataError.setVgap(2);
		panel_Search.add(panelDataError);
		
		JLabel lbl_Year_1 = new JLabel(checkCorrectinDataInExcell_labelCheckCurentDataInExcelFiles);
		
		lbl_Year_1.setPreferredSize(new Dimension(550, 15));
		lbl_Year_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year_1.setBorder(null);
		lbl_Year_1.setAlignmentX(0.5f);
		panelDataError.add(lbl_Year_1);
		
		btn_CheckCurentDataInExcelFiles = new JButton("Search");
		panelDataError.add(btn_CheckCurentDataInExcelFiles);
		btn_CheckCurentDataInExcelFiles.setMargin(new Insets(2, 5, 2, 5));
		btn_CheckCurentDataInExcelFiles.setPreferredSize(new Dimension(110, 23));
		
		
		return panelDataError;
	}

	private JPanel panel_ColumnIn5Sheet() {
		
		JPanel panelColumnIn5Sheet = new JPanel();
		FlowLayout fl_panelColumnIn5Sheet = (FlowLayout) panelColumnIn5Sheet.getLayout();
		fl_panelColumnIn5Sheet.setAlignment(FlowLayout.LEFT);
		fl_panelColumnIn5Sheet.setVgap(2);
		panel_Search.add(panelColumnIn5Sheet);
		
		JLabel lbl_Year_1 = new JLabel(checkCorrectinDataInExcell_labelErrorColumn);
		
		lbl_Year_1.setPreferredSize(new Dimension(550, 15));
		lbl_Year_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year_1.setBorder(null);
		lbl_Year_1.setAlignmentX(0.5f);
		panelColumnIn5Sheet.add(lbl_Year_1);
		
		btn_SearchAllColumn = new JButton("Search");
		panelColumnIn5Sheet.add(btn_SearchAllColumn);
		btn_SearchAllColumn.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchAllColumn.setPreferredSize(new Dimension(110, 23));
		
		
		return panelColumnIn5Sheet;
	}
		
	private JPanel panel_MonthError() {
			
		JPanel panelMonthError = new JPanel();
		FlowLayout fl_panelMonthError = (FlowLayout) panelMonthError.getLayout();
		fl_panelMonthError.setAlignment(FlowLayout.LEFT);
		fl_panelMonthError.setVgap(2);
		panel_Search.add(panelMonthError);

		JLabel lbl_Year = new JLabel(checkCorrectinDataInExcell_labelErrorExcell);
				
		lbl_Year.setPreferredSize(new Dimension(550, 15));
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panelMonthError.add(lbl_Year);
		
				btn_Search = new JButton("Search");
				panelMonthError.add(btn_Search);
				btn_Search.setMargin(new Insets(2, 5, 2, 5));
				btn_Search.setPreferredSize(new Dimension(110, 23));
				

		return panelMonthError;
	}

	private JPanel panel_OtdelPerson() {
		
		JPanel panelOtdelPerson = new JPanel();
		FlowLayout fl_panelOtdelPerson = (FlowLayout) panelOtdelPerson.getLayout();
		fl_panelOtdelPerson.setAlignment(FlowLayout.LEFT);
		fl_panelOtdelPerson.setVgap(2);
		panel_Search.add(panelOtdelPerson);

		JLabel lbl_CheckPersonStatus = new JLabel(CheckPersonStatus_labelError);
				
		lbl_CheckPersonStatus.setPreferredSize(new Dimension(550, 15));
		lbl_CheckPersonStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_CheckPersonStatus.setBorder(null);
		lbl_CheckPersonStatus.setAlignmentX(0.5f);
		panelOtdelPerson.add(lbl_CheckPersonStatus);
		
				btn_Search_CheckPersonStatus = new JButton("Search");
				panelOtdelPerson.add(btn_Search_CheckPersonStatus);
				btn_Search_CheckPersonStatus.setMargin(new Insets(2, 5, 2, 5));
				btn_Search_CheckPersonStatus.setPreferredSize(new Dimension(110, 23));
				

		return panelOtdelPerson;
	}

	private JPanel panel_KodeAndNameKD() {
		
		JPanel panelKodeAndNameKD = new JPanel();
		FlowLayout fl_panelKodeAndNameKD = (FlowLayout) panelKodeAndNameKD.getLayout();
		fl_panelKodeAndNameKD.setAlignment(FlowLayout.LEFT);
		fl_panelKodeAndNameKD.setVgap(2);
		panel_Search.add(panelKodeAndNameKD);

		JLabel lbl_KodeAndNameKD = new JLabel(KodeAndNameKD_labelError);
				
		lbl_KodeAndNameKD.setPreferredSize(new Dimension(550, 15));
		lbl_KodeAndNameKD.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_KodeAndNameKD.setBorder(null);
		lbl_KodeAndNameKD.setAlignmentX(0.5f);
		panelKodeAndNameKD.add(lbl_KodeAndNameKD);
		
				btn_Search_KodeAndNameKD = new JButton("Search");
				panelKodeAndNameKD.add(btn_Search_KodeAndNameKD);
				btn_Search_KodeAndNameKD.setMargin(new Insets(2, 5, 2, 5));
				btn_Search_KodeAndNameKD.setPreferredSize(new Dimension(110, 23));
				

		return panelKodeAndNameKD;
	}

	
	
	private JPanel panel_MeasurDBInMonth() {
		
		JPanel panelMeasurDBInMonth = new JPanel();
		FlowLayout fl_panelMeasurDBInMonth = (FlowLayout) panelMeasurDBInMonth.getLayout();
		fl_panelMeasurDBInMonth.setAlignment(FlowLayout.LEFT);
		fl_panelMeasurDBInMonth.setVgap(2);
		panel_Search.add(panelMeasurDBInMonth);

		JLabel lbl_Year = new JLabel(checkCorrectinDataInExcell_labelCheckDBaseToMouthFile);
				
		lbl_Year.setPreferredSize(new Dimension(515, 15));
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panelMeasurDBInMonth.add(lbl_Year);
		
		btn_CheckDBase = new JButton("Search");
		panelMeasurDBInMonth.add(btn_CheckDBase);
		btn_CheckDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_CheckDBase.setPreferredSize(new Dimension(70, 23));
		
		btn_CheckDBase_Clear = new JButton("Clear");
		btn_CheckDBase_Clear.setEnabled(false);
		btn_CheckDBase_Clear.setPreferredSize(new Dimension(70, 23));
		btn_CheckDBase_Clear.setMargin(new Insets(2, 5, 2, 5));
		panelMeasurDBInMonth.add(btn_CheckDBase_Clear);
				

		return panelMeasurDBInMonth;
	}

	private JPanel panel_KodeAndNameerror() {
		
		JPanel panelKodeAndNameerror = new JPanel();
		FlowLayout fl_panelKodeAndNameerror = (FlowLayout) panelKodeAndNameerror.getLayout();
		fl_panelKodeAndNameerror.setAlignment(FlowLayout.LEFT);
		fl_panelKodeAndNameerror.setVgap(2);
		panel_Search.add(panelKodeAndNameerror);

		JLabel lbl_Year = new JLabel(checkCorrectinDataInExcell_labelCheckName_KodestatDBaseToMouthFile);
				
		lbl_Year.setPreferredSize(new Dimension(515, 15));
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panelKodeAndNameerror.add(lbl_Year);
		
		btn_CheckDBaseNameKodeStat = new JButton("Search");
		panelKodeAndNameerror.add(btn_CheckDBaseNameKodeStat);
		btn_CheckDBaseNameKodeStat.setMargin(new Insets(2, 5, 2, 5));
		btn_CheckDBaseNameKodeStat.setPreferredSize(new Dimension(70, 23));
		
		btn_CheckDBaseNameKodeStat_Clear = new JButton("Clear");
		btn_CheckDBaseNameKodeStat_Clear.setEnabled(false);
		btn_CheckDBaseNameKodeStat_Clear.setPreferredSize(new Dimension(70, 23));
		btn_CheckDBaseNameKodeStat_Clear.setMargin(new Insets(2, 5, 2, 5));
		panelKodeAndNameerror.add(btn_CheckDBaseNameKodeStat_Clear);
				

		return panelKodeAndNameerror;
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
			list.add(person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}

	protected String createStringToInfoPanel(List<Person> listSelectionPerson) {
		String str = "";
		for (Person person : listSelectionPerson) {
			str = str + person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person) + "\n";
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



	public static JButton getBtn_CheckDBaseNameKodeStat() {
		return btn_CheckDBaseNameKodeStat;
	}



	public static JButton getBtn_CheckDBaseNameKodeStat_Clear() {
		return btn_CheckDBaseNameKodeStat_Clear;
	}



	public static void setBtn_CheckDBaseNameKodeStat(JButton btn_CheckDBaseNameKodeStat) {
		CheckErrorDataInExcellFiles_Frame.btn_CheckDBaseNameKodeStat = btn_CheckDBaseNameKodeStat;
	}



	public static void setBtn_CheckDBaseNameKodeStat_Clear(JButton btn_CheckDBaseNameKodeStat_Clear) {
		CheckErrorDataInExcellFiles_Frame.btn_CheckDBaseNameKodeStat_Clear = btn_CheckDBaseNameKodeStat_Clear;
	}



	public static JButton getBtn_CheckCurentDataInExcelFiles() {
		return btn_CheckCurentDataInExcelFiles;
	}



	public static void setBtn_CheckCurentDataInExcelFiles(JButton btn_CheckCurentDataInExcelFiles) {
		CheckErrorDataInExcellFiles_Frame.btn_CheckCurentDataInExcelFiles = btn_CheckCurentDataInExcelFiles;
	}

	public static List<String> getListMoveEGN() {
		return listMoveEGN;
	}

	public static void setListMoveEGN(List<String> listMoveEGN_In) {
		listMoveEGN = listMoveEGN_In;
	}

	
	
	
	
	
}
