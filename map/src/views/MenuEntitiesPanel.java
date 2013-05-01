package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import observer.Observer;
import views.buttons.AddEntityButton;
import views.listeners.MenuEntitiesPanelDropTargetListener;
import views.listeners.MenuMouseListener;
import controllers.EntityController;
import controllers.OperationController;


public class MenuEntitiesPanel extends JPanel implements Observer
{	
	private static final long serialVersionUID = 1L;
	
	private GlobalPanel _globalPanel;
	private OperationController _operation;
	
	private JPanel _panelAvailable;
	private JPanel _panelUnavailable;
	private JPanel _panelDropHere;

	private List<EntityPanel> _availableEntityPanels;
	private List<EntityPanel> _unavailableEntityPanels;

	private MapPanel _mapPanel;

	private MenuMouseListener _menuListener;

	public MenuEntitiesPanel(OperationController operation, GlobalPanel globalPanel)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		_operation = operation;
		_globalPanel = globalPanel;
		_mapPanel = _globalPanel.getMapPanel();
		
		_availableEntityPanels = new ArrayList<EntityPanel>();
		_unavailableEntityPanels = new ArrayList<EntityPanel>();
		
		_panelAvailable = new JPanel();
		_panelAvailable.setLayout(new BoxLayout(_panelAvailable, BoxLayout.Y_AXIS));
		_panelAvailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelAvailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Disponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		
		_panelUnavailable = new JPanel();
		_panelUnavailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelUnavailable.setMinimumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, 0));
		_panelUnavailable.setMaximumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, Short.MAX_VALUE));
		_panelUnavailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Indisponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		
		_panelDropHere = new JPanel();
		_panelDropHere.setLayout(new BorderLayout());
		_panelDropHere.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelDropHere.setMinimumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, 150));
		_panelDropHere.setMaximumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, 150));
		_panelDropHere.setPreferredSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, 150));
		Border bevel = BorderFactory.createBevelBorder(BevelBorder.LOWERED,new Color(255,255,255),new Color(128,128,128));
		Border empty = BorderFactory.createEmptyBorder(2,2,2,2);
		Border compound = BorderFactory.createCompoundBorder(empty, bevel);
		_panelDropHere.setBorder(compound);
		
		JPanel info = new JPanel();
		JLabel info1 = new JLabel("Glisser les entités",SwingConstants.CENTER);
		JLabel info2 = new JLabel("à désaffecter ici",SwingConstants.CENTER);
		info1.setForeground(new Color(128,128,128));
		info2.setForeground(new Color(128,128,128));
		info.setBackground(Color.BLACK);
		info1.setBackground(Color.BLACK);
		info2.setBackground(Color.BLACK);
		info.add(info1);
		info.add(info2);
		_panelDropHere.add(info, BorderLayout.CENTER);
		
		_panelAvailable.setBackground(Color.BLACK);
		_panelUnavailable.setBackground(Color.BLACK);
		_panelDropHere.setBackground(Color.BLACK);
		
		add(_panelAvailable, BorderLayout.NORTH);		
		add(_panelUnavailable, BorderLayout.CENTER);		
		add(_panelDropHere, BorderLayout.SOUTH);
		
		final int WIDTH_PANEL_ADD_BUTTON = MenuPanel.LEFT_PANEL_WIDTH - 13;
		final int HEIGHT_BUTTON = 25;
		JPanel panelAddButton = new JPanel();
		panelAddButton.setLayout(new BorderLayout());
		panelAddButton.setBackground(Color.BLACK);
		panelAddButton.setBorder(new EmptyBorder(0, 10, 2, 10));
		panelAddButton.setMinimumSize(new Dimension(WIDTH_PANEL_ADD_BUTTON, HEIGHT_BUTTON));
		panelAddButton.setMaximumSize(new Dimension(WIDTH_PANEL_ADD_BUTTON, HEIGHT_BUTTON));
		AddEntityButton addButton = new AddEntityButton(_globalPanel.getMapPanel(), "+");
		panelAddButton.add(addButton);
		_panelAvailable.add(panelAddButton);
		
		List<EntityController> listEntities = _operation.getEntityList();
		for(EntityController oneEntity : listEntities) {
			if(oneEntity.isAvailable()) {
				EntityPanel panel = new EntityPanel(this, oneEntity);
				_panelAvailable.add(panel);
				_availableEntityPanels.add(panel);
			} else {
				EntityPanel panel = new EntityPanel(this, oneEntity);
				_panelUnavailable.add(panel);
				_unavailableEntityPanels.add(panel);				
			}
		}
		setOpaque(false);	
	}
	
	public void addMenuEntitiesPanelListener() {
		_menuListener = new MenuMouseListener(_mapPanel);
		addMouseListener(_menuListener);
	}
	
	public void setListEntitiesContent()
	{
		List<EntityController> listEntities = _operation.getEntityList();
		List<EntityController> availableEntities = new ArrayList<EntityController>();
		List<EntityController> unavailableEntities = new ArrayList<EntityController>();
		//On trie les entités du controleur available/unavailable.
		for(EntityController oneEntity : listEntities) {
			if(oneEntity.isAvailable()) {
				availableEntities.add(oneEntity);
			} else {
				unavailableEntities.add(oneEntity);				
			}
		}
		
		List<EntityPanel> entityPanelsToDelete = new ArrayList<EntityPanel>();
		for(EntityPanel oneEntityPanel : _availableEntityPanels) {
			if(!availableEntities.contains(oneEntityPanel.getEntityController())) {
				_panelAvailable.remove(oneEntityPanel);
				entityPanelsToDelete.add(oneEntityPanel);
			} else {
				availableEntities.remove(oneEntityPanel.getEntityController());
			}
		}
		_availableEntityPanels.removeAll(entityPanelsToDelete);
		
		for(EntityController oneEntity : availableEntities) {
			EntityPanel panel = new EntityPanel(this, oneEntity);
			_panelAvailable.add(panel);
			_availableEntityPanels.add(panel);
		}
		
		entityPanelsToDelete.clear();
		for(EntityPanel oneEntityPanel : _unavailableEntityPanels) {
			if(!unavailableEntities.contains(oneEntityPanel.getEntityController())) {
				_panelUnavailable.remove(oneEntityPanel);
				entityPanelsToDelete.add(oneEntityPanel);
			} else {
				unavailableEntities.remove(oneEntityPanel.getEntityController());
			}
		}
		_unavailableEntityPanels.removeAll(entityPanelsToDelete);
		
		for(EntityController oneEntity : unavailableEntities) {
			EntityPanel panel = new EntityPanel(this, oneEntity);
			_panelUnavailable.add(panel);
			_unavailableEntityPanels.add(panel);
		}
		
		for(EntityPanel onePanel : _availableEntityPanels) {
			onePanel.update();
		}
		
		for(EntityPanel onePanel : _unavailableEntityPanels) {
			onePanel.update();
		}
	}
	
	public void addDropTarget() {
		_panelDropHere.setDropTarget(new DropTarget(this, new MenuEntitiesPanelDropTargetListener(_globalPanel)));
	}
	
	public GlobalPanel getGlobalPanel() {return _globalPanel;}
	
	public OperationController getOperationController()
	{
		return _operation;
	}

	public void enableListeners(boolean enable) {
//		Component[] com = _panelAvailable.getComponents();
//		for (int i = 0; i < com.length; i++)
//		     com[i].enableListener(enable);
	}

	@Override
	public synchronized void update()
	{
		setListEntitiesContent();	
	}
}
