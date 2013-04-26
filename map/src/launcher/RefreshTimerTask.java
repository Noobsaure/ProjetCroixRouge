package launcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import views.ErrorMessage;

import controllers.EntityController;
import controllers.LocationController;
import controllers.MapController;
import controllers.OperationController;
import controllers.TeamMemberController;

import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQuerySelect;

/***
 * Cette classe est en charge de la v��rification toutes les 5 secondes de la base de donn��es (Pour v��rifier les
 * 
 */
public class RefreshTimerTask extends TimerTask
{
	private OperationController _operation;
	private DatabaseManager _dbm;

	private List<EntityController> _entityList = new ArrayList<EntityController>();
	private List<TeamMemberController> _teamMemberList = new ArrayList<TeamMemberController>();
	private List<MapController> _mapList = new ArrayList<MapController>();
	private List<LocationController> _locationList = new ArrayList<LocationController>();

	public RefreshTimerTask(OperationController op,DatabaseManager dbm){
		super();
		_operation = op;
		_dbm = dbm;
	}

	@Override
	public void run() {
		refreshTeamMember();
		refreshEntity();
		//refreshTeamMemberIntoEntity();
		refreshMaps();
		refreshLocation();

		//_operation.setListTeamMember(_teamMemberList);
		//_operation.setListEntity(_entityList);
		//_operation.setListMaps(_mapList);
		//_operation.setListLocation(_locationList);
		//_operation.setCurrentMap(_mapList.get(currentMap));

		_operation.notifyObservers();	
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
			result = _dbm.executeQuerySelect(new SQLQuerySelect("*","Equipier", "enActivite=1"));
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
			}
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
			result = _dbm.executeQuerySelect(new SQLQuerySelect("id", "Entite", "operation_id='"+_operation.getId()+"'"));
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

							EntityController entite = new EntityController(_operation, _dbm, id, statut_id, position_id, date, nom, type, infos);
							_operation.addEntite(entite);
						}

					}catch(SQLException e2){e2.printStackTrace();}
				}
			}

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
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Carte", "operation_id='"+_operation.getId()+"'"));

			while(result.next()){
				int id = result.getInt("id");

				if(!_operation.existsInMapList(id)){
					String name = result.getString("nom");
					Boolean visibility = result.getBoolean("visibilite");
					MapController map = new MapController(_operation, _dbm , id, name, visibility);
					System.out.println("Chargement carte "+name+" avec l'id: "+id);
					_operation.addMap(map);
				}
			}
		}catch(MalformedQueryException e1){ 
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur lors de la mise à jour des cartes");
		}
		catch(SQLException e2){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur lors de la mise à jour des cartes");
		}
	}

	/*public void refreshTeamMemberIntoEntity() {
		for(TeamMemberController teamMember : _teamMemberList){
			teamMember.updateFieldEntity();
		}
	}*/

	public void refreshLocation(){
		System.out.println("%%%%% REFRESH LOCATION LIST %%%%%");
		try {
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*", "Localisation"));

			while(result.next()){
				int id = result.getInt("id");
				int id_carte = result.getInt("carte_id");
				String nom = result.getString("nom");
				String description = result.getString("desc");
				float x = result.getFloat("x");
				float y = result.getFloat("y");

				LocationController location = new LocationController(_operation,_dbm, id, id_carte, x, y, nom, description);
				_operation.addLocation(location);

			}
		}catch(MalformedQueryException e1){ e1.printStackTrace(); }
		catch(SQLException e2){ e2.printStackTrace(); }
	}



}
