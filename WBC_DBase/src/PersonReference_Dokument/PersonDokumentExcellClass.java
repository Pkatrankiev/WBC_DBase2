package PersonReference_Dokument;

import java.util.Date;

import BasicClassAccessDbase.Person;

public class PersonDokumentExcellClass {

	private Person person;
	private String kz1;
	private String kz2;
	private String hog;
	private Date StartDate;
	private Date EndDate;
	private String otdel;
	private String dokument;
	
	public PersonDokumentExcellClass( 
			Person person,
			String kz1,
			String kz2,
			String hog,
			Date StartDate,
			Date EndDate,
			String otdel,
			String dokument
			) {
		this.person = person;
		this. kz1 = kz1;
		this.  kz2 = kz2;
		this.  hog = hog;
		this. StartDate = StartDate;
		this. EndDate = EndDate;
		this. otdel = otdel;
		this.dokument = dokument;
		
	}
	public PersonDokumentExcellClass() {
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
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public String getDokument() {
		return dokument;
	}
	public void setDokument(String dokument) {
		this.dokument = dokument;
	}

	
}
