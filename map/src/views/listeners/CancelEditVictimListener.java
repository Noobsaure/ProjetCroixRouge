package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import launcher.Launcher;

import views.EditVictimPanel;
import views.MapPanel;
import views.SubMenuVictimPanel;

public class CancelEditVictimListener implements ActionListener
{
	private MapPanel _mapPanel;
	private EditVictimPanel _editVictimPanel;
	
	public CancelEditVictimListener(MapPanel mapPanel, EditVictimPanel editVictimPanel)
	{
		_mapPanel = mapPanel;
		_editVictimPanel = editVictimPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		_mapPanel.addMapPanelListener();
		_mapPanel.remove(_editVictimPanel);
		
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		
		SubMenuVictimPanel subMenu = new SubMenuVictimPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager());
		_mapPanel.add(subMenu);
		_mapPanel.setComponentZOrder(subMenu, 0);
		
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}
}
