package views;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import views.listeners.ErrorMessageButtonListener;


public class ErrorMessage extends JLayeredPane
{
	private static final long serialVersionUID = 1L;

	
	public ErrorMessage(MapPanel parent, String title, String message)
	{
		ErrorPanel errorPanel = new ErrorPanel(parent, title, message);
		errorPanel.addOkButtonListener(new ErrorMessageButtonListener(parent, errorPanel));
		parent.add(errorPanel);
		parent.setComponentZOrder(errorPanel, 0);
		
		parent.repaint();
		parent.revalidate();
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErrorMessage(MapPanel parent, String message)
	{
		ErrorPanel errorPanel = new ErrorPanel(parent, message);
		errorPanel.addOkButtonListener(new ErrorMessageButtonListener(parent, errorPanel));
		parent.add(errorPanel);
		parent.setComponentZOrder(errorPanel, 0);
		parent.repaint();
		parent.revalidate();
	}
}
