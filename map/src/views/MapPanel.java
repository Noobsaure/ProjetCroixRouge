package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import launcher.Launcher;
import observer.Observer;
import views.listeners.EditLocationButtonListener;
import views.listeners.MapPanelMouseListener;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;


public class MapPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	private OperationController _operation;

	private BufferedImage _map;
	private GlobalPanel _globalPanel;
	private SubMenuPanel _openedPanel;

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
		setLayout(null);
		_globalPanel = globalPanel;
		setBackground(Color.LIGHT_GRAY);
	}

	public void addMapPanelListener()
	{
		_mapListener = new MapPanelMouseListener(_globalPanel);
		addMouseListener(_mapListener);
		addMouseMotionListener(_mapListener);
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
		for(Location oneLoc : _locations) {
			oneLoc.moveLocation(x,y);
		}
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
			int x = (int) oneLoc.getX();
			int y = (int) oneLoc.getY();
			Location location = new Location(_globalPanel, oneLoc);
			LocationPanel locPanel = new LocationPanel(location, this, x, y);
			locPanel.addIconMouseListener(new EditLocationButtonListener(_operation,locPanel,_globalPanel.getMapPanel(),oneLoc));
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
		for(Location oneLoc : _locations) {
			oneLoc.setOffsetX(0);
			oneLoc.setOffsetY(0);
			_x = 0;
			_y = 0;
		}
	}

	public synchronized void disableLocationHighlight() {
		for(Location oneLoc : _locations) {
			oneLoc.displayPanel(false);
			oneLoc.setHighlight(false);
		}
	}

	public void openPanel(SubMenuPanel panel) {
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

	public synchronized void update()
	{
		Launcher launcher = _globalPanel.getLauncher();
		OperationController controller = launcher.getOperationController();
		MapController mapController = controller.getCurrentMap();
		if(mapController != null) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();
			double ratio = 1;
			ImageIcon image = mapController.getImage();

			if(image.getIconHeight() > height && image.getIconWidth() > width) {
				if(image.getIconHeight() > image.getIconWidth()) {
					ratio = width/image.getIconWidth();
				} else {
					ratio = height/image.getIconHeight();
				}
			} else if(image.getIconHeight() > height) {
				ratio = height/image.getIconHeight();
				if(image.getIconWidth() < getWidth()) {
					_x = (int) ((getWidth() - image.getIconWidth()) / 2);
				}
			} else if(image.getIconWidth() > width) {
				ratio = width/image.getIconWidth();
				if(image.getIconHeight() < getHeight()) {
					_y = (int) ((getHeight() - image.getIconHeight()) / 2);
				}
			} else {
				if(image.getIconWidth() < getWidth()) {
					_x = (int) ((getWidth() - image.getIconWidth()) / 2);
				}
				if(image.getIconHeight() < getHeight()) {
					_y = (int) ((getHeight() - image.getIconHeight()) / 2);
				}
			}

			_map = new BufferedImage((int)(image.getIconWidth() * ratio), (int)(image.getIconHeight()*ratio), BufferedImage.TYPE_INT_RGB);
			_map.getGraphics().drawImage(image.getImage(), 0, 0, (int)(image.getIconWidth()*ratio), (int)(image.getIconHeight()*ratio), null);
		} else {
			_map = null;
		}
		updateLocations();
		repaint();
		revalidate();
	}
}