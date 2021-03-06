
package eone.webui.desktop;

import static eone.base.model.SystemIDs.TREE_MENUPRIMARY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.service.event.EventHandler;
import org.zkoss.zhtml.Style;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.metainfo.PageDefinition;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zul.Anchorchildren;
import org.zkoss.zul.Anchorlayout;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;

import eone.base.event.EventManager;
import eone.base.event.IEventManager;
import eone.base.event.IEventTopics;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.I_AD_Preference;
import eone.base.model.MBroadcastMessage;
import eone.base.model.MMenu;
import eone.base.model.MPreference;
import eone.base.model.MQuery;
import eone.base.model.MTable;
import eone.base.model.MUser;
import eone.base.model.Query;
import eone.broadcast.BroadCastMsg;
import eone.broadcast.BroadCastUtil;
import eone.broadcast.BroadcastMsgUtil;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;
import eone.util.Util;
import eone.util.WebUtil;
import eone.webui.ClientInfo;
import eone.webui.LayoutUtils;
import eone.webui.adwindow.ADWindow;
import eone.webui.apps.AEnv;
import eone.webui.apps.BusyDialog;
import eone.webui.apps.GlobalSearch;
import eone.webui.apps.MenuSearchController;
import eone.webui.apps.ProcessDialog;
import eone.webui.component.Label;
import eone.webui.component.Tab;
import eone.webui.component.Tabpanel;
import eone.webui.component.ToolBarButton;
import eone.webui.component.Window;
import eone.webui.event.DrillEvent;
import eone.webui.event.MenuListener;
import eone.webui.event.ZKBroadCastManager;
import eone.webui.event.ZoomEvent;
import eone.webui.panel.ADForm;
import eone.webui.panel.BroadcastMessageWindow;
import eone.webui.panel.HeaderPanel;
import eone.webui.panel.MenuTreePanel;
import eone.webui.panel.TimeoutPanel;
import eone.webui.session.SessionManager;
import eone.webui.theme.ThemeManager;
import eone.webui.util.UserPreference;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.AboutWindow;

/**
 *
 * @author Admin
 */
public class DefaultDesktop extends TabbedDesktop implements MenuListener, Serializable, EventListener<Event>, EventHandler, DesktopCleanup
{
	//private static final String POPUP_OPEN_ATTR = "popup.open";

	private static final String HOME_TAB_RENDER_ATTR = "homeTab.render";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7189914859100400758L;

	@SuppressWarnings("unused")
	private static final CLogger logger = CLogger.getCLogger(DefaultDesktop.class);

	private Borderlayout layout;

	private int noOfNotice;

	private int noOfRequest;


	private int noOfUnprocessed;

	private Tabpanel homeTab;

	private DashboardController dashboardController, sideController;
	
	private HeaderPanel pnlHead;
	
	private Desktop m_desktop = null;
	
	private ToolBarButton max;
	
	private ToolBarButton showHeader;

	private Component headerContainer;

	private Window headerPopup;

	private Image logoMobile;
	private Image logoDesktop;
	
	private Div menuLookupL;
	
    public DefaultDesktop()
    {
    	super();
    	dashboardController = new DashboardController();
    	sideController = new DashboardController();
    	
    	m_desktop = AEnv.getDesktop();
    	m_desktop.addListener(this);
    	//subscribing to broadcast event
    	bindEventManager();
    	try {
    		ZKBroadCastManager.getBroadCastMgr();
    	} catch (Throwable e) {
    		e.printStackTrace();
    	}
    	
    	EventQueue<Event> queue = EventQueues.lookup(ACTIVITIES_EVENT_QUEUE, true);
    	queue.subscribe(this);
    }

