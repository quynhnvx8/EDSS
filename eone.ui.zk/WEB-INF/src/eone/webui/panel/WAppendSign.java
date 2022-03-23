
package eone.webui.panel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import eone.base.model.GridTab;
import eone.base.model.I_AD_Signer;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.base.model.X_AD_AppendSign;
import eone.base.model.X_AD_Signer;
import eone.base.process.DocAction;
import eone.exceptions.DBException;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;
import eone.webui.ClientInfo;
import eone.webui.apps.AEnv;
import eone.webui.component.Button;
import eone.webui.component.Combobox;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.ListCell;
import eone.webui.component.ListHead;
import eone.webui.component.ListHeader;
import eone.webui.component.SimpleListModel;

import eone.webui.component.Window;
import eone.webui.event.DialogEvents;
import eone.webui.factory.ButtonFactory;
import eone.webui.theme.ThemeManager;
import eone.webui.util.ZKUpdateUtil;

public class WAppendSign extends Window implements EventListener<Event>
{
	/**
	 * QUYNHVN.X8: Add 12/09/2021
	 */
	private static final long serialVersionUID = 8266807399792500541L;

	private static final CLogger log = CLogger.getCLogger(WAppendSign.class);


	private Button bDelete = ButtonFactory.createNamedButton(ConfirmPanel.A_DELETE, false, true);
	private Button bAddNew = new Button();
	private Button bCancel = ButtonFactory.createNamedButton(ConfirmPanel.A_CANCEL, false, true);
	private Button bOk = ButtonFactory.createNamedButton(ConfirmPanel.A_OK, false, true);
	
	private Borderlayout mainPanel = new Borderlayout();

	private Hbox toolBar = new Hbox();

	private Hlayout confirmPanel = new Hlayout();

	private List<Object []> retEmployee;
	private List<Object []> retDepartment;

	private String orientation;
	private String DocStatus;
	private boolean isReadOnly = true;

	private int m_AD_AppendSign_ID = 0;
	private int m_AD_Table_ID = 0;
	private int m_Record_ID = 0;
	private int m_AD_Window_ID = 0;

	public WAppendSign(GridTab gridTab,	int WindowNo, int AD_AppendSign_ID,	int AD_Table_ID, int Record_ID, String trxName)
	{
		this(gridTab, WindowNo, AD_AppendSign_ID, AD_Table_ID, Record_ID, trxName, (EventListener<Event>)null);
	}
	
	
	public WAppendSign(GridTab gridTab, int WindowNo, int AD_AppendSign_ID,	int AD_Table_ID, int Record_ID, String trxName, EventListener<Event> eventListener)
	{
		super();

		m_AD_AppendSign_ID = AD_AppendSign_ID;
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		m_AD_Window_ID = gridTab.getAD_Window_ID();
		dataAdd = new HashMap<Integer, X_AD_Signer>();
		DocStatus = (String)gridTab.getValue("DocStatus");
		isReadOnly = !DocAction.STATUS_Drafted.equals(DocStatus);
		
		if (log.isLoggable(Level.CONFIG)) log.config("ID=" + AD_AppendSign_ID + ", Table=" + AD_Table_ID + ", Record=" + Record_ID);

		this.addEventListener(DialogEvents.ON_WINDOW_CLOSE, this);
		if (eventListener != null) 
		{
			this.addEventListener(DialogEvents.ON_WINDOW_CLOSE, eventListener);
		}
		
		try
		{
			retEmployee = loadEmployee();
			retDepartment = loadDepartment();
			loadData();
			staticInit();
			
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}

		try
		{
			setAttribute(Window.MODE_KEY, Window.MODE_HIGHLIGHTED);
			AEnv.showWindow(this);
		}
		catch (Exception e)
		{
		}

	}

	
	SimpleListModel yesModel = new SimpleListModel();
	Listbox yesList = new Listbox();
	
