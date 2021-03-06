package views.listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.ConfigurationLocationPanel;
import views.CustomDialog;
import views.MapPanel;
import views.MessagePanel;
import controllers.LocationController;



public class ConfirmConfigureLocationListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";

	private MapPanel _mapPanel;
	private LocationController _locationController;
	private ConfigurationLocationPanel _configurationLocationPanel;


	public ConfirmConfigureLocationListener(MapPanel mapPanel, ConfigurationLocationPanel configurationLocationPanel, LocationController locationController)
	{
		_mapPanel = mapPanel;
		_locationController = locationController;
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
		int color = _configurationLocationPanel.getColor();
		
		if(!checkInput(name, informations))
		{
			MessagePanel errorPanel = new MessagePanel("Saisie incomplète - Le nom ne peut pas être vide.", EMPTY_NAME_MESSAGE);
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		} else {
			if(!name.equals(_locationController.getName())) {
				_locationController.setName(name);
			}
			if(!informations.equals(_locationController.getDescription())) {
				_locationController.setDescription(informations);
			}
			if(color != _locationController.getColor()){
				_locationController.setColor(color);
			}
			_mapPanel.getGlobalPanel().getLauncher().getOperationController().notifyObservers();
			CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_configurationLocationPanel);
			dialog.dispose();

		}
	}
}