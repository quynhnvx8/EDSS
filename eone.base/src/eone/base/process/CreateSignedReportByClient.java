

package eone.base.process;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import eone.base.model.MConfigSignReport;
import eone.base.model.MOrg;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.base.model.X_C_ConfigSignReport;
import eone.util.DB;
import eone.util.Env;

/**
 * @author Admin
 * Copy toàn bộ chân ký báo cáo cho cty đăng ký
 */

public class CreateSignedReportByClient extends SvrProcess {
	
	private int BATCH_SIZE = Env.getBatchSize(getCtx());

	private int p_AD_Org_ID = 0;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter p : params)
		{
			if ( p.getParameterName().equals("AD_Org_ID") )
				p_AD_Org_ID = p.getParameterAsInt();
			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + p.getParameterName());
		}		
	}

	@Override
	protected String doIt() throws Exception {
		MOrg reg = MOrg.get(getCtx(), p_AD_Org_ID);
		String whereClause = " AD_Client_ID = 11 AND AD_Process_ID NOT IN (SELECT NVL(AD_Process_ID,0) FROM C_ConfigSignReport WHERE AD_Client_ID = ?)";
		List<MConfigSignReport> list = new Query(getCtx(), X_C_ConfigSignReport.Table_Name, whereClause, get_TrxName())
				.setParameters(reg.getAD_Client_ID())
				.list();
		String sqlInsert = PO.getSqlInsert(X_C_ConfigSignReport.Table_ID, get_TrxName());
		List<List<Object>> values = new ArrayList<List<Object>>();
		
		List<String> colNames = PO.getSqlInsert_Para(X_C_ConfigSignReport.Table_ID, get_TrxName());
		MConfigSignReport addNew = null;
		for(int i = 0; i < list.size(); i++) {
			addNew = new MConfigSignReport(getCtx(), 0, get_TrxName());
			int id = DB.getNextID(X_C_ConfigSignReport.Table_Name, get_TrxName());
			addNew.setC_ConfigSignReport_ID(id);
			addNew.setAD_Process_ID(list.get(i).getAD_Process_ID());
			addNew.setAD_Client_ID(reg.getAD_Client_ID());
			addNew.setAD_Org_ID(reg.getAD_Org_ID());
			addNew.setIsActive(true);
			addNew.setName(list.get(i).getName());
			addNew.setName2(list.get(i).getName2());
			addNew.setPosition(list.get(i).getPosition());
			addNew.setSeqNo(list.get(i).getSeqNo());
			List<Object> line = PO.getBatchValueList(addNew, colNames, X_C_ConfigSignReport.Table_ID, get_TrxName(), id);
			values.add(line);
			
			if (values.size() >= BATCH_SIZE) {
				String err = DB.excuteBatch(sqlInsert, values, get_TrxName());
				if (err != null) {
					DB.rollback(false, get_TrxName());
				}
				values.clear();
			}
		}
		if (values.size() > 0) {
			String err = DB.excuteBatch(sqlInsert, values, get_TrxName());
			if (err != null) {
				DB.rollback(false, get_TrxName());
			}
			values.clear();
		}
		
		
		return "Inserted ";
	}
}
