package DeleteDataFromDBaseRemoveInCurenFromOldYear;

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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.Person;
import CheckErrorDataInExcellFiles.CheckCurentDataInExcelFilesMetod;
import CheckErrorDataInExcellFiles.CheckDataBethwinExcelFilesAndMonth;
import CheckErrorDataInExcellFiles.CheckEqualsForFirst5Column;
import CheckErrorDataInExcellFiles.CheckErrorDataInExcellFiles_Frame;
import CheckErrorDataInExcellFiles.CheckMeasurDBaseToMounthFile;
import CheckErrorDataInExcellFiles.CheckPersonName_KodeStatus_DBseToExcelFiles;
import CheckErrorDataInExcellFiles.CheckPersonStatus;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;

public class DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;

	private static JTextArea textArea;
	
	private static JButton btn_delete_PersonStatus;
	private static JButton btn_deleteSpisak_Prilogenia;
	private static JButton btn_delete_PersonWithoutExcelFile;
	



	private static JButton btn_Export;

	static String curentYear = AplicationMetods.getCurentYear();
	String CheckInfoDBaseToExcelFiles = ReadFileBGTextVariable.getGlobalTextVariableMap().get("deleteDataFromDBaseRemoveInCurenYearFromOldYear");
	
	String CheckInfoDBaseToExcelFiles_deleteSpisak_PrilogeniaFromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("CheckInfoDBaseToExcelFiles_deleteSpisak_PrilogeniaFromDBase");
	String CheckInfoDBaseToExcelFiles_delete_PersonStatus_FromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("CheckInfoDBaseToExcelFiles_delete_PersonStatus_FromDBase");
	String CheckInfoDBaseToExcelFiles_delete_PersonWithoutExcelFile_FromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get( "CheckInfoDBaseToExcelFiles_delete_PersonWithoutExcelFile_FromDBase");
	
	String checkCorrectinDataInExcell_labelErrorExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorExcell");
	String checkCorrectinDataInExcell_labelCheckDBaseToMouthFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelCheckDBaseToMouthFile");
	String checkCorrectinDataInExcell_labelCheckName_KodestatDBaseToMouthFile = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelCheckName_KodestatDBaseToMouthFile");
	String CheckPersonStatus_labelError = ReadFileBGTextVariable.getGlobalTextVariableMap().get("CheckPersonStatus_labelError");
	
	
	private JProgressBar progressBar;
	

	
	
	public DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame() {
		setTitle(CheckInfoDBaseToExcelFiles);

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		
		setMinimumSize(new Dimension(750, 300));

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
		
		panelA2();
		panelA0();
		panelA1();
		
		
		panel_Button();

		progressBar = new JProgressBar(0,100);
		 progressBar.setValue(0);
	     progressBar.setStringPainted(true);
		panel_Search.add(progressBar);
		
		 
		setSize(750, 300);
		setLocationRelativeTo(null);

		

//		CheckDataBethwinExcelFilesAndMonth.ActionListener_Btn_SearchError(panel_Search, btn_Search, textArea, progressBar);
//		CheckEqualsForFirst5Column.ActionListener_Btn_SearchAllColumn(panel_Search, btn_SearchAllColumn, textArea);
//		CheckMeasurDBaseToMounthFile.ActionListener_Btn_CheckDBaseToMounthFile(panel_Search, btn_CheckDBase, textArea);
//		CheckMeasurDBaseToMounthFile.ActionListener_Btn_CheckDBase_Clear(panel_Search, btn_CheckDBase_Clear, textArea);
//		CheckPersonName_KodeStatus_DBseToExcelFiles.ActionListener_Btn_CheckDBaseNameKodeStat(panel_Search, btn_CheckDBaseNameKodeStat, textArea, progressBar);
//		CheckPersonName_KodeStatus_DBseToExcelFiles.ActionListener_Btn_CheckDBaseNameKodeStat_Clear(panel_Search, btn_CheckDBaseNameKodeStat_Clear, textArea);
		
		getMasiveFromOriginalExcelFile.ActionListener_Btn_deleteSpisakPrilojenia(panel_Search, btn_deleteSpisak_Prilogenia, textArea, progressBar, curentYear);
		getMasiveFromOriginalExcelFile.ActionListener_Btn_deletePersonStatus(panel_Search, btn_delete_PersonStatus, textArea, progressBar, curentYear);
		getMasiveFromOriginalExcelFile.ActionListener_Btn_delete_PersonWithoutExcelFile(panel_Search, btn_delete_PersonWithoutExcelFile, textArea, progressBar, curentYear);
		
		setVisible(true);
		
					
	}
	
	public void updateBar(int newValue) {
		progressBar.setValue(newValue);
	  }

	private JPanel panelA0() {
		
		JPanel panelA0 = new JPanel();
		FlowLayout fl_panelA0 = (FlowLayout) panelA0.getLayout();
		fl_panelA0.setAlignment(FlowLayout.LEFT);
		fl_panelA0.setVgap(2);
		panel_Search.add(panelA0);
		
		JLabel lbl_Year_1 = new JLabel(CheckInfoDBaseToExcelFiles_deleteSpisak_PrilogeniaFromDBase);
		
		lbl_Year_1.setPreferredSize(new Dimension(600, 15));
		lbl_Year_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year_1.setBorder(null);
		lbl_Year_1.setAlignmentX(0.5f);
		panelA0.add(lbl_Year_1);
		
		btn_deleteSpisak_Prilogenia = new JButton("Старт");
		panelA0.add(btn_deleteSpisak_Prilogenia);
		btn_deleteSpisak_Prilogenia.setMargin(new Insets(2, 5, 2, 5));
		btn_deleteSpisak_Prilogenia.setPreferredSize(new Dimension(110, 23));
		
		
		return panelA0;
	}

	private JPanel panelA1() {
		
		JPanel panelA1 = new JPanel();
		FlowLayout fl_panelA1 = (FlowLayout) panelA1.getLayout();
		fl_panelA1.setAlignment(FlowLayout.LEFT);
		fl_panelA1.setVgap(2);
		panel_Search.add(panelA1);
		
		JLabel lbl_Year_1 = new JLabel(CheckInfoDBaseToExcelFiles_delete_PersonStatus_FromDBase);
		
		lbl_Year_1.setPreferredSize(new Dimension(600, 15));
		lbl_Year_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year_1.setBorder(null);
		lbl_Year_1.setAlignmentX(0.5f);
		panelA1.add(lbl_Year_1);
		
		btn_delete_PersonStatus = new JButton("Старт");
		panelA1.add(btn_delete_PersonStatus);
		btn_delete_PersonStatus.setMargin(new Insets(2, 5, 2, 5));
		btn_delete_PersonStatus.setPreferredSize(new Dimension(110, 23));
		
		
		return panelA1;
	}
		
	private JPanel panelA2() {
		
		JPanel panelA2 = new JPanel();
		FlowLayout fl_panelA2 = (FlowLayout) panelA2.getLayout();
		fl_panelA2.setAlignment(FlowLayout.LEFT);
		fl_panelA2.setVgap(2);
		panel_Search.add(panelA2);
		
		JLabel lbl_Year_1 = new JLabel(CheckInfoDBaseToExcelFiles_delete_PersonWithoutExcelFile_FromDBase);
		lbl_Year_1.setMaximumSize(new Dimension(700, 14));
		
		lbl_Year_1.setPreferredSize(new Dimension(600, 15));
		lbl_Year_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year_1.setBorder(null);
		lbl_Year_1.setAlignmentX(0.5f);
		panelA2.add(lbl_Year_1);
		
		btn_delete_PersonWithoutExcelFile = new JButton("Старт");
		panelA2.add(btn_delete_PersonWithoutExcelFile);
		btn_delete_PersonWithoutExcelFile.setMargin(new Insets(2, 5, 2, 5));
		btn_delete_PersonWithoutExcelFile.setPreferredSize(new Dimension(110, 23));
		
		
		return panelA2;
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

		
	public static JButton getBtn_delete_PersonStatus() {
		return btn_delete_PersonStatus;
	}

	public static JButton getBtn_Export() {
		return btn_Export;
	}


	public static JButton getBtn_CheckCurentDataInExcelFiles() {
		return btn_deleteSpisak_Prilogenia;
	}



	public static void setBtn_CheckCurentDataInExcelFiles(JButton btn_CheckCurentDataInExcelFiles) {
		DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame.btn_deleteSpisak_Prilogenia = btn_CheckCurentDataInExcelFiles;
	}

	
	
	
	
	
}
