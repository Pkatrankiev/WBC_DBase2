package PersonReference_OID;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OID_Person_VO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int oid_Person_AEC_ID;
	
	private String egn;
	private String firstName;
	private String secondName;
	private String lastName;
	private int predID;
	
	public OID_Person_VO( 
			String egn, 
			String firstName,
			String secondName,
			String lastName,
			int predID
			) {
		this.egn = egn;
		this.firstName = firstName;
		this. secondName = secondName;
		this. lastName = lastName;
		this.predID = predID;
		
	}
	public OID_Person_VO() {
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
	public int getPredID() {
		return predID;
	}
	public void setPredID(int predID) {
		this.predID = predID;
	}
	
	
}
