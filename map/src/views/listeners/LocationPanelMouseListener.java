package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import views.Location;

public class LocationPanelMouseListener implements MouseListener, MouseMotionListener {

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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
