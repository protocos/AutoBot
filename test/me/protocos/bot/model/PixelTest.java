package me.protocos.bot.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PixelTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeColorCoordinatesEqual()
	{
		//ASSEMBLE
		Pixel coordinate = new Pixel(1, 1, 1);
		//ACT
		//ASSERT
		Assert.assertEquals(new Pixel(1, 1, 1), coordinate);
	}

	@After
	public void takedown()
	{
	}
}