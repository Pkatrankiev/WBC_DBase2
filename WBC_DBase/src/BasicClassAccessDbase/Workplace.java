package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Workplace implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Workplace;
	private String firmName;
	private String otdel;
	private String SecondOtdelName;
	
	public Workplace(String firmName, String otdel, String secondOtdelName) {
		
		
		this.firmName = firmName;
		this.otdel = otdel;
		this.SecondOtdelName = secondOtdelName;
	}
	
	public Workplace() {
		super();
	}

	public String getSecondOtdelName() {
		return SecondOtdelName;
	}

	public void setSecondOtdelName(String secondOtdelName) {
		SecondOtdelName = secondOtdelName;
	}

	public int getId_Workplace() {
		return id_Workplace;
	}

	public void setId_Workplace(int id_Workplace) {
		this.id_Workplace = id_Workplace;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getOtdel() {
		return otdel;
	}

	public void setOtdel(String otdel) {
		this.otdel = otdel;
	}

}
