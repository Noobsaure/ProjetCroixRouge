package views;

import javax.swing.JPanel;

import views.buttons.CustomButton;
import views.buttons.SubMenuMapButton;
import views.listeners.CancelButtonListener;
import views.listeners.ConfirmDelMapListener;
import controllers.MapController;
import controllers.OperationController;

public class ChoicePanel extends MessagePanel implements PopUpPanel
{
	private static final long serialVersionUID = 1L;	
	private OperationController _operation;
	private MapController _map;

	public ChoicePanel(OperationController operation, SubMenuPanel subMenu, SubMenuMapButton button, MapController map, String title, String message)
	{
		super(title, message);
		
		_operation = operation;
		_map = map;
			
		CustomButton cancelButton = new CustomButton("Annuler");
		cancelButton.addActionListener(new CancelButtonListener(_operation, subMenu, this));
		super.getButtonsPanel().add(cancelButton);
		
		CustomButton okButton = super.getOkButton();
		//addOkButtonListener(new ConfirmDelMapListener(_operation, button, this, _map));
	}

	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}
}
