package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ReadFileBGTextVariable;
import PersonReferenceInWebKD.ChangeKodeInKDByExcelLog;


public class Menu_ChangeKodeInKD  extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String kodeReference = ReadFileBGTextVariable.getGlobalTextVariableMap().get("changeKodeInKD");
	static String kodeReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("changeKodeInKDTipText");
	public Menu_ChangeKodeInKD() {
		super(kodeReference);
		setToolTipText(kodeReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 ChangeKodeInKDByExcelLog. ChangeKodeInKD();
		     }
		    });
		    thread.start();	
		
				
	}

	}
