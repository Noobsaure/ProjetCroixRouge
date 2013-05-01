package views.listeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import launcher.Launcher;

import views.buttons.SubMenuMapButton;
import views.MapPanel;
import views.SubMenuPanel;
import controllers.MapController;
import controllers.OperationController;

public class SwitchMapButtonListener implements ActionListener
{
	private SubMenuMapButton _button;
	private MapPanel _mapPanel;
	private SubMenuPanel _subMenuPanel;
	private JPanel _thumbnailPanel;
	private OperationController _operationController;

	public SwitchMapButtonListener(MapPanel mapPanel, SubMenuMapButton button, SubMenuPanel subMenuPanel, OperationController operationController)
	{
		_button = button;
		_mapPanel = mapPanel;
		_subMenuPanel = subMenuPanel;
		_thumbnailPanel = subMenuPanel.getThumbnailPanel();
		_operationController = operationController;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JToggleButton buttonSelected = null;

		for(int i = 0; i < _thumbnailPanel.getComponentCount(); i += 2)
		{
			JToggleButton toggleButton = (JToggleButton)_thumbnailPanel.getComponent(i);
			if(toggleButton.isSelected())
				buttonSelected = toggleButton;
		}

		Map<JToggleButton, MapController> map = _subMenuPanel.getMapMap();
		MapController mapController = map.get(buttonSelected);

		if(mapController != null) {
			_operationController.setCurrentMap(mapController);
			_mapPanel.resetLocationOffsets();
		}
		
		_mapPanel.remove(_subMenuPanel);
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}

}
