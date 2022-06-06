package eone.base.impexp;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.standard.MediaSizeName;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import eone.base.process.ProcessInfo;
import eone.base.process.ProcessInfoParameter;
import eone.print.MPrintFormat;
import eone.print.MPrintFormatItem;
import eone.print.MPrintPaper;
import eone.print.ParameterReport;
import eone.print.PrintDataItem;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.Ini;
import eone.util.Language;
import eone.util.Util;

public class PrintDataXLSXExporter
{
	private MPrintFormat	m_printFormat;
	int rowCount = 0;
	int columnCount = 0;
	int windowNo;
	ProcessInfo info = null;
	private static float [] widthTable = null;
	private static MPrintFormatItem [] items = null;
	
	private XSSFWorkbook					m_workbook;
	private XSSFFont						m_fontHeader	= null;
	private XSSFFont						m_fontDefault	= null;
	private static List<ParameterReport> dataQueryParam = null;
	
	private HashMap<String, XSSFCellStyle>	m_styles		= new HashMap<String, XSSFCellStyle>();
	
	private List<ArrayList<PrintDataItem>> dataQuery = null;

	private HashMap<String, Object> m_params;
	
	public PrintDataXLSXExporter(HashMap<String, Object> m_params, MPrintFormat printFormat)
	{
		super();
		this.m_printFormat = printFormat;
		this.m_params = m_params;
		info = (ProcessInfo) m_params.get("ProcessInfo");
		rowCount = info.getRowCountQuery();
		windowNo = (int)m_params.get("WindowNo");
		columnCount = info.getColumnCountQuery();
		dataQuery = info.getDataQueryC();
		dataQueryParam = info.getDataQueryParam();
		widthTable = info.getWidthTable();
		m_workbook = new XSSFWorkbook();
		//m_params.clear();
		
	}

	
	public String getHeaderName(int col)
	{
		return m_printFormat.getItemMapSQL(col).getName(getLanguage(), windowNo);
	}
	
	protected Language getLanguage()
	{
		return Env.getLanguage(Env.getCtx());
	}

	public int getRowCount()
	{
		return rowCount;
	}

	public boolean isColumnPrinted(int col)
	{
		MPrintFormatItem item = m_printFormat.getItem(col);
		return item.isPrinted();
	}

	


	protected void formatPage(XSSFSheet sheet)
	{
		MPrintPaper paper = MPrintPaper.get(this.m_printFormat.getAD_PrintPaper_ID());

		// Set paper size:
		short paperSize = -1;
		MediaSizeName mediaSizeName = paper.getMediaSize().getMediaSizeName();
		if (MediaSizeName.NA_LETTER.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.LETTER_PAPERSIZE;
		}
		else if (MediaSizeName.NA_LEGAL.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.LEGAL_PAPERSIZE;
		}
		else if (MediaSizeName.EXECUTIVE.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.EXECUTIVE_PAPERSIZE;
		}
		else if (MediaSizeName.ISO_A4.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.A4_PAPERSIZE;
		}
		else if (MediaSizeName.ISO_A5.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.A5_PAPERSIZE;
		}
		else if (MediaSizeName.NA_NUMBER_10_ENVELOPE.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.ENVELOPE_10_PAPERSIZE;
		}
		else if (MediaSizeName.MONARCH_ENVELOPE.equals(mediaSizeName))
		{
			paperSize = HSSFPrintSetup.ENVELOPE_MONARCH_PAPERSIZE;
		}
		if (paperSize != -1)
		{
			sheet.getPrintSetup().setPaperSize(paperSize);
		}

		// Set Landscape/Portrait:
		sheet.getPrintSetup().setLandscape(paper.isLandscape());

		
		sheet.setMargin(HSSFSheet.TopMargin, ((double) paper.getMarginTop()) / 72);
		sheet.setMargin(HSSFSheet.RightMargin, ((double) paper.getMarginRight()) / 72);
		sheet.setMargin(HSSFSheet.LeftMargin, ((double) paper.getMarginLeft()) / 72);
		sheet.setMargin(HSSFSheet.BottomMargin, ((double) paper.getMarginBottom()) / 72);
	}
	

	
	private XSSFFont getFont(boolean isHeader)
	{
		XSSFFont font = null;
		if (isHeader)
		{
			if (m_fontHeader == null)
			{
				m_fontHeader = m_workbook.createFont();
				m_fontHeader.setBold(true);
			}
			font = m_fontHeader;
		}
		else
		{
			if (m_fontDefault == null)
			{
				m_fontDefault = m_workbook.createFont();
			}
			font = m_fontDefault;
		}
		return font;
	}


