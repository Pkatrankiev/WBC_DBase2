package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReference.PersonReferenceFrame;
import ResultFromMeasuringReference.ResultFromMeasuringReferenceFrame;

public class Menu_ResultFromMeasuringReference extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String resultFromMeasuringReference_Text = ReadFileBGTextVariable.getGlobalTextVariableMap().get("resultFromMeasuringReference_Text");
	static String personReferenceTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("resultFromMeasuringReference_TipText");
	
	public Menu_ResultFromMeasuringReference() {
		super(resultFromMeasuringReference_Text);
		setToolTipText(personReferenceTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 
		    	 new ResultFromMeasuringReferenceFrame(round);
		    	     	
		     }
		    });
		    thread.start();	
	
	}
	
}

