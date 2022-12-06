package FrameViewClass;

import java.awt.EventQueue;
import java.awt.Frame;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import Aplication.ReportMeasurClass;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import java.awt.event.ActionEvent;

public class AutoInsertMeasutingFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panelBasic;
	int countMeasur;
	String[] listSimbolNuclide;
	String[] listLaboratiry;
	String[] listUserWBC;
	String[] listTypeMeasur;

	JTextField[] textFieldDoza;
	JLabel[] lblFileName;
	JLabel[] lblDate;
	JLabel[] lblPersonName;
	JLabel[] lblEGN;
	JComboBox[] comboBoxLab;
	JComboBox[] comboBoxOperator;
	JComboBox[] comboBoxTypeMeasur;
	JCheckBox[] chckbxSetToExcel;
	JButton[] btnCalc;

	JComboBox[][] comboBox_Nuclide;
	JTextField[][] textField_GGP;
	JTextField[][] textField_Actyvity;
	JTextField[][] textField_Postaplenie;
	JTextField[][] textField_DozeNuclide;
	
	List<ReportMeasurClass> listReportMeasurClass;

	public AutoInsertMeasutingFrame(Frame f, List<ReportMeasurClass> listReportMeasur,
			String[] listSimbolNuclideIN, String[] listLaboratiryIN, String[] listUserWBCIN,
			String[] listTypeMeasurIN) {

		setResizable(false);
		int nuberLine = 0;
		int x = 190, y = 850;
		listSimbolNuclide = listSimbolNuclideIN;
		listLaboratiry = listLaboratiryIN;
		listUserWBC = listUserWBCIN;
		listTypeMeasur = listTypeMeasurIN;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		listReportMeasurClass = listReportMeasur;
		countMeasur = listReportMeasurClass.size();
		JPanel[] panel_Multy = new JPanel[countMeasur];
		JPanel[][] panel_Multy_Nuclide = new JPanel[countMeasur][20];
		btnCalc = new JButton[countMeasur];

		lblFileName = new JLabel[countMeasur];
		lblDate = new JLabel[countMeasur];
		lblPersonName = new JLabel[countMeasur];
		lblEGN = new JLabel[countMeasur];
		comboBoxLab = new JComboBox[countMeasur];
		comboBoxOperator = new JComboBox[countMeasur];
		comboBoxTypeMeasur = new JComboBox[countMeasur];
		chckbxSetToExcel = new JCheckBox[countMeasur];
		textFieldDoza = new JTextField[countMeasur];

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
			String doze = DoubleToString(reportMeasur.getMeasur().getDoze());

			panel_Multy[i] = panelMultyMeasuring(i, reportName, date, PersonName, egn, lab, operatorNmae, typeMeasur,
					doze);
			panelBasic.add(panel_Multy[i]);
			int k = 0;
			for (String string : reportMeasur.getListNuclideData()) {
				String[] value = StringUtils.split(string);
				String nucl = value[0].trim();
				String activ = value[2].trim();
				panel_Multy_Nuclide[i][k] = panelMulyNuclideMeasuring(i, k, nucl, activ);
				panelBasic.add(panel_Multy_Nuclide[i][k]);
				k++;
				nuberLine++;
			}
			nuberLine++;
		}

		panelcheckAll();
		panelButtons();

		x = x + nuberLine * 30;

		if (x > 1000) {
			x = 1000;
			y = y + 30;
		}
		setBounds(100, 100, y, x);
