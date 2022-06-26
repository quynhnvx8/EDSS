package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;


public class MProductionInput extends X_M_ProductionInput {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720901152312853611L;

	protected MProduction productionParent;


	/**
	 * 	Standard Constructor
	 *	@param ctx ctx
	 *	@param M_ProductionLine_ID id
	 */
	public MProductionInput (Properties ctx, int M_ProductionInput, String trxName)
	{
		super (ctx, M_ProductionInput, trxName);
			
	}	// MProductionLine
	
	public MProductionInput (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProductionLine
	
	/**
	 * Parent Constructor
	 * @param plan
	 */
	public MProductionInput( MProduction header ) {
		super( header.getCtx(), 0, header.get_TrxName() );
	}
	
	public MProductionInput( MProductionPlan header ) {
		super( header.getCtx(), 0, header.get_TrxName() );
	}
	

	

	public String toString() {
		if ( getM_Product_ID() == 0 )
			return ("No product defined for production input ");
		MProduct product = new MProduct(getCtx(),getM_Product_ID(), get_TrxName());
		return ( "Production input:" + product.getValue() + " -- " + getQty());
	}

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		
		return true;
	}

	
}
