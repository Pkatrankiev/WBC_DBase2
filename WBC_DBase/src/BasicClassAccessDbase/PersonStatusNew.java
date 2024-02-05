package BasicClassAccessDbase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class PersonStatusNew implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personStatusNew_ID;
	@ManyToOne
	private Person person;
	@ManyToOne
	private Workplace workplace;
	@ManyToOne
	private UsersWBC userWBC;
	
	private String formulyarName;
	private Date startDate;
	private Date endDate;
	private String year;
	
	private Date dateSet;
	private String zabelejka;
	
	public PersonStatusNew (
			Person person,
			Workplace workplace,
			String formulyarName,
			Date startDate,
			Date endDate,
			String year,
			UsersWBC userWBC,
			Date dateSet,
			String zabelejka
			) {
		this.person = person;
		this.workplace = workplace;
		this.formulyarName = formulyarName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.year = year;
		this.userWBC = userWBC;
		this.dateSet = dateSet;
		this.zabelejka = zabelejka;
	}
	
	public PersonStatusNew () {
		super();
	}

	public int getPersonStatusNew_ID() {
		return personStatusNew_ID;
	}

	public void setPersonStatusNew_ID(int personStatusNew_ID) {
		this.personStatusNew_ID = personStatusNew_ID;
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

	public String getFormulyarName() {
		return formulyarName;
	}

	public void setFormulyarName(String formulyarNamee) {
		formulyarName = formulyarNamee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDatee) {
		startDate = startDatee;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDatee) {
		endDate = endDatee;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String yearr) {
		year = yearr;
	}
	
	
}