	void staticInit() throws Exception
	{
		this.setMaximizable(true);
		ZKUpdateUtil.setWindowWidthX(this, 800);
		ZKUpdateUtil.setHeight(this, "60%");
		
		this.setTitle(Msg.getMsg(Env.getCtx(), "AppendSign"));
		this.setClosable(true);
		this.setSizable(true);
		this.setBorder("normal");
		this.setSclass("popup-dialog attachment-dialog");
		this.setShadow(true);
		this.appendChild(mainPanel);
		ZKUpdateUtil.setHeight(mainPanel, "100%");
		ZKUpdateUtil.setWidth(mainPanel, "100%");

		North northPanel = new North();
		northPanel.setStyle("padding: 4px");
		northPanel.setCollapsible(false);
		northPanel.setSplittable(false);

		toolBar.setAlign("center");
		toolBar.setPack("start");
		toolBar.appendChild(bAddNew);
		toolBar.appendChild(bDelete);

		mainPanel.appendChild(northPanel);
		Vlayout div = new Vlayout();
		div.appendChild(toolBar);
		northPanel.appendChild(div);
		
		Center center = new Center();
		ZKUpdateUtil.setVflex(yesList, true);
		
		ListHead listHead = new ListHead();
		listHead.setParent(yesList);
		ListHeader listHeader = new ListHeader();
		listHeader.appendChild(new Label(""));
		listHeader.setParent(listHead);
		ZKUpdateUtil.setWidth(listHeader, "50px");
		
		listHeader = new ListHeader();
		listHeader.appendChild(new Label(Msg.getMsg(Env.getCtx(), "AD_Department_ID")));
		ZKUpdateUtil.setWidth(listHeader, "200px");
		listHeader.setParent(listHead);
		
		listHeader = new ListHeader();
		listHeader.appendChild(new Label(Msg.getMsg(Env.getCtx(), "HR_Employee_ID")));
		ZKUpdateUtil.setWidth(listHeader, "200px");
		listHeader.setParent(listHead);
		
		listHeader = new ListHeader();
		listHeader.appendChild(new Label(Msg.getMsg(Env.getCtx(), "SeqNo")));
		ZKUpdateUtil.setWidth(listHeader, "50px");
		listHeader.setParent(listHead);
		
		listHeader = new ListHeader();
		listHeader.appendChild(new Label(Msg.getMsg(Env.getCtx(), "Comment")));
		//ZKUpdateUtil.setWidth(listHeader, "300px");
		listHeader.setParent(listHead);
		
		
		yesModel.setMultiple(true);
		//yesList.setCheckmark(true);
		//yesList.setMultiple(true);
		yesList.setModel(yesModel);
		
		rendererListbox();
		
		Hlayout hlayout = new Hlayout();

		ZKUpdateUtil.setVflex(hlayout, "true");
		ZKUpdateUtil.setHflex(hlayout, "true");
		hlayout.setStyle("margin: auto; margin-top: 2px;");


		ZKUpdateUtil.setVflex(yesList, true);
		ZKUpdateUtil.setHflex(yesList, "1");
		hlayout.appendChild(yesList);

		center.appendChild(hlayout);

		mainPanel.appendChild(center);

		bAddNew.setImage(ThemeManager.getThemeResource("images/New24.png"));
		bAddNew.setSclass("img-btn");
		bAddNew.setId("bAddNew");
		bAddNew.setTooltiptext(Msg.getMsg(Env.getCtx(), "AddNew"));
		bAddNew.addEventListener(Events.ON_CLICK, this);

		bDelete.addEventListener(Events.ON_CLICK, this);
		
		if (isReadOnly) {
			bAddNew.setVisible(false);
			bDelete.setVisible(false);
		}
		
		South southPane = new South();
		southPane.setSclass("dialog-footer");
		mainPanel.appendChild(southPane);
		southPane.appendChild(confirmPanel);
		ZKUpdateUtil.setVflex(southPane, "min");

		bCancel.addEventListener(Events.ON_CLICK, this);
		bOk.addEventListener(Events.ON_CLICK, this);

		ZKUpdateUtil.setHflex(confirmPanel, "1");
		Hbox hbox = new Hbox();
		hbox.setPack("end");
		ZKUpdateUtil.setHflex(hbox, "1");
		confirmPanel.appendChild(hbox);
		hbox.appendChild(bOk);
		hbox.appendChild(bCancel);
		
	}
	
