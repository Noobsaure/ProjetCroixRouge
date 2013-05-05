package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfigurationEntityPanel;
import controllers.EntityController;
import controllers.LocationController;

public class EditEntityLocationButtonListener implements ActionListener {

	private EntityController _entity;
	private ConfigurationEntityPanel _configPanel;

	public EditEntityLocationButtonListener(EntityController entity, ConfigurationEntityPanel configPanel) {
		_entity = entity;
		_configPanel=configPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocationController selectedLocation = _configPanel.getSelectedLocation();
		_entity.setLocation(selectedLocation);
	}
}