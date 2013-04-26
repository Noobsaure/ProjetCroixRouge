package views.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import views.Location;

public class LocationPanelMouseListener extends MouseAdapter implements MouseMotionListener {

	private Location _loc;
	private boolean _enabled;

	public LocationPanelMouseListener(Location loc) {
		_loc = loc;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(_enabled) {
			_loc.setHighlight(true);
		}
	}

	public void enable(boolean enable) {
		_enabled = enable;
	}
}
