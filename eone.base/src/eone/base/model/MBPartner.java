
package eone.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.util.CCache;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;


public class MBPartner extends X_C_BPartner
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5534148976588041343L;


	
	public static MBPartner get (Properties ctx, String Value) {
		return get(ctx,Value,null);		
	}


	public static MBPartner get (Properties ctx, String Value, String trxName)
	{
		if (Value == null || Value.length() == 0)
			return null;
		final String whereClause = "Value=?";
		MBPartner retValue = new Query(ctx, I_C_BPartner.Table_Name, whereClause, trxName)
		.setParameters(Value)
		.setApplyAccessFilter(true)
		.firstOnly();
		return retValue;
	}	//	get
	

	public static MBPartner getFirstWithTaxID (Properties ctx, String taxID, String trxName)
	{
		final String whereClause = "TaxID=?";
		MBPartner retValue = new Query(ctx, Table_Name, whereClause, trxName)
		.setParameters(taxID)
		.setApplyAccessFilter(true)
		.setOrderBy(COLUMNNAME_C_BPartner_ID)
		.first();
		return retValue;
	}	//	getFirstWithTaxID


	public static MBPartner get (Properties ctx, int C_BPartner_ID)
	{
		return get(ctx,C_BPartner_ID,null);
	}
	
	protected static CCache<String,MBPartner> s_cache = new CCache<String,MBPartner>(Table_Name, 50 );

	public static MBPartner get (Properties ctx, int C_BPartner_ID, String trxName)
	{
		String key = String.valueOf(C_BPartner_ID);
		MBPartner retValue = (MBPartner)s_cache.get(key);
		if (retValue != null)
			return retValue;
		final String whereClause = "C_BPartner_ID=?";
		retValue = new Query(ctx,I_C_BPartner.Table_Name,whereClause,trxName)
		.setParameters(C_BPartner_ID)
		.setApplyAccessFilter(true)
		.firstOnly();
		s_cache.put (key, retValue);
		return retValue;
	}	//	get

	

	public MBPartner (Properties ctx)
	{
		this (ctx, -1, null);
	}	//	MBPartner


	public MBPartner (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBPartner


	public MBPartner (Properties ctx, int C_BPartner_ID, String trxName)
	{
		super (ctx, C_BPartner_ID, trxName);
		
	}	//	MBPartner

	
	/** Users							*/
	protected MUser[]				m_contacts = null;
	/** BP Bank Accounts				*/
	protected Integer				m_primaryAD_User_ID = null;

	/**
	 * 	Load Default BPartner
	 * 	@param AD_Client_ID client
	 * 	@return true if loaded
	 */
	protected boolean initTemplate (int AD_Client_ID)
	{
		if (AD_Client_ID == 0)
			throw new IllegalArgumentException ("Client_ID=0");

		boolean success = true;
		String sql = "SELECT * FROM C_BPartner Where AD_Client_ID=?";
			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				success = load (rs);
			else
			{
				load(0, null);
				success = false;
				log.severe ("None found");
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
		setStandardDefaults();
		//	Reset
		set_ValueNoCheck ("C_BPartner_ID", I_ZERO);
		setValue ("");
		setName ("");
		setName2(null);
		set_ValueNoCheck ("C_BPartner_UU", "");
		return success;
	}	//	getTemplate


	/**
	 * 	Get All Contacts
	 * 	@param reload if true users will be requeried
	 *	@return contacts
	 */
	public MUser[] getContacts (boolean reload)
	{
		if (reload || m_contacts == null || m_contacts.length == 0)
			;
		else
			return m_contacts;
		//
		ArrayList<MUser> list = new ArrayList<MUser>();
		final String sql = "SELECT * FROM AD_User WHERE C_BPartner_ID=? ORDER BY AD_User_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_BPartner_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUser (getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		m_contacts = new MUser[list.size()];
		list.toArray(m_contacts);
		return m_contacts;
	}	//	getContacts

	/**
	 * 	Get specified or first Contact
	 * 	@param AD_User_ID optional user
	 *	@return contact or null
	 */
	public MUser getContact (int AD_User_ID)
	{
		MUser[] users = getContacts(false);
		if (users.length == 0)
			return null;
		for (int i = 0; AD_User_ID != 0 && i < users.length; i++)
		{
			if (users[i].getAD_User_ID() == AD_User_ID)
				return users[i];
		}
		return users[0];
	}	//	getContact
	
	
	
	/**************************************************************************
	 *	String Representation
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MBPartner[ID=")
			.append(get_ID())
			.append(",Value=").append(getValue())
			.append(",Name=").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString

	
	
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord || is_ValueChanged("Value") || isActive() || is_ValueChanged("Name")) {
			List<MBPartner> relValue = new Query(getCtx(), Table_Name, "C_BPartner_ID != ? And (Value = ? Or Name = ?) AND GroupType = ? And AD_Client_ID = ? And IsActive = 'Y'", get_TrxName())
					.setParameters(getC_BPartner_ID(), getValue(), getName(), getGroupType(), getAD_Client_ID())
					.setApplyAccessFilter(true)
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", "Value Or Name BPartner exists");
				return false;
			}

		}
		return true;
	}	//	beforeSave
	
	@Override
	protected boolean beforeDelete() {
		if (isRecordUsed()) {
			log.saveError("Error", Msg.getMsg(getCtx(), "RecordUsed"));
			return false;
		}
		return super.beforeDelete();
	}


	/**************************************************************************
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		
		return success;
	}	//	afterSave

	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (isAutoCreate()) {
			log.saveError("Error", "Can't delete this record!");
			return false;
		}
		return success;
	}	//	afterDelete

	@Override
	protected boolean postDelete() {
		if (getLogo_ID() > 0) {
			MImage img = new MImage(getCtx(), getLogo_ID(), get_TrxName());
			if (!img.delete(true)) {
				log.warning("Associated image could not be deleted for bpartner - AD_Image_ID=" + getLogo_ID());
				return false;
			}
		}
		return true;
	}
	
	//Hàm tạo BPartner tự động từ 3 đối tượng: 1.ORG - 2.DEPARTMENT - 3.EMPLOYEE
	/*
	 * groupType = 'ORG'
	 * groupType = 'EMP'
	 */
	public static boolean createBPartner(int AD_Org_ID, int AD_Client_ID, String Code, String Name, 
			int souce_id, String source_code, String groupType, boolean isInsert, String trxName) {
		Object [] params = {Code, Name, souce_id, source_code};
		if (isInsert)
		{
			String sqlUpdate = "UPDATE C_BPartner SET Value = ?, Name = ? WHERE Key_Original = ? And Code_Original = ?";
			int no = DB.executeUpdate(sqlUpdate, params, true, trxName);
			if (no <= 0)
			{
				MBPartner bp = new MBPartner(Env.getCtx(), 0, trxName);
				bp.setAD_Org_ID(AD_Org_ID);
				bp.setAD_Client_ID(AD_Client_ID);
				bp.setValue(Code);
				bp.setName(Name);
				bp.setName2(Name);
				bp.setIsAutoCreate(true);
				if ("EMP".equals(source_code))
					bp.setIsEmployee(true);
				else
					bp.setIsEmployee(false);
				bp.setGroupType(groupType);
				bp.setKey_Original(souce_id);
				bp.setCode_Original(source_code);
				bp.setProcessed(true);
				return bp.save();
			} else 
				return true;
		} else {
			
			String sql = "DELETE FROM C_BPartner WHERE Key_Original = ? And Code_Original = ?";
			params = new Object [] {souce_id, source_code};
			int no = DB.executeUpdate(sql, params, true, trxName);
			return no >= 0 ? true : false;
		}
	}
	
	public static MOrg getOrg_FromBPartner(Properties ctx, int C_BPartner_ID, String trxName) {
		String whereClause = " AD_Org_ID = (SELECT NVL(Key_Original,0) FROM C_BPartner WHERE C_BPartner_ID = ?)";
		MOrg value = new Query(ctx, X_AD_Org.Table_Name, whereClause, trxName)
				.setParameters(C_BPartner_ID)
				.first();
		return value;
	}
	
	public static MDepartment getDept_FromBPartner(Properties ctx, int C_BPartner_ID, String trxName) {
		String whereClause = " AD_Department_ID = (SELECT NVL(Key_Original,0) FROM C_BPartner WHERE C_BPartner_ID = ?)";
		MDepartment value = new Query(ctx, X_AD_Department.Table_Name, whereClause, trxName)
				.setParameters(C_BPartner_ID)
				.first();
		return value;
	}
	
	public static MBPartner getBPartner_FromOrgDept(Properties ctx, int source_id, String source_code, String trxName) {
		String whereClause = " Key_Original = ? AND Code_Original = ?";
		MBPartner value = new Query(ctx, X_C_BPartner.Table_Name, whereClause, trxName)
				.setParameters(source_id, source_code)
				.first();
		return value;
	}

}	//	MBPartner
