package database;

import javax.swing.JOptionPane;

public class MalformedQueryException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MalformedQueryException(String message)
	{
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
