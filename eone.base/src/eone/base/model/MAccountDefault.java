
package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eone.util.CCache;
import eone.util.Env;
import eone.util.Msg;


public class MAccountDefault extends X_C_AccountDefault
{
	/**
	 * Muc dich bang Account nay cau hinh theo thu tu uu tien nhu sau de fill tai khoan mac dinh khi hach toan
	 * Moi thuc the luon cau hinh 1 tai khoan mac dinh co C_Account.IsDefault = Y => Khong ton tai dong thoi 2 Default cho moi thuc the.
	 * 1. Uu tien DocType 
	 * 2. Neu co SubDocType thi theo SubDocType
	 * 3. Khi chon doi tuong, san pham, tai san, ... neu co cau hinh tai khoan rieng thi sẽ lay theo tai khoan rieng.
	 * 4. Uu tien tu "Con => cha": VD doi tuong ko co tai khoan thi quay ra nhom doi tuong tim tai khoan, neu ko co moi bo qua.
	 */
	private static final long serialVersionUID = 7980515458720808532L;


	static private CCache<Integer,HashMap<Integer, MAccountDefault>> s_cache = new CCache<Integer,HashMap<Integer, MAccountDefault>>(Table_Name, 30, 60);
	
	static private CCache<Integer,HashMap<Integer, MAccountDefault>> s_trasnfer = new CCache<Integer,HashMap<Integer, MAccountDefault>>(Table_Name, 30, 60);
	
	public static MAccountDefault get (Properties ctx)
	{
		final String whereClause = "1=1";
		MAccountDefault retValue =  new Query(ctx,Table_Name,whereClause,null)
		.first();
		return retValue;
	}	//	get
	
	//private static CLogger		s_log = CLogger.getCLogger (MAccount.class);


	public MAccountDefault (Properties ctx, int C_AccountDefault_ID, String trxName)
	{
		super (ctx, C_AccountDefault_ID, trxName);
		
	}   //  MAccount


	public MAccountDefault (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}   //  MAccount


	public MAccountDefault ()
	{
		this (Env.getCtx(), 0, null);
	}	//	Account


	public String toString()
	{
		return "";
	}	//	toString

	
	
	protected boolean beforeSave (boolean newRecord)
	{

		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_OrderNo, getOrderNo());
		dataColumn.put(COLUMNNAME_C_DocType_ID, getC_DocType_ID());
		dataColumn.put(COLUMNNAME_C_Element_ID, getC_Element_ID());
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_C_AccountDefault_ID, getC_AccountDefault_ID(), get_TrxName());
		dataColumn = null;
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_OrderNo);
			return false;
		}
		
		
		return true;
	}	//	beforeSave
	
	
	public static HashMap<Integer, MAccountDefault> getAccountDefault(int C_DocType_ID) {
		if(s_cache.containsKey(C_DocType_ID))
			return s_cache.get(C_DocType_ID);
		String sqlWhere = "C_DocType_ID = ? AND C_Element_ID = ?";
		int C_Element_ID = Env.getContextAsInt(Env.getCtx(), "#C_Element_ID");
		HashMap<Integer, MAccountDefault> data = new HashMap<Integer, MAccountDefault>();
		List<MAccountDefault> value = new Query(Env.getCtx(),  Table_Name, sqlWhere, null)
				.setParameters(C_DocType_ID, C_Element_ID)
				.setApplyAccessFilter(true)
				.setOrderBy("OrderNo ASC")
				.list();
		for(int i = 0; i < value.size(); i ++) {
			data.put(value.get(i).getOrderNo(), value.get(i));
		}
		s_cache.put(C_DocType_ID, data);
		data.clear();
		data = null;
		return s_cache.get(C_DocType_ID);
	}
	
	public static HashMap<Integer, MAccountDefault> getAccountTransfer(int C_DocType_ID) {
		if(s_trasnfer.containsKey(C_DocType_ID))
			return s_trasnfer.get(C_DocType_ID);
		String sqlWhere = "C_DocType_ID = ? AND C_Element_ID = ?";
		int C_Element_ID = Env.getContextAsInt(Env.getCtx(), "#C_Element_ID");
		HashMap<Integer, MAccountDefault> data = new HashMap<Integer, MAccountDefault>();
		List<MAccountDefault> value = new Query(Env.getCtx(),  Table_Name, sqlWhere, null)
				.setParameters(C_DocType_ID, C_Element_ID)
				.setApplyAccessFilter(true)
				.setOrderBy("OrderNo ASC")
				.list();
		for(int i = 0; i < value.size(); i ++) {
			data.put(value.get(i).getAccount_Cr_ID(), value.get(i));
		}
		s_trasnfer.put(C_DocType_ID, data);
		data.clear();
		data = null;
		return s_trasnfer.get(C_DocType_ID);
	}
}	//	Account

