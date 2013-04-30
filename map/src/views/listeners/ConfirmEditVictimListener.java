package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JPanel;

import views.AddVictimPanel;
import views.EditVictimPanel;
import views.ErrorMessage;
import views.MapPanel;
import views.SubMenuPanel;
import views.SubMenuVictimPanel;
import controllers.EntityController;
import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;



public class ConfirmEditVictimListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";
	private String EMPTY_TYPE_MESSAGE = "Veuillez renseigner la champ \"Type\".";
	
	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private EditVictimPanel _editVictimPanel;
	private VictimController _victimController;
	
	
	public ConfirmEditVictimListener(MapPanel mapPanel, SubMenuVictimPanel subMenu, OperationController operationController, DatabaseManager databaseManager, EditVictimPanel editVictimPanel, VictimController victimController)
	{
		_mapPanel = mapPanel;
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
		
		if(!ConfirmAddVictimListener.checkInput((motifsList.length == 0 ) ? "" : motifsList[0], otherMotif, idAnonymat, soins))
		{
			if((motifsList.length == 0 ) || (otherMotif.equals("")))
				new ErrorMessage(_mapPanel, "Saisie incomplète", ConfirmAddVictimListener.EMPTY_MOTIF_MESSAGE);
			
			if(idAnonymat.equals(""))
				new ErrorMessage(_mapPanel, "Saisie incomplète", ConfirmAddVictimListener.EMPTY_ID_ANONYMAT_MESSAGE);
			
			if(soins.equals(""))
				new ErrorMessage(_mapPanel, "Saisie incomplète", ConfirmAddVictimListener.EMPTY_SOINS_MESSAGE);	
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
				
				SubMenuVictimPanel subMenu = new SubMenuVictimPanel(_mapPanel, _operationController, _databaseManager);
				_mapPanel.add(subMenu);
				_mapPanel.setComponentZOrder(subMenu, 0);
			}
			catch(ParseException e1)
			{
				e1.printStackTrace();
			}
			
			MapPanel mapPanel = (MapPanel)_mapPanel;
			mapPanel.addMapPanelListener();
			
			_mapPanel.remove(_editVictimPanel);
			_mapPanel.repaint();
		}
	}
}