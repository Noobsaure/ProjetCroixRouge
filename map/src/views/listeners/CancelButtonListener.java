package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.ChoicePanel;
import views.CustomDialog;
import views.MapPanel;
import views.SubMenuPanelImpl;
import controllers.OperationController;

public class CancelButtonListener implements ActionListener {
	
	private ChoicePanel _choicePanel;

	public CancelButtonListener(ChoicePanel choicePanel){
		_choicePanel = choicePanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_choicePanel);
		dialog.dispose();
	}
}
