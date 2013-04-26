package database;

public class SQLQueryInsert
{
	private static final String _name = "insert into";
	private String _tables;
	private String _values;
	
	
	public SQLQueryInsert(String tables, String values) throws MalformedQueryException
	{
		if(tables == null)
			throw new MalformedQueryException("");
		if(values == null)
			throw new MalformedQueryException("");
		
		_tables = tables;
		_values = values;
	}
	
	
	public String getTables()
	{
		return _tables;
	}
	public String getValues()
	{
		return _values;
	}
	
	
	@Override
	public String toString()
	{
		return (_name + " " + _tables + " values " + _values);
	}
}
