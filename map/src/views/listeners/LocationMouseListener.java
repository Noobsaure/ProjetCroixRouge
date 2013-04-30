package views.listeners;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import views.GlobalPanel;
import views.Location;
import views.MapPanel;

public class LocationMouseListener extends AbstractObserverListener implements MouseMotionListener, MouseListener {

	private Location _loc;
	private MapPanel _map;
	private GlobalPanel _globalPanel;


	public LocationMouseListener(Location loc, GlobalPanel globalPanel) {
		super(globalPanel.getMapPanel());
		_loc = loc;
		_globalPanel = globalPanel;
		_map = _globalPanel.getMapPanel();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(isEnabled()) {
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
		if(isEnabled()) {
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
		if(isEnabled()) {
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
