package eone.rest.api.v2;

import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import eone.base.model.MTable;
import eone.base.model.PO;
import eone.base.model.POInfo;
import eone.base.process.DocAction;
import eone.rest.api.v2.entities.DataField;
import eone.rest.api.v2.entities.DataParse;
import eone.rest.api.v2.entities.DataRow;
import eone.rest.api.v2.entities.ModelProcessInfo;
import eone.rest.api.v2.entities.RecordRequest;
import eone.rest.api.v2.entities.RecordResponse;
import eone.rest.api.v2.entities.Response;
import eone.rest.api.v2.entities.RestResponse;
import eone.rest.api.v2.entities.VOResponse;
import eone.rest.api.v2.entities.VOfficeResource;
import eone.rest.api.v2.security.AuthResult;
import eone.rest.api.v2.security.SecureAuth;
import eone.rest.api.v2.security.UserDetails;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Trx;
import eone.util.ValueNamePair;


public class ModelRestServiceImpl implements ModelRestService {
	
	private final static String COLUMNNAME_DOCNO = "DocumentNo";
	private final static String  PASSWORD = "password";
	private final static String  USERNAME = "username";
	private final static String  CLIENTID = "client_id";

	
	
	//The attachment object
	//private MAttachment		_mAttachment = null;

	@Override
	public RestResponse saveData(DataRow dataRow, HttpServletRequest requestContext) {
		RestResponse ret = new RestResponse();
		// Authentication
		String ipAddress = requestContext.getRemoteAddr();
		String password = requestContext.getHeader(PASSWORD);
		String clientId = requestContext.getHeader(CLIENTID);
		String userName = requestContext.getHeader(USERNAME);
		
		AuthResult authResult = SecureAuth.auth2(new UserDetails(clientId, userName, password), ipAddress);
		if (!authResult.isAccess()) {
			ret.setStatus(Response.Status.AUTHENTICATION_FAILED);
			ret.setDescription(authResult.getDescription());
			return ret;
		}
		
    	// Start a trx
    	String trxName = "ModelRest_" + UUID.randomUUID();
    	Trx trx = Trx.get(trxName, true);
    	
		// Validate parameters vs service type
		if(dataRow.getTableId() == null) {
			return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Table_id is null");
		} 
		
		if (dataRow.getFieldArray() == null || dataRow.getFieldArray().length ==0) {
			return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Field is null");
		}
    	
		// get the PO for the tablename and record ID
    	Properties ctx = Env.getCtx();
		MTable table = MTable.get(ctx, dataRow.getTableId());
		
		if (table == null) {
			return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Not found table_id=" + dataRow.getTableId());
		}
		
		PO po;
		int recordId;
		if (dataRow.getRecordId() != null) {
			recordId = dataRow.getRecordId();
		} else {
			recordId = 0;
		}
		
		po = table.getPO(recordId, trxName);
		
    	if (po == null)
    		return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Not found record_id=" + recordId);
		
    	POInfo poInfo = POInfo.getPOInfo(ctx, table.getAD_Table_ID());
    	DataField[] fields = dataRow.getFieldArray();
    	ret = scanFields(fields, po, poInfo, trx, ret);
    	// Set env
    	
    	//po.setAPI(true);
    	if (!po.save()) {
    		Object lastError = Env.getCtx().get(CLogger.LAST_ERROR);
    		String msgr;
    		if (lastError != null) {
    			msgr = ((ValueNamePair)lastError).getName();
    		} else {
    			msgr = null;
    		}
    		System.out.println(po.get_Logger());
    		return rollbackAndSetError(trx, ret, Response.Status.SAVE_ERROR, msgr);
    	} else {
        	trx.commit();
        	trx.close();
    	}
    	
    	ret.setStatus(Response.Status.OK);
    	ret.setId(po.get_ID());
    	ret.setTableId(po.get_Table_ID());
    	ret.setDescription("Save is successful");
    	
		return ret;
	}
	
	/**
	 * Rollback and set error on standard response
	 * @param trx
	 * @param resp
	 * @param ret
	 * @param isError
	 * @param string
	 * @return
	 */
	protected  RestResponse rollbackAndSetError(Trx trx, RestResponse ret, String status, String description) {
		ret.setStatus(status);
		ret.setDescription(description);
		if (trx != null) {
			trx.rollback();
			trx.close();			
		}
		return ret;
	}
	
	protected RestResponse scanFields(DataField[] fields, PO po, POInfo poInfo, Trx trx, RestResponse ret) {
		String columnName;
		Object val = null;
		int idx;
		for (DataField field : fields) {
			columnName = field.getName();
			idx = poInfo.getColumnIndex(columnName);
			
			if (idx == -1) {
				continue;
			}
			
			if (poInfo.isVirtualColumn(idx)) {
				continue;
			}
			
			if (field.getValue() == null) {
				if (COLUMNNAME_DOCNO.equalsIgnoreCase(columnName)) {
					
				}
				po.set_ValueOfColumn(columnName, null);
				continue;
			}
			
			val = DataParse.parseVariable(columnName, field.getType(), field.getValue());	
			
			if (val != null) {
				po.set_ValueOfColumn(columnName, val);
			}
		}
		
		return ret;
	}
	
	

