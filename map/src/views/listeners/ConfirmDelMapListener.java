package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import controllers.EntityController;
import controllers.MapController;
import controllers.OperationController;

import views.ConfirmDelMapPanel;
import views.ErrorMessage;

public class ConfirmDelMapListener implements ActionListener {
	private OperationController _operation;
	private ConfirmDelMapPanel _confirmDelMapPanel;
	private MapController _map;
	
	public ConfirmDelMapListener(OperationController operation, ConfirmDelMapPanel confirmDelMapPanel, MapController map) {
		_operation = operation;
		_confirmDelMapPanel = confirmDelMapPanel;
		_map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<EntityController> _entityList = _map.getEntityListInThisMap();
		
		for(EntityController entity : _entityList){
			entity.setLocation(_operation.getLocation(_operation.getIdPcm()));
		}
		
		_map.hideMap();
	}

}
