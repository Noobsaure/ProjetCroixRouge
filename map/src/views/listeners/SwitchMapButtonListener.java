package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import views.MapPanel;
import views.SubMenuPanelImpl;
import views.ThumbnailMapPanel;
import controllers.MapController;
import controllers.OperationController;

public class SwitchMapButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private SubMenuPanelImpl _subMenuPanel;
	private JPanel _thumbnailPanel;
	private OperationController _operationController;

	public SwitchMapButtonListener(MapPanel mapPanel, SubMenuPanelImpl subMenuPanel, OperationController operationController)
	{
		_mapPanel = mapPanel;
		_subMenuPanel = subMenuPanel;
		_thumbnailPanel = subMenuPanel.getThumbnailPanel();
		_operationController = operationController;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JToggleButton buttonSelected = null;
		for(int i = 0; i < _thumbnailPanel.getComponentCount(); i++)
		{
			ThumbnailMapPanel thumbnail = (ThumbnailMapPanel)_thumbnailPanel.getComponent(i);
			if(thumbnail.getToggleButton().isSelected()) {
				buttonSelected = thumbnail.getToggleButton();
			}
		}

		Map<JToggleButton, MapController> map = _subMenuPanel.getMapMap();
		MapController mapController = map.get(buttonSelected);

		if(mapController != null) {
			_operationController.setCurrentMap(mapController);
		}
		
		_mapPanel.closePanel();
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}

}
