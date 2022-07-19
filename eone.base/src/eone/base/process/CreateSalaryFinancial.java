
package eone.base.process;

import java.math.BigDecimal;
import java.util.HashMap;

import eone.base.model.MAccountDefault;
import eone.base.model.MGeneral;
import eone.base.model.MGeneralLine;
import eone.util.DB;

public class CreateSalaryFinancial extends SvrProcess {

	/**
	 * Quynhnv.x8: Tong hop cong nhan vien
	 */

	
	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		MGeneral general = MGeneral.get(getCtx(), getRecord_ID(), get_TrxName());
		HashMap<Integer, MAccountDefault> retValue = MAccountDefault.getAccountDefault(general.getC_DocType_ID());
		String sqlDelete = "DELETE FROM C_GeneralLine WHERE C_General_ID = ?";
		DB.executeUpdate(sqlDelete, getRecord_ID(), get_TrxName());
		
		String sqlSelect =
				" SELECT "+
				" 	SUM(l.SalaryNet) AS TongLuong, "+
				" 	SUM(l.Org_Insua_Unemployee) AS BHTN_CT, "+
				" 	SUM(l.Insua_Unemployee) AS BHTN_CN, "+
				" 	SUM(l.Org_Insua_Social) AS BHXH_CT, "+
				" 	SUM(l.Insua_Social) AS BHXH_CN, "+
				" 	SUM(l.Org_Insua_Health) AS BHYT_CT, "+
				" 	SUM(l.Insua_Health) AS BHYT_CN, "+
				" 	SUM(l.OnionFeeAmt) AS PCD_CT, "+
				" 	SUM(l.TaxAmt) AS TTNCN	 "+
				" FROM HR_SalaryLine l "+
				" 	INNER JOIN HR_Salary r ON l.HR_Salary_ID = r.HR_Salary_ID "+
				" WHERE r.C_Period_ID = ? AND r.AD_Client_ID = ? AND r.Processed = 'Y'";
		Object [] params = {general.getC_Period_ID(), general.getAD_Client_ID()};
		Object [] data = DB.getObjectValuesEx(get_TrxName(), sqlSelect, 9, params);
		
		MGeneralLine line = null;
		for(int i = 0; i < retValue.size(); i++) {
			line = new MGeneralLine(getCtx(), 0, get_TrxName());
			line.setAccount_Dr_ID(retValue.get(i).getAccount_Dr_ID());
			line.setAccount_Cr_ID(retValue.get(i).getAccount_Cr_ID());
			line.setDescription(retValue.get(i).getDescription());
			line.setC_General_ID(general.getC_General_ID());
			
			int orderNo = retValue.get(i).getOrderNo();
			if (orderNo == 0 && data[0] != null) {
				line.setAmount(new BigDecimal(data[0].toString()));
			}
			if (orderNo == 1 && data[1] != null) {
				line.setAmount(new BigDecimal(data[1].toString()));
			}
			if (orderNo == 2 && data[2] != null) {
				line.setAmount(new BigDecimal(data[2].toString()));
			}
			if (orderNo == 3 && data[3] != null) {
				line.setAmount(new BigDecimal(data[3].toString()));
			}
			if (orderNo == 4 && data[4] != null) {
				line.setAmount(new BigDecimal(data[4].toString()));
			}
			if (orderNo == 5 && data[5] != null) {
				line.setAmount(new BigDecimal(data[5].toString()));
			}
			if (orderNo == 6 && data[6] != null) {
				line.setAmount(new BigDecimal(data[6].toString()));
			}
			if (orderNo == 7 && data[7] != null) {
				line.setAmount(new BigDecimal(data[7].toString()));
			}
			if (orderNo == 8 && data[8] != null) {
				line.setAmount(new BigDecimal(data[8].toString()));
			}
			line.save();
		}
		return "Success";
	}
}
