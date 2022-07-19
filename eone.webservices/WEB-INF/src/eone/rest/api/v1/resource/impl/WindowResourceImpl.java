/**********************************************************************
* This file is part of iDempiere ERP Open Source                      *
* http://www.idempiere.org                                            *
*                                                                     *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Trek Global Corporation                                           *
* - Heng Sin Low                                                      *
**********************************************************************/
package eone.rest.api.v1.resource.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import eone.base.model.DataStatusEvent;
import eone.base.model.DataStatusListener;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.GridWindow;
import eone.base.model.MField;
import eone.base.model.MForm;
import eone.base.model.MQuery;
import eone.base.model.MTab;
import eone.base.model.MTable;
import eone.base.model.MWindow;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.rest.api.json.EONERestException;
import eone.rest.api.json.IGridTabSerializer;
import eone.rest.api.json.IPOSerializer;
import eone.rest.api.json.TypeConverterUtils;
import eone.rest.api.json.filter.ConvertedQuery;
import eone.rest.api.json.filter.IQueryConverter;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.util.GridTabPaging;
import eone.rest.api.util.Paging;
import eone.rest.api.v1.resource.WindowResource;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;
import eone.util.Trx;
import eone.util.Util;

/**
 * 
 * @author hengsin
 *
 */
public class WindowResourceImpl implements WindowResource {

	private static final int DEFAULT_PAGE_SIZE = 100;
	private static final CLogger log = CLogger.getCLogger(WindowResourceImpl.class);
	
	/** Default select values **/
	private static final String[] WINDOW_SELECT_COLUMNS = new String[] {"AD_Window_ID", "AD_Window_UU", "Name", "Description", "Help", "WindowType", "EntityType"};
	private static final String[] TAB_SELECT_COLUMNS = new String[] {"AD_Tab_ID", "AD_Tab_UU", "Name", "Description", "Help", "EntityType", "SeqNo", "TabLevel"};
	private static final String[] FIELD_SELECT_COLUMNS = new String[] {"AD_Field_ID", "AD_Field_UU", "Name", "Description", "Help", "EntityType", "AD_Reference_ID", "AD_Column_ID", "MandatoryLogic"};
	
	public WindowResourceImpl() {
	}

	@Override
	public Response getWindows(String filter, String details, String select) {
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			JsonArray windowArray = new JsonArray();
			ConvertedQuery convertedStatement = converter.convertStatement(MForm.Table_Name, filter);
			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedStatement.getWhereClause());

			Query query = new Query(Env.getCtx(), MWindow.Table_Name, convertedStatement.getWhereClause(), null);
			query.setApplyAccessFilter(true).setOnlyActiveRecords(true).setOrderBy("Name");
			query.setParameters(convertedStatement.getParameters());

