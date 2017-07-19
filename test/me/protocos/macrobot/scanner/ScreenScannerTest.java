package me.protocos.macrobot.scanner;

import java.awt.AWTException;
import org.junit.Assert;
import org.junit.Test;

public class ScreenScannerTest
{
	@Test
	public void shouldBeSpeedTest() throws AWTException
	{
		//ASSEMBLE
		long totalTimeThreshold = 1000;
		ScreenScanner screenScanner = new ScreenScanner();

		//ACT
		long startTime = System.currentTimeMillis();
		screenScanner.scan();
		long stopTime = System.currentTimeMillis();

		//ASSERT
		System.out.println(stopTime - startTime);
		Assert.assertTrue(stopTime - startTime < totalTimeThreshold);
	}
}
