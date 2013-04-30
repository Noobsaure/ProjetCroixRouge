package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import observer.Observer;
import views.buttons.CustomButton;
import views.listeners.AddEquipierButtonListener;
import views.listeners.ColorChooserListener;
import views.listeners.EditEntityColorListener;
import views.listeners.EditEntityNameLocalisationButtonListener;
import views.listeners.EditStatusEntityButtonListener;
import views.listeners.RemoveEquipierListener;
import views.listeners.RetourEquipierEntityListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.EntityController;
import controllers.LocationController;
import controllers.OperationController;
import controllers.TeamMemberController;


// a modifier les fenetre pop up par des messages d'erreurs => erreur message
// modifier le listing des localisations
// éviter de mettre un nom d'entité vide
public class ConfigurationEntityPanel extends JLayeredPane implements Observer, PopUpPanel
{
	private static final long serialVersionUID = 1L;

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 680;
	public static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	protected static final Dimension DIMENSION_FORM_PANEL = new Dimension(380, 480);
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final String TITLE = "Information sur l'entité";

	private MapPanel _parent;
	private EntityController _entityController;
	private OperationController _operationController;
	
	private JPanel _background;
	private RoundedPanel _internalPanel;
	private JLabel _nomLabel;
	private JTextField _nomTextField;
	private JComboBox<String> _typeComboBox;
	public ButtonGroup JradioBoutonGroup = new ButtonGroup();
	private JPanel _colorChooserPanel;
	private Color colorEntity;
	private JPanel _listeEquipierPanel;
	
	private TeamMemberController _teamController; 
	private static List<TeamMemberController> listEquipiers;
	private static List<LocationController> listLocation;

	
	JRadioButton _disponibleStatutRadioButton = new JRadioButton("Disponible");
	JRadioButton _indisponibleStatutRadioButton = new JRadioButton("Indisponible");

	JTextArea _informationsTextArea;
	