			JsonObject json = new JsonObject();
			json.add("windows", windowArray);
			return Response.ok(json.toString()).build();
		} catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();
			
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get windows with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}
	}

	@Override
	public Response getTabs(String windowSlug) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		IPOSerializer serializer = IPOSerializer.getPOSerializer(MWindow.Table_Name, MTable.getClass(MWindow.Table_Name));
		JsonObject windowJsonObject = serializer.toJson(window, WINDOW_SELECT_COLUMNS, null);
		windowJsonObject.addProperty("slug", TypeConverterUtils.slugify(window.getName()));
		windowJsonObject.add("tabs", getWindowTabs(window, null, false));
		
		return Response.ok(windowJsonObject.toString()).build();
	}
	
	private JsonArray getWindowTabs(MWindow window, HashMap<String, ArrayList<String>> includes, boolean includeFields) {
		MTab[] tabs = window.getTabs(false, null);
		JsonArray tabArray = new JsonArray();
		IPOSerializer tabSerializer = IPOSerializer.getPOSerializer(MTab.Table_Name, MTable.getClass(MTab.Table_Name));
		
		String[] tabSelect = TAB_SELECT_COLUMNS;
		String[] fieldSelect = FIELD_SELECT_COLUMNS;
		if (includes != null) {
			if (includes.get(MTab.Table_Name) != null)
				tabSelect = includes.get(MTab.Table_Name).toArray(new String[includes.get(MTab.Table_Name).size()]);
			
			if (includeFields && includes.get(MField.Table_Name) != null)
				fieldSelect =  includes.get(MField.Table_Name).toArray(new String[includes.get(MField.Table_Name).size()]);
		}
	
		
		for(MTab tab : tabs) {
			JsonObject tabJsonObject = tabSerializer.toJson(tab, tabSelect, null);
			tabJsonObject.addProperty("slug", TypeConverterUtils.slugify(tab.getName()));
			
			if (includeFields) {
				MField[] fields = tab.getFields(false, null);
				JsonArray fieldArray = new JsonArray();
				IPOSerializer serializer = IPOSerializer.getPOSerializer(MField.Table_Name, MTable.getClass(MField.Table_Name));
				for(MField field : fields) {
					if (!field.isDisplayed())
						continue;
					JsonObject jsonObject = serializer.toJson(field, fieldSelect, null);
					fieldArray.add(jsonObject);
				}
				tabJsonObject.add("fields", fieldArray);
			}

			tabArray.add(tabJsonObject);
		}
		return tabArray;
	}

	@Override
	public Response getTabFields(String windowSlug, String tabSlug, String filter) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		
		MTab[] tabs = window.getTabs(false, null);
		int tabId=0;
		for(MTab tab : tabs) {
			if (TypeConverterUtils.slugify(tab.getName()).equals(tabSlug)) {
				tabId = tab.getAD_Tab_ID();
				break;
			}
		}
		if (tabId==0) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid tab name").append("No match found for tab name: ").append(tabSlug).build().toString())
					.build();
		}
		
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			StringBuilder whereClause = new StringBuilder("AD_Tab_ID=?");
			ConvertedQuery convertedStatement = converter.convertStatement(MForm.Table_Name, filter);
			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedStatement.getWhereClause());
			
			if (!Util.isEmpty(filter, true)) {
				whereClause.append(" AND (").append(convertedStatement.getWhereClause()).append(")");
			}
			query = new Query(Env.getCtx(), MField.Table_Name, whereClause.toString(), null);
			convertedStatement.addParameter(0, tabId);
			
			List<MField> fields = query.setParameters(convertedStatement.getParameters()).list();
			JsonArray fieldArray = new JsonArray();
			IPOSerializer serializer = IPOSerializer.getPOSerializer(MField.Table_Name, MTable.getClass(MField.Table_Name));
			for(MField field : fields ) {
				JsonObject jsonObject = serializer.toJson(field, FIELD_SELECT_COLUMNS, null);
				fieldArray.add(jsonObject);
			}

			JsonObject json = new JsonObject();
			json.add("fields", fieldArray);
			return Response.ok(json.toString()).build();
		}  catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();
			
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get TabFields with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}
	}
	
	@Override
	public Response getWindowRecords(String windowSlug, String filter, String sortColumn, int pageNo) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (gridTab.getTabLevel()==0) {
				QueryResult queryResult = query(gridTab, filter, sortColumn, pageNo);
				JsonObject json = new JsonObject();
				json.addProperty("page-count", queryResult.pageCount);
				json.addProperty("page-size", queryResult.pageSize);
				json.addProperty("page-number", queryResult.pageNo);
				json.addProperty("row-count", queryResult.rowCount);
				json.add("window-records", queryResult.jsonArray);
				return Response.ok(json.toString())
						.header("X-Page-Count", queryResult.pageCount)
						.header("X-Page-Size", queryResult.pageSize)
						.header("X-Page-Number", queryResult.pageNo)
						.header("X-Row-Count", queryResult.rowCount)
						.build();
			}
		}
		
		return Response.status(Status.NOT_FOUND)
				.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
				.build();
	}
	
	@Override
	public Response getWindowRecord(String windowSlug, int recordId, String details) {
		return getTabRecord(windowSlug, null, recordId, details);
	}
	
	@Override
	public Response getChildTabRecords(String windowSlug, String tabSlug, int recordId, String childTabSlug,
			String filter, String sortColumn, int pageNo) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		
		GridTab parentTab = null;
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (TypeConverterUtils.slugify(gridTab.getName()).equals(tabSlug)) {
				parentTab = gridTab;
				load(gridWindow, gridTab, recordId);
				break;
			}
		}
		
		if (parentTab == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid tab name").append("No match found for tab name: ").append(tabSlug).build().toString())
					.build();
		}
		
		if (parentTab.getRowCount() == 0) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(recordId).build().toString())
					.build();
		}
		
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (TypeConverterUtils.slugify(gridTab.getName()).equals(childTabSlug)) {
				if (gridTab.getTabLevel() != (parentTab.getTabLevel()+1)) {
					return Response.status(Status.BAD_REQUEST).entity(childTabSlug + " is not child tab of " + tabSlug).build();
				}
				if (!gridWindow.isTabInitialized(i))
					gridWindow.initTab(i);
				QueryResult queryResult = query(gridTab, filter, sortColumn, pageNo);
				JsonObject json = new JsonObject();
				json.addProperty("page-count", queryResult.pageCount);
				json.addProperty("page-size", queryResult.pageSize);
				json.addProperty("page-number", queryResult.pageNo);
				json.addProperty("row-count", queryResult.rowCount);
				json.add("childtab-records", queryResult.jsonArray);
				return Response.ok(json.toString())
						.header("X-Page-Count", queryResult.pageCount)
						.header("X-Page-Size", queryResult.pageSize)
						.header("X-Page-Number", queryResult.pageNo)
						.header("X-Row-Count", queryResult.rowCount)
						.build();
			}
		}
				
		return Response.status(Status.NOT_FOUND)
				.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid tab name").append("No match found for tab name: ").append(childTabSlug).build().toString())
				.build();
	}
	
	@Override
	public Response getTabRecord(String windowSlug, String tabSlug, int recordId, String details) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		JsonObject jsonObject = loadTabRecord(window, tabSlug, recordId, details);
		
		if (jsonObject != null)
			return Response.ok(jsonObject.toString()).build();
		else
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(recordId).build().toString())
					.build();
	}
	
	@Override
	public Response updateWindowRecord(String windowSlug, int recordId, String jsonText) {
		return updateTabRecord(windowSlug, null, recordId, jsonText);
	}

	@Override
	public Response createWindowRecord(String windowSlug, String jsonText) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		
		Env.setContext(Env.getCtx(), 1, "IsSOTrx", window.isSOTrx());
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		Trx trx = Trx.get(Trx.createTrxName(), true);
		try {
			trx.start();
			return createTabRecord(gridWindow, null, null, jsonObject, trx);
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
	public Response deleteWindowRecord(String windowSlug, int recordId) {
		return deleteTabRecord(windowSlug, null, recordId);
	}

	@Override
	public Response updateTabRecord(String windowSlug, String tabSlug, int recordId, String jsonText) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		
		Env.setContext(Env.getCtx(), 1, "IsSOTrx", window.isSOTrx());
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);		
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		Trx trx = Trx.get(Trx.createTrxName(), true);
		try {
			trx.start();
			return updateTabRecord(gridWindow, tabSlug, recordId, jsonObject, trx);
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
	public Response createChildTabRecord(String windowSlug, String tabSlug, int recordId, String childTabSlug,
			String jsonText) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		Env.setContext(Env.getCtx(), 1, "IsSOTrx", window.isSOTrx());
		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = gson.fromJson(jsonText, JsonObject.class);
		GridTab parentTab = null;
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (TypeConverterUtils.slugify(gridTab.getName()).equals(tabSlug)) {
				parentTab = gridTab;
				load(gridWindow, gridTab, recordId);
				break;
			}
		}
		
		if (parentTab == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid tab name").append("No match found for tab name: ").append(tabSlug).build().toString())
					.build();
		}
		
		if (parentTab.getRowCount() == 0) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id ").append(recordId).build().toString())
					.build();
		}
		
		Trx trx = Trx.get(Trx.createTrxName(), true);
		try {
			trx.start();
			return createTabRecord(gridWindow, parentTab, childTabSlug, jsonObject, trx);
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
	public Response deleteTabRecord(String windowSlug, String tabSlug, int recordId) {
		Query query = new Query(Env.getCtx(), MWindow.Table_Name, "slugify(name)=?", null);
		query.setApplyAccessFilter(true).setOnlyActiveRecords(true);
		query.setParameters(windowSlug);
		MWindow window = query.first();
		if (window == null) {
			query.setApplyAccessFilter(false);
			window = query.first();
			if (window != null) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for window: ").append(windowSlug).build().toString())
						.build();
			} else {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid window name").append("No match found for window name: ").append(windowSlug).build().toString())
						.build();
			}
		}
		
		
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if ((gridTab.getTabLevel()==0 && Util.isEmpty(tabSlug, true)) || 
				(TypeConverterUtils.slugify(gridTab.getName()).equals(tabSlug))) {
				if (gridTab.isReadOnly() || !gridTab.isDeleteRecord()) {
					return Response.status(Status.FORBIDDEN)
							.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Delete not allow").append("Delete not allow for tab: ").append(TypeConverterUtils.slugify(gridTab.getName())).build().toString())
							.build();
				}
				load(gridWindow, gridTab, recordId);
				if (gridTab.getRowCount() < 1) {
					return Response.status(Status.NOT_FOUND)
							.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id: ").append(recordId).build().toString())
							.build();
				} else if (gridTab.getRowCount() > 1) {
					return Response.status(Status.BAD_REQUEST)
							.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("More than 1 match").append("More than 1 matching record for id: ").append(recordId).build().toString())
							.build();
				}
				ErrorDataStatusListener edsl = new ErrorDataStatusListener();
				gridTab.getTableModel().addDataStatusListener(edsl);;
				try {
					if (gridTab.dataDelete()) {
						return Response.status(Status.OK).build();
					} else {
						String error = edsl.getError();
						return Response.status(Status.INTERNAL_SERVER_ERROR)
								.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Delete error")
										.append(!Util.isEmpty(error) ? "Server error with exception: " : "")
										.append(!Util.isEmpty(error) ? error : "")
										.build().toString())
								.build();
					}
				} finally {
					gridTab.getTableModel().removeDataStatusListener(edsl);
				}
			}
		}
		
		return Response.status(Status.NOT_FOUND)
				.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid tab name").append("No match found for tab name: ").append(tabSlug).build().toString())
				.build();
	}
	
	private QueryResult query(GridTab gridTab, String filter, String sortColumn, int pageNo) {
		IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(gridTab.getAD_Tab_UU());
		if (!Util.isEmpty(filter, true)) {
			MQuery gridTabQuery = new MQuery(gridTab.getTableName());
			gridTabQuery.addRestriction(filter);
			gridTab.setQuery(gridTabQuery);
			gridTab.query(false);
		} else {
			gridTab.query(false);
		}
		if (!Util.isEmpty(sortColumn, true)) {
			boolean ascending = true;
			if (sortColumn.startsWith("!")) {
				sortColumn = sortColumn.substring(1);
				ascending = false;
			}
			GridField[] fields = gridTab.getTableModel().getFields();
			for(int i = 0; i < fields.length; i++) {
				if (fields[i].getColumnName().equals(sortColumn)) {
					gridTab.getTableModel().sort(i, ascending);
					break;
				}
			}
		}
		JsonArray jsonArray = new JsonArray();
		Paging paging = new Paging(gridTab.getRowCount(), DEFAULT_PAGE_SIZE);
		GridTabPaging gridTabPaging = new GridTabPaging(gridTab, paging);
		if (pageNo > 0)
			paging.setActivePage(pageNo);
		int pageRowCount = gridTabPaging.getSize();
		for(int j = 0; j < pageRowCount; j++) {
			gridTabPaging.setCurrentRow(j);
			JsonObject jsonObject = serializer.toJson(gridTab);
			jsonObject.addProperty("slug", TypeConverterUtils.slugify(gridTab.getName()));
			jsonArray.add(jsonObject);
		}
		QueryResult queryResult = new QueryResult();
		queryResult.jsonArray = jsonArray;
		queryResult.rowCount = gridTab.getRowCount();
		queryResult.pageNo = paging.getActivePage();
		queryResult.pageCount = paging.getPageCount();
		queryResult.pageSize = paging.getPageSize();
		return queryResult;
	}
	
	private class QueryResult {
		JsonArray jsonArray;
		int rowCount;
		int pageNo;
		int pageCount;
		int pageSize;
	}
	
	private JsonObject loadTabRecord(MWindow window, String tabSlug, int recordId, String details) {
		JsonObject jsonObject = null;
		List<String> detailList = new ArrayList<>();
		if (!Util.isEmpty(details, true)) {
			String[] detailArray = details.split("[,]");
			detailList = Arrays.asList(detailArray);
		}
		GridWindow gridWindow = GridWindow.get(Env.getCtx(), 1, window.getAD_Window_ID());
		GridTab headerTab = null;
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (gridTab.getTabLevel()==0 && Util.isEmpty(tabSlug, true)) {
				if (!gridWindow.isTabInitialized(i))
					gridWindow.initTab(i);
				IGridTabSerializer serializer = load(gridWindow, gridTab, recordId);
				if (gridTab.getRowCount()==1) {
					jsonObject = serializer.toJson(gridTab);
					headerTab = gridTab;
				} else {
					break;
				}
			} else if (!Util.isEmpty(tabSlug, true) && headerTab == null) {
				String slug = TypeConverterUtils.slugify(gridTab.getName());
				if (slug.equals(tabSlug)) {
					if (!gridWindow.isTabInitialized(i))
						gridWindow.initTab(i);
					IGridTabSerializer serializer = load(gridWindow, gridTab, recordId);
					if (gridTab.getRowCount()==1) {
						jsonObject = serializer.toJson(gridTab);
						headerTab = gridTab;
					} else {
						break;
					}
				}
			} else if (headerTab != null && gridTab.getTabLevel()==(headerTab.getTabLevel()+1)) {
				String slug = TypeConverterUtils.slugify(gridTab.getName());
				if (detailList.contains(slug)) {
					if (!gridWindow.isTabInitialized(i))
						gridWindow.initTab(i);
					JsonArray jsonArray = new JsonArray();
					gridTab.query(false);						
					if (gridTab.getRowCount() > 0) {
						IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(gridTab.getAD_Tab_UU());
						for(int j = 0; j < gridTab.getRowCount(); j++) {
							if (j > 0)
								gridTab.setCurrentRow(j);
							JsonObject childJsonObject = serializer.toJson(gridTab);
							jsonArray.add(childJsonObject);
						}
					}											
					jsonObject.add(slug, jsonArray);
				}
			} else if (headerTab != null && gridTab.getTabLevel() < headerTab.getTabLevel()) {
				break;
			} else if (headerTab != null && gridTab.getTabLevel() == headerTab.getTabLevel() && headerTab.getTabLevel()>0) {
				break;
			}
		}
		return jsonObject;
	}

	private IGridTabSerializer load(GridWindow gridWindow, GridTab gridTab, int recordId) {
		IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(gridTab.getAD_Tab_UU());
		if (gridTab.getTabLevel() == 0) {
			MQuery gridTabQuery = new MQuery(gridTab.getTableName());
			gridTabQuery.addRestriction(gridTab.getTableModel().getKeyColumnName(), "=", recordId);
			gridTab.setQuery(gridTabQuery);
			gridTab.query(false);
		} else {
			List<GridTab> parents = new ArrayList<>();
			GridTab parent = gridTab.getParentTab();
			while(parent != null) {
				parents.add(parent);
				parent = parent.getParentTab();
			}
			List<Integer> parentIds = new ArrayList<>();
			GridTab currentChild = gridTab;
			int currentChildId = recordId;
			for(GridTab p : parents) {
				int index = gridWindow.getTabIndex(currentChild);
				if (!gridWindow.isTabInitialized(index))
					gridWindow.initTab(index);
				String linkColumn = currentChild.getLinkColumnName();
				String keyColumn = currentChild.getTableModel().getKeyColumnName();
				int parentId = DB.getSQLValueEx(null, "SELECT " + linkColumn + " FROM " + currentChild.getTableName() + " WHERE " + keyColumn + "=?", currentChildId);
				parentIds.add(parentId);
				currentChild = p;
				currentChildId = parentId;
			}
			for(int i = parents.size()-1; i >= 0; i--) {
				GridTab p = parents.get(i);
				int index = gridWindow.getTabIndex(p);
				if (!gridWindow.isTabInitialized(index))
					gridWindow.initTab(index);
				int id = parentIds.get(i);
				MQuery query = new MQuery();
				query.addRestriction(p.getTableModel().getKeyColumnName(), "=", id);
				p.setQuery(query);
				p.query(false);
			}
			
			if (!gridTab.isCurrent())
				gridTab.query(false);
			MQuery gridTabQuery = new MQuery(gridTab.getTableName());
			gridTabQuery.addRestriction(gridTab.getTableModel().getKeyColumnName(), "=", recordId);
			gridTab.setQuery(gridTabQuery);
			gridTab.query(false);
		}
		return serializer;
	}
	
	private Response updateTabRecord(GridWindow gridWindow, String tabSlug, int recordId, JsonObject jsonObject, Trx trx)
			throws SQLException {
		Response errorResponse = null;
		GridTab headerTab = null;
		Map<String, JsonArray> childMap = new LinkedHashMap<String, JsonArray>();
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (headerTab == null && 
				((gridTab.getTabLevel()==0 && Util.isEmpty(tabSlug, true)) || 
				 (TypeConverterUtils.slugify(gridTab.getName()).equals(tabSlug)))) {
				if (gridTab.isReadOnly()) {
					errorResponse = Response.status(Status.FORBIDDEN)
							.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Tab is readonly").append("Tab is readonly: ").append(tabSlug).build().toString())
							.build();
					break;
				}
				gridTab.getTableModel().setImportingMode(true, trx.getTrxName());
				IGridTabSerializer serializer = load(gridWindow, gridTab, recordId);
				if (gridTab.getRowCount() < 1) {
					errorResponse = Response.status(Status.NOT_FOUND)
							.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Record not found").append("No record found matching id: ").append(recordId).build().toString())
							.build();
					break;
				} else if (gridTab.getRowCount() > 1) {
					errorResponse = Response.status(Status.BAD_REQUEST)
							.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("More than 1 match").append("More than 1 matching record for id: ").append(recordId).build().toString())
							.build();
					break;
				}				
				serializer.fromJson(jsonObject, gridTab);
				if (gridTab.needSave(true, true)) {
					ErrorDataStatusListener edsl = new ErrorDataStatusListener();
					gridTab.getTableModel().addDataStatusListener(edsl);
					try {
						if (!gridTab.dataSave(false))  {
							String error = edsl.getError();
							errorResponse = Response.status(Status.INTERNAL_SERVER_ERROR)
												.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error")
														.append(!Util.isEmpty(error) ? "Save error with exception: " : "")
														.append(!Util.isEmpty(error) ? error : "")
														.build().toString())
												.build();
							break;
						} else {
							gridTab.dataRefresh();
						}
					} finally {
						gridTab.getTableModel().removeDataStatusListener(edsl);
					}
				}
				headerTab = gridTab;
			} else if (headerTab != null && gridTab.getTabLevel()==(headerTab.getTabLevel()+1) && !gridTab.isReadOnly()) {
				String slug = TypeConverterUtils.slugify(gridTab.getName());
				JsonElement tabSlugElement = jsonObject.get(slug);					
				if (tabSlugElement != null && tabSlugElement.isJsonArray()) {
					if (!gridWindow.isTabInitialized(i))
						gridWindow.initTab(i);
					gridTab.getTableModel().setImportingMode(true, trx.getTrxName());
					IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(gridTab.getAD_Tab_UU());
					JsonArray childJsonArray = tabSlugElement.getAsJsonArray();
					JsonArray updatedArray = new JsonArray();
					final Boolean[] error = new Boolean[] {Boolean.FALSE};
					ErrorDataStatusListener edsl = new ErrorDataStatusListener();
					gridTab.getTableModel().addDataStatusListener(edsl);
					try {
						childJsonArray.forEach(e -> {
							if (e.isJsonObject() && !error[0].booleanValue()) {
								JsonObject childJsonObject = e.getAsJsonObject();
								if (!optLoad(gridTab, childJsonObject)) {
									if (!gridTab.dataNew(false)) {
										error[0] = Boolean.TRUE;
										return;
									}
									gridTab.setValue(gridTab.getLinkColumnName(), recordId);
								}
								serializer.fromJson(childJsonObject, gridTab);
								if (gridTab.needSave(true, true)) {
									if (!gridTab.dataSave(false))  {
										error[0] = Boolean.TRUE;
										return;
									} else {
										gridTab.dataRefresh(false);
									}
								}
								childJsonObject = serializer.toJson(gridTab);
								updatedArray.add(childJsonObject);
							}
						});
					} finally {
						gridTab.getTableModel().removeDataStatusListener(edsl);
					}
					if (error[0].booleanValue()) {
						String saveError = edsl.getError();
						errorResponse = Response.status(Status.INTERNAL_SERVER_ERROR)
											.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error")
													.append(!Util.isEmpty(saveError) ? "Save error with exception: " : "")
													.append(!Util.isEmpty(saveError) ? saveError : "")
													.build().toString())
											.build();
						break;
					}
					childMap.put(slug, updatedArray);
				}
			} else if (headerTab != null && gridTab.getTabLevel() < headerTab.getTabLevel()) {
				break;
			} else if (headerTab != null && gridTab.getTabLevel() == headerTab.getTabLevel() && headerTab.getTabLevel()>0) {
				break;
			}
		}
		
		if (errorResponse != null) {
			trx.rollback();
			return errorResponse;
		}
		
		String error = runDocAction(headerTab, jsonObject, trx.getTrxName());
		if (Util.isEmpty(error, true)) {
			trx.commit(true);
		} else {
			trx.rollback();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Can't perform document action").append("Encounter exception during execution of document action: ").append(error).build().toString())
					.build();
		}
		
		JsonObject updatedJsonObject = null;
		if (headerTab != null) {
			IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(headerTab.getAD_Tab_UU());
			updatedJsonObject = serializer.toJson(headerTab);
			if (childMap.size() > 0) {
				for(String slug : childMap.keySet()) {
					updatedJsonObject.add(slug, childMap.get(slug));
				}
			}
		}
		ResponseBuilder responseBuilder = Response.status(Status.OK);
		if (updatedJsonObject != null)
			return responseBuilder.entity(updatedJsonObject.toString()).build();
		else
			return responseBuilder.build();
	}

	private boolean optLoad(GridTab gridTab, JsonObject jsonObject) {
		JsonElement idElement = jsonObject.get("id");											
		if (idElement != null && idElement.isJsonPrimitive()) {
			if (!gridTab.isCurrent())
				gridTab.query(false);
			MQuery query = new MQuery();
			query.addRestriction(gridTab.getTableModel().getKeyColumnName(), "=", idElement.getAsInt());
			gridTab.setQuery(query);
			gridTab.query(false);
			return gridTab.getRowCount()==1;
		} else {
			JsonElement uidElement = jsonObject.get("uid");
			if (uidElement != null && uidElement.isJsonPrimitive()) {
				if (!gridTab.isCurrent())
					gridTab.query(false);
				MQuery query = new MQuery();
				String uidColumnName = PO.getUUIDColumnName(gridTab.getTableName());
				query.addRestriction(uidColumnName, "=", uidElement.getAsString());
				gridTab.setQuery(query);
				gridTab.query(false);
				return gridTab.getRowCount()==1;
			}
		}
		return false;
	}
	
	private Response createTabRecord(GridWindow gridWindow, GridTab parentTab, String tabSlug, JsonObject jsonObject, Trx trx) throws SQLException {
		GridTab headerTab = null;
		Map<String, JsonArray> childMap = new LinkedHashMap<String, JsonArray>();
		for(int i = 0; i < gridWindow.getTabCount(); i++) {
			GridTab gridTab = gridWindow.getTab(i);
			if (headerTab == null && 
				 ((gridTab.getTabLevel()==0 && Util.isEmpty(tabSlug, true)) || 
				  (TypeConverterUtils.slugify(gridTab.getName()).equals(tabSlug)))) {
				if (gridTab.isReadOnly() || !gridTab.isInsertRecord()) {
					return Response.status(Status.FORBIDDEN)
							.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Create not allow").append("Create not allow for tab: ").append(TypeConverterUtils.slugify(gridTab.getName())).build().toString())
							.build();
				}
				if (gridTab.getTabLevel() > 0 && parentTab == null) {
					return Response.status(Status.BAD_REQUEST)
							.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("No parent tab record").append("Can't create child tab record without parent tab record: ").append(tabSlug).build().toString())
							.build();
				}
				if (gridTab.getTabLevel() > 0 && parentTab != null && gridTab.getTabLevel() != (parentTab.getTabLevel()+1) ) {
					return Response.status(Status.BAD_REQUEST)
							.entity(new ErrorBuilder().status(Status.BAD_REQUEST).title("Wrong parent tab").append(tabSlug)
									.append(" is not child tab of ").append(TypeConverterUtils.slugify(parentTab.getName())).build().toString())
							.build();
				}
				if (!gridWindow.isTabInitialized(i))
					gridWindow.initTab(i);
				gridTab.getTableModel().setImportingMode(true, trx.getTrxName());
				if (!gridTab.isCurrent()) {
					if (gridTab.getTabLevel() > 0) {
						gridTab.query(false);
					} else {
						MQuery query = new MQuery("");
			    		query.addRestriction("1=2");
						query.setRecordCount(0);
						gridTab.setQuery(query);
						gridTab.query(false);
					}
				}
				IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(gridTab.getAD_Tab_UU());
				ErrorDataStatusListener edsl = new ErrorDataStatusListener();
				gridTab.getTableModel().addDataStatusListener(edsl);
				try {
					if (!gridTab.dataNew(false)) {
						String error = edsl.getError();
						return Response.status(Status.INTERNAL_SERVER_ERROR)
								.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error")
										.append(!Util.isEmpty(error) ? "Save error with exception: " : "")
										.append(!Util.isEmpty(error) ? error : "")
										.build().toString())
								.build();
					}
					serializer.fromJson(jsonObject, gridTab);
					if (!gridTab.dataSave(false))  {
						trx.rollback();
						String error = edsl.getError();
						return Response.status(Status.INTERNAL_SERVER_ERROR)
								.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error")
										.append(!Util.isEmpty(error) ? "Save error with exception: " : "")
										.append(!Util.isEmpty(error) ? error : "")
										.build().toString())
								.build();
					} else {
						gridTab.dataRefresh(false);
					}
				} finally {
					gridTab.removeDataStatusListener(edsl);
				}
				
				headerTab = gridTab;
			} else if (headerTab != null && gridTab.getTabLevel()==(headerTab.getTabLevel()+1) && !gridTab.isReadOnly()) {
				String tSlug = TypeConverterUtils.slugify(gridTab.getName());
				JsonElement tabSlugElement = jsonObject.get(tSlug);					
				if (tabSlugElement != null && tabSlugElement.isJsonArray()) {
					if (!gridWindow.isTabInitialized(i))
						gridWindow.initTab(i);
					gridTab.getTableModel().setImportingMode(true, trx.getTrxName());
					IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(gridTab.getAD_Tab_UU());
					JsonArray childJsonArray = tabSlugElement.getAsJsonArray();
					JsonArray updatedArray = new JsonArray();
					final Boolean[] error = new Boolean[] {Boolean.FALSE};
					final GridTab finalHeaderTab = headerTab;
					ErrorDataStatusListener edsl = new ErrorDataStatusListener();
					gridTab.getTableModel().addDataStatusListener(edsl);
					try {
						childJsonArray.forEach(e -> {
							if (e.isJsonObject() && !error[0].booleanValue()) {
								JsonObject childJsonObject = e.getAsJsonObject();
								if (!gridTab.isCurrent())
									gridTab.query(false);
								if (!gridTab.dataNew(false)) {
									error[0] = Boolean.TRUE;
									return;
								}
								gridTab.setValue(gridTab.getLinkColumnName(), finalHeaderTab.getKeyID(0));								
								serializer.fromJson(childJsonObject, gridTab);
								if (!gridTab.dataSave(false))  {
									error[0] = Boolean.TRUE;
									return;
								} else {
									gridTab.dataRefresh(false);
								}
								childJsonObject = serializer.toJson(gridTab);
								updatedArray.add(childJsonObject);
							}
						});
						if (error[0].booleanValue()) {
							trx.rollback();
							String msg = edsl.getError();
							return Response.status(Status.INTERNAL_SERVER_ERROR)
									.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Save error")
											.append(!Util.isEmpty(msg) ? "Save error with exception: " : "")
											.append(!Util.isEmpty(msg) ? msg : "")
											.build().toString())
									.build();
						}
						childMap.put(tSlug, updatedArray);
					} finally {
						gridTab.removeDataStatusListener(edsl);
					}
				}
			} else if (headerTab != null && gridTab.getTabLevel() < headerTab.getTabLevel()) {
				break;
			} else if (headerTab != null && gridTab.getTabLevel() == headerTab.getTabLevel() && headerTab.getTabLevel()>0) {
				break;
			}
		}
		
		String error = runDocAction(headerTab, jsonObject, trx.getTrxName());
		if (Util.isEmpty(error, true)) {
			trx.commit(true);
		} else {
			trx.rollback();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Can't perform document action").append("Encounter exception during execution of document action: ").append(error).build().toString())
					.build();
		}
		
		JsonObject updatedJsonObject = null;
		if (headerTab != null) {
			IGridTabSerializer serializer = IGridTabSerializer.getGridTabSerializer(headerTab.getAD_Tab_UU());
			updatedJsonObject = serializer.toJson(headerTab);
			if (childMap.size() > 0) {
				for(String slug : childMap.keySet()) {
					updatedJsonObject.add(slug, childMap.get(slug));
				}
			}
		}
		ResponseBuilder responseBuilder = Response.status(Status.CREATED);
		if (updatedJsonObject != null)
			return responseBuilder.entity(updatedJsonObject.toString()).build();
		else
			return responseBuilder.build();
	}
	
	private String runDocAction(GridTab gridTab, JsonObject jsonObject, String trxName) {
		
		return null;
	}
	
	private class ErrorDataStatusListener implements DataStatusListener {
		private String error = null;
		
		@Override
		public void dataStatusChanged(DataStatusEvent e) {
			if (e.isError()) {
				String msg = e.getAD_Message();
				if (!Util.isEmpty(msg, true)) {
					error = Msg.getMsg(Env.getCtx(), msg);
				}
			}
		}
		
		public String getError() {
			return error;
		}
		
	}

	@Override
	public Response printWindowRecord(String windowSlug, int recordId, String reportType) {
		return printTabRecord(windowSlug, null, recordId, reportType);
	}

	@Override
	public Response printTabRecord(String windowSlug, String tabSlug, int recordId, String reportType) {
		return null;
	}
}