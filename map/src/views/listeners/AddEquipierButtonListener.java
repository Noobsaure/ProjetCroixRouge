package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import launcher.Launcher;
import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.EmptyEquipierPanel;
import views.EntityPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;


public class AddEquipierButtonListener implements ActionListener{

	private EntityController _entityController;
	private OperationController _operationController;
	private MapPanel _jParent;
	private ConfigurationEntityPanel _configEntityPanel;

	public AddEquipierButtonListener(MapPanel _parent, OperationController operationController, EntityController entityController, ConfigurationEntityPanel configEntityPanel) 
	{
		_jParent = _parent;
		_entityController=entityController;
		_operationController=operationController;
		_configEntityPanel=configEntityPanel;
	}

	public void actionPerformed(ActionEvent arg0)
	{		
		// Ouverture de la fentre qui permet l'ajout d'un équipier
		_operationController.removeObserver(_configEntityPanel);
System.out.println("list teamMember "+_operationController.getTeamMemberAvailableList().size());
		if (_operationController.getTeamMemberAvailableList().size() != 0)
		{
			AddEquipierPanel addEquipierPanel = new AddEquipierPanel(_jParent, _operationController, _entityController);
			_jParent.add(addEquipierPanel);
			_jParent.setComponentZOrder(addEquipierPanel, 0);
		}
		else
		{
			EmptyEquipierPanel emptyEquipierPanel = new EmptyEquipierPanel(_jParent, _operationController, _entityController);
			_jParent.add(emptyEquipierPanel);
			_jParent.setComponentZOrder(emptyEquipierPanel, 0);
		}
		_jParent.remove(_configEntityPanel);
		_jParent.repaint();
		_jParent.revalidate();
	}
}
