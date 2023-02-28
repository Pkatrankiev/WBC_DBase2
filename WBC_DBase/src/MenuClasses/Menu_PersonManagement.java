package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import SearchFreeKode.SearchFreeKodeFrame;

public class Menu_PersonManagement extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;

	public Menu_PersonManagement() {
		super("PersonManagement");
		setToolTipText("Management Person");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new SearchFreeKodeFrame(round);
		    	     	
		     }
		    });
		    thread.start();	
		
		
		
		
		
	

	}

}
