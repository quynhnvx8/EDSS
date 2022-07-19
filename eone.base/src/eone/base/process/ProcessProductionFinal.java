
package eone.base.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import eone.base.model.MAccountDefault;
import eone.base.model.MElementValue;
import eone.base.model.MInOut;
import eone.base.model.MInOutLine;
import eone.base.model.Query;
import eone.base.model.X_C_ElementValue;
import eone.base.model.X_C_TypeCost;
import eone.util.DB;
import eone.util.Env;
import eone.util.TimeUtil;

/**
 *	
 *  @author Client
 */
//eone.base.process.ProductionTransfer
public class ProcessProductionFinal extends SvrProcess
{
	private Timestamp	fromDate = null;
	private Timestamp	toDate = null;
	
	//private int C_Element_ID = Env.getContextAsInt(getCtx(), "#C_Element_ID");
	
	@Override
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("FromDate"))
				fromDate = (Timestamp) para[i].getParameter();
			else if (name.equals("ToDate"))
				toDate = (Timestamp) para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	}

	private HashMap<Integer, MAccountDefault> listAcct = null;
	
	//Tài khoản chi phí. Là danh sách tk có khi hạch toán.
	private String strAcctDr = "0";
	
	/*
	 * Thứ tự xử lý:
	 * 1. Tạo 1 phiếu làm header
	 * 2. Kết chuyển chi phí đích danh của thành phẩm, bán thành phẩm mỗi phân xưởng
	 * 3. Phân bổ chi phí chung của toàn bộ thành phẩm, bán thành phẩm mỗi phân xưởng
	 * 4. Tính giá cho từng công đoạn dựa vào sản phẩm sản xuất được.
	 * 
	 */
	@Override
	protected String doIt() throws Exception
	{
		String check = checkDate();
		if (!"".equals(check))
			return check;
		
		//Lấy danh sách tài khoản theo phương pháp phân bổ là NVL hay nhân công để phân bổ
		listAcctMaterial = getAcctByMethodAllocation(X_C_TypeCost.METHODALLOCATION_ByMaterial);
		listAcctWorker = getAcctByMethodAllocation(X_C_TypeCost.METHODALLOCATION_ByWorker);
		
		
		int C_DocType_ID = 200010; //154TRA
		listAcct = MAccountDefault.getAccountTransfer(C_DocType_ID);
		
		for (int key : listAcct.keySet()) {
			strAcctDr = strAcctDr + "," + key;
		}
		//1. Tạo 1 phiếu ở header.
		MInOut inout = new MInOut(getCtx(), 0, get_TrxName());
		inout.setDateAcct(toDate);
		inout.setDescription("Kết chuyển cuối kỳ tính giá thành " + TimeUtil.getStrDate(toDate));
		inout.setC_DocType_ID(C_DocType_ID);
		inout.setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_CurrencyDefault_ID"));
		inout.setCurrencyRate(Env.ONE);
		inout.setDocStatus("DR");
		inout.setProcessed(false);
		inout.save(get_TrxName());
		
		//2. Kết chuyển các chi phí đích danh
		processTransfer(inout);
		
		//3. Phân bổ các chi phí tập hợp
		processAllocation(inout);
		
		//4. Lần lượt thành phẩm được cấu hình từ dây chuyền sản xuất để tính giá vốn.
		//Nếu sản phẩm từ px này làm nguyên vật liệu cho px sau thì phải cộng thêm vào 154 của px sau nữa
		processPrice();
		
		//5. Viết hàm update lại đơn giá và thành tiền các chứng từ.
		updatePrice();
		
		objProduction = null;
		listAmtMaterial = null;
		listAmtWorker = null;
		lsProduct_Amount = null;
		lsPeriodPrevious = null;
		listQtyFinish = null;
		listQtyUnFinish = null;
		lsPriceProduct = null;
		listProduct_DU = null;
		return "Chứng từ: "+ inout.getDocumentNo() ;
	}	//	doIt
	
	/*
	 * B4:
	 * Xử lý tính toán giá trị
	 * Trả về danh sách giá sản phẩm
	 */
	private void processPrice() throws Exception {
		String sql = " SELECT h.M_Product_ID as KEY, l.OrderNo, l.M_Warehouse_ID, l.M_Product_ID"
				+ "	FROM M_Factories h INNER JOIN M_FactoriesLine l ON h.M_Factories_ID = l.M_Factories_ID"
				+ "	WHERE h.IsActive = 'Y' AND h.Processed = 'Y'"
				+ "	ORDER BY h.M_Product_ID, l.OrderNo ";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = ps.executeQuery();
		//Duyệt các bản ghi của từng phân xưởng
		
		int M_Product_ID = 0;
		BigDecimal amtProduct = null;
		int qtyFinish = 0;
		int qtyUnFinish = 0;
		BigDecimal amtUnFinish = null;
		BigDecimal amtFinal = null;
		while (rs.next()) {
			amtProduct = Env.ZERO;
			amtUnFinish = Env.ZERO;
			amtFinal = Env.ZERO;
			M_Product_ID = rs.getInt("M_Product_ID");
			
			//Lấy đầu kỳ
			if (lsPeriodPrevious.containsKey(M_Product_ID)) {
				amtProduct = amtProduct.add(lsPeriodPrevious.get(M_Product_ID));
				if (amtProduct == null)
					amtProduct = Env.ZERO;
			}
			
			//Lấy tiền ps trong kỳ (Chưa tính NVL từ BTP phân xưởng trước chuyển sang)
			if (lsProduct_Amount.containsKey(M_Product_ID)) {
				amtProduct = amtProduct.add(lsProduct_Amount.get(M_Product_ID));
				if (amtProduct == null)
					amtProduct = Env.ZERO;
			}
			
			//Lấy số lượng hoàn thành
			if (listQtyFinish.containsKey(M_Product_ID)) {
				qtyFinish = listQtyFinish.get(M_Product_ID);				
			}
			
			//Lấy số lượng dở dang (đã nhân với hệ số)
			if (listQtyUnFinish.containsKey(M_Product_ID)) {
				qtyUnFinish = listQtyUnFinish.get(M_Product_ID);				
			}
				
			if (qtyFinish == 0)
				qtyFinish = 1;
			if (qtyUnFinish == 0)
				qtyUnFinish = 1;
			//Tính giá trị dở dang
			//GTDD = (DK + PSTK)/(SLHT + SLDD)*SLDD
			
			amtUnFinish = amtProduct.divide(new BigDecimal(qtyFinish + qtyUnFinish)).divide(new BigDecimal(qtyUnFinish));
			
			amtFinal = amtProduct.subtract(amtUnFinish);
			ArrayList<BigDecimal> obj = new ArrayList<BigDecimal>();
			obj.add(new BigDecimal(qtyFinish));
			obj.add(amtFinal.divide(new BigDecimal(qtyFinish)));
			obj.add(amtFinal);
			lsPriceProduct.put(M_Product_ID, obj);
			
			//Tính lại tiền của sản phẩm cho phân xưởng tiếp theo.
			//Lấy số lượng nhân với đơn giá đã tính được để cộng thêm vào làm chi phí cho SP ở px tiếp theo.
			if (listProduct_DU.containsKey(M_Product_ID)) {
				ArrayList<Integer> arr = listProduct_DU.get(M_Product_ID);
				int M_Product_Dr_ID = arr.get(0);
				int qty = arr.get(1);
				if (lsProduct_Amount.containsKey(M_Product_Dr_ID)) {
					BigDecimal amt = lsProduct_Amount.get(M_Product_Dr_ID);
					BigDecimal amtEx = amtFinal.divide(new BigDecimal(qtyFinish)).multiply(new BigDecimal(qty));
					if (amtEx == null)
						amtEx = Env.ZERO;
					lsProduct_Amount.put(M_Product_Dr_ID, amt.add(amtEx));
				}
			}
			
		} //Kết thúc duyệt các phân xưởng.
		DB.close(rs, ps);
	}
	
	private Map<Integer, Integer> objProduction = new HashMap<Integer, Integer>();
	
	//Danh sách sản phẩm để phân bổ. Tiền theo nguyên vật liệu
	private Map<Integer, BigDecimal> listAmtMaterial = new HashMap<Integer, BigDecimal>();
	
	//Danh sách sản phẩm để phân bổ. Tiền theo nhân công
	private Map<Integer, BigDecimal> listAmtWorker = new HashMap<Integer, BigDecimal>();	
	
	//Tổng tiền nguyên vật liệu của toàn bộ SP.
	private BigDecimal totalAmtMaterial = Env.ZERO;
	
	//Tổng tiền nhân công của toàn bộ SF
	private BigDecimal totalAmtWorker = Env.ZERO;
	
	//Danh sách tài khoản NVL
	private List<Integer> listAcctMaterial = null;
	
	//Danh sách tài khoản nhân công
	private List<Integer> listAcctWorker = null;
	
	//Danh sách sản phẩm đối ứng khi chuyển từ px này sang px kia
	private Map<Integer, ArrayList<Integer>> listProduct_DU = new HashMap<Integer, ArrayList<Integer>>();
		
	//Danh sách sản phẩm hoàn thành
	private Map<Integer, Integer> listQtyFinish = new HashMap<Integer, Integer>();
	
	//Danh sách sản phẩm dở dang (đã nhân với tỷ lệ hoàn thành)
	private Map<Integer, Integer> listQtyUnFinish = new HashMap<Integer, Integer>();
	
	//Danh sách dư đầu kỳ của các sản phẩm
	private Map<Integer, BigDecimal> lsPeriodPrevious = new HashMap<Integer, BigDecimal>();
	
	//Danh sách sản phẩm  - Tiền để tính giá cho từng công đoạn
	private Map<Integer, BigDecimal> lsProduct_Amount = new HashMap<Integer, BigDecimal>();
	
	//Danh sách giá của từng sản phẩm sau khi tính xong
	private Map<Integer, ArrayList<BigDecimal>> lsPriceProduct = new HashMap<Integer, ArrayList<BigDecimal>>();
	
	/*
	 * Lấy dữ liệu là dạng tập hợp chi phí được cấu hình. Và phân bổ theo tiêu chí đã được chọn từ cấu hình.
	 * Tập hợp chi phí được nhập từ chức năng quản lý sản xuất. Hoặc từ khấu hao tài sản.
	 * Nếu từ khấu hao tài sản thì phải cấu hình khoản mục sản xuất.
	 */
	
	public void processAllocation(MInOut inout) throws Exception {
		
		Map<String, BigDecimal> data = new HashMap<String, BigDecimal>();
		String sql = " 	SELECT SUM(Amt) Amt, B.C_TypeCost_ID, Account_ID, t.MethodAllocation " 
				+" 	FROM " 
				+"	( "
				+"		SELECT SUM(l.Amount) Amt, l.C_TypeCost_ID, l.Account_Dr_ID Account_ID "
				+"		FROM C_General h INNER JOIN C_GeneralLine l ON h.C_General_ID = l.C_General_ID "
				+"		WHERE h.DateAcct BETWEEN ? AND ? " //#1#2
				+"			AND l.Account_DR_ID IN ("+ strAcctDr +") "
				+"			AND l.C_TypeCost_ID IN (SELECT C_TypeCost_ID From C_TYpeCost WHERE IsManufactured = 'Y')"	
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	//#3
				+"		GROUP BY l.Account_Dr_ID, l.C_TypeCost_ID "
				+"		UNION ALL "
				+"		SELECT -SUM(l.Amount) Amt, l.C_TypeCost_ID, l.Account_Cr_ID Account_ID "
				+"		FROM C_General h INNER JOIN C_GeneralLine l ON h.C_General_ID = l.C_General_ID "
				+"		WHERE h.DateAcct BETWEEN ? AND ? " //#4#5
				+"			AND l.Account_CR_ID IN ("+ strAcctDr +") "
				+"			AND l.C_TypeCost_ID IN (SELECT C_TypeCost_ID From C_TYpeCost WHERE IsManufactured = 'Y')"	
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	//#6
				+"		GROUP BY l.Account_Cr_ID, l.C_TypeCost_ID "
				+"		UNION ALL " //Lấy từ khấu hao TSCĐ (nếu có)
				+"		SELECT SUM(l.Amount) Amt, l.C_TypeCost_ID, l.Account_Dr_ID Account_ID "
				+"		FROM A_Depreciation h INNER JOIN A_Depreciation_Exp l ON h.A_Depreciation_ID = l.A_Depreciation_ID "
				+"		WHERE h.DateAcct BETWEEN ? AND ? " //#7#8
				+"			AND l.Account_DR_ID IN ("+ strAcctDr +") "
				+"			AND l.C_TypeCost_ID IN (SELECT C_TypeCost_ID From C_TYpeCost WHERE IsManufactured = 'Y')"	
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	//#9
				+"		GROUP BY l.Account_Dr_ID, l.C_TypeCost_ID "
				+"	)B INNER JOIN C_TypeCost t ON B.C_TypeCost_ID = t.C_TypeCost_ID "
				+ " GROUP BY Account_ID, B.C_TypeCost_ID, t.MethodAllocation"
				+ " HAVING SUM(Amt) > 0 ";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		ps.setTimestamp(1, fromDate);
		ps.setTimestamp(2, toDate);
		ps.setInt(3, Env.getAD_Client_ID(getCtx()));
		
		ps.setTimestamp(4, fromDate);
		ps.setTimestamp(5, toDate);
		ps.setInt(6, Env.getAD_Client_ID(getCtx()));
		
		ps.setTimestamp(7, fromDate);
		ps.setTimestamp(8, toDate);
		ps.setInt(9, Env.getAD_Client_ID(getCtx()));
		
		rs = ps.executeQuery();
		BigDecimal amt;
		BigDecimal total = Env.ZERO;
		while (rs.next()) {
			amt = Env.ZERO;
			
			String MethodAllocation = rs.getString("MethodAllocation");
			
			//Số tiền cần phân bổ
			BigDecimal amtAllocation = rs.getBigDecimal("Amt");
			
			//Xử lý phân bổ theo nguyên vật liệu
			if (X_C_TypeCost.METHODALLOCATION_ByMaterial.equals(MethodAllocation)) {
				int i = 0;
				for(Integer key : listAmtMaterial.keySet()) {
					if (i == listAmtMaterial.size())
						amt = totalAmtMaterial.subtract(total);
					else
						amt = listAmtMaterial.get(key).divide(totalAmtMaterial).multiply(amtAllocation);
					data.put(key + "_" + rs.getInt("Account_ID"), amt);
					total = total.add(amt);
					i++;
				}
			}
			//End NVL
			
			//Xử lý phân bổ Theo nhân công
			if (X_C_TypeCost.METHODALLOCATION_ByWorker.equals(MethodAllocation)) {
				int i = 0;
				for(Integer key : listAmtWorker.keySet()) {
					if (i == listAmtWorker.size())
						amt = totalAmtWorker.subtract(total);
					else
						amt = listAmtWorker.get(key).divide(totalAmtWorker).multiply(amtAllocation);
					data.put(key + "_" + rs.getInt("Account_ID"), amt);
					total = total.add(amt);
					i++;
				}
			}//End NC
			
		}
		DB.close(rs, ps);
		
		//Đưa dữ liệu sau khi phân bổ xong vào DB
		MInOutLine line = null;
		for (String key : data.keySet()) {
			BigDecimal amtLine = data.get(key);
			line = new MInOutLine(inout);
			String [] ls = key.split("_");
			int M_Product_ID = 0;
			if (ls[0] != null)
				M_Product_ID = Integer.parseInt(ls[0].toString());
			
			int Account_Cr_ID = 0;
			if (ls[1] != null) 
				Account_Cr_ID = Integer.parseInt(ls[1].toString());
			
			int M_Warehouse_ID = 0;
			if (objProduction.containsKey(M_Product_ID))
				M_Warehouse_ID = objProduction.get(M_Product_ID);
			
			//Tính tiền cho từng sản phẩm, phục vụ tính giá ở bước 4.
			if (lsProduct_Amount.containsKey(M_Product_ID)) {
				lsProduct_Amount.put(M_Product_ID, lsProduct_Amount.get(M_Product_ID).add(amtLine));
			} else {
				lsProduct_Amount.put(M_Product_ID, amtLine);
			}
			line.setAccount_Cr_ID(Account_Cr_ID);
			if (listAcct.containsKey(Account_Cr_ID))
				line.setAccount_Dr_ID(listAcct.get(Account_Cr_ID).getAccount_Dr_ID());
			
			line.setDescription("Allocation");
			
			line.setM_Product_Cr_ID(M_Product_ID);
			line.setM_Product_ID(M_Product_ID);
			line.setAmount(amtLine);
			line.setM_Warehouse_Dr_ID(M_Warehouse_ID);
			line.setM_Warehouse_Cr_ID(M_Warehouse_ID);
			line.save(get_TrxName());
		}
		
	}
	
	
	
	public List<Integer> getAcctByMethodAllocation (String method) {
		String whereClause = "";
		int C_Element_ID = Env.getContextAsInt(getCtx(), "#C_Element_ID");
		if (C_Element_ID == 105) {
			if (X_C_TypeCost.METHODALLOCATION_ByMaterial.equals(method))
				whereClause = " Value LIKE '621%' AND C_Element_ID = " + C_Element_ID;
			if (X_C_TypeCost.METHODALLOCATION_ByWorker.equals(method))
				whereClause = " Value LIKE '622%' AND C_Element_ID = " + C_Element_ID;
		} else {
			if (X_C_TypeCost.METHODALLOCATION_ByMaterial.equals(method))
				whereClause = "( Value LIKE '152%' OR Value like '153%') AND C_Element_ID != " + C_Element_ID;
			if (X_C_TypeCost.METHODALLOCATION_ByWorker.equals(method))
				whereClause = " Value LIKE '334%' AND C_Element_ID != " + C_Element_ID;
		}
		
		List<MElementValue> retValue = new Query(getCtx(), X_C_ElementValue.Table_Name, whereClause, get_TrxName(), true)
				.list();
		List<Integer> arr = new ArrayList<Integer>();
		for(int i = 0; i < retValue.size(); i++) {
			arr.add(retValue.get(i).getC_ElementValue_ID());
		}
		return arr;
	}
	
	/*
	 * Lấy toàn bộ chi phí.
	 * Nếu theo thông tư 200 thì ko cần phải lấy đối ứng.
	 * Nếu theo thông tư 133 thì phải lấy đối ứng. Vì bỏ qua hạch toán chi phí 621,622 mà lên thẳng 154 rồi
	 */
	public void processTransfer (MInOut inout) throws Exception {
		int C_Element_ID = Env.getContextAsInt(getCtx(), "#C_Element_ID");
		String sql = " 	SELECT SUM(Amt) Amt, Account_ID, M_Product_ID, M_Warehouse_ID " 
				+" 	FROM " 
				+"	( "
				+"		SELECT SUM(l.Amount) Amt, l.Account_Dr_ID Account_ID, "
				+ "			l.M_Product_ID, l.M_Warehouse_Dr_ID M_Warehouse_ID "
				+"		FROM M_InOut h INNER JOIN M_InOutLine l ON h.M_InOut_ID = l.M_InOut_ID "
				+"		WHERE h.DateAcct BETWEEN ? AND ? "
				+"			AND Account_DR_ID IN ("+ strAcctDr +") AND l.Amount > 0 "
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	
				+"			AND l.M_Product_ID IN (SELECT M_Product_ID From M_Product WHERE IsManufactured = 'Y')"
				+"		GROUP BY l.Account_Dr_ID, l.M_Product_ID, l.M_Warehouse_Dr_ID "
				+"		UNION ALL "
				+"		SELECT -SUM(l.Amount) Amt, l.Account_Cr_ID Account_ID, "
				+ "			l.M_Product_Cr_ID M_Product_ID, l.M_Warehouse_Cr_ID M_Warehouse_ID "
				+"		FROM M_InOut h INNER JOIN M_InOutLine l ON h.M_InOut_ID = l.M_InOut_ID "
				+"		WHERE h.DateAcct BETWEEN ? AND ? "
				+"			AND Account_CR_ID IN ("+ strAcctDr +") AND l.Amount > 0  "
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	
				+"			AND l.M_Product_ID IN (SELECT M_Product_ID From M_Product WHERE IsManufactured = 'Y')"
				+"		GROUP BY l.Account_Cr_ID, l.M_Product_Cr_ID, l.M_Warehouse_Cr_ID "
				+"	)B GROUP BY Account_ID, M_Product_ID, M_Warehouse_ID "
				+ "	HAVING SUM(Amt) > 0 ";
		
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		ps.setTimestamp(1, fromDate);
		ps.setTimestamp(2, toDate);
		ps.setInt(3, Env.getAD_Client_ID(getCtx()));
		
		ps.setTimestamp(4, fromDate);
		ps.setTimestamp(5, toDate);
		ps.setInt(6, Env.getAD_Client_ID(getCtx()));
	
		rs = ps.executeQuery();
		MInOutLine line = null;
		
		while (rs.next()) {
			int Account_Cr_ID = 0;
			BigDecimal amtLine = Env.ZERO;
			int M_Product_ID = rs.getInt("M_Product_ID");
			amtLine = rs.getBigDecimal("Amt");
			
			if (C_Element_ID == 105) {
				line = new MInOutLine(inout);
				Account_Cr_ID = rs.getInt("Account_ID");
				line.setAccount_Cr_ID(rs.getInt("Account_ID"));
				if (listAcct.containsKey(Account_Cr_ID))
					line.setAccount_Dr_ID(listAcct.get(Account_Cr_ID).getAccount_Dr_ID());
				line.setM_Product_Cr_ID(M_Product_ID);
				line.setM_Product_ID(M_Product_ID);
				line.setAmount(rs.getBigDecimal("Amt"));
				line.setDescription("Transfer");
				line.setM_Warehouse_Dr_ID(rs.getInt("M_Warehouse_ID"));
				line.setM_Warehouse_Cr_ID(rs.getInt("M_Warehouse_ID"));
				line.save(get_TrxName());
			}
			
			//Tính tiền cho từng sản phẩm, phục vụ tính giá ở bước 4.
			if (lsProduct_Amount.containsKey(M_Product_ID)) {
				lsProduct_Amount.put(M_Product_ID, lsProduct_Amount.get(M_Product_ID).add(amtLine));
			} else {
				lsProduct_Amount.put(M_Product_ID, amtLine);
			}
			
			
			//Kiểm tra tài khoản để add list và tổng tiền phục vụ phân bổ
			if (listAcctMaterial.contains(Account_Cr_ID)) {
				listAmtMaterial.put(Account_Cr_ID, amtLine);
				totalAmtMaterial = totalAmtMaterial.add(amtLine);
			}
			
			if (listAcctWorker.contains(Account_Cr_ID)) {
				listAmtWorker.put(Account_Cr_ID, amtLine);
				totalAmtWorker = totalAmtWorker.add(amtLine);
			}
		}
		
		DB.close(rs, ps);
		
		rs = null;
		ps = null;
	}
	
	private String checkDate() {

		String sql = "SELECT COUNT(1) FROM M_InOut WHERE DateAcct BETWEEN ? AND ? AND Processed = 'Y' AND C_DocType_ID = 200010";
		
		
		List<Object> params = new ArrayList<Object>();
		params.add(fromDate);
		params.add(toDate);
		int count = DB.getSQLValueEx(get_TrxName(), sql, params);
		
		if (count > 0)
			return "Đã tính giá rồi !";
		
		String sqlDel = "DELETE FROM M_InOutLine WHERE M_InOut_ID IN (SELECT M_InOut_ID FROM M_InOut WHERE DateAcct BETWEEN ? AND ? AND Processed = 'N')";
		Object [] obj = {fromDate, toDate};
		DB.executeUpdate(sqlDel, obj, true, get_TrxName());
		
		sqlDel = "DELETE FROM M_InOut WHERE DateAcct BETWEEN ? AND ? AND Processed = 'N'";
		DB.executeUpdate(sqlDel, obj, true, get_TrxName());
		
		return "";
	}
	
	
	//=================Lấy sản phẩm hoàn thành và sản phẩm dở dang để tính giá thành================
	
	public void getQtyFinish() {
		String sql = "SELECT SUM(Qty) Qty, M_Product_Cr_ID "
				+ "	FROM M_InOutLine l INNER JOIN M_InOut i ON i.M_InOut_ID = l.M_InOut_ID "
				+ "	WHERE i.DateAcct BETWEEN ? AND ? AND i.AD_Client_ID = ? "
				+ "		AND l.Account_Cr_ID IN (SELECT C_ElementValue_ID FROM C_ElementValue WHERE Value like '154%')"
				+ "		AND NVL(l.Amount,0) = 0 "
				+ "		AND l.M_Product_Cr_ID IN (SELECT M_Product_ID FROM M_Product WHERE IsManufactured = 'Y')"
				+ "	GROUP BY M_Product_Cr_ID";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		try {
			ps.setTimestamp(1, fromDate);
			ps.setTimestamp(2, toDate);
			ps.setInt(3, Env.getAD_Client_ID(getCtx()));
			rs = ps.executeQuery();
			while (rs.next()) {
				listQtyFinish.put(rs.getInt("M_Product_Cr_ID"), rs.getInt("Qty"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}
		
	}
	
	//Lấy sản phẩm đối ứng => Xác định giá vốn của px tiếp theo.
	public void getProduct_DU() {
		String sql = "SELECT M_Product_ID, M_Product_Cr_ID, Qty "
				+ "	FROM M_InOutLine l INNER JOIN M_InOut i ON i.M_InOut_ID = l.M_InOut_ID "
				+ "	WHERE i.DateAcct BETWEEN ? AND ? AND i.AD_Client_ID = ? "
				+ "		AND l.Account_Dr_ID IN (SELECT C_ElementValue_ID FROM C_ElementValue WHERE Value like '154%')"
				+ "		AND l.Account_Cr_ID IN (SELECT C_ElementValue_ID FROM C_ElementValue WHERE Value like '154%')"
				+ "		AND NVL(l.Amount,0) = 0 "
				+ "		AND l.M_Product_Cr_ID IN (SELECT M_Product_ID FROM M_Product WHERE IsManufactured = 'Y')"
				+ "	GROUP BY M_Product_ID, M_Product_Cr_ID";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		try {
			ps.setTimestamp(1, fromDate);
			ps.setTimestamp(2, toDate);
			ps.setInt(3, Env.getAD_Client_ID(getCtx()));
			rs = ps.executeQuery();
			while (rs.next()) {
				ArrayList<Integer> arr = new ArrayList<Integer>();
				arr.add(rs.getInt("M_Product_ID"));
				arr.add(rs.getInt("Qty"));
				listProduct_DU.put(rs.getInt("M_Product_Cr_ID"), arr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}
		
	}
	
	//Lấy sản phẩm chưa hoàn thành
	public void getQtyUnFinish() {
		String sql = "SELECT NVL(QtyUnFinish,0) * NVL(RateFinish,0) QtyFinish, M_Product_Cr_ID "
				+ "	FROM M_InOutLine l INNER JOIN M_InOut i ON i.M_InOut_ID = l.M_InOut_ID "
				+ "	WHERE i.DateAcct BETWEEN ? AND ? AND i.AD_Client_ID = ? "
				+ "		AND l.Account_Cr_ID IN (SELECT C_ElementValue_ID FROM C_ElementValue WHERE Value like '154%')"
				+ "		AND NVL(l.Amount,0) = 0 "
				+ "		AND l.M_Product_Cr_ID IN (SELECT M_Product_ID FROM M_Product WHERE IsManufactured = 'Y')";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		try {
			ps.setTimestamp(1, fromDate);
			ps.setTimestamp(2, toDate);
			ps.setInt(3, Env.getAD_Client_ID(getCtx()));
			rs = ps.executeQuery();
			while (rs.next()) {
				listQtyUnFinish.put(rs.getInt("M_Product_Cr_ID"), rs.getInt("QtyFinish"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}
	}
	//=================Kết thúc Lấy sản phẩm hoàn thành và sản phẩm dở dang để tính giá thành================
	
	//=================Lấy tồn đầu kỳ của 154.
	
	public void getAmtPeriodPrevious() {
		String sql = " 	SELECT SUM(Amt) Amt, M_Product_ID " 
				+" 	FROM " 
				+"	( "
				+"		SELECT SUM(l.Amount) Amt, l.M_Product_ID "
				+"		FROM M_InOut h INNER JOIN M_InOutLine l ON h.M_InOut_ID = l.M_InOut_ID "
				+"		WHERE h.DateAcct < ? "
				+"			AND Account_DR_ID LIKE '154%' AND l.Amount > 0 "
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	
				+"		GROUP BY l.M_Product_ID "
				+"		UNION ALL "
				+"		SELECT -SUM(l.Amount) Amt, l.M_Product_Cr_ID M_Product_ID "
				+"		FROM M_InOut h INNER JOIN M_InOutLine l ON h.M_InOut_ID = l.M_InOut_ID "
				+"		WHERE h.DateAcct < ? "
				+"			AND Account_CR_ID LIKE '154%' AND l.Amount > 0  "
				+"			AND h.Processed = 'Y' AND h.AD_Client_ID = ?"	
				+"		GROUP BY l.M_Product_Cr_ID "
				+"	)B GROUP BY M_Product_ID "
				+ "	HAVING SUM(Amt) > 0 ";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		try {
			ps.setTimestamp(1, fromDate);
			ps.setInt(2, Env.getAD_Client_ID(getCtx()));
			ps.setTimestamp(3, fromDate);
			ps.setInt(4, Env.getAD_Client_ID(getCtx()));
			rs = ps.executeQuery();
			while (rs.next()) {
				lsPeriodPrevious.put(rs.getInt("M_Product_ID"), rs.getBigDecimal("Amt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}
		
	}
	
	//obj.add(new BigDecimal(qtyFinish));
	//obj.add(amtFinal.divide(new BigDecimal(qtyFinish)));
	//obj.add(amtFinal);
	public void updatePrice() throws Exception {
		String sqlUpdate = "UPDATE M_InOutLine SET Price = ?, Amount = ? * Qty "
				+ " WHERE (M_Product_ID = ? OR M_Product_Cr_ID = ?) "
				+ "		AND M_InOut_ID IN (SELECT M_InOut_ID FROM M_InOut WHERE DateAcct BETWEEN ? AND ?)"
				+ "		AND AD_Client_ID = ? ";
		String sqlFact = "UPDATE Fact_Acct SET Price = ?, Amount = ? * Qty, AmountConvert = ? * Qty"
				+ "	WHERE (M_Product_ID = ? OR M_Product_Cr_ID = ?) "
				+ "		AND DateAcct BETWEEN ? AND ? AND AD_Client_ID = ?"
				+ "		AND NVL(Amount,0) = 0";
		List<Object> lineInOut = null;
		List<Object> lineFact = null;
		List<List<Object>> listLineInOut = new ArrayList<List<Object>>();
		List<List<Object>> listLineFact = new ArrayList<List<Object>>();
		for(int key : lsPriceProduct.keySet()) {
			
			ArrayList<BigDecimal> data = lsPriceProduct.get(key);
			BigDecimal price = data.get(1);
			
			lineInOut = new ArrayList<Object>();
			lineInOut.add(price);
			lineInOut.add(price);
			lineInOut.add(key);
			lineInOut.add(key);
			lineInOut.add(fromDate);
			lineInOut.add(toDate);
			lineInOut.add(Env.getAD_Client_ID(getCtx()));
			listLineInOut.add(lineInOut);
			
			lineFact = new ArrayList<Object>();
			lineFact.add(price);
			lineFact.add(price);
			lineFact.add(price);
			lineFact.add(key);
			lineFact.add(key);
			lineFact.add(fromDate);
			lineFact.add(toDate);
			lineFact.add(Env.getAD_Client_ID(getCtx()));
			listLineFact.add(lineFact);
		}
		
		if (listLineInOut.size() > 0) {
			String error = DB.excuteBatch(sqlUpdate, listLineInOut, get_TrxName());
			if (error != null) {
				DB.rollback(true, get_TrxName());
			}
			listLineInOut.clear();
		}
		
		if (listLineFact.size() > 0) {
			String error = DB.excuteBatch(sqlFact, listLineFact, get_TrxName());
			if (error != null) {
				DB.rollback(true, get_TrxName());
			}
			listLineFact.clear();
		}
	}
}

