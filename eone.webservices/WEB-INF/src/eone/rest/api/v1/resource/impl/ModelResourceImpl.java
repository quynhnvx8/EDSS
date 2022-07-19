
package eone.rest.api.v1.resource.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import org.osgi.service.event.Event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eone.base.event.EventManager;
import eone.base.event.EventProperty;
import eone.base.event.IEventManager;
import eone.base.model.MAttachment;
import eone.base.model.MAttachmentEntry;
import eone.base.model.MSysConfig;
import eone.base.model.MTable;
import eone.base.model.MValRule;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.exceptions.EONEException;
import eone.rest.api.json.EONERestException;
import eone.rest.api.json.IPOSerializer;
import eone.rest.api.json.TypeConverterUtils;
import eone.rest.api.json.filter.ConvertedQuery;
import eone.rest.api.json.filter.IQueryConverter;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.resource.ModelResource;
import eone.rest.api.v1.resource.file.FileStreamingOutput;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Msg;
import eone.util.Trx;
import eone.util.Util;

/**
 * @author Client
 *
 */
public class ModelResourceImpl implements ModelResource {
	
	/*
	request = HttpRequest.newBuilder()
			.uri(new URI(""))
			.version(HttpClient.Version.HTTP_1_1)
			.GET()
			.build();
	response = HttpClient.newHttpClient()
			.send(request, HttpResponse.BodyHandlers.ofString());
	System.out.println(response.body());
	*/

	private static final int DEFAULT_QUERY_TIMEOUT = 60 * 2;
	private static final int MAX_RECORDS_SIZE = MSysConfig.getIntValue("REST_MAX_RECORDS_SIZE", 100);
	private final static CLogger log = CLogger.getCLogger(ModelResourceImpl.class);
	
	private static final String CONTEXT_VARIABLES_SEPARATOR = ",";
	private static final String CONTEXT_NAMEVALUE_SEPARATOR = ":";
	public static final String PO_BEFORE_REST_SAVE = "beforeSave";
	public static final String PO_AFTER_REST_SAVE = "afterSave";
	
	private static final AtomicInteger windowNoAtomic = new AtomicInteger();

	/**
	 * default constructor
	 */
	public ModelResourceImpl() {
	}

	@Override
	public Response getPO(String tableName, String id, String details, String select) {
		return getPO(tableName, id, details, select, null);
	}
	
