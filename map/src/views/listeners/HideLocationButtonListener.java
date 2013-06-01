package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.ChoicePanel;
import views.CustomDialog;
import controllers.LocationController;
import controllers.OperationController;

public class HideLocationButtonListener implements MouseListener {
	private OperationController _operation;
	private LocationController _location;
	
	public HideLocationButtonListener(OperationController operation, LocationController location) {
		_operation = operation;
		_location = location;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		String title = "Confirmation suppression localisaiton : " + _location.getName();
		String message = "Êtes-vous sur de vouloir supprimer la localisation '"+_location.getName()+"'. Cette action est irréversible et toutes les entités présentes sur la localisation seront ramenées à leur localisation de base (PCM (défaut)).";
		
		ChoicePanel confirmDelMap = new ChoicePanel(title, message, new ConfirmDelLocationListener(_operation,_location));
		new CustomDialog(confirmDelMap, _operation.getGlobalPanel());
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
