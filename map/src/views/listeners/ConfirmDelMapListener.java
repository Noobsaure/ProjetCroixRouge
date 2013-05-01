package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingUtilities;

import views.ChoicePanel;
import views.MapPanel;
import views.MyJDialog;
import views.SubMenuMapPanel;
import views.buttons.SubMenuMapButton;
import controllers.EntityController;
import controllers.MapController;
import controllers.OperationController;

public class ConfirmDelMapListener implements ActionListener {
	private OperationController _operation;
	private SubMenuMapButton _button;
	private ChoicePanel _confirmDelMapPanel;
	private MapController _map;
	
	public ConfirmDelMapListener(OperationController operation, SubMenuMapButton button, ChoicePanel confirmDelMapPanel, MapController map) {
		_operation = operation;
		_button = button;
		_confirmDelMapPanel = confirmDelMapPanel;
		_map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_confirmDelMapPanel);
		dialog.dispose();
		
		List<EntityController> _entityList = _map.getEntityListInThisMap();
		
		for(EntityController entity : _entityList){
			entity.setLocation(_operation.getLocation(_operation.getIdPcm()));
		}
		_map.hideMap();
	}

}
