package be.yorian.entities;

import java.math.BigDecimal;

import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Spaarpot {
	
	private final DomeinController domeinController;
	@FXML
    private Label zichtRekening;
	@FXML
	private Label spaarRekening;

	
	public Spaarpot(DomeinController domeinController, Label zichtRekening, Label spaarRekening) {
		
		this.domeinController = domeinController;
		this.zichtRekening = zichtRekening;
		this.spaarRekening = spaarRekening;
		
	}

	public void berekenZichtRekening() {

		ObservableList<Transactie> positieveResultaten = FXCollections
				.observableArrayList(domeinController.geefAllePositieveTransactiesVoorZichtRekening());
		ObservableList<Transactie> negatieveResultaten = FXCollections
				.observableArrayList(domeinController.geefAlleNegatieveTransactiesVoorZichtRekening());
		BigDecimal totaalPositief = berekenTotaal(positieveResultaten);
		BigDecimal totaalNegatief = berekenTotaal(negatieveResultaten);			
		
		zichtRekening.setText((totaalPositief.subtract(totaalNegatief)).toString());
		
	}
	
	

	public void berekenSpaarRekening() {
		ObservableList<Transactie> positieveResultaten = FXCollections
				.observableArrayList(domeinController.geefAllePositieveTransactiesVoorSpaarRekening());
		ObservableList<Transactie> negatieveResultaten = FXCollections
				.observableArrayList(domeinController.geefAlleNegatieveTransactiesVoorSpaarRekening());
		BigDecimal totaalPositief = berekenTotaal(positieveResultaten);
		BigDecimal totaalNegatief = berekenTotaal(negatieveResultaten);
		
		spaarRekening.setText((totaalPositief.subtract(totaalNegatief)).toString());
		
	}
	
	public void herBereken(Transactie tx) {
		if(tx.getCategorie().equals("sparen") || tx.getCategorie().equals("opnamespaargeld")) {
			herberekenSpaarRekening(tx.getBedrag(), tx.getTeken());
			herberekenZichtRekening(tx.getBedrag(), tx.getTeken());
		} else {
			herberekenZichtRekening(tx.getBedrag(), tx.getTeken());
		}
	}
	
	public void herberekenZichtRekening(BigDecimal bedrag, String teken) {
		
		BigDecimal totaal = new BigDecimal(zichtRekening.getText());
		BigDecimal nieuwTotaal = new BigDecimal(0);
		if (teken.equals("+")){
			nieuwTotaal = totaal.add(bedrag);
		}
		if (teken.equals("-")){
			nieuwTotaal = totaal.subtract(bedrag);
		}
		
		zichtRekening.setText(nieuwTotaal.toString());
	}

	private void herberekenSpaarRekening(BigDecimal bedrag, String teken) {
		
		BigDecimal totaal = new BigDecimal(spaarRekening.getText());
		BigDecimal nieuwTotaal = new BigDecimal(0);
		if (teken.equals("+")){
			nieuwTotaal = totaal.add(bedrag);
		}
		if (teken.equals("-")){
			nieuwTotaal = totaal.subtract(bedrag);
		}
		
		spaarRekening.setText(nieuwTotaal.toString());
		
	}
	
	private BigDecimal berekenTotaal(ObservableList<Transactie> resultaten) {

		BigDecimal totaal = new BigDecimal(0);

		for (Transactie tx : resultaten) {
			totaal = totaal.add(tx.getBedrag());
		}

		return totaal;

	}

	
}
