package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import ReferenceKontrolPersonByPeriod.ReferenceKontrolPersonByPeroid_Frame;


public class Menu_ReferenceKontrolPerson extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String labelReferenceKontrolPersonByPeroid = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelReferenceKontrolPersonByPeroid");
	static String labelReferenceKontrolPersonByPeroidTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelReferenceKontrolPersonByPeroidTipText");
	
	public Menu_ReferenceKontrolPerson() {
		super(labelReferenceKontrolPersonByPeroid);
		setToolTipText(labelReferenceKontrolPersonByPeroidTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new ReferenceKontrolPersonByPeroid_Frame(round, labelReferenceKontrolPersonByPeroidTipText);
			    	
		    	     	
		     }
		    });
		    thread.start();	
		
				
	}

	}
