package MenuClasses;

import java.awt.event.ActionEvent;
import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReference.PersonReferenceFrame;


public class Menu_Reference_Person extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personReference = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReference");
	static String personReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceTipText");
	
	public Menu_Reference_Person() {
		super(personReference);
		setToolTipText(personReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new PersonReferenceFrame(round);
		    	     	
		     }
		    });
		    thread.start();	
		
		
		
		
		
	

	}
	
}

