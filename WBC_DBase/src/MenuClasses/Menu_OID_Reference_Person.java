package MenuClasses;

import java.awt.event.ActionEvent;
import java.io.IOException;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReference_OID.AccessRunner;
import PersonReference_OID.OID_PersonReferenceFrame;

public class Menu_OID_Reference_Person extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personReference_OID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReference_OID");
	static String personReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReferenceTipText");
	
	public Menu_OID_Reference_Person() {
		super(personReference_OID);
		setToolTipText(personReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		 ActionIcone round = new ActionIcone("Чета данни от ОиД", "");
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 ProcessBuilder pb;
		    	 try {
		 			pb = AccessRunner.runWithWorkgroup();
		 			 Process proc = pb.start();
		 			System.out.println("Access стартиран. Изчакване да приключи...");

		 	        int exitCode = proc.waitFor();  // Тук Java блокира докато Access процеса приключи
		 	        System.out.println("Access приключи с код: " + exitCode);
		 	        new OID_PersonReferenceFrame(round, personReference_OID);
		 		} catch (IOException | InterruptedException e1) {
		 			e1.printStackTrace();
		 		}		
		     }
		    });
		    thread.start();
	
	}
	
}
