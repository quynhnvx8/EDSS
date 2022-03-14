package eone.base.impexp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.base.IGridTabExporter;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;
import org.compiere.EOne;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluator;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.compiere.util.Util;

import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.GridTable;
import eone.base.model.MColumn;
import eone.base.model.MLookupFactory;
import eone.base.model.MQuery;
import eone.base.model.MRefList;
import eone.base.model.MTab;
import eone.base.model.MTable;
import eone.exceptions.EONEException;

public class GridTabXLSXExporter implements IGridTabExporter {
	/** Logger */
	private static CLogger log = CLogger.getCLogger(GridTabXLSXExporter.class);
	private static Language languageDefault = Env.getLanguage(Env.getCtx());
	private Map<GridTab, RowIndex> mapIdxRow = null;
	private GridTab m_currentTab = null;
	//
	private XSSFWorkbook m_mapWriter = null;
	private XSSFSheet m_sheetWriter = null;
	private FileOutputStream m_outWriter = null;
	private XSSFDataFormat m_dataFormat = null;
	private XSSFFont m_fontHeader = null;
	private XSSFFont m_fontDefault = null;
	/** Styles cache */
	private HashMap<String, XSSFCellStyle> m_styles = new HashMap<String, XSSFCellStyle>();
	/** Header */
	private List<Integer> m_TabFragmentIndexes = null;
	//
	private int m_colSplit = 1;
	private int m_rowSplit = 2;
	private int m_sheetCount = 0;

