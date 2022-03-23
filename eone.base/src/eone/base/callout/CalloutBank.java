
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
import eone.util.Env;


/**
 *	Bank Statement Callout	
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutBankStatement.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class CalloutBank extends CalloutEngine
{
	
	public String payment (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Payment_ID = (Integer)value;
		if (C_Payment_ID == null || C_Payment_ID.intValue() == 0)
			return "";
		//
		BigDecimal stmt = (BigDecimal)mTab.getValue("StmtAmt");
		if (stmt == null)
			stmt = Env.ZERO;

		String sql = "SELECT PayAmt FROM C_Payment_v WHERE C_Payment_ID=?";		//	1
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Payment_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				BigDecimal bd = rs.getBigDecimal(1);
				mTab.setValue("TrxAmt", bd);
				if (stmt.compareTo(Env.ZERO) == 0)
					mTab.setValue("StmtAmt", bd);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BankStmt_Payment", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return "";
	}	//	payment

}	//	CalloutBankStatement
