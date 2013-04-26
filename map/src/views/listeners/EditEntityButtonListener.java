package views.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import views.ConfigurationEntityPanel;
import views.EntityPanel;
import views.MapPanel;
import controllers.EntityController;
import controllers.OperationController;


public class EditEntityButtonListener implements MouseListener
{
	private OperationController _operationController;
	private EntityPanel _entityPanel;
	private MapPanel _mapPanel;
	private EntityController _entity;
	
	
	public EditEntityButtonListener(OperationController operationController, EntityPanel entityPanel, MapPanel mapPanel, EntityController entity)
	{
		_operationController = operationController;
		_entityPanel = entityPanel;
		_mapPanel = mapPanel;
		_entity = entity;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		_mapPanel.removeMapPanelListener();
		
		ConfigurationEntityPanel configurationEntityPanel = new ConfigurationEntityPanel(_mapPanel, _operationController, _entity);	
		_mapPanel.add(configurationEntityPanel);		
		_mapPanel.setComponentZOrder(configurationEntityPanel, 0);
		
		_mapPanel.repaint();
		_mapPanel.revalidate();			
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		_entityPanel.getIconGearLabel().setVisible(true);
		_entityPanel.setIconState(true);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		_entityPanel.getIconGearLabel().setVisible(false);
		_entityPanel.setIconState(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
