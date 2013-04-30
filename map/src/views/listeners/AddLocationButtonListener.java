package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import launcher.Launcher;
import views.AddEntityPanel;
import views.AddLocationPanel;
import views.MapPanel;

public class AddLocationButtonListener extends AbstractObserverListener implements ActionListener{

	private MapPanel _mapPanel;

	public AddLocationButtonListener(MapPanel mapPanel)
	{
		super(mapPanel);
		_mapPanel = mapPanel;
	}


	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		if(isEnabled()) {
			_mapPanel.getMapListener().setAddingLocation(true);
		}
	}

}
