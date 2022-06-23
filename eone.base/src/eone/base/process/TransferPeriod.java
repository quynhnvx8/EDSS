package eone.base.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eone.base.model.MTransferConfig;
import eone.base.model.MTransferConfigLine;
import eone.base.model.MTransferPeriod;
import eone.base.model.MTransferPeriodLine;
import eone.util.DB;
import eone.util.Env;

//TODO: Chua xu ly triet de.
public class TransferPeriod extends SvrProcess
{
	private int	p_Report_ID = 0;
	
	protected void prepare ()
	{
		
					
		p_Report_ID = getRecord_ID();
	}	//	prepare

	
	protected String doIt ()
		throws Exception
	{
		MTransferPeriod period = MTransferPeriod.get(getCtx(), p_Report_ID, get_TrxName());
		MTransferConfig config = MTransferConfig.get(getCtx(), period.getC_TransferConfig_ID(), get_TrxName());
		MTransferConfigLine [] lines = config.getLines(true);
		
		String sqlDel = "Delete From C_TransferPeriodLine Where C_TransferPeriod_ID = ?";
		DB.executeUpdate(sqlDel, p_Report_ID, get_TrxName());
		
		LinkedHashMap<String, String> arrSel = new LinkedHashMap<String, String>();
		Map<String, String> arrCal = new HashMap<String, String>();
		String key = "";
		String strData = "";
		
		//Quy chuan lai
		for(int i = 0; i < lines.length; i ++) {
			key = lines[i].getAccount_Dr_ID() +  "_" + 
					lines[i].getOrderCalculate() + "_" + 
					lines[i].getMethodTransfer() + "_" +
					lines[i].getTypeSelect() + "_" +
					lines[i].getAccount_RA_ID()
					;
			if (arrSel.containsKey(key)) {
				strData = arrSel.get(key);
			}
			if (strData != "") {
				strData = strData + "," + lines[i].getAccount_Cr_ID();
			} else {
				strData = lines[i].getAccount_Cr_ID();
			}
			if (lines[i].getOrderCalculate() != 0)
				arrCal.put(key, strData);
			
			arrSel.put(key, strData);
			
			key = "";
			strData = "";
		}
		
		//Xet thu tu tinh toan
		int orderCurr = 0;
		int orderMin = 0;
		String calMethod = "";
		String typeSelect = "";
		String keyAcct_RE = "";
		int account_target_id = 0;
		Map<String, HashMap<Integer, BigDecimal>> data = null;
		
		HashMap<Integer, BigDecimal> valueOrder = new HashMap<Integer, BigDecimal>();
		HashMap<Integer, BigDecimal> valueline = null;
		
		for (String k : arrSel.keySet()) {
			data = new HashMap<String, HashMap<Integer, BigDecimal>>();
			//Xu ly lay thu tu tinh nho nhat de tinh toan truoc.
			key = k;
			String [] keys = key.split("_");
			orderCurr = Integer.parseInt(keys[1].toString());
			calMethod = keys[2].toString();
			typeSelect = keys[3].toString();
			keyAcct_RE = keys[4].toString();
			account_target_id = Integer.parseInt(keys[0].toString());
			
			if (orderCurr < orderMin) {
				orderMin = orderCurr;
				
			}
			
			if (orderCurr == 0) {
				
				valueline = getAmt(arrSel.get(key), calMethod, typeSelect, keyAcct_RE, period);
				BigDecimal amt = Env.ZERO;
				BigDecimal amtOld = Env.ZERO;
				int fix = 1;
				if (calMethod.equalsIgnoreCase(MTransferConfigLine.METHODTRANSFER_BalanceCredit) || 
						calMethod.equalsIgnoreCase(MTransferConfigLine.METHODTRANSFER_IncurredCredit)) {
					fix = -1;					
				}
				if (valueOrder.containsKey(account_target_id))
					amtOld = valueOrder.get(account_target_id);
				for(int acct : valueline.keySet()) {
					amt = amt.add(valueline.get(acct));
				}
				amt = amt.multiply(new BigDecimal(fix));
				amt = amt.add(amtOld);
				valueOrder.put(account_target_id, amt);
			} else {
				valueline = valueOrder;
			}
			data.put(account_target_id + "_" + calMethod, valueline);
			String error = saveData(account_target_id, data);
			if (error != "") {
				
				return error;
			}
		}
		
		return "OK";
	}	//	doIt
	
