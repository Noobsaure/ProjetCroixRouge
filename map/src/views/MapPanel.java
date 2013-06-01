package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import launcher.Launcher;
import observer.Observer;
import views.listeners.HideLocationButtonListener;
import views.listeners.EditLocationButtonListener;
import views.listeners.MapPanelMouseListener;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;


public class MapPanel extends JPanel implements Observer, ComponentListener {
	private static final long serialVersionUID = 1L;

	private OperationController _operation;

	private BufferedImage _map;
	private GlobalPanel _globalPanel;
	private SubMenuPanelImpl _openedPanel;
	
	private double _ratio;

	private int _x = 0;
	private int _y = 0;
	private MapPanelMouseListener _mapListener;

	private List<Location> _locations = new ArrayList<Location>();

	public void setOperation(OperationController operation) {
		_operation = operation;
	}

	public MapPanel(GlobalPanel globalPanel)
	{
		super(true);
		_ratio = 1;
		setLayout(null);
		_globalPanel = globalPanel;
		setBackground(Color.BLACK);
	}

	public void addMapPanelListener()
	{
		_mapListener = new MapPanelMouseListener(_globalPanel);
		addMouseListener(_mapListener);
		addMouseMotionListener(_mapListener);
		addMouseWheelListener(_mapListener);
	}

	public List<Location> getLocations() {return _locations;}

	public MapPanelMouseListener getMapListener() {return _mapListener;}

	public int get_x() {return _x;}
	public int get_y() {return _y;}

	public void moveMap(int x, int y) {
		if(_map.getWidth() > getWidth()) {
			if(x > 0 && x + _x > 0) {x = -_x;}
			else if(x < 0 && x + _x + _map.getWidth() < getWidth()) {x = getWidth() - _x - _map.getWidth();}
			_x = _x + x;
		} else {
			x = 0;
		}
		if(_map.getHeight() > getHeight()) {
			if(y > 0 && y + _y > 0) {y = -_y;}
			else if(y < 0 && y + _y + _map.getHeight() < getHeight()) {y = getHeight() - _y - _map.getHeight();}
			_y = _y + y;
		} else {
			y = 0;
		}
		setLocationsMapXY(_x, _y);
		repaint();
	}

	@Override 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(_map != null) {
			g.drawImage(_map, _x, _y, _map.getWidth(), _map.getHeight(), null);
		}
	}

	public void showAddLocationPanel(int x, int y) {
		Launcher launcher = getGlobalPanel().getLauncher();
		AddLocationPanel addLocationPanel = new AddLocationPanel(this, launcher.getOperationController(), launcher.getDatabaseManager(), x, y);
		new CustomDialog(addLocationPanel, _globalPanel);
	}

	public GlobalPanel getGlobalPanel() {return _globalPanel;}
	public BufferedImage getMap() {return _map;}
	public void setMap(BufferedImage map) {_map = map;}

	public void updateLocations() {
		List<LocationController> listLocations = _operation.getMapLocationList();
		List<Location> listLocationsToDelete = new ArrayList<Location>();
		for(Location oneLoc : _locations) {
			if(!listLocations.contains(oneLoc.getLocationController())) {
				remove(oneLoc.getLocPanel());
				remove(oneLoc);
				listLocationsToDelete.add(oneLoc);
			} else {
				listLocations.remove(oneLoc.getLocationController());
			}
		}
		_locations.removeAll(listLocationsToDelete);
		for(LocationController oneLoc : listLocations) {
			int x = (int) (oneLoc.getX() * getRatio());
			int y = (int) (oneLoc.getY() * getRatio());
			Location location = new Location(_globalPanel, oneLoc, x, y);
			LocationPanel locPanel = new LocationPanel(location, this, x, y);
			locPanel.addIconMouseListeners(
					new EditLocationButtonListener(_operation,locPanel,_globalPanel.getMapPanel(),oneLoc),
					new HideLocationButtonListener(_operation,oneLoc));
			location.setLocPanel(locPanel);
			_locations.add(location);
			add(locPanel);
			add(location);
			setComponentZOrder(locPanel, 0);
		}
		for(Location oneLoc : _locations) {
			oneLoc.update();
		}
	}

	public void resetPosition() {
		_x = 0;
		_y = 0;
		centerMap();
	}

	public void setLocationsMapXY(int x, int y) {
		for(Location oneLoc : _locations) {
			oneLoc.setMapXY(x,y, _ratio);
			oneLoc.getLocPanel().setMapXY(x,y,_ratio);
		}
	}

	public synchronized void disableLocationHighlight() {
		for(Location oneLoc : _locations) {
			oneLoc.displayPanel(false);
			oneLoc.setHighlight(false);
		}
	}

	public void openPanel(SubMenuPanelImpl panel) {
		if(_openedPanel != null) {
			remove(_openedPanel);
			_openedPanel = panel;
			add(_openedPanel);
		} else {
			_openedPanel = panel;
			add(_openedPanel);
		}
	}

	public void closePanel() {
		if(_openedPanel != null) {
			remove(_openedPanel);
			_openedPanel = null;
		}
	}
	
	public void centerMap() {
		if(_map != null) {
			if(_map.getWidth() < getWidth()) {
				_x = (int) ((getWidth() - _map.getWidth()) / 2);
			} else {
				_x = Math.min(_x, 0);
			}
			if(_map.getHeight() < getHeight()) {
				_y = (int) ((getHeight() - _map.getHeight()) / 2);
			} else {
				_y = Math.min(_y, 0);
			}
			setLocationsMapXY(_x, _y);
		}
	}

	public void update()
	{
		Launcher launcher = _globalPanel.getLauncher();
		OperationController controller = launcher.getOperationController();
		MapController mapController = controller.getCurrentMap();
		if(mapController != null) {
			ImageIcon image = mapController.getImage();
			_map = new BufferedImage((int)(image.getIconWidth() * _ratio), (int)(image.getIconHeight()*_ratio), BufferedImage.TYPE_INT_RGB);
			_map.getGraphics().drawImage(image.getImage(), 0, 0, (int)(image.getIconWidth()*_ratio), (int)(image.getIconHeight()*_ratio), null);
		} else {
			_map = null;
		}
		if(_openedPanel != null) {
			_openedPanel.update();
		}
		updateLocations();
		centerMap();
		repaint();
		revalidate();
	}
	
	public double getRatio() {return _ratio;}
	public void setRatio(double ratio) {
		_ratio = Math.min(1, Math.max(0.25,ratio));
		update();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		centerMap();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		centerMap();
	}
}