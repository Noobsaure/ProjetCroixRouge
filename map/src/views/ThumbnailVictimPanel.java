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
	private JLabel _nameLabel;

	public ThumbnailVictimPanel(MapPanel mapPanel, SubMenuVictimPanel subMenu, VictimController victim) {
		_victim = victim;
		_nameLabel = new JLabel("(" + _victim.getIdAnonymat() + " -> " + _victim.getEntiteAssociee().getName() + ") " + _victim.getPrenom() + " " + _victim.getNom());
		_nameLabel.setForeground(Color.WHITE);
		setMaximumSize(new Dimension(SubMenuPanelImpl.WIDTH - 20, SubMenuPanelImpl.BUTTON_HEIGHT));
		setPreferredSize(new Dimension(SubMenuPanelImpl.WIDTH - 20, SubMenuPanelImpl.BUTTON_HEIGHT));
		setBackground(SubMenuPanelImpl.COLOR_BACKGROUND);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		add(_nameLabel);
		addMouseListener(new EditVictimButtonListener(mapPanel, subMenu, this, victim));
	}
	
	public VictimController getVictimController() {return _victim;}

	public void update(){
		_nameLabel.setText("(" + _victim.getIdAnonymat() + ") " + _victim.getPrenom() + " " + _victim.getNom());
	}
}