	protected Component doCreatePart(Component parent)
    {
    	PageDefinition pagedef = Executions.getCurrent().getPageDefinition(ThemeManager.getThemeResource("zul/desktop/desktop.zul"));
    	Component page = Executions.createComponents(pagedef, parent, null);
    	layout = (Borderlayout) page.getFellow("layout");
    	headerContainer = page.getFellow("northBody");
    	pnlHead = (HeaderPanel) headerContainer.getFellow("header");
        
        layout.addEventListener("onZoom", this);
        layout.addEventListener(DrillEvent.ON_DRILL_DOWN, this);
        
        West w = layout.getWest();
        //w.setTitle("Main Menu");//Main Menu trai
        w.setOpen(true);
        ZKUpdateUtil.setWidth(w, "20%");
        Style style = new Style();
		style.setContent(".z-anchorlayout-body { overflow:auto } .z-anchorchildren { overflow:visible } ");
		style.setPage(w.getPage());
       
		if( !ClientInfo.isMobile())
    	{
			Anchorlayout anchorlayout = new Anchorlayout();	        
	        anchorlayout.setSclass("dashboard-layout slimScroll");
	        anchorlayout.setVflex("1");
	        anchorlayout.setHflex("1");
	        
	        w.appendChild(anchorlayout);
	        
	        
	    	
	    	//Menu search
	        Anchorchildren searchMenu = new Anchorchildren();
	        anchorlayout.appendChild(searchMenu);
	        
	        MenuTreePanel menuTreePanel = new MenuTreePanel(searchMenu);
	        menuLookupL = new Div();
	        menuLookupL.setId("menuLookupL");
	        menuLookupL.addEventListener(Events.ON_CTRL_KEY, this);
	        
	        searchMenu.appendChild(menuLookupL);
	        
	        GlobalSearch globalSearch = new GlobalSearch(new MenuSearchController(menuTreePanel.getMenuTree()));
	        menuLookupL.getParent().insertBefore(globalSearch, menuLookupL);
	        menuLookupL.detach();	
	        
	        Anchorchildren menuTree = new Anchorchildren();
	        menuTree.setAnchor("100% 95%");
	        anchorlayout.appendChild(menuTree);
	        menuTreePanel.setBorder(true);   
	        menuTree.appendChild(menuTreePanel);
	        //menuTreePanel.setClass("z-panel-treemenu");
	        menuTreePanel.setId(UUID.randomUUID().toString());
	        menuTreePanel.setVisible(true);
	        
    	}
        
        Center windowArea = layout.getCenter();
        //windowArea.setZclass("xxxx");
        
        windowContainer.createPart(windowArea);

        homeTab = new Tabpanel();
        windowContainer.addWindow(homeTab, Util.cleanAmp(Msg.getMsg(Env.getCtx(), "Home")), false, null);
        homeTab.getLinkedTab().setSclass("desktop-hometab");
        ((Tab)homeTab.getLinkedTab()).setDisableDraggDrop(true);
        homeTab.setSclass("desktop-home-tabpanel");
        BusyDialog busyDialog = new BusyDialog();
        busyDialog.setShadow(false);
        homeTab.appendChild(busyDialog);
        
        // register as 0
        registerWindow(homeTab);
        
        BroadcastMessageWindow messageWindow = new BroadcastMessageWindow(pnlHead);
        BroadcastMsgUtil.showPendingMessage(Env.getAD_User_ID(Env.getCtx()), messageWindow);
        
        if (!layout.getDesktop().isServerPushEnabled())
    	{
    		layout.getDesktop().enableServerPush(true);
    	}

        Executions.schedule(layout.getDesktop(), event -> {
        	renderHomeTab();
        	//automaticOpen(Env.getCtx());
        }, new Event("onRenderHomeTab"));        

		
        if (!layout.getDesktop().isServerPushEnabled())
    	{
    		layout.getDesktop().enableServerPush(true);
    	}
       
        return layout;
    }

	private Popup popup;
	protected void openUserPanelPopup() {
		if (popup != null) {
			Object value = popup.removeAttribute(popup.getUuid());
			if (value != null && value instanceof Long) {
				long ts = ((Long)value).longValue();
				long since = System.currentTimeMillis() - ts;
				if (since < 500) {
					popup.detach();
					popup = null;
					return;
				}
			}
			popup.detach();
		}
		popup = new Popup();
		popup.setSclass("user-panel-popup");
		Vlayout layout = new Vlayout();
		layout.appendChild(new Label(getUserName()));
		layout.appendChild(new Label(getRoleName()));
		//layout.appendChild(new Label(getClientName() + "." + getOrgName()));
		
		layout.appendChild(new Space());
		//layout.appendChild(userPanelLinksContainer);
		
		popup.appendChild(layout);
		popup.setPage(lblUser.getPage());
		popup.setVflex("min");
		popup.setHflex("min");
		popup.setStyle("max-width: " + ClientInfo.get().desktopWidth + "px");
		popup.addEventListener(Events.ON_OPEN, (OpenEvent oe) -> {
			if (!oe.isOpen())
				popup.setAttribute(popup.getUuid(), System.currentTimeMillis());
		});
		popup.open(lblUser, "after_start");		
		
	}

