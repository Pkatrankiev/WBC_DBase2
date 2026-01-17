package CheckCodeByExcelFileAndOID;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicTable_PersonReference_PersonStatus.PersonReference_PersonStatus_Frame;
import BasicTable_PersonReference_PersonStatus.PersonReference_PersonStatus_Methods;
import WBCUsersLogin.WBCUsersLogin;

public class CheckCodeByExcelFileAndOID_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel save_Panel;
	private static JPanel infoPanel;
	private static JPanel tablePane;
	private JScrollPane scrollPane;

	private static JProgressBar progressBarA;
	private static JTextArea textArea;
	private static JButton btn_Export;

	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";

	
	static Border defoutBorder;
	private static JTextField textField_Year;

	private JButton btn_StartGenerateTable;

	private static Choice choice_Firm;

	List<String> listOtdelKz;
	List<String> listOtdelVO;
	List<String> listAdd;
	List<String> listFirm;
	private JLabel lblNewLabel;
	private JLabel lbl_EGN;
	private static JTextField textEGN;

	public CheckCodeByExcelFileAndOID_Frame(String dokumentStatusUser, ActionIcone round) {

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		setTitle(dokumentStatusUser);

		setMinimumSize(new Dimension(1120, 900));

		String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
		String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");

		listOtdelKz = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", AEC));
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);

		listOtdelVO = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", VO));
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
				
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

		textArea.setEditable(false);
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);

		tablePane = new JPanel();
		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablePane.add(scrollPane, BorderLayout.CENTER);

		listFirm = new ArrayList<>();
		listFirm.add("");
		listFirm.add(AEC);
		listFirm.add(VO);

		InsertStartPanel();
		
		ProgrressBarPanel();

		save_Panel();

		setSize(1120, 900);
		setLocationRelativeTo(null);

		CheckCodeByExcelFileAndOID_Methods.ActionListenertextField_Year(textField_Year, btn_StartGenerateTable);
		CheckCodeByExcelFileAndOID_Methods.ActionListener_Btn_StartGenerateTable_PersonStatus(btn_StartGenerateTable, panel_AllSaerch, tablePane, sp, progressBarA);
		
		PersonReference_PersonStatus_Methods.ActionListener_Btn_ExportToExcel(btn_Export, tablePane);
		
		setVisible(true);
		round.StopWindow();

	}

	private JPanel InsertStartPanel() {
		JPanel panel_4 = new JPanel();
		FlowLayout fl_panel_4 = (FlowLayout) panel_4.getLayout();
		fl_panel_4.setAlignment(FlowLayout.LEFT);
		panel_4.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel_4);

		JLabel lbl_textField_Year = new JLabel("Година");
		lbl_textField_Year.setPreferredSize(new Dimension(50, 20));
		lbl_textField_Year.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_textField_Year.setBorder(null);
		lbl_textField_Year.setAlignmentX(0.5f);
		panel_4.add(lbl_textField_Year);

		textField_Year = new JTextField(curentYear);
		panel_4.add(textField_Year);
		textField_Year.setColumns(4);

		TextFieldJustNumbers(textField_Year);
		
		lbl_EGN = new JLabel("ЕГН");
		lbl_EGN.setPreferredSize(new Dimension(50, 20));
		lbl_EGN.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(0.5f);
		panel_4.add(lbl_EGN);
		
		textEGN = new JTextField();
		textEGN.setColumns(8);
		panel_4.add(textEGN);
		TextFieldJustNumbers(textEGN);
		
		
		JLabel lblNewLabel_3 = new JLabel("Фирма");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setPreferredSize(new Dimension(100, 20));
		panel_4.add(lblNewLabel_3);

		choice_Firm = new Choice();
		choice_Firm.setPreferredSize(new Dimension(150, 20));
		choice_Firm.add("АЕЦ Козлодуй");
		choice_Firm.add("Външни организации");
		panel_4.add(choice_Firm);

		addItem(choice_Firm, listFirm);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setPreferredSize(new Dimension(45, 10));
		panel_4.add(lblNewLabel_6);

		btn_StartGenerateTable = new JButton("Старт");
		panel_4.add(btn_StartGenerateTable);
		btn_StartGenerateTable.setPreferredSize(new Dimension(80, 20));
		btn_StartGenerateTable.setMargin(new Insets(2, 2, 2, 2));
	

		return panel_4;
	}

	private JPanel ProgrressBarPanel() {
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 20));
		panel_Search.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		progressBarA = new JProgressBar(0,100);
		 progressBarA.setValue(0);
	     progressBarA.setStringPainted(true);
		
		panel_5.add(progressBarA);
		return panel_5;
	}

	public void updateBar(int newValue) {
		progressBarA.setValue(newValue);
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
		flowLayout.setAlignment(FlowLayout.LEFT);
		save_Panel.add(button_Panel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(85, 23));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				PersonelManegementMethods.setChoisePerson("");
//				choisePerson = NewChoisePerson;
				dispose(); // Destroy the JFrame object
			}
		});
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setPreferredSize(new Dimension(863, 10));
		button_Panel.add(lblNewLabel);
		button_Panel.add(cancelButton);

		btn_Export = new JButton("Export");
		btn_Export.setPreferredSize(new Dimension(85, 23));
		button_Panel.add(btn_Export);
		btn_Export.setEnabled(false);

		return button_Panel;
	}

	

	public static Border getDefoutBorder() {
		return defoutBorder;
	}
	

	static void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));

		contentPane.repaint();
		contentPane.revalidate();
	}

	static void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));

		contentPane.repaint();
		contentPane.revalidate();
	}

	public static void TextFieldJustNumbers(JTextField field) {
		((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
	}

	
	
	

	private void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	public static ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
		ArrayList<String> list = new ArrayList<String>();
		for (Workplace workplace : valueWorkplaceByObject) {
			list.add(workplace.getOtdel());
		}
		return list;
	}

	
	public static JTextField getTextField_Year() {
		return textField_Year;
	}

	
	public static Choice getChoice_Firm() {
		return choice_Firm;
	}



	

	public static JProgressBar getProgressBar() {
		return progressBarA;
	}

	public static void setProgressBar(JProgressBar progressBar) {
		progressBarA = progressBar;
	}

	
	public static JButton getBtn_Export() {
		return btn_Export;
	}

	public static JTextField getTextEGN() {
		return textEGN;
	}
}
