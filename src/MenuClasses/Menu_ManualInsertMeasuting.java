package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.UsersWBC;
import InsertMeasuting.ManualInsertMeasutingMethods;
import PersonManagement.PersonelManegementMethods;
import WBCUsersLogin.WBCUsersLogin;

public class Menu_ManualInsertMeasuting extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String manualInsertMeasuting = ReadFileBGTextVariable.getGlobalTextVariableMap().get("manualInsertMeasuting");
	static String manualInsertMeasutingTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("manualInsertMeasutingTipText");
	public Menu_ManualInsertMeasuting() {
		super(manualInsertMeasuting);
		setToolTipText(manualInsertMeasutingTipText);
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
		    		 ManualInsertMeasutingMethods.ManualInsertMeasutingStartFrame();
		 		}
		     }
		    });
		    thread.start();	
		
		}
		
		
		
				
		
	}       
	
}
