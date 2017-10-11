package me.protocos.bot.model;

import java.awt.Color;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "satellite_pixel")
public class SatellitePixel implements IPixel
{
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name = "pixel_constellation_id")
	private PixelConstellation pixelConstellation;
	@Embedded
	private Pixel satellitePixel;

	SatellitePixel()
	{
	}

	public SatellitePixel(PixelConstellation pixelConstellation, Pixel satellitePixel)
	{
		this.pixelConstellation = pixelConstellation;
		this.satellitePixel = satellitePixel;
	}

	@Override
	public void setX(int x)
	{
		this.satellitePixel.setX(x);
	}

	@Override
	public int getX()
	{
		return satellitePixel.getX() - getCenterPixel().getX();
	}

	@Override
	public void setY(int y)
	{
		this.satellitePixel.setY(y);
	}

	@Override
	public int getY()
	{
		return satellitePixel.getY() - getCenterPixel().getY();
	}

	@Override
	public int getRGB()
	{
		return satellitePixel.getRGB();
	}

	@Override
	public void setRGB(int rgb)
	{
		this.satellitePixel.setRGB(rgb);
	}

	@Override
	public Color getColor()
	{
		return satellitePixel.getColor();
	}

	private Pixel getCenterPixel()
	{
		return this.pixelConstellation.getCenterPixel();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof SatellitePixel))
			return false;

		SatellitePixel rhs = (SatellitePixel) obj;
		return new EqualsBuilder()
				.append(this.satellitePixel, rhs.satellitePixel)
				.isEquals();
	}

	@Override
	public String toString()
	{
		return satellitePixel.toString();
	}
}
