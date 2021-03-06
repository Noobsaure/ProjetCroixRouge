package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import launcher.Launcher;
import observer.Observer;
import controllers.OperationController;


public class GlobalPanel extends JApplet implements Observer
{
	private static final long serialVersionUID = 1L;

	private MapPanel _mapPanel;
	private MenuPanel _menuPanel;
	private Launcher _launcher;
	private OperationController _operation;
	private boolean _dragOccurring;
	private String _serveur;
	private String _port;
	private String _login;
	private String _mdp;
	private int _idOperateur;
	private int _idOperation;
	private String _databaseName;

	private List<CustomDialog> _dialogs;

	public static void main(String[] args)
	{
		new GlobalPanel();
	}

	@Override
	public void init()
	{
		_serveur = getParameter("serveur");
		_port = getParameter("port");
		_login = getParameter("login");
		_mdp = getParameter("mdp");
		_idOperation = Integer.parseInt(getParameter("idOperation"));
		_idOperateur = Integer.parseInt(getParameter("idOperateur"));
		_databaseName = getParameter("database");

		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			JDialog.setDefaultLookAndFeelDecorated(false); 
		}
		catch(InstantiationException e){}
		catch(ClassNotFoundException e){}
		catch(UnsupportedLookAndFeelException e){}
		catch(IllegalAccessException e){}

		getContentPane().setPreferredSize(new Dimension(getMaximumSize()));
		setLayout(new BorderLayout(0, 0));

		GlassPane glassPane = new GlassPane();
		setGlassPane(glassPane);

		_dialogs = new ArrayList<CustomDialog>();
		_mapPanel = new MapPanel(this);
		_mapPanel.addMapPanelListener();
		getContentPane().add(_mapPanel, BorderLayout.CENTER);
		addComponentListener(_mapPanel);

		_menuPanel = new MenuPanel(this, _mapPanel);

		getContentPane().add(_menuPanel, BorderLayout.WEST);

		_launcher = new Launcher(this, _serveur, _port, _login, _mdp, _idOperation, _idOperateur, _databaseName);
		_operation = _launcher.getOperationController();
		_operation.addObserver(this);
		_mapPanel.setOperation(_operation);
		_menuPanel.setOperation(_operation);
		update();
		_menuPanel.getEntitiesPanel().addDropTarget();
	}

	public void addDialog(CustomDialog dialog) {
		_dialogs.add(dialog);
	}

	public void removeDialog(CustomDialog dialog) {
		_dialogs.remove(dialog);
	}

	public void setDragOccurring(boolean dragOccurring) {
		_dragOccurring = dragOccurring;
	}

	public boolean isDragOccurring() {
		return _dragOccurring;
	}

	public void centerMap() {_mapPanel.centerMap();}

	public Launcher getLauncher() {return _launcher;}
	public MapPanel getMapPanel() {return _mapPanel;}
	public MenuPanel getMenu() {return _menuPanel;}

	public synchronized void update() {
		for(CustomDialog oneDialog : _dialogs) {
			oneDialog.update();
		}
		_menuPanel.update();
		_mapPanel.update();
		revalidate();
		repaint();
	}

	@Override
	public void destroy() {
		super.destroy();
		_operation.pauseTimerTask();
	}
}