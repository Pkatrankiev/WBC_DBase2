package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Measuring_Koment implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Measuring_Koment_ID;
	@ManyToOne
	private Measuring measuring;
	private String measurKoment;
	
	public Measuring_Koment (Measuring measuring, String measurKoment) {
		this.measuring = measuring;
		this.measurKoment = measurKoment;
	}
	
	public Measuring_Koment () {
		super();
	}

	public int getMeasuring_Koment_ID() {
		return Measuring_Koment_ID;
	}

	public void setMeasuring_Koment_ID(int measuring_Koment_ID) {
		Measuring_Koment_ID = measuring_Koment_ID;
	}

	public Measuring getMeasuring() {
		return measuring;
	}

	public void setMeasuring(Measuring measuring) {
		this.measuring = measuring;
	}

	public String getMeasurKoment() {
		return measurKoment;
	}

	public void setMeasurKoment(String measurKoment) {
		this.measurKoment = measurKoment;
	}

	
}
