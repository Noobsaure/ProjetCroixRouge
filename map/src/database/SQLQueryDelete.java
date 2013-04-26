package database;

public class SQLQueryDelete
{
	private static final String _name = "delete from";
	private String _tables;
	private String _conditions;
	
	
	public SQLQueryDelete(String tables, String conditions) throws MalformedQueryException
	{
		if(tables == null)
			throw new MalformedQueryException("");
		if(conditions == null)
			throw new MalformedQueryException("");
		
		_tables = tables;
		_conditions = conditions;
	}
	
	
	@Override
	public String toString()
	{
		return (_name + " " + _tables + " where " + _conditions);
	}
}
