package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.UsersWBC;
import PersonManagement.PersonelManegementFrame;
import PersonManagement.PersonelManegementMethods;
import WBCUsersLogin.WBCUsersLogin;


public class Menu_PersonManagement extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String personManagement = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagement");
	static String personManagementTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personManagementTipText");
	public Menu_PersonManagement() {
		super(personManagement);
		setToolTipText(personManagementTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		UsersWBC loginDlg = WBCUsersLogin.getCurentUser();
		if (loginDlg == null) {
			JOptionPane.showMessageDialog(null, ReadFileBGTextVariable.getGlobalTextVariableMap().get("logInMesege"));
		} else {
		
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	if(PersonelManegementMethods.checkIsClosedPersonAndExternalFile()) {
		    	new PersonelManegementFrame(round);
		    	
		    	}
		    	round.StopWindow();
		     }
		    });
		    thread.start();	
		
		}
		
		
		
	

	}

}
