package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.GlobalPanel;
import views.MapPanel;

public class NewLocationListener implements ActionListener {

	private MapPanel _map;
	private GlobalPanel _gPanel;
	private int _x,_y;
	
	
	public NewLocationListener(GlobalPanel gPanel, int x, int y) {
		_gPanel = gPanel;
		_map = _gPanel.getMapPanel();
		_x = x;
		_y = y;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_map.showAddLocationPanel(_x, _y);
		/*Location nLoc = new Location(_x,_y,_x/(float)_map.getWidth(),_y/(float)_map.getHeight(),"deeeerp",16,_gPanel);
		LocationPanel nLocPanel = new LocationPanel(nLoc,_x,_y);
		nLoc.set_locPanel(nLocPanel);
		_map.add(nLoc);
		_map.add(nLocPanel);
		int nbLocs = _map.getLocations().size();
		int z = 0;
		for(Location oneLoc : _map.getLocations()) {
			_map.setComponentZOrder(oneLoc, z + nbLocs);
			_map.setComponentZOrder(oneLoc.getLocPanel(), z);
			z++;
		}*/
		_map.repaint();
	}
}
