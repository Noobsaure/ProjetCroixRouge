package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import views.listeners.LocationPanelMouseListener;
import controllers.EntityController;

public class LocationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private static final int WIDTH_PANEL = 200;
	private static final int HEIGHT_PANEL = 100;
	private static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);

	private Location _loc;
	private JPanel _entitiesPanel;
	private MapPanel _mapPanel;
	private boolean _displayed;
	private int _mapX, _mapY;
	private int _x, _y;
	private JLabel _iconDeleteLabel,_iconGearLabel;
	private JLabel _locationName;
	private ImageIcon _iconGearOn,_iconGearOff,_iconDelete;
	private LocationPanelMouseListener _mouseListener;
	private List<AffectedEntityPanel> _affectedEntityPanels;

	public LocationPanel(Location loc, MapPanel mapPanel, int x, int y) {
		_loc = loc;
		_mapPanel = mapPanel;
		_x = x;
		_y = y;
		setBackground(Color.LIGHT_GRAY);
		_mouseListener = new LocationPanelMouseListener(_loc);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		
		initGui();
	}
	
	public void initGui() {
		setPreferredSize(DIMENSION_PANEL);
		setBounds(_x - (WIDTH_PANEL / 2), _y - (HEIGHT_PANEL / 2), WIDTH_PANEL, HEIGHT_PANEL);
		setLayout(new BorderLayout());
		
		_entitiesPanel = new JPanel();
		_entitiesPanel.setLayout(new FlowLayout());
		_entitiesPanel.setBackground(Color.WHITE);
		JScrollPane _scrollPane = new JScrollPane(_entitiesPanel);
		_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setBackground(Color.LIGHT_GRAY);
		
		_iconGearOn = new ImageIcon(EntityPanel.class.getResource("/ui/gear.png"));
		_iconGearOff = new ImageIcon(EntityPanel.class.getResource("/ui/gearLight.png"));
		
		_iconGearLabel = new JLabel();
		_iconGearLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
		_iconGearLabel.setIcon(_iconGearOff);
		
		ImageIcon iconDelete = new ImageIcon(EntityPanel.class.getResource("/ui/delete.png"));
		Image imageDeleteScaled = iconDelete.getImage().getScaledInstance(12, 16, Image.SCALE_DEFAULT);
		_iconDelete = new ImageIcon(imageDeleteScaled);
		
		_iconDeleteLabel = new JLabel();
		_iconDeleteLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
		_iconDeleteLabel.setIcon(_iconDelete);
		
		_locationName = new JLabel(_loc.getLocName(),SwingConstants.CENTER);
		_locationName.setFont(new Font(Font.SANS_SERIF,Font.BOLD,14));
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setOpaque(false);
		eastPanel.add(_iconGearLabel, BorderLayout.CENTER);
		eastPanel.add(_iconDeleteLabel, BorderLayout.EAST);
		Border empty = BorderFactory.createEmptyBorder(2,2,2,2);
		_locationName.setBorder(empty);
		southPanel.add(_locationName, BorderLayout.CENTER);
		southPanel.add(eastPanel, BorderLayout.EAST);
		
		add(_scrollPane,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);
		
		_affectedEntityPanels = new ArrayList<AffectedEntityPanel>();

		setVisible(false);
	}

	public void setMapXY(int x, int y, double ratio) {
		_mapX = x;
		_mapY = y;
		setBounds(_mapX + (int)(_x * ratio) - WIDTH_PANEL / 2, _mapY + (int)(_y * ratio) - HEIGHT_PANEL / 2, WIDTH_PANEL, HEIGHT_PANEL);
	}

	public void update() {
		List<EntityController> listEntities = _loc.getLocationController().getEntityList();
		List<AffectedEntityPanel> listAffectedEntityPanelsToDelete = new ArrayList<AffectedEntityPanel>();
		
		for(AffectedEntityPanel oneEntity : _affectedEntityPanels) {
			if(!listEntities.contains(oneEntity.getEntityController()))	{
				_entitiesPanel.remove(oneEntity);
				listAffectedEntityPanelsToDelete.add(oneEntity);
			}
			else
				listEntities.remove(oneEntity.getEntityController());
		}
		
		_affectedEntityPanels.removeAll(listAffectedEntityPanelsToDelete);
		
		AffectedEntityPanel affectedEntity;
		for(EntityController oneEntity : listEntities) {
			affectedEntity = new AffectedEntityPanel(_mapPanel, oneEntity);
			_entitiesPanel.add(affectedEntity);
			_affectedEntityPanels.add(affectedEntity);
		}
		
		int hgap = (_entitiesPanel.getWidth() - 2 * AffectedEntityPanel.WIDTH) / 3;
		int vgap = (_entitiesPanel.getHeight() - 2 * AffectedEntityPanel.HEIGHT) / 3;
		int x = hgap, y = vgap;
		
		for(AffectedEntityPanel oneEntity : _affectedEntityPanels) {
			oneEntity.setBounds(x, y, AffectedEntityPanel.WIDTH, AffectedEntityPanel.HEIGHT);
			oneEntity.update();
		}
		
		int preferredHeight = (AffectedEntityPanel.HEIGHT + 6) * (((_affectedEntityPanels.size() + 1) / (getWidth() / AffectedEntityPanel.WIDTH))) - 5;
		_entitiesPanel.setPreferredSize(new Dimension(getWidth(), preferredHeight));
		
		_locationName.setText(_loc.getLocName());
	}

	public boolean is_displayed() {return _displayed;}
	public void set_displayed(boolean _displayed) {this._displayed = _displayed;}
	public JPanel getEntitiesPanel() {return _entitiesPanel;}
	public Location getLoc() {return _loc;}
	public void setIconState(boolean on) {
		if(on)
			_iconGearLabel.setIcon(_iconGearOn);
		else
			_iconGearLabel.setIcon(_iconGearOff);
	}
	public void addIconMouseListeners(MouseListener editListener, MouseListener deleteListener) {
		_iconGearLabel.addMouseListener(editListener);
		_iconDeleteLabel.addMouseListener(deleteListener);
	}
}
