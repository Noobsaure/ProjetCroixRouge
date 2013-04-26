package database;

public class SQLQueryUpdate
{
	private static final String _name = "update";
	private String _tables;
	private String _newValues;
	private String _conditions;

	
	public SQLQueryUpdate(String tables, String newValues) throws MalformedQueryException
	{
		if(tables == null)
			throw new MalformedQueryException("");
		if(newValues == null)
			throw new MalformedQueryException("");
		
		_tables = tables;
		_newValues = newValues;
		_conditions = null;
	}
	
	public SQLQueryUpdate(String tables, String newValues, String conditions) throws MalformedQueryException
	{
		if(tables == null)
			throw new MalformedQueryException("");
		if(newValues == null)
			throw new MalformedQueryException("");
		
		_tables = tables;
		_newValues = newValues;
		_conditions = conditions;
	}
	

	public String getTables()
	{
		return _tables;
	}
	public String getNewValues()
	{
		return _newValues;
	}
	public String getConditions()
	{
		return _conditions;
	}
	
	
	@Override
	public String toString()
	{
		return (_name + " " + _tables + " set " + _newValues + ((_conditions != null) ? " where " + _conditions : ""));
	}
}
