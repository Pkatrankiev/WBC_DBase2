package PersonReference_OID;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table
public class OID_Person_WBC implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String egn;
	private String firstName;
	private String secondName;
	private String lastName;
	private String zsr1;
	private int zsr1n;
	private String zsr1b;
	private String zsr2;
	private int zsr2n;
	private String zsr2b;
	private Date dateSet;
	

	
	public OID_Person_WBC( 
			String egn, 
			String firstName,
			String secondName,
			String lastName,
			String zsr1,
			int zsr1n,
			String zsr1b,
			String zsr2,
			int zsr2n,
			String zsr2b,
			Date dateSet
			) {
		this.egn = egn;
		this.firstName = firstName;
		this. secondName = secondName;
		this. lastName = lastName;
		this.  zsr1 = zsr1;
		this.  zsr1n = zsr1n;
		this.  zsr1b = zsr1b;
		this.  zsr2 = zsr2;
		this.  zsr2n = zsr2n;
		this.  zsr2b = zsr2b;
		this.  dateSet = dateSet;
		
	}
	public OID_Person_WBC() {
		super();
	}
	

	public String getEgn() {
		return egn;
	}


	public void setEgn(String egn) {
		this.egn = egn;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getSecondName() {
		return secondName;
	}


	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getZsr1() {
		return zsr1;
	}
	public int getZsr1n() {
		return zsr1n;
	}
	public String getZsr1b() {
		return zsr1b;
	}
	public String getZsr2() {
		return zsr2;
	}
	public int getZsr2n() {
		return zsr2n;
	}
	public String getZsr2b() {
		return zsr2b;
	}
	public Date getDateSet() {
		return dateSet;
	}
	public void setZsr1(String zsr1) {
		this.zsr1 = zsr1;
	}
	public void setZsr1n(int zsr1n) {
		this.zsr1n = zsr1n;
	}
	public void setZsr1b(String zsr1b) {
		this.zsr1b = zsr1b;
	}
	public void setZsr2(String zsr2) {
		this.zsr2 = zsr2;
	}
	public void setZsr2n(int zsr2n) {
		this.zsr2n = zsr2n;
	}
	public void setZsr2b(String zsr2b) {
		this.zsr2b = zsr2b;
	}
	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}
	
	
}
