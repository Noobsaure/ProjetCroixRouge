package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.AddLocationPanel;
import views.MapPanel;

public class CancelAddLocationListener implements ActionListener
{
	private JPanel _parent;
	private AddLocationPanel _addLocationPanel;
	
	public CancelAddLocationListener(JPanel parent, AddLocationPanel addLocationPanel)
	{
		_parent = parent;
		_addLocationPanel = addLocationPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		
		_parent.remove(_addLocationPanel);
		_parent.repaint();
	}
}
