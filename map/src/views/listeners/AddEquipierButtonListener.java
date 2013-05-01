package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.GlobalPanel;
import views.MapPanel;
import views.MessagePanel;
import views.MyJDialog;
import controllers.EntityController;
import controllers.OperationController;


public class AddEquipierButtonListener implements ActionListener {

	private String EMPTY_LIST_MESSAGE = "Il n'y a aucun équipier d'enregistré";
	private String EMPTY_DISPO_MESSAGE = "Il n'y a plus d'équipier de disponible";
	private EntityController _entityController;
	private OperationController _operationController;
	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;
	private ConfigurationEntityPanel _configEntityPanel;

	public AddEquipierButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController, ConfigurationEntityPanel configEntityPanel) 
	{
		_mapPanel = mapPanel;
		_globalPanel = _mapPanel.getGlobalPanel();
		_entityController=entityController;
		_operationController=operationController;
		_configEntityPanel=configEntityPanel;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if (_operationController.getTeamMemberAvailableList().size() == 0)
		{
			if (_operationController.getTeamMemberList().size() == 0) {
				MessagePanel errorPanel = new MessagePanel("Equipier insuffisant", EMPTY_LIST_MESSAGE);
				new MyJDialog(errorPanel, _globalPanel);
			} else {
				MessagePanel errorPanel = new MessagePanel("Equipier insuffisant", EMPTY_DISPO_MESSAGE);
				new MyJDialog(errorPanel, _globalPanel);
			}
		} else {
			AddEquipierPanel addEquipierPanel = new AddEquipierPanel(_mapPanel, _operationController, _entityController);
			new MyJDialog(addEquipierPanel, _globalPanel);
		}
	}
}