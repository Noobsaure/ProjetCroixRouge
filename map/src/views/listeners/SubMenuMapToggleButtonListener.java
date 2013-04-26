package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import views.MapPanel;
import controllers.MapController;
import controllers.OperationController;

public class SubMenuMapToggleButtonListener implements ActionListener {
	
	private OperationController _operationController;
	private MapController _mapController;
	private MapPanel _mapPanel;
	
	public SubMenuMapToggleButtonListener(MapController mapController, MapPanel mapPanel, OperationController operationController) {
		_operationController = operationController;
		_mapController = mapController;
		_mapPanel = mapPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ImageIcon image = _mapController.getImage();
		BufferedImage newMap = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		_mapPanel.setMap(newMap);
		_mapPanel.getMap().getGraphics().drawImage(image.getImage(), 0,	0, image.getIconWidth(), image.getIconHeight(), null);
		_operationController.setCurrentMap(_mapController);
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}

}
