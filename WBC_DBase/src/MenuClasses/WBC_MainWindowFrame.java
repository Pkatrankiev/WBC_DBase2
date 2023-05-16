package MenuClasses;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;


import javax.swing.JMenuBar;


public class WBC_MainWindowFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public WBC_MainWindowFrame() {
	
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("arrowIcon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		setTitle("Data Base WBC"); 
		setMinimumSize(new Dimension(600, 300));
		GetVisibleLAF(this);	
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(269, 149);
		setLocationRelativeTo(null);
		
			
		  setJMenuBar(createMenuBar());  
		  
			setVisible(true);

	}
	
	
	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
		menu.add(createMenu_Measuring());
		menu.add(createMenu_Reference());
		menu.add(createMenu_PersonManagement());
		
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
}
