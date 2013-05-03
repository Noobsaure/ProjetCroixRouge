package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import views.listeners.HideMapListener;
import views.listeners.RenameMapNameListener;
import views.listeners.SubMenuMapToggleButtonListener;
import controllers.MapController;
import controllers.OperationController;

public class ThumbnailMapPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MapController _map;
	private JToggleButton _toggleButton;

	public ThumbnailMapPanel(MapPanel mapPanel, SubMenuMapPanel subMenu, MapController map, OperationController operationController, ButtonGroup group) {
		_map = map;
		setLayout(new BorderLayout());
		setBackground(SubMenuPanelImpl.COLOR_BACKGROUND);
		setMaximumSize(new Dimension());
		final int THUMBNAIL_WIDTH = SubMenuPanelImpl.WIDTH - 30;
		final int THUMBNAIL_HEIGHT = (int)(0.6 * THUMBNAIL_WIDTH);
		
		setMaximumSize(new Dimension(THUMBNAIL_WIDTH+10, THUMBNAIL_HEIGHT+35));
		setMinimumSize(new Dimension(THUMBNAIL_WIDTH+10, THUMBNAIL_HEIGHT+35));
		setPreferredSize(new Dimension(THUMBNAIL_WIDTH+10, THUMBNAIL_HEIGHT+35));

		ImageIcon icon = _map.getImage();
		Image imageScaled = icon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
		ImageIcon iconScaled = new ImageIcon(imageScaled);

		_toggleButton = new JToggleButton();
		_toggleButton.setMaximumSize(new Dimension(THUMBNAIL_WIDTH + 10, THUMBNAIL_HEIGHT + 10));
		_toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		_toggleButton.setIcon(iconScaled);
		group.add(_toggleButton);
		_toggleButton.addActionListener(new SubMenuMapToggleButtonListener(_map, mapPanel, operationController));
		add(_toggleButton, BorderLayout.CENTER);

		JLabel nameLabel = new JLabel(map.getName());
		nameLabel.setForeground(Color.WHITE);

		ImageIcon iconDelete = new ImageIcon(EntityPanel.class.getResource("/ui/delete.png"));
		Image imageDeleteScaled = iconDelete.getImage().getScaledInstance(12, 16, Image.SCALE_DEFAULT);
		ImageIcon iconDeleteScaled = new ImageIcon(imageDeleteScaled);

		JLabel deleteIcon = new JLabel();
		deleteIcon.setIcon(iconDeleteScaled);
		deleteIcon.addMouseListener(new HideMapListener(operationController, subMenu, map));
		
		JPanel panelLabel = new JPanel();
		panelLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
		panelLabel.setLayout(new BorderLayout());
		panelLabel.setMaximumSize(new Dimension(SubMenuPanelImpl.WIDTH - 20, SubMenuPanelImpl.BUTTON_HEIGHT));
		panelLabel.setPreferredSize(new Dimension(SubMenuPanelImpl.WIDTH - 20, SubMenuPanelImpl.BUTTON_HEIGHT));
		panelLabel.setBackground(SubMenuPanelImpl.COLOR_BACKGROUND);
		panelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelLabel.add(nameLabel, BorderLayout.CENTER);
		panelLabel.add(deleteIcon, BorderLayout.EAST);
		panelLabel.addMouseListener(new RenameMapNameListener(panelLabel, map));
		add(panelLabel, BorderLayout.SOUTH);
	}

	public JToggleButton getToggleButton() {
		return _toggleButton;
	}

	public void setSelected(boolean selected) {
		_toggleButton.setSelected(selected);
	}
	
	public MapController getMapController() {return _map;}
}
