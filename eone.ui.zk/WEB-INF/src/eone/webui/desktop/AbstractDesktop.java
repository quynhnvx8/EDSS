
package eone.webui.desktop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window.Mode;

import eone.base.model.MMenu;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.webui.ClientInfo;
import eone.webui.EONEWebUI;
import eone.webui.LayoutUtils;
import eone.webui.component.Window;
import eone.webui.event.DialogEvents;
import eone.webui.exception.ApplicationException;
import eone.webui.part.AbstractUIPart;
import eone.webui.session.SessionManager;

/**
 *
 */
public abstract class AbstractDesktop extends AbstractUIPart implements IDesktop {

	private transient ClientInfo clientInfo;

	@SuppressWarnings("unused")
	private static final CLogger logger = CLogger.getCLogger(AbstractDesktop.class);

	public AbstractDesktop() {
	}
	
	
    public void onMenuSelected(int menuId)
    {
    	//LOGOUT SAME SESSION
    	String currSession = Env.getContext(Env.getCtx(), "#AD_Session_ID");
    	
    	String dbSession = DB.getSQLValueString(null, "SELECT SessionID FROM AD_User WHERE AD_User_ID = ?", Env.getAD_User_ID(Env.getCtx()));
    	if (dbSession!= null && !dbSession.equalsIgnoreCase(currSession) && !Env.isUserSystem(Env.getCtx())) {
    		Clients.confirmClose(null);
    		SessionManager.logoutSession();
    		return;
    	}
    	//END LOGOUT SAME SESION
    	
        MMenu menu = new MMenu(Env.getCtx(), menuId, null);

        if(menu.getAction().equals(MMenu.ACTION_Window))
        {
        	openWindow(menu.getAD_Window_ID(), null);
        }
        else if(menu.getAction().equals(MMenu.ACTION_Process) ||
        		menu.getAction().equals(MMenu.ACTION_Report))
        {
        	openProcessDialog(menu.getAD_Process_ID(), menu.isSOTrx());
        }
        else if(menu.getAction().equals(MMenu.ACTION_Form))
        {
        	openForm(menu.getAD_Form_ID());        	
        }
        else if(menu.getAction().equals(MMenu.ACTION_Info))
        {
        	openInfo(menu.getAD_InfoWindow_ID());        	
        }
        else if(menu.getAction().equals(MMenu.ACTION_Task))
        {
        	openTask(menu.getAD_Task_ID());
        }
        else
        {
            throw new ApplicationException("Menu Action not yet implemented: " + menu.getAction());
        }
    }
    
    /**
	 * @return clientInfo
	 */
	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	/**
	 * 
	 * @param clientInfo
	 */
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	/**
	 * @param win
	 */
	public int registerWindow(Object win) {
		List<Object> windows = getWindows();
		int retValue = windows.size();
		windows.add(win);
		return retValue;
	}
	
	/**
	 * @param WindowNo
	 */
	public void unregisterWindow(int WindowNo) {
		List<Object> windows = getWindows();
		if (windows != null && WindowNo < windows.size())
			windows.set(WindowNo, null);
		Env.clearWinContext(WindowNo);
	}
   	
    /**
     * 
     * @param WindowNo
     * @return Object
     */
	public Object findWindow(int WindowNo) {
		List<Object> windows = getWindows();
		if (windows != null && WindowNo < windows.size())
			return windows.get(WindowNo);
		else
			return null;
	}
	
    /**
     * @param win
     */
    public void showWindow(Window win) 
    {
    	String pos = win.getPosition();
    	this.showWindow(win, pos);
    }
    
    /**
     * when width of win set by stylesheet (css class or in style) win sometime don't in center.
     * fix by find out method change to  {@link LayoutUtils#openOverlappedWindow(org.zkoss.zk.ui.Component, org.zkoss.zul.Window, String)}  
     * @param win
     * @param pos
     */
   	public void showWindow(final Window win, final String pos)
	{
		final Window.Mode windowMode = win.getModeAttribute();		
		
		if (Mode.MODAL == windowMode)
		{
			if (pos != null)
				win.setPosition(pos);
			showModal(win);
		}
		else 
		{
			if (Executions.getCurrent() != null) 
			{
				showNonModalWindow(win, pos, windowMode);
			}
			else
			{
				Executions.schedule(getComponent().getDesktop(), new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						showNonModalWindow(win, pos, windowMode);
					}
				}, new Event("onExecute"));
			}
		}
	}

	private void showNonModalWindow(final Window win, final String pos,
			final Mode mode) {		
		if (Mode.POPUP == mode)
		{
			showPopup(win, pos);
		}
		else if (Mode.OVERLAPPED == mode)
		{
			showOverlapped(win, pos);
		}
		else if (Mode.EMBEDDED == mode)
		{
			showEmbedded(win);
		}
		else if (Mode.HIGHLIGHTED == mode)
		{
			showHighlighted(win, pos);
		}
	}
   	
   	protected abstract void showEmbedded(Window win);

	/**
   	 * 
   	 * @param win
   	 */
   	protected void showModal(final Window win)
   	{
   		if (EONEWebUI.isEventThreadEnabled()) 
   		{
   			win.setPage(page);
   			win.doModal();
   		}
   		else 
   		{
	   		if (Executions.getCurrent() != null)
	   		{
	   			throw new RuntimeException("When event thread is disabled, you can only show modal window in background thread that doesn't update Desktop.");
	   		}
	   		
	   		final StringBuffer buffer = new StringBuffer();
	   		win.addEventListener(DialogEvents.ON_WINDOW_CLOSE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					buffer.append("*");
				}
			});
	   		
	   		Executions.schedule(this.getComponent().getDesktop(), new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					showHighlighted(win, null);
					if (win.getPage() == null) {
						buffer.append("*");
					}
				}
			}, new Event("onExecute"));
	   		
			while(buffer.length() == 0)
			{
				try {
					Thread.sleep(1000);//500
				} catch (InterruptedException e) {
					Thread.interrupted();
				}
			}
   		}
   		
   		
	}
   	
   	/**
   	 * 
   	 * @param win
   	 * @param position
   	 */
   	protected void showPopup(Window win, String position)
   	{
   		if (position == null)
   			win.setPosition("center");
   		else
   			win.setPosition(position);
   		
   		win.setPage(page);
   		win.doPopup();
   	}
   	
   	/**
   	 * 
   	 * @param win
   	 * @param position
   	 */
   	protected void showOverlapped(Window win, String position)
   	{
		if (position == null)
			win.setPosition("center");
		else
			win.setPosition(position);
		
		win.setPage(page);
   		win.doOverlapped();
   	}
	
	/**
	 * 
	 * @param win
	 * @param position
	 */
   	protected void showHighlighted(Window win, String position)
   	{
		if (position == null)
			win.setPosition("center");
		else
			win.setPosition(position);
		
		win.setPage(page);
   		win.doHighlighted();
   	}   	

    protected List<Object> getWindows(){
    	Desktop desktop = getComponent().getDesktop();
    	if (desktop != null) {
	    	Session session = desktop.getSession();
	    	
	    	@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) session.getAttribute("windows.list");
	    	if (list == null) {
	    		list = new ArrayList<Object>();
	    		session.setAttribute("windows.list", list);
	    	}
	    	
	    	return Collections.synchronizedList(list);
    	} else {
    		return null;
    	}
    }

}
