package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.EmptyEquipierPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;

public class EmptyEquipierListener implements ActionListener
{
	private MapPanel _jParent;
	private EmptyEquipierPanel _emptyEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	
	public EmptyEquipierListener(MapPanel parent, OperationController operationController, EntityController entityController, EmptyEquipierPanel emptyEquipierPanel)
	{
		_jParent = parent;
		_operationController = operationController;
		_entityController = entityController;
		_emptyEquipierPanel = emptyEquipierPanel;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		// Arret de l'jout d'equipier dans l'entité
		
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_jParent, _operationController, _entityController);
		_jParent.add(configurationEntityPanel);
		_jParent.setComponentZOrder(configurationEntityPanel, 0);
		
		_jParent.remove(_emptyEquipierPanel);
		_jParent.repaint();
		_jParent.revalidate();
	}
}
