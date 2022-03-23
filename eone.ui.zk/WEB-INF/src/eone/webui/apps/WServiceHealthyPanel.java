
package eone.webui.apps;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.North;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.South;

import eone.base.model.MColumn;
import eone.base.model.MLookup;
import eone.base.model.MLookupFactory;
import eone.base.model.MPatientRegister;
import eone.util.DB;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.MSort;
import eone.util.Msg;
import eone.util.TimeUtil;
import eone.webui.component.Button;
import eone.webui.component.Column;
import eone.webui.component.Columns;
import eone.webui.component.ComboItem;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Datebox;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Label;
import eone.webui.component.ListCell;
import eone.webui.component.ListHead;
import eone.webui.component.ListHeader;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.component.Window;
import eone.webui.editor.WSearchEditor;
import eone.webui.event.WTableModelEvent;
import eone.webui.event.WTableModelListener;
import eone.webui.panel.ADForm;
import eone.webui.panel.CustomForm;
import eone.webui.panel.IFormController;
import eone.webui.panel.StatusBarPanel;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;


public class WServiceHealthyPanel extends Window implements IFormController, EventListener<Event>, WTableModelListener
{

	private static final long serialVersionUID = -3464399582503864350L;
	
	private boolean				p_initOK = false;
	
	private Listbox 			selectedTable =  new Listbox();
	//private String zoomLogic = null;
	
	private StatusBarPanel 		selectionStatusBar = new StatusBarPanel();
	private Button				btnSearch;	
	
	private Label				lbDate;
	private Datebox				fieldFromDate;
	private Datebox				fieldToDate;
	
	private Label lPatient = new Label();
	private WSearchEditor selPatient;
	
	private Combobox			cbFinish;
	private Label				lbFinish;
	
	private ConfirmPanel 		confirmPanel;
	
	private StringBuilder		m_query = new StringBuilder();
	private WInfo_Column[] 		m_columns = null;	
	
	
	public WServiceHealthyPanel()
	{
		super();
		
		try
		{
			initComponent();
			prepareTable();
			jbInit ();
			
			p_initOK = dynInit();
		}
		catch (Exception ex)
		{
			p_initOK = false;
		}
	}	//	init
	
	ListHead listhead = new ListHead();
	
