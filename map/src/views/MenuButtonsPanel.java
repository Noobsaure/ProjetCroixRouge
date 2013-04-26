package views;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import views.buttons.AddLocationButton;
import views.buttons.AddVictimButton;
import views.buttons.EditVictimButton;
import views.buttons.SubMenuMapButton;
import views.buttons.SubMenuVictimButton;
import views.listeners.MapComboBoxListener;
import controllers.MapController;
import controllers.OperationController;

public class MenuButtonsPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private MapPanel _mapPanel;
	private JComboBox<String> _listMaps;
	private Map<String, MapController> _map;
	
	@SuppressWarnings("unchecked")
	public MenuButtonsPanel(MapPanel mapPanel)
	{
		setBorder(new EmptyBorder(10, 0, 10, 0));
		_mapPanel = mapPanel;
		setSize(200, 200);
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		_map = new HashMap<String, MapController>();

		add(new SubMenuVictimButton(mapPanel, "Victime"));
//		add(new EditVictimButton(mapPanel, "Editer victime"));
		add(new AddLocationButton(mapPanel, "Localisation"));
		add(new SubMenuMapButton(mapPanel, "Carte"));
	
//		setListPanelContent();
		
		OperationController operationController = _mapPanel.getGlobalPanel().getLauncher().getOperationController();
//		String mapName = (String)_listMaps.getSelectedItem();
//		MapController mapController = _map.get(mapName);
		operationController.setCurrentMap(operationController.getCurrentMap());
		
//		operationController.addObserver(_listMaps);
//		_listMaps.addActionListener(new MapComboBoxListener(_mapPanel, this));
//		add(_listMaps);
		
		setBackground(Color.LIGHT_GRAY);
		setOpaque(false);
		revalidate();
	}
	
	
//	public void setListPanelContent()
//	{
//		if(_listMaps != null)
//			remove(_listMaps);
//		
//		OperationController operationController = _mapPanel.getGlobalPanel().getLauncher().getOperationController();
//		List<MapController> listMapsName = operationController.getMapList();
//		
//		Vector<String> comboBoxItems = new Vector<String>();
//		for(int i = 0; i < listMapsName.size(); i++)
//		{
//			comboBoxItems.add(listMapsName.get(i).getName());
//			_map.put(listMapsName.get(i).getName(), listMapsName.get(i));
//			System.out.println("-----------------------------------------------------" + listMapsName.get(i).getName());
//		}
//		final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
//		
//		_listMaps = new JComboBox<String>(model);
//		_listMaps.setBackground(Color.BLACK);
//		_listMaps.setForeground(Color.WHITE);
//		_listMaps.setBorder(new EmptyBorder(3, 10, 3, 10));
//		
////		_listMaps.repaint();
////		_listMaps.revalidate();
////		repaint();
////		revalidate();
//		
//		_listMaps.addActionListener(new MapComboBoxListener(_mapPanel, this));
//		add(_listMaps);
//	}
//	
//	public JComboBox<String> getListMaps()
//	{
//		return _listMaps;
//	}
//	
//	public Map<String, MapController> getMap()
//	{
//		return _map;
//	}
}










