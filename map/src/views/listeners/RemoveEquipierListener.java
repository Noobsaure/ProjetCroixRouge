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
	
	public RemoveEquipierListener(MapPanel mapPanel, JPanel parent, JPanel nomEquipierPanel, OperationController operationController ,TeamMemberController equipier, EntityController entityController, ConfigurationEntityPanel configurationEntityPanel)
	{
		_mapPanel=mapPanel;
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
		
		//ne met pas à jour la liste d'équipier dans l'entity
		System.out.println("dans Remove : "+_entityController.getTeamMemberList().toString());
		System.out.println("equipier supprimé : "+_equipier.getFirstName());
		MapPanel mapPanel = _mapPanel;
		mapPanel.addMapPanelListener();
		
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_mapPanel, _operationController, _entityController);
		
		_parent.remove(_configEntityPanel);
		_parent.repaint();
		_parent.revalidate();
		
		System.out.println("fermeture de la fenetre puis ouverture");
		
		_parent.add(configurationEntityPanel);
		_parent.setComponentZOrder(configurationEntityPanel, 0);
	}
	
}
