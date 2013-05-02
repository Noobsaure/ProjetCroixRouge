package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import observer.Observer;
import observer.Subject;
import views.MessagePanel;
import views.CustomDialog;
import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

public class LocationController implements Subject {
	private OperationController _operation;
	private DatabaseManager _dbm;

	private int _id;
	private int _idOperation;
	private int _idMap;
	private float _coordX;
	private float  _coordY;
	private String _name;
	private String _description;

	private List<Observer> _observers = new ArrayList<Observer>();

	private List<EntityController> _entityList = new ArrayList<EntityController>();

	/**
	 * Constructs a new Location with its coordinates, name and description and which is not in the database.
	 * Also add this location to the MapController.
	 * @param operation
	 * @param dbm
	 * @param x
	 * @param y
	 * @param name
	 */
	public LocationController(OperationController operation, DatabaseManager dbm, float x, float y, String name, String description){
		int result;
		_operation = operation;
		_idOperation = operation.getId();
		_dbm = dbm;
		_idMap = operation.getCurrentMap().getId();

		if(name.compareTo("PCM (défaut)") == 0){
			MessagePanel errorPanel = new MessagePanel("Ajout localisation impossible" ,"Le nom de cette localisation est déjà utilisée comme localisation " +
					"de base pour toutes les entités. Veuillez rééssayer en donnant un nom différent.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
			return;
		}else if(_operation.locationNameAlreadyExist(name)){
			MessagePanel errorPanel = new MessagePanel("Ajout localisation impossible" ,"Le nom de cette localisation est déjà utilisée. Veuillez recommencer en utilisant un nom différent.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
			return;
		}

		try {
			result = _dbm.executeQueryInsert(new SQLQueryInsert("Localisation", "(NULL,"+_idOperation+","+_idMap+",'"+_dbm.addSlashes(name)+"','"+_dbm.addSlashes(description)+"',"+x+","+y+")"));
			_coordX = x;
			_coordY = y;
			_name = name;
			_description = description;
			_id = result;
		}catch(MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Création localisation" ,"La localisation "+_name+" n'a pas pu être ajouté dans la base de données." +
					"Veuillez réessayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		operation.getCurrentMap().addLocation(this);
		operation.addLocation(this);
	}

	/**
	 * Constructor for an existing location in database and add this location to the MapController.
	 * @param operation
	 * @param dbm
	 * @param x
	 * @param y
	 * @param name
	 * @param description
	 */
	public LocationController(OperationController operation, DatabaseManager dbm, int id, int idMap, float x, float y, String name, String description){
		_operation = operation;
		_dbm = dbm;
		_coordX = x;
		_coordY = y;
		_name = _dbm.stripSlashes(name);
		_description = _dbm.stripSlashes(description);
		_id = id;
		_idMap = idMap;
		_idOperation = _operation.getId();

		if(_name.compareTo("PCM (défaut)") != 0 )
			operation.getMap(_idMap).addLocation(this);

		_observers = new ArrayList<Observer>();
	}


	/**
	 * Add the entity in parameter to the current location and create a history of this entity for this location.
	 * Also set the current location for the entity.
	 * @param entity
	 */
	public int addEntity(EntityController entity){
		Integer result = new Integer(-1);

		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		try {
			result = _dbm.executeQueryInsert(new SQLQueryInsert("LocalisationHistorique", "(NULL,'"+entity.getLastPosCurrentId()+"','"+_id+"','"+entity.getId()+"','"+entity.getDateArriveeLocalisation()+"','"+datetime+"')"));
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Localisation - Ajout entité" ,"Une erreur est survenue lors de l'ajout de " +
					"l'entité '"+entity.getName()+"' au point de localisation '" +_name+"'.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		_entityList.add(entity);

		return result;
	}

	/**
	 * Remove the entity from this location and generate the history of the movement for the entity.
	 * @param entity
	 * @return 
	 */
	public void removeEntity(EntityController entity){
		_entityList.remove(entity);	
	}

	public void setName(String name){
		try {
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Localisation", "nom='"+_dbm.addSlashes(name)+"'","id="+_id));
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Localisation - Changement de nom" ,"Une erreur est survenue lors de la mise à jour du nom de la localisation " +
					"'"+_name+"' vers '"+name+"'. Veuillez rééssayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		genererMessageChangementDeNom(name);
		_name = name;
	}

	private void genererMessageChangementDeNom(String name) {
		String message =  _name+" a été renommée en "+name+".";
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		try {
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-1',"+_operation.getIdOperateur()+", '-3', "+_operation.getId()+",NULL, NULL,'"+datetime+"','"+_dbm.addSlashes(message)+"',0)"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur generation message" ,"Une erreur est survenue lors de la génération du message pour la main courante. "+
					"Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public List<EntityController> getEntityList(){
		List<EntityController> entityList = new ArrayList<EntityController>(_entityList);
		return entityList;
	}

	public int getId() {
		return _id;
	}

	public String getName(){
		return _name;
	}

	public float getX(){
		return _coordX;
	}

	public float getY(){
		return _coordY;
	}

	public String getDescription(){
		return _description;
	}

	public int getIdMap(){
		return _idMap;
	}

	public String show() {
		String result;
		System.out.println("ON va la... et name = "+_name);
		result = _name.toUpperCase();
		result += "Equipes presentes: \n";

		for(EntityController entite : _entityList){
			result += entite.getName();
		}

		return result;
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
	public void notifyObservers() {
		for(Observer oneObserver : _observers) {
			oneObserver.update();
		}
	}

	public void updateFields() {
		try{
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*","Localisation","id = "+_id));

			while(result.next()){
				_name = _dbm.stripSlashes(result.getString("nom"));
				_description = _dbm.stripSlashes(result.getString("desc"));
			}
			
			result.getStatement().close();
		}catch(SQLException e){
			MessagePanel errorPanel = new MessagePanel( "Erreur interne - Mise à jour localisation '"+_name+"'", "Une erreur est survenue lors de la mise à jour des attributs de la localisation'"+_name+"'.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}catch(MalformedQueryException e1){
			MessagePanel errorPanel = new MessagePanel( "Erreur interne - Mise à jour localisation '"+_name+"'", "Une erreur est survenue lors de la mise à jour des attributs de la localisation'"+_name+"'.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public void setDescription(String informations) {
		try {
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Localisation", "`desc`='"+_dbm.addSlashes(informations)+"'","id="+_id));
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne" ,"Une erreur est survenue lors de la mise à jour de la description de la localisation '"+_name+"'. Veuillez rééssayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_description = informations;
	}

	public void addEntityList(EntityController entityController) {
		_entityList.add(entityController);		
	}


}		
