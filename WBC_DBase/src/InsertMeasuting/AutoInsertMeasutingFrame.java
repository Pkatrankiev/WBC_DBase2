package InsertMeasuting;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.UsersWBC;
import DozeArt.DozeArtFrame;

import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("rawtypes")
public class AutoInsertMeasutingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JPanel panelBasic;

	static Frame frame;
	String clickTxt = ReadFileBGTextVariable.getGlobalTextVariableMap().get("klikToCopy");
	String labelFileNameToolTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelFileNameToolTipText");
	String autoInsertMeasuting = ReadFileBGTextVariable.getGlobalTextVariableMap().get("autoInsertMeasuting");
	static JButton btnSave;
	Color panelColor;
	private JButton btn_addMeasur;
	private static boolean manualInsertMeasur;
	private static List<Person> listAllPerson;
	static String[] listSimbolNuclide;
	static String[] listLaboratiry;
	static String[] listUserWBC;
	static String[] listTypeMeasur;
	static String[] listNameTypeMeasur;

	static JTextField[] textFieldDoza;
	static JTextField[] textFieldKoment;
	static JLabel[] lblNumber;
	static JLabel[] lblFileName;
	static JLabel[] lblDate;
	static JLabel[] lblPersonName;
	static JLabel[] lblEGN;

	static JComboBox[] comboBoxLab;
	static JComboBox[] comboBoxOperator;
	static JComboBox[] comboBoxTypeMeasur;
	static JCheckBox[] chckbxSetToExcel;
	static JButton[] btn_MinusNuclide;
	static JButton[] btn_PlusNuclide;
	static JButton[] btnCalc;

	static JComboBox[][] comboBox_Nuclide;
	static JTextField[][] textField_GGP;
	static JTextField[][] textField_Actyvity;
	static JTextField[][] textField_Postaplenie;
	static JTextField[][] textField_DozeNuclide;

	static List<ReportMeasurClass> listReportMeasurClass;
	static List<UsersWBC> listUsersWBC;
	static DimensionWBC dozeDimension;
	static int countMeasur;
	

	public AutoInsertMeasutingFrame(ActionIcone round, Frame f, List<ReportMeasurClass> listReportMeasur,
			String[] listSimbolNuclideIN, String[] listLaboratiryIN, String[] listUserWBCIN, String[] listTypeMeasurIN,
			String[] listNameTypeMeasurIN, Point pointFrame, List<Person> listAllPersonIN, boolean manualInsertMeasurIN) {
		setTitle(autoInsertMeasuting);

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));

