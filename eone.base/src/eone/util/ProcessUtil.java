
package eone.util;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.Core;
import eone.base.model.MProcess;
import eone.base.process.ProcessCall;
import eone.base.process.ProcessInfo;

/**
 *
 * @author Quynhnv.x8
 * Mod: 25/09/2020
 */
public final class ProcessUtil {

	public static final String JASPER_STARTER_CLASS = "org.adempiere.report.jasper.ReportStarter";

	/**	Logger				*/
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);

	private ProcessUtil() {}

	
	public static boolean startDatabaseProcedure (ProcessInfo processInfo, String ProcedureName, Trx trx) {
		return startDatabaseProcedure(processInfo, ProcedureName, trx, true);
	}

	
	public static boolean startDatabaseProcedure (ProcessInfo processInfo, String ProcedureName, Trx trx, boolean managedTrx) {
		String sql = "{call " + ProcedureName + "(?)}";
		String trxName = trx != null ? trx.getTrxName() : null;
		CallableStatement cstmt = null;
		try
		{
			//hengsin, add trx support, updateable support.
			cstmt = DB.prepareCall(sql, ResultSet.CONCUR_UPDATABLE, trxName);
			cstmt.setString(1, processInfo.getAD_Session_ID());
			cstmt.executeUpdate();
			if (trx != null && trx.isActive() && managedTrx)
			{
				trx.commit(true);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			if (trx != null && trx.isActive() && managedTrx)
			{
				trx.rollback();
			}
			processInfo.setSummary (Msg.getMsg(Env.getCtx(), "ProcessRunError") + " " + e.getLocalizedMessage());
			processInfo.setError (true);
			return false;
		}
		finally
		{
			DB.close(cstmt);
			cstmt = null;
			if (trx != null && managedTrx)
				trx.close();
		}
		return true;
	}

	@Deprecated
	public static boolean startJavaProcess(ProcessInfo pi, Trx trx, HashMap<String, Object> params) {
		return startJavaProcess(Env.getCtx(), pi, trx, params);
	}

	/**
	 * @param ctx
	 * @param pi
	 * @param trx
	 * @return boolean
	 */
	public static boolean startJavaProcess(Properties ctx, ProcessInfo pi, Trx trx, HashMap<String, Object> params) {
		return startJavaProcess(ctx, pi, trx, true, params);
	}

	/**
	 * @param ctx
	 * @param pi
	 * @param trx
	 * @param managedTrx false if trx is managed by caller
	 * @return boolean
	 */
	public static boolean startJavaProcess(Properties ctx, ProcessInfo pi, Trx trx, boolean managedTrx, HashMap<String, Object> params) {
		return startJavaProcess(ctx, pi, trx, managedTrx, null, params);
	}
	
	/**
	 * @param ctx
	 * @param pi
	 * @param trx
	 * @param managedTrx false if trx is managed by caller
	 * @return boolean
	 */
	public static boolean startJavaProcess(Properties ctx, ProcessInfo pi, Trx trx, boolean managedTrx, 
			IProcessUI processMonitor, HashMap<String, Object> params) {
		String className = pi.getClassName();
		if (className == null) {
			MProcess proc = new MProcess(ctx, pi.getAD_Process_ID(), trx.getTrxName());
			if (proc.getJasperReport() != null)
				className = JASPER_STARTER_CLASS;
		}

		ProcessCall process = null;
		//invoke process factory
		process = Core.getProcess(className);

		if (process == null) {
			pi.setSummary("Failed to create new process instance for " + className, true);
			return false;
		}

		boolean success = false;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try
		{			
			Thread.currentThread().setContextClassLoader(process.getClass().getClassLoader());
			process.setProcessUI(processMonitor);
			success = process.startProcess(ctx, pi, trx, params);
			if (success && trx != null && managedTrx)
			{
				trx.commit(true);
			}
		}
		catch (Throwable e)
		{
			pi.setSummary (Msg.getMsg(Env.getCtx(), "ProcessError") + " " + e.getLocalizedMessage(), true);
			log.log(Level.SEVERE, pi.getClassName(), e);
			success = false;
			return false;
		}
		finally
		{
			if (trx != null && managedTrx)
			{
				if (!success) {
					trx.rollback();
				}
				trx.close();
				trx = null;
			}
			Thread.currentThread().setContextClassLoader(cl);
		}
		return success;
	}

	
	public static boolean startJavaProcessWithoutTrxClose(Properties ctx, ProcessInfo pi, Trx trx, HashMap<String, Object> params) {
		return startJavaProcess(ctx, pi, trx, false, params);
	}


}