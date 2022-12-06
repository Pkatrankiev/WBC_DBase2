package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class ResultsWBC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int resultsWBC_ID;
	@ManyToOne
	private Measuring measuring;
	@ManyToOne
	private NuclideWBC nuclideWBC;
	private Double activity;
	private Double postaplenie;
	private Double ggp;
	private Double nuclideDoze;
	
	
	public ResultsWBC(Measuring measuring, NuclideWBC nuclideWBC, Double activity, Double postaplenie, Double ggp, Double nuclideDoze) {
		
		this.measuring = measuring;
		this.nuclideWBC = nuclideWBC;
		this.activity = activity;
		this.postaplenie = postaplenie;
		this.ggp = ggp;
		this.nuclideDoze = nuclideDoze;
		
	}
	
	public ResultsWBC () {
		super();
	}

	public int getResultsWBC_ID() {
		return resultsWBC_ID;
	}

	public void setResultsWBC_ID(int resultsWBC_ID) {
		this.resultsWBC_ID = resultsWBC_ID;
	}

	public Measuring getMeasuring() {
		return measuring;
	}

	public void setMeasuring(Measuring measuring) {
		this.measuring = measuring;
	}

	public NuclideWBC getNuclideWBC() {
		return nuclideWBC;
	}

	public void setNuclideWBC(NuclideWBC nuclideWBC) {
		this.nuclideWBC = nuclideWBC;
	}

	public Double getActivity() {
		return activity;
	}

	public void setActivity(Double activity) {
		this.activity = activity;
	}

	
	public Double getPostaplenie() {
		return postaplenie;
	}

	public void setPostaplenie(Double postaplenie) {
		this.postaplenie = postaplenie;
	}


	public Double getGgp() {
		return ggp;
	}

	public void setGgp(Double ggp) {
		this.ggp = ggp;
	}

	
	public Double getNuclideDoze() {
		return nuclideDoze;
	}

	public void setNuclideDoze(Double nuclideDoze) {
		this.nuclideDoze = nuclideDoze;
	}


	
}
