package be.yorian.persistence;

import java.util.ArrayList;

import be.yorian.entities.Periode;
import be.yorian.entities.Transactie;

public class PersistenceController {
	
	private TransactieRepository transactieRepository;
	private PeriodeRepository periodeRepository;

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

}
