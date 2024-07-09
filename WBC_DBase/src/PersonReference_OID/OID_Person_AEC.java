package PersonReference_OID;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OID_Person_AEC implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int oid_Person_AEC_ID;
	
	private String egn;
	private String firstName;
	private String secondName;
	private String lastName;
	private Date dateSet;
	
	public OID_Person_AEC( 
			String egn, 
			String firstName,
			String secondName,
			String lastName,
			Date dateSet
			) {
		this.egn = egn;
		this.firstName = firstName;
		this. secondName = secondName;
		this. lastName = lastName;
		this.  dateSet = dateSet;
		
	}
	public OID_Person_AEC() {
		super();
	}
	
	
	public int getOid_Person_AEC_ID() {
		return oid_Person_AEC_ID;
	}
	
	public void setOid_Person_AEC_ID(int oid_Person_AEC_ID) {
		this.oid_Person_AEC_ID = oid_Person_AEC_ID;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEgn() {
		return egn;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public String getLastName() {
		return lastName;
	}
	public Date getDateSet() {
		return dateSet;
	}
	public void setEgn(String egn) {
		this.egn = egn;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}
	
}
