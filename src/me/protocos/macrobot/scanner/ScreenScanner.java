package me.protocos.macrobot.scanner;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenScanner
{
	private Robot robot;
	private Rectangle screenRectangle;

	public ScreenScanner() throws AWTException
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenRectangle = new Rectangle((int) screenSize.getWidth(), (int) screenSize.getHeight());
		robot = new Robot();
	}

	public void scan()
	{
		BufferedImage screenCapture = robot.createScreenCapture(screenRectangle);

		int height = (int) screenRectangle.getHeight();
		int width = (int) screenRectangle.getWidth();

		int[][] result = new int[height][width];
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				result[y][x] = screenCapture.getRGB(x, y);
			}
		}
	}
}
