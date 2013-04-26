package views.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ColorChooserListener implements MouseListener
{
	private JPanel _source;
	
	public ColorChooserListener(JPanel source)
	{
		_source = source;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Color colorChosen = JColorChooser.showDialog(null, "Choix de la couleur associée à l'entité.", _source.getBackground());
		
		if(colorChosen == null)
			colorChosen = _source.getBackground();
		
		_source.setBackground(colorChosen);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