	private void rendererListbox() {
		yesList.setItemRenderer(new  ListitemRenderer<X_AD_Signer>() {
			@Override
			public void render(Listitem item, X_AD_Signer data, int index) throws Exception {
				ListCell cell = new ListCell();
				
				Checkbox cb = new Checkbox();
				cell.appendChild(cb);
				item.appendChild(cell);
				cb.setValue(data.getAD_Signer_ID());
				if (isReadOnly)
					cb.setDisabled(true);
				
				Label content;
				if (isReadOnly) {
					cell = new ListCell();
					content = new Label(data.getAD_Department().getName());
					cell.appendChild(content);
					item.appendChild(cell);
					
					cell = new ListCell();
					content = new Label(data.getHR_Employee().getName());
					cell.appendChild(content);
					item.appendChild(cell);
					
					cell = new ListCell();
					content = new Label(data.getSeqNo() + "");
					cell.appendChild(content);
					item.appendChild(cell);
					
				} else {
					cell = new ListCell();
					Combobox dep = new Combobox("");
					cell.appendChild(dep);
					SimpleListModel model = new SimpleListModel(retDepartment);
					dep.setModel(model);
					
					dep.setItemRenderer(new ComboitemRenderer<Object []>() {
						@Override
						public void render(Comboitem item, Object[] row, int index) throws Exception {
							item.setValue(row[0]);
							item.setLabel(row[1].toString());
							if (Integer.parseInt(row[0].toString()) == data.getAD_Department_ID()) {
								dep.setSelectedItem(item);
							}
						}
					});
					item.appendChild(cell);
					
					cell = new ListCell();
					Combobox box = new Combobox("");
					cell.appendChild(box);
					model = new SimpleListModel(retEmployee);
					box.setModel(model);
					
					box.setItemRenderer(new ComboitemRenderer<Object []>() {
						@Override
						public void render(Comboitem item, Object[] row, int index) throws Exception {
							item.setValue(row[0]);
							item.setLabel(row[1].toString());
							if (Integer.parseInt(row[0].toString()) == data.getHR_Employee_ID()) {
								box.setSelectedItem(item);
							}
						}
					});
					item.appendChild(cell);
					
					cell = new ListCell();
					Textbox text = new Textbox();
					cell.appendChild(text);		
					
					ZKUpdateUtil.setWidth(text, "30px");
					text.setValue(data.getSeqNo() + "");				
					item.appendChild(cell);	
				}
				
				cell = new ListCell();
				content = new Label(data.getComments());
				cell.appendChild(content);
				item.appendChild(cell);
				
			}
		});
	}
	
	protected void onClientInfo()
	{		
		if (getPage() != null)
		{
			String newOrienation = ClientInfo.get().orientation;
			if (!newOrienation.equals(orientation))
			{
				orientation = newOrienation;
				ZKUpdateUtil.setCSSHeight(this);
				ZKUpdateUtil.setCSSWidth(this);
				invalidate();
			}
		}
	}

	/**
	 * 	Dispose
	 */

	public void dispose ()
	{
		this.detach();
	} // dispose

