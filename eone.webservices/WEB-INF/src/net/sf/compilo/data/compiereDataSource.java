
package net.sf.compilo.data;

import eone.util.CLogger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *  compiereDataSource
 *
 * 	@author 	Peter Shen
 * 	@version 	$Id: compiereDataSource.java,v 1.4 2005/08/04 09:42:41 pshen Exp $
 *	@description:	compiereDataSource
 */
public abstract class compiereDataSource implements JRDataSource
{	
	protected transient CLogger log = CLogger.getCLogger(compiereDataSource.class);
	
	public abstract Object getFieldValue(JRField field) throws JRException;
	
	public abstract boolean next() throws JRException;
	
	public abstract void close();	
}
