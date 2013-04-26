package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import observer.Observer;
import observer.Subject;

import views.ErrorMessage;

import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

public class MapController implements Subject {
	private OperationController _operation;
	private DatabaseManager _dbm;

	private int _id;
	private int _idOperation;
	private String _name;
	private boolean _visibility;
	private ImageIcon _datas;

	private List<LocationController> _locationList = new ArrayList<LocationController>();

	private List<Observer> _listObservers = new ArrayList<Observer>();
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
		_id = _dbm.storeImage(name, path, _idOperation);

		_datas = _dbm.getImage(_id + "", name);

		_operation.addMap(this);
		_operation.setCurrentMap(this);
		_listObservers.add(_operation.getGlobalPanel().getMapPanel());
	}

	public MapController(OperationController operation, DatabaseManager dbm, int id, String name, boolean visibility){
		_operation = operation;
		_dbm = dbm;
		_id = id;
		_name = name;
		_visibility = visibility;
		_datas = _dbm.getImage(_id + "", name);

		try{
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("id","Localisation","carte_id = "+_id));

			while(result.next()){
				int idLocation = result.getInt("id");
				_locationList.add(operation.getLocation(idLocation));
			}
		}catch(SQLException e){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Chargement carte '"+name+"'", "Une erreur interne est survenue lors du rechargement de la carte'"+name+"'.");
		}
		catch(MalformedQueryException e){ e.printStackTrace(); }

		_operation.addMap(this);
		_operation.setCurrentMap(this);
		_listObservers.add(_operation.getGlobalPanel().getMapPanel());
		
	}

	public String getName(){
		return _name;
	}

	public int getId() {
		return _id;
	}

	public boolean getVisibility(){
		return _visibility;
	}

	public void hideMap(){		
		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Carte","visibilite = 0","id="+_id));
		}catch(MalformedQueryException e){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur lors de la supression de la carte '"+_name+"'.");
		}
		_visibility = false;
	}
	
	public ImageIcon getImage()
	{
		return _datas;
	}

	public void addLocation(LocationController locationController) {
		_locationList.add(locationController);
		//notifyObservers();
	}

	public String show() {
		String result;
		result = _name;
		result += _id;

		return result;
	}

	@Override
	public void addObserver(Observer observer){
		_listObservers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer){
		_listObservers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for(Observer observer : _listObservers){
			observer.update();
		}
	}

	public void updateFields() {
		try{
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("id","Localisation","carte_id = "+_id));

			while(result.next()){
				int idLocation = result.getInt("id");
				if(!_operation.existsInLocationList(idLocation))
					_locationList.add(_operation.getLocation(idLocation));
			}
		}catch(SQLException e){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Chargement carte '"+_name+"'", "Une erreur interne est survenue lors du rechargement de la carte'"+_name+"'.");
		}catch(MalformedQueryException e){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Chargement carte '"+_name+"'", "Une erreur interne est survenue lors du rechargement de la carte'"+_name+"'.");
		}
	}
}
