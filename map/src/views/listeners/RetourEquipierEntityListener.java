package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.ConfigurationEntityPanel;
import views.CustomDialog;

public class RetourEquipierEntityListener implements ActionListener
{
	private ConfigurationEntityPanel _configEntityPanel;
	
	public RetourEquipierEntityListener(ConfigurationEntityPanel configurationEntityPanel)
	{
		_configEntityPanel = configurationEntityPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_configEntityPanel);
		dialog.dispose();
	}
}