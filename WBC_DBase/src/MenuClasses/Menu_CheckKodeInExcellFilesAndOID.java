package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import CheckCodeByExcelFileAndOID.CheckCodeByExcelFileAndOID_Frame;


public class Menu_CheckKodeInExcellFilesAndOID extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String Menu_CheckKodeInExcellFilesAndOID_Title = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Menu_CheckKodeInExcellFilesAndOID_Title");
	static String Menu_CheckKodeInExcellFilesAndOID_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Menu_CheckKodeInExcellFilesAndOID_TipText");
	
	public Menu_CheckKodeInExcellFilesAndOID() {
		super(Menu_CheckKodeInExcellFilesAndOID_Title);
		setToolTipText(Menu_CheckKodeInExcellFilesAndOID_TipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     public void run() {
		    	new CheckCodeByExcelFileAndOID_Frame(Menu_CheckKodeInExcellFilesAndOID_Title, round);
		     }
		    });
		    thread.start();	
	
	}
	
}