	private XSSFSheet createTableSheet()
	{
		XSSFSheet sheet = m_workbook.createSheet();
		for(int i = 0; i < widthTable.length; i++) {
			int width = (int) widthTable[i];
			sheet.setColumnWidth(i, width * 45);
		}
		sheet.setAutobreaks(true);
		Footer footer = sheet.getFooter();
		footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );
		formatPage(sheet);
		createHeaderReport(sheet);
		createTableContent(sheet);
		return sheet;
	}
	
	private void createHeaderReport(XSSFSheet sheet) {
		//Header Report
		XSSFRow row = null;
		row = sheet.createRow(rowCreateCount);
		rowCreateCount++;
		String company = m_params.get("COMPANY_NAME").toString();
		String address = m_params.get("ADDRESS").toString();
		String tempApply = "";
		if (m_params.get("TEMPAPPLY") != null) {
			tempApply = m_params.get("TEMPAPPLY").toString();
		}
		
		int colExcel = 0;
		int colspan = (int) columnCount/2;
		XSSFCell cell = row.createCell(colExcel);
		XSSFCellStyle style = null;//
		style = getItemStyle(colExcel, true, 0, false, false, 0);
		sheet.addMergedRegion(new CellRangeAddress(rowCreateCount - 1, rowCreateCount - 1, colExcel, colspan - 1));
		cell.setCellValue(company);
		cell.setCellStyle(style);
		
		cell = row.createCell(colExcel + colspan);
		sheet.addMergedRegion(new CellRangeAddress(rowCreateCount - 1, rowCreateCount, colspan, columnCount - 1));
		cell.setCellValue(tempApply);
		style = getItemStyle(colExcel + colspan, false, 0, false, true, 0);
		cell.setCellStyle(style);

		row = sheet.createRow(rowCreateCount);
		rowCreateCount++;
		cell = row.createCell(colExcel);
		sheet.addMergedRegion(new CellRangeAddress(rowCreateCount - 1, rowCreateCount - 1, colExcel, colspan - 1));
		cell.setCellValue(address);
		
		row = sheet.createRow(rowCreateCount);
		rowCreateCount++;
				
		row = sheet.createRow(rowCreateCount);
		rowCreateCount++;
		String titleReport = m_params.get("ReportName").toString().toUpperCase();
		cell = row.createCell(colExcel);
		sheet.addMergedRegion(new CellRangeAddress(rowCreateCount - 1, rowCreateCount - 1, colExcel, columnCount - 1));
		style = getItemStyle(colExcel, true, 0, false, true, 0);
		cell.setCellStyle(style);
		cell.setCellValue(titleReport);
		
		
		//Insert tham số
		ProcessInfoParameter[] para = info.getProcessPara();
		String date = "";
        ArrayList<String> arrOtherParam = new ArrayList<String>();
    	for (int i = 0; i < para.length; i ++) {
    		ProcessInfoParameter item = para[i];
    		String other = "";
    		String name = para[i].getParameterName();
            String info = para[i].getInfo();
    		if ("FromDate".equalsIgnoreCase(name)) {
    			date += item.getHeader()  + ": " + info + " - ";
    		}
    		else if ("ToDate".equalsIgnoreCase(name)) {
    			date += item.getHeader()  + ": " + info;
    		} else if (info != null && !info.isEmpty()) {
    			other = item.getHeader()  + ": " + info;
    		}
    		arrOtherParam.add(other);
    	}
    	//Fill ngày tháng
    	row = sheet.createRow(rowCreateCount);
		rowCreateCount++;
		cell = row.createCell(colExcel);
		sheet.addMergedRegion(new CellRangeAddress(rowCreateCount - 1, rowCreateCount - 1, colExcel, columnCount - 1));
		style = getItemStyle(colExcel, false, 0, false, true, 0);
		cell.setCellStyle(style);
		cell.setCellValue(date);
		
		
		//Fill tham số còn lại
		for(int i = 0; i < arrOtherParam.size(); i++) {
			if (arrOtherParam.get(i) != null && !arrOtherParam.get(i).isBlank()) {
				row = sheet.createRow(rowCreateCount);
				rowCreateCount++;
				cell = row.createCell(colExcel);
				sheet.addMergedRegion(new CellRangeAddress(rowCreateCount - 1, rowCreateCount - 1, colExcel, columnCount - 1));
				cell.setCellValue(arrOtherParam.get(i));
				
			}
        }
	}
	
	//Biến này dùng đẩy số dòng dữ liệu
	private int rowCreateCount = 0;

	private void createTableContent(XSSFSheet sheet)
	{
		
		int maxRow = info.getMaxRow();
		items = m_printFormat.getItemContent();
		XSSFRow row = null;
		row = sheet.createRow(rowCreateCount);
		rowCreateCount++;
		
		for(int r = rowCreateCount; r < maxRow + rowCreateCount; r++) {
			row = sheet.createRow(r);
			int colStart = 0;
			int colEnd = 0;
			int rowStart = -1;
			int rowEnd = 0;
			int colExcel = 0;
    		for(int c = 0; c < items.length; c++) {
            	MPrintFormatItem item = items[c];
            	int rowOrder = Integer.parseInt(item.getOrderRowHeader());
            	if (rowOrder == r - rowCreateCount + 1 && item.isPrinted()) {
            		XSSFCell cell = row.createCell(colExcel);
            		XSSFCellStyle style = getHeaderStyle(colExcel);
    				cell.setCellStyle(style);
           			// header row
    				
    				if (item.getNumLines() > 1 && item.getColumnSpan() > 1)
    				{
    					colStart = colExcel;
    					colEnd = colExcel + item.getColumnSpan() - 1;
    					rowStart = r;
    					rowEnd = r + item.getNumLines() - 1;
    				}
    				else if (item.getColumnSpan() > 1) {
    					colStart = colExcel;
    					colEnd = colExcel + item.getColumnSpan() - 1;
    					rowStart = r;
    					rowEnd = r;
    				}
    				else if (item.getNumLines() > 1) {
    					colStart = colExcel;
    					colEnd = colExcel;
    					rowStart = r;
    					rowEnd = r + item.getNumLines() - 1;    					
    				} else {
    					colStart = colExcel;
    					colEnd = colExcel;
    					rowStart = r;
    					rowEnd = r;
    				}
    				
    				String str = fixString(item.getName(Env.getLanguage(Env.getCtx()), windowNo));
    				//System.out.println("header: "+ rowOrder + ": "+ str);
    				setBorderRange(colStart, colEnd, rowStart, rowEnd, sheet, 1);
    				
    				cell.setCellValue(new XSSFRichTextString(str));
    				colExcel += item.getColumnSpan();
    				//columnCount
    			} else if (r - rowCreateCount == rowOrder && item.getNumLines() > 1) {
            		colExcel += item.getColumnSpan();
            	} else if (r - rowCreateCount > rowOrder && r - rowCreateCount == item.getNumLines()) {
            		colExcel = 0;
            	}
            }            
    	}
		
		rowCreateCount += maxRow - 1;
		
	}
	
	private String fixString(String str)
	{
		return Util.stripDiacritics(str);
	}

	private void setBorderRange(int colStart, int colEnd, int rowStart, int rowEnd, XSSFSheet sheet, int border) {
		try {
			if (rowStart != rowEnd || colStart != colEnd) {
				CellRangeAddress range = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
				sheet.addMergedRegion(range);
				if (border == 1) {
					RegionUtil.setBorderTop(BorderStyle.THIN, range, sheet);
					RegionUtil.setBorderLeft(BorderStyle.THIN, range, sheet);
					RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
					RegionUtil.setBorderBottom(BorderStyle.THIN, range, sheet);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void export(OutputStream out) throws Exception
	{
		XSSFSheet sheet = createTableSheet();
		
		for (int r = 0; r < rowCount; r ++)
		{
			int pos = 0;
			ArrayList<PrintDataItem> arrItem = dataQuery.get(r);
			XSSFRow row = sheet.createRow(r+ rowCreateCount + 1);
			//rowCreateCount++;
			
			int border = 1;
        	ParameterReport sParams = new ParameterReport();
        	boolean isHeader = false;
        	boolean isBold = false;
        	if (dataQueryParam != null && dataQueryParam.size() > 0) {
        		
        		sParams = dataQueryParam.get(r);
        		isBold = sParams.isBold();
        		border = sParams.getBorder();
        		isHeader = sParams.isHeader();
        		
        	}
    		
        	
			// for all columns
			for (int col = 0; col < arrItem.size(); col++)
			{
				PrintDataItem item = arrItem.get(col);
				int displayType = item.getDisplayType();
				
				Object obj = item.getValue();
				
				XSSFCell cell = row.createCell(col);
				XSSFCellStyle style = getItemStyle(col, isBold, border, isHeader, false, displayType);
				cell.setCellStyle(style);
				
				//if (item.getColSpan() > 1)
				pos = item.getColSpan() - 1;
				
				if (!isHeader) {
					item.setRowSpan(1);
				}
				if (dataQueryParam != null && dataQueryParam.size() > 0) {
					if (col < col + pos || item.getRowSpan() > 1) {
						try {
							
							setBorderRange(col, col + pos, r+ rowCreateCount + 1, r+ rowCreateCount + item.getRowSpan(), sheet, border);
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						
					}
				}
				
				
				if (item.getColSpan() > 1) {
					col = col + item.getColSpan() - 1;
				}
				
				if (isHeader) {
        			Object valueHeader = getNameHeader(item, sParams);
        			if (valueHeader != null)
        				obj = valueHeader;
        		}
				
				if (!isHeader) {
					if (DisplayType.isDate(displayType))
					{
						java.util.Date value = (java.util.Date) obj;
						cell.setCellValue(value);
						
					}
					else if (DisplayType.isNumeric(displayType))
					{
						double value = 0;
						if (obj instanceof Number)
						{
							value = ((Number) obj).doubleValue();
						}
						if (value != 0)
						{
							cell.setCellValue(value);
							
						}
					}
					
					else 
					{
						if (obj != null)
						{
							String value = fixString(obj.toString()); // formatted
							cell.setCellValue(new XSSFRichTextString(value));
						}
					}
				} else {
					if (obj != null) {
						String value = fixString(obj.toString()); // formatted
						cell.setCellValue(new XSSFRichTextString(value));
					} 
				}
				
				
			}
			// for all columns
			
		} // for all rows

		if (out != null)
		{
			m_workbook.write(out);
			out.close();
		}
		
	}
	
	private Object getNameHeader(PrintDataItem item , ParameterReport sParams) {
    	String colName = item.getColumnName();
    	if ("Amt1".equals(colName))
    		return sParams.getResult1();
    	if ("Amt2".equals(colName))
    		return sParams.getResult2();
    	if ("Amt3".equals(colName))
    		return sParams.getResult3();
    	if ("Amt4".equals(colName))
    		return sParams.getResult4();
    	if ("Amt5".equals(colName))
    		return sParams.getResult5();
    	if ("Amt6".equals(colName))
    		return sParams.getResult6();
    	if ("Amt7".equals(colName))
    		return sParams.getResult7();
    	if ("Amt8".equals(colName))
    		return sParams.getResult8();
    	if ("Amt9".equals(colName))
    		return sParams.getResult9();
    	return null;
    }

	
	public void export(File file, boolean autoOpen) throws Exception
	{
		if (file == null)
			file = File.createTempFile("Report_", ".xlsx");
		FileOutputStream out = new FileOutputStream(file);
		export(out);
		if (autoOpen && Ini.isClient())
			Env.startBrowser(file.toURI().toString());
	}

	

	private XSSFCellStyle getHeaderStyle(int col)
	{
		String key = "header-" + col;
		XSSFCellStyle cs_header = m_styles.get(key);
		if (cs_header == null)
		{
			XSSFFont font_header = getFont(true);
			cs_header = m_workbook.createCellStyle();
			cs_header.setFont(font_header);
			cs_header.setBorderLeft(BorderStyle.THIN);
			//cs_header.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
			cs_header.setBorderTop(BorderStyle.THIN);
			cs_header.setBorderRight(BorderStyle.THIN);
			cs_header.setBorderBottom(BorderStyle.THIN);
			cs_header.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			cs_header.setWrapText(true);
			cs_header.setVerticalAlignment(VerticalAlignment.CENTER);
			cs_header.setAlignment(HorizontalAlignment.CENTER);
			cs_header.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY, new DefaultIndexedColorMap()));
			cs_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cs_header.setDataFormat(14);
			m_styles.put(key, cs_header);
		}
		return cs_header;
	}
	
	private XSSFCellStyle getItemStyle(int col, boolean isBold, int border, boolean header, boolean isCenter, int dataType)
	{
		String key = "item-" + col + isBold + border + header + isCenter + dataType;
		
		
		
		XSSFCellStyle cs_header = m_styles.get(key);
		if (cs_header == null)
		{
			cs_header = m_workbook.createCellStyle();
			
			if (!DisplayType.isText(dataType) && dataType > 0) {
				if (DisplayType.isDate(dataType))
				{
					CreationHelper createHelper = m_workbook.getCreationHelper();
					cs_header.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
					cs_header.setAlignment(HorizontalAlignment.CENTER);
				}
				else if (DisplayType.isNumeric(dataType))
				{
					CreationHelper createHelper = m_workbook.getCreationHelper();
					cs_header.setDataFormat(createHelper.createDataFormat().getFormat("#,###"));
					cs_header.setAlignment(HorizontalAlignment.RIGHT);
				}
				
				if (header) {
					cs_header.setAlignment(HorizontalAlignment.CENTER);
				}
			} else {
				cs_header.setDataFormat(BuiltinFormats.getBuiltinFormat("text"));
				cs_header.setAlignment(HorizontalAlignment.LEFT);
			}
			if (isBold) {
				XSSFFont font_header = getFont(true);
				cs_header.setFont(font_header);
			}
			if (border > 0) {
				cs_header.setBorderLeft(BorderStyle.THIN);
				cs_header.setBorderTop(BorderStyle.THIN);
				cs_header.setBorderRight(BorderStyle.THIN);
				cs_header.setBorderBottom(BorderStyle.THIN);
			}
			if (header) {
				cs_header.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY, new DefaultIndexedColorMap()));
				cs_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			if (isCenter)
				cs_header.setAlignment(HorizontalAlignment.CENTER);
			
			cs_header.setVerticalAlignment(VerticalAlignment.CENTER);
			cs_header.setWrapText(true);
			
			m_styles.put(key, cs_header);
		}
		return cs_header;
	}
	
}
