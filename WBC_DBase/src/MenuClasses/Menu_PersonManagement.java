package MenuClasses;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonManagement.OpenedExcelClass;
import PersonManagement.PersonelManegementFrame;
import PersonManagement.PersonelManegementMethods;


public class Menu_PersonManagement extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personManagement = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagement");
	static String personManagementTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagementTipText");
	public Menu_PersonManagement() {
		super(personManagement);
		setToolTipText(personManagementTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	if(PersonelManegementMethods.checkIsClosedPersonAndExternalFile()) {
		    	new PersonelManegementFrame(round);
		    	
		    	}
		    	round.StopWindow();
		     }
		    });
		    thread.start();	
		
		
		
		
		
	

	}

}
