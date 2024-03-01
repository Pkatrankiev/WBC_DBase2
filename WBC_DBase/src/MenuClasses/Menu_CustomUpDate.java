package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.UsersWBC;
import UpdateDBaseFromExcelFiles.CustomUpdate;
import WBCUsersLogin.WBCUsersLogin;

public class Menu_CustomUpDate extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String Menu_CustomUpDate_Title = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Menu_CustomUpDate_Title");
	static String Menu_CustomUpDate_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("Menu_CustomUpDate_TipText");
	
	public Menu_CustomUpDate() {
		super(Menu_CustomUpDate_Title);
		setToolTipText(Menu_CustomUpDate_TipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		UsersWBC loginDlg = WBCUsersLogin.getCurentUser();
		if (loginDlg == null) {
			JOptionPane.showMessageDialog(null, ReadFileBGTextVariable.getGlobalTextVariableMap().get("logInMesege"));
		} else {
		    	 new CustomUpdate(Menu_CustomUpDate_Title);
	
	}
	}
	
}

