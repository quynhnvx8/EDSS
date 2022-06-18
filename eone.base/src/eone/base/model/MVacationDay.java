package eone.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import eone.util.CCache;
import eone.util.TimeUtil;


public class MVacationDay extends X_HR_VacationDay
{
	/**
	 * Khi ngay nghi duoc Approved thi day cac ngay nghi vao bang cham cong.
	 * Tinh toan xac dinh cau hinh ngay nghi le va ngay lam viec trong nam de tinh cho dung
	 * thong qua MWorkDay va MHoliday
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static MVacationDay get (Properties ctx, int HR_VacationDay_ID, String trxName)
	{
		return (MVacationDay)MTable.get(ctx, Table_Name).getPO(HR_VacationDay_ID, trxName);
	}	//	get
	
	
	
	/** Create constructor */
	public MVacationDay (Properties ctx, int HR_VacationDay_ID, String trxName)
	{
		super (ctx, HR_VacationDay_ID,trxName);
		
	}	
	
	static private CCache<Integer,MVacationDay> s_cache = new CCache<Integer,MVacationDay>(Table_Name, 30, 60);
	
	
	//Date: Ngày xin nghỉ
	
	public static MVacationDay getVacantEmployee (Properties ctx, int HR_Employee_ID, Timestamp Date, String trxName)
	{
		String strYear = "" + TimeUtil.getYearSel(Date);
		if (s_cache.containsKey(HR_Employee_ID))
			return s_cache.get(HR_Employee_ID);
		String whereClause = " HR_Employee_ID= ? AND C_Year_ID IN (SELECT C_Year_ID FROM C_Year WHERE FiscalYear = ?";
		MVacationDay retValue = new Query(ctx, Table_Name, whereClause, trxName, true)
				.setParameters(HR_Employee_ID, strYear)
				.first();
		s_cache.put(HR_Employee_ID, retValue);
		
		return s_cache.get(HR_Employee_ID);
	}

	
	public MVacationDay (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	
	
	
	
	
	protected boolean beforeSave (boolean newRecord)
	{
		
		return true;
	}	//	beforeSave
	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if(!success)
		{
			return success;
		}
		
		
		
		return true;
	}	//	afterSave
	
	
	protected boolean beforeDelete()
	{
		
		
		return true;
	}       //      beforeDelete
	
	
	
}
