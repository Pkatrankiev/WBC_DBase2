package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import InstanceMenagement.InstanceMenagement_Frame;

public class Menu_InstanceMenagement extends AbstractMenuAction{

	private static final long serialVersionUID = 1L;
	static String instanceMenagementTitle = ReadFileBGTextVariable.getGlobalTextVariableMap().get("instanceMenagementTitle");
	static String instanceMenagementTitle_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("instanceMenagementTitle_TipText");
	public Menu_InstanceMenagement() {
		super(instanceMenagementTitle);
		setToolTipText(instanceMenagementTitle_TipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 new InstanceMenagement_Frame(instanceMenagementTitle, round);
		    	round.StopWindow();
		     }
		    });
		    thread.start();	
		    	     	
	
				
	}	
	
	
	
}