	protected String saveData(int account_target_id, Map<String, HashMap<Integer, BigDecimal>> data) {
		int dr_id = 0;
		
		int cr_id = 0;
		BigDecimal Amt = Env.ZERO;
		String error = "";
		for(String key : data.keySet()) {
			HashMap<Integer, BigDecimal> value = data.get(key);
			String [] ar = key.split("_");
			String method = ar[1];
			for(Integer acct_id : value.keySet()) {
				Amt = value.get(acct_id);
				if (method.equalsIgnoreCase(MTransferConfigLine.METHODTRANSFER_BalanceDebit) || 
						method.equalsIgnoreCase(MTransferConfigLine.METHODTRANSFER_IncurredDebit)) {
					dr_id = account_target_id;
					cr_id = acct_id;					
				} else if (method.equalsIgnoreCase(MTransferConfigLine.METHODTRANSFER_BalanceCredit) || 
						method.equalsIgnoreCase(MTransferConfigLine.METHODTRANSFER_IncurredCredit)){
					cr_id = account_target_id;
					dr_id = acct_id;
				} else {
					if (Amt.compareTo(Env.ZERO) < 0) {
						cr_id = account_target_id;
						dr_id = acct_id;
						Amt = Amt.multiply(new BigDecimal(-1));
					} else {
						dr_id = account_target_id;
						cr_id = acct_id;
						
					}
				}
				MTransferPeriodLine line = MTransferPeriodLine.get(getCtx(), 0, get_TrxName());
				line.setC_TransferPeriod_ID(p_Report_ID);
				line.setAccount_Dr_ID(dr_id);
				line.setAccount_Cr_ID(cr_id);
				line.setAmount(Amt);
				line.setAmountConvert(Amt);
				if (!line.save()) {
					if (error == "")
						error = "Error: " + Amt;
					else
						error = error + "Error: " + Amt;
				}
			}
		}
		return error;
	}
	
	//Lay du no hoac du co theo tai khoan
	protected HashMap<Integer, BigDecimal> getAmt(String keyAccount, String keyMethod, String typeSelect, String keyAcct_RE, MTransferPeriod period) throws Exception{
		HashMap<Integer, BigDecimal> arr = new HashMap<Integer, BigDecimal>();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		
		//PS BÊN NỢ
		if (keyMethod.equals(MTransferConfigLine.METHODTRANSFER_IncurredDebit)) {
			sql = " SELECT Account_Dr_ID Account_ID, SUM(AmountConvert) Amt FROM Fact_Acct WHERE DateAcct <= ?  AND DateAcct >= ? AND Account_Dr_ID in ("+ keyAccount +") ";
			params.add(period.getEndDate());
			params.add(period.getStartDate());
			
			if (typeSelect.equals(MTransferConfigLine.TYPESELECT_AND))
				sql = sql + " AND Account_CR_ID IN (" + keyAcct_RE + ")";
			else if (typeSelect.equals(MTransferConfigLine.TYPESELECT_NOT))
				sql = sql + " AND Account_CR_ID NOT IN (" + keyAcct_RE + ")";
					
			sql = sql + " AND AD_Client_ID = ? GROUP BY Account_Dr_ID";
			params.add(period.getAD_Client_ID());
		}
		
		//PS BÊN CÓ
		if (keyMethod.equals(MTransferConfigLine.METHODTRANSFER_IncurredCredit)) {
			sql = " SELECT Account_Cr_ID Account_ID, SUM(AmountConvert) Amt FROM Fact_Acct WHERE DateAcct <= ?  AND DateAcct >= ? And Account_Cr_ID in ("+ keyAccount +")";
			params.add(period.getEndDate());
			params.add(period.getStartDate());
			
			if (typeSelect.equals(MTransferConfigLine.TYPESELECT_AND))
				sql = sql + " AND Account_DR_ID IN (" + keyAcct_RE + ")";
			else if (typeSelect.equals(MTransferConfigLine.TYPESELECT_NOT))
				sql = sql + " AND Account_DR_ID NOT IN (" + keyAcct_RE + ")";
					
			sql = sql + " AND AD_Client_ID = ? GROUP BY Account_Cr_ID";
			params.add(period.getAD_Client_ID());
		}
		
		//DỰ NỢ
		if (keyMethod.equals(MTransferConfigLine.METHODTRANSFER_BalanceDebit)) {
			sql = " Select Account_ID, Sum(Amt) Amt "+
				" From "+
				" ( "+
				" 	select Account_Dr_ID Account_ID, Case when '"+ keyMethod +"' = "+ 
				"'"		+MTransferConfigLine.METHODTRANSFER_BalanceDebit + "'"+
				" 		Then Sum(AmountConvert) Else -Sum(AmountConvert) End Amt "+
				" 	From Fact_Acct  "+
				" 	Where DateAcct Between ? And ? And Account_Dr_ID in ("+ keyAccount +")  "+
				"		And AD_Client_ID = ?"+
				" 	Group by Account_Dr_ID "+
				" 	Union All "+
				" 	select Account_Cr_ID Account_ID, Case when '"+ keyMethod +"' = "+ 
				"'"		+MTransferConfigLine.METHODTRANSFER_BalanceDebit + "'"+
				" 		Then -Sum(AmountConvert) Else Sum(AmountConvert) End Amt "+
				" 	From Fact_Acct  "+
				" 	Where DateAcct Between ? And ? And Account_Cr_ID in ("+ keyAccount +") "+
				"		And AD_Client_ID = ?"+
				" 	Group by Account_Cr_ID "+
				" )b Group by Account_ID  HAVING Sum(Amt) > 0";
			params.add(period.getEndDate());
			params.add(period.getStartDate());
			params.add(period.getAD_Client_ID());
			params.add(period.getEndDate());
			params.add(period.getStartDate());
			params.add(period.getAD_Client_ID());
		}
		
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		DB.setParameters(ps, params);
		rs = ps.executeQuery();
		while (rs.next()) {
			arr.put(rs.getInt("Account_ID"), rs.getBigDecimal("Amt"));
		}
		return arr;
	}
	
	
}	