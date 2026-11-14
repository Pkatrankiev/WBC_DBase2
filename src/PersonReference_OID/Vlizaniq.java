package PersonReference_OID;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Vlizaniq implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Vlizaniq_ID;
	
	private String egn;
	private Date dateIn;
	private Time timeIn;
	private Time timeOut;
	private double doza;
	private int zona;
	private String userKod;
	private Date dateSet;
	
	public Vlizaniq( 
			String egn,
			Date dateIn,
			Time timeIn,
			Time timeOut,
			double doza,
			int zona,
			String userKod,
			Date dateSet
			) {
		this.egn = egn;
		this.dateIn = dateIn;
		this. timeIn = timeIn;
		this. timeOut = timeOut;
		this. doza = doza;
		this. zona = zona;
		this.  userKod = userKod;
		this.  dateSet = dateSet;
		
	}
	public Vlizaniq() {
		super();
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getVlizaniq_ID() {
		return Vlizaniq_ID;
	}
	public String getEgn() {
		return egn;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public Time getTimeIn() {
		return timeIn;
	}
	public Time getTimeOut() {
		return timeOut;
	}
	public double getDoza() {
		return doza;
	}
	public int getZona() {
		return zona;
	}
	public String getUserKod() {
		return userKod;
	}
	public Date getDateSet() {
		return dateSet;
	}
	public void setVlizaniq_ID(int vlizaniq_ID) {
		Vlizaniq_ID = vlizaniq_ID;
	}
	public void setEgn(String egn) {
		this.egn = egn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public void setTimeIn(Time timeIn) {
		this.timeIn = timeIn;
	}
	public void setTimeOut(Time timeOut) {
		this.timeOut = timeOut;
	}
	public void setDoza(double doza) {
		this.doza = doza;
	}
	public void setZona(int zona) {
		this.zona = zona;
	}
	public void setUserKod(String userKod) {
		this.userKod = userKod;
	}
	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}
}