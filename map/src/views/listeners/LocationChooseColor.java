package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfigurationLocationPanel;

public class LocationChooseColor implements ActionListener {

	private ConfigurationLocationPanel _configurationLocationPanel;
	private int _color;
	
	public LocationChooseColor(ConfigurationLocationPanel config, int color){
		_configurationLocationPanel = config;
		_color = color;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		_configurationLocationPanel.setColor(_color);
	}

}
