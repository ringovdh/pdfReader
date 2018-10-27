package be.yorian.persistence;

import java.util.ArrayList;

import be.yorian.entities.Periode;
import be.yorian.entities.Transactie;
import javafx.util.Callback;

public class DomeinController {

	private final PersistenceController persistenceController;
	
	public DomeinController() {
		persistenceController = new PersistenceController();
	}

	public void bewaarTransacties(ArrayList<Transactie> transacties) {
		for(Transactie tx : transacties){
			persistenceController.bewaarTransactie(tx);
		}
	}
	
	public void bewaarTransactie(Transactie tx) {
		persistenceController.bewaarTransactie(tx);
	}

	public ArrayList<Transactie> geefTransacties(int maand) {
		return persistenceController.geefTransacties(maand);
	}

	public ArrayList<Transactie> geefPositieveTransactiesPerMaand(int maand) {
		return persistenceController.geefPositieveTransactiesPerMaand(maand);
	}

	public ArrayList<Transactie> geefNegatieveTransactiesPerMaand(int maand) {
		return persistenceController.geefNegatieveTransactiesPerMaand(maand);
	}
	
	public ArrayList<Periode>geefAllePeriodes(){
		return persistenceController.geefAllePeriodes();
	}

	public ArrayList<Transactie> geefTransactiesPerType(String type) {
		return persistenceController.geefTransactiesPerType(type);
	}
}
