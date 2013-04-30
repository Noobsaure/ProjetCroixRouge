package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;
import controllers.TeamMemberController;

public class AddEquipierDansEntityButtonListener extends AbstractListener implements ActionListener {

	private MapPanel _parent;
	private AddEquipierPanel _addEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	private TeamMemberController _team;
	private static List<TeamMemberController> listEquipiers;


	public AddEquipierDansEntityButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController,  AddEquipierPanel addEquipierPanel) 
	{
		super(mapPanel);
		_parent = mapPanel;
		_addEquipierPanel = addEquipierPanel;
		_operationController = operationController;
		_entityController = entityController;
	}


	// on récupère l'index du jCombobox selectionné et on le récupère dans la iste des équipiers
	// pour l'ajouter à l'entité

	public void actionPerformed(ActionEvent e)
	{	
		if(isEnabled()) {
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
}


