package me.protocos.bot.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PixelConstellationTest
{
	private Pixel pixel1;
	private Pixel pixel2;
	private Pixel pixel3;
	private PixelContainer pixelContainer;
	private PixelConstellation pixelConstellation;

	@Before
	public void setup()
	{
	}

	@Test(expected = AssertionError.class)
	public void ShouldBePixelConstellationMustHaveAtLeastOnePixel()
	{
		//ASSEMBLE
		pixelContainer = new PixelContainer();
		//ACT
		pixelConstellation = new PixelConstellation("test", pixelContainer);
	}

	@Test
	public void ShouldBePixelConstellationGetCenterPixel()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		Pixel centerPixel = pixelConstellation.getCenterPixel();
		//ASSERT
		Assert.assertEquals(pixel1, centerPixel);
	}

	@Test
	public void ShouldBeGetLeftBoundFromCenterPixel()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		int leftConstellationBound = pixelConstellation.getLeftConstellationBound();
		//ASSERT
		Assert.assertEquals(40, leftConstellationBound);
	}

	@Test
	public void ShouldBeGetRightBoundFromCenterPixel()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		int rightConstellationBound = pixelConstellation.getRightConstellationBound();
		//ASSERT
		Assert.assertEquals(55, rightConstellationBound);
	}

	@Test
	public void ShouldBeGetTopBoundFromCenterPixel()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		int topConstellationBound = pixelConstellation.getTopConstellationBound();
		//ASSERT
		Assert.assertEquals(30, topConstellationBound);
	}

	@Test
	public void ShouldBeGetBottomBoundFromCenterPixel()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		int bottomConstellationBound = pixelConstellation.getBottomConstellationBound();
		//ASSERT
		Assert.assertEquals(60, bottomConstellationBound);
	}

	@Test
	public void ShouldBeGetBoundingWidth()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		int boundingWidth = pixelConstellation.getBoundingWidth();
		//ASSERT
		Assert.assertEquals(95, boundingWidth);
	}

	@Test
	public void ShouldBeGetBoundingHeight()
	{
		//ASSEMBLE
		setUpPixelContainerWithBasicData();
		//ACT
		int boundingHeight = pixelConstellation.getBoundingHeight();
		//ASSERT
		Assert.assertEquals(90, boundingHeight);
	}

	private void setUpPixelContainerWithBasicData()
	{
		pixelContainer = new PixelContainer();
		pixel1 = new Pixel(50, 50, 50);
		pixel2 = new Pixel(10, 20, 30);
		pixel3 = new Pixel(105, 110, 120);
		pixelContainer.add(pixel1);
		pixelContainer.add(pixel2);
		pixelContainer.add(pixel3);
		pixelConstellation = new PixelConstellation("test", pixelContainer);
	}

	@After
	public void takedown()
	{
	}
}