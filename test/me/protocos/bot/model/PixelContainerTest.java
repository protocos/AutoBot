package me.protocos.bot.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PixelContainerTest
{
	private PixelContainer pixelContainer;

	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeNewPixelContainer()
	{
		//ASSEMBLE
		pixelContainer = new PixelContainer();
		//ACT
		//ASSERT
		Assert.assertNotNull(pixelContainer);
	}

	@Test
	public void ShouldBeAddDuplicatePixel()
	{
		//ASSEMBLE
		pixelContainer = new PixelContainer();
		Pixel pixel1 = new Pixel(0, 0, 0);
		Pixel pixel2 = new Pixel(0, 0, 10);
		//ACT
		pixelContainer.add(pixel1);
		pixelContainer.add(pixel2);
		//ASSERT
		Assert.assertEquals(1, pixelContainer.size());
	}

	@Test
	public void ShouldBeGetFirstPixel()
	{
		//ASSEMBLE
		pixelContainer = new PixelContainer();
		Pixel pixel1 = new Pixel(0, 0, 0);
		Pixel pixel2 = new Pixel(0, 0, 10);
		//ACT
		pixelContainer.add(pixel1);
		pixelContainer.add(pixel2);
		//ASSERT
		Assert.assertEquals(pixel1, pixelContainer.getFirstPixel());
	}

	@After
	public void takedown()
	{
	}
}