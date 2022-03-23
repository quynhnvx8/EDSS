
package eone.base;

import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import eone.base.acct.Doc;
import eone.base.model.MTable;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.EONEUserError;
import eone.util.Env;

/**
 *
 * @author hengsin
 *
 */
public class DefaultDocumentFactory implements IDocFactory {

	private final static CLogger s_log = CLogger.getCLogger(DefaultDocumentFactory.class);

	@Override
	public Doc getDocument(int AD_Table_ID, int Record_ID,
			String trxName) {
		String tableName = MTable.getTableName(Env.getCtx(), AD_Table_ID);
		//
		Doc doc = null;
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
				doc = getDocument(AD_Table_ID, rs, trxName);
			}
			else
				s_log.severe("Not Found: " + tableName + "_ID=" + Record_ID);
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return doc;
	}

	@Override
	public Doc getDocument(int AD_Table_ID, ResultSet rs,
			String trxName) {
		Doc doc = null;


		String tableName = MTable.getTableName(Env.getCtx(), AD_Table_ID);
		String packageName = "eone.base.acct";
		String className = null;

		int firstUnderscore = tableName.indexOf("_");
		if (firstUnderscore == 1)
			className = packageName + ".Doc_" + tableName.substring(2).replaceAll("_", "");
		else
			className = packageName + ".Doc_" + tableName.replaceAll("_", "");

		try
		{
			Class<?> cClass = Class.forName(className);
			Constructor<?> cnstr = cClass.getConstructor(new Class[] {ResultSet.class, String.class});
			doc = (Doc) cnstr.newInstance(rs, trxName);
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "Doc Class invalid: " + className + " (" + e.toString() + ")");
			throw new EONEUserError("Doc Class invalid: " + className + " (" + e.toString() + ")");
		}

		if (doc == null)
			s_log.log(Level.SEVERE, "Unknown AD_Table_ID=" + AD_Table_ID);
		return doc;
	}

}
