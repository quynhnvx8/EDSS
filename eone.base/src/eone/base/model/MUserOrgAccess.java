
package eone.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;


public class MUserOrgAccess extends X_AD_User_OrgAccess
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11601583764711895L;


	
	public static MUserOrgAccess[] getOfUser (Properties ctx, int AD_User_ID)
	{
		return get (ctx, "SELECT * FROM AD_User_OrgAccess WHERE AD_User_ID=?", AD_User_ID);	
	}	//	getOfUser

	
	private static MUserOrgAccess[] get (Properties ctx, String sql, int id)
	{
		ArrayList<MUserOrgAccess> list = new ArrayList<MUserOrgAccess>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, id);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MUserOrgAccess(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MUserOrgAccess[] retValue = new MUserOrgAccess[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	get
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MUserOrgAccess.class);

	
	
	public MUserOrgAccess (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MUserOrgAccess

	
	public MUserOrgAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		setIsReadOnly(false);
	}	//	MUserOrgAccess
	
	
	public MUserOrgAccess (MOrg org, int AD_User_ID)
	{
		this (org.getCtx(), 0, org.get_TrxName());
		setAD_User_ID (AD_User_ID);
	}	//	MUserOrgAccess

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (newRecord || is_ValueChanged(X_AD_User_OrgAccess.COLUMNNAME_AD_Org_ID)) {
			if (Env.isUserSystem(getCtx()) && getAD_Org_ID() == 0) {
				log.saveError("Error!", "Vai trò này không được phép khai báo Org hệ thống");
				return false;
			}
		}
		return true;
	}

	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MUserOrgAccess[");
		sb.append("AD_User_ID=").append(getAD_User_ID())
			.append(",AD_Client_ID=").append(getAD_Client_ID())
			.append(",AD_Org_ID=").append(getAD_Org_ID())
			.append(",RO=").append(isReadOnly());	
		sb.append("]");
		return sb.toString();
	}	//	toString

	
	/**************************************************************************
	 * 	Extended String Representation
	 * 	@param ctx context
	 *	@return extended info
	 */
	public String toStringX (Properties ctx)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Msg.translate(ctx, "AD_Client_ID")).append("=").append(getClientName()).append(" - ")
			.append(Msg.translate(ctx, "AD_Org_ID")).append("=").append(getOrgName());	
		return sb.toString();
	}	//	toStringX

	private String	m_clientName;
	private String	m_orgName;
	
	/**
	 * 	Get Client Name
	 *	@return name
	 */
	public String getClientName()
	{
		if (m_clientName == null)
		{
			String sql = "SELECT c.Name, o.Name "
				+ "FROM AD_Client c INNER JOIN AD_Org o ON (c.AD_Client_ID=o.AD_Client_ID) "
				+ "WHERE o.AD_Org_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, getAD_Org_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_clientName = rs.getString(1);
					m_orgName = rs.getString(2);
				}
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
		return m_clientName;
	}	//	getClientName
	
	/**
	 * 	Get Client Name
	 *	@return name
	 */
	public String getOrgName()
	{
		if (m_orgName == null)
			getClientName();
		return m_orgName;
	}	//	getOrgName

	
	public static void createForOrg (Properties ctx, MOrg org, String trxName)
	{
		int BATCH_SIZE = Env.getBatchSize(ctx);
		String sql = PO.getSqlInsert(X_AD_User_OrgAccess.Table_ID, org.get_TrxName());
		String whereClause = "AD_User.AD_Client_ID = ?";
		List<MUser> users = new Query(ctx, X_AD_User.Table_Name, whereClause, trxName)
				.setParameters(Env.getAD_Client_ID(ctx))
				.list();
		
		List<List<Object>> values = new ArrayList<List<Object>>();
		for (int i = 0; i < users.size(); i++)
		{
			MUserOrgAccess orgAccess = new MUserOrgAccess (org, users.get(i).getAD_User_ID());
			int ID = DB.getNextID(ctx, X_AD_User_OrgAccess.Table_Name, trxName);
			orgAccess.setAD_Client_ID(org.getAD_Client_ID());
			orgAccess.setAD_Org_ID(org.getAD_Org_ID());
			orgAccess.setIsActive(true);
			orgAccess.setIsReadOnly(false);
			orgAccess.setAD_User_ID(users.get(i).getAD_User_ID());
			orgAccess.setAD_User_OrgAccess_ID(ID);
			orgAccess.set_ValueNoCheck("CreatedBy", Env.getAD_User_ID(Env.getCtx()));
			orgAccess.set_ValueNoCheck("UpdatedBy", Env.getAD_User_ID(Env.getCtx()));
			orgAccess.set_ValueNoCheck("Created", new Timestamp(new Date().getTime()));
			orgAccess.set_ValueNoCheck("Updated", new Timestamp(new Date().getTime()));
			orgAccess.setAD_Client_ID(org.getAD_Client_ID());
			orgAccess.setAD_Org_ID(org.getAD_Org_ID());
			
			List<String> colNames = PO.getSqlInsert_Para(X_AD_User_OrgAccess.Table_ID, trxName);
			List<Object> param = PO.getBatchValueList(orgAccess, colNames, X_AD_User_OrgAccess.Table_ID, trxName, ID);
			
			values.add(param);
			
			if (values.size() >= BATCH_SIZE) {
				String err = DB.excuteBatch(sql, values, trxName);
				if (err !=  null) {
					try {
						DB.rollback(false, trxName);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				values.clear();
			}
			
		}
		if (values.size() > 0) {
			String err = DB.excuteBatch(sql, values, trxName);
			if (err !=  null) {
				try {
					DB.rollback(false, trxName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			values.clear();			
		}
		
	}
}	//	MUserOrgAccess
