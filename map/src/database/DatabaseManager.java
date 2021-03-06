package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import views.CustomDialog;
import views.MessagePanel;
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
//			Class.forName("org.postgresql.Driver").newInstance();
			System.out.println("Driver charge");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erreur lors du chargement des pilotes de la base de données.");
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
			JOptionPane.showMessageDialog(null, "La connection à la base de données à échouée. Veuillez réessayer.");
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
			JOptionPane.showMessageDialog(null, "La connection à la base de données à échouée. Veuillez réessayer.");
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
			JOptionPane.showMessageDialog(null, "La connection à la base de données à échouée. Veuillez réessayer.");
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
			JOptionPane.showMessageDialog(null, "La connection à la base de données à échouée. Veuillez réessayer.");
		}
	}

	private void updateNbExecutedQueriesConnection()
	{
		_nbExecutedQueriesConnection++;

		//System.out.println("Nombre requête executée (connection principale) : " + _nbExecutedQueriesConnection);

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

		//System.out.println("Nombre requête executée (connection de secours) : " + _nbExecutedQueriesConnectionBack);

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
			updateNbExecutiionQueries();
			java.sql.Statement statement = _currentConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			result = statement.executeQuery(query.toString());
		}
		catch(Exception e)
		{
			displayError(e);
			MessagePanel errorPanel = new MessagePanel("La lecture de la base de données à échouée.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
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
		System.out.println("Execution de la requete : "+query);
		
		try
		{			
			updateNbExecutiionQueries();
			_currentConnection.setAutoCommit(true);
			java.sql.Statement statement = _currentConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			lastInserted = statement.executeUpdate(query.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
			statement.close();
		}
		catch(Exception e)
		{
			displayError(e);
			MessagePanel errorPanel = new MessagePanel("La mise à jour de la base de données à échouée.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
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
			updateNbExecutiionQueries();
			_currentConnection.setAutoCommit(true);
			
//			java.sql.PreparedStatement statementLock = _currentConnection.prepareStatement("LOCK TABLES " + query.getTables().replace(",", " WRITE ,") + " WRITE");
//			statementLock.execute();
	
//			System.out.println("Execution de la requete : " + lockTables);
			java.sql.Statement statement = _currentConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			lastInserted = statement.executeUpdate(query.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
			
//			java.sql.PreparedStatement statementUnlock = _currentConnection.prepareStatement("UNLOCK TABLES");
//			statementUnlock.execute();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			while(generatedKeys.next())
				lastInserted = generatedKeys.getInt(1);
			
			generatedKeys.close();
//			statement.close();
		}
		catch(Exception e)
		{
			displayError(e);
			MessagePanel errorPanel = new MessagePanel("La mise à jour de la base de données à échouée.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
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
	    	ImageIcon image = new ImageIcon(imagePath);	    	
	    	if(image.getIconWidth() != -1)
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
		    	updateNbExecutiionQueries();
		    	preparedStatement.executeUpdate();
		    	_connection.commit();
		    	
		    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		    	while(generatedKeys.next())
					id = generatedKeys.getInt(1);
		    	
		    	generatedKeys.close();
		    	preparedStatement.close();
		    	fileInputStream.close();
	    	}
	    }
	    catch(Exception e)
	    {
	    	displayError(e);
	    	MessagePanel errorPanel = new MessagePanel("Erreur interne - Insertion carte '"+name+"'", "Une erreur interne est survenue lors de l'ajout de la carte'"+name+"'. Veuillez recommencer s'il vous plait.");
	    	new CustomDialog(errorPanel, _operation.getGlobalPanel());
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
			MessagePanel errorPanel = new MessagePanel("La récupération dans la base de données de l'image '" + name + "' à échouée.");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
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
			output = input.replace("\\", "");
			output = output.replace("'", "\\'");
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

		JOptionPane.showMessageDialog(null, errorMessage);

		System.exit(0);
	}
	
	public java.sql.Timestamp getCurrentTime(){
		ResultSet result = null;
		java.sql.Timestamp currentTime = new java.sql.Timestamp(1);
		try {
			result = executeQuerySelect(new SQLQuerySelect("CURRENT_TIMESTAMP()","Operation LIMIT 1"));
		} catch (MalformedQueryException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne" ,"Une erreur est survenue lors de la recupération de l'heure du serveur");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		try {
			while(result.next()){
				currentTime = result.getTimestamp(1);
			}
		} catch (SQLException e) {
			MessagePanel errorPanel = new MessagePanel("Erreur interne" ,"Une erreur est survenue lors de la recupération de l'heure du serveur");
			new CustomDialog(errorPanel, _operation.getGlobalPanel());
		}
		
		return currentTime;
	}
}
