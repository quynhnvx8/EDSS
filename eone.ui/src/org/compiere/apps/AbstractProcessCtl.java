package org.compiere.apps;

import java.io.InvalidClassException;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.compiere.print.ReportCtl;

import eone.base.model.MClient;
import eone.base.model.MConfigSignReport;
import eone.base.model.MOrg;
import eone.base.model.MProcess;
import eone.base.process.ClientProcess;
import eone.base.process.ProcessInfo;
import eone.base.process.ProcessInfoParameter;
import eone.db.CConnection;
import eone.interfaces.Server;
import eone.print.MPrintFormat;
import eone.print.MPrintFormatItem;
import eone.print.ParameterReport;
import eone.print.PrintDataItem;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.IProcessUI;
import eone.util.Msg;
import eone.util.ProcessUtil;
import eone.util.Trx;

public abstract class AbstractProcessCtl implements Runnable
{

	/**
	 * Quynhnv.x8. Mod 26/09/2020
	 * Sua lai, khong dung duoc theo idempiere
	 */
	public AbstractProcessCtl (IProcessUI aProcessUI, int WindowNo, ProcessInfo pi, Trx trx)
	{
		windowno = WindowNo;
		m_processUI = aProcessUI;
		m_pi = pi;
		m_trx = trx;	
	} 

	private int windowno;
	private IProcessUI m_processUI;
	private ProcessInfo m_pi;
	private Trx				m_trx;
	private boolean 		m_IsServerProcess = false;
	
	private static final CLogger	log	= CLogger.getCLogger (AbstractProcessCtl.class);
	
	public void start()
	{
		Thread thread = new Thread(this);
		if (m_pi != null)
			thread.setName(m_pi.getTitle()+"-"+m_pi.getAD_Session_ID());
		thread.start();
	}
	

