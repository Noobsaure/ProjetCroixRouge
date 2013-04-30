package views.listeners;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import views.GlobalPanel;
import views.Location;
import views.MapPanel;

public class MapPanelMouseListener extends AbstractListener implements MouseListener, MouseMotionListener {

	private static final Cursor addableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	private static final Cursor notAddableCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);

	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;

	private int _x = 0;
	private int _y = 0;
	private boolean _addingLocation = false;
	private boolean _moving = false;

	public MapPanelMouseListener(GlobalPanel globalPanel) {
		super(globalPanel.getMapPanel());
		_globalPanel = globalPanel;
		_mapPanel = _globalPanel.getMapPanel();
	}

	public void mouseMoved(MouseEvent e) {
		if(isEnabled()) {
			int x = e.getX();
			int y = e.getY();
			_mapPanel.disableLocationHighlight();
			if(_addingLocation) {
				if(isCoordAwayFromLocs(x, y) && !_mapPanel.getCursor().equals(addableCursor)) {
					_mapPanel.setCursor(addableCursor);
				} else if (!isCoordAwayFromLocs(x, y) && !_mapPanel.getCursor().equals(notAddableCursor)){
					_mapPanel.setCursor(notAddableCursor);
				}
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(isEnabled() && _moving) {
			int x = e.getX();
			int y = e.getY();
			_mapPanel.moveMap(x - _x, y - _y);
			_x = x;
			_y = y;
		}
	}

	public void mousePressed(MouseEvent e) {
		if(isEnabled()) {
			int x = e.getX();
			int y = e.getY();
			if(e.getButton() == MouseEvent.BUTTON1 && isCoordAwayFromLocs(x, y)) {
				_x = x;
				_y = y;
				_moving = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		_moving = false;
		if(isEnabled()) {
			int x = e.getX();
			int y = e.getY();
			if(_addingLocation) {
				if(e.getButton() == MouseEvent.BUTTON1 && isCoordAwayFromLocs(x, y)) {
					_mapPanel.showAddLocationPanel(x, y);
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
			_mapPanel.setCursor(addableCursor);
		} else {
			_mapPanel.setCursor(Cursor.getDefaultCursor());
		}
	}
	public boolean getAddingLocation() {return _addingLocation;}

	private boolean isCoordWithinMap(int x, int y) {
		return x >= _mapPanel.get_x() && y >= _mapPanel.get_y() && x < _mapPanel.get_x() + _mapPanel.getMap().getWidth() && y < _mapPanel.get_y() + _mapPanel.getMap().getHeight();
	}

	private boolean isCoordAwayFromLocs(int x, int y) {
		boolean res = true;
		for(Location loc : _mapPanel.getLocations()) {
			int dx = Math.abs(x - loc.get_x());
			int dy = Math.abs(y - loc.get_y());
			if(dx < 32 && dy < 32) {
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
		launch.addActionListener(new NewLocationListener(_globalPanel,x,y));
		jpm.show(_mapPanel, x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}