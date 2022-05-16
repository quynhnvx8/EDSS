
package eone.base.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import eone.base.model.X_M_Production;
import eone.base.model.X_M_ProductionLine;
import eone.util.DB;
import eone.util.Env;

/**
 *  Post Invoice Documents.
 *  <pre>
 *  Table:              M_Production (325)
 *  Document Types:     MMP
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_Production.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Production extends Doc
{
	/**
	 *  Constructor
	 * 	@param as accounting schema
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Production (ResultSet rs, String trxName)
	{
		super (X_M_Production.class, rs, trxName);
	}   //  Doc_Production

	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails()
	{
		//setC_Currency_ID (NO_CURRENCY);
		X_M_Production prod = (X_M_Production)getPO();
		setDateAcct(prod.getDateAcct());
		//	Contained Objects
		p_lines = loadLines(prod);
		if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails

	
	private DocLine[] loadLines(X_M_Production prod)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		String sqlPL = "SELECT * FROM "
				+ " M_ProductionLine pro_line INNER JOIN M_ProductionPlan plan ON pro_line.M_ProductionPlan_id = plan.M_ProductionPlan_id "
				+ " INNER JOIN M_Production pro ON pro.M_Production_id = plan.M_Production_id "
				+ " WHERE pro.M_Production_ID=? "
				+ " ORDER BY plan.M_ProductionPlan_id, pro_line.Line";
		
		PreparedStatement pstmtPL = null;
		ResultSet rsPL = null;
		try
		{			
			pstmtPL = DB.prepareStatement(sqlPL, getTrxName());
			pstmtPL.setInt(1,get_ID());
			rsPL = pstmtPL.executeQuery();
			while (rsPL.next())
			{
				X_M_ProductionLine line = new X_M_ProductionLine(getCtx(), rsPL, getTrxName());
				if (line.getMovementQty().signum() == 0)
				{
					if (log.isLoggable(Level.INFO)) log.info("LineQty=0 - " + line);
					continue;
				}
				DocLine docLine = new DocLine (line, this);
				//docLine.setQty (line.getMovementQty(), false);
				
				if (log.isLoggable(Level.FINE)) log.fine(docLine.toString());
				list.add (docLine);
			}
		}
		catch (Exception ee)
		{
			log.log(Level.SEVERE, sqlPL, ee);
		}
		finally
		{
			DB.close(rsPL, pstmtPL);
			rsPL = null;
			pstmtPL = null;
		}
			
		DocLine[] dl = new DocLine[list.size()];
		list.toArray(dl);
		return dl;
	}	//	loadLines

	/**
	 *  Get Balance
	 *  @return Zero (always balanced)
	 */
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMP.
	 *  <pre>
	 *  Production
	 *      Inventory       DR      CR
	 *  </pre>
	 *  @param as account schema
	 *  @return Fact
	 */
	public ArrayList<Fact> createFacts ()
	{
		//  create Fact Header
		Fact fact = new Fact(this, Fact.POST_Actual);

		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

}   //  Doc_Production
