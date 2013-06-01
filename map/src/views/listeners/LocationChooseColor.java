package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfigurationLocationPanel;
import views.CustomPanelImpl;

public class LocationChooseColor implements ActionListener {

	private CustomPanelImpl _configurationLocationPanel;
	private int _color;
	
	public LocationChooseColor(CustomPanelImpl panel, int color){
		_configurationLocationPanel = panel;
		_color = color;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		_configurationLocationPanel.setColor(_color);
	}

}
