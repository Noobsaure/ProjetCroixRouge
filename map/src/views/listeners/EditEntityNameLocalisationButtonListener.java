package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import views.ConfigurationEntityPanel;
import views.CustomDialog;
import views.MapPanel;
import views.MessagePanel;
import controllers.EntityController;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;


public class EditEntityNameLocalisationButtonListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner le champ \"Nom\".";
	private String EMPTY_LOCAT_MESSAGE = "Veuillez choisir une autre localisation.";

	private OperationController _operationController;
	private MapPanel _mapPanel;
	private EntityController _entity;
	private LocationController _location;
	private ConfigurationEntityPanel _configPanel;
	private static List<LocationController> listLocation = new ArrayList<LocationController>();
	
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
		if(!checkInput(nomEntity)) {
			MessagePanel errorPanel = new MessagePanel("Saisie incomplète", EMPTY_NAME_MESSAGE);
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		} else {
			_entity.setName(nomEntity);
		}
		
		List<MapController> locatMap;
		locatMap = _operationController.getMapList();
		List<LocationController> locatMaplocat;
		System.out.println("index "+_configPanel.getIndexLocation());
		listLocation.add(_operationController.getLocation(_operationController.getIdPcm()));
		if (_configPanel.getIndexLocation() != -1)
		{
			for (MapController mapController : locatMap) 
			{	
				locatMaplocat =  mapController.getLocationList();
				for (LocationController locat : locatMaplocat)
				{	
					String temp = mapController.getName()+" => "+locat.getName();
					System.out.println("temp "+temp);
					listLocation.add(locat);
				}
			}
			_location = listLocation.get(_configPanel.getIndexLocation());
			System.out.println("liste de localisation "+_location.getName());
			_entity.setLocation(_location);
		}
		else
		{
		MessagePanel errorPanel = new MessagePanel("localisation ", EMPTY_LOCAT_MESSAGE);
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		}
		
	}

	
}