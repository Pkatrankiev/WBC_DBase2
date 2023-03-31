package MenuClasses;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

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
		
		List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
		String[] listSimbolNuclide = NuclideWBCDAO.getMasiveSimbolNuclide(); 
		String[] listLaboratory =  LaboratoryDAO.getMasiveLaboratory();
		String[] listUserWBC =  UsersWBCDAO.getMasiveUserWBCNames();
		String[] listTypeMeasur = TypeMeasurDAO.getMasiveTypeMeasur();
		String[] listTypeNameMeasur = TypeMeasurDAO.getMasiveNameTypeMeasur();
		new AutoInsertMeasutingFrame(new JFrame(), list, listSimbolNuclide, listLaboratory, listUserWBC, listTypeMeasur, listTypeNameMeasur, null);
			
	}
	
}
