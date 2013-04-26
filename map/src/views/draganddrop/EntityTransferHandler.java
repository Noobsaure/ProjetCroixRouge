package views.draganddrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import views.EntityPanel;

public class EntityTransferHandler extends TransferHandler{

	private static final long serialVersionUID = 1L;
	private EntityTransferable transferable;

	public boolean canImport(TransferHandler.TransferSupport info) {
		if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return false;
		}
		return true;
	}

	public boolean importData(TransferHandler.TransferSupport support){

		if(!canImport(support))
			return false;

		Transferable data = support.getTransferable();
		
		try {
			EntityPanel entity = null;
			entity = (EntityPanel)data.getTransferData(new DataFlavor(DataFlavor.javaSerializedObjectMimeType));
		} catch (UnsupportedFlavorException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return true;

	}

	protected void exportDone(JComponent c, Transferable t, int action) {
		
	}

	protected Transferable createTransferable(JComponent c) {
		return new EntityTransferable((EntityPanel)c);
	}

	public int getSourceActions(JComponent c) {
		return MOVE;
	}

}
