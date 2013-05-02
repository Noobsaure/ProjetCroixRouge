package views.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfigurationEntityPanel;
import views.MapPanel;
import controllers.EntityController;

public class EditEntityColorListener implements ActionListener {

	private MapPanel _mapPanel;
	private ConfigurationEntityPanel _configurationEntityPanel;
	private EntityController _entityController;
	
	public EditEntityColorListener (MapPanel mapPanel, ConfigurationEntityPanel configurationEntityPanel, EntityController entityController) {
		_mapPanel = mapPanel;
		_configurationEntityPanel = configurationEntityPanel;
		_entityController = entityController;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Color colorChosen = _configurationEntityPanel.getColor();
		System.out.println("couleur : "+colorChosen);
		String color = "#" + Integer.toHexString(colorChosen.getRGB()).substring(2, 8);

		_entityController.setColor(color);
		_mapPanel.repaint();
	}
}
