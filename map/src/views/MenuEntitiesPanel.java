package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import observer.Observer;
import views.buttons.AddEntityButton;
import views.listeners.MenuEntitiesPanelDropTargetListener;
import controllers.EntityController;
import controllers.OperationController;


public class MenuEntitiesPanel extends JPanel implements Observer
{	
	private static final long serialVersionUID = 1L;
	
	private GlobalPanel _globalPanel;
	private OperationController _operation;
	
	private JPanel _panelAvailable;
	private JPanel _panelUnavailable;

	private List<EntityPanel> _availableEntityPanels;
	private List<EntityPanel> _unavailableEntityPanels;

	private List<EntityController> _availableEntities;
	private List<EntityController> _unavailableEntities;

	public MenuEntitiesPanel(OperationController operation, GlobalPanel globalPanel)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		_operation = operation;
		_globalPanel = globalPanel;

		_availableEntities = new ArrayList<EntityController>();
		_unavailableEntities = new ArrayList<EntityController>();
		List<EntityController> listEntities = _operation.getEntityList();
		for(EntityController oneEntity : listEntities) {
			if(oneEntity.isAvailable()) {
				_availableEntities.add(oneEntity);
			} else {
				_unavailableEntities.add(oneEntity);				
			}
		}
		
		_panelAvailable = new JPanel();
		_panelAvailable.setLayout(new BoxLayout(_panelAvailable, BoxLayout.Y_AXIS));
		_panelAvailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelAvailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Disponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		_panelUnavailable = new JPanel();
		_panelUnavailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelUnavailable.setMinimumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, 0));
		_panelUnavailable.setMaximumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, Short.MAX_VALUE));
		_panelUnavailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Indisponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		
		_panelAvailable.setBackground(Color.BLACK);
		_panelUnavailable.setBackground(Color.BLACK);
		
		add(_panelAvailable, BorderLayout.CENTER);		
		add(_panelUnavailable, BorderLayout.SOUTH);
		

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
		
		for(EntityController oneEntity : _availableEntities) {
			EntityPanel panel = new EntityPanel(this, oneEntity);
			_panelAvailable.add(panel);
			_availableEntityPanels.add(panel);
		}
		
		for(EntityController oneEntity : _unavailableEntities) {
			EntityPanel panel = new EntityPanel(this, oneEntity);
			_panelUnavailable.add(panel);
			_unavailableEntityPanels.add(panel);
		}
		
		setOpaque(false);
		
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
		}
	}
	
	
	
	public void addDropTarget() {
		this.setDropTarget(new DropTarget(this, new MenuEntitiesPanelDropTargetListener(_globalPanel)));
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
