package com.github.rafaelsouzaf.prot.server.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure().buildSessionFactory();
			
//			Configuration cfg = new Configuration();
//			cfg.addClass(ProductModel.class);
//			cfg.addClass(UserModel.class);
////			cfg.addClass(EditionModel.class);
//			
//		    cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//		    cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/prot");
//		    cfg.setProperty("hibernate.connection.username", "root");
//		    cfg.setProperty("hibernate.connection.password", "");
//		    cfg.setProperty("hibernate.connection.pool_size", "1");
//		    cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		    cfg.setProperty("hibernate.current_session_context_class", "thread");
//		    cfg.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
//		    cfg.setProperty("hibernate.show_sql", "true");
//		    cfg.setProperty("hibernate.hbm2ddl.auto", "update");
//			
//		    sessionFactory = cfg.buildSessionFactory();
//		    System.out.println("Ok, conectado.");
			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}