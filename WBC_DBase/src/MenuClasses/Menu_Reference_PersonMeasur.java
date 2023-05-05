package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;


public class Menu_Reference_PersonMeasur  extends AbstractMenuAction{
		
		private static final long serialVersionUID = 1L;
		static String personReference = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReference");
		static String personReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceTipText");
		
		public Menu_Reference_PersonMeasur() {
			super(personReference);
			setToolTipText(personReferenceTipText);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			ActionIcone round = new ActionIcone();
			 final Thread thread = new Thread(new Runnable() {
			     @Override
			     public void run() {
			    	 
			    	 new Reference_PersonMeasur_Frame(round);
			    	     	
			     }
			    });
			    thread.start();	
			
			
			
			
			
		

		}
		
	}
