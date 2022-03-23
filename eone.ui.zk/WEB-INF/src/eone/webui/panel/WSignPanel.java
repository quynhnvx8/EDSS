package eone.webui.panel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vlayout;

import eone.base.model.GridTab;
import eone.base.model.MTable;
import eone.base.model.PO;
import eone.base.model.X_AD_Signer;
import eone.base.process.DocAction;
import eone.base.process.DocumentEngine;
import eone.util.Callback;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;
import eone.webui.LayoutUtils;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.component.Textbox;
import eone.webui.component.Window;
import eone.webui.event.DialogEvents;
import eone.webui.theme.ThemeManager;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;



public class WSignPanel extends Window implements EventListener<Event>, DialogEvents
{
	/**
	 * QUYNHNV.X8: Add 13/09/2021 
	 */
	private static final long serialVersionUID = -2166149559040327486L;

	private Label lblSignStatus;
	private Listbox lstSignStatus;
	
	private Label lblReason;
	private Textbox txtReason;

	private GridTab gridTab;
	private String[]		s_value = null;
	private String[]		s_name;
	
	private String DocStatus;
	private boolean m_OKpressed;
    private ConfirmPanel confirmPanel;
    
    private int m_HR_Employee_ID = 0;
    List<X_AD_Signer> data = null;

	public WSignPanel(GridTab mgridTab, List<X_AD_Signer> data)
	{
		gridTab = mgridTab;
		DocStatus = (String)gridTab.getValue("DocStatus");
		m_HR_Employee_ID = Env.getContextAsInt(Env.getCtx(), "#HR_Employee_ID");
		this.data = data;
		
		readReference();
		initComponents();
		dynInit();

		init();
	}

	private void dynInit()
	{

		if (!checkStatus(gridTab.getTableName(), gridTab.getRecord_ID(), DocStatus))
		{
			FDialog.error(gridTab.getWindowNo(), this, "DocumentStatusChanged");
			return;
		}
		
		for (int j = 0; j < s_value.length; j++)
		{
			if (!s_value[j].equalsIgnoreCase(DocAction.SIGNSTATUS_None)) 
			{
				Listitem newitem = lstSignStatus.appendItem(s_name[j],s_value[j]);
				if (s_value[j].equalsIgnoreCase(DocAction.SIGNSTATUS_Approved))
					lstSignStatus.setSelectedItem(newitem);
				lblReason.setVisible(false);
				txtReason.setVisible(false);
			}
		}
				
	}
	
	
	public List<Listitem> getDocActionItems() {
		return (List<Listitem>)lstSignStatus.getItems();
	}
	
	private boolean checkStatus (String TableName, int Record_ID, String DocStatus)
	{
		String sql = "SELECT 2 FROM " + TableName
			+ " WHERE " + TableName + "_ID=" + Record_ID
			+ " AND DocStatus='" + DocStatus + "'";
		int result = DB.getSQLValue(null, sql);
		return result == 2;
	}

	private void initComponents()
	{
		lblSignStatus = new Label();
		lblSignStatus.setValue(Msg.translate(Env.getCtx(), "ApprovedStatus"));
		
		lblReason = new Label(Msg.translate(Env.getCtx(), "ReasonReject"));
		txtReason = new Textbox();
		txtReason.setMultiline(true);
		txtReason.setRows(5);
		ZKUpdateUtil.setWidth(txtReason, "500px");

		lstSignStatus  = new Listbox();
		lstSignStatus.setId("lstSignStatus");
		lstSignStatus.setRows(0);
		lstSignStatus.setMold("select");
		ZKUpdateUtil.setWidth(lstSignStatus, "200px");
		lstSignStatus.addEventListener(Events.ON_SELECT, this);

        confirmPanel = new ConfirmPanel(true);
        confirmPanel.addActionListener(Events.ON_CLICK, this);
        ZKUpdateUtil.setVflex(confirmPanel, "true");
	}

