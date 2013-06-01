package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import views.buttons.CustomButton;
import views.listeners.MessagePanelButtonListener;


public class MessagePanel extends CustomPanelImpl
{
	private static final long serialVersionUID = 1L;

	private String _message;
	private String _title;
	private JPanel _buttonPanel;
	private CustomButton _okButton;
	private PopUpPanel _internalPanel;

	public MessagePanel(String title, String message) {
		_title = title;
		_message = message + "  ";
		init();
	}

	public MessagePanel(String message) {
		_title = "Erreur";
		_message = message + "  ";
		init();
	}

	public void init()
	{
		setLayout(new BorderLayout());
		setOpaque(false);

		JLabel messageLabel = new JLabel(_message);
		FontMetrics metrics = getFontMetrics(messageLabel.getFont());
		int width = metrics.stringWidth(_message) + 70;
		int height = metrics.getHeight() + 120;

		_internalPanel = new PopUpPanel();
		_internalPanel.setSize(width,height);
		add(_internalPanel, BorderLayout.CENTER);
		setPreferredSize(_internalPanel.getSize());
		_internalPanel.setLayout(new BorderLayout());

		String[] listSubStrings = new String[_message.length()];

		int sizeMax = _message.length();
		while((metrics.stringWidth(_message.substring(sizeMax)) < (width - 100)) && (sizeMax > 0))
			sizeMax--;
		sizeMax = metrics.stringWidth(_message.substring(sizeMax));

		StringTokenizer tokens = new StringTokenizer(_message, " ");

		for(int i = 0; i < listSubStrings.length; i++)
			listSubStrings[i] = new String();

		for(int i = 0; tokens.hasMoreTokens();)
		{
			if(metrics.stringWidth(listSubStrings[i]) < sizeMax)
				listSubStrings[i] += tokens.nextToken() + " ";
			else
				i++;
		}


		// Icone
		JLabel iconLabel = new JLabel();
		iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		iconLabel.setIcon(new ImageIcon(MessagePanel.class.getResource("/ui/error.png")));
		_internalPanel.add(iconLabel, BorderLayout.WEST);

		// Message
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(10, 10, 0, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		for(int i = 0; i < listSubStrings.length; i++) {
			panel.add(new JLabel(listSubStrings[i]));
		}
		_internalPanel.add(panel, BorderLayout.CENTER);

		// Titre
		JLabel titleLabel = new JLabel(_title);
		titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_internalPanel.add(titleLabel, BorderLayout.NORTH);

		// Bouton Ok
		_buttonPanel = new JPanel();
		_buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		_buttonPanel.setOpaque(false);
		_internalPanel.add(_buttonPanel, BorderLayout.SOUTH);

		_okButton = new CustomButton("Ok");
		_okButton.addActionListener(new MessagePanelButtonListener(this));
		_buttonPanel.add(_okButton);

		add(_internalPanel);
	}	

	public CustomButton getOkButton()
	{
		return _okButton;
	}

	public JPanel getButtonsPanel()
	{
		return _buttonPanel;
	}

	@Override
	public void updatePanel() {
		//Rien � update ici.
	}
}