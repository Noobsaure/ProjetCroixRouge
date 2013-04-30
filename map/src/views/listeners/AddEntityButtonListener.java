package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import observer.Observer;

import launcher.Launcher;
import views.AddEntityPanel;
import views.MapPanel;

public class AddEntityButtonListener extends AbstractListener implements ActionListener
{
	private MapPanel _mapPanel;

	public AddEntityButtonListener(MapPanel mapPanel)
	{
		super(mapPanel);
		_mapPanel = mapPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(isEnabled()) {
			_mapPanel.getGlobalPanel().enableListeners(false);

			Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();

			AddEntityPanel addEntityPanel = new AddEntityPanel(_mapPanel, launcher.getOperationController(), launcher.getDatabaseManager());		
			_mapPanel.add(addEntityPanel);		
			_mapPanel.setComponentZOrder(addEntityPanel, 0);

			_mapPanel.repaint();
			_mapPanel.revalidate();
		}
	}
}












