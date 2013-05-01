package views;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import observer.Observer;
import views.buttons.AddLocationButton;
import views.buttons.SubMenuMapButton;
import views.buttons.SubMenuVictimButton;
import controllers.MapController;
import controllers.OperationController;

public class MenuButtonsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MapPanel _mapPanel;
	private JComboBox<String> _listMaps;
	private Map<String, MapController> _map;

	private JButton _victimButton;
	private JButton _addLocationButton;
	private JButton _mapButton;
	private boolean _areButtonsEnabled = true;
	
	@SuppressWarnings("unchecked")
	public MenuButtonsPanel(MapPanel mapPanel) {
		_mapPanel = mapPanel;
		_map = new HashMap<String, MapController>();
		
		setBorder(new EmptyBorder(10, 0, 10, 0));
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

		_victimButton =new SubMenuVictimButton(mapPanel, "Victime");
		_addLocationButton = new AddLocationButton(mapPanel, "Localisation");
		_mapButton = new SubMenuMapButton(mapPanel, "Carte");
		
		add(_victimButton);
		add(_addLocationButton);
		add(_mapButton);
		
		setBackground(Color.LIGHT_GRAY);
		setOpaque(false);
		revalidate();
	}
}