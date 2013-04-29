package controllers;

import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import observer.Observer;
import observer.Subject;

import views.ErrorMessage;

public class VictimController implements Subject {
	
	private Integer _id;
	private OperationController _operation;
	private DatabaseManager _dbm;
	private String _nom;
	private String _prenom;
	private Timestamp _dateDeNaissance;
	private String _adresse;
	private String _statut;
	private Timestamp _datePriseEnCharge;
	private Timestamp _dateSortie;
	private String _motifSortie;
	private boolean _petitSoin;
	private boolean _malaise;
	private boolean _traumatisme;
	private boolean _inconscience;
	private boolean _arretCardiaque;
	private String _atteinteDetails;
	private String _soin;
	private String _idAnonymat; // champ d'anonymisation d'une victime
	private int _messageParent;
	private EntityController _entity;
	
	private List<Observer> _listObservers = new ArrayList<Observer>();
	/**
	 * Constructor for creation of a victim which is not in the database
	 * @param operation The current operation
	 * @param name Name of the entity
	 * @param type Type of the entity
	 * @param infos Other informations about the entity
	 * @param color 
	 * @throws ParseException 
	 */
	

	public VictimController(OperationController operation, DatabaseManager dbm, String nom, String prenom, String[] motif, String adresse, Timestamp dateDeNaissance, String atteinteDetails, String soin, String anon, EntityController entity) throws ParseException
	{
		_operation = operation;
		_dbm = dbm;
		_nom = nom;
		_prenom = prenom;

		for(int i=0 ; i < motif.length ; i++){
			if (motif[i] == "Arrêt cardiaque")
				_arretCardiaque = true;
			else if (motif[i] == "Inconscience")
				_inconscience = true;
			else if (motif[i] == "Malaise")
				_malaise = true;
			else if (motif[i] == "Petit soin")
				_petitSoin = true;
			else if (motif[i] == "Traumatisme")
				_traumatisme = true;
		}
		
		_adresse = adresse;
		
		_dateDeNaissance = dateDeNaissance;
		java.util.Date date = new java.util.Date();
		_datePriseEnCharge = new java.sql.Timestamp(date.getTime());
		
		_atteinteDetails = atteinteDetails;
		_soin = soin;
		_idAnonymat = anon;
		_statut = "";
		_entity = entity;

		try
		{
			String query = "(NULL,"+
							"NULL,'"+
							_operation.getId()+"','"+
							_idAnonymat+"','"+
							_nom+"','"+
							_prenom+"',"+
							((_dateDeNaissance == null) ? "NULL" : ("'" + _dateDeNaissance.toString()) + "'") +","+
							((_adresse.equals("")) ? "NULL" :("'" + _adresse + "'"))+",'"+
							_statut+"',"+
							"NULL,'"+
							_datePriseEnCharge+"',"+
							"NULL,'"+
							(_petitSoin ? 1 : 0)+"', '"+
							(_malaise ? 1 : 0)+"', '"+
							(_traumatisme ? 1 : 0)+"', '"+
							(_inconscience ? 1 : 0)+"', '"+
							(_arretCardiaque ? 1 : 0)+"', '"+
							_atteinteDetails+"', '"+
							_soin+"','"+
							_entity.getId()+"')";
			
				_id = _dbm.executeQueryInsert(new SQLQueryInsert("Victime", query));
				operation.addVictim(this);
				genererMessage("Début de prise en charge de la victime "+_idAnonymat);
			}
			catch(MalformedQueryException e) { 
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Creation victime","Impossible de creer la victime '"+_idAnonymat+"'.");

		}
	}

	
	public VictimController(OperationController operation,
			DatabaseManager dbm, int id_victim, String statut, String idAnonymat, String nom,
			String prenom, String adresse, Timestamp dateDeNaissance,
			Timestamp dateEntree, String atteinteDetails, String soin,
			boolean petitSoin, boolean malaise, boolean traumatisme,
			boolean inconscient, boolean arretCardiaque, int entity) {
		_operation = operation;
		_dbm = dbm;
		_idAnonymat = idAnonymat;
		_nom = nom;
		_prenom = prenom;
		_dateDeNaissance = dateDeNaissance;
		_datePriseEnCharge = dateEntree;
		_atteinteDetails = atteinteDetails;
		_soin = soin;
		_petitSoin = petitSoin;
		_malaise = malaise;
		_traumatisme = traumatisme;
		_inconscience = inconscient;
		_arretCardiaque = arretCardiaque;
		_messageParent = -1;
		_id = id_victim;
		_entity = _operation.getEntity(entity);
	}

