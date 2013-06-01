package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.MapPanel;
import views.SubMenuMapPanel;
import views.buttons.SubMenuMapButton;
import controllers.OperationController;

public class SubMenuMapListener implements ActionListener
{
	private MapPanel _parent;
	private SubMenuMapButton _mapButton;
	
	public SubMenuMapListener(MapPanel mapPanel, SubMenuMapButton mapButton)
	{
		_parent = mapPanel;
		_mapButton = mapButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		OperationController operationController = _parent.getGlobalPanel().getLauncher().getOperationController();
		
		SubMenuMapPanel subMenu = new SubMenuMapPanel(_parent, operationController);
		subMenu.addObserver(_mapButton);
		
		_parent.openPanel(subMenu);
		_parent.setComponentZOrder(subMenu, 0);
		_parent.repaint();
		_parent.revalidate();
	}
	
	
}
