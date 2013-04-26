package views.listeners;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import views.AffectedEntityPanel;
import views.LocationPanel;
import views.GlobalPanel;
import views.GlassPane;

public class AffectedEntityMouseListener extends MouseAdapter{

	private AffectedEntityPanel _entity;
	private GlassPane _glassPane;
	private GlobalPanel _globalPanel;
	private BufferedImage _image;
	private boolean _dragOccurring = false;

	public AffectedEntityMouseListener(AffectedEntityPanel entity, GlassPane glassPane, GlobalPanel globalPanel) {
		_entity = entity;
		_glassPane = glassPane;
		_globalPanel = globalPanel;
	}

	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1) {
			_glassPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
			setDragOccurring(true);
			Point location = (Point)e.getPoint().clone();
			SwingUtilities.convertPointToScreen(location, _entity);
			SwingUtilities.convertPointFromScreen(location, _glassPane);
			_image = new BufferedImage(_entity.getWidth(), _entity.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = _image.getGraphics();
			_entity.paint(g);
			_glassPane.setLocation(location);
			_glassPane.setImage(_image);
			_glassPane.setVisible(true);
			if(_entity.getParent().getParent() instanceof views.LocationPanel) {
				LocationPanel parent = (LocationPanel)_entity.getParent().getParent();
				parent.getLoc().displayPanel(false);
				parent.getLoc().setHighlight(false);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(_dragOccurring && e.getButton() == MouseEvent.BUTTON1) {
			_glassPane.setCursor(Cursor.getDefaultCursor());
			setDragOccurring(false);
			JComponent c = (JComponent) e.getSource();
			_entity.getTransferHandler().exportAsDrag(c, e, TransferHandler.MOVE);
			Component composant = e.getComponent();
			Point location = (Point)e.getPoint().clone();
			SwingUtilities.convertPointToScreen(location, composant);
			SwingUtilities.convertPointFromScreen(location, _glassPane);
			_glassPane.setLocation(location);
			_glassPane.setImage(null);
			_glassPane.setVisible(false);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(_dragOccurring) {
			Component c = e.getComponent();
			Point p = (Point) e.getPoint().clone();
			SwingUtilities.convertPointToScreen(p, c);
			SwingUtilities.convertPointFromScreen(p, _glassPane);
			_glassPane.setLocation(p);
			_glassPane.repaint();
		}
	}
	
	public void setDragOccurring(boolean dragOccurring) {
		_dragOccurring = dragOccurring;
		_globalPanel.setDragOccurring(dragOccurring);
	}
}