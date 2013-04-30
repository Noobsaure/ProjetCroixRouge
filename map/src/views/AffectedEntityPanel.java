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
	//private static final int WIDTH = 32;
	private static final int WIDTH =  (MenuPanel.LEFT_PANEL_WIDTH - 12) / 2;
	private static final int HEIGHT = 32;
	public static DataFlavor AFFECTED_ENTITY_PANEL_FLAVOR;

	private EntityController _entity;

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

		JLabel iconDude = new JLabel();
		if(entity.isAvailable())
			iconDude.setIcon(new ImageIcon(AffectedEntityPanel.class.getResource("/ui/entity-green.png")));
		else
			iconDude.setIcon(new ImageIcon(AffectedEntityPanel.class.getResource("/ui/entity-red.png")));
		add(iconDude, BorderLayout.WEST);

		JLabel nameEntity = new JLabel(entity.getName());
		add(nameEntity, BorderLayout.CENTER);

		AffectedEntityMouseListener listener = new AffectedEntityMouseListener(this, (GlassPane)mapPanel.getGlobalPanel().getGlassPane(), mapPanel.getGlobalPanel());
		addMouseListener(listener);
		addMouseMotionListener(listener);
		setTransferHandler(new AffectedEntityTransferHandler());
	}

	public EntityController getEntityController()
	{
		return _entity;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
