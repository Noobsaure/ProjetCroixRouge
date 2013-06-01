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
		_map.showAddLocationPanel(_x, _y, null);
		_map.repaint();
	}
}
