package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import views.ConfirmDelMapPanel;
import views.MapPanel;
import views.SubMenuMapPanel;
import views.buttons.SubMenuMapButton;
import controllers.EntityController;
import controllers.MapController;
import controllers.OperationController;

public class ConfirmDelMapListener implements ActionListener {
	private OperationController _operation;
	private SubMenuMapButton _button;
	private ConfirmDelMapPanel _confirmDelMapPanel;
	private MapController _map;
	
	public ConfirmDelMapListener(OperationController operation, SubMenuMapButton button, ConfirmDelMapPanel confirmDelMapPanel, MapController map) {
		_operation = operation;
		_button = button;
		_confirmDelMapPanel = confirmDelMapPanel;
		_map = map;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		MapPanel mapPanel = _operation.getGlobalPanel().getMapPanel();
		mapPanel.remove(_confirmDelMapPanel);
		mapPanel.repaint();
		mapPanel.revalidate();
		
		List<EntityController> _entityList = _map.getEntityListInThisMap();
		
		for(EntityController entity : _entityList){
			entity.setLocation(_operation.getLocation(_operation.getIdPcm()));
		}
		
		_map.hideMap();
		
		SubMenuMapPanel subMenu = new SubMenuMapPanel(mapPanel, _button, _operation, _operation.getGlobalPanel().getLauncher().getDatabaseManager());
		mapPanel.add(subMenu);
	}

}
