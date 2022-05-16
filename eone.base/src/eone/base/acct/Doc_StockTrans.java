
package eone.base.acct;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import eone.base.model.MElementValue;
import eone.base.model.MStockTrans;
import eone.base.model.MStockTransLine;

public class Doc_StockTrans extends Doc
{

	public Doc_StockTrans (ResultSet rs, String trxName)
	{
		super (MStockTrans.class, rs, trxName);
	}   //  DocInOut



	protected String loadDocumentDetails()
	{
		MStockTrans inout = (MStockTrans)getPO();
		p_lines = loadLines(inout);
		if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails


	private DocLine[] loadLines(MStockTrans inout)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MStockTransLine[] lines = inout.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MStockTransLine line = lines[i];
			if (line.getM_Product_ID() == 0)
			{
				if (log.isLoggable(Level.FINER)) log.finer("Ignored: " + line);
				continue;
			}

			DocLine docLine = new DocLine (line, this);
			
			
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	
	
	public ArrayList<Fact> createFacts ()
	{
		Fact fact = new Fact(this, Fact.POST_Actual);
		
		MElementValue dr = null;
		MElementValue cr = null;
		
		for (int i = 0; i < p_lines.length; i++)
		{
			
			DocLine line = p_lines[i];
			MStockTransLine inoutLine = (MStockTransLine) line.getPO();
			
			dr = MElementValue.get(getCtx(), inoutLine.getAccount_Dr_ID());
			cr = MElementValue.get(getCtx(), inoutLine.getAccount_Cr_ID());
			
			//COGS
			FactLine f = null;
			f = fact.createLine(line, dr, cr, inoutLine.getAmount(), inoutLine.getAmount());
			
			f.setM_Product_ID(inoutLine.getM_Product_ID());
			f.setM_Product_Cr_ID(inoutLine.getM_Product_ID());
			f.setC_BPartner_Dr_ID(inoutLine.getC_BPartner_Dr_ID());
			f.setC_BPartner_Cr_ID(inoutLine.getC_BPartner_Cr_ID());
			f.setQty(inoutLine.getQty());
			
		}//end for
		
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

	
}   //  Doc_InOut
