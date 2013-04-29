package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import views.listeners.SwitchMapButtonListener;
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

		addAddButtonListener(new AddVictimButtonListener(mapPanel, this));
		addOkButtonListener(null);
	}

	@Override
	public void displayThumbnail()
	{		
		List<VictimController> listVictimsName = _operationController.getVictimList();
		_group = new ButtonGroup();
		
		System.out.println("Nb victimes : " + listVictimsName.size());
		
		for(int i = 0; i < listVictimsName.size(); i++)
		{
			System.out.println("Victime " + i + " " + listVictimsName.get(i).getId() + " " + listVictimsName.get(i).getIdAnonymat());
			System.out.println("I : "+i);
			int id = listVictimsName.get(i)
					.getId();
			VictimController victim = listVictimsName.get(i);
			
			JLabel nameLabel = new JLabel("(" + victim.getIdAnonymat() + ") " + victim.getPrenom() + " " + victim.getNom());
			nameLabel.setForeground(Color.WHITE);
			JPanel panelLabel = new JPanel();
			panelLabel.setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			panelLabel.setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			panelLabel.setBackground(COLOR_BACKGROUND);
			panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelLabel.add(nameLabel);
			panelLabel.addMouseListener(new EditVictimButtonListener(_mapPanel, this, panelLabel, victim));
			_thumbnailsPanel.add(panelLabel);
			
//			_map.put(toggleButton, listVictimsName.get(i));
		}
	}

	@Override
	public void update()
	{
		_thumbnailsPanel.removeAll();
		
		displayThumbnail();
		
		_scrollPane.repaint();
		_scrollPane.revalidate();
		_thumbnailsPanel.repaint();
		_thumbnailsPanel.revalidate();
	}
}
