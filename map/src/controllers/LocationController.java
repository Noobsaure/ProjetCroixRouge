package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import observer.Observer;
import observer.Subject;

import views.ErrorMessage;

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
	
	private List<Observer> _observers;

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
				
		if(name.compareTo("LocalisationBaseDesEntites") == 0){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Ajout localisation impossible" ,"Le nom de cette localisation est déjà utilisée comme localisation \n" +
			"de départ pour toutes les entités. Veuillez rééssayer en donnant un nom différent.");	
			return;
		}
		
		try {
			result = _dbm.executeQueryInsert(new SQLQueryInsert("Localisation", "(NULL,"+_idOperation+","+_idMap+",'"+name+"','"+description+"',"+x+","+y+")"));
			_coordX = x;
			_coordY = y;
			_name = name;
			_description = description;
			_id = result;

		} catch (MalformedQueryException e) {
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur interne" ,"La localisation "+_name+" n'a pas pu être ajouté dans la base de données. \n" +
			"Veuillez relancer l'application.");				
		}

		operation.getCurrentMap().addLocation(this);
		operation.addLocation(this);
		System.out.println("Creation Localisation : "+name+" a réussi.");
		
		_observers = new ArrayList<Observer>();
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
		_name = name;
		_description = description;
		_id = id;
		_idMap = idMap;
		_idOperation = _operation.getId();

		if(_name.compareTo("LocalisationBaseDesEntites") != 0)
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
			result = _dbm.executeQueryInsert(new SQLQueryInsert("LocalisationHistorique", "(NULL,'"+entity.getIdPosCurrent()+"','"+_id+"','"+entity.getId()+"','"+entity.getDateArriveeLocalisation()+"','"+datetime+"')"));
		} catch (MalformedQueryException e) { 
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur interne" ,"Une erreur est survenue lors de l'ajout de \n" +
			"l'entité "+entity.getName()+" au point de localisation \"" +_name+"\".");
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
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Localisation", "nom='"+name+"'","id="+_id));
		} catch (MalformedQueryException e) { 
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur interne" ,"Une erreur est survenue lors de la mise à jour du nom de la localisation \n" +
			"\""+_name+"\" vers \""+name+"\".");
		}

		genererMessageChangementDeNom(name);
		_name = name;
	}

	private void genererMessageChangementDeNom(String name) {
		String message =  _name+" a été renommée en "+name+".";
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		try {
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,'-1','-1','-1','-1',"+_operation.getIdOperateur()+", NULL, "+_operation.getId()+",NULL, NULL,'"+datetime+"','"+message+"',0)"));
		} catch (MalformedQueryException e) {
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur generation message" ,"Une erreur est survenue lors de la génération du message pour la main courante \n"+
					"Message : "+message);
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

}		
