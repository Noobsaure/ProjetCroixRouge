package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.MapPanel;

public class CancelAddEntityListener implements ActionListener
{
	private JPanel _parent;
	private AddEntityPanel _addEntityPanel;
	
	public CancelAddEntityListener(JPanel parent, AddEntityPanel addEntityPanel)
	{
		_parent = parent;
		_addEntityPanel = addEntityPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		
		_parent.remove(_addEntityPanel);
		_parent.repaint();
	}
}
