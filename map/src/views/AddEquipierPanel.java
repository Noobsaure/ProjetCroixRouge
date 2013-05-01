package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import views.buttons.CustomButton;
import views.listeners.AddEquipierDansEntityButtonListener;
import views.listeners.CancelEquipierEquipierButtonListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.EntityController;
import controllers.OperationController;
import controllers.TeamMemberController;


public class AddEquipierPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 290;
	public static final Dimension DIMENSION_PANEL = new Dimension(WIDTH, HEIGHT);
	protected static final Dimension DIMENSION_FORM_PANEL = new Dimension(380, 200);
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	public static String TITLE = "";

	private MapPanel _parent;
	private OperationController _operationController;
	private EntityController _entityController;
	
	
	private JPanel _background;
	private RoundedPanel _internalPanel;
	private JLabel _nomLabel;
	private JTextField _nomTextField;
	private JComboBox<String> _typeComboBox;
	private JTextArea _informationsTextArea;
	private static List<TeamMemberController> listEquipiers;
	
	/**
	 * @wbp.parser.constructor
	 */
	
	public AddEquipierPanel(MapPanel parent,OperationController operationController, EntityController entityController)
	{
		_parent = parent;
		_operationController = operationController;
		_entityController=entityController;
		initGui();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGui()
	{
		setLayout(null);
		setSize(new Dimension(_parent.getWidth(), _parent.getHeight()));
		setOpaque(false);
		
		_internalPanel = new RoundedPanel();
		_internalPanel.setSize(new Dimension(400, 180));
		add(_internalPanel, 1);
		
		TITLE = _entityController.getName();
		
		JLabel title = new JLabel("Ajout d'un equipier à l'entite");
		_internalPanel.add(title, BorderLayout.NORTH);
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(380, 100));
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
				
				RowSpec.decode("27px"),
				RowSpec.decode("default:none"),}));

		
		/**************************************************************\
		 * 					Liste de tous les equipiers
		\**************************************************************/
		JLabel EquipierLabel = new JLabel("Equipier :");
		formPanel.add(EquipierLabel, "1, 2, left, default");
		
		Vector<String> comboBoxItems = new Vector<String>();
		
		listEquipiers= _operationController.getTeamMemberAvailableList();	
		 
		System.out.println("Taille list : "+listEquipiers.size());
		
		 for (TeamMemberController team : listEquipiers){
			comboBoxItems.add(team.getFirstName() +" "+team.getName());
			System.out.println("AJOUT");
		 }
		
		final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		_typeComboBox = new JComboBox<String>(model);
		formPanel.add(_typeComboBox, "2, 2, left, default");
		
		CustomButton ajoutButton = new CustomButton("Valider l'ajout d'un �quipier � l'entit�");	
		ajoutButton.addActionListener(new AddEquipierDansEntityButtonListener(_parent, _operationController, _entityController, this));
		
		formPanel.add(ajoutButton, "2, 4");
		/**************************************************************/

		
		/**************************************************************\
		 * 							Boutons
		\**************************************************************/
		JPanel buttonPanel = new JPanel();
		_internalPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		CustomButton retourButton = new CustomButton("Retour");
		buttonPanel.add(retourButton);
		retourButton.addActionListener(new CancelEquipierEquipierButtonListener(_parent, _operationController, _entityController, this));
		
		/**************************************************************/
	
		setPreferredSize(_internalPanel.getPreferredSize());
	}		
	
	public int getIndexEquipier()
	{
		return (int)_typeComboBox.getSelectedIndex();
	}
}










