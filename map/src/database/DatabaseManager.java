package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import views.MessagePanel;
import views.MyJDialog;
import controllers.OperationController;

public class DatabaseManager
{
	private static final int RECONNECTION_PERIOD = 100;
	
	private java.sql.Connection _connection;
	private java.sql.Connection _connectionBack;
	private java.sql.Connection _currentConnection;
	private OperationController _operation;
	private String _databaseURL;
	private String _login;
	private String _password;
	private int _nbExecutedQueriesConnection;
	private int _nbExecutedQueriesConnectionBack;
	private boolean _connectionReconnected = false;
	private boolean _connectionBackReconnected = false;
	
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
			_databaseURL = databaseURL;
			_login = login;
			_password = password;
			_nbExecutedQueriesConnection = 0;
			
			// Connection a la base de donnees
			System.out.print("[Connection] Connection of " + login + " to " + databaseURL + "...");
			_connection = DriverManager.getConnection(databaseURL, login, password);
			_currentConnection = _connection;
			System.out.println("Connected");
		}
		catch(Exception e)
		{
			displayError(e);
		}
	}
	
	
	public void connectionBack(String databaseURL, String login, String password)
	{
		try
		{
			_databaseURL = databaseURL;
			_login = login;
			_password = password;
			_nbExecutedQueriesConnectionBack = 0;
			
			// Connection a la base de donnees
			System.out.print("[Connection de secours] Connection of " + login + " to " + databaseURL + "...");
			_connectionBack = DriverManager.getConnection(databaseURL, login, password);
			System.out.println("Connected");
//			_statement = _connection.createStatement();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void reconnectionConnection()
	{
		try
		{
			_connection.close();
			connection(_databaseURL, _login, _password);
			
			_connectionReconnected = true;
			
			System.out.println("Reconnection (connection principale)!!!");
		}
		catch(SQLException e)
		{
			displayError(e);
		}
	}
	
	private void reconnectionConnectionBack()
	{
		try
		{
			_connectionBack.close();
			connectionBack(_databaseURL, _login, _password);
			
			_connectionBackReconnected = true;
			
			System.out.println("Reconnection (connection de secours)!!!");
		}
		catch(SQLException e)
		{
			displayError(e);
		}
	}
	
	private void updateNbExecutedQueriesConnection()
	{
		_nbExecutedQueriesConnection++;
		
		System.out.println("Nombre requête executée (connection principale) : " + _nbExecutedQueriesConnection);
		
		if(_nbExecutedQueriesConnection >= RECONNECTION_PERIOD)
		{
			_currentConnection = _connectionBack;
			_connectionBackReconnected = false;
		}
		else
			if(_nbExecutedQueriesConnection >= (RECONNECTION_PERIOD / 2))
				if(!_connectionBackReconnected)
					reconnectionConnectionBack();
	}
	
	private void updateNbExecutedQueriesConnectionBack()
	{
		_nbExecutedQueriesConnectionBack++;
		
		System.out.println("Nombre requête executée (connection de secours) : " + _nbExecutedQueriesConnectionBack);

		if(_nbExecutedQueriesConnectionBack >= RECONNECTION_PERIOD)
		{
			_currentConnection = _connection;
			_connectionReconnected = false;
		}
		else
			if(_nbExecutedQueriesConnectionBack >= (RECONNECTION_PERIOD / 2))
				if(!_connectionReconnected)
					reconnectionConnection();
	}
	
	private void updateNbExecutiionQueries()
	{
		if(_currentConnection == _connection)
			updateNbExecutedQueriesConnection();
		else
			updateNbExecutedQueriesConnectionBack();
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
			java.sql.Statement statement = _currentConnection.createStatement();
			updateNbExecutiionQueries();
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
			java.sql.Statement statement = _currentConnection.createStatement();
			updateNbExecutiionQueries();
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
			java.sql.PreparedStatement statement = _currentConnection.prepareStatement(query.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
			updateNbExecutiionQueries();
			statement.executeUpdate();
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			while(generatedKeys.next())
				lastInserted = generatedKeys.getInt(1);
			
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
	 * Stores an image in the database.  
	 * @param name the name of the image to insert
	 * @param imagePath the path of the image to insert
	 */
	public int storeImage(String name, String imagePath, int idOperation)
	{
		Integer id = -1;
		
	    try
	    {
	    	final String tables = "Carte(id, operation_id, nom, visibilite, image)";
	    	final String values = "(?, ?, ?, ?, ?)";
	    	final SQLQueryInsert query = new SQLQueryInsert(tables, values);
	    	
	    	File file = new File(imagePath);
	    	FileInputStream fileInputStream = new FileInputStream(file);
	    	PreparedStatement preparedStatement = null;
	    	
	    	// Insertion de l'image
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
	    	
	    	preparedStatement.close();
	    	fileInputStream.close();
	    	
	    	reconnectionConnection();
	    }
	    catch(Exception e)
	    {
	    	displayError(e);
	    	MessagePanel errorPanel = new MessagePanel("Erreur interne - Insertion carte '"+name+"'", "Une erreur interne est survenue lors de l'ajout de la carte'"+name+"'. Veuillez recommencer s'il vous plait.");
	    	new MyJDialog(errorPanel, _operation.getGlobalPanel());
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
	
	public static String addSlashes(String input)
	{
		String output = null;
		
		if(input != null)
		{
			output = input.replace("'", "\\'");
			output = output.replace("`", "\\`");
		}
		
		return output;
	}
	
	public static String stripSlashes(String input)
	{
		String output = null;
		
		if(input != null)
		{
			output = input.replace("\\'", "'");
			output = output.replace("\\`", "`");
		}
		
		return output;
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
		
		try
		{
			java.sql.Statement statement;
			
			if(!_connection.isClosed())
				statement = _connection.createStatement();
			else
				statement = _connectionBack.createStatement();

			JOptionPane.showMessageDialog(null, errorMessage + statement);
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
		
		System.exit(0);
	}
}
