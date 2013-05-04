package views;

import views.buttons.CustomButton;
import views.buttons.SubMenuMapButton;
import views.listeners.CancelButtonListener;
import views.listeners.ConfirmDelMapListener;
import controllers.MapController;
import controllers.OperationController;

public class ChoicePanel extends MessagePanel {
	private static final long serialVersionUID = 1L;	
	private OperationController _operation;
	private MapController _map;

	public ChoicePanel(OperationController operation, SubMenuPanelImpl subMenu, MapController map, String title, String message)
	{
		super(title, message);
		
		_operation = operation;
		_map = map;
			
		CustomButton cancelButton = new CustomButton("Annuler");
		cancelButton.addActionListener(new CancelButtonListener(this));
		super.getButtonsPanel().add(cancelButton);
		
		CustomButton okButton = super.getOkButton();
		okButton.addActionListener(new ConfirmDelMapListener(_operation, _map));
	}
}
