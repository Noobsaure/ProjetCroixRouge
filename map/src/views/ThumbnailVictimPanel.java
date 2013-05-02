package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import views.listeners.EditVictimButtonListener;
import controllers.VictimController;

public class ThumbnailVictimPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VictimController _victim;

	public ThumbnailVictimPanel(MapPanel mapPanel, SubMenuVictimPanel subMenu, VictimController victim) {
		_victim = victim;
		JLabel nameLabel = new JLabel("(" + victim.getIdAnonymat() + ") " + victim.getPrenom() + " " + victim.getNom());
		nameLabel.setForeground(Color.WHITE);
		setMaximumSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
		setPreferredSize(new Dimension(SubMenuPanel.WIDTH - 20, SubMenuPanel.BUTTON_HEIGHT));
		setBackground(SubMenuPanel.COLOR_BACKGROUND);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		add(nameLabel);
		addMouseListener(new EditVictimButtonListener(mapPanel, subMenu, this, victim));
	}
	
	public VictimController getVictimController() {return _victim;}

}
