package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.EditVictimPanel;
import views.MapPanel;
import views.MessagePanel;
import views.CustomDialog;
import views.SubMenuVictimPanel;
import controllers.VictimController;

public class FinDePriseEnChargeButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;
	private VictimController _victimController;
	private EditVictimPanel _editVictimPanel;

	public FinDePriseEnChargeButtonListener(MapPanel mapPanel, SubMenuVictimPanel subMenu, VictimController victimController, EditVictimPanel editVictimPanel)
	{
		_mapPanel = mapPanel;
		_subMenu = subMenu;
		_victimController = victimController;
		_editVictimPanel = editVictimPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String motifDeSortie = _editVictimPanel.getMotifTextField().getText();
		if(motifDeSortie.equals("")) {
			MessagePanel errorPanel = new MessagePanel("Saisie incomplète", "Un motif de fin de prise en charge doit être renseigné.");
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		} else {
			_victimController.finDePriseEnCharge(motifDeSortie);
			CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_editVictimPanel);
			dialog.dispose();
			_subMenu.update();
		}
	}
}
