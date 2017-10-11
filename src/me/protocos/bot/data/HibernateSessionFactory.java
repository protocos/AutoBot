package me.protocos.bot.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSessionFactory
{
	private static Session session;

	private HibernateSessionFactory()
	{
	}

	public static Session getInstance()
	{
		if (session == null)
		{
			Configuration configuration = new Configuration();
			configuration.configure();

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			session = sessionFactory.openSession();
		}
		return session;
	}

	public static void closeSession()
	{
		if (session != null)
		{
			session.close();
			session = null;
		}
	}
}
