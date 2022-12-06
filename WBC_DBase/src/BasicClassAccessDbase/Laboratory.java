package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Laboratory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lab_ID;
	private String lab;
	
	public Laboratory (int lab_ID,String lab) {
		this.lab_ID = lab_ID;
		this.lab = lab;
	}
	
	public Laboratory() {
		super();
	}

	public int getLab_ID() {
		return lab_ID;
	}

	public void setLab_ID(int lab_ID) {
		this.lab_ID = lab_ID;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}
	
	
}
