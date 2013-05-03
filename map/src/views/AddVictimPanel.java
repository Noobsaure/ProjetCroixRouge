package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import views.buttons.CustomButton;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import controllers.EntityController;
import controllers.OperationController;

public class AddVictimPanel extends CustomPanelImpl {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 470;
	private static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	private static final Dimension DIMENSION_FORM_PANEL = new Dimension(WIDTH- 20, 200);
	public static final String TITLE = "Ajouter une victime";
	
	private MapPanel _mapPanel;
	private OperationController _operationController;
	
	private PopUpPanel _mainPanel;
	private JLabel _title;
	private JList _motifsList;
	private JTextField _detailsTextArea;
	private JTextArea _soinsTextArea;
	private JTextField _nameTextField;
	private JTextField _prenomTextField;
	private JTextField _adressTextField;
	private JDateChooser _dateDeNaissanceDatePicker;
	private JTextField _idAnonymat;
	private CustomButton _cancelButton;
	private CustomButton _okButton;
	private JPanel _buttonPanel;
	private JPanel _identityPanel;
	private JLabel entiteAssocieeLabel;
	private JComboBox _entiteAssocieeCombobox;
	
	private Map<String, EntityController> _map;
	
	
	public AddVictimPanel(MapPanel mapPanel, OperationController operation)
	{
		_mapPanel = mapPanel;
		_operationController = operation;
		_map = new HashMap<String, EntityController>();
		
		initGui();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initGui()
	{
		setLayout(null);
		setSize(new Dimension(_mapPanel.getWidth(), _mapPanel.getHeight()));
		setOpaque(false);
		
		/**************************************************************\
		 * 						Panneau principal
		\**************************************************************/
		_mainPanel = new PopUpPanel();
		_mainPanel.setSize(DIMENSION_PANEL);
		add(_mainPanel);
		/**************************************************************/
		
		/**************************************************************\
		 * 							Titre
		\**************************************************************/
		_title = new JLabel("Victime");
		_mainPanel.add(_title, BorderLayout.NORTH);
		/**************************************************************/
		
		
		/**************************************************************\
		 * 							Form Panel
		\**************************************************************/
		JPanel formPanel = new JPanel();
		formPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
		formPanel.setPreferredSize(AddVictimPanel.DIMENSION_FORM_PANEL);
		formPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		_mainPanel.add(formPanel, BorderLayout.CENTER);
		/**************************************************************/
		
		
		/**************************************************************\
		 * 							Motif
		\**************************************************************/
		JLabel motifLabel = new JLabel("Motif :");
		formPanel.add(motifLabel, "2, 2");
		
		_motifsList = new JList();
		_motifsList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Arrêt cardiaque", "Inconscience", "Malaise", "Petit soin", "Traumatisme", "(Autre motif)"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		formPanel.add(_motifsList, "2, 4, fill, fill");
		/**************************************************************/
		
		
		/**************************************************************\
		 * 							Details
		\**************************************************************/
		JLabel otherMotifLabel = new JLabel("Autre motif :");
		formPanel.add(otherMotifLabel, "4, 2");
		
		JPanel panel = new JPanel();
		formPanel.add(panel, "4, 4");
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		_detailsTextArea = new JTextField();
		panel.add(_detailsTextArea);
		
		JLabel lblIdentifiantDanonymat = new JLabel("Identifiant d'anonymat :");
//		lblIdentifiantDanonymat.setBorder(new EmptyBorder(0, -10, 0, 0));
		panel.add(lblIdentifiantDanonymat);
		
		_idAnonymat = new JTextField();
		_idAnonymat.setText("Victime" + _operationController.getAnonymousNumber());
		panel.add(_idAnonymat);
		_idAnonymat.setColumns(10);
		
		JLabel soinsLabel = new JLabel("Soins:");
		panel.add(soinsLabel);
		
		_soinsTextArea = new JTextArea();
		_soinsTextArea.setPreferredSize(new Dimension(200, 500));
		panel.add(_soinsTextArea);
		
		JScrollPane scrollPane = new JScrollPane(_soinsTextArea);
		panel.add(scrollPane);
		/**************************************************************/
		
		
		/**************************************************************\
		 * 							Identity Panel
		\**************************************************************/
		_identityPanel = new JPanel();
		_identityPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229), 1, true), "Compl\u00E9ment d'informations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0};
		_identityPanel.setLayout(gridBagLayout);
		_mainPanel.add(_identityPanel);
		
		JLabel nameLabel = new JLabel("Nom :");
		
		_nameTextField = new JTextField();
		_nameTextField.setColumns(10);
		
		JLabel prenomLabel = new JLabel("Prénom :");
		
		_prenomTextField = new JTextField();
		_prenomTextField.setColumns(10);

		JLabel adresseLabel = new JLabel("Adresse :");
		
		_adressTextField = new JTextField();
		_adressTextField.setColumns(10);
//		_dateArriveeTextField.setColumns(10);
		
