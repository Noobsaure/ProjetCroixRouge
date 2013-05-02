package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.ConfigurationLocationPanel;
import views.CustomDialog;

public class CancelConfigureLocationListener implements ActionListener
{
	private ConfigurationLocationPanel _configurationLocationPanel;
	
	public CancelConfigureLocationListener(ConfigurationLocationPanel configurationLocationPanel)
	{
		_configurationLocationPanel = configurationLocationPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_configurationLocationPanel);
		dialog.dispose();
	}
}
