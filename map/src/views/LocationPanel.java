package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.EntityController;
import controllers.LocationController;

import views.listeners.LocationPanelMouseListener;
import views.listeners.EditLocationButtonListener;

public class LocationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Location _loc;
	private JPanel _entitiesPanel;
	private MapPanel _mapPanel;
	private boolean _displayed;
	private int _offsetX, _offsetY;
	private int _x, _y;
	private JLabel _iconGearLabel;
	private JLabel _locationName;
	private ImageIcon _iconGearOn,_iconGearOff;
	private LocationPanelMouseListener _mouseListener;
	private EditLocationButtonListener _iconGearMouseListener;
	private List<AffectedEntityPanel> _affectedEntityPanels;

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
		GridLayout gl = new GridLayout();
		gl.setColumns(2);
		gl.setRows(3);
		gl.setHgap(5);
		gl.setVgap(5);
		_entitiesPanel.setLayout(gl);
		JScrollPane scrollPane = new JScrollPane(_entitiesPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel southPanel = new JPanel();
		_iconGearLabel = new JLabel();
		_iconGearOn = new ImageIcon(EntityPanel.class.getResource("/ui/gear.png"));
		_iconGearOff = new ImageIcon(EntityPanel.class.getResource("/ui/gearLight.png"));

		southPanel.setLayout(new BorderLayout());
		southPanel.setOpaque(false);
		_entitiesPanel.setOpaque(false);
		_iconGearLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
		_iconGearLabel.setIcon(_iconGearOff);
		_locationName = new JLabel(_loc.getLocName(),SwingConstants.CENTER);
		_locationName.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		southPanel.add(_locationName, BorderLayout.CENTER);
		southPanel.add(_iconGearLabel, BorderLayout.EAST);
		add(scrollPane,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);

		_affectedEntityPanels = new ArrayList<AffectedEntityPanel>();
	}

	public void moveLocationPanel(int x, int y) {
		_offsetX = _offsetX + x;
		_offsetY = _offsetY + y;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBounds(_offsetX + _x - 100, _offsetY + _y - 50, 200,100);
		setForeground(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void update() {
		List<EntityController> listEntities = _loc.getLocationController().getEntityList();
		List<AffectedEntityPanel> listAffectedEntityPanelsToDelete = new ArrayList<AffectedEntityPanel>();
		
		for(AffectedEntityPanel oneEntity : _affectedEntityPanels) {
			if(!listEntities.contains(oneEntity.getEntityController())) {
				_entitiesPanel.remove(oneEntity);
				listAffectedEntityPanelsToDelete.add(oneEntity);
			} else {
				listEntities.remove(oneEntity.getEntityController());
			}
		}
		_affectedEntityPanels.removeAll(listAffectedEntityPanelsToDelete);
		
		AffectedEntityPanel affectedEntity;
		for(EntityController oneEntity : listEntities)
		{
			affectedEntity = new AffectedEntityPanel(_mapPanel, oneEntity);
			_entitiesPanel.add(affectedEntity);
			_affectedEntityPanels.add(affectedEntity);
		}
		_locationName.setText(_loc.getLocName());
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
