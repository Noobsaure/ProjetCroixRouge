package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.EditVictimPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.OperationController;
import database.DatabaseManager;



public class ConfirmEditVictimListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";
	private String EMPTY_TYPE_MESSAGE = "Veuillez renseigner la champ \"Type\".";
	
	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private EditVictimPanel _editVictimPanel;
	
	
	public ConfirmEditVictimListener(JPanel parent, OperationController operationController, DatabaseManager databaseManager, EditVictimPanel editVictimPanel)
	{
		_parent = parent;
		_operationController = operationController;
		_databaseManager = databaseManager;
		_editVictimPanel = editVictimPanel;
	}
	
	
	public boolean checkInput(String name, String type, String informations)
	{
		return (!name.equals("") && (type != null) && !type.equals(""));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		new ErrorMessage(_parent, "Ok");
//		String name = _addVictimPanel.getName();
//		String type = _addVictimPanel.getType();
//		String informations = _addVictimPanel.getInformations();
//		
//		if(!checkInput(name, type, informations))
//		{
//			if(name.equals(""))
//				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_NAME_MESSAGE);
//			
//			if((type != null) && type.equals(""))
//				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_TYPE_MESSAGE);
//			
//			if(informations.equals(""))
//				System.out.println("Informations null");			
//		}
//		else
//		{
//			new EntityController(_operationController, _databaseManager, name, type, informations);
			
			MapPanel mapPanel = (MapPanel)_parent;
			mapPanel.addMapPanelListener();
			
			_parent.remove(_editVictimPanel);
			_parent.repaint();
//		}
	}
}