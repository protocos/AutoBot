package me.protocos.bot.model;

import java.awt.Point;
import java.util.Comparator;

public class PointComparator implements Comparator<Point>
{
	@Override
	public int compare(Point point1, Point point2)
	{
		Integer point1X = new Integer(point1.x);
		Integer point1Y = new Integer(point1.y);
		Integer point2X = new Integer(point2.x);
		Integer point2Y = new Integer(point2.y);

		if (point1Y == point2Y)
		{
			if (point1X == point2X)
				return 0;
			return point1X.compareTo(point2X);
		}
		return point1Y.compareTo(point2Y);
	}
}
