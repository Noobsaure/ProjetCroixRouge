package views.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.ConfigurationEntityPanel;
import views.CustomDialog;
import views.MapPanel;
import views.MessagePanel;
import controllers.EntityController;
import controllers.LocationController;

public class ValiderConfigEntityListener implements ActionListener {

	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner le champ \"Nom\".";

	private MapPanel _mapPanel;
	private ConfigurationEntityPanel _configurationEntityPanel;
	private EntityController _entityController;
	
	public ValiderConfigEntityListener (MapPanel mapPanel, ConfigurationEntityPanel configurationEntityPanel, EntityController entityController) {
		_mapPanel = mapPanel;
		_configurationEntityPanel = configurationEntityPanel;
		_entityController = entityController;
	}
	
	public boolean checkName(String name)
	{
		return (!name.equals(""));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// modification du nom de l'entité
		String nomEntity = _configurationEntityPanel.getNewName();
		if(!checkName(nomEntity)&&(!nomEntity.equals(_entityController.getName()))) {
			MessagePanel errorPanel = new MessagePanel("Saisie incomplète", EMPTY_NAME_MESSAGE);
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		} else {
			_entityController.setName(nomEntity);
		}			
		
		// modification de la localisation
		LocationController selectedLocation = _configurationEntityPanel.getSelectedLocation();
		_entityController.setLocation(selectedLocation);
		
		// modification du statut
		boolean boolStatut=_configurationEntityPanel.getStatutDispo();
		String textInfos = _configurationEntityPanel.getInformations();
		if(_entityController.getStatut() != boolStatut || !_entityController.getInformationsStatut().equals(textInfos)) {
			_entityController.setAvailable(boolStatut, textInfos);
		}
		
		// modification de la couleur
		Color colorChosen = _configurationEntityPanel.getColor();
		System.out.println("couleur : "+colorChosen);
		String color = "#" + Integer.toHexString(colorChosen.getRGB()).substring(2, 8);
		_entityController.setColor(color);
		
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_configurationEntityPanel);
		dialog.dispose();
	}
}
