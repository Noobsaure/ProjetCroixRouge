package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import launcher.Launcher;
import views.AddEntityPanel;
import views.GlobalPanel;
import views.MapPanel;
import views.CustomDialog;

public class AddEntityButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;

	public AddEntityButtonListener(MapPanel mapPanel) {
		_mapPanel = mapPanel;
		_globalPanel = mapPanel.getGlobalPanel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Launcher launcher = _globalPanel.getLauncher();
		AddEntityPanel addEntityPanel = new AddEntityPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager());		
		new CustomDialog(addEntityPanel, _globalPanel);
	}
}












