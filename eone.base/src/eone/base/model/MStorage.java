
package eone.base.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.util.DB;
import eone.util.Env;
import eone.util.TimeUtil;

/**
 * 
 * @author Client
 * Phương pháp lưu:
 * 1. Giá bình quân: Đẩy cả nhập và xuất vào với TypeInOut là IN và OT. Khi chốt số dư cho từng năm thì TypeInOut = OP
 * 2. Giá FIFO và LIFO: 
 * 		TypeInOut = OP: KHông có
 * 		TypeInOut = IN: Nhập
 * 		TypeInOut = OT: Không có.
 * 		=> Đối với xuất thì căn cứ vào FIFO hay LIFO để Order DateTrx và update số lượng từng ngày nhập tương ứng.
 *
 */

public class MStorage extends X_M_Storage
{
	private static final long serialVersionUID = 7980515458720808532L;

	private static String MaterialPolicy = "";
	private static int BATCH_SIZE = Env.getBatchSize(Env.getCtx());

	public static MStorage get (Properties ctx)
	{
		final String whereClause = "1=1";
		MStorage retValue =  new Query(ctx,I_M_Storage.Table_Name,whereClause,null)
		.first();
		return retValue;
	}	//	get
	
	//private static CLogger		s_log = CLogger.getCLogger (MAccount.class);
	
	public static String sqlInsert = PO.getSqlInsert(X_M_Storage.Table_ID, null);
	public static String sqlUpdate = PO.getSqlUpdate(X_M_Storage.Table_ID);

	public MStorage (Properties ctx, int M_Storage_ID, String trxName)
	{
		super (ctx, M_Storage_ID, trxName);
	}   //  MAccount


	public MStorage (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}   //  MAccount


	public MStorage ()
	{
		this (Env.getCtx(), 0, null);
		
	}	//	Account


	public String toString()
	{
		return "";
	}	//	toString

	
	
	protected boolean beforeSave (boolean newRecord)
	{
		
		return true;
	}	//	beforeSave
	
	/**
	 * Insert hoặc Delete dữ liệu khi có nghiệp vụ phát sinh.
	 * Trong hàm này cần xác định theo phương pháp tính giá nào để Insert cho đúng.
	 * 
	 * Với Average: Insert các dòng phát sinh
	 * 
	 * Với FIFO: Insert các dòng nhập, các dòng xuất thì update lần lượt số lượng xuất từ cũ đến mới.
	 * 
	 * Với LIFO: Insert các dòng nhập, các dòng xuất thì update lần lượt số lượng từ mới về cũ (Phần này làm sau)
	 * 
	 * @param isComplete
	 * @param ctx
	 * @param M_Product_ID
	 * @param Record_ID
	 * @param C_DocType_ID
	 * @param trxName
	 */
	public static void insertOrUpdateStorage(boolean isComplete, Properties ctx, int Record_ID, int C_DocType_ID, String trxName) {
		
		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		if (MMPOLICY_None.equals(MaterialPolicy)) {
			return;
		}
		
		
		String sql = "";
		MDocType mDoctype = MDocType.get(ctx, C_DocType_ID);
		String doctype = mDoctype.getDocType();
		
		//Type nhap hoặc đầu kỳ
		if (MDocType.DOCTYPE_Input.equals(doctype) || MDocType.DOCTYPE_Balance.equals(doctype)) {
			sql = " select 'IN' DocType, il.M_InOutLine_ID, i.M_Warehouse_Dr_ID M_Warehouse_ID, i.DateAcct, il.M_Product_ID, il.Qty, il.PricePO, il.Amount "+
						" From M_InOut i Inner Join M_InOutLine il On i.M_InOut_ID = il.M_InOut_ID "+
						" Where i.M_InOut_ID = ? ";
		}
		
		
		if (MDocType.DOCTYPE_Output.equals(doctype)) {
			sql = " select 'OU' DocType, il.M_InOutLine_ID, i.M_Warehouse_Cr_ID M_Warehouse_ID, i.DateAcct, il.M_Product_ID, il.Qty, il.PricePO, il.Amount "+
					" From M_InOut i Inner Join M_InOutLine il On i.M_InOut_ID = il.M_InOut_ID "+
					" Where i.M_InOut_ID = ? ";
		}
		
		
		if (isComplete) {//CO
			if (MDocType.DOCTYPE_Output.equals(doctype)) {
				if (MaterialPolicy.equals(MMPOLICY_LiFo) 
						|| MaterialPolicy.equals(MMPOLICY_FiFo))
				{
					completeFIFO_LIFO(ctx, sql, Record_ID, trxName);
				}
				else
				{
					insertStorage (ctx, sql, Record_ID, trxName);
				}
			} else {
				insertStorage (ctx, sql, Record_ID, trxName);
			}
			
			
		} else { //RA
			if (MaterialPolicy.equals(MMPOLICY_Average)) {
				sql = "Delete M_Storage Where Record_ID  = ? ";
				DB.executeUpdate(sql, Record_ID, trxName);
			} else {
				reActivateFIFO_LIFO(ctx, Record_ID, trxName);
			}
		}
	}
	
