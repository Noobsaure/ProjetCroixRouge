package views;

import java.awt.Graphics;

import javax.swing.JPanel;


public class PopUpPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public PopUpPanel() {
		super();
		setOpaque(false);
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
} 