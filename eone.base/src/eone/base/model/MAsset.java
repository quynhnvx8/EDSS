package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.DB;
import eone.util.Msg;
import eone.util.TimeUtil;


public class MAsset extends X_A_Asset implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int CHANGETYPE_setAssetGroup = Table_ID * 100 + 1;
	
	
	public static MAsset get (Properties ctx, int A_Asset_ID, String trxName)
	{
		return (MAsset)MTable.get(ctx, MAsset.Table_Name).getPO(A_Asset_ID, trxName);
	}	//	get
	
	
	
	/** Create constructor */
	public MAsset (Properties ctx, int A_Asset_ID, String trxName)
	{
		super (ctx, A_Asset_ID,trxName);
		
	}	//	MAsset

	
	public MAsset (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAsset
	
	
	protected MAsset (MProject project)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setCreateDate(new Timestamp(System.currentTimeMillis()));
		setDescription(project.getDescription());
	}
	
	public MAsset(MInOut mInOut, MInOutLine sLine, int deliveryCount) {
		this(mInOut.getCtx(), 0, mInOut.get_TrxName());
		setCreateDate(new Timestamp(System.currentTimeMillis()));
		setDescription(sLine.getDescription());
		
	}

	
	protected boolean beforeSave (boolean newRecord)
	{
		
		if (newRecord || is_ValueChanged("Value") || is_ValueChanged("IsActive") || is_ValueChanged("Name")) {
			List<MAsset> relValue = new Query(getCtx(), Table_Name, "A_Asset_ID != ? And (Value = ? Or Name = ?) And IsActive = 'Y'", get_TrxName())
					.setParameters(getA_Asset_ID(), getValue(), getName())
					.setApplyAccessFilter(true)
					.list();
			if (relValue.size() >= 1) {
				log.saveError("Error", Msg.getMsg(getCtx(), "ValueOrNameExists"));//ValueExists, NameExists
				return false;
			}

		}
		
		if (is_ValueChanged(X_A_Asset.COLUMNNAME_StatusUse)) {
			if (X_A_Asset.STATUSUSE_Using.equals(getStatusUse()))
					setIsDepreciated(true);
			if (X_A_Asset.STATUSUSE_None.equals(getStatusUse())) {
				setIsDepreciated(false);
				setUseDate(null);
				setPendingDate(null);
			}
				
			if (X_A_Asset.STATUSUSE_UnUse.equals(getStatusUse()) && getUseDate() != null) {
				setPendingDate(TimeUtil.getDayLastMonth(new Timestamp(new Date().getTime())));
			}
			
			if (X_A_Asset.STATUSUSE_UnUse.equals(getStatusUse()) && getUseDate() == null) {
				setPendingDate(null);
				setIsDepreciated(false);
			}
		}
		
		
		
		
		
		return true;
	}	//	beforeSave
	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if(!success)
		{
			return success;
		}
		
		//Xóa cấu hình cũ
		if (is_ValueChanged(X_A_Asset.COLUMNNAME_A_Asset_Group_ID)) {
			String sql = "DELETE C_Account WHERE A_Asset_ID = ?";
			DB.executeUpdate(sql, getA_Asset_ID(), get_TrxName());
		}
		//Insert cấu hình mới
		if (newRecord || is_ValueChanged(X_A_Asset.COLUMNNAME_A_Asset_Group_ID)) {
			List<MAccount> lists = MAccount.getListAcctAssetGroup(getA_Asset_Group_ID());
			for (int i = 0; i < lists.size(); i++) {
				MAccount acc = new MAccount(getCtx(), 0, get_TrxName());
				acc.setA_Asset_ID(getA_Asset_ID());
				acc.setAccount_ID(lists.get(i).getAccount_ID());
				acc.setIsDefault(lists.get(i).isDefault());
				acc.setTypeAccount(lists.get(i).getTypeAccount());
				acc.save();
			}
		}
		
		return true;
	}	//	afterSave
	
	
	protected boolean beforeDelete()
	{
		if (isRecordUsed()) {
			log.saveError("Error", Msg.getMsg(getCtx(), "RecordUsed"));
			return false;
		}
		
		return true;
	}       //      beforeDelete
	
	
	
	protected String		m_processMsg = null;

	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		return engine.processIt (action, getDocStatus());
	}



	@Override
	public String completeIt() {
		m_processMsg = null;
		if (m_processMsg != null)
			return DocAction.STATUS_Drafted;

		
		setProcessed(true);
		return DocAction.STATUS_Completed;
	}



	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = null;
		if (m_processMsg != null)
			return false;	
				
		
		if(!super.reActivate())
			return false;
		
		setProcessed(false);
		
		return true;
	}


	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	public void setProcessMsg(String text) {
		m_processMsg = text;
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName());
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}



	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
