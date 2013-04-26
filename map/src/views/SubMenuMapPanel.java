package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import views.buttons.SubMenuMapButton;
import views.listeners.AddMapButtonListener;
import views.listeners.SwitchMapButtonListener;
import controllers.MapController;
import controllers.OperationController;
import database.DatabaseManager;

public class SubMenuMapPanel extends SubMenuPanel
{
	private Map<JToggleButton, MapController> _map;
	private JPanel _thumbnailsPanel;
	
	public SubMenuMapPanel(MapPanel mapPanel, SubMenuMapButton button, OperationController operationController, DatabaseManager databaseManager)
	{
		super(mapPanel, operationController, databaseManager);
		
		_map = super.getMapMap();
		_thumbnailsPanel = super.getThumbnailPanel();
		displayThumbnail(operationController, databaseManager);

		addAddButtonListener(new AddMapButtonListener(mapPanel));
		addOkButtonListener(new SwitchMapButtonListener(mapPanel, button, this, operationController));
	}

	@Override
	public void displayThumbnail(OperationController operationController, DatabaseManager databaseManager)
	{		
		_thumbnailsPanel.setBackground(SubMenuMapPanel.COLOR_BACKGROUND);
		_thumbnailsPanel.setLayout(new BoxLayout(_thumbnailsPanel, BoxLayout.PAGE_AXIS));
		
		List<MapController> listMapsName = operationController.getMapList();
		ButtonGroup group = new ButtonGroup();
		
		for(int i = 0; i < listMapsName.size(); i++)
		{
			int currentMapId = operationController.getCurrentMap().getId();
		
			final int THUMBNAIL_WIDTH = SubMenuPanel.WIDTH - 30;
			final int THUMBNAIL_HEIGHT = (int)(0.6 * THUMBNAIL_WIDTH);
			
			int id = listMapsName.get(i).getId();
			ImageIcon icon = databaseManager.getImage(id + "", listMapsName.get(i).getName());
			Image imageScaled = icon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
			ImageIcon iconScaled = new ImageIcon(imageScaled);
			
			JToggleButton toggleButton = new JToggleButton();
			toggleButton.setMaximumSize(new Dimension(THUMBNAIL_WIDTH + 10, THUMBNAIL_HEIGHT + 10));
			toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toggleButton.setIcon(iconScaled);
			group.add(toggleButton);
			_thumbnailsPanel.add(toggleButton);
			
			System.out.println("i : " + i + " id : " + id + ", Name image : " + listMapsName.get(i).getName());
			
			JLabel nameLabel = new JLabel(listMapsName.get(i).getName());
			nameLabel.setForeground(Color.WHITE);
			
			ImageIcon iconDelete = new ImageIcon(EntityPanel.class.getResource("/ui/delete.png"));
			Image imageDeleteScaled = iconDelete.getImage().getScaledInstance(12, 16, Image.SCALE_DEFAULT);
			ImageIcon iconDeleteScaled = new ImageIcon(imageDeleteScaled);
			
			JLabel deleteIcon = new JLabel();
			deleteIcon.setIcon(iconDeleteScaled);
			
			JPanel panelLabel = new JPanel();
			panelLabel.setLayout(new BorderLayout());
			panelLabel.setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			panelLabel.setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
			panelLabel.setBackground(COLOR_BACKGROUND);
			panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelLabel.add(nameLabel, BorderLayout.CENTER);
			panelLabel.add(deleteIcon, BorderLayout.EAST);
			_thumbnailsPanel.add(panelLabel);
			
			if(id == currentMapId)
			{
				toggleButton.setSelected(true);
//				toggleButton.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
			}
			
			_map.put(toggleButton, listMapsName.get(i));
		}
		
		JScrollPane scrollPane = new JScrollPane(_thumbnailsPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportBorder(null);
		
		add(scrollPane, BorderLayout.CENTER);
	}
}
