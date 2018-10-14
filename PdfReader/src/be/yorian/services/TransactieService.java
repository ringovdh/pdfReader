package be.yorian.services;

import java.util.ArrayList;

import be.yorian.entities.Transactie;
import be.yorian.persistence.TransactieRepository;

public class TransactieService {

	private final TransactieRepository repo;
	public TransactieService() {
		this.repo = new TransactieRepository();
	}

	public void bewaarTransacties(ArrayList<Transactie> transacties){
		for(Transactie tx : transacties){
			repo.bewaarTransactie(tx);
		}
	}
	
}
