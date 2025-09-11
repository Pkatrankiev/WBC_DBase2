package BasicTable_Measuring_Results;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


import Aplication.ReadFileBGTextVariable;
import Aplication.ReportMeasurClass;
import BasiClassDAO.LaboratoryDAO;

import BasiClassDAO.TypeMeasurDAO;
import BasicClassAccessDbase.Measuring;

import javax.swing.JCheckBox;


public class Edit_Measuring_Results_Frame extends JDialog {

	private static JPanel contentPane;

	String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("calendarIcon");
	private ImageIcon Calendarpic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
	
	private static JPanel panel_Date;
	private static JPanel panel_Rutinen;
	private static JPanel panel_Secialen;
	private static  JPanel panel_Postaplenie_Free;
	private static JPanel panel_EdnokratnoMeasur;
	private static JPanel panel_MnogokratnoMeasur;
	
	private static JPanel panel_FreePanelMeasur;
	
	
	private static JTextField lbl_DozeAll;
	
	private static JPanel panel_Nuclide_Data;
	private static Choice[] choice_NuclideName;
	
	
	private static JTextField[] textField_Activity;
	private static JTextField[] textField_Postaplenie;
	
	private static JTextField[] lbl_PostaplenieBq;
	private static JTextField[] lbl_GGPCalc;
	private static JTextField[] lbl_DozaNuclide;
	
	private static JButton[] btn_MinusNuclide;
	private static JButton[] btn_PlusNuclide;
	
	private static JButton btn_InsertNewMeasur;
	private static JButton btn_Update;
	private static JButton btn_Delete;
	
	static JCheckBox chckbxNewCheckBox;
	
	static String[] listLaboratiry = LaboratoryDAO.getMasiveLaboratory();
	static List<String>  listNuclideName = Measuring_Results_Metods.ReadNuclideList();
	static String[]  listTypemeasur = TypeMeasurDAO.getMasiveNameTypeMeasur();
	static List<ReportMeasurClass> listReportMeasurClass = new ArrayList<>();
	
	static Choice choice_Lab;
	static Choice choice_Type;
	static JTextField lbl_Date_Measur;
	
	private static JLabel lbl_InfoPersonMeasur;
	
	static Object[] masive_FromDatePanel;
	static int countNuclide = 1;
	int frameWith = 370;
	private static String[][] dataForAutoInsertMeasur = null;
	static String dozeCalculateTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dozeCalculateTipText");
	
	static Measuring measur;
	static JTextField textKoment;
	private JPanel panel_Buttons_1;
	
