package be.yorian.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "periode")
public class Periode {

	@Id
	@Column(name = "periode_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String waarde;

	
	

	public int getId() {
		return id;
	}
	
	
	public String getWaarde() {
		return waarde;
	}

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}


	@Override
	public String toString() {
		return waarde;
	}
	
	

}
