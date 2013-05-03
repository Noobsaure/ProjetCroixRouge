package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import views.EntityComparator;
import controllers.EntityController;

public class DatabaseTest
{
	private static final String _user = "root";
	private static final String _password = "apagos35";
	private static final String _urlBase = "jdbc:mysql://localhost/";
	
	
	public static void main(String[] args) throws SQLException
	{
//		testQueries();
//		testImages();
		
		final String input = "L'ap\\ostr`oph\\e";
		final String output = DatabaseManager.addSlashes(input);
		System.out.println("Test : " + output);
		System.out.println(DatabaseManager.stripSlashes(output));
		
		final String table = "symfony";
		DatabaseManager databaseManager = new DatabaseManager();
		databaseManager.connection(_urlBase + table, _user, _password);
		
		ArrayList<EntityController> listEntites = new ArrayList<EntityController>();
		listEntites.add(new EntityController(null, databaseManager, 0, 0, 0, null, "Florian", null, null, null));
		listEntites.add(new EntityController(null, databaseManager, 0, 0, 0, null, "Dorian", null, null, null));
		listEntites.add(new EntityController(null, databaseManager, 0, 0, 0, null, "Fouad", null, null, null));
		Collections.sort(listEntites, new EntityComparator());
		
		for(EntityController entite : listEntites)
			System.out.println("Name : " + entite.getName());
	}
	
	
	private static void testQueries()
	{
		final String table = "TestJDBC";
		DatabaseManager databaseManager = new DatabaseManager();
		
		try
		{
			// Connection
			databaseManager.connection(_urlBase + table, _user, _password);
			
			// Selection
			databaseManager.executeQuerySelect(new SQLQuerySelect("*", table));
			databaseManager.displayTable(table);
			
			// Mise a jour
			databaseManager.executeQueryUpdate(new SQLQueryUpdate(table, "Nom = 'Lelooouuuup'", "Prénom = 'Florian'"));
			databaseManager.displayTable(table);
			databaseManager.executeQueryUpdate(new SQLQueryUpdate(table, "Nom = 'Leloup'", "Prénom = 'Florian'"));
			databaseManager.displayTable(table);
			
			// Insertion
			databaseManager.executeQueryInsert(new SQLQueryInsert(table, "('Deret', 'Anthony', '69')"));
			databaseManager.displayTable(table);
			databaseManager.executeQueryInsert(new SQLQueryInsert(table, "('Deret', 'Anthony', '69')"));
			databaseManager.displayTable(table);
			
//			// Suppression
//			databaseManager.executeQueryDelete(new SQLQueryDelete(table, "Age = 69"));
//			databaseManager.displayTable(table);
		}
		catch(MalformedQueryException e)
		{
			databaseManager.displayError(e);
		}
	}
	
	
	private static void testImages()
	{
		final String table = "TestJDBC";
		DatabaseManager databaseManager = new DatabaseManager();
		
		// Connection
		databaseManager.connection(_urlBase + table, _user, _password);
		
		// Insertion d'une image
		System.out.println("retour storeImage : " + databaseManager.storeImage("Loupe", "resources/ui/loupe.png", 2));;
//		databaseManager.displayTable("Carte");
		
//		// Recuperation d'une image
//		ImageIcon image = databaseManager.getImage("003", "Test");
//		
//		// Affichage de l'image dans une fenetre
//		JFrame frameTest = new JFrame("Test image");
//		frameTest.setSize(800, 600);
//		frameTest.setLocationRelativeTo(frameTest.getParent());
//		frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		JButton buttonTest = new JButton(image);
		
//		if(image == null)
//			JOptionPane.showMessageDialog(null, "Image null");
//		
//		frameTest.getContentPane().add(buttonTest);
//	
//		frameTest.setVisible(true);
	}
}
