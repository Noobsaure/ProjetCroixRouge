package views.listeners;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import views.GlobalPanel;
import views.Location;
import views.MapPanel;

public class MapPanelMouseListener extends MouseAdapter {

	private static final Cursor addableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	private static final Cursor notAddableCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);

	private MapPanel _map;
	private GlobalPanel _gPanel;

	private int _x = 0;
	private int _y = 0;
	private boolean _addingLocation = false;
	private boolean _enabled = true;

	public MapPanelMouseListener(GlobalPanel gPanel) {
		super();
		_gPanel = gPanel;
		_map = _gPanel.getMapPanel();
	}

	public void mouseMoved(MouseEvent e) {
		if(_enabled) {
			int x = e.getX();
			int y = e.getY();
			_map.disableLocationHighlight();
			if(_addingLocation) {
				if(isCoordAwayFromLocs(x, y) && !_map.getCursor().equals(addableCursor)) {
					_map.setCursor(addableCursor);
				} else if (!isCoordAwayFromLocs(x, y) && !_map.getCursor().equals(notAddableCursor)){
					_map.setCursor(notAddableCursor);
				}
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(_enabled) {
			int x = e.getX();
			int y = e.getY();
			_map.moveMap(x - _x, y - _y);
			_x = x;
			_y = y;
		}
	}

	public void mousePressed(MouseEvent e) {
		if(_enabled) {
			_x = e.getX();
			_y = e.getY();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(_enabled) {
			int x = e.getX();
			int y = e.getY();
			if(_addingLocation) {
				if(e.getButton() == MouseEvent.BUTTON1 && isCoordAwayFromLocs(x, y)) {
					_map.showAddLocationPanel(x, y);
					setAddingLocation(false);
				} else {
					setAddingLocation(false);				
				}
			}
			else if(e.getButton() == MouseEvent.BUTTON3) {
				displayJPopMenu(x,y);
			}
		}
	}

	public void setAddingLocation(boolean addingLocation) {
		_addingLocation = addingLocation;
		if(_addingLocation){
			_map.setCursor(addableCursor);
		} else {
			_map.setCursor(Cursor.getDefaultCursor());
		}
	}
	public boolean getAddingLocation() {return _addingLocation;}

	private boolean isCoordWithinMap(int x, int y) {
		return x >= _map.get_x() && y >= _map.get_y() && x < _map.get_x() + _map.getWidth() && y < _map.get_y() + _map.getHeight();
	}

	private boolean isCoordAwayFromLocs(int x, int y) {
		boolean res = true;
		for(Location loc : _map.getLocations()) {
			int dx = Math.abs(x - loc.get_x());
			int dy = Math.abs(y - loc.get_y());
			if(dx < 32 && dy < 32) {
				res = false;
				break;
			}
		}
		return res;
	}

	public void enable(boolean enable) {
		_enabled = enable;
		_addingLocation = _enabled && _addingLocation ;
	}

	private void displayJPopMenu(int x, int y) {
		JPopupMenu jpm = new JPopupMenu();
		JMenuItem launch = new JMenuItem("Ajouter une localisation");
		launch.setEnabled(isCoordAwayFromLocs(x,y));
		jpm.add(launch);
		launch.addActionListener(new NewLocationListener(_gPanel,x,y));
		jpm.show(_map, x, y);
	}
}