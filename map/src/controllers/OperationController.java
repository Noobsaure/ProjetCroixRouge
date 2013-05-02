package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import launcher.RefreshTimerTask;
import observer.Observer;
import observer.Subject;
import views.GlobalPanel;
import views.MessagePanel;
import views.CustomDialog;
import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQuerySelect;


public class OperationController implements Subject {

	private static int _idOperation;
	private static int _idOperateur;
	private List<EntityController> _entityList;
	private List<TeamMemberController> _teamMemberList;
	private List<MapController> _mapList;
	private List<LocationController> _locationList;
	private List<VictimController> _victimList;
	private MapController _currentMap;
	private GlobalPanel _globalPanel;
	private int _idPcm;
	private Timer _timer;
	private RefreshTimerTask _timerTask;

	private DatabaseManager _dbm;

	private List<Observer> _observers;

	public OperationController(DatabaseManager dbm, int idOperation, int idOperateur)
	{
		_dbm = dbm;
		_idOperation =idOperation;
		_idOperateur = idOperateur;
		_entityList = new ArrayList<EntityController>();
		_teamMemberList = new ArrayList<TeamMemberController>();
		_mapList = new ArrayList<MapController>();
		_victimList = new ArrayList<VictimController>();
		_locationList = new ArrayList<LocationController>();
		_observers = new ArrayList<Observer>();
	}

