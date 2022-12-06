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

public class AutoInsertMeasutingFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDoza;
	private JTextField textField_GGP;
	private JTextField textField_Actyvity;
	private JTextField textField_Postaplenie;
	private JTextField textField_DozeNuclide;
	
	String[] listSimbolNuclide; 
	String[] listLaboratiry; 
	String[] listUserWBC;
	String[] listTypeMeasur;
	

	public AutoInsertMeasutingFrame(Frame f, List<ReportMeasurClass> listReportMeasurClass, String[] listSimbolNuclideIN, String[] listLaboratiryIN, String[] listUserWBCIN, String[] listTypeMeasurIN) {
		
		 listSimbolNuclide = listSimbolNuclideIN;
		 listLaboratiry = listLaboratiryIN; 
		 listUserWBC = listUserWBCIN;
		 listTypeMeasur = listTypeMeasurIN;
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		int countMeasur = listReportMeasurClass.size();
		JPanel[] panel_Multy = new JPanel[countMeasur];
		JPanel[][] panel_Multy_Nuclide = new JPanel[countMeasur][20];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 862, 300);
		contentPane = new JPanel();
		contentPane.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel_Header = panelHeader();
		contentPane.add(panel_Header);
		
		
		for (int i = 0; i < countMeasur; i++) {
			ReportMeasurClass reportMeasur =  listReportMeasurClass.get(i);
			String reportName = reportMeasur.getMeasur().getReportFileName();
			String date = sdf.format(reportMeasur.getMeasur().getDate());
			String PersonName = reportMeasur.getMeasur().getPerson().getFirstName()
				+" "+reportMeasur.getMeasur().getPerson().getSecondName()+" " +reportMeasur.getMeasur().getPerson().getLastName();
			String egn = reportMeasur.getMeasur().getPerson().getEgn();
			String  lab = reportMeasur.getMeasur().getLab().getLab();
			String operatorNmae = reportMeasur.getMeasur().getUser().getName()+" "+reportMeasur.getMeasur().getUser().getLastName();
			String typeMeasur = reportMeasur.getMeasur().getTypeMeasur().getKodeType();
			String doze = DoubleToString(reportMeasur.getMeasur().getDoze());
			
			panel_Multy[i] = panelMultyMeasuring();
			contentPane.add(panel_Multy[i]);
			int k = 0;
			for (String string : reportMeasur.getListNuclideData()) {
				String[] value = StringUtils.split(string);
				String nucl = value[0].trim();
				String activ = value[2].trim();
			 panel_Multy_Nuclide[i][k] = panelMulyNuclideMeasuring(nucl, activ);
			contentPane.add( panel_Multy_Nuclide[i][k]);
			k++;
			}	
		}
				
		 
	}

	private JPanel panelMulyNuclideMeasuring(String nucl, String  activ) {
		
		JPanel panel_Multy_Nuclide = new JPanel();
		panel_Multy_Nuclide.setMaximumSize(new Dimension(32767, 30));
		panel_Multy_Nuclide.setAlignmentY(0.0f);
		panel_Multy_Nuclide.setAlignmentX(0.0f);
		
		panel_Multy_Nuclide.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNuclide = new JLabel("Nuclide");
		lblNuclide.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNuclide.setPreferredSize(new Dimension(40, 20));
		panel_Multy_Nuclide.add(lblNuclide);
		
		JComboBox comboBox_Nuclide = new JComboBox(listSimbolNuclide);
		comboBox_Nuclide.setPreferredSize(new Dimension(60, 20));
		panel_Multy_Nuclide.add(comboBox_Nuclide);
		
		JLabel lblActyvity = new JLabel("Actyvity");
		lblActyvity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblActyvity.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblActyvity.setPreferredSize(new Dimension(50, 20));
		panel_Multy_Nuclide.add(lblActyvity);
		
		textField_Actyvity = new JTextField();
		textField_Actyvity.setName("");
		textField_Actyvity.setColumns(4);
		panel_Multy_Nuclide.add(textField_Actyvity);
		
		JLabel lblActyvityDimen = new JLabel("Bq");
		lblActyvityDimen.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblActyvityDimen);
		
		JLabel lblPostaplenie = new JLabel("Postaplenie");
		lblPostaplenie.setPreferredSize(new Dimension(60, 20));
		panel_Multy_Nuclide.add(lblPostaplenie);
		
		textField_Postaplenie = new JTextField();
		textField_Postaplenie.setColumns(4);
		panel_Multy_Nuclide.add(textField_Postaplenie);
		
		
		JLabel lblPostaplenieDim = new JLabel("%");
		lblPostaplenieDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblPostaplenieDim);
		
		JLabel lblGGP = new JLabel("GGP");
		lblGGP.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGGP.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblGGP);
		
		textField_GGP = new JTextField();
		textField_GGP.setColumns(4);
		panel_Multy_Nuclide.add(textField_GGP);
		
		JLabel lblGGPDim = new JLabel("%");
		lblGGPDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblGGPDim);
		
		JLabel lblDoza = new JLabel("Doza");
		lblDoza.setSize(new Dimension(20, 20));
		lblDoza.setPreferredSize(new Dimension(50, 20));
		lblDoza.setMinimumSize(new Dimension(60, 50));
		lblDoza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDoza.setAlignmentX(1.0f);
		panel_Multy_Nuclide.add(lblDoza);
		
		textField_DozeNuclide = new JTextField();
		textField_DozeNuclide.setColumns(4);
		panel_Multy_Nuclide.add(textField_DozeNuclide);
		
		JLabel lblDozaNuclideDim = new JLabel("mSv");
		lblDozaNuclideDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy_Nuclide.add(lblDozaNuclideDim);
		
		return panel_Multy_Nuclide;
	}

	private JPanel panelMultyMeasuring() {
		JPanel panel_Multy = new JPanel();
		panel_Multy.setMaximumSize(new Dimension(32767, 30));
		panel_Multy.setAlignmentY(0.0f);
		panel_Multy.setAlignmentX(0.0f);
		
		panel_Multy.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblFileName = new JLabel("file");
		lblFileName.setPreferredSize(new Dimension(80, 20));
		panel_Multy.add(lblFileName);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setPreferredSize(new Dimension(60, 20));
		panel_Multy.add(lblDate);
		
		JLabel lblPersonName = new JLabel("PersonName");
		lblPersonName.setPreferredSize(new Dimension(220, 20));
		panel_Multy.add(lblPersonName);
		
		JLabel lblEGN = new JLabel("EGN");
		lblEGN.setPreferredSize(new Dimension(70, 20));
		panel_Multy.add(lblEGN);
		
		JComboBox comboBoxLab = new JComboBox(listLaboratiry);
		comboBoxLab.setPreferredSize(new Dimension(50, 20));
		panel_Multy.add(comboBoxLab);
		
		JComboBox comboBoxOperator = new JComboBox(listUserWBC);
		comboBoxOperator.setPreferredSize(new Dimension(130, 20));
		panel_Multy.add(comboBoxOperator);
		
		JComboBox comboBoxTypeMeasur = new JComboBox(listTypeMeasur);
		comboBoxTypeMeasur.setPreferredSize(new Dimension(40, 20));
		panel_Multy.add(comboBoxTypeMeasur);
		
		textFieldDoza = new JTextField();
		textFieldDoza.setColumns(4);
		panel_Multy.add(textFieldDoza);
		
		JLabel lblDozeDim = new JLabel("mSv");
		lblDozeDim.setPreferredSize(new Dimension(30, 20));
		panel_Multy.add(lblDozeDim);
		return panel_Multy;
	}

	private JPanel panelHeader() {
		JPanel panel_Header = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Header.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_Header.setMaximumSize(new Dimension(32767, 30));
		panel_Header.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_Header.setAlignmentY(Component.TOP_ALIGNMENT);
		
		
		JLabel lbl_L_File = new JLabel("File");
		lbl_L_File.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_L_File.setPreferredSize(new Dimension(80, 20));
		lbl_L_File.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lbl_L_File.setSize(new Dimension(20, 20));
		lbl_L_File.setMinimumSize(new Dimension(80, 20));
		panel_Header.add(lbl_L_File);
		
		JLabel lbl_L_Date = new JLabel("Date");
		lbl_L_Date.setSize(new Dimension(20, 20));
		lbl_L_Date.setPreferredSize(new Dimension(60, 20));
		lbl_L_Date.setMinimumSize(new Dimension(60, 50));
		lbl_L_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_L_Date.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Date);
		
		JLabel lbl_L_PersonName = new JLabel("Person Name");
		lbl_L_PersonName.setSize(new Dimension(20, 20));
		lbl_L_PersonName.setPreferredSize(new Dimension(220, 20));
		lbl_L_PersonName.setMinimumSize(new Dimension(60, 50));
		lbl_L_PersonName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_PersonName.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Header.add(lbl_L_PersonName);
		
		JLabel lbl_L_EGN = new JLabel("9999999999");
		lbl_L_EGN.setSize(new Dimension(20, 20));
		lbl_L_EGN.setPreferredSize(new Dimension(70, 20));
		lbl_L_EGN.setMinimumSize(new Dimension(60, 50));
		lbl_L_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_EGN.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_EGN);
		
		JLabel lbl_L_Lab = new JLabel("WBC-3");
		lbl_L_Lab.setSize(new Dimension(20, 20));
		lbl_L_Lab.setPreferredSize(new Dimension(50, 20));
		lbl_L_Lab.setMinimumSize(new Dimension(60, 50));
		lbl_L_Lab.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Lab.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Lab);
		
		JLabel lbl_L_OperatorName = new JLabel("qwertyuiop qwertyuiop");
		lbl_L_OperatorName.setSize(new Dimension(20, 20));
		lbl_L_OperatorName.setPreferredSize(new Dimension(130, 20));
		lbl_L_OperatorName.setMinimumSize(new Dimension(60, 50));
		lbl_L_OperatorName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_OperatorName.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_OperatorName);
		
		JLabel lbl_L_TypeMeasur = new JLabel("R");
		lbl_L_TypeMeasur.setSize(new Dimension(20, 20));
		lbl_L_TypeMeasur.setPreferredSize(new Dimension(40, 20));
		lbl_L_TypeMeasur.setMinimumSize(new Dimension(60, 50));
		lbl_L_TypeMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_TypeMeasur.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_TypeMeasur);
		
		JLabel lbl_L_Doze = new JLabel("Doza");
		lbl_L_Doze.setSize(new Dimension(20, 20));
		lbl_L_Doze.setPreferredSize(new Dimension(50, 20));
		lbl_L_Doze.setMinimumSize(new Dimension(60, 50));
		lbl_L_Doze.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Doze.setAlignmentX(1.0f);
		panel_Header.add(lbl_L_Doze);
		return panel_Header;
	}

	public static String DoubleToString(Double value) {
		DecimalFormat df4 = new DecimalFormat("#.##");
		df4.setRoundingMode(RoundingMode.HALF_UP);
		String stt = df4.format(value).replaceAll(",", ".");

		return stt;

	}
	
}
