package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.AddLocationPanel;
import views.MapPanel;

public class CancelAddLocationListener implements ActionListener {
	private MapPanel _mapPanel;
	private AddLocationPanel _addLocationPanel;
	
	public CancelAddLocationListener(MapPanel mapPanel, AddLocationPanel addLocationPanel) {
		_mapPanel = mapPanel;
		_addLocationPanel = addLocationPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		_mapPanel.setCurrentPopUp(null);
		_mapPanel.remove(_addLocationPanel);
		_mapPanel.repaint();
	}
}
