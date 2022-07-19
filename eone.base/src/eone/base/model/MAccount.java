
package eone.base.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.DB;
import eone.util.Env;


public class MAccount extends X_C_Account
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


	public static MAccount get (Properties ctx)
	{
		final String whereClause = "1=1";
		MAccount retValue =  new Query(ctx,I_C_Account.Table_Name,whereClause,null)
		.first();
		return retValue;
	}	//	get
	
	//private static CLogger		s_log = CLogger.getCLogger (MAccount.class);


	public MAccount (Properties ctx, int C_Account_ID, String trxName)
	{
		super (ctx, C_Account_ID, trxName);
		
	}   //  MAccount


	public MAccount (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}   //  MAccount


	public MAccount ()
	{
		this (Env.getCtx(), 0, null);
	}	//	Account


	public String toString()
	{
		return "";
	}	//	toString

	
	
	protected boolean beforeSave (boolean newRecord)
	{

		MElementValue value = MElementValue.get(getCtx(), getAccount_ID());
		setC_Element_ID(value.getC_Element_ID());
		
		String sql = "";
		List<Object>  params = new ArrayList<Object>();
		if (isDefault()) {
			if (getC_DocType_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_DocType_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And TypeAccount = ? And C_Element_ID = ?";
				params.add(getC_DocType_ID());
				params.add(getC_Account_ID());
				params.add(getTypeAccount());
				params.add(getC_Element_ID());
			}
			
			if (getC_DocTypeSub_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_DocTypeSub_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And TypeAccount = ? And C_Element_ID = ?";
				params.add(getC_DocTypeSub_ID());
				params.add(getC_Account_ID());
				params.add(getTypeAccount());
				params.add(getC_Element_ID());
			}
			//Asset
			
			if (getA_Asset_ID() > 0) {
				sql = "Select count(1) From C_Account Where A_Asset_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getA_Asset_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			//BPartner
			
			if (getC_BPartner_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_BPartner_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getC_BPartner_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			
			//Product
			
			if (getM_Product_ID() > 0) {
				sql = "Select count(1) From C_Account Where M_Product_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getM_Product_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			if (getM_Warehouse_ID() > 0) {
				sql = "Select count(1) From C_Account Where M_Warehouse_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getM_Warehouse_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			
			//Tax
			if (getC_Tax_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_Tax_ID = ? And C_Account_ID != ? And IsDefault = 'Y'  And TypeAccount = ? And C_Element_ID = ?";
				params.add(getC_Tax_ID());
				params.add(getC_Account_ID());
				params.add(getTypeAccount());
				params.add(getC_Element_ID());
			}
			
			//Contract
			if (getC_Contract_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_Contract_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getC_Contract_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			
			//Project
			if (getC_Project_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_Project_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getC_Project_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			
			//Construction
			if (getC_Construction_ID() > 0) {
				sql = "Select count(1) From C_Account Where C_Construction_ID = ? And C_Account_ID != ? And IsDefault = 'Y' And C_Element_ID = ?";
				params.add(getC_Construction_ID());
				params.add(getC_Account_ID());
				params.add(getC_Element_ID());
			}
			
			int no = DB.getSQLValue(get_TrxName(), sql, params);
			if (no >= 1) {
				log.saveError("Error", "Exists set default");
				return false;
			}
		}
		
		return true;
	}	//	beforeSave
	
	//private static CCache<Integer,MAccount> s_cache	= new CCache<Integer,MAccount>(Table_Name, 40, CCache.DEFAULT_TIMEOUT);

	/*
	 * Lay danh sach tai khoan cau hinh theo doctype
	 */
	public static List<MAccount> getAccountDocType(int C_DocType_ID) {
		String sqlWhere = "C_DocType_ID = ? And IsDefault = 'Y'";
		
		List<MAccount> value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(C_DocType_ID)
				.setApplyAccessFilter(true)
				.list();
		return value;
	}
	
	
	/*
	 * Lay danh sach tai khoan cau hinh theo doctypeSub
	 */
	public static List<MAccount> getAccountDocTypeSub(int C_DocTypeSub_ID) {
		String sqlWhere = "C_DocTypeSub_ID = ? And IsDefault = 'Y'";
		List<MAccount> value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(C_DocTypeSub_ID)
				.setApplyAccessFilter(true)
				.list();
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh cua BPartner (neu co)
	 */
	public static MAccount getAccountBPartner(int C_BPartner_ID) {
		String sqlWhere = "C_BPartner_ID = ? And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(C_BPartner_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		
		return value;
	}
	
	public static List<MAccount> getListAcctAssetGroup(int A_Asset_Group_ID) {
		String sqlWhere = "A_Asset_Group_ID = ?";
		List<MAccount> value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(A_Asset_Group_ID)
				.list();
		
		return value;
	}
	
	public static List<MAccount> getListAcctProductGroup(int M_ProductGroup_ID) {
		String sqlWhere = "M_ProductGroup_ID = ?";
		List<MAccount> value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(M_ProductGroup_ID)
				.list();
		
		return value;
	}
	
	public static List<MAccount> getListAcctAsset(int A_Asset_ID) {
		String sqlWhere = "A_Asset_ID = ? AND IsDefault = 'Y'";
		List<MAccount> value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(A_Asset_ID)
				.list();
		
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh cua Asset (neu co)
	 */
	public static MAccount getAccountAsset(int A_Asset_ID) {
		String sqlWhere = "A_Asset_ID = ?  And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(A_Asset_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh Product (neu co)
	 */
	public static MAccount getAccountProduct(int M_Product_ID) {
		boolean check = Env.getContext(Env.getCtx(), "#IsProduct").equals(Env.YES) ? true : false;
		if (!check) {
			return null;
		}
		String sqlWhere = "M_Product_ID = ? And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(M_Product_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh Thue (neu co)
	 */
	public static List<MAccount> getAccountTax(int C_Tax_ID) {
		String sqlWhere = "C_Tax_ID = ? And IsDefault = 'Y'";
		List<MAccount> value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(C_Tax_ID)
				.setApplyAccessFilter(true)
				.list();
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh cua Hop dong (neu co)
	 */
	public static MAccount getAccountContract(int C_Contract_ID) {
		boolean check = Env.getContext(Env.getCtx(), "#IsContract").equals(Env.YES) ? true : false;
		if (!check) {
			return null;
		}
		String sqlWhere = "C_Contract_ID = ? And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(C_Contract_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh cua Du An (neu co)
	 */
	public static MAccount getAccountProject(int C_Project_ID) {
		boolean check = Env.getContext(Env.getCtx(), "#IsProject").equals(Env.YES) ? true : false;
		if (!check) {
			return null;
		}
		String sqlWhere = "C_Project_ID = ? And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null)
				.setParameters(C_Project_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh cua Cong trinh (neu co)
	 */
	public static MAccount getAccountConstruction(int C_Construction_ID) {
		boolean check = Env.getContext(Env.getCtx(), "#IsConstruction").equals(Env.YES) ? true : false;
		if (!check) {
			return null;
		}
		String sqlWhere = "C_Construction_ID = ? And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(C_Construction_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		return value;
	}
	
	/*
	 * Lay tai khoan mac dinh cua Kho (neu co)
	 */
	public static MAccount getAccountWarehouse(int M_Warehouse_ID) {
		boolean check = Env.getContext(Env.getCtx(), "#IsProduct").equals(Env.YES) ? true : false;
		if (!check) {
			return null;
		}
		String sqlWhere = "M_Warehouse_ID = ? And IsDefault = 'Y'";
		MAccount value = new Query(Env.getCtx(),  X_C_Account.Table_Name, sqlWhere, null, true)
				.setParameters(M_Warehouse_ID)
				.setApplyAccessFilter(true)
				.firstOnly();
		return value;
	}
}	//	Account

