package ResultFromMeasuringReference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;

public class ResultFromMeasuringReferenceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JLayeredPane panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel save_Panel;
	private static JPanel infoPanel;
	private static JPanel tablePane;
	private static JScrollPane scrollPane;

	private static JTextArea textArea;
	
	private static JButton btn_Export;
	private JLabel lbl_Period_1;
	
	
	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
	static Date curentDate = Calendar.getInstance().getTime();
	
		
	static String oldOtdelPerson;
	static Border defoutBorder;
	private JTextField textFieldGodina;
	
	private static JLabel lblNewLabel_1;
	
	private static JButton btnBackToTable;
	
	
	private static JTextField txtStartDate;
	private static JTextField txtEndDate;

	private static JButton btn_SearchMeasur;
private JProgressBar progressBar;

	private static String referencePersonMeasur_startDate = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_startDate");
	private static String labelReferenceKontrolPerson_Peroid = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("labelReferenceKontrolPerson_Peroid");
	private static String referencePersonMeasur_endDate = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_endDate");
	
	
	
	

	public ResultFromMeasuringReferenceFrame(ActionIcone round) {
		
		String resultFromMeasuringReference_Text = ReadFileBGTextVariable.getGlobalTextVariableMap().get("resultFromMeasuringReference_Text");
		
		setTitle(resultFromMeasuringReference_Text);
		
		setMinimumSize(new Dimension(730, 900));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel_Search = new JPanel();
		panel_Search.setLayout(new BoxLayout(panel_Search, BoxLayout.Y_AXIS));
		contentPane.add(panel_Search, BorderLayout.NORTH);
		
		panel_AllSaerch = new JLayeredPane();
		contentPane.add(panel_AllSaerch, BorderLayout.CENTER);
		panel_AllSaerch.setLayout(null);
		
		
		tablePane = new JPanel();
		tablePane.setBounds(0, 0, 880, 723);
		panel_AllSaerch.setLayer(tablePane, 1);
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablePane.add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		
		infoPanel = new JPanel();
		infoPanel.setBounds(0, 0, 880, 723);
		panel_AllSaerch.setLayer(infoPanel, 4);
		infoPanel.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		
		textArea = new JTextArea();
		
		textArea.setEditable(false);
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);
		
		panel_2();
		panel_2A();
		
		progressBar = new JProgressBar(0,100);
		 progressBar.setValue(0);
	     progressBar.setStringPainted(true);
		panel_Search.add(progressBar);
		
		save_Panel();

		setSize(909, 900);
		setLocationRelativeTo(null);
		
		Metods_ResultFromMeasuringReference.setDataTable(null);
		Metods_ResultFromMeasuringReference.ActionListener_Btn_ReadMeasur( btn_SearchMeasur,  textArea,  
												 infoPanel, tablePane,  panel_AllSaerch,  scrollPane, txtStartDate, txtEndDate, btnBackToTable, progressBar);
		
		Metods_ResultFromMeasuringReference.ActionListenerBtnBackToTable(btnBackToTable, textArea, tablePane,
									panel_AllSaerch, scrollPane, textFieldGodina);
		
		Metods_ResultFromMeasuringReference.ActionListener_Btn_Export(btn_Export, save_Panel, save_Panel);
		
		Metods_ResultFromMeasuringReference.ActionListenerChengePanelSizeByFrameSize(this, tablePane, infoPanel);
		
		
		Metods_ResultFromMeasuringReference.ActionListenerSetDateByDatePicker(txtStartDate, btn_SearchMeasur);
		Metods_ResultFromMeasuringReference.ActionListenerSetDateByDatePicker(txtEndDate, btn_SearchMeasur);
		
		
		setVisible(true);
		round.StopWindow();
		
		
	}

	private JPanel panel_2() {


		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);

		JLabel lbl_Period = new JLabel();
		lbl_Period.setSize(new Dimension(120, 20));
		lbl_Period.setPreferredSize(new Dimension(102, 15));
		lbl_Period.setMinimumSize(new Dimension(120, 20));
		lbl_Period.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Period.setBorder(null);
		lbl_Period.setAlignmentX(0.5f);
		panel2.add(lbl_Period);

		JLabel lbl_StartDate = new JLabel(referencePersonMeasur_startDate);
		lbl_StartDate.setSize(new Dimension(80, 20));
		lbl_StartDate.setPreferredSize(new Dimension(70, 15));
		lbl_StartDate.setMinimumSize(new Dimension(80, 20));
		lbl_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_StartDate.setBorder(null);
		lbl_StartDate.setAlignmentX(0.5f);
		panel2.add(lbl_StartDate);

		JLabel lbl_EndDate = new JLabel(referencePersonMeasur_endDate);
		lbl_EndDate.setSize(new Dimension(80, 20));
		lbl_EndDate.setPreferredSize(new Dimension(70, 15));
		lbl_EndDate.setMinimumSize(new Dimension(80, 20));
		lbl_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EndDate.setBorder(null);
		lbl_EndDate.setAlignmentX(0.5f);
		panel2.add(lbl_EndDate);

		JLabel lblNewLabel_1_1 = new JLabel("");
		panel2.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setPreferredSize(new Dimension(150, 14));

		return panel2;
	}

	private JPanel panel_2A() {
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		
		JPanel panel2A = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel2A.getLayout();
		flowLayout_2.setVgap(2);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel2A.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel2A);
		
		lbl_Period_1 = new JLabel(labelReferenceKontrolPerson_Peroid);
		lbl_Period_1.setSize(new Dimension(120, 20));
		lbl_Period_1.setPreferredSize(new Dimension(102, 15));
		lbl_Period_1.setMinimumSize(new Dimension(120, 20));
		lbl_Period_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Period_1.setBorder(null);
		lbl_Period_1.setAlignmentX(0.5f);
		panel2A.add(lbl_Period_1);
				
		
		txtStartDate = new JTextField("01.01."+curentYear);
		txtStartDate.setPreferredSize(new Dimension(8, 20));
		txtStartDate.setMinimumSize(new Dimension(5, 20));
		txtStartDate.setColumns(8);
		panel2A.add(txtStartDate);

		txtEndDate = new JTextField(sdf2.format(curentDate));
		txtEndDate.setPreferredSize(new Dimension(5, 20));
		txtEndDate.setMinimumSize(new Dimension(5, 20));
		txtEndDate.setColumns(8);
		panel2A.add(txtEndDate);
				

		JLabel lblNewLabel_1_1 = new JLabel();
		lblNewLabel_1_1.setPreferredSize(new Dimension(147, 14));
		panel2A.add(lblNewLabel_1_1);
		
				btn_SearchMeasur = new JButton("Старт");
				panel2A.add(btn_SearchMeasur);
				btn_SearchMeasur.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btn_SearchMeasur.setMargin(new Insets(2, 5, 2, 5));
				btn_SearchMeasur.setIconTextGap(1);
				btn_SearchMeasur.setPreferredSize(new Dimension(125, 23));
