package be.yorian.gui;

import java.math.BigDecimal;
import java.util.ArrayList;

import be.yorian.entities.Categorie;
import be.yorian.entities.Transactie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommonFrameController {
	
	public BigDecimal berekenTotaal(ObservableList<Transactie> resultaten) {

		BigDecimal totaal = new BigDecimal(0);

		for (Transactie tx : resultaten) {
			totaal = totaal.add(tx.getBedrag());
		}

		return totaal;
	
	}

	public ObservableList<Categorie> vulCategorieLijst() {
		
		ArrayList<Categorie> types = new ArrayList<>();
		for (Categorie type : Categorie.values()) {
			types.add(type);
		}
		
		return FXCollections.observableArrayList(types);
	}

}
