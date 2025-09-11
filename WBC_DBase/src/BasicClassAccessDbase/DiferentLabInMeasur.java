package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DiferentLabInMeasur implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int diferentLabInMeasur_ID;
	

	private Laboratory labWorkplace;
	private Measuring measur;
	private Workplace workplace;
	private String year;
	private boolean check;

	public DiferentLabInMeasur(Laboratory labWorkplace, Measuring measur, Workplace workplace, String year, boolean check) {
	
		this.labWorkplace = labWorkplace;
		this.measur = measur;
		this.workplace = workplace;
		this.year = year;
		this.check = check;
	}


	public DiferentLabInMeasur() {
		super();
	}
	
	public int getDiferentLabInMeasur_ID() {
		return diferentLabInMeasur_ID;
	}
	
	public void setDiferentLabInMeasur_ID(int diferentLabInMeasur_ID) {
		this.diferentLabInMeasur_ID = diferentLabInMeasur_ID;
	}


	public Laboratory getLabWorkplace() {
		return labWorkplace;
	}

	public Measuring getMeasur() {
		return measur;
	}

	public String getYear() {
		return year;
	}

	public boolean getCheck() {
		return check;
	}

	public void setLabWorkplace(Laboratory lab) {
		this.labWorkplace = lab;
	}

	public void setMeasur(Measuring measur) {
		this.measur = measur;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}


	public Workplace getWorkplace() {
		return workplace;
	}


	public void setWorkplace(Workplace workpl) {
		this.workplace = workpl;
	}


	
}
