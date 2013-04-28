package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JPanel;

import views.AddVictimPanel;
import views.ErrorMessage;
import views.MapPanel;
import views.SubMenuVictimPanel;
import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;



public class ConfirmAddVictimListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";
	private String EMPTY_TYPE_MESSAGE = "Veuillez renseigner la champ \"Type\".";
	
	private JPanel _parent;
	private SubMenuVictimPanel _subMenu;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private AddVictimPanel _addVictimPanel;
	
	
	public ConfirmAddVictimListener(JPanel parent, SubMenuVictimPanel subMenu, OperationController operationController, DatabaseManager databaseManager, AddVictimPanel addVictimPanel)
	{
		_parent = parent;
		_subMenu = subMenu;
		_operationController = operationController;
		_databaseManager = databaseManager;
		_addVictimPanel = addVictimPanel;
	}
	
	
	public static boolean checkInput(String motif, String otherMotif, String idAnomymat, String soins)
	{
		return ((!motif.equals("") || (!otherMotif.equals(""))) && (idAnomymat != null) && !soins.equals(""));
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e)
	{
		_parent.add(_subMenu);
		
		Object[] objects = _addVictimPanel.getMotifList().getSelectedValuesList().toArray();
		String[] motifsList = new String[objects.length];
		for(int i = 0; i < objects.length; i++)
			motifsList[i] = (String)objects[i];
		String otherMotif = _addVictimPanel.getDetailsTextArea().getText();
		String idAnonymat = _addVictimPanel.getIdAnonymat().getText();
		String soins = _addVictimPanel.getSoins().getText();
		
		if(!checkInput((motifsList.length == 0 ) ? "" : motifsList[0], otherMotif, idAnonymat, soins))
		{
			if(motifsList.length == 0 )
				new ErrorMessage(_parent, "Saisie incomplète", "");
			
			if(otherMotif.equals(""))
				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_NAME_MESSAGE);
			
			if(idAnonymat.equals(""))
				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_TYPE_MESSAGE);
			
			if(soins.equals(""))
				System.out.println("Informations null");			
		}
		else
		{
			String name = _addVictimPanel.getNameTextField().getText();
			String prenom = _addVictimPanel.getPrenomTextField().getText();
			String adress = _addVictimPanel.getAdressTextField().getText();
			
			Date dateDeNaissanceDate = _addVictimPanel.getDateDeNaissanceDatePicker().getDate();
			Timestamp dateDeNaissance = null;
			if(dateDeNaissanceDate != null)
				dateDeNaissance = new Timestamp(dateDeNaissanceDate.getYear(),  dateDeNaissanceDate.getMonth(), dateDeNaissanceDate.getDate(), dateDeNaissanceDate.getHours(), dateDeNaissanceDate.getMinutes(), dateDeNaissanceDate.getSeconds(), 0);
			
			Date datePriseEnChargeDate = _addVictimPanel.getDatePriseEnChargeDatePicker().getDate();
			Timestamp datePriseEnCharge = null;
			if(datePriseEnChargeDate != null)
				datePriseEnCharge = new Timestamp(datePriseEnChargeDate.getYear(),  datePriseEnChargeDate.getMonth(), datePriseEnChargeDate.getDate(), datePriseEnChargeDate.getHours(), datePriseEnChargeDate.getMinutes(), datePriseEnChargeDate.getSeconds(), 0);

			Timestamp dateSortie = null;
			
			try
			{
				new VictimController(_operationController, _databaseManager, name, prenom, motifsList, adress, dateDeNaissance, datePriseEnCharge, dateSortie, otherMotif, soins, idAnonymat, true);
				_subMenu.update();
			}
			catch(ParseException e1)
			{
				e1.printStackTrace();
			}
			
			MapPanel mapPanel = (MapPanel)_parent;
			mapPanel.addMapPanelListener();
			
			_parent.remove(_addVictimPanel);
			_parent.repaint();
		}
	}
}