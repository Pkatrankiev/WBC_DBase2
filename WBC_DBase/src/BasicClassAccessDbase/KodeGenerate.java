package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class KodeGenerate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int kodeGenerate_ID;
	private String letter_L;
	private String letter_R;
	private int startCount;
	private int endCount;
	@ManyToOne
	private Workplace workplace;
	@ManyToOne
	private Zone zone;
	
	public KodeGenerate(Workplace workplace,
			 Zone zone,
			 String letter_L,
	 String letter_R,
	 int startCount,
	 int endCount) {
		
		this.workplace = workplace;
		this.zone = zone;
		this.letter_L = letter_L;
		this.letter_R = letter_R;
		this.startCount = startCount;
		this.endCount = endCount;
		
	}
	
	public KodeGenerate(){
		super();
	}

	public int getKodeGenerate_ID() {
		return kodeGenerate_ID;
	}

	public void setKodeGenerate_ID(int kodeGenerate_ID) {
		this.kodeGenerate_ID = kodeGenerate_ID;
	}

	public String getLetter_L() {
		return letter_L;
	}

	public void setLetter_L(String letter_L) {
		this.letter_L = letter_L;
	}

	public String getLetter_R() {
		return letter_R;
	}

	public void setLetter_R(String letter_R) {
		this.letter_R = letter_R;
	}

	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	
}
