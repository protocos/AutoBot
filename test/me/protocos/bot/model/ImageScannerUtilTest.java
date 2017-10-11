package me.protocos.bot.model;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageScannerUtilTest
{
	private BufferedImage image;
	private PixelContainer pixelContainer;
	private PixelConstellation pixelConstellation;

	@Before
	public void setup()
	{
		image = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
		image.setRGB(4, 4, 0xffff0000);
		image.setRGB(5, 5, 0xffff0000);
		image.setRGB(6, 6, 0xffff0000);
		pixelContainer = new PixelContainer();
		pixelContainer.add(new Pixel(3, 3, new Color(0xffff0000)));
		pixelContainer.add(new Pixel(4, 4, new Color(0xffff0000)));
		pixelContainer.add(new Pixel(2, 2, new Color(0xffff0000)));
		pixelConstellation = new PixelConstellation("test", pixelContainer);
	}

	@Test
	public void ShouldBeSetOfCenterPointsWherePixelConstellationMatches()
	{
		//ASSEMBLE
		//ACT
		Set<Point> pixelConstellationMatches = ImageScannerUtil.scan(image, pixelConstellation);
		//ASSERT
		Assert.assertTrue(pixelConstellationMatches.contains(new Point(5, 5)));
		Assert.assertEquals(1, pixelConstellationMatches.size());
	}

	@Test
	public void ShouldBeEdgeCases()
	{
		//ASSEMBLE
		// RED pixels
		image.setRGB(0, 0, 0xffff0000);
		image.setRGB(1, 1, 0xffff0000);
		image.setRGB(2, 2, 0xffff0000);
		// BLACK pixels
		image.setRGB(3, 3, 0xff000000);
		image.setRGB(4, 4, 0xff000000);
		image.setRGB(5, 5, 0xff000000);
		image.setRGB(6, 6, 0xff000000);
		// RED pixels
		image.setRGB(7, 7, 0xffff0000);
		image.setRGB(8, 8, 0xffff0000);
		image.setRGB(9, 9, 0xffff0000);
		//ACT
		Set<Point> pixelConstellationMatches = ImageScannerUtil.scan(image, pixelConstellation);
		//ASSERT
		Assert.assertTrue(pixelConstellationMatches.contains(new Point(1, 1)));
		Assert.assertTrue(pixelConstellationMatches.contains(new Point(8, 8)));
		Assert.assertEquals(2, pixelConstellationMatches.size());
	}

	@After
	public void takedown()
	{
	}
}