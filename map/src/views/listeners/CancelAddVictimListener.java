package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddVictimPanel;
import views.MapPanel;

public class CancelAddVictimListener implements ActionListener
{
	private JPanel _parent;
	private AddVictimPanel _addVictimPanel;
	
	public CancelAddVictimListener(JPanel parent, AddVictimPanel addVictimPanel)
	{
		_parent = parent;
		_addVictimPanel = addVictimPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		
		_parent.remove(_addVictimPanel);
		_parent.repaint();
	}
}
