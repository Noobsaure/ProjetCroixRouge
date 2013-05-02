package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.AddEquipierPanel;
import views.CustomDialog;

public class CancelEquipierEquipierButtonListener implements ActionListener
{
	private AddEquipierPanel _addEquipierPanel;
	
	public CancelEquipierEquipierButtonListener(AddEquipierPanel addEquipierPanel)
	{
		_addEquipierPanel = addEquipierPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{	
		CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addEquipierPanel);
		dialog.dispose();
	}
}
