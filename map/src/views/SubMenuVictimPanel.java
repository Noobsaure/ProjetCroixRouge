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
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private ButtonGroup _group;
	
	public SubMenuVictimPanel(MapPanel mapPanel, OperationController operationController, DatabaseManager databaseManager)
	{
		super(mapPanel, operationController, databaseManager);
		
		_operationController = operationController;
		_databaseManager = databaseManager;
		
		_map = super.getMapVictim();
		_thumbnailsPanel = super.getThumbnailPanel();
		displayThumbnail(operationController, databaseManager);

		addAddButtonListener(new AddVictimButtonListener(mapPanel, this));
		addOkButtonListener(null);
	}

	@Override
	public void displayThumbnail(OperationController operationController, DatabaseManager databaseManager)
	{		
		_thumbnailsPanel.setBackground(SubMenuMapPanel.COLOR_BACKGROUND);
		_thumbnailsPanel.setLayout(new BoxLayout(_thumbnailsPanel, BoxLayout.PAGE_AXIS));
		
		List<VictimController> listVictimsName = operationController.getVictimList();
		_group = new ButtonGroup();
		
		for(int i = 0; i < listVictimsName.size(); i++)
		{			
			int id = listVictimsName.get(i).getId();
//			ImageIcon icon = databaseManager.getImage(id + "", listVictimsName.get(i).getNom());
//			Image imageScaled = icon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
//			ImageIcon iconScaled = new ImageIcon(imageScaled);
//			
//			JToggleButton toggleButton = new JToggleButton();
//			toggleButton.setMaximumSize(new Dimension(THUMBNAIL_WIDTH + 10, THUMBNAIL_HEIGHT + 10));
//			toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//			toggleButton.setIcon(iconScaled);
//			group.add(toggleButton);
//			_thumbnailsPanel.add(toggleButton);
			
			JLabel nameLabel = new JLabel(listVictimsName.get(i).getPrenom() + " " + listVictimsName.get(i).getNom());
			nameLabel.setForeground(Color.WHITE);
			JPanel _panelLabel = new JPanel();
			_panelLabel.setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			_panelLabel.setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			_panelLabel.setBackground(COLOR_BACKGROUND);
			_panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			_panelLabel.add(nameLabel);
			_thumbnailsPanel.add(_panelLabel);
			
//			_map.put(toggleButton, listVictimsName.get(i));
		}
		
		JScrollPane scrollPane = new JScrollPane(_thumbnailsPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportBorder(null);
		
		add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void update()
	{
		List<VictimController> listVictimsName = _operationController.getVictimList();
		_group = new ButtonGroup();
		
		int i = listVictimsName.size() - 1;
		
		int id = listVictimsName.get(i).getId();
//		ImageIcon icon = databaseManager.getImage(id + "", listVictimsName.get(i).getNom());
//		Image imageScaled = icon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
//		ImageIcon iconScaled = new ImageIcon(imageScaled);
//		
//		JToggleButton toggleButton = new JToggleButton();
//		toggleButton.setMaximumSize(new Dimension(THUMBNAIL_WIDTH + 10, THUMBNAIL_HEIGHT + 10));
//		toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//		toggleButton.setIcon(iconScaled);
//		group.add(toggleButton);
//		_thumbnailsPanel.add(toggleButton);
			
		JLabel nameLabel = new JLabel(listVictimsName.get(i).getPrenom() + " " + listVictimsName.get(i).getNom());
		nameLabel.setForeground(Color.WHITE);
		JPanel panelLabel = new JPanel();
		panelLabel.setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
		panelLabel.setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
		panelLabel.setBackground(COLOR_BACKGROUND);
		panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelLabel.add(nameLabel);
		_thumbnailsPanel.add(panelLabel);
			
//		_map.put(toggleButton, listVictimsName.get(i));
	}
}
