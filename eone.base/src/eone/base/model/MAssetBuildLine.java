package eone.base.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MAssetBuildLine extends X_A_Asset_BuildLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247516669047870893L;

	public MAssetBuildLine (Properties ctx, int A_Asset_BuildLine_ID, String trxName)
	{
		super (ctx, A_Asset_BuildLine_ID, trxName);
		
	}	//	MAssetUse

	public static Map<Integer, MAssetBuildLine> get(Properties ctx, int A_Asset_Build_ID) {
		
		String sqlWhere = "A_Asset_Build_ID = ?";
		List<MAssetBuildLine> list = new Query(ctx, I_A_Asset_BuildLine.Table_Name, sqlWhere, null)
				.setParameters(A_Asset_Build_ID)
				.setOnlyActiveRecords(true)
				.setApplyAccessFilter(true)
				.list();
				
		MAssetBuildLine [] retValue = new MAssetBuildLine[list.size ()];
		Map<Integer, MAssetBuildLine> map = new HashMap<Integer, MAssetBuildLine>();
		for (int i = 0; i <= retValue.length; i++) {
			map.put(retValue[i].getA_Asset_BuildLine_ID(), retValue[i]);
		}
		return map;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		return true;
	}


	public MAssetBuildLine (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	


	protected boolean afterSave (boolean newRecord,boolean success)
	{
		log.info ("afterSave");
		if (!success)
			return success;
		
		return success;
		 
		
	}	//	afterSave



}
