package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import controllers.EntityController;
import controllers.LocationController;
import controllers.OperationController;

public class ConfirmDelLocationListener implements ActionListener {
	private OperationController _operation;
	private LocationController _location;
	
	public ConfirmDelLocationListener(OperationController operation, LocationController location) {
		_operation = operation;
		_location = location;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{		
		List<EntityController> _entityList = _location.getEntityList();
		
		for(EntityController entity : _entityList){
			entity.setLocation(_operation.getPcmLocation());
		}
		
		_location.hideLocation();
	}

}