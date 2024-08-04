package MenuClasses;

import java.awt.event.ActionEvent;


import Aplication.ReadFileBGTextVariable;
import CheckErrorDataInExcellFiles.CheckErrorDataInExcellFiles_Frame;


public class Menu_CheckErrorDataInExcellFiles extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String checkCorrectinDataInExcell = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell");
	static String referencePersonMeasurTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasurTipText");
	
	public Menu_CheckErrorDataInExcellFiles() {
		super(checkCorrectinDataInExcell);
		setToolTipText(checkCorrectinDataInExcell);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		    	 new CheckErrorDataInExcellFiles_Frame ();
	
	}
	
}
