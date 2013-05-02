package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
import controllers.MapController;
import controllers.OperationController;
import controllers.TeamMemberController;

public class ConfigurationEntityPanel extends CustomPanelImpl
{
	private static final long serialVersionUID = 1L;

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 680;
	public static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	protected static final Dimension DIMENSION_FORM_PANEL = new Dimension(380, 480);
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final String TITLE = "Information sur l'entité";
	private JPanel _parent;
	private MapPanel _mapPanel;
	private EntityController _entityController;
	private OperationController _operationController;

	private JPanel _internalPanel;
	private JLabel _nomLabel;
	private JTextField _nomTextField;
	private JComboBox<String> _typeComboBox;
	public ButtonGroup JradioBoutonGroup = new ButtonGroup();
	private JPanel _colorChooserPanel;
	private Color colorEntity;
	private JPanel _listeEquipierPanel;
	
	private static List<TeamMemberController> listEquipiers;
	
	JRadioButton _disponibleStatutRadioButton = new JRadioButton("Disponible");
	JRadioButton _indisponibleStatutRadioButton = new JRadioButton("Indisponible");

	JTextArea _informationsTextArea;

	public ConfigurationEntityPanel(MapPanel parent,OperationController operationController, EntityController entityController) {
		_mapPanel = parent;
		_operationController = operationController;
		_entityController = entityController;
		initGui();
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGui()
	{
		setLayout(null);
		setSize(new Dimension(_mapPanel.getWidth(), _mapPanel.getHeight()));
		setOpaque(false);

		_internalPanel = new PopUpPanel();
		_internalPanel.setSize(DIMENSION_PANEL);
		add(_internalPanel);

		JLabel title = new JLabel("Ajouter une entité");
		_internalPanel.add(title, BorderLayout.NORTH);

		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(380, 600));
		_internalPanel.add(formPanel, BorderLayout.CENTER);

		formPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("100px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
		formPanel.add(_nomTextField, "4, 2");
		/**************************************************************/


		/**************************************************************\
		 * 						Localisation
		\**************************************************************/
		JLabel typeLabel = new JLabel("Localisation:");
		formPanel.add(typeLabel, "1, 4, left, default");

		Vector<String> comboBoxItems = new Vector<String>();
		
		// On récupère la list des map
		List<MapController> locatMap;
		locatMap = _operationController.getMapList();
		List<LocationController> locatMaplocat;
		comboBoxItems.add(_operationController.getLocation(_operationController.getIdPcm()).getName());
		for (MapController mapController : locatMap) 
		{	
			locatMaplocat =  mapController.getLocationList();
			for (LocationController locat : locatMaplocat)
			{	
				String temp = mapController.getName()+" => "+locat.getName();
				comboBoxItems.add(temp);
			}
		}		

		final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);


		// mise par défaut du nom de la localisation
		LocationController localisationEntite = _operationController.getLocation(_entityController.getIdPosCurrent());
		String maploca = _operationController.getMap(localisationEntite.getIdMap()).getName();
		String tempLocaMap = maploca+" => "+ localisationEntite.getName();
		model.setSelectedItem(tempLocaMap);

		_typeComboBox = new JComboBox<String>(model);
		formPanel.add(_typeComboBox, "4, 4, fill, default");

		CustomButton editEntityButton = new CustomButton("Valider Modifier le Nom et la Localisation");
		editEntityButton.setAlignmentX(0.5f);
		editEntityButton.addActionListener(new EditEntityNameLocalisationButtonListener(_mapPanel,_operationController, _entityController, this));
		formPanel.add(editEntityButton, "4, 6");

		/**************************************************************/



		/**************************************************************\
		 * 						Liste des equipiers
		\**************************************************************/

		JSeparator separator = new JSeparator();
		formPanel.add(separator, "1, 8, 4, 1");
		JLabel listeEquipierLabel = new JLabel("Liste Equipiers:");
		formPanel.add(listeEquipierLabel, "1, 10, left, top");

		_listeEquipierPanel = new JPanel();
		_listeEquipierPanel.setPreferredSize(new Dimension(270, 160));
		_listeEquipierPanel.setMinimumSize(new Dimension(210, 160));
		_listeEquipierPanel.setSize(new Dimension(200, 140));
		formPanel.add(_listeEquipierPanel, "4, 10, center, center");

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
			removeEquipierButton.addActionListener(new RemoveEquipierListener(_mapPanel, nomEquipierPanel, _operationController, team, _entityController, this));
			nomEquipierPanel.add(removeEquipierButton, BorderLayout.EAST);
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
		}

