package DozeArt;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import PersonManagement.SelectSpisPrilFrame;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.FlowLayout;
import java.awt.Choice;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import java.awt.ComponentOrientation;
import javax.swing.JScrollBar;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Insets;
import javax.swing.border.MatteBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JDialog;

@SuppressWarnings("serial")
public class DozeArtFrame extends JDialog {

	private static JPanel contentPane;
	private static JTextField textField_Date_MeasurNaw;
	private static JTextField textField_dateMeasurPrev;
	private static JTextField textField_dateMeasurPrePrev;

	private static JTextField textField_dateMeasur_Start;
	private static JTextField textField_dateMeasur_End;

	String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("calendarIcon");
	private ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));

	private static JLabel lbl_Icon_Naw;
	private static JLabel lbl_Icon_Pred;
	private static JLabel lbl_Icon_PrePred;
	private static JLabel lbl_DateErrorInfo;

	private static JLabel lbl_Icon_Start;
	private static JLabel lbl_Icon_End;
	
	private static JPanel panel_Date;
	private static JLayeredPane layeredPane_Postaplenie;
	private static JPanel panel_Rutinen;
	private static JPanel panel_Secialen;
	private static  JPanel panel_Postaplenie_Free;
	
	private static  JLabel lbl_DateErrorInfo_Special;
	
	private static JLayeredPane layeredPane_Mnogokratno;
	private static JPanel panel_EdnokratnoMeasur;
	private static JPanel panel_MnogokratnoMeasur;
	
	private static JTextField textField_MnogokratnoMeasur;
	private static JLabel lbl_Icon_MnogokratnoMeasur;
	private static JButton btn_MnogokratnoMeasur;
	private static JList<String> jlist;
	private static JLabel lbl_ErrorInfo_MnogokratnoMeasur;
	
	private static JPanel panel_FreePanelMeasur;
	
	private static JLabel lbl_Monitoring;
	private static JRadioButton rdbtnSpecialen;
	private static JRadioButton rdbtnRutinen;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private static JRadioButton rdbtnEdnokratno;
	private static JRadioButton rdbtnMnogokratno;
	private static JRadioButton rdbtnNeprekasnato;
	private static JRadioButton rdbtnSrTotchka;
	private final ButtonGroup buttonGroup2 = new ButtonGroup();
	

	private static JLabel lbl_DateNawToPrevios;
	private static JLabel lbl_DateToPrevios;
	private static JLabel lbl_Count_DayToPrevios;
	
	private static JLabel lbl_DateNawToPrePrevios;
	private static JLabel lbl_DateToPrePrevios;
	private static JLabel lbl_Count_DayToPrePrevios;
	
	private static String[] dimentionStr = {"Bq","µCi"};

	private static JButton buttonToPreviosMinus;
	private static JButton buttonToPreviosPlus;
	
	private static JButton buttonToPrePreviosMinus;
	private static JButton buttonToPrePreviosPlus;
	
	private static JLabel lbl_DozeAll;
	
	private static JPanel panel_Nuclide_Data;
	private static Choice[] choice_NuclideName;
	private static Choice[] choice_AMAD;
	
	private static JTextField[] textField_Activity;
	private static JTextField[] textField_PreviosPostaplenie;
	@SuppressWarnings("rawtypes")
	private static JComboBox[] cmbBox_ActivityDimencion;
	
	private static JLabel[] lbl_PostaplenieBq;
	private static JLabel[] lbl_PostaplenieMCi;
	private static JLabel[] lbl_GGPCalc;
	private static JLabel[] lbl_DozaNuclide;
	
	private static JButton[] btn_MinusNuclide;
	private static JButton[] btn_PlusNuclide;
	
	private static JButton btn_Calculation;
	private static JButton btn_Report;
	private static JButton btn_Export;
	
	private static JLabel lbl_InfoPersonMeasur;
	List<String>  listNuclideName = DozeArt_Methods.ReadNuclideList();
	static Object[] masive_FromDatePanel;
	static int countNuclide = 3;
	int frameWith = 370;
	private boolean fromDBase = false;
	private static String[][] dataForAutoInsertMeasur = null;
	static String dozeCalculateTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dozeCalculateTipText");
	public DozeArtFrame(JFrame parent, String[][] oldNuclideData, String[] infoFromDBase) {
		
		super(parent, dozeCalculateTipText, true);
				
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

		setPanelDate(panel_All);

		setPanel_Nuclide(oldNuclideData, infoFromDBase);
		
		setPanel_Buttons(infoFromDBase);
	
		
		
		
		DozeArt_Methods.ActionListenerMonitoringButtons(rdbtnRutinen, rdbtnSpecialen);
		
		DozeArt_Methods.ActionListenerPostaplenieButtons(rdbtnEdnokratno, rdbtnMnogokratno, rdbtnNeprekasnato, rdbtnSrTotchka);
		
		DozeArt_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_Naw, textField_Date_MeasurNaw);
		DozeArt_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_Pred, textField_dateMeasurPrev);
		DozeArt_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_PrePred, textField_dateMeasurPrePrev);
		boolean toPrevios = true;
		ScrollBar_Methods.scrollBarToPreviosListener(buttonToPreviosMinus, buttonToPreviosPlus, lbl_DateToPrevios, lbl_Count_DayToPrevios, toPrevios);
		toPrevios = false;
		ScrollBar_Methods.scrollBarToPreviosListener(buttonToPrePreviosMinus, buttonToPrePreviosPlus, lbl_DateToPrePrevios, lbl_Count_DayToPrePrevios, toPrevios);
		
		DozeArt_Methods.ActionListenerSetDateByDatePicker_Spetial(lbl_Icon_Start, textField_dateMeasur_Start);
		DozeArt_Methods.ActionListenerSetDateByDatePicker_Spetial(lbl_Icon_End, textField_dateMeasur_End);
		
		DozeArt_Methods.ActionListenerSetDateByDatePicker_MnogokratnoMeasur(lbl_Icon_MnogokratnoMeasur, textField_MnogokratnoMeasur, btn_MnogokratnoMeasur);
		DozeArt_Methods.ActionListener_AddDateInJList(textField_MnogokratnoMeasur,btn_MnogokratnoMeasur,jlist);
		
		if(infoFromDBase !=null) {
			textField_Date_MeasurNaw.setText(infoFromDBase[1]);
			textField_dateMeasurPrev.setText(infoFromDBase[2]);
			textField_dateMeasurPrePrev.setText(infoFromDBase[3]);
			DozeArt_Methods.editPanelsByCorectTextField(textField_dateMeasurPrev, true);
			DozeArt_Methods.editPanelsByCorectTextField(textField_dateMeasurPrePrev, true);
		}
		
		
		setSize(700, frameWith);
//		setResizable(false);
		infoFromDBase = null;
		oldNuclideData = null;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dataForAutoInsertMeasur = null;
				setVisible(false);
				dispose();
