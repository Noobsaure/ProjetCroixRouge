package views;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import views.buttons.CustomButton;
import views.listeners.FinDePriseEnChargeButtonListener;

import com.toedter.calendar.JDateChooser;

import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;

public class EditVictimPanel extends AddVictimPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Editer victime";

	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _dbm;
	private SubMenuVictimPanel _subMenu;
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
	private JTextField _motifTextField;
	
	
	public EditVictimPanel(JPanel parent, SubMenuVictimPanel subMenu, OperationController operation, DatabaseManager dbm, VictimController victim)
	{
		super(parent, subMenu, operation, dbm);
		
		_parent = parent;
		_subMenu = subMenu;
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
		
		JLabel finDePriseEnChargeLabel = new JLabel("Motif fin de prise ne charge :");
		GridBagConstraints gbc_finDePriseEnChargeLabel = new GridBagConstraints();
		gbc_finDePriseEnChargeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_finDePriseEnChargeLabel.gridx = 0;
		gbc_finDePriseEnChargeLabel.gridy = 4;
		super.getIdentityPanel().add(finDePriseEnChargeLabel, gbc_finDePriseEnChargeLabel);
		
		_motifTextField = new JTextField();
		GridBagConstraints gbc_motifTextArea = new GridBagConstraints();
		gbc_motifTextArea.insets = new Insets(0, 0, 0, 5);
		gbc_motifTextArea.fill = GridBagConstraints.BOTH;
		gbc_motifTextArea.gridx = 1;
		gbc_motifTextArea.gridy = 4;
		gbc_motifTextArea.gridwidth = 3;
		super.getIdentityPanel().add(_motifTextField, gbc_motifTextArea);
		
		CustomButton finDePriseEnChargeButton = new CustomButton("Fin de prise en charge");
		finDePriseEnChargeButton.addActionListener(new FinDePriseEnChargeButtonListener(_parent, _subMenu, _victimController, this));
		super.getButtonPanel().add(finDePriseEnChargeButton);
	}
	
	public JTextField getMotifTextField()
	{
		return _motifTextField;
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
