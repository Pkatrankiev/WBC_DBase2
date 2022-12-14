package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class KodeStatus implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int kodeStatus_ID;
	@ManyToOne
	private Person person;
	private String kode;
	@ManyToOne
	private Zone zone;
	private boolean freeKode;
	private String year;
	
	public KodeStatus (Person person,
	String kode, Zone zone, boolean freeKode, String year) {
		
		this.person = person;
		this.kode = kode;
		this.zone = zone;
		this.freeKode = freeKode;
		this.year = year;
	}
	
	public KodeStatus() {
		super();
	}

	public int getKodeStatus_ID() {
		return kodeStatus_ID;
	}

	public void setKodeStatus_ID(int kodeStatus_ID) {
		this.kodeStatus_ID = kodeStatus_ID;
	}

	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public boolean getisFreeKode() {
		return freeKode;
	}

	public void setisFreeKode(boolean freeKode) {
		this.freeKode = freeKode;
	}
	
	
	
}
