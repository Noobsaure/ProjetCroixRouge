package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import observer.Observer;
import views.CustomDialog;
import views.MessagePanel;
import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

public class VictimController {

	private Integer _id;
	private OperationController _operation;
	private DatabaseManager DatabaseManager;
	private String _nom;
	private String _prenom;
	private Timestamp _dateDeNaissance;
	private String _adresse;
	private Timestamp _datePriseEnCharge;
	private Timestamp _dateSortie;
	private boolean _petitSoin;
	private boolean _malaise;
	private boolean _traumatisme;
	private boolean _inconscience;
	private boolean _arretCardiaque;
	private String _atteinteDetails;
	private String _soin;
	private String _idAnonymat; // champ d'anonymisation d'une victime
	private EntityController _entity;
	private int _idMessageParent;
	
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
		DatabaseManager = dbm;
		_nom = nom;
		_prenom = prenom;		

		for(int i=0 ; i < motif.length ; i++){
			if (motif[i].equals("Arrêt cardiaque"))
				_arretCardiaque = true;
			else if (motif[i].equals("Inconscience"))
				_inconscience = true;
			else if (motif[i].equals("Malaise"))
				_malaise = true;
			else if (motif[i].equals("Petit soin"))
				_petitSoin = true;
			else if (motif[i].equals("Traumatisme"))
				_traumatisme = true;
		}

		_adresse = adresse;

		_dateDeNaissance = dateDeNaissance;
		java.util.Date date = new java.util.Date();
		_datePriseEnCharge = new java.sql.Timestamp(date.getTime());

		_atteinteDetails = atteinteDetails;
		_soin = soin;		
		_idAnonymat = anon;
		_entity = entity;

		try
		{
			String query = "(NULL,"+
					"NULL,'"+
					_operation.getId()+"','"+
					_entity.getId()+"','"+
					_idAnonymat+"','"+
					_nom+"','"+
					_prenom+"',"+
					((_dateDeNaissance == null) ? "NULL" : ("'" + _dateDeNaissance.toString()) + "'") +","+
					((_adresse.equals("")) ? "NULL" :("'" + _adresse + "'"))+","+
					"NULL,'"+
					_datePriseEnCharge+"',"+
					"NULL,'"+
					(_petitSoin ? 1 : 0)+"', '"+
					(_malaise ? 1 : 0)+"', '"+
					(_traumatisme ? 1 : 0)+"', '"+
					(_inconscience ? 1 : 0)+"', '"+
					(_arretCardiaque ? 1 : 0)+"', '"+
					_atteinteDetails+"', '"+
					_soin+"')";

			_id = DatabaseManager.executeQueryInsert(new SQLQueryInsert("Victime", query));
			operation.addVictim(this);
	
			genererDebutPriseEnChargeMessage();		

			_operation.setLastModified();
		}
		catch(MalformedQueryException e) { 
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Creation victime","Impossible de creer la victime \""+_idAnonymat+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}


	public VictimController(OperationController operation,
			DatabaseManager dbm, int id_victim, String idAnonymat, String nom,
			String prenom, String adresse, Timestamp dateDeNaissance,
			Timestamp dateEntree, String atteinteDetails, String soin,
			boolean petitSoin, boolean malaise, boolean traumatisme,
			boolean inconscient, boolean arretCardiaque, int entity) {
		_operation = operation;
		DatabaseManager = dbm;
		_idAnonymat = DatabaseManager.stripSlashes(idAnonymat);
		_nom = DatabaseManager.stripSlashes(nom);
		_prenom = DatabaseManager.stripSlashes(prenom);
		_dateDeNaissance = dateDeNaissance;
		_datePriseEnCharge = dateEntree;
		_atteinteDetails = DatabaseManager.stripSlashes(atteinteDetails);
		_soin = DatabaseManager.stripSlashes(soin);
		_petitSoin = petitSoin;
		_malaise = malaise;
		_traumatisme = traumatisme;
		_inconscience = inconscient;
		_arretCardiaque = arretCardiaque;
		_id = id_victim;
		_entity = _operation.getEntity(entity);
	}

