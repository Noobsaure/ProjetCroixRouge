package views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.dnd.DropTarget;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.EntityController;
import controllers.LocationController;

import views.listeners.LocationDropTargetListener;
import views.listeners.LocationMouseListener;

public class Location extends JPanel {

	private static final long serialVersionUID = 1L;

	private int _x,_y;
	private int _offsetX = 0;
	private int _offsetY = 0;
	private boolean _dragOver = false;
	private String _name;
	private boolean _highlighted = false;
	private boolean _mouseWithin = false;
	private ImageIcon _iconLoc;
	private ImageIcon _iconLocDragOver;
	private JLabel _iconLocPanel;
	private LocationPanel _locPanel;
	private LocationController _locationController;
	private LocationMouseListener _mouseListener;

	public Location(GlobalPanel gPanel, LocationController locationController) {
		super();
		_locationController = locationController;
		System.out.println("Liste des entites : ");
		for(EntityController entity : locationController.getEntityList()){
			System.out.println(entity.show());
		}
		_name = _locationController.getName();
		_x = (int) _locationController.getX();
		_y = (int) _locationController.getY();
		
		_iconLoc = new ImageIcon(EntityPanel.class.getResource("/ui/rouge2.png"));
		_iconLocDragOver = new ImageIcon(EntityPanel.class.getResource("/ui/vert2.png"));
		
		setBounds(_x - 16, _y - 16, 32, 32);
		setOpaque(false);
		_mouseListener = new LocationMouseListener(this, gPanel);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		setDropTarget(new DropTarget(this, new LocationDropTargetListener(this, gPanel)));
	}
	
	public void moveLocation(int x, int y) {
		_offsetX = _offsetX + x;
		_offsetY = _offsetY + y;
		setBounds(_offsetX + _x - 16, _offsetY + _y - 16, getWidth(), getHeight());
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
			g2d.drawImage(_iconLocDragOver.getImage(), 0, 0, 32, 32, null, null);
		} else {
			g2d.drawImage(_iconLoc.getImage(), 0, 0, 32, 32, null, null);
		}
		g.drawString(""+(_locPanel.getEntitiesPanel().getComponentCount()), getWidth() / 2, getHeight() / 2);
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
