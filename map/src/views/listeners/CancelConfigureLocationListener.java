package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.ConfigurationLocationPanel;
import views.MapPanel;

public class CancelConfigureLocationListener implements ActionListener
{
	private JPanel _parent;
	private ConfigurationLocationPanel _configurationLocationPanel;
	
	public CancelConfigureLocationListener(JPanel parent, ConfigurationLocationPanel configurationLocationPanel)
	{
		_parent = parent;
		_configurationLocationPanel = configurationLocationPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		
		_parent.remove(_configurationLocationPanel);
		_parent.repaint();
	}
}
