
package eone.base.process;

import java.util.List;
import java.util.logging.Level;

import eone.base.model.PO;
import eone.base.model.Query;
import eone.base.model.X_HR_Item;

public class CreateHRItem extends SvrProcess {

	/**
	 * Quynhnv.x8: Tong hop cong nhan vien
	 */
	private int AD_Client_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter parameter : params)
		{
			String para = parameter.getParameterName();
			if ( para.equals("AD_Client_ID") )
				AD_Client_ID = parameter.getParameterAsInt();
			else
				log.log(Level.WARNING, "Unknown paramter: " + para);
		}
	}

	@Override
	protected String doIt() throws Exception {
		String whereClause = " AD_Client_ID = 0 AND Value NOT IN (SELECT Value FROM HR_Item WHERE AD_Client_ID = ?)";
		List<X_HR_Item> retValue = new Query(getCtx(), X_HR_Item.Table_Name, whereClause, get_TrxName(), true)
				.setParameters(AD_Client_ID)
				.list();
		if (retValue != null) {
			X_HR_Item item = null;
			for(int i = 0; i < retValue.size(); i++) {
				item = new X_HR_Item(getCtx(), 0, get_TrxName());
				PO.copyValues(retValue.get(i), item);
				item.setAD_Client_ID(AD_Client_ID);
				item.save();
			}
		}
		return "Success";
	}

	
}
