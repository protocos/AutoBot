package me.protocos.bot.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Pixel implements Comparable<Pixel>, IPixel
{
	public static final Pixel DOES_NOT_EXIST = new Pixel(-1, -1, -1);
	@Column(name = "x")
	private int x;
	@Column(name = "y")
	private int y;
	@Column(name = "rgb")
	private int RGB;

	Pixel()
	{
	}

	public Pixel(int x, int y, int RGB)
	{
		this.x = x;
		this.y = y;
		this.RGB = RGB;
	}

	public Pixel(int x, int y, Color color)
	{
		this.x = x;
		this.y = y;
		this.RGB = color.getRGB();
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public int getRGB()
	{
		return RGB;
	}

	@Override
	public void setRGB(int rGB)
	{
		RGB = rGB;
	}

	@Transient
	@Override
	public Color getColor()
	{
		return new Color(RGB);
	}

	public int getRed()
	{
		return this.getColor().getRed();
	}

	public int getGreen()
	{
		return this.getColor().getGreen();
	}

	public int getBlue()
	{
		return this.getColor().getBlue();
	}

	public boolean exists(BufferedImage image)
	{
		return image.getRGB(this.getX(), this.getY()) == this.getRGB();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Pixel))
			return false;

		Pixel rhs = (Pixel) obj;
		return this.getX() == rhs.getX() && this.getY() == rhs.getY() && this.getRGB() == rhs.getRGB();
	}

	@Override
	public int compareTo(Pixel other)
	{
		if (this.getY() < other.getY())
			return -1;
		else if (this.getY() == other.getY())
		{
			if (this.getX() < other.getX())
				return -1;
			else if (this.getX() == other.getX())
			{
				if (this.getRGB() < other.getRGB())
					return -1;
				else if (this.getRGB() == other.getRGB())
				{
					return 0;
				}
			}
		}
		return 1;
	}

	@Override
	public String toString()
	{
		return "(" + this.getX() + "," + this.getY() + ",RGB:" + this.getRGB() + ")";
	}
}
