package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.swing.SwingUtilities;

import views.EditVictimPanel;
import views.GlobalPanel;
import views.MapPanel;
import views.MessagePanel;
import views.MyJDialog;
import views.SubMenuVictimPanel;
import controllers.EntityController;
import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;



public class ConfirmEditVictimListener implements ActionListener
{	
	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;
	private SubMenuVictimPanel _subMenu;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private EditVictimPanel _editVictimPanel;
	private VictimController _victimController;
	
	public ConfirmEditVictimListener(MapPanel mapPanel, SubMenuVictimPanel subMenu, OperationController operationController, DatabaseManager databaseManager, EditVictimPanel editVictimPanel, VictimController victimController)
	{
		_mapPanel = mapPanel;
		_globalPanel = _mapPanel.getGlobalPanel();
		_subMenu = subMenu;
		_operationController = operationController;
		_databaseManager = databaseManager;
		_editVictimPanel = editVictimPanel;
		_victimController = victimController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object[] objects = _editVictimPanel.getMotifList().getSelectedValuesList().toArray();
		String[] motifsList = new String[objects.length];
		for(int i = 0; i < objects.length; i++)
			motifsList[i] = (String)objects[i];
		String otherMotif = _editVictimPanel.getDetailsTextArea().getText();
		String idAnonymat = _editVictimPanel.getIdAnonymat().getText();
		String soins = _editVictimPanel.getSoins().getText();
		
		EntityController entitesAssociees = _editVictimPanel.getMap().get(_editVictimPanel.getEntiteAssocieeCombobox().getSelectedItem());
		
		if(!ConfirmAddVictimListener.checkInput(((motifsList.length == 0) || (motifsList[0].equals(" "))) ? "" : ((motifsList[0].equals("")) ? "" : motifsList[0]), otherMotif, idAnonymat, soins, entitesAssociees))
		{
			if((motifsList.length == 0 ) || (otherMotif.equals(""))) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_MOTIF_MESSAGE);
				new MyJDialog(errorPanel, _globalPanel);
			} else if(idAnonymat.equals("")) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_ID_ANONYMAT_MESSAGE);
				new MyJDialog(errorPanel, _globalPanel);
			} else if(soins.equals("")) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_SOINS_MESSAGE);
				new MyJDialog(errorPanel, _globalPanel);
			} else if(entitesAssociees == null) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", ConfirmAddVictimListener.EMPTY_ENTITY_ASSOCIATED_MESSAGE);
				new MyJDialog(errorPanel, _globalPanel);
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
			}
			catch(ParseException e1)
			{
				e1.printStackTrace();
			}
			
			MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_editVictimPanel);
			dialog.dispose();
		}
	}
}