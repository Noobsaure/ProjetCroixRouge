package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfigurationEntityPanel;
import views.CustomDialog;
import views.MapPanel;
import views.MessagePanel;
import controllers.EntityController;

public class EditEntityNameButtonListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner le champ \"Nom\".";

	private MapPanel _mapPanel;
	private EntityController _entity;
	private ConfigurationEntityPanel _configPanel;

	public EditEntityNameButtonListener(MapPanel mapPanel, EntityController entity, ConfigurationEntityPanel configPanel)
	{
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
			if(_entity.getName().compareTo(nomEntity) != 0) {
				_entity.setName(nomEntity);
			}			
		}
	}
}