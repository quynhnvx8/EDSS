
package eone.base.callout;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.util.DB;


/**
 *	Resource Assignment Callout
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutAssignment.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutAssignment extends CalloutEngine
{

	/**
	 *	Assignment_Product.
	 *		- called from S_ResourceAssignment_ID
	 *		- sets M_Product_ID, Description
	 *			- Qty.. 
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
		//	get value
		int S_ResourceAssignment_ID = ((Integer)value).intValue();
		if (S_ResourceAssignment_ID == 0)
			return "";

		int M_Product_ID = 0;
		String Name = null;
		String Description = null;
		BigDecimal Qty = null;
		String sql = "SELECT p.M_Product_ID, ra.Name, ra.Description, ra.Qty "
			+ "FROM S_ResourceAssignment ra"
			+ " INNER JOIN M_Product p ON (p.S_Resource_ID=ra.S_Resource_ID) "
			+ "WHERE ra.S_ResourceAssignment_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, S_ResourceAssignment_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				M_Product_ID = rs.getInt (1);
				Name = rs.getString(2);
				Description = rs.getString(3);
				Qty = rs.getBigDecimal(4);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "product", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (log.isLoggable(Level.FINE)) log.fine("S_ResourceAssignment_ID=" + S_ResourceAssignment_ID + " - M_Product_ID=" + M_Product_ID);
		if (M_Product_ID != 0)
		{
			mTab.setValue ("M_Product_ID", Integer.valueOf(M_Product_ID));
			if (Description != null)
				Name += " (" + Description + ")";
			if (!".".equals(Name))
				mTab.setValue("Description", Name);
			//
			String variable = "Qty";	//	TimeExpenseLine
			if (mTab.getTableName().startsWith("C_Order"))
				variable = "QtyOrdered";
			else if (mTab.getTableName().startsWith("C_Invoice"))
				variable = "QtyInvoiced";
			if (Qty != null)
				mTab.setValue(variable, Qty);
				mTab.setValue("QtyEntered", Qty);  //red1 BR2836655-Resource Assignment always return Qty 1
		}
		return "";
	}	//	product

}	//	CalloutAssignment
