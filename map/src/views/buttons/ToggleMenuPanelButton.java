package views.buttons;

import java.awt.Graphics;

import javax.swing.JButton;

import views.GlobalPanel;
import views.MapPanel;
import views.listeners.ToggleMenuPanelButtonListener;

public class ToggleMenuPanelButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	private GlobalPanel _gPanel;
	private boolean _hidden = true;
	
	public ToggleMenuPanelButton(GlobalPanel gPanel, MapPanel map)
	{
		super();
		_gPanel = gPanel;
		setBounds(0,0,25,25);
		addActionListener(new ToggleMenuPanelButtonListener(_gPanel, map, this));
	}

	public boolean isHidden() {return _hidden;}
	public void setHidden(boolean hidden) {_hidden = hidden;}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	
}
