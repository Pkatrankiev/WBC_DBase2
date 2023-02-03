package BasicClassAccessDbase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class PersonStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personStatus_ID;
	@ManyToOne
	private Person person;
	@ManyToOne
	private Workplace workplace;
	@ManyToOne
	private Spisak_Prilogenia spisak_prilogenia;
	@ManyToOne
	private UsersWBC userWBC;
	
	private Date dateSet;
	private String zabelejka;
	
	public PersonStatus (Person person,
	Workplace workplace,
	 Spisak_Prilogenia spisak_prilogenia,
	 UsersWBC userWBC,
	 Date dateSet,
	 String zabelejka
			) {
		this.person = person;
		this.workplace = workplace;
		this.spisak_prilogenia = spisak_prilogenia;
		this.userWBC = userWBC;
		this.dateSet = dateSet;
		this.zabelejka = zabelejka;
	}
	
	public PersonStatus () {
		super();
	}

	public int getPersonStatus_ID() {
		return personStatus_ID;
	}

	public void setPersonStatus_ID(int personStatus_ID) {
		this.personStatus_ID = personStatus_ID;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}

	public Spisak_Prilogenia getSpisak_prilogenia() {
		return spisak_prilogenia;
	}

	public void setSpisak_prilogenia(Spisak_Prilogenia spisak_prilogenia) {
		this.spisak_prilogenia = spisak_prilogenia;
	}

	public UsersWBC getUserWBC() {
		return userWBC;
	}

	public void setUserWBC(UsersWBC userWBC) {
		this.userWBC = userWBC;
	}

	public Date getDateSet() {
		return dateSet;
	}

	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}

	public String getZabelejka() {
		return zabelejka;
	}

	public void setZabelejka(String zabelejka) {
		this.zabelejka = zabelejka;
	}
	
	
}