	public void loadTeamMember(){
		ResultSet result;

		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("*","Equipier", "enActivite=1 AND (operation_id='"+_idOperation+"' OR operation_id is NULL)"));
			while(result.next()){
				int id = result.getInt("id");
				String name = result.getString("nom");
				String firstName = result.getString("prenom");
				String phoneNumber = result.getString("tel");
				String othersInformations = result.getString("autres");
				if(othersInformations == null){
					othersInformations = "";
				}
				int entityId = result.getInt("entite_id");
				System.out.println("EntiteID : "+entityId);

				TeamMemberController equipier = new TeamMemberController(this, _dbm, id, name, firstName, phoneNumber, othersInformations, entityId);
				System.out.println("---->Chargement equipier :"+name+" avec l'id: "+id+" "+entityId);
				_teamMemberList.add(equipier);
				if(_timerTask == null)
					System.out.println("TimerTask null ?");
				System.out.println("PRINT POUR LE RI");
				_timerTask.set_lastEntityId(id);
			}
			result.getStatement().close();
		} catch (SQLException e) {e.printStackTrace();}
		catch(MalformedQueryException e1) {e1.printStackTrace();}
	}	

	public void loadEntity(){
		ResultSet result;
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Entite", "operation_id='"+_idOperation+"'"));

			while(result.next()){
				int id_entite = result.getInt("id");
				ResultSet result2 = _dbm.executeQuerySelect(new SQLQuerySelect("*","Entite","id='"+id_entite+"'"));
				try{
					while(result2.next()){
						int id = result2.getInt("id");
						int statut_id = result2.getInt("statut_id");
						int position_id = result2.getInt("pos_courante_id");
						String nom = result2.getString("nom");
						String type = result2.getString("type");
						String infos = result2.getString("infos");
						java.sql.Timestamp date = result2.getTimestamp("date_depart");
						String color = result2.getString("couleur");

						EntityController entite = new EntityController(this, _dbm, id, statut_id, position_id, date, nom, type, infos, color);
						_entityList.add(entite);
						
						_timerTask.set_lastEntityId(id);
					}
					result2.getStatement().close();

				}catch(SQLException e2){e2.printStackTrace();}
			}
			result.getStatement().close();

		}catch(SQLException e) {e.printStackTrace();}
		catch(MalformedQueryException e1) {e1.printStackTrace();}

	}

	public void loadLocation(){
		try {
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Localisation", "operation_id="+this.getId()));

			while(result.next()){
				int id = result.getInt("id");
				int id_carte = result.getInt("carte_id");
				String nom = result.getString("nom");
				String description = result.getString("desc");
				float x = result.getFloat("x");
				float y = result.getFloat("y");

				if(getMap(id_carte) != null){
					LocationController location = new LocationController(this,_dbm, id, id_carte, x, y, nom, description);
					_locationList.add(location);
				}else if(nom.compareTo("PCM (défaut)") == 0){
					LocationController location = new LocationController(this,_dbm, id, id_carte, x, y, nom, description);
					_locationList.add(location);
				}

				_timerTask.set_lastLocationId(id);
			}
			result.getStatement().close();
		}catch(MalformedQueryException e1){e1.printStackTrace();}
		catch(SQLException e2){e2.printStackTrace();}

		for(LocationController location: _locationList){
			if(location.getName().compareTo("PCM (défaut)") == 0){
				_idPcm= location.getId();				
				return;
			}
		}

		try {
			_idPcm = _dbm.executeQueryInsert(new SQLQueryInsert("Localisation", "(NULL,"+_idOperation+",NULL,'PCM (défaut)','Poste de commandement mobile. Par défaut toutes les entitées se trouvent à cette endroit.',0,0)"));
			LocationController location = new LocationController(this, _dbm, _idPcm, new Integer(0), new Float(0.0), new Float (0.0), "PCM (défaut)", "Poste de commandement mobile. Par défaut toutes les entitées se trouvent à cette endroit.");
			_locationList.add(location);
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur lors de la génération de la localisation de base des entitées.");
			new CustomDialog(errorPanel, _globalPanel);
		}
	}

	public void loadMaps(){
		try {
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Carte", "operation_id='"+_idOperation+"' AND visibilite=1"));

			while(result.next()){
				int id = result.getInt("id");
				String name = result.getString("nom");
				Boolean visibility = result.getBoolean("visibilite");

				MapController map = new MapController(this, _dbm , id, name, visibility);
				_timerTask.set_lastMapControllerId(id);
			}

			result.getStatement().close();
		}catch(MalformedQueryException e1){ e1.printStackTrace(); }
		catch(SQLException e2){ e2.printStackTrace(); }
	}

	public void loadVictim() {
		ResultSet result;

		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Victime", "operation_id='"+_idOperation+"'"));

			while(result.next()){
				if (result.getString("motif_sortie") == null) {
					int id_victim = result.getInt("id");
					String idAnonymat = result.getString("surnom");
					String nom = result.getString("nom");
					String prenom = result.getString("prenom");
					java.sql.Timestamp dateDeNaissance = result.getTimestamp("date_naissance");
					String adresse = result.getString("adresse");
					java.sql.Timestamp dateEntree = result.getTimestamp("date_entree");
					boolean petitSoin = result.getBoolean("petit_soin");
					boolean malaise = result.getBoolean("malaise");
					boolean traumatisme = result.getBoolean("traumatisme");
					boolean inconscient = result.getBoolean("inconscient");
					boolean arretCardiaque = result.getBoolean("arret_cardiaque");
					String atteinteDetails = result.getString("atteinte_details");
					String soin = result.getString("soin");
					java.sql.Timestamp dateSortiePriseEnCharge = result.getTimestamp("date_sortie");
					int entityId = result.getInt("entite_id");

					if(dateSortiePriseEnCharge == null);{
						VictimController victim = new VictimController(this, _dbm, id_victim, idAnonymat, nom, prenom, adresse, dateDeNaissance, dateEntree, atteinteDetails, soin, petitSoin, malaise, traumatisme, inconscient, arretCardiaque, entityId);
						_victimList.add(victim);
					}

					_timerTask.set_lastVictimId(id_victim);
				}
			}
			result.getStatement().close();
		}catch(MalformedQueryException e1){ e1.printStackTrace(); }
		catch(SQLException e2){ e2.printStackTrace(); }
	}

	public void loadTeamMemberIntoEntity() {
		for(TeamMemberController teamMember : _teamMemberList){
			teamMember.loadEntity();
		}
	}

	public void loadEntityIntoLocation(){
		for(EntityController entity : _entityList){
			entity.loadLocation();
		}
	}

	public void showTeamMemberList(){
		System.out.println(">>>> Liste des equipiers :");
		for(TeamMemberController equipier : _teamMemberList){
			System.out.println(equipier.show());
		}
	}

	public void showEntityList(){
		System.out.println("\n||||||||||||||  Liste des entites |||||||||||||||||");
		for(EntityController entite : _entityList){
			System.out.println(entite.show());
		}
	}

	public void showLocation() {
		System.out.println(">>>> Liste des localisations :");
		for(LocationController location :  _locationList){
			System.out.println(location.show());
		}}

	public void showMaps(){
		System.out.println(">>>> Liste des cartes :");
		for(MapController map :  _mapList){
			System.out.println(map.show());
		}
	}

	public void creerEntite(String nomEntite, String type, String infos, String color){
		EntityController entite = new EntityController(this, _dbm, nomEntite, type, infos, color);
		_entityList.add(entite);
	}

	public TeamMemberController getEquipier(int id){
		for(TeamMemberController teamMember : _teamMemberList){
			if(teamMember.getId() == id){
				return teamMember;
			}
		}
		return null;
	}

	public EntityController getEntity(int id) {
		for(EntityController entity : _entityList){
			if(entity.getId() == id){
				return entity;
			}
		}
		return null;
	}

	public boolean isMapAvailable(){
		if(_currentMap != null) return true;
		else return false;
	}

	public MapController getCurrentMap() {
		return _currentMap;
	}

	public void setCurrentMap(MapController map){
		_currentMap = map;
		notifyObservers();
	}

	public int getId() {
		return _idOperation;
	}

	public int getIdPcm(){
		return _idPcm;
	}

	public MapController getMap(int idMap) {
		for(MapController map : _mapList){
			if(map.getId() == idMap){
				return map;
			}
		}
		return null;
	}

	public LocationController getLocation(int idLocation) {
		for(LocationController location : _locationList){
			if(location.getId() == idLocation){
				return location;
			}
		}
		return null;
	}

	public void addEntite(EntityController entity) {
		_entityList.add(entity);
		notifyObservers();
	}

	public void addTeamMember(TeamMemberController equipier) {
		_teamMemberList.add(equipier);
	}

	public void addMap(MapController map){
		_mapList.add(map);
	}

	public void addLocation(LocationController location){
		_locationList.add(location);
		notifyObservers();
	}

	public GlobalPanel getGlobalPanel(){
		return _globalPanel;
	}

	public void setGlobalPanel(GlobalPanel panel){
		_globalPanel = panel;
	}

	public List<EntityController> getEntityList(){
		List<EntityController> listEntity = new ArrayList<EntityController>();

		for(EntityController entity : _entityList){
			if( entity.isTypeInterne()  )
				listEntity.add(entity);
		}

		return listEntity;
	}


	public List<TeamMemberController> getTeamMemberList(){
		List<TeamMemberController> listTeamMember = new ArrayList<TeamMemberController>(_teamMemberList);

		return listTeamMember;
	}

	public List<TeamMemberController> getTeamMemberAvailableList(){
		List<TeamMemberController> listTeamMember = new ArrayList<TeamMemberController>();

		for(TeamMemberController teamMember : _teamMemberList){
			if(!teamMember.isActive())
				listTeamMember.add(teamMember);
		}

		return listTeamMember;
	}

	public List<MapController> getMapList(){
		List<MapController> listMap = new ArrayList<MapController>();

		for(MapController map : _mapList){
			if(map.getVisibility())
				listMap.add(map);
		}
		return listMap;
	}

	public List<LocationController> getLocationList(){
		List<LocationController> listLocation = new ArrayList<LocationController>(_locationList);

		return listLocation;
	}

	public List<LocationController> getMapLocationList(){
		List<LocationController> listLocation = new ArrayList<LocationController>();

		for(LocationController location : _locationList){
			if(location.getId() != _idPcm && (_currentMap!=null) ){
				if(location.getIdMap() == _currentMap.getId())
					listLocation.add(location);
			}
		}

		return listLocation;
	}

	@Override
	public void addObserver(Observer observer) {
		_observers.add(observer);
	}


	@Override
	public void removeObserver(Observer observer) {
		_observers.remove(observer);
	}


	@Override
	public synchronized void notifyObservers() {
		for(Observer oneObserver : _observers) {
			System.out.println("on notifie");
			oneObserver.update();
		}
	}

	public void addVictim(VictimController victim) {
		_victimList.add(victim);
		notifyObservers();
	}

	public void delVictim(VictimController victim) {
		_victimList.remove(victim);
		notifyObservers();
	}

	public List<VictimController> getVictimList(){
		List<VictimController> listVictim = new ArrayList<VictimController>(_victimList);

		return listVictim;
	}

	public int getIdOperateur() {
		return _idOperateur;
	}

	public LocationController getPcmLocation(){
		return getLocation(_idPcm);
	}

	public void setListTeamMember(List<TeamMemberController> teamMemberList) {
		_teamMemberList = teamMemberList;		
	}

	public void setListEntity(List<EntityController> entityList) {
		_entityList = entityList;
	}

	public void setListMaps(List<MapController> mapList) {
		_mapList = mapList;
	}

	public void setListLocation(List<LocationController> locationList) {
		_locationList = locationList;
	}

	public boolean existsInMapList(int id) {
		for(MapController map : _mapList){
			if(map.getId() == id)
				return true;
		}
		return false;
	}

	public boolean existsInLocationList(int id) {
		for(LocationController location : _locationList){
			if(location.getId() == id)
				return true;
		}		
		return false;
	}

	public boolean existsInEnityList(int id) {
		for(EntityController entity : _entityList){
			if(entity.getId() == id)
				return true;
		}		
		return false;
	}

	public boolean existsInTeamMemberList(int id) {
		for(TeamMemberController team : _teamMemberList){
			if(team.getId() == id)
				return true;
		}		
		return false;
	}

	public void setTimerTask(Timer timer) {
		_timer = timer;
	}

	public Timer getTimerTask(){
		return _timer;
	}

	public void pauseTimerTask(){
		_timerTask.cancel();
	}

	public void startTimerTask(){
		_timerTask = new RefreshTimerTask(this, _dbm);
		_timer.schedule(_timerTask,0,5000);
	}

	public boolean existsInVictimList(int id) {
		for(VictimController victim : _victimList){
			if(victim.getId() == id)
				return true;
		}
		return false;
	}

	public void removeMap(MapController mapController) {
		_mapList.remove(mapController);
	}

	public int locationNameAlreadyExist(String name) {
		ResultSet result;
		
		try{
			result = _dbm.executeQuerySelect(new SQLQuerySelect("`id`,`nom`", "Localisation", "operation_id='"+_idOperation+"'"));

			try{
				while(result.next()){
					if(name.compareTo(result.getString("nom")) == 0){
						return result.getInt("id");
					}
				}
				result.getStatement().close();
			}catch(SQLException e){	
				MessagePanel errorPanel = new MessagePanel("Erreur interne - Verification nom localisation","Une erreur est survenue lors de la vérification de l'unicité du nom de la localisation.");
				new CustomDialog(errorPanel, _globalPanel);
			}
		}catch(MalformedQueryException e1) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Verification nom localisation" ,"Une erreur est survenue lors de la vérification de l'unicité du nom de la localisation.");
			new CustomDialog(errorPanel, _globalPanel);
		}
		
		return -1;
	}
	
	public int anonymatAlreadyExist(String anonymat) {
		ResultSet result;
		
		try{
			result = _dbm.executeQuerySelect(new SQLQuerySelect("`id`,`surnom`", "Victime", "operation_id='"+_idOperation+"'"));
			
			try{
				while(result.next()){
					String surnom = result.getString("surnom");
					if( (surnom != null) && (anonymat.compareTo(surnom) == 0)){
						return result.getInt("id");
					}
				}
				result.getStatement().close();
			}catch(SQLException e){	
				MessagePanel errorPanel = new MessagePanel("Erreur interne - Verification numéro anonymat","Une erreur est survenue lors de la vérification de l'unicité du numéro d'anonymat.");
				new CustomDialog(errorPanel, _globalPanel);
				return -1;
			}
		}catch(MalformedQueryException e1) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Verification numéro anonymat" ,"Une erreur est survenue lors de la vérification de l'unicité du numéro d'anonymat.");
			new CustomDialog(errorPanel, _globalPanel);
			return -1;
		}
		
		return -1;
	}

	public int getAnonymousNumber(){
		ResultSet result;
		int idAnonymat= 1;

		try{
			result = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Victime", "operation_id='"+_idOperation+"' ORDER BY id DESC LIMIT 1 "));

			try{
				while(result.next()){
					idAnonymat = result.getInt("id") +1;
				}
				result.getStatement().close();
			}catch(SQLException e){	
				MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement numéro anonymat","Une erreur est survenue lors de la génération du numéro d'anonymat.");
				new CustomDialog(errorPanel, _globalPanel);
			}
		}catch(MalformedQueryException e1) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement Entité" ,"Une erreur est survenue lors de la génération du numéro d'anonymat.");
			new CustomDialog(errorPanel, _globalPanel);
		}
		
		while(anonymatAlreadyExist(String.valueOf(idAnonymat)) != -1){
			idAnonymat++;
		}
		
		return idAnonymat;
	}
	
	public void setTimerTask(RefreshTimerTask timerTask){
		 _timerTask = timerTask;
	}

	public void setTimer(Timer timer) {
		_timer = timer;
	}
}


