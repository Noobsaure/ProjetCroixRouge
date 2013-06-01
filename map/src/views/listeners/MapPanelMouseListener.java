package views.listeners;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import observer.Observer;
import observer.Subject;

import views.CustomDialog;
import views.GlobalPanel;
import views.Location;
import views.MapPanel;
import views.MessagePanel;
import views.buttons.AddLocationButton;

public class MapPanelMouseListener implements MouseListener, MouseMotionListener {

	private static final Cursor addableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	private static final Cursor notAddableCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);

	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;

	private int _x = 0;
	private int _y = 0;
	private boolean _addingLocation = false;
	private boolean _moving = false;

	private AddLocationButton _addLocationButton;

	public MapPanelMouseListener(GlobalPanel globalPanel) {
		_globalPanel = globalPanel;
		_mapPanel = _globalPanel.getMapPanel();
	}

	public void mouseMoved(MouseEvent e) {
		if(_addingLocation) {
			int x = e.getX();
			int y = e.getY();
			boolean away = isCoordAwayFromLocs(x, y);
			if(away && !_mapPanel.getCursor().equals(addableCursor)) {
				_mapPanel.setCursor(addableCursor);
			} else if (!away && !_mapPanel.getCursor().equals(notAddableCursor)){
				_mapPanel.setCursor(notAddableCursor);
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(_moving) {
			int x = e.getX();
			int y = e.getY();
			_mapPanel.moveMap(x - _x, y - _y);
			_x = x;
			_y = y;
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1) {
			_x = x;
			_y = y;
			_moving = true;
		}
	}


	public void mouseReleased(MouseEvent e) {
		_moving = false;
		int x = e.getX();
		int y = e.getY();
		if(_addingLocation) {
			if(e.getButton() == MouseEvent.BUTTON1 && isCoordAwayFromLocs(x, y)) {
				if(!isCoordWithinMap(x, y)) {
					MessagePanel errorPanel = new MessagePanel("Erreur" ,"Point en dehors de la carte.");
					new CustomDialog(errorPanel, _globalPanel);
				} else {
					_mapPanel.showAddLocationPanel(x - _mapPanel.get_x(), y - _mapPanel.get_y(), _addLocationButton);
					setAddingLocation(false, _addLocationButton);
				}
			} else {
				setAddingLocation(false, _addLocationButton);
			}
		} else if(e.getButton() == MouseEvent.BUTTON3) {
			if(!isCoordWithinMap(x, y)) {
				MessagePanel errorPanel = new MessagePanel("Erreur" ,"Point en dehors de la carte.");
				new CustomDialog(errorPanel, _globalPanel);
			} else {
				displayJPopMenu(x,y);
			}
		}
	}

	public void setAddingLocation(boolean addingLocation, AddLocationButton addButton) {
		_addingLocation = addingLocation;
		_addLocationButton = addButton;
		if(_addingLocation){
			_mapPanel.setCursor(addableCursor);
		} else {
			_mapPanel.setCursor(Cursor.getDefaultCursor());
		}
	}
	public boolean getAddingLocation() {return _addingLocation;}

	private boolean isCoordWithinMap(int x, int y) {
		if(_mapPanel.getMap() == null) {
			return false;
		}
		return x >= _mapPanel.get_x() && y >= _mapPanel.get_y() && x < _mapPanel.get_x() + _mapPanel.getMap().getWidth() && y < _mapPanel.get_y() + _mapPanel.getMap().getHeight();
	}

	private boolean isCoordAwayFromLocs(int x, int y) {
		boolean res = true;
		int mx = _mapPanel.get_x();
		int my = _mapPanel.get_y();
		for(Location loc : _mapPanel.getLocations()) {
			int dx = Math.abs(x - (loc.get_x() + mx));
			int dy = Math.abs(y - (loc.get_y() + my));
			if(dx < Location._iconLocWidth && dy < Location._iconLocHeight) {
				res = false;
				break;
			}
		}
		return res;
	}

	private void displayJPopMenu(int x, int y) {
		JPopupMenu jpm = new JPopupMenu();
		JMenuItem launch = new JMenuItem("Ajouter une localisation");
		launch.setEnabled(isCoordAwayFromLocs(x,y));
		jpm.add(launch);
		launch.addActionListener(new NewLocationListener(_globalPanel,x - _mapPanel.get_x(),y - _mapPanel.get_y()));
		jpm.show(_mapPanel, x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		_mapPanel.disableLocationHighlight();

	}

	@Override
	public void mouseExited(MouseEvent e) {
		_mapPanel.disableLocationHighlight();
	}
}