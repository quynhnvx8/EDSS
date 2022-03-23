
package eone.webui.panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.South;

import eone.base.model.I_AD_Field;
import eone.base.model.MField;
import eone.base.model.Query;
import eone.exceptions.DBException;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Msg;
import eone.util.NamePair;
import eone.webui.ClientInfo;
import eone.webui.LayoutUtils;
import eone.webui.adwindow.GridView;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Label;
import eone.webui.component.ListCell;
import eone.webui.component.ListHead;
import eone.webui.component.ListHeader;
import eone.webui.component.ListItem;
import eone.webui.component.Listbox;
import eone.webui.component.Panel;
import eone.webui.component.SimpleListModel;
import eone.webui.component.Textbox;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;

/**
 * 
 * @author hengsin
 *
 */
public class CustomizeGridViewPanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6200912526954948898L;

	ArrayList<Integer> tableSeqs;
	GridView gridPanel = null;

	
	public CustomizeGridViewPanel(int WindowNo, int AD_Tab_ID,ArrayList<Integer> gridFieldIds)
	{
		m_WindowNo = WindowNo;

		m_AD_Tab_ID = AD_Tab_ID;
		tableSeqs = gridFieldIds;
		this.setStyle("position : relative;height: 100%; width:100%; margin: none; border: none; padding: none;");
	}	//	

	/**	Logger			*/
	protected static final CLogger log = CLogger.getCLogger(CustomizeGridViewPanel.class);
	private int			m_WindowNo;
	private int			m_AD_Tab_ID;

	private Label yesLabel = new Label();
	
	SimpleListModel yesModel = new SimpleListModel();
	Listbox yesList = new Listbox();

	private boolean uiCreated;
	private boolean m_saved = false;
	private ConfirmPanel confirmPanel = new ConfirmPanel(true, false, true, false, false, false);
	
