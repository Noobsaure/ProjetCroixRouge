package views;

import javax.swing.JPanel;

import views.buttons.CustomButton;
import views.buttons.SubMenuMapButton;
import views.listeners.CancelButtonListener;
import views.listeners.ConfirmDelMapListener;
import controllers.MapController;
import controllers.OperationController;

public class ConfirmDelMapPanel extends ErrorPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel _parent;	
	private OperationController _operation;
	private MapController _map;

	public ConfirmDelMapPanel(OperationController operation, SubMenuPanel subMenu, SubMenuMapButton button, MapController map, String title, String message)
	{
		super(operation.getGlobalPanel().getMapPanel(), title, message);
		
		_operation = operation;
		_map = map;
		_parent = operation.getGlobalPanel().getMapPanel();
			
		CustomButton cancelButton = new CustomButton("Annuler");
		cancelButton.addActionListener( new CancelButtonListener(_operation, subMenu, this) );
		super.getButtonsPanel().add(cancelButton);
		
		CustomButton okButton = super.getOkButton();
		addOkButtonListener(new ConfirmDelMapListener(_operation, button, this, _map));
	}
}
