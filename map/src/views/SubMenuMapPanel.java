package views;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import observer.Observer;
import observer.Subject;

import views.listeners.AddMapButtonListener;
import views.listeners.SwitchMapButtonListener;
import controllers.MapController;
import controllers.OperationController;

public class SubMenuMapPanel extends SubMenuPanelImpl implements Subject{
	private static final long serialVersionUID = 1L;

	private Map<JToggleButton, MapController> _map;
	private JPanel _thumbnailsPanel;
	private JScrollPane _scrollPane;
	private MapPanel _mapPanel;
	private OperationController _operationController;
	private List<MapController> _listMapsName;
	private List<Observer> _listObserver;
	private ButtonGroup _group = new ButtonGroup();

	private List<ThumbnailMapPanel> _thumbnailsList;

	public SubMenuMapPanel(MapPanel mapPanel, OperationController operationController)
	{
		super(mapPanel);
		_mapPanel = mapPanel;
		_map = super.getMapMap();
		_thumbnailsPanel = super.getThumbnailPanel();
		_operationController = operationController;
		displayThumbnail();

		_scrollPane = new JScrollPane(_thumbnailsPanel);
		_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		_scrollPane.setBorder(null);
		_scrollPane.setViewportBorder(null);
		add(_scrollPane, BorderLayout.CENTER);

		addAddButtonListener(new AddMapButtonListener(mapPanel,this));
		addOkButtonListener(new SwitchMapButtonListener(mapPanel, this, operationController));
		
		_listObserver = new ArrayList<Observer>();
	}

	@Override
	public void displayThumbnail()
	{
		_thumbnailsPanel.setBackground(SubMenuMapPanel.COLOR_BACKGROUND);
		_thumbnailsPanel.setLayout(new BoxLayout(_thumbnailsPanel, BoxLayout.PAGE_AXIS));
		_thumbnailsList = new ArrayList<ThumbnailMapPanel>();
		_listMapsName = _operationController.getMapList();

		for(MapController oneMap : _listMapsName)
		{
			ThumbnailMapPanel mapThumbnail = new ThumbnailMapPanel(_mapPanel, this, oneMap, _operationController, _group);
			int currentMapId = _operationController.getCurrentMap().getId();
			if(oneMap.getId() == currentMapId) {
				mapThumbnail.setSelected(true);
			}
			_thumbnailsPanel.add(mapThumbnail);
			_thumbnailsList.add(mapThumbnail);
			_map.put(mapThumbnail.getToggleButton(), oneMap);
		}
	}

	public void setListMapsContent()
	{
		List<MapController> listMaps = _operationController.getMapList();

		List<ThumbnailMapPanel> mapThumbnailsToDelete = new ArrayList<ThumbnailMapPanel>();

		for(ThumbnailMapPanel oneMapThumbnail : _thumbnailsList) {
			if(!listMaps.contains(oneMapThumbnail.getMapController())) {
				_thumbnailsPanel.remove(oneMapThumbnail);
				mapThumbnailsToDelete.add(oneMapThumbnail);
			} else {
				listMaps.remove(oneMapThumbnail.getMapController());
			}
		}
		_thumbnailsList.removeAll(mapThumbnailsToDelete);

		for(MapController oneMap : listMaps) {
			ThumbnailMapPanel mapThumbnail = new ThumbnailMapPanel(_mapPanel, this, oneMap, _operationController, _group);
			int currentMapId = _operationController.getCurrentMap().getId();
			if(oneMap.getId() == currentMapId) {
				mapThumbnail.setSelected(true);
			}
			_map.put(mapThumbnail.getToggleButton(), oneMap);
			_thumbnailsPanel.add(mapThumbnail);
			_thumbnailsList.add(mapThumbnail);
		}
		
		/*for(ThumbnailMapPanel oneThumbnail : _thumbnailsList) {
			oneThumbnail.update();
		}*/
	}

	@Override
	public void update()
	{
		setListMapsContent();
		repaint();
		revalidate();
	}

	@Override
	public void addObserver(Observer observer)
	{
		_listObserver.add(observer);
	}

	@Override
	public void removeObserver(Observer observer)
	{
		_listObserver.remove(_listObserver.lastIndexOf(observer));
		
	}

	@Override
	public void notifyObservers()
	{
		for(Observer observer : _listObserver)
			observer.update();
	}
}
