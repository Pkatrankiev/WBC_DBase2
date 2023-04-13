package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import SearchFreeKode.SearchFreeKodeFrame;

public class Menu_Reference_Kode extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String kodeReference = ReadFileBGTextVariable.getGlobalTextVariableMap().get("kodeReference");
	static String kodeReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("kodeReferenceTipText");
	public Menu_Reference_Kode() {
		super(kodeReference);
		setToolTipText(kodeReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new SearchFreeKodeFrame(round, "", "");
		    	     	
		     }
		    });
		    thread.start();	
		
				
	}

	}
