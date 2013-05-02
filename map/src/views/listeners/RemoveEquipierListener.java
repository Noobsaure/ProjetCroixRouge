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
	
	private MapPanel _mapPanel;
	private JPanel _parent;
	private JPanel _nomEquipierPanel;
	private OperationController _operationController;
	private TeamMemberController _equipier;
	private EntityController _entityController;
	private ConfigurationEntityPanel _configEntityPanel;
	
	public RemoveEquipierListener(EntityController entityController, TeamMemberController equipier) {
		_entityController = entityController;
		_equipier = equipier;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		_entityController.removeTeamMember(_equipier);
	}
	
}
