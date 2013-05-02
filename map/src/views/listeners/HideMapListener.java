package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.ChoicePanel;
import views.CustomDialog;
import views.SubMenuMapPanel;
import views.buttons.SubMenuMapButton;
import controllers.MapController;
import controllers.OperationController;

public class HideMapListener implements MouseListener {
	private OperationController _operation;
	private MapController _map;
	private SubMenuMapPanel _subMenu;

	public HideMapListener(OperationController operation, SubMenuMapPanel subMenu, MapController map){
		_operation = operation;
		_subMenu = subMenu;
		_map = map;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		String title = "Confirmation suppression map : " + _map.getName();
		String message = "Êtes-vous sur de vouloir supprimer la carte '"+_map.getName()+"'. " +
				"Cette action est irréversible et toutes les entités présentes sur la carte seront ramenées à leur localisation de base.";

		ChoicePanel confirmDelMap = new ChoicePanel(_operation, _subMenu, _map, title, message);
		new CustomDialog(confirmDelMap, _operation.getGlobalPanel());
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
