package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import launcher.Launcher;
import views.EditVictimPanel;
import views.MapPanel;

public class EditVictimButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	
	public EditVictimButtonListener(MapPanel mapPanel)
	{
		_mapPanel = mapPanel;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{		
		_mapPanel.removeMapPanelListener();
		
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		
		// évidemment il faudra remplacer 1 par l'id de la victime sélectionnée
		EditVictimPanel editVictimPanel = new EditVictimPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager(), 1);		
		_mapPanel.add(editVictimPanel);		
		_mapPanel.setComponentZOrder(editVictimPanel, 0);
		
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}
}
