package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.EntityController;
import controllers.TeamMemberController;


public class RemoveEquipierListener implements ActionListener{
	
	private TeamMemberController _equipier;
	private EntityController _entityController;
	
	public RemoveEquipierListener(EntityController entityController, TeamMemberController equipier) {
		_entityController = entityController;
		_equipier = equipier;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		_entityController.removeTeamMember(_equipier);
	}
	
}
