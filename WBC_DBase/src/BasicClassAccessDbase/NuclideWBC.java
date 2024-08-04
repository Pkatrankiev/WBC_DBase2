package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class NuclideWBC implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nuclideWBC_ID;

	private String name_bg;
	private String name_en;
	private String symbol;
	private Double half_life;
	

	public NuclideWBC (String name_bg, String name_en, String symbol, Double half_life) {
		super();
		
		this.name_bg = name_bg;
		this.name_en = name_en;
		this.symbol = symbol;
		this.half_life = half_life;
		
	}

	public NuclideWBC () {
		super();
	}
	
	public void setNuclideWBC_ID(int nuclideWBC_ID) {
		this.nuclideWBC_ID = nuclideWBC_ID;
	}
	
	public int getId_nuclide() {
		return nuclideWBC_ID;
	}

	public String getName_bg_nuclide() {
		return name_bg;
	}

	public void setName_bg_nuclide(String name) {
		this.name_bg = name;
	}
	
	public String getName_en_nuclide() {
		return name_en;
	}

	public void setName_en_nuclide(String name) {
		this.name_en = name;
	}

	public String getSymbol_nuclide() {
		return symbol;
	}

	public void setSymbol_nuclide(String symbol) {
		this.symbol = symbol;
	}

	public Double getHalf_life_nuclide() {
		return half_life;
	}

	public void setHalf_life_nuclide(Double half_life) {
		this.half_life = half_life;
	}

	}