	/**
	 * @wbp.parser.constructor
	 */
	public ConfigurationEntityPanel(MapPanel parent,OperationController operationController, EntityController entityController)
	{
		_parent = parent;
		_operationController = operationController;
		_entityController = entityController;
		_entityController.addObserver(this);
		_parent.setCurrentPopUp(this);
		initGui();
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGui()
	{
		setLayout(null);
		setSize(new Dimension(_parent.getWidth(), _parent.getHeight()));
		setOpaque(false);
	
		_internalPanel = new RoundedPanel();
		_internalPanel.setSize(DIMENSION_PANEL);	
		centrer();
		add(_internalPanel, 1);
		
		JLabel title = new JLabel("Ajouter une entité");
		_internalPanel.add(title, BorderLayout.NORTH);
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(380, 600));
		_internalPanel.add(formPanel, BorderLayout.CENTER);

		formPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("100px:grow"),
				ColumnSpec.decode("270px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("100px:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("27px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		/**************************************************************\
		 * 							Nom
		\**************************************************************/
		_nomLabel = new JLabel("Nom:");
		formPanel.add(_nomLabel, "1, 2, left, default");
		
		_nomTextField = new JTextField(_entityController.getName());
		formPanel.add(_nomTextField, "2, 2");
		/**************************************************************/
		
		
		/**************************************************************\
		 * 						Localisation
		\**************************************************************/
		JLabel typeLabel = new JLabel("Localisation:");
		formPanel.add(typeLabel, "1, 4, left, default");
				 
		Vector<String> comboBoxItems = new Vector<String>();
		listLocation=_operationController.getLocationList();
		
		//System.out.println("taille liste location "+listLocation.size());
		
		 for (LocationController location : listLocation){
				comboBoxItems.add(location.getName());
		}
		  
		final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		
		
		// mmise par défaut du nom de la localisation
		model.setSelectedItem(_operationController.getLocation(_entityController.getIdPosCurrent()).getName());
		
		_typeComboBox = new JComboBox<String>(model);
		formPanel.add(_typeComboBox, "2, 4, fill, default");
		
		CustomButton editEntityButton = new CustomButton("Valider Modifier le Nom et la Localisation");
		editEntityButton.setAlignmentX(0.5f);
		editEntityButton.addActionListener(new EditEntityNameLocalisationButtonListener(_parent,_operationController, _entityController, this, (MapPanel)_parent));
		formPanel.add(editEntityButton, "2, 6");
		
		/**************************************************************/
		
		
		
		/**************************************************************\
		 * 						Liste des equipiers
		\**************************************************************/
		
		JSeparator separator = new JSeparator();
		formPanel.add(separator, "1, 8, 2, 1");
		JLabel listeEquipierLabel = new JLabel("Liste Equipiers:");
		formPanel.add(listeEquipierLabel, "1, 10, left, top");
		
		_listeEquipierPanel = new JPanel();
		_listeEquipierPanel.setPreferredSize(new Dimension(270, 160));
		_listeEquipierPanel.setMinimumSize(new Dimension(210, 160));
		_listeEquipierPanel.setSize(new Dimension(200, 140));
		formPanel.add(_listeEquipierPanel, "2, 10, center, center");
		
		_listeEquipierPanel.setLayout(new BoxLayout(_listeEquipierPanel, BoxLayout.Y_AXIS));
		
		listEquipiers= _entityController.getTeamMemberList();	
		
		for (TeamMemberController team : listEquipiers){
			JPanel nomEquipierPanel = new JPanel();
			nomEquipierPanel.setMaximumSize(new Dimension(300, 25));
			_listeEquipierPanel.add(nomEquipierPanel);
			nomEquipierPanel.setLayout(new BorderLayout(0, 0));
				
			JLabel _nomEquipierLabel = new JLabel(team.getName());
			_nomEquipierLabel.setBorder(new EmptyBorder(5, 6, 5, 0));
			nomEquipierPanel.add(_nomEquipierLabel, BorderLayout.WEST);
			_nomEquipierLabel.setPreferredSize(new Dimension(200, 16));
			_nomEquipierLabel.setSize(new Dimension(200, 16));
			_nomEquipierLabel.setMaximumSize(new Dimension(200, 16));
			_nomEquipierLabel.setMinimumSize(new Dimension(200, 16));
			
			JButton removeEquipierButton = new JButton("X");
			removeEquipierButton.setBorder(new EmptyBorder(5, 0, 5, 0));
			removeEquipierButton.addActionListener(new RemoveEquipierListener(_parent, nomEquipierPanel, _operationController, team, _entityController, this));
			nomEquipierPanel.add(removeEquipierButton, BorderLayout.EAST);
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
		}
		
		CustomButton AjoutEquipierButton = new CustomButton("Ajouter un equipier");
		AjoutEquipierButton.addActionListener(new AddEquipierButtonListener(_parent,_operationController, _entityController, this));
		AjoutEquipierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPanel.add(AjoutEquipierButton, "2, 12, fill, fill");
		
		JSeparator separator_1 = new JSeparator();
		formPanel.add(separator_1, "1, 13, 2, 1");
		
		JLabel _statusLabel = new JLabel("Status :");
		formPanel.add(_statusLabel, "1, 15");
		
		JSplitPane splitPane = new JSplitPane();
		formPanel.add(splitPane, "2, 15, fill, fill");
		_disponibleStatutRadioButton.setPreferredSize(new Dimension(73, 14));
		
		_disponibleStatutRadioButton.setSize(new Dimension(50, 23));
		splitPane.setLeftComponent(_disponibleStatutRadioButton);
		JradioBoutonGroup.add(_disponibleStatutRadioButton);
		_indisponibleStatutRadioButton.setPreferredSize(new Dimension(83, 14));
		
		_indisponibleStatutRadioButton.setSize(new Dimension(50, 23));
		splitPane.setRightComponent(_indisponibleStatutRadioButton);
		JradioBoutonGroup.add(_indisponibleStatutRadioButton);
		
		/**************************************************************\
		 * 					Buttons radio Dispo/Indispo
		\**************************************************************/
		
		System.out.println("récupération de l'entity : "+_entityController.getStatut());
		if (_entityController.getStatut()) {_disponibleStatutRadioButton.setSelected(true);
											_indisponibleStatutRadioButton.setSelected(false);}
		else {_disponibleStatutRadioButton.setSelected(false);
			  _indisponibleStatutRadioButton.setSelected(true);}
			
		/**************************************************************\
		 * 					Localisation et Information
		\**************************************************************/
		
		JLabel lblInformations = new JLabel("Informations :");
		formPanel.add(lblInformations, "1, 17, left, top");
		
		JScrollPane textAreaScrollPane = new JScrollPane();
		formPanel.add(textAreaScrollPane, "2, 17, 1, 6, fill, fill");
		
		_informationsTextArea = new JTextArea();
		textAreaScrollPane.setViewportView(_informationsTextArea);
		
		CustomButton modifStatusButton = new CustomButton("Valider Modifier status");
		modifStatusButton.addActionListener(new EditStatusEntityButtonListener(_operationController, _entityController, this));
		formPanel.add(modifStatusButton, "2, 24");
		
		JSeparator separator_2 = new JSeparator();
		formPanel.add(separator_2, "1, 26, 2, 1");
		
		/**************************************************************\
		 * 						Couleur
		\**************************************************************/
		
		JLabel lblCouleur = new JLabel("Couleur :");
		formPanel.add(lblCouleur, "1, 28");
		
		_colorChooserPanel = new JPanel();
		_colorChooserPanel.setMinimumSize(new Dimension(10, 1));
		_colorChooserPanel.setPreferredSize(new Dimension(10, 1));
		_colorChooserPanel.addMouseListener(new ColorChooserListener(_colorChooserPanel));
		
		String stringColor = _entityController.getColor();
		
		colorEntity = stringToColor(stringColor);

		_colorChooserPanel.setBackground(colorEntity);
				
		formPanel.add(_colorChooserPanel, "2, 28, fill, fill");
		
		CustomButton ModifColorButton = new CustomButton("Valider Modifier couleur");
		ModifColorButton.addActionListener(new EditEntityColorListener(_parent,_operationController, this, _entityController));
		formPanel.add(ModifColorButton, "2, 30");
		
		JSeparator separator_3 = new JSeparator();
		formPanel.add(separator_3, "1, 31, 2, 1");
		
		/**************************************************************\
		 * 						Button retour
		\**************************************************************/
		
		JPanel buttonPanel = new JPanel();
		_internalPanel.add(buttonPanel, BorderLayout.SOUTH);
				
		CustomButton retourEquipierEntiteButton = new CustomButton("Retour");
		retourEquipierEntiteButton.addActionListener(new RetourEquipierEntityListener(_parent, this,_operationController, _entityController));
		buttonPanel.add(retourEquipierEntiteButton);
		
		/**************************************************************/
	}

	

	public String getNewName()
	{
		return _nomTextField.getText();
	}
	
	/*public String localisation()
	{
		return _localisation.getSelectedIndex();
	}*/
	
	
	private Color stringToColor(String couleur){
		Color coulor = new Color(Integer.parseInt(couleur.substring(1, 3),16), Integer.parseInt(couleur.substring(3, 5),16), Integer.parseInt(couleur.substring(5, 7),16));
		return coulor;
	}
	
	public boolean getStatutDispo()
	{
		return _disponibleStatutRadioButton.isSelected();
	}
	
	public String getInformations()
	{
		return _informationsTextArea.getText();
	}
	
	public int getIndexLocation()
	{
		return _typeComboBox.getSelectedIndex();
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return _colorChooserPanel.getBackground();
	}
	

	
	private void centrer()
	{
		int x = (_parent.getWidth() / 2) - (_internalPanel.getWidth() / 2);
		int y = (_parent.getHeight() / 2) - (_internalPanel.getHeight() / 2);
		_internalPanel.setLocation(x, y);
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		setSize(new Dimension(_parent.getWidth(), _parent.getHeight()));
		
		centrer();
		
		repaint();
		revalidate();
	}

	@Override
	public void update() {
		_listeEquipierPanel.removeAll();
		listEquipiers= _entityController.getTeamMemberList();
		for (TeamMemberController team : listEquipiers){
			JPanel nomEquipierPanel = new JPanel();
			nomEquipierPanel.setMaximumSize(new Dimension(300, 25));
			_listeEquipierPanel.add(nomEquipierPanel);
			nomEquipierPanel.setLayout(new BorderLayout(0, 0));
				
			JLabel _nomEquipierLabel = new JLabel(team.getName());
			_nomEquipierLabel.setBorder(new EmptyBorder(5, 6, 5, 0));
			nomEquipierPanel.add(_nomEquipierLabel, BorderLayout.WEST);
			_nomEquipierLabel.setPreferredSize(new Dimension(200, 16));
			_nomEquipierLabel.setSize(new Dimension(200, 16));
			_nomEquipierLabel.setMaximumSize(new Dimension(200, 16));
			_nomEquipierLabel.setMinimumSize(new Dimension(200, 16));
			
			JButton removeEquipierButton = new JButton("X");
			removeEquipierButton.setBorder(new EmptyBorder(5, 0, 5, 0));
			removeEquipierButton.addActionListener(new RemoveEquipierListener(_parent, nomEquipierPanel, _operationController, team, _entityController, this));
			nomEquipierPanel.add(removeEquipierButton, BorderLayout.EAST);
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
		}
	}

	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}


}










