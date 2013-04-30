package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import views.EditVictimPanel;
import views.ErrorMessage;
import views.MapPanel;
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
		if(motifDeSortie.equals(""))
			new ErrorMessage(_mapPanel, "Saisie incompl�te", "Un motif de fin de prise en charge doit être renseigné.");
		else
		{
			_victimController.finDePriseEnCharge(motifDeSortie);
			
			_mapPanel.remove(_editVictimPanel);
			
			_mapPanel.repaint();
			_mapPanel.revalidate();
			
			_subMenu.update();
		}
	}
}
