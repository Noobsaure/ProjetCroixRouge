package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import launcher.Launcher;
import views.AddVictimPanel;
import views.MyJDialog;
import views.SubMenuVictimPanel;
import views.MapPanel;

public class AddVictimButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;

	public AddVictimButtonListener(MapPanel mapPanel, SubMenuVictimPanel subMenu)
	{
		_mapPanel = mapPanel;
		_subMenu = subMenu;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		AddVictimPanel addVictimPanel = new AddVictimPanel(_mapPanel, _subMenu, launcher.getOperationController(), launcher.getDatabaseManager());		
		addVictimPanel.addCancelButtonListener(new CancelAddVictimListener(_mapPanel, _subMenu, addVictimPanel));
		addVictimPanel.addOkButtonListener(new ConfirmAddVictimListener(_mapPanel, _subMenu, launcher.getOperationController(), launcher.getDatabaseManager(), addVictimPanel));
		new MyJDialog(addVictimPanel, _mapPanel.getGlobalPanel());
	}
}
