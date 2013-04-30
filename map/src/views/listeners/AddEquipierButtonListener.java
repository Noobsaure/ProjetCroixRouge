package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;


public class AddEquipierButtonListener extends AbstractObserverListener implements ActionListener {

	private String EMPTY_LIST_MESSAGE = "Il n'y a aucun équipier d'enregistré";
	private String EMPTY_DISPO_MESSAGE = "Il n'y a plus d'équipier de disponible";
	private EntityController _entityController;
	private OperationController _operationController;
	private MapPanel _mapPanel;
	private ConfigurationEntityPanel _configEntityPanel;

	public AddEquipierButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController, ConfigurationEntityPanel configEntityPanel) 
	{
		super(mapPanel);
		_mapPanel = mapPanel;
		_entityController=entityController;
		_operationController=operationController;
		_configEntityPanel=configEntityPanel;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if(isEnabled()) {
			// Ouverture de la fentre qui permet l'ajout d'un équipier
			System.out.println("list teamMember "+_operationController.getTeamMemberAvailableList().size());

			if (_operationController.getTeamMemberAvailableList().size() == 0)
			{
				if (_operationController.getTeamMemberList().size() == 0)
					new ErrorMessage(_mapPanel, "Equipier insuffisant", EMPTY_LIST_MESSAGE);
				else
					new ErrorMessage(_mapPanel, "Equipier insuffisant", EMPTY_DISPO_MESSAGE);
			}
			else
			{
				AddEquipierPanel addEquipierPanel = new AddEquipierPanel(_mapPanel, _operationController, _entityController);
				_mapPanel.remove(_configEntityPanel);
				_mapPanel.setCurrentPopUp(null);
				_mapPanel.add(addEquipierPanel);
				_mapPanel.setCurrentPopUp(addEquipierPanel);
				_mapPanel.setComponentZOrder(addEquipierPanel, 0);
				_mapPanel.repaint();
				_mapPanel.revalidate();
			}
		}
	}
}