	public void run ()
	{
		if (log.isLoggable(Level.FINE)) log.fine("AD_Session_ID=" + m_pi.getAD_Session_ID()
			+ ", Record_ID=" + m_pi.getRecord_ID());
		int C_Element_ID = Env.getContextAsInt(Env.getCtx(), "#C_Element_ID");
		String 	ProcedureName = "";
		String  JasperReport = "";
		String TemplateApply = "";
		boolean IsReport = false;
		String sql = "SELECT p.Name, p.ProcedureName,p.ClassName, p.AD_Process_ID,"		
			+ " p.isReport, p.IsServerProcess, p.JasperReport, p.AD_Process_UU, "
			+ " CASE WHEN "+C_Element_ID+" = 105 THEN TemplateApply ELSE TemplateOther END TemplateApply "  
			+ " FROM AD_Process p"
			+ " WHERE p.IsActive='Y'"
			+ " AND p.AD_Process_ID=?";
		if (!Env.isBaseLanguage(Env.getCtx(), "AD_Process"))
			sql = "SELECT t.Name, p.ProcedureName,p.ClassName, p.AD_Process_ID,"	
				+ " p.isReport, p.IsServerProcess, p.JasperReport, p.AD_Process_UU,"
				+ " CASE WHEN "+C_Element_ID+" = 105 THEN TemplateApply ELSE TemplateOther END TemplateApply " 	
				+ "FROM AD_Process p"
				+ " INNER JOIN AD_Process_Trl t ON (p.AD_Process_ID=t.AD_Process_ID"
					+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "') "
				+ " WHERE p.IsActive='Y'"
				+ " AND p.AD_Process_ID=?";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, 
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, m_pi.getAD_Process_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_pi.setTitle (rs.getString("Name"));
				//updateProgressWindowTitle(m_pi.getTitle());
				ProcedureName = rs.getString("ProcedureName");
				m_pi.setClassName (rs.getString("ClassName"));
				m_pi.setAD_Process_ID (rs.getInt("AD_Process_ID"));
				m_pi.setAD_Process_UU(rs.getString("AD_Process_UU"));
				IsReport = "Y".equalsIgnoreCase(rs.getString("isReport"));
				
				m_IsServerProcess = "Y".equals(rs.getString("IsServerProcess"));
				JasperReport = rs.getString("JasperReport");
				TemplateApply = rs.getString("TemplateApply");
			}
			else
				log.log(Level.SEVERE, "No AD_Session =" + m_pi.getAD_Session_ID());
		}
		catch (Throwable e)
		{
			m_pi.setSummary (Msg.getMsg(Env.getCtx(), "ProcessNoProcedure") + " " + e.getLocalizedMessage(), true);
			log.log(Level.SEVERE, "run", e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		if (ProcedureName == null)
			ProcedureName = "";

		boolean isJasper = false;
		if (JasperReport != null && JasperReport.trim().length() > 0) {
			isJasper = true;
			if (ProcessUtil.JASPER_STARTER_CLASS.equals(m_pi.getClassName())) {
				m_pi.setClassName(null);
			}
			m_pi.setJasperReport(JasperReport);
		}
		
		/**********************************************************************
		 *	Start Optional Class
		 */
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		addProcessParametersNew(params, m_pi.getProcessPara());
		addProcessInfoParameters(params, m_pi.getParameter());
		if (!IsReport && m_pi.getClassName() != null)
		{
			if (isJasper)
			{
				m_pi.setReportingProcess(true);
			}
			
			//	Run Class
			if (!startProcess(params))
			{
				return;
			}

			if (!IsReport && ProcedureName.length() == 0)
			{
				return;
			}
			
		}
		
		loadInfoClient(params);
		
		params.put("TEMPAPPLY", TemplateApply);
		MConfigSignReport [] listSign = MConfigSignReport.getLines(Env.getCtx(), m_pi.getAD_Process_ID());
		MProcess process = MProcess.get(Env.getCtx(), m_pi.getAD_Process_ID());
		MPrintFormat  format = MPrintFormat.get(Env.getCtx(), process.getAD_PrintFormat_ID(), false);
		params.put("SIGNREPORT", listSign);
		//Uu tien chay Jasper, neu ko co jasper thi chay Procedure
		if (IsReport && isJasper)
		{
			params.put("ProcessInfo", m_pi);
			m_pi.setReportingProcess(true);
			m_pi.setClassName(ProcessUtil.JASPER_STARTER_CLASS);
			//ReportCtl.start(m_processUI, windowno, m_pi, params);
			startProcess(params);			
			return;
		}
		
		if (IsReport && ProcedureName.length() > 0)
		{
			params.put("ProcedureName", ProcedureName);
			params.put("ReportName", m_pi.getTitle());
			params.put("WindowNo", windowno);
			params.put("MPrintFormat", format);
			//excuteQuery(ProcedureName, format);
			if (ProcedureName.indexOf("'a'") > 0)
				getDataExcute(ProcedureName, format);
			else
				getDataExcuteOld(ProcedureName, format);
			//Luu y phai dat o day de lay duoc data
			params.put("ProcessInfo", m_pi);
			m_pi.setReportingProcess(true);
			boolean ok = ReportCtl.start(m_processUI, windowno, m_pi, params);
			m_pi.setSummary(Msg.getCleanMsg(Env.getCtx(), "Report"), !ok);
		}			
			
	}   //  run
	
	private BigDecimal getBalanceRow(Map<String, Object> data, String formula, String columnName, int row, BigDecimal valueOlds) {
		if (valueOlds == null || row == 0) {
			valueOlds = Env.ZERO;
    	}
    	
    	String formulaStr = formula;
    	
    	for(Map.Entry<String, Object> entry : data.entrySet()) {
    		String key = entry.getKey();
    		Object value = entry.getValue();
    		if (value == null) {
    			value = 0;
    		}
    		if(formulaStr.contains(key)) {
    			formulaStr = formulaStr.replaceAll(key, ""+ value);
    		}
    	}
    	formulaStr = formulaStr + "+" + valueOlds;
    	BigDecimal valueNew = Env.ZERO;
    	try {
    		valueNew = Env.getValueByFormula(formulaStr);
    	} catch (Exception e) {
    		log.config("Error " + row);
    	}
    	
    	return valueNew;
    }
	
	private Serializable getBalance(Map<String, Object> data, Map<String, String> formula, String columnName, Map<String, Object> valueOlds) {
    	String formulaStr = formula.get(columnName);
    	if (formulaStr != null) {
	    	for(Map.Entry<String, Object> entry : data.entrySet()) {
	    		String key = entry.getKey();
	    		Object value = entry.getValue();
	    		if (value == null) {
	    			value = 0;
	    		}
	    		if(formulaStr.contains(key)) {
	    			formulaStr = formulaStr.replaceAll(key, ""+ value);
	    		}
	    	}
	    	Object valueOld = valueOlds.get(columnName);
	    	if (valueOld == null) {
	    		valueOld = Env.ZERO;
	    	}
	    	Serializable valueNew = Env.getValueByFormula(formulaStr);
	    	
	    	BigDecimal total = new BigDecimal(valueOld.toString()).add(new BigDecimal(valueNew.toString()));
	    	
	    	return (Serializable) (total);
    	}else {
    		return 0;
    	}
    }
	
	private void getDataExcuteOld(String sql, MPrintFormat  format)
	{
        String sqlProc = Env.parseContext(Env.getCtx(), windowno, sql, false);
        PreparedStatement ps = DB.prepareCall(sqlProc);
        
        ArrayList<PrintDataItem> arrC = null;
        ArrayList<PrintDataItem> arrH = null;
        ArrayList<PrintDataItem> arrF = null;
        ArrayList<ArrayList<PrintDataItem>> arrsC = new ArrayList<ArrayList<PrintDataItem>>();
        ArrayList<ArrayList<PrintDataItem>> arrsH = new ArrayList<ArrayList<PrintDataItem>>();
        ArrayList<ArrayList<PrintDataItem>> arrsF = new ArrayList<ArrayList<PrintDataItem>>();
        
        int rowCount = 0;
		ResultSet rs = null;
		MPrintFormatItem [] itemsC = format.getItemContent();
		MPrintFormatItem [] itemsH = format.getItemHeader();
		MPrintFormatItem [] itemsF = format.getItemFooter();
		
		//Gia tri tinh toan cong thuc bao cao
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> formula = format.getFormula();
		ArrayList<Object> arrWidth = new ArrayList<Object>();
		Map<String, Object> valueOld = new HashMap<String, Object>();
		Serializable balance = Env.ZERO;
		
		int maxRow = 0;
		
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Serializable element = null;
				arrC = new ArrayList<PrintDataItem>();
				arrH = new ArrayList<PrintDataItem>();
				arrF = new ArrayList<PrintDataItem>();
				
				for(int i = 0; i < itemsH.length; i++) {
					MPrintFormatItem item = itemsH[i];
					if (item.isMapColumnSelectSQL() && item.isPrinted()){
						if(item.getName().contains("%#")) {
							String [] hf = item.getName().split("%#");
							for(int v = 0; v < hf.length; v++) {
				    			if(hf[v].contains("%")) {
				    				String columnName = hf[v].trim().substring(0, hf[v].lastIndexOf("%"));
				    				element = (Serializable) rs.getObject(columnName);
				    				item.setName(columnName);
				    				break;
				    			}
				    			
				    		}        
						}
						arrH.add(addNewItem(item, element));						
					}
				}
				
				for(int i = 0; i < itemsF.length; i++) {
					
					MPrintFormatItem item = itemsF[i];
					if (item.isMapColumnSelectSQL() && item.isPrinted()){
						if(item.getName().contains("%#")) {
							String [] hf = item.getName().split("%#");
							for(int v = 0; v < hf.length; v++) {
				    			if(hf[v].contains("%")) {
				    				String columnName = hf[v].trim().substring(0, hf[v].lastIndexOf("%"));
				    				element = (Serializable) rs.getObject(columnName);
				    				item.setName(columnName);
				    				break;
				    			}
				    			
				    		}        
						}
						arrF.add(addNewItem(item, element));						
					}
				}
				
				for(int i = 0; i < itemsC.length; i++) {
					MPrintFormatItem item = itemsC[i];
					if (item.isMapColumnSelectSQL() && item.isPrinted()){
						element = (Serializable) rs.getObject(item.getName());
						
						//Nhung truong du lieu kieu so se add vao data phuc vu cho tinh toan cong thuc (neu co)
						if (item.isNumber()) {
		        			data.put(item.getColumnName(), element);
		        		}
						
						//Tinh toan doi voi cot dat cong thuc de day vao List data truoc khi view bao cao.
						if (item.isBalanceFinal() && item.getFormulaSetup() != "") {
		        			balance = getBalance(data, formula, item.getColumnName(), valueOld); 
		        			valueOld.put(item.getColumnName(), balance);
		        			if (new BigDecimal(balance.toString()).compareTo(Env.ZERO) < 0)
		        				balance = 0;
		        			element = balance;        			       			        			        				
		        		}
						
						//Add vao data
						arrC.add(addNewItem(item, element));
						//Add do rong cua cac cot
						if (rowCount == 0)
							arrWidth.add((float)itemsC[i].getMaxWidth());
					}
					if (Integer.parseInt(item.getOrderRowHeader()) > maxRow && !item.isGroupBy()) {
						maxRow = Integer.parseInt(item.getOrderRowHeader());
					}
				}
				arrsC.add(arrC);
				rowCount++;
			} 
		}
		catch (SQLException e)
		{
			log.saveError("Error", e);
		}
		finally
		{
			DB.close(rs, ps);
			rs = null; ps = null;
		}
		
		//Truong hop query khong co du lieu tra ve
		if (arrsC.size() == 0) {
			Serializable element = null;
			arrC = new ArrayList<PrintDataItem>();
			for(int i = 0; i < itemsC.length; i++) {
				MPrintFormatItem item = itemsC[i];
				if (item.isMapColumnSelectSQL() && item.isPrinted()) {
					arrC.add(addNewItem(item, element));
					//Add do rong cua cac cot
					arrWidth.add((float)itemsC[i].getMaxWidth());
						
				}
				if (Integer.parseInt(item.getOrderRowHeader()) > maxRow && !item.isGroupBy()) {
					maxRow = Integer.parseInt(item.getOrderRowHeader());
				}
			}
			arrsC.add(arrC);
			arrsH.add(arrH);
			arrsF.add(arrF);
		}//End khogn co du lieu tra ve
		
		float [] retValue = new float [arrWidth.size()];
		for(int i = 0; i < arrWidth.size(); i++) {
			retValue[i] = (float)arrWidth.get(i);
		}
		
		m_pi.setWidthTable(retValue);
		m_pi.setDataQueryC(arrsC);
		m_pi.setDataQueryH(arrsH);
		m_pi.setDataQueryF(arrsF);
		m_pi.setRowCountQuery(rowCount);
		m_pi.setColumnCountQuery(arrWidth.size());
		m_pi.setMaxRow(maxRow);
		m_pi.setItemsC(itemsC);
		m_pi.setItemsF(itemsF);
		m_pi.setItemsH(itemsH);
	}
	
	private void getDataExcute(String sql, MPrintFormat  format)
	{
		Connection conn = DB.getConnectionID();
		
        String sqlProc = Env.parseContext(Env.getCtx(), windowno, sql, false);
        PreparedStatement ps = null;
        ArrayList<PrintDataItem> arrC = null;
        ParameterReport sPara = null;
        ArrayList<PrintDataItem> arrG = null;
        ArrayList<PrintDataItem> arrH = null;
        ArrayList<PrintDataItem> arrF = null;
        List<ArrayList<PrintDataItem>> arrsC = new ArrayList<ArrayList<PrintDataItem>>();
        List<ParameterReport> sParas = new ArrayList<ParameterReport>();
        List<ArrayList<PrintDataItem>> arrsH = new ArrayList<ArrayList<PrintDataItem>>();
        List<ArrayList<PrintDataItem>> arrsF = new ArrayList<ArrayList<PrintDataItem>>();
        
        //Object chart
        Object [] arrChart = null;
        List<Object []> arrsChart = new ArrayList<Object []>();
        
        
		ResultSet rs = null;
		MPrintFormatItem [] itemsC = format.getItemContent();
		MPrintFormatItem [] itemsG = format.getItemGroup();
		MPrintFormatItem [] itemsH = format.getItemHeader();
		MPrintFormatItem [] itemsF = format.getItemFooter();
		MPrintFormatItem [] itemsR = null;
		
		if (format.isShowChart()) {
			itemsR = format.getItemChart();
		}
		
		//Group by
		Map<String, BigDecimal> objGroup = new HashMap<String, BigDecimal>();
		Map<String, Map<String, BigDecimal>> dataGroup = new HashMap<String, Map<String, BigDecimal>>();
    	//End
		
		Map<String, Object> dataRow = new HashMap<String, Object>();
		//Map<String, String> formula = format.getFormula();
		//Map<String, Object> valueOld = new HashMap<String, Object>();
		BigDecimal balance = Env.ZERO;
    	
		ArrayList<Object> arrWidth = new ArrayList<Object>();
		
		int rowCount = 0;
		int countGroup = 0;
		int maxRow = 0;
		
		try {
			conn.setAutoCommit(false);
        	ps = conn.prepareCall(sqlProc);
			rs = ps.executeQuery();
			while (rs.next()) {
				
				Serializable element = null;
				
				/*=========================================================*/
				//Process Header
				ResultSet rsH = (ResultSet) rs.getObject(1);
				ResultSetMetaData  metaH = rsH.getMetaData();
				while (rsH.next()) {
					arrH = new ArrayList<PrintDataItem>();
					String name = metaH.getColumnName(1);
					if ("?column?".equalsIgnoreCase(name))
						break;
					for(int i = 0; i < itemsH.length; i++) {
						MPrintFormatItem item = itemsH[i];
						if (item.isMapColumnSelectSQL() && item.isPrinted()){
							if(item.getName().contains("%#")) {
								String [] hf = item.getName().split("%#");
								for(int v = 0; v < hf.length; v++) {
					    			if(hf[v].contains("%")) {
					    				String columnName = hf[v].trim().substring(0, hf[v].lastIndexOf("%"));
					    				element = (Serializable) rsH.getObject(columnName);
					    				item.setName(columnName);
					    				break;
					    			}
					    			
					    		}        
							}
							arrH.add(addNewItem(item, element));						
						}
					}
					arrsH.add(arrH);
				}
				rsH.close();
				//End Header
				/*=========================================================*/
				
				
				/*=========================================================*/
				//Process Content
				//??ang c???u h??nh ch??? 1 group
				MPrintFormatItem itemG = null;
				BigDecimal valueRowOld = null;
				Map<String, BigDecimal> balances = new HashMap<String, BigDecimal>(); 
				
				if(itemsG != null && itemsG.length > 0)
					itemG = itemsG[0];
				
				ResultSet rsC = (ResultSet) rs.getObject(2);
				String ColSpanReport = "";
				Map<String, Integer> mapColSpan = new HashMap<String, Integer>();
				String colSpanReportOld = ""; //D??ng ????? d??nh d???u xem ???? sang c???u h??nh colspan kh??c ch??a th?? m???i x??? l??
				boolean isColspanConfig = true;
				//int inc = 0;
				while (rsC.next()) {
					dataRow = new HashMap<String, Object>();
					//inc++;
					//??o???n n??y x??? l?? l???y ColSpan ???????c c???u h??nh ????? ?????y v??o c??c Col thay v?? c???u h??nh tr??n PrintFormat.
					if (isColspanConfig) {
						sPara = processRow(rsC);
						try {
							ColSpanReport = rsC.getString("ColSpanReport");
							if (ColSpanReport == null)
								ColSpanReport = "";
							
						}catch (Exception e) {
							isColspanConfig = false;
							ColSpanReport = "";
							sPara = null;
						}
					}
					String [] arrColSpan = null;
					
					
					//C?? gi?? tr??? colspan v?? khi thay ?????i gi?? tr??? c???u h??nh theo t???ng d??ng
					
					if (!ColSpanReport.isBlank() && !colSpanReportOld.equalsIgnoreCase(ColSpanReport)) {
						mapColSpan = new HashMap<String, Integer>();
						arrColSpan = ColSpanReport.split(",");
						for(int s = 0; s < arrColSpan.length; s ++) {
							String [] item = arrColSpan[s].trim().split("-");
							mapColSpan.put(item[0].trim(), Integer.valueOf(item[1].toString()));
						}
						colSpanReportOld = ColSpanReport;
					}
					
					//add group
					int nextcol = 0;
					if (itemsG != null && itemsG.length > 0) {
						itemG = itemsG[nextcol];
						element = (Serializable)rsC.getString(itemG.getName());
						String groupName = itemG.getName();
						if (!dataGroup.containsKey(groupName + "-o0o-"+ element) && element != null) {
							arrG = new ArrayList<PrintDataItem>();
							
							arrG.add(addNewItem(itemG, element, itemG.getColumnSpan()));
							nextcol++;
							while (nextcol < itemG.getColumnSpan()) {
								arrG.add(addNewItem(itemG, null, 1));
								nextcol++;
							}
							//nextcol = nextcol + itemG.getColumnSpan();
							//End 
							String [] obj = itemG.getFieldSumGroup().split(";");
							if (obj.length > 0) {
								for(int g = 0; g < obj.length; g++) {
									//Lay gia tri cot cua group.
									itemG = itemsC[nextcol];
									
									String [] col = obj[g].split("->");
									String colGroup = col[0].trim();
									String forColSum = col[1].trim();
									objGroup.put(forColSum, rsC.getBigDecimal(""+ colGroup +""));
									
									//add vao arr cua group
									Serializable valGroup = (Serializable)rsC.getBigDecimal(""+ colGroup +"");
									
									arrG.add(addNewItem(itemG, valGroup, 1));
									
									nextcol++;
								}
								dataGroup.put(groupName + "-o0o-"+ element, objGroup);
							}
							
							arrsC.add(arrG);
							countGroup++;
						}								
					}//End add group
					
					
					arrC = new ArrayList<PrintDataItem>();
					
					
					for(int i = 0; i < itemsC.length; i++) {
						MPrintFormatItem item = itemsC[i];
						//item.setNumLines(0);
						if (item.isMapColumnSelectSQL() && item.isPrinted()){
							element = (Serializable) rsC.getObject(item.getName());
							
							if (item.isNumber()) {
								dataRow.put(item.getColumnName(), element);
			        		}
							
							
							
							//Tinh toan doi voi cot dat cong thuc de day vao List data truoc khi view bao cao.
							if (item.isBalanceFinal() && item.getFormulaSetup() != "") {
								if (balances.containsKey(item.getColumnName()))
									valueRowOld = balances.get(item.getColumnName());
			        			balance = getBalanceRow(dataRow, item.getFormulaSetup(), item.getColumnName(), rowCount, valueRowOld); 
			        			element = balance;
			        			valueRowOld = balance;
			        			balances.put(item.getColumnName(), valueRowOld);
			        			valueRowOld = Env.ZERO;
			        		}
							
							//??O???n n??y set l???i colspan
							if (mapColSpan.size() > 0 && mapColSpan.containsKey(item.getColumnName())) {
								int colspan = mapColSpan.get(item.getColumnName());
								if (colspan > 0)
									item.setColumnSpan(colspan);
							}
							
							
							arrC.add(addNewItem(item, element));
								
							//Add do rong cua cac cot
							if (rowCount == 0) {
								arrWidth.add((float)itemsC[i].getMaxWidth());
									
							}
						}
						
						if (Integer.parseInt(item.getOrderRowHeader()) > maxRow && !item.isGroupBy()) {
							maxRow = Integer.parseInt(item.getOrderRowHeader());
						}
					}
					arrsC.add(arrC);
					if (sPara != null)
						sParas.add(sPara);
					rowCount++;
				}
				rsC.close();
				//End Content
				/*=========================================================*/
				

				
				/*=========================================================*/
				//Process Footer
				ResultSet rsF = (ResultSet) rs.getObject(3);
				ResultSetMetaData  metaF = rsF.getMetaData();
				while (rsF.next()) {

					arrF = new ArrayList<PrintDataItem>();
					
					String name = metaF.getColumnName(1);
					if ("?column?".equalsIgnoreCase(name))
						break;
					for(int i = 0; i < itemsF.length; i++) {
						
						MPrintFormatItem item = itemsF[i];
						if (item.isMapColumnSelectSQL() && item.isPrinted()){
							if(item.getName().contains("%#")) {
								String [] hf = item.getName().split("%#");
								for(int v = 0; v < hf.length; v++) {
					    			if(hf[v].contains("%")) {
					    				String columnName = hf[v].trim().substring(0, hf[v].lastIndexOf("%"));
					    				element = (Serializable) rsF.getObject(columnName);
					    				item.setName(columnName);
					    				break;
					    			}
					    			
					    		}        
							}
							arrF.add(addNewItem(item, element));						
						}
					}
					arrsF.add(arrF);
				}
				rsF.close();
				//End Footer
				/*=========================================================*/
				
				if (format.isShowChart()) {
					ResultSet rsChart = (ResultSet) rs.getObject(4);
					while (rsChart.next()) {
						
						arrC = new ArrayList<PrintDataItem>();
						if(itemsR == null)
							break;
						Object name = null;
						Object value = null;
						String key = "No Value";
						
						for(int i = 0; i < itemsR.length; i++) {
							MPrintFormatItem item = itemsR[i];
							element = (Serializable) rsChart.getObject(item.getName());
							
							if(!"NONE".equalsIgnoreCase(item.getChartColumn())) {
								if ("NAME".equals(item.getChartColumn()))
									name = element;
								if ("VALUE".equals(item.getChartColumn())) {
									value = element;
									if (value == null)
										value = Env.ZERO;
								}
								if ("GROUP".equals(item.getChartColumn())) {
									key = (String) element;
									if (key == null)
										key = "No Value";
								}
							}
							if (name != null && value != null) {
								arrChart = new Object [] {key, name, value};
							}
						}
						if (arrChart != null) {
							arrsChart.add(arrChart);
						}						
					}
					rsChart.close();
					
				}
				//End chart
				/*=========================================================*/
				
				
			} 
			conn.commit();
			conn.close();
		}
		catch (SQLException e)
		{
			log.saveError("Error", e);
		}
		finally
		{
			DB.close(rs, ps);
			rs = null; ps = null;
			
		}
		
		//Truong hop query khong co du lieu tra ve
		if (arrsC.size() == 0) {
			Serializable element = null;
			arrC = new ArrayList<PrintDataItem>();
			for(int i = 0; i < itemsC.length; i++) {
				MPrintFormatItem item = itemsC[i];
				if (item.isMapColumnSelectSQL() && item.isPrinted()) {
					arrC.add(addNewItem(item, element));
					//Add do rong cua cac cot
					arrWidth.add((float)itemsC[i].getMaxWidth());
						
				}
				if (Integer.parseInt(item.getOrderRowHeader()) > maxRow) {
					maxRow = Integer.parseInt(item.getOrderRowHeader());
				}
			}
			arrsC.add(arrC);
		}//End khogn co du lieu tra ve
		
		float [] retValue = new float [arrWidth.size()];
		for(int i = 0; i < arrWidth.size(); i++) {
			retValue[i] = (float)arrWidth.get(i);
		}
		
		//N???u c?? ??a template th?? ko ?????t maxRow n??y n???a, gi?? tr??? n??y ph???c v??? cho vi???c tr??? d??ng ?????u ti??n b??o c??o.
		if (sParas.size() > 0)
			maxRow = 0;
		m_pi.setWidthTable(retValue);
		m_pi.setDataQueryC(arrsC);
		m_pi.setDataQueryParam(sParas);
		
		m_pi.setDataChart(arrsChart);
		m_pi.setDataQueryH(arrsH);
		m_pi.setDataQueryF(arrsF);
		m_pi.setRowCountQuery(rowCount + countGroup);
		m_pi.setColumnCountQuery(arrWidth.size());
		m_pi.setMaxRow(maxRow);
		m_pi.setDataGroup(dataGroup);
		
		m_pi.setItemsC(itemsC);
		m_pi.setItemsF(itemsF);
		m_pi.setItemsH(itemsH);
	}
	
	
	private ParameterReport processRow(ResultSet rs){
		ParameterReport data = new ParameterReport();

		String result1 = "";
		try {
			result1 = rs.getString("Result1");
	    }
	    catch(SQLException e) {
	        
	    }
		String result2 = "";
		try {
			result2 = rs.getString("Result2");
	    }
	    catch(SQLException e) {
	        
	    }
		String result3 = "";
		try {
			result3 = rs.getString("Result3");
	    }
	    catch(SQLException e) {
	        
	    }
		String result4 = "";
		try {
			result4 = rs.getString("Result4");
	    }
	    catch(SQLException e) {
	        
	    }
		String result5 = "";
		try {
			result5 = rs.getString("Result5");
	    }
	    catch(SQLException e) {
	        
	    }
		String result6 = "";
		try {
			result6 = rs.getString("Result6");
	    }
	    catch(SQLException e) {
	        
	    }
		String result7 = "";
		try {
			result7 = rs.getString("Result7");
	    }
	    catch(SQLException e) {
	        
	    }
		String result8 = "";
		try {
			result8 = rs.getString("Result8");
	    }
	    catch(SQLException e) {
	        
	    }
		String result9 = "";
		try {
			result9 = rs.getString("Result9");
	    }
	    catch(SQLException e) {
	        
	    }
		
		String bold = "N";
		try {
			bold = rs.getString("IsBold");
	    }
	    catch(SQLException e) {
	        
	    }
		
		String sborder = "N";
		try {
			sborder = rs.getString("IsBorder");
	    }
	    catch(SQLException e) {
	        
	    }
		
		String header = "N";
		try {
			header = rs.getString("IsHeader");
	    }
	    catch(SQLException e) {
	        
	    }
		
		data.setBold("N".equals(bold) ? false : true);
		data.setBorder("N".equals(sborder) ? 0 : 1);
		data.setHeader("N".equals(header) ? false : true);
		data.setResult1(result1);
		data.setResult2(result2);
		data.setResult3(result3);
		data.setResult4(result4);
		data.setResult5(result5);
		data.setResult6(result6);
		data.setResult7(result7);
		data.setResult8(result8);
		data.setResult9(result9);
		
		return data;
	}
	

	private PrintDataItem addNewItem(MPrintFormatItem item, Serializable element) {
		return new PrintDataItem(
				item.getName(), 			//Ten cot
				element, 					//Gia tri cua cot
				item.getAD_Reference_ID(), 	//kieu hien thi
				item.getFormatPattern(),	//Dinh dang hien thi
				item.getZoomLogic(), 		//zoom den ban ghi goc
				item.getAlignment(), 		//Can trai, phai, giua
				item.getFormulaSetup(),		//thiet lap cong thuc tinh so du
				
				item.isGroupBy(), 			//Co phan nhom hay khong	
				item.isSummarized(), 		//Co cong tong bao cao hay khong
				item.isCountedGroup(),		//Dem so luong ban ghi cua nhom
				item.isBalanceFinal(),		//So du cuoi ky
				
				item.getTableName(),		//Ten bang can de zoom theo dieu kien ZoomLogic	
				item.getRotationText(),		//Huong chu tren header cua bao cao
				item.getFieldSumGroup(),	//T??nh t???ng theo group
				item.getNumLines(),
				item.getColumnSpan(),
				item.isBreakPage(),
				item.getPrintAreaType()
				);
	}
	
	private PrintDataItem addNewItem(MPrintFormatItem item, Serializable element, int colSpan) {
		return new PrintDataItem(
				item.getName(), 			//Ten cot
				element, 					//Gia tri cua cot
				item.getAD_Reference_ID(), 	//kieu hien thi
				item.getFormatPattern(),	//Dinh dang hien thi
				item.getZoomLogic(), 		//zoom den ban ghi goc
				item.getAlignment(), 		//Can trai, phai, giua
				item.getFormulaSetup(),		//thiet lap cong thuc tinh so du
				
				item.isGroupBy(), 			//Co phan nhom hay khong	
				item.isSummarized(), 		//Co cong tong bao cao hay khong
				item.isCountedGroup(),		//Dem so luong ban ghi cua nhom
				item.isBalanceFinal(),		//So du cuoi ky
				
				item.getTableName(),		//Ten bang can de zoom theo dieu kien ZoomLogic	
				item.getRotationText(),		//Huong chu tren header cua bao cao
				item.getFieldSumGroup(),	//T??nh t???ng theo group
				item.getNumLines(),
				colSpan,
				item.isBreakPage(),
				item.getPrintAreaType()
				);
	}
	
	protected int getWindowNo() 
	{
		return windowno;
	}
	
	protected ProcessInfo getProcessInfo()
	{
		return m_pi;
	}
	
	protected IProcessUI getProcessMonitor()
	{
		return m_processUI;
	}
	
	protected IProcessUI getParent()
	{
		return getProcessMonitor();
	}
	
	protected boolean isServerProcess()
	{
		return m_IsServerProcess;
	}
	
	
	private boolean startProcess (HashMap<String, Object> params)
	{
		if (log.isLoggable(Level.FINE)) log.fine(m_pi.toString());
		boolean started = false;
		
		boolean clientOnly = false;
		try {
			Class<?> processClass = Class.forName(m_pi.getClassName());
			if (ClientProcess.class.isAssignableFrom(processClass))
				clientOnly = true;
		} catch (Exception e) {}
		
		if (m_IsServerProcess && !clientOnly)
		{
			Server server = CConnection.get().getServer();
			try
			{
				if (server != null)
				{	
					//	See ServerBean
					m_pi = server.process (Env.getRemoteCallCtx(Env.getCtx()), m_pi);
					if (log.isLoggable(Level.FINEST)) log.finest("server => " + m_pi);
					started = true;		
				}
			}
			catch (UndeclaredThrowableException ex)
			{
				Throwable cause = ex.getCause();
				if (cause != null)
				{
					if (cause instanceof InvalidClassException)
						log.log(Level.SEVERE, "Version Server <> Client: " 
							+  cause.toString() + " - " + m_pi, ex);
					else
						log.log(Level.SEVERE, "AppsServer error(1b): " 
							+ cause.toString() + " - " + m_pi, ex);
				}
				else
					log.log(Level.SEVERE, " AppsServer error(1) - " 
						+ m_pi, ex);
				started = false;
			}
			catch (Exception ex)
			{
				Throwable cause = ex.getCause();
				if (cause == null)
					cause = ex;
				log.log(Level.SEVERE, "AppsServer error - " + m_pi, cause);
				started = false;
			}
		}
		
		if (!started && (!m_IsServerProcess || clientOnly ))
		{
			return ProcessUtil.startJavaProcess(Env.getCtx(), m_pi, m_trx, true, m_processUI, params);
		}
		return !m_pi.isError();
	}   //  startProcess
	
	
	private static void addProcessParametersNew(Map<String, Object> params, ProcessInfoParameter[] para)
    {
        
        if (para != null) {
        	for (int i = 0; i < para.length; i ++) {
        		String name = para[i].getParameterName();
                String pStr = para[i].getP_String();
                String pStrTo = para[i].getP_String_To();
                BigDecimal pNum = para[i].getP_Number();
                BigDecimal pNumTo = para[i].getP_Number_To();

                Timestamp pDate = para[i].getP_Date();
                Timestamp pDateTo = para[i].getP_Date_To();
                if (pStr != null) {
                    if (pStrTo!=null) {
                        params.put( name+"1", pStr);
                        params.put( name+"2", pStrTo);
                    } else {
                        params.put( name, pStr);
                    }
                } else if (pDate != null) {
                    if (pDateTo!=null) {
                        params.put( name+"1", pDate);
                        params.put( name+"2", pDateTo);
                    } else {
                        params.put( name, pDate);
                    }
                } else if (pNum != null) {
                	if (name.endsWith("_ID")) {
                		if (pNumTo!=null) {
	                        params.put( name+"1", pNum.intValue());
	                        params.put( name+"2", pNumTo.intValue());
	                    } else {
	                        params.put( name, pNum.intValue());
	                    }
                	} else {
	                    if (pNumTo!=null) {
	                        params.put( name+"1", pNum);
	                        params.put( name+"2", pNumTo);
	                    } else {
	                        params.put( name, pNum);
	                    }
                	}
                }
                //
                // Add parameter info - teo_sarca FR [ 2581145 ]
                String info = para[i].getInfo();
                String infoTo = para[i].getInfo_To();
                if (infoTo != null) {
                	params.put(name+"_View1", (info != null ? info : ""));
            		params.put(name+"_View2", (infoTo != null ? infoTo : ""));
                } else {
                	params.put(name+"_View", (info != null ? info : ""));
                }
        		
        	}
        }
    }

    private void addProcessInfoParameters(Map<String, Object> params, ProcessInfoParameter[] para)
    {
    	if (para != null) {
			for (int i = 0; i < para.length; i++) {
				if (para[i].getParameter_To() == null) {
					if (para[i].getParameterName().endsWith("_ID") && para[i].getParameter() instanceof BigDecimal) {
						params.put(para[i].getParameterName(), ((BigDecimal)para[i].getParameter()).intValue());
					} else {
						params.put(para[i].getParameterName(), para[i].getParameter());
					}
				} else {
					// range - from
					if (para[i].getParameterName().endsWith("_ID") && para[i].getParameter() != null && para[i].getParameter() instanceof BigDecimal) {
		                params.put( para[i].getParameterName()+"1", ((BigDecimal)para[i].getParameter()).intValue());
					} else {
		                params.put( para[i].getParameterName()+"1", para[i].getParameter());
					}
					// range - to
					if (para[i].getParameterName().endsWith("_ID") && para[i].getParameter_To() instanceof BigDecimal) {
		                params.put( para[i].getParameterName()+"2", ((BigDecimal)para[i].getParameter_To()).intValue());
					} else {
		                params.put( para[i].getParameterName()+"2", para[i].getParameter_To());
					}
				}
			}
    	}
	}
    
    private void loadInfoClient(HashMap<String, Object> params) {
    	params.put("AD_CLIENT_ID", Integer.valueOf( Env.getAD_Client_ID(Env.getCtx())));
    	//params.put("AD_ROLE_ID", Integer.valueOf( Env.getAD_Role_ID(Env.getCtx())));
    	params.put("AD_USER_ID", Integer.valueOf( Env.getAD_User_ID(Env.getCtx())));

    	params.put("AD_CLIENT_NAME", Env.getContext(Env.getCtx(), "#AD_Client_Name").toUpperCase());
    	params.put("AD_ROLE_NAME", Env.getContext(Env.getCtx(), "#AD_Role_Name").toUpperCase());
    	params.put("AD_USER_NAME", Env.getContext(Env.getCtx(), "#AD_User_Name"));
    	params.put("AD_ORG_NAME", Env.getContext(Env.getCtx(), "#AD_Org_Name").toUpperCase());
    	params.put("AD_LANGUAGE", Env.getAD_Client_ID(Env.getCtx()));
    	if (Env.getContext(Env.getCtx(), "#C_CurrencyDefault_ID") != null && !"".equals(Env.getContext(Env.getCtx(), "#C_CurrencyDefault_ID")))
    		params.put("C_CURRENCY_ID", Integer.valueOf(Env.getContext(Env.getCtx(), "#C_CurrencyDefault_ID")));
    	
    	MClient client = MClient.get(Env.getCtx());
    	MOrg org = MOrg.get(Env.getCtx(), Env.getContextAsInt(Env.getCtx(), "#AD_Org_ID"));
    	String address = "";
    	if (client.isGroup() && org.getAddress() != null && !org.getAddress().isEmpty()) {
    		address =  org.getAddress();
    		params.put("COMPANY_NAME", Env.getContext(Env.getCtx(), "#AD_Org_Name").toUpperCase());        	
    	} else {
    		address = client.getAddress();
    		params.put("COMPANY_NAME", Env.getContext(Env.getCtx(), "#AD_Client_Name").toUpperCase());        	
    	}
    	params.put("ADDRESS", address);
    	params.put("AD_DEPARTMENT_ID", Env.getContextAsInt(Env.getCtx(), "#AD_Department_ID"));
    	params.put("AD_DEPARTMENT_NAME", Env.getContext(Env.getCtx(), "#AD_Department_Name"));
    }
    
}	//	ProcessCtl
