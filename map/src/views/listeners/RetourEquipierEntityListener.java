package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controllers.EntityController;
import controllers.OperationController;
import views.ConfigurationEntityPanel;
import views.MapPanel;
import views.CustomDialog;

public class RetourEquipierEntityListener implements ActionListener
{
	private MapPanel _mapPanel;
	private ConfigurationEntityPanel _configEntityPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	
	public RetourEquipierEntityListener(MapPanel mapPanel, ConfigurationEntityPanel configurationEntityPanel, OperationController operationController, EntityController entityController)
	{
		_mapPanel = mapPanel;
		_configEntityPanel=configurationEntityPanel;
		_operationController = operationController;
		_entityController = entityController;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		_operationController.removeObserver(_configEntityPanel);
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_configEntityPanel);
		dialog.dispose();
	}
}