package BasicClassAccessDbase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Spisak_Prilogenia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int spisak_Prilogenia_ID;
	private String year;
	private String formulyarName;
	private Date startDate;
	private Date endDate;
	private String Zabelejka;
	@ManyToOne
	private Workplace workplace;

	public Spisak_Prilogenia( String formulyarName, String year, Date startDate, Date endDate,
			Workplace workplace, String Zabelejka) {
		
		this.formulyarName = formulyarName;
		this.year = year;
		this.startDate = startDate;
		this.endDate = endDate;
		this.workplace = workplace;
		this.Zabelejka = Zabelejka;
	}
	
	public Spisak_Prilogenia() {
		super();
	}

	public int getSpisak_Prilogenia_ID() {
		return spisak_Prilogenia_ID;
	}

	public void setSpisak_Prilogenia_ID(int spisak_Prilogenia_ID) {
		this.spisak_Prilogenia_ID = spisak_Prilogenia_ID;
	}

	public String getFormulyarName() {
		return formulyarName;
	}

	public void setFormulyarName(String formulyarName) {
		this.formulyarName = formulyarName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getZabelejka() {
		return Zabelejka;
	}

	public void setZabelejka(String zabelejka) {
		Zabelejka = zabelejka;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
}
