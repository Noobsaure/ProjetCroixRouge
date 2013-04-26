package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.ErrorPanel;

public class ErrorMessageButtonListener implements ActionListener
{
	private JPanel _parent;
	private ErrorPanel _errorMessage;
	
	
	public ErrorMessageButtonListener(JPanel parent, ErrorPanel errorMessage)
	{
		_parent = parent;
		_errorMessage = errorMessage;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		_parent.remove(_errorMessage);
		_parent.repaint();
	}
}