//		setResizable(false);
		int numberLine = 0;
		int y = 220, x = 1200;
		int numberMeasuring = 1;
		String lastDate = "";
		manualInsertMeasur = manualInsertMeasurIN;
		listAllPerson = listAllPersonIN;
		listUsersWBC = UsersWBCDAO.getAllValueUsersWBC();
		dozeDimension = DimensionWBCDAO.getValueDimensionWBCByID(2);
		frame = f;
		listSimbolNuclide = listSimbolNuclideIN;
		listLaboratiry = listLaboratiryIN;
		listUserWBC = listUserWBCIN;
		listTypeMeasur = listTypeMeasurIN;
		listNameTypeMeasur = listNameTypeMeasurIN;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		listReportMeasurClass = listReportMeasur;
		countMeasur = listReportMeasurClass.size();
		JPanel[] panel_Multy = new JPanel[countMeasur];
		JPanel[][] panel_Multy_Nuclide = new JPanel[countMeasur][20];
		btnCalc = new JButton[countMeasur];

		lblNumber = new JLabel[countMeasur];
		lblFileName = new JLabel[countMeasur];
		lblDate = new JLabel[countMeasur];
		lblPersonName = new JLabel[countMeasur];
		lblEGN = new JLabel[countMeasur];
		comboBoxLab = new JComboBox[countMeasur];
		comboBoxOperator = new JComboBox[countMeasur];
		comboBoxTypeMeasur = new JComboBox[countMeasur];
		chckbxSetToExcel = new JCheckBox[countMeasur];
		textFieldDoza = new JTextField[countMeasur];
		textFieldKoment = new JTextField[countMeasur];
		btn_MinusNuclide = new JButton[countMeasur];
		btn_PlusNuclide = new JButton[countMeasur];

		comboBox_Nuclide = new JComboBox[countMeasur][20];
		textField_GGP = new JTextField[countMeasur][20];
		textField_Actyvity = new JTextField[countMeasur][20];
		textField_Postaplenie = new JTextField[countMeasur][20];
		textField_DozeNuclide = new JTextField[countMeasur][20];
		for (int i = 0; i < countMeasur; i++) {
			for (int j = 0; j < 20; j++) {
				textField_DozeNuclide[i][j] = new JTextField();

			}
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panelHeader();

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		panelBasic = new JPanel();
		scrollPane.setViewportView(panelBasic);
		panelBasic.setLayout(new BoxLayout(panelBasic, BoxLayout.Y_AXIS));
		panelColor = panelBasic.getBackground();
//		JPanel panel_1 = panelMultyMeasuring(0, "", "01.01.2000", "tetst tetst tetsts", "123123123", "WBC 2","Виктор Младенов", "R",
//				"", true, "", null, 1);
//		panelBasic.add(panel_1);
		for (int i = 0; i < countMeasur; i++) {
			ReportMeasurClass reportMeasur = listReportMeasurClass.get(i);
			String reportName = reportMeasur.getMeasur().getReportFileName();
			String date = sdf.format(reportMeasur.getMeasur().getDate());
			String PersonName = reportMeasur.getMeasur().getPerson().getFirstName() + " "
					+ reportMeasur.getMeasur().getPerson().getSecondName() + " "
					+ reportMeasur.getMeasur().getPerson().getLastName();
			String egn = reportMeasur.getMeasur().getPerson().getEgn();
			String lab = reportMeasur.getMeasur().getLab().getLab();
			String operatorNmae = reportMeasur.getMeasur().getUser().getName() + " "
					+ reportMeasur.getMeasur().getUser().getLastName();
			String typeMeasur = reportMeasur.getMeasur().getTypeMeasur().getKodeType();
			boolean toExcell = reportMeasur.getToExcell();
			String koment = reportMeasur.getKoment();

			String doze = DoubleToString(reportMeasur.getMeasur().getDoze());
			// 999999 - absurdna doza imitirasta prazna kletka za propuskane na tozi red za
			// zapis v bazata
			if (doze.equals("999999")) {
				doze = "";
			}

			if (!lastDate.equals(date)) {
				lastDate = date;
				numberMeasuring = 1;
			}
			panel_Multy[i] = panelMultyMeasuring(i, reportName, date, PersonName, egn, lab, operatorNmae, typeMeasur,
					doze, toExcell, koment, reportMeasur, numberMeasuring);
			panelBasic.add(panel_Multy[i]);
			int k = 0;
			for (String string : reportMeasur.getListNuclideData()) {
				String nucl = "";
				String activ = "";
				String post = "";
				String ggp = "";
				String doz = "";
				if (string.startsWith("##")) {
					String[] value = StringUtils.split(string, ":");
					nucl = value[1].trim();
					activ = value[2].trim();
					post = value[3].trim();
					ggp = value[4].trim();
					doz = value[5].trim();
				} else {
					String[] value = StringUtils.split(string);
					
					for (String str : value) {
						System.out.print(" ------ "+str);
					}
					
					
					
					if (lab.contains("2")) {
						nucl = value[0].trim();
						activ = value[1].trim()+" ("+ value[2].trim()+")";
					} else {

						nucl = value[0].trim();
						activ = value[2].trim()+"("+ value[3].trim()+")";
					}
				}
				System.out.println(i + " " + k + " " + nucl + " " + activ + " " + post + " " + ggp + " " + doz);

				panel_Multy_Nuclide[i][k] = panelMultyNuclideMeasuring(i, k, nucl, activ, post, ggp, doz);
				panelBasic.add(panel_Multy_Nuclide[i][k]);
				k++;
				numberLine++;
			}
			numberLine++;
			numberMeasuring++;
		}

		panelcheckAll();
		panelButtons();

		y = y + numberLine * 30;

		if (y > 1000) {
			y = 1000;
			x = x + 30;
		}

		setSize(x, y);
		setLocationRelativeTo(null);

		if (pointFrame != null) {
			setLocation((int) pointFrame.getX(), (int) pointFrame.getY());
		}

		
	
		round.StopWindow();
		setVisible(true);
		
		if(manualInsertMeasur) {
			btn_addMeasur.requestFocusInWindow();
		}
	}

	private JPanel panelButtons() {

		String autoInsertMeasuting_cancel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_cancel");
		String autoInsertMeasuting_save = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_save");

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);

		JPanel panelButtons = new JPanel();
		panelButtons.setAlignmentX(0.0f);
		panel.add(panelButtons);
		panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		if(manualInsertMeasur) {
		btn_addMeasur = new JButton("Добави измерване");
		btn_addMeasur.setEnabled(true);
		btn_addMeasur_ManualInsertMeasuting_ActionListener(btn_addMeasur, this, listAllPerson);
		btn_addMeasur.setAlignmentX(1.0f);
		panelButtons.add(btn_addMeasur);
		btn_addMeasur.setFocusable(true);
	
		}
		
		
		JButton btnCancel = new JButton(autoInsertMeasuting_cancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Destroy the JFrame object
			}
		});
		panelButtons.add(btnCancel);

		btnSave = new JButton(autoInsertMeasuting_save);
		btnSave.setEnabled(true);
		AutoInsertMeasutingMethods.btnSave_AutoInsertMeasuting_ActionListener(this);
		btnSave.setAlignmentX(1.0f);
		panelButtons.add(btnSave);
		return panelButtons;
	}

	private JPanel panelcheckAll() {

		String autoInsertMeasuting_CheckAll = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_CheckAll");

		JPanel panelcheckAll = new JPanel();
		panelcheckAll.setMaximumSize(new Dimension(32767, 30));
		panelcheckAll.setAlignmentY(0.0f);
		panelcheckAll.setAlignmentX(0.0f);
		panelcheckAll.setLayout(new FlowLayout(FlowLayout.RIGHT, 13, 5));
		panelBasic.add(panelcheckAll);

		JCheckBox chckbxCheckAll = new JCheckBox();
		chckbxCheckAll.setAlignmentX(Component.CENTER_ALIGNMENT);
		chckbxCheckAll.setText(autoInsertMeasuting_CheckAll);
		chckbxCheckAll.setHorizontalTextPosition(SwingConstants.LEFT);
		chckbxCheckAll.setSelected(true);
		panelcheckAll.add(chckbxCheckAll);

		chckbxCheckAll.addMouseListener(new MouseAdapter() {

			boolean selectTrue;

			public void mousePressed(MouseEvent me) {

				if (chckbxCheckAll.isSelected()) {
					selectTrue = false;
				} else {
					selectTrue = true;
				}
				for (int i = 0; i < countMeasur; i++) {
					chckbxSetToExcel[i].setSelected(selectTrue);
				}
				repaint();

			}

		});

		return panelcheckAll;
	}

	private JPanel panelHeader() {

		String autoInsertMeasuting_File = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_File");
		String referencePerson_Date = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Date");
		String referencePerson_FirstName = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("referencePerson_FirstName");
		String referencePerson_EGN = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_EGN");
		String referencePerson_Lab = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Lab");
		String autoInsertMeasuting_Operator = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Operator");
		String autoInsertMeasuting_Type = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Type");
		String autoInsertMeasuting_Doza = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Doza");
		String referencePerson_Koment = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Koment");
		String autoInsertMeasuting_NuclidePLMU = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_NuclidePLMU");
		String autoInsertMeasuting_ToExcel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_ToExcel");

		JPanel panel_Header = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_Header.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_Header.setMaximumSize(new Dimension(32767, 30));
		panel_Header.setAlignmentY(0.0f);
		contentPane.add(panel_Header, BorderLayout.NORTH);

		JLabel lbl_L_Number = new JLabel("№");
		lbl_L_Number.setSize(new Dimension(20, 20));
		lbl_L_Number.setPreferredSize(new Dimension(20, 20));
		lbl_L_Number.setMinimumSize(new Dimension(20, 20));
		lbl_L_Number.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Number.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Number.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Number);

		JLabel lbl_L_File = new JLabel(autoInsertMeasuting_File);
		lbl_L_File.setSize(new Dimension(80, 20));
		lbl_L_File.setPreferredSize(new Dimension(80, 20));
		lbl_L_File.setMinimumSize(new Dimension(80, 20));
		lbl_L_File.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_File.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_File.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_File);

		JLabel lbl_L_Date = new JLabel(referencePerson_Date);
		lbl_L_Date.setSize(new Dimension(60, 20));
		lbl_L_Date.setPreferredSize(new Dimension(60, 20));
		lbl_L_Date.setMinimumSize(new Dimension(60, 20));
		lbl_L_Date.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Date.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Date.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Date);

		JLabel lbl_L_PersonName = new JLabel(referencePerson_FirstName);
		lbl_L_PersonName.setSize(new Dimension(220, 20));
		lbl_L_PersonName.setPreferredSize(new Dimension(220, 20));
		lbl_L_PersonName.setMinimumSize(new Dimension(220, 20));
		lbl_L_PersonName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_PersonName.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_PersonName.setAlignmentX(0.5f);
		panel_Header.add(lbl_L_PersonName);

		JLabel lbl_L_EGN = new JLabel(referencePerson_EGN);
		lbl_L_EGN.setSize(new Dimension(70, 20));
		lbl_L_EGN.setPreferredSize(new Dimension(70, 20));
		lbl_L_EGN.setMinimumSize(new Dimension(70, 20));
		lbl_L_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_EGN.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_EGN.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_EGN);

		JLabel lbl_L_Lab = new JLabel(referencePerson_Lab);
		lbl_L_Lab.setSize(new Dimension(70, 20));
		lbl_L_Lab.setPreferredSize(new Dimension(70, 20));
		lbl_L_Lab.setMinimumSize(new Dimension(70, 20));
		lbl_L_Lab.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Lab.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Lab.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Lab);

		JLabel lbl_L_OperatorName = new JLabel(autoInsertMeasuting_Operator);
		lbl_L_OperatorName.setSize(new Dimension(130, 20));
		lbl_L_OperatorName.setPreferredSize(new Dimension(130, 20));
		lbl_L_OperatorName.setMinimumSize(new Dimension(130, 20));
		lbl_L_OperatorName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_OperatorName.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_OperatorName.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_OperatorName);

		JLabel lbl_L_TypeMeasur = new JLabel(autoInsertMeasuting_Type);
		lbl_L_TypeMeasur.setSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setPreferredSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setMinimumSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_TypeMeasur.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_TypeMeasur.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_TypeMeasur);

		JLabel lbl_L_Doze = new JLabel(autoInsertMeasuting_Doza);
		lbl_L_Doze.setSize(new Dimension(75, 20));
		lbl_L_Doze.setPreferredSize(new Dimension(75, 20));
		lbl_L_Doze.setMinimumSize(new Dimension(75, 20));
		lbl_L_Doze.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Doze.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Doze.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Doze);

		JLabel lbl_L_Koment = new JLabel(referencePerson_Koment);
		lbl_L_Koment.setSize(new Dimension(245, 20));
		lbl_L_Koment.setPreferredSize(new Dimension(245, 20));
		lbl_L_Koment.setMinimumSize(new Dimension(245, 20));
		lbl_L_Koment.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Koment.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Koment.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Koment);

		JLabel lbl_L_AddNuclide = new JLabel(autoInsertMeasuting_NuclidePLMU);
		lbl_L_AddNuclide.setSize(new Dimension(60, 20));
		lbl_L_AddNuclide.setPreferredSize(new Dimension(55, 20));
		lbl_L_AddNuclide.setMinimumSize(new Dimension(60, 20));
		lbl_L_AddNuclide.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_AddNuclide.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_AddNuclide.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_AddNuclide);

		JLabel lbl_L_SetToExcel = new JLabel(autoInsertMeasuting_ToExcel);
		lbl_L_SetToExcel.setSize(new Dimension(40, 20));
		lbl_L_SetToExcel.setPreferredSize(new Dimension(40, 20));
		lbl_L_SetToExcel.setMinimumSize(new Dimension(40, 20));
		lbl_L_SetToExcel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_SetToExcel.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_SetToExcel.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_SetToExcel);

		return panel_Header;
	}

	@SuppressWarnings("unchecked")
	private JPanel panelMultyMeasuring(int index, String reportName, String date, String PersonName, String egn,
			String lab, String operatorNmae, String typeMeasur, String doze, boolean toExcell, String koment,
			ReportMeasurClass reportMeasur, int numberMeasuring) {
		JPanel panel_Multy = new JPanel();
		panel_Multy.setMaximumSize(new Dimension(32767, 30));
		panel_Multy.setAlignmentY(0.0f);
		panel_Multy.setAlignmentX(0.0f);

		panel_Multy.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblNumber[index] = new JLabel(numberMeasuring + "");
		lblNumber[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblNumber[index].setPreferredSize(new Dimension(20, 20));
		panel_Multy.add(lblNumber[index]);

		lblFileName[index] = new JLabel(reportName);
		lblFileName[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblFileName[index].setPreferredSize(new Dimension(80, 20));
		panel_Multy.add(lblFileName[index]);
		lblFileName[index].setToolTipText(clickTxt);
		startFileInNotepad(lblFileName[index]);

		lblDate[index] = new JLabel(date);
		lblDate[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblDate[index].setPreferredSize(new Dimension(60, 20));
		panel_Multy.add(lblDate[index]);
		lblDate[index].setToolTipText(clickTxt);
		copyToClipboard(lblDate[index]);

		lblPersonName[index] = new JLabel(PersonName);
		lblPersonName[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblPersonName[index].setPreferredSize(new Dimension(220, 20));
		panel_Multy.add(lblPersonName[index]);
		lblPersonName[index].setToolTipText(clickTxt);
		copyToClipboard(lblPersonName[index]);

		lblEGN[index] = new JLabel(egn);
		lblEGN[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblEGN[index].setPreferredSize(new Dimension(70, 20));
		panel_Multy.add(lblEGN[index]);
		lblEGN[index].setToolTipText(clickTxt);
		copyToClipboard(lblEGN[index]);

		comboBoxLab[index] = new JComboBox(listLaboratiry);
		comboBoxLab[index].setPreferredSize(new Dimension(70, 20));
		comboBoxLab[index].setSelectedItem(lab);
		panel_Multy.add(comboBoxLab[index]);

		comboBoxOperator[index] = new JComboBox(listUserWBC);
		comboBoxOperator[index].setPreferredSize(new Dimension(130, 20));
		comboBoxOperator[index].setSelectedItem(operatorNmae);
		panel_Multy.add(comboBoxOperator[index]);

		comboBoxTypeMeasur[index] = new JComboBox(listTypeMeasur);
		comboBoxTypeMeasur[index].setPreferredSize(new Dimension(40, 20));
		comboBoxTypeMeasur[index].setSelectedItem(typeMeasur);
		panel_Multy.add(comboBoxTypeMeasur[index]);
		comboBoxTypeMeasur[index].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				String sst = comboBoxTypeMeasur[index].getSelectedItem().toString();
				comboBoxTypeMeasur[index].setToolTipText(getNameTapeMeasur(sst));
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

			}

		});

		textFieldDoza[index] = new JTextField(doze);
		textFieldDoza[index].setColumns(4);
		panel_Multy.add(textFieldDoza[index]);

		textFieldDoza[index].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {
			}

			@Override
			public void keyReleased(KeyEvent event) {
				textFieldDoza[index].setBackground(Color.WHITE);
				textFieldDoza[index].setText(checkFormatString(textFieldDoza[index].getText()));
			}

			@Override
			public void keyPressed(KeyEvent event) {
			}
		});

		JLabel lblDozeDim = new JLabel("mSv");
		lblDozeDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy.add(lblDozeDim);

		textFieldKoment[index] = new JTextField(koment);
		textFieldKoment[index].setColumns(30);
		panel_Multy.add(textFieldKoment[index]);

		btn_MinusNuclide[index] = new JButton("-");
		btn_MinusNuclide[index].setPreferredSize(new Dimension(25, 20));
		btn_MinusNuclide[index].setHorizontalAlignment(SwingConstants.CENTER);
		btn_MinusNuclide[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		btn_MinusNuclide[index].setAlignmentX(1.0f);
		panel_Multy.add(btn_MinusNuclide[index]);

		btn_MinusNuclide[index].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionListenerMinusBTN(index);
			}
		});

		btn_PlusNuclide[index] = new JButton("+");
		btn_PlusNuclide[index].setPreferredSize(new Dimension(25, 20));
		btn_PlusNuclide[index].setHorizontalAlignment(SwingConstants.CENTER);
		btn_PlusNuclide[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		btn_PlusNuclide[index].setAlignmentX(1.0f);
		panel_Multy.add(btn_PlusNuclide[index]);
		btn_PlusNuclide[index].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionListenerPlusBTN(index);
			}
		});

		chckbxSetToExcel[index] = new JCheckBox();
		chckbxSetToExcel[index].setSelected(toExcell);
		panel_Multy.add(chckbxSetToExcel[index]);
		chckbxSetToExcel[index].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				chckbxSetToExcel[index].setBackground(panelColor);
			}

		});

		return panel_Multy;
	}

	@SuppressWarnings("unchecked")
	private JPanel panelMultyNuclideMeasuring(int index, int subIndex, String nucl, String activ, String post,
			String ggp, String doz) {

		String referencePerson_Nuclid = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Nuclid");
		String autoInsertMeasuting_Activity = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Activity");
		String autoInsertMeasuting_Postaplenie = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Postaplenie");
		String autoInsertMeasuting_GGP = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_GGP");
		String autoInsertMeasuting_Doza = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Doza");
		String autoInsertMeasuting_calc = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_calc");

		JPanel panel_Multy_Nuclide = new JPanel();
		panel_Multy_Nuclide.setMaximumSize(new Dimension(32767, 30));
		panel_Multy_Nuclide.setAlignmentY(0.0f);
		panel_Multy_Nuclide.setAlignmentX(0.0f);
		panel_Multy_Nuclide.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblNuclide = new JLabel(referencePerson_Nuclid);
		lblNuclide.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNuclide.setPreferredSize(new Dimension(40, 20));
		panel_Multy_Nuclide.add(lblNuclide);

		comboBox_Nuclide[index][subIndex] = new JComboBox(listSimbolNuclide);
		comboBox_Nuclide[index][subIndex].setPreferredSize(new Dimension(80, 20));
		comboBox_Nuclide[index][subIndex].setSelectedItem(nucl);
		panel_Multy_Nuclide.add(comboBox_Nuclide[index][subIndex]);
		comboBox_Nuclide[index][subIndex].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cneckNuclideChoise(index, subIndex);

			}

		});

		JLabel lblActyvity = new JLabel(autoInsertMeasuting_Activity);
		lblActyvity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblActyvity.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblActyvity.setPreferredSize(new Dimension(50, 20));
		panel_Multy_Nuclide.add(lblActyvity);

		textField_Actyvity[index][subIndex] = new JTextField();
		textField_Actyvity[index][subIndex].setText(activ);
		textField_Actyvity[index][subIndex].setColumns(12);
		panel_Multy_Nuclide.add(textField_Actyvity[index][subIndex]);
		textField_Actyvity[index][subIndex].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {
				textField_Actyvity[index][subIndex]
						.setText(checkFormatString(textField_Actyvity[index][subIndex].getText()));
				textField_Actyvity[index][subIndex].setBackground(Color.WHITE);
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblActyvityDimen = new JLabel("Bq");
		lblActyvityDimen.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblActyvityDimen);

		JLabel lblPostaplenie = new JLabel(autoInsertMeasuting_Postaplenie);
		lblPostaplenie.setPreferredSize(new Dimension(60, 20));
		panel_Multy_Nuclide.add(lblPostaplenie);

		textField_Postaplenie[index][subIndex] = new JTextField();
		textField_Postaplenie[index][subIndex].setText(post);
		textField_Postaplenie[index][subIndex].setColumns(4);
		panel_Multy_Nuclide.add(textField_Postaplenie[index][subIndex]);
		textField_Postaplenie[index][subIndex].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {
				textField_Postaplenie[index][subIndex]
						.setText(checkFormatString(textField_Postaplenie[index][subIndex].getText()));
				textField_Postaplenie[index][subIndex].setBackground(Color.WHITE);
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblPostaplenieDim = new JLabel("Bq");
		lblPostaplenieDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblPostaplenieDim);

		JLabel lblGGP = new JLabel(autoInsertMeasuting_GGP);
		lblGGP.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGGP.setPreferredSize(new Dimension(25, 20));
		panel_Multy_Nuclide.add(lblGGP);

		textField_GGP[index][subIndex] = new JTextField();
		textField_GGP[index][subIndex].setText(ggp);
		textField_GGP[index][subIndex].setColumns(4);
		panel_Multy_Nuclide.add(textField_GGP[index][subIndex]);
		textField_GGP[index][subIndex].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {
				textField_GGP[index][subIndex].setText(checkFormatString(textField_GGP[index][subIndex].getText()));
				textField_GGP[index][subIndex].setBackground(Color.WHITE);
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblGGPDim = new JLabel("%");
		lblGGPDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblGGPDim);

		JLabel lblDoza = new JLabel(autoInsertMeasuting_Doza);
		lblDoza.setPreferredSize(new Dimension(40, 20));
		lblDoza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDoza.setAlignmentX(1.0f);
		panel_Multy_Nuclide.add(lblDoza);

		textField_DozeNuclide[index][subIndex] = new JTextField();
		textField_DozeNuclide[index][subIndex].setText(doz);
		textField_DozeNuclide[index][subIndex].setColumns(4);
		panel_Multy_Nuclide.add(textField_DozeNuclide[index][subIndex]);

		textField_DozeNuclide[index][subIndex].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {
				updateTextFildsDozeNuclide(index, subIndex);
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblDozaNuclideDim = new JLabel("mSv");
		lblDozaNuclideDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblDozaNuclideDim);

		if (subIndex == 0) {
			btnCalc[index] = new JButton(autoInsertMeasuting_calc);
			panel_Multy_Nuclide.add(btnCalc[index]);
			btnCalc[index].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CalculateDozeMeasur(listReportMeasurClass.get(index), index);

				}

			});
		}

		return panel_Multy_Nuclide;
	}

	private void updateTextFildsDozeNuclide(int index, int subIndex) {
		textField_DozeNuclide[index][subIndex].setBackground(Color.WHITE);
		textField_DozeNuclide[index][subIndex]
				.setText(checkFormatString(textField_DozeNuclide[index][subIndex].getText()));

		double dd = 0.0;
		for (int i = 0; i < 20; i++) {
			try {
				double ddn = Double.parseDouble(textField_DozeNuclide[index][i].getText());
				dd = dd + ddn;
			} catch (NumberFormatException e) {

			}
		}
		textFieldDoza[index].setText(DoubleToString(dd));

		cneckNuclideChoise(index, subIndex);
		repaint();
	}

	private void setCalculateDataInTextFilds(int index, String[][] masive) {
		if (masive != null) {
			for (int i = 0; i < masive.length; i++) {
				comboBox_Nuclide[index][i].setSelectedItem(masive[i][0]);
				textField_Actyvity[index][i].setText(masive[i][1]);
				textField_Postaplenie[index][i].setText(masive[i][2]);
				textField_GGP[index][i].setText(masive[i][3]);
				textField_DozeNuclide[index][i].setText(masive[i][4]);
				updateTextFildsDozeNuclide(index, i);
			}
		}
	}

	private void cneckNuclideChoise(int index, int subIndex) {
		boolean fl = true;
		List<String> listNuclideData = listReportMeasurClass.get(index).getListNuclideData();
		if (listNuclideData != null) {
			if (listNuclideData.size() > 0) {
				fl = true;
				for (int i = 0; i < listNuclideData.size(); i++) {
					System.out.println(comboBox_Nuclide[index][subIndex].getSelectedItem().toString() + " <-> "
							+ comboBox_Nuclide[index][i].getSelectedItem().toString());
					if (subIndex != i) {
						if (comboBox_Nuclide[index][subIndex].getSelectedItem().toString()
								.equals(comboBox_Nuclide[index][i].getSelectedItem().toString())) {
							fl = false;
						}
					}
				}
				System.out.println(fl);
				if (fl) {
					comboBox_Nuclide[index][subIndex].setForeground(Color.BLACK);
					btnSave.setEnabled(true);

				} else {
					comboBox_Nuclide[index][subIndex].setForeground(Color.RED);
					btnSave.setEnabled(false);
				}
			}
		}
	}

	protected void CalculateDozeMeasur(ReportMeasurClass reportMeasur, int index) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String dataMeasur = sdf.format(reportMeasur.getMeasur().getDate());
		String egn = reportMeasur.getMeasur().getPerson().getEgn();

		List<String> listNuclideName = new ArrayList<>();

		for (int k = 0; k < 20; k++) {
			
			if (comboBox_Nuclide[index][k] != null) {

				listNuclideName.add(comboBox_Nuclide[index][k].getSelectedItem().toString() + "#"
						+ removeUncertainFromActiviti( textField_Actyvity[index][k].getText()));

			}

		}
		System.out.println("listNuclideName.size()" + listNuclideName.size());
		System.out.println("listNuclideName --------------------------------------------------");
		for (String strings : listNuclideName) {
			System.out.println(strings);
		}

		String[] masiveInfoPerson = getTextInfoPerson(egn, dataMeasur, listNuclideName);
		System.out.println("masiveInfoPerson --------------------------------------------------");
		for (String strings : masiveInfoPerson) {
			System.out.println(strings);
		}
		System.out.println("END masiveInfoPerson --------------------------------------------------");
		final JFrame f = new JFrame();
		DozeArtFrame ee = new DozeArtFrame(f, null, masiveInfoPerson);
		String[][] masive = ee.getDataForAutoInsertMeasur();
		if (masive != null) {
			System.out.println("DozeArtFrame --------------------------------------------------");
			setCalculateDataInTextFilds(index, masive);
		}

	}

	private String removeUncertainFromActiviti(String activitiAndUncert) {
		int index = activitiAndUncert.indexOf("(");
		if(index > 0) {
			return activitiAndUncert.substring(0,index);
		}
		return activitiAndUncert;
	}

	public static String[] getTextInfoPerson(String egn, String dateMeasur, List<String> listNuclideName) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String[] masive = new String[4 + listNuclideName.size()];
		Person person = PersonDAO.getValuePersonByEGN(egn);
		PersonStatusNew lastPersonstatusNew = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person);
		System.out.println(person.getEgn() + "  " + dateMeasur.substring(6));
		List<KodeStatus> listKodeStatus = KodeStatusDAO.getKodeStatusByPersonYear(person, dateMeasur.substring(6));

		String text = egn + " ";
		text += person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName() + " ";
		text = getTextKodeStatus(listKodeStatus, text);
		text += lastPersonstatusNew.getWorkplace().getOtdel();

		masive[0] = text;
		List<Measuring> listMeasuring = MeasuringDAO.getValueMeasuringByObjectSortByColumnName("Person_ID", person,
				"Date");

		masive[1] = dateMeasur;
		Measuring measur1 = listMeasuring.get(listMeasuring.size() - 1);
		Measuring measur2 = listMeasuring.get(listMeasuring.size() - 2);
		masive[2] = sdf.format(measur1.getDate());
		masive[3] = sdf.format(measur2.getDate());
		List<ResultsWBC> listResult1 = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur1);
		int index = 1;

		for (String nuclideName : listNuclideName) {

			System.out.println("nuclideName " + nuclideName);
			masive[3 + index] = nuclideName;

			String[] nuclideinfo = nuclideName.split("#");

			System.out.println("nuclideinfo[0] " + nuclideinfo[0]);
			for (ResultsWBC resultsWBC : listResult1) {
				System.out.println("resultsWBC.getNucl " + resultsWBC.getNuclideWBC().getSymbol_nuclide());
				if (nuclideinfo[0].equals(resultsWBC.getNuclideWBC().getSymbol_nuclide())) {
					masive[3 + index] += "#" + resultsWBC.getPostaplenie();
				}
			}
			System.out.println(index + " masive[3 + index] " + masive[3 + index]);
			index++;
		}
		return masive;
	}

	private static String getTextKodeStatus(List<KodeStatus> listKodeStatus, String text) {
		String kode = "";
		for (KodeStatus kodeStatus : listKodeStatus) {
			switch (kodeStatus.getZone().getId_Zone()) {
			case 1:
				kode = "Код зона 1: " + kodeStatus.getKode();
				break;
			case 2:
				kode = "Код зона 2: " + kodeStatus.getKode();
				break;
			case 3:
				kode = "Код зона ХОГ: " + kodeStatus.getKode();
				break;

			default:
				break;
			}
			text += kode + " ";
		}
		return text;
	}

	public static String checkFormatString(String code) {

		String newCode = code;

		System.out.println(code);

		try {

			Double.parseDouble(code);
			newCode = code.replaceAll("[dfDF ]", "");
		} catch (NumberFormatException e) {
			if (code.length() > 0) {
				newCode = code.substring(0, code.length() - 1);
			}
		}

		return newCode;
	}

	public static List<ReportMeasurClass> generateListReportMeasurClassForSaveData(int countData,
			List<ReportMeasurClass> listReportMeasur) {
		List<ReportMeasurClass> listReportMeasurToData = new ArrayList<>();
		for (int i = 0; i < countData; i++) {
			String dozze = textFieldDoza[i].getText();
			if (dozze.length() > 0) {
				ReportMeasurClass reportMeasur = new ReportMeasurClass();
				Measuring measur = new Measuring();
				measur.setReportFileName(listReportMeasur.get(i).getMeasur().getReportFileName());
				measur.setDate(listReportMeasur.get(i).getMeasur().getDate());
				measur.setPerson(listReportMeasur.get(i).getMeasur().getPerson());
				measur.setLab(
						LaboratoryDAO.getValueLaboratoryByName(comboBoxLab[i].getSelectedItem().toString()).get(0));
				measur.setUser(getUserWBCFromName(comboBoxOperator[i].getSelectedItem().toString()));
				measur.setTypeMeasur(TypeMeasurDAO
						.getValueTypeMeasurByObject("KodeType", comboBoxTypeMeasur[i].getSelectedItem().toString())
						.get(0));
				measur.setDoze(Double.parseDouble(dozze));
				measur.setDoseDimension(dozeDimension);
				boolean toExcell = chckbxSetToExcel[i].isSelected();
				String koment = textFieldKoment[i].getText();
				measur.setMeasurKoment(koment);
				List<String> listString = new ArrayList<>();
				String DataNuclide = "";
				for (int k = 0; k < 20; k++) {
					if (textField_DozeNuclide[i][k].getText().trim().length() > 0) {
						DataNuclide = DataNuclide + ": " + comboBox_Nuclide[i][k].getSelectedItem().toString();
						DataNuclide = DataNuclide + ": " + ifEmptyToNull(textField_Actyvity[i][k].getText());
						DataNuclide = DataNuclide + ": " + ifEmptyToNull(textField_Postaplenie[i][k].getText());
						DataNuclide = DataNuclide + ": " + ifEmptyToNull(textField_GGP[i][k].getText());
						DataNuclide = DataNuclide + ": " + ifEmptyToNull(textField_DozeNuclide[i][k].getText());

					}

					if (DataNuclide.trim().length() > 0) {
						DataNuclide = "## " + DataNuclide;
						listString.add(DataNuclide);
						DataNuclide = "";
					}
				}
				reportMeasur.setMeasur(measur);
				reportMeasur.setListNuclideData(listString);
				reportMeasur.setToExcell(toExcell);
				reportMeasur.setKoment(koment);
				reportMeasur.setReportFile(listReportMeasur.get(i).getReportFile());
				listReportMeasurToData.add(reportMeasur);
			}
		}

		return listReportMeasurToData;
	}

	private static String ifEmptyToNull(String text) {
		if (text.trim().length() > 0) {
			return text;
		}
		return "0";
	}

	public static List<ReportMeasurClass> generateListReportMeasurClassForRepain(int countData,
			List<ReportMeasurClass> listReportMeasur) {
		List<ReportMeasurClass> listReportMeasurToData = new ArrayList<>();
		for (int i = 0; i < countData; i++) {
			ReportMeasurClass reportMeasur = new ReportMeasurClass();
			Measuring measur = new Measuring();
			measur.setReportFileName(listReportMeasur.get(i).getMeasur().getReportFileName());
			measur.setDate(listReportMeasur.get(i).getMeasur().getDate());
			measur.setPerson(listReportMeasur.get(i).getMeasur().getPerson());
			measur.setLab(LaboratoryDAO.getValueLaboratoryByName(comboBoxLab[i].getSelectedItem().toString()).get(0));
			measur.setUser(getUserWBCFromName(comboBoxOperator[i].getSelectedItem().toString()));
			measur.setTypeMeasur(TypeMeasurDAO
					.getValueTypeMeasurByObject("KodeType", comboBoxTypeMeasur[i].getSelectedItem().toString()).get(0));
			boolean toExcell = chckbxSetToExcel[i].isSelected();
			String koment = textFieldKoment[i].getText();
			measur.setMeasurKoment(koment);
			try {
				measur.setDoze(Double.parseDouble(textFieldDoza[i].getText()));
			} catch (Exception e) {
				// 999999 - absurdna doza imitirashta prazna kletka, za propuskane na tozi red
				// za zapis v bazata
				measur.setDoze(999999);
			}
			measur.setDoseDimension(dozeDimension);
			List<String> listString = new ArrayList<>();
			String DataNuclide = "";
			for (int k = 0; k < 20; k++) {
				if (comboBox_Nuclide[i][k] != null) {
					DataNuclide = DataNuclide + ": " + comboBox_Nuclide[i][k].getSelectedItem().toString();
					DataNuclide = DataNuclide + ": " + textField_Actyvity[i][k].getText();
					DataNuclide = DataNuclide + ": " + textField_Postaplenie[i][k].getText();
					DataNuclide = DataNuclide + ": " + textField_GGP[i][k].getText();
					DataNuclide = DataNuclide + ": " + textField_DozeNuclide[i][k].getText();

				}

				if (DataNuclide.trim().length() > 0) {
					DataNuclide = "## " + DataNuclide;
					listString.add(DataNuclide);
					DataNuclide = "";
				}
			}
			reportMeasur.setMeasur(measur);
			reportMeasur.setListNuclideData(listString);
			reportMeasur.setToExcell(toExcell);
			reportMeasur.setKoment(koment);
			reportMeasur.setReportFile(listReportMeasur.get(i).getReportFile());
			listReportMeasurToData.add(reportMeasur);
		}

		return listReportMeasurToData;
	}

	private static UsersWBC getUserWBCFromName(String comboNmame) {
		for (UsersWBC object : listUsersWBC) {
			String name = object.getName() + " " + object.getLastName();
			if (comboNmame.equals(name)) {
				return object;
			}
		}
		return null;
	}

	private void copyToClipboard(JLabel label) {
		label.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				AplicationMetods.copyToClipboard(label.getText());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	private void startFileInNotepad(JLabel label) {
		label.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String fileName = label.getText();
					if(!fileName.isEmpty())
					for (ReportMeasurClass repMes : listReportMeasurClass) {
						System.out.println(repMes.getReportFile().getName());
						if (repMes.getReportFile().getName().equals(fileName)) {

							Runtime.getRuntime().exec("notepad.exe " + repMes.getReportFile().getPath());
						}
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	protected void ActionListenerPlusBTN(int index) {
		List<ReportMeasurClass> listReportMeasurClassToSave = generateListReportMeasurClassForRepain(countMeasur,
				listReportMeasurClass);
		System.out.println("index " + index);
		String strRecord = "CO-60       0.00      0.00      0.00";
		ReadResultFromReport.PrintListReportMeasurClass(listReportMeasurClassToSave);
		listReportMeasurClass = listReportMeasurClassToSave;
		List<String> listNuclideData = listReportMeasurClass.get(index).getListNuclideData();
		if (listNuclideData == null) {
			listNuclideData = new ArrayList<>();
		} else {
			if (listNuclideData.size() > 0) {
				String lastRecord = listNuclideData.get(listNuclideData.size() - 1);
				System.out.println("lastRecord " + lastRecord);
				String nucl = "";
				if (lastRecord.startsWith("##")) {
					String[] value = StringUtils.split(lastRecord, ":");
					nucl = value[1].trim();

				} else {
					String[] value = StringUtils.split(lastRecord);
					nucl = value[0].trim();
				}
				for (int i = 0; i < listSimbolNuclide.length; i++) {
					if (listSimbolNuclide[i].equals(nucl)) {
						if (i == listSimbolNuclide.length - 1) {
							i = -1;
						}
						strRecord = listSimbolNuclide[i + 1] + "       0.00      0.00      0.00";
						i = listSimbolNuclide.length;
					}
				}
			}
		}

		listNuclideData.add(strRecord);
		listReportMeasurClass.get(index).setListNuclideData(listNuclideData);
		Point pointFrame = getLocation();
		setVisible(false);

		ActionIcone round = new ActionIcone();
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				new AutoInsertMeasutingFrame(round, frame, listReportMeasurClass, listSimbolNuclide, listLaboratiry,
						listUserWBC, listTypeMeasur, listNameTypeMeasur, pointFrame, listAllPerson,manualInsertMeasur);
			}
		});
		thread.start();

	}

	protected void ActionListenerMinusBTN(int index) {
		List<ReportMeasurClass> listReportMeasurClassToSave = generateListReportMeasurClassForRepain(countMeasur,
				listReportMeasurClass);
		System.out.println("index " + index);
		ReadResultFromReport.PrintListReportMeasurClass(listReportMeasurClassToSave);
		listReportMeasurClass = listReportMeasurClassToSave;
		List<String> listNuclideData = listReportMeasurClass.get(index).getListNuclideData();
		if (listNuclideData != null) {
			int lastIndex = listNuclideData.size();
			if (lastIndex > 0) {
				listNuclideData.remove(lastIndex - 1);
				listReportMeasurClass.get(index).setListNuclideData(listNuclideData);
				Point pointFrame = getLocation();
				setVisible(false);
				ActionIcone round = new ActionIcone();
				final Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {

						new AutoInsertMeasutingFrame(round, frame, listReportMeasurClass, listSimbolNuclide,
								listLaboratiry, listUserWBC, listTypeMeasur, listNameTypeMeasur, pointFrame, listAllPerson,manualInsertMeasur);
					}
				});
				thread.start();
			}
		}

	}

	protected void btn_addMeasur_ManualInsertMeasuting_ActionListener(JButton btn_addMeasur, AutoInsertMeasutingFrame autoInsertMeasutingFrame, List<Person> listAllPersonIN) {
		
		btn_addMeasur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				List<ReportMeasurClass> listReportMeasurClassToSave = generateListReportMeasurClassForRepain(countMeasur,
						listReportMeasurClass);		
				
		final JFrame f = new JFrame();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		int count = listReportMeasurClassToSave.size();
		String date ="";
		if(count > 0 ) {
			date = sdf.format(listReportMeasurClassToSave.get(count-1).getMeasur().getDate());
		}
		
		AddNewMeasurFrame newMeasur = new AddNewMeasurFrame(f,listAllPersonIN, date);
		
		if(newMeasur.getSelectedContent() != null) {
		ReportMeasurClass newReportMeasur = ManualInsertMeasutingMethods.generateReportMeasurClassForRepain(newMeasur, listReportMeasurClassToSave );
		
		listReportMeasurClassToSave.add(newReportMeasur);
		ReadResultFromReport.PrintListReportMeasurClass(listReportMeasurClassToSave);
		listReportMeasurClass = listReportMeasurClassToSave;
		
		
		Point pointFrame = getLocation();
		setVisible(false);
				ActionIcone round = new ActionIcone();
				final Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {

						new AutoInsertMeasutingFrame(round, frame, listReportMeasurClass, listSimbolNuclide,
								listLaboratiry, listUserWBC, listTypeMeasur, listNameTypeMeasur, pointFrame, listAllPerson, manualInsertMeasur);
						
						
					}
				});
				thread.start();
		}
		
			}
		});
		
	}
	
	
	
	
	
	private String getNameTapeMeasur(String string) {
		for (int i = 0; i < listNameTypeMeasur.length; i++) {
			if (listTypeMeasur[i].equals(string)) {
				return listNameTypeMeasur[i];
			}
		}
		return "";
	}

	public static String DoubleToString(Double value) {
		DecimalFormat df4 = new DecimalFormat("#.##");
		df4.setRoundingMode(RoundingMode.HALF_UP);
		String stt = df4.format(value).replaceAll(",", ".");

		return stt;

	}

	public static JButton getBtnSave() {
		return btnSave;
	}

	public static void setBtnSave(JButton btnSave) {
		AutoInsertMeasutingFrame.btnSave = btnSave;
	}

	public static List<ReportMeasurClass> getListReportMeasurClass() {
		return listReportMeasurClass;
	}

