package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.AddLocationPanel;
import views.CustomDialog;

public class CancelAddLocationListener implements ActionListener {
	private AddLocationPanel _addLocationPanel;
	
	public CancelAddLocationListener(AddLocationPanel addLocationPanel) {
		_addLocationPanel = addLocationPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addLocationPanel);
		dialog.dispose();
		_addLocationPanel.notifyObservers();
	}
}
