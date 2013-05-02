package views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import launcher.Launcher;
import views.ExtensionFileFilter;
import views.MapPanel;
import views.SubMenuMapPanel;
import controllers.MapController;
import controllers.OperationController;
import database.DatabaseManager;

public class AddMapButtonListener implements ActionListener {
	private MapPanel _mapPanel;
	private final JFileChooser _fileChooser;
	private SubMenuMapPanel _subMenu;

	public AddMapButtonListener(MapPanel mapPanel, SubMenuMapPanel subMenu)
	{
		_mapPanel = mapPanel;
		_fileChooser = new JFileChooser();
		_subMenu = subMenu;
	}

	private String getFileName(String path)
	{
		StringTokenizer tokens = new StringTokenizer(path, File.separator);
		String result = "";

		while(tokens.hasMoreTokens())
			result = tokens.nextToken();
		
		String resultWithoutExtension = "";
		String lastToken = "";
		
		StringTokenizer tokensWithoutExtension = new StringTokenizer(result, ".");
		
		while(tokensWithoutExtension.hasMoreElements())
		{
			resultWithoutExtension += lastToken;
			lastToken = (String)tokensWithoutExtension.nextElement();
		}

		return resultWithoutExtension;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Launcher launcher = _mapPanel.getGlobalPanel().getLauncher();
		OperationController operationController = launcher.getOperationController();
		DatabaseManager databaseManager = launcher.getDatabaseManager();

		FileFilter filter1 = new ExtensionFileFilter("Images", new String[] { "PNG", "JPG", "JPEG", "BMP", "GIF" });
		_fileChooser.setFileFilter(filter1);

		int result = _fileChooser.showOpenDialog(null);

		switch(result)
		{
		case JFileChooser.CANCEL_OPTION:
			break;

		case JFileChooser.APPROVE_OPTION:
			String path = _fileChooser.getSelectedFile().getAbsolutePath();
			new MapController(operationController, databaseManager, getFileName(path), path);
			_subMenu.update();
			break;
		}
	}
}
