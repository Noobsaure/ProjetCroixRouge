package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import controllers.EntityController;
import controllers.OperationController;
import controllers.TeamMemberController;

public class AddEquipierDansEntityButtonListener implements ActionListener {
	
	private JPanel _parent;
	private AddEquipierPanel _addEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	private TeamMemberController _team;
	private static List<TeamMemberController> listEquipiers;

	
	public AddEquipierDansEntityButtonListener(JPanel parent, OperationController operationController, EntityController entityController,  AddEquipierPanel addEquipierPanel) 
	{
		_parent = parent;
		_addEquipierPanel = addEquipierPanel;
		_operationController = operationController;
		_entityController = entityController;
	}

	
	// on récupère l'index du jCombobox selectionné et on le récupère dans la iste des équipiers
	// pour l'ajouter à l'entité
	
	public void actionPerformed(ActionEvent e)
	{	
		// On récupère l'équipier qui vient d'etre ajouté par l'opérateur.
		int indexEquipier = _addEquipierPanel.getIndexEquipier();
		
		listEquipiers= _operationController.getTeamMemberAvailableList();	
		
		if (indexEquipier>=0) 
		{
			_team=listEquipiers.get(indexEquipier);
			_entityController.addTeamMember(_team);
		}
		else
		{
			System.out.println("Impossible d'ajouter un équipier.");
		}
		
		// on retourne à la fenetre de configuration de l'entité
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_parent, _operationController, _entityController);
		
		_parent.add(configurationEntityPanel);
		_parent.setComponentZOrder(configurationEntityPanel, 0);
		
		_parent.remove(_addEquipierPanel);
		_parent.repaint();
		_parent.revalidate();
	
	}
}


