package views;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import views.buttons.CustomButton;
import views.listeners.FinDePriseEnChargeButtonListener;

import com.toedter.calendar.JDateChooser;

import controllers.EntityController;
import controllers.OperationController;
import controllers.VictimController;
import database.DatabaseManager;

public class EditVictimPanel extends AddVictimPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "Editer victime";

	private MapPanel _mapPanel;
	private SubMenuVictimPanel _subMenu;
	private VictimController _victimController;
	
	private JList _motifsList;
	private JTextField _detailsTextArea;
	private JTextField _idAnonymat;
	private JTextArea _soinsTextArea;
	private JTextField _nameTextField;
	private JTextField _prenomTextField;
	private JTextField _adressTextField;
	private JDateChooser _dateDeNaissanceDatePicker;
	private JTextField _motifTextField;
	private JComboBox _entiteAssociee;
	
	
	public EditVictimPanel(MapPanel mapPanel, SubMenuVictimPanel subMenu, OperationController operation, DatabaseManager dbm, VictimController victim)
	{
		super(mapPanel, operation);
		
		_mapPanel = mapPanel;
		_subMenu = subMenu;
		_victimController = victim;
		
		_motifsList = super.getMotifList();
		_detailsTextArea = super.getDetailsTextArea();
		_idAnonymat = super.getIdAnonymat();
		_soinsTextArea = super.getSoins();
		_nameTextField = super.getNameTextField();
		_prenomTextField = super.getPrenomTextField();
		_adressTextField = super.getAdressTextField();
		_dateDeNaissanceDatePicker = super.getDateDeNaissanceDatePicker();
		_entiteAssociee = super.getEntiteAssocieeCombobox();
		
		initFields();
	}
	
	private void initFields()
	{
		_motifsList.setSelectionModel(new DefaultListSelectionModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
		    public void setSelectionInterval(int index0, int index1)
		    {
		        if(super.isSelectedIndex(index0))
		            super.removeSelectionInterval(index0, index1);
		        else
		            super.addSelectionInterval(index0, index1);
		    }
		});
		
		if(_victimController.getArretCardiaque())
			_motifsList.setSelectedIndex(0);
		
		if(_victimController.getInconscience())
			_motifsList.setSelectedIndex(1);
		
		if(_victimController.getMalaise())
			_motifsList.setSelectedIndex(2);
		
		if(_victimController.getPetitSoin())
			_motifsList.setSelectedIndex(3);
		
		if(_victimController.getTraumatisme())
			_motifsList.setSelectedIndex(4);
		
		if(!_victimController.getArretCardiaque() && !_victimController.getInconscience() && !_victimController.getMalaise() && !_victimController.getPetitSoin() && !_victimController.getTraumatisme())
			_motifsList.setSelectedIndex(5);
		
		_detailsTextArea.setText(_victimController.getAtteinteDetails());
		_idAnonymat.setText(_victimController.getIdAnonymat());
		_soinsTextArea.setText(_victimController.getSoin());
		_nameTextField.setText(_victimController.getNom());
		_prenomTextField.setText(_victimController.getPrenom());
		_adressTextField.setText(_victimController.getAdresse());
		_dateDeNaissanceDatePicker.setDate(_victimController.getDateDeNaissance());
		
		Map<String, EntityController> map = super.getMap();
		Set<String> keys = map.keySet();
		EntityController entiteAssociee = _victimController.getEntiteAssociee();
		String stringEntiteAssociee = "";
		for(String key : keys)
		{
			if(map.get(key) == entiteAssociee)
				stringEntiteAssociee = key;
		}
		
		for(int i = 0; i < _entiteAssociee.getItemCount(); i++)
		{
			if((String)_entiteAssociee.getItemAt(i) == stringEntiteAssociee)
				_entiteAssociee.setSelectedItem(stringEntiteAssociee);
		}
		
		JLabel finDePriseEnChargeLabel = new JLabel("Motif fin de prise en charge :");
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
		finDePriseEnChargeButton.addActionListener(new FinDePriseEnChargeButtonListener(_mapPanel, _subMenu, _victimController, this));
		super.getButtonPanel().add(finDePriseEnChargeButton);
	}
	
	public JTextField getMotifTextField()
	{
		return _motifTextField;
	}
}
