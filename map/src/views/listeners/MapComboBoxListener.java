package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import views.MapPanel;
import views.MenuButtonsPanel;
import controllers.MapController;
import controllers.OperationController;

public class MapComboBoxListener //implements ActionListener
{
//	private MapPanel _mapPanel;
//	private MenuButtonsPanel _menuButtonPanel;
//	
//	public MapComboBoxListener(MapPanel mapPanel, MenuButtonsPanel menuButtonPanel)
//	{
//		_mapPanel = mapPanel;
//		_menuButtonPanel = menuButtonPanel;
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent arg0)
//	{
//		OperationController operationController = _mapPanel.getGlobalPanel().getLauncher().getOperationController();
//		
//		String mapName = (String)_menuButtonPanel.getListMaps().getSelectedItem();
//		Map<String, MapController> map = _menuButtonPanel.getMap();
//		
//		MapController mapController = map.get(mapName);
//		
//		operationController.setCurrentMap(mapController);
//	}
}
