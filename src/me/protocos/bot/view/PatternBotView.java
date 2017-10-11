package me.protocos.bot.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.*;
import me.protocos.bot.model.ColoredRectangleComponent;
import me.protocos.bot.model.Pixel;

@SuppressWarnings("serial")
public class PatternBotView extends JFrame
{
	//-11893157 = Trello Green
	//-8483936 = Cobalt
	private static final Color BACKGROUND_COLOR = new Color(-8483936);

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private static final int HALF_WINDOW_WIDTH = WINDOW_WIDTH / 2;
	private static final int MENUBAR_MARGIN = 22;
	private static final int STANDARD_MARGIN = 10;
	private static final int HALF_COMPONENT_WIDTH = HALF_WINDOW_WIDTH / 2 - (2 * STANDARD_MARGIN);
	private static final int FULL_COMPONENT_WIDTH = HALF_WINDOW_WIDTH - (2 * STANDARD_MARGIN);
	private static final int BUTTON_HEIGHT = 30;
	private static final int LABEL_HEIGHT = 20;
	private static final int FIELD_HEIGHT = 30;
	private static final int SCROLL_PANE_HEIGHT = 130;
	private static final int PATTERN_PANEL_HEIGHT = 400;
	private static final int PATTERN_PANEL_Y_OFFSET = WINDOW_HEIGHT - MENUBAR_MARGIN - STANDARD_MARGIN - PATTERN_PANEL_HEIGHT;

	private static final int PREVIEW_PANEL_HEIGHT = 100;
	private static final int PREVIEW_PANEL_Y_OFFSET = PATTERN_PANEL_Y_OFFSET - STANDARD_MARGIN - PREVIEW_PANEL_HEIGHT;
	private static final int PREVIEW_SIZE = 120;

	private static final int HEADER_PANEL_HEIGHT = 40;
	private static final int HEADER_PANEL_Y_OFFSET = PREVIEW_PANEL_Y_OFFSET - STANDARD_MARGIN - HEADER_PANEL_HEIGHT;

	private static final int RIGHT_SIDE_X_OFFSET = HALF_WINDOW_WIDTH + STANDARD_MARGIN;
	private static final int RIGHT_SIDE_HEIGHT = WINDOW_HEIGHT - MENUBAR_MARGIN - STANDARD_MARGIN - STANDARD_MARGIN;

	private static final int SCRIPT_AREA_HEIGHT = RIGHT_SIDE_HEIGHT - BUTTON_HEIGHT - STANDARD_MARGIN;

	private Dimension screenDimensions;

	private JPanel headerPanel;
	private JPanel previewPanel;
	private JPanel patternPanel;
	private JPanel scriptPanel;

	private JLabel setKeyCodeTitle;
	private JButton setKeyCodeButton;

	private JTextField nameField;
	private JLabel nameFieldTitle;

	private JLabel recordedPatternTitle;
	private JButton recordedPatternAddButton;
	private JList<String> recordedPatternList;
	private JScrollPane recordedPatternScroll;

	private JLabel pixelListTitle;
	private JButton pixelListClearButton;
	private JTextArea pixelList;
	private JScrollPane pixelListScroll;

	private JButton runScriptButton;

	private JTextArea scriptTextArea;
	private JScrollPane scriptTextAreaScroll;

	public PatternBotView(Dimension screenDimensions)
	{
		this.screenDimensions = screenDimensions;
		this.headerPanel = new JPanel();
		this.previewPanel = new JPanel();
		this.patternPanel = new JPanel();
		this.scriptPanel = new JPanel();
		this.setKeyCodeTitle = new JLabel("Set Key Code");
		this.setKeyCodeButton = new JButton("Set");
		this.nameFieldTitle = new JLabel("Pattern Name");
		this.nameField = new JTextField();
		this.recordedPatternTitle = new JLabel("Recorded Patterns");
		this.recordedPatternAddButton = new JButton("Add");
		this.recordedPatternList = new JList<>();
		this.recordedPatternScroll = new JScrollPane(recordedPatternList);
		this.pixelListTitle = new JLabel("Pixel List");
		this.pixelListClearButton = new JButton("Clear");
		this.pixelList = new JTextArea();
		this.pixelList.setEditable(false);
		this.pixelListScroll = new JScrollPane(pixelList);
		this.runScriptButton = new JButton("Run");
		this.scriptTextArea = new JTextArea();
		this.scriptTextAreaScroll = new JScrollPane(scriptTextArea);

		redraw();
		refreshPreview(new Pixel(0, 0, new Color(0xFFFFFF)));
	}

