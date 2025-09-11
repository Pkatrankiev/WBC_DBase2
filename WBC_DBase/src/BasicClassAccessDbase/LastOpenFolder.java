package BasicClassAccessDbase;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class LastOpenFolder implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lastOpenFolder_ID;
	private String action;
	@ManyToOne
	private UsersWBC userAction;
	
	private String nameOpenFolder;
	
	public LastOpenFolder (String action, UsersWBC userAction, String nameOpenFolder) {
		
		this.action = action;
		this.userAction = userAction;
		this.nameOpenFolder = nameOpenFolder;
	}
	
	public LastOpenFolder() {
		super();
	}

	public int lastOpenFolder_ID() {
		return lastOpenFolder_ID;
	}

	public int getLastOpenFolder_ID() {
		return lastOpenFolder_ID;
	}
	public void setLastOpenFolder_ID(int lastOpenFolder_ID) {
		this.lastOpenFolder_ID = lastOpenFolder_ID;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public UsersWBC getUserAction() {
		return userAction;
	}

	public void setUserAction(UsersWBC userAction) {
		this.userAction = userAction;
	}
	
	public String getNameOpenFolder() {
		return nameOpenFolder;
	}

	

	

	public void setNameOpenFolder(String nameOpenFolder) {
		this.nameOpenFolder = nameOpenFolder;
	}

	

}
