package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddEquipierPanel;
import views.CustomDialog;
import views.GlobalPanel;
import views.MapPanel;
import views.MessagePanel;
import controllers.EntityController;
import controllers.OperationController;


public class AddEquipierButtonListener implements ActionListener {

	private String EMPTY_LIST_MESSAGE = "Il n'y a aucun équipier d'enregistré";
	private String EMPTY_DISPO_MESSAGE = "Il n'y a plus d'équipier de disponible";
	private EntityController _entityController;
	private OperationController _operationController;
	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;

	public AddEquipierButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController) 
	{
		_mapPanel = mapPanel;
		_globalPanel = _mapPanel.getGlobalPanel();
		_entityController=entityController;
		_operationController=operationController;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if (_operationController.getTeamMemberAvailableList().size() == 0)
		{
			if (_operationController.getTeamMemberList().size() == 0) {
				MessagePanel errorPanel = new MessagePanel("Equipier insuffisant", EMPTY_LIST_MESSAGE);
				new CustomDialog(errorPanel, _globalPanel);
			} else {
				MessagePanel errorPanel = new MessagePanel("Equipier insuffisant", EMPTY_DISPO_MESSAGE);
				new CustomDialog(errorPanel, _globalPanel);
			}
		} else {
			AddEquipierPanel addEquipierPanel = new AddEquipierPanel(_operationController, _entityController);
			new CustomDialog(addEquipierPanel, _globalPanel);
		}
	}
}