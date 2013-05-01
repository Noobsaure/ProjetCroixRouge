package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.ChoicePanel;
import views.MapPanel;
import views.SubMenuMapPanel;
import views.buttons.SubMenuMapButton;
import controllers.MapController;
import controllers.OperationController;

public class HideMapListener implements MouseListener {
	private OperationController _operation;
	private MapController _map;
	private SubMenuMapPanel _subMenu;
	private SubMenuMapButton _button;

	public HideMapListener(OperationController operation, SubMenuMapPanel subMenu, SubMenuMapButton button, MapController map){
		_operation = operation;
		_subMenu = subMenu;
		_map = map;
		_button = button;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		_operation.getGlobalPanel().getMapPanel().remove(_subMenu);
		
		MapPanel mapPanel = _operation.getGlobalPanel().getMapPanel();
		String title = "Confirmation suppression map : " + _map.getName();
		String message = "Êtes-vous sur de vouloir supprimer la carte '"+_map.getName()+"'. " +
				"Cette action est irréversible et toutes les entités présentes sur la carte seront ramenées à leur localisation de base.";

		ChoicePanel confirmDelMap = new ChoicePanel(_operation, _subMenu, _button, _map, title, message);
		mapPanel.add(confirmDelMap);
		mapPanel.setComponentZOrder(confirmDelMap, 0);
		mapPanel.setCurrentPopUp(confirmDelMap);
		mapPanel.repaint();
		mapPanel.revalidate();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
