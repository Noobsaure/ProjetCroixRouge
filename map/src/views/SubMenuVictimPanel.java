package views;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import observer.Observer;
import observer.Subject;

import views.listeners.AddVictimButtonListener;
import views.listeners.OkVictimButtonListener;
import controllers.OperationController;
import controllers.VictimController;

public class SubMenuVictimPanel extends SubMenuPanelImpl implements Subject
{
	private static final long serialVersionUID = 1L;
	
	private JPanel _thumbnailsPanel;
	private JScrollPane _scrollPane;
	private MapPanel _mapPanel;
	private OperationController _operationController;
	private List<VictimController> _listVictimsName;
	private List<ThumbnailVictimPanel> _thumbnailsList;
	private List<Observer> _listObserver;
	
	public SubMenuVictimPanel(MapPanel mapPanel, OperationController operationController)
	{
		super(mapPanel);
		
		_mapPanel = mapPanel;
		_operationController = operationController;
		_thumbnailsPanel = super.getThumbnailPanel();
		_thumbnailsPanel.setBackground(SubMenuMapPanel.COLOR_BACKGROUND);
		_thumbnailsPanel.setLayout(new BoxLayout(_thumbnailsPanel, BoxLayout.PAGE_AXIS));
		
		displayThumbnail();

		_scrollPane = new JScrollPane(_thumbnailsPanel);
		_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		_scrollPane.setBorder(null);
		_scrollPane.setViewportBorder(null);
		add(_scrollPane, BorderLayout.CENTER);

		addAddButtonListener(new AddVictimButtonListener(mapPanel));
		addOkButtonListener(new OkVictimButtonListener(mapPanel, this));
		
		_listObserver = new ArrayList<Observer>();
	}

	@Override
	public synchronized void displayThumbnail()
	{		
		_listVictimsName = _operationController.getVictimList();
		_thumbnailsList = new ArrayList<ThumbnailVictimPanel>();
		
		for(VictimController oneVictim : _listVictimsName){
			ThumbnailVictimPanel victimThumbnail = new ThumbnailVictimPanel(_mapPanel, this, oneVictim);
			_thumbnailsPanel.add(victimThumbnail);
			_thumbnailsList.add(victimThumbnail);
		}
	}

	public void setListVictimsContent()
	{
		System.out.println("HELLO");
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
		}
		
		for(ThumbnailVictimPanel thumbnailVictimPanel : _thumbnailsList){
			thumbnailVictimPanel.update();
		}
	}
	
	@Override
	public void update()
	{
		setListVictimsContent();
		revalidate();
		repaint();
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