	protected void updateSideControllerWidthPreference(String width) {

    	if( width != null ){
        	Query query = new Query(Env.getCtx(), 
        			MTable.get(Env.getCtx(), I_AD_Preference.Table_ID), 
        			" Attribute=? AND AD_User_ID=? AND AD_Process_ID IS NULL AND PreferenceFor = 'W'",
        			null);

        	int userId = Env.getAD_User_ID(Env.getCtx());
        	MPreference preference = query.setOnlyActiveRecords(true)
        			.setApplyAccessFilter(true)
        			.setParameters("SideController.Width", userId)
        			.first();
        	
        	if ( preference == null || preference.getAD_Preference_ID() <= 0 ) {
        		
        		preference = new MPreference(Env.getCtx(), 0, null);
        		preference.set_ValueOfColumn("AD_User_ID", userId); // required set_Value for System=0 user
        		preference.setAttribute("SideController.Width");
        	}
        	preference.setValue(width);
        	preference.saveEx();

    	}
		
	}

	
	
	private void updateHeaderCollapsedPreference(boolean collapsed) {
		UserPreference pref = SessionManager.getSessionApplication().getUserPreference();
		pref.setProperty(UserPreference.P_HEADER_COLLAPSED, collapsed);
		pref.savePreference();
	}
	
	public void renderHomeTab()
	{		
		homeTab.getChildren().clear();		

		dashboardController.render(homeTab, this);
		
		homeTab.setAttribute(HOME_TAB_RENDER_ATTR, Boolean.TRUE);
	
		
		//Component side = null;
		West w = layout.getWest();
		if (ClientInfo.isMobile())
		{
			w.setVisible(false);
		}
		
		logoMobile = pnlHead.getLogo();
		//side = logo;
		if (ClientInfo.isMobile() && logoMobile != null)
		{
			Anchorchildren ac = new Anchorchildren();
			ac.appendChild(logoMobile);
			ac.setStyle("padding: 4px;");
			Anchorlayout layout = new Anchorlayout(); //(Anchorlayout) side.getFirstChild();
			layout.insertBefore(ac, layout.getFirstChild());
		}
		
		if (ClientInfo.isMobile())
		{
			pnlHead.invalidate();
		}
	}

	protected void setSidePopupWidth(Popup popup) {
		if (ClientInfo.minWidth(ClientInfo.LARGE_WIDTH))
			popup.setWidth("30%");
		else if (ClientInfo.minWidth(ClientInfo.MEDIUM_WIDTH))
			popup.setWidth("40%");
		else if (ClientInfo.minWidth(ClientInfo.SMALL_WIDTH))
			popup.setWidth("50%");
		else if (ClientInfo.minWidth(ClientInfo.EXTRA_SMALL_WIDTH))
			popup.setWidth("60%");
		else if (ClientInfo.minWidth(400))
			popup.setWidth("70%");
		else
			popup.setWidth("80%");
	}

