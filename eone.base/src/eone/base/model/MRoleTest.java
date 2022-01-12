/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package eone.base.model;

import junit.framework.TestCase;

/**
 * The class <code>MRoleTest</code> contains tests for the class MRole
 * <p>
 * @author Jorg Janke
 * @version $Id: MRoleTest.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 */
public class MRoleTest extends TestCase
{
	/**
	 * Construct new test instance
	 * @param name the test name
	 */
	public MRoleTest(String name)
	{
		super(name);
	}

	
	protected void setUp() throws Exception, Exception
	{
		
		super.setUp();
	}

	/**
	 * Perform post-test clean up
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	 * Run the String addAccessSQL(String, String, boolean, boolean) method
	 * test
	 */
	public void testAddAccessSQL()
	{
		
	}


	/**
	 * Launch the test.
	 * @param args String[]
	 */
	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(MRoleTest.class);
	}

}	//	MRoleTest
