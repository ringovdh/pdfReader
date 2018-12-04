package be.yorian.persistence;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import be.yorian.entities.Transactie;

public class TransactieRepository {

	private final SessionFactory sessionFactory;

	public TransactieRepository() {
		StandardServiceRegistry standardRegistry = 
				new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
		this.sessionFactory = metaData.getSessionFactoryBuilder().build();
	}

	public void bewaarTransactie(Transactie tx) {

		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transx = currentSession.beginTransaction();
		currentSession.saveOrUpdate(tx);
		transx.commit();

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefTransacties(int maand) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE MONTH(tx.datum) = :maand");
		query.setParameter("maand", maand);
		
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefPositieveTransactiesPerMaand(int maand) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.periode = :maand AND tx.teken = :teken");
		query.setParameter("maand", maand);
		query.setParameter("teken", "+");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefNegatieveTransactiesPerMaand(int maand) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.periode = :maand AND tx.teken = :teken");
		query.setParameter("maand", maand);
		query.setParameter("teken", "-");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefAllePositieveTransacties() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.teken = :teken");
		query.setParameter("teken", "+");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefAlleNegatieveTransacties() {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.teken = :teken");
		query.setParameter("teken", "-");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefTransactiesPerType(String categorie) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.categorie = :categorie");
		query.setParameter("categorie", categorie);
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefAllePositieveTransactiesVoorZichtRekening() {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.categorie != :categorie AND tx.teken = :teken");
		query.setParameter("categorie", "SPAREN");
		query.setParameter("teken", "+");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefAlleNegatieveTransactiesVoorZichtRekening() {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.categorie != :categorie AND tx.teken = :teken");
		query.setParameter("categorie", "SPAREN");
		query.setParameter("teken", "-");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefAllePositieveTransactiesVoorSpaarRekening() {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.categorie = :categorie AND tx.teken = :teken");
		query.setParameter("categorie", "SPAREN");
		query.setParameter("teken", "+");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Transactie> geefAlleNegatieveTransactiesVoorSpaarRekening() {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.categorie = :categorie AND tx.teken = :teken");
		query.setParameter("categorie", "SPAREN");
		query.setParameter("teken", "-");
		
		ArrayList<Transactie> result = (ArrayList<Transactie>) query.list();
		
		return result;
	}

	public Transactie controleerTransactie(String txNummer, int periode) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession
				.createQuery("FROM Transactie tx WHERE tx.txnummer = :txNummer AND tx.periode = :periode");
		query.setParameter("txNummer", txNummer);
		query.setParameter("periode", periode);
		
		Transactie tx = (Transactie)query.uniqueResult();
		
		return tx;
	}

}
