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

	private JPanel _parent;
	private OperationController _operationController;
	private ConfigurationEntityPanel _configurationEntityPanel;
	private EntityController _entityController;
	
	public EditEntityColorListener (JPanel parent, OperationController operationController, ConfigurationEntityPanel configurationEntityPanel, EntityController entityController) {
		_parent = parent;
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
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		
		_parent.repaint();
		
		
	}

}
