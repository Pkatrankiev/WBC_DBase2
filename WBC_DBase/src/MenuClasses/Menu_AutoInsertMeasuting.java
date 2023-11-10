package MenuClasses;

import java.awt.event.ActionEvent;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.AutoInsertMeasutingMethods;




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
		
		AutoInsertMeasutingMethods.AutoInsertMeasutingStartFrame();
				
		
	}       
	
}
