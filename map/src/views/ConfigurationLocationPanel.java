package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import views.buttons.CustomButton;
import controllers.LocationController;
import views.listeners.CancelConfigureLocationListener;
import views.listeners.ConfirmConfigureLocationListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.OperationController;
import database.DatabaseManager;

public class ConfigurationLocationPanel extends CustomPanelImpl {
	private static final long serialVersionUID = 1L;

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 280;
	public static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	protected static final Dimension DIMENSION_FORM_PANEL = new Dimension(380, 200);
	protected static final int ARC_WIDTH = 30;
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final String TITLE = "Modifier une localisation";
	protected static final int MIDDLE = ConfigurationLocationPanel.WIDTH / 2;

	private MapPanel _mapPanel;
	private OperationController _operationController;
	private DatabaseManager _dbm;
	
	private LocationController _locationController;
	private RoundedPanel _internalPanel;
	private JLabel _nomLabel;
	private JTextField _nomTextField;
	private JTextArea _informationsTextArea;	
	
	public ConfigurationLocationPanel(MapPanel mapPanel, OperationController operation, DatabaseManager dbm, String nom, String informations, LocationController locationController)
	{
		_mapPanel = mapPanel;
		_operationController = operation;
		_locationController = locationController;
		_dbm = dbm;
		
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
		
		_internalPanel = new RoundedPanel();
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
				RowSpec.decode("default:grow"),}));

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
		/**************************************************************/

		
		
		/**************************************************************\
		 * 							Boutons
		\**************************************************************/
		JPanel buttonPanel = new JPanel();
		_internalPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		CustomButton annulerButton = new CustomButton("Annuler");
		annulerButton.addActionListener(new CancelConfigureLocationListener(_mapPanel, this));
		buttonPanel.add(annulerButton);
		
		CustomButton okButton = new CustomButton("Ok");
		buttonPanel.add(okButton);
		okButton.addActionListener(new ConfirmConfigureLocationListener(_mapPanel, _operationController, _dbm, this, _locationController));
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
}