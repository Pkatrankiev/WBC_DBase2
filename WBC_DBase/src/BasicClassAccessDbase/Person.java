package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Person;
	private String egn;
	private String firstName;
	private String secondName;
	private String lastName;
	

	public Person( 
			String egn, 
			String firstName,
			String secondName,
			String lastName
			) {
		this.egn = egn;
		this.firstName = firstName;
		this. secondName = secondName;
		this. lastName = lastName;
		
	}
	public Person() {
		super();
	}

	public int getId_Person() {
		return id_Person;
	}


	public void setId_Person(int id_Person) {
		this.id_Person = id_Person;
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
	
	
}
