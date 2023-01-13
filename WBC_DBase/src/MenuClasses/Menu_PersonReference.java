package MenuClasses;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import AutoInsertMeasuting.AutoInsertMeasutingFrame;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import PersonReference.PersonReferenceFrame;

public class Menu_PersonReference extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;

	public Menu_PersonReference() {
		super("PersonReference");
		setToolTipText("Reference Person");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	new PersonReferenceFrame();
			
	}
	
}

