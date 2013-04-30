package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import views.buttons.CustomButton;
import views.listeners.AddEquipierDansEntityButtonListener;
import views.listeners.CancelEquipierEquipierButtonListener;
import views.listeners.EmptyEquipierListener;
import views.listeners.RemoveEquipierListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.EntityController;
import controllers.OperationController;
import controllers.TeamMemberController;
import java.awt.Component;



public class EmptyEquipierPanel extends JLayeredPane implements PopUpPanel
{
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
	private EmptyEquipierListener emptyButton;
	
	private JPanel _background;
	private RoundedPanel _internalPanel;
	private JLabel _nomLabel;
	
	/**
	 * @wbp.parser.constructor
	 */
	
	public EmptyEquipierPanel(MapPanel parent,OperationController operationController, EntityController entityController)
	{
		_parent = parent;
		_operationController = operationController;
		_entityController=entityController;
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
		_internalPanel.setSize(new Dimension(250, 80));	
		centrer();
		add(_internalPanel, 1);
		
		_internalPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel titre = new JLabel("Il n'y a pas ou plus d'équipier disponible", JLabel.CENTER);
		_internalPanel.add(titre, BorderLayout.CENTER);
		
		CustomButton emptyButton = new CustomButton("OK");
		emptyButton.addActionListener(new EmptyEquipierListener(_parent, _operationController, _entityController, this));
		_internalPanel.add(emptyButton, BorderLayout.SOUTH);
		
		

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
		
		centrer();		
	}

	@Override
	public void updatePanel() {
		// TODO Auto-generated method stub
		
	}
}