//				btn_SearchMeasur.setEnabled(false);

				btnBackToTable = new JButton("Към таблицата");
				btnBackToTable.setMargin(new Insets(2, 5, 2, 5));
				btnBackToTable.setPreferredSize(new Dimension(125, 23));
		panel2A.add(btnBackToTable);

		btnBackToTable.setEnabled(false);
		

		checkorektDate(txtStartDate);
		checkorektDate(txtEndDate);
		return panel2A;
	}

	
	public static void checkorektDate(JTextField textFieldDate) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SearchMeasur.setEnabled(false);
					

				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SearchMeasur.setEnabled(true);
					
				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}
	

	private JPanel save_Panel() {

		save_Panel = new JPanel();
		getContentPane().add(save_Panel, BorderLayout.SOUTH);
		save_Panel.setLayout(new BoxLayout(save_Panel, BoxLayout.Y_AXIS));

		button_Panel(save_Panel);

		return save_Panel;
	}

	
	
	private JPanel button_Panel(JPanel save_Panel) {
		
		
		JPanel button_Panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) button_Panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		save_Panel.add(button_Panel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(65, 23));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Destroy the JFrame object
			}
		});
		button_Panel.add(cancelButton);
		
	
		btn_Export = new JButton("Export");
		button_Panel.add(btn_Export);
		
		return button_Panel;
	}



	public static String getOldOtdelPerson() {
		return oldOtdelPerson;
	}

	public static void setOldOtdelPerson(String oldOtdelPerson1) {
		oldOtdelPerson = oldOtdelPerson1;
	}


	static void viewTablePanel() {
		
		panel_AllSaerch.setLayer(tablePane, 4, 0);
		panel_AllSaerch.setLayer(infoPanel, 2, 0);
		infoPanel.setVisible(false);
		tablePane.setVisible(true);
		
	}

	static void viewInfoPanel() {
		
		panel_AllSaerch.setLayer(infoPanel, 4, 0);
		panel_AllSaerch.setLayer(tablePane, 1, 0);
		tablePane.setVisible(false);
		infoPanel.setVisible(true);
		
	}

	
	
	public static Border getDefoutBorder() {
		return defoutBorder;
	}



	public static JLabel getLbl_OtdelKodeArea() {
		return lblNewLabel_1;
	}

	public static JButton getBtnBackToTable() {
		return btnBackToTable;
	}



	public static JButton getBtn_Export() {
		return btn_Export;
	}



	public static JTextArea getTextArea() {
		return textArea;
	}



	public static JPanel getTablePane() {
		return tablePane;
	}



	public static JLayeredPane getPanel_AllSaerch() {
		return panel_AllSaerch;
	}



	public static JScrollPane getScrollPane() {
		return scrollPane;
	}
}