	@Override
	public RestResponse deleteData(DataRow dataRow, HttpServletRequest requestContext) {
		RestResponse ret = new RestResponse();
		// Authentication
		String ipAddress = requestContext.getRemoteAddr();
		String password = requestContext.getHeader(PASSWORD);
		String clientId = requestContext.getHeader(CLIENTID);
		String userName = requestContext.getHeader(USERNAME);
		
		AuthResult authResult = SecureAuth.auth2(new UserDetails(clientId, userName, password), ipAddress);
		if (!authResult.isAccess()) {
			ret.setStatus(Response.Status.AUTHENTICATION_FAILED);
			ret.setDescription(authResult.getDescription());
			return ret;
		}
	   	// Start a trx
    	String trxName = "ModelRest_" + UUID.randomUUID();
    	Trx trx = Trx.get(trxName, true);

		// Validate parameters vs service type
		if(dataRow.getTableId() == null) {
			return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Table_id is null");
		} 
		
		if(dataRow.getRecordId() == null) {
			return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Record_id is null");
		} 
		// get the PO for the tablename and record ID
    	Properties ctx = Env.getCtx();
		MTable table = MTable.get(ctx, dataRow.getTableId());
		
		if (table == null) {
			return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Not found table_id=" + dataRow.getTableId());
		}
		
		PO po;
		
		int recordId = dataRow.getRecordId();
		po = table.getPO(recordId, trxName);
		
    	if (po == null)
    		return rollbackAndSetError(trx, ret, Response.Status.INVALID_PARAMETER, "Not found record_id=" + recordId);
    	
    	if (!po.delete(true)) {
    		Object lastError = Env.getCtx().get(CLogger.LAST_ERROR);
    		String msgr;
    		if (lastError != null) {
    			msgr = ((ValueNamePair)lastError).getName();
    		} else {
    			msgr = null;
    		}
    		System.out.println(po.get_Logger());
    		return rollbackAndSetError(trx, ret, Response.Status.DELETE_ERROR, msgr);
    	} else {
        	trx.commit();
        	trx.close();
    	}
    	
    	ret.setId(po.get_ID());
    	ret.setTableId(po.get_Table_ID());
    	ret.setStatus(Response.Status.OK);
    	ret.setDescription("Delete is success");
		return ret;
	}

	@Override
	public Response getData() {
		RecordResponse resp = new RecordResponse();
		return Response.ok(resp, "Test OK").build();
	}

	@Override
	public RestResponse postIt(RecordRequest record, HttpServletRequest requestContext) {
		return null;
	}
	
	private RestResponse doIt(RecordRequest record, String action) {
		return null;
	
	}

	@Override
	public RestResponse completeIt(RecordRequest record, HttpServletRequest requestContext) {
		RestResponse ret = new RestResponse();
		// Authentication
		String ipAddress = requestContext.getRemoteAddr();
		String password = requestContext.getHeader(PASSWORD);
		String clientId = requestContext.getHeader(CLIENTID);
		String userName = requestContext.getHeader(USERNAME);
		
		AuthResult authResult = SecureAuth.auth2(new UserDetails(clientId, userName, password), ipAddress);
		if (!authResult.isAccess()) {
			ret.setStatus(Response.Status.AUTHENTICATION_FAILED);
			ret.setDescription(authResult.getDescription());
			return ret;
		}
		return doIt(record, DocAction.STATUS_Completed);
	}

	@Override
	public RestResponse reActivateIt(RecordRequest record, HttpServletRequest requestContext) {
		RestResponse ret = new RestResponse();
		// Authentication
		String ipAddress = requestContext.getRemoteAddr();
		String password = requestContext.getHeader(PASSWORD);
		String clientId = requestContext.getHeader(CLIENTID);
		String userName = requestContext.getHeader(USERNAME);
		
		AuthResult authResult = SecureAuth.auth2(new UserDetails(clientId, userName, password), ipAddress);
		if (!authResult.isAccess()) {
			ret.setStatus(Response.Status.AUTHENTICATION_FAILED);
			ret.setDescription(authResult.getDescription());
			return ret;
		}
		return doIt(record, DocAction.STATUS_ReActivate);
	}


	@Override
	public RestResponse processIt(ModelProcessInfo mpi, HttpServletRequest requestContext) {
		return null;
	}

	@Override
	public VOResponse signDocument(VOfficeResource vo, HttpServletRequest requestContext) {
		return null;
	}
}
