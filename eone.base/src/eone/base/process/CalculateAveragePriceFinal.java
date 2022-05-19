

package eone.base.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import eone.base.model.MPeriod;
import eone.base.model.MYear;
import eone.base.model.X_M_Storage;
import eone.util.DB;
import eone.util.Env;
import eone.util.TimeUtil;

/**
 * @author Admin
 * Chạy lại quét toàn bộ dữ liệu hạch toán, cập nhật lại đơn giá và thành tiền của kho
 */

public class CalculateAveragePriceFinal extends SvrProcess {
	
	private int BATCH_SIZE = 0;
	private int p_Year_ID = 0;
	private int p_Period_ID = 0;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter p : params)
		{
			if ( p.getParameterName().equals("C_Year_ID") )
				p_Year_ID = p.getParameterAsInt();
			if ( p.getParameterName().equals("C_Period_ID") )
				p_Period_ID = p.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + p.getParameterName());
		}
	}

	@Override
	protected String doIt() throws Exception {
		BATCH_SIZE = Env.getBatchSize(getCtx());
		
		if (X_M_Storage.MMPOLICY_None.equals(Env.getMaterialPolicy(getCtx()))) {
			return "Đơn vị chưa chọn phương pháp tính giá!";
		}
		
		if (!X_M_Storage.MMPOLICY_Average.equals(Env.getMaterialPolicy(getCtx()))) {
			return "Phương pháp tính giá hiện tại không cần thực hiện chức năng này!";
		}
		
		MPeriod period = MPeriod.get(getCtx(), p_Period_ID);
		MYear year = MYear.get(getCtx(), p_Year_ID, get_TrxName());
		
		Timestamp startDate = TimeUtil.getFinalYear(year.getFiscalYear(), true);
		
		Timestamp endDate = period.getEndDate();
		
		String sql = 
				" WITH t AS ("
				+ " select Sum(Amount)/Sum(Qty) Price, M_Product_ID from M_Storage " 
				+ " Where (TypeInOut = 'IN' Or TypeInOut = 'OP')  And AD_Client_ID = ? And DateTrx >= ? And DateTrx <= ? "
				+ " Group by M_Product_ID "
				+ ")"
				+ " UPDATE M_InOutLine l SET "
				+ "		PricePO = (SELECT Price FROM t WHERE l.M_Product_ID = t.M_Product_ID) "
				+ "		Amount = Qty * (SELECT Price FROM t WHERE l.M_Product_ID = t.M_Product_ID)"
				+ " WHERE NVL(PricePO,0) > 0 ";
			
		List<List<Object>> values = new ArrayList<List<Object>>();
		List<Object> line = null;
		PreparedStatement ps = DB.prepareCall(sql);
		ps.setInt(1, Env.getAD_Client_ID(getCtx()));
		ps.setTimestamp(2, startDate);
		ps.setTimestamp(3, endDate);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			line = new ArrayList<Object>();
			line.add(rs.getBigDecimal("Price"));
			line.add(rs.getInt("M_Product_ID"));
			values.add(line);
		}
		
		DB.close(rs, ps);
		return "Updated! ";
	}
	
	
}
