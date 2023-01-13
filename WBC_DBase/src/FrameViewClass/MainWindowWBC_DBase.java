package FrameViewClass;


import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Aplication.ResourceLoader;
import MenuClasses.Menu_AutoInsertMeasuting;
import MenuClasses.Menu_PersonReference;

import javax.swing.JMenuBar;


public class MainWindowWBC_DBase extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainWindowWBC_DBase() {
	
		
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
		menu.add(createMenu_PersonReference());
		
		return menu;
	}
	
	
	private JMenu createMenu_PersonReference() {
		JMenu personReferenceMenu = new JMenu("PersonReference");
		personReferenceMenu.setToolTipText("for reference by Person");
		personReferenceMenu.setMnemonic(KeyEvent.VK_P);
		
		personReferenceMenu.add(new Menu_PersonReference());
		personReferenceMenu.addSeparator();
		
		return personReferenceMenu;
	}


	private JMenu createMenu_Measuring() {
		JMenu measuringMenu = new JMenu("InsertMeasuring");
		measuringMenu.setToolTipText("for add measuring");
		measuringMenu.setMnemonic(KeyEvent.VK_I);
		
		measuringMenu.add(new Menu_AutoInsertMeasuting());
		measuringMenu.addSeparator();
		
		return measuringMenu;
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
