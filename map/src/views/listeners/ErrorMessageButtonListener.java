package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.ErrorPanel;
import views.MapPanel;

public class ErrorMessageButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private ErrorPanel _errorMessage;
	
	public ErrorMessageButtonListener(MapPanel mapPanel, ErrorPanel errorMessage)
	{
		_mapPanel = mapPanel;
		_errorMessage = errorMessage;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		_mapPanel.remove(_errorMessage);
		_mapPanel.setCurrentPopUp(null);
		_mapPanel.repaint();
	}
}
