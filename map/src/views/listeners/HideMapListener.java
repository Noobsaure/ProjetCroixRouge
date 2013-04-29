package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.AddEquipierPanel;
import views.ConfirmDelMapPanel;
import views.ErrorMessage;
import views.MapPanel;

import controllers.MapController;
import controllers.OperationController;

public class HideMapListener implements MouseListener {
	private OperationController _operation;
	private MapController _map;

	public HideMapListener(OperationController operation, MapController map){
		_operation = operation;
		_map = map;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MapPanel mapPanel = _operation.getGlobalPanel().getMapPanel();

		ConfirmDelMapPanel confirmDelMap = new ConfirmDelMapPanel(_operation, _map);
		mapPanel.add(confirmDelMap);
		mapPanel.setComponentZOrder(confirmDelMap, 0);

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
