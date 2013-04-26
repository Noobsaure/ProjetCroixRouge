package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import views.draganddrop.EntityTransferHandler;
import views.listeners.EditEntityButtonListener;
import views.listeners.EntityMouseListener;
import controllers.EntityController;


public class EntityPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = MenuPanel.LEFT_PANEL_WIDTH - 12;
	private static final int HEIGHT = 25;
	public static DataFlavor ENTITY_PANEL_FLAVOR;

	private JLabel _iconGearLabel;
	private JLabel _entityName;
	private ImageIcon _iconGearOn;
	private ImageIcon _iconGearOff;
	private EntityController _entity;


	public EntityPanel(MenuEntitiesPanel entitiesPanel, EntityController entity)
	{
		_entity = entity;
		
		setBorder(new EmptyBorder(0, 0, 0, 0));
		if(ENTITY_PANEL_FLAVOR == null)
		{
			try
			{
				ENTITY_PANEL_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + EntityPanel.class.getName());
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		setMaximumSize(new Dimension(WIDTH,HEIGHT));
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setForeground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JLabel iconDude = new JLabel();
		if(entity.isAvailable())
			iconDude.setIcon(new ImageIcon(EntityPanel.class.getResource("/ui/entity-green.png")));
		else
			iconDude.setIcon(new ImageIcon(EntityPanel.class.getResource("/ui/entity-red.png")));
		add(iconDude, BorderLayout.WEST);

		_entityName = new JLabel(entity.getName());
		add(_entityName, BorderLayout.CENTER);

		_iconGearLabel = new JLabel();
		_iconGearOn = new ImageIcon(EntityPanel.class.getResource("/ui/gear.png"));
		_iconGearOff = new ImageIcon(EntityPanel.class.getResource("/ui/gearLight.png"));
		_iconGearLabel.setIcon(_iconGearOff);
		_iconGearLabel.setVisible(false);
		_iconGearLabel.addMouseListener(new EditEntityButtonListener(entitiesPanel.getOperationController(), this, entitiesPanel.getGlobalPanel().getMapPanel(), _entity));
		add(_iconGearLabel, BorderLayout.EAST);

		EntityMouseListener listener = new EntityMouseListener(this, (GlassPane)entitiesPanel.getGlobalPanel().getGlassPane(), entitiesPanel.getGlobalPanel());
		_entityName.addMouseListener(listener);
		_entityName.addMouseMotionListener(listener);

		setTransferHandler(new EntityTransferHandler());
	}
	
	
	public JLabel getIconGearLabel()	{return _iconGearLabel;}
	public EntityController getEntity() {return _entity;}
	public void setIconState(boolean on) {
		if(on) {
			_iconGearLabel.setIcon(_iconGearOn);
		} else {
			_iconGearLabel.setIcon(_iconGearOff);
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
