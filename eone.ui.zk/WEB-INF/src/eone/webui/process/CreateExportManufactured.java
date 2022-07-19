

package eone.webui.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import eone.base.model.MInOut;
import eone.base.model.MInOutLine;
import eone.base.model.MStorage;
import eone.base.model.X_M_Production;
import eone.base.process.SvrProcess;
import eone.util.DB;
import eone.util.Env;

/**
 * 
 * @author Client
 *
 */
public class CreateExportManufactured extends SvrProcess {


	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		String sqlDelete = "DELETE FROM M_InOutLine WHERE M_InOut_ID IN (SELECT M_InOUT_ID FROM M_InOut WHERE Key_Original = ? AND Code_Original = ? AND DocStatus = 'DR')";
		Object [] params = {getRecord_ID(), X_M_Production.Table_Name};
		DB.executeUpdate(sqlDelete, params, true, get_TrxName());
		
		sqlDelete = "DELETE FROM M_InOut WHERE Key_Original = ? AND Code_Original = ? AND DocStatus = 'DR'";
		DB.executeUpdate(sqlDelete, params, true, get_TrxName());
		
		//AEnv.zoom(319, 516);
		String sql = 
				"	SELECT p.DateAcct, p.DocumentNo, o.M_Warehouse_ID M_Warehouse_Dr_ID, o.M_Product_ID, "
				+ "		i.M_Warehouse_ID M_Warehouse_Cr_ID, i.M_Product_ID M_Product_Cr_ID, i.Qty "
				+ "	FROM M_Production p "
				+ "		INNER JOIN M_ProductionOutput o ON p.M_Production_ID = o.M_Production_ID"
				+ "		INNER JOIN M_ProductionInput i ON o.M_ProductionOutput_ID = i.M_ProductionOutput_ID"
				+ "	WHERE p.M_Production_ID = ? AND p.Processed = 'Y'";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		ps.setInt(1, getRecord_ID());
		rs = ps.executeQuery();
		int i = 0;
		MInOut inout = null;
		MInOutLine line = null;
		while (rs.next()) {
			i++;
			if (i == 1) {
				inout = new MInOut(getCtx(), 0, get_TrxName());
				inout.setDateAcct(rs.getTimestamp("DateAcct"));
				inout.setC_DocType_ID(137);
				inout.setC_DocTypeSub_ID(1000228);
				inout.setProcessed(false);
				inout.setDescription("Xuất kho theo lệnh: " + rs.getString("DocumentNo"));
				inout.setDocStatus("DR");
				
				inout.setKey_Original(getRecord_ID());
				inout.setCode_Original(X_M_Production.Table_Name);
				inout.setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_CurrencyDefault_ID"));
				inout.setCurrencyRate(Env.ONE);
				
				inout.save(get_TrxName());
			}
			line = new MInOutLine(inout);
			line.setM_Product_ID(rs.getInt("M_Product_ID"));
			line.setM_Product_Cr_ID(rs.getInt("M_Product_Cr_ID"));
			line.setLine(i * 10);
			line.setQty(rs.getBigDecimal("Qty"));
			line.setM_Warehouse_Dr_ID(rs.getInt("M_Warehouse_Dr_ID"));
			line.setM_Warehouse_Cr_ID(rs.getInt("M_Warehouse_Cr_ID"));
			BigDecimal price = MStorage.getPriceStorage(line.getM_Product_Cr_ID(), line.getM_Warehouse_Cr_ID(), inout.getDateAcct(), null, line.getQty());
			line.setPrice(price);
			line.setAmount(line.getQty().multiply(line.getPrice()));
			line.save(get_TrxName());
		}
		DB.close(rs, ps);
		rs = null;
		ps = null;
		return "Số chứng từ: "+ inout.getDocumentNo();
	}
}