/**
	 * Static Layout
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		Borderlayout layout = new Borderlayout();
		layout.setStyle("position: absolute; width: 100%; height: 100%; border: none; margin: none; padding: none;");
		Center center = new Center();

		yesLabel.setValue(Msg.getMsg(Env.getCtx(), "ColumnName"));

		ZKUpdateUtil.setVflex(yesList, true);
		
		EventListener<Event> mouseListener = new EventListener<Event>()
		{

			@Override
			public void onEvent(Event event) throws Exception
			{
				if (Events.ON_DOUBLE_CLICK.equals(event.getName()))
				{
					migrateValueAcrossLists(event);
				}
			}
		};
		yesList.addDoubleClickListener(mouseListener);
		
		yesModel.setMultiple(true);
		yesList.setCheckmark(true);
		yesList.setMultiple(true);

		
		EventListener<Event> crossListMouseListener = new DragListener();
		yesList.addOnDropListener(crossListMouseListener);
		if (!ClientInfo.isMobile()) 
		{
			yesList.setItemDraggable(true);
		}

		
		ListHead listHead = new ListHead();
		listHead.setParent(yesList);
		ListHeader listHeader = new ListHeader();
		listHeader.appendChild(yesLabel);
		listHeader.setParent(listHead);
		
		Hlayout hlayout = new Hlayout();

		ZKUpdateUtil.setVflex(hlayout, "true");
		ZKUpdateUtil.setHflex(hlayout, "true");
		hlayout.setStyle("margin: auto; margin-top: 2px;");


		ZKUpdateUtil.setVflex(yesList, true);
		ZKUpdateUtil.setHflex(yesList, "1");
		hlayout.appendChild(yesList);

		center.appendChild(hlayout);

		layout.appendChild(center);

		South south = new South();
		south.setStyle("border: none; margin: 0; padding: 0; ");
		Panel southPanel = new Panel();

		
		LayoutUtils.addSclass("dialog-footer", confirmPanel);

		EventListener<Event> onClickListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getTarget().equals(
						confirmPanel.getButton(ConfirmPanel.A_OK))) {
					saveData();
				} else if (event.getTarget().equals(
						confirmPanel.getButton(ConfirmPanel.A_CANCEL))) {
					getParent().detach();
				} else if (event.getTarget().equals(confirmPanel.getButton(ConfirmPanel.A_RESET))) {
					tableSeqs.clear();
					loadData();
				}
			}

		};

		confirmPanel.addActionListener(onClickListener);
		southPanel.appendChild(confirmPanel);
		south.appendChild(southPanel);
		layout.appendChild(south);

		this.appendChild(layout);
		
	}	//	init
	
	public void loadData()
	{
		yesModel.removeAllElements();
		boolean baseLanguage = Env.isBaseLanguage(Env.getCtx(), "AD_Field");
		Query query = null;
		
		query = new Query(Env.getCtx(), I_AD_Field.Table_Name, "AD_Tab_ID=? AND IsDisplayed='Y' AND IsActive='Y'", null);
		query.setOrderBy("SeqNo, Name");
		query.setParameters(new Object [] {m_AD_Tab_ID});
		query.setApplyAccessFilter(true);

		try
		{
			List<MField> lsFieldsOfGrid = query.list();
			HashMap<Integer, ListElement> curTabSel = new HashMap<Integer, CustomizeGridViewPanel.ListElement>();
			
			ListCell cell = null;
			ListItem item = null;
			for (MField field : lsFieldsOfGrid)
			{
				item = new  ListItem();
				
				
				int key = field.get_ID();
				String name = null; 
				if (baseLanguage)
					name = field.getName();
				else
					name = field.get_Translation(I_AD_Field.COLUMNNAME_Name);
				
				ListElement pp = new ListElement(key, name, field.getDisplayLength());
				
				item.appendChild(new ListCell(name));
				cell = new ListCell();
				cell.appendChild(new Textbox(field.getDisplayLength() + ""));
				item.appendChild(cell);
				//items.add(item);
				yesList.appendChild(item);
				
				if (tableSeqs != null && tableSeqs.size() > 0 ) {
					if (tableSeqs.contains(key)) {
						curTabSel.put(key, pp);
					}
				} 
			}
			if(tableSeqs!=null){
				for(int key:tableSeqs){
					if(curTabSel.get(key)!=null){
						yesModel.addElement(curTabSel.get(key));
					}
				}
			}
		}
		catch (DBException e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		
		yesList.setEnabled(true);
		
		
	}	//	loadData

	/**
	 * @param event
	 */
	void migrateValueAcrossLists (Event event)
	{
		Object source = event.getTarget();
		if (source instanceof ListItem) {
			source = ((ListItem)source).getListbox();
		}
	}

	void migrateLists (Listbox listFrom , Listbox listTo , int endIndex	)
	{
		int index = 0;
		SimpleListModel lmFrom = (SimpleListModel) listFrom.getModel();
		SimpleListModel lmTo = (SimpleListModel) listTo.getModel();		
		Set<?> selectedItems = listFrom.getSelectedItems();
		List<ListElement> selObjects = new ArrayList<ListElement>();
		for (Object obj : selectedItems) {
			ListItem listItem = (ListItem) obj;
			index = listFrom.getIndexOfItem(listItem);
			ListElement selObject = (ListElement)lmFrom.getElementAt(index);
			selObjects.add(selObject);
		}
		for (ListElement selObject : selObjects)
		{
			if (selObject == null || !selObject.isUpdateable())
				continue;

			lmFrom.removeElement(selObject);
			lmTo.add(endIndex, selObject);
			endIndex++;			
			index = lmTo.indexOf(selObject);
			listTo.setSelectedIndex(index);
		}
	}
	
	/**
	 * 	Move within Yes List with Drag Event and Multiple Choice
	 *	@param event event
	 */
	void migrateValueWithinYesList (int endIndex, List<ListElement> selObjects)
	{
		int iniIndex =0;
		Arrays.sort(selObjects.toArray());	
		ListElement endObject = (ListElement)yesModel.getElementAt(endIndex);
		for (ListElement selected : selObjects) {
			iniIndex = yesModel.indexOf(selected);
			ListElement selObject = (ListElement)yesModel.getElementAt(iniIndex);
			yesModel.removeElement(selObject);
			endIndex = yesModel.indexOf(endObject);
			yesModel.add(endIndex, selObject);
		}		
	}

	/**
	 * 	Move within Yes List
	 *	@param event event
	 */
	void migrateValueWithinYesList (Event event)
	{
		Object[] selObjects = yesList.getSelectedItems().toArray();
		
		if (selObjects == null)
			return;
		int length = selObjects.length;
		if (length == 0)
			return;
		//
		int[] indices = yesList.getSelectedIndices();
		Arrays.sort(indices);
		//
		boolean change = false;
		
		//
		if (change) {
			yesList.setSelectedIndices(indices);
			if ( yesList.getSelectedItem() != null)
			{
				AuFocus focus = new AuFocus(yesList.getSelectedItem());
				Clients.response(focus);
			}
		}
	}	//	migrateValueWithinYesList

	public void saveData()
	{
		log.fine("");

		List<List<Object>> paramsList = new ArrayList<List<Object>>();
		List<Object> params = null;
		
		String ok = "";
		
		
		List<Listitem> items = yesList.getItems();
		
		for (int i = 0; i < items.size(); i++)
		{
			params = new ArrayList<Object>();
			
			Listitem item = items.get(i);
			List<Component> com = item.getChildren();
			for (Component compCell : com) {
				Listcell list = (Listcell) compCell;
				if (list.getFirstChild() instanceof Textbox) {
					
					Textbox text = (Textbox) list.getFirstChild();
					String width = text.getValue();
					if (text != null && !"".equals(text.getValue()))
						params.add(Integer.parseInt(width));
					else
						params.add(0);
				}
			}
			
			ListElement pp = (ListElement)yesModel.getElementAt(i);
			if (pp != null)
				params.add(pp.getKey());
			else
				params.add(0);
			
			paramsList.add(params);
		}

		String sqlUpdate = "UPDATE AD_FIELD SET DisplayLength = ? WHERE AD_FIELD_ID = ?";
		
		ok = MField.updateWidthDisplay(sqlUpdate, paramsList, null);
		if(!"".equals(ok)) {
			m_saved = true;
			getParent().detach();
			if(gridPanel!=null){
				Events.postEvent("onCustomizeGrid", gridPanel, null);
			}
		} else {
			FDialog.error(m_WindowNo, null, "SaveError", "Error!");
		}
	}	//	saveData

	
	private static class ListElement extends NamePair {
		
		private static final long serialVersionUID = -1717531470895073281L;

		private int		m_key;

		private boolean	m_updateable;

		public ListElement(int key, String name) {
			super(name);
			this.m_key = key;
			this.m_updateable = true;
		}
		
		
		public ListElement(int key, String name, int width) {
			super(name);
			this.m_key = key;
			this.m_updateable = true;
		}
		
		public int getKey() {
			return m_key;
		}

		public boolean isUpdateable() {
			return m_updateable;
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
		public boolean equals(Object obj)
		{
			if (obj instanceof ListElement)
			{
				ListElement li = (ListElement)obj;
				return
					li.getKey() == m_key
					&& li.getName() != null
					&& li.getName().equals(getName());
			}
			return false;
		}	//	equals

		@Override
		public String toString() {
			String s = super.toString();
			if (s == null || s.trim().length() == 0)
				s = "<" + getKey() + ">";
			return s;
		}

	}
		/**
	 * @author eslatis
	 *
	 */
	private class DragListener implements EventListener<Event>
	{

		
		public DragListener()
		{
		}

		@Override
		public void onEvent(Event event) throws Exception {
			if (event instanceof DropEvent)
			{	
				int endIndex = 0;
				DropEvent me = (DropEvent) event;
				ListItem endItem = (ListItem) me.getTarget();
				ListItem startItem = (ListItem) me.getDragged();
				
				if (!startItem.isSelected())
					startItem.setSelected(true);
					
				if (!(startItem.getListbox() == endItem.getListbox()))
				{			
					Listbox listFrom = (Listbox)startItem.getListbox();
					Listbox listTo =  (Listbox)endItem.getListbox();		
					endIndex = yesList.getIndexOfItem(endItem);
					migrateLists (listFrom,listTo,endIndex);
				}else if (startItem.getListbox() == endItem.getListbox() && startItem.getListbox() == yesList)
				{  
					List<ListElement> selObjects = new ArrayList<ListElement>();
					endIndex = yesList.getIndexOfItem(endItem);	
					for (Object obj : yesList.getSelectedItems()) {
						ListItem listItem = (ListItem) obj;
						int index = yesList.getIndexOfItem(listItem);
						ListElement selObject = (ListElement)yesModel.getElementAt(index);				
						selObjects.add(selObject);						
					}
					migrateValueWithinYesList (endIndex, selObjects);
					yesList.clearSelection();
				}
			}
		}
	}

	public void activate(boolean b) {
		if (b && !uiCreated) createUI();
	}

	public void createUI() {
		if (uiCreated) return;
		try
		{
			init();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		uiCreated = true;
	}

	public void query() {
		loadData();
	}

	public boolean isSaved() {
		return m_saved;
	}

	public void setGridPanel(GridView gridPanel){
		this.gridPanel = gridPanel;
	}

}	//CustomizeGridViewPanel

