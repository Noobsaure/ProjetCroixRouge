package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controllers.OperationController;

import views.ErrorMessage;


public class DatabaseManager
{
	private java.sql.Statement _statement;
	private java.sql.Connection _connection;
	private OperationController _operation;
	
	/**
	 * Constructor
	 */
	public DatabaseManager()
	{
		// Chargement du pilote
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver charge");
		}
		catch(Exception e)
		{
			displayError(e);
		}
	}
	
	public void setOperation (OperationController operation){
		_operation = operation;
	}
	
	/**
	 * Connects to a database given in parameters.
	 * @param databaseURL the database url to connect
	 * @param login a login that allows the connection
	 * @param password the password associated to the login
	 */
	public void connection(String databaseURL, String login, String password)
	{	
		try
		{
			// Connection a la base de donnees
			System.out.print("Connection of " + login + " to " + databaseURL + "...");
			_connection = DriverManager.getConnection(databaseURL, login, password);
			System.out.println("Connected");
			_statement = _connection.createStatement();
		}
		catch (Exception e)
		{
			displayError(e);
		}
	}
	
	
	
	/**
	 * Executes a SQL query SELECT.
	 * @param query the SQL query SELECT to execute
	 * @return a ResultSet object that contains the data produced by the given query; never null
	 */
	public ResultSet executeQuerySelect(SQLQuerySelect query)
	{
		ResultSet result = null;
		//System.out.println("Execution de la requete : "+query);
		try
		{
			java.sql.Statement statement = _connection.createStatement();
			result = statement.executeQuery(query.toString());
		}
		catch(Exception e)
		{
			displayError(e);
		}
		
		return result;
	}
	
	
	
	/**
	 * Executes a SQL query UPDATE and returns the content of the column given in parameters for the elements updated. 
	 * @param query the SQL query UPDATE to execute
	 * @param columnName the name of the column from which the integers have to be extracted for the elements updated
	 * @return a list of the integers from the column given for the elments updated
	 */
	public int executeQueryUpdate(SQLQueryUpdate query)
	{
		int lastInserted = -1;
		
		try
		{			
			System.out.println("Execution de la requete : " + query);
			java.sql.Statement statement = _connection.createStatement();
			lastInserted = statement.executeUpdate(query.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
			System.out.println("Requete executee...");
		}
		catch(Exception e)
		{
			displayError(e);
		}
		
		return lastInserted;
	}
	
	
	
	/**
	 * 
	 * @param query
	 * @param columnName
	 * @return
	 */
	public Integer executeQueryInsert(SQLQueryInsert query)
	{
		Integer lastInserted = -1;
		
		try
		{
			System.out.println("Execution de la requete : " + query);
			java.sql.PreparedStatement statement = _connection.prepareStatement(query.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			while(generatedKeys.next())
			{
				//JOptionPane.showMessageDialog(null, "Last inserted : " + generatedKeys.getInt(1));
				lastInserted = generatedKeys.getInt(1);
			}
			
			System.out.println("Requete executee...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			displayError(e);
		}
		
		return lastInserted;
	}
	
	
	
	/**
	 * 
	 * @param query
	 */
	public void executeQueryDelete(SQLQueryDelete query)
	{
		try
		{
			//System.out.println("Execution de la requete : " + query);
			java.sql.Statement statement = _connection.createStatement();
			statement.executeUpdate(query.toString());
			System.out.println("Requete executee...");
		}
		catch(Exception e)
		{
			displayError(e);
		}		
	}
	
	
	
	/**
	 * Stores an image in the database.  
	 * @param name the name of the image to insert
	 * @param imagePath the path of the image to insert
	 */
	public int storeImage(String name, String imagePath, int idOperation)
	{
		Integer id = -1;
		System.out.println("Id operation : "+idOperation+"\n");
		
	    try
	    {
	    	final String tables = "Carte(id, operation_id, nom, visibilite, image)";
	    	final String values = "(?, ?, ?, ?, ?)";
	    	final SQLQueryInsert query = new SQLQueryInsert(tables, values);
	    	
	    	File file = new File(imagePath);
	    	FileInputStream fileInputStream = new FileInputStream(file);
	    	PreparedStatement preparedStatement = null;
	    	
	    	// Insertion de l'image
	    	System.out.println("Insertion image...");
	    	_connection.setAutoCommit(false);
			
	    	preparedStatement = _connection.prepareStatement(query.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setInt(1, 0);
	    	preparedStatement.setInt(2, idOperation);
	    	preparedStatement.setString(3, name);
	    	preparedStatement.setBoolean(4, true);
	    	preparedStatement.setBinaryStream(5, fileInputStream, (int) file.length());
	    	preparedStatement.executeUpdate();
	    	
	    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	    	while(generatedKeys.next())
				id = generatedKeys.getInt(1);
	    	_connection.commit();
	    	System.out.println("Image inserted successfully");
	    	
	    	preparedStatement.close();
	    	fileInputStream.close();
	    }
	    catch(Exception e)
	    {
	    	displayError(e);
	    	new ErrorMessage(_operation.getGlobalPanel().getMapPanel(), "Erreur interne - Insertion carte '"+name+"'", "Une erreur interne est survenue lors de l'ajout de la carte'"+name+"'. Veuillez recommencer s'il vous plait.");
	    }
	    
	    return id;
	}
	
	
	
	/**
	 * Gets an image from the database.
	 * @param id the id of the the image to get
	 * @param name the name of the image to get
	 * @return 
	 */
	public ImageIcon getImage(String id, String name)
	{
		ImageIcon image = null;

		try
		{
			ResultSet result = executeQuerySelect(new SQLQuerySelect("*", "Carte", "id = " + id));
			
			result.next();
			Blob blob = result.getBlob("image");
			image = new ImageIcon(blob.getBytes(1L, (int)blob.length()));
		}
		catch(Exception e)
		{
			displayError(e);
		}
		
		return image;
	}
	
	
	
	/**
	 * Displays the table given in parameters.
	 * @param table
	 */
	public void displayTable(String table)
	{
		try
		{
			ResultSet result = executeQuerySelect(new SQLQuerySelect("*", table));
			
			while(result.next())
				System.out.println("Valeur : " + result.getString(1));
		}
		catch(Exception e)
		{
			displayError(e);
		}
	}
	
	
	/**
	 * 
	 * @param e
	 */
	public void displayError(Exception e)
	{
		String errorMessage = e + "\n";
		for(int i = 0; i < e.getStackTrace().length; i++)
			errorMessage += e.getStackTrace()[i] + "\n";
		
		JOptionPane.showMessageDialog(null, errorMessage + _statement);
		
		System.exit(0);
	}
}
