package views.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JToggleButton;

public class CustomButton extends JToggleButton
{
	private static final long serialVersionUID = 1L;

	private static final Color FOREGROUND = Color.WHITE;
	private static final Color BACKGROUND = Color.BLACK;
	private static final int RADIUS = 15;

	
	public CustomButton()
	{
		super();
	}
	
	public CustomButton(String text)
	{
		super(text);
		
		setBackground(BACKGROUND);
		setForeground(FOREGROUND);
		setFocusPainted(false);
	    setContentAreaFilled(false);
	}
	
	
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	    if(isSelected())
	      g2d.setColor(FOREGROUND);
	    else
	      g2d.setColor(BACKGROUND);

	    g2d.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, RADIUS, RADIUS);

	    super.paintComponent(g2d);
	}

	
	protected void paintBorder(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(isSelected())
		{
			setForeground(BACKGROUND);
			g2d.setColor(BACKGROUND);
		}
		else
		{
			setForeground(FOREGROUND);
			g2d.setColor(FOREGROUND);
		}
	    g2d.drawRoundRect(0, 0, getSize().width-1, getSize().height-1, RADIUS, RADIUS);
	}
}









