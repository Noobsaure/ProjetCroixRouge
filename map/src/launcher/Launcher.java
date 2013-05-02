package launcher;

import java.util.List;
import java.util.Timer;

import views.GlobalPanel;
import controllers.EntityController;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;
import database.DatabaseManager;
import database.MalformedQueryException;

public class Launcher {

	private OperationController _operation;
	private DatabaseManager _dbm;
	/**
	 * @param idOperateur 
	 * @param idOperation 
	 * @param args
	 * @throws MalformedQueryException 
	 */
	public Launcher(GlobalPanel panel,String adresseServeur, String port, String login, String mdp, int idOperation, int idOperateur){
		
		_dbm = new DatabaseManager();
		_dbm.connection("jdbc:mysql://localhost:3306/symfony", "root", "apagos35");
		_dbm.connectionBack("jdbc:mysql://localhost:3306/symfony", "root", "apagos35");

		//int idOperation =  Integer.parseInt(args[0]);
		// Lorsque la map sera intégrée à la page symfony : _operation = new OperationController(_dbm, getParameter("idOperation"), getParameter("idOperateur"));
		_operation = new OperationController(_dbm,idOperation,idOperateur);
		RefreshTimerTask timerTask = new RefreshTimerTask(_operation, _dbm);
		_operation.setTimerTask(timerTask);
		_operation.setGlobalPanel(panel);
		_dbm.setOperation(_operation);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>     Initialisation de la JApplet   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		_operation.loadTeamMember();
		_operation.loadEntity();
		_operation.loadTeamMemberIntoEntity();
		_operation.loadMaps();
		_operation.loadVictim();
		_operation.loadLocation();
		_operation.loadEntityIntoLocation();
				
		_operation.notifyObservers();
		
		_operation.showTeamMemberList();
		_operation.showEntityList();
		_operation.showMaps();
		_operation.showLocation();
		

		Timer timer = new Timer();
		timer.schedule(timerTask,0,5000);
		
		_operation.setTimer(timer);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>     Tests controlleurs   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
	
		
		System.out.println("\n \n \n>>>>>>>>>>>>>>>>>>>>>>>     Resultats   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
	}

	public DatabaseManager getDatabaseManager(){
		return _dbm;
	}
	
	public OperationController getOperationController(){
		return _operation;
	}
}
