package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import launcher.Launcher;
import observer.Observer;
import views.listeners.EditLocationButtonListener;
import views.listeners.MapPanelMouseListener;
import controllers.EntityController;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;


public class MapPanel extends JPanel implements Observer
{
	private static final long serialVersionUID = 1L;

	private OperationController _operation;

	private BufferedImage _map;
	private GlobalPanel _gPanel;
	private int _x = 0;
	private int _y = 0;
	private MapPanelMouseListener _mapListener;

	private List<Location> _locations = new ArrayList<Location>();

	public void setOperation(OperationController operation) {
		_operation = operation;
	}
	
	public MapPanel(GlobalPanel gPanel)
	{
		super(true);
		setLayout(null);
		_gPanel = gPanel;

		setBackground(Color.LIGHT_GRAY);
	}
	
	public void addMapPanelListener()
	{
		_mapListener = new MapPanelMouseListener(_gPanel);
		addMouseListener(_mapListener);
		addMouseMotionListener(_mapListener);
	}

	public void removeMapPanelListener()
	{
		removeMouseListener(_mapListener);
		removeMouseMotionListener(_mapListener);
	}

	public List<Location> getLocations() {return _locations;}

	public MapPanelMouseListener getMapListener() {return _mapListener;}

	public int get_x() {return _x;}
	public int get_y() {return _y;}

	public void moveMap(int x, int y){
		_x = _x + x;
		_y = _y + y;
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

	public void showAddLocationPanel(int x, int y){
		removeMapPanelListener();

		Launcher launcher = getGlobalPanel().getLauncher();

		AddLocationPanel addLocationPanel = new AddLocationPanel(this, launcher.getOperationController(), launcher.getDatabaseManager(), x, y);		
		add(addLocationPanel);		
		setComponentZOrder(addLocationPanel, 0);

		repaint();
		revalidate();
	}

	protected BufferedImage getScaledImage(float ratio) {
		int w = (int)(_map.getWidth() * ratio);
		int h = (int)(_map.getHeight() * ratio);
		BufferedImage img = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.drawImage(_map.getScaledInstance(w, h, Image.SCALE_FAST), 0, 0, w, h, null);
		g.dispose();
		return img;
	}


	public GlobalPanel getGlobalPanel() {return _gPanel;}
	public BufferedImage getMap() {return _map;}
	public void setMap(BufferedImage map) {_map = map;}

	public void updateLocations() {
		List<LocationController> listLocations = _operation.getMapLocationList();
		for(Location oneLoc : _locations) {
			remove(oneLoc.getLocPanel());
			remove(oneLoc);
		}
		_locations.clear();
		for(int i = 0; i < listLocations.size(); i++)
		{
			LocationController locationController = listLocations.get(i);
			int x = (int) locationController.getX();
			int y = (int) locationController.getY();
			Location location = new Location(_gPanel, locationController);
			LocationPanel locPanel = new LocationPanel(location, this, x, y);
			locPanel.addIconMouseListener(new EditLocationButtonListener(_operation,locPanel,_gPanel.getMapPanel(),locationController));
			location.setLocPanel(locPanel);
			_locations.add(location);
			add(locPanel);
		}
		for(Location oneLoc : _locations) {
			oneLoc.update();
			add(oneLoc);
		}
	}
	
	public void enableListeners(boolean enable) {
		_mapListener.enable(enable);
		for(Location oneLoc : _locations) {
			oneLoc.setEnabled(enable);
			oneLoc.getLocPanel().setEnabled(enable);
		}
	}
	
	public synchronized void disableLocationHighlight() {
		for(Location oneLoc : _locations) {
			oneLoc.displayPanel(false);
			oneLoc.setHighlight(false);
		}
	}

	public synchronized void update()
	{
		Launcher launcher = _gPanel.getLauncher();
		OperationController controller = launcher.getOperationController();
		MapController mapController = controller.getCurrentMap();
		if(mapController != null && _map == null) {
			ImageIcon image = mapController.getImage();
			_map = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			_map.getGraphics().drawImage(
					image.getImage(),
					0, 
					0,
					image.getIconWidth(), 
					image.getIconHeight(), 
					null);
		}
		updateLocations();

		repaint();
		revalidate();
	}

}