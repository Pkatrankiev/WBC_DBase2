package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReference_OID.OID_PersonReferenceFrame;

public class Menu_OID_Reference_Person extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personReference_OID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReference_OID");
	static String personReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceTipText");
	
	public Menu_OID_Reference_Person() {
		super(personReference_OID);
		setToolTipText(personReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new OID_PersonReferenceFrame(round, personReference_OID);
		    	     	
		     }
		    });
		    thread.start();
	
	}
	
}
