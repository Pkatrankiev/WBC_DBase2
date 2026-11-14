package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DimensionWBC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dimensionWBC_ID;
	private String dimensionName;
	private double dimensionScale;
	
	public DimensionWBC(String dimensionName, double dimensionScale) {
		this.dimensionName = dimensionName;
		this.dimensionScale = dimensionScale;
	}
			
		public 	DimensionWBC() {
			super();
		}

		public int getDimensionWBC_ID() {
			return dimensionWBC_ID;
		}

		public void setDimensionWBC_ID(int dimensionWBC_ID) {
			this.dimensionWBC_ID = dimensionWBC_ID;
		}

		public String getDimensionName() {
			return dimensionName;
		}

		public void setDimensionName(String dimensionName) {
			this.dimensionName = dimensionName;
		}

		public double getDimensionScale() {
			return dimensionScale;
		}

		public void setDimensionScale(double dimensionScale) {
			this.dimensionScale = dimensionScale;
		}
	
	
}
