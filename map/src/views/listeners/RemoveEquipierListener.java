package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.ConfigurationEntityPanel;
import views.MapPanel;

import controllers.EntityController;
import controllers.OperationController;
import controllers.TeamMemberController;


public class RemoveEquipierListener implements ActionListener{

	private MapPanel _parent;
	private JPanel _nomEquipierPanel;
	private OperationController _operationController;
	private TeamMemberController _equipier;
	private EntityController _entityController;
	private ConfigurationEntityPanel _configEntityPanel;
	
	public RemoveEquipierListener(MapPanel parent, JPanel nomEquipierPanel, OperationController operationController ,TeamMemberController equipier, EntityController entityController, ConfigurationEntityPanel configurationEntityPanel)
	{
		_parent = parent;
		_nomEquipierPanel = nomEquipierPanel;
		_equipier = equipier;
		_entityController = entityController;
		_operationController = operationController;
		_configEntityPanel = configurationEntityPanel;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		_entityController.removeTeamMember(_equipier);
	}
	
}