//		setPreferredSize(new Dimension(x, y));
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
			}
		});
		panelButtons.add(btnCancel);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setAlignmentX(1.0f);
		panelButtons.add(btnSave);
		return panelButtons;
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
		    	
		    	 if(chckbxCheckAll.isSelected()) {
						selectTrue = false;	
					}else {
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

	private JPanel panelMulyNuclideMeasuring(int index, int subIndex, String nucl, String activ) {

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

		JLabel lblActyvity = new JLabel("Actyvity");
		lblActyvity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblActyvity.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblActyvity.setPreferredSize(new Dimension(50, 20));
		panel_Multy_Nuclide.add(lblActyvity);

		textField_Actyvity[index][subIndex] = new JTextField();
		textField_Actyvity[index][subIndex].setText(activ);
		;
		textField_Actyvity[index][subIndex].setColumns(12);
		panel_Multy_Nuclide.add(textField_Actyvity[index][subIndex]);

		JLabel lblActyvityDimen = new JLabel("Bq");
		lblActyvityDimen.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblActyvityDimen);

		JLabel lblPostaplenie = new JLabel("Postaplenie");
		lblPostaplenie.setPreferredSize(new Dimension(60, 20));
		panel_Multy_Nuclide.add(lblPostaplenie);

		textField_Postaplenie[index][subIndex] = new JTextField();
		textField_Postaplenie[index][subIndex].setColumns(4);
		panel_Multy_Nuclide.add(textField_Postaplenie[index][subIndex]);

		JLabel lblPostaplenieDim = new JLabel("%");
		lblPostaplenieDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblPostaplenieDim);

		JLabel lblGGP = new JLabel("GGP");
		lblGGP.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGGP.setPreferredSize(new Dimension(25, 20));
		panel_Multy_Nuclide.add(lblGGP);

		textField_GGP[index][subIndex] = new JTextField();
		textField_GGP[index][subIndex].setColumns(4);
		panel_Multy_Nuclide.add(textField_GGP[index][subIndex]);

		JLabel lblGGPDim = new JLabel("%");
		lblGGPDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblGGPDim);

		JLabel lblDoza = new JLabel("Doza");
		lblDoza.setPreferredSize(new Dimension(40, 20));
		lblDoza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDoza.setAlignmentX(1.0f);
		panel_Multy_Nuclide.add(lblDoza);

		textField_DozeNuclide[index][subIndex] = new JTextField();
		textField_DozeNuclide[index][subIndex].setColumns(4);
		panel_Multy_Nuclide.add(textField_DozeNuclide[index][subIndex]);

		textField_DozeNuclide[index][subIndex].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

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
					CalculateDozeMeasur( listReportMeasurClass.get(index));
					
				}
			});
		}

		return panel_Multy_Nuclide;
	}

	protected void CalculateDozeMeasur(ReportMeasurClass reportMeasur) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		System.out.println(sdf.format(reportMeasur.getMeasur().getDate()) +
				" - " +	reportMeasur.getMeasur().getPerson().getFirstName() +
				 " - " + reportMeasur.getMeasur().getDoze() +
				 " - " + reportMeasur.getMeasur().getDoseDimension().getDimensionName()+
				 " - " +  reportMeasur.getMeasur().getLab().getLab()+ 
				 " - " +  reportMeasur.getMeasur().getUser().getLastName()+
				 " - " +  reportMeasur.getMeasur().getTypeMeasur().getKodeType());
		 
		 
		for (String string : reportMeasur.getListNuclideData()) {
			System.out.println(string);
		}
	}

	public static String checkFormatString(String code) {

		String newCode = code;

		System.out.println(code);

		try {
			Double.parseDouble(code);

		} catch (NumberFormatException e) {
			newCode = code.substring(0, code.length() - 1);
		}

		return newCode;
	}

	private JPanel panelMultyMeasuring(int index, String reportName, String date, String PersonName, String egn,
			String lab, String operatorNmae, String typeMeasur, String doze) {
		JPanel panel_Multy = new JPanel();
		panel_Multy.setMaximumSize(new Dimension(32767, 30));
		panel_Multy.setAlignmentY(0.0f);
		panel_Multy.setAlignmentX(0.0f);

		panel_Multy.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblFileName[index] = new JLabel(reportName);
		lblFileName[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblFileName[index].setPreferredSize(new Dimension(80, 20));
		panel_Multy.add(lblFileName[index]);

		lblDate[index] = new JLabel(date);
		lblDate[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblDate[index].setPreferredSize(new Dimension(60, 20));
		panel_Multy.add(lblDate[index]);

		lblPersonName[index] = new JLabel(PersonName);
		lblPersonName[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblPersonName[index].setPreferredSize(new Dimension(220, 20));
		panel_Multy.add(lblPersonName[index]);

		lblEGN[index] = new JLabel(egn);
		lblEGN[index].setBorder(new LineBorder(new Color(192, 192, 192)));
		lblEGN[index].setPreferredSize(new Dimension(70, 20));
		panel_Multy.add(lblEGN[index]);

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

		textFieldDoza[index] = new JTextField(doze);
		textFieldDoza[index].setColumns(4);
		panel_Multy.add(textFieldDoza[index]);

		textFieldDoza[index].addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {
			}

			@Override
			public void keyReleased(KeyEvent event) {
			textFieldDoza[index].setText(checkFormatString(textFieldDoza[index].getText()));
			}

			@Override
			public void keyPressed(KeyEvent event) {
			}
		});

		JLabel lblDozeDim = new JLabel("mSv");
		lblDozeDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy.add(lblDozeDim);

		chckbxSetToExcel[index] = new JCheckBox();
		chckbxSetToExcel[index].setSelected(true);
		panel_Multy.add(chckbxSetToExcel[index]);

		return panel_Multy;
	}

	private JPanel panelHeader() {

//		JPanel panel = new JPanel();
//		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
//		flowLayout.setAlignment(FlowLayout.LEFT);
//		contentPane.add(panel, BorderLayout.NORTH);

		JPanel panel_Header = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_Header.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_Header.setMaximumSize(new Dimension(32767, 30));
		panel_Header.setAlignmentY(0.0f);
		contentPane.add(panel_Header, BorderLayout.NORTH);

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
		lbl_L_Doze.setSize(new Dimension(60, 20));
		lbl_L_Doze.setPreferredSize(new Dimension(60, 20));
		lbl_L_Doze.setMinimumSize(new Dimension(60, 20));
		lbl_L_Doze.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Doze.setBorder(new LineBorder(new Color(192, 192, 192)));
		lbl_L_Doze.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Doze);

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

	public static String DoubleToString(Double value) {
		DecimalFormat df4 = new DecimalFormat("#.##");
		df4.setRoundingMode(RoundingMode.HALF_UP);
		String stt = df4.format(value).replaceAll(",", ".");

		return stt;

	}

}