//				System.exit(0);
			}
		});
		
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private void generateMasivsElements(int count) {
		choice_NuclideName = new Choice[count];
		choice_AMAD = new Choice[count];
		
		textField_Activity = new JTextField[count];
		textField_PreviosPostaplenie = new JTextField[count];
		
		cmbBox_ActivityDimencion = new JComboBox[count];
		
		lbl_PostaplenieBq = new JLabel[count];
		lbl_PostaplenieMCi = new JLabel[count];
		lbl_GGPCalc = new JLabel[count];
		lbl_DozaNuclide = new JLabel[count];
		
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

		if(oldNuclideData != null) {
			countNuclide = oldNuclideData.length;
			}
		
		if(infoFromDBase!= null) {
			countNuclide = infoFromDBase.length-4;
			 oldNuclideData = generateMasiveForNuclideData(infoFromDBase);
			fromDBase  = true;
		}
		
		generateMasivsElements(countNuclide); 
		
		for (int i = 0; i < countNuclide; i++) {
			setPanel_NuclieData(i,oldNuclideData, panel_Nuclide_DataAll);
		}
	
	}


	private String[][] generateMasiveForNuclideData(String[] infoFromDBase) {
		String[][] masive = new String[countNuclide][5];
		
		for (int i = 0; i < countNuclide; i++) {
			
			String[] infoForMeasur = infoFromDBase[4+i].split("#");
			System.out.println("String[] infoForMeasur --------------------------------------------------");
			for (String strings : infoForMeasur) {
				System.out.println(strings);
			}
			masive[i][0] = infoForMeasur[0] != null ? infoForMeasur[0]:"";
			masive[i][1] = "";
			masive[i][2] = infoForMeasur[1] != null ? infoForMeasur[1]:"";
			masive[i][3] = infoForMeasur.length > 2 ? infoForMeasur[2]:"";
			masive[i][4] = "Bq";
			
		}
		return masive;
	}

	
	
	

	private void setPanel_Buttons(String[] infoFromDBase) {
		
		
		JPanel panel_Buttons = new JPanel();
		panel_Buttons.setLayout(new BoxLayout(panel_Buttons, BoxLayout.Y_AXIS));
		contentPane.add(panel_Buttons, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_Buttons.add(panel);
		lbl_InfoPersonMeasur = new JLabel("");
		panel.add(lbl_InfoPersonMeasur);
		
		panel.setVisible(false);
		
		if(infoFromDBase != null) {
		lbl_InfoPersonMeasur.setText(infoFromDBase[0]);
		panel.setVisible(true);
		
		}
		
		panel_Calculate_Buttons(panel_Buttons);
		
	}


	private void panel_Calculate_Buttons(JPanel panel_Buttons) {
		
		
		JPanel panel_CalculateButtons = new JPanel();
		FlowLayout fl_panel_CalculateButtons = (FlowLayout) panel_CalculateButtons.getLayout();
		fl_panel_CalculateButtons.setAlignment(FlowLayout.RIGHT);
		panel_Buttons.add(panel_CalculateButtons);
		
		JLabel lbl_DozeAllLabel = new JLabel("Обща Доза");
		lbl_DozeAllLabel.setPreferredSize(new Dimension(65, 20));
		panel_CalculateButtons.add(lbl_DozeAllLabel);
		
		lbl_DozeAll = new JLabel();
		lbl_DozeAll.setPreferredSize(new Dimension(85, 20));
		lbl_DozeAll.setOpaque(true);
		lbl_DozeAll.setMinimumSize(new Dimension(80, 16));
		lbl_DozeAll.setMaximumSize(new Dimension(32767, 16));
		lbl_DozeAll.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DozeAll.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_DozeAll.setBackground(Color.WHITE);
		lbl_DozeAll.setAlignmentX(2.0f);
		panel_CalculateButtons.add(lbl_DozeAll);
		
		JLabel lbl_DozeAllDimention = new JLabel("mSv");
		lbl_DozeAllDimention.setPreferredSize(new Dimension(40, 14));
		panel_CalculateButtons.add(lbl_DozeAllDimention);
		
		btn_Calculation = new JButton("Изчисление");
		btn_Calculation.setPreferredSize(new Dimension(80, 20));
		btn_Calculation.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_Calculation);
		btn_Calculation.setEnabled(false);
		
		btn_Report = new JButton("Репорт");
		btn_Report.setPreferredSize(new Dimension(60, 20));
		btn_Report.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_Report);
		btn_Report.setEnabled(false);
		
		btn_Export = new JButton("Експорт към Измерване");
		btn_Export.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_Export);
		btn_Export.setEnabled(false);
		
		btn_Calculation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DozeArt_Methods.ActionListenerCalculationBTN();
				if(DozeArt_Methods.getMasiveDataForReport() != null) {
					btn_Report.setEnabled(true);
					btn_Export.setEnabled(true);
				}else {
					btn_Report.setEnabled(false);
					btn_Export.setEnabled(false);
				}
			}
		});
		
		btn_Report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame parent = new JFrame();
				String report = DozeArt_Methods.createReportDozeArt();
				int max =  countOccurences(report);
				int[] sizeInfoFrame = {600, max*21 };
				int[] Coord = AplicationMetods.getCurentKoordinates(sizeInfoFrame);
				ActionIcone round = new ActionIcone();
				
				
				new DozeArt_ReportFrame(parent, Coord, report, sizeInfoFrame,  round);
				
			}
		});
		
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataForAutoInsertMeasur = DozeArt_Methods.createExportData();
//				setVisible(false);
				dispose();
			}
		});
	}
	
	
	

	protected int countOccurences(String report) {
		if(report == null || report.isEmpty()){
			return 0;
		}
		return report.split("\n", -1).length-1;
	}


	public static JButton getBtn_Export() {
		return btn_Export;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		
		choice_NuclideName[index].addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				DozeArt_Methods.clearCalculationData();
			}
		});
		
		
		JLabel lblDistancia1 = new JLabel("");
		lblDistancia1.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1);

		choice_AMAD[index] = new Choice();
		choice_AMAD[index].setPreferredSize(new Dimension(50, 20));
		panel_Data_NuclideData.add(choice_AMAD[index]);
		
		String nuclideName = choice_NuclideName[index].getSelectedItem();
		DozeArt_Methods.setItemByNuclideName(nuclideName, choice_AMAD[index]);
				
		
		if(oldNuclideData != null) {
			choice_AMAD[index].select(oldNuclideData[index][1]);
		}
		choice_AMAD[index].addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				DozeArt_Methods.clearCalculationData();
			}
		});
		
		JLabel lblDistancia1_1 = new JLabel("");
		lblDistancia1_1.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1_1);

		textField_Activity[index] = new JTextField();
		textField_Activity[index].setPreferredSize(new Dimension(75, 20));
		panel_Data_NuclideData.add(textField_Activity[index]);
		
		if(oldNuclideData != null) {
			textField_Activity[index].setText(oldNuclideData[index][2]);
		}
		
		textField_Activity[index].addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				DozeArt_Methods.clearCalculationData();
			}

		});
		
		
		JLabel lblDistancia1_2 = new JLabel("");
		lblDistancia1_2.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1_2);

		textField_PreviosPostaplenie[index] = new JTextField();
		textField_PreviosPostaplenie[index].setPreferredSize(new Dimension(75, 20));
		panel_Data_NuclideData.add(textField_PreviosPostaplenie[index]);
		
		if(oldNuclideData != null) {
			textField_PreviosPostaplenie[index].setText(oldNuclideData[index][3]);
		}
		textField_PreviosPostaplenie[index].addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				DozeArt_Methods.clearCalculationData();
			}
		});
		
		
		
		cmbBox_ActivityDimencion[index] = new JComboBox(dimentionStr);
		panel_Data_NuclideData.add(cmbBox_ActivityDimencion[index]);
		
		if(oldNuclideData != null) {
			if(oldNuclideData[index][4] != null) {
			cmbBox_ActivityDimencion[index].setSelectedItem(oldNuclideData[index][4]);
			}else {
				cmbBox_ActivityDimencion[index].setSelectedItem("Bq");
			}
		}
		cmbBox_ActivityDimencion[index].addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				DozeArt_Methods.clearCalculationData();
			}
		});
		
		JLabel lbl_IconCalc = new JLabel("");
		lbl_IconCalc.setPreferredSize(new Dimension(20, 14));
		panel_Data_NuclideData.add(lbl_IconCalc);
		
		lbl_PostaplenieBq[index] = new JLabel();
		lbl_PostaplenieBq[index].setPreferredSize(new Dimension(70, 20));
		lbl_PostaplenieBq[index].setOpaque(true);
		lbl_PostaplenieBq[index].setMinimumSize(new Dimension(80, 16));
		lbl_PostaplenieBq[index].setMaximumSize(new Dimension(32767, 16));
		lbl_PostaplenieBq[index].setHorizontalAlignment(SwingConstants.CENTER);
		lbl_PostaplenieBq[index].setBackground(Color.WHITE);
		lbl_PostaplenieBq[index].setAlignmentX(2.0f);
		lbl_PostaplenieBq[index].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_PostaplenieBq[index].setToolTipText(lbl_PostaplenieBq[index].getText());
			}

			public void mousePressed(MouseEvent e1) {
			}
		});
		
		lbl_PostaplenieBq[index].setToolTipText(lbl_PostaplenieBq[index].getText());
		panel_Data_NuclideData.add(lbl_PostaplenieBq[index]);
		
		JLabel lbl_PostaplenieBqDimention = new JLabel("Bq");
		lbl_PostaplenieBqDimention.setPreferredSize(new Dimension(17, 20));
		panel_Data_NuclideData.add(lbl_PostaplenieBqDimention);
		
		lbl_PostaplenieMCi[index] = new JLabel();
		lbl_PostaplenieMCi[index].setPreferredSize(new Dimension(70, 20));
		lbl_PostaplenieMCi[index].setOpaque(true);
		lbl_PostaplenieMCi[index].setMinimumSize(new Dimension(80, 16));
		lbl_PostaplenieMCi[index].setMaximumSize(new Dimension(32767, 16));
		lbl_PostaplenieMCi[index].setHorizontalAlignment(SwingConstants.CENTER);
		lbl_PostaplenieMCi[index].setBackground(Color.WHITE);
		lbl_PostaplenieMCi[index].setAlignmentX(2.0f);
		lbl_PostaplenieMCi[index].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_PostaplenieMCi[index].setToolTipText(lbl_PostaplenieMCi[index].getText());
			}

			public void mousePressed(MouseEvent e1) {
			}
		});
		
		
		panel_Data_NuclideData.add(lbl_PostaplenieMCi[index]);
		
		JLabel lbl_PostaplenieMCiDimention = new JLabel("µCi");
		lbl_PostaplenieMCiDimention.setPreferredSize(new Dimension(17, 20));
		panel_Data_NuclideData.add(lbl_PostaplenieMCiDimention);
		
		lbl_GGPCalc[index] = new JLabel();
		lbl_GGPCalc[index].setPreferredSize(new Dimension(35, 16));
		lbl_GGPCalc[index].setOpaque(true);
		lbl_GGPCalc[index].setMinimumSize(new Dimension(80, 16));
		lbl_GGPCalc[index].setMaximumSize(new Dimension(32767, 16));
		lbl_GGPCalc[index].setHorizontalAlignment(SwingConstants.CENTER);
		lbl_GGPCalc[index].setBackground(Color.WHITE);
		lbl_GGPCalc[index].setAlignmentX(2.0f);
		lbl_GGPCalc[index].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_GGPCalc[index].setToolTipText(lbl_GGPCalc[index].getText());
			}

			public void mousePressed(MouseEvent e1) {
			}
		});
		
		
		panel_Data_NuclideData.add(lbl_GGPCalc[index]);
		
		JLabel lbl_GGPDimencions = new JLabel("%");
		lbl_GGPDimencions.setPreferredSize(new Dimension(17, 14));
		panel_Data_NuclideData.add(lbl_GGPDimencions);
		
		lbl_DozaNuclide[index] = new JLabel();
		lbl_DozaNuclide[index].setPreferredSize(new Dimension(35, 16));
		lbl_DozaNuclide[index].setOpaque(true);
		lbl_DozaNuclide[index].setMinimumSize(new Dimension(80, 16));
		lbl_DozaNuclide[index].setMaximumSize(new Dimension(32767, 16));
		lbl_DozaNuclide[index].setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DozaNuclide[index].setBackground(Color.WHITE);
		lbl_DozaNuclide[index].setAlignmentX(2.0f);
		lbl_DozaNuclide[index].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_DozaNuclide[index].setToolTipText(lbl_DozaNuclide[index].getText());
			}

			public void mousePressed(MouseEvent e1) {
			}
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
		
		if(fromDBase) {
			btn_PlusNuclide[index].setEnabled(false);
			btn_MinusNuclide[index].setEnabled(false);
		}
		
		
		
		DozeArt_Methods.ActionListenerChoiceNuclide(choice_NuclideName[index],choice_AMAD[index]);
	
		
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
		
		JLabel lblDistancia1 = new JLabel("");
		lblDistancia1.setPreferredSize(new Dimension(5, 0));
		panel_Label_NuclideData.add(lblDistancia1);

		JLabel lbl_AMADLabel = new JLabel("АМАД");
		lbl_AMADLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_AMADLabel.setPreferredSize(new Dimension(50, 15));
		lbl_AMADLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Label_NuclideData.add(lbl_AMADLabel);
		
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
		lbl_PreviosPostaplenieLabel.setPreferredSize(new Dimension(75, 15));
		panel_Label_NuclideData.add(lbl_PreviosPostaplenieLabel);
		
		JLabel lbl_IconCalcLabel = new JLabel("");
		lbl_IconCalcLabel.setPreferredSize(new Dimension(60, 14));
		panel_Label_NuclideData.add(lbl_IconCalcLabel);
		
		JLabel lbl_PostaplenieLabel = new JLabel("Постапление");
		lbl_PostaplenieLabel.setBorder(new LineBorder(new Color(128, 128, 128)));
		lbl_PostaplenieLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_PostaplenieLabel.setPreferredSize(new Dimension(172, 15));
		panel_Label_NuclideData.add(lbl_PostaplenieLabel);
		
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

	
	private void setPanelDate(JPanel panel_All) {
		panel_Date = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_Date.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel_All.add(panel_Date);

		setPanel_DateMeasur(panel_Date);
		
		JPanel panel_Monitoring =  setPanel_Monitoring(panel_Date);

		panel_Monitoring.setEnabled(false);
		
		layeredPane_Postaplenie = new JLayeredPane();
		layeredPane_Postaplenie.setEnabled(false);
		layeredPane_Postaplenie.setBorder(null);
		layeredPane_Postaplenie.setPreferredSize(new Dimension(170, 100));
		panel_Date.add(layeredPane_Postaplenie);
		
		panel_Rutinen = setPanel_Rutinen();
		panel_Secialen = setPanel_SpecialMeasur();
		panel_Postaplenie_Free = setPanel_Postaplenie_Free();
		
		layeredPane_Postaplenie.add(panel_Secialen);
		layeredPane_Postaplenie.add(panel_Rutinen);
		layeredPane_Postaplenie.add(panel_Postaplenie_Free);
		
		layeredPane_Mnogokratno = new JLayeredPane();
		layeredPane_Mnogokratno.setPreferredSize(new Dimension(190, 100));
		panel_Date.add(layeredPane_Mnogokratno);
		
		panel_EdnokratnoMeasur = setPanel_EdnokratnoMeasur();
		panel_MnogokratnoMeasur = setPanel_MnogokratnoMeasur();
		panel_FreePanelMeasur = setPanel_FreePanelMeasur();
		
		
		layeredPane_Mnogokratno.add(panel_MnogokratnoMeasur);
		layeredPane_Mnogokratno.add(panel_FreePanelMeasur);
		layeredPane_Mnogokratno.add(panel_EdnokratnoMeasur);
	
	}
	

	private JPanel setPanel_Monitoring(JPanel panel_Date) {
		JPanel panel_Monitoring = new JPanel();
		
		panel_Monitoring.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Monitoring.setPreferredSize(new Dimension(90, 100));
		panel_Monitoring.setMinimumSize(new Dimension(90, 100));
		panel_Monitoring.setLayout(new BoxLayout(panel_Monitoring, BoxLayout.Y_AXIS));
		panel_Date.add(panel_Monitoring);

		lbl_Monitoring = new JLabel("Мониторинг");
		lbl_Monitoring.setEnabled(false);
		lbl_Monitoring.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Monitoring.setMaximumSize(new Dimension(32767, 14));
		panel_Monitoring.add(lbl_Monitoring);

		rdbtnRutinen = new JRadioButton("Рутинен");
		rdbtnRutinen.setEnabled(false);
		panel_Monitoring.add(rdbtnRutinen);
		rdbtnRutinen.setSelected(true);
		
		rdbtnSpecialen = new JRadioButton("Специален");
		rdbtnSpecialen.setEnabled(false);
		panel_Monitoring.add(rdbtnSpecialen);
		
		buttonGroup.add(rdbtnSpecialen);
		buttonGroup.add(rdbtnRutinen);
		
		return panel_Monitoring;
		
	}
	
	
	private void setPanel_DateMeasur(JPanel panel_Date) {
		JPanel panel_DateMeasur = new JPanel();
		panel_DateMeasur.setPreferredSize(new Dimension(200, 100));
		panel_DateMeasur.setMinimumSize(new Dimension(200, 100));
		panel_DateMeasur.setMaximumSize(new Dimension(200, 32767));
		panel_DateMeasur.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_DateMeasur.setLayout(new BoxLayout(panel_DateMeasur, BoxLayout.Y_AXIS));
		panel_Date.add(panel_DateMeasur);

		setPanel_dateLabel(panel_DateMeasur);
		setPanel_dateMeasurNaw(panel_DateMeasur);
		setPanel_dateMeasurPrev(panel_DateMeasur);
		setPanel_dateMeasurPrePrev(panel_DateMeasur);
		setPanel_dateInfoLabel(panel_DateMeasur);
		
	}

	private void setPanel_dateLabel(JPanel panel_DateMeasur) {
		JPanel panel_Date_Label = new JPanel();
		panel_Date_Label.setMaximumSize(new Dimension(32767, 15));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_Label.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Измерване");
		lblNewLabel_1.setMinimumSize(new Dimension(80, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(100, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_Label.add(lblNewLabel_1);

		JLabel lbl_DateIzm = new JLabel("Дата");
		lbl_DateIzm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateIzm.setMaximumSize(new Dimension(32767, 14));
		lbl_DateIzm.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateIzm.setPreferredSize(new Dimension(70, 15));
		panel_Date_Label.add(lbl_DateIzm);
		panel_DateMeasur.add(panel_Date_Label);
	}

	private void setPanel_dateInfoLabel(JPanel panel_DateMeasur) {
		JPanel panel_Date_Label = new JPanel();
		panel_Date_Label.setMaximumSize(new Dimension(32767, 15));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_Label.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		lbl_DateErrorInfo = new JLabel();
		lbl_DateErrorInfo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_DateErrorInfo.setForeground(new Color(255, 0, 0));
		lbl_DateErrorInfo.setMinimumSize(new Dimension(80, 14));
		lbl_DateErrorInfo.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_DateErrorInfo.setPreferredSize(new Dimension(195, 15));
		lbl_DateErrorInfo.setMaximumSize(new Dimension(32767, 14));
		lbl_DateErrorInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_Label.add(lbl_DateErrorInfo);
		panel_DateMeasur.add(panel_Date_Label);
	}

	
	private void setPanel_dateMeasurNaw(JPanel panel_DateMeasur) {
		JPanel panel_Date_MeasurNaw = new JPanel();
		panel_Date_MeasurNaw.setMaximumSize(new Dimension(32767, 22));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_MeasurNaw.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Текущо");
		lblNewLabel_1.setMinimumSize(new Dimension(80, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(100, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_MeasurNaw.add(lblNewLabel_1);

		textField_Date_MeasurNaw = new JTextField();
		panel_Date_MeasurNaw.add(textField_Date_MeasurNaw);
		textField_Date_MeasurNaw.setColumns(8);
		panel_DateMeasur.add(panel_Date_MeasurNaw);

		lbl_Icon_Naw = new JLabel(pic);
		lbl_Icon_Naw.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_Naw.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_Naw.setBorder(null);
		lbl_Icon_Naw.setAlignmentX(1.0f);
		panel_Date_MeasurNaw.add(lbl_Icon_Naw);
	}

	private void setPanel_dateMeasurPrev(JPanel panel_DateMeasur) {
		JPanel panel_date_MeasurPrev = new JPanel();
		panel_date_MeasurPrev.setMaximumSize(new Dimension(32767, 22));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_date_MeasurPrev.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Предходно");
		lblNewLabel_1.setMinimumSize(new Dimension(80, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(100, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_date_MeasurPrev.add(lblNewLabel_1);

		textField_dateMeasurPrev = new JTextField();
		panel_date_MeasurPrev.add(textField_dateMeasurPrev);
		textField_dateMeasurPrev.setColumns(8);
		panel_DateMeasur.add(panel_date_MeasurPrev);

		lbl_Icon_Pred = new JLabel(pic);
		lbl_Icon_Pred.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_Pred.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_Pred.setBorder(null);
		lbl_Icon_Pred.setAlignmentX(1.0f);
		panel_date_MeasurPrev.add(lbl_Icon_Pred);
	}

	private void setPanel_dateMeasurPrePrev(JPanel panel_DateMeasur) {
		JPanel panel_date_MeasurPrev = new JPanel();
		panel_date_MeasurPrev.setMaximumSize(new Dimension(32767, 22));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_date_MeasurPrev.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Преди предходно");
		lblNewLabel_1.setMinimumSize(new Dimension(80, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(100, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_date_MeasurPrev.add(lblNewLabel_1);

		textField_dateMeasurPrePrev = new JTextField();
		panel_date_MeasurPrev.add(textField_dateMeasurPrePrev);
		textField_dateMeasurPrePrev.setColumns(8);
		panel_DateMeasur.add(panel_date_MeasurPrev);

		lbl_Icon_PrePred = new JLabel(pic);
		lbl_Icon_PrePred.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_PrePred.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_PrePred.setBorder(null);
		lbl_Icon_PrePred.setAlignmentX(1.0f);
		panel_date_MeasurPrev.add(lbl_Icon_PrePred);
	}

	private JPanel setPanel_Rutinen() {
		
		JPanel panel__Rutinen = new JPanel();
		layeredPane_Postaplenie.setLayer(panel__Rutinen, 3);
		panel__Rutinen.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel__Rutinen.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel__Rutinen.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel__Rutinen.setBounds(0, 0, 170, 100);
		panel__Rutinen.setLayout(new BoxLayout(panel__Rutinen, BoxLayout.Y_AXIS));
		
		JLabel lbl_DateMeasur = new JLabel("Рутинен");
		lbl_DateMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateMeasur.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateMeasur.setMaximumSize(new Dimension(32767, 15));
		lbl_DateMeasur.setPreferredSize(new Dimension(100, 15));
		panel__Rutinen.add(lbl_DateMeasur);
		
		JPanel panel_rdbtnEdnokratno = new JPanel();
		panel_rdbtnEdnokratno.setMaximumSize(new Dimension(32767, 18));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_rdbtnEdnokratno.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);
		
		rdbtnEdnokratno = new JRadioButton("Еднократно");
		rdbtnEdnokratno.setPreferredSize(new Dimension(87, 15));
		rdbtnEdnokratno.setMinimumSize(new Dimension(87, 15));
		rdbtnEdnokratno.setMaximumSize(new Dimension(87, 15));
		
		panel_rdbtnEdnokratno.add(rdbtnEdnokratno);
		panel__Rutinen.add(panel_rdbtnEdnokratno);

		
		JPanel panel_rdbtnMnogokratno = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_rdbtnMnogokratno.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		panel_rdbtnMnogokratno.setMaximumSize(new Dimension(32767, 18));
		((FlowLayout) panel_rdbtnMnogokratno.getLayout()).setAlignment(FlowLayout.LEFT);
		rdbtnMnogokratno = new JRadioButton("Многократно");
		rdbtnMnogokratno.setPreferredSize(new Dimension(93, 15));
		rdbtnMnogokratno.setMinimumSize(new Dimension(93, 15));
		rdbtnMnogokratno.setMaximumSize(new Dimension(93, 15));
		panel_rdbtnMnogokratno.add(rdbtnMnogokratno);
		panel__Rutinen.add(panel_rdbtnMnogokratno);

		
		JPanel panel_rdbtnNeprekasnato = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_rdbtnNeprekasnato.getLayout();
		flowLayout_1.setVgap(1);
		flowLayout_1.setHgap(1);
		panel_rdbtnNeprekasnato.setMaximumSize(new Dimension(32767, 18));
		((FlowLayout) panel_rdbtnNeprekasnato.getLayout()).setAlignment(FlowLayout.LEFT);
		rdbtnNeprekasnato = new JRadioButton("Непрекъснато");
		rdbtnNeprekasnato.setPreferredSize(new Dimension(99, 15));
		rdbtnNeprekasnato.setMinimumSize(new Dimension(99, 15));
		rdbtnNeprekasnato.setMaximumSize(new Dimension(99, 15));
		panel_rdbtnNeprekasnato.add(rdbtnNeprekasnato);
		panel__Rutinen.add(panel_rdbtnNeprekasnato);

		
		JPanel panel_rdbtnSrTotchka = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_rdbtnSrTotchka.getLayout();
		flowLayout_2.setVgap(1);
		flowLayout_2.setHgap(1);
		panel_rdbtnSrTotchka.setMaximumSize(new Dimension(32767, 18));
		((FlowLayout) panel_rdbtnSrTotchka.getLayout()).setAlignment(FlowLayout.LEFT);
		rdbtnSrTotchka = new JRadioButton("Ср. точка");
		rdbtnSrTotchka.setMinimumSize(new Dimension(75, 15));
		rdbtnSrTotchka.setPreferredSize(new Dimension(75, 15));
		rdbtnSrTotchka.setMaximumSize(new Dimension(75, 15));
		panel_rdbtnSrTotchka.add(rdbtnSrTotchka);
		panel__Rutinen.add(panel_rdbtnSrTotchka);
		rdbtnSrTotchka.setSelected(true);
	
		buttonGroup2.add(rdbtnEdnokratno);
		buttonGroup2.add(rdbtnMnogokratno);
		buttonGroup2.add(rdbtnNeprekasnato);
		buttonGroup2.add(rdbtnSrTotchka);
		
		return panel__Rutinen;
				
	}

	private JPanel setPanel_Postaplenie_Free() {
		
		JPanel panel__Postaplenie_Free = new JPanel();
		layeredPane_Postaplenie.setLayer(panel__Postaplenie_Free, 4);
		panel__Postaplenie_Free.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel__Postaplenie_Free.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel__Postaplenie_Free.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel__Postaplenie_Free.setBounds(0, 0, 170, 100);
		panel__Postaplenie_Free.setLayout(new BoxLayout(panel__Postaplenie_Free, BoxLayout.Y_AXIS));
				
		return panel__Postaplenie_Free;
				
	}
	
	private JPanel setPanel_SpecialMeasur() {
	
		JPanel panel_SpecialMeasur = new JPanel();
		layeredPane_Postaplenie.setLayer(panel_SpecialMeasur, 2);
		panel_SpecialMeasur.setBorder(new LineBorder(new Color(0, 0, 0)));;
		panel_SpecialMeasur.setBounds(0, 0, 170, 100);
		panel_SpecialMeasur.setLayout(new BoxLayout(panel_SpecialMeasur, BoxLayout.Y_AXIS));
				
		
		JLabel lbl_DateMeasur = new JLabel("Специален");
		lbl_DateMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateMeasur.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateMeasur.setMaximumSize(new Dimension(32767, 15));
		lbl_DateMeasur.setPreferredSize(new Dimension(100, 15));
		panel_SpecialMeasur.add(lbl_DateMeasur);

		setPanel_SpecialMeasur_dateLabel(panel_SpecialMeasur);
		setPanel_SpecialMeasur_dateMeasurStart(panel_SpecialMeasur);
		setPanel_SpecialMeasur_dateMeasurEnd(panel_SpecialMeasur);
		
		lbl_DateErrorInfo_Special = new JLabel();
		lbl_DateErrorInfo_Special.setPreferredSize(new Dimension(195, 25));
		lbl_DateErrorInfo_Special.setMinimumSize(new Dimension(80, 14));
		lbl_DateErrorInfo_Special.setMaximumSize(new Dimension(32767, 14));
		lbl_DateErrorInfo_Special.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_DateErrorInfo_Special.setForeground(Color.RED);
		lbl_DateErrorInfo_Special.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_DateErrorInfo_Special.setAlignmentX(0.5f);
		panel_SpecialMeasur.add(lbl_DateErrorInfo_Special);
		
		return panel_SpecialMeasur;

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
		
		setSize(700, frameWith);
		repaint();
		revalidate();
	}
	
	private void setPanel_SpecialMeasur_dateLabel(JPanel panel) {
		JPanel panel_Date_Label = new JPanel();
		panel_Date_Label.setPreferredSize(new Dimension(10, 15));
		panel_Date_Label.setMinimumSize(new Dimension(10, 15));
		panel_Date_Label.setMaximumSize(new Dimension(32767, 15));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_Label.getLayout();
		fl_panel_Label_NuclideData.setVgap(0);
		fl_panel_Label_NuclideData.setHgap(0);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Операция");
		lblNewLabel_1.setMinimumSize(new Dimension(70, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(70, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_Label.add(lblNewLabel_1);

		JLabel lbl_DateIzm = new JLabel("Дата");
		lbl_DateIzm.setMinimumSize(new Dimension(70, 14));
		lbl_DateIzm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateIzm.setMaximumSize(new Dimension(32767, 14));
		lbl_DateIzm.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateIzm.setPreferredSize(new Dimension(70, 15));
		panel_Date_Label.add(lbl_DateIzm);
		
		panel.add(panel_Date_Label);
	}

	private void setPanel_SpecialMeasur_dateMeasurStart(JPanel panel_DateMeasur) {
		JPanel panel_Date_MeasurNaw = new JPanel();
		panel_Date_MeasurNaw.setMaximumSize(new Dimension(32767, 22));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_MeasurNaw.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Начало");
		lblNewLabel_1.setMinimumSize(new Dimension(70, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(70, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_MeasurNaw.add(lblNewLabel_1);

		textField_dateMeasur_Start = new JTextField();
		panel_Date_MeasurNaw.add(textField_dateMeasur_Start);
		textField_dateMeasur_Start.setColumns(8);
		panel_DateMeasur.add(panel_Date_MeasurNaw);
		
		lbl_Icon_Start = new JLabel(pic);
		lbl_Icon_Start.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_Start.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_Start.setBorder(null);
		lbl_Icon_Start.setAlignmentX(1.0f);
		panel_Date_MeasurNaw.add(lbl_Icon_Start);
	}

	private void setPanel_SpecialMeasur_dateMeasurEnd(JPanel panel_DateMeasur) {
		JPanel panel_Date_MeasurNaw = new JPanel();
		panel_Date_MeasurNaw.setMaximumSize(new Dimension(32767, 22));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_MeasurNaw.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Край");
		lblNewLabel_1.setMinimumSize(new Dimension(70, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(70, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_MeasurNaw.add(lblNewLabel_1);

		textField_dateMeasur_End = new JTextField();
		panel_Date_MeasurNaw.add(textField_dateMeasur_End);
		textField_dateMeasur_End.setColumns(8);
		panel_DateMeasur.add(panel_Date_MeasurNaw);

		lbl_Icon_End = new JLabel(pic);
		lbl_Icon_End.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_End.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_End.setBorder(null);
		lbl_Icon_End.setAlignmentX(1.0f);
		panel_Date_MeasurNaw.add(lbl_Icon_End);
	}

	private JPanel setPanel_EdnokratnoMeasur() {
		JPanel panel__EdnokratnoMeasur = new JPanel();
		layeredPane_Mnogokratno.setLayer(panel__EdnokratnoMeasur, 2); //2
		panel__EdnokratnoMeasur.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel__EdnokratnoMeasur.setAlignmentX(5.0f);
		panel__EdnokratnoMeasur.setAlignmentY(5.0f);;
		panel__EdnokratnoMeasur.setBounds(0, 0, 190, 100);
		panel__EdnokratnoMeasur.setLayout(new BoxLayout(panel__EdnokratnoMeasur, BoxLayout.Y_AXIS));
		

		JLabel lbl_DateMeasur = new JLabel("Еднократно");
		lbl_DateMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateMeasur.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateMeasur.setMaximumSize(new Dimension(32767, 14));
		lbl_DateMeasur.setPreferredSize(new Dimension(80, 14));
		panel__EdnokratnoMeasur.add(lbl_DateMeasur);

		setPanel_EdnokratnoMeasur_dateLabel(panel__EdnokratnoMeasur);
		
		setPanel_EdnokratnoMeasur_ToPrevios(panel__EdnokratnoMeasur);
		
		setPanel_EdnokratnoMeasur_Button_ToPrevios(panel__EdnokratnoMeasur) ;

		
		setPanel_EdnokratnoMeasur_ToPrePrevios(panel__EdnokratnoMeasur);
		
		setPanel_EdnokratnoMeasur_Button_DateNawToPrePrev(panel__EdnokratnoMeasur) ;

	
		return panel__EdnokratnoMeasur;

	}

	private JPanel setPanel_MnogokratnoMeasur() {
		JPanel MnogokratnoMeasur = new JPanel();
		layeredPane_Mnogokratno.setLayer(MnogokratnoMeasur, 3);
		MnogokratnoMeasur.setBorder(new LineBorder(new Color(0, 0, 0)));
		MnogokratnoMeasur.setAlignmentX(5.0f);
		MnogokratnoMeasur.setAlignmentY(5.0f);;
		MnogokratnoMeasur.setBounds(0, 0, 190, 100);
		MnogokratnoMeasur.setLayout(new BoxLayout(MnogokratnoMeasur, BoxLayout.Y_AXIS));
		

		JLabel lbl_DateMeasur = new JLabel("Многократно");
		lbl_DateMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateMeasur.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateMeasur.setMaximumSize(new Dimension(32767, 15));
		lbl_DateMeasur.setPreferredSize(new Dimension(80, 15));
		MnogokratnoMeasur.add(lbl_DateMeasur);

		setPanel_MnogokratnoMeasur_dateLabel(MnogokratnoMeasur);
		
		setPanel_MnogokratnoMeasur(MnogokratnoMeasur);
		
		
		
		return MnogokratnoMeasur;

	}
	
	private JPanel setPanel_FreePanelMeasur() {
		JPanel FreePanelMeasur = new JPanel();
		layeredPane_Mnogokratno.setLayer(FreePanelMeasur, 4);  //4
		FreePanelMeasur.setBorder(new LineBorder(new Color(0, 0, 0)));
		FreePanelMeasur.setAlignmentX(5.0f);
		FreePanelMeasur.setAlignmentY(5.0f);;
		FreePanelMeasur.setBounds(0, 0, 190, 100);
		FreePanelMeasur.setLayout(new BoxLayout(FreePanelMeasur, BoxLayout.Y_AXIS));
		return FreePanelMeasur;
	}
	
	private void setPanel_EdnokratnoMeasur_dateLabel(JPanel panel) {
		JPanel panel_Date_Label = new JPanel();
		panel_Date_Label.setMaximumSize(new Dimension(32767, 15));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_Label.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.RIGHT);
		
		JLabel lbl_DateIzm_1 = new JLabel("От Дата");
		lbl_DateIzm_1.setPreferredSize(new Dimension(70, 15));
		lbl_DateIzm_1.setMaximumSize(new Dimension(32767, 14));
		lbl_DateIzm_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateIzm_1.setAlignmentX(0.5f);
		panel_Date_Label.add(lbl_DateIzm_1);

		JLabel lbl_DateIzm = new JLabel("До Дата");
		lbl_DateIzm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateIzm.setMaximumSize(new Dimension(32767, 14));
		lbl_DateIzm.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateIzm.setPreferredSize(new Dimension(70, 15));
		panel_Date_Label.add(lbl_DateIzm);
		
		JLabel lblNewLabel_1 = new JLabel("Бр. дни");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setMinimumSize(new Dimension(80, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(40, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_Label.add(lblNewLabel_1);
		
		panel.add(panel_Date_Label);
	}
	
	private void setPanel_EdnokratnoMeasur_ToPrePrevios(JPanel panel_DateMeasur) {
		JPanel panel_DateNawToPrePrev = new JPanel();
		panel_DateNawToPrePrev.setPreferredSize(new Dimension(10, 18));
		panel_DateNawToPrePrev.setMinimumSize(new Dimension(10, 18));
		panel_DateNawToPrePrev.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		
		panel_DateNawToPrePrev.setMaximumSize(new Dimension(32767, 18));
		FlowLayout fl_panel_DateNawToPrePrev = (FlowLayout) panel_DateNawToPrePrev.getLayout();
		fl_panel_DateNawToPrePrev.setVgap(1);
		fl_panel_DateNawToPrePrev.setHgap(2);
		fl_panel_DateNawToPrePrev.setAlignment(FlowLayout.RIGHT);
		
		lbl_DateNawToPrePrevios = new JLabel("");
		lbl_DateNawToPrePrevios.setPreferredSize(new Dimension(70, 15));
		lbl_DateNawToPrePrevios.setMaximumSize(new Dimension(32767, 14));
		lbl_DateNawToPrePrevios.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateNawToPrePrevios.setAlignmentX(0.5f);
		panel_DateNawToPrePrev.add(lbl_DateNawToPrePrevios);

		lbl_DateToPrePrevios = new JLabel();
		lbl_DateToPrePrevios.setBackground(new Color(255, 255, 255));
		lbl_DateToPrePrevios.setOpaque(true);
		lbl_DateToPrePrevios.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_DateToPrePrevios.setPreferredSize(new Dimension(70, 16));
		lbl_DateToPrePrevios.setMinimumSize(new Dimension(80, 16));
		lbl_DateToPrePrevios.setMaximumSize(new Dimension(32767, 16));
		lbl_DateToPrePrevios.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateToPrePrevios.setAlignmentX(0.5f);
		panel_DateNawToPrePrev.add(lbl_DateToPrePrevios);
		
		lbl_Count_DayToPrePrevios = new JLabel();
		lbl_Count_DayToPrePrevios.setBackground(new Color(255, 255, 255));
		lbl_Count_DayToPrePrevios.setOpaque(true);
		lbl_Count_DayToPrePrevios.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_Count_DayToPrePrevios.setMinimumSize(new Dimension(80, 16));
		lbl_Count_DayToPrePrevios.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Count_DayToPrePrevios.setPreferredSize(new Dimension(35, 16));
		lbl_Count_DayToPrePrevios.setMaximumSize(new Dimension(32767, 16));
		lbl_Count_DayToPrePrevios.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_DateNawToPrePrev.add(lbl_Count_DayToPrePrevios);
	
		panel_DateMeasur.add(panel_DateNawToPrePrev);
	}
	
	private void setPanel_EdnokratnoMeasur_ToPrevios(JPanel panel_DateMeasur) {
		JPanel panel_DateNawToPrev = new JPanel();
		panel_DateNawToPrev.setPreferredSize(new Dimension(10, 18));
		panel_DateNawToPrev.setMinimumSize(new Dimension(10, 18));
		
		panel_DateNawToPrev.setMaximumSize(new Dimension(32767, 18));
		FlowLayout fl_panel_DateNawToPrev = (FlowLayout) panel_DateNawToPrev.getLayout();
		fl_panel_DateNawToPrev.setAlignOnBaseline(true);
		fl_panel_DateNawToPrev.setVgap(1);
		fl_panel_DateNawToPrev.setHgap(2);
		fl_panel_DateNawToPrev.setAlignment(FlowLayout.RIGHT);
		
		lbl_DateNawToPrevios = new JLabel();
		lbl_DateNawToPrevios.setPreferredSize(new Dimension(70, 15));
		lbl_DateNawToPrevios.setMaximumSize(new Dimension(32767, 14));
		lbl_DateNawToPrevios.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateNawToPrevios.setAlignmentX(0.5f);
		panel_DateNawToPrev.add(lbl_DateNawToPrevios);

		lbl_DateToPrevios = new JLabel();
		lbl_DateToPrevios.setOpaque(true);
		lbl_DateToPrevios.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_DateToPrevios.setBackground(new Color(255, 255, 255));
		lbl_DateToPrevios.setPreferredSize(new Dimension(70, 16));
		lbl_DateToPrevios.setMinimumSize(new Dimension(80, 16));
		lbl_DateToPrevios.setMaximumSize(new Dimension(32767, 16));
		lbl_DateToPrevios.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateToPrevios.setAlignmentX(0.5f);
		panel_DateNawToPrev.add(lbl_DateToPrevios);
		
		lbl_Count_DayToPrevios = new JLabel();
		lbl_Count_DayToPrevios.setOpaque(true);
		lbl_Count_DayToPrevios.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_Count_DayToPrevios.setBackground(new Color(255, 255, 255));
		lbl_Count_DayToPrevios.setMinimumSize(new Dimension(80, 16));
		lbl_Count_DayToPrevios.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Count_DayToPrevios.setPreferredSize(new Dimension(35, 16));
		lbl_Count_DayToPrevios.setMaximumSize(new Dimension(32767, 16));
		lbl_Count_DayToPrevios.setAlignmentX(2.0f);
		panel_DateNawToPrev.add(lbl_Count_DayToPrevios);
		
		panel_DateMeasur.add(panel_DateNawToPrev);
	}
	
	private void setPanel_EdnokratnoMeasur_Button_ToPrevios(JPanel panel_DateMeasur) {
		JPanel panelButton_DateNawToPrev = new JPanel();
		panelButton_DateNawToPrev.setMinimumSize(new Dimension(10, 16));
		
		panelButton_DateNawToPrev.setMaximumSize(new Dimension(32767, 16));
		FlowLayout fl_panel_DateNawToPrePrev = (FlowLayout) panelButton_DateNawToPrev.getLayout();
		fl_panel_DateNawToPrePrev.setVgap(1);
		fl_panel_DateNawToPrePrev.setHgap(2);
		fl_panel_DateNawToPrePrev.setAlignment(FlowLayout.RIGHT);
		
	
		panel_DateMeasur.add(panelButton_DateNawToPrev);
		
		buttonToPreviosMinus = new JButton("-");
		buttonToPreviosMinus.setMargin(new Insets(2, 2, 2, 2));
		buttonToPreviosMinus.setPreferredSize(new Dimension(30, 14));
		buttonToPreviosMinus.setMinimumSize(new Dimension(30, 14));
		buttonToPreviosMinus.setMaximumSize(new Dimension(30, 14));
		panelButton_DateNawToPrev.add(buttonToPreviosMinus);
		
		buttonToPreviosPlus = new JButton("+");
		buttonToPreviosPlus.setMargin(new Insets(2, 2, 2, 2));
		buttonToPreviosPlus.setPreferredSize(new Dimension(30, 14));
		buttonToPreviosPlus.setMinimumSize(new Dimension(30, 14));
		buttonToPreviosPlus.setMaximumSize(new Dimension(30, 14));
		panelButton_DateNawToPrev.add(buttonToPreviosPlus);
	}
	
	
	
	public static JButton getButtonToPreviosPlus() {
		return buttonToPreviosPlus;
	}


	private void setPanel_EdnokratnoMeasur_Button_DateNawToPrePrev(JPanel panel_DateMeasur) {
		JPanel panel_Button_DateNawToPrePrev = new JPanel();
		panel_Button_DateNawToPrePrev.setMinimumSize(new Dimension(10, 16));
		
		panel_Button_DateNawToPrePrev.setMaximumSize(new Dimension(32767, 16));
		FlowLayout fl_panel_DateNawToPrePrev = (FlowLayout) panel_Button_DateNawToPrePrev.getLayout();
		fl_panel_DateNawToPrePrev.setVgap(1);
		fl_panel_DateNawToPrePrev.setHgap(2);
		fl_panel_DateNawToPrePrev.setAlignment(FlowLayout.RIGHT);
		
	
		panel_DateMeasur.add(panel_Button_DateNawToPrePrev);
		
		buttonToPrePreviosMinus = new JButton("-");
		buttonToPrePreviosMinus.setMargin(new Insets(2, 2, 2, 2));
		buttonToPrePreviosMinus.setPreferredSize(new Dimension(30, 14));
		buttonToPrePreviosMinus.setMinimumSize(new Dimension(30, 14));
		buttonToPrePreviosMinus.setMaximumSize(new Dimension(30, 14));
		panel_Button_DateNawToPrePrev.add(buttonToPrePreviosMinus);
		
		buttonToPrePreviosPlus = new JButton("+");
		buttonToPrePreviosPlus.setMargin(new Insets(2, 2, 2, 2));
		buttonToPrePreviosPlus.setPreferredSize(new Dimension(30, 14));
		buttonToPrePreviosPlus.setMinimumSize(new Dimension(30, 14));
		buttonToPrePreviosPlus.setMaximumSize(new Dimension(30, 14));
		panel_Button_DateNawToPrePrev.add(buttonToPrePreviosPlus);
	}
	
	
	
	private void setPanel_MnogokratnoMeasur_dateLabel(JPanel panel) {
		JPanel panel_Date_Label = new JPanel();
		panel_Date_Label.setMaximumSize(new Dimension(32767, 15));
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_Label.getLayout();
		fl_panel_Label_NuclideData.setVgap(1);
		fl_panel_Label_NuclideData.setHgap(1);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);

		JLabel lblNewLabel_1 = new JLabel("Списък");
		lblNewLabel_1.setMinimumSize(new Dimension(80, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(80, 15));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, 14));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Date_Label.add(lblNewLabel_1);

		JLabel lbl_DateIzm = new JLabel("Дата");
		lbl_DateIzm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateIzm.setMaximumSize(new Dimension(32767, 14));
		lbl_DateIzm.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateIzm.setPreferredSize(new Dimension(65, 15));
		panel_Date_Label.add(lbl_DateIzm);
		
		panel.add(panel_Date_Label);
	}
	
	private void setPanel_MnogokratnoMeasur(JPanel panel_DateMeasur) {
		JPanel panel_Date_MeasurNaw = new JPanel();
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Date_MeasurNaw.getLayout();
		fl_panel_Label_NuclideData.setVgap(0);
		fl_panel_Label_NuclideData.setHgap(2);
		fl_panel_Label_NuclideData.setAlignment(FlowLayout.LEFT);
		panel_DateMeasur.add(panel_Date_MeasurNaw);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(85, 65));
		panel_Date_MeasurNaw.add(scrollPane);
//		String[] array = {"13,33,3333","23,33,3333","33,33,3333","43,33,3333","53,33,3333","63,33,3333"};
		jlist = new JList<>();
		scrollPane.setViewportView(jlist);
		
		
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setPreferredSize(new Dimension(95, 65));
		panel.setMinimumSize(new Dimension(70, 60));
		panel_Date_MeasurNaw.add(panel);
		
		textField_MnogokratnoMeasur = new JTextField();
		textField_MnogokratnoMeasur.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_MnogokratnoMeasur.setColumns(8);
		panel.add(textField_MnogokratnoMeasur);
		
		lbl_Icon_MnogokratnoMeasur = new JLabel(pic);
		lbl_Icon_MnogokratnoMeasur.setPreferredSize(new Dimension(20, 20));
		lbl_Icon_MnogokratnoMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Icon_MnogokratnoMeasur.setBorder(null);
		lbl_Icon_MnogokratnoMeasur.setAlignmentX(1.0f);
		panel.add(lbl_Icon_MnogokratnoMeasur);
		
		JLabel lbldistans = new JLabel("");
		lbldistans.setPreferredSize(new Dimension(35, 0));
		panel.add(lbldistans);
		
		btn_MnogokratnoMeasur = new JButton("Вмъкни");
		btn_MnogokratnoMeasur.setMinimumSize(new Dimension(69, 18));
		btn_MnogokratnoMeasur.setMaximumSize(new Dimension(69, 18));
		btn_MnogokratnoMeasur.setPreferredSize(new Dimension(55, 15));
		btn_MnogokratnoMeasur.setMargin(new Insets(2, 2, 2, 2));
		panel.add(btn_MnogokratnoMeasur);
		
		lbl_ErrorInfo_MnogokratnoMeasur = new JLabel("");
		lbl_ErrorInfo_MnogokratnoMeasur.setForeground(new Color(255, 0, 0));
		lbl_ErrorInfo_MnogokratnoMeasur.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbl_ErrorInfo_MnogokratnoMeasur.setPreferredSize(new Dimension(90, 25));
		panel.add(lbl_ErrorInfo_MnogokratnoMeasur);
	}


	private void ActionListenerMinusBTN(int index) {
		String[][] masiveFromDozeArtFrame = DozeArt_Methods.readMasiveDataFromDozeArtFrame();
		String[][] masiveMinus = new String[masiveFromDozeArtFrame.length-1][5];
		int k=0;
		for (int i = 0; i < masiveFromDozeArtFrame.length; i++) {
			if(i != index) {
				masiveMinus[k] = masiveFromDozeArtFrame[i];
						k++;
			}
			
		}
		lbl_DozeAll.setText("");
		viewNewPanel(masiveMinus, null);
		DozeArt_Methods.clearCalculationData();
	}


	private void ActionListenerPlusBTN() {
		
		String[][] masiveFromDozeArtFrame = DozeArt_Methods.readMasiveDataFromDozeArtFrame();
		String[][] masivePlus = new String[masiveFromDozeArtFrame.length+1][5];	
		for (int i = 0; i < masiveFromDozeArtFrame.length; i++) {
			masivePlus[i] = masiveFromDozeArtFrame[i];
		}
		lbl_DozeAll.setText("");
		viewNewPanel(masivePlus, null);
		DozeArt_Methods.clearCalculationData();
		
	}

	
	
	
	

	public static JTextField getTextField_Date_MeasurNaw() {
		return textField_Date_MeasurNaw;
	}


	public static JTextField getTextField_dateMeasurPrev() {
		return textField_dateMeasurPrev;
	}


	public static JTextField getTextField_dateMeasurPrePrev() {
		return textField_dateMeasurPrePrev;
	}


	public static JTextField getTextField_dateMeasur_Start() {
		return textField_dateMeasur_Start;
	}


	public static JTextField getTextField_dateMeasur_End() {
		return textField_dateMeasur_End;
	}


	public static JLabel getLbl_Icon_Naw() {
		return lbl_Icon_Naw;
	}


	public static JLabel getLbl_Icon_Pred() {
		return lbl_Icon_Pred;
	}


	public static JLabel getLbl_Icon_PrePred() {
		return lbl_Icon_PrePred;
	}


	public static JLabel getLbl_Icon_Start() {
		return lbl_Icon_Start;
	}


	public static JLabel getLbl_Icon_End() {
		return lbl_Icon_End;
	}


	public static JPanel getPanel_Date() {
		return panel_Date;
	}


	public static JLayeredPane getLayeredPane_Postaplenie() {
		return layeredPane_Postaplenie;
	}


	public static JPanel getPanel_Rutinen() {
		return panel_Rutinen;
	}


	public static JPanel getPanel_Secialen() {
		return panel_Secialen;
	}


	public static JLayeredPane getLayeredPane_Mnogokratno() {
		return layeredPane_Mnogokratno;
	}


	public static JPanel getPanel_EdnokratnoMeasur() {
		return panel_EdnokratnoMeasur;
	}


	public static JPanel getPanel_MnogokratnoMeasur() {
		return panel_MnogokratnoMeasur;
	}


	public JTextField getTextField_MnogokratnoMeasur() {
		return textField_MnogokratnoMeasur;
	}


	public static JLabel getLbl_Icon_MnogokratnoMeasur() {
		return lbl_Icon_MnogokratnoMeasur;
	}


	public static JButton getBtnNewButton() {
		return btn_MnogokratnoMeasur;
	}


	public static JPanel getPanel_FreePanelMeasur() {
		return panel_FreePanelMeasur;
	}


	public static JRadioButton getRdbtnSpecialen() {
		return rdbtnSpecialen;
	}


	public static JRadioButton getRdbtnRutinen() {
		return rdbtnRutinen;
	}


	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}


	public static JRadioButton getRdbtnEdnokratno() {
		return rdbtnEdnokratno;
	}


	public static JRadioButton getRdbtnMnogokratno() {
		return rdbtnMnogokratno;
	}


	public static JRadioButton getRdbtnNeprekasnato() {
		return rdbtnNeprekasnato;
	}


	public static JRadioButton getRdbtnSrTotchka() {
		return rdbtnSrTotchka;
	}


	public ButtonGroup getButtonGroup2() {
		return buttonGroup2;
	}


	
	public static JLabel getLbl_Monitoring() {
		return lbl_Monitoring;
	}


	public static JPanel getPanel_Postaplenie_Free() {
		return panel_Postaplenie_Free;
	}

	public static JLabel getLbl_DateToPrePrevios() {
		return lbl_DateToPrePrevios;
	}


	public static JLabel getLbl_Count_DayToPrePrevios() {
		return lbl_Count_DayToPrePrevios;
	}


	public static JLabel getLbl_DateToPrevios() {
		return lbl_DateToPrevios;
	}

	


	public static JLabel getLbl_Count_DayToPrevios() {
		return lbl_Count_DayToPrevios;
	}


	public static JLabel getLbl_DateNawToPrePrevios() {
		return lbl_DateNawToPrePrevios;
	}


	public static JLabel getLbl_DateNawToPrevios() {
		return lbl_DateNawToPrevios;
	}


	public static JLabel getLbl_DateErrorInfo() {
		return lbl_DateErrorInfo;
	}
	
	public static JLabel getLbl_DateErrorInfo_Special() {
		return lbl_DateErrorInfo_Special;
	}


	public static JList<String> getJlist() {
		return jlist;
	}


	public static JLabel getLbl_ErrorInfo_MnogokratnoMeasur() {
		return lbl_ErrorInfo_MnogokratnoMeasur;
	}


	public static Choice[] getChoice_NuclideName() {
		return choice_NuclideName;
	}


	public static Choice[] getChoice_AMAD() {
		return choice_AMAD;
	}


	public static int getCountNuclide() {
		return countNuclide;
	}


	public static JTextField[] getTextField_Activity() {
		return textField_Activity;
	}


	public static JTextField[] getTextField_PreviosPostaplenie() {
		return textField_PreviosPostaplenie;
	}


	@SuppressWarnings("rawtypes")
	public static JComboBox[] getCmbBox_ActivityDimencion() {
		return cmbBox_ActivityDimencion;
	}


	public static JLabel[] getLbl_PostaplenieBq() {
		return lbl_PostaplenieBq;
	}


	public static JLabel[] getLbl_PostaplenieMCi() {
		return lbl_PostaplenieMCi;
	}


	public static JLabel[] getLbl_GGPCalc() {
		return lbl_GGPCalc;
	}


	public static JLabel[] getLbl_DozaNuclide() {
		return lbl_DozaNuclide;
	}


	public static Object[] getMasive_FromDatePanel() {
		return masive_FromDatePanel;
	}


	public static JLabel getLbl_DozeAll() {
		return lbl_DozeAll;
	}


	public static JLabel getLbl_InfoPersonMeasur() {
		return lbl_InfoPersonMeasur;
	}


	public static JButton getBtn_Calculation() {
		return btn_Calculation;
	}


	public static JButton getBtn_Report() {
		return btn_Report;
	}


	public String[][] getDataForAutoInsertMeasur() {
		return dataForAutoInsertMeasur;
	}


	public static JButton getButtonToPrePreviosMinus() {
		return buttonToPrePreviosMinus;
	}


	public static JButton getButtonToPrePreviosPlus() {
		return buttonToPrePreviosPlus;
	}


	public static JButton getButtonToPreviosMinus() {
		return buttonToPreviosMinus;
	}
}
