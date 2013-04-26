package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.MapPanel;
import views.SubMenuMapPanel;
import controllers.OperationController;
import database.DatabaseManager;
import views.buttons.SubMenuMapButton;

public class SubMenuMapListener implements ActionListener
{
	private MapPanel _parent;
	private SubMenuMapButton _button;
	
	public SubMenuMapListener(MapPanel mapPanel, SubMenuMapButton button)
	{
		_parent = mapPanel;
		_button = button;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		OperationController operationController = _parent.getGlobalPanel().getLauncher().getOperationController();
		DatabaseManager databaseManager = _parent.getGlobalPanel().getLauncher().getDatabaseManager();
		
		SubMenuMapPanel subMenu = new SubMenuMapPanel(_parent, _button, operationController, databaseManager);
		
		_button.setEnabled(false);		
		_parent.add(subMenu);
		_parent.setComponentZOrder(subMenu, 0);
		
		_parent.repaint();
		_parent.revalidate();
	}
	
	
}
