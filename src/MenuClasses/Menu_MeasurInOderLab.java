package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import MeasurInOderLabCheck.MeasurInOderLabFrame;


public class Menu_MeasurInOderLab extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String labelMeasurInOderLabMenu = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelMeasurInOderLabMenu");
	static String labelMeasurInOderLabFrame = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelMeasurInOderLabFrame");
	public Menu_MeasurInOderLab() {
		super(labelMeasurInOderLabMenu);
		setToolTipText(labelMeasurInOderLabFrame);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 new MeasurInOderLabFrame(round);
			    	
		     }
		    });
		    thread.start();	
		
		
				
		
	}       
	
}