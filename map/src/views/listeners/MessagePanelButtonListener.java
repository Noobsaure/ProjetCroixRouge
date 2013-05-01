package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.MapPanel;
import views.MessagePanel;
import views.MyJDialog;

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
		MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_messagePanel);
		dialog.dispose();
	}
}
