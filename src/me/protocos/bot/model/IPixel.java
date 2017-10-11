package me.protocos.bot.model;

import java.awt.Color;

public interface IPixel
{
	public abstract void setX(int x);

	public abstract int getX();

	public abstract void setY(int y);

	public abstract int getY();

	public abstract void setRGB(int rgb);

	public abstract int getRGB();

	public abstract Color getColor();
}
