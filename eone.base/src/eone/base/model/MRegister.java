package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import eone.util.CCache;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;

public class MRegister extends X_AD_Register
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MRegister (Properties ctx, int AD_Register_ID, String trxName)
	{
		super (ctx, AD_Register_ID, trxName);
		
	}	//	MAssetUse

	
	public static MRegister get (Properties ctx, int AD_Register_ID, String trxName)
	{
		return (MRegister) MTable.get(ctx, Table_ID).getPO(AD_Register_ID, trxName);
	}
	
	private static CCache<String, MRegister> s_cache = new CCache<String, MRegister>(Table_Name, 5);
	
	public static MRegister getAllDomain (Properties ctx, String trxName, String domain)
	{
		if (!s_cache.containsKey(domain))
		{
			final String whereClause = " Domain = ?";
			MRegister retValue = new Query(ctx,I_AD_Register.Table_Name,whereClause,trxName)
					.setParameters(domain)
					.setOrderBy(COLUMNNAME_Domain)
					.first();
			
			s_cache.put(domain, retValue);
		}
		return s_cache.get(domain);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		
		Map<String, Object> dataColumn = new HashMap<String, Object>();
		dataColumn.put(COLUMNNAME_AD_Register_ID, getAD_Register_ID());
		dataColumn.put(COLUMNNAME_EMail, getEMail());
		boolean check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_Register_ID, getAD_Register_ID(), get_TrxName());
		
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_Domain);
			return false;
		}
		
		dataColumn.clear();
		dataColumn.put(COLUMNNAME_AD_Register_ID, getAD_Register_ID());
		dataColumn.put(COLUMNNAME_TaxID, getTaxID());
		check = isCheckDoubleValue(Table_Name, dataColumn, COLUMNNAME_AD_Register_ID, getAD_Register_ID(), get_TrxName());
		
		if (!check) {
			log.saveError("Error", Msg.getMsg(Env.getLanguage(getCtx()), "ValueExists") + ": " + COLUMNNAME_TaxID);
			return false;
		}
		
		String error = createData(getCtx(), get_TrxName());
		
		if (!"".equals(error)) {
			log.saveError("Error", error);
			return false;
		}
		
		return true;
	}


	public MRegister (Properties ctx, ResultSet rs, String trxName)
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

	
	public String createData(Properties ctx, String trxName) {
		
		//Insert Client
		
		MClient  client = isExistsClient(ctx, getTaxID(), trxName); 
		
		if (client == null)
		{
			client = new MClient(ctx, -1, trxName);
			client.setValue(getDomain());
			client.setName(getName());
			client.setAddress(getAddress());
			client.setAD_Client_ID(DB.getNextID(ctx, X_AD_Client.Table_Name, trxName));
			client.set_ValueNoCheck("Created", new Timestamp(new Date().getTime()));
			client.set_ValueNoCheck("Updated", new Timestamp(new Date().getTime()));
			client.set_ValueNoCheck("CreatedBy", 100);
			client.set_ValueNoCheck("UpdatedBy", 100);
			client.set_ValueNoCheck("IsActive", true);
			client.setC_Element_ID(getC_Element_ID());
			client.setAD_Language("vi_VN");
			client.setRoundUnitPrice(4);
			client.setRoundCalculate(2);
			client.setRoundFinal(0);
			client.setIsCheckMaterial(true);
			client.setIsGroup(false);
			client.setIsMultiCurrency(false);
			client.setC_Currency_ID(234);
			client.setMMPolicy(getMMPolicy());
			client.setAutoArchive("N");
			client.setAD_Org_ID(0);
			if (!client.save()) {
				return "Tạo công ty mới bị lỗi !";
			}
		}
		
		//Insert Org
		
		MOrg org = isExistsOrg(ctx, getTaxID(), trxName); 
		if (org == null) {
			org = new MOrg(ctx, 0, trxName);
			org.setAD_Client_ID(client.getAD_Client_ID());
			org.setValue(client.getValue());
			org.setName(client.getName());
			org.setAddress(client.getAddress());
			org.setOrgType(X_AD_Org.ORGTYPE_Company);
			org.setTaxID(getTaxID());
			org.setPhone(getPhone());
			
			org.setIsCreateWarehouse(true);
			org.setAD_Org_ID(DB.getNextID(ctx, X_AD_Org.Table_Name, trxName));
			if (!org.save()) {
				return "Tạo đơn vị mới bị lỗi !";
			}			
		}
		
		//Insert Department
		MDepartment dept = isExistsDept(ctx, getDomain(), trxName); 
		if (dept == null) {
			dept = new MDepartment(ctx, 0, trxName);
			dept.setAD_Client_ID(org.getAD_Client_ID());
			dept.setAD_Org_ID(org.getAD_Org_ID());
			dept.setValue(org.getValue());
			dept.setName(org.getName());
			dept.setIsCreateBPartner(false);
			dept.setIsAutoCreate(true);
			dept.setAD_Department_ID(DB.getNextID(ctx, X_AD_Department.Table_Name, trxName));
			if (!dept.save()) {
				return "Tạo phòng ban mới bị lỗi !";
			}
		}
		//Insert Emplooyee
		MEmployee emp = isExistsEmp(ctx, getEMail(), trxName); 
		if (emp == null) {
			emp = new MEmployee(ctx, 0, trxName);
			emp.setCode("");
			emp.setName(getEMail());
			emp.setGender(X_HR_Employee.GENDER_None);
			emp.setBirthday(new Timestamp(new Date().getTime()));
			emp.setCardID("N/A");
			emp.setDateIssue(emp.getBirthday());
			emp.setPlaceIssue("N/A");
			emp.setAddress("N/A");
			emp.setPhone("N/A");
			emp.setAD_Client_ID(org.getAD_Client_ID());
			emp.setAD_Org_ID(org.getAD_Org_ID());
			emp.setAD_Department_ID(dept.getAD_Department_ID());
			emp.setStartDate(new Timestamp(new Date().getTime()));
			emp.setHR_Employee_ID(DB.getNextID(ctx, X_HR_Employee.Table_Name, trxName));
			if (!emp.save()) {
				return "Tạo nhân sự bị lỗi !";
			}
		} else if (emp.getAD_Department_ID() != dept.getAD_Department_ID()) {
			return "Nhân viên không cùng phòng ban vừa được tạo";
		}
		
		//Insert User
		MUser u = isExistsUser(ctx, getEMail(), trxName); 
		if (u == null) {
			u = new MUser(ctx, 0, trxName);
			u.setValue(getEMail().substring(0, getEMail().indexOf("@")));
			u.setEMail(getEMail());
			u.setName(u.getValue());
			u.setHR_Employee_ID(emp.getHR_Employee_ID());
			u.setNotificationType(X_AD_User.NOTIFICATIONTYPE_None);
			u.setPassword("1");
			u.setIsUserAdmin(true);
			u.setIsUserSystem(false);
			u.setAD_Client_ID(org.getAD_Client_ID());
			u.setAD_Org_ID(org.getAD_Org_ID());
			u.setAD_User_ID(DB.getNextID(ctx, X_AD_User.Table_Name, trxName));
			if (!u.save()) {
				return "Tạo account bị lỗi !";
			}
		}
		//AD_Role_ID = 1050008: Quản trị hệ thống. Role này là không được xóa, và được validate trong MRole
		MUserRoles ur = isExistsUserRole(ctx, u.getAD_User_ID(), 1050008, trxName);
		if (ur == null) {
			ur = new MUserRoles(ctx, u.getAD_User_ID(), 1050008, trxName);
			ur.setAD_Client_ID(client.getAD_Client_ID());
			ur.setAD_Org_ID(org.getAD_Org_ID());
			if (!ur.save()) {
				return "Tạo account bị lỗi !";
			}
		}
		
		return "";
	}
	
	private MClient isExistsClient(Properties ctx, String domain, String trxName) {
		MClient item = new Query(ctx, X_AD_Client.Table_Name, "Value = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	
	private MOrg isExistsOrg(Properties ctx, String domain, String trxName) {
		MOrg item = new Query(ctx, X_AD_Org.Table_Name, "Value = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MDepartment isExistsDept(Properties ctx, String domain, String trxName) {
		MDepartment item = new Query(ctx, X_AD_Department.Table_Name, "Value = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MEmployee isExistsEmp(Properties ctx, String domain, String trxName) {
		MEmployee item = new Query(ctx, X_HR_Employee.Table_Name, "Name = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MUser isExistsUser(Properties ctx, String domain, String trxName) {
		MUser item = new Query(ctx, X_AD_User.Table_Name, "Email = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MUserRoles isExistsUserRole(Properties ctx, int AD_User_ID, int AD_Role_ID, String trxName) {
		MUserRoles item = new Query(ctx, X_AD_User_Roles.Table_Name, "AD_User_ID = ? AND AD_Role_ID = ?", trxName)
				.setParameters(AD_User_ID, AD_Role_ID)
				.first();
		return item;
	}
	
	
}
