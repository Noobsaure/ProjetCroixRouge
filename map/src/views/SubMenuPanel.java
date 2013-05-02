package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import views.buttons.CustomButton;
import controllers.MapController;
import controllers.VictimController;

public class SubMenuPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 250;
	public static final Color COLOR_BACKGROUND = Color.DARK_GRAY;
	public static final int BUTTON_HEIGHT = 25;
	
	private MapPanel _mapPanel;
	private JPanel _thumbnailsPanel;
	private CustomButton _addButton;
	private CustomButton _okButton;
	private Map<JToggleButton, MapController> _mapMaps;
	private Map<JToggleButton, VictimController> _mapVictims;
	
	public SubMenuPanel(MapPanel mapPanel)
	{
		_mapPanel = mapPanel;
		_thumbnailsPanel = new JPanel();
		_mapMaps = new HashMap<JToggleButton, MapController>();
		_mapVictims = new HashMap<JToggleButton, VictimController>();
		
		setSize(new Dimension(WIDTH, mapPanel.getHeight()));
		setLocation(0, 0);
		setBackground(COLOR_BACKGROUND);
		setLayout(new BorderLayout());

		JPanel panelAddButton = new JPanel();
		panelAddButton.setLayout(new BorderLayout());
		panelAddButton.setBackground(COLOR_BACKGROUND);
		panelAddButton.setBorder(new EmptyBorder(2, 30, 2, 30));
		_addButton = new CustomButton("+");
		panelAddButton.add(_addButton);
		add(panelAddButton, BorderLayout.NORTH);

		JPanel _panelOkButton = new JPanel();
		_panelOkButton.setBackground(COLOR_BACKGROUND);
		_panelOkButton.setBorder(new EmptyBorder(2, 30, 2, 30));
		_okButton = new CustomButton("  Ok  ");
		_panelOkButton.add(_okButton);
		add(_panelOkButton, BorderLayout.SOUTH);
	}
	
	
	public void displayThumbnail()
	{
		
	}
	

	public void addAddButtonListener(ActionListener listener)
	{
		_addButton.addActionListener(listener);
	}
	public void addOkButtonListener(ActionListener listener)
	{
		_okButton.addActionListener(listener);
	}
	
	
	
	public JPanel getThumbnailPanel()
	{
		return _thumbnailsPanel;
	}
	
	public Map<JToggleButton, MapController> getMapMap()
	{
		return _mapMaps;
	}
	
	public Map<JToggleButton, VictimController> getMapVictim()
	{
		return _mapVictims;
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		setSize(WIDTH, _mapPanel.getHeight());

		revalidate();
		repaint();
	}
}