	/**
	 * 
	 * @param tableName
	 * @param id id or uuid
	 * @param details child/link entity
	 * @param multiProperty comma separated columns
	 * @param singleProperty single column
	 * @return
	 */
	private Response getPO(String tableName, String id, String details, String multiProperty, String singleProperty) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		try {
			PO po = TypeConverterUtils.getPO(tableName, id, true, false);

			if (po != null) {
				IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, po.getClass());
				HashMap<String, ArrayList<String>> includeParser = TypeConverterUtils.getIncludes(tableName, multiProperty, details);
				String[] includes = null;
				if (!Util.isEmpty(multiProperty, true)) {
					includes =  includeParser != null && includeParser.get(table.getTableName()) != null ? 
							includeParser.get(table.getTableName()).toArray(new String[includeParser.get(table.getTableName()).size()]) : 
								null;;
				} else if (!Util.isEmpty(singleProperty, true)) {
					if (po.get_Value(singleProperty) == null) {
						return Response.status(Status.NOT_FOUND)
								.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid property name").append("No match found for table name: ").append(singleProperty).build().toString())
								.build();
					}
					includes = new String[] {singleProperty};
				}
				JsonObject json = serializer.toJson(po, includes, null);
				if (!Util.isEmpty(details, true))
					loadDetails(po, json, details, includeParser);
				return Response.ok(json.toString()).build();
			} else {
				po = TypeConverterUtils.getPO(tableName, id, false, false);

				if (po != null) {
					return Response.status(Status.FORBIDDEN)
							.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
							.build();
				} else {
					return Response.status(Status.NOT_FOUND)
							.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
							.build();
				}
			}
		} catch(Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();

			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get POs with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}
	}
	
	@Override
	public Response getPOProperty(String tableName, String id, String propertyName) {
		return getPO(tableName, id, null, null, propertyName);
	}

	@Override
	public Response getModels(String filter) {
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			ConvertedQuery convertedStatement = converter.convertStatement(MTable.Table_Name, filter);

			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedStatement.getWhereClause());

			Query query = new Query(Env.getCtx(), MTable.Table_Name, convertedStatement.getWhereClause(), null);
			query.setOnlyActiveRecords(true).setApplyAccessFilter(true);
			query.setParameters(convertedStatement.getParameters());

			List<MTable> tables = query.setOrderBy("AD_Table.TableName").list();
			JsonArray array = new JsonArray();
			for(MTable table : tables) {
				JsonObject json = new JsonObject();
				json.addProperty("id", table.getAD_Table_ID());
				if (!Util.isEmpty(""+table.getAD_Table_ID())) {
					json.addProperty("uid", table.getAD_Table_ID());
				}
				json.addProperty("model-name", table.getTableName().toLowerCase());
				json.addProperty("name", table.getName());
				if (!Util.isEmpty(table.getDescription())) {
					json.addProperty("description", table.getDescription());
				}
				array.add(json);
			}
			JsonObject json = new JsonObject();
			json.add("models", array);
			return Response.ok(json.toString()).build();			
		} catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();
			
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get models with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}

	}

	@Override
	public Response getPOs(String tableName, String details, String filter, String order, String select, int top, int skip,
			String validationRuleID, String context) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		String whereClause = "";
		if (!Util.isEmpty(filter, true) ) {
			whereClause = filter;
		}
		
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			ConvertedQuery convertedStatement = converter.convertStatement(tableName, whereClause);
			String convertedWhereClause = convertedStatement.getWhereClause();
			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedWhereClause);
			
			if (validationRuleID != null) {
				MValRule validationRule = getValidationRule(validationRuleID);
				if (validationRule == null ||validationRule.getAD_Val_Rule_ID() == 0) {
					return Response.status(Status.NOT_FOUND)
							.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid validation rule").append("No match found for validation with ID: ").append(validationRuleID).build().toString())
							.build();
				}

				if (validationRule.getCode() != null) {
					if (!Util.isEmpty(convertedWhereClause))
						convertedWhereClause =  convertedWhereClause + " AND ";
					convertedWhereClause = convertedWhereClause + "(" + validationRule.getCode() + ")";

					if (!Util.isEmpty(context)) {
						convertedWhereClause = parseContext(convertedWhereClause, context);
					}
				}
			}

			Query query = new Query(Env.getCtx(), table, convertedWhereClause, null);
			query.setApplyAccessFilter(true, false)
			.setOnlyActiveRecords(true)
			.setParameters(convertedStatement.getParameters());

			if (isValidOrderBy(table, order)) {
				query.setOrderBy(order);
			}
			query.setQueryTimeout(DEFAULT_QUERY_TIMEOUT);
			int rowCount = query.count();
			int pageCount = 1;
			if (MAX_RECORDS_SIZE > 0 && (top > MAX_RECORDS_SIZE || top <= 0))
				top = MAX_RECORDS_SIZE;

			if (top > 0 && rowCount > top) {
				pageCount = (int)Math.ceil(rowCount / (double)top);
			} 
			query.setPageSize(top);
			query.setRecordstoSkip(skip);

			List<PO> list = query.list();
			JsonArray array = new JsonArray();
			if (list != null) {
				IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));
				
				HashMap<String, ArrayList<String>> includeParser = TypeConverterUtils.getIncludes(tableName, select, details);
				String[] includes = includeParser != null && includeParser.get(table.getTableName()) != null ? 
						includeParser.get(table.getTableName()).toArray(new String[includeParser.get(table.getTableName()).size()]) : 
						null;

				for(PO po : list) {
					JsonObject json = serializer.toJson(po, includes, null);
					if (!Util.isEmpty(details, true))
						loadDetails(po, json, details, includeParser);
					array.add(json);
				}
				JsonObject json = new JsonObject();
				json.addProperty("page-count", pageCount);
				json.addProperty("records-size", top);
				json.addProperty("skip-records", skip);
				json.addProperty("row-count", rowCount);
				json.addProperty("array-count", array.size());
				json.add("records", array);
				return Response.ok(json.toString())
						.header("X-Page-Count", pageCount)
						.header("X-Records-Size", top)
						.header("X-Skip-Records", skip)
						.header("X-Row-Count", rowCount)
						.header("X-Array-Count", array.size())
						.build();
			} else {
				JsonObject json = new JsonObject();
				json.add("records", array);
				return Response.ok(json.toString()).build();
			}
		} catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();

			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get POs with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}
	}
	
	private MValRule getValidationRule(String validationRuleID) {
		return (MValRule) TypeConverterUtils.getPO(MValRule.Table_Name, validationRuleID, false, false);
	}
	
	private String parseContext(String whereClause, String context) {
		String parsedWhereClause = whereClause;
		int windowNo = windowNoAtomic.getAndIncrement();

		for (String contextNameValue : context.split(CONTEXT_VARIABLES_SEPARATOR)) {
			String[] namevaluePair = contextNameValue.split(CONTEXT_NAMEVALUE_SEPARATOR);
			String contextName = namevaluePair[0];
			String contextValue = namevaluePair[1];
			
			if (!isValidContextValue(contextValue)) 
				continue;
			Env.setContext(Env.getCtx(), windowNo, contextName, contextValue);
		}
		
		parsedWhereClause = Env.parseContext(Env.getCtx(), windowNo, parsedWhereClause, false, true);
		Env.clearWinContext(windowNo);

		return parsedWhereClause;
	}
	
	/**
	 * Validates the context value to avoid
	 * potential SQL injection
	 * @param value
	 * @return
	 */
	private boolean isValidContextValue(String value) {
		final String sanitize = "^[A-Za-z0-9\\s\\-]+$";
		return Pattern.matches(sanitize, value);
	}

	@Override
	public Response create(String tableName, String jsonText) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
	
		Trx trx = Trx.get(Trx.createTrxName(), true);
		try {
			trx.start();
			Gson gson = new GsonBuilder().create();
			JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
			IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));
			PO po = serializer.fromJson(jsonObject, table);
			po.set_TrxName(trx.getTrxName());
			fireRestSaveEvent(po, PO_BEFORE_REST_SAVE, true);
			try {
				if (! po.validForeignKeys()) {
					String msg = CLogger.retrieveErrorString("Foreign key validation error");
					throw new EONEException(msg);
				}
				po.saveEx();
				fireRestSaveEvent(po, PO_AFTER_REST_SAVE, true);
			} catch (Exception ex) {
				trx.rollback();
				log.log(Level.SEVERE, ex.getMessage(), ex);
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error").append("Save error with exception: ").append(ex.getMessage()).build().toString())
						.build();
			}
			
			Map<String, JsonArray> detailMap = new LinkedHashMap<>();
			Set<String> fields = jsonObject.keySet();
			for(String field : fields) {
				JsonElement fieldElement = jsonObject.get(field);
				if (fieldElement != null && fieldElement.isJsonArray()) {
					MTable childTable = MTable.get(Env.getCtx(), field);
					if (childTable != null && childTable.getAD_Table_ID() > 0) {
						IPOSerializer childSerializer = IPOSerializer.getPOSerializer(field, MTable.getClass(field));
						JsonArray fieldArray = fieldElement.getAsJsonArray();
						JsonArray savedArray = new JsonArray();
						try {
							fieldArray.forEach(e -> {
								if (e.isJsonObject()) {
									JsonObject childJsonObject = e.getAsJsonObject();
									PO childPO = childSerializer.fromJson(childJsonObject, childTable);
									childPO.set_TrxName(trx.getTrxName());
									childPO.set_ValueOfColumn(tableName+"_ID", po.get_ID());
									fireRestSaveEvent(childPO, PO_BEFORE_REST_SAVE, true);
								if (! childPO.validForeignKeys()) {
										String msg = CLogger.retrieveErrorString("Foreign key validation error");
										throw new EONEException(msg);
									}
									childPO.saveEx();
									fireRestSaveEvent(childPO, PO_AFTER_REST_SAVE, true);
									childJsonObject = serializer.toJson(childPO);
									savedArray.add(childJsonObject);
								}
							});
							if (savedArray.size() > 0)
								detailMap.put(field, savedArray);
						} catch (Exception ex) {
							trx.rollback();
							log.log(Level.SEVERE, ex.getMessage(), ex);
							return Response.status(Status.INTERNAL_SERVER_ERROR)
									.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error").append("Save error with exception: ").append(ex.getMessage()).build().toString())
									.build();
						}
					}
				}
			}
			
			String error = runDocAction(po, jsonObject);
			if (Util.isEmpty(error, true)) {
				trx.commit(true);
			} else {
				trx.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Can't perform document action").append("Encounter exception during execution of document action: ").append(error).build().toString())
						.build();
			}

			po.load(trx.getTrxName());
			jsonObject = serializer.toJson(po);
			if (detailMap.size() > 0) {
				for(String childTableName : detailMap.keySet()) {
					JsonArray childArray = detailMap.get(childTableName);
					jsonObject.add(childTableName, childArray);
				}
			}
			return Response.status(Status.CREATED).entity(jsonObject.toString()).build();
		} catch (Exception ex) {
			trx.rollback();
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Server error").append("Server error with exception: ").append(ex.getMessage()).build().toString())
					.build();
		} finally {
			trx.close();
		}
	}

	@Override
	public Response update(String tableName, String id, String jsonText) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, true);
		if (po == null) {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
		
		Trx trx = Trx.get(Trx.createTrxName(), true);
		try {
			trx.start();
			Gson gson = new GsonBuilder().create();
			JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
			IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));
			po = serializer.fromJson(jsonObject, po);
			po.set_TrxName(trx.getTrxName());
			fireRestSaveEvent(po, PO_BEFORE_REST_SAVE, false);
			try {
				if (! po.validForeignKeys()) {
					String msg = CLogger.retrieveErrorString("Foreign key validation error");
					throw new EONEException(msg);
				}
				po.saveEx();
				fireRestSaveEvent(po, PO_AFTER_REST_SAVE, false);
			} catch (Exception ex) {
				trx.rollback();
				log.log(Level.SEVERE, ex.getMessage(), ex);
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error").append("Save error with exception: ").append(ex.getMessage()).build().toString())
						.build();
			}
			
			Map<String, JsonArray> detailMap = new LinkedHashMap<>();
			Set<String> fields = jsonObject.keySet();
			final int parentId = po.get_ID();
			for(String field : fields) {
				JsonElement fieldElement = jsonObject.get(field);
				if (fieldElement != null && fieldElement.isJsonArray()) {
					MTable childTable = MTable.get(Env.getCtx(), field);
					if (childTable != null && childTable.getAD_Table_ID() > 0) {									
						IPOSerializer childSerializer = IPOSerializer.getPOSerializer(field, MTable.getClass(field));
						JsonArray fieldArray = fieldElement.getAsJsonArray();
						JsonArray savedArray = new JsonArray();
						try {
							fieldArray.forEach(e -> {
								if (e.isJsonObject()) {
									JsonObject childJsonObject = e.getAsJsonObject();
									PO childPO = loadPO(field, childJsonObject);
									
									if (childPO == null) {
										childPO = childSerializer.fromJson(childJsonObject, childTable);
										childPO.set_ValueOfColumn(tableName+"_ID", parentId);
									} else {
										childPO = childSerializer.fromJson(childJsonObject, childPO);
									}
									childPO.set_TrxName(trx.getTrxName());
									fireRestSaveEvent(childPO, PO_BEFORE_REST_SAVE, false);
									if (! childPO.validForeignKeys()) {
										String msg = CLogger.retrieveErrorString("Foreign key validation error");
										throw new EONEException(msg);
									}
									childPO.saveEx();
									fireRestSaveEvent(childPO, PO_AFTER_REST_SAVE, false);
									childJsonObject = serializer.toJson(childPO);
									savedArray.add(childJsonObject);
								}
							});
							if (savedArray.size() > 0)
								detailMap.put(field, savedArray);
						} catch (Exception ex) {
							trx.rollback();
							log.log(Level.SEVERE, ex.getMessage(), ex);
							return Response.status(Status.INTERNAL_SERVER_ERROR)
									.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error").append("Save error with exception: ").append(ex.getMessage()).build().toString())
									.build();
						}
					}
				}
			}
			
			String error = runDocAction(po, jsonObject);
			if (Util.isEmpty(error, true)) {
				trx.commit(true);
			} else {
				trx.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Can't perform document action").append("Encounter exception during execution of document action: ").append(error).build().toString())
						.build();
			}
			
			po.load(trx.getTrxName());
			jsonObject = serializer.toJson(po);
			if (detailMap.size() > 0) {
				for(String field : detailMap.keySet()) {
					JsonArray child = detailMap.get(field);
					jsonObject.add(field, child);
				}
			}
			return Response.status(Status.OK).entity(jsonObject.toString()).build();
		} catch (Exception ex) {
			trx.rollback();
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Server error").append("Server error with exception: ").append(ex.getMessage()).build().toString())
					.build();
		} finally {
			trx.close();
		}
	}

	/**
	 * Fire the PO_BEFORE_REST_SAVE/PO_AFTER_REST_SAVE event, to catch and manipulate the object before the model beforeSave/afterSave
	 * @param po
	 */
	private void fireRestSaveEvent(PO po, String topic, boolean isNew) {
		Event event = EventManager.newEvent(topic,
				new EventProperty(EventManager.EVENT_DATA, po), new EventProperty("tableName", po.get_TableName()),
				new EventProperty("isNew", isNew));
		EventManager.getInstance().sendEvent(event);
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) event.getProperty(IEventManager.EVENT_ERROR_MESSAGES);
		if (errors != null && !errors.isEmpty())
			throw new EONEException(errors.get(0));
	}

	@Override
	public Response delete(String tableName, String id) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, true);
		if (po != null) {
			try {
				po.deleteEx(true);
				JsonObject json = new JsonObject();
				json.addProperty("msg", Msg.getMsg(Env.getCtx(), "Deleted"));
				return Response.ok(json.toString()).build();
			} catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage(), ex);
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Delete error").append("Delete error with exception: ").append(ex.getMessage()).build().toString())
						.build();
			}
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);

			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response getAttachments(String tableName, String id) {
		JsonArray array = new JsonArray();
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			MAttachment attachment = po.getAttachment();
			if (attachment != null) {
				for(MAttachmentEntry entry : attachment.getEntries()) {
					JsonObject entryJsonObject = new JsonObject();
					entryJsonObject.addProperty("name", entry.getName());
					if (!Util.isEmpty(entry.getContentType(),  true))
						entryJsonObject.addProperty("contentType", entry.getContentType());
					array.add(entryJsonObject);
				}
			}
			JsonObject json = new JsonObject();
			json.add("attachments", array);
			return Response.ok(json.toString()).build();
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response getAttachmentsAsZip(String tableName, String id) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			MAttachment attachment = po.getAttachment();
			if (attachment != null) {
				File zipFile = attachment.saveAsZip();
				if (zipFile != null) {
					FileStreamingOutput fso = new FileStreamingOutput(zipFile);
					return Response.ok(fso).build();
				}
			}
			return Response.status(Status.NO_CONTENT).build();
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response createAttachmentsFromZip(String tableName, String id, String jsonText) {
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
		
		boolean overwrite = false;
		JsonElement jsonElement = jsonObject.get("overwrite");
		if (jsonElement != null && jsonElement.isJsonPrimitive())
			overwrite = jsonElement.getAsBoolean();
		
		jsonElement = jsonObject.get("data");
		if (jsonElement == null || !jsonElement.isJsonPrimitive())
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("data property is mandatory").build().toString())
					.build();
		String base64Content = jsonElement.getAsString();
		if (Util.isEmpty(base64Content, true))
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("data property is mandatory").build().toString())
					.build();
		
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			byte[] data = DatatypeConverter.parseBase64Binary(base64Content);
			if (data == null || data.length == 0)
				return Response.status(Status.BAD_REQUEST)
						.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("Can't parse data").append("Can't parse data in Json content, not base64 encoded").build().toString())
						.build();
			MAttachment attachment = po.getAttachment();
			if (attachment == null)
				attachment = po.createAttachment();
			
			try (ZipInputStream stream = new ZipInputStream(new ByteArrayInputStream(data))) {
	            ZipEntry entry;
	            while ((entry = stream.getNextEntry()) != null) {
	            	String name = entry.getName();
	            	for(int i = 0; i < attachment.getEntryCount(); i++) {
	    				MAttachmentEntry e = attachment.getEntry(i);
	    				if (e.getName().equals(name)) {
	    					if (overwrite) {
	    						attachment.deleteEntry(i);
	    						break;
	    					} else {
	    						return Response.status(Status.CONFLICT)
	    								.entity(new ErrorBuilder().status(Status.CONFLICT).title("Duplicate file name").append("Duplicate file name: ").append(name).build().toString())
	    								.build();
	    					}
	    				}
	    			}
	            	
	            	byte[] buffer = new byte[2048];
	            	ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int len;
                    while ((len = stream.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                    attachment.addEntry(name, bos.toByteArray());
	            }
	            attachment.saveEx();
	        } catch (Exception ex) {
	        	log.log(Level.SEVERE, ex.getMessage(), ex);
	        	return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Server error").append("Server error with exception: ").append(ex.getMessage()).build().toString())
						.build();
			}
															
			return Response.status(Status.CREATED).build();
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response getAttachmentEntry(String tableName, String id, String fileName) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			MAttachment attachment = po.getAttachment();
			if (attachment != null) {
				for(MAttachmentEntry entry : attachment.getEntries()) {
					if (entry.getName().equals(fileName)) {
						try {
							Path tempPath = Files.createTempDirectory(tableName);
							File tempFolder = tempPath.toFile();
							File zipFile = new File(tempFolder, fileName);
							zipFile = entry.getFile(zipFile);
							FileStreamingOutput fso = new FileStreamingOutput(zipFile);
							return Response.ok(fso).build();
						} catch (IOException ex) {
							log.log(Level.SEVERE, ex.getMessage(), ex);
							return Response.status(Status.INTERNAL_SERVER_ERROR)
									.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("IO error").append("IO error with exception: ").append(ex.getMessage()).build().toString())
									.build();
						}
					}
				}
			}
			return Response.status(Status.NO_CONTENT).build();
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response addAttachmentEntry(String tableName, String id, String jsonText) {
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
		
		JsonElement jsonElement = jsonObject.get("name");
		if (jsonElement == null || !jsonElement.isJsonPrimitive())
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("name property is mandatory").build().toString())
					.build();
		String fileName = jsonElement.getAsString();
		if (Util.isEmpty(fileName, true))
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("name property is mandatory").build().toString())
					.build();
		
		jsonElement = jsonObject.get("data");
		if (jsonElement == null || !jsonElement.isJsonPrimitive())
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("data property is mandatory").build().toString())
					.build();
		String base64Content = jsonElement.getAsString();
		if (Util.isEmpty(base64Content, true))
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("data property is mandatory").build().toString())
					.build();
		
		boolean overwrite = false;
		jsonElement = jsonObject.get("overwrite");
		if (jsonElement != null && jsonElement.isJsonPrimitive())
			overwrite = jsonElement.getAsBoolean();
		
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			byte[] data = DatatypeConverter.parseBase64Binary(base64Content);
			if (data == null || data.length == 0)
				return Response.status(Status.BAD_REQUEST)
						.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("Can't parse data").append("Can't parse data in Json content, not base64 encoded").build().toString())
						.build();
			MAttachment attachment = po.getAttachment();
			if (attachment == null)
				attachment = po.createAttachment();
			
			for(int i = 0; i < attachment.getEntryCount(); i++) {
				MAttachmentEntry entry = attachment.getEntry(i);
				if (entry.getName().equals(fileName)) {
					if (overwrite) {
						attachment.deleteEntry(i);
						break;
					} else {
						return Response.status(Status.CONFLICT)
								.entity(new ErrorBuilder().status(Status.CONFLICT).title("Duplicate file name").append("Duplicate file name: ").append(fileName).build().toString())
								.build();
					}
				}
			}		
			
			try {
				attachment.addEntry(fileName, data);
				attachment.saveEx();
			} catch (Exception ex) {
				log.log(Level.SEVERE, ex.getMessage(), ex);
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error").append("Save error with exception: ").append(ex.getMessage()).build().toString())
						.build();
			}
			return Response.status(Status.CREATED).build();
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);

			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response deleteAttachments(String tableName, String id) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			MAttachment attachment = po.getAttachment();
			if (attachment != null) {
				try {
					attachment.deleteEx(true);
				} catch (Exception ex) {
					log.log(Level.SEVERE, ex.getMessage(), ex);
					return Response.status(Status.INTERNAL_SERVER_ERROR)
							.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Delete error").append("Delete error with exception: ").append(ex.getMessage()).build().toString())
							.build();
				}
				JsonObject json = new JsonObject();
				json.addProperty("msg", Msg.getMsg(Env.getCtx(), "Deleted"));
				return Response.ok(json.toString()).build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("No attachments").append("No attachment is found for record with id ").append(id).build().toString())
						.build();
			}
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	@Override
	public Response deleteAttachmentEntry(String tableName, String id, String fileName) {
		MTable table = MTable.get(Env.getCtx(), tableName);
		if (table == null || table.getAD_Table_ID()==0)
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid table name").append("No match found for table name: ").append(tableName).build().toString())
					.build();
		
		PO po = TypeConverterUtils.getPO(tableName, id, true, false);
		if (po != null) {
			MAttachment attachment = po.getAttachment();
			if (attachment != null) {
				int i = 0;
				for(MAttachmentEntry entry : attachment.getEntries()) {
					if (entry.getName().equals(fileName)) {
						if (attachment.deleteEntry(i)) {
							try {
								attachment.saveEx();
							} catch (Exception ex) {
								log.log(Level.SEVERE, ex.getMessage(), ex);
								return Response.status(Status.INTERNAL_SERVER_ERROR)
										.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error").append("Save error with exception: ").append(ex.getMessage()).build().toString())
										.build();
							}
							JsonObject json = new JsonObject();
							json.addProperty("msg", Msg.getMsg(Env.getCtx(), "Deleted"));
							return Response.ok(json.toString()).build();
						} else {
							return Response.status(Status.INTERNAL_SERVER_ERROR)
									.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Fail to remove attachment entry").build().toString())
									.build();
						}
					}
					i++;
				}
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("No matching attachment entry").append("No attachment entry is found for name: ").append(fileName).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("No attachments").append("No attachment is found for record with id ").append(id).build().toString())
						.build();
			}
		} else {
			po = TypeConverterUtils.getPO(tableName, id, false, false);
			if (po != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for record with id ").append(id).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(id).build().toString())
						.build();
			}
		}
	}

	private void loadDetails(PO po, JsonObject jsonObject, String details, HashMap<String, ArrayList<String>> includeParser) {
		if (Util.isEmpty(details, true))
			return;
		
		String[] tableNames = details.split("[,]");
		String keyColumn = po.get_TableName() + "_ID";
		String[] includes;
		for(String tableName : tableNames) {
			MTable table = MTable.get(Env.getCtx(), tableName);
			if (table == null)
				continue;
			
			Query query = new Query(Env.getCtx(), table, keyColumn + "=?", null);
			query.setApplyAccessFilter(true, false)
				 .setOnlyActiveRecords(true);
			List<PO> childPOs = query.setParameters(po.get_ID()).list();
			if (childPOs != null && childPOs.size() > 0) {
				JsonArray childArray = new JsonArray();
				IPOSerializer serializer = IPOSerializer.getPOSerializer(tableName, MTable.getClass(tableName));
				includes = includeParser != null && includeParser.get(table.getTableName()) != null ? 
						includeParser.get(table.getTableName()).toArray(new String[includeParser.get(table.getTableName()).size()]) : 
						null;
				
				for(PO child : childPOs) {							
					JsonObject childJsonObject = serializer.toJson(child, includes, new String[] {keyColumn, "model-name"});
					childArray.add(childJsonObject);
				}
				jsonObject.add(tableName, childArray);
			}
		}
	}
	
	
	private PO loadPO(String tableName, JsonObject jsonObject) {
		PO po = null;
		String idColumn = tableName + "_ID";
		String uidColumn = PO.getUUIDColumnName(tableName);
		JsonElement idElement = jsonObject.get("id");											
		if (idElement != null && idElement.isJsonPrimitive()) {
			Query query = new Query(Env.getCtx(), tableName, idColumn + "=?", null);
			query.setApplyAccessFilter(true, false);
			po = query.setParameters(idElement.getAsInt()).first();
		}
		else {
			JsonElement uidElement = jsonObject.get("uid");
			if (uidElement != null && uidElement.isJsonPrimitive()) {
				Query query = new Query(Env.getCtx(), tableName, uidColumn + "=?", null);
				query.setApplyAccessFilter(true, false);
				po = query.setParameters(uidElement.getAsString()).first();
			}
		}
		return po;
	}

	private String runDocAction(PO po, JsonObject jsonObject) {
		
		return null;
	}
	
	private boolean isValidOrderBy(MTable table, String orderBy) {
		if (!Util.isEmpty(orderBy, true)) {
			String[] columnNames = orderBy.split("[,]");
			for (String columnName : columnNames) {
				columnName = columnName.trim();

				if (columnName.contains(" ")) {
					String[] names = columnName.split(" ");
					columnName = names[0];
					String orderPreference = names[1];
					if (names.length > 2 || (!"asc".equals(orderPreference.toLowerCase()) && !"desc".equals(orderPreference.toLowerCase()))) {
						log.log(Level.WARNING, "Invalid order by clause.");
						return false;
					}
				}
				if (table.getColumnIndex(columnName) < 0) {
					log.log(Level.WARNING, "Column: " + columnName + " is not a valid column to be ordered by");
					return false;
				}
			}

			return true;
		}

		return false;
	}
}
