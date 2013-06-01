package views.buttons;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;

import observer.Observer;
import views.MapPanel;
import views.listeners.SubMenuVictimListener;

public class SubMenuVictimButton extends CustomButton implements Observer
{
	private static final long serialVersionUID = 1L;

	public SubMenuVictimButton(MapPanel mapPanel, String name)
	{
		super(name);
		setMinimumSize(new Dimension(150,25));
		setMaximumSize(new Dimension(150,25));
		setPreferredSize(new Dimension(150,25));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		addActionListener(new SubMenuVictimListener(mapPanel, this));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	@Override
	public void update()
	{
		setSelected(false);	
	}
}
