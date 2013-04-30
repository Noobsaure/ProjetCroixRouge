package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import launcher.Launcher;

import views.ConfigurationLocationPanel;
import views.Location;
import views.LocationPanel;
import views.MapPanel;
import controllers.OperationController;
import controllers.LocationController;

public class EditLocationButtonListener implements MouseListener
{
	private OperationController _operationController;
	private LocationController _locationController;
	private LocationPanel _locationPanel;
	private Location _loc;
	private MapPanel _mapPanel;
	private boolean _enabled = true;

	public EditLocationButtonListener(OperationController operationController, LocationPanel locationPanel, MapPanel mapPanel, LocationController locationController)
	{
		_operationController = operationController;
		_locationPanel = locationPanel;
		_loc = _locationPanel.getLoc();
		_mapPanel = mapPanel;
		_locationController = locationController;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		_loc.displayPanel(false);
		_loc.setHighlight(false);

		_mapPanel.removeMapPanelListener();

		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();

		ConfigurationLocationPanel configurationLocationPanel = new ConfigurationLocationPanel(_mapPanel,
				_operationController,launcher.getDatabaseManager(),_locationController.getName(),
				_locationController.getDescription(),_locationController);	
		_mapPanel.add(configurationLocationPanel);
		_mapPanel.setCurrentPopUp(configurationLocationPanel);
		_mapPanel.setComponentZOrder(configurationLocationPanel, 0);
		_mapPanel.repaint();
		_mapPanel.revalidate();	
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		_locationPanel.setIconState(true);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		_locationPanel.setIconState(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public void enable(boolean enable) {
		_enabled = enable;
	}
}
