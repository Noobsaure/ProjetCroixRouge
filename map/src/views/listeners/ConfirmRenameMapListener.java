package views.listeners;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.MapController;

public class ConfirmRenameMapListener implements KeyListener
{
	private JPanel _labelPanel;
	private String _oldName;
	private MapController _mapController;
	
	
	public ConfirmRenameMapListener(JPanel labelPanel, String oldName, MapController mapController)
	{
		_labelPanel = labelPanel;
		_oldName = oldName;
		_mapController = mapController;
	}
	
	private void replaceNewLabel(String newName)
	{
		JLabel newNameLabel = new JLabel(newName);
		newNameLabel.setForeground(Color.WHITE);
		_labelPanel.remove(0);
		_labelPanel.add(newNameLabel, 0);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		switch(arg0.getKeyChar())
		{
			case  KeyEvent.VK_ESCAPE:
				replaceNewLabel(_oldName);
			case KeyEvent.VK_ENTER:
				JTextField textField = (JTextField)_labelPanel.getComponent(0);
				if((textField != null) && !textField.equals(""))
					replaceNewLabel(textField.getText());
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
