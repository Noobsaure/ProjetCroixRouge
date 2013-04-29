package views;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class ErrorMessage extends JLayeredPane
{
	private static final long serialVersionUID = 1L;

	
	public ErrorMessage(JPanel parent, String title, String message)
	{
		ErrorPanel errorPanel = new ErrorPanel(parent, title, message);
		parent.add(errorPanel);
		parent.setComponentZOrder(errorPanel, 0);
		
		parent.repaint();
		parent.revalidate();
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErrorMessage(JPanel parent, String message)
	{
		ErrorPanel errorPanel = new ErrorPanel(parent, message);
		parent.add(errorPanel);
		parent.setComponentZOrder(errorPanel, 0);
		parent.repaint();
		parent.revalidate();
	}
}
