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

	private JPanel _parent;
	private JPanel _nomEquipierPanel;
	private OperationController _operationController;
	private TeamMemberController _equipier;
	private EntityController _entityController;
	private ConfigurationEntityPanel _configEntityPanel;
	
	public RemoveEquipierListener(JPanel parent, JPanel nomEquipierPanel, OperationController operationController ,TeamMemberController equipier, EntityController entityController, ConfigurationEntityPanel configurationEntityPanel)
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
		// on supprime l'équipier de l'entité
		_entityController.removeTeamMember(_equipier);
		//_equipier.leaveEntity();
		
		
		// ??????
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		// *********
		
		_parent.remove(_nomEquipierPanel);
		
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_parent, _operationController, _entityController);
		
		_parent.remove(_configEntityPanel);
		_parent.repaint();
		_parent.revalidate();
		
		System.out.println("fermeture de la fenetre puis ouverture");
		
		_parent.add(configurationEntityPanel);
		_parent.setComponentZOrder(configurationEntityPanel, 0);
	}
	
}