//	public void setListReportMeasurClass(List<ReportMeasurClass> listReportMeasurClass) {
//		this.listReportMeasurClass = listReportMeasurClass;
//	}

	public static int getCountMeasur() {
		return countMeasur;
	}

	public static void setCountMeasur(int countMeasur) {
		AutoInsertMeasutingFrame.countMeasur = countMeasur;
	}

	public static JTextField[] getTextFieldDoza() {
		return textFieldDoza;
	}

	public static void setTextFieldDoza(JTextField[] textFieldDoza) {
		AutoInsertMeasutingFrame.textFieldDoza = textFieldDoza;
	}

	public static JComboBox[][] getComboBox_Nuclide() {
		return comboBox_Nuclide;
	}

	public static void setComboBox_Nuclide(JComboBox[][] comboBox_Nuclide) {
		AutoInsertMeasutingFrame.comboBox_Nuclide = comboBox_Nuclide;
	}

	public static JTextField[][] getTextField_GGP() {
		return textField_GGP;
	}

	public static void setTextField_GGP(JTextField[][] textField_GGP) {
		AutoInsertMeasutingFrame.textField_GGP = textField_GGP;
	}

	public static JTextField[][] getTextField_Actyvity() {
		return textField_Actyvity;
	}

	public static void setTextField_Actyvity(JTextField[][] textField_Actyvity) {
		AutoInsertMeasutingFrame.textField_Actyvity = textField_Actyvity;
	}

	public static JTextField[][] getTextField_Postaplenie() {
		return textField_Postaplenie;
	}

	public static void setTextField_Postaplenie(JTextField[][] textField_Postaplenie) {
		AutoInsertMeasutingFrame.textField_Postaplenie = textField_Postaplenie;
	}

	public static JTextField[][] getTextField_DozeNuclide() {
		return textField_DozeNuclide;
	}

	public static void setTextField_DozeNuclide(JTextField[][] textField_DozeNuclide) {
		AutoInsertMeasutingFrame.textField_DozeNuclide = textField_DozeNuclide;
	}

	public static JComboBox[] getComboBoxTypeMeasur() {
		return comboBoxTypeMeasur;
	}

	public static void setComboBoxTypeMeasur(JComboBox[] comboBoxTypeMeasur) {
		AutoInsertMeasutingFrame.comboBoxTypeMeasur = comboBoxTypeMeasur;
	}

	public static JCheckBox[] getChckbxSetToExcel() {
		return chckbxSetToExcel;
	}

	public static void setChckbxSetToExcel(JCheckBox[] chckbxSetToExcel) {
		AutoInsertMeasutingFrame.chckbxSetToExcel = chckbxSetToExcel;
	}

}
