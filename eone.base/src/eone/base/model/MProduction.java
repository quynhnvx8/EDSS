package eone.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.exceptions.EONEException;
import eone.util.CLogger;
import eone.util.DB;

public class MProduction extends X_M_Production implements DocAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4650232602150964606L;

	/**
	 * 
	 */
	/** Log								*/
	protected static CLogger		m_log = CLogger.getCLogger (MProduction.class);
	protected int lineno;
	protected int count;

	public MProduction(Properties ctx, int M_Production_ID, String trxName) {
		super(ctx, M_Production_ID, trxName);
		
	}

	public MProduction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MProduction( MOrderLine line ) {
		super( line.getCtx(), 0, line.get_TrxName());
	}

	
	@Override
	public String completeIt()
	{
		m_processMsg = null;
		if (m_processMsg != null)
			return DocAction.STATUS_Drafted;
		setProcessed(true);
		return DocAction.STATUS_Inprogress;
	}


	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		StringBuilder sql = new StringBuilder("UPDATE M_ProductionOutput SET Processed='")
			.append((processed ? "Y" : "N"))
			.append("' WHERE M_Production_ID=").append(getM_Production_ID());
		DB.executeUpdate(sql.toString(), get_TrxName());
		
		sql = new StringBuilder("UPDATE M_ProductionInput SET Processed = '")
				.append((processed ? "Y" : "N"))
				.append("' WHERE M_ProductionOutput_ID  IN (SELECT M_ProductionOutput_ID FROM M_ProductionOutput WHERE M_Production_ID = ")
				.append(getM_Production_ID())
				.append(")");
		DB.executeUpdate(sql.toString(), get_TrxName());
	}
	

	public MProductionOutput[] getLines() {
		ArrayList<MProductionOutput> list = new ArrayList<MProductionOutput>();
		
		String sql = "SELECT pl.M_ProductionOutput_ID "
			+ "FROM M_ProductionOutput pl "
			+ "WHERE pl.M_Production_ID = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, get_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add( new MProductionOutput( getCtx(), rs.getInt(1), get_TrxName() ) );	
		}
		catch (SQLException ex)
		{
			throw new EONEException("Unable to load production lines", ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		MProductionOutput[] retValue = new MProductionOutput[list.size()];
		list.toArray(retValue);
		return retValue;
	}
	
	public void deleteLines(String trxName) {

		String sqlDel = "DELETE FROM M_ProductionInput WHERE M_ProductionOutput_ID IN (SELECT M_ProductionOutput_ID FROM M_ProductionOutput WHERE M_Production_ID = ?)";
		DB.executeUpdate(sqlDel, getM_Production_ID(), trxName);
		
		sqlDel = "DELETE FROM M_ProductionOutput WHERE M_Production_ID = ?";
		DB.executeUpdate(sqlDel, getM_Production_ID(), trxName);
		
	}// deleteLines

	
	
	@Override
	protected boolean beforeDelete() {
		deleteLines(get_TrxName());
		return true;
	}

	
	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		return engine.processIt (action, getDocStatus());
	}

	/**	Process Message 			*/
	protected String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	protected boolean		m_justPrepared = false;

	

	


	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else{
			StringBuilder msgd = new StringBuilder(desc).append(" | ").append(description);
			setDescription(msgd.toString());
		}
	}	//	addDescription

	

	@Override
	public boolean reActivateIt() {
		if(!super.reActivate())
			return false;
		
		
		setProcessed(false);
		return true;
	}

	@Override
	public String getSummary() {
		return getDocumentNo() + " - " + getDescription();
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
	protected boolean beforeSave(boolean newRecord) {
		
		return true;
	}

	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
