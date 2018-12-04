package be.yorian.gui;

import java.math.BigDecimal;

import be.yorian.entities.Transactie;
import javafx.collections.ObservableList;

public class CommonFrameController {
	
	public BigDecimal berekenTotaal(ObservableList<Transactie> resultaten) {

		BigDecimal totaal = new BigDecimal(0);

		for (Transactie tx : resultaten) {
			totaal = totaal.add(tx.getBedrag());
		}

		return totaal;
	
	}

}
