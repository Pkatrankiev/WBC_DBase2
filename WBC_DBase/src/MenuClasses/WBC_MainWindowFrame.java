package MenuClasses;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import BasiClassDAO.ActualExcellFilesDAO;
import BasicClassAccessDbase.ActualExcellFiles;
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class WBC_MainWindowFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JLabel lblNewLabel;

	public WBC_MainWindowFrame() {
	
		String MainWindowFrame_BtnActual = ReadFileBGTextVariable.getGlobalTextVariableMap().get("MainWindowFrame_BtnActual");
		String Version = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Version");
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		setTitle("Data Base WBC "+Version); 
		setMinimumSize(new Dimension(600, 300));
		GetVisibleLAF(this);	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(600, 300);
		setLocationRelativeTo(null);
		
		setJMenuBar(createMenuBar()); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblNewLabel = new JLabel();
		lblNewLabel.setPreferredSize(new Dimension(450, 20));

		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblNewLabel);
				
		JButton btnNewButton = new JButton(MainWindowFrame_BtnActual);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateBDataFromExcellFiles.updataFromGodExcelFile();
				
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


	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
		menu.add(createMenu_Measuring());
		menu.add(createMenu_Reference());
		menu.add(createMenu_PersonManagement());
		menu.add(createMenu_CheckErrorData());
		
		return menu;
	}
	
	
	private JMenu createMenu_Reference() {
		 String reference = ReadFileBGTextVariable.getGlobalTextVariableMap().get("reference");
		 String referenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceTipText");
		
		JMenu personReferenceMenu = new JMenu(reference);
		personReferenceMenu.setToolTipText(referenceTipText);
		personReferenceMenu.setMnemonic(KeyEvent.VK_P);
		
		personReferenceMenu.add(new Menu_Reference_Person());
		personReferenceMenu.add(new Menu_Reference_Kode());
		personReferenceMenu.addSeparator();
		personReferenceMenu.add(new Menu_Reference_PersonMeasur());
		personReferenceMenu.add(new Menu_ReferenceMeasuringLab());
		
		return personReferenceMenu;
	}


	private JMenu createMenu_Measuring() {
		 String insertMeasuring = ReadFileBGTextVariable.getGlobalTextVariableMap().get("insertMeasuring");
		 String insertMeasuringTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("insertMeasuringTipText");
		JMenu measuringMenu = new JMenu(insertMeasuring);
		measuringMenu.setToolTipText(insertMeasuringTipText);
		measuringMenu.setMnemonic(KeyEvent.VK_I);
		
		measuringMenu.add(new Menu_AutoInsertMeasuting());
		measuringMenu.addSeparator();
		
		return measuringMenu;
	}
	
	private JMenu createMenu_PersonManagement() {
		 String personManagement = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagement");
		 String personManagementTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagementTipText");
		JMenu personReferenceMenu = new JMenu(personManagement);
		personReferenceMenu.setToolTipText(personManagementTipText);
		personReferenceMenu.setMnemonic(KeyEvent.VK_M);
		
		personReferenceMenu.add(new Menu_PersonManagement());
		personReferenceMenu.addSeparator();
		
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
	
	private void GetVisibleLAF(final JFrame win) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(win);
			this.pack();
		} catch (Exception ex) {
			ResourceLoader.appendToFile(ex);
			Logger.getLogger(JFileChooser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void updateLastActualsDBaseFromExcelFile(boolean afterExcelUpdate) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		String MainWindowFrame_Label = ReadFileBGTextVariable.getGlobalTextVariableMap().get("MainWindowFrame_Label");
		ActualExcellFiles actualExcellFiles_LastActuals = ActualExcellFilesDAO.getValueActualExcellFilesByName("LastActuals");
		if(afterExcelUpdate) {
		actualExcellFiles_LastActuals.setActualExcellFiles_Date(new Timestamp(System.currentTimeMillis()));
		ActualExcellFilesDAO.updateValueActualExcellFiles(actualExcellFiles_LastActuals);
		}
		String date_LastActuals = sdfrmt.format(actualExcellFiles_LastActuals.getActualExcellFiles_Date());
		WBC_MainWindowFrame.getLblNewLabel().setText(MainWindowFrame_Label+" "+date_LastActuals);
	}
}
