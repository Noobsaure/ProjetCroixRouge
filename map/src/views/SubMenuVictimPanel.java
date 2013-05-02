package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import observer.Observer;
import views.listeners.AddVictimButtonListener;
import views.listeners.EditVictimButtonListener;
import views.listeners.OkVictimButtonListener;
import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;

public class SubMenuVictimPanel extends SubMenuPanel implements Observer
{
	private static final long serialVersionUID = 1L;
	
	private final int THUMBNAIL_WIDTH = SubMenuPanel.WIDTH - 30;
	private final int THUMBNAIL_HEIGHT = (int)(0.6 * THUMBNAIL_WIDTH);
	
	private Map<JToggleButton, VictimController> _map;
	private JPanel _thumbnailsPanel;
	private JScrollPane _scrollPane;
	private MapPanel _mapPanel;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private ButtonGroup _group;
	private List<VictimController> _listVictimsName;
	private List<ThumbnailVictimPanel> _thumbnailsList;
	
	public SubMenuVictimPanel(MapPanel mapPanel, OperationController operationController, DatabaseManager databaseManager)
	{
		super(mapPanel, operationController, databaseManager);
		
		_mapPanel = mapPanel;
		_operationController = operationController;
		_databaseManager = databaseManager;
		
		_map = super.getMapVictim();
		_thumbnailsPanel = super.getThumbnailPanel();
		_thumbnailsPanel.setBackground(SubMenuMapPanel.COLOR_BACKGROUND);
		_thumbnailsPanel.setLayout(new BoxLayout(_thumbnailsPanel, BoxLayout.PAGE_AXIS));
		
		displayThumbnail();

		_scrollPane = new JScrollPane(_thumbnailsPanel);
		_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		_scrollPane.setViewportBorder(null);
		add(_scrollPane, BorderLayout.CENTER);

		addAddButtonListener(new AddVictimButtonListener(mapPanel));
		addOkButtonListener(new OkVictimButtonListener(mapPanel, this));
		
		_operationController.addObserver(this);
	}

	@Override
	public synchronized void displayThumbnail()
	{		
		_listVictimsName = _operationController.getVictimList();
		_thumbnailsList = new ArrayList<ThumbnailVictimPanel>(); 
		_group = new ButtonGroup();
		
		for(VictimController victim : _listVictimsName){
			ThumbnailVictimPanel victimThumbnail = new ThumbnailVictimPanel(_mapPanel, this, victim);
			_thumbnailsPanel.add(victimThumbnail);
			_thumbnailsList.add(victimThumbnail);
		}
	}

	public void setListVictimsContent()
	{
		List<VictimController> listVictims = _operationController.getVictimList();
		
		List<ThumbnailVictimPanel> victimThumbnailsToDelete = new ArrayList<ThumbnailVictimPanel>();
		
		for(ThumbnailVictimPanel oneVictimThumbnail : _thumbnailsList) {
			if(!listVictims.contains(oneVictimThumbnail.getVictimController())) {
				_thumbnailsPanel.remove(oneVictimThumbnail);
				victimThumbnailsToDelete.add(oneVictimThumbnail);
			} else {
				listVictims.remove(oneVictimThumbnail.getVictimController());
			}
		}
		_thumbnailsList.removeAll(victimThumbnailsToDelete);
		
		for(VictimController oneVictim : listVictims) {
			ThumbnailVictimPanel victimThumbnail = new ThumbnailVictimPanel(_mapPanel, this, oneVictim);
			_thumbnailsPanel.add(victimThumbnail);
			_thumbnailsList.add(victimThumbnail);
			System.out.println("TROLOLO");
		}
	}
	
	@Override
	public synchronized void update()
	{
		setListVictimsContent();
		revalidate();
		repaint();
		
	}
}
