package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.UsersWBC;
import PersonManagement.PersonelManegementMethods;
import SpisakPersonManagement.SpisakPersonelManegementFrame;
import WBCUsersLogin.WBCUsersLogin;

public class Menu_SpisakPersonManagement extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String spisakPersonManagement = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Person_Manegement_Spisaci");
	static String spisakPersonManagementTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Person_Manegement_SpisaciTipText");
	public Menu_SpisakPersonManagement() {
		super(spisakPersonManagement);
		setToolTipText(spisakPersonManagementTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		UsersWBC loginDlg = WBCUsersLogin.getCurentUser();
		if (loginDlg == null) {
			JOptionPane.showMessageDialog(null, ReadFileBGTextVariable.getGlobalTextVariableMap().get("logInMesege"));
		} else {
			if (loginDlg.getId_Users() == 1) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	if(PersonelManegementMethods.checkIsClosedPersonAndExternalFile()) {
		    	new SpisakPersonelManegementFrame(round);
		    	
		    	}
		    	round.StopWindow();
		     }
		    });
		    thread.start();	
			} else {
				JOptionPane.showMessageDialog(null, ReadFileBGTextVariable.getGlobalTextVariableMap().get("JustKatrankiev"));	
			}
		}
		
		
		
	

	}

}

