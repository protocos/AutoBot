package me.protocos.bot.data;

import java.util.List;
import javax.persistence.EntityExistsException;
import me.protocos.bot.model.Pixel;
import me.protocos.bot.model.PixelConstellation;
import me.protocos.bot.model.PixelContainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PixelConstellationDaoTest
{
	private Session session;
	private String name;
	private PixelConstellation pixelConstellation;
	private PixelConstellation retrievedPixelConstellation;
	private PixelConstellationDao dao;
	private PixelContainer pixelContainer;

	@Before
	public void setup()
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		new SchemaExport(configuration).create(true, true);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();

		dao = new PixelConstellationDao(session);
	}

	@Test
	public void ShouldBeUpdateRetrieveItemFromDatabase()
	{
		//ASSEMBLE
		setupPixelConstellationWithFourPoints();

		//ACT
		updateAndRetrieve();

		//ASSERT
		Assert.assertEquals(pixelConstellation, retrievedPixelConstellation);
	}

	@Test
	public void ShouldBeUpdateModifiedPixelConstellationToDatabase()
	{
		//ASSEMBLE
		setupPixelConstellationWithFourPoints();
		updateAndRetrieve();
		setupPixelConstellationWithFivePoints();

		//ACT
		updateAndRetrieve();

		//ASSERT
		Assert.assertEquals(pixelConstellation, retrievedPixelConstellation);
	}

	@Test
	public void ShouldBeUpdatePixelConstellationExist()
	{
		//ASSEMBLE
		setupPixelConstellationWithFourPoints();
		updateAndRetrieve();
		//ACT
		boolean exists = dao.exists(name);
		//ASSERT
		Assert.assertTrue(exists);
	}

	@Test
	public void ShouldBeUpdateRemovePixelConstellationDoesNotExist()
	{
		//ASSEMBLE
		setupPixelConstellationWithFourPoints();
		updateAndRetrieve();
		dao.remove(name);

		//ACT
		boolean exists = dao.exists(name);

		//ASSERT
		Assert.assertFalse(exists);
	}

	@Test
	public void ShouldBeRetrieveAllPixelConstellationsAsList()
	{
		//ASSEMBLE
		setupPixelConstellationWithFourPoints();
		pixelConstellation = new PixelConstellation("name1", pixelContainer);
		updateAndRetrieve();
		pixelConstellation = new PixelConstellation("name2", pixelContainer);
		updateAndRetrieve();

		List<PixelConstellation> allPixelConstellations;

		//ACT
		allPixelConstellations = dao.retrieveAll();

		//ASSERT
		Assert.assertEquals(2, allPixelConstellations.size());
	}

	@Test(expected = EntityExistsException.class)
	public void ShouldBeAddEntityAlreadyExists()
	{
		//ASSEMBLE
		setupPixelConstellationWithFourPoints();
		updateAndRetrieve();
		//ACT
		dao.add(pixelConstellation);
		//ASSERT
	}

	private void updateAndRetrieve()
	{
		dao.update(pixelConstellation);
		retrievedPixelConstellation = dao.retrieve(name);
	}

	private void setupPixelConstellationWithFourPoints()
	{
		name = "test";
		Pixel pixel1 = new Pixel(40, 50, 60);
		Pixel pixel2 = new Pixel(10, 2000, 30);
		Pixel pixel3 = new Pixel(70, 80, 90);
		Pixel pixel4 = new Pixel(40, 25, 60);
		pixelContainer = new PixelContainer();
		pixelContainer.add(pixel1);
		pixelContainer.add(pixel2);
		pixelContainer.add(pixel3);
		pixelContainer.add(pixel4);
		pixelConstellation = new PixelConstellation(name, pixelContainer);
	}

	private void setupPixelConstellationWithFivePoints()
	{
		name = "test";
		Pixel pixel1 = new Pixel(40, 50, 60);
		Pixel pixel2 = new Pixel(10, 2000, 30);
		Pixel pixel3 = new Pixel(70, 80, 90);
		Pixel pixel4 = new Pixel(40, 25, 60);
		Pixel pixel5 = new Pixel(10, 10, 10);
		pixelContainer = new PixelContainer();
		pixelContainer.add(pixel1);
		pixelContainer.add(pixel2);
		pixelContainer.add(pixel3);
		pixelContainer.add(pixel4);
		pixelContainer.add(pixel5);
		pixelConstellation = new PixelConstellation(name, pixelContainer);
	}

	@After
	public void takedown()
	{
		session.close();

	}
}