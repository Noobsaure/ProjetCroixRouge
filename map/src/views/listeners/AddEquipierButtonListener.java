package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;


public class AddEquipierButtonListener implements ActionListener {

	private String EMPTY_LIST_MESSAGE = "Il n'y a aucun équipier d'enregistré";
	private String EMPTY_DISPO_MESSAGE = "Il n'y a plus d'équipier de disponible";
	private EntityController _entityController;
	private OperationController _operationController;
	private MapPanel _mapPanel;
	private ConfigurationEntityPanel _configEntityPanel;

	public AddEquipierButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController, ConfigurationEntityPanel configEntityPanel) 
	{
		_mapPanel = mapPanel;
		_entityController=entityController;
		_operationController=operationController;
		_configEntityPanel=configEntityPanel;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if (_operationController.getTeamMemberAvailableList().size() == 0)
		{
			if (_operationController.getTeamMemberList().size() == 0) {
				new ErrorMessage(_mapPanel, "Equipier insuffisant", EMPTY_LIST_MESSAGE);
			} else {
				new ErrorMessage(_mapPanel, "Equipier insuffisant", EMPTY_DISPO_MESSAGE);
			}
		} else {
			_mapPanel.remove(_configEntityPanel);
			_mapPanel.setCurrentPopUp(null);
			AddEquipierPanel addEquipierPanel = new AddEquipierPanel(_mapPanel, _operationController, _entityController);
			_mapPanel.add(addEquipierPanel);
			_mapPanel.setComponentZOrder(addEquipierPanel, 0);
			_mapPanel.repaint();
			_mapPanel.revalidate();
		}
	}
}