	public void updateVictim(String nom, String prenom, String[] motif, String adresse, Timestamp dateDeNaissance, String atteinteDetails, String soin, String anon, EntityController entity) throws ParseException{
		_arretCardiaque = false;
		_inconscience = false;
		_malaise = false;
		_petitSoin = false;
		_traumatisme = false;
		
		for(int i=0 ; i < motif.length ; i++){
			if (motif[i].equals("Arrêt cardiaque"))
				_arretCardiaque = true;
			else if (motif[i].equals("Inconscience"))
				_inconscience = true;
			else if (motif[i].equals("Malaise"))
				_malaise = true;
			else if (motif[i].equals("Petit soin"))
				_petitSoin = true;
			else if (motif[i].equals("Traumatisme"))
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
			_entity = entity;
			genererChangementEntite();
		}

		String query = "surnom = '" + DatabaseManager.addSlashes(_idAnonymat) + "'" +
				((_nom.equals("")) ? (", nom = ''") : (", nom = '" + DatabaseManager.addSlashes(_nom) + "'")) +
				((_prenom.equals("")) ? (", prenom = ''") : (", prenom = '" + DatabaseManager.addSlashes(_prenom) + "'")) +
				((_dateDeNaissance == null) ? (", date_naissance = NULL") : (", date_naissance = '" + _dateDeNaissance.toString()) + "'") +
				((_adresse.equals("")) ? (", adresse = NULL") : (", adresse = '" + DatabaseManager.addSlashes(_adresse) + "'")) +
				", petit_soin = " + _petitSoin +
				", malaise = " + _malaise +
				", traumatisme = " + _traumatisme +
				", inconscient = " + _inconscience +
				", arret_cardiaque = " + _arretCardiaque +
				((_atteinteDetails.equals("")) ? (", atteinte_details = NULL") : (", atteinte_details = '" + DatabaseManager.addSlashes(_atteinteDetails) + "'")) +
				((_soin.equals("")) ? (", soin = NULL") : (", soin = '" + DatabaseManager.addSlashes(_soin) + "'")) +
				", entite_id = "+_entity.getId()+
				" WHERE id = " + _id;
		
		genererUpdateVictimMessage();
		try {
			DatabaseManager.executeQueryUpdate(new SQLQueryUpdate("Victime", query));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour victime \""+_idAnonymat+"\"", "Une erreur est survenue lors de la mise à jour de la victime \""+_idAnonymat+"\". Veuillez réessayez.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_operation.setLastModified();
	}

	public void finDePriseEnCharge(String motifSortie){
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datesortie = new java.sql.Timestamp(date.getTime());

		try{
			DatabaseManager.executeQueryUpdate(new SQLQueryUpdate("Victime","date_sortie='"+datesortie+"',motif_sortie='"+DatabaseManager.addSlashes(motifSortie)+"'","id="+_id));
		}catch(MalformedQueryException e){
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Fin de prise en charge", "La fin de prise en charge de la victime \""+_idAnonymat+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		genererFinDePriseEnCharge();
		_operation.setLastModified();
	}

	private void genererChangementEntite() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		int idMessage;
		String message = "La victime \""+_idAnonymat+"\" est maintenant prise en charge par \""+DatabaseManager.addSlashes(_entity.getName())+"\".";
		try {			
			idMessage = DatabaseManager.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', '-2', '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+DatabaseManager.addSlashes(message)+"','0')"));	
			genererVictimeMessage(idMessage);
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message du changement d'entité pour la victime. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	private void genererFinDePriseEnCharge() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		int idMessage;
		
		String message = "La victime \""+_idAnonymat+"\" n'est plus prise en charge.";
		try {			
			idMessage = DatabaseManager.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', '-2', '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+DatabaseManager.addSlashes(message)+"','0')"));
			genererVictimeMessage(idMessage);
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message de fin de prise en charge. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}

		_operation.delVictim(this);
	}

	public void genererDebutPriseEnChargeMessage() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
				
		String message = "Début de prise en charge de la victime \""+_idAnonymat+"\". Informations de prise en charge: \n"+getInfosPriseEnCharge();
		try {			
			_idMessageParent = DatabaseManager.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', '-2', '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+DatabaseManager.addSlashes(message)+"','0')"));	
			genererVictimeMessage(_idMessageParent);
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la création d'une victime. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

	private void genererUpdateVictimMessage(){
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		int idMessage;
		
		String message = "Mise à jour des informations concernant la victime \""+_idAnonymat+"\". \n";
		message += getInfosPriseEnCharge();
		
		try {			
			idMessage = DatabaseManager.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,NULL,'-1','-2','"+_operation.getIdOperateur()+"', '-2', '"+_operation.getId()+"',"+_idMessageParent+",NULL,'"+datetime+"','"+DatabaseManager.addSlashes(message)+"','0')"));	
			genererVictimeMessage(idMessage);
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la création d'une victime. Message : "+message);
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}
	
	private String getInfosPriseEnCharge(){
		String infos = "Motif prise en charge : \n";
		if(_petitSoin)
			infos += "- Petits soins \n";
		if(_malaise)
			infos += "- Malaise \n";
		if(_traumatisme)
			infos += "- Traumatisme \n";
		if(_inconscience)
			infos += "- Inconscience \n";
		if(_arretCardiaque)
			infos += "- Arrêt cardiaque \n";
		if(!_atteinteDetails.equals(""))
			infos += "- Autre: "+_atteinteDetails+" \n";
		
		if(!_soin.equals(""))
			infos += "Soins prodigués : \n - "+_soin;
		
		return infos;		
	}
	
	private void genererVictimeMessage(int idMessage) {
		try {			
			DatabaseManager.executeQueryInsert(new SQLQueryInsert("victimes_messages" ,"('"+idMessage+"','"+_id+"')"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur génération message" ,"Une erreur est survenue lors de l'insertion du message pour la victime \""+_nom+"\" dans la table d'association.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
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
	
	public EntityController getEntiteAssociee() {
		return _entity;
	}

	public String getNom() {
		return _nom;
	}

	public String getPrenom() {
		return _prenom;
	}

	public Timestamp getDateDeNaissance() {
		return _dateDeNaissance;
	}

	public String getAdresse() {
		return _adresse;
	}

	public Timestamp getDateEntree() {
		return _datePriseEnCharge;
	}

	public Timestamp getDateSortie() {
		return _dateSortie;
	}

	public boolean getPetitSoin() {
		return _petitSoin;
	}

	public boolean getMalaise() {
		return _malaise;
	}

	public boolean getTraumatisme() {
		return _traumatisme;
	}

	public boolean getInconscience() {
		return _inconscience;
	}

	public boolean getArretCardiaque() {
		return _arretCardiaque;
	}


	public String getAtteinteDetails() {
		return _atteinteDetails;
	}

	public String getSoin() {
		return _soin;
	}

	public void updateFields() {
		try{
			ResultSet result = DatabaseManager.executeQuerySelect(new SQLQuerySelect("*","Victime","id = "+_id));

			while(result.next()){
				_idAnonymat = DatabaseManager.stripSlashes(result.getString("surnom"));
				_nom = DatabaseManager.stripSlashes(result.getString("nom"));
				_prenom = DatabaseManager.stripSlashes(result.getString("prenom"));
				_dateDeNaissance = result.getTimestamp("date_naissance");
				_adresse = DatabaseManager.stripSlashes(result.getString("adresse"));
				_datePriseEnCharge = result.getTimestamp("date_entree");
				_petitSoin = result.getBoolean("petit_soin");
				_malaise = result.getBoolean("malaise");
				_traumatisme = result.getBoolean("traumatisme");
				_arretCardiaque = result.getBoolean("arret_cardiaque");
				_inconscience = result.getBoolean("inconscient");
				_atteinteDetails = DatabaseManager.stripSlashes(result.getString("atteinte_details"));
				_soin = DatabaseManager.stripSlashes(result.getString("soin"));
				_dateSortie = result.getTimestamp("date_sortie");
				if(_dateSortie != null){
					_operation.delVictim(this);
				}
			}			
			result.getStatement().close();
		}catch(SQLException e){
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour victime", "Une erreur est survenue lors de la mise à jour des attributs de la victime \""+_nom+" "+_prenom+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}catch(MalformedQueryException e1){
			MessagePanel errorPanel = new MessagePanel("Erreur interne - Mise à jour victime", "Une erreur est survenue lors de la mise à jour des attributs de la victime \""+_nom+" "+_prenom+"\".");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
	}

}
