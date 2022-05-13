package eone.webui.panel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.compiere.minigrid.IDColumn;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.South;

import eone.base.model.I_HM_PatientRegisterLine;
import eone.base.model.MPatientRegisterLine;
import eone.base.model.MProduct;
import eone.base.model.MProductGroup;
import eone.base.model.MUOM;
import eone.base.model.PO;
import eone.base.model.X_HM_PatientRegisterLine;
import eone.util.DB;
import eone.util.Env;
import eone.util.KeyNamePair;
import eone.util.MSort;
import eone.util.Msg;
import eone.webui.LayoutUtils;
import eone.webui.apps.WInfo_Column;
import eone.webui.component.Button;
import eone.webui.component.Combobox;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Label;
import eone.webui.component.ListHead;
import eone.webui.component.ListHeader;
import eone.webui.component.ListboxFactory;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.component.SimpleListModel;
import eone.webui.component.WListbox;
import eone.webui.event.ValueChangeEvent;
import eone.webui.event.ValueChangeListener;
import eone.webui.util.ZKUpdateUtil;

public class WCreateNewRegisterExamsPanel extends ADForm implements IFormController, EventListener<Event>, ValueChangeListener
{

	private static final long serialVersionUID = -3464399582503864350L;
	private static int 			BATCH_SIZE = Env.getBatchSize(Env.getCtx());;
	
	private boolean				p_initOK = false;
	/** Properties */
	protected Properties 		p_ctx = null;

	private int record_ID = 0;
	private int M_Product_ID;
	//Selection
	private WListbox 			selectionTable = ListboxFactory.newDataTable();
	
	
	private StatusBarPanel 		selectionStatusBar = new StatusBarPanel();
	private Label 				lbproduct;
	private Label				lblTotal;
	private int 				iTotal = 0;
	
	private Combobox 			product = new Combobox("---------Lựa chọn----------");
	
	
	private Button				btnAdd;	
	
	HashMap<Integer, List<Object>> hastData = new HashMap<Integer, List<Object>>();
	
	private ConfirmPanel 		confirmPanel;
	
	private StringBuilder		m_query = new StringBuilder();
	private StringBuilder		m_order = new StringBuilder();
	
	private WInfo_Column[] 		m_columns = null;
	

	public WCreateNewRegisterExamsPanel() {
		
	}
	
	@Override
	public Mode getWindowMode() {
		return Mode.HIGHLIGHTED;
	}
	
	@Override
	protected void initForm() {
		
		try {
			record_ID = this.getProcessInfo().getRecord_ID();
			initComponent();
			jbInit ();
			prepareTable();
			p_initOK = dynInit();
		} catch (Exception e) {
			p_initOK = false;
		}
		
	}
	
