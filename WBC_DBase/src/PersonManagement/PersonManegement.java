package PersonManagement;

import BasicClassAccessDbase.Person;

public class PersonManegement {

	private String kodeFromList;
	private Person person;
	
	public PersonManegement(
			Person person,
			String kodeFromList
			) {
		this.person = person;
		this.kodeFromList = kodeFromList;
	}

	public PersonManegement () {
		super();
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getKodeFromList() {
		return kodeFromList;
	}

	public void setKodeFromList(String kodeFromList) {
		this.kodeFromList = kodeFromList;
	}
}		
	

