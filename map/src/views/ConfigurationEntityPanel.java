package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.awt.FlowLayout;
import java.awt.Rectangle;

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

	private JTextField _nameTextField;
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
		setOpaque(false);

		JPanel internalPanel = new PopUpPanel();
		internalPanel.setLocation(0, 11);
		internalPanel.setLayout(new BoxLayout(internalPanel,BoxLayout.PAGE_AXIS));
		internalPanel.setBorder(new EmptyBorder(5,5,5,5));
		add(internalPanel);

		JLabel title = new JLabel("Éditer l'entité '"+_entityController.getName()+"'", JLabel.CENTER);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(0,0,10,0));
		internalPanel.add(title);
		Dimension labelSize = new Dimension(100,25);
		Dimension fieldSize = new Dimension(240,25);
		Dimension buttonSize = new Dimension(40,25);
		
		JPanel panel_Nom = new JPanel();
		panel_Nom.setPreferredSize(new Dimension(0, 0));
		internalPanel.add(panel_Nom);
		panel_Nom.setLayout(new BorderLayout(0, 0));
		
				JLabel nameLabel = new JLabel("Nom:");
				panel_Nom.add(nameLabel, BorderLayout.WEST);
				nameLabel.setBorder(new EmptyBorder(0,5,0,5));
				nameLabel.setMinimumSize(labelSize);
				nameLabel.setMaximumSize(labelSize);
				nameLabel.setPreferredSize(labelSize);
				nameLabel.setSize(labelSize);
				
						_nameTextField = new JTextField(_entityController.getName());
						panel_Nom.add(_nameTextField, BorderLayout.CENTER);
						_nameTextField.setBorder(new EmptyBorder(0,5,0,5));
						_nameTextField.setMinimumSize(fieldSize);
						_nameTextField.setMaximumSize(fieldSize);
						_nameTextField.setPreferredSize(fieldSize);
						
								CustomButton editNameEntityButton = new CustomButton("Ok");
								panel_Nom.add(editNameEntityButton, BorderLayout.EAST);
								editNameEntityButton.setBorder(new EmptyBorder(0,5,0,5));
								editNameEntityButton.setMinimumSize(buttonSize);
								editNameEntityButton.setMaximumSize(buttonSize);
								editNameEntityButton.setPreferredSize(buttonSize);
								editNameEntityButton.addActionListener(new EditEntityNameButtonListener(_mapPanel, _entityController, this));

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
		
		JPanel panel_Localisation = new JPanel();
		panel_Localisation.setSize(new Dimension(400, 10));
		panel_Localisation.setMinimumSize(new Dimension(400, 10));
		panel_Localisation.setPreferredSize(new Dimension(0, 0));
		internalPanel.add(panel_Localisation);
		panel_Localisation.setLayout(new BorderLayout(0, 0));
		
				JLabel locLabel = new JLabel("Localisation:");
				panel_Localisation.add(locLabel, BorderLayout.WEST);
				locLabel.setBorder(new EmptyBorder(0,5,0,5));
				locLabel.setMinimumSize(labelSize);
				locLabel.setMaximumSize(labelSize);
				locLabel.setPreferredSize(new Dimension(100, 15));
				
						_typeComboBox = new JComboBox<String>(model);
						_typeComboBox.setSize(new Dimension(240, 15));
						panel_Localisation.add(_typeComboBox, BorderLayout.CENTER);
						_typeComboBox.setMinimumSize(new Dimension(240, 15));
						_typeComboBox.setMaximumSize(fieldSize);
						_typeComboBox.setPreferredSize(new Dimension(240, 15));
						
								CustomButton editLocationEntityButton = new CustomButton("Ok");
								panel_Localisation.add(editLocationEntityButton, BorderLayout.EAST);
								editLocationEntityButton.addActionListener(new EditEntityLocationButtonListener(_entityController, this));
		editLocationEntityButton.setBorder(new EmptyBorder(0,5,0,5));
		editLocationEntityButton.setMinimumSize(buttonSize);
		editLocationEntityButton.setMaximumSize(buttonSize);
		editLocationEntityButton.setPreferredSize(new Dimension(40, 15));
		
		JPanel panel_5 = new JPanel();
		internalPanel.add(panel_5);
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(400, 2));
		panel_5.add(separator);
		
		JPanel panel_Liste_Equipier = new JPanel();
		internalPanel.add(panel_Liste_Equipier);
		panel_Liste_Equipier.setLayout(new BorderLayout(0, 0));
		
				CustomButton AjoutEquipierButton = new CustomButton("Ajouter un equipier");
				panel_Liste_Equipier.add(AjoutEquipierButton, BorderLayout.SOUTH);
				AjoutEquipierButton.addActionListener(new AddEquipierButtonListener(_mapPanel,_operationController, _entityController));
				AjoutEquipierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				
						_listeEquipierPanel = new JPanel();
						
								JScrollPane scrollPane = new JScrollPane(_listeEquipierPanel);
								panel_Liste_Equipier.add(scrollPane, BorderLayout.CENTER);
								scrollPane.setMaximumSize(new Dimension(250, 200));
								scrollPane.setMinimumSize(new Dimension(250, 200));
								scrollPane.setPreferredSize(new Dimension(250, 200));
								scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
								scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
								
										_listeEquipierPanel.setLayout(new BoxLayout(_listeEquipierPanel, BoxLayout.Y_AXIS));
										
										JPanel panel_1 = new JPanel();
										panel_Liste_Equipier.add(panel_1, BorderLayout.WEST);
										panel_1.setLayout(new BorderLayout(0, 0));
										JLabel listeEquipierLabel = new JLabel("Liste Equipiers:", JLabel.LEFT);
										panel_1.add(listeEquipierLabel, BorderLayout.NORTH);
										listeEquipierLabel.setAlignmentY(Component.TOP_ALIGNMENT);
										listeEquipierLabel.setPreferredSize(new Dimension(100, 14));
										listeEquipierLabel.setAlignmentX(LEFT_ALIGNMENT);

		setEquipiersList();

		Dimension dispoPanelDimension = new Dimension(125,50);
		Dimension scrollPanelDimension = new Dimension(250,100);
		Dimension infoLabelDimension = new Dimension(250,25);

		JPanel dispoPanel = new JPanel();
		dispoPanel.setLayout(new BoxLayout(dispoPanel, BoxLayout.LINE_AXIS));

		if (_entityController.getStatut()) {
			_disponibleStatutRadioButton.setSelected(true);
			_indisponibleStatutRadioButton.setSelected(false);
		} else {
			_disponibleStatutRadioButton.setSelected(false);
			_indisponibleStatutRadioButton.setSelected(true);
		}
		
		JPanel panel_2 = new JPanel();
		internalPanel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setPreferredSize(new Dimension(400, 2));
		panel_2.add(separator_1);
		
		JPanel panel_Statut = new JPanel();
		panel_Statut.setPreferredSize(new Dimension(10, 20));
		internalPanel.add(panel_Statut);
		panel_Statut.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel_Statut.add(panel, BorderLayout.CENTER);
		panel.setMaximumSize(dispoPanelDimension);
		panel.setMinimumSize(dispoPanelDimension);
		panel.setPreferredSize(new Dimension(125, 20));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(_disponibleStatutRadioButton);

		JradioBoutonGroup.add(_disponibleStatutRadioButton);
		panel.add(_indisponibleStatutRadioButton);

		JradioBoutonGroup.add(_indisponibleStatutRadioButton);
		
		JPanel panel_3 = new JPanel();
		panel_Statut.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel statutLabel = new JLabel("Statut :");
		panel_3.add(statutLabel, BorderLayout.NORTH);
		statutLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		statutLabel.setMaximumSize(dispoPanelDimension);
		statutLabel.setMinimumSize(dispoPanelDimension);
		statutLabel.setPreferredSize(new Dimension(100, 20));

		internalPanel.add(dispoPanel);
		
		JPanel panel_Informations = new JPanel();
		internalPanel.add(panel_Informations);
		panel_Informations.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_Informations.add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
				JLabel infoLabel = new JLabel("Informations :");
				panel_4.add(infoLabel, BorderLayout.NORTH);
				infoLabel.setAlignmentY(Component.TOP_ALIGNMENT);
				infoLabel.setMaximumSize(infoLabelDimension);
				infoLabel.setMinimumSize(infoLabelDimension);
				infoLabel.setPreferredSize(new Dimension(100, 25));

		JScrollPane textAreaScrollPane = new JScrollPane();
		panel_Informations.add(textAreaScrollPane, BorderLayout.CENTER);
		textAreaScrollPane.setMaximumSize(scrollPanelDimension);
		textAreaScrollPane.setMinimumSize(scrollPanelDimension);
		textAreaScrollPane.setPreferredSize(new Dimension(250, 50));

		_informationsTextArea = new JTextArea(_entityController.getInformationsStatut());
		_informationsTextArea.setPreferredSize(new Dimension(200, 26));
		textAreaScrollPane.setViewportView(_informationsTextArea);

		CustomButton modifStatusButton = new CustomButton("Valider");
		panel_Informations.add(modifStatusButton, BorderLayout.SOUTH);
		modifStatusButton.addActionListener(new EditStatusEntityButtonListener(_entityController, this));

		/**************************************************************\
		 * 						Couleur
		\**************************************************************/

		String stringColor = _entityController.getColor();

		_colorEntity = stringToColor(stringColor);
		
		JPanel panel_7 = new JPanel();
		internalPanel.add(panel_7);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setPreferredSize(new Dimension(400, 2));
		panel_7.add(separator_2);
		
		JPanel panel_Couleur = new JPanel();
		panel_Couleur.setPreferredSize(new Dimension(0, 0));
		internalPanel.add(panel_Couleur);
		panel_Couleur.setLayout(new BorderLayout(0, 0));
						
						JPanel panel_6 = new JPanel();
						panel_Couleur.add(panel_6, BorderLayout.WEST);
						
								JLabel lblCouleur = new JLabel("Couleur :");
								panel_6.add(lblCouleur);
				
						_colorChooserPanel = new JPanel();
						_colorChooserPanel.setPreferredSize(new Dimension(10, 0));
						panel_Couleur.add(_colorChooserPanel, BorderLayout.CENTER);
						_colorChooserPanel.addMouseListener(new ColorChooserListener(_colorChooserPanel));

		_colorChooserPanel.setBackground(_colorEntity);

		CustomButton ModifColorButton = new CustomButton("Valider");
		ModifColorButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_Couleur.add(ModifColorButton, BorderLayout.SOUTH);
		ModifColorButton.addActionListener(new EditEntityColorListener(_mapPanel,this, _entityController));
		
		JPanel panel_8 = new JPanel();
		panel_8.setSize(new Dimension(0, 30));
		internalPanel.add(panel_8);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setPreferredSize(new Dimension(400, 2));
		panel_8.add(separator_3);

		JPanel buttonPanel = new JPanel();
		internalPanel.add(buttonPanel);

		CustomButton retourEquipierEntiteButton = new CustomButton("Retour");
		retourEquipierEntiteButton.addActionListener(new RetourEquipierEntityListener(this));
		buttonPanel.add(retourEquipierEntiteButton);

		/**************************************************************/

		internalPanel.setSize(new Dimension(400, 680));
		setPreferredSize(internalPanel.getSize());
	}

	public String getNewName()
	{
		return _nameTextField.getText();
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