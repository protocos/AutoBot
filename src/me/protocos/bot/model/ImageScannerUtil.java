package me.protocos.bot.model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;

public class ImageScannerUtil
{
	public static Point firstMatch(BufferedImage image, PixelConstellation pixelConstellation)
	{
		return scan(image, pixelConstellation, true).iterator().next();
	}

	public static Set<Point> scan(BufferedImage image, PixelConstellation pixelConstellation)
	{
		return scan(image, pixelConstellation, false);
	}

	public static Set<Point> scan(BufferedImage image, PixelConstellation pixelConstellation, boolean breakOnFirstMatch)
	{
		boolean firstMatchFound = false;
		Set<Point> points = new TreeSet<Point>(new PointComparator());
		int topBound = pixelConstellation.getTopConstellationBound();
		int bottomBound = pixelConstellation.getBottomConstellationBound();
		int leftBound = pixelConstellation.getLeftConstellationBound();
		int rightBound = pixelConstellation.getRightConstellationBound();

		for (int y = topBound; y < image.getHeight() - bottomBound; y++)
		{
			for (int x = leftBound; x < image.getWidth() - rightBound; x++)
			{
				if (image.getRGB(x, y) == pixelConstellation.getCenterRGB())
				{
					boolean matchingPattern = true;
					for (SatellitePixel satellitePixel : pixelConstellation.getSatellitePixels())
					{
						int satelliteX = satellitePixel.getX();
						int satelliteY = satellitePixel.getY();
						if (image.getRGB(x + satelliteX, y + satelliteY) != satellitePixel.getRGB())
						{
							matchingPattern = false;
							break;
						}
					}
					if (matchingPattern)
					{
						points.add(new Point(x, y));
						firstMatchFound = true;
					}
				}
				if (breakOnFirstMatch && firstMatchFound)
					break;
			}
			if (breakOnFirstMatch && firstMatchFound)
				break;
		}
		return points;
	}
}
