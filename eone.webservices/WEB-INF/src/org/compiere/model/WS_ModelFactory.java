
package org.compiere.model;

import java.sql.ResultSet;

import eone.base.IModelFactory;
import eone.base.model.PO;
import eone.base.model.X_WS_WebService;
import eone.base.model.X_WS_WebServiceType;
import eone.base.model.X_WS_WebService_Para;
import eone.util.Env;

public class WS_ModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (X_WS_WebService_Para.Table_Name.equals(tableName))
			return MWebServicePara.class;
		if (X_WS_WebServiceType.Table_Name.equals(tableName))
			return MWebServiceType.class;
		if (X_WS_WebService.Table_Name.equals(tableName))
			return MWebService.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (X_WS_WebService_Para.Table_Name.equals(tableName))
			return new MWebServicePara(Env.getCtx(), Record_ID, trxName);
		if (X_WS_WebServiceType.Table_Name.equals(tableName))
			return new MWebServiceType(Env.getCtx(), Record_ID, trxName);
		if (X_WS_WebService.Table_Name.equals(tableName))
			return new MWebService(Env.getCtx(), Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (X_WS_WebService_Para.Table_Name.equals(tableName))
			return new MWebServicePara(Env.getCtx(), rs, trxName);
		if (X_WS_WebServiceType.Table_Name.equals(tableName))
			return new MWebServiceType(Env.getCtx(), rs, trxName);
		if (X_WS_WebService.Table_Name.equals(tableName))
			return new MWebService(Env.getCtx(), rs, trxName);
		return null;
	}

}
