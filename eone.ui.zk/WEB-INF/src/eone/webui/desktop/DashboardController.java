package eone.webui.desktop;

import java.util.List;
import java.util.logging.Level;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Anchorchildren;
import org.zkoss.zul.Anchorlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Include;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Timer;

import eone.base.Service;
import eone.base.model.MSysConfig;
import eone.exceptions.EONEException;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Msg;
import eone.webui.ClientInfo;
import eone.webui.dashboard.DashboardPanel;
import eone.webui.dashboard.DashboardRunnable;
import eone.webui.factory.IDashboardGadgetFactory;
import eone.webui.util.ZKUpdateUtil;

/**
 * @author Client
 *
 */
public class DashboardController implements EventListener<Event> {

	private static final String PANEL_EMPTY_ATTR = "panel.empty";
	private final static CLogger logger = CLogger.getCLogger(DashboardController.class);

	private Anchorlayout dashboardLayout;
	private Anchorchildren maximizedHolder;	
	private DashboardRunnable dashboardRunnable;
	private Timer dashboardTimer;
	private int noOfCols;
	
	public DashboardController() {
		dashboardLayout = new Anchorlayout();
		dashboardLayout.setSclass("dashboard-layout");
        ZKUpdateUtil.setVflex(dashboardLayout, "1");
        ZKUpdateUtil.setHflex(dashboardLayout, "1");
        
        maximizedHolder = new Anchorchildren();                
        maximizedHolder.setAnchor("100% 100%");
        maximizedHolder.setStyle("overflow: hidden; border: none; margin: 0; padding: 0;");
	}
	
	public void render(Component parent, IDesktop desktopImpl) {
		render(parent, desktopImpl, false);
	}
	
	protected void render(Component parent, IDesktop desktopImpl, boolean update) {
		if (!update)
			parent.appendChild(dashboardLayout);
		if (!update)
			((HtmlBasedComponent)parent).setStyle("overflow-x: auto;");
		dashboardLayout.getChildren().clear();
        
        if (!dashboardLayout.getDesktop().isServerPushEnabled())
        	dashboardLayout.getDesktop().enableServerPush(true);
        
        if (!update)
        	dashboardRunnable = new DashboardRunnable(parent.getDesktop());
        
        
        int noOfCols = 1;

        try
		{
        	
        	this.noOfCols = noOfCols;
            
            int columnNo = 10;
        	int effColumn = columnNo;
        	if (effColumn+1 > noOfCols)
        		effColumn = noOfCols-1;
        	

        	Panel panel = new Panel();
        	Caption caption = new Caption(Msg.getMsg(Env.getAD_Language(Env.getCtx()), "Calendar"));
        	panel.appendChild(caption);
        	
        	//panel.addEventListener(Events.ON_MAXIMIZE, this);
        	
        	panel.setMaximizable(true);
        	panel.setMaximized(true);
        	
        	
        	panel.setBorder("normal");
        	
        	if (panel != null && panel.getAttribute(PANEL_EMPTY_ATTR) == null) {
        		dashboardLayout.appendChild(maximizedHolder);
	    		maximizedHolder.appendChild(panel);
	    		panel.setSclass("dashboard-widget dashboard-widget-max");
        	}
        		
        	if (!update) {
	            Panelchildren content = new Panelchildren();
	            panel.appendChild(content);

	            boolean panelEmpty = true;

	            panelEmpty = !render(content, dashboardRunnable);	            		
	        	
	        	if (panelEmpty) {
	        		panel.detach();
	        		panel.setAttribute(PANEL_EMPTY_ATTR, Boolean.TRUE);
	        	}
        	}
            
		}
        catch (Exception e)
        {
			logger.log(Level.WARNING, "Failed to create dashboard content", e);
		}
        //
           
        if (!update && !dashboardRunnable.isEmpty())
        {
        	dashboardRunnable.refreshDashboard(false);

			// default Update every one minutes
			int interval = MSysConfig.getIntValue(MSysConfig.ZK_DASHBOARD_REFRESH_INTERVAL, 60000);
			dashboardTimer = new Timer();
			dashboardTimer.setDelay(interval);
			dashboardTimer.setRepeats(true);
			dashboardTimer.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (dashboardRunnable != null && !dashboardRunnable.isEmpty()) {
						dashboardRunnable.run();
					}
				}
			});
			dashboardTimer.setPage(parent.getPage());
		}
	}

	public  boolean render(Component content, DashboardRunnable dashboardRunnable) throws Exception {
		boolean empty = true;
		
        // ZUL file url
    	String url = "/zul/calendar.zul";//dc.getZulFilePath();
    	if(url != null)
    	{
        	try {
        		
                Component component = null;
                List<IDashboardGadgetFactory> f = Service.locator().list(IDashboardGadgetFactory.class).getServices();
                for (IDashboardGadgetFactory factory : f) {
                        component = factory.getGadget(url.toString(),content);
                        if(component != null)
                                break;
                }
                
                if(component != null)
                {
                	if (component instanceof Include)
                		component = component.getFirstChild();
                	
                	if (component instanceof DashboardPanel)
                	{
	                	DashboardPanel dashboardPanel = (DashboardPanel) component;
	                	if (!dashboardPanel.getChildren().isEmpty()) {
	                		content.appendChild(dashboardPanel);
	                		if (dashboardRunnable != null)
	                			dashboardRunnable.add(dashboardPanel);
	                		empty = false;
	                	}
                	}
                	else
                	{
                		content.appendChild(component);
                		empty = false;
                	}
                }
			} catch (Exception e) {
				logger.log(Level.WARNING, "Failed to create components. zul="+url, e);
				throw new EONEException(e);
			}
    	}

    	return !empty;
	}
	
	public void onEvent(Event event) throws Exception {
        
		
	}
	
	
	public void onSetPage(Page page, Desktop desktop) {
		if (dashboardTimer != null) {
			
			DashboardRunnable tmp = dashboardRunnable;			
			dashboardRunnable = new DashboardRunnable(tmp, desktop);
			dashboardTimer.setPage(page);
		}
	}
	
	/**
	 * clean up for logout
	 */
	public void onLogOut() {
		if (dashboardTimer != null) {
			dashboardTimer.detach();
			dashboardTimer = null;
		}
		if (dashboardRunnable != null) {			
			dashboardRunnable = null;
		}
		dashboardLayout.detach();
		dashboardLayout = null;
	}

	public void addDashboardPanel(DashboardPanel dashboardPanel) {
		if (dashboardRunnable != null) {
			dashboardRunnable.add(dashboardPanel);
		}
	}
	
   		
	public void updateLayout(ClientInfo clientInfo) {
		if (ClientInfo.isMobile()) {
			int n = 0;
        	if (ClientInfo.maxWidth(ClientInfo.MEDIUM_WIDTH-1)) {	        		
        		if (ClientInfo.maxWidth(ClientInfo.SMALL_WIDTH-1)) {
        			n = 1;
        		} else {
        			n = 2;
        		}
        	}
        	if (noOfCols > 0 && n > 0 && noOfCols != n) {
        		render(null, null, true);
        		dashboardLayout.invalidate();
        	}
    	}			
	}
}
