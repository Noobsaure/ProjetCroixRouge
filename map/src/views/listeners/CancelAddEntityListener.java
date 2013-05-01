package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.AddEntityPanel;
import views.MapPanel;
import views.CustomDialog;

public class CancelAddEntityListener implements ActionListener
{
	private AddEntityPanel _addEntityPanel;
	
	public CancelAddEntityListener(MapPanel mapPanel, AddEntityPanel addEntityPanel)
	{
		_addEntityPanel = addEntityPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addEntityPanel);
		dialog.dispose();
	}
}
