package launcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TimerTask;

import views.MessagePanel;
import views.CustomDialog;

import controllers.EntityController;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;
import controllers.TeamMemberController;
import controllers.VictimController;
import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQuerySelect;

/***
 * Cette classe est en charge de la vérification toutes les 5 secondes de la base de données (Pour vérifier les
 * 
 */
public class RefreshTimerTask extends TimerTask
{
	private OperationController _operation;
	private DatabaseManager _dbm;
	
	private int _lastVictimId;
	private int _lastEntityId;
	private int _lastTeamMemberId;
	private int _lastMapControllerId;
	private int _lastLocationId;
	
	public RefreshTimerTask(OperationController op,DatabaseManager dbm){
		super();
		_operation = op;
		_dbm = dbm;
	}

	@Override
	public void run() {
		refreshTeamMember();
		refreshEntity();
		refreshMaps();
		refreshLocation();
		refreshVictim();
		
		_operation.loadTeamMemberIntoEntity();
		_operation.loadEntityIntoLocation();

		_operation.notifyObservers();	
	}

	private void refreshVictim() {
		System.out.println("%%%%% REFRESH VICTIM LIST %%%%%");
		/* UPDATE TEAMMEMBER ALREADY IN THE LIST */		
		List<VictimController> victimList = _operation.getVictimList();

		for(VictimController victim : victimList){
			victim.updateFields();
		}		

		/* ADD NEW TEAMMEMBER WHICH ARE IN THE DATABASE */
		ResultSet result;
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("id","Victime", "operation_id="+_operation.getId()+" AND date_sortie is NULL AND id>"+_lastVictimId));
			while(result.next()){
				int id = result.getInt("id");
				
				if(!_operation.existsInVictimList(id)){
					ResultSet result2 = _dbm.executeQuerySelect(new SQLQuerySelect("*","Victime", "id="+id));
					while(result2.next()){
						System.out.println("VICTIME EN PLUS	");
						String idAnonymat = result2.getString("surnom");
						String nom = result2.getString("nom");
						String prenom = result2.getString("prenom");
						java.sql.Timestamp dateDeNaissance = result2.getTimestamp("date_naissance");
						String adresse = result2.getString("adresse");
						String statut = result2.getString("statut");
						java.sql.Timestamp dateEntree = result2.getTimestamp("date_entree");
						boolean petitSoin = result2.getBoolean("petit_soin");
						boolean malaise = result2.getBoolean("malaise");
						boolean traumatisme = result2.getBoolean("traumatisme");
						boolean inconscient = result2.getBoolean("inconscient");
						boolean arretCardiaque = result2.getBoolean("arret_cardiaque");
						String atteinteDetails = result2.getString("atteinte_details");
						String soin = result2.getString("soin");
						int entiteId = result2.getInt("entite_id");

						VictimController victim = new VictimController(_operation, _dbm, id, statut, idAnonymat, nom, prenom, adresse, dateDeNaissance, dateEntree, atteinteDetails, soin, petitSoin, malaise, traumatisme, inconscient, arretCardiaque,entiteId);
						_operation.addVictim(victim);
					}
					result2.getStatement().close();
				}
				_lastVictimId = id;
			}
			result.getStatement().close();
		} catch (SQLException e) {e.printStackTrace();}
		catch(MalformedQueryException e1) {e1.printStackTrace();}
	}

	public void refreshTeamMember(){
		System.out.println("%%%%% REFRESH TEAMMEMBER LIST %%%%%");
		/* UPDATE TEAMMEMBER ALREADY IN THE LIST */		
		List<TeamMemberController> teamMemberList = _operation.getTeamMemberList();

		for(TeamMemberController team : teamMemberList){
			team.updateFields();
		}		

		/* ADD NEW TEAMMEMBER WHICH ARE IN THE DATABASE */
		ResultSet result;
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("*","Equipier", "enActivite=1 AND (operation_id='"+_operation.getId()+"' OR operation_id is NULL) AND id>"+_lastTeamMemberId));
			while(result.next()){
				int id = result.getInt("id");

				if(!_operation.existsInTeamMemberList(id)){
					String name = result.getString("nom");
					String firstName = result.getString("prenom");
					String phoneNumber = result.getString("tel");
					String othersInformations = result.getString("autres");
					if(othersInformations == null){
						othersInformations = "";
					}
					int entityId = result.getInt("entite_id");
					
					TeamMemberController equipier = new TeamMemberController(_operation, _dbm, id, name, firstName, phoneNumber, othersInformations, entityId);
					_operation.addTeamMember(equipier);
				}
				_lastTeamMemberId = id;
			}

			result.getStatement().close();
		} catch (SQLException e) {e.printStackTrace();}
		catch(MalformedQueryException e1) {e1.printStackTrace();}
	}	

	public void refreshEntity(){
		System.out.println("%%%%% REFRESH ENTITY LIST %%%%%");
		/* UPDATE ENTITY ALREADY IN THE LIST */
		List<EntityController> entityList = _operation.getEntityList();

		for(EntityController entity : entityList){
			entity.updateFields();
		}

		/* ADD NEW ENTITY WHICH ARE IN THE DATABASE */
		ResultSet result;
		try {
			result = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Entite", "operation_id='"+_operation.getId()+"' AND id>"+_lastEntityId));
			while(result.next()){
				int id = result.getInt("id");
				if(!_operation.existsInEnityList(id)){
					ResultSet result2 = _dbm.executeQuerySelect(new SQLQuerySelect("*","Entite","id='"+id+"'"));
					try{
						while(result2.next()){					
							int statut_id = result2.getInt("statut_id");
							int position_id = result2.getInt("pos_courante_id");
							String nom = result2.getString("nom");
							String type = result2.getString("type");
							String infos = result2.getString("infos");
							java.sql.Timestamp date = result2.getTimestamp("date_depart");
							String color = result2.getString("couleur");
							EntityController entite = new EntityController(_operation, _dbm, id, statut_id, position_id, date, nom, type, infos, color);
							_operation.addEntite(entite);
						}
						result2.getStatement().close();

					}catch(SQLException e2){e2.printStackTrace();}
				}
				_lastEntityId = id;
			}

			result.getStatement().close();

		}catch(SQLException e) {e.printStackTrace();}
		catch(MalformedQueryException e1) {e1.printStackTrace();}
	}

	public void refreshMaps(){
		System.out.println("%%%%% REFRESH MAPS LIST %%%%%");
		/* UPDATE MAPS ALREADY IN THE LIST */
		List<MapController> mapList = _operation.getMapList();

		for(MapController map : mapList){
			map.updateFields();
		}

		/* ADD NEW MAPS WHICH ARE IN THE DATABASE */

		try {
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Carte", "operation_id='"+_operation.getId()+"' AND visibilite=1 AND id>"+_lastMapControllerId));

			while(result.next()){
				int id = result.getInt("id");

				if(!_operation.existsInMapList(id)){
					ResultSet result2 = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Carte", "id='"+id+"'"));
					while(result2.next()){
						String name = result2.getString("nom");
						Boolean visibility = result2.getBoolean("visibilite");
						MapController currentMap = _operation.getCurrentMap();
						new MapController(_operation, _dbm , id, name, visibility);
						_operation.setCurrentMap(currentMap);
					}
					result2.getStatement().close();
				}
			}
			result.getStatement().close();
		}catch(MalformedQueryException e1){ 
			MessagePanel errorPanel = new MessagePanel("Erreur lors de la mise � jour des cartes");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		catch(SQLException e2){
			MessagePanel errorPanel = new MessagePanel("Erreur lors de la mise � jour des cartes");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	public void refreshLocation(){
		System.out.println("%%%%% REFRESH LOCATION LIST %%%%%");
		/* UPDATE MAPS ALREADY IN THE LIST */
		List<LocationController> locationList = _operation.getLocationList();

		for(LocationController location : locationList){
			location.updateFields();
		}

		try {
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Localisation","operation_id="+_operation.getId()+" AND id>"+_lastLocationId));

			while(result.next()){
				int id = result.getInt("id");

				if(!_operation.existsInLocationList(id)){
					int id_carte = result.getInt("carte_id");
					String nom = result.getString("nom");
					String description = result.getString("desc");
					float x = result.getFloat("x");
					float y = result.getFloat("y");
					
					if(_operation.getMap(id_carte) != null){
						LocationController location = new LocationController(_operation,_dbm, id, id_carte, x, y, nom, description);
						_operation.addLocation(location);
					}
				}
				_lastLocationId = id;
			}

			result.getStatement().close();
		}catch(MalformedQueryException e1){ e1.printStackTrace(); }
		catch(SQLException e2){ e2.printStackTrace(); }
	}

	public int get_lastVictimId() {
		return _lastVictimId;
	}

	public void set_lastVictimId(int _lastVictimId) {
		this._lastVictimId = _lastVictimId;
	}

	public int get_lastEntityId() {
		return _lastEntityId;
	}

	public void set_lastEntityId(int _lastEntityId) {
		this._lastEntityId = _lastEntityId;
	}

	public int get_lastTeamMemberId() {
		return _lastTeamMemberId;
	}

	public void set_lastTeamMemberId(int _lastTeamMemberId) {
		this._lastTeamMemberId = _lastTeamMemberId;
	}

	public int get_lastMapControllerId() {
		return _lastMapControllerId;
	}

	public void set_lastMapControllerId(int _lastMapControllerId) {
		this._lastMapControllerId = _lastMapControllerId;
	}

	public int get_lastLocationId() {
		return _lastLocationId;
	}

	public void set_lastLocationId(int _lastLocationId) {
		this._lastLocationId = _lastLocationId;
	}



}
