package me.protocos.bot.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "pixel_constellation")
public class PixelConstellation
{
	@Id
	@Column(name = "name")
	private String name;
	@Embedded
	@Column(name = "centroid_pixel")
	private Pixel centroidPixel;
	@OneToMany(cascade = CascadeType.ALL)
	private List<SatellitePixel> satellitePixels;
	@Column(name = "left_bound")
	private int leftBound;
	@Column(name = "right_bound")
	private int rightBound;
	@Column(name = "top_bound")
	private int topBound;
	@Column(name = "bottom_bound")
	private int bottomBound;

	PixelConstellation()
	{
	}

	public PixelConstellation(String name, PixelContainer pixelContainer)
	{
		this.name = name;
		if (pixelContainer.isEmpty())
			throw new AssertionError("Pixel container must not be empty");
		centroidPixel = pixelContainer.getFirstPixel();
		satellitePixels = new LinkedList<SatellitePixel>();
		for (Pixel pixel : pixelContainer)
		{
			if (!pixel.equals(centroidPixel))
			{
				SatellitePixel satellitePixel = new SatellitePixel(this, pixel);
				this.leftBound = Math.min(this.leftBound, satellitePixel.getX());
				this.rightBound = Math.max(this.rightBound, satellitePixel.getX());
				this.topBound = Math.min(this.topBound, satellitePixel.getY());
				this.bottomBound = Math.max(this.bottomBound, satellitePixel.getY());
				satellitePixels.add(satellitePixel);
			}
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Pixel getCenterPixel()
	{
		return centroidPixel;
	}

	public int getCenterRGB()
	{
		return centroidPixel.getRGB();
	}

	public int getLeftConstellationBound()
	{
		return Math.abs(leftBound);
	}

	public int getRightConstellationBound()
	{
		return Math.abs(rightBound);
	}

	public int getTopConstellationBound()
	{
		return Math.abs(topBound);
	}

	public int getBottomConstellationBound()
	{
		return Math.abs(bottomBound);
	}

	public int getBoundingWidth()
	{
		return getLeftConstellationBound() + getRightConstellationBound();
	}

	public int getBoundingHeight()
	{
		return getTopConstellationBound() + getBottomConstellationBound();
	}

	public List<SatellitePixel> getSatellitePixels()
	{
		return new LinkedList<SatellitePixel>(satellitePixels);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof PixelConstellation))
			return false;

		PixelConstellation rhs = (PixelConstellation) obj;
		return new EqualsBuilder()
				.append(this.centroidPixel, rhs.centroidPixel)
				.append(this.satellitePixels, rhs.satellitePixels)
				.isEquals();
	}

	@Override
	public String toString()
	{
		StringBuffer output = new StringBuffer();
		output.append("name:");
		output.append(name);
		output.append("\n");
		output.append("center:");
		output.append(centroidPixel.toString());
		for (SatellitePixel satellitePixel : satellitePixels)
		{
			output.append("\n");
			output.append("satellite:");
			output.append(satellitePixel.toString());
		}
		return output.toString();
	}
}
