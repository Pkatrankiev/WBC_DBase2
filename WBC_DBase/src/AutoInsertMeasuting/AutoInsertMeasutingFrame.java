package AutoInsertMeasuting;

import java.awt.Frame;
import java.awt.Point;
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
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.UsersWBC;

import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.Icon;

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
	private JPanel panelBasic;
	int countMeasur;
	Frame frame;
	String clickTxt = ReadFileBGTextVariable.getGlobalTextVariableMap().get("klikToCopy");
	String labelFileNameToolTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelFileNameToolTipText");
	String autoInsertMeasuting = ReadFileBGTextVariable.getGlobalTextVariableMap().get("autoInsertMeasuting");
	JButton btnSave;
	Color panelColor;
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

	List<ReportMeasurClass> listReportMeasurClass;
	static List<UsersWBC> listUsersWBC;
	static DimensionWBC dozeDimension;

	public AutoInsertMeasutingFrame(ActionIcone round, Frame f, List<ReportMeasurClass> listReportMeasur, String[] listSimbolNuclideIN,
			String[] listLaboratiryIN, String[] listUserWBCIN, String[] listTypeMeasurIN, String[] listNameTypeMeasurIN,
			Point pointFrame) {
		setTitle(autoInsertMeasuting);

		setResizable(false);
		int numberLine = 0;
		int y = 190, x = 1200;
		int numberMeasuring = 1;
		String lastDate = "";
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
			
			if(!lastDate.equals(date)) {
				lastDate = date;
				 numberMeasuring = 1;
			}
			panel_Multy[i] = panelMultyMeasuring(i, reportName, date, PersonName, egn, lab, operatorNmae, typeMeasur,
					doze, toExcell, koment, reportMeasur,  numberMeasuring);
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
					if(lab.contains("2")) {
						nucl = value[0].trim();
						activ = value[1].trim();
					}else {
					
					nucl = value[0].trim();
					activ = value[2].trim();
					}
				}
				System.out.println(i+" "+ k+" "+ nucl+" "+ activ+" "+ post+" "+ ggp+" "+ doz);
				
				panel_Multy_Nuclide[i][k] = panelMultyNuclideMeasuring(i, k, nucl, activ, post, ggp, doz);
				panelBasic.add(panel_Multy_Nuclide[i][k]);
				k++;
				numberLine++;
			}
			numberLine++;
			 numberMeasuring ++;
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

	}

	private JPanel panelButtons() {

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);

		JPanel panelButtons = new JPanel();
		panelButtons.setAlignmentX(0.0f);
		panel.add(panelButtons);
		panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //Destroy the JFrame object
			}
		});
		panelButtons.add(btnCancel);

		btnSave = new JButton("Save");
		btnSave.setEnabled(true);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ActionIcone round = new ActionIcone();
				 final Thread thread = new Thread(new Runnable() {
				     @Override
				     public void run() {
				    		if (checkEmptryPostaplenieField(countMeasur)) {
								if (checkEmptryDozeField(countMeasur)) {
									List<ReportMeasurClass> listReportMeasurClassToSave = generateListReportMeasurClassForSaveData(
											countMeasur, listReportMeasurClass);
									InsertMeasurToExcel.SaveListReportMeasurClassToExcellFile(listReportMeasurClassToSave, false);
									SaveListReportMeasurClassToDBase(round, listReportMeasurClassToSave);
								}
							}	 
				    	 
				     }
				    });
				    thread.start();
				
				
				
			
			}
		});
		btnSave.setAlignmentX(1.0f);
		panelButtons.add(btnSave);
		return panelButtons;
	}

	protected void SaveListReportMeasurClassToDBase(ActionIcone round, List<ReportMeasurClass> listReportMeasurClassToSave) {
		Measuring lastMeasur = null;
		for (ReportMeasurClass reportMeasur : listReportMeasurClassToSave) {
			
				MeasuringDAO.setObjectMeasuringToTable(reportMeasur.getMeasur());
				lastMeasur = MeasuringDAO.getLastMeasuring();
				if(!reportMeasur.getListNuclideData().isEmpty()) {
					for (String stringNuclideData : reportMeasur.getListNuclideData()) {
					
						stringNuclideData = stringNuclideData.replaceAll("##", "");
							String[] masiveStrNuclide = stringNuclideData.split(":");

							NuclideWBC nuclide = NuclideWBCDAO.getValueNuclideWBCByObject("Symbol", masiveStrNuclide[1].trim()).get(0);
							double actyviti = Double.parseDouble(masiveStrNuclide[2].replaceAll(",", "."));
							double postaplenie = Double.parseDouble(masiveStrNuclide[3].replaceAll(",", "."));
							double ggp = Double.parseDouble(masiveStrNuclide[4].replaceAll(",", "."));
							double nuclideDoze = Double.parseDouble(masiveStrNuclide[5].replaceAll(",", "."));
							
							ResultsWBCDAO.setValueResultsWBC(lastMeasur, nuclide, actyviti, postaplenie, ggp, nuclideDoze);
				
					}
				
				}
			
			}
		round.StopWindow();
	}

	private JPanel panelcheckAll() {
		JPanel panelcheckAll = new JPanel();
		panelcheckAll.setMaximumSize(new Dimension(32767, 30));
		panelcheckAll.setAlignmentY(0.0f);
		panelcheckAll.setAlignmentX(0.0f);
		panelcheckAll.setLayout(new FlowLayout(FlowLayout.RIGHT, 13, 5));
		panelBasic.add(panelcheckAll);

		JCheckBox chckbxCheckAll = new JCheckBox();
		chckbxCheckAll.setAlignmentX(Component.CENTER_ALIGNMENT);
		chckbxCheckAll.setText("checkAll");
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

		JLabel lbl_L_File = new JLabel("File");
		lbl_L_File.setSize(new Dimension(80, 20));
		lbl_L_File.setPreferredSize(new Dimension(80, 20));
		lbl_L_File.setMinimumSize(new Dimension(80, 20));
		lbl_L_File.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_File.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_File.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_File);

		JLabel lbl_L_Date = new JLabel("Date");
		lbl_L_Date.setSize(new Dimension(60, 20));
		lbl_L_Date.setPreferredSize(new Dimension(60, 20));
		lbl_L_Date.setMinimumSize(new Dimension(60, 20));
		lbl_L_Date.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Date.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Date.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Date);

		JLabel lbl_L_PersonName = new JLabel("Person Name");
		lbl_L_PersonName.setSize(new Dimension(220, 20));
		lbl_L_PersonName.setPreferredSize(new Dimension(220, 20));
		lbl_L_PersonName.setMinimumSize(new Dimension(220, 20));
		lbl_L_PersonName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_PersonName.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_PersonName.setAlignmentX(0.5f);
		panel_Header.add(lbl_L_PersonName);

		JLabel lbl_L_EGN = new JLabel("EGN");
		lbl_L_EGN.setSize(new Dimension(70, 20));
		lbl_L_EGN.setPreferredSize(new Dimension(70, 20));
		lbl_L_EGN.setMinimumSize(new Dimension(70, 20));
		lbl_L_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_EGN.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_EGN.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_EGN);

		JLabel lbl_L_Lab = new JLabel("Lab");
		lbl_L_Lab.setSize(new Dimension(70, 20));
		lbl_L_Lab.setPreferredSize(new Dimension(70, 20));
		lbl_L_Lab.setMinimumSize(new Dimension(70, 20));
		lbl_L_Lab.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Lab.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Lab.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Lab);

		JLabel lbl_L_OperatorName = new JLabel("Operator");
		lbl_L_OperatorName.setSize(new Dimension(130, 20));
		lbl_L_OperatorName.setPreferredSize(new Dimension(130, 20));
		lbl_L_OperatorName.setMinimumSize(new Dimension(130, 20));
		lbl_L_OperatorName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_OperatorName.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_OperatorName.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_OperatorName);

		JLabel lbl_L_TypeMeasur = new JLabel("Type");
		lbl_L_TypeMeasur.setSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setPreferredSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setMinimumSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_TypeMeasur.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_TypeMeasur.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_TypeMeasur);

		JLabel lbl_L_Doze = new JLabel("Doza");
		lbl_L_Doze.setSize(new Dimension(75, 20));
		lbl_L_Doze.setPreferredSize(new Dimension(75, 20));
		lbl_L_Doze.setMinimumSize(new Dimension(75, 20));
		lbl_L_Doze.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Doze.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Doze.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Doze);

		JLabel lbl_L_Koment = new JLabel("Koment");
		lbl_L_Koment.setSize(new Dimension(245, 20));
		lbl_L_Koment.setPreferredSize(new Dimension(245, 20));
		lbl_L_Koment.setMinimumSize(new Dimension(245, 20));
		lbl_L_Koment.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Koment.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Koment.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Koment);

		JLabel lbl_L_AddNuclide = new JLabel("± Nuclide");
		lbl_L_AddNuclide.setSize(new Dimension(60, 20));
		lbl_L_AddNuclide.setPreferredSize(new Dimension(55, 20));
		lbl_L_AddNuclide.setMinimumSize(new Dimension(60, 20));
		lbl_L_AddNuclide.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_AddNuclide.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_AddNuclide.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_AddNuclide);

		JLabel lbl_L_SetToExcel = new JLabel("ToExcel");
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
			String lab, String operatorNmae, String typeMeasur, String doze, boolean toExcell, String koment, ReportMeasurClass reportMeasur, int  numberMeasuring) {
		JPanel panel_Multy = new JPanel();
		panel_Multy.setMaximumSize(new Dimension(32767, 30));
		panel_Multy.setAlignmentY(0.0f);
		panel_Multy.setAlignmentX(0.0f);

		panel_Multy.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblNumber[index] = new JLabel(numberMeasuring+"");
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

		JPanel panel_Multy_Nuclide = new JPanel();
		panel_Multy_Nuclide.setMaximumSize(new Dimension(32767, 30));
		panel_Multy_Nuclide.setAlignmentY(0.0f);
		panel_Multy_Nuclide.setAlignmentX(0.0f);
		panel_Multy_Nuclide.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblNuclide = new JLabel("Nuclide");
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

		JLabel lblActyvity = new JLabel("Actyvity");
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
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblActyvityDimen = new JLabel("Bq");
		lblActyvityDimen.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblActyvityDimen);

		JLabel lblPostaplenie = new JLabel("Postaplenie");
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
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblPostaplenieDim = new JLabel("Bq");
		lblPostaplenieDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblPostaplenieDim);

		JLabel lblGGP = new JLabel("GGP");
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
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblGGPDim = new JLabel("%");
		lblGGPDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblGGPDim);

		JLabel lblDoza = new JLabel("Doza");
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

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});

		JLabel lblDozaNuclideDim = new JLabel("mSv");
		lblDozaNuclideDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblDozaNuclideDim);

		if (subIndex == 0) {
			btnCalc[index] = new JButton("Calculate");
			panel_Multy_Nuclide.add(btnCalc[index]);
			btnCalc[index].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CalculateDozeMeasur(listReportMeasurClass.get(index));

				}
			});
		}

		return panel_Multy_Nuclide;
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
				if (fl ) {
					comboBox_Nuclide[index][subIndex].setForeground(Color.BLACK);
					btnSave.setEnabled(true);

				} else {
					comboBox_Nuclide[index][subIndex].setForeground(Color.RED);
					btnSave.setEnabled(false);
				}
			}
		}
	}

	protected void CalculateDozeMeasur(ReportMeasurClass reportMeasur) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		System.out.println(sdf.format(reportMeasur.getMeasur().getDate()) + " - "
				+ reportMeasur.getMeasur().getPerson().getFirstName() + " - " + reportMeasur.getMeasur().getDoze()
				+ " - " + reportMeasur.getMeasur().getDoseDimension().getDimensionName() + " - "
				+ reportMeasur.getMeasur().getLab().getLab() + " - " + reportMeasur.getMeasur().getUser().getLastName()
				+ " - " + reportMeasur.getMeasur().getTypeMeasur().getKodeType());

		for (String string : reportMeasur.getListNuclideData()) {
			System.out.println(string);
		}
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

	public static boolean checkEmptryDozeField(int countData) {
		String isEmptyDozeFilds = ReadFileBGTextVariable.getGlobalTextVariableMap().get("isEmptyDozeFilds");
		String isEmptyDozeNuclideFilds = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("isEmptyDozeNuclideFilds");
		String noSaveRowToBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("noSaveRowToBase");
		String rowWithoutSaveToExcellFile = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("rowWithoutSaveToExcellFile");

		boolean emtryDoze = false;
		boolean emtryDozeNuclide = false;
		boolean noCheckedInExcell = false;
		String mesage = "";
		for (int i = 0; i < countData; i++) {
			if (textFieldDoza[i].getText().isEmpty()) {
				textFieldDoza[i].setBackground(Color.RED);
				emtryDoze = true;
			}
			if (!chckbxSetToExcel[i].isSelected()) {
				chckbxSetToExcel[i].setBackground(Color.RED);
				noCheckedInExcell = true;
			}
			for (int k = 0; k < 20; k++) {
				if (comboBox_Nuclide[i][k] != null && !comboBoxTypeMeasur[i].getSelectedItem().toString().equals("M")) {
					if (textField_DozeNuclide[i][k].getText().isEmpty()) {
						textField_DozeNuclide[i][k].setBackground(Color.RED);
						emtryDozeNuclide = true;
					}
				}
			}
		}

		if (emtryDoze) {
			mesage = mesage + "<html>" + isEmptyDozeFilds;
		}
		if (emtryDozeNuclide) {
			if (mesage.isEmpty()) {
				mesage = "<html>";
			} else {
				mesage = mesage + "<br>";
			}
			mesage = mesage + isEmptyDozeNuclideFilds + "<br>";
		}
		if (!mesage.isEmpty()) {
			mesage = mesage + noSaveRowToBase;
		}
		if (noCheckedInExcell) {
			if (mesage.isEmpty()) {
				mesage = "<html>";
			} else {
				mesage = mesage + "<br>";
			}
			mesage = mesage + rowWithoutSaveToExcellFile;
		}
		if (!mesage.isEmpty()) {
			mesage = mesage + "</html>";
		}

		if (mesage.isEmpty()) {
			return true;
		} else {
			return OptionDialog(mesage);
		}
	}

	public static boolean checkEmptryPostaplenieField(int countData) {

		String isEmptyPostaplenieNuclideFilds = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("isEmptyPostaplenieNuclideFilds");
		String dozeStr = "";
		boolean emtryDozeNuclide = false, isnuclide = false;

		String mesage = "";
		for (int i = 0; i < countData; i++) {
			dozeStr = textFieldDoza[i].getText();
			if (!dozeStr.isEmpty() && Double.parseDouble(dozeStr.replaceAll(",", ".")) > 0) {
				isnuclide = false;
				for (int k = 0; k < 20; k++) {
					if (comboBox_Nuclide[i][k] != null) {
						isnuclide = true;
						if (textField_DozeNuclide[i][k].getText().isEmpty()
								|| !(Double.parseDouble(textField_DozeNuclide[i][k].getText().replaceAll(",", ".")) > 0)
								|| textField_Actyvity[i][k].getText().isEmpty()
								|| !(Double.parseDouble(textField_Actyvity[i][k].getText().replaceAll(",", ".")) > 0)
								|| textField_Postaplenie[i][k].getText().isEmpty()
								|| !(Double.parseDouble(textField_Postaplenie[i][k].getText().replaceAll(",", ".")) > 0)
								|| textField_GGP[i][k].getText().isEmpty()
								|| !(Double.parseDouble(textField_GGP[i][k].getText().replaceAll(",", ".")) > 0)) {

							emtryDozeNuclide = true;

						}
					}
				}
				if(!isnuclide) {
					emtryDozeNuclide = true;
				}
			}
		}
System.out.println(isnuclide+"  "+emtryDozeNuclide);
		if (emtryDozeNuclide) {
			mesage = "<html>" + isEmptyPostaplenieNuclideFilds + "</html>";
			MessageDialog(mesage);
			return false;
		}

		return true;

	}

	public static void MessageDialog(String mesage) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, mesage, "Info", JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	public static boolean OptionDialog(String mesage) {
		String[] options = { "Back", "Save" };
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);
		/**
		 * select "Back" -> 0; select "Ok" -> 1;
		 */
		if (x > 0) {
			return true;
		}
		return false;
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
					for(ReportMeasurClass repMes : listReportMeasurClass) {
						System.out.println(repMes.getReportFile().getName());
						if(repMes.getReportFile().getName().equals(fileName)) {
							
							Runtime.getRuntime().exec("notepad.exe "+repMes.getReportFile().getPath());
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
		}else {
			if(listNuclideData.size()>0) {
			String lastRecord = listNuclideData.get(listNuclideData.size()-1);
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
				if(listSimbolNuclide[i].equals(nucl)) {
					if(i == listSimbolNuclide.length-1) {
						i=-1;
					}
					strRecord = listSimbolNuclide[i+1]+"       0.00      0.00      0.00";
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
		    	 
		    	 new AutoInsertMeasutingFrame(round, frame, listReportMeasurClass, listSimbolNuclide, listLaboratiry, listUserWBC,
		 				listTypeMeasur, listNameTypeMeasur, pointFrame);
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
				    	 
				    	 new AutoInsertMeasutingFrame(round, frame, listReportMeasurClass, listSimbolNuclide, listLaboratiry, listUserWBC,
				 				listTypeMeasur, listNameTypeMeasur, pointFrame);
				     }
				    });
				    thread.start();	
			}
		}

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

}
