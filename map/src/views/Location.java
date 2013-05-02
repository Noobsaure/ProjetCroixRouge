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

	private int _x,_y;
	private int _offsetX = 0;
	private int _offsetY = 0;
	private boolean _dragOver = false;
	private String _name;
	private boolean _highlighted = false;
	public static final ImageIcon _iconLoc = new ImageIcon(EntityPanel.class.getResource("/ui/rouge.png"));
	public static final ImageIcon _iconLocDragOver = new ImageIcon(EntityPanel.class.getResource("/ui/vert.png"));
	private LocationPanel _locPanel;
	private LocationController _locationController;
	private LocationMouseListener _mouseListener;
	private JLabel _entitiesCount;
	
	public Location(GlobalPanel gPanel, LocationController locationController) {
		super();
		setLayout(new BorderLayout());
		_locationController = locationController;
		_name = _locationController.getName();
		_x = (int) _locationController.getX();
		_y = (int) _locationController.getY();
		_entitiesCount = new JLabel(_locationController.getEntityList().size()+"",SwingConstants.CENTER);
		_entitiesCount.setForeground(Color.WHITE);
		_entitiesCount.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		add(_entitiesCount, BorderLayout.CENTER);
		setBounds(_x - _iconLoc.getIconWidth() / 2, _y - _iconLoc.getIconHeight() / 2, _iconLoc.getIconWidth(), _iconLoc.getIconHeight());
		setOpaque(false);
		_mouseListener = new LocationMouseListener(this, gPanel);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		setDropTarget(new DropTarget(this, new LocationDropTargetListener(this, gPanel)));		
	}
	
	public void moveLocation(int x, int y) {
		_offsetX = _offsetX + x;
		_offsetY = _offsetY + y;
		setBounds(_offsetX + _x - _iconLoc.getIconWidth() / 2, _offsetY + _y - _iconLoc.getIconHeight() / 2, _iconLoc.getIconWidth(), _iconLoc.getIconHeight());
		_locPanel.moveLocationPanel(x, y);
	}

	public void setOffsetX(int x) {
		_offsetX = x;
	}
	public void setOffsetY(int y) {
		_offsetY = y;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		if(_dragOver) {
			g2d.drawImage(_iconLocDragOver.getImage(), 0, 0, _iconLoc.getIconWidth(), _iconLoc.getIconHeight(), null, null);
		} else {
			g2d.drawImage(_iconLoc.getImage(), 0, 0, _iconLoc.getIconWidth(), _iconLoc.getIconHeight(), null, null);
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
	public int getRadius() {return _iconLoc.getIconWidth() / 2;}
	public LocationController getLocationController() {return _locationController;}
}
