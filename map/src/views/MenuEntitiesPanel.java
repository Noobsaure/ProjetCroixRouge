package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.dnd.DropTarget;
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
	
	private GlobalPanel _gPanel;
	private OperationController _operation;
	
	private JPanel _panelAvailable;
	private JPanel _panelNotAvailable;

	public MenuEntitiesPanel(OperationController operation, GlobalPanel gPanel)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		_operation = operation;
		_gPanel = gPanel;
		
		_panelAvailable = new JPanel();
		_panelAvailable.setLayout(new BoxLayout(_panelAvailable, BoxLayout.Y_AXIS));
		_panelAvailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelAvailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Disponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		_panelNotAvailable = new JPanel();
		_panelNotAvailable.setAlignmentX(Component.LEFT_ALIGNMENT);
		_panelNotAvailable.setMinimumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, 0));
		_panelNotAvailable.setMaximumSize(new Dimension(MenuPanel.LEFT_PANEL_WIDTH, Short.MAX_VALUE));
		_panelNotAvailable.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), "Indisponible", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		
		_panelAvailable.setBackground(Color.BLACK);
		_panelNotAvailable.setBackground(Color.BLACK);
		
		add(_panelAvailable, BorderLayout.CENTER);
		add(_panelNotAvailable, BorderLayout.SOUTH);
		
		setOpaque(false);
	}
	
	
	public void setListEntitiesContent()
	{
		List<EntityController> listEntities = _operation.getEntityList();
		_panelAvailable.removeAll();
		_panelNotAvailable.removeAll();
		
		final int WIDTH_PANEL_ADD_BUTTON = MenuPanel.LEFT_PANEL_WIDTH - 13;
		final int HEIGHT_BUTTON = 25;
		JPanel panelAddButton = new JPanel();
		panelAddButton.setLayout(new BorderLayout());
		panelAddButton.setBackground(Color.BLACK);
		panelAddButton.setBorder(new EmptyBorder(0, 10, 2, 10));
		panelAddButton.setMinimumSize(new Dimension(WIDTH_PANEL_ADD_BUTTON, HEIGHT_BUTTON));
		panelAddButton.setMaximumSize(new Dimension(WIDTH_PANEL_ADD_BUTTON, HEIGHT_BUTTON));
		AddEntityButton addButton = new AddEntityButton(_gPanel.getMapPanel(), "+");
		panelAddButton.add(addButton);
		_panelAvailable.add(panelAddButton);
		
		for(int i = 0; i < listEntities.size(); i++)
		{
			if (listEntities.get(i).isAvailable())
			{
				EntityPanel panel = new EntityPanel(this, listEntities.get(i));
				_panelAvailable.add(panel);
			}
			else
			{
				EntityPanel panelNotAvailable = new EntityPanel(this, listEntities.get(i));
				_panelNotAvailable.add(panelNotAvailable);
			}
		}

		/*_panelAvailable.repaint();
		_panelAvailable.revalidate();
		_panelNotAvailable.repaint();
		_panelNotAvailable.revalidate();*/
	}
	
	
	
	public void addDropTarget() {
		this.setDropTarget(new DropTarget(this, new MenuEntitiesPanelDropTargetListener(_gPanel)));
	}
	
	public GlobalPanel getGlobalPanel() {return _gPanel;}
	
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
