package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AddOrEditLocationPanel;
import views.CustomPanelImpl;

public class LocationChooseColor implements ActionListener {

	private AddOrEditLocationPanel _configurationLocationPanel;
	private int _color;
	
	public LocationChooseColor(AddOrEditLocationPanel panel, int color){
		_configurationLocationPanel = panel;
		_color = color;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ON CHOISIT LA COULEUR :"+_color);
		_configurationLocationPanel.setColor(_color);
	}

}
