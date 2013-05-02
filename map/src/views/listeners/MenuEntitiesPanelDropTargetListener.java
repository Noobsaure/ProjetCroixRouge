package views.listeners;

import java.awt.Cursor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import views.AffectedEntityPanel;
import views.EntityPanel;
import views.GlobalPanel;
import views.MenuPanel;

public class MenuEntitiesPanelDropTargetListener implements DropTargetListener {

	private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private MenuPanel _menu;
	private GlobalPanel _globalPanel;

	public MenuEntitiesPanelDropTargetListener(GlobalPanel gPanel) {
		_globalPanel = gPanel;
		_menu = _globalPanel.getMenu();
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
			Object transferableObj = dtde.getTransferable().getTransferData(AffectedEntityPanel.AFFECTED_ENTITY_PANEL_FLAVOR);
			if(transferableObj instanceof EntityPanel) {
				EntityPanel droppedPanel = (EntityPanel)transferableObj;
				droppedPanel.getEntityController().setLocation(_globalPanel.getLauncher().getOperationController().getPcmLocation());
			} else {
				AffectedEntityPanel droppedPanel = (AffectedEntityPanel)transferableObj;
				droppedPanel.getEntityController().setLocation(_globalPanel.getLauncher().getOperationController().getPcmLocation());
			}
			_globalPanel.revalidate();
			_globalPanel.repaint();
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
