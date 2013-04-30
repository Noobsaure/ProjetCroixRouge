package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.MapPanel;

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
		_mapPanel.remove(_addEntityPanel);
		_mapPanel.setCurrentPopUp(null);
		_mapPanel.repaint();
	}
}
