package me.protocos.bot;

import me.protocos.bot.controller.AutoBotController;
import me.protocos.bot.data.HibernateSessionFactory;
import me.protocos.bot.data.PixelConstellationDao;
import me.protocos.bot.view.PatternBotView;

public class App
{
	public static void main(String[] args)
	{
		Modbot bot = Modbot.getInstance();
		PatternBotView patternBotView = new PatternBotView(bot.getScreenDimensions());
		PixelConstellationDao constellationDao = new PixelConstellationDao(HibernateSessionFactory.getInstance());
		AutoBotController controller = new AutoBotController(patternBotView, constellationDao);
		controller.start();
	}
}
