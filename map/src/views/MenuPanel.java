package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import views.listeners.MenuMouseListener;
import controllers.OperationController;


public class MenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int LEFT_PANEL_WIDTH = 200;

	private GlobalPanel _globalPanel;
	private MapPanel _mapPanel;
	private JLabel[] _labels = new JLabel[10];
	private JPanel _buttonsPanel;
	private JPanel _entitiesPanel;
	private MenuMouseListener _menuListener;

	private Color left = Color.black;
	private Color right = Color.gray;

	private GradientPaint _gradient = new GradientPaint(0,0, left,0,0,right,false);

	public MenuPanel(GlobalPanel globalPanel, MapPanel mapPanel)
	{
		super(true);
		_globalPanel = globalPanel;
		_mapPanel = mapPanel;
		setMinimumSize(new Dimension(LEFT_PANEL_WIDTH, getHeight()));
		setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, getHeight()));
		setLayout(new BorderLayout());
	}

	public void setOperation(OperationController operation) {
		_entitiesPanel = new MenuEntitiesPanel(operation,_globalPanel);
		((MenuEntitiesPanel) _entitiesPanel).addMenuEntitiesPanelListener();
		JScrollPane scrollPane = new JScrollPane(_entitiesPanel);
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		_buttonsPanel = new MenuButtonsPanel(_mapPanel);
		add(_buttonsPanel, BorderLayout.SOUTH);
	}

	public MenuEntitiesPanel getEntitiesPanel() {return (MenuEntitiesPanel)_entitiesPanel;}
	public MenuButtonsPanel getButtonsPanel() {return (MenuButtonsPanel)_buttonsPanel;}

	public void addMenuPanelListener() {
		_menuListener = new MenuMouseListener(_mapPanel);
		addMouseListener(_menuListener);
	}

	public void update() {
		if(_entitiesPanel != null) {
			((MenuEntitiesPanel)_entitiesPanel).update();
		}
	}

	@Override 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(_gradient);
		g2d.fillRoundRect(0, 0, 200, getHeight(), 0, 0);
		g2d.fillRect(0, 0, 180, getHeight());
	}
}
