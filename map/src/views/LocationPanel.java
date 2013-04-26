package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.EntityController;

import views.listeners.LocationPanelMouseListener;
import views.listeners.EditLocationButtonListener;

public class LocationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Location _loc;
	private JPanel _entitiesPanel;
	private MapPanel _mapPanel;
	private boolean _displayed;
	private int _x, _y;
	private JLabel _iconGearLabel;
	private ImageIcon _iconGearOn,_iconGearOff;
	private LocationPanelMouseListener _mouseListener;
	private EditLocationButtonListener _iconGearMouseListener;

	public LocationPanel(Location loc, MapPanel mapPanel, int x, int y) {
		super();
		_loc = loc;
		_mapPanel = mapPanel;
		_x = x;
		_y = y;
		setMinimumSize(new Dimension(200,100));
		setPreferredSize(new Dimension(200,100));
		setBounds(_x - 100, _y - 50, 200,100);
		setVisible(false);
		setOpaque(false);
		_mouseListener = new LocationPanelMouseListener(_loc);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		setLayout(new BorderLayout());

		_entitiesPanel = new JPanel();
		JPanel southPanel = new JPanel();
		_iconGearLabel = new JLabel();
		_iconGearOn = new ImageIcon(EntityPanel.class.getResource("/ui/gear.png"));
		_iconGearOff = new ImageIcon(EntityPanel.class.getResource("/ui/gearLight.png"));

		southPanel.setLayout(new BorderLayout());
		southPanel.setOpaque(false);
		_entitiesPanel.setOpaque(false);
		_iconGearLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
		_iconGearLabel.setIcon(_iconGearOff);
		southPanel.add(new JLabel(_loc.getLocName()), BorderLayout.CENTER);
		southPanel.add(_iconGearLabel, BorderLayout.EAST);
		add(_entitiesPanel,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBounds(_x - 100, _y - 50, 200,100);
		setForeground(Color.LIGHT_GRAY);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
	}
	
	public void update() {
		List<EntityController> entityList = _loc.getLocationController().getEntityList();
		AffectedEntityPanel affectedEntity;
		for(EntityController oneEntity : entityList) {
			affectedEntity = new AffectedEntityPanel(_mapPanel, oneEntity);
			_entitiesPanel.add(affectedEntity);
		}
	}

	public boolean is_displayed() {return _displayed;}
	public void set_displayed(boolean _displayed) {this._displayed = _displayed;}
	public JPanel getEntitiesPanel() {return _entitiesPanel;}
	public Location getLoc() {return _loc;}
	public void setIconState(boolean on) {
		if(on) {
			_iconGearLabel.setIcon(_iconGearOn);
		} else {
			_iconGearLabel.setIcon(_iconGearOff);
		}
	}
	public void addIconMouseListener(MouseListener listener) {_iconGearLabel.addMouseListener(listener);}
	
	public void enableListeners(boolean enable) {
		_mouseListener.enable(enable);
		_iconGearMouseListener.enable(enable);
	}
}
