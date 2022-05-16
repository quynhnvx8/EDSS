
package eone.base.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;

import eone.base.model.MElementValue;
import eone.base.model.MFactAcct;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Msg;


public final class Fact
{
	
	public Fact (Doc document, String defaultPostingType)
	{
		m_doc = document;
		m_postingType = defaultPostingType;
		m_trxName = document.getTrxName();
		if (log.isLoggable(Level.CONFIG)) log.config(toString());
	}	//	Fact


	private static final CLogger	log = CLogger.getCLogger(Fact.class);

	private Doc             m_doc = null;
	private String m_trxName;

	private String		    m_postingType = null;

	public static final String	POST_Actual = MFactAcct.POSTINGTYPE_Actual;
	public static final String	POST_Wait = MFactAcct.POSTINGTYPE_Wait;
	

	private ArrayList<FactLine>	m_lines = new ArrayList<FactLine>();


	public void dispose()
	{
		m_lines.clear();
		m_lines = null;
	}   //  dispose

	
	public FactLine createLine (DocLine docLine, MElementValue dr, MElementValue cr, BigDecimal Amt, BigDecimal AmtConvert)
	{
		
		FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(),	m_doc.get_ID(),	docLine.get_ID(), m_trxName);
		
		line.setInfoLine(m_doc, docLine);
		line.setPostingType(m_postingType);
		line.setAccount(dr, cr);
		line.setAmount(m_doc.getC_Currency_ID(), m_doc.getRate(), Amt,  AmtConvert);
		
		add(line);
		return line;
	}	//	createLine
	
	public FactLine createHeader (MElementValue dr, MElementValue cr, BigDecimal Amt, BigDecimal AmtConvert)
		{
			
			FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(),	m_doc.get_ID(),	m_doc.get_ID(), m_trxName);
			
			line.setInfoHeader(m_doc);
			line.setPostingType(m_postingType);
			line.setAccount(dr, cr);
			
			line.setAmount(m_doc.getC_Currency_ID(), m_doc.getRate(), Amt,  AmtConvert);
			
			add(line);
			return line;
		}

	/**
	 *  Add Fact Line
	 *  @param line fact line
	 */
	public void add (FactLine line)
	{
		m_lines.add(line);
	}   //  add

	
	public String checkAccounts()
	{
		if (m_lines.size() == 0)
			return "";
		
		
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			
			MElementValue dr = line.getAccountDr();
			
			if (dr != null && dr.isSummary()) {
				log.warning("Cannot post to Summary Account " + dr + ": " + line);
				return Msg.getMsg(Env.getCtx(), "Yes_Account_Summary") + ": " + dr + "; ";
			}
			if (dr != null && !dr.isActive()) {
				log.warning("Cannot post to Inactive Account " + dr	+ ": " + line);
				return Msg.getMsg(Env.getCtx(), "No_Account_Active") + ": " + dr + "; ";
			}
			
			MElementValue cr = line.getAccountCr();
			
			if (cr != null && cr.isSummary()) {
				log.warning("Cannot post to Summary Account " + cr + ": " + line);
				return Msg.getMsg(Env.getCtx(), "Yes_Account_Summary") + ": " + cr + "; ";
			}
			if (cr != null && !cr.isActive()) {
				log.warning("Cannot post to Inactive Account " + cr + ": " + line);
				return Msg.getMsg(Env.getCtx(), "No_Account_Active") + ": " + cr + "; ";
			}

		}	//	for all lines
		
		return "";
	}	//	checkAccounts
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Fact[");
		sb.append(m_doc.toString());
		sb.append(",PostType=").append(m_postingType);
		sb.append("]");
		return sb.toString();
	}	//	toString

	/**
	 *	Get Lines
	 *  @return FactLine Array
	 */
	public FactLine[] getLines()
	{
		FactLine[] temp = new FactLine[m_lines.size()];
		m_lines.toArray(temp);
		return temp;
	}	//	getLines

	
	public boolean save (String trxName)
	{
		m_trxName = trxName;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine fl = (FactLine)m_lines.get(i);
			if (!fl.save(trxName))  //  abort on first error
				return false;
		}
		return true;
	}   //  commit

	
	public String get_TrxName() 
	{
		return m_trxName;
	}	//	getTrxName

}   //  Fact