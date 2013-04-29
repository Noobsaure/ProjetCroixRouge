package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import views.buttons.CustomButton;
import views.listeners.AddEquipierDansEntityButtonListener;
import views.listeners.CancelButtonListener;
import views.listeners.CancelEquipierEquipierButtonListener;
import views.listeners.ConfirmDelMapListener;
import views.listeners.ErrorMessageButtonListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import controllers.EntityController;
import controllers.MapController;
import controllers.OperationController;
import controllers.TeamMemberController;

public class ConfirmDelMapPanel extends JLayeredPane{

	private static final long serialVersionUID = 1L;
	private JPanel _parent;	
	private OperationController _operation;
	private MapController _map;

	public ConfirmDelMapPanel(OperationController operation, MapController map) {
		System.out.println("GO THERE");
		_operation = operation;
		_map = map;
		_parent = operation.getGlobalPanel().getMapPanel();
		initGui();
	}

	private void initGui()
	{
		setOpaque(false);
		
		String _message = "Êtes-vous sur de vouloir supprimer la carte '"+_map.getName()+"' \n" +
				"Cette action est irréversible et toutes les entités présentes sur la carte seront ramenées à leur localisation de base.";
		
		RoundedPanel popupPanel = new RoundedPanel();
		JLabel messageLabel = new JLabel(_message);

		// Dimensions de la popup
		FontMetrics metrics = getFontMetrics(messageLabel.getFont());
		int width = metrics.stringWidth(_message)/2 + 70;
		int height = metrics.getHeight() + 120;
		setSize(new Dimension(width, height));
		popupPanel.setLayout(new BorderLayout(0, 0));

		centrer();
		
		// Titre
		JLabel titleLabel = new JLabel("Supression '"+_map.getName()+"'");
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
		panel.setLayout(new GridLayout(1, 2));
		popupPanel.add(panel, BorderLayout.SOUTH);

		CustomButton cancelButton = new CustomButton("Annuler");
		cancelButton.addActionListener( new CancelButtonListener(_operation, this) );
		panel.add(cancelButton);
		
		CustomButton okButton = new CustomButton("Valider");
		okButton.addActionListener(new ConfirmDelMapListener(_operation, this, _map));
		panel.add(okButton);

		add(popupPanel);

	}

	private void centrer()
	{		
		int x = 0, y = 0;
		
		if(_parent != null)
		{
			System.out.println("Ici");
			x = (_parent.getWidth() / 2) - (getWidth() / 2);
			System.out.println("Width :"+getWidth());
			y = (_parent.getHeight() / 2) - (getHeight() / 2);
			System.out.println("Height :"+getHeight());
		}
		else
		{
			System.out.println("la");
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
		System.out.println("X : "+x+" Y: "+y);
		setLocation(x, y);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);	
		
		centrer();
	}
}
