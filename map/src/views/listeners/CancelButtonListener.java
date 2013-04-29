package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfirmDelMapPanel;
import views.MapPanel;
import controllers.OperationController;

public class CancelButtonListener implements ActionListener {
	private OperationController _operation;
	private ConfirmDelMapPanel _popUp;
	
	public CancelButtonListener(OperationController operation, ConfirmDelMapPanel popUp){
		_operation = operation;
		_popUp = popUp;
	}
	
	public void actionPerformed(ActionEvent e) {
		MapPanel parent = _operation.getGlobalPanel().getMapPanel();
		parent.remove(_popUp);
		parent.repaint();
		parent.revalidate();
	}

}
