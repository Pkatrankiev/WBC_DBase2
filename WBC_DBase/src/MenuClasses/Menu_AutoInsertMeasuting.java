package MenuClasses;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import FrameViewClass.AutoInsertMeasutingFrame;




public class Menu_AutoInsertMeasuting extends AbstractMenuAction{

	public Menu_AutoInsertMeasuting() {
		super("AutoInsertMeasuting");
		setToolTipText("add measuring from report file");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles();
		String[] listSimbolNuclide = NuclideWBCDAO.getMasiveSimbolNuclide(); 
		String[] listLaboratory =  LaboratoryDAO.getMasiveLaboratory();
		String[] listUserWBC =  UsersWBCDAO.getMasiveUserWBCNames();
		String[] listTypeMeasur = TypeMeasurDAO.getMasiveTypeMeasur();
		new AutoInsertMeasutingFrame(new JFrame(), list, listSimbolNuclide, listLaboratory, listUserWBC, listTypeMeasur);
			
	}
	
}