	private void init()
	{
		setSclass("popup-dialog doc-action-dialog");
		Vlayout vlayout = new Vlayout();
		ZKUpdateUtil.setHflex(vlayout, "1");
		this.appendChild(vlayout);
		
		Grid grid = GridFactory.newGridLayout();
        grid.setStyle("background-image: none;");
        LayoutUtils.addSclass("dialog-content", grid);
        vlayout.appendChild(grid);

        Rows rows = new Rows();
        grid.appendChild(rows);

		Row row = new Row();
		row.appendChild(lblSignStatus);
		rows.appendChild(row);
		
		row = new Row();
		row.appendChild(lstSignStatus);
		rows.appendChild(row);
		
		row = new Row();
		row.appendChild(lblReason);
		rows.appendChild(row);
		
		row = new Row();
		row.appendChild(txtReason);
		rows.appendChild(row);

		Div footer = new Div();
	    footer.setSclass("dialog-footer");
	    vlayout.appendChild(footer);
	    footer.appendChild(confirmPanel);
	    ZKUpdateUtil.setVflex(confirmPanel, "min");
	    
	    this.setTitle(Msg.translate(Env.getCtx(), "ApprovedStatus"));
	    if (!ThemeManager.isUseCSSForWindowSize())
	    	ZKUpdateUtil.setWindowWidthX(this, 300);
	    this.setBorder("normal");
	    this.setZindex(1000);
	}

	
	public boolean isStartProcess()
	{
		return m_OKpressed;
	}	//	isStartProcess

	public void onEvent(Event event)
	{

		if (Events.ON_CLICK.equals(event.getName()))
		{
			if (confirmPanel.getButton("Ok").equals(event.getTarget()))
			{				
				onOk(null);
			}
			else if (confirmPanel.getButton("Cancel").equals(event.getTarget()))
			{
				m_OKpressed = false;
				this.detach();
			}			
		}
		else if (Events.ON_SELECT.equals(event.getName()))
		{
			String selected = (lstSignStatus.getSelectedItem().getValue()).toString();
			
			if (selected.equalsIgnoreCase(DocAction.SIGNSTATUS_Cancel))
			{
				lblReason.setVisible(true);
				txtReason.setVisible(true);
			} 
			else
			{
				lblReason.setVisible(false);
				txtReason.setVisible(false);
			}
			
		}
	}

	public void setSelectedItem(String value) {
		lstSignStatus.setSelectedIndex(-1);
		List<Listitem> lst = (List<Listitem>)lstSignStatus.getItems();
		for(Listitem item: lst) {
			if (value.equals(item.getValue())) {
				item.setSelected(true);
				break;
			}
		}
	}
	
	public void onOk(final Callback<Boolean> callback) {
		/*
		String docAction = lstSignStatus.getSelectedItem().getLabel();
		MessageFormat mf = new MessageFormat(Msg.getMsg(Env.getAD_Language(Env.getCtx()), "ConfirmApprovedStatus"));
		Object[] arguments = new Object[]{docAction};
		FDialog.ask(0, this, mf.format(arguments), new Callback<Boolean>() {
			@Override
			public void onCallback(Boolean result) {
				if(result)
				{
					setValueAndClose();
					if (callback != null)
						callback.onCallback(Boolean.TRUE);
				}
				else
				{
					if (callback != null)
						callback.onCallback(Boolean.FALSE);
					return;
				}
			}
		});
		*/
		setValueAndClose();
	}

	private void setValueAndClose() {
		/*
		String statusSql = "SELECT DocStatus FROM " + gridTab.getTableName() 
				+ " WHERE " + gridTab.getKeyColumnName() + " = ? ";
		String currentStatus = DB.getSQLValueString((String)null, statusSql, gridTab.getKeyID(gridTab.getCurrentRow()));
		if (DocStatus != null && !DocStatus.equals(currentStatus)) {
			throw new IllegalStateException(Msg.getMsg(Env.getCtx(), "DocStatusChanged"));
		}
		*/
		m_OKpressed = true;
		
		int seqNoCurrent = 0;
		int AD_AppendSign_ID = 0;
		boolean lastSign = true;
		if (data != null) {
			for(int i = 0; i < data.size(); i++) {
				X_AD_Signer signer = data.get(i);
				if (signer.getHR_Employee_ID() == m_HR_Employee_ID) {
					seqNoCurrent = signer.getSeqNo();
				}
				AD_AppendSign_ID = signer.getAD_AppendSign_ID();
			}
			
			for(int i = 0; i < data.size(); i++) {
				X_AD_Signer signer = data.get(i);
				if (signer.getSeqNo() > seqNoCurrent) {
					lastSign = false;
					break;
				}
			}
		}
		
		String statusApproved = "";
		
		String selected = getSelectedItemValue();
		if (selected != null && DocAction.SIGNSTATUS_Approved.equals(selected)) {
			//Nếu người ký cuối cùng thì cập nhật hoàn thành.
			if (DocAction.STATUS_Drafted.equals(DocStatus) && !lastSign) {
				statusApproved = DocAction.STATUS_Inprogress;//Đang xử lý
			}
			if (lastSign) {
				statusApproved = DocAction.STATUS_Completed;//Hoàn thành
			}
		} else {
			statusApproved = DocAction.STATUS_Drafted;
		}
		setValue(statusApproved);
		
		updateSinger(AD_AppendSign_ID, m_HR_Employee_ID, selected);
		
		SignApproved(statusApproved);
		
		detach();
	}
	
