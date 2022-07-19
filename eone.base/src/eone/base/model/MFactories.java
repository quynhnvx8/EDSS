
package eone.base.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.CCache;
import eone.util.DB;

/**
 *  @author Client
 */
public class MFactories extends X_M_Factories implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8885316310068284701L;


	
	public static MFactories get (Properties ctx, int M_BOM_ID)
	{
		Integer key = Integer.valueOf(M_BOM_ID);
		MFactories retValue = (MFactories) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MFactories (ctx, M_BOM_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	
	public static MFactories[] getOfProduct (Properties ctx, int M_Product_ID, 
		String trxName, String whereClause)
	{
		StringBuilder where = new StringBuilder("M_Product_ID=?");
		if (whereClause != null && whereClause.length() > 0)
			where.append(" AND ").append(whereClause);
		List <MFactories> list = new Query(ctx, Table_Name, where.toString(), trxName)
		.setParameters(M_Product_ID)
		.list();
		
		MFactories[] retValue = new MFactories[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfProduct

	/**	Cache						*/
	private static CCache<Integer,MFactories>	s_cache	= new CCache<Integer,MFactories>(Table_Name, 20);
	
	public MFactories (Properties ctx, int M_BOM_ID, String trxName)
	{
		super (ctx, M_BOM_ID, trxName);
		
	}

	
	public MFactories (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true/false
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		String sql = "SELECT COUNT(1) FROM M_Factories WHERE M_Product_ID = ? AND M_Warehouse_ID = ? AND M_Factories_ID !=?";
		List<Object> params = new ArrayList<Object>();
		params.add(getM_Product_ID());
		params.add(getM_Warehouse_ID());
		params.add(getM_Factories_ID());
		int count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count >= 1) {
			log.saveError("Error", "Sản phẩm này đã thiết lập rồi");
			return false;
		}
		
		sql = "SELECT COUNT(1) FROM M_BOM WHERE M_Product_ID = ? AND M_Warehouse_ID = ?";
		params = new ArrayList<Object>();
		params.add(getM_Product_ID());
		params.add(getM_Warehouse_ID());
		count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count <= 0) {
			log.saveError("Error", "Sản phẩm và phân xưởng không khớp với thiết lập định mức");
			return false;
		}
		
		return true;
	}	//	beforeSave
	
	
	//Implements DocAction
	protected String		m_processMsg = null;
	
	protected MBOMProduct[]	m_lines = null;
	
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		StringBuilder sql = new StringBuilder("UPDATE M_FactoriesLine SET Processed='")
			.append((processed ? "Y" : "N"))
			.append("' WHERE M_Factories_ID=").append(getM_Factories_ID());
		int noLine = DB.executeUpdate(sql.toString(), get_TrxName());
		m_lines = null;
		if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
	}
	
	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		return engine.processIt (action, getDocStatus());
	}

	@Override
	public String completeIt() {
		String sql = "SELECT COUNT(1) FROM M_FactoriesLine "
				+ " WHERE M_Product_ID = ? AND M_Factories_ID = ? "
				+ "		AND OrderNo = (SELECT MAX(OrderNo) FROM M_FactoriesLine WHERE M_Factories_ID = ?)";
		List<Object> params = new ArrayList<Object>();
		params.add(getM_Product_ID());
		params.add(getM_Factories_ID());
		params.add(getM_Factories_ID());
		int count = DB.getSQLValue(get_TrxName(), sql, params);
		if (count <= 0) {
			m_processMsg = "Thứ tự cuối cùng phải là sản phẩm cuối cùng";
			return DocAction.STATUS_Drafted;
		}
		setProcessed(true);
		return DocAction.STATUS_Completed;
	}



	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(!super.reActivate())
			return false;
		
		setProcessed(false);
			
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
}	//	MBOM
