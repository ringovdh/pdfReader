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

import be.yorian.entities.Omschrijving;
import be.yorian.entities.Transactie;


public class OmschrijvingRepository {
	
	private final SessionFactory sessionFactory;

	public OmschrijvingRepository() {

		StandardServiceRegistry standardRegistry = 
				new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
		this.sessionFactory = metaData.getSessionFactoryBuilder().build();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> geefAlleZoektermen() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession.createQuery("SELECT zoekterm FROM Omschrijving o");
		ArrayList<String> result = (ArrayList<String>) query.list();
		return result;
	}

	public Omschrijving geefOmschrijving(String zoekterm) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession.createQuery("FROM Omschrijving o where zoekterm = :zoekterm");
		query.setParameter("zoekterm", zoekterm);
		Omschrijving omschrijving = (Omschrijving)query.uniqueResult();
		
		return omschrijving;
	}

	public void bewaarZoekterm(Omschrijving omschrijving) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transx = currentSession.beginTransaction();
		currentSession.saveOrUpdate(omschrijving);
		transx.commit();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Omschrijving> geefAlleOmschrijvingen() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession.createQuery("FROM Omschrijving o");
		ArrayList<Omschrijving> result = (ArrayList<Omschrijving>) query.list();
		
		return result;
	}

}
