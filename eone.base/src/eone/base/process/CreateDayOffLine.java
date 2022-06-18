
package eone.base.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eone.base.model.MDayOff;
import eone.base.model.MDayOffLine;
import eone.base.model.MHoliday;
import eone.base.model.MTimekeeperMap;
import eone.base.model.MVacationDay;
import eone.base.model.MWorkDay;
import eone.base.model.PO;
import eone.base.model.X_HR_DayOffLine;
import eone.util.DB;
import eone.util.Env;
import eone.util.TimeUtil;

public class CreateDayOffLine extends SvrProcess {

	/**
	 * Quynhnv.x8: Tong hop cong nhan vien
	 */
	private int BATCH_SIZE = Env.getBatchSize(getCtx());
	
	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		String sqlDel = "Delete from HR_DayOffLine Where HR_DayOff_ID = ?";
		DB.executeUpdate(sqlDel, getRecord_ID(), get_TrxName());
		
		MDayOff dayoff = MDayOff.get(getCtx(), getRecord_ID(), get_TrxName());
		
		double dayOffStandard = 12;//TODO: Tinh toan them cho cau hinh ngay nghi phep de lay gia tri nay
		
		MVacationDay vacantEmployee = MVacationDay.getVacantEmployee(getCtx(), dayoff.getHR_Employee_ID(), dayoff.getStartTime(), get_TrxName());
		if (vacantEmployee != null && vacantEmployee.getRemainQty().compareTo(Env.ZERO) > 0)
			dayOffStandard = Double.parseDouble(vacantEmployee.getRemainQty().toString());
		
		Map<String, MTimekeeperMap> listItems = MTimekeeperMap.getAllItems(getCtx(), get_TrxName());
		List<List<Object>> values = new ArrayList<List<Object>>();
		
		int daybetween = TimeUtil.getDaysBetween(dayoff.getStartTime(), dayoff.getEndTime());
		Timestamp dayInc = dayoff.getStartTime();
		MDayOffLine line = null;
		int sequence = -1;
		
		//Lay list danh sach ngay lam viec va ngay nghi
		Map<Timestamp, Timestamp> listHoliday = MHoliday.getListHoliday(getCtx(), get_TrxName(), dayoff.getStartTime(), dayoff.getEndTime());
		Map<Timestamp, Timestamp> listWorkDay = MWorkDay.getListWorkDay(getCtx(), get_TrxName(), dayoff.getStartTime(), dayoff.getEndTime());
		
		while (dayInc.compareTo(dayoff.getEndTime()) <= 0) {
			
			//Khong phai ngay lam viec thi bo qua
			if (!listWorkDay.containsKey(dayInc)) {
				dayInc = TimeUtil.getNextDay(dayInc);
				continue;
			}
			
			line = new MDayOffLine(getCtx(), 0, get_TrxName());
			
			//Neu la ngay le thi cham cong la nghi le, nguoc lai thi moi xet co phai la ngay phep khong
			if (listHoliday.containsKey(dayInc)) {
				line.setDayOffType(X_HR_DayOffLine.DAYOFFTYPE_NL);
			} else {
				if (dayOffStandard > 0) 
				{
					Long hour = TimeUtil.getNumberOnCalander(dayoff.getStartTime(), dayoff.getEndTime(), "hours");
					if (hour <= 4) {
						line.setDayOffType(X_HR_DayOffLine.DAYOFFTYPE_P4);
						dayOffStandard = dayOffStandard - 0.5;
					} else {
						line.setDayOffType(X_HR_DayOffLine.DAYOFFTYPE_P8);
						dayOffStandard = dayOffStandard - 1;
					}
										
				} else {
					line.setDayOffType(X_HR_DayOffLine.DAYOFFTYPE_KL);
				}
			}//End ngay le
			
			line.setHR_DayOff_ID(getRecord_ID());
			
			line.setStartTime(dayInc);
			line.setEndTime(dayInc);
			line.setAddress(dayoff.getAddress());
			if (daybetween > 5) {
				sequence = DB.getNextID(X_HR_DayOffLine.Table_Name, null);
				line.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				line.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				
				List<String> colNames = PO.getSqlInsert_Para(X_HR_DayOffLine.Table_ID, get_TrxName());
				List<Object> lstParam = PO.getBatchValueList(line, colNames, X_HR_DayOffLine.Table_ID, get_TrxName(), sequence);
				
				values.add(lstParam);
				if(values.size() >= BATCH_SIZE) {
					String sqlInsert = PO.getSqlInsert(X_HR_DayOffLine.Table_ID, null);
					if(values.size() > 0) {
						String err = DB.excuteBatch(sqlInsert, values, null);
						if(err != null) {
							try {
								DB.rollback(false, null);
							} catch(Exception e) {
							}
						}
					}
					values.clear();
				}
			} else {
				line.save();
			}
			
			dayInc = TimeUtil.getNextDay(dayInc);
		}
		String sqlInsert = PO.getSqlInsert(X_HR_DayOffLine.Table_ID, null);
		if(values.size() > 0) {
			String err = DB.excuteBatch(sqlInsert, values, null);
			if(err != null) {
				try {
					DB.rollback(false, null);
				} catch(Exception e) {
				}
			}
		}
		
		//Clear Memory Leak
		listItems.clear();
		listHoliday.clear();
		listWorkDay.clear();
		return "Success";
	}
}
