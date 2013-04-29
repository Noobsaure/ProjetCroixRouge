package views.listeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import launcher.Launcher;

import views.buttons.SubMenuMapButton;
import views.MapPanel;
import views.SubMenuPanel;
import controllers.MapController;
import controllers.OperationController;

public class SwitchMapButtonListener implements ActionListener
{
	private SubMenuMapButton _button;
	private MapPanel _mapPanel;
	private SubMenuPanel _subMenuPanel;
	private JPanel _thumbnailPanel;
	private OperationController _operationController;
	
	public SwitchMapButtonListener(MapPanel mapPanel, SubMenuMapButton button, SubMenuPanel subMenuPanel, OperationController operationController)
	{
		_button = button;
		_mapPanel = mapPanel;
		_subMenuPanel = subMenuPanel;
		_thumbnailPanel = subMenuPanel.getThumbnailPanel();
		_operationController = operationController;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JToggleButton buttonSelected = null;
		
		for(int i = 0; i < _thumbnailPanel.getComponentCount(); i += 2)
		{
			JToggleButton toggleButton = (JToggleButton)_thumbnailPanel.getComponent(i);
			
			if(toggleButton.isSelected())
				buttonSelected = toggleButton;
		}
		
		Map<JToggleButton, MapController> map = _subMenuPanel.getMapMap();
		MapController mapController = map.get(buttonSelected);
		
		if(mapController != null)
		{
			ImageIcon image = mapController.getImage();
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();
			double ratio = 1;
			
			if(image.getIconHeight() > height && image.getIconWidth() > width)
			{
				if(image.getIconHeight() > image.getIconWidth())
					ratio = width/image.getIconWidth();
				else
					ratio = height/image.getIconHeight();
			}
			else
				if(image.getIconHeight() > height)
					ratio = height/image.getIconHeight();
				else
					if(image.getIconWidth() > width)
						ratio = width/image.getIconWidth();
			
			BufferedImage newMap = new BufferedImage((int)(image.getIconWidth() * ratio), (int)(image.getIconHeight()*ratio), BufferedImage.TYPE_INT_RGB);
			newMap.getGraphics().drawImage(image.getImage(), 0, 0, (int)(image.getIconWidth()*ratio), (int)(image.getIconHeight()*ratio), null);
			
			_mapPanel.setMap(newMap);
			_operationController.setCurrentMap(mapController);
		}
		
		_button.setEnabled(true);
		_mapPanel.remove(_subMenuPanel);
		
		_mapPanel.repaint();
		_mapPanel.revalidate();
	}

}
