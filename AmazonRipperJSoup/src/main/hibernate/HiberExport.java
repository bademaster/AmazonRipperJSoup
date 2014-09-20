package main.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HiberExport {


	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	public void exportToDB (ReviewData iData) {
		
		
		try {
			SessionFactory sessionFactory = createSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(iData);
			session.getTransaction().commit();
			session.close();
			sessionFactory.close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	public static SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
	
}
