package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.ConfigurationLocationPanel;
import views.MapPanel;

public class CancelConfigureLocationListener implements ActionListener
{
	private MapPanel _mapPanel;
	private ConfigurationLocationPanel _configurationLocationPanel;
	
	public CancelConfigureLocationListener(MapPanel mapPanel, ConfigurationLocationPanel configurationLocationPanel)
	{
		_mapPanel = mapPanel;
		_configurationLocationPanel = configurationLocationPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		_mapPanel.setCurrentPopUp(null);
		_mapPanel.remove(_configurationLocationPanel);
		_mapPanel.repaint();
	}
}
