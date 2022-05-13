package eone.base.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import eone.base.model.MAccount;
import eone.base.model.MAsset;
import eone.base.model.MDepreciation;
import eone.base.model.MDepreciationExp;
import eone.base.model.MDepreciationSplit;
import eone.base.model.Query;
import eone.base.model.X_A_Asset;
import eone.base.model.X_A_Depreciation_Split;
import eone.base.model.X_C_Account;
import eone.util.DB;
import eone.util.Env;
import eone.util.TimeUtil;


public class BuildDepreciation extends SvrProcess
{
	private int p_Record_ID = 0;
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null && para[i].getParameter_To() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	}	//	prepare


	
	protected String doIt () throws Exception
	{
		MDepreciation de = MDepreciation.get(getCtx(), p_Record_ID);
		//Lay danh sach tai san tinh khau hao
		String sqlWhereAsset = "IsActive = 'Y' And IsDepreciated = 'Y' And COALESCE(IsDisposed,'N') != 'Y'"+
				" And COALESCE(IsTransferred,'N') != 'Y' "+
				" And RemainAmt > 0 "+
				" AND (PendingDate IS NULL OR PendingDate >= ?)"+
				" And A_Asset_ID not in (Select A_Asset_ID From A_Depreciation_Exp e Where A_Depreciation_ID = ?)";
		List<MAsset> assets = new Query(getCtx(), X_A_Asset.Table_Name, sqlWhereAsset, get_TrxName())
				.setParameters(de.getDateAcct(), p_Record_ID)
				.list();
		String listAsset_ID = "0";
		for (int a = 0; a < assets.size(); a++) {
			listAsset_ID = listAsset_ID + "," + assets.get(a).getA_Asset_ID();
		}
		
		Timestamp firstDate = TimeUtil.getDayFirstMonth(de.getDateAcct());
		Timestamp lastDate = TimeUtil.getDayLastMonth(de.getDateAcct());
		
		Map<Integer, Object []> data = ProcessWorkfile(listAsset_ID, firstDate, lastDate);
		
		int A_Asset_ID = 0;
		BigDecimal accumulatedAmt = Env.ZERO;
		
		boolean insert = true;
		
		MDepreciationExp newExp = new MDepreciationExp(getCtx(), 0, get_TrxName());
		MAsset asset = null;
		
		if (data != null) {
			for(int key : data.keySet()) {
				A_Asset_ID = key;
				asset = MAsset.get(getCtx(), A_Asset_ID, get_TrxName());
				List<MAccount> lists = MAccount.getListAcctAsset(A_Asset_ID);
				int Account_Dr_ID = 0;
				int Account_Cr_ID = 0;
				for(int c = 0; c < lists.size(); c++) {
					if (X_C_Account.TYPEACCOUNT_AccumulateAccount.equals(lists.get(c).getTypeAccount()))
						Account_Cr_ID = lists.get(c).getAccount_ID();
					if (X_C_Account.TYPEACCOUNT_ExpenseAccumulateAccount.equals(lists.get(c).getTypeAccount()))
						Account_Dr_ID = lists.get(c).getAccount_ID();
				}
				
				//getData của từng tài sản : 0: Tổng tiền khấu hao; 1: Bit xác định tách khấu hao hay ko
				Object [] obj = data.get(key);
				if (obj != null ) {
					BigDecimal amt = Env.ZERO;
					if (obj[0] != null)
						amt = new BigDecimal(obj[0].toString());
					String split = "N";
					if (obj[1] != null)
						split = obj[1].toString();
					//Có phân tách khấu hao
					if ("Y".equalsIgnoreCase(split)) {
						//Lay danh sach khau hao phan tach
						String sqlWhereDS = "A_Asset_ID = ?";
						List<MDepreciationSplit> ds = new Query(getCtx(), X_A_Depreciation_Split.Table_Name, sqlWhereDS, get_TrxName())
								.setParameters(A_Asset_ID)
								.list();
						int percent = 0;
						BigDecimal amtAccu = Env.ZERO;
						for(int m = 0; m < ds.size(); m ++) {
							if (m == ds.size() - 1) {
								amt = accumulatedAmt.subtract(amtAccu);
							} else {
								percent = percent + ds.get(m).getPercent();
								amt = accumulatedAmt.multiply(new BigDecimal(ds.get(m).getPercent())).divide(new BigDecimal(10), Env.roundAmount, RoundingMode.HALF_UP);
								amtAccu = amtAccu.add(amt);
								
							}
							
							insert = insertDepreciationExp(newExp, amt, 
									ds.get(m).getC_TypeCost_ID() > 0 ? ds.get(m).getC_TypeCost_ID() :  asset.getC_TypeCost_ID(), 
									ds.get(m).getAccount_ID() > 0 ? ds.get(m).getAccount_ID() : Account_Dr_ID, 
											Account_Cr_ID, de.getDateAcct(), asset );
							if (!insert) {
								return "Insert false!";
							}
						}
					}else {
						insert = insertDepreciationExp(newExp, amt, asset.getC_TypeCost_ID(), Account_Dr_ID, 
								Account_Cr_ID, de.getDateAcct(), asset );
						if (!insert) {
							return "Insert false!";
						}
					}
				}
			}
		}
		
		return "Success!";
	}	//	doIt
	/**
	 * Hàm này trả về kết quả như sau cho mỗi tài sản
	 * A_Asset_ID		Amount		OrderNo	StartDate				EndDate
	 * 
	 * 10				333333.33	10		"2022-06-01 00:00:00"	"2022-06-08 00:00:00"
	 * 10				349206.35	20		"2022-06-09 00:00:00"	"2022-06-30 00:00:00"
	 * 
	 * 11				12345666	10		"2022-06-01 00:00:00"	"2022-06-30 00:00:00"
	 * 
	 * Từ đây sẽ tính được khoảng ngày khấu hao của từng tài sản (kể cả trong tháng có thay đổi nguyên giá)
	 * @return
	 */
	private Map<Integer, Object []> ProcessWorkfile(String listAsset_ID, Timestamp startDate, Timestamp endDate) {
		Map<Integer, Object []> data = new HashMap<Integer, Object []>(); 
		String sqlwf = 
				" SELECT w.A_Asset_ID, Amount, OrderNo, a.TypeCalculate, a.DepreciationSplit, "+
				"   AGE("+		
				" 	CASE WHEN EndDate IS NULL THEN ? ELSE EndDate END, "+ //#1
				" 	CASE WHEN StartDate > ? THEN StartDate ELSE ? END "+ //#2,#3 
				"	)+1 AS Days "+
				" FROM A_Depreciation_Workfile w "+
				"		INNER JOIN A_Asset a ON w.A_Asset_ID = a.A_Asset_ID "+
				" WHERE w.A_Asset_ID IN ("+ listAsset_ID +")"+
				" 	AND (EndDate BETWEEN ? AND ? OR EndDate is null)"+ //#4,#5
				" ORDER BY w.A_Asset_ID, OrderNo";
		PreparedStatement ps = DB.prepareCall(sqlwf);
		ResultSet rs = null;
		try {
			ps.setTimestamp(1, endDate);
			ps.setTimestamp(2, startDate);
			ps.setTimestamp(3, startDate);
			ps.setTimestamp(4, startDate);
			ps.setTimestamp(5, endDate);
			rs = ps.executeQuery();
			
			int A_Asset_ID = 0;
			int A_Asset_ID_Old = 0;
			BigDecimal Amt = Env.ZERO;
			BigDecimal totalAmt = Env.ZERO;
			String split = "";
			String typeCal = "";
			int days = 0;
			int totalDay = 0;
			while (rs.next()) {
				A_Asset_ID = rs.getInt("A_Asset_ID");
				split = rs.getString("DepreciationSplit");
				//Khi sang tài sản mới thì khởi tạo lại.
				if (A_Asset_ID_Old != A_Asset_ID && A_Asset_ID_Old > 0) {
					Object [] obj = {totalAmt, split};
					data.put(A_Asset_ID_Old, obj);
					totalDay = 0;
					totalAmt = Env.ZERO;
				}
				
				typeCal = rs.getString("TypeCalculate");
				days = rs.getInt("Days");
				if (X_A_Asset.TYPECALCULATE_ByMonth.equals(typeCal) && days > 30)
					days = 30;
				
				//Cộng tổng các ngày trong 1 thang để xem có vượt 30 hay ko
				totalDay = totalDay + days;
				
				if (X_A_Asset.TYPECALCULATE_ByMonth.equals(typeCal) && totalDay > 30)
					days = days - (totalDay - 30);
				
				Amt = rs.getBigDecimal("Amount").multiply(new BigDecimal(days));
				totalAmt = totalAmt.add(Amt);
				
				A_Asset_ID_Old = A_Asset_ID;
			}
			Object [] obj = {totalAmt, split};
			data.put(A_Asset_ID_Old, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}
		
		return data;
	}
	
	private boolean insertDepreciationExp(MDepreciationExp newExp, BigDecimal accumulate, int C_TypeCost_ID, 
			int Account_Dr_ID, int Account_Cr_ID, Timestamp dateAcct, MAsset asset) {
		newExp = new MDepreciationExp(getCtx(), 0, get_TrxName());
		Timestamp startTime = TimeUtil.getDayFirstMonth(dateAcct);
		Timestamp endTime = TimeUtil.getDayLastMonth(dateAcct);
		int numberDay = TimeUtil.getDaysBetween(startTime, endTime);
		if (X_A_Asset.TYPECALCULATE_ByMonth.equals(asset.getTypeCalculate())) {
			numberDay = 30;
		}
		newExp.setOneDay(accumulate.divide(new BigDecimal(numberDay), Env.rountQty, RoundingMode.HALF_UP));
		
		accumulate = accumulate.setScale(Env.getScaleFinal(), RoundingMode.HALF_UP);
		newExp.setIsSelected(true);
		newExp.setA_Asset_ID(asset.getA_Asset_ID());
		newExp.setA_Depreciation_ID(p_Record_ID);
		newExp.setC_TypeCost_ID(C_TypeCost_ID);
		newExp.setAccount_Dr_ID(Account_Dr_ID);
		newExp.setAccount_Cr_ID(Account_Cr_ID);
		newExp.setAmount(accumulate);
		newExp.setStartDate(startTime);
		newExp.setEndDate(endTime);
		newExp.setDateAcct(dateAcct);
		newExp.setUseLifed(1);
		if (!newExp.save() )
			return false;
		return true;
		
	}
	
}	