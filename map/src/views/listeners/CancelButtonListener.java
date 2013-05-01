package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.ChoicePanel;
import views.MapPanel;
import views.SubMenuPanel;
import controllers.OperationController;

public class CancelButtonListener implements ActionListener {
	private OperationController _operation;
	private SubMenuPanel _subMenu;
	private ChoicePanel _popUp;
	
	public CancelButtonListener(OperationController operation, SubMenuPanel subMenu, ChoicePanel popUp){
		_operation = operation;
		_subMenu = subMenu;
		_popUp = popUp;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

}
