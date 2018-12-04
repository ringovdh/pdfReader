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

import be.yorian.entities.Periode;

public class PeriodeRepository {

	private final SessionFactory sessionFactory;

	public PeriodeRepository() {
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
				.build();
		Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
		this.sessionFactory = metaData.getSessionFactoryBuilder().build();
	}

	public void bewaarPeriode(Periode periode) {

		Session currentSession = sessionFactory.getCurrentSession();
		Transaction transx = currentSession.beginTransaction();
		currentSession.saveOrUpdate(periode);
		transx.commit();

	}

	public ArrayList<Periode> geefAllePeriodes() {

		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession.createQuery("FROM Periode p");

		ArrayList<Periode> result = (ArrayList<Periode>) query.list();

		return result;
	}

	public String geefPeriodeNaam(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Query query = currentSession.createQuery("FROM Periode p where p.id  = :id");
		query.setParameter("id", id);
		
		Periode result = (Periode) query.uniqueResult();

		return result.getWaarde();
	}

}
