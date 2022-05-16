
package eone.base.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import eone.base.model.MAssetBuild;
import eone.base.model.MAssetBuildLine;
import eone.base.model.MElementValue;


public class Doc_AssetBuild extends Doc
{
	
	public Doc_AssetBuild (ResultSet rs, String trxName)
	{
		super(MAssetBuild.class, rs, trxName);
	}	

	
	protected String loadDocumentDetails ()
	{
		MAssetBuild header = (MAssetBuild)getPO();

		p_lines = loadLines(header);
		return null;
	}  

	
	/**
	 *	Load Cash Line
	 *	@param cash journal
	 *	@param cb cash book
	 *  @return DocLine Array
	 */
	
	private DocLine[] loadLines(MAssetBuild exp)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MAssetBuildLine[] lines = exp.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MAssetBuildLine line = lines[i];
			DocLine docLine = new DocLine (line, this);
			//
			list.add(docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	
	
	
	public ArrayList<Fact> createFacts ()
	{
		
		Fact fact = new Fact(this, Fact.POST_Actual);
		
		MAssetBuild header = (MAssetBuild)getPO();
		//MDocType dt = MDocType.get(getCtx(), header.getC_DocType_ID());
		
		for(int i = 0; i < p_lines.length; i++) {
			DocLine docLine = p_lines[i];
			MAssetBuildLine line = (MAssetBuildLine) docLine.getPO();

			if (!postFixedAsset(fact, docLine, header, line)) {
				return null;
			}
		}
		
		
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

	
	private boolean postFixedAsset(Fact fact, DocLine docLine, MAssetBuild header, MAssetBuildLine line) {
		
		MElementValue dr = null;
		MElementValue cr = null;
		dr = MElementValue.get(getCtx(), line.getAccount_Dr_ID());
		cr = MElementValue.get(getCtx(), line.getAccount_Cr_ID());
		BigDecimal amount = line.getAmount();
		
		FactLine f = fact.createLine(docLine, dr, cr, amount, amount);
		if (f == null) {
			p_Error = "Not Create Fact";
			log.log(Level.SEVERE, p_Error);
			return false;
		}
		f.setA_Asset_ID(header.getA_Asset_ID());
		f.setM_Product_ID(line.getM_Product_ID());
		f.setM_Product_Cr_ID(line.getM_Product_ID());
		f.setM_Warehouse_Cr_ID(line.getM_Warehouse_Cr_ID());
		return true;
	}
}   
