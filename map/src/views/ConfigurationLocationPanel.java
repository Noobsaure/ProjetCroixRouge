package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import views.buttons.CustomButton;
import views.listeners.CancelConfigureLocationListener;
import views.listeners.ConfirmConfigureLocationListener;
import views.listeners.LocationChooseColor;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.LocationController;
import controllers.OperationController;
import database.DatabaseManager;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;

public class ConfigurationLocationPanel extends AddOrEditLocationPanel {
	private static final long serialVersionUID = 1L;

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 330;
	public static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	protected static final Dimension DIMENSION_FORM_PANEL = new Dimension(380, 250);
	protected static final int ARC_WIDTH = 30;
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final String TITLE = "Modifier une localisation";
	protected static final int MIDDLE = ConfigurationLocationPanel.WIDTH / 2;


	protected static final int RED = 1;
	protected static final int ORANGE = 2;
	protected static final int YELLOW = 3;
	protected static final int LIGHTGREEN = 4;
	protected static final int GREEN = 5;
	
	private MapPanel _mapPanel;
	
	private LocationController _locationController;
	private PopUpPanel _internalPanel;
	private JLabel _nomLabel;
	private JTextField _nomTextField;
	private JTextArea _informationsTextArea;	
	private JLabel _couleurTextField;
	private ButtonGroup _couleurRadioButton;
	private int _color;
	
	public ConfigurationLocationPanel(MapPanel mapPanel, OperationController operation, DatabaseManager dbm, String nom, String informations, LocationController locationController)
	{
		_mapPanel = mapPanel;
		_locationController = locationController;
		
		initGui();
		
		_nomTextField.setText(nom);
		_informationsTextArea.setText(informations);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initGui()
	{
		setLayout(null);
		setSize(new Dimension(_mapPanel.getWidth(), _mapPanel.getHeight()));
		setOpaque(false);
		
		_internalPanel = new PopUpPanel();
		_internalPanel.setSize(DIMENSION_PANEL);
		add(_internalPanel);
		
		JLabel title = new JLabel(ConfigurationLocationPanel.TITLE);
		_internalPanel.add(title, BorderLayout.NORTH);
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(ConfigurationLocationPanel.DIMENSION_FORM_PANEL);
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
				RowSpec.decode("default:grow"),
				RowSpec.decode("default:none"),}));

		/**************************************************************\
		 * 							Nom
		\**************************************************************/
		_nomLabel = new JLabel("Nom:");
		formPanel.add(_nomLabel, "1, 2, left, default");
		
		_nomTextField = new JTextField();
		formPanel.add(_nomTextField, "2, 2");
		_nomTextField.requestFocus();
		/**************************************************************/
		
		
		/**************************************************************\
		 * 						Informations
		\**************************************************************/
		JLabel informationsLabel = new JLabel("Informations:");
		formPanel.add(informationsLabel, "1, 6, left, top");
		
		JScrollPane textAreaScrollPane = new JScrollPane();
		formPanel.add(textAreaScrollPane, "2, 6, fill, fill");
		
		_informationsTextArea = new JTextArea();
		textAreaScrollPane.setViewportView(_informationsTextArea);
		
		
		/**************************************************************\
		 * 						Couleur
		\**************************************************************/
		_couleurRadioButton = new ButtonGroup();
		final int ICON_WIDTH = 30;
		final int ICON_HEIGHT = 30;
		
		ImageIcon iconRed = new ImageIcon(ConfigurationLocationPanel.class.getResource("/ui/rouge.png"));
		Image img = iconRed.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT);
		iconRed = new ImageIcon(img);
		
		ImageIcon iconOrange = new ImageIcon(ConfigurationLocationPanel.class.getResource("/ui/orange.png"));
		Image img2 = iconOrange.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT);
		iconOrange = new ImageIcon(img2);
		
		ImageIcon iconYellow = new ImageIcon(ConfigurationLocationPanel.class.getResource("/ui/jaune.png"));
		Image img3 = iconYellow.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT);
		iconYellow = new ImageIcon(img3);
		
		ImageIcon iconLightGreen = new ImageIcon(ConfigurationLocationPanel.class.getResource("/ui/lightGreen.png"));
		Image img4 = iconLightGreen.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT);
		iconLightGreen = new ImageIcon(img4);
		
		ImageIcon iconGreen = new ImageIcon(ConfigurationLocationPanel.class.getResource("/ui/vert.png"));
		Image img5 = iconGreen.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT);
		iconGreen = new ImageIcon(img5);
		
		_couleurTextField = new JLabel("Couleur:");
		formPanel.add(_couleurTextField, "1, 7");
		
		JPanel panel = new JPanel();
		formPanel.add(panel, "2, 7, default, default");
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JToggleButton rdbtnNewRadioButton = new JToggleButton(iconRed);
		rdbtnNewRadioButton.setSize(new Dimension(30,30));
		rdbtnNewRadioButton.addActionListener(new LocationChooseColor(this,RED));
		_couleurRadioButton.add(rdbtnNewRadioButton);
		panel.add(rdbtnNewRadioButton);
		
		JToggleButton rdbtnNewRadioButton_1 = new JToggleButton(iconOrange);
		rdbtnNewRadioButton_1.setSize(new Dimension(30,30));
		rdbtnNewRadioButton_1.addActionListener(new LocationChooseColor(this,ORANGE));
		_couleurRadioButton.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_1);
		
		JToggleButton rdbtnNewRadioButton_4 = new JToggleButton(iconYellow);
		rdbtnNewRadioButton_4.setSize(new Dimension(30,30));
		rdbtnNewRadioButton_4.addActionListener(new LocationChooseColor(this,YELLOW));
		rdbtnNewRadioButton_4.setSelected(true);
		_couleurRadioButton.add(rdbtnNewRadioButton_4);
		panel.add(rdbtnNewRadioButton_4);
		
		JToggleButton rdbtnNewRadioButton_2 = new JToggleButton(iconLightGreen);
		rdbtnNewRadioButton_2.setSize(new Dimension(30,30));
		rdbtnNewRadioButton_2.addActionListener(new LocationChooseColor(this,LIGHTGREEN));
		_couleurRadioButton.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton_2);
		
		JToggleButton rdbtnNewRadioButton_3 = new JToggleButton(iconGreen);
		rdbtnNewRadioButton_3.setSize(new Dimension(30,30));
		rdbtnNewRadioButton_3.addActionListener(new LocationChooseColor(this,GREEN));
		_couleurRadioButton.add(rdbtnNewRadioButton_3);
		panel.add(rdbtnNewRadioButton_3);
		
		/**************************************************************/
		
		
		/**************************************************************\
		 * 							Boutons
		\**************************************************************/
		JPanel buttonPanel = new JPanel();
		_internalPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		CustomButton annulerButton = new CustomButton("Annuler");
		annulerButton.addActionListener(new CancelConfigureLocationListener(this));
		buttonPanel.add(annulerButton);
		
		CustomButton okButton = new CustomButton("Ok");
		buttonPanel.add(okButton);
		okButton.addActionListener(new ConfirmConfigureLocationListener(_mapPanel, this, _locationController));
		/**************************************************************/
		
		setPreferredSize(_internalPanel.getSize());
	}	
	
	public String getFieldName()
	{
		return _nomTextField.getText();
	}
	
	public String getInformations()
	{
		return _informationsTextArea.getText();
	}


	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}
	
	public int getColor() {
		return _color;
	}

	public void setColor(int i) {
		_color =  i;
	}


}