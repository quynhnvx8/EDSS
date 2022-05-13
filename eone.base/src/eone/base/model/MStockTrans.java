package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.DB;
import eone.util.Env;

public class MStockTrans extends X_M_StockTrans implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MStockTrans (Properties ctx, int M_StockTrans_ID, String trxName)
	{
		super (ctx, M_StockTrans_ID, trxName);
		
	}	//	MAssetUse

	
	public static MStockTrans get (Properties ctx, int M_StockTrans_ID)
	{
		return get(ctx,M_StockTrans_ID,null);
	}
	
	

	public static MStockTrans get (Properties ctx, int M_StockTrans_ID, String trxName)
	{
		final String whereClause = "M_StockTrans_ID=? AND AD_Client_ID=?";
		MStockTrans retValue = new Query(ctx,I_HR_Salary.Table_Name,whereClause,trxName)
		.setParameters(M_StockTrans_ID,Env.getAD_Client_ID(ctx))
		.firstOnly();
		return retValue;
	}
	
	
	
	public Map<Integer,MStockTransLine> getAllStock (Properties ctx, String trxName)
	{
		final String whereClause = "M_StockTrans_ID=? AND AD_Client_ID=?";
		List<MStockTransLine> retValue = new Query(ctx,I_HR_SalaryLine.Table_Name,whereClause,trxName)
				.setParameters(getM_StockTrans_ID(), getAD_Client_ID())
				.list();
		Map<Integer, MStockTransLine> listItems = new HashMap<Integer, MStockTransLine>();
		for(int i = 0; i < retValue.size(); i++) {
			listItems.put(retValue.get(i).getM_StockTrans_ID(), retValue.get(i));
		}
		return listItems;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		
		return true;
	}

	protected void updateProcessed(boolean status) {
		String sql = "Update M_StockTransLine set Processed = ? Where M_StockTrans_ID = ?";
		DB.executeUpdate(sql, new Object [] {status, getM_StockTrans_ID()}, true, get_TrxName());
	}

	public MStockTrans (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAssetUse


	protected boolean afterSave (boolean newRecord,boolean success)
	{
		log.info ("afterSave");
		if (!success)
			return success;
		
		return success;
		 
		
	}	//	afterSave
	
	protected MStockTransLine[]	m_lines = null;
	public MStockTransLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MStockTransLine> list = new Query(getCtx(), I_M_StockTransLine.Table_Name, "M_StockTrans_ID=?", get_TrxName())
		.setParameters(getM_StockTrans_ID())
		.list();
		//
		m_lines = new MStockTransLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	//	getMInOutLines

	/**
	 * 	Get Lines of Shipment
	 * 	@return lines
	 */
	public MStockTransLine[] getLines()
	{
		return getLines(false);
	}	//	getLines


	//Implements DocAction
	protected String		m_processMsg = null;
	
	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		return engine.processIt (action, getDocStatus());
	}

	@Override
	public String completeIt() {
		
		setProcessed(true);
		updateProcessed(true);
		return DocAction.STATUS_Completed;
	}



	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(!super.reActivate())
			return false;
		
		setProcessed(false);
		updateProcessed(false);
			
		return true;
	}


	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public void setProcessMsg(String text) {
		m_processMsg = text;
	}
	
	@Override
	public String getSummary() {
		
		return "";
	}


	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
