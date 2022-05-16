
package eone.base.acct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import eone.base.IDocFactory;
import eone.base.IServiceReferenceHolder;
import eone.base.Service;
import eone.base.ServiceQuery;
import eone.exceptions.DBException;
import eone.exceptions.EONEException;
import eone.util.CCache;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Trx;


public class DocManager {

	private static final CCache<String, IServiceReferenceHolder<IDocFactory>> s_DocFactoryCache = new CCache<>(null, "IDocFactory", 100, false);
	
	private final static CLogger s_log = CLogger.getCLogger(DocManager.class);

	/** AD_Table_ID's of documents          */
	private static int[]  documentsTableID = null;

	/** Table Names of documents          */
	private static String[]  documentsTableName = null;

	
	public static int[] getDocumentsTableID() {
		fillDocumentsTableArrays();
		return documentsTableID;
	}

	public static String[] getDocumentsTableName() {
		fillDocumentsTableArrays();
		return documentsTableName;
	}

	private synchronized static void fillDocumentsTableArrays() {
		if (documentsTableID == null || documentsTableID.length == 0) {
			String sql = "SELECT t.AD_Table_ID, t.TableName " +
							"FROM AD_Table t, AD_Column c " +
							"WHERE t.AD_Table_ID=c.AD_Table_ID AND " +
							"c.ColumnName='DocStatus' AND " +
							"IsView='N' " +
							"ORDER BY t.AD_Table_ID";
			ArrayList<Integer> tableIDs = new ArrayList<Integer>();
			ArrayList<String> tableNames = new ArrayList<String>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					tableIDs.add(rs.getInt(1));
					tableNames.add(rs.getString(2));
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			//	Convert to array
			documentsTableID = new int[tableIDs.size()];
			documentsTableName = new String[tableIDs.size()];
			for (int i = 0; i < documentsTableID.length; i++)
			{
				documentsTableID[i] = tableIDs.get(i);
				documentsTableName[i] = tableNames.get(i);
			}
		}
	}


	public static Doc getDocument(int AD_Table_ID, ResultSet rs, String trxName)
	{
		String cacheKey = "" + AD_Table_ID;
		IServiceReferenceHolder<IDocFactory> cache = s_DocFactoryCache.get(cacheKey);
		if (cache != null)
		{
			IDocFactory service = cache.getService();
			if (service != null)
			{
				Doc doc = service.getDocument(AD_Table_ID, rs, trxName);
				if (doc != null)
					return doc;
			}
			s_DocFactoryCache.remove(cacheKey);
		}
		
		
		ServiceQuery query = new ServiceQuery();
		query.put("gaap", "GAAP");
		List<IServiceReferenceHolder<IDocFactory>> factoryList = Service.locator().list(IDocFactory.class,query).getServiceReferences();
		if (factoryList != null)
		{
			for(IServiceReferenceHolder<IDocFactory> factory : factoryList)
			{
				IDocFactory service = factory.getService();
				if (service != null)
				{
					Doc doc = service.getDocument(AD_Table_ID, rs, trxName);
					if (doc != null)
					{
						s_DocFactoryCache.put(cacheKey, factory);
						return doc;
					}
				}
			}
		}


		query.clear();
		query.put("gaap", "*");
		factoryList = Service.locator().list(IDocFactory.class,query).getServiceReferences();
		if (factoryList != null)
		{
			for(IServiceReferenceHolder<IDocFactory> factory : factoryList)
			{
				IDocFactory service = factory.getService();
				if (service != null)
				{
					Doc doc = service.getDocument(AD_Table_ID, rs, trxName);
					if (doc != null)
					{
						s_DocFactoryCache.put(cacheKey, factory);
						return doc;
					}
				}
			}
		}


		return null;
	}


	public static String postDocument(int AD_Table_ID, int Record_ID, String trxName, int AD_Window_ID) {

		String tableName = null;
		for (int i = 0; i < getDocumentsTableID().length; i++)
		{
			if (getDocumentsTableID()[i] == AD_Table_ID)
			{
				tableName = getDocumentsTableName()[i];
				break;
			}
		}
		if (tableName == null)
		{
			s_log.severe("Table not a financial document. AD_Table_ID=" + AD_Table_ID);
			StringBuilder msgreturn = new StringBuilder("Table not a financial document. AD_Table_ID=").append(AD_Table_ID);
			return msgreturn.toString();
		}

		StringBuilder sql = new StringBuilder("SELECT * FROM ")
		.append(tableName)
		.append(" WHERE ").append(tableName).append("_ID=? AND Processed='Y'");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), trxName);
			pstmt.setInt (1, Record_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				return postDocument(AD_Table_ID, rs, trxName, AD_Window_ID);
			}
			else
			{
				s_log.severe("Not Found: " + tableName + "_ID=" + Record_ID);
				return "NoDoc";
			}
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
				throw (RuntimeException)e;
			else
				throw new EONEException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	
	public static String postDocument(int AD_Table_ID, ResultSet rs, String trxName, int AD_Window_ID) {
		String localTrxName = null;
		if (trxName == null)
		{
			localTrxName = Trx.createTrxName("Post");
			trxName = localTrxName;
		}
		
		Trx trx = Trx.get(trxName, true);
		if (localTrxName != null)
			trx.setDisplayName(DocManager.class.getName()+"_postDocument");
		String error = null;
		Savepoint savepoint = null;
		try
		{
			savepoint = localTrxName == null ? trx.setSavepoint(null) : null;
			
			Doc doc = getDocument(AD_Table_ID, rs, trxName);
			
			if (doc != null)
			{
				doc.setAD_Window_ID(AD_Window_ID);
				error = doc.post ();	//	repost
				if (error != null && error.trim().length() > 0)
				{
					if (savepoint != null)
					{
						trx.rollback(savepoint);
						savepoint = null;
					}
					else
						trx.rollback();
					s_log.info("Error Posting " + doc + "  Error: " + error);
					
				}
			}
			else
			{
				if (savepoint != null)
				{
					trx.rollback(savepoint);
					savepoint = null;
				}
				else
					trx.rollback();

				s_log.info("Error Posting " + doc + " Error:  NoDoc");
				return "NoDoc";
			}

			
			
			if (savepoint != null)
			{
				try
				{
					trx.releaseSavepoint(savepoint);
				} catch (SQLException e1) {}
				savepoint = null;
			}
			if (localTrxName != null && error == null) {
				if (trx != null) {
					trx.commit();
				}
			}
		}
		catch (Exception e)
		{
			if (localTrxName != null) {
				if (trx != null)
					trx.rollback();
			} else if (trx != null && savepoint != null) {
				try {
					trx.rollback(savepoint);
				} catch (SQLException e1) {}
			}
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new EONEException(e);
		}
		finally
		{
			if (localTrxName != null)
			{
				if (trx != null)
					trx.close();
			}
		}
		return error;
	}

	
}
