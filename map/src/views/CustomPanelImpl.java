package views;

import javax.swing.JPanel;

public abstract class CustomPanelImpl extends JPanel implements CustomPanel {

	private static final long serialVersionUID = 1L;

	@Override
	public void updatePanel() {
		System.out.println("Override this!!");
	}

}
