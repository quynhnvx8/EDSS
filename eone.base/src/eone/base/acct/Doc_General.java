
package eone.base.acct;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import eone.base.model.MElementValue;
import eone.base.model.MGeneral;
import eone.base.model.MGeneralLine;
import eone.util.Env;


public class Doc_General extends Doc
{
	
	public Doc_General (ResultSet rs, String trxName)
	{
		super(MGeneral.class, rs, trxName);
	}	

	
	protected String loadDocumentDetails()
	{
		MGeneral parent = (MGeneral)getPO();
		p_lines = loadLines(parent);
		if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length);
		return null;
	} 
	
	private DocLine[] loadLines(MGeneral parent)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MGeneralLine[] lines = parent.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MGeneralLine line = lines[i];
			
			DocLine docLine = new DocLine (line, this);
			
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	
	
	
	public ArrayList<Fact> createFacts ()
	{
		
		Fact fact = new Fact(this, Fact.POST_Actual);
		MElementValue dr = null;
		MElementValue cr = null;
		
		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine docLine = p_lines[i];
			MGeneralLine line = (MGeneralLine) docLine.getPO();
			
			dr = MElementValue.get(getCtx(), line.getAccount_Dr_ID());
			cr = MElementValue.get(getCtx(), line.getAccount_Cr_ID());
			FactLine f = fact.createLine(docLine, dr, cr, line.getAmount(), line.getAmount());
			
			f.setC_TypeCost_ID(line.getC_TypeCost_ID());
			f.setC_TypeRevenue_ID(line.getC_TypeRevenue_ID());
			
			
			f.setC_BPartner_Cr_ID(line.getC_BPartner_Cr_ID());
			f.setC_BPartner_Dr_ID(line.getC_BPartner_Dr_ID());
			
			
			if (Env.DisContract)
			{
				f.setC_Contract_ID(line.getC_Contract_ID());
				f.setC_ContractAnnex_ID(line.getC_ContractAnnex_ID());
			}
			if(Env.DisContractLine)
				f.setC_ContractLine_ID(line.getC_Contract_ID());
			
			if (Env.DisProject)
				f.setC_Project_ID(line.getC_Project_ID());
			if (Env.DisProjectLine)
				f.setC_ProjectLine_ID(line.getC_ProjectLine_ID());
			
			if (Env.DisConstruction)
				f.setC_Construction_ID(line.getC_Construction_ID());
			if (Env.DisConstructionLine)
				f.setC_ConstructionLine_ID(line.getC_ConstructionLine_ID());
			
			
		}
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

	
}   
