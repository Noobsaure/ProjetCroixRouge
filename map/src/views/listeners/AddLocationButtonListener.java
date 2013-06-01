package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.MapPanel;
import views.buttons.AddLocationButton;

public class AddLocationButtonListener implements ActionListener{

	private MapPanel _mapPanel;
	private AddLocationButton _addButton;

	public AddLocationButtonListener(MapPanel mapPanel, AddLocationButton addButton)
	{
		_mapPanel = mapPanel;
		_addButton = addButton;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		_mapPanel.getMapListener().setAddingLocation(true, _addButton);
	}
}