	public void updateVictim(String nom, String prenom, String[] motif, String adresse, Timestamp dateDeNaissance, String atteinteDetails, String soin, String anon, EntityController entity) throws ParseException{
		for(int i=0 ; i < motif.length ; i++){
			if (motif[i] == "Arrêt cardiaque")
				_arretCardiaque = true;
			else if (motif[i] == "Inconscience")
				_inconscience = true;
			else if (motif[i] == "Malaise")
				_malaise = true;
			else if (motif[i] == "Petit soin")
				_petitSoin = true;
			else if (motif[i] == "Traumatisme")
				_traumatisme = true;
		}
		
		_adresse = adresse;
		_nom = nom;
		_prenom = prenom;
		_dateDeNaissance = dateDeNaissance;
		_atteinteDetails = atteinteDetails;
		_soin = soin;
		_idAnonymat = anon;
		
		if(_entity.getId() != entity.getId()){
			genererChangementEntite();
			_entity = entity;
		}
		
		String query = "surnom = '" + _idAnonymat + "'" +
				((_nom.equals("")) ? "" : (", nom = '" + _nom + "'")) +
				((_prenom.equals("")) ? "" : (", prenom = '" + _prenom + "'")) +
				((_dateDeNaissance == null) ? "" : (", date_naissance = '" + _dateDeNaissance.toString()) + "'") +
				((_adresse.equals("")) ? "" : (", adresse = '" + _adresse + "'")) +
				((_statut.equals("")) ? "" : (", statut = '" + _statut + "'")) +
				", petit_soin = " + _petitSoin +
				", malaise = " + _malaise +
				", traumatisme = " + _traumatisme +
				", inconscient = " + _inconscience +
				", arret_cardiaque = " + _arretCardiaque +
				((_atteinteDetails.equals("")) ? "" : (", atteinte_details = '" + _atteinteDetails + "'")) +
				((_soin.equals("")) ? "" : (", soin = '" + _soin + "'")) +
				", entite_id = "+_entity.getId()+
				" WHERE id = " + _id;
		
		try {
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Victime", query));
		} catch (MalformedQueryException e) {
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Mise à jour victime '"+_idAnonymat+"'", "Une erreur est survenue lors de la mise à jour de la victime '"+_idAnonymat+"' \n " +
					"Veuillez réessayez.");
		}
	}

	public void finDePriseEnCharge(String motifSortie){
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datesortie = new java.sql.Timestamp(date.getTime());
		
		try{
			_dbm.executeQueryUpdate(new SQLQueryUpdate("Victime","date_sortie='"+datesortie+"',motif_sortie='"+motifSortie+"'","id="+_id));
		}catch(MalformedQueryException e){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Fin de prise en charge", "La fin de prise en charge de la victime '"+_idAnonymat+"'.");
		}
		
		genererFinDePriseEnCharge();
	}
	
	private void genererChangementEntite() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		String newLine = System.getProperty("line.separator");
		String message = "La victime '"+_idAnonymat+"' est maintenant pris en charge par "+_entity.getName()+".";
		try {			
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', NULL, '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+message+"','0')"));	
		} catch (MalformedQueryException e) {
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur génération message" ,"Une erreur est survenue lors de la génération du message du changement d'entité pour la victime "+newLine+
					"Message : "+message);
		}
	}
	
	private void genererFinDePriseEnCharge() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		String newLine = System.getProperty("line.separator");
		
		String message = "La victime '"+_idAnonymat+"' n'est plus prise en charge.";
		try {			
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', NULL, '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+message+"','0')"));	
		} catch (MalformedQueryException e) {
					new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur génération message" ,"Une erreur est survenue lors de la génération du message de fin de prise en charge."+newLine+
							"Message : "+message);
		}
		
		_operation.delVictim(this);
	}

	public void genererMessage(String message) {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		String newLine = System.getProperty("line.separator");
		try {			
			_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', NULL, '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+message+"','0')"));	
		} catch (MalformedQueryException e) {
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la création d'une victime "+newLine+
					"Message : "+message);
		}
		
	}
		
	public Integer getId() {
		return _id;
	}
	
	public String getIdAnonymat() {
		return _idAnonymat;
	}
	
	public void setIdAnonymat(String idAnonymat) {
		_idAnonymat = idAnonymat;
	}
	
	public String getNom() {
		return _nom;
	}
	
	public void setNom(String nom) {
		_nom = nom;
	}
	
	public String getPrenom() {
		return _prenom;
	}
	
	public void setPrenom(String prenom) {
		_prenom = prenom;
	}
	
	public Timestamp getDateDeNaissance() {
		return _dateDeNaissance;
	}
	
	public void setNom(Timestamp dateDeNaissance) {
		_dateDeNaissance = dateDeNaissance;
	}
	
	public String getAdresse() {
		return _adresse;
	}
	
	public void setAdresse(String adresse) {
		_adresse = adresse;
	}
	
	public String getStatut() {
		return _statut;
	}
	
	public void setStatut(String statut) {
		_statut = statut;
	}
	
	public Timestamp getDateEntree() {
		return _datePriseEnCharge;
	}
	
	public void setDateEntree(Timestamp dateEntree) {
		_datePriseEnCharge = dateEntree;
	}
	
	public Timestamp getDateSortie() {
		return _dateSortie;
	}
	
	public void setDateSortie(Timestamp dateSortie) {
		_dateSortie = dateSortie;
	}
	
	public boolean getPetitSoin() {
		return _petitSoin;
	}
	
	public void setPetitSoin(Boolean petitSoin) {
		_petitSoin = petitSoin;
	}
	
	public boolean getMalaise() {
		return _malaise;
	}
	
	public void setMalaise(Boolean malaise) {
		_malaise = malaise;
	}
	
	public boolean getTraumatisme() {
		return _traumatisme;
	}
	
	public void setTraumatisme(Boolean traumatisme) {
		_traumatisme = traumatisme;
	}
	
	public boolean getInconscience() {
		return _inconscience;
	}
	
	public void setInconscience(Boolean inconscience) {
		_inconscience = inconscience;
	}
	
	public boolean getArretCardiaque() {
		return _arretCardiaque;
	}
	
	public void setArretCardiaque(Boolean arretCardiaque) {
		_arretCardiaque = arretCardiaque;
	}
	
	public String getAtteinteDetails() {
		return _atteinteDetails;
	}
	
	public void setAtteinteDetails(String atteinteDetails) {
		_atteinteDetails = atteinteDetails;
	}
	
	public String getSoin() {
		return _soin;
	}
	
	public void setSoin(String soin) {
		_soin = soin;
	}
	

	@Override
	public void addObserver(Observer observer) {
		
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}


	public void updateFields() {
		try{
			ResultSet result = _dbm.executeQuerySelect(new SQLQuerySelect("*","Victime","id = "+_id));

			while(result.next()){
				_idAnonymat = result.getString("surnom");
				_nom = result.getString("nom");
				_prenom = result.getString("prenom");
				_dateDeNaissance = result.getTimestamp("date_naissance");
				_adresse = result.getString("adresse");
				_statut = result.getString("statut");
				_datePriseEnCharge = result.getTimestamp("date_entree");
				_petitSoin = result.getBoolean("petit_soin");
				_malaise = result.getBoolean("malaise");
				_traumatisme = result.getBoolean("traumatisme");
				_arretCardiaque = result.getBoolean("arret_cardiaque");
				_inconscience = result.getBoolean("inconscient");
				_atteinteDetails = result.getString("atteinte_details");
				_soin = result.getString("soin");
				_dateSortie = result.getTimestamp("date_sortie");
				if(_dateSortie != null){
					_operation.delVictim(this);
				}
			}			
			result.getStatement().close();
		}catch(SQLException e){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Mise à jour victime", "Une erreur est survenue lors de la mise à jour des attributs de la victime '"+_nom+" "+_prenom+"'.");
		}catch(MalformedQueryException e1){
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Mise à jour victime", "Une erreur est survenue lors de la mise à jour des attributs de la victime '"+_nom+" "+_prenom+"'.");}
	}
	
}
