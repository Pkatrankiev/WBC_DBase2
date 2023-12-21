package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.AutoInsertMeasutingMethods;
import PersonManagement.PersonelManegementFrame;
import PersonManagement.PersonelManegementMethods;




public class Menu_AutoInsertMeasuting extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String autoInsertMeasuting = ReadFileBGTextVariable.getGlobalTextVariableMap().get("autoInsertMeasuting");
	static String autoInsertMeasutingTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("autoInsertMeasutingTipText");
	public Menu_AutoInsertMeasuting() {
		super(autoInsertMeasuting);
		setToolTipText(autoInsertMeasutingTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 if(PersonelManegementMethods.checkIsClosedMonthANDPersonAndExternalFile(round)) {
		    	AutoInsertMeasutingMethods.AutoInsertMeasutingStartFrame();
		 		}
		     }
		    });
		    thread.start();	
		
		
		
		
		
				
		
	}       
	
}
