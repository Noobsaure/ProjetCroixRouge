package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import controllers.EntityController;
import controllers.MapController;
import controllers.OperationController;

public class ConfirmDelMapListener implements ActionListener {
	private OperationController _operation;
	private MapController _map;
	
	public ConfirmDelMapListener(OperationController operation, MapController map) {
		_operation = operation;
		_map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		List<EntityController> _entityList = _map.getEntityListInThisMap();
		
		for(EntityController entity : _entityList){
			entity.setLocation(_operation.getLocation(_operation.getIdPcm()));
		}
		_map.hideMap();
	}

}