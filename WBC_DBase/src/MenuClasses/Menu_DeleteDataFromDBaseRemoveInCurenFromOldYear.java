package MenuClasses;

import java.awt.event.ActionEvent;


import Aplication.ReadFileBGTextVariable;
import DeleteDataFromDBaseRemoveInCurenFromOldYear.DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame;


public class Menu_DeleteDataFromDBaseRemoveInCurenFromOldYear extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String CheckInfoDBaseToExcelFiles = ReadFileBGTextVariable.getGlobalTextVariableMap().get("deleteDataFromDBaseRemoveInCurenYearFromOldYear");
	static String CheckInfoDBaseToExcelFilesTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("deleteDataFromDBaseRemoveInCurenYearFromOldYear");
	
	public Menu_DeleteDataFromDBaseRemoveInCurenFromOldYear() {
		super(CheckInfoDBaseToExcelFiles);
		setToolTipText(CheckInfoDBaseToExcelFilesTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		    	 new DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame ();
	
	}
	
}

