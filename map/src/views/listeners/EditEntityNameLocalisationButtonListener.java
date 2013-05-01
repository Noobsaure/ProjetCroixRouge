package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import views.ConfigurationEntityPanel;
import views.EntityPanel;
import views.MapPanel;
import views.MessagePanel;
import views.CustomDialog;
import controllers.EntityController;
import controllers.LocationController;
import controllers.OperationController;


public class EditEntityNameLocalisationButtonListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner le champ \"Nom\".";

	private OperationController _operationController;
	private EntityPanel _entityPanel;
	private MapPanel _mapPanel;
	private EntityController _entity;
	private LocationController _location;
	private ConfigurationEntityPanel _configPanel;
	private static List<LocationController> listLocation;
	
	public EditEntityNameLocalisationButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entity, ConfigurationEntityPanel configPanel)
	{
		_operationController = operationController;
		_entity = entity;
		_configPanel=configPanel;
		_mapPanel = mapPanel;
	}

	public boolean checkInput(String name)
	{
		return (!name.equals(""));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nomEntity = _configPanel.getNewName();
		int indexLocation = _configPanel.getIndexLocation();
		
		if(!checkInput(nomEntity)) {
			MessagePanel errorPanel = new MessagePanel("Saisie incomplète", EMPTY_NAME_MESSAGE);
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		} else {
			_entity.setName(nomEntity);
		}
		
		listLocation= _operationController.getLocationList();
		_location = listLocation.get(_configPanel.getIndexLocation());
		_entity.setLocation(_location);
	}

	
}