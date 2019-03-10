package be.yorian.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "omschrijving")
public class Omschrijving {

	@Id
	@Column(name = "omschrijving_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String zoekterm;
	private String omschrijving;
	private String categorie;
	
	public Omschrijving() {
		
	}
	
	public Omschrijving(String zoekterm, String omschrijving, String categorie) {
		
		this.zoekterm = zoekterm;
		this.omschrijving = omschrijving;
		this.categorie = categorie;
	}

	
	
	public Omschrijving(String zoekterm, String categorie) {
		this.zoekterm = zoekterm;
		this.categorie = categorie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	public String getZoekterm() {
		return zoekterm;
	}

	public void setZoekterm(String zoekterm) {
		this.zoekterm = zoekterm;
	}

	
	
	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	
	
	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	
}
