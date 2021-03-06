
package eone.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.model.AccessSqlParser.TableInfo;
import eone.exceptions.EONEException;
import eone.util.CCache;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;


public class MInfoWindow extends X_AD_InfoWindow
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1619434756919905441L;


	public MInfoWindow (Properties ctx, int AD_InfoWindow_ID, String trxName)
	{
		super (ctx, AD_InfoWindow_ID, trxName);
	}	//	MInfoWindow


	public MInfoWindow (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MInfoWindow

	protected static CCache<String,MInfoWindow> s_cache = new CCache<String,MInfoWindow>(Table_Name, 50 );
	
	public static MInfoWindow get(String tableName, String trxName) {
		String key = tableName;
		MInfoWindow retValue = (MInfoWindow)s_cache.get(key);
		if (retValue != null)
			return retValue;
		
		
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table != null) {
			final String whereClause = "AD_Table_ID=? And IsActive = 'Y'";
			retValue = new Query(Env.getCtx(),X_AD_InfoWindow.Table_Name,whereClause,trxName)
			.setParameters(table.getAD_Table_ID())
			.firstOnly();
			s_cache.put (key, retValue);
			return retValue;
		}
		return null;
	}


	public static MInfoWindow getInfoWindow(int AD_InfoWindow_ID) {

		if (AD_InfoWindow_ID != 0) {

			MInfoWindow infoWin =  (MInfoWindow)new Query(Env.getCtx(), Table_Name, "AD_InfoWindow_ID=?", null)
				.setParameters(AD_InfoWindow_ID)
				.first();

			if (infoWin != null)
				return infoWin;
		}

		return null;
	}


	/** return true if the current role can access to the specified info window ; otherwise return null */
	public static MInfoWindow get(int infoWindowID, String trxName) {
		MInfoWindow iw = new MInfoWindow(Env.getCtx(), infoWindowID, null);
		return iw;
	}

	public MInfoColumn[] getInfoColumns(TableInfo[] tableInfos) {
		Query query = new Query(getCtx(), MTable.get(getCtx(), I_AD_InfoColumn.Table_ID), I_AD_InfoColumn.COLUMNNAME_AD_InfoWindow_ID+"=?", get_TrxName());
		List<MInfoColumn> list = query.setParameters(getAD_InfoWindow_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("SeqNo, AD_InfoColumn_ID")
				.list();
		for(int i = list.size() - 1; i >= 0; i--) {
			MInfoColumn infoColumn = list.get(i);
			if (!infoColumn.isColumnAccess(tableInfos))
				list.remove(i);
		}
		return list.toArray(new MInfoColumn[0]);
	}
	
	public MInfoColumn[] getInfoColumns() {
		Query query = new Query(getCtx(), MTable.get(getCtx(), I_AD_InfoColumn.Table_ID), I_AD_InfoColumn.COLUMNNAME_AD_InfoWindow_ID+"=?", get_TrxName());
		List<MInfoColumn> list = query.setParameters(getAD_InfoWindow_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("SeqNo, AD_InfoColumn_ID")
				.list();
		return list.toArray(new MInfoColumn[0]);
	}

	/**
	 * @author xolali
	 */
	private MInfoColumn[] m_infocolumns = null;

	public MInfoColumn[] getInfoColumns(boolean requery, boolean checkDisplay) {
		if ((this.m_infocolumns != null) && (!requery)) {
			set_TrxName(this.m_infocolumns, get_TrxName());
			return this.m_infocolumns;
		}
		if (checkDisplay) {
			List<MInfoColumn> list = new Query(getCtx(), MInfoColumn.Table_Name, "AD_InfoWindow_ID=? AND IsDisplayed='Y'", get_TrxName())
				.setParameters(get_ID())
				.setOrderBy("SeqNo")
				.list();
			this.m_infocolumns = list.toArray(new MInfoColumn[list.size()]);
		} else {
			List<MInfoColumn> list = new Query(getCtx(), MInfoColumn.Table_Name, "AD_InfoWindow_ID=?", get_TrxName())
				.setParameters(get_ID())
				.setOrderBy("SeqNo")
				.list();
			this.m_infocolumns = list.toArray(new MInfoColumn[list.size()]);
		}

		return this.m_infocolumns;
	}

	/**
	 * @author xolali
	 * @return
	 */
	public String getSql(){

		String fromsql = getFromClause();
		String oclause = getOtherClause();
		if (oclause == null)
			oclause=" ";

		//boolean success = true;
		MInfoColumn[] mColumns = getInfoColumns(true,true);
		StringBuilder sql = new StringBuilder("SELECT ");
		int size = mColumns.length;//get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			if (i != 0) // can also use if i>0
				sql.append(",");
			sql.append(mColumns[i].getSelectClause());//getColumnSQL());	//	Normal and Virtual Column
		}
		sql.append(" FROM ").append(fromsql)//getTableName())
		.append(oclause);
		log.info("Generated SQL -- getSql: "+ sql.toString());

		return sql.toString();
	}

	public boolean validateSql(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = getSql();
		try
		{
			String countSql = Msg.parseTranslation(Env.getCtx(), sql.toString());	//	Variables
			countSql = MRole.addAccessSQL(countSql, MTable.getTableName(Env.getCtx(), getAD_Table_ID()),// getTableName(), 
					MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO, Env.getCtx());

			pstmt = DB.prepareStatement(countSql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				break;
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return false;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return true;
	}  // validate sql

	@Override
	protected boolean beforeSave(boolean newRecord) {
		AccessSqlParser parser = new AccessSqlParser("SELECT * FROM " + getFromClause());
		TableInfo[] tableInfos = parser.getTableInfo(0);
		if (tableInfos == null || tableInfos.length == 0) {
			log.saveError("ParseFromClauseError", "Failed to parse from clause");
			return false;
		}
		
		//only one default per table
		if (newRecord || is_ValueChanged("IsDefault")) {
			if (isDefault()) {				
				if (newRecord) {
					Query query = new Query(getCtx(), MTable.get(getCtx(), Table_ID), 
							"AD_Table_ID=? AND IsDefault='Y' AND AD_Client_ID=?", get_TrxName());
					List<MInfoWindow> list = query.setParameters(getAD_Table_ID(),getAD_Client_ID()).list();
					for(MInfoWindow iw : list) {
						iw.setIsDefault(false);
						iw.saveEx();
					}
				} else {
					Query query = new Query(getCtx(), MTable.get(getCtx(), Table_ID), 
							"AD_InfoWindow_ID<>? AND AD_Table_ID=? AND IsDefault='Y' AND AD_Client_ID=?", get_TrxName());
					List<MInfoWindow> list = query.setParameters(getAD_InfoWindow_ID(), getAD_Table_ID(), getAD_Client_ID()).list();
					for(MInfoWindow iw : list) {
						iw.setIsDefault(false);
						iw.saveEx();
					}
				}
			}
		}
		
		// evaluate need valid
		boolean isNeedValid = is_new() || is_ValueChanged (I_AD_InfoWindow.COLUMNNAME_AD_Table_ID) || is_ValueChanged (I_AD_InfoWindow.COLUMNNAME_WhereClause) ||
								is_ValueChanged (I_AD_InfoWindow.COLUMNNAME_FromClause) || is_ValueChanged (I_AD_InfoWindow.COLUMNNAME_OrderByClause) ||
								is_ValueChanged (I_AD_InfoWindow.COLUMNNAME_OtherClause) || is_ValueChanged (I_AD_InfoWindow.COLUMNNAME_IsDistinct);
		
		// valid config 
		if (isNeedValid){
			validate();
		}
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success)
			return success;
		
		else if (is_ValueChanged("IsActive") || is_ValueChanged("Name") 
			|| is_ValueChanged("Description"))
		{
			MMenu[] menues = MMenu.get(getCtx(), "AD_InfoWindow_ID=" + getAD_InfoWindow_ID(), get_TrxName());
			for (int i = 0; i < menues.length; i++)
			{
				menues[i].setName(getName());
				menues[i].setDescription(getDescription());
				menues[i].setIsActive(isActive());
				menues[i].saveEx();
			}
			//
		}
		return super.afterSave(newRecord, success);
	}
	
	
	public void validate ()
	{		
		// default, before complete check is invalid
		this.setIsValid(false);		
		
		// add DISTINCT clause
		StringBuilder builder = new StringBuilder("SELECT ");
		if (this.isDistinct())
			builder.append("DISTINCT ");
		
		MInfoColumn[] infoColumns = this.getInfoColumns();
		// none column make this invalid
		if (infoColumns.length == 0){
			return;
		}
		
		// build select clause
		for (int columnIndex = 0; columnIndex < infoColumns.length; columnIndex++) {
			if (columnIndex > 0)
            {
                builder.append(", ");
            }
			builder.append(infoColumns[columnIndex].getSelectClause());
		}
		
		// build from clause
		builder.append( " FROM ").append(this.getFromClause());
		
		// build where clause add (1=2) because not need get result, decrease load 
		if (this.getWhereClause() != null && this.getWhereClause().trim().length() > 0) {
			builder.append(" WHERE (1=2) AND (").append(this.getWhereClause()).append(")");
		} else {
			builder.append(" WHERE 1=2");
		}
		
		// build other (having) clause
		if (this.getOtherClause() != null && this.getOtherClause().trim().length() > 0) {
			builder.append(" ").append(this.getOtherClause());
		}
		
		// build order (having) clause
		if (this.getOrderByClause() != null && this.getOrderByClause().trim().length() > 0) {
			builder.append(" ORDER BY ").append(this.getOrderByClause());
		}
		
		// replace env value by dummy value
		while(builder.indexOf("@") >= 0) {
			int start = builder.indexOf("@");
			int end = builder.indexOf("@", start+1);
			if (start >=0 && end > start) {
				builder.replace(start, end+1, "0");
			} else {
				break;
			}
		}
		
		// try run sql
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(builder.toString(), get_TrxName());
			pstmt.executeQuery();
		}catch (Exception ex){
			log.log(Level.WARNING, ex.getMessage());
			throw new EONEException(ex);
		} finally {
			DB.close(pstmt);
		}
		
		// valid state
		this.setIsValid(true);		
	}

	
	private boolean m_validateEachColumn = true;

	public void setIsValidateEachColumn (boolean validateEachColumn) {
		m_validateEachColumn= validateEachColumn;
	}

	boolean isValidateEachColumn() {
		return m_validateEachColumn;
	}
	
	
}	//	MInfoWindow
