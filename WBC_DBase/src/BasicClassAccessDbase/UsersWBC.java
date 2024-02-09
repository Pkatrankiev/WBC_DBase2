package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UsersWBC  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Users;
	private String name;
	private String lastName;
	private String nikName;
	private String pass;
	private String lastName_EG;
	private boolean acting;
	
	public UsersWBC (String name,	String lastName, String nikName, String pass, String lastName_EG, boolean acting) {
		this.name = name;
		this.lastName = lastName;
		this.nikName = nikName;
		this.pass = pass;
		this.lastName_EG = lastName_EG;
		this.setActing(acting);
	}
	
	public UsersWBC() {
		super();
	}

	public String getLastName_EG() {
		return lastName_EG;
	}

	public void setLastName_EG(String lastName_EG) {
		this.lastName_EG = lastName_EG;
	}

	public int getId_Users() {
		return id_Users;
	}

	public void setId_Users(int id_Users) {
		this.id_Users = id_Users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNikName() {
		return nikName;
	}

	public void setNikName(String nikName) {
		this.nikName = nikName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean getActing() {
		return acting;
	}

	public void setActing(boolean acting) {
		this.acting = acting;
	}
	
	
}
