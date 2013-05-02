package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.MapController;

public class RenameMapNameListener implements MouseListener
{
	private JPanel _labelPanel;
	private MapController _mapController;
	
	
	public RenameMapNameListener(JPanel labelPanel, MapController mapController)
	{
		_labelPanel = labelPanel;
		_mapController = mapController;
	}


	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getClickCount() == 2)
		{
			String lastName = ((JLabel)_labelPanel.getComponent(0)).getText();
			_labelPanel.remove(0);
			JTextField textField = new JTextField();
			textField.setText(lastName);
			textField.setFocusable(true);
			textField.addKeyListener(new ConfirmRenameMapListener(_labelPanel, lastName, _mapController));
			_labelPanel.add(textField, 0);
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
