package controllers;

import database.DatabaseManager;
import database.MalformedQueryException;
import database.SQLQueryInsert;
import database.SQLQueryUpdate;

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
	
	// java.sql.Timestamp Timestamp = new java.sql.Timestamp(date.getTime());

	public VictimController(OperationController operation, DatabaseManager dbm, Integer id, String nom, String prenom, String[] motif, String adresse, Timestamp dateDeNaissance, Timestamp datePriseEnCharge,  Timestamp dateSortie, String atteinteDetails, String soin, String anon) throws ParseException
	{
		_operation = operation;
		_dbm = dbm;
		_id = id;
		_nom = nom;
		_prenom = prenom;

		for(int i=0 ; i < motif.length ; i++)
		{
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
		
		_motifSortie = new String();
		_adresse = adresse;
		
		_dateDeNaissance = dateDeNaissance;
		_datePriseEnCharge = datePriseEnCharge;
		_dateSortie = dateSortie;
		
		_atteinteDetails = atteinteDetails;
		_soin = soin;
		_idAnonymat = anon;
		_statut = "";

		try
		{
			String query = "";
			int result;
			
			if(id == null)
			{
				query = "(" +
							_id + ",NULL,'"+
							_operation.getId()+"','"+
							_idAnonymat+"','"+
							_nom+"','"+
							_prenom+"',"+
							((_dateDeNaissance == null) ? "NULL" : ("'" + _dateDeNaissance.toString()) + "'") +","+
							((_adresse.equals("")) ? "NULL" : _adresse)+",'"+
							_statut+"', '"+
							_motifSortie+"', "+
							((_datePriseEnCharge == null) ? "NULL" : ("'" + _datePriseEnCharge.toString()) + "'") +", "+
							((_dateSortie == null) ? "NULL" : ("'" + _dateSortie.toString()) + "'") +", '"+
							(_petitSoin ? 1 : 0)+"', '"+
							(_malaise ? 1 : 0)+"', '"+
							(_traumatisme ? 1 : 0)+"', '"+
							(_inconscience ? 1 : 0)+"', '"+
							(_arretCardiaque ? 1 : 0)+"', '"+
							_atteinteDetails+"', '"+
							_soin+"')";
			
				result = _dbm.executeQueryInsert(new SQLQueryInsert("Victime", query));
				
				this.genererMessage("Début de prise en charge de la victime "+_idAnonymat);
				operation.addVictim(this);
			}
			else
			{
				query = "categorie_id = NULL" +
						", operation_id = " + _operation.getId() +
						", surnom = '" + _idAnonymat + "'" +
						((_nom.equals("")) ? "" : (", nom = '" + _nom + "'")) +
						((_prenom.equals("")) ? "" : (", prenom = '" + _prenom + "'")) +
						((_dateDeNaissance == null) ? "" : (", date_naissance = '" + _dateDeNaissance.toString()) + "'") +
						((_adresse.equals("")) ? "" : (", adresse = '" + _adresse + "'")) +
						((_statut.equals("")) ? "" : (", statut = '" + _statut + "'")) +
						((_motifSortie.equals("")) ? "" : (", motif_sortie = '" + _motifSortie + "'")) +
						((_datePriseEnCharge == null ) ? "" : (", date_entree = '" + _datePriseEnCharge.toString()) + "'") +
						((_dateSortie == null) ? "" : (", date_sortie = '" + _dateSortie.toString()) + "'") +
						", petit_soin = " + _petitSoin +
						", malaise = " + _malaise +
						", traumatisme = " + _traumatisme +
						", inconscient = " + _inconscience +
						", arret_cardiaque = " + _arretCardiaque +
						((_atteinteDetails.equals("")) ? "" : (", atteinte_details = '" + _atteinteDetails + "'")) +
						((_soin.equals("")) ? "" : (", soin = '" + _soin + "'")) +
						"where id = " + _id;
				result = _dbm.executeQueryUpdate(new SQLQueryUpdate("Victime", query));
			}
		
			_id = result;
			
			System.out.println("ID de la victime qui vient d'être créée :"+_id);
		}
		catch(MalformedQueryException e) { e.printStackTrace(); }
	}

	
	public VictimController(OperationController operation,
			DatabaseManager dbm, int id_victim, String statut, String idAnonymat, String nom,
			String prenom, String adresse, Timestamp dateDeNaissance,
			Timestamp dateEntree, String atteinteDetails, String soin,
			boolean petitSoin, boolean malaise, boolean traumatisme,
			boolean inconscient, boolean arretCardiaque) {
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
	
	public void genererMessage(String message) {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp datetime = new java.sql.Timestamp(date.getTime());
		try {
			// La catégorie 1 correspond à une prise en charge de victime
			if (_messageParent == -1)
			{
				int messageId = _dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,'-1','-1','-1','"+_operation.getIdOperateur()+"', '1', '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+message+"','0')"));
				_messageParent = messageId;
			}
			else
			{
				_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,NULL,'-1','-1','-1','"+_operation.getIdOperateur()+"', '1', '"+_operation.getId()+"','"+_messageParent+"',NULL,'"+datetime+"','"+message+"','0')"));
			}
		} catch (MalformedQueryException e) {
			new ErrorMessage(_operation.getGlobalPanel().getMapPanel(),"Erreur génération message" ,"Une erreur est survenue lors de la génération du message pour la création d'une victime \n"+
					"Message : "+message);
		}
		
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
	
}
