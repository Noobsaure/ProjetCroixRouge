package views.buttons;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import views.MapPanel;
import views.listeners.AddMapButtonListener;

public class AddMapButton extends CustomButton
{
	private static final long serialVersionUID = 1L;
	
	
	public AddMapButton(MapPanel mapPanel, String name)
	{
		super(name);
		setMinimumSize(new Dimension(150,25));
		setMaximumSize(new Dimension(150,25));
		setPreferredSize(new Dimension(150,25));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		addActionListener(new AddMapButtonListener(mapPanel));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
