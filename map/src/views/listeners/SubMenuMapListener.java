package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.MapPanel;
import views.SubMenuMapPanel;
import controllers.OperationController;
import database.DatabaseManager;

public class SubMenuMapListener implements ActionListener
{
	private MapPanel _parent;
	
	public SubMenuMapListener(MapPanel mapPanel)
	{
		_parent = mapPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		OperationController operationController = _parent.getGlobalPanel().getLauncher().getOperationController();
		DatabaseManager databaseManager = _parent.getGlobalPanel().getLauncher().getDatabaseManager();
		
		SubMenuMapPanel subMenu = new SubMenuMapPanel(_parent, operationController, databaseManager);
		
		_parent.add(subMenu);
		_parent.setComponentZOrder(subMenu, 0);
		_parent.repaint();
		_parent.revalidate();
	}
	
	
}
