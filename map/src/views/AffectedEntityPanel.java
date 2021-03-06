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

import views.draganddrop.AffectedEntityTransferHandler;
import views.listeners.AffectedEntityMouseListener;
import controllers.EntityController;


public class AffectedEntityPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH =  (MenuPanel.LEFT_PANEL_WIDTH - 12) / 2;
	public static final int HEIGHT = 32;
	public static DataFlavor AFFECTED_ENTITY_PANEL_FLAVOR;

	private EntityController _entity;
	private JLabel _entityName;
	private JLabel _iconDude;

	public AffectedEntityPanel(MapPanel mapPanel, EntityController entity)
	{
		_entity = entity;
		setBorder(new EmptyBorder(0, 0, 0, 0));
		if(AFFECTED_ENTITY_PANEL_FLAVOR == null)
		{
			try
			{
				AFFECTED_ENTITY_PANEL_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + AffectedEntityPanel.class.getName());
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

		_iconDude = new JLabel();
		if(entity.isAvailable()) {
			_iconDude.setIcon(EntityPanel.iconDudeOn);
		} else {
			_iconDude.setIcon(EntityPanel.iconDudeOff);
		}
		add(_iconDude, BorderLayout.WEST);

		_entityName = new JLabel(_entity.getName());
		add(_entityName, BorderLayout.CENTER);

		AffectedEntityMouseListener listener = new AffectedEntityMouseListener(this, (GlassPane)mapPanel.getGlobalPanel().getGlassPane(), mapPanel.getGlobalPanel());
		addMouseListener(listener);
		addMouseMotionListener(listener);
		setTransferHandler(new AffectedEntityTransferHandler());
	}

	public EntityController getEntityController()
	{
		return _entity;
	}
	
	public void update() {
		_entityName.setText(_entity.getName());
		if(_entity.isAvailable()) {
			_iconDude.setIcon(EntityPanel.iconDudeOn);
		} else {
			_iconDude.setIcon(EntityPanel.iconDudeOff);
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
