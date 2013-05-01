package views.listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.ConfigurationLocationPanel;
import views.MapPanel;
import views.MessagePanel;
import views.CustomDialog;
import controllers.LocationController;
import controllers.OperationController;
import database.DatabaseManager;



public class ConfirmConfigureLocationListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";

	private MapPanel _mapPanel;
	private OperationController _operationController;
	private LocationController _locationController;
	private DatabaseManager _databaseManager;
	private ConfigurationLocationPanel _configurationLocationPanel;


	public ConfirmConfigureLocationListener(MapPanel mapPanel, OperationController operationController, DatabaseManager databaseManager, ConfigurationLocationPanel configurationLocationPanel, LocationController locationController)
	{
		_mapPanel = mapPanel;
		_operationController = operationController;
		_locationController = locationController;
		_databaseManager = databaseManager;
		_configurationLocationPanel = configurationLocationPanel;
	}


	public boolean checkInput(String name, String informations)
	{
		return !name.equals("");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String name = _configurationLocationPanel.getFieldName();
		String informations = _configurationLocationPanel.getInformations();

		if(!checkInput(name, informations))
		{
			if(name.equals("")) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète - Le nom ne peut pas être vide.", EMPTY_NAME_MESSAGE);
				new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
			}
		}
		else
		{
			_locationController.setName(name);
			_locationController.setDescription(informations);
			_locationController.notifyObservers();
			CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_configurationLocationPanel);
			dialog.dispose();
		}
	}
}