	@Override
	public void export(GridTab gridTab, List<GridTab> childs, boolean currentRowOnly, File file,
			int indxDetailSelected) {

		Map<GridTab, GridField[]> tabMapDetails = new HashMap<GridTab, GridField[]>();
		MTable table = null;
		MTable tableDetail = null;
		m_currentTab = gridTab;
		try {
			m_outWriter = new FileOutputStream(file);
			m_mapWriter = new XSSFWorkbook();
			m_dataFormat = m_mapWriter.createDataFormat();
			m_TabFragmentIndexes = new ArrayList<Integer>();

			String isValidTab = isValidTabToExport(gridTab);
			if (isValidTab != null) {
				throw new EONEException(isValidTab);
			}
			GridTable gt = gridTab.getTableModel();
			GridField[] gridFields = getFields(gridTab);
			if (gridFields.length == 0)
				throw new EONEException(gridTab.getName() + ": Did not find any available field to be exported.");

			List<String> headArray = new ArrayList<String>(); //Array column from DB
			List<String> headNameArray = new ArrayList<String>();//Array Column Name display
			m_sheetWriter = createTableSheet(headArray, headNameArray);
			List<CellProcessor> procArray = new ArrayList<CellProcessor>();
			table = MTable.get(Env.getCtx(), gridTab.getTableName());
			//int specialHDispayType = 0;
			int col = 0;
			// master tab
			for (int idxfld = 0; idxfld < gridFields.length; idxfld++) {
				GridField field = gridFields[idxfld];
				MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
				String header = field.getHeader();
				if (column.isKey())
					header = "KEY";
				String headName = resolveColumnName(table, column, field);
				if (headName == null) {
					continue;
				}
				headArray.add(headName);
				headNameArray.add(header + "{"+ headName +"}");
				procArray.add(getProccesorFromColumn(field));
				String format = field.getFormatPattern();
				if (format == null || format.length() == 0) {
					format = column.getFormatPattern();
				}
				createStyle(col++, field.getDisplayType());

			}
			m_TabFragmentIndexes.add(col);
			m_colSplit = col;
			
			// Details up to tab level 1
			if (childs.size() > 0) {
				//int specialDetDispayType = 0;
				for (GridTab detail : childs) {

					if (!detail.isDisplayed())
						continue;

					if (detail.getDisplayLogic() != null) {
						if (!Evaluator.evaluateLogic(detail, detail.getDisplayLogic()))
							continue;
					}
					// comment this line if you want to export all tabs
					isValidTab = isValidTabToExport(detail);
					if (isValidTab != null) {
						if (log.isLoggable(Level.INFO))
							log.info(isValidTab);
						continue;
					}
					tableDetail = MTable.get(Env.getCtx(), detail.getTableName());
					gridFields = getFields(detail);
					for (GridField field : gridFields) {
						MColumn columnDetail = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
						String header = field.getHeader();
						if(columnDetail.isKey())
							header = "KEY";
						if (columnDetail.isParent())
							header = "PARENT";
						String headNameDetail = detail.getTableName();
						String resolveCol = resolveColumnName(tableDetail, columnDetail, field);
						if (resolveCol == null) {
							continue;
						}

						headNameDetail += "." + resolveCol;
						headArray.add(headNameDetail);
						headNameArray.add(header + "{"+ headNameDetail +"}");
						procArray.add(getProccesorFromColumn(field));
						String format = field.getFormatPattern();
						if (format == null || format.length() == 0) {
							format = columnDetail.getFormatPattern();
						}
						createStyle(col++, field.getDisplayType());

					}
					
					tabMapDetails.put(detail, gridFields);
					m_TabFragmentIndexes.add(col);
				}
				gridFields = null;
			}

			String[] header = headArray.toArray(new String[headArray.size()]);

			createTableHeader(m_sheetWriter, headNameArray, headArray);
			// write the beans
			int start = 0;
			int end = 0;
			if (currentRowOnly) {
				start = gridTab.getCurrentRow();
				end = start + 1;
			} else {
				end = gt.getRowCount();
			}
			Map<String, Object> row = null;
			for (int idxrow = start; idxrow < end; idxrow++) {
				row = new HashMap<String, Object>();
				int idxfld = 0;
				int index = 0;
				int rowDetail = 0;
				boolean isActiveRow = true;

				for (GridField field : getFields(gridTab)) {
					MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
					Object value = null;

					String headName = header[idxfld];
					value = resolveValue(gridTab, table, column, idxrow, headName);
					// Ignore row
					if ("IsActive".equals(headName) && value != null && Boolean.valueOf((Boolean) value) == false) {
						isActiveRow = false;
						break;
					}
					row.put(headName, value);
					idxfld++;
					index++;
				}
				if (!isActiveRow)
					continue;

				int maxTabLevel = 0;
				for (GridTab child : childs) {
					if (maxTabLevel < child.getTabLevel()) {
						maxTabLevel = child.getTabLevel();
					}
				}

				Map<String, Map<String, Object>> tmpRow = null;

				tmpRow = resolveMasterDetailRow(rowDetail, tabMapDetails, headArray, index,
						gridTab.getKeyID(idxrow), gridTab.getKeyColumnName(), maxTabLevel);
				if (tmpRow != null) {
					for (Map.Entry<String, Map<String, Object>> details : tmpRow.entrySet()) {
						Map<String, Object> value = details.getValue();
						for(Map.Entry<String, Object> valCol : value.entrySet()) {
							String key = valCol.getKey();
							Object val = valCol.getValue();
							row.put(key, val);									
						}
						rowDetail++;
						createTableDetail(m_sheetWriter, row, headArray, procArray);
					}
					
				}

				tmpRow = null;
				//tabMapDetails = null;

				if (rowDetail == 0)
					createTableDetail(m_sheetWriter, row, headArray, procArray);

				idxfld = 0;
			}
			row = null;

			if (m_outWriter != null) {
				closeTableSheet(m_sheetWriter, "template", headArray.size());
				m_mapWriter.write(m_outWriter);
			}
		} catch (IOException e) {
			throw new EONEException(e);
		} finally {
			if (m_outWriter != null) {
				try {
					m_outWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//Clear Hashmaps
			mapForeignColumn = null;
			refValue = null;
			mapIdxRow = null;
			tabMapDetails = null;
			
		}

	}

	private void closeTableSheet(XSSFSheet prevSheet, String prevSheetName, int colCount) {
		if (prevSheet == null)
			return;
		//
		fixColumnWidth(prevSheet, colCount);
		if (m_colSplit >= 0 || m_rowSplit >= 0)
			prevSheet.createFreezePane(1, m_rowSplit >= 0 ? m_rowSplit : 0);
	}

	protected void setFreezePane(int colSplit, int rowSplit) {
		m_colSplit = colSplit;
		m_rowSplit = rowSplit;
	}

	// add constraints to not allow certain tabs
	private String isValidTabToExport(GridTab gridTab) {
		String result = null;

		MTab tab = new MTab(Env.getCtx(), gridTab.getAD_Tab_ID(), null);

		if (tab.isReadOnly())
			result = Msg.getMsg(Env.getCtx(), "FieldIsReadOnly", new Object[] { gridTab.getName() });

		if (gridTab.getTableName().endsWith("_Acct"))
			result = "Accounting Tab are not exported by default: " + gridTab.getName();

		return result;
	}

	private void fixColumnWidth(XSSFSheet sheet, int lastColumnIndex) {
		for (short colnum = 0; colnum < lastColumnIndex; colnum++) {
			sheet.autoSizeColumn(colnum);
		}
	}

	private int index = 0;
	
	private Map<String, Map<String, Object>> resolveMasterDetailRow(int currentDetRow, Map<GridTab, GridField[]> tabMapDetails,
			List<String> headArray, int idxfld, int record_Id, String keyColumnParent, int maxTabLevel) {
		Map<String, Map<String, Object>> activeRow = new HashMap<String, Map<String, Object>>();
		Object value = null;
		boolean hasDetails = false;
		//int specialDetDispayType = 0;
		int r_Id = 0;
		String keyColumnName = "";

		if (mapIdxRow == null) {
			mapIdxRow = new HashMap<GridTab, RowIndex>();
		}

		if (currentDetRow > 0)
			for (int j = 0; j < idxfld; j++)
				activeRow.put(headArray.get(j), null);

		for (Map.Entry<GridTab, GridField[]> childTabDetail : tabMapDetails.entrySet()) {
			GridTab childTab = childTabDetail.getKey();

			if (!mapIdxRow.containsKey(childTab)) {
				RowIndex rowIndex = new RowIndex(0, childTab.getKeyColumnName());
				mapIdxRow.put(childTab, rowIndex);
			}

			if (childTab.getTabLevel() == (m_currentTab.getTabLevel() + 1)) {
				r_Id = record_Id;
				keyColumnName = keyColumnParent;
			} else {
				GridTab parent = childTab.getParentTab();
				if (parent != null) {
					keyColumnName = parent.getKeyColumnName();
					RowIndex rowIndx = mapIdxRow.get(parent);
					if (rowIndx == null)
						continue;
					String whereCla = rowIndx.getWhereClause();

					if (whereCla == null || whereCla.length() == 0)
						continue;
					parent.getTableModel().dataRequery(whereCla, false, 0);
					Object idO = parent.getValue(rowIndx.m_key, keyColumnName);
					if (idO == null)
						continue;

					r_Id = Integer.parseInt(idO.toString());
					keyColumnName = parent.getKeyColumnName();
					RowIndex rowIndxChild = mapIdxRow.get(childTab);
					if (rowIndxChild != null) {
						rowIndxChild.setParent(rowIndx);
					}
				}

			}

			String whereCla = getWhereClause(childTab, r_Id, keyColumnName);
			if (whereCla == null && childTab.getWhereClause().length() > 0) {
				log.info("Not support for link tables by SQLWhere!");
				// Next row
				if (maxTabLevel == childTab.getTabLevel() || childTab.getChildrenTab().length == 0) {
					RowIndex rowIndex = mapIdxRow.get(childTab);
					rowIndex.nextIndex();
				}
				continue;
			}

			childTab.getTableModel().dataRequery(whereCla, false, 0);
			Map<String, Object> row = new HashMap<String, Object>();
			boolean isActiveRow = true;
			if (childTab.getRowCount() > 0) {
				for(int i = 0; i < childTab.getRowCount(); i++) {
					row = new HashMap<String, Object>();
					String key = "";
					
					//int specialRecordId = 0;
					RowIndex rowIndex = mapIdxRow.get(childTab);
					if (rowIndex != null) {
						rowIndex.setWhereClause(whereCla);
						rowIndex.setRowCount(childTab.getRowCount());
					}
					for (GridField field : childTabDetail.getValue()) {
 						MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
						
						MTable tableDetail = MTable.get(Env.getCtx(), childTab.getTableName());
						String resolveCol = resolveColumnName(tableDetail, column, field);

						if (resolveCol == null) {
							continue;
						}

						String headName = headArray.get(headArray.indexOf(childTab.getTableName() + "." + resolveCol));
						value = resolveValue(childTab, MTable.get(Env.getCtx(), childTab.getTableName()), column,
								i, headName.substring(headName.indexOf(".") + 1, headName.length()));
						if( column.isKey()) {
							if (value != null) {
								key = value.toString();
							}
						} 
						
						row.put(headName, value);
						
						if (value != null)
							hasDetails = true;
						// Ignore row
						if (headName.contains("IsActive") && value != null && Boolean.valueOf((Boolean) value) == false) {
							isActiveRow = false;
							break;
						}
					}
					// Next row
					if (maxTabLevel == childTab.getTabLevel() || childTab.getChildrenTab().length == 0) {
						rowIndex.nextIndex();
					}
					
					if (isActiveRow && !key.isEmpty())
						activeRow.put(key, row);
					//row.clear();
				}
				index = index + childTab.getRowCount();
			} else {
				// Next row
				if (maxTabLevel == childTab.getTabLevel()) {
					RowIndex rowIndex = mapIdxRow.get(childTab);
					rowIndex.nextIndex();
				}
			}
			
		}

		if (hasDetails) {
			return activeRow;
		} else {
			return null;
		}
	}

	public String getWhereClause(GridTab childTab, int record_Id, String keyColumnParent) {
		String whereClau = null;
		String linkColumn = childTab.getLinkColumnName();
		if (keyColumnParent.equals(linkColumn) && !keyColumnParent.isEmpty()) {
			whereClau = linkColumn + MQuery.EQUAL + record_Id;
		}
		return whereClau;
	}

	private Map<String, Object> mapForeignColumn = new HashMap<String, Object>();
	
	private Object resolveValue(GridTab gridTab, MTable table, MColumn column, int i, String headName) {
		Object value = null;
		if (headName.indexOf("[") >= 0 && headName.endsWith("]")) {
			String foreignTable = column.getReferenceTableName();
			Object idO = gridTab.getValue(i, column.getColumnName());
			if (idO != null) {
				if (foreignTable.equals("AD_Ref_List")) {
					String ref = (String) idO;
					value = ref +" - "+ MRefList.getListName(Env.getCtx(), column.getAD_Reference_Value_ID(), ref);
				} else {
					int id = Integer.parseInt(idO.toString());
					int start = headName.indexOf("[") + 1;
					int end = headName.length() - 1;
					String foreignColumn = headName.substring(start, end);
					if (foreignColumn.contains(" - ")) {
						foreignColumn = foreignColumn.replace(" - ", "||'-'||");
					}
					if (mapForeignColumn.containsKey(id + "_" + foreignTable))
						value = mapForeignColumn.get(id + "_" + foreignTable);
					else {
						StringBuilder select = new StringBuilder("SELECT ").append(foreignColumn).append(" FROM ")
								.append(foreignTable).append(" WHERE ").append(foreignTable).append("_ID=?");
						
						value = id + " - " + DB.getSQLValueStringEx(null, select.toString(), id);
						mapForeignColumn.put(id + "_" + foreignTable, value);
					}
				}
			}
		} else {
			value = gridTab.getValue(i, headName);
		}
		return value;
	}

	Map<Integer, Object []> refValue = new HashMap<Integer, Object[]>();
	
	private String resolveColumnName(MTable table, MColumn column, GridField field) {
		StringBuilder name = new StringBuilder(column.getColumnName());

		if (DisplayType.isLookup(column.getAD_Reference_ID())) {
			String foreignTable = column.getReferenceTableName();
			if (!"AD_Language".equals(foreignTable)) {
				// Support for Customize Column, Virtual Column
				// Hardcoded / do not check for Value on AD_Org, AD_User and AD_Ref_List, must
				// use name for these two tables
				if (!("AD_Ref_List".equals(foreignTable))) {
					// Get Reference Table
					MTable fTable = MTable.get(Env.getCtx(), foreignTable);
					// TableDir
					if (column.getAD_Reference_ID() == DisplayType.TableDir
							|| (field.getAD_Reference_Value_ID() == 0 && column.getAD_Reference_Value_ID() == 0)) {
						if (column.isParent() || column.isKey()
								|| column.getColumnName().equalsIgnoreCase(field.getGridTab().getLinkColumnName())) {
							name.append("[").append(column.getColumnName()).append("]");
						} else if (fTable.getColumn("Value") != null || fTable.getColumn("Name") != null
								|| fTable.getColumn("DocumentNo") != null) {
							name.append("[");
							if (fTable.getColumn("Value") != null) {
								name.append("Value - ");
							}

							if (fTable.getColumn("Name") != null) {
								name.append("Name - ");
							}

							if (fTable.getColumn("DocumentNo") != null) {
								name.append("DocumentNo - ");
							}

							name.setLength(name.length() - 3); // Delete ||'-'||
							name.append("]");
						} else {
							String displayColumn = MLookupFactory.getDisplayColumn(Env.getLanguage(Env.getCtx()),
									column.getColumnName());
							if (displayColumn != null && displayColumn.length() > 0) {
								name.append("[").append(displayColumn).append("]");
							}
						}
					}
					// Table, Search
					else if (!column.isKey() && !column.isParent()
							&& !column.getColumnName().equalsIgnoreCase(field.getGridTab().getLinkColumnName())) {
						String sql = "SELECT t.TableName,ck.ColumnName AS KeyColumn,"
								+ "cd.ColumnName AS DisplayColumn,rt.isValueDisplayed,cd.IsTranslated, "
								+ "cd.ColumnSQL AS ColumnSQL " 
								+ "FROM AD_Ref_Table rt"
								+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID)"
								+ " INNER JOIN AD_Column ck ON (rt.AD_Key=ck.AD_Column_ID)"
								+ " INNER JOIN AD_Column cd ON (rt.AD_Display=cd.AD_Column_ID) "
								+ "WHERE rt.AD_Reference_ID=?" + " AND rt.IsActive='Y' AND t.IsActive='Y'";

						String DisplayColumn;
						String ColumnSQL;
						boolean isValueDisplayed;
						
						int AD_Ref_Value_ID = field.getAD_Reference_Value_ID();
						if (AD_Ref_Value_ID <= 0) {
							AD_Ref_Value_ID = column.getAD_Reference_Value_ID();
						}
						Object [] retValue = null;
						if (refValue.containsKey(AD_Ref_Value_ID)) {
							retValue = refValue.get(AD_Ref_Value_ID);
						} else {
							retValue = DB.getObjectValuesEx(null, sql, 6, AD_Ref_Value_ID);
							refValue.put(AD_Ref_Value_ID, retValue);								
						}
						
						
						DisplayColumn = retValue[2].toString(); // rs.getString(3);
						isValueDisplayed = retValue[3].toString().equals("Y"); //rs.getString(4).equals("Y");
						ColumnSQL = retValue[5] == null ? null : retValue[5].toString();//rs.getString(6);						

						// Virtual Column
						name.append("[");
						if (isValueDisplayed) {
							name.append("Value - ");
						}

						if (ColumnSQL != null && ColumnSQL.length() > 0) {
							name.append(ColumnSQL);
						} else {
							name.append(DisplayColumn);
						}

						name.append("]");

					}
				} else {
					name.append("[Value - Name]");
				}
			}
		}

		return name.toString();
	}


	@Override
	public String getFileExtension() {
		return "";
	}

	@Override
	public String getFileExtensionLabel() {
		return Msg.getMsg(Env.getCtx(), "FileXLSXTemp");
	}

	@Override
	public String getContentType() {
		return "application/xlsx";
	}

	private GridField[] getFields(GridTab gridTab) {
		GridTable tableModel = gridTab.getTableModel();
		GridField[] tmpFields = tableModel.getFields();
		GridField[] gridFields = null;

		ArrayList<GridField> gridFieldList = new ArrayList<GridField>();

		// Add Key Column
		GridField gfKeyID = null;
		for (GridField field : tmpFields) {
			if (field.isKey()) {
				gfKeyID = field;
			}
		}
		gridFieldList.add(gfKeyID);

		for (GridField field : tmpFields) {
			if ("AD_Client_ID".equals(field.getColumnName()) || "CreatedBy".equals(field.getColumnName())
					|| "Created".equals(field.getColumnName()) || "UpdatedBy".equals(field.getColumnName())
					|| "Updated".equals(field.getColumnName()))
				continue;
			MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());

			if (DisplayType.Button == column.getAD_Reference_ID())
				continue;
			if (field.isVirtualColumn() || field.isEncrypted() || field.isEncryptedColumn())
				continue;

			if (column.isParent() || column.getColumnName().equalsIgnoreCase(field.getGridTab().getLinkColumnName())) {
				gridFieldList.add(field);
				continue;
			}

			if (!field.getDisplayLogic().contains("@")) {
				if (!field.isDisplayed(true))
					continue;
			}

			if (field.isDisplayed())
				gridFieldList.add(field);
		}

		Collections.sort(gridFieldList, new Comparator<GridField>() {
			@Override
			public int compare(GridField o1, GridField o2) {
				int seq1 = o1.getSeqNo();
				int seq2 = o2.getSeqNo();

				MColumn col1 = MColumn.get(Env.getCtx(), o1.getAD_Column_ID());
				if (col1.isKey()) {
					return -1;
				} else if (col1.isParent()
						|| col1.getColumnName().equalsIgnoreCase(o1.getGridTab().getLinkColumnName())) {
					seq1 = 1;
				}

				MColumn col2 = MColumn.get(Env.getCtx(), o2.getAD_Column_ID());
				if (col2.isKey()) {
					return 1;
				} else if (col2.isParent()
						|| col2.getColumnName().equalsIgnoreCase(o2.getGridTab().getLinkColumnName())) {
					seq2 = 1;
				}

				return (seq1 - seq2);
			}
		});

		gridFields = new GridField[gridFieldList.size()];
		gridFieldList.toArray(gridFields);

		return gridFields;
	}

