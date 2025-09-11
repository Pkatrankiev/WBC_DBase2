package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReferenceInWebKD.PersonReferenceInWebKD_Frame;


public class Menu_PersonReferenceInWebKD extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personReferenceInWebKD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceInWebKD");
	static String personReferenceInWebKD_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceInWebKD_TipText");
	
	public Menu_PersonReferenceInWebKD() {
		super(personReferenceInWebKD);
		setToolTipText(personReferenceInWebKD_TipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new PersonReferenceInWebKD_Frame( round, personReferenceInWebKD);
		    	     	
		     }
		    });
		    thread.start();
	
	}
	
}
