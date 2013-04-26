package database;

public class SQLQuerySelect
{
	private static final String _name = "select";
	private String _columns;
	private String _tables;
	private String _conditions;
	

	public SQLQuerySelect(String columns, String tables) throws MalformedQueryException
	{
		if(columns == null)
			throw new MalformedQueryException("");
		if(tables == null)
			throw new MalformedQueryException("");
		
		_columns = columns;
		_tables = tables;
		_conditions = null;
	}
	
	public SQLQuerySelect(String columns, String tables, String conditions) throws MalformedQueryException
	{
		if(columns == null)
			throw new MalformedQueryException("");
		if(tables == null)
			throw new MalformedQueryException("");
		
		_columns = columns;
		_tables = tables;
		_conditions = conditions;
	}
	
	
	@Override
	public String toString()
	{
		return (_name + " " + _columns + " from " + _tables + ((_conditions != null) ? " where " + _conditions : ""));
	}
}
