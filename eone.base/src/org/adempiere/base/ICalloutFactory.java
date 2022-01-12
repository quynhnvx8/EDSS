/******************************************************************************
 * Copyright (C) 2015 Dirk Niemeyer                                           *
 * Copyright (C) 2015 action 42 GmbH               							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.base;

import eone.base.model.Callout;

/**
 * 
 * @author a42niem
 *
 */
public interface ICalloutFactory {

	/**
	 * 
	 * @param className
	 * @param methodName 
	 * @return matching Callout
	 */
	public Callout getCallout(String className, String methodName);
	
}
