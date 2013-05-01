package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ConfirmDelMapPanel;
import views.MapPanel;
import views.SubMenuPanel;
import controllers.OperationController;

public class CancelButtonListener implements ActionListener {
	private OperationController _operation;
	private SubMenuPanel _subMenu;
	private ConfirmDelMapPanel _popUp;
	
	public CancelButtonListener(OperationController operation, SubMenuPanel subMenu, ConfirmDelMapPanel popUp){
		_operation = operation;
		_subMenu = subMenu;
		_popUp = popUp;
	}
	
	public void actionPerformed(ActionEvent e) {
		MapPanel parent = _operation.getGlobalPanel().getMapPanel();
		parent.remove(_popUp);
		parent.setCurrentPopUp(null);
		parent.add(_subMenu);
		parent.repaint();
		parent.revalidate();
	}

}
