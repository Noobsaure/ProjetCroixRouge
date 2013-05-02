package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.MapPanel;
import views.SubMenuVictimPanel;

public class OkVictimButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	SubMenuVictimPanel _subMenu;
	
	public OkVictimButtonListener(MapPanel mapPanel, SubMenuVictimPanel subMenu)
	{
		_mapPanel = mapPanel;
		_subMenu = subMenu;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		_mapPanel.closePanel();
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}
}