	@Override
	public String getSuggestedFileName(GridTab gridTab) {
		return "Template_" + gridTab.getTableName() + getFileExtension();
	}

	/**
	 * Function nay tao phan Header cho template -Phan header giup parse gia tri cac
	 * cot (Hide) -Phan header mo ta ten cac cot
	 * 
	 * @param sheet
	 * @param header
	 * @param headerMap
	 */
	private void createTableHeader(XSSFSheet sheet, List<String> header, List<String> headerMap) {
		int colnumMax = 0;

		XSSFRow rowMap = sheet.createRow(0);
		rowMap.setZeroHeight(true);
		XSSFRow row = sheet.createRow(1);
		// for all columns
		int colnum = 0;
		for (int col = 0; col < header.size(); col++) {
			if (colnum > colnumMax)
				colnumMax = colnum;
			//
			XSSFCell cellMap = rowMap.createCell(colnum);
			XSSFCell cell = row.createCell(colnum);
			// header row
			XSSFCellStyle style = getHeaderStyle(col);
			cell.setCellStyle(style);
			String str = fixString(header.get(col));
			cell.setCellValue(new XSSFRichTextString(str));
			// header map row
			String strMap = fixString(headerMap.get(col));
			cellMap.setCellValue(new XSSFRichTextString(strMap));

			colnum++;
		} // for all columns
	}

