package views;

import java.awt.Graphics;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;

public class EditVictimPanel extends AddVictimPanel
{
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Editer victime";

	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _dbm;
	private VictimController _victimController;
	
	private RoundedPanel _mainPanel;
	private JList _motifsList;
	private JTextArea _detailsTextArea;
	private JTextField _idAnonymat;
	private JTextArea _soinsTextArea;
	private JTextField _nameTextField;
	private JTextField _prenomTextField;
	private JTextField _adressTextField;
	private JDateChooser _dateDeNaissanceDatePicker;
	private JDateChooser _dateArriveeDatePicker;
	
	
	public EditVictimPanel(JPanel parent, SubMenuVictimPanel subMenu, OperationController operation, DatabaseManager dbm, VictimController victim)
	{
		super(parent, subMenu, operation, dbm);
		
		_parent = parent;
		_operationController = operation;
		_dbm = dbm;
		_victimController = victim;
		
		_mainPanel = super.getMainPanel();
		_motifsList = super.getMotifList();
		_detailsTextArea = super.getDetailsTextArea();
		_idAnonymat = super.getIdAnonymat();
		_soinsTextArea = super.getSoins();
		_nameTextField = super.getNameTextField();
		_prenomTextField = super.getPrenomTextField();
		_adressTextField = super.getAdressTextField();
		_dateDeNaissanceDatePicker = super.getDateDeNaissanceDatePicker();
		_dateArriveeDatePicker = super.getDatePriseEnChargeDatePicker();
		
		initFields();
	}
	
	private void initFields()
	{
		if(_victimController.getArretCardiaque())
			_motifsList.setSelectedIndex(0);
		else
		if(_victimController.getInconscience())
			_motifsList.setSelectedIndex(1);
		else
		if(_victimController.getMalaise())
			_motifsList.setSelectedIndex(2);
		else
		if(_victimController.getPetitSoin())
			_motifsList.setSelectedIndex(3);
		else
		if(_victimController.getTraumatisme())
			_motifsList.setSelectedIndex(4);
		else
			_motifsList.setSelectedIndex(5);
		
		_detailsTextArea.setText(_victimController.getAtteinteDetails());
		_idAnonymat.setText(_victimController.getIdAnonymat());
		_soinsTextArea.setText(_victimController.getSoin());
		_nameTextField.setText(_victimController.getNom());
		_prenomTextField.setText(_victimController.getPrenom());
		_adressTextField.setText(_victimController.getAdresse());
		_dateDeNaissanceDatePicker.setDate(_victimController.getDateDeNaissance());
		_dateArriveeDatePicker.setDate(_victimController.getDateEntree());
	}
	
	
	private void centrer()
	{
		int x = (_parent.getWidth() / 2) - (_mainPanel.getWidth() / 2);
		int y = (_parent.getHeight() / 2) - (_mainPanel.getHeight() / 2);
		this.setLocation(x, y);
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		centrer();
	}
}
