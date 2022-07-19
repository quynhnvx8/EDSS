
package eone.rest.api.v1.resource.impl;

import java.util.logging.Level;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eone.base.model.MProcess;
import eone.base.model.MProcessPara;
import eone.base.model.MTable;
import eone.base.model.Query;
import eone.rest.api.json.EONERestException;
import eone.rest.api.json.IPOSerializer;
import eone.rest.api.json.TypeConverterUtils;
import eone.rest.api.json.filter.ConvertedQuery;
import eone.rest.api.json.filter.IQueryConverter;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.resource.ProcessResource;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Util;

/**
 * 
 * @author hengsin
 *
 */
public class ProcessResourceImpl implements ProcessResource {

	private final static CLogger log = CLogger.getCLogger(ProcessResourceImpl.class);

	public ProcessResourceImpl() {
	}

	@Override
	public Response getProcesses(String filter) {
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			ConvertedQuery convertedStatement = converter.convertStatement(MProcess.Table_Name, filter);
			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedStatement.getWhereClause());

			JsonArray processArray = new JsonArray();
			StringBuilder where = new StringBuilder("AD_Form_ID IS NULL");
			if (!Util.isEmpty(filter, true)) {
				where.append(" AND (").append(convertedStatement.getWhereClause()).append(")");
			}
			Query query = new Query(Env.getCtx(), MProcess.Table_Name, where.toString(), null);
			query.setApplyAccessFilter(true).setOnlyActiveRecords(true).setOrderBy("Value");
			query.setParameters(convertedStatement.getParameters());

			JsonObject json = new JsonObject();
			json.add("processes", processArray);
			return Response.ok(json.toString()).build();
		} catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();
			
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get process with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}
	}

	@Override
	public Response getProcess(String processSlug) {
		Query query = new Query(Env.getCtx(), MProcess.Table_Name, "Slugify(Value)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		MProcess process = query.setParameters(processSlug).first();
		if (process == null) {
			query.setApplyAccessFilter(false);
			process = query.setParameters(processSlug).first();
			if (process != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for process: ").append(processSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid process name").append("No match found for process name: ").append(processSlug).build().toString())
						.build();
			}
		}
		
		IPOSerializer serializer = IPOSerializer.getPOSerializer(MProcess.Table_Name, MTable.getClass(MProcess.Table_Name));
		JsonObject jsonObject = serializer.toJson(process, new String[] {"AD_Process_ID", "AD_Process_UU", "Value", "Name", "Description", "Help", "EntityType", "IsReport", "AD_ReportView_ID", "AD_PrintFormat_ID", "AD_Workflow_ID", "JasperReport"}, null);
		jsonObject.addProperty("slug", TypeConverterUtils.slugify(process.getValue()));
	
		JsonArray parameterArray = new JsonArray();
		MProcessPara[] parameters = process.getParameters();
		serializer = IPOSerializer.getPOSerializer(MProcessPara.Table_Name, MTable.getClass(MProcessPara.Table_Name));
		for(MProcessPara parameter : parameters) {
			JsonObject parameterJsonObject = serializer.toJson(parameter, null, new String[] {"AD_Client_ID", "AD_Org_ID", "AD_Process_ID", 
					"FieldLength", "IsCentrallyMaintained", "IsEncrypted", "AD_Element_ID", "ColumnName", "IsActive",
					"Created", "CreatedBy", "Updated", "UpdatedBy"});
			String propertyName = parameter.getColumnName();
			parameterJsonObject.addProperty("parameterName", propertyName);
			parameterArray.add(parameterJsonObject);
		}
		jsonObject.add("parameters", parameterArray);
		
		return Response.ok(jsonObject.toString()).build();
	}

	@Override
	public Response runProcess(String processSlug, String jsonText) {
		
		return Response.ok("OK".toString()).build();
	}

	@Override
	public Response getJobs() {
		
		return Response.ok("OK".toString()).build();
	}
	
	@Override
	public Response getJob(int id) {
		return null;	
	}

	@Override
	public Response runJob(String processSlug, String jsonText) {		
		return null;
	}
	
	
}
