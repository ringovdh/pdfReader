package be.yorian.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactie")
public class Transactie {
	
	@Id
	@Column(name = "tx_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String txnummer;
	private String omschrijving;
	private BigDecimal bedrag;
	private String teken;
	private Date datum;
	private String categorie;
	private int periode;
	
	
	public Transactie() {
		
	}
	
	public Transactie(String id) {
		this.txnummer = id; 
	}
	
	

	public String getTxnummer() {
		return txnummer;
	}

	public void setTxnummer(String txnummer) {
		this.txnummer = txnummer;
	}
	

	
	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	
	
	public BigDecimal getBedrag() {
		return bedrag;
	}

	public void setBedrag(String bedrag) {
	
		BigDecimal waarde = null;
	
		String correctbedrag = bedrag.replace(".", ""); 
		String juist = correctbedrag.replace(",", ".");
		try {
			waarde = new BigDecimal(juist);
		} catch(Exception e) {
			System.out.println(bedrag + " is geen bedrag!");
			e.printStackTrace();
		}
		this.bedrag = waarde;
	}
	
	public void setPositiefBedrag(String bedrag) {
		setBedrag(bedrag.substring(0, bedrag.length() - 1));
	}

	
	
	public String getTeken() {
		return teken;
	}

	public void setTeken(String teken) {
		this.teken = teken;
	}

	
	
	public String getDatum() {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(datum);
	}

	public void setDatum(String datum) throws ParseException {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		this.datum = simpleDateFormat.parse(datum);
	}

	
	
	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	

	public int getPeriode() {
		return periode;
	}

	public void setPeriode(int periode) {
		this.periode = periode;
	}

	
	
	@Override
	public String toString() {
		return "Transactie [txnummer=" + txnummer + ", omschrijving=" + omschrijving + ", bedrag=" + bedrag + ", teken="
				+ teken + ", datum=" + datum + "]";
	}
	
}
