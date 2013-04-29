package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import views.EditVictimPanel;
import views.ErrorMessage;
import views.SubMenuVictimPanel;
import controllers.VictimController;

public class FinDePriseEnChargeButtonListener implements ActionListener
{
	private JPanel _parent;
	private SubMenuVictimPanel _subMenu;
	private VictimController _victimController;
	private EditVictimPanel _editVictimPanel;
	
	public FinDePriseEnChargeButtonListener(JPanel parent, SubMenuVictimPanel subMenu, VictimController victimController, EditVictimPanel editVictimPanel)
	{
		_parent = parent;
		_subMenu = subMenu;
		_victimController = victimController;
		_editVictimPanel = editVictimPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String motifDeSortie = _editVictimPanel.getMotifTextField().getText();
		if(motifDeSortie.equals(""))
			new ErrorMessage(_parent, "Saisie incomplète", "Un motif de fin de prise en charge doit être renseigné.");
		else
		{
			_victimController.finDePriseEnCharge(motifDeSortie);
			
			_parent.remove(_editVictimPanel);
			
			_parent.repaint();
			_parent.revalidate();
			
			_subMenu.update();
		}
	}
}
