
package eone.base.acct;

import java.math.BigDecimal;
import java.util.Properties;

import eone.base.model.MElementValue;
import eone.base.model.X_Fact_Acct;
import eone.util.Env;


public final class FactLine extends X_Fact_Acct
{
	
	private static final long serialVersionUID = 6141312459030795891L;


	public FactLine (Properties ctx, int AD_Table_ID, int Record_ID, int Line_ID, String trxName)
	{
		super(ctx, 0, trxName);
		setAmount (Env.ZERO);
		setAmountConvert (Env.ZERO);
		setAD_Table_ID (AD_Table_ID);
		setRecord_ID (Record_ID);
		int c_periodPayment_id = getC_PeriodPayment_ID();
		if (c_periodPayment_id <= 0) {
			setC_PeriodPayment_ID(1);
		}
		setLine_ID (Line_ID);
	}   //  FactLine

	private MElementValue	m_acct = null;
	private MElementValue m_acctCr = null;
	
	
	public void setAccount (MElementValue dr, MElementValue cr)
	{
		m_acct = dr;
		m_acctCr = cr;
		if (dr != null)
			setAccount_Dr_ID(dr.getC_ElementValue_ID());
		if (cr != null)
			setAccount_Cr_ID(cr.getC_ElementValue_ID());
	} 
	
	public void setAmount(int C_Currency_ID, BigDecimal rate, BigDecimal Amount , BigDecimal AmountConvert) {
		setC_Currency_ID(C_Currency_ID);
		setCurrencyRate(rate);
		setAmount(Amount);
		setAmountConvert(AmountConvert);
	}
	
	public void setInfoLine(Doc doc, DocLine docLine)
	{
		
		setAD_Org_ID(doc.getAD_Org_ID());
		setAD_Department_ID(doc.getAD_Department_ID());
		setAD_Client_ID (doc.getAD_Client_ID());
		setC_DocType_ID(doc.getC_DocType_ID());
		setC_DocTypeSub_ID(doc.getC_DocTypeSub_ID());
		setDateAcct (doc.getDateAcct());
		setDocumentNo(doc.getDocumentNo());
		setAD_Window_ID(doc.getAD_Window_ID());
		setC_Period_ID (doc.getC_Period_ID());
		setC_Tax_ID (docLine.getC_Tax_ID());
		setDescription(doc.getDescription());
		
		
	}   //  setDocumentInfo
	
	public void setInfoHeader(Doc doc)
	{
		
		setAD_Org_ID(doc.getAD_Org_ID());
		setAD_Client_ID (doc.getAD_Client_ID());
		setAD_Department_ID(doc.getAD_Department_ID());
		setC_DocType_ID(doc.getC_DocType_ID());
		setC_DocTypeSub_ID(doc.getC_DocTypeSub_ID());
		setDateAcct (doc.getDateAcct());
		setDocumentNo(doc.getDocumentNo());
		setC_Period_ID (doc.getC_Period_ID());
		setC_Tax_ID (doc.getC_Tax_ID());
		
		setDescription(doc.getDescription());
		setAD_Window_ID(doc.getAD_Window_ID());
		
		
	}

	
	
	public MElementValue getAccountDr()
	{
		return m_acct;
	}	//	getAccount
	
	
	public MElementValue getAccountCr()
	{
		return m_acctCr;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("FactLine=[");
		sb.append(getAD_Table_ID()).append(":").append(getRecord_ID())
			.append(",").append(m_acct)
			.append(",Cur=").append(getC_Currency_ID())
			.append(", DR=").append(getAmount()).append("|").append(getAmountConvert())
			.append("]");
		return sb.toString();
	}	//	toString

	
}	//	FactLine
