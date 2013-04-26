package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.MapPanel;
import views.SubMenuPanel;
import views.SubMenuVictimPanel;
import controllers.OperationController;
import database.DatabaseManager;

public class SubMenuVictimListener implements ActionListener
{
	private MapPanel _parent;
	
	public SubMenuVictimListener(MapPanel mapPanel)
	{
		_parent = mapPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		OperationController operationController = _parent.getGlobalPanel().getLauncher().getOperationController();
		DatabaseManager databaseManager = _parent.getGlobalPanel().getLauncher().getDatabaseManager();
		
		SubMenuVictimPanel subMenu = new SubMenuVictimPanel(_parent, operationController, databaseManager);
		_parent.add(subMenu);
		_parent.setComponentZOrder(subMenu, 0);
		
		_parent.repaint();
		_parent.revalidate();
	}
	
	
}
