package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.MapPanel;

public class MenuMouseListener extends AbstractObserverListener implements MouseListener {

	private MapPanel _mapPanel;
	
	public MenuMouseListener(MapPanel mapPanel) {
		super(mapPanel);
		_mapPanel = mapPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		_mapPanel.disableLocationHighlight();
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
