package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.EditVictimPanel;
import views.MapPanel;

public class CancelEditVictimListener implements ActionListener
{
	private JPanel _parent;
	private EditVictimPanel _editVictimPanel;
	
	public CancelEditVictimListener(JPanel parent, EditVictimPanel editVictimPanel)
	{
		_parent = parent;
		_editVictimPanel = editVictimPanel;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MapPanel mapPanel = (MapPanel)_parent;
		mapPanel.addMapPanelListener();
		
		_parent.remove(_editVictimPanel);
		_parent.repaint();
	}
}
