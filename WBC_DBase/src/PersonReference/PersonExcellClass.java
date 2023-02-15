package PersonReference;

import BasicClassAccessDbase.Person;

public class PersonExcellClass {

	private Person person;
	private String kz1;
	private String kz2;
	private String hog;
	private String otdel;
	
	public PersonExcellClass( 
			Person person,
			String kz1,
			String kz2,
			String hog,
			String otdel
			) {
		this.person = person;
		this. kz1 = kz1;
		this.  kz2 = kz2;
		this.  hog = hog;
		this. otdel = otdel;
	}
	public PersonExcellClass() {
		super();
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getKz1() {
		return kz1;
	}
	public void setKz1(String kz1) {
		this.kz1 = kz1;
	}
	public String getKz2() {
		return kz2;
	}
	public void setKz2(String kz2) {
		this.kz2 = kz2;
	}
	public String getHog() {
		return hog;
	}
	public void setHog(String hog) {
		this.hog = hog;
	}
	public String getOtdel() {
		return otdel;
	}
	public void setOtdel(String otdel) {
		this.otdel = otdel;
	}

	
}
