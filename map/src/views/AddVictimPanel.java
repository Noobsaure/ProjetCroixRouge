package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import views.buttons.CustomButton;
import views.listeners.CancelAddVictimListener;
import views.listeners.ConfirmAddVictimListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import controllers.OperationController;
import database.DatabaseManager;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;

public class AddVictimPanel extends JLayeredPane
{
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 435;
	private static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	private static final Dimension DIMENSION_FORM_PANEL = new Dimension(WIDTH- 20, 200);
	private static final String TITLE = "Ajouter une victime";
	

	private JPanel _parent;
	private OperationController _operationController;
	private DatabaseManager _dbm;
	
	private RoundedPanel _mainPanel;
	private SubMenuVictimPanel _subMenu;
	private JList _motifsList;
	private JTextArea _detailsTextArea;
	private JTextArea _soinsTextArea;
	private JTextField _nameTextField;
	private JTextField _prenomTextField;
	private JTextField _adressTextField;
	private JTextField _codePostaleTextField;
	private JTextField _villeTextField;
	private JDateChooser _dateDeNaissanceTextField;
	private JDateChooser _dateArriveeTextField;
	private JTextField _idAnonymat;
	
	
	public AddVictimPanel(JPanel parent, SubMenuVictimPanel subMenu, OperationController operation, DatabaseManager dbm)
	{
		_parent = parent;
		_subMenu = subMenu;
		_operationController = operation;
		_dbm = dbm;
		
		initGui();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private void initGui()
	{
		setSize(DIMENSION_PANEL);
		setOpaque(false);
		
		/**************************************************************\
		 * 						Panneau principal
		\**************************************************************/
		_mainPanel = new RoundedPanel();
		_mainPanel.setSize(DIMENSION_PANEL);
		add(_mainPanel);
		/**************************************************************/
		
		centrer();
		
		/**************************************************************\
		 * 							Titre
		\**************************************************************/
		JLabel title = new JLabel(AddVictimPanel.TITLE);
		_mainPanel.add(title, BorderLayout.NORTH);
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
			String[] values = new String[] {"Arrêt cardiaque", "Inconscience", "Malaise", "Petit soin", "Traumatisme", " "};
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
		
		JScrollPane scrollPane = new JScrollPane();
		formPanel.add(scrollPane, "4, 4, fill, fill");
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		_detailsTextArea = new JTextArea();
		panel.add(_detailsTextArea);
		
		JLabel lblIdentifiantDanonymat = new JLabel("Identifiant d'anonymat :");
		panel.add(lblIdentifiantDanonymat);
		
		_idAnonymat = new JTextField();
		panel.add(_idAnonymat);
		_idAnonymat.setColumns(10);
		
		JLabel soinsLabel = new JLabel("Soins:");
		panel.add(soinsLabel);
		
		_soinsTextArea = new JTextArea();
		panel.add(_soinsTextArea);
		/**************************************************************/
		
		
		/**************************************************************\
		 * 							Identity Panel
		\**************************************************************/
		JPanel identityPanel = new JPanel();
		identityPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229), 1, true), "Compl\u00E9ment d'informations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		identityPanel.setLayout(gridBagLayout);
		_mainPanel.add(identityPanel);
		
		JLabel nameLabel = new JLabel("Nom :");
		
		_nameTextField = new JTextField();
		_nameTextField.setColumns(10);
		
		JLabel prenomLabel = new JLabel("Prénom :");
		
		_prenomTextField = new JTextField();
		_prenomTextField.setColumns(10);

		JLabel adresseLabel = new JLabel("Adresse :");
		
		_adressTextField = new JTextField();
		_adressTextField.setColumns(10);
		
		JLabel codePostalLabel = new JLabel("Code postal :");
		
		_codePostaleTextField = new JTextField();
		_codePostaleTextField.setColumns(10);
		
		JLabel villeLabel = new JLabel("Ville :");
		
		_villeTextField = new JTextField();
		_villeTextField.setColumns(10);

		JLabel dateDeNaissanceLabel = new JLabel("Date de naissance :");
		
		_dateDeNaissanceTextField = new JDateChooser();
//		_dateDeNaissanceTextField.setColumns(10);
		
		JLabel dateArriveeLabel = new JLabel("Date d'arrivée :");
		
		_dateArriveeTextField = new JDateChooser();
//		_dateArriveeTextField.setColumns(10);
		