	public static void insertStorage (Properties ctx, String sql, int Record_ID, String trxName) {

		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		List<Object> lstColumn = new ArrayList<Object>();
		List<List<Object>> lstRows = new ArrayList<List<Object>>();
		
		ResultSet rs = null;
		PreparedStatement ps = DB.prepareCall(sql);
		try {
			ps.setInt(1, Record_ID);
			rs = ps.executeQuery();
			MStorage storage = null;
			while (rs.next()) {
				lstColumn = new ArrayList<Object>();
				String docType = rs.getString("DocType");
				storage = new MStorage(ctx, 0, trxName);
				storage.setRecord_ID(Record_ID);
				storage.setLine_ID(rs.getInt("M_InOutLine_ID"));
				storage.setM_Product_ID(rs.getInt("M_Product_ID"));
				storage.setM_Warehouse_ID(rs.getInt("M_Warehouse_ID"));
				storage.setQty(rs.getBigDecimal("Qty"));
				storage.setAccumulateQty(Env.ZERO);
				storage.setPrice(rs.getBigDecimal("PricePO"));
				storage.setAmount(rs.getBigDecimal("Amount"));
				storage.setTypeInOut(docType);
				storage.setMMPolicy(MaterialPolicy);
				storage.setDateTrx(rs.getTimestamp("DateAcct"));
				int ID = DB.getNextID(Table_Name, trxName);
				storage.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				storage.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				
				List<String> colNames = PO.getSqlInsert_Para(Table_ID, trxName);
				lstColumn = PO.getBatchValueList(storage, colNames, Table_ID, trxName, ID);
				lstRows.add(lstColumn);
				if (lstRows.size() >= BATCH_SIZE) {
					DB.excuteBatch(sqlInsert, lstRows, trxName);
					lstRows.clear();
				}
			}
			if (lstRows.size() > 0) {
				DB.excuteBatch(sqlInsert, lstRows, trxName);
				lstRows.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Hàm này để cập nhật số lượng xuất vào cột AccumulateQty khi CO
	 * @param ctx
	 * @param sql
	 * @param Record_ID
	 * @param trxName
	 */
	public static void completeFIFO_LIFO(Properties ctx, String sql, int Record_ID, String trxName) {
		
		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		//Lấy dữ liệu đã nhập đối với LIFO và FIFO
		String sqlUpdate = "UPDATE M_Storage SET AccumulateQty = ?, Note = ? WHERE M_Storage_ID = ?";
		List<List<Object>> values = new ArrayList<List<Object>>();
		List<Object> line = null;
		String whereClause = " NVL(Qty,0) - NVL(AccumulateQty,0) > 0 AND EXISTS (SELECT 1 FROM M_InOutLine WHERE M_Storage.M_Product_ID = M_InOutLine.M_Product_ID AND M_InOut_ID = ?)";
		List<MStorage> list = null;
		if (MMPOLICY_FiFo.equals(MaterialPolicy))
			list = new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(Record_ID)
				.setOrderBy(" M_Product_ID, DateTrx ASC")
				.list();
		else
			list = new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(Record_ID)
				.setOrderBy(" M_Product_ID, DateTrx DESC")
				.list();
		
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		try {
			ps.setInt(1, Record_ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				int M_Product_ID = rs.getInt("M_Product_ID");
				BigDecimal QtyTotal = rs.getBigDecimal("Qty");
				for(int i = 0; i < list.size(); i ++) {
					if (M_Product_ID == list.get(i).getM_Product_ID()) {
						line = new ArrayList<Object>();
						String note = list.get(i).getNote();
						if (note == null) 
							note = "0:0";
						
						BigDecimal accumulateQty = list.get(i).getAccumulateQty();
						if( accumulateQty == null)
							accumulateQty = Env.ZERO;
						
						note = note + "@" + Record_ID + ":" + accumulateQty;
						
						QtyTotal = QtyTotal.add(accumulateQty);
						if (QtyTotal.compareTo(list.get(i).getQty()) > 0) {
							line.add(list.get(i).getQty());
							QtyTotal = QtyTotal.subtract(list.get(i).getQty());
						} else {
							line.add(QtyTotal);
							QtyTotal = Env.ZERO;
						}
						//Hai trường này để đánh dấu khi RA
						line.add(note);
						
						line.add(list.get(i).getM_Storage_ID());
						
						values.add(line);
						if (QtyTotal.compareTo(Env.ZERO) == 0) {
							break;
						}
					}
				}
				if (values.size() >= BATCH_SIZE) {
					String err = DB.excuteBatch(sqlUpdate, values, trxName);
					if (err != null) {
						DB.rollback(true, trxName);
					}
					values.clear();
				}
			}
			if (values.size() > 0) {
				String err = DB.excuteBatch(sqlUpdate, values, trxName);
				if (err != null) {
					DB.rollback(true, trxName);
				}
				values.clear();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			DB.close(rs, ps);
		}
	}
	
	public static void reActivateFIFO_LIFO(Properties ctx, int Record_ID, String trxName) {
		String whereClause = "STRPOS(Note, '@"+ Record_ID +":') > 0";
		List<MStorage> list = new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(Record_ID)
				.list();
		String sqlUpdate = "UPDATE M_Storage SET AccumulateQty = ? WHERE M_Storage_ID = ?";
		List<List<Object>> values = new ArrayList<List<Object>>();
		List<Object> line = null;
		for(int i = 0; i < list.size(); i++) {
			line = new ArrayList<Object>();
			String [] arr = list.get(i).getNote().split("@");
			BigDecimal QtyOld = Env.ZERO;
			for(int j = 0; j < arr.length; j ++) {
				if (arr[j].contains(""+Record_ID)) {
					String [] arrLine = arr[j].split(":");
					if (arrLine[1] != null) {
						QtyOld = new BigDecimal(arrLine[1]);						
					}
					break;
				}
			}
			line.add(QtyOld);
			line.add(list.get(i).getM_Storage_ID());
			values.add(line);
		}
		String err = DB.excuteBatch(sqlUpdate, values, trxName);
		if (err != null) {
			try {
				DB.rollback(false, trxName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		values.clear();
	}
	
	/*
	 * Ham nay check ton kho cua vat tu tai ngay xuat (ko phai ton kho tai thoi diem hien tai)
	 * Luu y:	Neu khong cau hinh check ton kho thi bo qua.
	 */
	public static BigDecimal getQtyRemain(int M_Product_ID, int M_Warehouse_ID, Timestamp DateTrx) {
		
		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		String sql = "";
		Object [] param = null;
		BigDecimal qty = Env.ZERO;
		if (MaterialPolicy.equals(MMPOLICY_Average)) {
			List<Object> params = new ArrayList<Object>();
			params.add(Env.getAD_Client_ID(Env.getCtx()));
			Timestamp startDate = DB.getSQLValueTS(null, "Select Max(StartDate) From C_Year Where StatusBalance = 'Y' And AD_Client_ID = ?", params);
			
			if (startDate == null) {
				startDate = TimeUtil.getDay(2021, 1, 1);
			}
			sql = " Select Sum(Qty) Qty"+
				" From "+
				" ( "+
				" 	select Sum(Qty) Qty, Sum(Amount) Amount from M_Storage "+ 
				" 	Where (TypeInOut in ('IN','OP')) And DateTrx >= ? And DateTrx <= ? And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#1,#2,#3,#4
				" 	Union All "+
				" 	select -Sum(Qty) Qty, -Sum(Amount) Amount from M_Storage "+ 
				" 	Where TypeInOut = 'OU' And DateTrx >= ? And DateTrx <= ?  And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#5,#6,#7,#8
				" )B Having Sum(Qty) != 0";
			param = new Object[] {startDate, DateTrx, M_Warehouse_ID, M_Product_ID, startDate, DateTrx, M_Warehouse_ID, M_Product_ID};
			
		} else {
			//FIFO và LIFO
			sql = "SELECT SUM(NVL(Qty,0) - NVL(AccumulateAmt,0)) FROM M_Storage "+
				" WHERE M_Product_ID = ? AND NVL(Qty,0) > NVL(AccumulateAmt,0) AND M_Warehouse_ID = ?";
			param = new Object [] {M_Product_ID, M_Warehouse_ID};
		}
		
		qty =  DB.getSQLValueBD(null, sql, param);
		if (qty == null)
			qty = Env.ZERO;
		return qty;
		
	}
	
	
	/**
	 * Hàm này lấy đơn giá tại thời điểm xuất vật tư hàng hóa cho các phương pháp tính giá 
	 * 
	 */
	
	public static BigDecimal getPriceStorage(int M_Product_ID, int M_Warehouse_ID, Timestamp DateTrx, String trxName, BigDecimal Qty) {
		
		if (Qty.compareTo(Env.ZERO) == 0) {
			return Env.ZERO;
		}
		String sql = "";
		
		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		//Lấy giá tiền và số lượng: Binh quan cuoi ky
		if (MMPOLICY_Average.equals(MaterialPolicy))
		{
			//Lấy ngày chốt dữ liệu số dư. Mục đích cho phạm vi quét dữ liệu ít đi.
			String sqlSelect = "Select Max(StartDate) From C_Year Where StatusBalance = 'Y' And AD_Client_ID = ?";
			Timestamp startDate = DB.getSQLValueTS(trxName, sqlSelect, new Object [] {Env.getAD_Client_ID(Env.getCtx())});
			
			if (startDate == null) {
				startDate = TimeUtil.getDay(2000, 1, 1);
			}
			
			sql = 
				" Select Sum(Qty) Qty, Sum(Amount) Amount"+
				" From "+
				" ( "+
				" 	select Sum(Qty) Qty, Sum(Amount) Amount from M_Storage "+ 
				" 	Where (TypeInOut in ('IN','OP')) And DateTrx >= ? And DateTrx <= ? And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#1,#2,#3,#4
				" 	Union All "+
				" 	select -Sum(Qty) Qty, -Sum(Amount) Amount from M_Storage "+ 
				" 	Where TypeInOut = 'OU' And DateTrx >= ? And DateTrx <= ?  And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#5,#6,#7,#8
				" )B Having Sum(Qty) != 0";
			Object [] params = new Object[] {startDate, DateTrx, M_Warehouse_ID, M_Product_ID, startDate, DateTrx, M_Warehouse_ID, M_Product_ID};
			Object [] data = DB.getObjectValuesEx(trxName, null, 2, params);
			if (data != null) {
				BigDecimal qty = Env.ZERO;
				BigDecimal amt = Env.ZERO;
				if (data[0] != null)
					qty = new BigDecimal(data[0].toString());
				if (data[1] != null)
					amt = new BigDecimal(data[1].toString());
				if (qty == null) {
					qty = Env.ZERO;
				}
				if (qty.compareTo(Env.ZERO) > 0) 
					return amt.divide(qty, Env.getScalePrice(), RoundingMode.HALF_UP);
			}
			
		}//End binh quan
		
		//Lấy giá tiền và số lượng: FIFO và LIFO
		if (MMPOLICY_FiFo.equals(MaterialPolicy) || MMPOLICY_LiFo.equals(MaterialPolicy))
		{
			sql = "M_Product_ID = ? AND M_Warehouse_ID = ? AND NVL(Qty,0) >  NVL(AccumulateQty,0)";
			List<MStorage> list = null;
			//LIFO thì lấy theo ngày giảm dần còn FIFO thì lấy theo ngày tăng dần
			if (MMPOLICY_LiFo.equals(MaterialPolicy))
				list = new Query(Env.getCtx(), Table_Name, sql, trxName)
					.setParameters(M_Product_ID, M_Warehouse_ID)
					.setOrderBy("DateTrx DESC")
					.list();
			else
				list = new Query(Env.getCtx(), Table_Name, sql, trxName)
				.setParameters(M_Product_ID, M_Warehouse_ID)
				.setOrderBy("DateTrx ASC")
				.list();
			
			BigDecimal totalAmount = Env.ZERO;
		
			for(int i = 0; i < list.size(); i++) {
				BigDecimal priceLine = list.get(i).getPrice();
				BigDecimal qtyLine = Env.ZERO;
				BigDecimal remainQty = list.get(i).getQty().subtract(list.get(i).getAccumulateQty());
				if (remainQty == null) {
					remainQty = Env.ZERO;
				}
				if(Qty.compareTo(remainQty) > 0)
				{
					qtyLine = remainQty;
					Qty = Qty.subtract(remainQty);
				}else {
					qtyLine = Qty;
					Qty = Env.ZERO;
				}
				
				totalAmount = totalAmount.add(priceLine.multiply(qtyLine));
				
				if (Qty.compareTo(Env.ZERO) == 0) {
					break;
				}
			}
			return totalAmount.divide(Qty, Env.getScalePrice(), RoundingMode.HALF_UP);
		}
		return Env.ZERO;
	}
	
	/*
	 * Hàm này dùng để chốt số dư đầu kỳ theo thời gian được cấu hình trong bảng C_Year đối với trường hợp là tính bình quân
	 */
	/**
	 * 
	 * @param startDate: 01/01/Năm N. Năm N này đc xác định từ việc chốt số dư bảng C_Year.
	 * @param endDate: 31/12/Năm N. Năm N này đc xác định từ việc chốt số dư bảng C_Year.
	 * @param ctx
	 * @param trxName
	 */
	public static void insertOpenBalance(Timestamp startDate, Timestamp endDate, Properties ctx, String trxName) {
		
		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		if (MMPOLICY_None.equals(MaterialPolicy)) {
			return;
		}
		String sqlInsert = "";
		int BATCH_SIZE = 0;
		List<Object> lstColumn = new ArrayList<Object>();
		List<List<Object>> lstRows = new ArrayList<List<Object>>();
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		
		//TypeInOut: IN: Nhap; OP: Day ky; OU: XUAT
		String sql = 
				" Select Sum(Qty) Qty, Sum(Amount) Amount, "+
				"		Case when Sum(Qty) > 0 Then Sum(Amount)/Sum(Qty) Else 0 End Price, M_Product_ID, M_Warehouse_ID "+
				" From "+
				" ( "+
				" 	select Sum(Qty) Qty, Sum(Amount) Amount, M_Product_ID, M_Warehouse_ID from M_Storage "+ 
				" 	Where (TypeInOut = 'IN' Or TypeInOut = 'OP')  And AD_Client_ID = ? And DateTrx >= ? And DateTrx <= ? "+ //#1,#2,#3
				" 	Group by M_Product_ID, M_Warehouse_ID "+
				" 	Union All "+
				" 	select -Sum(Qty) Qty, -Sum(Amount) Amount, M_Product_ID, M_Warehouse_ID from M_Storage "+ 
				" 	Where TypeInOut = 'OU'  And AD_Client_ID = ? And DateTrx >= ? And DateTrx <= ? "+ //#4,#5,#6
				" 	Group by M_Product_ID, M_Warehouse_ID "+
				" )B Group by M_Product_ID, M_Warehouse_ID  "+
				" Having Sum(Qty) != 0";
		ResultSet rs = null;
		PreparedStatement ps = DB.prepareCall(sql);
		
		
		try {
			MStorage storage = null;
			ps.setInt(1, AD_Client_ID);
			ps.setTimestamp(2, startDate);
			ps.setTimestamp(3, endDate);
			
			ps.setInt(4, AD_Client_ID);
			ps.setTimestamp(5, startDate);
			ps.setTimestamp(6, endDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				lstColumn = new ArrayList<Object>();
				storage = new MStorage(ctx, 0, null);
				storage.setM_Product_ID(rs.getInt("M_Product_ID"));
				storage.setM_Warehouse_ID(rs.getInt("M_Warehouse_ID"));
				storage.setQty(rs.getBigDecimal("Qty"));
				storage.setPrice(rs.getBigDecimal("Price"));
				storage.setAmount(rs.getBigDecimal("Amount"));
				storage.setTypeInOut(TYPEINOUT_Opening);
				storage.setMMPolicy(MMPOLICY_Average);
				storage.setDateTrx(endDate);
				int ID = DB.getNextID(Table_Name, trxName);
				storage.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				storage.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				
				List<String> colNames = PO.getSqlInsert_Para(Table_ID, trxName);
				lstColumn = PO.getBatchValueList(storage, colNames, Table_ID, trxName, ID);
				lstRows.add(lstColumn);
				if (lstRows.size() >= BATCH_SIZE) {
					DB.excuteBatch(sqlInsert, lstRows, trxName);
					lstRows.clear();
				}
			}
			if (lstRows.size() > 0) {
				DB.excuteBatch(sqlInsert, lstRows, trxName);
				lstRows.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.close(rs, ps);
		}
		
		
	}
	
}

