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
	private MapPanel _jParent;
	private AddEquipierPanel _addEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	
	public CancelEquipierEquipierButtonListener(MapPanel parent, OperationController operationController, EntityController entityController,  AddEquipierPanel addEquipierPanel)
	{
		_jParent = parent;
		_addEquipierPanel = addEquipierPanel;
		_operationController = operationController;
		_entityController = entityController;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		// Arret de l'jout d'equipier dans l'entité
		
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_jParent, _operationController, _entityController);
		_jParent.add(configurationEntityPanel);
		_jParent.setComponentZOrder(configurationEntityPanel, 0);
		
		_jParent.remove(_addEquipierPanel);
		_jParent.repaint();
		_jParent.revalidate();
	}
}
