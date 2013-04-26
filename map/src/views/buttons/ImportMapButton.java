package views.buttons;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.border.Border;

import views.listeners.ImportMapButtonListener;

public class ImportMapButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	public ImportMapButton(String name)
	{
		super(name);
		setMinimumSize(new Dimension(150,25));
		setMaximumSize(new Dimension(150,25));
		setPreferredSize(new Dimension(150,25));
		//setBorder(new Border());
		addActionListener(new ImportMapButtonListener());
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
