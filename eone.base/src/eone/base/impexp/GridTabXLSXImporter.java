package eone.base.impexp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.supercsv.exception.SuperCsvCellProcessorException;

import eone.base.IGridTabImporter;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.MColumn;
import eone.base.model.MTable;
import eone.base.model.PO;
import eone.exceptions.EONEException;
import eone.tools.FileUtil;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.IProcessUI;
import eone.util.Language;
import eone.util.Msg;
import eone.util.Trx;

public class GridTabXLSXImporter implements IGridTabImporter {
	private static final String ERROR_HEADER = "_ERROR_";
	private static final String LOG_HEADER = "_LOG_";
	private boolean m_isError = false;
	
	private static Language languageDefault = Env.getLanguage(Env.getCtx());
	private int BATCH_SIZE = 200;
	
	int counter = 0;
	List<String> lstHeader = new ArrayList<String>();

	private List<List<Object>> m_tmpRow = null;

	/** Logger */
	private static CLogger log = CLogger.getCLogger(GridTabXLSXImporter.class);
	
	//private boolean isInsert = false;
	//private int primaryKey = 0;

	public File fileImport(GridTab gridTab, List<GridTab> childs, InputStream filestream, Charset charset, String importMode) {
		long start = System.currentTimeMillis();
		Map<Integer, Integer> mapTabParent = new HashMap<Integer, Integer>();
		
		// doan nay tim ra quan he cha con cua cac tab
		GridTab preTab = gridTab;
		GridTab preParentTab = gridTab;
		for (GridTab detail : childs) {
			if (detail.getTabLevel() > preTab.getTabLevel()) {
				mapTabParent.put(detail.getAD_Tab_ID(), preTab.getAD_Tab_ID());
				preParentTab = preTab;
			} else {
				mapTabParent.put(detail.getAD_Tab_ID(), preParentTab.getAD_Tab_ID());
			}
			preTab = detail;
		}

		XSSFWorkbook mapReader = null;
		XSSFSheet sheetReader = null;
		
		List<String> lstKey = new ArrayList<String>();

		//
		File errFile = null;
		//FileOutputStream fileO = null;
		File logFile = null;
		PrintWriter errFileW = null;
		PrintWriter logFileW = null;
		
		if (!gridTab.isInsertRecord() )
			throw new EONEException("Insert record disabled for Tab");
		LinkedHashMap<Integer, List<List<Object>>> mapValues = new LinkedHashMap<Integer, List<List<Object>>>();
		
		try {
			String errFileName = FileUtil.getTempMailName("Import_" + gridTab.getTableName(), "_err.xlsx");
			errFile = new File(errFileName);
			//fileO = new FileOutputStream(errFile);
			errFileW = new PrintWriter(errFile, charset.name());
			mapReader = new XSSFWorkbook(filestream);
			sheetReader = mapReader.getSheetAt(0);
 			//mapReader.write(fileO);
			mapReader.close();
			if (sheetReader == null) {
				errFileW.close();
				throw new EONEException("Sheet cannot be empty!");
			}
			//int maxRow = sheetReader.getLastRowNum();
			//boolean isUseBatch = maxRow >= BATCH_SIZE ? true : false;
			XSSFRow rowHeader = sheetReader.getRow(0); // Line 1
			List<String> header = getValuesAt(rowHeader);
			//
			List<CellProcessor> readProcArray = new ArrayList<CellProcessor>();
			Map<GridTab, Integer> tabMapIndexes = new HashMap<GridTab, Integer>();
			int indxDetail = 0;
			// List<GridField> locationFields = null;
			boolean isThereKey = false;
			// boolean isThereDocAction = false;
			String tableName = gridTab.getTableName();
			HashMap<Integer, PO> mapPO = new HashMap<Integer, PO>();
			// Mapping header
			for (int idx = 0; idx < header.size(); idx++) {
				String headName = header.get(idx);
				if (headName == null) {
					errFileW.close();
					throw new EONEException("Header column cannot be empty, Col: " + (idx + 1));
				}

				if (headName.equals(ERROR_HEADER) || headName.equals(LOG_HEADER)) {
					header.set(idx, null);
					readProcArray.add(null);
					continue;
				}
				
				//TODO Đến tab con là break. Vậy còn tab con xử lý ???
				if (headName.indexOf(".") > 0) {
					if (idx == 0) {
						errFileW.close();
						throw new EONEException(Msg.getMsg(Env.getCtx(), "WrongHeader", new Object[] { headName }));
					} else
						break;

				} else if (!headName.contains(".")) {
					boolean isKeyColumn = headName.indexOf("/") > 0 || headName.contains(tableName + "_ID");
					boolean isForeing = headName.indexOf("[") > 0 && headName.indexOf("]") > 0;
					String columnName = getColumnName(isKeyColumn, isForeing, false, headName);

					GridField field = gridTab.getField(columnName);

					if (field == null) {
						errFileW.close();
						throw new EONEException(Msg.getMsg(Env.getCtx(), "FieldNotFound", new Object[] { columnName }));
					} else if (isKeyColumn && !isThereKey)
						isThereKey = true;

					readProcArray.add(getProccesorFromColumn(field));
					indxDetail++;
				}
			}

			mapPO.clear();
			
			if (!isThereKey)
				throw new EONEException(gridTab.getTableName() + ": " + Msg.getMsg(Env.getCtx(), "NoKeyFound"));

			tabMapIndexes.put(gridTab, indxDetail - 1);
			String childTableName = null;
			isThereKey = false;
			// locationFields = null;
			GridTab currentDetailTab = null;
			// Mapping details
			List<Integer> lstTabAdded = new ArrayList<Integer>();
			
			//TODO ĐỌc tiếp đoạn tab con 
			for (int idx = indxDetail; idx < header.size(); idx++) {
				String detailName = header.get(idx);
				if (detailName != null && detailName.indexOf(".") > 0) {
					childTableName = detailName.substring(0, detailName.indexOf("."));
					if (currentDetailTab == null
							|| (currentDetailTab != null && !childTableName.equals(currentDetailTab.getTableName()))) {

						if (currentDetailTab != null) {
							// check out key per Tab
							System.out.println("Vào đây ko");
							if (!isThereKey) {
								errFileW.close();
								throw new EONEException(currentDetailTab.getTableName() + ": " + Msg.getMsg(Env.getCtx(), "NoKeyFound"));
							} else {
								tabMapIndexes.put(currentDetailTab, idx - 1);
								isThereKey = false;
							}
						}

						for (GridTab detail : childs) {
							if (detail.getTableName().equals(childTableName)) {
								if (!lstTabAdded.contains(detail.getAD_Tab_ID())) {
									currentDetailTab = detail;
									lstTabAdded.add(detail.getAD_Tab_ID());
									break;
								}
							}
						}
					}

					if (currentDetailTab == null) {
						errFileW.close();
						throw new EONEException( Msg.getMsg(Env.getCtx(), "NoChildTab", new Object[] { childTableName }));
					}

					String columnName = detailName;
					boolean isKeyColumn = columnName.indexOf("/") > 0 || columnName.contains(childTableName + "_ID");
					boolean isForeing = columnName.indexOf("[") > 0 && columnName.indexOf("]") > 0;
					columnName = getColumnName(isKeyColumn, isForeing, true, columnName);
					if (columnName.contains("."))
						columnName = columnName.substring(columnName.indexOf(".") + 1);
					GridField field = currentDetailTab.getField(columnName);

					if (field == null) {
						errFileW.close();
						throw new EONEException(Msg.getMsg(Env.getCtx(), "FieldNotFound", new Object[] { detailName }));
					} else if (isKeyColumn && !isThereKey)
						isThereKey = true;

					readProcArray.add(getProccesorFromColumn(field));
			   
				} else {
					errFileW.close();
					throw new EONEException(Msg.getMsg(Env.getCtx(), "WrongDetailName", new Object[] { " col(" + idx + ") ", detailName }));
				}
			}

			if (currentDetailTab != null) {
				if (!isThereKey)
					throw new EONEException(currentDetailTab.getTableName() + ": " + Msg.getMsg(Env.getCtx(), "NoKeyFound"));

				tabMapIndexes.put(currentDetailTab, header.size() - 1);
			}

			TreeMap<GridTab, Integer> sortedtTabMapIndexes = null;
			if (childs.size() > 0 && !tabMapIndexes.isEmpty()) {
				ValueComparator bvc = new ValueComparator(tabMapIndexes);
				sortedtTabMapIndexes = new TreeMap<GridTab, Integer>(bvc);
				sortedtTabMapIndexes.putAll(tabMapIndexes);
			} else {
				Map<GridTab, Integer> localMapIndexes = new HashMap<GridTab, Integer>();
				localMapIndexes.put(gridTab, header.size() - 1);
				ValueComparator bvc = new ValueComparator(localMapIndexes);
				sortedtTabMapIndexes = new TreeMap<GridTab, Integer>(bvc);
				sortedtTabMapIndexes.putAll(localMapIndexes);
			}

			m_isError = false;
			// write the header
			String rawHeader = "RESULT IMPORT";
			errFileW.write(rawHeader + "\n");
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<String> rawData = new ArrayList<String>();
			m_tmpRow = new ArrayList<List<Object>>();
			// pre-process to check for errors

			// Line 3: Fix giá trị này do phụ thuộc vào file export
			int rownum = 2;
			Trx trx = null;
			String trxName = "Import_" + gridTab.getTableName() + "_" + UUID.randomUUID();
			trx = Trx.get(trxName, true);
			
			HashMap<Integer, MTable> mapTable = new HashMap<Integer, MTable>();
			while (true) {
				Map<String, Object> map = null;
				boolean isLineError = false;
				StringBuilder errMsg = new StringBuilder();
				try {
					map = readTableDetail(sheetReader, rownum, header, readProcArray);
				} catch (SuperCsvCellProcessorException e) {
					int idx = e.getCsvContext().getColumnNumber() - 1;
					errMsg.append(header.get(idx)).append(": ").append(e.getMessage());
					isLineError = true;
				}
				int rowReal = rownum++ + 1;
				String rawLine = "Row "+  rowReal  +" ";
				if (!isLineError) {
					if (map == null)
						break;

					// Re-order information coming from map
					List<Object> tmpRow = getOrderedRowFromMap(header, map);
					m_tmpRow.add(tmpRow);
					// read master and detail
					int initIndx = 0;
					for (Map.Entry<GridTab, Integer> tabIndex : sortedtTabMapIndexes.entrySet()) {
						GridTab tmpGrid = tabIndex.getKey();
						int endindx = tabIndex.getValue();
						StringBuilder lineError = preprocessRow(tmpGrid, header, tmpRow, initIndx, endindx);
						if (lineError != null && lineError.length() > 0) {
							isLineError = true;
							if (errMsg.length() > 0)
								errMsg.append(" / ");
							errMsg.append(lineError);
						}
						initIndx = endindx + 1;
					}
				}
				if (isLineError && !m_isError)
					m_isError = true;
				if (!m_isError) {
					data.add(map);
					rawData.add(rawLine);
				}
				// write
				rawLine = rawLine  + errMsg.toString() + "\n";
				errFileW.write(rawLine);
			}
			
			
			
			if (!m_isError) {
				String logFileName = FileUtil.getTempMailName("Import_" + gridTab.getTableName(), "_log.xlsx");
				logFile = new File(logFileName);
				logFileW = new PrintWriter(logFile, charset.name());
				// write the header
				logFileW.write(rawHeader + "\n");

				boolean error = false;
				
				
				//List<String> rowsTmpResult = new ArrayList<String>();
				List<List<Object>> values = null;
				
				System.out.println("FOR ROWS STARTS...");
				PO parent = null;
				for (int idx = 0; idx < data.size(); idx++) {
					lstHeader.clear();
					String rawLine = rawData.get(idx);
					String logMsg = null;
					StringBuilder rowResult = new StringBuilder();
					GridTab currentGridTab = null;

					boolean isDetail = false;
					int currentColumn = 0;
					error = false;

					if (rawLine.charAt(0) == ',') {
						isDetail = true;
						// check out if master row comes empty
						Map<String, Object> rowMap = data.get(idx);
						for (int i = 0; i < indxDetail - 1; i++) {
							if (rowMap.get(header.get(i)) != null) {
								isDetail = false;
								break;
							}
						}
					}

					
					try {

						
						for (Map.Entry<GridTab, Integer> tabIndex : sortedtTabMapIndexes.entrySet()) {
							
							//primaryKey = 0;
							currentGridTab = tabIndex.getKey();
							if (isDetail && gridTab.equals(currentGridTab)) {
								currentColumn = indxDetail;
								continue;
							}
							MTable curTable = null;
							if (mapTable.containsKey(currentGridTab.getAD_Table_ID())) {
								curTable = mapTable.get(currentGridTab.getAD_Table_ID());
							} else {
								curTable = new MTable(Env.getCtx(), currentGridTab.getAD_Table_ID(), null);
							}
							PO po2 = curTable.getPO(0, trxName);
							
							
							if (!gridTab.equals(currentGridTab)) {
								isDetail = true;
							}

							int j = tabIndex.getValue();
							
							boolean isParent = proccessRowPO(currentGridTab, po2, header, data.get(idx), parent, currentColumn, j, trx);
							
							currentColumn = j + 1;
							
							if (!error) {
								//Có header
								if (isParent) {
									if (mapValues.containsKey(currentGridTab.getAD_Table_ID())) {
										values = mapValues.get(currentGridTab.getAD_Table_ID());
									} else {
										values = new ArrayList<List<Object>>();
									}
									int sequence = -1;
									/*TODO Không cho Update
									if (isInsert) {
										sequence = DB.getNextID(currentGridTab.getTableName(), trxName);
									} else {
										sequence = primaryKey;
									}
									*/
									sequence = DB.getNextID(currentGridTab.getTableName(), trxName);
									
									po2.set_ValueNoCheck(currentGridTab.getKeyColumnName(), sequence);	
									
									List<String> colNames = null;
									/*
									if(isInsert)
										colNames = PO.getSqlInsert_Para(currentGridTab.getAD_Table_ID(), trxName);
									else
										colNames = PO.getSqlUpdate_Para(currentGridTab.getAD_Table_ID());
									*/
									colNames = PO.getSqlInsert_Para(currentGridTab.getAD_Table_ID(), trxName);
									List<Object> lstParam = PO.getBatchValueList(po2, colNames, currentGridTab.getAD_Table_ID(), trxName, sequence);
									values.add(lstParam);
									if (!mapValues.containsKey(currentGridTab.getAD_Table_ID())) {
										mapValues.put(currentGridTab.getAD_Table_ID(), values);
									}
									if (values.size() >= BATCH_SIZE) {
										for (Entry<Integer, List<List<Object>>> entry : mapValues.entrySet()) {
											String sql = "";
											/*
											if (isInsert)
												sql = PO.getSqlInsert(entry.getKey(), trxName);
											else
												sql = PO.getSqlUpdate(entry.getKey());
												*/
											sql = PO.getSqlInsert(entry.getKey(), trxName);
											if (entry.getValue().size() > 0) {
												String err = DB.excuteBatch(sql, entry.getValue(), trxName);
												if (err != null) {
													try {
														DB.rollback(false, trxName);
													} catch (Exception e) {
													}
												}
											}
										}
										values.clear();
										mapValues.clear();
									}
									
									
									//End batch
									
									if (logMsg != null) {
										rowResult.append(logMsg);
									} else {
										rowResult.append("successed!");
									}
								}
								//End có header
								//Gán tab cha 
								if (preParentTab.equals(currentGridTab) && isParent)
									parent = po2;
							} 
						}
					} catch (Exception e) {
						rowResult.append(Msg.getMsg(Env.getCtx(), "Error") + " " + e);

						error = true;
						
					} 
					// write
					rawLine = rawLine + rowResult.toString() + "\n";
					//rowsTmpResult.add(rawLine);
					logFileW.write(rawLine);
				}
			}
			
			

			mapTable = null;

			for (Entry<Integer, List<List<Object>>> entry : mapValues.entrySet()) {
				String sql = "";
				/*
				if (isInsert)
					sql = PO.getSqlInsert(entry.getKey(), trxName);
				else
					sql = PO.getSqlUpdate(entry.getKey());
				*/
				sql = PO.getSqlInsert(entry.getKey(), trxName);
				if (entry.getValue().size() > 0) {
					String err = DB.excuteBatch(sql, entry.getValue(), trxName);
					if (err != null) {
						try {
							DB.rollback(false, trxName);
						} catch (Exception e) {
							
						}
					}
				}
			}
			try {
				DB.commit(true, trxName);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mapValues.clear();
			tabMapIndexes = null;

		} catch (IOException e) {
			throw new EONEException(e);
		} finally {
			try {
				if (errFileW != null) {
					errFileW.flush();
					errFileW.close();
				}
				if (logFileW != null) {
					logFileW.flush();
					logFileW.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		lstKey.clear();
		mapValues = null;
		mapTabParent = null;

		gridTab.dataRefreshAll();
		System.out.print("END FUNCTIONS IN " + (System.currentTimeMillis() - start) + " ms");
		if (logFile != null)
			return logFile;
		else
			return errFile;
	}

	
	private String getColumnName(boolean isKey, boolean isForeing, boolean isDetail, String headName) {

		if (isKey) {
			if (headName.lastIndexOf(".") > 0) {
				headName = headName.substring(headName.lastIndexOf(".") + 1, headName.length());
			}
		}

		if (isForeing)
			headName = headName.substring(0, headName.indexOf("["));

		if (isDetail) {
			if (headName.lastIndexOf(".") > 0)
				headName = headName.substring(headName.lastIndexOf(".") + 1, headName.length());
		}
		return headName;
	}

	private List<Object> getOrderedRowFromMap(List<String> header, Map<String, Object> map) {
		List<Object> tmpRow = new ArrayList<Object>();
		for (int i = 0; i < header.size(); i++)
			tmpRow.add(null);

		for (Map.Entry<String, Object> record : map.entrySet()) {
			String Column = record.getKey();
			Object value = record.getValue();
			int toIndx = -1;
			if (Column.startsWith("2"))
				Column = Column.substring(1);
			toIndx = header.indexOf(Column);
			tmpRow.set(toIndx, value);
		}
		return tmpRow;
	}

	/**
	 * Validation data
	 * 
	 * @param gridTab
	 * @param header
	 * @param tmpRow
	 * @param startindx
	 * @param endindx
	 * @return
	 */
	private StringBuilder preprocessRow(GridTab gridTab, List<String> header, List<Object> tmpRow, int startindx, int endindx) {

		boolean isEmptyRow = true;
		StringBuilder mandatoryColumns = new StringBuilder();
		for (int i = startindx; i < endindx + 1; i++) {
			String columnName = header.get(i);
			Object value = tmpRow.get(i);
			if (value != null)
				isEmptyRow = false;

			if (log.isLoggable(Level.FINE))
				log.fine("Setting " + columnName + " to " + value);

			boolean isKeyColumn = columnName.indexOf("/") > 0 || columnName.contains(gridTab.getTableName() + "_ID");
			boolean isForeing = columnName.indexOf("[") > 0 && columnName.indexOf("]") > 0;
			boolean isDetail = columnName.indexOf(".") > 0;
			columnName = getColumnName(isKeyColumn, isForeing, isDetail, columnName);

			GridField field = gridTab.getField(columnName);
			if (field == null)
				return new StringBuilder(Msg.getMsg(Env.getCtx(), "NotAWindowField", new Object[] { header.get(i) }));
		}

		if (mandatoryColumns.length() > 1 && !isEmptyRow)
			return new StringBuilder(Msg.getMsg(Env.getCtx(), "FillMandatory") + " " + mandatoryColumns);
		else
			return null;
	}

	private  boolean proccessRowPO(GridTab gridTab, PO tabCurrent, List<String> header, Map<String, Object> map, PO tabParent,
			int startindx, int endindx, Trx trx) {
		boolean check = true;
		//Xử lý dữ liệu default mà không hiển thị lên form
		for(int i = 0; i < gridTab.getFields().length; i++) {
			GridField field = gridTab.getField(i);
			if (field.isDisplayed())
				continue;
			
			if (field.getDefault() != null && field.getDefault().toString().length() > 0 ) {
				tabCurrent.set_ValueNoCheck(field.getColumnName(), field.getDefault());
				//gridTab.setValue(field, field.getDefault());
				
			}
		}

		for (int i = startindx; i < endindx + 1; i++) {
			String columnName = header.get(i);
			
			Object value = map.get(header.get(i));
			
			
			if (lstHeader.contains(columnName)) {
				value = map.get("2" + header.get(i));
			} else
				lstHeader.add(columnName);
			
			boolean isDetail = false;
			

			boolean isKeyColumn = columnName.indexOf("/") > 0 || columnName.contains(gridTab.getTableName() + "_ID");
			boolean isForeing = columnName.indexOf("[") > 0 && columnName.indexOf("]") > 0;
			isDetail = columnName.indexOf(".") > 0;
			columnName = getColumnName(isKeyColumn, isForeing, isDetail, columnName);
			
			Object setValue = null;
			
			GridField field = gridTab.getField(columnName);
			MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());
			
			if ("DocumentNo".equals(column.getColumnName()) || "Value".equals(column.getColumnName())) {
				if (value == null || value.toString().isEmpty()) {
					check = false;
				}
			}
			
			/*TODO không nên cho update khi import
			if (field.isKey()) {
				if (value == null)
					isInsert = true;
				else
					primaryKey = Integer.valueOf(value.toString());				
			}
			*/
			
			
			if (value == null) {
				if (tabParent == null)
					continue;
				else if (isForeing && !tabCurrent.equals(tabParent) && field.isParentColumn())
					value = tabParent.get_ID();
			}

			if (columnName.endsWith("_ID") && "0".equals(value))
				continue;

			if (isKeyColumn)
				continue;

			if (value == null) {
				//logMsg = gridTab.setValue(field, null);
				tabCurrent.set_ValueNoCheck(field.getColumnName(), null);
				continue;
			}
			
			String[] arrValue = value.toString().split(" - ");
			Object foreignkey = arrValue[0];
			if (foreignkey == null) {
				foreignkey = "";
			}
			String idS = null;
			
			if (field.isParentValue() || field.isKey()) {

				
				String foreignTable = column.getReferenceTableName();
				

				int id = -1;

				if ("AD_Ref_List".equals(foreignTable))
					idS = foreignkey.toString();
				else
				{
					if ("".equals(foreignkey) )
						foreignkey = "0";
					id = Integer.valueOf(foreignkey.toString());
				}

				if (id != 0) {
					//logMsg = gridTab.setValue(field, id);
					tabCurrent.set_ValueNoCheck(field.getColumnName(), id);
				}
				if (idS != null) {
					//logMsg = gridTab.setValue(field, idS);
					tabCurrent.set_ValueNoCheck(field.getColumnName(), idS);
				}
				
			} else {

				
				if (isForeing) {
					String foreignTable = column.getReferenceTableName();

					if ("AD_Ref_List".equals(foreignTable)) {
						idS = foreignkey.toString();
						
						setValue = idS;
					} else {
						
						int id = Integer.valueOf(foreignkey.toString());
						
						setValue = id;
						
					}
				} else {
					if (value != null) {
						if (value instanceof java.util.Date)
							value = new Timestamp(((java.util.Date) value).getTime());
						setValue = value;
					}
				}

				if (setValue != null) {
					
					tabCurrent.set_ValueNoCheck(field.getColumnName(),  setValue);
				}

			}
		}
		return check;
	}

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
			return (new CellProcessor(field.getDisplayType()));
		} else if (DisplayType.Integer == field.getDisplayType() || DisplayType.ID == field.getDisplayType()) {
			return (new CellProcessor(field.getDisplayType()));
		} else if (DisplayType.isNumeric(field.getDisplayType())) {
			String format = field.getFormatPattern();
			if (format == null) {
				MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());

				format = column.getFormatPattern();
			}
			return (new CellProcessor(field.getDisplayType()));
		} else {
			String format = field.getFormatPattern();
			if (format == null) {
				MColumn column = MColumn.get(Env.getCtx(), field.getAD_Column_ID());

				format = column.getFormatPattern();
			}
			return (new CellProcessor(field.getDisplayType()));
		}

	}
	
