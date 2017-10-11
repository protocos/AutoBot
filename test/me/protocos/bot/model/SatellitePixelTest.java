package me.protocos.bot.model;

import java.awt.Color;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SatellitePixelTest
{
	private Pixel centerPixel;
	private Pixel otherPixel;
	private SatellitePixel satellitePixel;

	@Before
	public void setup()
	{
		String name = "name";
		centerPixel = new Pixel(50, 50, 50);
		otherPixel = new Pixel(10, 20, 30);
		PixelContainer pixelContainer = new PixelContainer();
		pixelContainer.add(centerPixel);
		pixelContainer.add(otherPixel);
		PixelConstellation pixelConstellation = new PixelConstellation(name, pixelContainer);
		satellitePixel = new SatellitePixel(pixelConstellation, otherPixel);
	}

	@Test
	public void ShouldBeSatellitePixelGetXRelativeToCenterPixel()
	{
		//ASSEMBLE
		//ACT
		int relativeX = satellitePixel.getX();
		//ASSERT
		Assert.assertEquals(-40, relativeX);
	}

	@Test
	public void ShouldBeSatellitePixelGetYRelativeToCenterPixel()
	{
		//ASSEMBLE
		//ACT
		int relativeY = satellitePixel.getY();
		//ASSERT
		Assert.assertEquals(-30, relativeY);
	}

	@Test
	public void ShouldBeSatellitePixelGetRGB()
	{
		//ASSEMBLE
		//ACT
		int RGB = satellitePixel.getRGB();
		//ASSERT
		Assert.assertEquals(otherPixel.getRGB(), RGB);
	}

	@Test
	public void ShouldBeSatellitePixelGetColor()
	{
		//ASSEMBLE
		//ACT
		Color color = satellitePixel.getColor();
		//ASSERT
		Assert.assertEquals(otherPixel.getColor(), color);
	}

	@After
	public void takedown()
	{
	}
}