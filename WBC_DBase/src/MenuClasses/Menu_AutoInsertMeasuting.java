package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.AutoInsertMeasutingMethods;
import BasicClassAccessDbase.UsersWBC;
import PersonManagement.PersonelManegementMethods;
import WBCUsersLogin.WBCUsersLogin;


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
		
		UsersWBC loginDlg = WBCUsersLogin.getCurentUser();
		if (loginDlg == null) {
			JOptionPane.showMessageDialog(null, ReadFileBGTextVariable.getGlobalTextVariableMap().get("logInMesege"));
		} else {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	 if(PersonelManegementMethods.checkIsClosedMonthANDPersonAndExternalFile(round)) {
		    	AutoInsertMeasutingMethods.AutoInsertMeasutingStartFrame();
		 		}
		     }
		    });
		    thread.start();	
		
		}
		
		
		
				
		
	}       
	
}
