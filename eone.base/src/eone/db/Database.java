
package eone.db;

import java.util.ArrayList;
import java.util.List;

import eone.base.IServiceReferenceHolder;
import eone.base.Service;
import eone.base.ServiceQuery;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.Util;


public class Database
{
	private static final CLogger			log = CLogger.getCLogger (Database.class);
	
	public static String        DB_ORACLE   = "Oracle";               	
    
	public static String        DB_POSTGRESQL = "PostgreSQL";
	
	public static String        DB_MSSQL = "MSSQL";

	public static int           CONNECTION_TIMEOUT = 10;
	
	public static final int         DB_ORACLE_DEFAULT_PORT = 1521;
    
	public static final int         DB_ORACLE_DEFAULT_CM_PORT = 1630;
    
    public static final int         DB_POSTGRESQL_DEFAULT_PORT = 5432;
    
    public static final int         DB_MSSQL_DEFAULT_PORT = 1433;
	
	private static final CCache<String, IServiceReferenceHolder<EONEDatabase>> s_databaseReferenceCache = new CCache<>(null, "IDatabase", 3, false);
	
	public static EONEDatabase getDatabase (String type)
	{
		EONEDatabase db = null;
		IServiceReferenceHolder<EONEDatabase> cache = s_databaseReferenceCache.get(type);
		if (cache != null) {
			db = cache.getService();
			if (db != null)
				return db;
			s_databaseReferenceCache.remove(type);
		}
		ServiceQuery query = new ServiceQuery();
		query.put("id", type);
		IServiceReferenceHolder<EONEDatabase> serviceReference = Service.locator().locate(EONEDatabase.class, query).getServiceReference();
		if (serviceReference != null) {
			db = serviceReference.getService();
			s_databaseReferenceCache.put(type, serviceReference);
		}
		return db;
	}
	
	public static String[] getDatabaseNames()
	{
		List<String> names = new ArrayList<String>();
		List<EONEDatabase> services = Service.locator().list(EONEDatabase.class).getServices();
		for (EONEDatabase db : services) {
			names.add(db.getName());
		}
		return names.toArray(new String[0]);
	}
	
	
	public static EONEDatabase getDatabaseFromURL(String url)
	{
		if (url == null)
		{
			log.severe("No Database URL");
			return null;
		}
		if (url.indexOf("oracle") != -1)
			return getDatabase(DB_ORACLE);
        if (url.indexOf("postgresql") != -1)
			return getDatabase(DB_POSTGRESQL);

		log.severe("No Database for " + url);
		return null;
	}

	public static String isValidIdentifier(String identifier)
	{
		if (Util.isEmpty(identifier))
			return "InvalidIdentifierEmpty";
		if (identifier.contains(" "))
			return "InvalidIdentifierSpaces";
		if (! identifier.substring(0,  1).matches("[a-zA-Z]"))
			return "InvalidIdentifierFirstCharAlpha";
		if (! identifier.matches("^[a-zA-Z0-9_]*$"))
			return "InvalidIdentifierJustAlpha";
		return null;
	}

}  
