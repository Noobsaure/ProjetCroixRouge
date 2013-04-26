package views.buttons;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import controllers.EntityController;

import views.MapPanel;
import views.listeners.AddEquipierButtonListener;

public class AddEquipierButton extends CustomButton
{	
	private static final long serialVersionUID = 1L;
	
	public AddEquipierButton(MapPanel mapPanel,String name, EntityController entityController)
	{
		super(name);
		setMinimumSize(new Dimension(150,25));
		setMaximumSize(new Dimension(150,25));
		setPreferredSize(new Dimension(150,25));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		//addActionListener(new AddEquipierButtonListener(mapPanel, entityController));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}