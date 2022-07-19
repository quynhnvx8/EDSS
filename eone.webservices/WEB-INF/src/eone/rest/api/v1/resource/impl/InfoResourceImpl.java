
package eone.rest.api.v1.resource.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eone.base.model.MInfoColumn;
import eone.base.model.MInfoWindow;
import eone.base.model.Query;
import eone.rest.api.json.EONERestException;
import eone.rest.api.json.IPOSerializer;
import eone.rest.api.json.TypeConverterUtils;
import eone.rest.api.json.filter.ConvertedQuery;
import eone.rest.api.json.filter.IQueryConverter;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.resource.InfoResource;
import eone.rest.api.v1.resource.info.InfoWindow;
import eone.rest.api.v1.resource.info.QueryResponse;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Util;

/**
 * @author hengsin
 *
 */
public class InfoResourceImpl implements InfoResource {

	private final static CLogger log = CLogger.getCLogger(InfoResourceImpl.class);
	
	private static final int DEFAULT_QUERY_TIMEOUT = 60 * 2;
	private static final int DEFAULT_PAGE_SIZE = 100;

	/**
	 * default constructor 
	 */
	public InfoResourceImpl() {
	}

	@Override
	public Response getInfoWindows(String filter) {
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			ConvertedQuery convertedStatement = converter.convertStatement(MInfoWindow.Table_Name, filter);
			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedStatement.getWhereClause());

			Query query = new Query(Env.getCtx(), MInfoWindow.Table_Name, convertedStatement.getWhereClause(), null);
			query.setOnlyActiveRecords(true).setApplyAccessFilter(true);
			query.setQueryTimeout(DEFAULT_QUERY_TIMEOUT);
			query.setParameters(convertedStatement.getParameters());

			List<MInfoWindow> infoWindows = query.setOrderBy("AD_InfoWindow.Name").list();
			JsonArray array = new JsonArray();
			IPOSerializer serializer = IPOSerializer.getPOSerializer(MInfoWindow.Table_Name, MInfoWindow.class);
			for(MInfoWindow infoWindow : infoWindows) {
				JsonObject json = serializer.toJson(infoWindow);
				json.addProperty("slug", TypeConverterUtils.slugify(infoWindow.getName()));
				array.add(json);
			}
			JsonObject json = new JsonObject();
			json.add("infowindows", array);
			return Response.ok(json.toString()).build();
		} catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();
			
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get InfoWindows with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}
	}

	
	@Override
	public Response getInfoWindowRecords(String infoSlug, String parameters, String whereClause, String orderBy, int pageNo) {
		Query query = new Query(Env.getCtx(), MInfoWindow.Table_Name, "slugify(name)=?", null);
		query.setOnlyActiveRecords(true).setApplyAccessFilter(true);
		MInfoWindow infoWindowModel = query.setParameters(infoSlug).first();
		if (infoWindowModel == null) {
			query.setApplyAccessFilter(false);
			infoWindowModel = query.setParameters(infoSlug).first();
			if (infoWindowModel != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for info window: ").append(infoSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid info window name").append("No match found for info window name: ").append(infoSlug).build().toString())
						.build();
			}
		}
		
		
		Map<String, JsonElement> paraMap = new HashMap<String, JsonElement>();
		if (!Util.isEmpty(parameters)) {
			Gson gson = new GsonBuilder().create();
			JsonObject jso = gson.fromJson(parameters, JsonObject.class);
			for(String key : jso.keySet()) {
				JsonElement element = jso.get(key);
				if (element != null) {
					paraMap.put(key, element);
				}
			}
		}
		
		InfoWindow infoWindow = new InfoWindow(infoWindowModel, whereClause, orderBy, true);
		infoWindow.setQueryParameters(paraMap);
		QueryResponse queryResponse = infoWindow.executeQuery(DEFAULT_PAGE_SIZE, pageNo, DEFAULT_QUERY_TIMEOUT);
		JsonArray array = queryResponse.getRecords();
		JsonObject json = new JsonObject();
		json.add("infowindow-records", array);
		ResponseBuilder response = Response.ok(json.toString());
		if (array.size() > 0) {
			pageNo = queryResponse.getPageNo();
			response.header("X-Page", pageNo);
			response.header("X-Per-Page", DEFAULT_PAGE_SIZE);
			if (queryResponse.isHasNextPage()) {
				response.header("X-Next-Page", pageNo+1);
			}
			if (pageNo > 1)
				response.header("X-Prev-Page: 1", pageNo-1);						
		}
		
		return response.build();
	}

	@Override
	public Response getInfoWindowColumns(String infoSlug) {
		Query query = new Query(Env.getCtx(), MInfoWindow.Table_Name, "slugify(name)=?", null);
		query.setOnlyActiveRecords(true).setApplyAccessFilter(true);
		MInfoWindow infoWindowModel = query.setParameters(infoSlug).first();
		if (infoWindowModel == null) {
			query.setApplyAccessFilter(false);
			infoWindowModel = query.setParameters(infoSlug).first();
			if (infoWindowModel != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for info window: ").append(infoSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid info window name").append("No match found for info window name: ").append(infoSlug).build().toString())
						.build();
			}
		}
		
		
		MInfoColumn[] infoColumns = infoWindowModel.getInfoColumns();
		JsonArray array = new JsonArray();
		IPOSerializer serializer = IPOSerializer.getPOSerializer(MInfoColumn.Table_Name, MInfoColumn.class);
		for(MInfoColumn infoColumn : infoColumns) {
			JsonObject json = serializer.toJson(infoColumn);
			array.add(json);
		}
		JsonObject json = new JsonObject();
		json.add("infowindowcolumns", array);
		return Response.ok(json.toString()).build();
	}

	@Override
	public Response getInfoWindowProcesses(String infoSlug) {
		
		return Response.ok("OK".toString()).build();
	}

	@Override
	public Response getInfoWindowRelateds(String infoSlug) {
		return null;
	}

}
