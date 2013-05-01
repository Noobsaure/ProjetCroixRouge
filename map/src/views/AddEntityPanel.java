package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import views.buttons.CustomButton;
import views.listeners.CancelAddEntityListener;
import views.listeners.ColorChooserListener;
import views.listeners.ConfirmAddEntityListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.OperationController;
import database.DatabaseManager;



public class AddEntityPanel extends JPanel implements PopUpPanel
{
	private static final long serialVersionUID = 1L;

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 290;
	public static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	protected static final Dimension DIMENSION_FORM_PANEL = new Dimension(380, 200);
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final String TITLE = "Ajouter une entité";

	private MapPanel _mapPanel;
	private OperationController _operationController;
	private DatabaseManager _dbm;
	
	private RoundedPanel _internalPanel;
	private JLabel _nomLabel;
	private JTextField _nomTextField;
	private JComboBox<String> _typeComboBox;
	private JTextArea _informationsTextArea;
	private JPanel _colorChooserPanel;
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public AddEntityPanel(MapPanel mapPanel, OperationController operation, DatabaseManager dbm)
	{
		_mapPanel = mapPanel;
		_operationController = operation;
		_dbm = dbm;
		_mapPanel.setCurrentPopUp(this);
		initGui();
	}
	
	
	public AddEntityPanel(MapPanel parent, OperationController operation, DatabaseManager dbm, String nom, String type, String informations)
	{
		_mapPanel = parent;
		_operationController = operation;
		_dbm = dbm;
		_mapPanel.setCurrentPopUp(this);
		
		initGui();
		
		_nomTextField.setText(nom);
		_typeComboBox.setSelectedIndex(0);
		_informationsTextArea.setText(informations);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGui()
	{
		setLayout(null);
		setSize(new Dimension(_mapPanel.getWidth(), _mapPanel.getHeight()));
		setOpaque(false);
		
		_internalPanel = new RoundedPanel();
		_internalPanel.setSize(DIMENSION_PANEL);
		add(_internalPanel, 0);
		
		JLabel title = new JLabel(AddEntityPanel.TITLE);
		_internalPanel.add(title, BorderLayout.NORTH);
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(AddEntityPanel.DIMENSION_FORM_PANEL);
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
		 * 							Type
		\**************************************************************/
		JLabel typeLabel = new JLabel("Type:");
		formPanel.add(typeLabel, "1, 4, left, default");
		
		Vector<String> comboBoxItems = new Vector<String>();
	    comboBoxItems.add("Interne");
	    comboBoxItems.add("Externe");
	    
		final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		_typeComboBox = new JComboBox<String>(model);
		formPanel.add(_typeComboBox, "2, 4, left, default");
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
		annulerButton.addActionListener(new CancelAddEntityListener(_mapPanel, this));
		buttonPanel.add(annulerButton);
		
		CustomButton okButton = new CustomButton("Ok");
		buttonPanel.add(okButton);
		okButton.addActionListener(new ConfirmAddEntityListener(_mapPanel, _operationController, _dbm, this));
		/**************************************************************/

		JLabel colorLabel = new JLabel("Couleur :");
		formPanel.add(colorLabel, "1, 7");
		
		_colorChooserPanel = new JPanel();
		_colorChooserPanel.addMouseListener(new ColorChooserListener(_colorChooserPanel));
		_colorChooserPanel.setBackground(Color.BLACK);
		formPanel.add(_colorChooserPanel, "2, 7, fill, fill");
		
		setPreferredSize(_internalPanel.getSize());
	}
	
	public String getFieldName()
	{
		return _nomTextField.getText();
	}
	
	public String getType()
	{
		return (String)_typeComboBox.getSelectedItem();
	}
	
	public String getInformations()
	{
		return _informationsTextArea.getText();
	}
	
	public Color getColor()
	{
		return _colorChooserPanel.getBackground();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
//		Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
//        
//        g2d.setColor(Color.BLACK);
//		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
//
//		_internalPanel.repaint();
//		_internalPanel.revalidate();		
	}


	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}
}