	//Hàm cập nhật trạng thái khi ký hoặc từ chối.
	private void updateSinger(int AD_AppendSign_ID, int HR_Employee_ID, String selected) {
		String label = getSelectedItemLabel();
		String reason = txtReason.getValue();
		String employeeName = Env.getContext(Env.getCtx(), "#EmployeeName");
		String comment = employeeName + " " + label + ".";
		if (reason != null && !reason.isEmpty())
			comment = comment + " " + reason;
		Object [] params = {selected, comment, AD_AppendSign_ID, HR_Employee_ID};
		if (!DocAction.SIGNSTATUS_Approved.equalsIgnoreCase(selected)) {
			String sqlUpdatAll = "UPDATE AD_Signer SET SingStatus = '"+ DocAction.SIGNSTATUS_None +"' WHERE AD_AppendSign_ID = ?";
			DB.executeUpdate(sqlUpdatAll, AD_AppendSign_ID, null);
		}
		String sql = "UPDATE AD_Signer SET SignStatus = ?, Comments =  ? WHERE AD_AppendSign_ID = ? AND HR_Employee_ID = ?";
		DB.executeUpdate(sql, params, true, null);
	}

	private void setValue(String value)
	{
		gridTab.setValue("DocStatus", value);
	}	
	
	private void SignApproved (String statusApproved) {
		boolean success = false;
		String processMsg = null;
		DocAction doc = null;
		String m_docStatus = null;
		MTable table = MTable.get(Env.getCtx(), gridTab.getAD_Table_ID());
		PO po = table.getPO(gridTab.getRecord_ID(), null);
		if (po instanceof DocAction)
		{
			doc = (DocAction)po;
			//
			try {
				success = doc.processIt (statusApproved, gridTab.getAD_Window_ID());	
				processMsg = doc.getProcessMsg();
				m_docStatus = doc.getDocStatus();
				if(m_docStatus == null || !m_docStatus.equals(statusApproved)) {
					processMsg = doc.getProcessMsg();
					FDialog.error(gridTab.getWindowNo(), this, processMsg);
					return;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			if (processMsg != null && !processMsg.equals(""))
				FDialog.error(gridTab.getWindowNo(), this, processMsg);
		}
		if (!po.save())
		{
			success = false;
			processMsg = "SaveError";
		}
		if (!success)
		{
			if (processMsg == null || processMsg.length() == 0)
			{
				if (doc != null)	//	problem: status will be rolled back
					processMsg += " - DocStatus=" + doc.getDocStatus();
			}
		}
		gridTab.dataRefresh();
	}

	private void readReference()
	{
		ArrayList<String> v_value = new ArrayList<String>();
    	ArrayList<String> v_name = new ArrayList<String>();
    		
    	DocumentEngine.readReferenceListSign(v_value, v_name);

    	int size = v_value.size();
		s_value = new String[size];
		s_name = new String[size];

		for (int i = 0; i < size; i++)
		{
			s_value[i] = (String)v_value.get(i);
			s_name[i] = (String)v_name.get(i);
		}
	}   //  readReference

	private String getSelectedItemValue() {
		if(lstSignStatus.getSelectedItem() != null)
		{
			return (lstSignStatus.getSelectedItem().getValue()).toString();
		}
		return null;
	}
	
	private String getSelectedItemLabel() {
		if(lstSignStatus.getSelectedItem() != null)
		{
			return (lstSignStatus.getSelectedItem().getLabel()).toString();
		}
		return null;
	}
}
