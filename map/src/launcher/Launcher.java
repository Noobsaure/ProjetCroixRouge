package launcher;

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

		//int idOperation =  Integer.parseInt(args[0]);
		// Lorsque la map sera intégrée à la page symfony : _operation = new OperationController(_dbm, getParameter("idOperation"), getParameter("idOperateur"));
		_operation = new OperationController(_dbm,idOperation,idOperateur);
		
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
		timer.schedule(new RefreshTimerTask(_operation, _dbm),0,5000);
		
		_operation.setTimerTask(timer);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>     Tests controlleurs   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		/*
		EntityController entite = new EntityController(_operation, _dbm, "Alpha1" ,"Mobile", "Equipe 1");		
		EntityController entite2 = new EntityController(_operation, _dbm, "Beta2" ,"Pied", "Equipe 2");
		EntityController entite3 = new EntityController(_operation, _dbm, "Charlie3" ,"Lol", "Equipe 3");
		*/
		
		//_operation.getEntity(1).addTeamMember(_operation.getEquipier(1));
		//_operation.getEntity(2).addTeamMember(_operation.getEquipier(2));
		//_operation.getEntity(3).addTeamMember(_operation.getEquipier(2));
		
		/*
		String path = "resources/map/map.png";
		_operation.addMap(new MapController(_operation, databaseManager, "Carte1", path ));
		
		_operation.addLocation(new LocationController(_operation, databaseManager, 2, 3, "PointLocalisation 1","Le premier point de localisation de notre GigaSuperCarte"));
		 */
		
		//_operation.getEntity(61).addTeamMember(_operation.getEquipier(2));
		//_operation.getEntity(62).addTeamMember(_operation.getEquipier(1));
		
		//_operation.getEquipier(2).joinEntity(_operation.getEntity(63));
		//_operation.getEquipier(2).leaveEntity();
		
		//_operation.get
		
		System.out.println("\n \n \n>>>>>>>>>>>>>>>>>>>>>>>     Resultats   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		_operation.showEntityList();
		
		/*EntityController entity = _operation.getEntity(19);
		entity.setAvailable(false, "Blablabla");
		System.out.println("Etat equipe : DISPO "+entity.getStatut());
		entity.setAvailable(true, "infos2");

		System.out.println("Etat equipe : DISPO "+entity.getStatut());*/
	}

	public DatabaseManager getDatabaseManager(){
		return _dbm;
	}
	
	public OperationController getOperationController(){
		return _operation;
	}
}
