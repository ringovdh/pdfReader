package be.yorian.persistence;

import java.util.ArrayList;

import be.yorian.entities.Periode;
import be.yorian.entities.Transactie;

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

	public String geefPeriodeNaam(int id) {
		return persistenceController.geefPeriodeNaam(id);
	}

	public ArrayList<Transactie> geefAllePositieveTransacties() {
		return persistenceController.geefAllePositieveTransacties();
	}

	public ArrayList<Transactie> geefAlleNegatieveTransacties() {
		return persistenceController.geefAlleNegatieveTransacties();
	}

	public ArrayList<Transactie> geefAllePositieveTransactiesVoorZichtRekening() {
		return persistenceController.geefAllePositieveTransactiesVoorZichtRekening();
	}

	public ArrayList<Transactie> geefAlleNegatieveTransactiesVoorZichtRekening() {
		return persistenceController.geefAlleNegatieveTransactiesVoorZichtRekening();
	}

	public ArrayList<Transactie> geefAllePositieveTransactiesVoorSpaarRekening() {
		return persistenceController.geefAllePositieveTransactiesVoorSpaarRekening();
	}
	
	public ArrayList<Transactie> geefAlleNegatieveTransactiesVoorSpaarRekening() {
		return persistenceController.geefAlleNegatieveTransactiesVoorSpaarRekening();
	}

	public Transactie controleerTransactie(String txNummer, int periode) {
		return persistenceController.controleerTransactie(txNummer, periode);
	}

	public void bewerkTransactie(Transactie tx) {
		// TODO Auto-generated method stub
		
	}
}
