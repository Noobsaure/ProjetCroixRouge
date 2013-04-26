package views.listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddLocationPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.LocationController;
import controllers.OperationController;
import database.DatabaseManager;



public class ConfirmAddLocationListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";
	
	private int _x, _y;
	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private AddLocationPanel _addLocationPanel;
	
	
	public ConfirmAddLocationListener(JPanel parent, OperationController operationController, DatabaseManager databaseManager, AddLocationPanel addLocationPanel, int x, int y)
	{
		_parent = parent;
		_operationController = operationController;
		_databaseManager = databaseManager;
		_addLocationPanel = addLocationPanel;
		_x = x;
		_y = y;
	}
	
	
	public boolean checkInput(String name, String informations)
	{
		return !name.equals("");
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String name = _addLocationPanel.getName();
		String informations = _addLocationPanel.getInformations();
		
		if(!checkInput(name, informations))
		{
			if(name.equals(""))
				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_NAME_MESSAGE);
			
			if(informations.equals(""))
				System.out.println("Informations null");	
		}
		else
		{
			LocationController location = new LocationController(_operationController, _databaseManager,_x,_y,name,informations);
			MapPanel mapPanel = (MapPanel)_parent;
			mapPanel.addMapPanelListener();
			_parent.remove(_addLocationPanel);
			_parent.repaint();
		}
	}
}

















