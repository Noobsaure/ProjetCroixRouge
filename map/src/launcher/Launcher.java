package launcher;

import java.util.Timer;

import views.GlobalPanel;
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
	public Launcher(GlobalPanel panel,String adresseServeur, String port, String login, String mdp, int idOperation, int idOperateur, String database_name){
		
		_dbm = new DatabaseManager();
//		_dbm.connection("jdbc:mysql://localhost:3306/symfony", "root", "apagos35");
//		_dbm.connectionBack("jdbc:mysql://localhost:3306/symfony", "root", "apagos35");
		_dbm.connection("jdbc:mysql://"+adresseServeur+":"+port+"/symfony", login, mdp);
		_dbm.connectionBack("jdbc:mysql://"+adresseServeur+":"+port+"/"+database_name, login, mdp);

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

		timerTask.set_lastmodified(_operation.getLastModified());
		
		Timer timer = new Timer();
		timer.schedule(timerTask,0,1000);
		
		_operation.setTimer(timer);
		
	}

	public DatabaseManager getDatabaseManager(){
		return _dbm;
	}
	
	public OperationController getOperationController(){
		return _operation;
	}
}
