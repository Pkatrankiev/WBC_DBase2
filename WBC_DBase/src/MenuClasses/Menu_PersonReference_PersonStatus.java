package MenuClasses;

import java.awt.event.ActionEvent;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonReference_PersonStatus.PersonReference_PersonStatus_Frame;

public class Menu_PersonReference_PersonStatus extends AbstractMenuAction{

	private static final long serialVersionUID = 1L;
	static String dokumentStatusUser = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dokumentStatusUser");
	static String dokumentStatusUser_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dokumentStatusUser_TipText");
	public Menu_PersonReference_PersonStatus() {
		super(dokumentStatusUser);
		setToolTipText(dokumentStatusUser_TipText);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		new PersonReference_PersonStatus_Frame(dokumentStatusUser, round);
	
		     }
		    });
		    thread.start();
	}

}
