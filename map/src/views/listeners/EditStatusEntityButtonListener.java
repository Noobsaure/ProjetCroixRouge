package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfigurationEntityPanel;
import controllers.EntityController;


public class EditStatusEntityButtonListener implements ActionListener
{
	private EntityController _entity;
	private ConfigurationEntityPanel _configPanel;
	
	public EditStatusEntityButtonListener(EntityController entity, ConfigurationEntityPanel configPanel)
	{
		_entity = entity;
		_configPanel=configPanel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		boolean boolStatut=_configPanel.getStatutDispo();
		String textInfos = _configPanel.getInformations();
		
		_entity.setAvailable(boolStatut, textInfos);
				
	}

	
}

