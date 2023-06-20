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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasicClassAccessDbase.Person;
import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonReferenceFrame;
import Reference_PersonMeasur.Reference_PersonMeasur_Metods;
import java.awt.Component;

public class CheckErrorDataInExcellFiles_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;

	private static JTextArea textArea;
	private static JButton btn_Search;
	private static JButton btn_Search_1;
	



	private static JButton btn_Export;

	static String curentYear = AplicationMetods.getCurentYear();
	String checkCorrectinDataInExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell");
	String checkCorrectinDataInExcell_labelErrorColumn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorColumn");
	String checkCorrectinDataInExcell_labelErrorExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_labelErrorExcell");
	
	
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

		panel_Button();

		setSize(737, 900);
		setLocationRelativeTo(null);

		

		CheckErrorDataInExcellFiles_Methods.ActionListener_Btn_SearchError(panel_Search);
		CheckErrorDataInExcellFiles_Methods.ActionListener_Btn_SearchAllColumn(panel_Search);

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
		
		btn_Search_1 = new JButton("Search");
		panel1.add(btn_Search_1);
		btn_Search_1.setMargin(new Insets(2, 5, 2, 5));
		btn_Search_1.setPreferredSize(new Dimension(110, 23));
		
		
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
		return btn_Search_1;
	}

	public static JButton getBtn_Export() {
		return btn_Export;
	}

	
}
