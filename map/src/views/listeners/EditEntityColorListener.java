package views.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.ConfigurationEntityPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;
import database.DatabaseManager;

public class EditEntityColorListener implements ActionListener {

	private MapPanel _mapPanel;
	private OperationController _operationController;
	private ConfigurationEntityPanel _configurationEntityPanel;
	private EntityController _entityController;
	
	public EditEntityColorListener (MapPanel mapPanel, OperationController operationController, ConfigurationEntityPanel configurationEntityPanel, EntityController entityController) {
		_mapPanel = mapPanel;
		_operationController = operationController;
		_configurationEntityPanel = configurationEntityPanel;
		_entityController = entityController;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		Color colorChosen = _configurationEntityPanel.getColor();
		System.out.println("couleur : "+colorChosen);
		String color = "#" + Integer.toHexString(colorChosen.getRGB()).substring(2, 8);

		_entityController.setColor(color);
		_mapPanel.setCurrentPopUp(null);
		_mapPanel.repaint();
		
		
	}

}
