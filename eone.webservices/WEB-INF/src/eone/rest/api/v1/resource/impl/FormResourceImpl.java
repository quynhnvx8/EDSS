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
* - BX Service GmbH                                                   *
* - Diego Ruiz                                                        *
**********************************************************************/
package eone.rest.api.v1.resource.impl;

import java.util.logging.Level;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eone.base.model.MForm;
import eone.base.model.Query;
import eone.rest.api.json.EONERestException;
import eone.rest.api.json.filter.ConvertedQuery;
import eone.rest.api.json.filter.IQueryConverter;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.resource.FormResource;
import eone.util.CLogger;
import eone.util.Env;

/**
 * 
 * @author druiz
 *
 */
public class FormResourceImpl implements FormResource {

	private final static CLogger log = CLogger.getCLogger(FormResourceImpl.class);

	public FormResourceImpl() {
	}

	@Override
	public Response getForms(String filter) {
		JsonArray formArray = new JsonArray();
		IQueryConverter converter = IQueryConverter.getQueryConverter("DEFAULT");
		try {
			ConvertedQuery convertedStatement = converter.convertStatement(MForm.Table_Name, filter);
			
			if (log.isLoggable(Level.INFO)) log.info("Where Clause: " + convertedStatement.getWhereClause());
			
			Query query = new Query(Env.getCtx(), MForm.Table_Name, convertedStatement.getWhereClause(), null);
			query.setApplyAccessFilter(true).setOnlyActiveRecords(true).setOrderBy("Name");
			query.setParameters(convertedStatement.getParameters());
			query.setOrderBy(" AD_Form_ID");
			int rowCount = query.count();

			
			JsonObject json = new JsonObject();
			json.add("forms", formArray);
			return Response.ok(json.toString())
					.header("X-Row-Count", rowCount)
					.build();
		} catch (Exception ex) {
			Status status = Status.INTERNAL_SERVER_ERROR;
			if (ex instanceof EONERestException)
				status = ((EONERestException) ex).getErrorResponseStatus();
			
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(status)
					.entity(new ErrorBuilder().status(status)
							.title("GET Error")
							.append("Get forms with exception: ")
							.append(ex.getMessage())
							.build().toString())
					.build();
		}

	}
}
