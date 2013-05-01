package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingUtilities;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.MapPanel;
import views.CustomDialog;
import controllers.EntityController;
import controllers.OperationController;
import controllers.TeamMemberController;

public class AddEquipierDansEntityButtonListener implements ActionListener {

	private MapPanel _parent;
	private AddEquipierPanel _addEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	private TeamMemberController _team;
	private static List<TeamMemberController> listEquipiers;


	public AddEquipierDansEntityButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController,  AddEquipierPanel addEquipierPanel) 
	{
		_parent = mapPanel;
		_addEquipierPanel = addEquipierPanel;
		_operationController = operationController;
		_entityController = entityController;
	}

	// On récupère l'index du jCombobox selectionné et on le récupère dans la iste des équipiers
	// pour l'ajouter à l'entité

	public void actionPerformed(ActionEvent e)
	{	
		// On récupère l'équipier qui vient d'etre ajouté par l'opérateur.
		int indexEquipier = _addEquipierPanel.getIndexEquipier();

		listEquipiers= _operationController.getTeamMemberAvailableList();	

		if (indexEquipier>=0) {
			_team=listEquipiers.get(indexEquipier);
			System.out.println("Nom équipier : "+_team.getFirstName());
			_entityController.addTeamMember(_team);
		}
		
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addEquipierPanel);
		dialog.dispose();
	}
}



