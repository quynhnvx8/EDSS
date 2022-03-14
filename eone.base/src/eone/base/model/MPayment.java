package eone.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;

import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;


public class MPayment extends X_C_Payment implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 285926961771269935L;

	
	public static MPayment get (Properties ctx, int C_Payment_ID)
	{
		if (C_Payment_ID <= 0)
		{
			return null;
		}
		Integer key = Integer.valueOf(C_Payment_ID);
		MPayment retValue = (MPayment) s_cache.get (key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MPayment (ctx, C_Payment_ID, null);
		if (retValue.get_ID () != 0)
		{
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get
	
	
	public static List<MPaymentLine> getLines(Properties ctx, int C_Payment_ID, String trxNam ) {
		String whereClause = " C_Payment_ID = ? ";
			
		List<MPaymentLine> retValue = new Query(ctx, X_C_PaymentLine.Table_Name, whereClause, trxNam)
				.setParameters(C_Payment_ID)
				.list();
		return retValue;
	}
	
	
	/**	Cache						*/
	private static CCache<Integer,MPayment> s_cache	= new CCache<Integer,MPayment>(Table_Name, 40, 5);	//	5 minutes
	
	public MPayment (Properties ctx, int M_Product_ID, String trxName)
	{
		super (ctx, M_Product_ID, trxName);
		if (M_Product_ID == 0)
		{
		
		}
	}	//	MProduct

	/**
	 * 	Load constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPayment (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProduct


	
	@Override
	public String toString()
	{
		return "";
	}	//	toString

	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		
		
		
		BigDecimal amt = getTransferAmt().add(getCashAmt()).add(getDebitAmt()); 
		
		if(amt.compareTo(getTotalPaid()) > 0) {
			log.saveError("Error!", "Tiền mặt và chuyển khoản không hợp lệ!");
			return false;
		}
		
		return true;
	}	//	beforeSave

	
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if(newRecord | is_ValueChanged("HM_PatientRegister_ID")) {
			String sqlDel = "DELETE C_PaymentLine WHERE C_Payment_ID = ?";
			DB.executeUpdate(sqlDel, getC_Payment_ID(), get_TrxName());
			List<MPatientRegisterLine> registerLine = MPatientRegister.getLines(getCtx(), getHM_PatientRegister_ID(), get_TrxName(), "NU");
			MPaymentLine line = null;
			for(int i = 0; i < registerLine.size(); i++) {
				line = new MPaymentLine(getCtx(), 0, get_TrxName());
				line.setC_Payment_ID(getC_Payment_ID());
				line.setM_Product_ID(registerLine.get(i).getM_Product_ID());
				line.setC_PaymentLine_ID(DB.getNextID(getCtx(), X_C_PaymentLine.Table_Name, get_TrxName()));
				line.setHM_PatientRegisterLine_ID(registerLine.get(i).getHM_PatientRegisterLine_ID());
				line.setAmount(registerLine.get(i).getAmount());
				line.setPerformStatus("NU");
				line.save();
			}
		}		
		return success;
	}	//	afterSave

	
	
	protected String		m_processMsg = null;

	@Override
	public boolean processIt(String action, int AD_Window_ID) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus(), AD_Window_ID, false);
		return engine.processIt (action, getDocStatus());
	}


	@Override
	public String completeIt() {
		
		m_processMsg = null;
		
		if (getTotalPaid().compareTo(Env.ZERO) <= 0) {
			m_processMsg = "Tiền thanh toán phải lớn hơn 0!";
		}
		
		if (getCashAmt().compareTo(Env.ZERO) < 0) {
			m_processMsg = "Tiền thanh toán phải lớn hơn 0!";
		}
		
		if (getTransferAmt().compareTo(Env.ZERO) < 0) {
			m_processMsg =  "Tiền thanh toán phải lớn hơn 0!";
		}
		
		if (getDebitAmt().compareTo(Env.ZERO) < 0) {
			m_processMsg =  "Tiền nợ phải lớn hơn 0!";
		}
		
		if (getAmount().compareTo(getTotalPaid()) > 0) {
			m_processMsg =  "Phải thanh toán hết mới được phê duyệt.";
		}
		
		if (getCashAmt().add(getTransferAmt()).compareTo(getTotalPaid()) != 0) {
			m_processMsg =  "Tiền mặt + chuyển khoản không đúng với tổng tiền";
		}
		
		if (m_processMsg != null)
			return DocAction.STATUS_Drafted;
		setProcessed(true);
		updatePaidPayment(true);
		updatePaidRegister(true);
		return DocAction.STATUS_Completed;
	}

	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		StringBuilder sql = new StringBuilder("UPDATE C_PaymentLine SET Processed='")
			.append((processed ? "Y" : "N"))
			.append("' WHERE C_Payment_ID=").append(getC_Payment_ID());
		int noLine = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine(processed + " - Lines=" + noLine);
	}
	
	private void updatePaidRegister(boolean isPaid) {
		if (isPaid) {
			List<MPaymentLine> line = getLines(getCtx(), getC_Payment_ID(), get_TrxName());
			for (int i = 0; i < line.size(); i++) {
				MPatientRegisterLine register = MPatientRegisterLine.get(getCtx(), line.get(i).getHM_PatientRegisterLine_ID());
				register.setPerformStatus(line.get(i).getPerformStatus());
				register.save();
			}	
		} else {
			String sqlUpdate = "UPDATE HM_PatientRegisterLine SET PerformStatus = 'NU' WHERE HM_PatientRegisterLine_ID IN "
					+" (SELECT HM_PatientRegisterLine_ID FROM C_PaymentLine WHERE C_Payment_ID = ?)";
			DB.executeUpdate(sqlUpdate, getC_Payment_ID(), get_TrxName());
		}				
	}
	
	private void updatePaidPayment(boolean isPaid) {
		if (isPaid) {
			if (getTotalPaid().compareTo(getAmount()) >= 0) {
				String sqlUpdate = "UPDATE C_PaymentLine SET PerformStatus = 'YE' WHERE C_Payment_ID = ?";
				DB.executeUpdate(sqlUpdate, getC_Payment_ID(), get_TrxName());
			}
		} else {
			if (getTotalPaid().compareTo(getAmount()) >= 0) {
				String sqlUpdate = "UPDATE C_PaymentLine SET PerformStatus = 'NU' WHERE C_Payment_ID = ?";
				DB.executeUpdate(sqlUpdate, getC_Payment_ID(), get_TrxName());
			}
		}
	}
	
	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		if(!super.reActivate())
			return false;
		updatePaidRegister(false);
		updatePaidPayment(false);
		setProcessed(false);
		return true;
	}


	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public void setProcessMsg(String text) {
		m_processMsg = text;
	}
	
	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDescription());
		return sb.toString();
	}



	@Override
	public int getAD_Window_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}	//	MProduct
