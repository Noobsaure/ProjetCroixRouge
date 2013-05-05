package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import views.CustomDialog;
import views.MessagePanel;
import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

public class EntityController {
	private OperationController _operation;	
	private DatabaseManager _dbm;

	private String _name;
	private String _type;
	private String _infos;
	private int _id;
	private java.sql.Timestamp _dateArriveeLocalisation;
	private int _lastPosCurrentId;
	private int _removeFromThisPoint =-1;
	private String _color;

	private boolean _available;
	private String _state;
	private int _stateId;
	
	private int _posCurrentId;

	private List<TeamMemberController> _teamMemberList = new ArrayList<TeamMemberController>();


	/**
	 * Constructor for creation of an entity which is not in the database
	 * @param operation The current operation
	 * @param name Name of the entity
	 * @param type Type of the entity
	 * @param infos Other informations about the entity
	 * @param color 
	 */
	public EntityController(OperationController operation, DatabaseManager dbm, String name, String type, String infos, String color){
		_dbm = dbm;
		_name = name;
		_type = type;
		_infos = infos;
		_available = true;
		_posCurrentId = operation.getIdPcm();
		_operation = operation;
		_color = color;

		if(_operation.entityNameAlreadyExist(name) != -1){
			MessagePanel errorPanel = new MessagePanel("Ajout entité" ,"Le nom de cette entité est déjà utilisée. Veuillez recommencer en utilisant un nom différent.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
			return;
		}
		
		int idOperation = operation.getId();
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		_dateArriveeLocalisation = datetime;

		//Creation of a new state in database
		int result;

		try {
			result = _dbm.executeQueryInsert(new SQLQueryInsert("Statut" ,"(NULL,NULL,'1','"+datetime+"','Création entité')"));
			_stateId = result;
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Creation Entite" ,"Une erreur est survenue lors de la creation du statut pour l'entité \""+name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		
		//Creation of the entity in database
		int result2;

		try {
			result2 = _dbm.executeQueryInsert(new SQLQueryInsert("Entite", "(NULL,'"+_stateId+"','"+_posCurrentId+"',"+operation.getIdOperateur()+",'"+idOperation+"','"+DatabaseManager.addSlashes(name)+"','"+datetime+"','"+type+"','"+color+"','"+DatabaseManager.addSlashes(infos)+"')"));
			_id = result2;
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Creation Entite" ,"Une erreur est survenue lors de la creation de l'entité \""+name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}	

		//Update of the status of the entity
		try {
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Statut","entite_id = '"+_id+"'", "id='"+_stateId+"'"));
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Creation Entite", "Une erreur est survenue lors de la mise à jour du statut pour l'entité \""+name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_operation.addEntite(this);
		genererMessageCreation();
		_operation.setLastModified();
	}


	/**
	 * Constructor for creation of an instance for an existant entity in database
	 * @param dbm The database manager
	 * @param id The id of the entity in the database
	 * @param statut_id The id of the status in the database 
	 * @param position_id The id of the position in the database
	 * @param name The name of the entity in the database
	 * @param type The type of the entity in the database
	 * @param infos The informations about the entity in database
	 */
	public EntityController(OperationController operation, DatabaseManager dbm, int id, int stateId, int positionId, java.sql.Timestamp dateArriveeLocalisation, String name, String type, String infos, String color){
		_operation = operation;
		_dbm = dbm;
		_id = id;
		_stateId = stateId;
		_posCurrentId = positionId;
		_name = DatabaseManager.stripSlashes(name);
		_type = type;
		_infos = DatabaseManager.stripSlashes(infos);
		_dateArriveeLocalisation = dateArriveeLocalisation;
		_color = color;

		ResultSet result;
		
		//On va regarder le statut pour indiquer la disponibilite de l'equipe
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("`dispo`,`infos`", "Statut", "id='"+stateId+"'"));
			try{
				while(result.next()){
					_state = result.getString("infos");
					if(_state != null){
						_state = DatabaseManager.stripSlashes(_state);
					}
					else if( (_state == null) || (_state.compareTo("Création entité") == 0) ){
						_state=" ";
					}
					_available = result.getBoolean("dispo");
				}
				result.getStatement().close();
			}catch(SQLException e){	
				MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement Entité" ,"Une erreur est survenue lors du chargement du statut pour l'entité \""+name+"\". Aucun résultat n'a été trouvé. Veuillez relancer l'application.");
				new CustomDialog(errorPanel, _operation.getGlobalPanel());
			}
		}catch (MalformedQueryException e1) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement Entité" ,"Une erreur est survenue lors du chargement du statut pour l'entité \""+name+"\". Veuillez relancer l'applciation.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		//On ajoute les equipiers presents dans l'entite
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Equipier", "entite_id='"+_id+"'"));
			try{
				while(result.next()){
					int idEquipier = result.getInt("id");
					_teamMemberList.add(operation.getEquipier(idEquipier));
				}
				result.getStatement().close();
			}catch(SQLException e){ 
				MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement Entité" ,"Une erreur est survenue lors du chargement des équipiers pour l'entité \""+name+"\". Aucun résultat n'a été trouvé. Veuillez relancer l'application.");
				new CustomDialog(errorPanel, _operation.getGlobalPanel());
			}
		}catch(MalformedQueryException e1) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement Entité" ,"Une erreur est survenue lors du chargement des équipiers pour l'entité \""+name+"\".Veuillez relancer l'application.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public void setAvailable(boolean state, String infos){
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		int result;

		//Create new status for the entity and update of the variable class "_available" and "stateId"
		try {
			result = _dbm.executeQueryInsert(new SQLQueryInsert("Statut" ,"(NULL,"+_id+","+state+",'"+datetime+"','"+DatabaseManager.addSlashes(infos)+"')"));
			_stateId = result;
			System.out.println("State ID "+_stateId);
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Changement de statut" ,"Impossible de créér un nouveau statut pour l'entite \""+_name+"\". Veuillez réessayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		try {
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Entite","statut_id ="+_stateId,"id ="+_id));
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Changement de statut" ,"Impossible de mettre à jour le statut pour l'entite \""+_name+"\". Veuillez réessayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_available = state;
		_state = infos;
		genererMessageStatut();
		
		_operation.setLastModified();
	}

	/**
	 * Add a team-member to the entity and update "Entity_historique" table
	 * @param teamMember The teamMember to add
	 */
	public void addTeamMember(TeamMemberController teamMember){
		if(teamMember.joinEntity(this)){
			_teamMemberList.add(teamMember);
		}
	}

	public void removeTeamMember(TeamMemberController teamMember){
		if(teamMember.leaveEntity()){
			_teamMemberList.remove(teamMember);
		}
	}

	public List<TeamMemberController> getTeamMemberList(){
		return _teamMemberList;
	}

	public boolean isAvailable(){
		return _available;
	}
	
	public String show(){
		String result = "---------------------------\n";
		result+= "Nom : "+_name+" \n";
		result+= "Type: "+_type+"\n";
		result+= "Infos:"+_infos+"\n";
		result+= "Id:"+_id+"\n";
		result+= "PosCurrantID"+_posCurrentId+"\n";
		result+= "Disponibilite:"+_available+" (ID statut = "+_stateId+")\n";
		if(!_available){
			result+="Cause : "+_state+"\n";
		}
		if(_teamMemberList.size() != 0){
			result += "Liste des equipiers: \n";
			for(TeamMemberController teammember : _teamMemberList){
				result += teammember.show();
			}
		}

		result += "\n---------------------------\n";
		return result;
	}

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public int getIdPosCurrent(){
		return _posCurrentId;
	}

	public boolean getStatut(){
		return _available;
	}

	public String getColor() {
		return _color;
	}

	public java.sql.Timestamp getDateArriveeLocalisation(){
		return _dateArriveeLocalisation;
	}

	public void setColor(String newColor){
		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Entite", "couleur='"+newColor+"'","id ="+_id));
		}catch(MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne" ,"Impossible de mettre à jour la couleur pour l'entite \""+_name+"\". Veuillez réessayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		_color = newColor;
		_operation.setLastModified();
	}

	public void setLocation(LocationController location) {	
		if(_operation.getLocation(_posCurrentId).equals(location)){
			return;
		}
		
		_lastPosCurrentId = _posCurrentId;

		LocationController loc = _operation.getLocation(_posCurrentId);
		if(loc != null)	loc.removeEntity(this);

		if(location == null){
			_posCurrentId = _operation.getIdPcm();
		}else{
			_posCurrentId = location.getId();
		}

		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Entite", "pos_courante_id ="+_posCurrentId,"id ="+_id));
		}catch(MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Changement de position" ,"Impossible de mettre à jour la position courante pour l'entite \""+_name+"\". L'entité a été ramené à \""+_operation.getPcmLocation().getName()+"\". Veuillez la repositionner." );
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
			_posCurrentId = _operation.getIdPcm();
		}

		int idDeplacement = _operation.getLocation(location.getId()).addEntity(this);

		java.util.Date date = new java.util.Date();
		java.sql.Timestamp _dateArriveeLocalisation = new java.sql.Timestamp(date.getTime());

		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Entite", "date_depart ='"+_dateArriveeLocalisation+"'","id ="+_id));
		}catch(MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Changement de position" ,"Impossible de mettre à jour la date d'arrivée à la localisation pour l'entite \""+_name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		genererMessageDeplacement(idDeplacement);
		
		_operation.setLastModified();
	}

	public void setName(String newName){
		if(_name.equals(newName)){
			return;
		}
		if( (_operation.entityNameAlreadyExist(newName) != _id) && (_operation.entityNameAlreadyExist(newName) != -1) ){
			MessagePanel errorPanel = new MessagePanel("Mise à jour localisation impossible" ,"Le nom de cette entité est déjà utilisée. Veuillez recommencer en utilisant un nom différent.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
			return;
		}
		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Entite", "nom='"+DatabaseManager.addSlashes(newName)+"'","id ="+_id));
		}catch(MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Entité - Changement de nom" ,"Impossible de mettre à jour le nom pour l'entite \""+_name+"\". Veuillez recommencer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		String tmp = _name;
		_name = newName;
		_operation.notifyObservers();
		genererMessageChangementDeNom(tmp);
		_operation.setLastModified();
	}

	private void genererMessageChangementDeNom(String tmp) {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		String message =  "\""+tmp+"\" a été renommée en \""+_name+"\".";

		try {
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2',"+_operation.getIdOperateur()+", '-4', "+_operation.getId()+",NULL, NULL, '"+datetime+"','"+DatabaseManager.addSlashes(message)+"','0')"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la main courante. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}


	/***
	 * Generate message in database for table "Messages", to alert daybook of the move of the entity.
	 * @param lastPosCurrentId
	 */
	public void genererMessageDeplacement(int idDeplacement){
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		String message =  "\""+_name+"\" a quitté \""+_operation.getLocation(_lastPosCurrentId).getName()+"\" pour \""+_operation.getLocation(_posCurrentId).getName()+"\".";

		try {
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2',"+_operation.getIdOperateur()+", '-1', "+_operation.getId()+",NULL, '"+idDeplacement+"', '"+datetime+"','"+DatabaseManager.addSlashes(message)+"','0')"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la main courante. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	/***
	 * Generate message in database for table "Messages", to alert daybook of the change of the status.
	 */
	public void genererMessageStatut(){
		String disponibility;

		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		if(_available){
			disponibility = "disponible";
		}else{
			disponibility = "indisponible";
		}

		String message =  "\""+_name+"\" est maintenant "+disponibility;
		
		if(_state != null){
			message +=" (Informations: "+_state+" ).";
		}

		try {
			int id = _operation.getId();
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2',"+_operation.getIdOperateur()+",'-4',"+id+",NULL,NULL,'"+datetime+"','"+DatabaseManager.addSlashes(message)+"',0)"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur generation message" ,"Une erreur est survenue lors de la génération du message pour la main courante. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	private void genererMessageCreation() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		String message =  "L'entité \""+_name+"\" vient d'être créée.";

		try {
			int id = _operation.getId();
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2',"+_operation.getIdOperateur()+",'-4',"+id+",NULL,NULL,'"+datetime+"','"+DatabaseManager.addSlashes(message)+"',0)"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur generation message" ,"Une erreur est survenue lors de la génération du message pour la main courante. "+
					"Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public boolean isTypeInterne() {
		if(_type.compareTo("Interne") == 0)
			return true;
		else
			return false;
	}


	public void updateFields() {
		ResultSet result;
		
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("statut_id, nom, date_depart, pos_courante_id", "Entite", "id="+_id));
			while(result.next()){
				_stateId = result.getInt("statut_id");
				_name = DatabaseManager.stripSlashes(result.getString("nom"));
				_dateArriveeLocalisation = result.getTimestamp("date_depart");
				
				if(result.getInt("pos_courante_id") != _posCurrentId){
					_removeFromThisPoint = _posCurrentId;
					_posCurrentId = result.getInt("pos_courante_id");
				}else{
					_removeFromThisPoint = -1;
				}
				
				try {
					ResultSet result2 = _dbm.executeQuerySelect(new SQLQuerySelect("`infos`,`dispo`", "Statut", "id='"+_stateId+"'"));
					try{
						while(result2.next()){
							_available = result2.getBoolean("dispo");
							_state = result2.getString("infos");
						}
						result2.getStatement().close();
					}catch(SQLException e){
						MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour entité", "Une erreur est survenue lors de la récupération des attributs du statut de l'entité \""+_name+"\".");
						new CustomDialog(errorPanel, _operation.getGlobalPanel());
					}
				}catch (MalformedQueryException e1) {
					MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour entité", "Une erreur est survenue lors de la récupération des attributs du statut de l'entité \""+_name+"\".");
					new CustomDialog(errorPanel, _operation.getGlobalPanel());
				}

				//On ajoute les equipiers presents dans l'entite
				_teamMemberList.clear();
				try {
					ResultSet result2 = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Equipier", "entite_id='"+_id+"'"));
					try{
						while(result2.next()){
							int idEquipier = result2.getInt("id");
							_teamMemberList.add(_operation.getEquipier(idEquipier));
						}
						result2.getStatement().close();
					}catch (SQLException e) { 
						MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour entité", "Une erreur est survenue lors de la récupération des attributs de composition de l'équipe \""+_name+"\".");
						new CustomDialog(errorPanel, _operation.getGlobalPanel());
					}
				} catch (MalformedQueryException e1) { 
					MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour entité", "Une erreur est survenue lors de la récupération des attributs de composition de l'équipe \""+_name+"\".");
					new CustomDialog(errorPanel, _operation.getGlobalPanel());
				}
			}
			result.getStatement().close();
		}catch(SQLException e){
			MessagePanel errorPanel = new MessagePanel( "Erreur interne - Mise à jour entité", "Une erreur est survenue lors de la mise à jour des informartions pour l'entité +\""+_name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}catch(MalformedQueryException e){
			MessagePanel errorPanel = new MessagePanel( "Erreur interne", "Une erreur est survenue lors de la mise à jour des informartions pour l'entité +\""+_name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public void loadLocation() {
		if( (_posCurrentId != _operation.getIdPcm()) && 
				(_operation.getLocation(_posCurrentId) != null) ){
			if(!_operation.getLocation(_posCurrentId).getEntityList().contains(this)){
				_operation.getLocation(_posCurrentId).addEntityList(this);
			}
		}
		else if(_operation.getLocation(_posCurrentId) == null){
			_posCurrentId = _operation.getIdPcm();
			try{
				_dbm.executeQueryUpdate(new SQLQueryUpdate("Entite", "pos_courante_id ="+_posCurrentId,"id ="+_id));
			}catch(MalformedQueryException e) {
				MessagePanel errorPanel = new MessagePanel("Erreur interne" ,"L'entité a été ramené à \""+_operation.getPcmLocation().getName()+"\" car sa localisation préceddente se trouvait sur une carte qui a été supprimée. Veuillez la repositionner." );
				new CustomDialog(errorPanel, _operation.getGlobalPanel());
				_posCurrentId = _operation.getIdPcm();
			}
		}
	}

	public int getLastPosCurrentId(){
		return _lastPosCurrentId;
	}


	public int getRemoveFromThisPoint() {
		return _removeFromThisPoint;
	}
	
	public String getInformationsStatut(){
		return _state;
	}
}

