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
	private Timestamp _dateEntree;
	private Timestamp _dateSortie;
	private String _motifSortie;
	private boolean _petitSoin;
	private boolean _malaise;
	private boolean _traumatisme;
	private boolean _inconscience;
	private boolean _arretCardiaque;
	private String _atteinteDetails;
	private String _soin;
	private String _anon; // champ d'anonymisation d'une victime
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

	public VictimController(OperationController operation, DatabaseManager dbm, String nom, String prenom, String[] motif, String adresse, String codePostale, String ville, String dateDeNaissance, String atteinteDetails, String soin, String anon) throws ParseException{
		_operation = operation;
		_nom = nom;
		_prenom = prenom;
		_adresse = adresse + ' ' + codePostale + ' ' + ville;
		
		System.out.println("date de naissance : " + dateDeNaissance);
		
		if(!dateDeNaissance.equals("NULL"))
			_dateDeNaissance = new java.sql.Timestamp(new  SimpleDateFormat("dd/mm/YYYY").parse(dateDeNaissance).getTime());
		 
		_motifSortie = "";
		for (int i=0 ; i < motif.length ; i++) {
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
		_atteinteDetails = atteinteDetails;
		_soin = soin;
		java.util.Date date = new java.util.Date();
		_dateEntree = new java.sql.Timestamp(date.getTime());
		_anon = anon;
		_statut = "";
		int result;

		try {
			System.out.println("Création d'une victime");
			System.out.println(_operation.getId());
			System.out.println(_nom);
			System.out.println(_prenom);
			System.out.println("date de naissance : " + _dateDeNaissance);
			System.out.println(_adresse);
			System.out.println(_statut);
			System.out.println(_motifSortie);
			System.out.println(_dateEntree);
			System.out.println(_dateSortie);
			System.out.println(_petitSoin);
			System.out.println(_malaise);
			System.out.println(_traumatisme);
			System.out.println(_inconscience);
			System.out.println(_arretCardiaque);
			System.out.println(_atteinteDetails);
			System.out.println(_soin);
			
			String tNom;
			String tPrenom;
			String tAdresse;
			String tAtteinteDetails;
			String tSoin;
			tNom = (_nom == null) ? "" : _nom;
			tPrenom = (_prenom == null) ? "" : _prenom;
			tAdresse = (_adresse == null) ? "" : _adresse;
			tAtteinteDetails = (_atteinteDetails == null) ? "" : _atteinteDetails;
			tSoin = (_soin == null) ? "" : _soin;
			
			result = _dbm.executeQueryInsert(new SQLQueryInsert("Victime", "(NULL,NULL,'"+_operation.getId()+"','"+tNom+"','"+tPrenom+"','"+_dateDeNaissance+"','"+tAdresse+"','"+_statut+"', '"+_motifSortie+"', '"+_dateEntree+"', '"+_dateSortie+"', '"+_petitSoin+"', '"+_malaise+"', '"+_traumatisme+"', '"+_inconscience+"', '"+_arretCardiaque+"', '"+tAtteinteDetails+"', '"+tSoin+"')"));
			_id = result;
			System.out.println("ID de la victime qui vient d'être créée :"+_id);
		} catch (MalformedQueryException e) { e.printStackTrace(); }
		this.genererMessage("Début de prise en charge de la victime "+_anon);
		operation.addVictim(this);
	}

	/**
	 * Constructor for creation of a victim which is in the database
	 * @param operation The current operation
	 * @param name Name of the entity
	 * @param type Type of the entity
	 * @param infos Other informations about the entity
	 * @param color 
	 */
	public VictimController(OperationController operation, DatabaseManager dbm, Integer idVictime, String nom, String prenom, String[] motif, String adresse, String codePostale, String ville, String dateDeNaissance, String atteinteDetails, String soin, String motifSortie, String anon) throws ParseException{
		_nom = nom;
		_prenom = prenom;
		_adresse = adresse + ' ' + codePostale + ' ' + ville;
		_dateDeNaissance = (Timestamp) new  SimpleDateFormat("dd/mm/YYYY").parse(dateDeNaissance);
		for (int i=0 ; i < motif.length ; i++) {
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
		_atteinteDetails = atteinteDetails;
		_soin = soin;
		_messageParent = -1;
		if (!_anon.equals(anon)) {
			this.genererMessage("La victime "+_anon+" a été renommée en "+anon);
			_anon = anon;
		}
		String tStatut;
		String tNom;
		String tPrenom;
		String tAdresse;
		String tAtteinteDetails;
		String tSoin;
		tNom = (_nom == null) ? "" : _nom;
		tPrenom = (_prenom == null) ? "" : _prenom;
		tAdresse = (_adresse == null) ? "" : _adresse;
		tAtteinteDetails = (_atteinteDetails == null) ? "" : _atteinteDetails;
		tSoin = (_soin == null) ? "" : _soin;
		tStatut = (_statut == null) ? "" : _statut;
		
		int result;

		try {
			System.out.println("Mise à jour d'une victime");
			String requete = "nom="+tNom+",prenom="+tPrenom+",date_naissance="+_dateDeNaissance+",adresse="+tAdresse+",statut="+tStatut+",motif_sortie="+_motifSortie+",petit_soin="+_petitSoin+",malaise="+_malaise+",traumatisme="+_traumatisme+",incoscient="+_inconscience+",arret_cardiaque="+_arretCardiaque+",atteinte_details="+tAtteinteDetails+",soin="+tSoin+",surnom="+_anon;
			if (!motifSortie.equals(""))
			{
				_motifSortie = motifSortie;
				java.util.Date date = new java.util.Date();
				_dateSortie = new java.sql.Timestamp(date.getTime());
				requete += "motif_sortie="+_motifSortie+",date_sortie="+_dateSortie;
				operation.delVictim(this);
				this.genererMessage("Fin de prise en charge de la victime "+_anon);
			}
			result = _dbm.executeQueryUpdate(new SQLQueryUpdate("Victime", requete , "id="+_id));
			_id = result;
			System.out.println("ID de la victime qui vient d'être mis à jour :"+_id);
		} catch (MalformedQueryException e) { e.printStackTrace(); }	
	}
	
	public VictimController(OperationController operation,
			DatabaseManager dbm, int id_victim, String statut, String nom,
			String prenom, String adresse, Timestamp dateDeNaissance,
			Timestamp dateEntree, String atteinteDetails, String soin,
			boolean petitSoin, boolean malaise, boolean traumatisme,
			boolean inconscient, boolean arretCardiaque) {
		_operation = operation;
		_dbm = dbm;
		_nom = nom;
		_prenom = prenom;
		_dateDeNaissance = dateDeNaissance;
		_dateEntree = dateEntree;
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
		return _dateEntree;
	}
	
	public void setDateEntree(Timestamp dateEntree) {
		_dateEntree = dateEntree;
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
				int messageId = _dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,'-1','-1','-1','-1','"+_operation.getIdOperateur()+"', '1', '"+_operation.getId()+"',NULL,NULL,'"+datetime+"','"+message+"','0')"));
				_messageParent = messageId;
			}
			else
			{
				_dbm.executeQueryInsert(new SQLQueryInsert("Message" ,"(NULL,'-1','-1','-1','-1','"+_operation.getIdOperateur()+"', '1', '"+_operation.getId()+"','"+_messageParent+"',NULL,'"+datetime+"','"+message+"','0')"));
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
