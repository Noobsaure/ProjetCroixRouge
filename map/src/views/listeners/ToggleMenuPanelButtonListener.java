package views.listeners;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import views.GlobalPanel;
import views.Location;
import views.MapPanel;
import views.MenuPanel;
import views.buttons.ToggleMenuPanelButton;

public class ToggleMenuPanelButtonListener implements ActionListener{

	private GlobalPanel _gPanel;
	private MapPanel _map;
	private MenuPanel _menu;
	private ToggleMenuPanelButton _button;
	private Timer _timer;
	private int _nW;

	public ToggleMenuPanelButtonListener(GlobalPanel gPanel, MapPanel map, ToggleMenuPanelButton button)
	{
		_gPanel = gPanel;
		_map = map;
		_menu = _gPanel.getMenu();
		_button = button;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		/*_button.setEnabled(false);
		_timer = new Timer(25, new ToggleMenuPanelTimerListener(_gPanel,_map,_button));
		_timer.start();*/
		
		if(!_button.isHidden()) {_nW = 0;}
		else {_nW = 200;}
		
		_menu.setPreferredSize(new Dimension(_nW,_gPanel.getHeight()));
		_menu.setMinimumSize(new Dimension(_nW,_gPanel.getHeight()));
		_map.setPreferredSize(new Dimension(_gPanel.getWidth() - _nW,_gPanel.getHeight()));
		for(Location loc : _map.getLocations()) {
			//loc.update_x(_nW);
		}
		
		_gPanel.getContentPane().revalidate();
		_button.setHidden(!_button.isHidden());
	}

}
