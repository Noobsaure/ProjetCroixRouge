package views.listeners;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import views.GlobalPanel;
import views.MapPanel;
import views.MenuPanel;
import views.Location;
import views.buttons.ToggleMenuPanelButton;

public class ToggleMenuPanelTimerListener implements ActionListener {

	private GlobalPanel _gPanel;
	private MenuPanel _menu;
	private MapPanel _map;
	private ToggleMenuPanelButton _button;
	private boolean _hiding;
	private int _nbIterations = 0;
	private int _nW;

	private final int SLIDING_SPEED = 20;
	private final int MAX_ITERATIONS = 10;


	public ToggleMenuPanelTimerListener(GlobalPanel gPanel, MapPanel map, ToggleMenuPanelButton button) {
		_gPanel = gPanel;
		_menu = _gPanel.getMenu();
		_map = map;
		_button = button;
		_hiding = !_button.isHidden();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_nbIterations++;
		
		if(_hiding) {_nW = 200 - SLIDING_SPEED * _nbIterations;}
		else {_nW = SLIDING_SPEED * _nbIterations;}

		_menu.setPreferredSize(new Dimension(_nW,_gPanel.getHeight()));
		_menu.setMinimumSize(new Dimension(_nW,_gPanel.getHeight()));
		_map.setPreferredSize(new Dimension(_gPanel.getWidth() - _nW,_gPanel.getHeight()));
		for(Location loc : _map.getLocations()) {
			//loc.update_x(_nW);
		}

		_gPanel.getContentPane().revalidate();
		if(_nbIterations == MAX_ITERATIONS) {
			((Timer)e.getSource()).stop();
			_button.setHidden(_hiding);
			_button.setEnabled(true);
		}
	}


}
