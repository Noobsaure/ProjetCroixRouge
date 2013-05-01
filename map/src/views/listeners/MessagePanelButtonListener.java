package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.MapPanel;
import views.MessagePanel;
import views.CustomDialog;

public class MessagePanelButtonListener implements ActionListener
{
	private MessagePanel _messagePanel;
	
	public MessagePanelButtonListener(MessagePanel messagePanel)
	{
		_messagePanel = messagePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_messagePanel);
		dialog.dispose();
	}
}
