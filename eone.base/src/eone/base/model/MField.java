
package eone.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import eone.util.DB;


public class MField extends X_AD_Field
{

	private static final long serialVersionUID = 7124162742037904113L;

	public MField (Properties ctx, int AD_Field_ID, String trxName)
	{
		super (ctx, AD_Field_ID, trxName);
		if (AD_Field_ID == 0)
		{
			setIsDisplayed (true);	// Y
			setIsDisplayedGrid (true);	// Y
			setIsEncrypted (false);
			setIsFieldOnly (false);
			setIsHeading (false);
			setIsReadOnly (false);
			setIsSameLine (false);
		}	
	}	//	MField

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MField (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MField

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 */
	public MField (MTab parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setAD_Tab_ID(parent.getAD_Tab_ID());
	}	//	MField
	
	/**
	 * 	Copy Constructor
	 *	@param parent parent
	 *	@param from copy from
	 */
	public MField (MTab parent, MField from)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		copyValues(from, this);
		setClientOrg(parent);
		setAD_Tab_ID(parent.getAD_Tab_ID());
	}	//	M_Field
	
	/**
	 * 	Set Column Values
	 *	@param column column
	 */
	public void setColumn (MColumn column)
	{
		setAD_Column_ID (column.getAD_Column_ID());
		setName (column.getName());
		setDescription(column.getDescription());
	}	//	setColumn
	
	/**
	 * 	beforeSave
	 *	@see eone.base.model.PO#beforeSave(boolean)
	 *	@param newRecord
	 *	@return
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//	Sync Terminology
		if ((newRecord || is_ValueChanged("AD_Column_ID")))
		{
			M_Element element = M_Element.getOfColumn(getCtx(), getAD_Column_ID(), get_TrxName());
			setName (element.getName ());
			setDescription (element.getDescription ());
		}
		
		if (getIsAllowCopy() != null) {
			MColumn column = (MColumn) getAD_Column();
			if (   column.isKey()
				|| column.isVirtualColumn()
				|| column.isUUIDColumn()
				|| (column.isStandardColumn() && !column.getColumnName().equals("AD_Org_ID")) // AD_Org_ID can be copied
			)
				setIsAllowCopy(null);
		}
		if (getIsAllowCopy() == null) {
			if (getAD_Column().getColumnName().equals("AD_Org_ID")) // AD_Org_ID can be copied
				setIsAllowCopy("Y");
		}
		if (getAD_Reference_ID() <= 0) {
			setAD_Reference_Value_ID(0);
			setAD_Val_Rule_ID(0);
			setIsToolbarButton(null);
		}
		if (isDisplayed()) {
			MColumn column = (MColumn) getAD_Column();
			if (column.isVirtualSearchColumn()) {
				setIsDisplayed(false);
				setIsDisplayedGrid(false);
			}
		}

		return true;
	}	//	beforeSave
	
	public static String updateWidthDisplay(String sql, List<List<Object>> paramsList, String trxName) {
		return DB.excuteBatch(sql, paramsList, trxName);
	}
	
}	//	MField
