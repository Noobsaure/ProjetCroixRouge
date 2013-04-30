package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import views.ConfigurationEntityPanel;
import views.EntityPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;


public class EditStatusEntityButtonListener implements ActionListener
{
	private OperationController _operationController;
	private EntityPanel _entityPanel;
	private MapPanel _mapPanel;
	private EntityController _entity;
	private ConfigurationEntityPanel _configPanel;
	
	public EditStatusEntityButtonListener(OperationController operationController, EntityController entity, ConfigurationEntityPanel configPanel)
	{
		_operationController = operationController;
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

