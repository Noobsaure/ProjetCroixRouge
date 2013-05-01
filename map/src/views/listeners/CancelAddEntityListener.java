package views.listeners;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import views.AddEntityPanel;
import views.MapPanel;
import views.MyJDialog;

public class CancelAddEntityListener implements ActionListener
{
	private MapPanel _mapPanel;
	private AddEntityPanel _addEntityPanel;
	
	public CancelAddEntityListener(MapPanel mapPanel, AddEntityPanel addEntityPanel)
	{
		_mapPanel = mapPanel;
		_addEntityPanel = addEntityPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_addEntityPanel);
		dialog.dispose();
	}
}
