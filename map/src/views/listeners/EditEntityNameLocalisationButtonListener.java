package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import views.ConfigurationEntityPanel;
import views.EntityPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.LocationController;
import controllers.OperationController;


public class EditEntityNameLocalisationButtonListener implements ActionListener
{
	private JPanel _parent;
	private OperationController _operationController;
	private EntityPanel _entityPanel;
	private MapPanel _mapPanel;
	private EntityController _entity;
	private LocationController _location;
	private ConfigurationEntityPanel _configPanel;
	private static List<LocationController> listLocation;

	
	public EditEntityNameLocalisationButtonListener(JPanel parent, OperationController operationController, EntityController entity, ConfigurationEntityPanel configPanel, MapPanel mapPanel)
	{
		_parent=parent;
		_operationController = operationController;
		_entity = entity;
		_configPanel=configPanel;
		_mapPanel = mapPanel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		String nomEntity = _configPanel.getNewName();
		int indexLocation = _configPanel.getIndexLocation();
		
		//System.out.println("entity ancien nom : "+_entity.getName());
		
		System.out.println("nom entity : "+nomEntity);
		_entity.setName(nomEntity);
		
		// modification de la localisation de l'entité
		listLocation= _operationController.getLocationList();
		_location = listLocation.get(_configPanel.getIndexLocation());
		System.out.println("Nouvelle localisation entity : "+_location.getName());
		
		// erreur sur la modification de la localisation d'une entité
		
		//_entity.setLocation(_operationController.getLocation(indexLocation));
		_entity.setLocation(_location); 
		
		_parent.repaint();
		_parent.revalidate();
		
		_mapPanel.addMapPanelListener();
		
	}

	
}