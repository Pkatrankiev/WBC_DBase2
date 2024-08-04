package DozeArt;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Aplication.ReadFileBGTextVariable;
import DatePicker.DatePicker;
import PersonReference_PersonStatus.PersonReference_PersonStatus_Frame;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.FlowLayout;
import java.awt.Choice;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.Label;
import java.awt.Point;

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
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JComboBox;

public class DozeArtFrame extends JFrame {

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
	private static JRadioButton rdbtnSecialen;
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

	private static JScrollBar scrollBarToPrePrevios;
	private static JScrollBar scrollBarToPrevios;
	private JPanel panel_Nuclide_1;
	
	
	private static Choice choice_NuclideName;
	private static Choice choice_AMAD; 
	
	List<String>  listNuclideName = DozeArt_Methods.ReadNuclideList();
	
	
	public DozeArtFrame() {
		
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		
		setPreferredSize(new Dimension(690, 355));
		setMinimumSize(new Dimension(690, 355));
		setResizable(false);
		setMaximumSize(new Dimension(690, 355));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_All = new JPanel();
		panel_All.setLayout(new BoxLayout(panel_All, BoxLayout.Y_AXIS));
		contentPane.add(panel_All, BorderLayout.NORTH);

		setPanelDate(panel_All);

		setPanel_Nuclide(panel_All);
		
	
		
		DozeArt_Methods.ActionListenerChoiceNuclide(choice_NuclideName,choice_AMAD);
		
		DozeArt_Methods.ActionListenerMonitoringButtons(rdbtnRutinen, rdbtnSecialen);
		
		DozeArt_Methods.ActionListenerPostaplenieButtons(rdbtnEdnokratno, rdbtnMnogokratno, rdbtnNeprekasnato, rdbtnSrTotchka);
		
		DozeArt_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_Naw, textField_Date_MeasurNaw);
		DozeArt_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_Pred, textField_dateMeasurPrev);
		DozeArt_Methods.ActionListenerSetDateByDatePicker(lbl_Icon_PrePred, textField_dateMeasurPrePrev);
		
		DozeArt_Methods.scrollBarToPreviosListener(scrollBarToPrevios, lbl_DateToPrevios, lbl_Count_DayToPrevios);
		DozeArt_Methods.scrollBarToPrePreviosListener(scrollBarToPrePrevios, lbl_DateToPrePrevios, lbl_Count_DayToPrePrevios);
		
		DozeArt_Methods.ActionListenerSetDateByDatePicker_Spetial(lbl_Icon_Start, textField_dateMeasur_Start);
		DozeArt_Methods.ActionListenerSetDateByDatePicker_Spetial(lbl_Icon_End, textField_dateMeasur_End);
		
		DozeArt_Methods.ActionListenerSetDateByDatePicker_MnogokratnoMeasur(lbl_Icon_MnogokratnoMeasur, textField_MnogokratnoMeasur, btn_MnogokratnoMeasur);
		DozeArt_Methods.ActionListener_AddDateInJList(textField_MnogokratnoMeasur,btn_MnogokratnoMeasur,jlist);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	private void setPanel_Nuclide(JPanel panel_All) {
		panel_Nuclide_1 = new JPanel();
		panel_Nuclide_1.setLayout(new BoxLayout(panel_Nuclide_1, BoxLayout.Y_AXIS));
		contentPane.add(panel_All, BorderLayout.NORTH);
		panel_All.add(panel_Nuclide_1);

		setPanel_NuclideLabel(panel_Nuclide_1);

		setPanel_NuclieData(panel_Nuclide_1);
		
		JPanel panel_CalculateButtons = new JPanel();
		FlowLayout fl_panel_CalculateButtons = (FlowLayout) panel_CalculateButtons.getLayout();
		fl_panel_CalculateButtons.setAlignment(FlowLayout.RIGHT);
		panel_Nuclide_1.add(panel_CalculateButtons);
		
		JLabel lbl_DozeAllLabel = new JLabel("Обща Доза");
		lbl_DozeAllLabel.setPreferredSize(new Dimension(65, 20));
		panel_CalculateButtons.add(lbl_DozeAllLabel);
		
		JLabel lbl_DozeAll = new JLabel();
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
		
		JButton btn_Calculation = new JButton("Изчисление");
		btn_Calculation.setPreferredSize(new Dimension(80, 20));
		btn_Calculation.setMargin(new Insets(2, 2, 2, 2));
		panel_CalculateButtons.add(btn_Calculation);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setPanel_NuclieData(JPanel panel_Nuclide) {
		JPanel panel_Data_NuclideData = new JPanel();
		FlowLayout fl_panel_Data_NuclideData = (FlowLayout) panel_Data_NuclideData.getLayout();
		fl_panel_Data_NuclideData.setHgap(1);
		fl_panel_Data_NuclideData.setAlignment(FlowLayout.LEFT);
		panel_Nuclide.add(panel_Data_NuclideData);

		choice_NuclideName = new Choice();
		choice_NuclideName.setPreferredSize(new Dimension(60, 20));
		panel_Data_NuclideData.add(choice_NuclideName);
		
		for (String string : listNuclideName) {
			choice_NuclideName.add(string);
		}
		
		
		JLabel lblDistancia1 = new JLabel("");
		lblDistancia1.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1);

		choice_AMAD = new Choice();
		choice_AMAD.setPreferredSize(new Dimension(50, 20));
		panel_Data_NuclideData.add(choice_AMAD);
		
		
		JLabel lblDistancia1_1 = new JLabel("");
		lblDistancia1_1.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1_1);

		TextField textField_Activity = new TextField();
		textField_Activity.setPreferredSize(new Dimension(75, 20));
		panel_Data_NuclideData.add(textField_Activity);
		
		JLabel lblDistancia1_2 = new JLabel("");
		lblDistancia1_2.setPreferredSize(new Dimension(5, 0));
		panel_Data_NuclideData.add(lblDistancia1_2);

		TextField textField_PreviosPostaplenie = new TextField();
		textField_PreviosPostaplenie.setPreferredSize(new Dimension(75, 20));
		panel_Data_NuclideData.add(textField_PreviosPostaplenie);
		
		JComboBox cmbBox_ActivityDimencion = new JComboBox(dimentionStr);
		panel_Data_NuclideData.add(cmbBox_ActivityDimencion);
		
		JLabel lbl_IconCalc = new JLabel("");
		lbl_IconCalc.setPreferredSize(new Dimension(20, 14));
		panel_Data_NuclideData.add(lbl_IconCalc);
		
		JLabel lbl_PostaplenieBq = new JLabel();
		lbl_PostaplenieBq.setPreferredSize(new Dimension(70, 20));
		lbl_PostaplenieBq.setOpaque(true);
		lbl_PostaplenieBq.setMinimumSize(new Dimension(80, 16));
		lbl_PostaplenieBq.setMaximumSize(new Dimension(32767, 16));
		lbl_PostaplenieBq.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_PostaplenieBq.setBackground(Color.WHITE);
		lbl_PostaplenieBq.setAlignmentX(2.0f);
		panel_Data_NuclideData.add(lbl_PostaplenieBq);
		
		JLabel lbl_PostaplenieBqDimention = new JLabel("Bq");
		lbl_PostaplenieBqDimention.setPreferredSize(new Dimension(17, 20));
		panel_Data_NuclideData.add(lbl_PostaplenieBqDimention);
		
		JLabel lbl_PostaplenieMCi = new JLabel();
		lbl_PostaplenieMCi.setPreferredSize(new Dimension(70, 20));
		lbl_PostaplenieMCi.setOpaque(true);
		lbl_PostaplenieMCi.setMinimumSize(new Dimension(80, 16));
		lbl_PostaplenieMCi.setMaximumSize(new Dimension(32767, 16));
		lbl_PostaplenieMCi.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_PostaplenieMCi.setBackground(Color.WHITE);
		lbl_PostaplenieMCi.setAlignmentX(2.0f);
		panel_Data_NuclideData.add(lbl_PostaplenieMCi);
		
		JLabel lbl_PostaplenieMCiDimention = new JLabel("µCi");
		lbl_PostaplenieMCiDimention.setPreferredSize(new Dimension(17, 20));
		panel_Data_NuclideData.add(lbl_PostaplenieMCiDimention);
		
		JLabel lbl_GGPCalc = new JLabel();
		lbl_GGPCalc.setPreferredSize(new Dimension(35, 16));
		lbl_GGPCalc.setOpaque(true);
		lbl_GGPCalc.setMinimumSize(new Dimension(80, 16));
		lbl_GGPCalc.setMaximumSize(new Dimension(32767, 16));
		lbl_GGPCalc.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_GGPCalc.setBackground(Color.WHITE);
		lbl_GGPCalc.setAlignmentX(2.0f);
		panel_Data_NuclideData.add(lbl_GGPCalc);
		
		JLabel lbl_GGPDimencions = new JLabel("%");
		lbl_GGPDimencions.setPreferredSize(new Dimension(17, 14));
		panel_Data_NuclideData.add(lbl_GGPDimencions);
		
		JLabel lbl_DozaNuclide = new JLabel();
		lbl_DozaNuclide.setPreferredSize(new Dimension(35, 16));
		lbl_DozaNuclide.setOpaque(true);
		lbl_DozaNuclide.setMinimumSize(new Dimension(80, 16));
		lbl_DozaNuclide.setMaximumSize(new Dimension(32767, 16));
		lbl_DozaNuclide.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DozaNuclide.setBackground(Color.WHITE);
		lbl_DozaNuclide.setAlignmentX(2.0f);
		panel_Data_NuclideData.add(lbl_DozaNuclide);
		
		JLabel lbl_DozaNuclideDimencion = new JLabel("mSv");
		lbl_DozaNuclideDimencion.setPreferredSize(new Dimension(25, 14));
		panel_Data_NuclideData.add(lbl_DozaNuclideDimencion);
		
		JButton btn_MinusNuclide = new JButton("-");
		btn_MinusNuclide.setMargin(new Insets(2, 2, 2, 2));
		btn_MinusNuclide.setPreferredSize(new Dimension(15, 20));
		btn_MinusNuclide.setHorizontalAlignment(SwingConstants.CENTER);
		
		btn_MinusNuclide.setAlignmentX(1.0f);
		panel_Data_NuclideData.add(btn_MinusNuclide);

		btn_MinusNuclide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ActionListenerMinusBTN();
			}
		});

		JButton btn_PlusNuclide = new JButton("+");
		btn_PlusNuclide.setMargin(new Insets(2, 1, 2, 1));
		btn_PlusNuclide.setPreferredSize(new Dimension(15, 20));
		btn_PlusNuclide.setHorizontalAlignment(SwingConstants.CENTER);
	
		btn_PlusNuclide.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_Data_NuclideData.add(btn_PlusNuclide);
		btn_PlusNuclide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ActionListenerPlusBTN(index);
			}
		});
		
		
		
		
		
	}

	private void setPanel_NuclideLabel(JPanel panel_Nuclide) {
		JPanel panel_Label_NuclideData = new JPanel();
		FlowLayout fl_panel_Label_NuclideData = (FlowLayout) panel_Label_NuclideData.getLayout();
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
		
		rdbtnSecialen = new JRadioButton("Специален");
		rdbtnSecialen.setEnabled(false);
		panel_Monitoring.add(rdbtnSecialen);
		
		buttonGroup.add(rdbtnSecialen);
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
		layeredPane_Postaplenie.setLayer(panel_SpecialMeasur, 12);
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
		layeredPane_Mnogokratno.setLayer(panel__EdnokratnoMeasur, 2);
		panel__EdnokratnoMeasur.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel__EdnokratnoMeasur.setAlignmentX(5.0f);
		panel__EdnokratnoMeasur.setAlignmentY(5.0f);;
		panel__EdnokratnoMeasur.setBounds(0, 0, 190, 100);
		panel__EdnokratnoMeasur.setLayout(new BoxLayout(panel__EdnokratnoMeasur, BoxLayout.Y_AXIS));
		

		JLabel lbl_DateMeasur = new JLabel("Еднократно");
		lbl_DateMeasur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DateMeasur.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_DateMeasur.setMaximumSize(new Dimension(32767, 15));
		lbl_DateMeasur.setPreferredSize(new Dimension(80, 15));
		panel__EdnokratnoMeasur.add(lbl_DateMeasur);

		setPanel_EdnokratnoMeasur_dateLabel(panel__EdnokratnoMeasur);
		
		setPanel_EdnokratnoMeasur_ToPrevios(panel__EdnokratnoMeasur);
		
		scrollBarToPrevios = new JScrollBar();
		scrollBarToPrevios.setPreferredSize(new Dimension(10, 13));
		scrollBarToPrevios.setOrientation(JScrollBar.HORIZONTAL);
		panel__EdnokratnoMeasur.add(scrollBarToPrevios);
		
		setPanel_EdnokratnoMeasur_ToPrePrevios(panel__EdnokratnoMeasur);
		
		scrollBarToPrePrevios = new JScrollBar();
		scrollBarToPrePrevios.setPreferredSize(new Dimension(10, 13));
		scrollBarToPrePrevios.setOrientation(JScrollBar.HORIZONTAL);
		panel__EdnokratnoMeasur.add(scrollBarToPrePrevios);
		
		
		
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
		layeredPane_Mnogokratno.setLayer(FreePanelMeasur, 4);
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
		panel_DateNawToPrePrev.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		
		panel_DateNawToPrePrev.setMaximumSize(new Dimension(32767, 20));
		FlowLayout fl_panel_DateNawToPrePrev = (FlowLayout) panel_DateNawToPrePrev.getLayout();
		fl_panel_DateNawToPrePrev.setVgap(4);
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
		
		panel_DateNawToPrev.setMaximumSize(new Dimension(32767, 16));
		FlowLayout fl_panel_DateNawToPrev = (FlowLayout) panel_DateNawToPrev.getLayout();
		fl_panel_DateNawToPrev.setAlignOnBaseline(true);
		fl_panel_DateNawToPrev.setVgap(0);
		fl_panel_DateNawToPrev.setHgap(2);
		fl_panel_DateNawToPrev.setAlignment(FlowLayout.RIGHT);
		
		lbl_DateNawToPrevios = new JLabel("");
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


	public static JRadioButton getRdbtnSecialen() {
		return rdbtnSecialen;
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


	public static JScrollBar getScrollBarToPrePrevios() {
		return scrollBarToPrePrevios;
	}


	public static JScrollBar getScrollBarToPrevios() {
		return scrollBarToPrevios;
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


	public static JList<?> getJlist() {
		return jlist;
	}


	public static JLabel getLbl_ErrorInfo_MnogokratnoMeasur() {
		return lbl_ErrorInfo_MnogokratnoMeasur;
	}


	public static Choice getChoice_NuclideName() {
		return choice_NuclideName;
	}


	public static Choice getChoice_AMAD() {
		return choice_AMAD;
	}


	public static void setChoice_AMAD(Choice choice_AMAD) {
		DozeArtFrame.choice_AMAD = choice_AMAD;
	}
}
