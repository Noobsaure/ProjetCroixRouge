package views.listeners;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import views.Location;
import views.GlobalPanel;
import views.EntityPanel;
import views.MenuPanel;
import views.draganddrop.EntityTransferable;

public class MenuEntitiesPanelDropTargetListener implements DropTargetListener {

	private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private MenuPanel _menu;
	private GlobalPanel _gPanel;

	public MenuEntitiesPanelDropTargetListener(GlobalPanel gPanel) {
		_gPanel = gPanel;
		_menu = _gPanel.getMenu();//.getEntitiesPanel();
	}

	public void dragEnter(DropTargetDragEvent dtde) {}

	public void dragOver(DropTargetDragEvent dtde) {
		if (!this._menu.getCursor().equals(droppableCursor)) {
			this._menu.setCursor(droppableCursor);
		}
	}
	
	public void dropActionChanged(DropTargetDragEvent dtde) {}
	
	public void dragExit(DropTargetEvent dte) {
		this._menu.setCursor(Cursor.getDefaultCursor());
	}

	public void drop(DropTargetDropEvent dtde) {
		this._menu.setCursor(Cursor.getDefaultCursor());

		try {
			Object transferableObj = dtde.getTransferable().getTransferData(EntityPanel.ENTITY_PANEL_FLAVOR);
			EntityPanel droppedPanel = (EntityPanel)transferableObj;
			droppedPanel.getEntity().setLocation(null);
			_gPanel.revalidate();
			_gPanel.repaint();
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
