package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import views.AddEntityPanel;
import views.AddLocationPanel;
import views.MapPanel;
import views.MyJDialog;

public class CancelAddLocationListener implements ActionListener {
	private MapPanel _mapPanel;
	private AddLocationPanel _addLocationPanel;
	
	public CancelAddLocationListener(MapPanel mapPanel, AddLocationPanel addLocationPanel) {
		_mapPanel = mapPanel;
		_addLocationPanel = addLocationPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_addLocationPanel);
		dialog.dispose();
	}
}