	public void loadData()
	{
		yesModel.removeAllElements();
		Query query = null;
		
		query = new Query(Env.getCtx(), I_AD_Signer.Table_Name, " AD_AppendSign_ID = ? AND IsActive='Y'", null);
		query.setOrderBy("SeqNo");
		query.setParameters(new Object [] {m_AD_AppendSign_ID});
		query.setApplyAccessFilter(true);

		try
		{
			List<X_AD_Signer> lsFieldsOfGrid = query.list();			
			yesModel = new SimpleListModel(lsFieldsOfGrid);
		}
		catch (DBException e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		
		
	}

	Map<Integer, X_AD_Signer> dataAdd;
	

	public void onEvent(Event e)
	{
		//	Save and Close
		if (e.getTarget() == bOk) {
			if (!isReadOnly) {
				int AD_AppendSign_ID = 0;
				if (m_AD_AppendSign_ID <= 0)
					AD_AppendSign_ID = saveParent();
				else
					AD_AppendSign_ID = m_AD_AppendSign_ID;
				saveChild(AD_AppendSign_ID);
			}			
			dispose();
		} else if (e.getTarget() == bCancel || DialogEvents.ON_WINDOW_CLOSE.equals(e.getName())) {
			//	Cancel
			dispose();
		} else if (e.getTarget() == bDelete) {
			deleteChild();
		} else if (e.getTarget() == bAddNew) {
			SimpleListModel model = (SimpleListModel) yesList.getModel();
			X_AD_Signer sign = new X_AD_Signer(Env.getCtx(), 0, null);
			model.add(model.getSize(), sign);
			dataAdd.put(model.getSize(), sign);
		} 

	}	//	onEvent

	private void saveChild(int AD_AppendSign_ID) {
		List<List<Object>> valuesIn = new ArrayList<List<Object>>();
		List<List<Object>> valuesUp = new ArrayList<List<Object>>();
		String sql = PO.getSqlInsert(X_AD_Signer.Table_ID, null);
		SimpleListModel model = (SimpleListModel) yesList.getModel();
		String sqlUpdate = "UPDATE AD_Signer SET HR_Employee_ID = ?, SeqNo = ? WHERE AD_Signer_ID = ?";
		List<Object> paramsU = null;
		for (int i = 0; i < model.getSize(); i++) {
			Listitem item = yesList.getItemAtIndex(i);
			Listcell cell = (Listcell) item.getChildren().get(0);
			Checkbox cb = (Checkbox) cell.getChildren().get(0);
			if (cb.isChecked()) {
				paramsU = new ArrayList<Object>();
				cell = (Listcell) item.getChildren().get(1);
				Combobox dept = (Combobox) cell.getChildren().get(0);
				cell = (Listcell) item.getChildren().get(2);
				Combobox empl = (Combobox) cell.getChildren().get(0);
				cell = (Listcell) item.getChildren().get(3);
				Textbox text = (Textbox) cell.getChildren().get(0);
				paramsU.add(dept.getSelectedItem().getValue());
				paramsU.add(empl.getSelectedItem().getValue());
				paramsU.add(Integer.parseInt(text.getValue()));
				paramsU.add(cb.getValue());
				valuesUp.add(paramsU);
			}
		}
		
		for(int sign : dataAdd.keySet()) {
			X_AD_Signer rowList = (X_AD_Signer) model.getElementAt(sign - 1);
			if (rowList != null) {
				Listitem item = yesList.getItemAtIndex(sign - 1);
				Listcell cell = (Listcell) item.getChildren().get(3);
				Textbox text = (Textbox) cell.getChildren().get(0);
				rowList.setSeqNo(Integer.parseInt(text.getValue()));
				
				cell = (Listcell) item.getChildren().get(1);
				Combobox cob = (Combobox) cell.getChildren().get(0);
				rowList.setAD_Department_ID(cob.getSelectedItem().getValue());
				
				cell = (Listcell) item.getChildren().get(2);
				cob = (Combobox) cell.getChildren().get(0);
				rowList.setHR_Employee_ID(cob.getSelectedItem().getValue());
				
				rowList.setSignStatus(DocAction.SIGNSTATUS_None);
				rowList.setAD_Signer_ID(DB.getNextID(Env.getCtx(), X_AD_Signer.Table_Name, null));
				rowList.setAD_AppendSign_ID(AD_AppendSign_ID);
				rowList.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				rowList.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
				
				List<String> colNames = PO.getSqlInsert_Para(X_AD_Signer.Table_ID, null);
				List<Object> params = PO.getBatchValueList(rowList, colNames, X_AD_Signer.Table_ID, null, rowList.getAD_Signer_ID());
				valuesIn.add(params);
			}
		}
		if (valuesUp.size() > 0) {
			DB.excuteBatch(sqlUpdate, valuesUp, null);
		}
		if (valuesIn.size() > 0) {
			DB.excuteBatch(sql, valuesIn, null);
		}
	}
	
	private int saveParent() {
		X_AD_AppendSign appendSign = new X_AD_AppendSign(Env.getCtx(), 0, null);
		appendSign.setAD_AppendSign_ID(DB.getNextID(Env.getCtx(), X_AD_AppendSign.Table_Name, null));
		appendSign.setAD_Table_ID(m_AD_Table_ID);
		appendSign.setRecord_ID(m_Record_ID);
		appendSign.setAD_Window_ID(m_AD_Window_ID);
		if (appendSign.save())
			return appendSign.getAD_AppendSign_ID();
		else
			return 0;
	}
	
	private void deleteChild() {
		SimpleListModel model = (SimpleListModel) yesList.getModel();
		
		String listID = "";
		for (int i = 0; i < model.getSize(); i++) {
			Listitem item = yesList.getItemAtIndex(i);
			Listcell cell = (Listcell) item.getChildren().get(0);
			Checkbox cb = (Checkbox) cell.getChildren().get(0);
			if (cb.isChecked()) {
				if ("".equals(listID))
					listID = cb.getValue() + "";
				else
					listID = listID + "," + cb.getValue();
			}
		}
		if (listID.length() > 0) {
			String sql = "DELETE FROM AD_Signer WHERE AD_Signer_ID IN ("+ listID +")";
			DB.executeUpdate(sql);
		}
	}
	
	private List<Object []> loadEmployee() {
		List<Object []> retValue = new ArrayList<Object[]>();
		String sql = "Select HR_Employee_ID, Name From HR_Employee Where IsActive = 'Y'";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				retValue.add(new Object [] {rs.getInt("HR_Employee_ID"), rs.getString("Name")});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
			rs = null;
			ps = null;
		}
		return retValue;
	}
	
	private List<Object []> loadDepartment() {
		List<Object []> retValue = new ArrayList<Object[]>();
		String sql = "Select AD_Department_ID, Name From AD_Department Where IsActive = 'Y' And IsSummary = 'N' AND DepartmentType = 'CLI'";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				retValue.add(new Object [] {rs.getInt("AD_Department_ID"), rs.getString("Name")});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
			rs = null;
			ps = null;
		}
		return retValue;
	}
}
