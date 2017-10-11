package me.protocos.bot.util;

import java.util.Comparator;
import me.protocos.bot.model.Pixel;

public class PixelPositionComparator implements Comparator<Pixel>
{
	@Override
	public int compare(Pixel o1, Pixel o2)
	{
		if (o1.getY() < o2.getY())
			return -1;
		else if (o1.getY() == o2.getY())
		{
			if (o1.getX() < o2.getX())
				return -1;
			else if (o1.getX() == o2.getX())
			{
				return 0;
			}
		}
		return 1;
	}
}
