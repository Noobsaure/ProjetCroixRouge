package views.listeners;

import java.awt.Cursor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import views.EntityPanel;
import views.AffectedEntityPanel;
import views.Location;
import views.GlobalPanel;

public class LocationDropTargetListener implements DropTargetListener {

	private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private Location _loc;
	private GlobalPanel _gPanel;

	public LocationDropTargetListener(Location loc, GlobalPanel gPanel) {
		_loc = loc;
		_gPanel = gPanel;
	}

	public void dragEnter(DropTargetDragEvent dtde) {}

	public void dragOver(DropTargetDragEvent dtde) {
		if (!_loc.getCursor().equals(droppableCursor)) {
			_loc.setCursor(droppableCursor);
		}
		_loc.setDragOver(true);
		_loc.repaint();
	}
	public void dropActionChanged(DropTargetDragEvent dtde) {}
	public void dragExit(DropTargetEvent dte) {
		if(!_loc.getCursor().equals(Cursor.getDefaultCursor())) {
			_loc.setCursor(Cursor.getDefaultCursor());
		}
		_loc.setDragOver(false);
		_loc.repaint();
	}

	public void drop(DropTargetDropEvent dtde) {
		_loc.setCursor(Cursor.getDefaultCursor());

		try {
			Object transferableObj = dtde.getTransferable().getTransferData(EntityPanel.ENTITY_PANEL_FLAVOR);
			if(transferableObj instanceof EntityPanel) {
				EntityPanel droppedPanel = (EntityPanel)transferableObj;
				droppedPanel.getEntityController().setLocation(_loc.getLocationController());
			} else {
				AffectedEntityPanel droppedPanel = (AffectedEntityPanel)transferableObj;
				droppedPanel.getEntityController().setLocation(_loc.getLocationController());
			}
			_loc.setDragOver(false);
			_gPanel.revalidate();
			_gPanel.repaint();
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
