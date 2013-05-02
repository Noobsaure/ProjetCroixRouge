package views.listeners;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import views.AddEntityPanel;
import views.GlobalPanel;
import views.MapPanel;
import views.MessagePanel;
import views.CustomDialog;
import controllers.EntityController;
import controllers.OperationController;
import database.DatabaseManager;



public class ConfirmAddEntityListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner le champ \"Nom\".";
	private String EMPTY_TYPE_MESSAGE = "Veuillez renseigner le champ \"Type\".";

	private MapPanel _mapPanel;
	private GlobalPanel _globalPanel;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private AddEntityPanel _addEntityPanel;


	public ConfirmAddEntityListener(MapPanel mapPanel, OperationController operationController, DatabaseManager databaseManager, AddEntityPanel addEntityPanel)
	{
		_mapPanel = mapPanel;
		_globalPanel = _mapPanel.getGlobalPanel();
		_operationController = operationController;
		_databaseManager = databaseManager;
		_addEntityPanel = addEntityPanel;
	}


	public boolean checkInput(String name, String type, String informations)
	{
		return (!name.equals("") && (type != null) && !type.equals(""));
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		String name = _addEntityPanel.getFieldName();
		String type = _addEntityPanel.getType();
		String informations = _addEntityPanel.getInformations();
		Color colorChosen = _addEntityPanel.getColor();
		String color = "#" + Integer.toHexString(colorChosen.getRGB()).substring(2, 8);

		if(!checkInput(name, type, informations))
		{
			if(name.equals("")) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", EMPTY_NAME_MESSAGE);
				new CustomDialog(errorPanel, _globalPanel);
			} else if((type != null) && type.equals("")) {
				MessagePanel errorPanel = new MessagePanel("Saisie incomplète", EMPTY_TYPE_MESSAGE);
				new CustomDialog(errorPanel, _globalPanel);
			} else if(informations.equals("")) {
				System.out.println("Informations null");	
			}
		} else {
			CustomDialog dialog = (CustomDialog) SwingUtilities.getAncestorOfClass(CustomDialog.class,_addEntityPanel);
			dialog.dispose();
			new EntityController(_operationController, _databaseManager, name, type, informations, color);
		}
	}
}

