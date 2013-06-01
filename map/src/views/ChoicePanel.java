package views;

import java.awt.event.ActionListener;

import views.buttons.CustomButton;
import views.listeners.CancelButtonListener;

public class ChoicePanel extends MessagePanel {
	private static final long serialVersionUID = 1L;

	public ChoicePanel(String title, String message, ActionListener listener)
	{
		super(title, message);
			
		CustomButton cancelButton = new CustomButton("Annuler");
		cancelButton.addActionListener(new CancelButtonListener(this));
		super.getButtonsPanel().add(cancelButton);
		
		CustomButton okButton = super.getOkButton();
		okButton.addActionListener(listener);
	}
}