	public void onEvent(Event event)
    {
        Component comp = event.getTarget();
        String eventName = event.getName();

        if(eventName.equals(Events.ON_CLICK))
        {
        	if(comp == logoDesktop)
			{
        		AEnv.showWindow(new AboutWindow());
			}
        	
        	
         	
        	if (comp == max)
        	{
        		if (layout.getNorth().isVisible())
        		{
        			collapseHeader();
        		}
        		else
        		{
        			restoreHeader();
        		}
        	}
        	else if (comp == showHeader)
        	{        		
    			showHeader.setPressed(true);
    			if (pnlHead.getParent() != headerPopup)
    				headerPopup.appendChild(pnlHead);        			
    			LayoutUtils.openPopupWindow(showHeader, headerPopup, "after_start");        			
        	}
        	/*
        	else if (event.getTarget() instanceof Menuitem)
    		{
    			Menuitem mi = (Menuitem) event.getTarget();
    			if ("changeRoleDesktop".equals(mi.getId())) 
    			{
    				MUser user = MUser.get(Env.getCtx());
    				Clients.confirmClose(null);
    				SessionManager.changeRole(user);
    			}
    			else if ("logoutDesktop".equals(mi.getId()))
    			{
    				Clients.confirmClose(null);
    				SessionManager.logoutSession();
    			}
    		}
    		*/
        	else if(comp instanceof ToolBarButton)
            {
            	ToolBarButton btn = (ToolBarButton) comp;

            	if (btn.getAttribute("AD_Menu_ID") != null)
            	{
	            	int menuId = (Integer)btn.getAttribute("AD_Menu_ID");
	            	if(menuId > 0) onMenuSelected(menuId);
            	}
            }
        }
        else if (eventName.equals(ON_ACTIVITIES_CHANGED_EVENT))
        {
        	@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) event.getData();
        	Integer notice = (Integer) map.get("notice");
        	Integer request = (Integer) map.get("request");
        	Integer unprocessed = (Integer) map.get("unprocessed");
        	boolean change = false;
        	if (notice != null && notice.intValue() != noOfNotice) 
        	{
        		noOfNotice = notice.intValue(); change = true;
        	}
        	if (request != null && request.intValue() != noOfRequest) 
        	{
        		noOfRequest = request.intValue(); change = true;
        	}		
        	
        	if (unprocessed != null && unprocessed.intValue() != noOfUnprocessed) 
        	{
        		noOfUnprocessed = unprocessed.intValue(); change = true;
        	}
        	if (change)
        		updateUI();
        }
        else if (event instanceof ZoomEvent) 
		{
        	Clients.clearBusy();
			ZoomEvent ze = (ZoomEvent) event;
			if (ze.getData() != null && ze.getData() instanceof MQuery) {
				AEnv.zoom((MQuery) ze.getData());
			}
		}
		
       
    }
	
	protected void collapseHeader() {
		layout.getNorth().setVisible(false);
		showHeader.setVisible(true);
		pnlHead.detach();
		if (headerPopup == null) 
		{
			headerPopup = new Window(); 
			headerPopup.setSclass("desktop-header-popup");
			ZKUpdateUtil.setVflex(headerPopup, "true");
			headerPopup.setVisible(false);
			headerPopup.addEventListener(Events.ON_OPEN, new EventListener<OpenEvent>() {
				@Override
				public void onEvent(OpenEvent event) throws Exception {
					if (!event.isOpen()) {
						if (showHeader.isPressed())
							showHeader.setPressed(false);
					}
				}
			});            			
		}
		headerPopup.appendChild(pnlHead);
		updateHeaderCollapsedPreference(true);
	}

	
	protected void restoreHeader() {
		layout.getNorth().setVisible(true);
		//max.setImage(ThemeManager.getThemeResource(IMAGES_UPARROW_PNG));
		showHeader.setVisible(false);
		pnlHead.detach();
		headerContainer.appendChild(pnlHead);
		Clients.resize(pnlHead);
		updateHeaderCollapsedPreference(false);
	}
	
   	
	/**
	 *
	 * @param page
	 */
	public void setPage(Page page) {
		if (this.page != page) {
			layout.setPage(page);
			this.page = page;
			
			if (dashboardController != null) {
				dashboardController.onSetPage(page, layout.getDesktop());
			}
			
			if (sideController != null) {
				sideController.onSetPage(page, layout.getDesktop());
			}
		}
	}

	/**
	 * Get the root component
	 * @return Component
	 */
	public Component getComponent() {
		return layout;
	}

	public void logout() {
		unbindEventManager();
		if (dashboardController != null) {
			dashboardController.onLogOut();
			dashboardController = null;
		}
		
		if (sideController != null) {
			sideController.onLogOut();
			sideController = null;
		}
		layout.detach();
		layout = null;
		pnlHead = null;
		//max = null;
		m_desktop = null;
	}

	public void updateUI() {
		int total = noOfNotice + noOfRequest + noOfUnprocessed;
		windowContainer.setTabTitle(0, Util.cleanAmp(Msg.getMsg(Env.getCtx(), "Home"))
				+ " (" + total + ")",
				Msg.translate(Env.getCtx(), "AD_Note_ID") + " : " + noOfNotice
				+ ", " + Msg.translate(Env.getCtx(), "R_Request_ID") + " : " + noOfRequest
				+ (noOfUnprocessed>0 ? ", " + Msg.getMsg (Env.getCtx(), "UnprocessedDocs") + " : " + noOfUnprocessed : "")
				);
	}

	//use _docClick undocumented api. need verification after major zk release update
	private final static String autoHideMenuScript = "try{var w=zk.Widget.$('#{0}');var t=zk.Widget.$('#{1}');" +
			"var e=new Object;e.target=t;w._docClick(e);}catch(error){}";
	
	private void autoHideMenu() {
			
		if (layout.getWest().isCollapsible() && !layout.getWest().isOpen())
		{
			String id = layout.getWest().getUuid();
			Tab tab = windowContainer.getSelectedTab();
			if (tab != null) {
				String tabId = tab.getUuid();
				String script = autoHideMenuScript.replace("{0}", id);
				script = script.replace("{1}", tabId);
				AuScript aus = new AuScript(layout.getWest(), script);
				Clients.response("autoHideWest", aus);
			}			
		}
	}

	@Override
	protected void preOpenNewTab()
	{
		autoHideMenu();
	}

	//Implementation for Broadcast message
	/**
	 * @param eventManager
	 */
	public void bindEventManager() {
		EventManager.getInstance().register(IEventTopics.BROADCAST_MESSAGE, this);
	}

	/**
	 * @param eventManager
	 */
	public void unbindEventManager() {
		EventManager.getInstance().unregister(this);
	}
	
	@Override
	public void handleEvent(final org.osgi.service.event.Event event) {
		String eventName = event.getTopic();
		if (eventName.equals(IEventTopics.BROADCAST_MESSAGE)) {
			EventListener<Event> listner = new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					BroadCastMsg msg = (BroadCastMsg) event.getData();
					

					switch (msg.getEventId()) {
					case BroadCastUtil.EVENT_TEST_BROADCAST_MESSAGE:
						MBroadcastMessage mbMessage = MBroadcastMessage.get(
								Env.getCtx(), msg.getIntData());
						String currSession = Integer
								.toString(Env.getContextAsInt(Env.getCtx(),
										"AD_Session_ID"));
						if (currSession.equals(msg.getTarget())) {
							BroadcastMessageWindow testMessageWindow = new BroadcastMessageWindow(
										pnlHead);
							testMessageWindow.appendMessage(mbMessage, true);
							testMessageWindow = null;

						}
						break;
					case BroadCastUtil.EVENT_BROADCAST_MESSAGE:
						mbMessage = MBroadcastMessage.get(
								Env.getCtx(), msg.getIntData());
						if (mbMessage.isValidUserforMessage()) {
							
							BroadcastMessageWindow messageWindow = new BroadcastMessageWindow(
										pnlHead);
							messageWindow.appendMessage(mbMessage, false);
						}
						break;
					case BroadCastUtil.EVENT_SESSION_TIMEOUT:

						currSession = Integer.toString(Env.getContextAsInt(
								Env.getCtx(), "AD_Session_ID"));
						if (currSession.equalsIgnoreCase(msg.getTarget())) {
							new TimeoutPanel(pnlHead, msg.getIntData());
						}

						break;
					case BroadCastUtil.EVENT_SESSION_ONNODE_TIMEOUT:

						currSession = WebUtil.getServerName();

						if (currSession.equalsIgnoreCase(msg.getTarget())) {
							new TimeoutPanel(pnlHead, msg.getIntData());
						}

					}

				}

			};

			Executions.schedule(m_desktop, listner, new Event("OnBroadcast",
					null, event.getProperty(IEventManager.EVENT_DATA)));

		}
	}

	@Override
	public void cleanup(Desktop desktop) throws Exception {
		unbindEventManager();
	}

	//@Override
	public void updateHelpContext(String ctxType, int recordId) {
		
	}

	@Override
	public void updateHelpTooltip(GridField gridField) {
	}

	@Override
	public void updateHelpTooltip(String hdr, String  desc, String help, String otherContent) {		
	}

	@Override
	public void updateHelpQuickInfo(GridTab gridTab) {
	}

	@Override
	public ProcessDialog openProcessDialog(int processId, boolean soTrx) {
		ProcessDialog pd = super.openProcessDialog(processId, soTrx);
		return pd;
	}

	@Override
	public ADForm openForm(int formId) {
		ADForm form = super.openForm(formId);
		return form;
	}

	@Override
	public void openInfo(int infoId) {
		super.openInfo(infoId);
	}

	

	@Override
	public void openTask(int taskId) {
		super.openTask(taskId);
	}

    public boolean isPendingWindow() {
        List<Object> windows = getWindows();
        if (windows != null) {
        	for (int idx = 0; idx < windows.size(); idx++) {
                Object ad = windows.get(idx);
                if (ad != null && ad instanceof ADWindow && ((ADWindow)ad).getADWindowContent() != null) {
                	if ( ((ADWindow)ad).getADWindowContent().isPendingChanges()) {
                		return true;
                	}
                }
        	}
        }
        return false;
    }

	@Override
	public void onMenuSelected(int menuId) {
		//System.out.println("-------------------OPEN WINDOW----------------------------");
		super.onMenuSelected(menuId);
		//comment theo doi phan Response
		/*TODO COMMENT 05/05/2021
		if (showHeader != null && showHeader.isVisible()) {
			//ensure header popup is close
			String script = "var w=zk.Widget.$('#" + layout.getUuid()+"'); " +
					"zWatch.fire('onFloatUp', w);";
			Clients.response(new AuScript(script));
		} 
		*/
	}

	int getMenuID()
	{
		/*
		int AD_Role_ID = Env.getAD_Role_ID(Env.getCtx());
		int AD_Tree_ID = DB.getSQLValue(null,
				"SELECT COALESCE(r.AD_Tree_Menu_ID, ci.AD_Tree_Menu_ID)" 
						+ "FROM AD_ClientInfo ci" 
						+ " INNER JOIN AD_Role r ON (ci.AD_Client_ID=r.AD_Client_ID) "
						+ "WHERE AD_Role_ID=?", AD_Role_ID);
		*/
		int AD_Tree_ID = 0;
		if (AD_Tree_ID <= 0)
			AD_Tree_ID = TREE_MENUPRIMARY;	//	Menu

		return AD_Tree_ID;
	}
	
	@Override
	public void setClientInfo(ClientInfo clientInfo) {
		super.setClientInfo(clientInfo);
		if (clientInfo.tablet) {
			if (homeTab != null && homeTab.getAttribute(HOME_TAB_RENDER_ATTR) != null) {
				dashboardController.updateLayout(clientInfo);
				//updateSideLayout();
			}
		}
	}
	/*
	private void updateSideLayout() {
		if (westPopup != null && westPopup.getChildren().size() > 1)
			setSidePopupWidth(westPopup);
		
	}  
	*/
    private boolean isActionURL() {
		ConcurrentMap<String, String[]> parameters = new ConcurrentHashMap<String, String[]>(Executions.getCurrent().getParameterMap());
    	String action = "";
    	if (parameters != null) {
        	String[] strs = parameters.get("Action");
        	if (strs != null && strs.length == 1 && strs[0] != null)
        		action = strs[0];
    	}
		return ! Util.isEmpty(action);
    }


	//Logout
	protected ToolBarButton lblUser = new ToolBarButton();

    private String getUserName()
    {
        MUser user = MUser.get(Env.getCtx());
        return user.getName();
    }

    private String getRoleName()
    {
        //MRole role = MRole.getDefault(Env.getCtx(), false);
        return "";//role.getName();
    }
    
}
