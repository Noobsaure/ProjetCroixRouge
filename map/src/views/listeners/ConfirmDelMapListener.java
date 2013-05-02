package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingUtilities;

import views.ChoicePanel;
import views.CustomDialog;
import controllers.EntityController;
import controllers.MapController;
import controllers.OperationController;

public class ConfirmDelMapListener implements ActionListener {
	private OperationController _operation;
	private ChoicePanel _confirmDelMapPanel;
	private MapController _map;
	
	public ConfirmDelMapListener(OperationController operation, ChoicePanel confirmDelMapPanel, MapController map) {
		_operation = operation;
		_confirmDelMapPanel = confirmDelMapPanel;
		_map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_confirmDelMapPanel);
		dialog.dispose();
		
		List<EntityController> _entityList = _map.getEntityListInThisMap();
		
		for(EntityController entity : _entityList){
			entity.setLocation(_operation.getLocation(_operation.getIdPcm()));
		}
		_map.hideMap();
	}

}