	public void launch()
	{
		setTitle("PatternBot GUI");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		getContentPane().setBackground(BACKGROUND_COLOR);
		setLayout(null);
		setLocation(screenDimensions.width - WINDOW_WIDTH, 0);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void redraw()
	{
		//Remove all components before repainting
		getContentPane().removeAll();

		//Add set key code button
		addSetKeyCodeTitle();
		addSetKeyCodeButton();

		//Add name field for pattern
		addPatternNameField();

		//Add pattern list
		addPatternListTitle();
		addPatternListAddButton();
		addPatternList();

		//Add pixel list
		addPixelListTitle();
		addPixelListClearButton();
		addPixelList();

		//Add panels in order from bottom to top
		addInfoPanel();
		addPreviewPanel();
		addHeaderPanel();

		//Add script editing pane
		addScriptTextArea();

		//Add run button for script
		addRunScriptButton();

		//Add script panel
		addScriptPanel();

		this.repaint();
		this.revalidate();
	}

	public String getNameField()
	{
		return nameField.getText();
	}

	public void clearNameField()
	{
		nameField.setText("");
	}

	public void setNameFieldRedBorder()
	{
		this.nameField.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	public List<String> getRecordedPatternSelectedList()
	{
		return recordedPatternList.getSelectedValuesList();
	}

	public void setRecordedPatternListText(List<String> list)
	{
		recordedPatternList.setListData(list.toArray(new String[list.size()]));
		redraw();
	}

	public void setPixelListText(String text)
	{
		pixelList.setText(text);
		redraw();
	}

	public void addSetKeyCodeButtonListener(ActionListener addButtonListener)
	{
		setKeyCodeButton.addActionListener(addButtonListener);
	}

	public void addRecordedPatternAddButtonListener(ActionListener addButtonListener)
	{
		recordedPatternAddButton.addActionListener(addButtonListener);
	}

	public void addPixelListClearButtonListener(ActionListener clearButtonListener)
	{
		pixelListClearButton.addActionListener(clearButtonListener);
	}

	public void addRecordedPatternKeyListener(KeyListener keyListener)
	{
		recordedPatternList.addKeyListener(keyListener);
	}

	public void addRunButtonListener(ActionListener runButtonListener)
	{
		runScriptButton.addActionListener(runButtonListener);
	}

	public void refreshPreview(Pixel pixel)
	{
		setPreviewComponent(pixel);
		previewPanel.repaint();
		previewPanel.revalidate();
	}

	private void addInfoPanel()
	{
		patternPanel.setBounds(STANDARD_MARGIN, PATTERN_PANEL_Y_OFFSET, FULL_COMPONENT_WIDTH, PATTERN_PANEL_HEIGHT);
		patternPanel.setLayout(new FlowLayout());
		patternPanel.setBackground(BACKGROUND_COLOR);
		add(patternPanel);
	}

	private void addPreviewPanel()
	{
		previewPanel.setBounds(STANDARD_MARGIN, PREVIEW_PANEL_Y_OFFSET, FULL_COMPONENT_WIDTH, PREVIEW_PANEL_HEIGHT);
		previewPanel.setLayout(new FlowLayout());
		previewPanel.setBackground(BACKGROUND_COLOR);
		add(previewPanel);
	}

	private void addHeaderPanel()
	{
		headerPanel.setBounds(STANDARD_MARGIN, HEADER_PANEL_Y_OFFSET, FULL_COMPONENT_WIDTH, HEADER_PANEL_HEIGHT);
		headerPanel.setLayout(new FlowLayout());
		headerPanel.setBackground(BACKGROUND_COLOR);
		add(headerPanel);
	}

	private void addScriptPanel()
	{
		scriptPanel.setBounds(RIGHT_SIDE_X_OFFSET, STANDARD_MARGIN, FULL_COMPONENT_WIDTH, RIGHT_SIDE_HEIGHT);
		scriptPanel.setLayout(new FlowLayout());
		scriptPanel.setBackground(BACKGROUND_COLOR);
		add(scriptPanel);
	}

	private void setPreviewComponent(Pixel pixel)
	{
		previewPanel.removeAll();

		JComponent preview = new ColoredRectangleComponent(pixel);
		preview.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
		JTextArea pixelInfo = new JTextArea();
		pixelInfo.setText("X = " + pixel.getX() + "\n" +
				"Y = " + pixel.getY() + "\n" +
				"Red = " + pixel.getRed() + "\n" +
				"Green = " + pixel.getGreen() + "\n" +
				"Blue = " + pixel.getBlue() + "\n" +
				"RGB = " + pixel.getRGB());
		pixelInfo.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
		pixelInfo.setBackground(BACKGROUND_COLOR);

		previewPanel.add(preview);
		previewPanel.add(pixelInfo);
	}

	private void addSetKeyCodeTitle()
	{
		setHalfLabelSize(setKeyCodeTitle);
		headerPanel.add(setKeyCodeTitle);
	}

	private void addSetKeyCodeButton()
	{
		setButtonSize(setKeyCodeButton);
		headerPanel.add(setKeyCodeButton);
	}

	private void addPatternNameField()
	{
		setFullLabelSize(nameFieldTitle);
		setFieldSize(nameField);
		setBlackBorder(nameField);
		patternPanel.add(nameFieldTitle);
		patternPanel.add(nameField);
	}

	private void addPatternListTitle()
	{
		setHalfLabelSize(recordedPatternTitle);
		patternPanel.add(recordedPatternTitle);
	}

	private void addPatternListAddButton()
	{
		setButtonSize(recordedPatternAddButton);
		patternPanel.add(recordedPatternAddButton);
	}

	private void addPatternList()
	{
		setScrollPaneSizeAndScrollbar(recordedPatternScroll);
		setBlackBorder(recordedPatternScroll);
		patternPanel.add(recordedPatternScroll);
	}

	private void addPixelListTitle()
	{
		setHalfLabelSize(pixelListTitle);
		patternPanel.add(pixelListTitle);
	}

	private void addPixelListClearButton()
	{
		setButtonSize(pixelListClearButton);
		patternPanel.add(pixelListClearButton);
	}

	private void addPixelList()
	{
		setScrollPaneSizeAndScrollbar(pixelListScroll);
		setBlackBorder(pixelListScroll);
		patternPanel.add(pixelListScroll);
	}

	private void addRunScriptButton()
	{
		runScriptButton.setPreferredSize(new Dimension(FULL_COMPONENT_WIDTH, BUTTON_HEIGHT));
		scriptPanel.add(runScriptButton);
	}

	private void addScriptTextArea()
	{
		scriptTextAreaScroll.setPreferredSize(new Dimension(FULL_COMPONENT_WIDTH, SCRIPT_AREA_HEIGHT));
		scriptTextAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setBlackBorder(scriptTextAreaScroll);
		scriptPanel.add(scriptTextAreaScroll);
	}

	public String getScript()
	{
		return scriptTextArea.getText();
	}

	private void setBlackBorder(JComponent component)
	{
		component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	private void setButtonSize(JButton button)
	{
		button.setPreferredSize(new Dimension(HALF_COMPONENT_WIDTH, BUTTON_HEIGHT));
	}

	private void setHalfLabelSize(JLabel label)
	{
		label.setPreferredSize(new Dimension(HALF_COMPONENT_WIDTH, LABEL_HEIGHT));
	}

	private void setFullLabelSize(JLabel label)
	{
		label.setPreferredSize(new Dimension(FULL_COMPONENT_WIDTH, LABEL_HEIGHT));
	}

	private void setFieldSize(JTextField field)
	{
		field.setPreferredSize(new Dimension(FULL_COMPONENT_WIDTH, FIELD_HEIGHT));
	}

	private void setScrollPaneSizeAndScrollbar(JScrollPane scrollPane)
	{
		scrollPane.setPreferredSize(new Dimension(FULL_COMPONENT_WIDTH, SCROLL_PANE_HEIGHT));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}

	public boolean isOutsideWindow(int x, int y)
	{
		if (x >= this.getLocation().getX() &&
				x < this.getLocation().getX() + this.getWidth() &&
				y >= this.getLocation().getY() &&
				y < this.getLocation().getY() + this.getHeight())
			return false;
		return true;
	}
}