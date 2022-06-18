
package eone.base.callout;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.MSalaryTable;
import eone.base.model.MSalaryTableLine;
import eone.base.model.X_HR_Employee;
import eone.base.model.X_HR_Payroll;
import eone.base.model.X_HR_SalaryExtra;
import eone.base.model.X_HR_SalaryTableLine;
import eone.util.DB;
import eone.util.Env;

//eone.base.callout.CalloutEmployee.fillSalaryExtra 
//eone.base.callout.CalloutEmployee.fillCodeEmployee 
public class CalloutEmployee extends CalloutEngine
{

	

	public String fillSalaryExtra (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";

		Object salaryLine = mTab.getValue("HR_SalaryTableLine_ID");
		int HR_SalaryTableLine_ID = 0;
		if (salaryLine != null) {
			HR_SalaryTableLine_ID = Integer.parseInt(salaryLine.toString());
			MSalaryTableLine line = MSalaryTableLine.get(ctx, HR_SalaryTableLine_ID, null);
			mTab.setValue(X_HR_SalaryExtra.COLUMNNAME_TypeExtra, line.getTypeExtra());
			mTab.setValue(X_HR_SalaryExtra.COLUMNNAME_Percent, line.getPercent());
			mTab.setValue(X_HR_SalaryExtra.COLUMNNAME_FormulaSetup, line.getFormulaSetup());
			
		} else {
			mTab.setValue(X_HR_SalaryExtra.COLUMNNAME_Percent, null);
			mTab.setValue(X_HR_SalaryExtra.COLUMNNAME_FormulaSetup, null);
		}
		return "";
	}

	public String fillRate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";

		Object TypeExtra = mTab.getValue("TypeExtra");
		if (TypeExtra != null ) {
			if (X_HR_SalaryTableLine.TYPEEXTRA_ByRate.equalsIgnoreCase(TypeExtra.toString())) {
				mTab.setValue(X_HR_SalaryTableLine.COLUMNNAME_Percent, null);
			} else {
				mTab.setValue(X_HR_SalaryTableLine.COLUMNNAME_Percent, Env.ONE);
			}
			
		} 
		
		return "";
	}
	
	//eone.base.callout.CalloutEmployee.fillSalaryTable 
	public String fillSalaryTable (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";

		int HR_SalaryTable_ID = 0;
		if (value != null)
			HR_SalaryTable_ID = Integer.valueOf(value.toString());
		MSalaryTable table = MSalaryTable.get(ctx, HR_SalaryTable_ID);
		if (table != null) {
			mTab.setValue(X_HR_Payroll.COLUMNNAME_SalaryBase, table.getSalaryBase());
			mTab.setValue(X_HR_Payroll.COLUMNNAME_SalaryInsurance, table.getSalaryBase());
			
		}
		return "";
	}
	
	//eone.base.callout.CalloutEmployee.fillSalaryTableLine 
	public String fillSalaryTableLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";
		int HR_SalaryTableLine_ID = 0;
		if (value != null)
			HR_SalaryTableLine_ID = Integer.valueOf(value.toString());
		MSalaryTableLine line = MSalaryTableLine.get(ctx, HR_SalaryTableLine_ID, null);
		
		if (line != null ) {
			mTab.setValue(X_HR_Payroll.COLUMNNAME_SalaryRate, line.getPercent());
		} 
		
		return "";
	}
	
	public String fillCodeEmployee (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";
		
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		
		String sql = "Select max(Code) from hr_employee where AD_Client_ID = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(AD_Client_ID);
		
		String code = "000001";
		int retNumber = DB.getSQLValue(null, sql, params);
		if (retNumber > 0) {
			code =  Env.numToChar(++retNumber, 6);
		}
		
		mTab.setValue(X_HR_Employee.COLUMNNAME_Code, code);
		
		return "";
	}
}	//	CalloutInOut