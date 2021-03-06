
package eone.base;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.model.PO;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Util;

/**
 * Default model factory implementation base on legacy code in MTable.
 * @author Jorg Janke
 * @author hengsin
 */
public class DefaultModelFactory implements IModelFactory {

	//private static CCache<String,Class<?>> s_classCache = new CCache<String,Class<?>>(null, "PO_Class", 20, false);
	private static CCache<String,Class<?>> s_classCache = new CCache<String,Class<?>>(null, "PO_Class", 100, 120, false, 2000);
	
	private final static CLogger s_log = CLogger.getCLogger(DefaultModelFactory.class);

	/**	Packages for Model Classes	*/
	private static final String[]	s_packages = new String[] {

		"eone.base.model",
		"eone.print", "eone.base.impexp",
		"eone.base.model"
	};

	/**	Special Classes				*/
	private static final String[]	s_special = new String[] {
		"AD_Element", "eone.base.model.M_Element",
		"AD_Tree", "eone.base.model.MTree_Base",
		"R_Category", "eone.base.model.MRequestCategory",
		"GL_Category", "eone.base.model.MGLCategory",
		"K_Category", "eone.base.model.MKCategory",
		"C_Account", "eone.base.model.MAccount",
		"C_Phase", "eone.base.model.MProjectTypePhase",
		"C_Task", "eone.base.model.MProjectTypeTask"
	};

	/* (non-Javadoc)
	 * @see org.adempiere.base.IModelFactory#getClass(java.lang.String)
	 */
	@Override
	public Class<?> getClass(String tableName) {
//		Not supported
		if (tableName == null || tableName.endsWith("_Trl"))
			return null;

		//check cache
		Class<?> cache = s_classCache.get(tableName);
		if (cache != null)
		{
			//Object.class indicate no generated PO class for tableName
			if (cache.equals(Object.class))
				return null;
			else
				return cache;
		}


		//	Import Tables (Name conflict)
		//  Import Tables doesn't manage model M classes, just X_
		if (tableName.startsWith("I_"))
		{
			String etmodelpackage = "eone.base.model";
			Class<?> clazz = getPOclass(etmodelpackage + ".X_" + tableName, tableName);
			if (clazz != null)
			{
				s_classCache.put(tableName, clazz);
				return clazz;
			}
			s_log.warning("No class for table: " + tableName);
			return null;
		}

		//	Special Naming
		for (int i = 0; i < s_special.length; i++)
		{
			if (s_special[i++].equals(tableName))
			{
				Class<?> clazz = getPOclass(s_special[i], tableName);
				if (clazz != null)
				{
					s_classCache.put(tableName, clazz);
					return clazz;
				}
				break;
			}
		}


		//	Strip table name prefix (e.g. AD_) Customizations are 3/4
		String className = tableName;
		int index = className.indexOf('_');
		if (index > 0)
		{
			if (index < 3)		//	AD_, A_
				 className = className.substring(index+1);
			
		}
		//	Remove underlines
		className = Util.replace(className, "_", "");

		//	Search packages
		for (int i = 0; i < s_packages.length; i++)
		{
			StringBuilder name = new StringBuilder(s_packages[i]).append(".M").append(className);
			Class<?> clazz = getPOclass(name.toString(), tableName);
			if (clazz != null)
			{
				s_classCache.put(tableName, clazz);
				return clazz;
			}
		}


		//	Adempiere Extension
		Class<?> clazz = getPOclass("base.model.X_" + tableName, tableName);
		if (clazz != null)
		{
			s_classCache.put(tableName, clazz);
			return clazz;
		}

		//hengsin - allow compatibility with compiere plugins
		//Compiere Extension
		clazz = getPOclass("base.model.X_" + tableName, tableName);
		if (clazz != null)
		{
			s_classCache.put(tableName, clazz);
			return clazz;
		}

		//	Default
		clazz = getPOclass("eone.base.model.X_" + tableName, tableName);
		if (clazz != null)
		{
			s_classCache.put(tableName, clazz);
			return clazz;
		}

		//Object.class to indicate no PO class for tableName
		s_classCache.put(tableName, Object.class);
		return null;
	}

	/**
	 * Get PO class
	 * @param className fully qualified class name
	 * @param tableName Optional. If specified, the loaded class will be validated for that table name
	 * @return class or null
	 */
	private Class<?> getPOclass (String className, String tableName)
	{
		try
		{
			Class<?> clazz = Class.forName(className);
			// Validate if the class is for specified tableName
			if (tableName != null)
			{
				String classTableName = clazz.getField("Table_Name").get(null).toString();
				if (!tableName.equals(classTableName))
				{
					if (s_log.isLoggable(Level.FINEST)) s_log.finest("Invalid class for table: " + className+" (tableName="+tableName+", classTableName="+classTableName+")");
					return null;
				}
			}
			//	Make sure that it is a PO class
			Class<?> superClazz = clazz.getSuperclass();
			while (superClazz != null)
			{
				if (superClazz == PO.class)
				{
					if (s_log.isLoggable(Level.FINE)) s_log.fine("Use: " + className);
					return clazz;
				}
				superClazz = superClazz.getSuperclass();
			}
		}
		catch (Exception e)
		{
		}
		if (s_log.isLoggable(Level.FINEST)) s_log.finest("Not found: " + className);
		return null;
	}	//	getPOclass

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		Class<?> clazz = getClass(tableName);
		if (clazz == null)
		{
			return null;
		}

		boolean errorLogged = false;
		try
		{
			Constructor<?> constructor = null;
			try
			{
				constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
			}
			catch (Exception e)
			{
				String msg = e.getMessage();
				if (msg == null)
					msg = e.toString();
				s_log.warning("No transaction Constructor for " + clazz + " (" + msg + ")");
			}

			PO po = constructor!=null ? (PO)constructor.newInstance(new Object[] {Env.getCtx(), Integer.valueOf(Record_ID), trxName}) : null;
			return po;
		}
		catch (Exception e)
		{
			if (e.getCause() != null)
			{
				Throwable t = e.getCause();
				s_log.log(Level.SEVERE, "(id) - Table=" + tableName + ",Class=" + clazz, t);
				errorLogged = true;
				if (t instanceof Exception)
					s_log.saveError("Error", (Exception)e.getCause());
				else
					s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
			}
			else
			{
				s_log.log(Level.SEVERE, "(id) - Table=" + tableName + ",Class=" + clazz, e);
				errorLogged = true;
				s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
			}
		}
		if (!errorLogged)
			s_log.log(Level.SEVERE, "(id) - Not found - Table=" + tableName
				+ ", Record_ID=" + Record_ID);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		Class<?> clazz = getClass(tableName);
		if (clazz == null)
		{
			return null;
		}

		boolean errorLogged = false;
		try
		{
			Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, ResultSet.class, String.class});
			PO po = (PO)constructor.newInstance(new Object[] {Env.getCtx(), rs, trxName});
			return po;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "(rs) - Table=" + tableName + ",Class=" + clazz, e);
			errorLogged = true;
			s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
		}
		if (!errorLogged)
			s_log.log(Level.SEVERE, "(rs) - Not found - Table=" + tableName);
		return null;
	}
}
