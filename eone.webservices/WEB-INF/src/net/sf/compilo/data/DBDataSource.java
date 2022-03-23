
package net.sf.compilo.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import eone.util.DB;
import net.sf.compilo.report.ReportInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRQueryExecuter;

/**
 *  DBDataSource
 *
 * 	@author 	Peter Shen
 * 	@version 	$Id: DBDataSource.java,v 1.3 2005/08/04 09:42:41 pshen Exp $
 *	@description:	DBDataSource
 */
public class DBDataSource extends compiereDataSource
{
    private PreparedStatement m_pstmt = null;
    private ResultSet	m_resultSet = null;
    
    public DBDataSource(Properties ctx, ReportInfo ri, HashMap<String,Object> params)
    {
        JasperReport jr = ri.getJasperReport();
        //Generate parameters map
        HashMap<String, JRParameter> parametersMap = new HashMap<String, JRParameter>();
        JRParameter[] jpara = jr.getParameters();
        for (int i=0; i<jpara.length; i++)
        {
            parametersMap.put(jpara[i].getName(), jpara[i]);
        }
        
        try
        {
            m_pstmt = JRQueryExecuter.getStatement(
                    jr.getQuery(), 
    				parametersMap, 
    				params, 
    				DB.getConnectionRO()
    				);
            if(m_pstmt != null)
                m_resultSet = m_pstmt.executeQuery();
        }
        catch (JRException jre)
        {
            log.saveError("GetStatement", jre);
            m_resultSet = null;
        }
        catch (SQLException sqle)
        {
            log.saveError("GetResultSet", sqle);
            m_resultSet = null;
        }
    }
    
   
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField field) throws JRException
    {
        Object objValue = null;
		if (field != null && m_resultSet != null)
		{
			Class<?> clazz = field.getValueClass();

			try
			{
				if (clazz.equals(java.lang.Object.class))
				{
					objValue = m_resultSet.getObject(field.getName());
				}
				else if (clazz.equals(java.lang.Boolean.class))
				{
					objValue = m_resultSet.getBoolean(field.getName()) ? Boolean.TRUE : Boolean.FALSE;
				}
				else if (clazz.equals(java.lang.Byte.class))
				{
					objValue = Byte.valueOf(m_resultSet.getByte(field.getName()));
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.util.Date.class))
				{
					objValue = m_resultSet.getDate(field.getName());
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.sql.Timestamp.class))
				{
					objValue = m_resultSet.getTimestamp(field.getName());
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.sql.Time.class))
				{
					objValue = m_resultSet.getTime(field.getName());
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Double.class))
				{
					objValue = Double.valueOf(m_resultSet.getDouble(field.getName()));
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Float.class))
				{
					objValue = Float.valueOf(m_resultSet.getFloat(field.getName()));
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Integer.class))
				{
					objValue = Integer.valueOf(m_resultSet.getInt(field.getName()));
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Long.class))
				{
					objValue = Long.valueOf(m_resultSet.getLong(field.getName()));
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Short.class))
				{
					objValue = Short.valueOf(m_resultSet.getShort(field.getName()));
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.math.BigDecimal.class))
				{
					objValue = m_resultSet.getBigDecimal(field.getName());
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.String.class))
				{
					objValue = m_resultSet.getString(field.getName());
					if(m_resultSet.wasNull())
					{
						objValue = null;
					}
				}
			}
			catch (Exception e)
			{
				throw new JRException("Unable to get value for field '" + field.getName() + "' of class '" + clazz.getName() + "'", e);
			}
		}
		return objValue;        
    }
    
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#next()
     */
    public boolean next() throws JRException
    {
        boolean retValue = false;
        if(m_resultSet == null)
            return retValue;
        
        try
        {
            retValue = m_resultSet.next();
        }
        catch(SQLException sqle)
        {
            throw new JRException(sqle);
        }
        return retValue;
    }
    
    /* (non-Javadoc)
     * @see net.sf.compilo.data.compiereDataSource#close()
     */
    public void close()
    {
        DB.close(m_resultSet);
        m_resultSet = null;
        DB.close(m_pstmt);
        m_pstmt = null;
    }
}
