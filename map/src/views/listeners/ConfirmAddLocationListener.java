package views.listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.AddLocationPanel;
import views.MapPanel;
import views.MessagePanel;
import views.MyJDialog;
import controllers.LocationController;
import controllers.OperationController;
import database.DatabaseManager;



public class ConfirmAddLocationListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";
	
	private int _x, _y;
	private MapPanel _mapPanel;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private AddLocationPanel _addLocationPanel;
	
	
	public ConfirmAddLocationListener(MapPanel mapPanel, OperationController operationController, DatabaseManager databaseManager, AddLocationPanel addLocationPanel, int x, int y)
	{
		_mapPanel = mapPanel;
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
			if(name.equals("")) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", EMPTY_NAME_MESSAGE);
				new MyJDialog(errorPanel, _mapPanel.getGlobalPanel());
			}
		} else {
			new LocationController(_operationController, _databaseManager,_x,_y,name,informations);
			MyJDialog dialog = (MyJDialog) SwingUtilities.getAncestorOfClass(MyJDialog.class,_addLocationPanel);
			dialog.dispose();
		}
	}
}