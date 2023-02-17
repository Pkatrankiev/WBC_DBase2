package MenuClasses;

import java.awt.event.ActionEvent;
import Aplication.ActionIcone;
import PersonReference.PersonReferenceFrame;


public class Menu_PersonReference extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;

	public Menu_PersonReference() {
		super("PersonReference");
		setToolTipText("Reference Person");
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