		int anchor = GridBagConstraints.WEST;
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = anchor;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		identityPanel.add(nameLabel, gbc_nameLabel);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		identityPanel.add(_nameTextField, gbc_nameTextField);
		GridBagConstraints gbc_prenomLabel = new GridBagConstraints();
		gbc_prenomLabel.anchor = anchor;
		gbc_prenomLabel.insets = new Insets(0, 0, 5, 5);
		gbc_prenomLabel.gridx = 2;
		gbc_prenomLabel.gridy = 0;
		identityPanel.add(prenomLabel, gbc_prenomLabel);
		GridBagConstraints gbc_prenomTextField = new GridBagConstraints();
		gbc_prenomTextField.insets = new Insets(0, 0, 5, 0);
		gbc_prenomTextField.gridx = 3;
		gbc_prenomTextField.gridy = 0;
		identityPanel.add(_prenomTextField, gbc_prenomTextField);
		GridBagConstraints gbc_adresseLabel = new GridBagConstraints();
		gbc_adresseLabel.anchor = anchor;
		gbc_adresseLabel.insets = new Insets(0, 0, 5, 5);
		gbc_adresseLabel.gridx = 0;
		gbc_adresseLabel.gridy = 1;
		identityPanel.add(adresseLabel, gbc_adresseLabel);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridwidth = 3;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		identityPanel.add(_adressTextField, gbc_textField);		
		GridBagConstraints gbc_codePostalLabel = new GridBagConstraints();
		gbc_codePostalLabel.anchor = anchor;
		gbc_codePostalLabel.insets = new Insets(0, 0, 5, 5);
		gbc_codePostalLabel.gridx = 0;
		gbc_codePostalLabel.gridy = 2;
		identityPanel.add(codePostalLabel, gbc_codePostalLabel);
		GridBagConstraints gbc_codePostaleTextField = new GridBagConstraints();
		gbc_codePostaleTextField = new GridBagConstraints();
		gbc_codePostaleTextField.insets = new Insets(0, 0, 5, 5);
		gbc_codePostaleTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_codePostaleTextField.gridx = 1;
		gbc_codePostaleTextField.gridy = 2;
		identityPanel.add(_codePostaleTextField, gbc_codePostaleTextField);
		GridBagConstraints gbc_villeLabel = new GridBagConstraints();
		gbc_villeLabel.anchor = anchor;
		gbc_villeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_villeLabel.gridx = 2;
		gbc_villeLabel.gridy = 2;
		identityPanel.add(villeLabel, gbc_villeLabel);
		GridBagConstraints gbc_villeTextField = new GridBagConstraints();
		gbc_villeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_villeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_villeTextField.gridx = 3;
		gbc_villeTextField.gridy = 2;
		identityPanel.add(_villeTextField, gbc_villeTextField);
		GridBagConstraints gbc_dateDeNaissanceLabel = new GridBagConstraints();
		gbc_dateDeNaissanceLabel.anchor = anchor;
		gbc_dateDeNaissanceLabel.insets = new Insets(0, 0, 0, 5);
		gbc_dateDeNaissanceLabel.gridx = 0;
		gbc_dateDeNaissanceLabel.gridy = 3;
		identityPanel.add(dateDeNaissanceLabel, gbc_dateDeNaissanceLabel);
		GridBagConstraints gbc_dateDeNaissanceTextField = new GridBagConstraints();
		gbc_dateDeNaissanceTextField.insets = new Insets(0, 0, 0, 5);
		gbc_dateDeNaissanceTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateDeNaissanceTextField.gridx = 1;
		gbc_dateDeNaissanceTextField.gridy = 3;
		identityPanel.add(_dateDeNaissanceTextField, gbc_dateDeNaissanceTextField);
		GridBagConstraints gbc_dateArriveeLabel = new GridBagConstraints();
		gbc_dateArriveeLabel.anchor = anchor;
		gbc_dateArriveeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_dateArriveeLabel.gridx = 2;
		gbc_dateArriveeLabel.gridy = 3;
		identityPanel.add(dateArriveeLabel, gbc_dateArriveeLabel);
		GridBagConstraints gbc_dateArriveeTextField = new GridBagConstraints();
		gbc_dateArriveeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateArriveeTextField.gridx = 3;
		gbc_dateArriveeTextField.gridy = 3;
		identityPanel.add(_dateArriveeTextField, gbc_dateArriveeTextField);
		/**************************************************************/

		
		
		
		/**************************************************************\
		 * 						Panneau de boutons
		\**************************************************************/
		JPanel buttonPanel = new JPanel();
		_mainPanel.add(buttonPanel);
		
		CustomButton cancelButton = new CustomButton("Annuler");
		cancelButton.addActionListener(new CancelAddVictimListener(_parent, this));
		buttonPanel.add(cancelButton);
		
		CustomButton okButton = new CustomButton("Ok");
		okButton.addActionListener(new ConfirmAddVictimListener(_parent, _subMenu, _operationController, _dbm, this));
		buttonPanel.add(okButton);
		/**************************************************************/
	}
	
	

	public JList getMotifList()
	{
		return _motifsList;
	}
	public JTextArea getDetailsTextArea()
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
	public JTextField getCodePostaleTextField()
	{
		return _codePostaleTextField;
	}
	public JTextField getVilleTextField()
	{
		return _villeTextField;
	}
	public JDateChooser getDateDeNaissanceTextField()
	{
		return _dateDeNaissanceTextField;
	}
	public JDateChooser getDateArriveeTextField()
	{
		return _dateArriveeTextField;
	}
	public JTextField getIdANonymat()
	{
		return _idAnonymat;
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
