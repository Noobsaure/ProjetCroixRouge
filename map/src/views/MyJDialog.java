package views;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyJDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private CustomPanelImpl _contentPane;

	public MyJDialog(CustomPanelImpl contentPane, GlobalPanel globalPanel) {
		super((Frame)SwingUtilities.getAncestorOfClass(Frame.class,globalPanel), true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setContentPane(contentPane);
		setSize((int)contentPane.getPreferredSize().getWidth(),(int)contentPane.getPreferredSize().getHeight());
		setLocationRelativeTo(null);
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
		setUndecorated(true);
		setVisible(true);
		globalPanel.addDialog(this);
	}
	
	public void update() {
		_contentPane.updatePanel();
	}
}
