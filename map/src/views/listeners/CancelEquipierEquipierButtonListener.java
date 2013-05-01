package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.AddEquipierPanel;
import views.ConfigurationEntityPanel;
import views.MapPanel;
import views.MyJDialog;
import controllers.EntityController;
import controllers.OperationController;

public class CancelEquipierEquipierButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private AddEquipierPanel _addEquipierPanel;
	private OperationController _operationController;
	private EntityController _entityController;
	
	public CancelEquipierEquipierButtonListener(MapPanel mapPanel, OperationController operationController, EntityController entityController,  AddEquipierPanel addEquipierPanel)
	{
		_mapPanel = mapPanel;
		_addEquipierPanel = addEquipierPanel;
		_operationController = operationController;
		_entityController = entityController;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_addEquipierPanel);
		dialog.dispose();
	}
}
