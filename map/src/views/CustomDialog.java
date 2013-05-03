package views;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class CustomDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private GlobalPanel _globalPanel;
	
	private CustomPanelImpl _contentPane;

	public CustomDialog(CustomPanelImpl contentPane, GlobalPanel globalPanel) {
		super((Frame)SwingUtilities.getAncestorOfClass(Frame.class,globalPanel), true);
		_globalPanel = globalPanel;
		_globalPanel.addDialog(this);
		_contentPane = contentPane;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setContentPane(_contentPane);
		setSize((int)contentPane.getPreferredSize().getWidth(),(int)contentPane.getPreferredSize().getHeight());
		setLocationRelativeTo(null);
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
		setUndecorated(true);
		setVisible(true);
	}
	
	public void update() {
		_contentPane.updatePanel();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		_globalPanel.removeDialog(this);
	}
}
