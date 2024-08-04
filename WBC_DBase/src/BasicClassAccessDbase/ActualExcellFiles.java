package BasicClassAccessDbase;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ActualExcellFiles implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int actualExcellFiles_ID;
	private String actualExcellFiles_Name;
	private Timestamp actualExcellFiles_Date;

	public ActualExcellFiles(String actualExcellFiles_Name, Timestamp actualExcellFiles_Date) {
	
		this.actualExcellFiles_Name = actualExcellFiles_Name;
		this.actualExcellFiles_Date = actualExcellFiles_Date;
	}

	public ActualExcellFiles() {
		super();
	}

	public int getActualExcellFiles_ID() {
		return actualExcellFiles_ID;
	}

	public void setActualExcellFiles_ID(int actualExcellFiles_ID) {
		this.actualExcellFiles_ID = actualExcellFiles_ID;
	}

	public String getActualExcellFiles_Name() {
		return actualExcellFiles_Name;
	}

	public void setActualExcellFiles_Name(String actualExcellFiles_Name) {
		this.actualExcellFiles_Name = actualExcellFiles_Name;
	}

	public Timestamp getActualExcellFiles_Date() {
		return actualExcellFiles_Date;
	}

	public void setActualExcellFiles_Date(Timestamp timestamp) {
		this.actualExcellFiles_Date = timestamp;
	}

	
}
