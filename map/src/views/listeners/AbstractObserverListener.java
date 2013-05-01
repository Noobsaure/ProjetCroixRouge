package views.listeners;

import observer.Observer;
import views.MapPanel;

public abstract class AbstractObserverListener implements Observer {

	public AbstractObserverListener(MapPanel mapPanel) {
		mapPanel.addObserver(this);
		_enabled = (mapPanel.getCurrentPopUp() == null);
	}
	
	private boolean _enabled;
	
	public boolean isEnabled() {return _enabled;}
	
	@Override
	public void update() {
		_enabled = !_enabled;
	}
}
