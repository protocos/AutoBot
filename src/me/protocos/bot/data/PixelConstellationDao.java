package me.protocos.bot.data;

import java.util.List;
import javax.persistence.EntityExistsException;
import me.protocos.bot.model.PixelConstellation;
import org.hibernate.Session;

public class PixelConstellationDao
{
	private Session session;

	public PixelConstellationDao(Session session)
	{
		this.session = session;
	}

	public void add(PixelConstellation pixelConstellation)
	{
		if (this.exists(pixelConstellation.getName()))
			throw new EntityExistsException("Entity with name '" + pixelConstellation.getName() + "' already exists");
		session.beginTransaction();
		session.save(pixelConstellation);
		session.getTransaction().commit();
	}

	public void update(PixelConstellation pixelConstellation)
	{
		session.beginTransaction();
		session.merge(pixelConstellation);
		session.getTransaction().commit();
	}

	public boolean exists(String name)
	{
		session.beginTransaction();
		boolean exists = session.get(PixelConstellation.class, name) != null;
		session.getTransaction().commit();
		return exists;
	}

	public PixelConstellation retrieve(String name)
	{
		session.beginTransaction();
		PixelConstellation retrievedPixelConstellation = (PixelConstellation) session.get(PixelConstellation.class, name);
		session.getTransaction().commit();
		return retrievedPixelConstellation;
	}

	public void remove(String name)
	{
		if (exists(name))
		{
			PixelConstellation persistentConstellation = retrieve(name);
			session.beginTransaction();
			session.delete(persistentConstellation);
			session.getTransaction().commit();
		}
	}

	public List<PixelConstellation> retrieveAll()
	{
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<PixelConstellation> list = session.createCriteria(PixelConstellation.class).list();
		session.getTransaction().commit();
		return list;
	}

}
