package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import observer.Observer;
import views.buttons.SubMenuMapButton;
import views.listeners.AddMapButtonListener;
import views.listeners.HideMapListener;
import views.listeners.SubMenuMapToggleButtonListener;
import views.listeners.SwitchMapButtonListener;
import controllers.MapController;
import controllers.OperationController;
import database.DatabaseManager;

public class SubMenuMapPanel extends SubMenuPanel implements Observer
{
	private static final long serialVersionUID = 1L;
	
	private Map<JToggleButton, MapController> _map;
	private JPanel _thumbnailsPanel;
	private JScrollPane _scrollPane;
	private MapPanel _mapPanel;
	private OperationController _operationController;
	private SubMenuMapButton _button;
	private List<MapController> _listMapsName;
	private ButtonGroup _group = new ButtonGroup();
	
	public SubMenuMapPanel(MapPanel mapPanel, SubMenuMapButton button, OperationController operationController, DatabaseManager databaseManager)
	{
		super(mapPanel, operationController, databaseManager);
		_mapPanel = mapPanel;
		_map = super.getMapMap();
		_thumbnailsPanel = super.getThumbnailPanel();
		_operationController = operationController;
		_button = button;
		displayThumbnail();

		_scrollPane = new JScrollPane(_thumbnailsPanel);
		_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		_scrollPane.setViewportBorder(null);
		add(_scrollPane, BorderLayout.CENTER);
		
		addAddButtonListener(new AddMapButtonListener(mapPanel,this));
		addOkButtonListener(new SwitchMapButtonListener(mapPanel, button, this, operationController));
		
		_operationController.addObserver(this);
	}

	@Override
	public void displayThumbnail()
	{		
		_thumbnailsPanel.setBackground(SubMenuMapPanel.COLOR_BACKGROUND);
		_thumbnailsPanel.setLayout(new BoxLayout(_thumbnailsPanel, BoxLayout.PAGE_AXIS));
		
		_listMapsName = _operationController.getMapList();
				
		for(int i = 0; i < _listMapsName.size(); i++)
		{
			int currentMapId = _operationController.getCurrentMap().getId();
		
			final int THUMBNAIL_WIDTH = SubMenuPanel.WIDTH - 30;
			final int THUMBNAIL_HEIGHT = (int)(0.6 * THUMBNAIL_WIDTH);
			
			int id = _listMapsName.get(i).getId();
			ImageIcon icon = _listMapsName.get(i).getImage();
			Image imageScaled = icon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
			ImageIcon iconScaled = new ImageIcon(imageScaled);
			
			JToggleButton toggleButton = new JToggleButton();
			toggleButton.setMaximumSize(new Dimension(THUMBNAIL_WIDTH + 10, THUMBNAIL_HEIGHT + 10));
			toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toggleButton.setIcon(iconScaled);
			_group.add(toggleButton);
			toggleButton.addActionListener(new SubMenuMapToggleButtonListener(_listMapsName.get(i), _mapPanel, _operationController));
			_thumbnailsPanel.add(toggleButton);
			
			System.out.println("i : " + i + " id : " + id + ", Name image : " + _listMapsName.get(i).getName());
			
			JLabel nameLabel = new JLabel(_listMapsName.get(i).getName());
			nameLabel.setForeground(Color.WHITE);
			
			ImageIcon iconDelete = new ImageIcon(EntityPanel.class.getResource("/ui/delete.png"));
			Image imageDeleteScaled = iconDelete.getImage().getScaledInstance(12, 16, Image.SCALE_DEFAULT);
			ImageIcon iconDeleteScaled = new ImageIcon(imageDeleteScaled);
			
			JLabel deleteIcon = new JLabel();
			deleteIcon.setIcon(iconDeleteScaled);
			deleteIcon.addMouseListener(new HideMapListener(_operationController, this, _button, _listMapsName.get(i)));
			
			JPanel panelLabel = new JPanel();
			panelLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
			panelLabel.setLayout(new BorderLayout());
			panelLabel.setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			panelLabel.setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			panelLabel.setBackground(COLOR_BACKGROUND);
			panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelLabel.add(nameLabel, BorderLayout.CENTER);
			panelLabel.add(deleteIcon, BorderLayout.EAST);
			_thumbnailsPanel.add(panelLabel);
			
			if(id == currentMapId)
				toggleButton.setSelected(true);
			
			_map.put(toggleButton, _listMapsName.get(i));
		}
	}

	@Override
	public synchronized void update()
	{
		List<MapController> mapList = _operationController.getMapList();
		
		for(MapController map : mapList){
			if(!_listMapsName.contains(map)){
				_listMapsName.add(map);
				int currentMapId = _operationController.getCurrentMap().getId();
				
				final int THUMBNAIL_WIDTH = SubMenuPanel.WIDTH - 30;
				final int THUMBNAIL_HEIGHT = (int)(0.6 * THUMBNAIL_WIDTH);
				
				int id = map.getId();
				ImageIcon icon = map.getImage();
				Image imageScaled = icon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
				ImageIcon iconScaled = new ImageIcon(imageScaled);
				
				JToggleButton toggleButton = new JToggleButton();
				toggleButton.setMaximumSize(new Dimension(THUMBNAIL_WIDTH + 10, THUMBNAIL_HEIGHT + 10));
				toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				toggleButton.setIcon(iconScaled);
				_group.add(toggleButton);
				toggleButton.addActionListener(new SubMenuMapToggleButtonListener(map, _mapPanel, _operationController));
				_thumbnailsPanel.add(toggleButton);
				
				System.out.println(" id : " + id + ", Name image : " + map.getName());
				
				JLabel nameLabel = new JLabel(map.getName());
				nameLabel.setForeground(Color.WHITE);
				
				ImageIcon iconDelete = new ImageIcon(EntityPanel.class.getResource("/ui/delete.png"));
				Image imageDeleteScaled = iconDelete.getImage().getScaledInstance(12, 16, Image.SCALE_DEFAULT);
				ImageIcon iconDeleteScaled = new ImageIcon(imageDeleteScaled);
				
				JLabel deleteIcon = new JLabel();
				deleteIcon.setIcon(iconDeleteScaled);
				deleteIcon.addMouseListener(new HideMapListener(_operationController, this, _button,map));
				
				JPanel panelLabel = new JPanel();
				panelLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
				panelLabel.setLayout(new BorderLayout());
				panelLabel.setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
				panelLabel.setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
				panelLabel.setBackground(COLOR_BACKGROUND);
				panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				panelLabel.add(nameLabel, BorderLayout.CENTER);
				panelLabel.add(deleteIcon, BorderLayout.EAST);
				_thumbnailsPanel.add(panelLabel);
				
				if(id == currentMapId)
					toggleButton.setSelected(true);
				
				_map.put(toggleButton, map);
			}
		}
		_thumbnailsPanel.repaint();
		_thumbnailsPanel.revalidate();
	}	
}
