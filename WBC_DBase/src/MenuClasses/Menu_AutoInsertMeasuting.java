package MenuClasses;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import AutoInsertMeasuting.AutoInsertMeasutingFrame;
import AutoInsertMeasuting.AutoInsertMeasutingMethods;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;



public class Menu_AutoInsertMeasuting extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String autoInsertMeasuting = ReadFileBGTextVariable.getGlobalTextVariableMap().get("autoInsertMeasuting");
	static String autoInsertMeasutingTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("autoInsertMeasutingTipText");
	public Menu_AutoInsertMeasuting() {
		super(autoInsertMeasuting);
		setToolTipText(autoInsertMeasutingTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		AutoInsertMeasutingMethods.AutoInsertMeasutingStartFrame();
				
		
	}       
	
}