	private XSSFSheet createTableSheet(List<String> header, List<String> headerMap) {
		XSSFSheet sheet = m_mapWriter.createSheet("template_" + m_sheetCount);
		formatPage(sheet);
		createHeaderFooter(sheet);
		createTableHeader(sheet, header, headerMap);
		m_sheetCount++;
		//
		return sheet;
	}

	protected void createHeaderFooter(XSSFSheet sheet) {
		// Sheet Header
		XSSFHeaderFooter header = (XSSFHeaderFooter) sheet.getHeader();
		header.setRight(HSSFHeader.page() + " / " + HSSFHeader.numPages());

		// Sheet Footer
		XSSFHeaderFooter footer = (XSSFHeaderFooter) sheet.getFooter();
		footer.setLeft(EOne.EONE_R);
		footer.setCenter(Env.getHeader(Env.getCtx(), 0));
		Timestamp now = new Timestamp(System.currentTimeMillis());
		footer.setRight(DisplayType.getDateFormat(DisplayType.DateTime, languageDefault).format(now));
	}

	protected void formatPage(XSSFSheet sheet) {
		sheet.setFitToPage(true);
		// Print Setup
		XSSFPrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setNoColor(true);
		ps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		ps.setLandscape(false);
	}

	/**
	 * Function nay cho phep tao dong du lieu tren mot sheet
	 * 
	 * @param sheet
	 * @param row
	 * @param header
	 * @param processer
	 */
	private void createTableDetail(XSSFSheet sheet, Map<String, Object> row, List<String> header,
			List<CellProcessor> processer) {
		int rownum = sheet.getLastRowNum();
		XSSFRow xlsRow = sheet.createRow(rownum + 1);
		// for all columns
		for (int col = 0; col < header.size(); col++) {
			XSSFCell cell = xlsRow.createCell(col);
			int displayType = processer.get(col).getDisplayType();
			String formatPattern = processer.get(col).getFormatPattern();
			XSSFCellStyle style = getDetailStyle(col, displayType);
			Object obj = row.get(header.get(col));
			cell.setCellStyle(style);

			if (obj != null) {
				if (DisplayType.isDate(displayType)) {
					if (formatPattern != null) {
						style.setDataFormat(m_dataFormat.getFormat(formatPattern));
					} else {
						style.setDataFormat(
								m_dataFormat.getFormat(DisplayType.getDateFormat(languageDefault).toPattern()));
					}
					Timestamp value = (Timestamp) obj;
					cell.setCellValue(value);
				} else if (DisplayType.ID == displayType) {
					if (obj instanceof Integer) {
						int value = ((Integer) obj).intValue();
						cell.setCellValue(value);
					}
				} else if (DisplayType.isNumeric(displayType)) {
					if (formatPattern == null) {
						DecimalFormat df = DisplayType.getNumberFormat(displayType, languageDefault);
						formatPattern = getFormatString(df, true);
					}
					style.setDataFormat(m_dataFormat.getFormat(formatPattern));
					if (obj instanceof Number) {
						double value = ((Number) obj).doubleValue();
						cell.setCellValue(value);
					} else if (obj instanceof Integer) {
						int value = ((Integer) obj).intValue();
						cell.setCellValue(value);
					}
				} else if (DisplayType.YesNo == displayType) {
					boolean value = false;
					if (obj instanceof Boolean)
						value = (Boolean) obj;
					else
						value = "Y".equals(obj);
					cell.setCellValue(value == true ? "Y" : "N");
				} else {
					String value = fixString(obj.toString()); // formatted
					cell.setCellValue(new XSSFRichTextString(value));
				}
			}
		}

	}

