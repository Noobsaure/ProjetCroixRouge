package views;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JApplet;
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
	private String _idOperateur;
	private String _idOperation;

	public static void main(String[] args)
	{
		new GlobalPanel();
	}


	@Override
	public void init()
	{
		/*
		String _serveur = getParameter("serveur");
		String _port = getParameter("port");
		String _login = getParameter("login");
		String _mdp = getParameter("mdp");
		int _idOperation = Integer.parseInt(getParameter("idOperation"));
		int _idOperateur = Integer.parseInt(getParameter("idOperateur"));
		*/
		
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		}
		catch(InstantiationException e){}
		catch(ClassNotFoundException e){}
		catch(UnsupportedLookAndFeelException e){}
		catch(IllegalAccessException e){}

		getContentPane().setPreferredSize(new Dimension(getMaximumSize()));
		setLayout(new BorderLayout(0, 0));

		GlassPane glassPane = new GlassPane();
		setGlassPane(glassPane);

		_mapPanel = new MapPanel(this);
		_mapPanel.addMapPanelListener();
		getContentPane().add(_mapPanel, BorderLayout.CENTER);

		_menuPanel = new MenuPanel(this, _mapPanel);

		getContentPane().add(_menuPanel, BorderLayout.WEST);

		//_launcher = new Launcher(this, _serveur, _port, _login, _mdp, _idOperation, _idOperateur);
		_launcher = new Launcher(this, "localhost", "3306", "root", "apagos35", 1, 1);
		_operation = _launcher.getOperationController();
		_operation.addObserver(this);
		_mapPanel.setOperation(_operation);
		_menuPanel.setOperation(_operation);
		update();
		_menuPanel.getEntitiesPanel().addDropTarget();
	}

	public void setDragOccurring(boolean dragOccurring) {
		_dragOccurring = dragOccurring;
	}
	public boolean isDragOccurring() {
		return _dragOccurring;
	}
	public void enableListeners(boolean enable) {
		System.out.println("TROLOLO");
		_mapPanel.setEnabled(enable);
		for(int i=0;i<_menuPanel.getComponentCount();i++) {
			_menuPanel.getComponent(i).setEnabled(enable);
		}
	}

	public Launcher getLauncher() {return _launcher;}
	public MapPanel getMapPanel() {return _mapPanel;}
	public MenuPanel getMenu() {return _menuPanel;}

	public synchronized void update() {
		_menuPanel.update();
		_mapPanel.update();
		revalidate();
		repaint();
	}
}