package be.yorian.persistence;

import java.util.ArrayList;

import be.yorian.entities.Omschrijving;
import be.yorian.entities.Periode;
import be.yorian.entities.Transactie;

public class PersistenceController {
	
	private TransactieRepository transactieRepository;
	private PeriodeRepository periodeRepository;
	private OmschrijvingRepository omschrijvingRepository;

	public void bewaarTransactie(Transactie tx) {
		getTransactieRepositorie().bewaarTransactie(tx);		
	}
	
	public ArrayList<Transactie> geefTransacties(int maand) {
		return getTransactieRepositorie().geefTransacties(maand);
	}

	public ArrayList<Transactie> geefPositieveTransactiesPerMaand(int maand) {
		return getTransactieRepositorie().geefPositieveTransactiesPerMaand(maand);
	}

	public ArrayList<Transactie> geefNegatieveTransactiesPerMaand(int maand) {
		return getTransactieRepositorie().geefNegatieveTransactiesPerMaand(maand);
	}
	
	public ArrayList<Periode> geefAllePeriodes() {
		return getPeriodeRepository().geefAllePeriodes();
	}
	
	public String geefPeriodeNaam(int id) {
		return getPeriodeRepository().geefPeriodeNaam(id);
	}
	
	public ArrayList<Transactie> geefTransactiesPerType(String type) {
		return getTransactieRepositorie().geefTransactiesPerType(type);
	}
	
	public ArrayList<Transactie> geefAllePositieveTransacties() {
		return getTransactieRepositorie().geefAllePositieveTransacties();
	}

	public ArrayList<Transactie> geefAlleNegatieveTransacties() {
		return getTransactieRepositorie().geefAlleNegatieveTransacties();
	}
	
	public ArrayList<Transactie> geefAllePositieveTransactiesVoorZichtRekening() {
		return getTransactieRepositorie().geefAllePositieveTransactiesVoorZichtRekening();
	}
	
	public ArrayList<Transactie> geefAlleNegatieveTransactiesVoorZichtRekening() {
		return getTransactieRepositorie().geefAlleNegatieveTransactiesVoorZichtRekening();
	}
	
	public ArrayList<Transactie> geefAllePositieveTransactiesVoorSpaarRekening() {
		return getTransactieRepositorie().geefAllePositieveTransactiesVoorSpaarRekening();
	}
	
	public ArrayList<Transactie> geefAlleNegatieveTransactiesVoorSpaarRekening() {
		return getTransactieRepositorie().geefAlleNegatieveTransactiesVoorSpaarRekening();
	}
	
	public Transactie controleerTransactie(String txNummer, int periode) {
		return getTransactieRepositorie().controleerTransactie(txNummer, periode);
	}
	
	public ArrayList<String> geefAlleZoektermen() {
		return getOmschrijvingRepository().geefAlleZoektermen();
	}
	
	public Omschrijving geefOmschrijving(String zoekterm) {
		return getOmschrijvingRepository().geefOmschrijving(zoekterm);
	}
	
	public void bewaarZoekterm(Omschrijving omschrijving) {
		getOmschrijvingRepository().bewaarZoekterm(omschrijving);
	}
	
	public ArrayList<Omschrijving> geefAlleOmschrijvingen() {
		return getOmschrijvingRepository().geefAlleOmschrijvingen();
	}

	private TransactieRepository getTransactieRepositorie() {
		if(transactieRepository == null) {
			transactieRepository = new TransactieRepository();
		}
		return transactieRepository;
	}

	private PeriodeRepository getPeriodeRepository() {
		if (periodeRepository == null) {
			periodeRepository = new PeriodeRepository();
		}
		return periodeRepository;
	}

	private OmschrijvingRepository getOmschrijvingRepository() {
		if (omschrijvingRepository == null) {
			omschrijvingRepository = new OmschrijvingRepository();
		}
		return omschrijvingRepository;
	}

}
