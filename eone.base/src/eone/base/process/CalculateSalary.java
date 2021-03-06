
package eone.base.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eone.base.model.I_HR_SalaryLine;
import eone.base.model.MConfig;
import eone.base.model.MFamilyTies;
import eone.base.model.MPayroll;
import eone.base.model.MSalary;
import eone.base.model.MSalaryExtra;
import eone.base.model.MSalaryLine;
import eone.base.model.MTaxPersonal;
import eone.base.model.PO;
import eone.base.model.X_HR_Config;
import eone.base.model.X_HR_SalaryLine;
import eone.util.DB;
import eone.util.Env;

public class CalculateSalary extends SvrProcess {

	/**
	 * Quynhnv.x8: Phe duyet cong den ngay nao day
	 */
	private int BATCH_SIZE = Env.getBatchSize(getCtx());
	@Override
	protected void prepare() {
		//
		/*
		for (ProcessInfoParameter para : getParameter()) {
			String name = para.getParameterName();
			if ("Date".equals(name)) {
				p_Date  = para.getParameterAsTimestamp();
			} else {
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}
		*/
	}

	@Override
	protected String doIt() throws Exception {
		String sql = "Select count(1) From HR_SalaryLine Where HR_Salary_ID = ?";
		int no = DB.getSQLValue(get_TrxName(), sql, getRecord_ID());
		if (no <= 0)
			return "No record detail!";
		
		MSalary salary = MSalary.get(getCtx(), getRecord_ID());
		
		Map<Integer,MSalaryLine> listEmployees = salary.getAllEmployee(getCtx(), get_TrxName());
		//Lay danh sach thong tin luong duoc cau hinh.
		Map<Integer, MPayroll> listItems = MPayroll.getAllItem(getCtx(), get_TrxName(), salary.getAD_Client_ID());
		
		//Tinh phu cap
		List<MSalaryExtra> listExtra = null;
		BigDecimal totalExtra;
		MSalaryExtra extra;
		
		MSalaryLine line;
		MPayroll payroll;
		
		BigDecimal totalSalaryPart1;			//Tong luong ky 1: = Luong di lam + luong phu cap ... + luong lam them.
		BigDecimal totalSalaryWorkDay;			//Tong luong di lam = Luong di lam + Luong nghi phep
		BigDecimal totalSalaryWorkExtra;		//Tong luong lam them = Lam them ngay thuong + lam them ngay le
		BigDecimal totalSalaryExtra;			//Tong luong phu cap: chuc vu, ...
		BigDecimal totalWorkSalary;				//Tong ngay lam viec
		BigDecimal salaryOneDay;				//Luong cua 1 ngay
		BigDecimal totalTaxableIncom;			//Tong thu nhap ch???u thu = Gross - cac khoan giam tru.
		BigDecimal taxIncom;					//Thue thu nhap ca nhan
		String sqlUpdate = PO.getSqlUpdate(I_HR_SalaryLine.Table_ID);
		
		List<List<Object>> values = new ArrayList<List<Object>>();
		List<Object> params;
		
		//Lay cac thong tin cau hinh
		Map<String, MConfig> hrConfig = MConfig.getAllItem(getCtx(), get_TrxName(), Env.getAD_Client_ID(getCtx()));
		
		//B???o hi???m y t???
		BigDecimal healthInsurance = Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_IndividualHealthInsurance))
			healthInsurance = hrConfig.get(X_HR_Config.NAME_IndividualHealthInsurance).getValueNumber();
		
		//B???o hi???m x?? h???i
		BigDecimal socialInsurance = 		Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_IndividualSocialInsurance))
			socialInsurance = 		hrConfig.get(X_HR_Config.NAME_IndividualSocialInsurance).getValueNumber();
		
		//B???o hi???m th???t nghi???p
		BigDecimal unemploymentInsurance = 	Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_UnemploymentInsurance))
			unemploymentInsurance = 	hrConfig.get(X_HR_Config.NAME_UnemploymentInsurance).getValueNumber();
		
		//Gi???m tr??? c?? nh??n
		BigDecimal personalDeduction = 		Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_PersonalDeduction))
			personalDeduction = 		hrConfig.get(X_HR_Config.NAME_PersonalDeduction).getValueNumber();
		
		//Gi???m tr??? ng?????i ph??? thu???c
		BigDecimal dependentDeduction = 	Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_DependentDeduction))
			dependentDeduction = 	hrConfig.get(X_HR_Config.NAME_DependentDeduction).getValueNumber();
		
		//L????ng c?? b???n
		BigDecimal baseSalary = 			Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_BaseSalaryPay))
			baseSalary = 			hrConfig.get(X_HR_Config.NAME_BaseSalaryPay).getValueNumber();
		
		//Ph???n d??nh cho doanh nghi???p
		//Doanh nghi???p: Ph?? c??ng ??o??n
		
		BigDecimal unionFee = 				Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_UnionFee))
			unionFee = hrConfig.get(X_HR_Config.NAME_UnionFee).getValueNumber();
		//Doanh nghi???p: B???o hi???m y t???
		BigDecimal ohealthInsurance = Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_OrganizationHealthInsurance))
			ohealthInsurance = hrConfig.get(X_HR_Config.NAME_OrganizationHealthInsurance).getValueNumber();
		
		//Doanh nghi???p: B???o hi???m x?? h???i
		BigDecimal osocialInsurance = 		Env.ZERO;
		if (hrConfig.containsKey(X_HR_Config.NAME_OrganizationSocialInsurance))
			osocialInsurance = 		hrConfig.get(X_HR_Config.NAME_OrganizationSocialInsurance).getValueNumber();
		
		
		//So nguoi phu thuoc
		Map<Integer, BigDecimal> listDependent = MFamilyTies.getAllItem(getCtx(), salary.getAD_Client_ID(), salary.getC_Period_ID());
		BigDecimal numberDependent;
		
		for(Map.Entry<Integer, MSalaryLine> entry : listEmployees.entrySet()) {
			totalSalaryPart1 = Env.ZERO;
			totalSalaryWorkDay = Env.ZERO;
			totalSalaryWorkExtra = Env.ZERO;
			totalSalaryExtra = Env.ZERO;
			totalWorkSalary = Env.ZERO;
			totalTaxableIncom = Env.ZERO;
			numberDependent = Env.ZERO;
			totalExtra = Env.ZERO;
			taxIncom = Env.ZERO;
			
			line = entry.getValue();
			
			BigDecimal salaryInsurance = Env.ZERO;
			
			if (listItems.containsKey(line.getHR_Employee_ID())) {
				params = new ArrayList<Object>();
				
				//Thong tin luong
				payroll = listItems.get(line.getHR_Employee_ID());
				
				if (payroll.getSalaryRate().compareTo(Env.ZERO) > 0) {
					line.setSalaryBase(payroll.getSalaryBase().multiply(payroll.getSalaryRate()));				
				} else
					line.setSalaryBase(payroll.getSalaryBase());
				
				//Tong ngay cong tinh luong: di lam + nghi phep + nghi le
				totalWorkSalary = line.getTotalWorkDay().add(line.getTotalDayOff());
				
				//Luong 1 ngay cong di lam
				salaryOneDay = payroll.getSalaryBase()
						.multiply(payroll.getSalaryPercent()).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP)
						.divide(new BigDecimal(line.getWorkdaySTD()), 2, RoundingMode.HALF_UP);
				
				//N???u c?? h??? s??? l????ng th?? nh??n th??m h??? s??? l????ng
				if (payroll.getSalaryRate().compareTo(Env.ZERO) > 0) {
					salaryOneDay = salaryOneDay.multiply(payroll.getSalaryRate());
				}
				
				//Neu tong cong di lam vuot cong chuan thi lay bang cong chuan.
				if (totalWorkSalary.compareTo(new BigDecimal(line.getWorkdaySTD())) > 0) {
					totalWorkSalary = new BigDecimal(line.getWorkdaySTD());
					totalSalaryWorkDay = payroll.getSalaryBase();
				} else {
					//Tong luong di lam + nghi phep
					totalSalaryWorkDay = salaryOneDay.multiply(totalWorkSalary);
				}
				
				//Tinh luong lam them: (Ngay thuong + Ngay le ) * Luong 1 ngay. (Trong nay da tinh ty le huong roi)
				totalSalaryWorkExtra = (line.getTotalWorkExtra().add(line.getTotalWorkExtraHoliday())).multiply(salaryOneDay);
				
				//Nhan them voi ty le huong luong
				totalSalaryWorkDay = totalSalaryWorkDay.multiply(payroll.getSalaryPercent()).divide(Env.ONEHUNDRED);
				
				//Tinh tong cac loai luong:
				totalSalaryPart1 = totalSalaryWorkDay.add(totalSalaryExtra).add(totalSalaryWorkExtra);
				
				line.setSalaryPart1(totalSalaryPart1.setScale(Env.getScaleFinal(), RoundingMode.HALF_UP));
				
				//L????ng ch???c v???
				line.setSalaryPosition(payroll.getSalaryPosition());
				
				line.setSalaryProduction(Env.ZERO);
				
				//Tinh luong phu cap
				
				listExtra = MSalaryExtra.getAllItem(getCtx(), get_TrxName(), salary.getAD_Client_ID(), line.getHR_Employee_ID());
				if (listExtra != null) {
					for(int i = 0; i < listExtra.size(); i++) {
						extra = listExtra.get(i);
						String str = extra.getFormulaSetup();
						if (extra.getFormulaSetup().contains("@SalaryBase@")) {
							str = baseSalary.toString();
						}
						BigDecimal value = Env.ZERO;
						if (!str.isEmpty() && str != null)
							value = Env.getValueByFormula(str);
						totalExtra = totalExtra.add(extra.getPercent().multiply(value));
					}
					line.setSalaryExtra(totalExtra);
				}
				
				line.setSalaryGross(line.getSalaryPart1()
						.add(line.getSalaryProduction())
						.add(line.getSalaryExtra()));
				
				//N???u c?? l????ng t??nh b???o hi???m ri??ng th?? l???y theo gi?? tr??? t??nh b???o hi???m
				salaryInsurance = line.getSalaryGross();
				if (payroll.getSalaryInsurance().compareTo(Env.ZERO) > 0) {
					salaryInsurance = payroll.getSalaryInsurance()
							.multiply(totalWorkSalary)
							.divide(new BigDecimal(line.getWorkdaySTD()), 0, RoundingMode.HALF_UP);
				}
				
				line.setInsua_Health(salaryInsurance.multiply(healthInsurance).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				line.setInsua_Social(salaryInsurance.multiply(socialInsurance).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				line.setInsua_Unemployee(salaryInsurance.multiply(unemploymentInsurance).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				
				//T??nh cho doanh nghi???p
				line.setOrg_Insua_Health(salaryInsurance.multiply(ohealthInsurance).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				line.setOrg_Insua_Social(salaryInsurance.multiply(osocialInsurance).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				line.setOrg_Insua_Unemployee(salaryInsurance.multiply(unemploymentInsurance).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				line.setOnionFeeAmt(salaryInsurance.multiply(unionFee).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP));
				
				
				//Tinh so nguoi phu thuoc
				if (listDependent.containsKey(line.getHR_Employee_ID()))
					numberDependent = listDependent.get(line.getHR_Employee_ID());
				if (numberDependent == null) 
					numberDependent = Env.ZERO;
				
				//Tinh tong thu nhap chiu thue
				totalTaxableIncom = line.getSalaryGross()
						.subtract(line.getInsua_Health())
						.subtract(line.getInsua_Social())
						.subtract(line.getInsua_Unemployee())
						.subtract(personalDeduction)
						.subtract(dependentDeduction.multiply(numberDependent));
				
				taxIncom = MTaxPersonal.getAmountTaxPersonal(totalTaxableIncom, getCtx(), get_TrxName(), Env.getAD_Client_ID(getCtx())); 
				line.setTaxAmt(taxIncom.setScale(Env.getScaleFinal(), RoundingMode.HALF_UP));
				
				line.setSalaryNet(line.getSalaryGross()
						.subtract(line.getInsua_Health())
						.subtract(line.getInsua_Social())
						.subtract(line.getInsua_Unemployee())
						.subtract(line.getTaxAmt())						
						);
				
				//line.save();
				line.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				line.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				//Add du lieu vao list de update thoe batch
				
				List<String> colNames = PO.getSqlUpdate_Para(X_HR_SalaryLine.Table_ID);
				params = PO.getBatchValueList(line, colNames, I_HR_SalaryLine.Table_ID, get_TrxName(), line.getHR_SalaryLine_ID());
				values.add(params);
				
				
				if (values.size() >= BATCH_SIZE) {
					String err = DB.excuteBatch(sqlUpdate, values, get_TrxName());
					if(err != null) {
						try {
							DB.rollback(false, null);
						} catch(Exception e) {
						}
					}
					values.clear();
				}
			}
		}
		
		if (values.size() > 0) {
			String err = DB.excuteBatch(sqlUpdate, values, get_TrxName());
			if(err != null) {
				try {
					DB.rollback(false, null);
				} catch(Exception e) {
				}
			}
			values.clear();
		}
		
		listEmployees.clear();
		hrConfig.clear();
		listItems.clear();
		listDependent.clear();
		return "Success";
	}
	
	
}