	private void prepareTable()
	{
		selectionTable.getItems().clear();		
		
		String m_queryAlias = "SELECT ";
		
		StringBuilder m_queryFrom = new StringBuilder(" M_Product ")
					.append(" INNER JOIN M_ProductGroup ON M_Product.M_ProductGroup_ID = M_ProductGroup.M_ProductGroup_ID")
					.append(" INNER JOIN C_UOM ON M_Product.C_UOM_ID = C_UOM.C_UOM_ID");
		
		int colNumbers = 6;
		int index = 0;
		m_columns = new WInfo_Column[colNumbers];
		m_columns[index++] = new WInfo_Column("", MProduct.Table_Name + "." + MProduct.COLUMNNAME_M_Product_ID, IDColumn.class, 40);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "productCode"), MProduct.Table_Name + "." + MProduct.COLUMNNAME_Value, String.class, 80);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "productName"), MProduct.Table_Name + "." + MProduct.COLUMNNAME_Name, String.class, 300);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "UnitServices"), MUOM.Table_Name + "." + MUOM.COLUMNNAME_Name, String.class, 80);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "PriceSOServices"), MProduct.Table_Name + "." + MProduct.COLUMNNAME_PriceSO, Double.class, 100);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "CategoryType"), MProductGroup.Table_Name + "." + MProductGroup.COLUMNNAME_CategoryType, String.class);
		
		
		WListbox table = this.selectionTable;
		WInfo_Column[] layout = (WInfo_Column[])m_columns;
		ListHead listhead = new ListHead();
		table.appendChild(listhead);
		
		// remove existing column header
		listhead.getChildren().clear();
		for (int i = 0; i < layout.length; i++)
		{
			if (layout[i] != null)
			{
				if (i > 0)
					m_query.append(", ");
				m_query.append(layout[i].getColSQL());

				if (layout[i].isIDcol())
					m_query.append(",").append(layout[i].getIDcolSQL());
				
				// add Header
				ListHeader listheader = null; 					
				listheader = new ListHeader(layout[i].getColHeader());
				
				listheader.setWidth(layout[i].getWidth() + "px");
				
				listheader.setTooltiptext(layout[i].getColHeader());
				
				Comparator<Object> ascComparator =  getColumnComparator(true, i);
		        Comparator<Object> dscComparator =  getColumnComparator(false, i);
		        listheader.setSortAscending(ascComparator);
		        listheader.setSortDescending(dscComparator);

				listhead.appendChild(listheader);
				
				//  add to model
				table.addColumn(layout[i].getColHeader());
				
				if (layout[i].isColorColumn())
					table.setColorColumn(i);					
			}
		}
		table.autoSize();
		
		for (int i = 0; i < layout.length; i++)
		{
			if (layout[i] != null)
			{
				table.setColumnClass(i, layout[i].getColClass(), 
						layout[i].isReadOnly(), 
						layout[i].getColHeader());
			}
		}
		m_query.append(", M_Product.M_Product_ID ");
		m_query = new StringBuilder(m_queryAlias).append(m_query);
		m_query = m_query.append(" FROM ").append(m_queryFrom);
		
		this.selectionTable.setMultiple(true);
		this.selectionTable.setCheckmark(true);
		this.selectionTable.setMultiSelection(true);
		
	}
	
	private boolean dynInit()
	{
		setTitle(Msg.translate(Env.getCtx(), "Select health care services"));
		return true;
	}
	
	/**
	 *	Init OK to be able to make changes?
	 *  @return on if initialized
	 */
	public boolean isInitOK()
	{
		return p_initOK;
	}	//	isInitOK
	
	
	private void initComponent()
	{
		
		
		btnAdd = new Button(Msg.translate(Env.getCtx(), "Search"));
		btnAdd.addActionListener(this);
		confirmPanel = new ConfirmPanel(true, false, false, false, false, false);
		confirmPanel.addActionListener(Events.ON_CLICK, this);

		
		
		return;
	}
	
	private List<ObjCombobox> getProductCategory() {
		List<ObjCombobox> arrs = new ArrayList<WCreateNewRegisterExamsPanel.ObjCombobox>();
		arrs.add(new ObjCombobox(0, "---------Lựa chọn----------"));
		String sql = "Select M_Product_ID, Name From M_Product Where ProductType = 'MED_EXA'";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				arrs.add(new ObjCombobox(rs.getInt("M_Product_ID"), rs.getString("Name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
			rs = null;
			ps = null;
		}
		return arrs;
	}
	
	private void jbInit () throws Exception
	{
		this.setSizable(true);
		this.setClosable(true);
		this.setMaximizable(true);
		ZKUpdateUtil.setWidth(this, "50%");
		ZKUpdateUtil.setHeight(this, "80%");
		
		Borderlayout mainPanel = new Borderlayout();
		this.appendChild (mainPanel);
		LayoutUtils.addSclass("tab-editor-form-content", mainPanel);
		this.setBorder("normal");
		ZKUpdateUtil.setWidth(mainPanel, "100%");
		ZKUpdateUtil.setHeight(mainPanel, "100%");
        mainPanel.setStyle("border-width: 5px; position: relative");

		confirmPanel.addActionListener(Events.ON_CLICK, this);
		
		//west North
		North north = new North();
		mainPanel.appendChild(north);
		Grid grid = GridFactory.newGridLayout();
		Rows rows = new Rows();
		Row row = new Row();
		lbproduct = new Label(Msg.translate(Env.getCtx(), "ServiceExamination"));
		row.appendChild(lbproduct);
		
		SimpleListModel model = new SimpleListModel(getProductCategory());
		product.setModel(model);
		product.setItemRenderer(new ComboitemRenderer<ObjCombobox>() {

			@Override
			public void render(Comboitem item, ObjCombobox data, int index) throws Exception {
				item.setValue(data.getValue());
				item.setLabel(data.getName());
			}
			
		});
		
		row.appendChild(product);
		
		row.appendChild(btnAdd);
		rows.appendChild(row);
		
		row = new Row();
		rows.appendChild(row);
		lblTotal = new Label("TỔNG :" + iTotal);
		row.appendChild(lblTotal);
		
		grid.appendChild(rows);
		north.appendChild(grid);
		
		//west center
		Center wNCenter = new Center();
		wNCenter.appendChild(selectionTable);
		selectionTable.addActionListener(this);
		mainPanel.appendChild(wNCenter);
		
		// south
		South westSouth = new South();
		
		westSouth.appendChild(confirmPanel);
		
		mainPanel.appendChild(westSouth);
		//~south
		
		
	}	//	jbInit

	/**
	 */
	public void dispose()
	{
		this.detach();
	}	//	dispose
				
	
	private boolean saveChange()
	{
		int [] listID = selectionTable.getSelectedIndices();
		MPatientRegisterLine line = null;
		
		List<List<Object>> listParams = new ArrayList<List<Object>>();
		String sqlInsert = PO.getSqlInsert(I_HM_PatientRegisterLine.Table_ID, null);
		
		int index = 0;
		if (listID != null) {
			for( int i = 0; i < listID.length; i++) {
				index = listID[i];
				Listitem item = selectionTable.getItemAtIndex(index);
				Listcell Obj_Product_ID = (Listcell)item.getChildren().get(0);
				Listcell Obj_Product_Category_ID = (Listcell)item.getChildren().get(6);
				Listcell Obj_PriceSO = (Listcell)item.getChildren().get(5);
				
				int M_PatientRegisterLine_ID = DB.getNextID(Env.getCtx(), I_HM_PatientRegisterLine.Table_Name, null);
				line = new MPatientRegisterLine(Env.getCtx(), 0, null);
				line.setHM_PatientRegister_ID(record_ID);
				line.setHM_PatientRegisterLine_ID(M_PatientRegisterLine_ID);
				line.setM_Product_Category_ID(Obj_Product_Category_ID.getValue());
				line.setM_Product_ID(Obj_Product_ID.getValue());
				if (!"".equals(Obj_PriceSO.getValue()))
					line.setPrice(new BigDecimal(Obj_PriceSO.getValue().toString()));
				line.setQty(Env.ONE);
				line.setAmount(line.getQty().multiply(line.getPrice()));
				line.setIsSchedule(false);
				line.setQtyCount(Env.ZERO);
				line.setResultCLS("");
				line.setHR_Employee_ID(0);
				line.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				line.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				
				List<String> colNames = PO.getSqlInsert_Para(X_HM_PatientRegisterLine.Table_ID, null);
				List<Object> params = PO.getBatchValueList(line, colNames, I_HM_PatientRegisterLine.Table_ID, null, M_PatientRegisterLine_ID);
				listParams.add(params);
				if (listParams.size() >= BATCH_SIZE) {
					DB.excuteBatch(sqlInsert, listParams, null);
					listParams.clear();
				}
			}
			if (listParams.size() > 0) {
				DB.excuteBatch(sqlInsert, listParams, null);
				listParams.clear();
			}
		}
		
		return true;
		
	}
	
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void onEvent (Event event)
	{
		if (event != null)
		{
			if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_CANCEL)))
			{
				dispose();
			}
			if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_OK)))
			{
				saveChange();
				dispose();
			}
			else if (event.getTarget().equals(btnAdd))
			{
				if (product == null && product.getSelectedItem() == null) {
					M_Product_ID = 0;
				}
				run();
			}else if (event.getTarget() == selectionTable) {
				int row = selectionTable.getRowCount();
				for(int i = 0; i < row; i++) {
					
				}
			}
		}
	}	//	actionPerformed
	
	private String getSQLWhere()
	{
		StringBuilder whereClause = new StringBuilder();
	
		whereClause.append("    WHERE 1=1");
		if (product != null && product.getSelectedItem() != null) {
			M_Product_ID = Integer.parseInt(product.getSelectedItem().getValue().toString());
			if (M_Product_ID == 0)
				whereClause.append(" AND M_Product.ProductType = 'MED_EXA'");
			else
				whereClause.append(" AND M_Product.M_Product_ID = " + M_Product_ID);
		}
		
		return whereClause.toString();
	}
	
	
	
	public void run()
	{
		
		if (this.selectionTable == null)
			return;
		
		//  Clear Table
		this.selectionTable.setRowCount(0);
		//
		String sql = m_query.toString();
		
		String dynWhere = getSQLWhere();
		if (dynWhere.length() > 0)
			sql += dynWhere;		
		sql += m_order.toString();
		String dataSql = Msg.parseTranslation(p_ctx, sql);		
		PreparedStatement m_pstmt = null;
		ResultSet m_rs = null;
		try
		{
			m_pstmt = DB.prepareCall(dataSql);
			
			m_rs = m_pstmt.executeQuery();
			while (m_rs.next())
			{
				int row = this.selectionTable.getRowCount();
				this.selectionTable.setRowCount(row+1);
				int colOffset = 1;  //  columns start with 1
				List<Object> list = new ArrayList<Object>();
				for (int col = 0; col < m_columns.length; col++)
				{
					if (m_columns[col] != null)
					{						
						Object data = null;
						Class<?> c = m_columns[col].getColClass();
						int colIndex = col + colOffset;
						if (c == IDColumn.class)
						{
							data = new IDColumn(m_rs.getInt(colIndex));
							
						}
						else if (c == Boolean.class) 
							data = Boolean.parseBoolean("Y".equals(m_rs.getString(colIndex))? "true":"false");
						else if (c == Timestamp.class)
							data = m_rs.getTimestamp(colIndex);
						else if (c == BigDecimal.class)
							data = m_rs.getBigDecimal(colIndex);
						else if (c == Double.class)
							if (m_rs.getObject(colIndex) != null)
								data = Double.parseDouble(m_rs.getObject(colIndex).toString());
							else
								data = null;
						else if (c == Integer.class)
							if (m_rs.getObject(colIndex) != null)
								data = Integer.parseInt(m_rs.getObject(colIndex).toString());
							else
								data = null;
							
						else if (c == KeyNamePair.class)
						{
							String display = m_rs.getString(colIndex);
							int key = m_rs.getInt(colIndex+1);
							data = new KeyNamePair(key, display);
							colOffset++;
						}
						else
							data = m_rs.getString(colIndex);
						
						//  store
						this.selectionTable.setValueAt(data, row, col);
						list.add(data);
					}
				}
				List<Object> lst = new ArrayList<Object>();
				int M_Product_ID = m_rs.getInt("M_Product_ID");
				if (M_Product_ID > 0) {
					lst.add(m_rs.getInt("M_Product_ID"));
					lst.add(m_rs.getBigDecimal("PriceSO"));
					hastData.put(M_Product_ID, lst);
				}
			}
		}
		catch (Exception e)
		{
			String ex = e.toString();
			ex = ex + e.toString();
		}
		finally
		{
			DB.close(m_rs, m_pstmt);
			m_rs = null;
			m_pstmt = null;
		}
		//
		int no = selectionTable.getRowCount();
		selectionTable.autoSize();
		
		//
		setSearchStatusLine(no, Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
	}   //  run
	
	
	
	public void setSearchStatusLine (int no, String text, boolean error)
	{
		selectionStatusBar.setStatusLine(text, error);
	}	//	setStatusLine

	
	
	protected Comparator<Object> getColumnComparator(boolean ascending, final int columnIndex)
    {
    	return new ColumnComparator(ascending, columnIndex);
    }
	
	public static class ColumnComparator implements Comparator<Object>
    {

    	private int columnIndex;
		private MSort sort;

		public ColumnComparator(boolean ascending, int columnIndex)
    	{
    		this.columnIndex = columnIndex;
    		sort = new MSort(0, null);
        	sort.setSortAsc(ascending);
    	}

        public int compare(Object o1, Object o2)
        {
                Object item1 = ((List<?>)o1).get(columnIndex);
                Object item2 = ((List<?>)o2).get(columnIndex);
                return sort.compare(item1, item2);
        }

		public int getColumnIndex()
		{
			return columnIndex;
		}
    }
	

	@Override
	public void valueChange(ValueChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ADForm getForm() {
		return null;
	}
	
	class ObjCombobox {
		private int value;
		private String name;
		
		public ObjCombobox(int value, String name) {
			this.setValue(value);
			this.setName(name);
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
