package me.protocos.bot.action;

import java.awt.Point;
import me.protocos.bot.Modbot;
import me.protocos.bot.model.ImageScannerUtil;
import me.protocos.bot.model.PixelConstellation;

public class MoveAction implements IAction
{
	private Modbot bot;

	public MoveAction()
	{
		this.bot = Modbot.getInstance();
	}

	@Override
	public void act(PixelConstellation pixelConstellation)
	{
		Point point = ImageScannerUtil.firstMatch(bot.getScreenShot(), pixelConstellation);
		bot.move(point);
	}
}
