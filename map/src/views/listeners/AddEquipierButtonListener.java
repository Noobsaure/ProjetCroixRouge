package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import launcher.Launcher;
import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.EmptyEquipierPanel;
import views.EntityPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;


public class AddEquipierButtonListener implements ActionListener{

	private String EMPTY_LIST_MESSAGE = "Il n'y a aucun équipier d'enregistré";
	private String EMPTY_DISPO_MESSAGE = "Il n'y a plus d'équipier de disponible";
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
		
		if (_operationController.getTeamMemberAvailableList().size() == 0)
		{
			if (_operationController.getTeamMemberList().size() == 0)
				new ErrorMessage(_jParent, "Equipier insuffisant", EMPTY_LIST_MESSAGE);
			else
				new ErrorMessage(_jParent, "Equipier insuffisant", EMPTY_DISPO_MESSAGE);
		}
		else
		{
			AddEquipierPanel addEquipierPanel = new AddEquipierPanel(_jParent, _operationController, _entityController);
			_jParent.add(addEquipierPanel);
			
			_jParent.setComponentZOrder(addEquipierPanel, 0);
			_jParent.remove(_configEntityPanel);
			_jParent.repaint();
			_jParent.revalidate();
		}
		
	}
}
