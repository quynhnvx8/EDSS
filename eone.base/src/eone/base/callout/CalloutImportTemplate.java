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
 * - Carlos Ruiz - globalqss                                           *
 **********************************************************************/
package eone.base.callout;

import java.nio.charset.Charset;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.util.Msg;

/**
 * Import Template callout
 * @author Carlos Ruiz
 *
 */
public class CalloutImportTemplate extends CalloutEngine
{
	public String characterSet(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null) {
			return "";
		}

		String characterSet = value.toString();
		if (!Charset.isSupported(characterSet)) {
			/*
			StringBuilder valid = new StringBuilder();
			for (Charset validch : Charset.availableCharsets().values()) {
				if (valid.length() > 0)
					valid.append(", ");
				valid.append(validch.displayName());
			}
			return Msg.parseTranslation(ctx, "@Invalid@ @CharacterSet@ -> " + valid);
			*/
			return Msg.parseTranslation(ctx, "@Invalid@ @CharacterSet@");
		}

		return null;
	}
}