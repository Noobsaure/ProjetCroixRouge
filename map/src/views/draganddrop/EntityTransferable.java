package views.draganddrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import views.EntityPanel;

public class EntityTransferable implements Transferable {

	private EntityPanel _entity;
	public static DataFlavor _flavor;
	
	
	public EntityTransferable(EntityPanel entity) {
		_entity = entity;
	}
	
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return _entity;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {_flavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		
		return true;
	}
	

}
