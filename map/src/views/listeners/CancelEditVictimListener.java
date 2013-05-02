package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import launcher.Launcher;
import views.CustomDialog;
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
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		new SubMenuVictimPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager());
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_editVictimPanel);
		dialog.dispose();
	}
}
