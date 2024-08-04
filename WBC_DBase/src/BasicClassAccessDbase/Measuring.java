package BasicClassAccessDbase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


public class Measuring implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int measuring_ID;
	private Date date;
	private double doze;
	@ManyToOne
	private DimensionWBC doseDimension;
	@ManyToOne
	private Person person;
	@ManyToOne
	private Laboratory lab;
	@ManyToOne
	private UsersWBC user;
	@ManyToOne
	private TypeMeasur typeMeasur;
	private String measurKoment;
	private String reportFileName;
	private String excelPosition;
	
	
	
	public Measuring(
			Person person,
			Date date,
			double doze,
			DimensionWBC doseDimension,
			Laboratory lab,
			UsersWBC user,
			TypeMeasur typeMeasur,
			 String measurKoment, 
			String reportFileName,
			String excelPosition) {
		
		this.person = person;
		this.date = date;
		this.doze = doze;
		this.doseDimension = doseDimension;
		this.lab = lab;
		this.user = user;
		this.typeMeasur = typeMeasur;
		this.measurKoment = measurKoment;
		this.reportFileName = reportFileName;
		this.excelPosition = excelPosition;
		
	}
	
	public Measuring() {
		super();
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public int getMeasuring_ID() {
		return measuring_ID;
	}

	public void setMeasuring_ID(int measuring_ID) {
		this.measuring_ID = measuring_ID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getDoze() {
		return doze;
	}

	public void setDoze(double doze) {
		this.doze = doze;
	}

	

	public DimensionWBC getDoseDimension() {
		return doseDimension;
	}

	public void setDoseDimension(DimensionWBC doseDimension) {
		this.doseDimension = doseDimension;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Laboratory getLab() {
		return lab;
	}

	public void setLab(Laboratory lab) {
		this.lab = lab;
	}

	public UsersWBC getUser() {
		return user;
	}

	public void setUser(UsersWBC user) {
		this.user = user;
	}

	public String getMeasurKoment() {
		return measurKoment;
	}

	public void setMeasurKoment(String measurKoment) {
		this.measurKoment = measurKoment;
	}

	public TypeMeasur getTypeMeasur() {
		return typeMeasur;
	}

	public void setTypeMeasur(TypeMeasur typeMeasur) {
		this.typeMeasur = typeMeasur;
	}

	public String getExcelPosition() {
		return excelPosition;
	}

	public void setExcelPosition(String excelPosition) {
		this.excelPosition = excelPosition;
	}

	
	
	
	
}
