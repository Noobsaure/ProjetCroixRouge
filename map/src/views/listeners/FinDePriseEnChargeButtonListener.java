package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.swing.SwingUtilities;

import views.EditVictimPanel;
import views.MapPanel;
import views.MessagePanel;
import views.CustomDialog;
import views.SubMenuVictimPanel;
import controllers.EntityController;
import controllers.OperationController;
import controllers.VictimController;

public class FinDePriseEnChargeButtonListener implements ActionListener
{
	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;
	private VictimController _victimController;
	private EditVictimPanel _editVictimPanel;
	private OperationController _operation;

	public FinDePriseEnChargeButtonListener(MapPanel mapPanel, SubMenuVictimPanel subMenu, VictimController victimController, EditVictimPanel editVictimPanel)
	{
		_mapPanel = mapPanel;
		_subMenu = subMenu;
		_victimController = victimController;
		_editVictimPanel = editVictimPanel;
		
		_operation = _mapPanel.getGlobalPanel().getLauncher().getOperationController();
	}
	
	public boolean checkInput(String motif, String otherMotif, String idAnonymat, String soins, EntityController entityAssociated)
	{
		return ((!motif.equals("") || (!otherMotif.equals(""))) && (idAnonymat != null) && !soins.equals("") && (entityAssociated != null) && ((_operation.anonymatAlreadyExist(idAnonymat) == -1) || (_operation.anonymatAlreadyExist(idAnonymat) == _victimController.getId())));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String motifDeSortie = _editVictimPanel.getMotifTextField().getText();
		if(motifDeSortie.equals(""))
		{
			MessagePanel errorPanel = new MessagePanel("Saisie incomplète", "Un motif de fin de prise en charge doit être renseigné.");
			new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
		}
		else
		{
			Object[] objects = _editVictimPanel.getMotifList().getSelectedValuesList().toArray();
			String[] motifsList = new String[objects.length];
			for(int i = 0; i < objects.length; i++)
				motifsList[i] = (String)objects[i];
			String otherMotif = _editVictimPanel.getDetailsTextArea().getText();
			String idAnonymat = _editVictimPanel.getIdAnonymat().getText();
			String soins = _editVictimPanel.getSoins().getText();
			
			EntityController entitesAssociees = _editVictimPanel.getMap().get(_editVictimPanel.getEntiteAssocieeCombobox().getSelectedItem());
			
			if(!checkInput(((motifsList.length == 0 ) || (motifsList[0].equals(" "))) ? "" : motifsList[0], otherMotif, idAnonymat, soins, entitesAssociees))
			{
				if(((motifsList.length == 0) || (motifsList[0].equals("(Autre motif)"))) && (otherMotif.equals("")))
				{
					MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_MOTIF_MESSAGE);
					new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
				}
				else
					if(idAnonymat.equals(""))
					{
						MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_ID_ANONYMAT_MESSAGE);
						new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
					}
					else
						if((_operation.anonymatAlreadyExist(idAnonymat) != -1) || (_operation.anonymatAlreadyExist(idAnonymat) != _victimController.getId()))
						{
							MessagePanel errorPanel = new MessagePanel("Erreur interne - Numéro anonymat" ,"Numéro d'anonymat déjà utilisé pour cette opération.");
							new CustomDialog(errorPanel, _operation.getGlobalPanel());
						}
						else
							if(soins.equals(""))
							{
								MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_SOINS_MESSAGE);
								new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
							}
							else
								if(entitesAssociees == null)
								{
									MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_ENTITY_ASSOCIATED_MESSAGE);
									new CustomDialog(errorPanel, _mapPanel.getGlobalPanel());
								}
			}
			else
			{
				String name = _editVictimPanel.getNameTextField().getText();
				String prenom = _editVictimPanel.getPrenomTextField().getText();
				String adress = _editVictimPanel.getAdressTextField().getText();
				
				Date dateDeNaissanceDate = _editVictimPanel.getDateDeNaissanceDatePicker().getDate();
				Timestamp dateDeNaissance = null;
				if(dateDeNaissanceDate != null)
					dateDeNaissance = new Timestamp(dateDeNaissanceDate.getYear(),  dateDeNaissanceDate.getMonth(), dateDeNaissanceDate.getDate(), dateDeNaissanceDate.getHours(), dateDeNaissanceDate.getMinutes(), dateDeNaissanceDate.getSeconds(), 0);
				
				try
				{
					_victimController.updateVictim(name, prenom, motifsList, adress, dateDeNaissance, otherMotif, soins, idAnonymat, entitesAssociees);
					_victimController.finDePriseEnCharge(motifDeSortie);
					CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_editVictimPanel);
					dialog.dispose();
					_subMenu.update();
				}
				catch(ParseException e1)
				{
					e1.printStackTrace();
				}
				
				CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_editVictimPanel);
				dialog.dispose();
			}
		}
	}
}
