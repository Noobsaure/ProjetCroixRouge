package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;

public class CancelEquipierEquipierButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private AddEquipierPanel _addEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	
	public CancelEquipierEquipierButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController,  AddEquipierPanel addEquipierPanel)
	{
		_mapPanel = mapPanel;
		_addEquipierPanel = addEquipierPanel;
		_operationController = operationController;
		_entityController = entityController;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		_mapPanel.remove(_addEquipierPanel);
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_mapPanel, _operationController, _entityController);
		_mapPanel.add(configurationEntityPanel);
		_mapPanel.setCurrentPopUp(configurationEntityPanel);
		_mapPanel.setComponentZOrder(configurationEntityPanel, 0);
		_mapPanel.repaint();
	}
}
