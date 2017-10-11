package me.protocos.bot.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import me.protocos.bot.util.PixelPositionComparator;

public class PixelContainer implements Iterable<Pixel>
{
	private LinkedList<Pixel> pixels;

	public PixelContainer()
	{
		pixels = new LinkedList<Pixel>();
	}

	public Pixel getFirstPixel()
	{
		return pixels.get(0);
	}

	public void add(Pixel pixel)
	{
		if (pixel == null)
			return;
		PixelPositionComparator comparator = new PixelPositionComparator();
		boolean contains = false;
		for (Pixel p : pixels)
		{
			if (comparator.compare(p, pixel) == 0)
				contains = true;
		}
		if (!contains)
			pixels.add(pixel);
	}

	public boolean isEmpty()
	{
		return pixels.isEmpty();
	}

	public boolean contains(Pixel pixel)
	{
		return pixels.contains(pixel);
	}

	public int size()
	{
		return pixels.size();
	}

	public Collection<Pixel> getPixels()
	{
		return new LinkedList<Pixel>(pixels);
	}

	@Override
	public Iterator<Pixel> iterator()
	{
		return pixels.iterator();
	}

	@Override
	public String toString()
	{
		String output = "";
		for (Pixel pixel : pixels)
		{
			output += pixel.toString() + "\n";
		}
		return output.trim();
	}
}
