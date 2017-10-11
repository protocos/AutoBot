package me.protocos.bot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class Modbot
{
	private volatile static Modbot instance;
	private volatile int defaultDelay;
	private volatile Dimension screenSize;
	private volatile Robot robot;

	private Modbot()
	{
		defaultDelay = 50;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
	}

	public static Modbot getInstance()
	{
		if (instance == null)
		{
			instance = new Modbot();
		}
		return instance;
	}

	public void setDefaultDelay(int defaultDelay)
	{
		this.defaultDelay = defaultDelay;
	}

	public void delay()
	{
		robot.delay(defaultDelay);
	}

	public void delay(int delay)
	{
		robot.delay(delay);
	}

	public void move(Point point)
	{
		move(point.x, point.y);
	}

	public void move(int x, int y)
	{
		robot.mouseMove(x, y);
		robot.delay(defaultDelay);
	}

	public void click()
	{
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(defaultDelay);
	}

	public void clickHold()
	{
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}

	public void clickRelease()
	{
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void rightClick()
	{
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
		robot.delay(defaultDelay);
	}

	public Color getPixelColor(int x, int y)
	{
		return robot.getPixelColor(x, y);
	}

	public int getPixelRGB(int x, int y)
	{
		return this.getPixelColor(x, y).getRGB();
	}

	public void key(int keycode)
	{
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
		robot.delay(defaultDelay);
	}

	public void click(int x, int y)
	{
		this.move(x, y);
		this.click();
	}

	public void clickAndDelay(int x, int y, int delay)
	{
		this.click(x, y);
		this.delay(delay);
	}

	public void rightClick(int x, int y)
	{
		this.move(x, y);
		this.rightClick();
	}

	public void rightClickAndDelay(int x, int y, int delay)
	{
		this.rightClick(x, y);
		this.delay(delay);
	}

	public Point getMouseCoordinates()
	{
		return MouseInfo.getPointerInfo().getLocation();
	}

	public int getMouseX()
	{
		return (int) Math.round(this.getMouseCoordinates().getX());
	}

	public int getMouseY()
	{
		return (int) Math.round(this.getMouseCoordinates().getY());
	}

	public BufferedImage getScreenShot()
	{
		return robot.createScreenCapture(new Rectangle(screenSize));
	}

	public int getScreenHeight()
	{
		return (int) screenSize.getHeight();
	}

	public int getScreenWidth()
	{
		return (int) screenSize.getWidth();
	}

	/**
	 * Grabs 2 screenshots and analyzes them for differences
	 * 
	 * @return value representing the 'chaos' of the screen or the percentage of
	 *         pixels that changed between the 2 screenshots
	 * 
	 */
	public double getScreenEntropy()
	{
		double numSamePixels = 0;
		double numTotalPixels = this.getScreenHeight() * this.getScreenWidth();
		BufferedImage imageCompare1 = this.getScreenShot();
		BufferedImage imageCompare2 = this.getScreenShot();
		//compare all pixels
		for (int y = 0; y < this.getScreenHeight(); y++)
		{
			for (int x = 0; x < this.getScreenWidth(); x++)
			{
				if (imageCompare1.getRGB(x, y) == imageCompare2.getRGB(x, y))
					numSamePixels++;
			}
		}
		double percentSimilar = numSamePixels / numTotalPixels;
		return 1 - percentSimilar;
	}

	public Dimension getScreenDimensions()
	{
		return this.screenSize;
	}
}
