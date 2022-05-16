

package eone.base.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import eone.base.model.MStorage;
import eone.base.model.MYear;
import eone.base.model.X_M_Storage;
import eone.util.Env;
import eone.util.TimeUtil;

/**
 * @author Admin
 * Chot so du vat tu hang hoa cap nhat vao bang M_Storage
 * N: Nam hien tai can tinh toan cap nhat lai kho.
 * Tap hop chot so du cho nam N. 
 * Tinh toan lai so phat sinh cho nam N+1.
 * VD: can chot so du cho nam 2020
 * 		1. Ton cuoi bao nhieu dua vao ngay 01/01/2021. Số tồn
 * 			TypeInOut = OP: Tồn
 * 			TypeInOut = IN: NHập
 * 			TypeInOut = OT: Xuất
 * 		2. Quet ra soat cap nhat lai ps nam 2021. => Không cần
 */

public class UpdateOpenBalanceWarehouse extends SvrProcess {
	

	private int p_Year_ID = 0;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter p : params)
		{
			if ( p.getParameterName().equals("C_Year_ID") )
				p_Year_ID = p.getParameterAsInt();
			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + p.getParameterName());
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		
		if (X_M_Storage.MMPOLICY_None.equals(Env.getMaterialPolicy(getCtx()))) {
			return "Đơn vị chưa chọn phương pháp tính giá!";
		}
		
		MYear year = MYear.get(getCtx(), p_Year_ID, get_TrxName());
		//Lay sang ngay 01/01 cua nam sau thay vi 31/12 cua nam truoc. Vi co the lech gio, phut, giay.
		Timestamp startDate = TimeUtil.getFinalYear(year.getFiscalYear(), true);
		
		//Lay ngay 31/12/N de chot so du den ngay nay.
		Timestamp endDate = TimeUtil.getFinalYear(year.getFiscalYear(), false);
		endDate = TimeUtil.addDays(endDate, 1);//
		
		//Tinh toan so du dau ky cua nam N de Insert vao nam N + 1
		MStorage.insertOpenBalance(startDate, endDate, getCtx(), get_TrxName());
		
		return "inserted ";
	}
}
