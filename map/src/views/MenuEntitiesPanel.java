package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.util.ArrayList;
import java.util.Collections;
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

import views.buttons.AddEntityButton;
import views.listeners.MenuEntitiesPanelDropTargetListener;
import views.listeners.MenuMouseListener;
import controllers.EntityController;
import controllers.OperationController;

public class MenuEntitiesPanel extends JPanel {	
	private static final long serialVersionUID = 1L;

	private GlobalPanel _globalPanel;
	private OperationController _operation;

	private JPanel _panelAvailable;
	private JPanel _panelUnavailable;
	private JPanel _panelDropHere;

	private List<EntityPanel> _availableEntityPanelsList;
	private List<EntityPanel> _unavailableEntityPanelsList;

	private MapPanel _mapPanel;

	private MenuMouseListener _menuListener;

	public MenuEntitiesPanel(OperationController operation, GlobalPanel globalPanel)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		_operation = operation;
		_globalPanel = globalPanel;
		_mapPanel = _globalPanel.getMapPanel();

		_availableEntityPanelsList = new ArrayList<EntityPanel>();
		_unavailableEntityPanelsList = new ArrayList<EntityPanel>();

		_panelAvailable = new JPanel();
		_panelAvailable.setLayout(new BoxLayout(_panelAvailable, BoxLayout.Y_AXIS));
		_panelAvailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelAvailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Disponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));

		_panelUnavailable = new JPanel();
		_panelUnavailable.setLayout(new BoxLayout(_panelUnavailable, BoxLayout.Y_AXIS));
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

		Collections.sort(listEntities, new EntityComparator());

		for(EntityController oneEntity : listEntities)
		{
			if(oneEntity.isAvailable()) {
				EntityPanel panel = new EntityPanel(this, oneEntity);
				_panelAvailable.add(panel);
				_availableEntityPanelsList.add(panel);
			}
			else
			{
				EntityPanel panel = new EntityPanel(this, oneEntity);
				_panelUnavailable.add(panel);
				_unavailableEntityPanelsList.add(panel);				
			}
		}

		setOpaque(false);	
	}

	public void addMenuEntitiesPanelListener()
	{
		_menuListener = new MenuMouseListener(_mapPanel);
		addMouseListener(_menuListener);
	}

	public void setListEntitiesContent()
	{
		List<EntityController> listEntities = _operation.getEntityList();
		List<EntityController> availableEntitiesList = new ArrayList<EntityController>();
		List<EntityController> unavailableEntitiesList = new ArrayList<EntityController>();

		Collections.sort(listEntities, new EntityComparator());

		for(EntityController oneEntity : listEntities)
		{
			if(oneEntity.isAvailable())
				availableEntitiesList.add(oneEntity);
			else
				unavailableEntitiesList.add(oneEntity);				
		}

		int i;	
		boolean differenceFound = false;
		List<EntityPanel> newAvailableEntityPanelsList = new ArrayList<>();
		for(i = 0; (i < _availableEntityPanelsList.size()) && (i < availableEntitiesList.size()) && !differenceFound; i++)
		{
			EntityPanel entityPanel = _availableEntityPanelsList.get(i);
			EntityController entityController = availableEntitiesList.get(i);

			if(entityPanel.getEntityController() != entityController)
				differenceFound = true;
			else
				newAvailableEntityPanelsList.add(entityPanel);
		}

		if((_availableEntityPanelsList.size() == 0) || (availableEntitiesList.size() == 0))
			i = 1;

		int nbComponent = _panelAvailable.getComponentCount();
		for(int j = i; (j < nbComponent); j++)
			_panelAvailable.remove(i);

		for(int j = i - 1; j < availableEntitiesList.size(); j++)
		{
			EntityPanel newEntityPanel = new EntityPanel(this, availableEntitiesList.get(j));
			_panelAvailable.add(newEntityPanel); 
			newAvailableEntityPanelsList.add(newEntityPanel);
		}

		_availableEntityPanelsList = newAvailableEntityPanelsList;


		int k;	
		differenceFound = false;
		List<EntityPanel> newUnavailableEntityPanelsList = new ArrayList<>();
		for(k = 0; (k < _unavailableEntityPanelsList.size()) && (k < unavailableEntitiesList.size()) && !differenceFound; k++)
		{
			EntityPanel entityPanel = _unavailableEntityPanelsList.get(k);
			EntityController entityController = unavailableEntitiesList.get(k);

			if(entityPanel.getEntityController() != entityController)
				differenceFound = true;
			else
				newUnavailableEntityPanelsList.add(entityPanel);
		}

		if((_unavailableEntityPanelsList.size() > 0) && (unavailableEntitiesList.size() > 0))
			k--;

		int nbComponentUnavailable = _panelUnavailable.getComponentCount();
		for(int j = k; (j < nbComponentUnavailable); j++)
			_panelUnavailable.remove(k);

		for(int j = k; j < unavailableEntitiesList.size(); j++)
		{
			EntityPanel newEntityPanel = new EntityPanel(this, unavailableEntitiesList.get(j));
			_panelUnavailable.add(newEntityPanel); 
			newUnavailableEntityPanelsList.add(newEntityPanel);
		}

		_unavailableEntityPanelsList = newUnavailableEntityPanelsList;

		System.out.println();
		System.out.println("AVAILABLE");
		System.out.println();
		for(EntityPanel onePanel : _availableEntityPanelsList) {
			onePanel.update();
			if(onePanel.getEntityController().isAvailable()) {
				System.out.println(onePanel.getEntityController().getName() + " EST DISPONIBLE");
			} else {
				System.out.println(onePanel.getEntityController().getName() + " N'EST PAS DISPONIBLE");
			}
		}
		System.out.println();
		System.out.println("UNAVAILABLE");
		System.out.println();
		for(EntityPanel onePanel : _unavailableEntityPanelsList) {
			onePanel.update();
			if(onePanel.getEntityController().isAvailable()) {
				System.out.println(onePanel.getEntityController().getName() + " EST DISPONIBLE");
			} else {
				System.out.println(onePanel.getEntityController().getName() + " N'EST PAS DISPONIBLE");
			}
		}
		System.out.println();
	}


	public void addDropTarget()
	{
		_panelDropHere.setDropTarget(new DropTarget(this, new MenuEntitiesPanelDropTargetListener(_globalPanel)));
	}

	public GlobalPanel getGlobalPanel()
	{
		return _globalPanel;
	}

	public OperationController getOperationController()
	{
		return _operation;
	}

	public void update()
	{
		setListEntitiesContent();	
	}
}
