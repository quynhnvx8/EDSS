package eone.base.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.DB;
import eone.util.Env;
import eone.util.TimeUtil;


public class MAssetBuild extends X_A_Asset_Build implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static MAssetBuild get (Properties ctx, int A_Asset_Build_ID, String trxName)
	{
		return (MAssetBuild)MTable.get(ctx, MAssetBuild.Table_Name).getPO(A_Asset_Build_ID, trxName);
	}	//	get
	
	
	
	/** Create constructor */
	public MAssetBuild (Properties ctx, int A_Asset_Build_ID, String trxName)
	{
		super (ctx, A_Asset_Build_ID,trxName);
		
	}	//	MAsset

	
	public MAssetBuild (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAsset
	
	
	protected MAssetBuild (MProject project)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setDescription(project.getDescription());
	}
	
	public MAssetBuild(MInOut mInOut, MInOutLine sLine, int deliveryCount) {
		this(mInOut.getCtx(), 0, mInOut.get_TrxName());
		setDescription(sLine.getDescription());
		
	}

	private boolean checkIncrement = true;
	private void CountBefore() {
		String sql = "Select count(1) FROM A_Depreciation_Workfile WHERE DateAcct < ? AND A_Asset_ID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(getDateAcct());
		params.add(getA_Asset_ID());
		int no = DB.getSQLValue(get_TrxName(), sql, params);
		if (no > 0) {
			checkIncrement = false;
		}
		
	}
	
	protected boolean beforeSave (boolean newRecord)
	{
		
		return true;
	}	//	beforeSave
	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if(!success)
		{
			return success;
		}
		
		
		
		return true;
	}	//	afterSave
	
	
	protected boolean beforeDelete()
	{
		
		
		return true;
	}       //      beforeDelete
	
	
	
	protected String		m_processMsg = null;

	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, true);
		return engine.processIt (action, getDocStatus());
	}


	@Override
	public String completeIt() {
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Drafted;
		}
		
		updateAllAsset(true);
		
		updateAssetAmount(getA_Asset_ID(), getDateAcct(), true);
		
		setProcessed(true);
		return DocAction.STATUS_Completed;
	}



	@Override
	public boolean reActivateIt() {
		
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return false;
		}
		
		
		updateAllAsset(false);
		
		updateAssetAmount(getA_Asset_ID(), getDateAcct(), false);
		
		if(!super.reActivate())
			return false;
		
		setProcessed(false);
		
			
		return true;
	}



	@Override
	public String getProcessMsg() {
		if (m_processMsg != null) {
			setProcessed(false);
			
		}
		return m_processMsg;
	}

	@Override
	public void setProcessMsg(String text) {
		m_processMsg = text;
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}
	
	private void updateAllAsset(boolean insert) {
		//Hàm kiểm tra xem có là nâng cấp hay tăng mới
		CountBefore();
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		MAsset asset = MAsset.get(getCtx(), getA_Asset_ID(), get_TrxName());
		updateAsset(insert, dt);
		
		updateTools(insert, dt, asset);
	}
	
	private void updateAssetAmount(int A_Asset_ID, Timestamp dateAcct, boolean isComplete) {
		String sql = "";
		Object [] params = null;
		String sqlCO = 			
				" WITH B1 AS "+
				" ( "+
				" 	SELECT s.A_Asset_ID,  t1.Accumulate, t2.baseAmt, t3.baseAmtOri, t2.UseLifes, t1.UseLifed, "+
				"		NVL(t2.baseAmt,0) - NVL(Accumulate,0) RemainAmt  "+
				" 	FROM A_Asset s  "+
				" 		LEFT JOIN   "+
				" 		(  "+
				" 			SELECT SUM(Amount) Accumulate, SUM(UseLifed) UseLifed, A_Asset_ID FROM A_Depreciation_Exp   "+
				" 			WHERE Processed = 'Y'  "+
				" 			GROUP BY A_Asset_ID  "+
				" 		)t1 ON s.A_Asset_ID = t1.A_Asset_ID  "+
				" 		LEFT JOIN  "+
				" 		(  "+
				" 			SELECT SUM(NVL(Amount,0) + NVL(AccumulateAmt,0)) baseAmt, SUM(NVL(UseLifes,0)) UseLifes, A_Asset_ID   "+
				" 			FROM A_Asset_History  "+
				" 			GROUP BY A_Asset_ID  "+
				" 		)t2 ON s.A_Asset_ID = t2.A_Asset_ID  "+
				" 		LEFT JOIN  "+
				" 		(  "+
				" 			SELECT SUM(NVL(Amount,0) + NVL(AccumulateAmt,0)) baseAmtOri, A_Asset_ID   "+
				" 			FROM A_Asset_History   "+
				" 			WHERE IsFirst = 'Y'  "+
				" 			GROUP BY A_Asset_ID  "+
				" 		)t3 ON s.A_Asset_ID = t3.A_Asset_ID  "+
				" 	WHERE s.A_Asset_ID = ? "+ //#1
				" ) "+
				" UPDATE A_Asset s SET  "+
				" 	BaseAmtCurrent = (SELECT NVL(BaseAmt,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	BaseAmtOriginal = (SELECT NVL(baseAmtOri,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	AccumulateAmt = (SELECT NVL(Accumulate,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	UseLifes = (SELECT NVL(UseLifes,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	UseLifed = (SELECT NVL(UseLifed,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	RemainAmt = (SELECT NVL(RemainAmt,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID) "+
				" WHERE s.A_Asset_ID = ?"; //#2
		String sqlRA =		
				" WITH B1 AS "+
				" ( "+
				" 	SELECT s.A_Asset_ID,  t1.Accumulate, t2.baseAmt, t3.baseAmtOri, t2.UseLifes, t1.UseLifed , "+
				"		NVL(t2.baseAmt,0) - NVL(Accumulate,0) RemainAmt "+
				" 	FROM A_Asset s  "+
				" 		LEFT JOIN   "+
				" 		(  "+
				" 			SELECT SUM(Amount) Accumulate, SUM(UseLifed) UseLifed, A_Asset_ID FROM A_Depreciation_Exp   "+
				" 			WHERE Processed = 'Y' AND DateAcct != ?  "+ //#1
				" 			GROUP BY A_Asset_ID  "+
				" 		)t1 ON s.A_Asset_ID = t1.A_Asset_ID  "+
				" 		LEFT JOIN  "+
				" 		(  "+
				" 			SELECT SUM(NVL(Amount,0) + NVL(AccumulateAmt,0)) baseAmt, SUM(NVL(UseLifes,0)) UseLifes, A_Asset_ID   "+
				" 			FROM A_Asset_History   "+
				" 			WHERE ChangeDate != ?  "+ //#2
				" 			GROUP BY A_Asset_ID  "+
				" 		)t2 ON s.A_Asset_ID = t2.A_Asset_ID  "+
				" 		LEFT JOIN  "+
				" 		(  "+
				" 			SELECT SUM(NVL(Amount,0) + NVL(AccumulateAmt,0)) baseAmtOri, A_Asset_ID   "+
				" 			FROM A_Asset_History   "+
				" 			WHERE ChangeDate != ? AND IsFirst = 'Y'  "+ //#3
				" 			GROUP BY A_Asset_ID  "+
				" 		)t3 ON s.A_Asset_ID = t3.A_Asset_ID  "+
				" 	WHERE s.A_Asset_ID = ? "+ //#4
				" )"+
				" UPDATE A_Asset s SET  "+
				" 	BaseAmtCurrent = (SELECT NVL(BaseAmt,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	BaseAmtOriginal = (SELECT NVL(baseAmtOri,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	AccumulateAmt = (SELECT NVL(Accumulate,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	UseLifes = (SELECT NVL(UseLifes,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	UseLifed = (SELECT NVL(UseLifed,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID), "+
				" 	RemainAmt = (SELECT NVL(RemainAmt,0) FROM B1 WHERE s.A_Asset_ID = B1.A_Asset_ID) "+
				" WHERE s.A_Asset_ID = ?"; //#5
		if (isComplete) {
			sql = sqlCO;
			params = new Object [] {A_Asset_ID, A_Asset_ID};
		} else {
			sql = sqlRA;
			params = new Object [] {dateAcct, dateAcct, dateAcct, A_Asset_ID, A_Asset_ID};
		}
		
		DB.executeUpdate(sql, params, true, get_TrxName());
	}
	
	
	private void updateAsset(boolean insert, MDocType dt) {
		
		MAsset asset = MAsset.get(getCtx(), getA_Asset_ID(), get_TrxName());
		MAssetBuildLine [] lines = getLines();
		
		BigDecimal remainAmt = Env.ZERO;
		BigDecimal accumulate = Env.ZERO;
		BigDecimal useLifes = Env.ZERO;
		int UseLifed = 0;
		
		int Account_Dr_ID = 0, Account_Cr_ID = 0;
		
		for (int i = 0; i < lines.length; i++) {
			MElementValue dr = MElementValue.get(getCtx(), lines[i].getAccount_Dr_ID());
			MElementValue cr = MElementValue.get(getCtx(), lines[i].getAccount_Cr_ID());
			//Nếu nhập dư đầu kỳ
			if (X_C_DocType.DOCBASETYPE_AssetOpenbalance.equalsIgnoreCase(dt.getDocBaseType()))
			{
				if (X_C_ElementValue.ACCOUNTTYPE_Asset.equals(dr.getAccountType()) 
						&& X_C_ElementValue.ACCOUNTTYPE_OpenBalance.equals(cr.getAccountType())) {
					remainAmt = lines[i].getAmount();
					if (remainAmt == null)
						remainAmt = Env.ZERO;
				}
				
				if (X_C_ElementValue.ACCOUNTTYPE_OpenBalance.equals(dr.getAccountType()) 
						&& X_C_ElementValue.ACCOUNTTYPE_Accumulate.equals(cr.getAccountType())) {
					accumulate = lines[i].getAmount();
					Account_Dr_ID = dr.getC_ElementValue_ID();
					Account_Cr_ID = cr.getC_ElementValue_ID();
					if (accumulate == null)
						accumulate = Env.ZERO;
				}
				if (lines[i].getUseLifes().compareTo(Env.ZERO) > 0)
					useLifes = useLifes.add(lines[i].getUseLifes());
				
			} else //Ghi tăng
				if (X_C_DocType.DOCBASETYPE_AssetIncrement.equalsIgnoreCase(dt.getDocBaseType()) 
						&& X_C_ElementValue.ACCOUNTTYPE_Asset.equals(dr.getAccountType())) {
					remainAmt = remainAmt.add(lines[i].getAmount());
					if (lines[i].getUseLifes().compareTo(Env.ZERO) > 0)
						useLifes = useLifes.add(lines[i].getUseLifes());
				}
				//Ghi giảm
				else if (X_C_DocType.DOCBASETYPE_AssetDeIncrement.equalsIgnoreCase(dt.getDocBaseType()) 
						&& X_C_ElementValue.ACCOUNTTYPE_Asset.equals(cr.getAccountType())){
					remainAmt = remainAmt.add(lines[i].getAmount().multiply(new BigDecimal("-1")));
					
					if (lines[i].getUseLifes().compareTo(Env.ZERO) > 0)
						useLifes = useLifes.add(lines[i].getUseLifes().multiply(new BigDecimal("-1")));
				}
			
			if (lines[i].getUseLifed() > 0) {
				UseLifed = lines[i].getUseLifed();
			}
		}
		
		//Đẩy giá trị còn lại vào lịch sử để xem biến động thay đổi nguyên giá
		updateAssetHistory(insert, remainAmt, accumulate, useLifes);
		
		//Đẩy giá trị lũy kế khấu hao vào bảng khấu hao chung.
		if (X_C_DocType.DOCBASETYPE_AssetOpenbalance.equalsIgnoreCase(dt.getDocBaseType()) && accumulate.compareTo(Env.ZERO) > 0)
		{
			insertDepreciationExp(insert, accumulate, Account_Dr_ID, Account_Cr_ID, getDateAcct(), asset, UseLifed);
		}
		
		//Đẩy dữ liệu vào bảng khấu hao dự tính
		updateWorkfile(insert, asset, remainAmt, useLifes, UseLifed);		
	}
	
	private MDepreciationWorkfile getBeforeRow() {
		String sqlWhere = "OrderNo = (select max(OrderNo) From A_Depreciation_Workfile Where A_Asset_ID = ?) And  A_Asset_ID = ?";
		
		MDepreciationWorkfile relValue = new Query(getCtx(), X_A_Depreciation_Workfile.Table_Name, sqlWhere, get_TrxName())
				.setParameters(getA_Asset_ID(), getA_Asset_ID())
				.setApplyAccessFilter(true)
				.first();
		return relValue;
		
	}
	
	private String updateWorkfile (boolean insert, MAsset asset, BigDecimal amt, BigDecimal life, int UseLifed) {
		MDepreciationWorkfile workfile = null;
		if (insert) {
			BigDecimal remainAmt = Env.ZERO;
			BigDecimal useLifes = Env.ZERO;
			List<Object> data = getInfoBefore();
			if (data != null) {
				if (data.get(0) != null) {
					remainAmt = new BigDecimal(data.get(0).toString());
				}
				if (data.get(1) != null) {
					useLifes = new BigDecimal(data.get(1).toString());
					
				}
			}
			if (UseLifed > 0)
				life = life.subtract(new BigDecimal(UseLifed));
			remainAmt = remainAmt.add(amt);
			useLifes = useLifes.add(life);
			
			//Nếu chưa có bản ghi nào thì OrderNo = 10. Nếu có rồi thì Order = Order + 10. Cập nhật EndDate của dòng trước đấy
			int order = 0;
			MDepreciationWorkfile wf = getBeforeRow();
			if (wf != null) {
				order = wf.getOrderNo();
			}
			workfile = new MDepreciationWorkfile(getCtx(), 0, get_TrxName());
			workfile.setDateAcct(getDateAcct());
			if (order == 0)
				workfile.setStartDate(asset.getUseDate());
			else
				workfile.setStartDate(getDateAcct());
			workfile.setEndDate(null);
			workfile.setA_Asset_ID(getA_Asset_ID());
			
			//Thu tu nay rat quan trong, dung de xac dinh cac ky tinh toan. Gia tri: 10,20,30...
			workfile.setOrderNo(order + 10);
			
			workfile.setAmount(remainAmt.divide(useLifes.multiply(new BigDecimal("30")), Env.rountQty, RoundingMode.HALF_UP));
			workfile.saveEx();
			
			//Cập nhật EndDate dòng trước đó
			if (order > 0) {
				String sqlupdate = "UPDATE A_Depreciation_Workfile SET EndDate = ? WHERE OrderNo = ? AND A_Asset_ID = ?";
				Object [] params = {TimeUtil.addDays(getDateAcct(), -1), order, getA_Asset_ID()};
				DB.executeUpdate(sqlupdate, params, true, get_TrxName());
			}
			
		} else {
			String sql = "Delete A_Depreciation_Workfile Where DateAcct = ? And A_Asset_ID = ?";
			Object [] params = {getDateAcct(), getA_Asset_ID()};
			DB.executeUpdate(sql, params, true, get_TrxName());
		}
		
		return "";
	}
	
	//Lấy số tiền còn lại và số kỳ còn lại trước khi thay đổi nguyên giá để tính lại giá trị khấu hao mới
	private List<Object> getInfoBefore() {
		String sql = 
				" SELECT t1.BaseAmt - COALESCE(t2.Amount), t1.UseLifes - COALESCE(t2.UseLifed) "+
				" FROM A_Asset s "+
				" 	LEFT JOIN ( "+
				" 		SELECT A_Asset_ID, SUM(COALESCE(Amount,0) + COALESCE(AccumulateAmt)) BaseAmt , SUM(UseLifes)  UseLifes "+
				" 		FROM A_Asset_History  "+
				" 		WHERE A_Asset_ID = ? AND ChangeDate != ? "+//#1,2
				" 		GROUP BY A_Asset_ID "+
				" 	)t1 ON s.A_Asset_ID = t1.A_Asset_ID "+
				" 	LEFT JOIN ( "+
				" 		SELECT A_Asset_ID, SUM(Amount) Amount, SUM(UseLifed) UseLifed FROM A_Depreciation_Exp  "+
				" 		WHERE A_Asset_ID = ? AND Processed = 'Y' "+//#3
				" 		GROUP BY A_Asset_ID "+
				" 	)t2 ON s.A_Asset_ID = t2.A_Asset_ID "+
				" WHERE s.A_Asset_ID = ?";//#4
		Object [] params = {getA_Asset_ID(), getDateAcct(), getA_Asset_ID(), getA_Asset_ID()};
		List<Object> data = DB.getSQLValueObjectsEx(get_TrxName(), sql, params);
		return data;
	}
	
	
	
	private void updateAssetHistory (boolean insert, BigDecimal amount, BigDecimal accumulateAmt, BigDecimal useLifes) {
		//Neu Co thi kiem tra chua ton tai thi insert co roi thi thoi. Truong hop RA thi xoa.
		if (insert) {
			MAssetHistory his = MAssetHistory.get(getCtx(), getA_Asset_ID(), getDateAcct());
			if (his != null) {
				return;
			}
			his = new MAssetHistory(getCtx(), 0, get_TrxName());
			his.setA_Asset_ID(getA_Asset_ID());
			his.setChangeDate(getDateAcct());
			his.setDescription(getSummary());
			his.setAmount(amount);
			his.setAccumulateAmt(accumulateAmt);
			his.setUseLifes(useLifes);
			if (checkIncrement) {
				his.setIsFirst(true);
			} else {
				his.setIsFirst(false);
			}
			his.save();
		} else {
			String sql = "Delete A_Asset_History Where ChangeDate = ? And A_Asset_ID = ?";
			Object [] params = {getDateAcct(), getA_Asset_ID()};
			DB.executeUpdate(sql, params, true, get_TrxName());		
			
		}
	}
	
	private void updateTools (boolean insert, MDocType dt, MAsset asset) {
		//Neu Co thi kiem tra chua ton tai thi insert co roi thi thoi. Truong hop RA thi xoa.
		String listProduct = "0";
		MAssetBuildLine [] lines = getLines();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].getM_Product_ID() > 0) {
				MAssetTools tools = MAssetTools.get(getCtx(), asset.getA_Asset_ID(), lines[i].getM_Product_ID(), asset.getUseDate());
				if (insert) {
					if (tools != null) {
						return;
					}
					tools = new MAssetTools(getCtx(), 0, get_TrxName());
					tools.setA_Asset_ID(asset.getA_Asset_ID());
					tools.setM_Product_ID(lines[i].getM_Product_ID());
					tools.setDateTrx(asset.getUseDate());
					tools.save();
				}else {
					if (lines[i].getM_Product_ID() > 0)
						listProduct = listProduct + "," + lines[i].getM_Product_ID();					
				}
			}			
		}
		
		if (!insert) {
			String sql = "Delete A_Asset_Tools Where M_Product_ID IN ("+ listProduct +") And A_Asset_ID = ?";
			Object [] params = {getA_Asset_ID()};
			DB.executeUpdate(sql, params, true, get_TrxName());
		}		
	}
	
	private void insertDepreciationExp(boolean insert, BigDecimal accumulate, 
			int Account_Dr_ID, int Account_Cr_ID, Timestamp dateAcct, MAsset asset, int UseLifed) {
		if (insert) {
			MDepreciationExp newExp = new MDepreciationExp(getCtx(), 0, get_TrxName());
			
			accumulate = accumulate.setScale(Env.getScaleFinal(), RoundingMode.HALF_UP);
			newExp.setIsSelected(true);
			newExp.setA_Asset_ID(asset.getA_Asset_ID());
			newExp.setC_TypeCost_ID(asset.getC_TypeCost_ID());
			newExp.setAccount_Dr_ID(Account_Dr_ID);
			newExp.setAccount_Cr_ID(Account_Cr_ID);
			newExp.setAmount(accumulate);
			newExp.setStartDate(asset.getUseDate());
			newExp.setEndDate(dateAcct);
			newExp.setDateAcct(dateAcct);
			newExp.setUseLifed(UseLifed);
			newExp.setProcessed(true);
			newExp.save();
		} else {
			String sql = "Delete A_Depreciation_Exp Where A_Asset_ID = ? AND A_Depreciation_ID is null AND EndDate = ?";
			Object [] params = {getA_Asset_ID(), dateAcct};
			DB.executeUpdate(sql, params, true, get_TrxName());	
		}
		
	}
	
	
	protected MAssetBuildLine[]	m_lines = null;
	
	public MAssetBuildLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MAssetBuildLine> list = new Query(getCtx(), I_A_Asset_BuildLine.Table_Name, "A_Asset_Build_ID=?", get_TrxName())
		.setParameters(getA_Asset_Build_ID())
		.list();
		//
		m_lines = new MAssetBuildLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	//	getMInOutLines

	/**
	 * 	Get Lines of Shipment
	 * 	@return lines
	 */
	public MAssetBuildLine[] getLines()
	{
		return getLines(false);
	}


	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
