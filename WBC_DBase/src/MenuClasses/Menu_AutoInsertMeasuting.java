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
		
		 JFileChooser chooiser = new JFileChooser();
		 chooiser.setMultiSelectionEnabled(true);
		 chooiser.showOpenDialog(null);
		 File[] files = chooiser.getSelectedFiles();
		System.out.println(files.length);
				
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles( files);
		    	 String[] listSimbolNuclide = NuclideWBCDAO.getMasiveSimbolNuclide(); 
			 		String[] listLaboratory =  LaboratoryDAO.getMasiveLaboratory();
			 		String[] listUserWBC =  UsersWBCDAO.getMasiveUserWBCNames();
			 		String[] listTypeMeasur = TypeMeasurDAO.getMasiveTypeMeasur();
			 		String[] listTypeNameMeasur = TypeMeasurDAO.getMasiveNameTypeMeasur();
		    	new AutoInsertMeasutingFrame(round, new JFrame(), list, listSimbolNuclide, listLaboratory, listUserWBC, listTypeMeasur, listTypeNameMeasur, null);
		 	  }
		    });
		    thread.start();	
		
		
		
		
		
	}       
	
}
