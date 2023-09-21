package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import ReferenceMeasuringLab.ReferenceMeasuringLabFrame;

public class Menu_ReferenceMeasuringLab extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String referenceMeasuringLab = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab");
	static String referenceMeasuringLabTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLabTipText");
	
	public Menu_ReferenceMeasuringLab() {
		super(referenceMeasuringLab);
		setToolTipText(referenceMeasuringLabTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new ReferenceMeasuringLabFrame(round, referenceMeasuringLab);
		    	     	
		     }
		    });
		    thread.start();	
		
		
		
		
		
	

	}
	
}
