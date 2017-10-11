package me.protocos.bot.model;

import java.awt.Graphics;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ColoredRectangleComponent extends JComponent
{
	private Pixel pixel;

	public ColoredRectangleComponent(Pixel pixel)
	{
		this.pixel = pixel;
	}

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		graphics.setColor(pixel.getColor());
		graphics.fillRect(0, 0, 100, 100);
	}
}
