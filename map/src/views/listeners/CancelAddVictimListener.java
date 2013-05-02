package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import launcher.Launcher;
import views.AddVictimPanel;
import views.CustomDialog;
import views.MapPanel;
import views.SubMenuVictimPanel;

public class CancelAddVictimListener implements ActionListener
{
	private MapPanel _mapPanel;
	private AddVictimPanel _addVictimPanel;
	
	public CancelAddVictimListener(MapPanel parent, AddVictimPanel addVictimPanel)
	{
		_mapPanel = parent;
		_addVictimPanel = addVictimPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		new SubMenuVictimPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager());
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addVictimPanel);
		dialog.dispose();
	}
}
