package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import views.ConfigurationLocationPanel;
import views.MapPanel;
import views.MyJDialog;

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
		MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_configurationLocationPanel);
		dialog.dispose();
	}
}