	public Edit_Measuring_Results_Frame(JFrame parent, String[][] oldNuclideData, String[] infoFromDBase, Measuring measur) {
		
		super(parent, dozeCalculateTipText, true);
		setMinimumSize(new Dimension(440, 370));
		countNuclide = 1;	
		

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		generateMasivsElements(countNuclide); 
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_All = new JPanel();
		panel_All.setLayout(new BoxLayout(panel_All, BoxLayout.Y_AXIS));
		contentPane.add(panel_All, BorderLayout.NORTH);

		JPanel panel_date = new JPanel();
		FlowLayout fl_panel_date = (FlowLayout) panel_date.getLayout();
		fl_panel_date.setAlignment(FlowLayout.LEFT);
		panel_All.add(panel_date);
		
		JLabel lbl_DozeAllLabel_1 = new JLabel("Дата на измерване");
		panel_date.add(lbl_DozeAllLabel_1);
		lbl_DozeAllLabel_1.setPreferredSize(new Dimension(100, 20));
		
		lbl_Date_Measur = new JTextField();
		panel_date.add(lbl_Date_Measur);
		lbl_Date_Measur.setPreferredSize(new Dimension(70, 20));
		lbl_Date_Measur.setOpaque(true);
		lbl_Date_Measur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Date_Measur.setBackground(Color.WHITE);
		lbl_Date_Measur.setAlignmentX(2.0f);
		
		
		
		JPanel panel_Lab = new JPanel();
		FlowLayout fl_panel_Lab = (FlowLayout) panel_Lab.getLayout();
		fl_panel_Lab.setAlignment(FlowLayout.LEFT);
		panel_All.add(panel_Lab);
		
		JLabel lbl_Laboratory = new JLabel("Лаборатория на измерване");
		panel_Lab.add(lbl_Laboratory);
		lbl_Laboratory.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Laboratory.setPreferredSize(new Dimension(150, 20));
		
		
		choice_Lab= new Choice();
		panel_Lab.add(choice_Lab);
		choice_Lab.setPreferredSize(new Dimension(70, 20));
		
		for (String string : listLaboratiry) {
			choice_Lab.add(string);
		}
		
		
		JPanel panel_Type = new JPanel();
		FlowLayout fl_panel_Type = (FlowLayout) panel_Type.getLayout();
		fl_panel_Type.setAlignment(FlowLayout.LEFT);
		panel_All.add(panel_Type);
		
		JLabel lbl_Type = new JLabel("Тип на измерване");
		panel_Type.add(lbl_Type);
		lbl_Type.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Type.setPreferredSize(new Dimension(100, 20));
		
		choice_Type = new Choice();
		panel_Type.add(choice_Type);
		choice_Type.setPreferredSize(new Dimension(70, 20));
		
		for (String string : listTypemeasur) {
			choice_Type.add(string);
		}
		
		JPanel panel_Koment = new JPanel();
		FlowLayout fl_panel_Koment = (FlowLayout) panel_Koment.getLayout();
		fl_panel_Koment.setAlignment(FlowLayout.LEFT);
		panel_All.add(panel_Koment);
		
		JLabel lblKoment = new JLabel("Коментар");
		panel_Koment.add(lblKoment);
		
		textKoment = new JTextField();
		textKoment.setPreferredSize(new Dimension(350, 20));
		panel_Koment.add(textKoment);

		
		if(infoFromDBase != null) {
			lbl_Date_Measur.setText(infoFromDBase[1]);
			choice_Lab.select(infoFromDBase[2]);
			choice_Type.select(infoFromDBase[3]);
			textKoment.setText(measur.getMeasurKoment());
		}
		
		setPanel_Nuclide(oldNuclideData, infoFromDBase);
		
		setPanel_Buttons(infoFromDBase);
	
		
		Measuring_Results_Metods.btnSave_InsertNewMeasuting_ActionListener(btn_InsertNewMeasur, this, measur);
		Measuring_Results_Metods.btnDelete_Measuting_ActionListener(btn_Delete, this,  measur);
		Measuring_Results_Metods.btnUpData_Measuting_ActionListener(btn_Update, this,  measur);
		

		
		
		setSize(440, 370);

		infoFromDBase = null;
		oldNuclideData = null;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dataForAutoInsertMeasur = null;
				setVisible(false);
				dispose();

			}
		});
		
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private void generateMasivsElements(int count) {
		choice_NuclideName = new Choice[count];
	
		textField_Activity = new JTextField[count];
		textField_Postaplenie = new JTextField[count];
		
		lbl_PostaplenieBq = new JTextField[count];
		lbl_GGPCalc = new JTextField[count];
		lbl_DozaNuclide = new JTextField[count];
		
		btn_MinusNuclide = new JButton[count]; 
		btn_PlusNuclide = new JButton[count];
	}


	private void setPanel_Nuclide(String[][] oldNuclideData, String[] infoFromDBase) {
		
		panel_Nuclide_Data = new JPanel();
		panel_Nuclide_Data.setLayout(new BoxLayout(panel_Nuclide_Data, BoxLayout.Y_AXIS));
		contentPane.add(panel_Nuclide_Data, BorderLayout.CENTER);
		
		SetNuclideDataAll(oldNuclideData, infoFromDBase);
		
	
		
	}


	private void SetNuclideDataAll(String[][] oldNuclideData, String[] infoFromDBase) {
		JPanel panel_Nuclide_DataAll = new JPanel();
		panel_Nuclide_DataAll.setLayout(new BoxLayout(panel_Nuclide_DataAll, BoxLayout.Y_AXIS));
		panel_Nuclide_Data.add(panel_Nuclide_DataAll, BorderLayout.NORTH);

		setPanel_NuclideLabel(panel_Nuclide_DataAll);

//		if(oldNuclideData != null) {
//			countNuclide = oldNuclideData.length;
//			}
			if(infoFromDBase!= null && infoFromDBase.length > 6) {
			countNuclide = infoFromDBase.length-6;
			 oldNuclideData = generateMasiveForNuclideData(infoFromDBase);
		}
			generateMasivsElements(countNuclide); 
		
		for (int i = 0; i < countNuclide; i++) {
			
			setPanel_NuclieData(i,oldNuclideData, panel_Nuclide_DataAll);
		}
	
	}


	private String[][] generateMasiveForNuclideData(String[] infoFromDBase) {
		String[][] masive = new String[countNuclide][5];
		
		for (int i = 0; i < countNuclide; i++) {
			
			String[] infoForMeasur = infoFromDBase[6+i].split("#");
			System.out.println("String[] infoForMeasur --------------------------------------------------");
			for (String strings : infoForMeasur) {
				System.out.println(strings);
			}
			masive[i][0] = infoForMeasur[0] != null ? infoForMeasur[0]:"";
			masive[i][1] = infoForMeasur[1] != null ? infoForMeasur[1]:"";
			masive[i][2] = infoForMeasur[1] != null ? infoForMeasur[2]:"";
			masive[i][3] = infoForMeasur[1] != null ? infoForMeasur[3]:"";
			masive[i][4] = infoForMeasur[1] != null ? infoForMeasur[4]:"";
			
		}
		return masive;
	}

	

	private void setPanel_Buttons(String[] infoFromDBase) {
		
		
		panel_Buttons_1 = new JPanel();
		panel_Buttons_1.setLayout(new BoxLayout(panel_Buttons_1, BoxLayout.Y_AXIS));
		contentPane.add(panel_Buttons_1, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_Buttons_1.add(panel);
		lbl_InfoPersonMeasur = new JLabel("");
		panel.add(lbl_InfoPersonMeasur);
		
//		panel.setVisible(false);
		
		if(infoFromDBase != null) {
		lbl_InfoPersonMeasur.setText(infoFromDBase[0]);
		panel.setVisible(true);
		
		}
		
		panel_Calculate_Buttons(panel_Buttons_1, infoFromDBase);
		
	}


	private void panel_Calculate_Buttons(JPanel panel_Buttons, String[] infoFromDBase) {
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_Buttons_1.add(panel);
		
		JLabel lblNewLabel = new JLabel("Да се запишат промените и в ексел файловете");
		panel.add(lblNewLabel);
		
		chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setSelected(true);
		panel.add(chckbxNewCheckBox);
		
		
		JPanel panel_CalculateButtons = new JPanel();
		FlowLayout fl_panel_CalculateButtons = (FlowLayout) panel_CalculateButtons.getLayout();
		fl_panel_CalculateButtons.setAlignment(FlowLayout.LEFT);
		panel_Buttons.add(panel_CalculateButtons);
		
		JLabel lbl_DozeAllLabel = new JLabel("Обща Доза");
		lbl_DozeAllLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DozeAllLabel.setPreferredSize(new Dimension(75, 20));
		panel_CalculateButtons.add(lbl_DozeAllLabel);
		
		lbl_DozeAll = new JTextField();
		lbl_DozeAll.setPreferredSize(new Dimension(65, 20));
		lbl_DozeAll.setOpaque(true);
		lbl_DozeAll.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DozeAll.setBackground(Color.WHITE);
		lbl_DozeAll.setAlignmentX(2.0f);
		panel_CalculateButtons.add(lbl_DozeAll);
		
		if(infoFromDBase != null) {
			lbl_DozeAll.setText(infoFromDBase[5]);
		}
		
		JLabel lbl_DozeAllDimention = new JLabel("mSv");
		lbl_DozeAllDimention.setPreferredSize(new Dimension(40, 14));
		panel_CalculateButtons.add(lbl_DozeAllDimention);
		
		btn_InsertNewMeasur = new JButton("Добавяне");
		btn_InsertNewMeasur.setPreferredSize(new Dimension(80, 20));
		btn_InsertNewMeasur.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_InsertNewMeasur);
		
		btn_Update = new JButton("Промяна");
		btn_Update.setPreferredSize(new Dimension(60, 20));
		btn_Update.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_Update);
		
		btn_Delete = new JButton("Изтриване");
		btn_Delete.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_Delete);
	
	}
		
	

	protected int countOccurences(String report) {
		if(report == null || report.isEmpty()){
			return 0;
		}
		return report.split("\n", -1).length-1;
	}


	public static JButton getBtn_Export() {
		return btn_Delete;
	}


	private void setPanel_NuclieData(int index, String[][] oldNuclideData, JPanel panel_Nuclide_DataAll) {
		JPanel panel_Data_NuclideData = new JPanel();
		panel_Data_NuclideData.setMaximumSize(new Dimension(32767, 25));
		FlowLayout fl_panel_Data_NuclideData = (FlowLayout) panel_Data_NuclideData.getLayout();
		fl_panel_Data_NuclideData.setAlignment(FlowLayout.LEFT);
		fl_panel_Data_NuclideData.setHgap(1);
		fl_panel_Data_NuclideData.setVgap(1);
		panel_Nuclide_DataAll.add(panel_Data_NuclideData);

		choice_NuclideName[index] = new Choice();
		choice_NuclideName[index].setPreferredSize(new Dimension(60, 20));
		panel_Data_NuclideData.add(choice_NuclideName[index]);
		
		for (String string : listNuclideName) {
			choice_NuclideName[index].add(string);
		}
		
		if(oldNuclideData != null) {
			choice_NuclideName[index].select(oldNuclideData[index][0]);
		}
		
		
		JLabel lblDistancia1_1 = new JLabel("");
		lblDistancia1_1.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1_1);

		textField_Activity[index] = new JTextField();
		textField_Activity[index].setPreferredSize(new Dimension(60, 20));
		panel_Data_NuclideData.add(textField_Activity[index]);
		
		if(oldNuclideData != null) {
			textField_Activity[index].setText(oldNuclideData[index][1]);
		}
		

		
		
		JLabel lblDistancia1_2 = new JLabel("Bq");
		lblDistancia1_2.setPreferredSize(new Dimension(20, 20));
		panel_Data_NuclideData.add(lblDistancia1_2);

		textField_Postaplenie[index] = new JTextField();
		textField_Postaplenie[index].setPreferredSize(new Dimension(75, 20));
		panel_Data_NuclideData.add(textField_Postaplenie[index]);
		
		if(oldNuclideData != null) {
			textField_Postaplenie[index].setText(oldNuclideData[index][2]);
		}

		
		JLabel lbl_IconCalc = new JLabel("Bq");
		lbl_IconCalc.setPreferredSize(new Dimension(20, 20));
		panel_Data_NuclideData.add(lbl_IconCalc);
		
		
		
		lbl_GGPCalc[index] = new JTextField();
		lbl_GGPCalc[index].setPreferredSize(new Dimension(35, 20));
		lbl_GGPCalc[index].setOpaque(true);
		
		lbl_GGPCalc[index].setHorizontalAlignment(SwingConstants.CENTER);
		lbl_GGPCalc[index].setBackground(Color.WHITE);
		lbl_GGPCalc[index].setAlignmentX(2.0f);
		
		if(oldNuclideData != null) {
			lbl_GGPCalc[index].setText(oldNuclideData[index][3]);
		}
		
		
		panel_Data_NuclideData.add(lbl_GGPCalc[index]);
		
		JLabel lbl_GGPDimencions = new JLabel("%");
		lbl_GGPDimencions.setPreferredSize(new Dimension(17, 20));
		panel_Data_NuclideData.add(lbl_GGPDimencions);
		
		lbl_DozaNuclide[index] = new JTextField();
		lbl_DozaNuclide[index].setPreferredSize(new Dimension(35, 20));
		lbl_DozaNuclide[index].setOpaque(true);
		lbl_DozaNuclide[index].setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DozaNuclide[index].setBackground(Color.WHITE);
		lbl_DozaNuclide[index].setAlignmentX(2.0f);
		
		if(oldNuclideData != null) {
			lbl_DozaNuclide[index].setText(oldNuclideData[index][4]);
		}
		
		lbl_DozaNuclide[index].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_DozeAll.setText(sumdoze());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_DozaNuclide[index].setToolTipText(lbl_DozaNuclide[index].getText());
				lbl_DozeAll.setText(sumdoze());
			}

		});
		
		lbl_DozaNuclide[index].addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				lbl_DozeAll.setText(sumdoze());
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				lbl_DozeAll.setText(sumdoze());
			}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		
		panel_Data_NuclideData.add(lbl_DozaNuclide[index]);
		
		JLabel lbl_DozaNuclideDimencion = new JLabel("mSv");
		lbl_DozaNuclideDimencion.setPreferredSize(new Dimension(25, 14));
		panel_Data_NuclideData.add(lbl_DozaNuclideDimencion);
		
		btn_MinusNuclide[index] = new JButton("-");
		btn_MinusNuclide[index].setMargin(new Insets(2, 2, 2, 2));
		btn_MinusNuclide[index].setPreferredSize(new Dimension(15, 20));
		btn_MinusNuclide[index].setHorizontalAlignment(SwingConstants.CENTER);
		
		btn_MinusNuclide[index].setAlignmentX(1.0f);
		panel_Data_NuclideData.add(btn_MinusNuclide[index]);

		if(countNuclide<2) 	btn_MinusNuclide[index].setEnabled(false);
		btn_MinusNuclide[index].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionListenerMinusBTN(index);
			}
		});

		btn_PlusNuclide[index] = new JButton("+");
		btn_PlusNuclide[index].setMargin(new Insets(2, 1, 2, 1));
		btn_PlusNuclide[index].setPreferredSize(new Dimension(15, 20));
		btn_PlusNuclide[index].setHorizontalAlignment(SwingConstants.CENTER);
	
		btn_PlusNuclide[index].setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Data_NuclideData.add(btn_PlusNuclide[index]);
		btn_PlusNuclide[index].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionListenerPlusBTN();
			}
		});
		

	
		
	}


	
	private void setPanel_NuclideLabel(JPanel panel_Nuclide) {
		JPanel panel_Label_NuclideData = new JPanel();
		panel_Label_NuclideData.setMaximumSize(new Dimension(32767, 20));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Label_NuclideData.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);
		panel_Nuclide.add(panel_Label_NuclideData);

		JLabel lbl_NuclideNameLabel = new JLabel("Нуклид");
		lbl_NuclideNameLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_NuclideNameLabel.setPreferredSize(new Dimension(60, 15));
		lbl_NuclideNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_NuclideNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Label_NuclideData.add(lbl_NuclideNameLabel);
		
		JLabel lblDistancia1_1 = new JLabel("");
		lblDistancia1_1.setPreferredSize(new Dimension(5, 0));
		panel_Label_NuclideData.add(lblDistancia1_1);

		JLabel lbl_ActivityLabel = new JLabel("Активност");
		lbl_ActivityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ActivityLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_ActivityLabel.setPreferredSize(new Dimension(75, 15));
		panel_Label_NuclideData.add(lbl_ActivityLabel);
		
		JLabel lblDistancia1_2 = new JLabel("");
		lblDistancia1_2.setPreferredSize(new Dimension(5, 0));
		panel_Label_NuclideData.add(lblDistancia1_2);

		JLabel lbl_PreviosPostaplenieLabel = new JLabel("Постапление");
		lbl_PreviosPostaplenieLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_PreviosPostaplenieLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_PreviosPostaplenieLabel.setPreferredSize(new Dimension(90, 15));
		panel_Label_NuclideData.add(lbl_PreviosPostaplenieLabel);
		
		JLabel lblDistancia1_3 = new JLabel("");
		lblDistancia1_3.setPreferredSize(new Dimension(5, 0));
		panel_Label_NuclideData.add(lblDistancia1_3);
		
		JLabel lbl_GGPLabel = new JLabel("ГГП");
		lbl_GGPLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_GGPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_GGPLabel.setPreferredSize(new Dimension(45, 14));
		panel_Label_NuclideData.add(lbl_GGPLabel);
		
		JLabel lblDistancia1_4 = new JLabel("");
		lblDistancia1_4.setPreferredSize(new Dimension(5, 0));
		panel_Label_NuclideData.add(lblDistancia1_4);
		
		JLabel lbl_DozaLabel = new JLabel("Доза");
		lbl_DozaLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_DozaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DozaLabel.setPreferredSize(new Dimension(55, 14));
		panel_Label_NuclideData.add(lbl_DozaLabel);
	}


	private void viewNewPanel(String[][] oldNuclideData, String[] infoFromDBase) {
		
		if(oldNuclideData != null) {
			countNuclide = oldNuclideData.length;
		}
		
		if(countNuclide > 5) {
			frameWith = 370 + (countNuclide-5)*30;
		}
		
		System.out.println("countNuclide "+countNuclide);
		panel_Nuclide_Data.removeAll();
		
		SetNuclideDataAll(oldNuclideData, infoFromDBase);
//		setPanel_Nuclide(oldNuclideData);
		
		setSize(440, frameWith);
		repaint();
		revalidate();
	}
	
	
	private String sumdoze() {
		DecimalFormat formatta = new  DecimalFormat("#0.00");
		double  sumDoze = 0;
		for (int i = 0; i < countNuclide; i++) {
				if(!lbl_DozaNuclide[i].getText().isEmpty()) {
					System.out.println(lbl_DozaNuclide[i].getText());
				sumDoze += Double.parseDouble(lbl_DozaNuclide[i].getText());
				}
						
			}
		return formatta.format(sumDoze).replace(",", ".");
	}

	

	private void ActionListenerMinusBTN(int index) {
		DecimalFormat formatta = new  DecimalFormat("#0.00");
		String[][] masiveFromDozeArtFrame = Measuring_Results_Metods.readMasiveDataFromDozeArtFrame();
		String[][] masiveMinus = new String[masiveFromDozeArtFrame.length-1][5];
		double  sumDoze = 0;
		int k=0;
		for (int i = 0; i < masiveFromDozeArtFrame.length; i++) {
			if(i != index) {
				masiveMinus[k] = masiveFromDozeArtFrame[i];
				if(!masiveMinus[k][4].isEmpty()) {
				sumDoze += Double.parseDouble(masiveMinus[k][4]);
				}
						k++;
			}
			
		}
		lbl_DozeAll.setText(formatta.format(sumDoze).replace(",", "."));
		viewNewPanel(masiveMinus, null);
	}


	private void ActionListenerPlusBTN() {
		DecimalFormat formatta = new  DecimalFormat("#0.00");
		
		double  sumDoze = 0;
		String[][] masiveFromDozeArtFrame = Measuring_Results_Metods.readMasiveDataFromDozeArtFrame();
		String[][] masivePlus = new String[masiveFromDozeArtFrame.length+1][5];	
		for (int i = 0; i < masiveFromDozeArtFrame.length; i++) {
			masivePlus[i] = masiveFromDozeArtFrame[i];
			if(!masivePlus[i][4].isEmpty()) {
			sumDoze += Double.parseDouble(masivePlus[i][4]);
			}
		}
		lbl_DozeAll.setText(formatta.format(sumDoze).replace(",", "."));
		viewNewPanel(masivePlus, null);
		
	}

	




	public static JPanel getPanel_Date() {
		return panel_Date;
	}


	

	public static JPanel getPanel_Rutinen() {
		return panel_Rutinen;
	}


	public static JPanel getPanel_Secialen() {
		return panel_Secialen;
	}



	public static JPanel getPanel_EdnokratnoMeasur() {
		return panel_EdnokratnoMeasur;
	}


	public static JPanel getPanel_MnogokratnoMeasur() {
		return panel_MnogokratnoMeasur;
	}


	


	public static JPanel getPanel_FreePanelMeasur() {
		return panel_FreePanelMeasur;
	}


	


	public static JPanel getPanel_Postaplenie_Free() {
		return panel_Postaplenie_Free;
	}

	
	public static Choice[] getChoice_NuclideName() {
		return choice_NuclideName;
	}


	


	public static int getCountNuclide() {
		return countNuclide;
	}


	public static JTextField[] getTextField_Activity() {
		return textField_Activity;
	}


	public static JTextField[] getTextField_Postaplenie() {
		return textField_Postaplenie;
	}



	public static JTextField[] getLbl_PostaplenieBq() {
		return lbl_PostaplenieBq;
	}


	


	public static JTextField[] getLbl_GGPCalc() {
		return lbl_GGPCalc;
	}


	public static JTextField[] getLbl_DozaNuclide() {
		return lbl_DozaNuclide;
	}


	public static Object[] getMasive_FromDatePanel() {
		return masive_FromDatePanel;
	}


	public static JTextField getLbl_DozeAll() {
		return lbl_DozeAll;
	}


	public static JLabel getLbl_InfoPersonMeasur() {
		return lbl_InfoPersonMeasur;
	}


	public static JButton getBtn_Aditision() {
		return btn_InsertNewMeasur;
	}


	public static JButton getBtn_Update() {
		return btn_Update;
	}


	public String[][] getDataForAutoInsertMeasur() {
		return dataForAutoInsertMeasur;
	}

	public static List<ReportMeasurClass> getListReportMeasurClass() {
		return listReportMeasurClass;
	}


	public static JCheckBox getChckbxNewCheckBox() {
		return chckbxNewCheckBox;
	}


	public static JTextField getLbl_Date_Measur() {
		return lbl_Date_Measur;
	}


	public static Choice getChoice_Lab() {
		return choice_Lab;
	}


	public static Choice getChoice_Type() {
		return choice_Type;
	}


	public static JTextField getTextKoment() {
		return textKoment;
	}
	
}
