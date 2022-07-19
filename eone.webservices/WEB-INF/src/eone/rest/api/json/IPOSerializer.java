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
package eone.rest.api.json;

import java.util.List;

import com.google.gson.JsonObject;

import eone.base.Service;
import eone.base.model.MTable;
import eone.base.model.PO;

/**
 * 
 * PO serialize/deserialize interface
 * @author hengsin
 *
 */
public interface IPOSerializer {

	/**
	 * Transform PO to JsonObject
	 * @param po
	 * @return JsonObject
	 */
	public default JsonObject toJson(PO po) {
		return toJson(po, null, null);
	}

	/**
	 * Transform PO to JsonObject
	 * @param po
	 * @param includes columns to include
	 * @param excludes columns to exclude
	 * @return JsonObject
	 */
	public JsonObject toJson(PO po, String[] includes, String[] excludes);
	
	/**
	 * Transform JsonObject to PO
	 * @param json
	 * @param table
	 * @return PO
	 */
	public PO fromJson(JsonObject json, MTable table);
	
	/**
	 * Copy values from JsonObject to PO
	 * @param json
	 * @param po
	 * @return PO
	 */
	public PO fromJson(JsonObject json, PO po);
	
	/**
	 * Get PO serializer
	 * @param tableName
	 * @param modelClass
	 * @return IPOSerializer
	 */
	public static IPOSerializer getPOSerializer(String tableName, Class<?> modelClass) {
		IPOSerializer serializer = null;
		List<IPOSerializerFactory> factories = Service.locator().list(IPOSerializerFactory.class).getServices();
		for (IPOSerializerFactory  factory : factories) {
			serializer = factory.getPOSerializer(tableName, modelClass);
			if (serializer != null) {
				break;
			}
		}
		if (serializer == null) {
			for (IPOSerializerFactory  factory : factories) {
				serializer = factory.getPOSerializer("*", modelClass);
				if (serializer != null) {
					break;
				}
			}
		}
		
		return serializer;
	}
}
