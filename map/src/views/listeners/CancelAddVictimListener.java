package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import launcher.Launcher;

import views.AddVictimPanel;
import views.MapPanel;
import views.SubMenuVictimPanel;

public class CancelAddVictimListener implements ActionListener
{
	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;
	private AddVictimPanel _addVictimPanel;
	
	public CancelAddVictimListener(MapPanel parent, SubMenuVictimPanel subMenu, AddVictimPanel addVictimPanel)
	{
		_mapPanel = parent;
		_subMenu = subMenu;
		_addVictimPanel = addVictimPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		_mapPanel.setCurrentPopUp(null);
		_mapPanel.remove(_addVictimPanel);
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		SubMenuVictimPanel subMenu = new SubMenuVictimPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager());
		_mapPanel.add(subMenu);
		_mapPanel.setComponentZOrder(subMenu, 0);
		_mapPanel.repaint();
	}
}
