package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Zone implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Zone;
	private String nameTerritory;
	
	public Zone (int id_Zone, String nameTerritory) {
		this.id_Zone = id_Zone;
		this.nameTerritory = nameTerritory;
	}
	
	public Zone() {
		super();
	}

	public int getId_Zone() {
		return id_Zone;
	}

	public void setId_Zone(int id_Zone) {
		this.id_Zone = id_Zone;
	}

	public String getNameTerritory() {
		return nameTerritory;
	}

	public void setNameTerritory(String nameTerritory) {
		this.nameTerritory = nameTerritory;
	}
	
	
	
}
