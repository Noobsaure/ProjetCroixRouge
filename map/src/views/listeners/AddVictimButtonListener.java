package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import launcher.Launcher;
import views.AddVictimPanel;
import views.CustomDialog;
import views.MapPanel;

public class AddVictimButtonListener implements ActionListener
{
	private MapPanel _mapPanel;

	public AddVictimButtonListener(MapPanel mapPanel)
	{
		_mapPanel = mapPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		AddVictimPanel addVictimPanel = new AddVictimPanel(_mapPanel, launcher.getOperationController());		
		addVictimPanel.addCancelButtonListener(new CancelAddVictimListener(_mapPanel, addVictimPanel));
		addVictimPanel.addOkButtonListener(new ConfirmAddVictimListener(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager(), addVictimPanel));
		new CustomDialog(addVictimPanel, _mapPanel.getGlobalPanel());
	}
}
