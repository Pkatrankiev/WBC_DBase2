package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReference_Dokument.PersonReference_Dokument_Frame;


public class Menu_PersonReference_Dokument extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personReference_Dokument = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReference_Dokument");
	static String personReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceTipText");
	
	public Menu_PersonReference_Dokument() {
		super(personReference_Dokument);
		setToolTipText(personReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new PersonReference_Dokument_Frame(round, personReference_Dokument);
		    	     	
		     }
		    });
		    thread.start();
	
	}
	
}
