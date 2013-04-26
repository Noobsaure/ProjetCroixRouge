package views.listeners;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import views.GlobalPanel;
import views.Location;
import views.MapPanel;

public class LocationMouseListener extends MouseAdapter implements MouseMotionListener {

	private Location _loc;
	private MapPanel _map;
	private GlobalPanel _globalPanel;
	private boolean _enabled = true;


	public LocationMouseListener(Location loc, GlobalPanel globalPanel) {
		_loc = loc;
		_globalPanel = globalPanel;
		_map = _globalPanel.getMapPanel();
	}

	public void enable(boolean enable) {
		_enabled = enable;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(_enabled) {
			if(_globalPanel.isDragOccurring()) {
				_loc.setDragOver(false);
			} else if(_loc.isHighlighted()) {
				_loc.setHighlight(false);
				_loc.repaint();
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(_enabled) {
			for(Location oneLoc : _map.getLocations()) {
				if(oneLoc != _loc) {
					oneLoc.displayPanel(false);
					oneLoc.setHighlight(false);
				}
			}

			if(_globalPanel.isDragOccurring()) {
				_loc.setDragOver(true);
			} else if(!_loc.isHighlighted()) {
				_loc.setHighlight(true);
				_loc.displayPanel(true);
				_loc.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(_enabled) {
			for(Location oneLoc : _map.getLocations()) {
				if(oneLoc != _loc) {
					oneLoc.displayPanel(false);
					oneLoc.setHighlight(false);
				}
			}

			if(_globalPanel.isDragOccurring()) {
				_loc.setDragOver(true);
			} else if(!_loc.isHighlighted()) {
				_loc.setHighlight(true);
				_loc.displayPanel(true);
				_loc.repaint();
			}
		}
	}
}
