
package eone.base.model;

import java.math.BigDecimal;
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


public class MStorage extends X_M_Storage
{
	private static final long serialVersionUID = 7980515458720808532L;

	private static String MaterialPolicy = "";

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
		MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
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
		
		if (X_M_Storage.MMPOLICY_None.equals(MaterialPolicy)) {
			return;
		}
		
		List<Object> lstColumn = new ArrayList<Object>();
		List<List<Object>> lstRows = new ArrayList<List<Object>>();
		String sqlInsert = MStorage.sqlInsert;
		int BATCH_SIZE = Env.getBatchSize(ctx);
		String sql = "";
		MDocType mDoctype = MDocType.get(ctx, C_DocType_ID);
		String doctype = mDoctype.getDocType();
		
		//Type nhap so du dau ky
		if (MDocType.DOCTYPE_Balance.equals(doctype)) {
			sql = " select 'OP' DocType, il.M_InOutLine_ID, i.M_Warehouse_Dr_ID M_Warehouse_ID, i.DateAcct, il.M_Product_ID, il.Qty, il.PricePO, il.Amount "+
					" From M_InOut i Inner Join M_InOutLine il On i.M_InOut_ID = il.M_InOut_ID "+
					" Where i.M_InOut_ID = ? ";
		}
		
		//Type nhap
		if (MDocType.DOCTYPE_Input.equals(doctype)) {
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
					storage.setPrice(rs.getBigDecimal("PricePO"));
					storage.setAmount(rs.getBigDecimal("Amount"));
					storage.setTypeInOut(docType);
					storage.setMMPolicy(MaterialPolicy);
					storage.setDateTrx(rs.getTimestamp("DateAcct"));
					int ID = DB.getNextID(MStorage.Table_Name, trxName);
					storage.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
					storage.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
					
					List<String> colNames = PO.getSqlInsert_Para(MStorage.Table_ID, trxName);
					lstColumn = PO.getBatchValueList(storage, colNames, MStorage.Table_ID, trxName, ID);
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
			
		} else { //RA
			sql = "Delete M_Storage Where Record_ID  = ? ";
			DB.executeUpdate(sql, Record_ID, trxName);
		}
	}
	
	//private static CCache<Integer,MStorage> s_cache	= new CCache<Integer,MStorage>(Table_Name, 40, CCache.DEFAULT_TIMEOUT);

	/*
	 * DateTrx: 		Ngay phat sinh giao dich => Lay ngay tu bang M_InOut
	 * Qty: 			So luong giao dich => Lay bang M_InOutLine
	 * RemainQty: 		So luong con lai => Tu dong tinh
	 * TypeInOut: 		Trang thai nhap hay xuat => Tu dong xac dinh
	 * Record_ID: 		Tuong ung truong M_InoutLine_ID
	 * 
	 */
	
	/*
	 * Ham nay check ton kho cua vat tu tai ngay xuat (ko phai ton kho tai thoi diem hien tai)
	 * Luu y:	Neu khong cau hinh check ton kho thi bo qua.
	 */
	public static BigDecimal getQtyRemain(int M_Product_ID, int M_Warehouse_ID, Timestamp DateTrx) {
		List<Object> params = new ArrayList<Object>();
		params.add(Env.getAD_Client_ID(Env.getCtx()));
		Timestamp startDate = DB.getSQLValueTS(null, "Select Max(StartDate) From C_Year Where StatusBalance = 'Y' And AD_Client_ID = ?", params);
		
		if (startDate == null) {
			startDate = TimeUtil.getDay(2000, 1, 1);
		}
		String sql = 
				" Select Sum(Qty) Qty"+
				" From "+
				" ( "+
				" 	select Sum(Qty) Qty, Sum(Amount) Amount from M_Storage "+ 
				" 	Where (TypeInOut in ('IN','OP')) And DateTrx >= ? And DateTrx <= ? And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#1,#2,#3,#4
				" 	Union All "+
				" 	select -Sum(Qty) Qty, -Sum(Amount) Amount from M_Storage "+ 
				" 	Where TypeInOut = 'OU' And DateTrx >= ? And DateTrx <= ?  And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#5,#6,#7,#8
				" )B Having Sum(Qty) != 0";
		Object [] param = new Object[] {startDate, DateTrx, M_Warehouse_ID, M_Product_ID, startDate, DateTrx, M_Warehouse_ID, M_Product_ID};
		BigDecimal qty =  DB.getSQLValueBD(null, sql, param);
		if (qty == null)
			qty = Env.ZERO;
		return qty;
	}
	
	
	/**
	 * Hàm này lấy đơn giá và số lượng. 
	 * Nếu tính bình quân thì trả về kết quả 1 dòng.
	 * Nếu tính FIFO thì trả về số dòng có tổng bằng số lượng người dùng nhập.
	 * 
	 * @return
	 */
	
	public static List<List<Object>> getQtyPrice(int M_Product_ID, int M_Warehouse_ID, Timestamp DateTrx, String trxName) {
		
		//Lấy ngày chốt dữ liệu số dư. Mục đích cho phạm vi quét dữ liệu ít đi.
		String sqlSelect = "Select Max(StartDate) From C_Year Where StatusBalance = 'Y' And AD_Client_ID = ?";
		Timestamp startDate = DB.getSQLValueTS(trxName, sqlSelect, new Object [] {Env.getAD_Client_ID(Env.getCtx())});
		
		if (startDate == null) {
			startDate = TimeUtil.getDay(2000, 1, 1);
		}
		
		String sql = "";
		
		
		String MaterialPolicy = Env.getMaterialPolicy(Env.getCtx());
		
		//Binh quan cuoi ky
		if (MClient.MMPOLICY_Average.equals(MaterialPolicy))
		{
			sql = 
				" Select Sum(Qty) Qty, round(Sum(Amount)/Sum(Qty),4) Price, Sum(Amount) Amount"+
				" From "+
				" ( "+
				" 	select Sum(Qty) Qty, Sum(Amount) Amount from M_Storage "+ 
				" 	Where (TypeInOut in ('IN','OP')) And DateTrx >= ? And DateTrx <= ? And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#1,#2,#3,#4
				" 	Union All "+
				" 	select -Sum(Qty) Qty, -Sum(Amount) Amount from M_Storage "+ 
				" 	Where TypeInOut = 'OU' And DateTrx >= ? And DateTrx <= ?  And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#5,#6,#7,#8
				" )B Having Sum(Qty) != 0";
			Object [] params = new Object[] {startDate, DateTrx, M_Warehouse_ID, M_Product_ID, startDate, DateTrx, M_Warehouse_ID, M_Product_ID};
			return DB.getSQLArrayObjectsEx(trxName, sql, params);
			
			
		}//End binh quan
		
		//FIFO
		if (MClient.MMPOLICY_FiFo.equals(MaterialPolicy))
		{//Phan nay se lam sau (khi nao co don vi ap dung thi lam vi no phuc tap)
			sql = 
					" Select Sum(Qty) Qty, round(Sum(Amount)/Sum(Qty),4) Price, Sum(Amount) Amount"+
					" From "+
					" ( "+
					" 	select Sum(Qty) Qty, Sum(Amount) Amount from M_Storage "+ 
					" 	Where (TypeInOut in ('IN','OP')) And DateTrx >= ? And DateTrx <= ? And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#1,#2,#3,#4
					"	Group by "+
					" 	Union All "+
					" 	select -Sum(Qty) Qty, -Sum(Amount) Amount from M_Storage "+ 
					" 	Where TypeInOut = 'OU' And DateTrx >= ? And DateTrx <= ?  And M_Warehouse_ID = ? And M_Product_ID = ? "+ //#5,#6,#7,#8
					" )B Having Sum(Qty) != 0";
				Object [] params = new Object[] {startDate, DateTrx, M_Warehouse_ID, M_Product_ID, startDate, DateTrx, M_Warehouse_ID, M_Product_ID};
				return DB.getSQLArrayObjectsEx(trxName, sql, params);
			
		}//End FIFO
		
		//LIFO
		if (MClient.MMPOLICY_LiFo.equals(MaterialPolicy))
		{//Phần này chắc không dùng
			
		}//End LIFO
		
		return null;
	}
	
	/*
	 * Hàm này dùng để chốt số dư đầu kỳ theo thời gian được cấu hình trong bảng C_Year đối với trường hợp là tính bình quân
	 */
	public static void insertOpenBalance(Timestamp startDate, Timestamp endDate, Properties ctx, String trxName) {
		
		if (X_M_Storage.MMPOLICY_None.equals(MaterialPolicy)) {
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
				storage.setTypeInOut(MStorage.TYPEINOUT_Opening);
				storage.setMMPolicy(X_M_Storage.MMPOLICY_Average);
				storage.setDateTrx(endDate);
				int ID = DB.getNextID(MStorage.Table_Name, trxName);
				storage.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				storage.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				
				List<String> colNames = PO.getSqlInsert_Para(MStorage.Table_ID, trxName);
				lstColumn = PO.getBatchValueList(storage, colNames, MStorage.Table_ID, trxName, ID);
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

