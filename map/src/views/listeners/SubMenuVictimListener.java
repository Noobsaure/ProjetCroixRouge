package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import observer.Observer;
import views.MapPanel;
import views.SubMenuVictimPanel;
import views.buttons.SubMenuVictimButton;
import controllers.OperationController;

public class SubMenuVictimListener implements ActionListener
{
	private MapPanel _parent;
	private SubMenuVictimButton _victimButton;
	
	public SubMenuVictimListener(MapPanel mapPanel, SubMenuVictimButton victimButton)
	{
		_parent = mapPanel;
		_victimButton = victimButton;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		OperationController operationController = _parent.getGlobalPanel().getLauncher().getOperationController();
		
		SubMenuVictimPanel subMenu = new SubMenuVictimPanel(_parent, operationController);
		subMenu.addObserver((Observer)_victimButton);
		
		_parent.openPanel(subMenu);
		_parent.setComponentZOrder(subMenu, 0);
		_parent.repaint();
		_parent.revalidate();
	}
	
	
}
