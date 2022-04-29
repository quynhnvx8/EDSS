/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/

package eone.base.callout;

import java.math.BigDecimal;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.util.DB;

/**
 * 
 * Sales Opportunity callout
 * @author Paul Bowden, Adaxa Pty Ltd
 * 
 * 
 */
public class CalloutOpportunity extends CalloutEngine {
	
	/**
	 * Update probability based on sales stage
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String salesStage (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
	
		int C_SalesStage_ID = (Integer) value;
		
		String sql = "SELECT Probability FROM C_SalesStage WHERE C_SalesStage_ID = ?";
		BigDecimal probability = DB.getSQLValueBD(null, sql, C_SalesStage_ID);
		if ( probability != null )
			mTab.setValue("Probability", probability);
		
		return "";
	}
}