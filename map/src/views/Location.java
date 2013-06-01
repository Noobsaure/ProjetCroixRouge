package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.dnd.DropTarget;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import views.listeners.LocationDropTargetListener;
import views.listeners.LocationMouseListener;
import controllers.LocationController;

public class Location extends JPanel {

	private static final long serialVersionUID = 1L;

	private int _x,_y,_mapX,_mapY;
	private boolean _dragOver = false;
	private String _name;
	private boolean _highlighted = false;
	public static final ImageIcon[] _iconLoc = new ImageIcon[] {
		new ImageIcon(EntityPanel.class.getResource("/ui/rouge.png")),
		new ImageIcon(EntityPanel.class.getResource("/ui/orange.png")),
		new ImageIcon(EntityPanel.class.getResource("/ui/jaune.png")),
		new ImageIcon(EntityPanel.class.getResource("/ui/lightGreen.png")),
		new ImageIcon(EntityPanel.class.getResource("/ui/vert.png"))};
	public static final ImageIcon _iconLocDragOver = new ImageIcon(EntityPanel.class.getResource("/ui/violet.png"));
	public static final int _iconLocWidth = _iconLocDragOver.getIconWidth();
	public static final int _iconLocHeight = _iconLocDragOver.getIconHeight();
	private LocationPanel _locPanel;
	private LocationController _locationController;
	private LocationMouseListener _mouseListener;
	private JLabel _entitiesCount;
	private int _locColor;
	
	public Location(GlobalPanel globalPanel, LocationController locationController, int x, int y) {
		super();
		setLayout(new BorderLayout());
		_locationController = locationController;
		_name = _locationController.getName();
		_locColor = Math.max(0,_locationController.getColor() - 1);
		_mapX = globalPanel.getMapPanel().get_x();
		_mapY = globalPanel.getMapPanel().get_y();
		_x = x;
		_y = y;
		_entitiesCount = new JLabel(_locationController.getEntityList().size()+"",SwingConstants.CENTER);
		_entitiesCount.setForeground(Color.WHITE);
		_entitiesCount.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		add(_entitiesCount, BorderLayout.CENTER);
		setBounds(_x + _mapX - _iconLocWidth / 2, _y + _mapY - _iconLocHeight / 2, _iconLocWidth, _iconLocHeight);
		setOpaque(false);
		_mouseListener = new LocationMouseListener(this, globalPanel);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		setDropTarget(new DropTarget(this, new LocationDropTargetListener(this, globalPanel)));		
	}
	
	public void setMapXY(int x, int y, double ratio) {
		_mapX = x;
		_mapY = y;
		setBounds(_mapX + (int)(_x * ratio) - _iconLocWidth / 2, _mapY + (int)(_y * ratio) - _iconLocHeight / 2, _iconLocWidth, _iconLocHeight);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		if(_dragOver) {
			g2d.drawImage(_iconLocDragOver.getImage(), 0, 0, _iconLocWidth, _iconLocHeight, null, null);
		} else {
			g2d.drawImage(_iconLoc[_locColor].getImage(), 0, 0, _iconLocWidth, _iconLocHeight, null, null);
		}
	}

	public void displayPanel(boolean display) {
		if(display == _highlighted || _locPanel.is_displayed()) {
			_locPanel.setVisible(display);
			_locPanel.set_displayed(display);
		}
		repaint();
	}
	
	public void update() {
		_name = _locationController.getName();
		_locPanel.update();
		_entitiesCount.setText(_locationController.getEntityList().size()+"");
		repaint();
	}

	public LocationPanel getLocPanel() {return _locPanel;}
	public String getLocName() {return _name;}
	public void setLocPanel(LocationPanel locPanel) {_locPanel = locPanel;}
	public int get_x() {return _x;}
	public void set_x(int x) {_x = x;}
	public int get_y() {return _y;}
	public void set_y(int y) {_y = y;}
	public void setDragOver(boolean dragOver) {_dragOver = dragOver;}
	public boolean isHighlighted() {return _highlighted;}
	public void setHighlight(boolean highlighted) {_highlighted = highlighted;}
	public boolean isLocationPanelDisplayed() {return _locPanel.is_displayed();}
	public int getRadius() {return _iconLocWidth / 2;}
	public LocationController getLocationController() {return _locationController;}
}
