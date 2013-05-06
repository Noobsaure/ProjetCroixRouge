package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import views.CustomDialog;
import views.MessagePanel;
import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

public class MapController {
	private OperationController _operation;
	private DatabaseManager _dbm;

	private int _id;
	private int _idOperation;
	private String _name;
	private boolean _visibility;
	private ImageIcon _datas;

	private List<LocationController> _locationList = new ArrayList<LocationController>();

	/**
	 * Construct a new MapController which is not in the database with the name and the path of the file.
	 * @param operation
	 * @param dbm
	 * @param id
	 * @param name
	 * @param visibility
	 */
	public MapController(OperationController operation, DatabaseManager dbm, String name, String path){
		_operation = operation;
		_dbm = dbm;
		_name = name;
		_visibility = true;
		_idOperation = operation.getId();
		_id = _dbm.storeImage(_dbm.stripSlashes(name), path, _idOperation);

		_datas = _dbm.getImage(_id + "", name);

		_operation.addCurrentMap(this);
		_operation.setLastModified();
	}

	public MapController(OperationController operation, DatabaseManager dbm, int id, String name, boolean visibility, boolean display){
		_operation = operation;
		_dbm = dbm;
		_id = id;
		_name = _dbm.stripSlashes(name);
		_visibility = visibility;
		_datas = _dbm.getImage(_id + "", name);

		_operation.addMap(this);
		if(display) {
			_operation.setCurrentMap(this);
		}
	}

	public String getName(){
		return _name;
	}

	public void setName(String name){
		
		if(_name.equals(name)){
			return;
		}
		
		String lastName = _name;
		try {
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Carte", "nom='"+_dbm.addSlashes(name)+"'","id="+_id));
		} catch (MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne" ,"Une erreur est survenue lors de la mise à jour du nom de la carte \""+_name+"\". Veuillez rééssayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_name = name;
		genererMessageChangementNom(lastName);
	}

	private void genererMessageChangementNom(String lastName) {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		String message = "La carte \""+lastName+"\" a été renommée en \""+_name+"\".";
		try {			
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', -3, '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+_dbm.addSlashes(message)+"','0')"));	
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message de changement nom de la carte. "+
					"Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_operation.setLastModified();
	}
	
	private void genererMessageSuppressionMap() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());

		String message = "La carte \""+_name+"\" a été supprimée.";
		try {			
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', -3, '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+_dbm.addSlashes(message)+"','0')"));	
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la suppression de la carte. "+
					"Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public int getId() {
		return _id;
	}

	public boolean getVisibility(){
		return _visibility;
	}

	public List<EntityController> getEntityListInThisMap(){
		List<EntityController> _entityList = new ArrayList<EntityController>();

		for(LocationController location : _locationList){
			if(location.getEntityList().size() != 0){
				_entityList.addAll(location.getEntityList());
			}
		}

		return _entityList;
	}

	public List<LocationController> getLocationList(){
		List<LocationController> locationList = new ArrayList<>(_locationList);

		return locationList;
	}

	public void hideMap(){		
		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Carte","visibilite = 0","id="+_id));
		}catch(MalformedQueryException e){
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Supression carte", "Erreur lors de la supression de la carte \""+_name+"\". Veuillez rééssayer.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_visibility = false;
		_operation.removeMap(this);
		
		if(_operation.getMapList().size() != 0)
			_operation.setCurrentMap(_operation.getMapList().get(_operation.getMapList().size()-1));
		else
			_operation.setCurrentMap(null);

		genererMessageSuppressionMap();
		_operation.setLastModified();
	}

	public ImageIcon getImage()
	{
		return _datas;
	}

	public void addLocation(LocationController locationController) {
		_locationList.add(locationController);
	}

	public String show() {
		String result;
		result = _name;
		result += _id;

		return result;
	}

	public void updateFields() {
		try{
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("nom , visibilite","Carte","id = "+_id));

			while(result.next()){
				_name = result.getString("nom");
				_visibility = result.getBoolean("visibilite");
			}
			result.getStatement().close();
		}catch(SQLException e){
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement carte \""+_name+"\"", "Une erreur interne est survenue lors du rechargement de la carte \""+_name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}catch(MalformedQueryException e){
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Chargement carte \""+_name+"\"", "Une erreur interne est survenue lors du rechargement de la carte \""+_name+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		if(!_visibility){

			List<EntityController> _entityList = this.getEntityListInThisMap();

			for(EntityController entity : _entityList){
				entity.setLocation(_operation.getLocation(_operation.getIdPcm()));
			}

			_operation.removeMap(this);

			if(_operation.getMapList().size() != 0)
				_operation.setCurrentMap(_operation.getMapList().get(_operation.getMapList().size()-1));
			else
				_operation.setCurrentMap(null);
		}
	}

	public void simpleAddLocation(LocationController locationController) {
		_locationList.add(locationController);
	}
}
