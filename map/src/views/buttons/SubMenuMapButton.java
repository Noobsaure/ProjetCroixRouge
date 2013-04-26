package views.buttons;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import views.MapPanel;
import views.listeners.SubMenuMapListener;

public class SubMenuMapButton extends CustomButton
{
	private static final long serialVersionUID = 1L;

	public SubMenuMapButton(MapPanel mapPanel, String name)
	{
		super(name);
		setMinimumSize(new Dimension(150,25));
		setMaximumSize(new Dimension(150,25));
		setPreferredSize(new Dimension(150,25));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		addActionListener(new SubMenuMapListener(mapPanel, this));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
