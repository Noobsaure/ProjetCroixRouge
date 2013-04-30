package views.listeners;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import views.EntityPanel;
import views.GlassPane;
import views.GlobalPanel;
import views.LocationPanel;

public class IconLocationMouseListener extends AbstractObserverListener implements MouseListener, MouseMotionListener {

	private EntityPanel _entity;
	private GlobalPanel _globalPanel;
	private GlassPane _glassPane;
	private BufferedImage _image;
	private boolean _dragOccurring = false;


	public IconLocationMouseListener(EntityPanel entity, GlassPane glassPane, GlobalPanel globalPanel) {
		super(globalPanel.getMapPanel());
		_entity = entity;
		_glassPane = glassPane;
		_globalPanel = globalPanel;
	}

	public void mousePressed(MouseEvent e){
		if(isEnabled()) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				_glassPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
				setDragOccurring(true);
				_entity.getIconGearLabel().setVisible(false);
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
	}

	public void mouseReleased(MouseEvent e) {
		if(_dragOccurring && e.getButton() == MouseEvent.BUTTON1) {
			_glassPane.setCursor(Cursor.getDefaultCursor());
			setDragOccurring(false);
			_entity.getTransferHandler().exportAsDrag(_entity, e, TransferHandler.MOVE);
			Point location = (Point)e.getPoint().clone();
			SwingUtilities.convertPointToScreen(location, _entity);
			SwingUtilities.convertPointFromScreen(location, _glassPane);
			_glassPane.setLocation(location);
			_glassPane.setImage(null);
			_glassPane.setVisible(false);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(_dragOccurring) {
			Point p = (Point) e.getPoint().clone();
			SwingUtilities.convertPointToScreen(p, _entity);
			SwingUtilities.convertPointFromScreen(p, _glassPane);
			_glassPane.setLocation(p);
			_glassPane.repaint();
		}
	}

	public void mouseEntered(MouseEvent e)
	{
		if(isEnabled()) {
			_entity.getIconGearLabel().setVisible(true);
		}
	}

	public void mouseExited(MouseEvent e)
	{
		if(isEnabled()) {
			_entity.getIconGearLabel().setVisible(false);
		}
	}

	public void setDragOccurring(boolean dragOccurring) {
		_dragOccurring = dragOccurring;
		_globalPanel.setDragOccurring(dragOccurring);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
