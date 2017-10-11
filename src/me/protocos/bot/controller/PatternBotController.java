package me.protocos.bot.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import me.protocos.bot.Modbot;
import me.protocos.bot.data.PixelConstellationDao;
import me.protocos.bot.model.Pixel;
import me.protocos.bot.model.PixelConstellation;
import me.protocos.bot.model.PixelContainer;
import me.protocos.bot.model.Script;
import me.protocos.bot.view.PatternBotView;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

public class PatternBotController implements NativeMouseInputListener, NativeMouseMotionListener, NativeMouseWheelListener, NativeKeyListener
{
	private boolean resetSaveKeyCode = false;
	private int savePixelKeyCode = 29;
	private Modbot bot;
	private PatternBotView patternBotView;
	private PixelConstellationDao constellationDao;
	private PixelContainer currentPixelContainer;

	public PatternBotController(PatternBotView patternBotView, PixelConstellationDao constellationDao)
	{
		this.bot = Modbot.getInstance();
		this.patternBotView = patternBotView;
		this.constellationDao = constellationDao;
		this.currentPixelContainer = new PixelContainer();
	}

	private void refreshView()
	{
		List<String> constellationNames = new ArrayList<>();
		for (PixelConstellation pixelConstellation : constellationDao.retrieveAll())
		{
			constellationNames.add(pixelConstellation.getName());
		}
		this.patternBotView.setRecordedPatternListText(constellationNames);
		this.patternBotView.setPixelListText(this.currentPixelContainer.toString());
	}

	public void start()
	{
		registerAppListeners();
		registerNativeListeners();
		refreshView();
		patternBotView.launch();
	}

	private void registerAppListeners()
	{
		this.patternBotView.addSetKeyCodeButtonListener(new SetKeyCodeButtonListener());
		this.patternBotView.addRecordedPatternAddButtonListener(new RecordedPatternAddButtonListener());
		this.patternBotView.addPixelListClearButtonListener(new PixelListClearButtonListener());
		this.patternBotView.addRecordedPatternKeyListener(new RecordedPatternKeyboardListener());
		this.patternBotView.addRunButtonListener(new RunButtonListener());
	}

	public void registerNativeListeners()
	{
		try
		{
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex)
		{
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);
		GlobalScreen.addNativeKeyListener(this);
	}

	class SetKeyCodeButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			resetSaveKeyCode = true;
		}
	}

	class RecordedPatternAddButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String name = patternBotView.getNameField();
			if (currentPixelContainer.isEmpty())
			{
			}
			else if (!name.equals(""))
			{
				constellationDao.add(new PixelConstellation(name, currentPixelContainer));
				currentPixelContainer = new PixelContainer();
				patternBotView.clearNameField();
				refreshView();
			}
			else
			{
				patternBotView.setNameFieldRedBorder();
			}
		}
	}

	class PixelListClearButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			currentPixelContainer = new PixelContainer();
			refreshView();
		}
	}

	class RecordedPatternKeyboardListener implements KeyListener
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			System.out.println(e.getKeyCode());
			// IF backspace key is pressed
			// OR delete key is pressed
			if (e.getKeyCode() == 8
					|| e.getKeyCode() == 127)
			{
				List<String> names = patternBotView.getRecordedPatternSelectedList();
				for (String name : names)
				{
					constellationDao.remove(name);
				}
				refreshView();
			}
		}
	}

	class RunButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Script script = new Script(patternBotView.getScript(), constellationDao.retrieveAll());
			script.run();
		}
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent event)
	{
		if (this.patternBotView.isOutsideWindow(event.getX(), event.getY()))
		{
			this.currentPixelContainer.add(new Pixel(event.getX(), event.getY(), bot.getPixelRGB(event.getX(), event.getY())));
			refreshView();
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent event)
	{
		// TODO Auto-generated method stub
		if (this.patternBotView.isOutsideWindow(event.getX(), event.getY()))
		{
			Pixel pixel = new Pixel(event.getX(), event.getY(), bot.getPixelColor(event.getX(), event.getY()));
			this.patternBotView.refreshPreview(pixel);
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent event)
	{
		if (resetSaveKeyCode)
		{
			savePixelKeyCode = event.getKeyCode();
			resetSaveKeyCode = false;
		}
		else if (this.patternBotView.isOutsideWindow(bot.getMouseX(), bot.getMouseY()))
		{
			// IF a right or a left modifier key is pressed
			// AND the mask indicates that only CTRL is pressed
			// THEN save the pixel in the list
			if (event.getKeyCode() == savePixelKeyCode)
			{
				this.currentPixelContainer.add(new Pixel(bot.getMouseX(), bot.getMouseY(), bot.getPixelRGB(bot.getMouseX(), bot.getMouseY())));
				refreshView();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}