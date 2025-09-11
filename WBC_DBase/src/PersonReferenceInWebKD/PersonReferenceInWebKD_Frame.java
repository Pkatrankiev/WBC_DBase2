package PersonReferenceInWebKD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;


public class PersonReferenceInWebKD_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;
	private JPanel tablePane;
	
	private static JTextArea textArea;
	private static JTextField textField_EGN;
	private static JTextField textField_FName;
	private static JTextField textField_SName;
	private static JTextField textField_LName;

	private static JButton btn_SearchDBase;

	private String notResults;

	ArrayList<String> listOtdelKz;
	List<String> listOtdelVO;
	List<String> listOtdelAll;
	List<String> listAdd;
	List<String> listFirm;
	String[][] dataTable;

	private static String referencePerson_EGN = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_EGN");
	private static String referencePerson_FirstName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_FirstName");
	private static String referencePerson_SecondName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SecondName");
	private static String referencePerson_LastName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_LastName");

	static String curentYear = AplicationMetods.getCurentYear();

	public PersonReferenceInWebKD_Frame(ActionIcone round, String title) {

	

		ChromeDriver driver = PersonReferenceInWebKD_Methods.openChromeDriver();

			if(PersonReferenceInWebKD_Methods.logInToWebSheet( round, driver)) {
						
				setTitle(title);
				setMinimumSize(new Dimension(740, 500));

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));

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

			tablePane = new JPanel();
			tablePane.setPreferredSize(new Dimension(10, 0));
			tablePane.setMaximumSize(new Dimension(32767, 0));
			panel_AllSaerch.add(tablePane);
			tablePane.setLayout(new BorderLayout(0, 0));

			panel_1();
			panel_1A(driver);

			getRootPane().setDefaultButton(btn_SearchDBase);

			panel_Button(driver);

			setSize(740, 500);
			setLocationRelativeTo(null);
			setVisible(true);
			round.StopWindow();
			
			  this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						dispose();
						driver.quit();
					}
				});
			
			
			}else {
				dispose();
				driver.quit();
			}

		

	}



	private JPanel panel_1() {

		JPanel panel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel1.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1);

		JLabel lbl_EGN = new JLabel(referencePerson_EGN);
		lbl_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EGN.setSize(new Dimension(80, 20));
		lbl_EGN.setPreferredSize(new Dimension(85, 15));
		lbl_EGN.setMinimumSize(new Dimension(80, 20));
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(lbl_EGN);

		JLabel distantion = new JLabel();
		distantion.setPreferredSize(new Dimension(30, 14));
		panel1.add(distantion);

		JLabel lbl_FirstName = new JLabel(referencePerson_FirstName);
		lbl_FirstName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_FirstName.setSize(new Dimension(220, 20));
		lbl_FirstName.setPreferredSize(new Dimension(126, 15));
		lbl_FirstName.setMinimumSize(new Dimension(220, 20));
		lbl_FirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_FirstName.setBorder(null);
		panel1.add(lbl_FirstName);

		JLabel lbl_L_SecondName = new JLabel(referencePerson_SecondName);
		lbl_L_SecondName.setSize(new Dimension(70, 20));
		lbl_L_SecondName.setPreferredSize(new Dimension(126, 15));
		lbl_L_SecondName.setMinimumSize(new Dimension(70, 20));
		lbl_L_SecondName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_SecondName.setBorder(null);
		lbl_L_SecondName.setAlignmentX(1.0f);
		panel1.add(lbl_L_SecondName);

		JLabel lbl_L_LastName = new JLabel(referencePerson_LastName);
		lbl_L_LastName.setSize(new Dimension(70, 20));
		lbl_L_LastName.setPreferredSize(new Dimension(126, 15));
		lbl_L_LastName.setMinimumSize(new Dimension(70, 20));
		lbl_L_LastName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_LastName.setBorder(null);
		lbl_L_LastName.setAlignmentX(1.0f);
		panel1.add(lbl_L_LastName);
		return panel1;
	}

	private JPanel panel_1A(ChromeDriver driver) {

		String referencePerson_SearchFromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SearchFromDBase");

		JPanel panel1A = new JPanel();
		panel1A.setPreferredSize(new Dimension(10, 30));
		FlowLayout fl_panel1A = (FlowLayout) panel1A.getLayout();
		fl_panel1A.setVgap(2);
		fl_panel1A.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1A);

		textField_EGN = new JTextField();
		textField_EGN.setPreferredSize(new Dimension(5, 20));
		textField_EGN.setMinimumSize(new Dimension(5, 20));
		panel1A.add(textField_EGN);
		textField_EGN.setColumns(10);

		TextFieldJustNumbers(textField_EGN);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setPreferredSize(new Dimension(29, 14));
		panel1A.add(lblNewLabel_1);

		textField_FName = new JTextField();
		textField_FName.setPreferredSize(new Dimension(5, 20));
		textField_FName.setMinimumSize(new Dimension(5, 20));
		textField_FName.setColumns(15);
		panel1A.add(textField_FName);

		textField_SName = new JTextField();
		textField_SName.setPreferredSize(new Dimension(5, 20));
		textField_SName.setMinimumSize(new Dimension(5, 20));
		textField_SName.setColumns(15);
		panel1A.add(textField_SName);

		textField_LName = new JTextField();
		textField_LName.setPreferredSize(new Dimension(5, 20));
		textField_LName.setMinimumSize(new Dimension(5, 20));
		textField_LName.setColumns(15);
		panel1A.add(textField_LName);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setPreferredSize(new Dimension(50, 14));
		panel1A.add(lblNewLabel_1_1);

		btn_SearchDBase = new JButton(referencePerson_SearchFromDBase);
		btn_SearchDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase.setIconTextGap(1);
		btn_SearchDBase.setPreferredSize(new Dimension(125, 23));
		panel1A.add(btn_SearchDBase);

		ActionListenerbBtn_SearchDBase(driver);

		return panel1A;
	}
	
	private JPanel panel_Button(ChromeDriver driver) {

//		String referencePerson_Export = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Export");

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Изход");
		cancelButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			driver.quit();
		}
	});
		buttonPanel.add(cancelButton);

		return buttonPanel;
	}

	protected static boolean allFieldsEmnty() {
		return (textField_EGN.getText().trim().isEmpty() && textField_FName.getText().trim().isEmpty()
				&& textField_SName.getText().trim().isEmpty() && textField_LName.getText().trim().isEmpty());
	}
	
	void ActionListenerbBtn_SearchDBase(ChromeDriver driver) {
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				 viewInfoPanel();
				
				if (!allFieldsEmnty()) {
					setWaitCursor(panel_AllSaerch);
					dataTable = null;
					textArea.setText("");
					System.out.println("11111111111111111");
					String egn = getTextField_EGN().getText();
					String firstName = getTextField_FName().getText();
					String secontName = getTextField_SName().getText();
					String lastName = getTextField_LName().getText();

					String[] masiveData =PersonReferenceInWebKD_Methods.extractedMasiveInfoPerson(driver, firstName, secontName, lastName, egn);
					
					if (masiveData[0] == null) {
						textArea.setText("Няма намерени резултати");
						textArea.setText(notResults);
						
						viewInfoPanel();

					}else {
						panel_infoPanelTablePanel(masiveData);
						viewTablePanel();
					}

//					  panel_infoPanelTablePanel(masiveData);

					setDefaultCursor(panel_AllSaerch);
				}
			}

		});

	}
	
	void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));

		repaint();
		revalidate();
	}

	private void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		

		repaint();
		revalidate();
	}
	
	private JPanel panel_infoPanelTablePanel(String[] dataInfoPerson) {

		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));

		JPanel panelPerson = new JPanel();
		panelPerson.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPerson.setMaximumSize(new Dimension(32767, 100));
		infoPane.add(panelPerson);
		panelPerson.setLayout(new BoxLayout(panelPerson, BoxLayout.X_AXIS));

		JLabel lblImagePerson = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("ICONS/men.png")));
		if(dataInfoPerson[9].equals("Женски")) {
			lblImagePerson = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("ICONS/women.png")));
		}
		BufferedImage image = null;
		try {
			if(dataInfoPerson[8] != null && !dataInfoPerson[8].isEmpty()) {
			image = PersonReferenceInWebKD_Methods.extractedImage(dataInfoPerson[8]);
			double aspectRatio = (double) image.getTileWidth() / image.getHeight();

			Image scalImage = image.getScaledInstance((int) (100 * aspectRatio), 100, Image.SCALE_SMOOTH);

			lblImagePerson = new JLabel(new ImageIcon(scalImage));
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		lblImagePerson.setMaximumSize(new Dimension(84, 100));
		lblImagePerson.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImagePerson.setMinimumSize(new Dimension(84, 100));
		lblImagePerson.setPreferredSize(new Dimension(84, 100));
		panelPerson.add(lblImagePerson);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setPreferredSize(new Dimension(10, 0));
		lblNewLabel.setMaximumSize(new Dimension(10, 14));
		panelPerson.add(lblNewLabel);

		JPanel panel_NamePerson = new JPanel();
		panelPerson.add(panel_NamePerson);
		panel_NamePerson.setLayout(new BoxLayout(panel_NamePerson, BoxLayout.Y_AXIS));

		JTextField lblNamePerson = new JTextField("Име:   " + dataInfoPerson[0].replace("&&", ""));
		lblNamePerson.setEditable(false);
		lblNamePerson.setBorder(null);
		lblNamePerson.setBackground(null);
		lblNamePerson.setFocusable(false);
		lblNamePerson.setMaximumSize(new Dimension(32767, 32767));
		panel_NamePerson.add(lblNamePerson);

		JTextField lblEGNPerson = new JTextField("ЕГН:   " + dataInfoPerson[1]);
		lblEGNPerson.setEditable(false);
		lblEGNPerson.setBorder(null);
		lblEGNPerson.setBackground(null);
		lblEGNPerson.setFocusable(false);
		lblEGNPerson.setMaximumSize(new Dimension(32767, 32767));
		panel_NamePerson.add(lblEGNPerson);

		JLabel lblEmptiLabel = new JLabel("");
		lblEmptiLabel.setMaximumSize(new Dimension(32767, 32767));
		panel_NamePerson.add(lblEmptiLabel);

		JPanel panel_InfoPerson = new JPanel();
		infoPane.add(panel_InfoPerson);
		panel_InfoPerson.setLayout(new BoxLayout(panel_InfoPerson, BoxLayout.Y_AXIS));

		JPanel panel_Workpl = new JPanel();
		panel_Workpl.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel_Workpl.setMaximumSize(new Dimension(32767, 25));
		FlowLayout fl_panel_Workpl = (FlowLayout) panel_Workpl.getLayout();
		fl_panel_Workpl.setAlignment(FlowLayout.LEFT);
		panel_InfoPerson.add(panel_Workpl);

		JLabel lblWorkpl = new JLabel("Предприятие:    ");
		lblWorkpl.setPreferredSize(new Dimension(130, 15));
		panel_Workpl.add(lblWorkpl);

		JLabel lblWorkplInfo = new JLabel(dataInfoPerson[2]);
		lblWorkplInfo.setPreferredSize(new Dimension(320, 20));

		panel_Workpl.add(lblWorkplInfo);

		JPanel panel_Zveno = new JPanel();
		panel_Zveno.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel_Zveno.setMaximumSize(new Dimension(32767, 25));
		FlowLayout fl_panel_Zveno = (FlowLayout) panel_Zveno.getLayout();
		fl_panel_Zveno.setAlignment(FlowLayout.LEFT);
		panel_InfoPerson.add(panel_Zveno);

		JLabel lblZveno = new JLabel("Звено:    ");
		lblZveno.setPreferredSize(new Dimension(130, 15));
		panel_Zveno.add(lblZveno);

		JLabel lblZvenoInfo = new JLabel(dataInfoPerson[3]);
		lblZvenoInfo.setPreferredSize(new Dimension(320, 20));
		panel_Zveno.add(lblZvenoInfo);

		JPanel panel_Dlajnost = new JPanel();
		panel_Dlajnost.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel_Dlajnost.setMaximumSize(new Dimension(32767, 25));
		FlowLayout fl_panel_Dlajnost = (FlowLayout) panel_Dlajnost.getLayout();
		fl_panel_Dlajnost.setAlignment(FlowLayout.LEFT);
		panel_InfoPerson.add(panel_Dlajnost);

		JLabel lblDlajnost = new JLabel("Длъжност:    ");
		lblDlajnost.setPreferredSize(new Dimension(130, 15));
		panel_Dlajnost.add(lblDlajnost);

		JLabel lblDlajnostInfo = new JLabel(dataInfoPerson[4]);
		lblDlajnostInfo.setPreferredSize(new Dimension(320, 20));
		panel_Dlajnost.add(lblDlajnostInfo);

		JPanel panel_STM = new JPanel();
		panel_STM.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel_STM.setMaximumSize(new Dimension(32767, 25));
		FlowLayout fl_panel_STM = (FlowLayout) panel_STM.getLayout();
		fl_panel_STM.setAlignment(FlowLayout.LEFT);
		panel_InfoPerson.add(panel_STM);

		JLabel lblSTM = new JLabel("Заключение СТМ:    ");
		lblSTM.setPreferredSize(new Dimension(130, 15));
		panel_STM.add(lblSTM);

		JLabel lblSTMInfo = new JLabel(dataInfoPerson[5]);
		lblSTMInfo.setMinimumSize(new Dimension(320, 14));
		lblSTMInfo.setMaximumSize(new Dimension(32767, 14));
		lblSTMInfo.setPreferredSize(new Dimension(320, 20));
		panel_STM.add(lblSTMInfo);

		JPanel panel_BotamInfo = new JPanel();
		panel_BotamInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout fl_panel_BotamInfo = (FlowLayout) panel_BotamInfo.getLayout();
		fl_panel_BotamInfo.setAlignment(FlowLayout.LEFT);
		panel_BotamInfo.setMaximumSize(new Dimension(32767, 90));
		panel_InfoPerson.add(panel_BotamInfo);

		JPanel panel_Kods = new JPanel();
		panel_Kods.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		panel_Kods.setPreferredSize(new Dimension(200, 60));
		panel_BotamInfo.add(panel_Kods);
		panel_Kods.setLayout(new BoxLayout(panel_Kods, BoxLayout.Y_AXIS));

		JLabel lblIDKodeHeader = new JLabel("ИД Кодове");
		lblIDKodeHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblIDKodeHeader.setHorizontalTextPosition(SwingConstants.CENTER);
		lblIDKodeHeader.setMaximumSize(new Dimension(190, 15));
		lblIDKodeHeader.setPreferredSize(new Dimension(190, 15));
		panel_Kods.add(lblIDKodeHeader);

		String[] kode = dataInfoPerson[6].split("@2#");
		for (int i = 0; i < kode.length; i++) {
			JLabel lblIDKodeInfo = new JLabel(kode[i].replace("@#", ""));
			lblIDKodeInfo.setPreferredSize(new Dimension(190, 15));
			lblIDKodeInfo.setMaximumSize(new Dimension(190, 15));
			panel_Kods.add(lblIDKodeInfo);
		}

		JPanel panel_Measur = new JPanel();
		panel_Measur.setPreferredSize(new Dimension(255, 60));
		panel_BotamInfo.add(panel_Measur);
		panel_Measur.setLayout(new BoxLayout(panel_Measur, BoxLayout.Y_AXIS));
		
		JLabel lblLastMeasur = new JLabel("Последно измерване: "+dataInfoPerson[7]);
		lblLastMeasur.setPreferredSize(new Dimension(100, 20));
		panel_Measur.add(lblLastMeasur);

		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));
		tablePane.add(infoPane, BorderLayout.CENTER);

		return infoPane;

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
	
	public static void setWaitCursor(JPanel panel_AllSaerch) {
		if (panel_AllSaerch != null) {
			RootPaneContainer root = (RootPaneContainer) panel_AllSaerch.getRootPane().getTopLevelAncestor();
			root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			root.getGlassPane().setVisible(true);
		}
	}

	public static void setDefaultCursor(JPanel panel_AllSaerch) {
		if (panel_AllSaerch != null) {
			RootPaneContainer root = (RootPaneContainer) panel_AllSaerch.getRootPane().getTopLevelAncestor();
			root.getGlassPane().setCursor(Cursor.getDefaultCursor());
			root.getGlassPane().setVisible(false);
		}
	}

	public static String InputDialog(String[] options) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		String n = (String) JOptionPane.showInputDialog(null, "Има повече от един резултат", "Направете избор",
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		System.out.println(n);
		return n;
	}

	public static void MessageDialogText(String dialogText, String text) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, dialogText, text,
				JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	
	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	public static JTextField getTextField_EGN() {
		return textField_EGN;
	}

	public static JTextField getTextField_FName() {
		return textField_FName;
	}

	public static JTextField getTextField_SName() {
		return textField_SName;
	}

	public static JTextField getTextField_LName() {
		return textField_LName;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	

	
}
