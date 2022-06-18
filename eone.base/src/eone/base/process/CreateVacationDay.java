
package eone.base.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;

import eone.base.model.MEmployee;
import eone.base.model.MPeriod;
import eone.base.model.MYear;
import eone.base.model.Query;
import eone.base.model.X_HR_VacationDay;
import eone.util.Env;
import eone.util.TimeUtil;

public class CreateVacationDay extends SvrProcess {

	/**
	 * Quynhnv.x8: Tạo ngày phép được nghỉ cho toàn bộ nhân viên
	 */
	private int C_Year_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter parameter : params)
		{
			String para = parameter.getParameterName();
			if ( para.equals("C_Year_ID") )
				C_Year_ID = parameter.getParameterAsInt();
			else
				log.log(Level.WARNING, "Unknown paramter: " + para);
		}
	}

	@Override
	protected String doIt() throws Exception {
		String whereClause = " 1=1";
		List<MEmployee> retValue = new Query(getCtx(), MEmployee.Table_Name, whereClause, get_TrxName(), true)
				.list();
		if (retValue != null) {
			MYear year = MYear.get(getCtx(), C_Year_ID, get_TrxName());
			MPeriod period = MPeriod.get(getCtx(), TimeUtil.getDay(year.getYearAsInt(), 1, 1));
			X_HR_VacationDay item = null;
			for(int i = 0; i < retValue.size(); i++) {
				item = new X_HR_VacationDay(getCtx(), 0, get_TrxName());
				item.setC_Year_ID(C_Year_ID);
				item.setHR_Employee_ID(retValue.get(i).getHR_Employee_ID());
				item.setAccumulateQty(Env.ZERO);
				item.setVacantDay(new BigDecimal("12"));
				item.setC_Period_ID(period.getC_Period_ID());
				item.setRemainQty(item.getVacantDay().subtract(item.getAccumulateQty()));
				item.save();
			}
		}
		return "Success";
	}

	
}