	private void prepareTable()
	{
		
		int colNumbers = 6;
		int index = 0;		
		//select l.HM_PatientRegisterLine_ID, r.No, p.Name, p.BirthYear, p.Address
		index = 0;
		m_columns = new WInfo_Column[colNumbers];
		m_columns[index++] = new WInfo_Column("", 							"", Button.class, 	42);
		//m_columns[index++] = new WInfo_Column("", 							"l.HM_PatientRegisterLine_ID", IDColumn.class, 	100);		
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "SeqNo"), 			"r.SeqNo", 		String.class, 	80, "center");
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "HM_Patient_ID"), 	"p.Name", 		String.class,	150);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "BirthYear"), 		"p.BirthYear", 	String.class,	80, "center");
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "Address"), 			"p.Address", 	String.class, 300);
		m_columns[index++] = new WInfo_Column(Msg.translate(Env.getCtx(), "ListService"),		"ListService",String.class, 500);
		
		
		Object[] layouts = new Object[] {m_columns};


		WInfo_Column[] layout = (WInfo_Column[])layouts[0];
		
		
		// remove existing column header
		listhead.getChildren().clear();
		listhead.setSizable(true);
		for (int i = 0; i < layout.length; i++)
		{
			if (layout[i] != null)
			{
				if (i > 0)
					m_query.append(", ");
				m_query.append(layout[i].getColSQL());
				//  adding ID column
				if (layout[i].isIDcol())
					m_query.append(",").append(layout[i].getIDcolSQL());
				
				// add Header
				ListHeader listheader = new ListHeader(layout[i].getColHeader());
				listheader.setWidth(layout[i].getWidth() + "px");
				if(i > 0) {
					listhead.setSizable(true);
				}
				
				if (layout[i].getTextAlign() != null) {
					listheader.setAlign(layout[i].getTextAlign());
				}
				
				listheader.setTooltiptext(layout[i].getColHeader());
				Comparator<Object> ascComparator =  getColumnComparator(true, i);
		        Comparator<Object> dscComparator =  getColumnComparator(false, i);
			        listheader.setSortAscending(ascComparator);
		        listheader.setSortDescending(dscComparator);

				listhead.appendChild(listheader);
								
			}
		}
		
	}
	
	private List<Object[]> loadData() {
		List<Object []> retValue = new ArrayList<Object[]>();
		String sqlSelect = 
				"SELECT r.HM_PatientRegister_ID, r.SeqNo, p.Name patientName, p.BirthYear, p.Address,  "
				+" ("
				+ "		SELECT STRING_AGG(p.Name :: text, ' + ') "
				+ "		FROM HM_PatientRegisterLine l INNER JOIN M_Product p ON l.M_Product_ID = p.M_Product_ID "
				+ "		WHERE l.HM_PatientRegister_ID = r.HM_PatientRegister_ID "
				+ ") AS ListService"
				+" From HM_PatientRegister r "
				+ "		LEFT JOIN HM_Patient p ON r.HM_Patient_ID = p.HM_Patient_ID ";
		String sqlWhere = getSQLWhere();
		String sqlOrder = getOrderBy();
		StringBuilder sql = new StringBuilder()
				.append(sqlSelect)
				.append(sqlWhere)
				.append(sqlOrder);
		PreparedStatement ps = DB.prepareCall(sql.toString());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Object [] param = 
					{
							rs.getInt("HM_PatientRegister_ID"),
							rs.getString("SeqNo"),
							rs.getString("patientName"),
							rs.getString("BirthYear"),
							rs.getString("Address"),
							rs.getString("ListService")
					};
				retValue.add(param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}
		return retValue;
	}
	
	private boolean dynInit()
	{
		return true;
	}
	
	public boolean isInitOK()
	{
		return p_initOK;
	}	//	isInitOK
	
	private void initComponent()
	{
		lbDate = new Label(Msg.translate(Env.getCtx(), "DateAcct"));
		fieldFromDate = new Datebox();			
		fieldToDate = new Datebox();	
		
		lPatient.setValue(Msg.translate(Env.getCtx(), "HM_Patient_ID"));
		MLookup mLookup = MLookupFactory.get(Env.getCtx(), 0, 0, MColumn.getColumn_ID("HM_Patient", "HM_Patient_ID"), DisplayType.Search);
		selPatient = new WSearchEditor("HM_Patient_ID", false, false, true, mLookup);
		ZKUpdateUtil.setWidth(selPatient.getComponent(), "90%");
		
		lbFinish = new Label(Msg.translate(Env.getCtx(), "FinishStatus"));
		
		ComboItem item = null;
		cbFinish = new Combobox("---- "+ Msg.translate(Env.getCtx(), "Select") + " ----");
		item =new ComboItem("---- "+ Msg.translate(Env.getCtx(), "Select") + " ----", "0");
		cbFinish.appendChild(item);
		item =new ComboItem(Msg.translate(Env.getCtx(), "UnFinish"), "N");
		cbFinish.appendChild(item);
		item =new ComboItem(Msg.translate(Env.getCtx(), "Finish"), "Y");
		cbFinish.appendChild(item);
		
		
		btnSearch = new Button(Msg.translate(Env.getCtx(), "Search"));
		btnSearch.addActionListener(this);
		confirmPanel = new ConfirmPanel(true, false, false, false, false, false);
		confirmPanel.addActionListener(Events.ON_CLICK, this);
		
		return;
	}
	
	
	private void jbInit () throws Exception
	{        
		form = new CustomForm();
		
		Borderlayout mainPanel = new Borderlayout();
		ZKUpdateUtil.setWidth(mainPanel, "100%");
		ZKUpdateUtil.setHeight(mainPanel, "100%");
		
        this.setBorder("normal");
		
		North north = new North();
		mainPanel.appendChild(north);
		Grid grid = GridFactory.newGridLayout();
					
		Columns columns = new Columns();
		grid.appendChild(columns);
		//Group 1
		Column column = new Column();
		ZKUpdateUtil.setWidth(column, "10%");
		columns.appendChild(column);
		column = new Column();
		ZKUpdateUtil.setWidth(column, "25%");
		columns.appendChild(column);
		
		//Gruop 2
		column = new Column();
		ZKUpdateUtil.setWidth(column, "10%");
		columns.appendChild(column);		
		column = new Column();
		ZKUpdateUtil.setWidth(column, "20%");
		columns.appendChild(column);
		
		//Group 3
		column = new Column();
		ZKUpdateUtil.setWidth(column, "10%");
		columns.appendChild(column);
		column = new Column();
		ZKUpdateUtil.setWidth(column, "25%");
		columns.appendChild(column);
		
		Rows rows = new Rows();
		Row row = rows.newRow();		
		Hlayout hlayout = new Hlayout();
		lbDate.setValue(Msg.translate(Env.getCtx(), "DateService"));
		row.appendChild(lbDate);
		hlayout = new Hlayout();
		hlayout.appendChild(fieldFromDate);		
		fieldFromDate.setValue(Env.getContextAsDate(Env.getCtx(), "#FYDate"));
		hlayout.appendChild(new Label(" - "));
		hlayout.appendChild(fieldToDate);
		fieldToDate.setValue(Env.getContextAsDate(Env.getCtx(), "#LDate"));
		row.appendChild(hlayout);
		
		row.appendChild(lPatient);
		row.appendChild(selPatient.getComponent());
		
		row.appendChild(lbFinish);
		hlayout = new Hlayout();
		hlayout.appendChild(cbFinish);
		hlayout.appendChild(btnSearch);
		
		row.appendChild(hlayout);
		rows.appendChild(row);
		
		grid.appendChild(rows);
		north.appendChild(grid);
		
		// Center 
		Center wCenter = new Center();
		wCenter.appendChild(selectedTable);
		mainPanel.appendChild(wCenter);
		
		selectedTable.appendChild(listhead);
		ZKUpdateUtil.setWidth(selectedTable, "100%");
		ZKUpdateUtil.setHeight(selectedTable, "100%");
		
		rendererListTable();
		
		South westSouth = new South();
		//westSouth.setStyle("border: solid 1px gray");
		//westSouth.appendChild(confirmPanel);
		mainPanel.appendChild(westSouth);
		
		form.appendChild(mainPanel);
        this.appendChild(form);
	}	//	jbInit

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		this.detach();
	}	//	dispose
				
	
	private void rendererListTable () {
		selectedTable.clearSelection();
		SimpleListModel<Object []> model = new SimpleListModel<Object []>(loadData());
		selectedTable.setModel(model);
		selectedTable.setItemRenderer(new ListitemRenderer<Object []>() {

			@Override
			public void render(Listitem item, Object[] data, int index) throws Exception {
				Listcell cell = new ListCell();
				Button btn = new Button("...");
				btn.addActionListener(new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						AEnv.zoom(MPatientRegister.Table_ID, Integer.parseInt(data[0].toString()), form.getZoomWindow());
					}
					
				});
				cell.appendChild(btn);
				item.appendChild(cell);
				
				//cell = new ListCell(data[0].toString());
				//item.appendChild(cell);
				
				cell = new ListCell(data[1] == null? "" : data[1].toString());
				item.appendChild(cell);
				
				cell = new ListCell(data[2] == null? "" : data[2].toString());
				item.appendChild(cell);
				
				cell = new ListCell(data[3] == null? "" : data[3].toString());
				item.appendChild(cell);
				
				cell = new ListCell(data[4] == null? "" : data[4].toString());
				item.appendChild(cell);
				
				cell = new ListCell(data[5] == null? "" : data[5].toString());
				item.appendChild(cell);
			}
			
		});
	}
	
	public void onEvent (Event event)
	{
		if (event != null)
		{
			if (event.getName() == Events.ON_DOUBLE_CLICK) {
				;//AEnv.zoom(MPatientRegister.Table_ID, data[0], 15200304);
			}
			if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_CANCEL)))
			{
				dispose();
			}
			if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_OK)))
			{
				dispose();
			}
			else if (event.getTarget().equals(btnSearch))
			{
				if (fieldFromDate == null || fieldFromDate.getValue() == null) {
					FDialog.warn(0, "From date is null");
					return ;
				}
				if (fieldToDate == null || fieldToDate.getValue() == null) {
					FDialog.warn(0, "To date is null");
					return ;
				}
				Timestamp fromDate = new Timestamp(fieldFromDate.getValue().getTime());
				Timestamp toDate = new Timestamp(fieldToDate.getValue().getTime());
				
				if (TimeUtil.getYearSel(fromDate) != TimeUtil.getYearSel(toDate)) {
					FDialog.warn(0, "From Date and To date not same year !");
					return ;
				}
				rendererListTable();
			}
		}
	}	//	actionPerformed
	
	private String getSQLWhere()
	{
		
		StringBuilder whereClause = new StringBuilder(" WHERE ").append(form.getZoomLogic());
		
		whereClause.append(" AND Exists (Select 1 From HM_PatientRegisterLine l Where l.HM_PatientRegister_ID = r.HM_PatientRegister_ID AND l.Processed = 'N')");
		
		if (selPatient != null && selPatient.getValue() != null) {
			whereClause
			.append(" AND r.HM_Patient_ID = ")
			.append(Integer.parseInt(selPatient.getValue().toString()));
		}
		
		if (fieldFromDate != null && fieldFromDate.getValue() != null) {
			Timestamp fromDate = new Timestamp(fieldFromDate.getValue().getTime());
			whereClause.append(" AND r.DateService >= ").append(DB.TO_DATE(fromDate));
		}
		
		if (fieldToDate != null && fieldToDate.getValue() != null) {
			Timestamp toDate = new Timestamp(fieldToDate.getValue().getTime());
			whereClause.append(" AND r.DateService <= ").append(DB.TO_DATE(toDate));
		}
		
		if (!"0".equals(cbFinish.getSelectedItem().getValue())) {
			whereClause.append(" AND r.IsFinish = '").append(cbFinish.getSelectedItem().getValue().toString()).append("'");
		}
		
		
		return whereClause.toString();
	}
	
	private String getOrderBy () {
		return " ORDER BY r.SeqNo";
	}
	
	
	
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

	private CustomForm form;
	@Override
	public ADForm getForm() {
		return form;
	}

	@Override
	public void tableChanged(WTableModelEvent event) {
		// TODO Auto-generated method stub
		
	}


}
	