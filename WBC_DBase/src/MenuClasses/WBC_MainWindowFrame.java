package MenuClasses;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.MyDarkerLaf;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import BasicClassAccessDbase.UsersWBC;

import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;
import WBCUsersLogin.WBCUsersLogin;


import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class WBC_MainWindowFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JLabel lblNewLabel;

	private static WBCUsersLogin loginDlg;
	private static String mainWindow_LogInStr_Btn = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("mainWindow_LogInStr_Btn");
	private static String mainWindow_LogOutStr_Btn = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("mainWindow_LogOutStr_Btn");
	private static String mainWindow_Title = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("mainWindow_Title");
	private static String Version = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Version");
	private static String InputVersion; 
	
	public WBC_MainWindowFrame(String Mainversion) {
	
		
		
		String MainWindowFrame_BtnActual = ReadFileBGTextVariable.getGlobalTextVariableMap().get("MainWindowFrame_BtnActual");
		
		InputVersion = compareByDateJarFileAndInputVersion(Mainversion);
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		setTitle(mainWindow_Title+" "+getVersion()); 
		setMinimumSize(new Dimension(700, 300));
		 if(ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD").equals("1")) {
				FlatLightLafSetup(this);
			}else {
		GetVisibleLAF(this);
			}
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(700, 300);
		setLocationRelativeTo(null);
		
		setJMenuBar(createMenuBar(this)); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblNewLabel = new JLabel();
		lblNewLabel.setPreferredSize(new Dimension(550, 20));

		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblNewLabel);
				
		JButton btnNewButton = new JButton(MainWindowFrame_BtnActual);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsersWBC loginDlg = WBCUsersLogin.getCurentUser();
				if (loginDlg == null) {
					JOptionPane.showMessageDialog(null, ReadFileBGTextVariable.getGlobalTextVariableMap().get("logInMesege"));
				} else {
				UpdateBDataFromExcellFiles.updataFromGodExcelFile();
				}
			}

		
		});
		panel.add(btnNewButton);
			
		  
		  
		  this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					System.exit(0);
				}
			});
		  
		 
		  
			setVisible(true);
			
	}
	
	
	public static JLabel getLblNewLabel() {
		return lblNewLabel;
	}


	@SuppressWarnings("static-access")
	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}


	private JMenuBar createMenuBar(Frame win) {
		JMenuBar menu = new JMenuBar();
		menu.add(createLoginMenu(win), BorderLayout.EAST);
		menu.add(createMenu_Measuring());
		menu.add(createMenu_Reference());
		menu.add(createMenu_PersonManagement());
		menu.add(createMenu_CheckErrorData());
		menu.add(createMenu_EditBasicTable());
		
		return menu;
	}
	
	
	private JMenu createMenu_Reference() {
		 String reference = ReadFileBGTextVariable.getGlobalTextVariableMap().get("reference");
		 String referenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceTipText");
		
		JMenu personReferenceMenu = new JMenu(reference);
		personReferenceMenu.setToolTipText(referenceTipText);
		personReferenceMenu.setMnemonic(KeyEvent.VK_P);
		
		personReferenceMenu.add(new Menu_Reference_Person());
		personReferenceMenu.add(new Menu_OID_Reference_Person());
		personReferenceMenu.add(new Menu_PersonReferenceInWebKD());
		personReferenceMenu.add(new Menu_PersonReference_Dokument());
		personReferenceMenu.add(new Menu_Reference_Kode());
		personReferenceMenu.addSeparator();
		personReferenceMenu.add(new Menu_Reference_PersonMeasur());
		personReferenceMenu.add(new Menu_ReferenceMeasuringLab());
		personReferenceMenu.add(new Menu_MeasurInOderLab());
		personReferenceMenu.add(new Menu_ResultFromMeasuringReference());
		personReferenceMenu.addSeparator();
		personReferenceMenu.add(new Menu_ReferenceKontrolPerson());
		
		return personReferenceMenu;
	}

	
	private JMenu createMenu_EditBasicTable() {
		 String editBasicTable = ReadFileBGTextVariable.getGlobalTextVariableMap().get("editBasicTable");
		 String editBasicTable_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("editBasicTable_TipText");
		
		JMenu personReferenceMenu = new JMenu(editBasicTable);
		personReferenceMenu.setToolTipText(editBasicTable_TipText);
		personReferenceMenu.setMnemonic(KeyEvent.VK_B);
		
		personReferenceMenu.add(new Menu_PersonReference_PersonStatus());
		personReferenceMenu.add(new Menu_Measuring_Results());
		personReferenceMenu.add(new Menu_DeleteDublicateShortEGN());
		personReferenceMenu.addSeparator();
		personReferenceMenu.add(new Menu_DeleteDataFromDBaseRemoveInCurenFromOldYear());
		
		return personReferenceMenu;
	}
	
	

	private JButton createLoginMenu(Frame win) {
		JButton loginMenu = new JButton(mainWindow_LogInStr_Btn);
		loginMenu.setPreferredSize(new Dimension(70, 20));
		loginMenu.setMaximumSize(new Dimension(70, 20));
		loginMenu.setMnemonic(KeyEvent.VK_L);
		loginMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionIcone round = new ActionIcone();
				String textBtnLogin = loginMenu.getText();

				if (textBtnLogin.equals(mainWindow_LogOutStr_Btn)) {
					round.StopWindow();
					WBCUsersLogin.logOut();

					loginMenu.setText(mainWindow_LogInStr_Btn);
					win.setTitle(mainWindow_Title+" "+getVersion());

				} else {
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {

							StartLoginMenu(win, loginMenu);

						}
					});
					thread.start();

				}
			}

		});
		return loginMenu;
	}
	
	public static void StartLoginMenu(Frame win, JButton loginMenu) {
		ActionIcone round = new ActionIcone();
		String frameName = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("LoginFrame_Title");
		loginDlg = new WBCUsersLogin(win, frameName, round);
		loginDlg.setVisible(true);

		if (loginDlg.isSucceeded()) {
			@SuppressWarnings("static-access")
			UsersWBC user = loginDlg.getCurentUser();

			win.setTitle(mainWindow_Title+" "+getVersion() + " "
					+ ReadFileBGTextVariable.getGlobalTextVariableMap().get("MainWindow_Title_work") + " "
					+ user.getName() + " " + user.getLastName());
			loginMenu.setText(mainWindow_LogOutStr_Btn);

		}
	}
		
	
	private JMenu createMenu_Measuring() {
		 String insertMeasuring = ReadFileBGTextVariable.getGlobalTextVariableMap().get("insertMeasuring");
		 String insertMeasuringTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("insertMeasuringTipText");
		JMenu measuringMenu = new JMenu(insertMeasuring);
		measuringMenu.setToolTipText(insertMeasuringTipText);
		measuringMenu.setMnemonic(KeyEvent.VK_I);
		
		measuringMenu.add(new Menu_AutoInsertMeasuting());
		measuringMenu.add(new Menu_ManualInsertMeasuting());
		measuringMenu.addSeparator();
		measuringMenu.add(new Menu_DozeArt());
		
		return measuringMenu;
	}
	
	private JMenu createMenu_PersonManagement() {
		 String personManagement = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagement");
		 String personManagementTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagementTipText");
		JMenu personReferenceMenu = new JMenu(personManagement);
		personReferenceMenu.setToolTipText(personManagementTipText);
		personReferenceMenu.setMnemonic(KeyEvent.VK_M);
		
		personReferenceMenu.add(new Menu_PersonManagement());
		personReferenceMenu.add(new Menu_SpisakPersonManagement());
		personReferenceMenu.addSeparator();
		personReferenceMenu.add(new Menu_CustomUpDate());
		
		return personReferenceMenu;
	}
	
	private JMenu createMenu_CheckErrorData() {
		 String checkCorrectinDataInExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell");
		 JMenu checkErrorDataMenu = new JMenu(checkCorrectinDataInExcell);
		 checkErrorDataMenu.setToolTipText(checkCorrectinDataInExcell);
		 checkErrorDataMenu.setMnemonic(KeyEvent.VK_E);
			
		 checkErrorDataMenu.add(new Menu_CheckErrorDataInExcellFiles());
		return checkErrorDataMenu;
	}
	
	
	public static  void GetVisibleLAF(final JFrame win) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(win);
			win.pack();
		} catch (Exception ex) {
			ResourceLoader.appendToFile(ex);
			Logger.getLogger(JFileChooser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void FlatLightLafSetup(JFrame frame) {
		
		
		
		MyDarkerLaf.setup(); //setting the look and feel
//	UIManager.put( "TitlePane.background" , new Color(23,180,252));
//	JFrame.setDefaultLookAndFeelDecorated(true);
//	frame.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23,180,252));
//	       frame.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);
	}

	private static String getVersion() {
		Date datMainVersion = null;
		Date datVersion = null;
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			try {
				datMainVersion = sdf.parse(InputVersion);
				datVersion = sdf.parse(Version.replace("v.", ""));
			} catch (ParseException e) {
				e.printStackTrace();
				return Version;
			}
		

		return datMainVersion.before(datVersion) ? Version : "v."+InputVersion;
	}


	public static String  compareByDateJarFileAndInputVersion(String Mainversion) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		Date jarVersion = null, datMainVersion = null;
		
		try {
			datMainVersion = sdf.parse(Mainversion);
		
		File fis = new File("WBC_DBase.jar");
		long lastModifiedFile = fis.lastModified();
		String stringday = sdf.format(lastModifiedFile);
		jarVersion = sdf.parse(stringday);
		if(jarVersion.after(datMainVersion)) {
			return stringday;
		}

		} catch (ParseException e) {
		e.printStackTrace();
			return Mainversion;
		}
		return Mainversion;
		
	}
	
	
	
}
