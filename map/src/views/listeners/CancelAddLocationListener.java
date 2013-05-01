package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import views.AddEntityPanel;
import views.AddLocationPanel;
import views.MapPanel;
import views.CustomDialog;

public class CancelAddLocationListener implements ActionListener {
	private MapPanel _mapPanel;
	private AddLocationPanel _addLocationPanel;
	
	public CancelAddLocationListener(MapPanel mapPanel, AddLocationPanel addLocationPanel) {
		_mapPanel = mapPanel;
		_addLocationPanel = addLocationPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addLocationPanel);
		dialog.dispose();
	}
}
