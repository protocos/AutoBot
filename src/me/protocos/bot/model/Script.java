package me.protocos.bot.model;

import java.util.*;
import me.protocos.bot.action.ClickAction;
import me.protocos.bot.action.IAction;
import me.protocos.bot.action.MoveAction;
import me.protocos.bot.exception.InvalidCommandException;
import me.protocos.bot.exception.InvalidPatternException;

public class Script implements Runnable
{
	private static Map<String, IAction> actions;
	static
	{
		actions = new HashMap<>();
		actions.put("move", new MoveAction());
		actions.put("click", new ClickAction());
	}

	private String script;
	private Map<String, PixelConstellation> pixelConstellations;

	public Script(String script, List<PixelConstellation> pixelConstellationlist)
	{
		this.script = script;
		pixelConstellations = new HashMap<>();
		for (PixelConstellation constellation : pixelConstellationlist)
		{
			pixelConstellations.put(constellation.getName(), constellation);
		}
		this.checkSyntax();
	}

	private void checkSyntax()
	{
		List<String> lines = new LinkedList<>(Arrays.asList(script.split("\n")));
		int lineNumber = 0;
		for (String line : lines)
		{
			lineNumber++;
			String command = line.split(" ")[0];
			if (!actions.containsKey(command))
				throw new InvalidCommandException(command, lineNumber);
		}
	}

	@Override
	public void run()
	{
		List<String> lines = new LinkedList<>(Arrays.asList(script.split("\n")));
		int lineNumber = 0;
		for (String line : lines)
		{
			lineNumber++;
			String[] split = line.split(" ");
			String command = split[0];
			String pattern = split[1];
			if (!pixelConstellations.containsKey(pattern))
				throw new InvalidPatternException(command, lineNumber);
			IAction action = actions.get(command);
			action.act(pixelConstellations.get(pattern));
		}
	}
}
