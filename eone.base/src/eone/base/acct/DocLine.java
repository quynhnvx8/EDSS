
package eone.base.acct;

import java.math.BigDecimal;

import eone.base.model.MAccount;
import eone.base.model.PO;
import eone.util.CLogger;
import eone.util.Env;

public class DocLine
{	
	
	public DocLine (PO po, Doc doc)
	{
		if (po == null)
			throw new IllegalArgumentException("PO is null");
		p_po = po;
	}	

	protected PO				p_po = null;
	protected transient CLogger	log = CLogger.getCLogger(getClass());
	private BigDecimal      	m_Amount = Env.ZERO;
	private BigDecimal      	m_AmountConvert = Env.ZERO;
	private MAccount 			m_account = null;

	
	public void setAmount (BigDecimal sourceAmt)
	{
		m_Amount = sourceAmt == null ? Env.ZERO : sourceAmt;
	}   //  setAmounts

	
	public void setAmountConvert (BigDecimal AmountConvert)
	{
		m_AmountConvert = AmountConvert == null ? Env.ZERO : AmountConvert;
	}   //  setConvertedAmt

	
	public BigDecimal getAmount()
	{
		return m_Amount;
	}   //  getAmount

	public BigDecimal getAmountConvert()
	{
		return m_AmountConvert;
	}   //  getAmount

	
	public void setAccount (MAccount acct)
	{
		m_account = acct;
	}   //  setAccount

	
	public MAccount getAccount()
	{
		return m_account;
	}   //  getAccount

	
	public int get_ID()
	{
		return p_po.get_ID();
	}	//	get_ID
	
	public String getDescription()
	{
		int index = p_po.get_ColumnIndex("Description");
		if (index != -1)
			return (String)p_po.get_Value(index);
		return null;
	}	//	getDescription

	
	public int getC_Tax_ID()
	{
		int index = p_po.get_ColumnIndex("C_Tax_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}	//	getC_Tax_ID

	
	public int getLine()
	{
		int index = p_po.get_ColumnIndex("Line");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getLine

	
	public int getValue(String ColumnName)
	{
		int index = p_po.get_ColumnIndex(ColumnName);
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getValue

	
	public PO getPO() 
	{
		return p_po;
	}
	
	
	public String toString()
	{
		
		return "";
	}	//	toString

}	//	DocumentLine
