package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import views.buttons.CustomButton;
import views.listeners.AddEquipierButtonListener;
import views.listeners.ColorChooserListener;
import views.listeners.EditEntityColorListener;
import views.listeners.EditEntityLocationButtonListener;
import views.listeners.EditEntityNameButtonListener;
import views.listeners.EditStatusEntityButtonListener;
import views.listeners.RemoveEquipierListener;
import views.listeners.RetourEquipierEntityListener;
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
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final String TITLE = "Informations sur l'entité";
	private MapPanel _mapPanel;
	private EntityController _entityController;
	private OperationController _operationController;

	private JTextField _nomTextField;
	public ButtonGroup JradioBoutonGroup = new ButtonGroup();
	private JPanel _colorChooserPanel;
	private Color _colorEntity;
	private JPanel _listeEquipierPanel;
	
	private static List<TeamMemberController> listEquipiers;
	private static ArrayList<LocationController> listLocations;
	
	JRadioButton _disponibleStatutRadioButton = new JRadioButton("Disponible");
	JRadioButton _indisponibleStatutRadioButton = new JRadioButton("Indisponible");

	JTextArea _informationsTextArea;

	private JComboBox<String> _typeComboBox;

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

		JPanel internalPanel = new PopUpPanel();
		internalPanel.setBorder(new EmptyBorder(5,5,5,5));
		internalPanel.setSize(DIMENSION_PANEL);
		internalPanel.setLayout(new BoxLayout(internalPanel,BoxLayout.PAGE_AXIS));
		add(internalPanel);

		JLabel title = new JLabel("Éditer entité "+_entityController.getName(), JLabel.CENTER);
		internalPanel.add(title);

		JPanel nameLocPanel = new JPanel();
		nameLocPanel.setLayout(new GridLayout(2,1));
		
		internalPanel.add(nameLocPanel);

		/**************************************************************\
		 * 							Nom
		\**************************************************************/
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		namePanel.setBorder(new EmptyBorder(5,0,5,0));
		
		JLabel nameLabel = new JLabel("Nom:");
		namePanel.add(nameLabel, BorderLayout.WEST);
		nameLabel.setPreferredSize(new Dimension(50,50));

		_nomTextField = new JTextField(_entityController.getName());
		namePanel.add(_nomTextField, BorderLayout.CENTER);
		_nomTextField.setPreferredSize(new Dimension(290,50));
		
		CustomButton editNameEntityButton = new CustomButton("Ok");
		editNameEntityButton.addActionListener(new EditEntityNameButtonListener(_mapPanel, _entityController, this));
		namePanel.add(editNameEntityButton, BorderLayout.EAST);
		editNameEntityButton.setPreferredSize(new Dimension(50,50));
		
		nameLocPanel.add(namePanel);
		
		/**************************************************************/


		/**************************************************************\
		 * 						Localisation
		\**************************************************************/
		JPanel locPanel = new JPanel();
		locPanel.setLayout(new BorderLayout());
		locPanel.setBorder(new EmptyBorder(5,0,5,0));
		
		JLabel typeLabel = new JLabel("Localisation:");
		locPanel.add(typeLabel, BorderLayout.WEST);

		Vector<String> comboBoxItems = new Vector<String>();
		
		// On récupère la list des map
		List<MapController> locatMap;
		locatMap = _operationController.getMapList();
		List<LocationController> oneMapLocations;
		listLocations = new ArrayList<LocationController>();
		listLocations.add(_operationController.getPcmLocation());
		String tmp = _operationController.getLocation(_operationController.getIdPcm()).getName();
		comboBoxItems.add(tmp);
		String selectedLocation = new String(tmp);
		int entityCurrentPosId = _entityController.getIdPosCurrent();
		for (MapController oneMap : locatMap) 
		{
			oneMapLocations =  oneMap.getLocationList();
			for (LocationController oneLocation : oneMapLocations)
			{
				listLocations.add(oneLocation);
				tmp = oneMap.getName()+" => "+oneLocation.getName();
				comboBoxItems.add(tmp);
				if(entityCurrentPosId == oneLocation.getId()) {
					selectedLocation = new String(tmp);
				}
			}
		}		

		final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);

		model.setSelectedItem(selectedLocation);

		_typeComboBox = new JComboBox<String>(model);
		locPanel.add(_typeComboBox, BorderLayout.CENTER);

		CustomButton editLocationEntityButton = new CustomButton("Ok");
		editLocationEntityButton.addActionListener(new EditEntityLocationButtonListener(_entityController, this));
		locPanel.add(editLocationEntityButton, BorderLayout.EAST);
		
		nameLocPanel.add(locPanel);
		/**************************************************************/



		/**************************************************************\
		 * 						Liste des equipiers
		\**************************************************************/

		JSeparator separator = new JSeparator();
		internalPanel.add(separator, "1, 8, 4, 1");
		JLabel listeEquipierLabel = new JLabel("Liste Equipiers:");
		internalPanel.add(listeEquipierLabel, "1, 10, left, top");
		
		_listeEquipierPanel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(_listeEquipierPanel);
		scrollPane.setPreferredSize(new Dimension(270, 160));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		internalPanel.add(scrollPane, "4, 10, center, center");

		_listeEquipierPanel.setLayout(new BoxLayout(_listeEquipierPanel, BoxLayout.Y_AXIS));

		setEquipiersList();

		CustomButton AjoutEquipierButton = new CustomButton("Ajouter un equipier");
		AjoutEquipierButton.addActionListener(new AddEquipierButtonListener(_mapPanel,_operationController, _entityController));
		AjoutEquipierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		internalPanel.add(AjoutEquipierButton, "4, 12, fill, fill");

		/**************************************************************\
		 * 					Buttons radio Dispo/Indispo
		\**************************************************************/

		JSeparator separator_1 = new JSeparator();
		internalPanel.add(separator_1);

		JLabel _statutLabel = new JLabel("Statut :");
		internalPanel.add(_statutLabel);
		
		if (_entityController.getStatut()) {_disponibleStatutRadioButton.setSelected(true);
		_indisponibleStatutRadioButton.setSelected(false);}
		else {_disponibleStatutRadioButton.setSelected(false);
		_indisponibleStatutRadioButton.setSelected(true);}
		
		JPanel panel = new JPanel();
		internalPanel.add(panel);
		panel.add(_disponibleStatutRadioButton);
		_disponibleStatutRadioButton.setPreferredSize(new Dimension(103, 14));
		
		_disponibleStatutRadioButton.setSize(new Dimension(50, 23));
		JradioBoutonGroup.add(_disponibleStatutRadioButton);
		panel.add(_indisponibleStatutRadioButton);
		_indisponibleStatutRadioButton.setPreferredSize(new Dimension(143, 14));
		
		_indisponibleStatutRadioButton.setSize(new Dimension(100, 23));
		JradioBoutonGroup.add(_indisponibleStatutRadioButton);

		JLabel lblInformations = new JLabel("Informations :");
		internalPanel.add(lblInformations);

		JScrollPane textAreaScrollPane = new JScrollPane();
		internalPanel.add(textAreaScrollPane);

		_informationsTextArea = new JTextArea(_entityController.getInformationsStatut());
		textAreaScrollPane.setViewportView(_informationsTextArea);

		CustomButton modifStatusButton = new CustomButton("Valider Modifier status");
		modifStatusButton.addActionListener(new EditStatusEntityButtonListener(_entityController, this));
		internalPanel.add(modifStatusButton);

		JSeparator separator_2 = new JSeparator();
		internalPanel.add(separator_2);

		/**************************************************************\
		 * 						Couleur
		\**************************************************************/

		JLabel lblCouleur = new JLabel("Couleur :");
		internalPanel.add(lblCouleur, "1, 28");

		_colorChooserPanel = new JPanel();
		_colorChooserPanel.setMinimumSize(new Dimension(10, 1));
		_colorChooserPanel.setPreferredSize(new Dimension(10, 1));
		_colorChooserPanel.addMouseListener(new ColorChooserListener(_colorChooserPanel));

		String stringColor = _entityController.getColor();

		_colorEntity = stringToColor(stringColor);

		_colorChooserPanel.setBackground(_colorEntity);

		internalPanel.add(_colorChooserPanel, "4, 28, fill, fill");

		CustomButton ModifColorButton = new CustomButton("Valider Modifier couleur");
		ModifColorButton.addActionListener(new EditEntityColorListener(_mapPanel,this, _entityController));
		internalPanel.add(ModifColorButton, "4, 30");

		JSeparator separator_3 = new JSeparator();
		internalPanel.add(separator_3, "1, 31, 4, 1");

		/**************************************************************\
		 * 						Button retour
		\**************************************************************/

		JPanel buttonPanel = new JPanel();
		internalPanel.add(buttonPanel);

		CustomButton retourEquipierEntiteButton = new CustomButton("Retour");
		retourEquipierEntiteButton.addActionListener(new RetourEquipierEntityListener(this));
		buttonPanel.add(retourEquipierEntiteButton);

		/**************************************************************/

		setPreferredSize(internalPanel.getSize());
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
	
	public LocationController getSelectedLocation() {
		return listLocations.get(getIndexLocation());
	}

	public Color getColor() {
		return _colorChooserPanel.getBackground();
	}
	
	public static ArrayList<LocationController> getLocationsList() {return listLocations;}
	
	private void setEquipiersList()
	{
		listEquipiers= _entityController.getTeamMemberList();	

		for (TeamMemberController team : listEquipiers){
			JPanel nomEquipierPanel = new JPanel();
			nomEquipierPanel.setMaximumSize(new Dimension(300, 25));
			_listeEquipierPanel.add(nomEquipierPanel);
			nomEquipierPanel.setLayout(new BorderLayout(0, 0));

			JLabel _nomEquipierLabel = new JLabel(team.getFirstName() + team.getName());
			_nomEquipierLabel.setBorder(new EmptyBorder(5, 6, 5, 0));
			nomEquipierPanel.add(_nomEquipierLabel, BorderLayout.WEST);

			JButton removeEquipierButton = new JButton("X");
			removeEquipierButton.setBorder(new EmptyBorder(5, 0, 5, 0));

			removeEquipierButton.addActionListener(new RemoveEquipierListener(_entityController, team));
			nomEquipierPanel.add(removeEquipierButton, BorderLayout.EAST);
			removeEquipierButton.setPreferredSize(new Dimension(40, 16));
		}
	}
	
	@Override
	public void updatePanel() {
		_listeEquipierPanel.removeAll();
		
		listEquipiers= _entityController.getTeamMemberList();	
		
		setEquipiersList();
		
		revalidate();
		repaint();
	}
}