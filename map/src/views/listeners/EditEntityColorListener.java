package views.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.ConfigurationEntityPanel;
import controllers.OperationController;
import database.DatabaseManager;

public class EditEntityColorListener implements ActionListener {

	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private ConfigurationEntityPanel _configurationEntityPanel;
	
	public EditEntityColorListener (JPanel parent, OperationController operationController, DatabaseManager databaseManager, ConfigurationEntityPanel configurationEntityPanel) {
		_parent = parent;
		_operationController = operationController;
		_databaseManager = databaseManager;
		_configurationEntityPanel = configurationEntityPanel;
	}
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		Color colorChosen = _configurationEntityPanel.getColor();
		String color = "#" + Integer.toHexString(colorChosen.getRGB()).substring(2, 8);
		
		
		
		
	}

}