		int anchor = GridBagConstraints.WEST;
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = anchor;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		_identityPanel.add(nameLabel, gbc_nameLabel);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		_identityPanel.add(_nameTextField, gbc_nameTextField);
		GridBagConstraints gbc_prenomLabel = new GridBagConstraints();
		gbc_prenomLabel.anchor = anchor;
		gbc_prenomLabel.insets = new Insets(0, 0, 5, 5);
		gbc_prenomLabel.gridx = 2;
		gbc_prenomLabel.gridy = 0;
		_identityPanel.add(prenomLabel, gbc_prenomLabel);
		GridBagConstraints gbc_prenomTextField = new GridBagConstraints();
		gbc_prenomTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_prenomTextField.insets = new Insets(0, 0, 5, 0);
		gbc_prenomTextField.gridx = 3;
		gbc_prenomTextField.gridy = 0;
		_identityPanel.add(_prenomTextField, gbc_prenomTextField);
		GridBagConstraints gbc_adresseLabel = new GridBagConstraints();
		gbc_adresseLabel.anchor = anchor;
		gbc_adresseLabel.insets = new Insets(0, 0, 5, 5);
		gbc_adresseLabel.gridx = 0;
		gbc_adresseLabel.gridy = 1;
		_identityPanel.add(adresseLabel, gbc_adresseLabel);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridwidth = 3;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		_identityPanel.add(_adressTextField, gbc_textField);
		
		JLabel dateDeNaissanceLabel = new JLabel("Date de naissance :");
		GridBagConstraints gbc_dateDeNaissanceLabel = new GridBagConstraints();
		gbc_dateDeNaissanceLabel.anchor = anchor;
		gbc_dateDeNaissanceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dateDeNaissanceLabel.gridx = 0;
		gbc_dateDeNaissanceLabel.gridy = 2;
		_identityPanel.add(dateDeNaissanceLabel, gbc_dateDeNaissanceLabel);
		
		_dateDeNaissanceDatePicker = new JDateChooser();
		GridBagConstraints gbc_dateDeNaissanceTextField = new GridBagConstraints();
		gbc_dateDeNaissanceTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dateDeNaissanceTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateDeNaissanceTextField.gridx = 1;
		gbc_dateDeNaissanceTextField.gridy = 2;
		gbc_dateDeNaissanceTextField.gridwidth = 2;
		_identityPanel.add(_dateDeNaissanceDatePicker, gbc_dateDeNaissanceTextField);
		
		entiteAssocieeLabel = new JLabel("Entité associée :");
		GridBagConstraints gbc_entiteAssocieeLabel = new GridBagConstraints();
		gbc_entiteAssocieeLabel.anchor = anchor;
		gbc_entiteAssocieeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_entiteAssocieeLabel.gridx = 0;
		gbc_entiteAssocieeLabel.gridy = 3;
		_identityPanel.add(entiteAssocieeLabel, gbc_entiteAssocieeLabel);
		
		Object[] entitiesListObjects = _operationController.getEntityList().toArray();
		String[] entitiesList = new String[entitiesListObjects.length];
		for(int i = 0; i < entitiesListObjects.length; i++)
		{
			EntityController entityController = ((EntityController)entitiesListObjects[i]);
			String string = entityController.getName();
			entitiesList[i] = string;
			_map.put(string, entityController);
		}
			_entiteAssocieeCombobox = new JComboBox(entitiesList);
		GridBagConstraints gbc_entiteAssocieeCombobox = new GridBagConstraints();
		gbc_entiteAssocieeCombobox.insets = new Insets(0, 0, 0, 5);
		gbc_entiteAssocieeCombobox.fill = GridBagConstraints.HORIZONTAL;
		gbc_entiteAssocieeCombobox.gridx = 1;
		gbc_entiteAssocieeCombobox.gridy = 3;
		gbc_entiteAssocieeCombobox.gridwidth = 2;
		_identityPanel.add(_entiteAssocieeCombobox, gbc_entiteAssocieeCombobox);
		/**************************************************************/

		
		
		
		/**************************************************************\
		 * 						Panneau de boutons
		\**************************************************************/
		_buttonPanel = new JPanel();
		_buttonPanel.setPreferredSize(new Dimension(400, 35));
		_mainPanel.add(_buttonPanel);
		
		_cancelButton = new CustomButton("Annuler");
		_buttonPanel.add(_cancelButton);
		
		_okButton = new CustomButton("Modifier");
		_okButton.setText("Ok");
		_buttonPanel.add(_okButton);
		/**************************************************************/
		
		setPreferredSize(_mainPanel.getSize());
	}
	
	public void addCancelButtonListener(ActionListener listener)
	{
		_cancelButton.addActionListener(listener);
	}
	
	public void addOkButtonListener(ActionListener listener)
	{
		_okButton.addActionListener(listener);
	}
	
	public static String getTitle()
	{
		return TITLE;
	}
	
	public JPanel getButtonPanel()
	{
		return _buttonPanel;
	}
	
	public JPanel getIdentityPanel()
	{
		return _identityPanel;
	}
	
	public JComboBox getEntiteAssocieeCombobox()
	{
		return _entiteAssocieeCombobox;
	}
		

	public PopUpPanel getMainPanel()
	{
		return _mainPanel;
	}
	public JList getMotifList()
	{
		return _motifsList;
	}
	public JTextField getDetailsTextArea()
	{
		return _detailsTextArea;
	}
	public JTextArea getSoins()
	{
		return _soinsTextArea;
	}
	public JTextField getNameTextField()
	{
		return _nameTextField;
	}
	public JTextField getPrenomTextField()
	{
		return _prenomTextField;
	}
	public JTextField getAdressTextField()
	{
		return _adressTextField;
	}
	public JDateChooser getDateDeNaissanceDatePicker()
	{
		return _dateDeNaissanceDatePicker;
	}
	public JTextField getIdAnonymat()
	{
		return _idAnonymat;
	}
	
	public Map<String, EntityController> getMap()
	{
		return _map;
	}

	@Override
	public void updatePanel() {
		//Pas d'update à faire ici.
	}
}
