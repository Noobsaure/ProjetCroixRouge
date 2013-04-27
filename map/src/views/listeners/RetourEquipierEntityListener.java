package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controllers.EntityController;
import controllers.OperationController;
import views.ConfigurationEntityPanel;
import views.MapPanel;


public class RetourEquipierEntityListener implements ActionListener
{
	private JPanel _parent;
	private ConfigurationEntityPanel _configEntityPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	
	public RetourEquipierEntityListener(JPanel parent, ConfigurationEntityPanel configurationEntityPanel, OperationController operationController, EntityController entityController)
	{
		_parent = parent;
		_configEntityPanel=configurationEntityPanel;
		_operationController = operationController;
		_entityController = entityController;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		// On arret la configuration de l'entité
		
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
	
		_operationController.removeObserver(_configEntityPanel);
		_parent.remove(_configEntityPanel);
		_parent.revalidate();
		_parent.repaint();
	}
}












