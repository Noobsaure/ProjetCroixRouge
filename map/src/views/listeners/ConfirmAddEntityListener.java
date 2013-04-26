package views.listeners;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import views.AddEntityPanel;
import views.ErrorMessage;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;
import database.DatabaseManager;



public class ConfirmAddEntityListener implements ActionListener
{
	private String EMPTY_NAME_MESSAGE = "Veuillez renseigner la champ \"Nom\".";
	private String EMPTY_TYPE_MESSAGE = "Veuillez renseigner la champ \"Type\".";
	
	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _databaseManager;
	private AddEntityPanel _addEntityPanel;
	
	
	public ConfirmAddEntityListener(JPanel parent, OperationController operationController, DatabaseManager databaseManager, AddEntityPanel addEntityPanel)
	{
		_parent = parent;
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
		String name = _addEntityPanel.getName();
		String type = _addEntityPanel.getType();
		String informations = _addEntityPanel.getInformations();
		Color colorChosen = _addEntityPanel.getColor();
		String color = "#" + Integer.toHexString(colorChosen.getRGB()).substring(2, 8);
		
		if(!checkInput(name, type, informations))
		{
			if(name.equals(""))
				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_NAME_MESSAGE);
			
			if((type != null) && type.equals(""))
				new ErrorMessage(_parent, "Saisie incomplète", EMPTY_TYPE_MESSAGE);
			
			if(informations.equals(""))
				System.out.println("Informations null");			
		}
		else
		{
			EntityController entity = new EntityController(_operationController, _databaseManager, name, type, informations, color);
			MapPanel mapPanel = (MapPanel)_parent;
			mapPanel.addMapPanelListener();
			entity.addObserver(mapPanel.getGlobalPanel());
			entity.notifyObservers();
			_parent.remove(_addEntityPanel);
			_parent.repaint();
		}
	}
}

