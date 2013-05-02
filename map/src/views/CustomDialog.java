package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class CustomDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	/** Stroke size. it is recommended to set it to 1 for better view */
    protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady = false;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(0, 0);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 5;
    /** The offset of shadow.  */
    protected int shadowOffset = 4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 150;
	
	
	private CustomPanelImpl _contentPane;

	public CustomDialog(CustomPanelImpl contentPane, GlobalPanel globalPanel) {
		super((Frame)SwingUtilities.getAncestorOfClass(Frame.class,globalPanel), true);
		globalPanel.addDialog(this);
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
}