		CustomButton AjoutEquipierButton = new CustomButton("Ajouter un equipier");
		AjoutEquipierButton.addActionListener(new AddEquipierButtonListener(_mapPanel,_operationController, _entityController));
		AjoutEquipierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPanel.add(AjoutEquipierButton, "4, 12, fill, fill");

		JSeparator separator_1 = new JSeparator();
		formPanel.add(separator_1, "1, 13, 4, 1");

		JLabel _statusLabel = new JLabel("Status :");
		formPanel.add(_statusLabel, "1, 15");

		/**************************************************************\
		 * 					Buttons radio Dispo/Indispo
		\**************************************************************/

		System.out.println("récupération de l'entity : "+_entityController.getStatut());
		if (_entityController.getStatut()) {_disponibleStatutRadioButton.setSelected(true);
		_indisponibleStatutRadioButton.setSelected(false);}
		else {_disponibleStatutRadioButton.setSelected(false);
		_indisponibleStatutRadioButton.setSelected(true);}
		
		JPanel panel = new JPanel();
		formPanel.add(panel, "4, 15, fill, fill");
		panel.add(_disponibleStatutRadioButton);
		_disponibleStatutRadioButton.setPreferredSize(new Dimension(103, 14));
		
		_disponibleStatutRadioButton.setSize(new Dimension(50, 23));
		JradioBoutonGroup.add(_disponibleStatutRadioButton);
		panel.add(_indisponibleStatutRadioButton);
		_indisponibleStatutRadioButton.setPreferredSize(new Dimension(143, 14));
		
		_indisponibleStatutRadioButton.setSize(new Dimension(100, 23));
		JradioBoutonGroup.add(_indisponibleStatutRadioButton);

		JLabel lblInformations = new JLabel("Informations :");
		formPanel.add(lblInformations, "1, 17, left, top");

		JScrollPane textAreaScrollPane = new JScrollPane();
		formPanel.add(textAreaScrollPane, "4, 17, 1, 6, fill, fill");

		_informationsTextArea = new JTextArea();
		textAreaScrollPane.setViewportView(_informationsTextArea);

		CustomButton modifStatusButton = new CustomButton("Valider Modifier status");
		modifStatusButton.addActionListener(new EditStatusEntityButtonListener(_entityController, this));
		formPanel.add(modifStatusButton, "4, 24");

		JSeparator separator_2 = new JSeparator();
		formPanel.add(separator_2, "1, 26, 4, 1");

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

		formPanel.add(_colorChooserPanel, "4, 28, fill, fill");

		CustomButton ModifColorButton = new CustomButton("Valider Modifier couleur");
		ModifColorButton.addActionListener(new EditEntityColorListener(_mapPanel,this, _entityController));
		formPanel.add(ModifColorButton, "4, 30");

		JSeparator separator_3 = new JSeparator();
		formPanel.add(separator_3, "1, 31, 4, 1");

		/**************************************************************\
		 * 						Button retour
		\**************************************************************/

		JPanel buttonPanel = new JPanel();
		_internalPanel.add(buttonPanel, BorderLayout.SOUTH);

		CustomButton retourEquipierEntiteButton = new CustomButton("Retour");
		retourEquipierEntiteButton.addActionListener(new RetourEquipierEntityListener(this));
		buttonPanel.add(retourEquipierEntiteButton);

		/**************************************************************/

		setPreferredSize(_internalPanel.getSize());
	}

	public String getNewName()
	{
		return _nomTextField.getText();
	}


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
		return _colorChooserPanel.getBackground();
	}
	
	@Override
	public void updatePanel() {
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
			removeEquipierButton.addActionListener(new RemoveEquipierListener(_mapPanel, nomEquipierPanel, _operationController, team, _entityController, this));
			nomEquipierPanel.add(removeEquipierButton, BorderLayout.EAST);
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
		}
		
		revalidate();
		repaint();
	}
}