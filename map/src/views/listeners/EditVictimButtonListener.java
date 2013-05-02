package views.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import launcher.Launcher;
import views.EditVictimPanel;
import views.MapPanel;
import views.CustomDialog;
import views.SubMenuVictimPanel;
import controllers.VictimController;

public class EditVictimButtonListener implements MouseListener {
	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;
	private JPanel _panelLabel;
	private VictimController _victimCOntroller;
	
	public EditVictimButtonListener(MapPanel mapPanel, SubMenuVictimPanel subMenu, JPanel panelLabel, VictimController victimCOntroller) {
		_mapPanel = mapPanel;
		_subMenu = subMenu;
		_panelLabel = panelLabel;
		_victimCOntroller = victimCOntroller;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {		
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		
		EditVictimPanel editVictimPanel = new EditVictimPanel(_mapPanel, _subMenu, launcher.getOperationController(), launcher.getDatabaseManager(), _victimCOntroller);		
		editVictimPanel.addCancelButtonListener(new CancelEditVictimListener(_mapPanel, editVictimPanel));
		editVictimPanel.addOkButtonListener(new ConfirmEditVictimListener(_mapPanel, editVictimPanel, _victimCOntroller));
		new CustomDialog(editVictimPanel, _mapPanel.getGlobalPanel());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		_panelLabel.setBackground(Color.WHITE);
		_panelLabel.getComponent(0).setForeground(Color.BLACK);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		_panelLabel.setBackground(Color.DARK_GRAY);
		_panelLabel.getComponent(0).setForeground(Color.WHITE);
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
