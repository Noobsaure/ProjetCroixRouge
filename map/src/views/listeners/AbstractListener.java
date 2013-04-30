package views.listeners;

import observer.Observer;
import views.MapPanel;

public abstract class AbstractListener implements Observer {

	public AbstractListener(MapPanel mapPanel) {
		mapPanel.addObserver(this);
	}
	
	private boolean _enabled = true;
	
	public boolean isEnabled() {return _enabled;}
	
	@Override
	public void update() {_enabled = !_enabled;}

}
