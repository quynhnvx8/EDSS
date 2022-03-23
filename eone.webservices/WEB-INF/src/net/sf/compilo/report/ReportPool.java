
package net.sf.compilo.report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import eone.util.CLogger;

;

/**
 * ReportPool
 * 
 * @author Peter Shen
 * @version $Id: ReportPool.java,v 1.2 2005/08/03 10:33:00 pshen Exp $
 * @description: JasperReport Object Pool
 */
public class ReportPool
{
    private static HashMap<Integer, ReportInfo>   pool   = new HashMap<Integer, ReportInfo>();

    private static final int MAXNUM = 15;
    private static int 	size = 0;

    private static final CLogger log = CLogger.getCLogger(ReportPool.class);
    
    public ReportInfo getReport (int AD_Process_ID )
    {
/*        ReportInfo reportinfo = (ReportInfo) pool.get(Integer.valueOf(AD_Process_ID));
        if (reportinfo == null)
        {
            reportinfo = loadReport(AD_Process_ID);
            addReport(AD_Process_ID, reportinfo, false);
        }
        else if(reportinfo.isDirty())
        {
            log.info("ReportInfo  " + reportinfo + "dirty, Refresh");
            reportinfo = loadReport(AD_Process_ID);
            if(!reportinfo.hasError())
                addReport(AD_Process_ID, reportinfo, true);
        }
*/
        ReportInfo reportinfo;
        reportinfo = loadReport(AD_Process_ID);
        return reportinfo;
    }

    private ReportInfo loadReport (int AD_Process_ID )
    {
        ReportInfo reportinfo = new ReportInfo(AD_Process_ID);
        return reportinfo;
    }
    
    @SuppressWarnings("unused")
	private synchronized void addReport(int AD_Process_ID, ReportInfo reportinfo, boolean refresh)
    {
        if(refresh)
        {
            pool.put(Integer.valueOf(AD_Process_ID), reportinfo);            
        }
        else
        {
            while(size > MAXNUM)
            {
                Set<Integer> s = pool.keySet();
                Iterator<Integer> it = s.iterator();
            	if (it.hasNext())
            	{
            		Integer key = it.next();
            		if (log.isLoggable(Level.INFO)) log.info("Remove " + key + " from the report pool");
            		pool.remove(key);
            	}
            	size--;
            }
            
            pool.put(Integer.valueOf(AD_Process_ID), reportinfo);
            size++;            
        }
        if (log.isLoggable(Level.INFO)) log.info("Load " + reportinfo + " into Report Pool" + " Size=" + size);
    }

}