	@Override
	public String getFileExtension() {
		return "xls";
	}

	@Override
	public String getFileExtensionLabel() {
		return Msg.getMsg(Env.getCtx(), "File Excel xlsx");
	}

	@Override
	public String getContentType() {
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}

	@Override
	public String getSuggestedFileName(GridTab gridTab) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dt = sdf.format(cal.getTime());
		String localFile = "Import_" + gridTab.getTableName() + "_" + dt + (m_isError ? "_err" : "_log") + "."+ getFileExtension();
		return localFile;
	}

	static class ValueComparator implements Comparator<GridTab> {
		Map<GridTab, Integer> base;

		public ValueComparator(Map<GridTab, Integer> base) {
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with equals.
		public int compare(GridTab a, GridTab b) {
			if (base.get(a) >= base.get(b))
				return 1;
			else
				return -1;
		}
	}

	private List<String> getValuesAt(XSSFRow row) {
		List<String> values = new ArrayList<String>();
		Iterator<Cell> cellIterator = row.cellIterator();

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			values.add(cell.getStringCellValue());
		}

		return values;
	}

	@SuppressWarnings("deprecation")
	private Map<String, Object> readTableDetail(XSSFSheet sheet, int rownum, List<String> header, List<CellProcessor> processors) {
		if (rownum < sheet.getFirstRowNum() || rownum > sheet.getLastRowNum())
			return null;
		Map<String, Object> mapRow = new HashMap<String, Object>();
		XSSFRow row = sheet.getRow(rownum);
		int col = 0;
		List<String> lstHeaderName = new ArrayList<String>();
		if (row != null) {
			for (int cn = 0; cn < row.getLastCellNum(); cn++) {
				Cell cell = row.getCell(cn, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String headerMap = header.get(col);
				if (lstHeaderName.contains(headerMap)) {
					headerMap = "2" + headerMap;
					lstHeaderName.add(headerMap);
				} else {
					lstHeaderName.add(headerMap);
				}
				int displayType = processors.get(col++).getDisplayType();
				if (cell.getCellType() == CellType.BLANK) {
					mapRow.put(headerMap, null);
				} else if (DisplayType.isDate(displayType)) {
					try {
						String tmp = cell.getStringCellValue();
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date parsedTimeStamp = dateFormat.parse(tmp);
						Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
						mapRow.put(headerMap, timestamp);
					} catch (Exception e) {
						mapRow.put(headerMap, cell.getDateCellValue());
					}
				} else if (DisplayType.Integer == displayType || DisplayType.ID == displayType) {
					try {
						String value = cell.getStringCellValue();
						if (value != null && value.toString().length() > 0) {
							try {
								mapRow.put(headerMap, new Integer(value));
							} catch (Exception e) {
								log.saveError("Error", "Error while importing field " + headerMap + " with value: " + value);
							}
						} else {
							mapRow.put(headerMap, null);
						}
					} catch (Exception e) {
						double value = cell.getNumericCellValue();
						mapRow.put(headerMap, Double.valueOf(value).intValue());
					}
				} else if (DisplayType.isNumeric(displayType)) {
					double value = cell.getNumericCellValue();
					mapRow.put(headerMap, BigDecimal.valueOf(value));
				} else if (DisplayType.YesNo == displayType) {
					try {
						String tmp = cell.getStringCellValue();
						if (tmp != null && tmp.equals("Y"))
							mapRow.put(headerMap, true);
						else
							mapRow.put(headerMap, false);
					} catch (Exception e) {
						mapRow.put(headerMap, false);
					}
				} else {
					try {
						if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals(""))
							mapRow.put(headerMap, cell.getStringCellValue());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else
			return null;

		return mapRow;
	}

	
	private class CellProcessor {
		private int m_displayType = -1;

		public CellProcessor(int type) {
			m_displayType = type;
		}

		public int getDisplayType() {
			return m_displayType;
		}
	}

	@Override
	public File fileImport(GridTab gridTab, List<GridTab> childs, InputStream filestream, Charset charset,
			String importMode, IProcessUI processUI) {
		// TODO Auto-generated method stub
		return null;
	}
}