	private XSSFCellStyle getHeaderStyle(int col) {
		String key = "header-" + col;
		XSSFCellStyle cs_header = m_styles.get(key);
		if (cs_header == null) {
			XSSFFont font_header = getFont(true);
			cs_header = m_mapWriter.createCellStyle();
			cs_header.setFont(font_header);
			cs_header.setBorderLeft(BorderStyle.MEDIUM);
			cs_header.setBorderTop(BorderStyle.MEDIUM);
			cs_header.setBorderRight(BorderStyle.MEDIUM);
			cs_header.setBorderBottom(BorderStyle.MEDIUM);
			cs_header.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			cs_header.setWrapText(true);
			m_styles.put(key, cs_header);
		}
		setFragmentStyle(col, cs_header);
		return cs_header;
	}

	/**
	 * Function nay tra ve style cac cell dong du lieu
	 * 
	 * @param col
	 * @param displayType
	 * @return
	 */
	private XSSFCellStyle getDetailStyle(int col, int displayType) {
		String key = "cell-" + col + "-" + displayType;
		XSSFCellStyle cs = m_styles.get(key);
		setFragmentStyle(col, cs);
		return cs;
	}

	/**
	 * Function nay tao style cho cell
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private void createStyle(int col, int displayType) {
		String key = "cell-" + col + "-" + displayType;
		XSSFCellStyle cs = m_styles.get(key);
		if (cs == null) {
			cs = m_mapWriter.createCellStyle();
			XSSFFont font = getFont(false);
			cs.setFont(font);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);

			m_styles.put(key, cs);
		}
	}

	/**
	 * Function nay thiet lap color, fragment tren sheet
	 * 
	 * @date 15/05/2014
	 * @param col
	 * @param cs
	 */
	public void setFragmentStyle(int col, XSSFCellStyle cs) {
		// Fix 5 color cho cac tab child
		if (m_TabFragmentIndexes.size() == 0) {
			// nothing
		} else if (m_TabFragmentIndexes.size() == 1) {
			if (col >= m_TabFragmentIndexes.get(0)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			}
		} else if (m_TabFragmentIndexes.size() == 2) {
			if (col >= m_TabFragmentIndexes.get(1)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(0)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			}
		} else if (m_TabFragmentIndexes.size() == 3) {
			if (col >= m_TabFragmentIndexes.get(2)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(1)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(0)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			}
		} else if (m_TabFragmentIndexes.size() == 4) {
			if (col >= m_TabFragmentIndexes.get(3)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(2)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(1)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(0)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.BROWN.getIndex());
			}
		} else if (m_TabFragmentIndexes.size() >= 5) {
			if (col >= m_TabFragmentIndexes.get(4)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(3)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(2)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(1)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.BROWN.getIndex());
			} else if (col >= m_TabFragmentIndexes.get(0)) {
				cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cs.setFillForegroundColor(IndexedColors.BLUE.getIndex());
			}
		}
	}

	/**
	 * Get Excel number format string by given {@link NumberFormat}
	 * 
	 * @param df                         number format
	 * @param isHighlightNegativeNumbers highlight negative numbers using RED color
	 * @return number excel format string
	 */
	private String getFormatString(NumberFormat df, boolean isHighlightNegativeNumbers) {
		StringBuffer format = new StringBuffer();
		int integerDigitsMin = df.getMinimumIntegerDigits();
		int integerDigitsMax = df.getMaximumIntegerDigits();
		for (int i = 0; i < integerDigitsMax; i++) {
			if (i < integerDigitsMin)
				format.insert(0, "0");
			else
				format.insert(0, "#");
			if (i == 2) {
				format.insert(0, ",");
			}
		}
		int fractionDigitsMin = df.getMinimumFractionDigits();
		int fractionDigitsMax = df.getMaximumFractionDigits();
		for (int i = 0; i < fractionDigitsMax; i++) {
			if (i == 0)
				format.append(".");
			if (i < fractionDigitsMin)
				format.append("0");
			else
				format.append("#");
		}
		if (isHighlightNegativeNumbers) {
			String f = format.toString();
			format = new StringBuffer(f).append(";[RED]-").append(f);
		}
		//
		if (log.isLoggable(Level.FINEST))
			log.finest("NumberFormat: " + format);
		return format.toString();

	}

	/**
	 * @date 15/05/2014
	 * @param field
	 * @return
	 */
	private CellProcessor getProccesorFromColumn(GridField field) {
		if (DisplayType.isDate(field.getDisplayType())) {
			String format = field.getFormatPattern();
			if (format == null) {
				MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
				format = column.getFormatPattern();
				if (format == null || format.length() == 0) {
					format = languageDefault.getDateFormat().toPattern();
				}
			}
			return (new CellProcessor(field.getDisplayType(), format));
		} else if (DisplayType.Integer == field.getDisplayType() || DisplayType.ID == field.getDisplayType()) {
			return (new CellProcessor(field.getDisplayType(), ""));
		} else if (DisplayType.isNumeric(field.getDisplayType())) {
			String format = field.getFormatPattern();
			if (format == null) {
				MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
				format = column.getFormatPattern();
			}
			return (new CellProcessor(field.getDisplayType(), format));
		} else {
			String format = field.getFormatPattern();
			if (format == null) {
				MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
				format = column.getFormatPattern();
			}
			return (new CellProcessor(field.getDisplayType(), format));
		}

	}

	
	private XSSFFont getFont(boolean isHeader) {
		XSSFFont font = null;
		if (isHeader) {
			if (m_fontHeader == null) {
				m_fontHeader = m_mapWriter.createFont();
				m_fontHeader.setBold(true);
			}
			font = m_fontHeader;
		} else {
			if (m_fontDefault == null) {
				m_fontDefault = m_mapWriter.createFont();
			}
			font = m_fontDefault;
		}
		return font;
	}

	/**
	 * @param str
	 * @return
	 */
	private String fixString(String str) {
		// ms excel doesn't support UTF8 charset
		return Util.stripDiacritics(str);
	}

	private class CellProcessor {
		private int m_displayType = -1;
		private String m_formatPattern = "";

		public CellProcessor(int type, String format) {
			m_displayType = type;
			m_formatPattern = format;
		}

		public int getDisplayType() {
			return m_displayType;
		}

		public String getFormatPattern() {
			return m_formatPattern;
		}
	}

	/**
	 * List Item
	 * 
	 * @author Teo Sarca
	 * @date 15/05/2014
	 */
	private static class RowIndex extends NamePair {
		/**
		 *
		 */
		private static final long serialVersionUID = -5645910649588308798L;
		private int m_key;
		private String m_whereClause = null;
		private RowIndex m_parent = null;
		private int maxRow = 0;

		/** Initial selection flag */

		public RowIndex(int key, String name) {
			super(name);
			this.m_key = key;
		}

		public void setParent(RowIndex parent) {
			this.m_parent = parent;
		}

		public int getKey() {
			return m_key;
		}

		public void setRowCount(int count) {
			this.maxRow = count;
		}

		public void nextIndex() {
			m_key = m_key + 1;
			if (m_key >= maxRow) {
				if (m_parent != null) {
					m_parent.nextIndex();
					m_key = 0;
				}

			}
		}

		public void setWhereClause(String whereClause) {
			this.m_whereClause = whereClause;
		}

		public String getWhereClause() {
			return m_whereClause;
		}

		@Override
		public String getID() {
			return m_key != -1 ? String.valueOf(m_key) : null;
		}

		@Override
		public int hashCode() {
			return m_key;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof RowIndex) {
				RowIndex li = (RowIndex) obj;
				return li.getKey() == m_key && li.getName() != null && li.getName().equals(getName());
			}
			return false;
		} // equals

		@Override
		public String toString() {
			String s = super.toString();
			if (s == null || s.trim().length() == 0)
				s = "<" + getKey() + ">";
			return s;
		}
	}

	@Override
	public boolean isExportableTab(GridTab gridTab) {
		// TODO Auto-generated method stub
		return false;
	}

}
