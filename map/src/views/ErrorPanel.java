package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import views.listeners.ErrorMessageButtonListener;
import views.buttons.CustomButton;


public class ErrorPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JPanel _parent;
	private String _message;
	private String _title;
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public ErrorPanel(JPanel parent, String title, String message)
	{
		_parent = parent;
		_title = title;
		_message = message + "  ";
		
		init();
	}
	
	public ErrorPanel(JPanel parent, String message)
	{
		_parent = parent;
		_title = "Erreur";
		_message = message + "  ";
		
		init();
	}
	
	
	public void init()
	{
		setOpaque(false);
		
		RoundedPanel popupPanel = new RoundedPanel();
		JLabel messageLabel = new JLabel(_message);
		
		// Dimensions de la popup
		FontMetrics metrics = getFontMetrics(messageLabel.getFont());
		int width = metrics.stringWidth(_message) + 70;
		int height = metrics.getHeight() + 120;
		setSize(new Dimension(width, height));
		popupPanel.setLayout(new BorderLayout(0, 0));
		
		centrer();
		
		// Titre
		JLabel titleLabel = new JLabel(_title);
		titleLabel.setBorder(new EmptyBorder(5, 0, 8, 0));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		popupPanel.add(titleLabel, BorderLayout.NORTH);
		
		// Icone
		JLabel iconLabel = new JLabel();
		iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		iconLabel.setIcon(new ImageIcon(ErrorMessage.class.getResource("/ui/error.png")));
		popupPanel.add(iconLabel, BorderLayout.WEST);
		
		// Message
		popupPanel.add(messageLabel);
		
		// Bouton Ok
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.setOpaque(false);
		popupPanel.add(panel, BorderLayout.SOUTH);
		
		CustomButton okButton = new CustomButton("Ok");
		okButton.addActionListener(new ErrorMessageButtonListener(_parent, this));
		panel.add(okButton);
		
		add(popupPanel);
	}
	
	
	private void centrer()
	{		
		int x = 0, y = 0;
		
		if(_parent != null)
		{
			x = (_parent.getWidth() / 2) - (getWidth() / 2);
			y = (_parent.getHeight() / 2) - (getHeight() / 2);
		}
		else
		{
			Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int)screenSize.getWidth();
			int height = (int)screenSize.getHeight();
			
			x = (width / 2) - (getWidth() / 2);
			y = (height / 2) - (getHeight() / 2);
		}
		
		if(x < 0)
			x = 0;
		if(y < 0)
			y = 0;
		setLocation(x, y);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);	
		
		centrer();
	}
}