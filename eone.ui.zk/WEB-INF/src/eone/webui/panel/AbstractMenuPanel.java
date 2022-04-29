

package eone.webui.panel;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import eone.base.model.MMenu;
import eone.base.model.MQuery;
import eone.base.model.MTree;
import eone.base.model.MTreeNode;
import eone.util.Callback;
import eone.util.Env;
import eone.webui.adwindow.ADTabpanel;
import eone.webui.adwindow.ADWindow;
import eone.webui.apps.MenuSearchController;
import eone.webui.exception.ApplicationException;
import eone.webui.session.SessionManager;
import eone.webui.theme.ThemeManager;
import eone.webui.util.ZKUpdateUtil;

/**
 * Menu Panel Base
 * @author Elaine
 * @date July 31, 2012
 */
public abstract class AbstractMenuPanel extends Panel implements EventListener<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160708371157917922L;
	
	public static final String MENU_ITEM_SELECTED_QUEUE = "MENU_ITEM_SELECTED_QUEUE";
	
	private Properties ctx;
    private Tree menuTree;

	private EventListener<Event> listener;
    
    public AbstractMenuPanel(Component parent)
    {
    	if (parent != null)
    		this.setParent(parent);
        init();            	
    }
    
    protected void init() {
		ctx = Env.getCtx();
		String adTreeIds = Env.getContext(getCtx(), "#AD_Tree_IDs");
        int AD_Tree_ID = 10; //TREE_MENUPRIMARY
        MTree mTree = new MTree(ctx, AD_Tree_ID, false, true, null, adTreeIds);      
        
        MTreeNode rootNode = mTree.getRoot();
        initComponents();
        initMenu(rootNode);
    }
    
    protected void initComponents()
    {
    	this.setSclass("menu-panel");
    	ZKUpdateUtil.setVflex(this, "1");
    	
        menuTree = new Tree();
        menuTree.setMultiple(false);
        menuTree.setId("mnuMain");
        ZKUpdateUtil.setVflex(menuTree, "1");
        menuTree.setSizedByContent(false);
        menuTree.setPageSize(-1); // Due to bug in the new paging functionality
    }
    
    private void initMenu(MTreeNode rootNode)
    {
        Treecols treeCols = new Treecols();
        Treecol treeCol = new Treecol();
        Treechildren rootTreeChildren = new Treechildren();        
        treeCols.appendChild(treeCol);
        menuTree.appendChild(treeCols);
        menuTree.appendChild(rootTreeChildren);
        
        generateMenu(rootTreeChildren, rootNode);
    }
    
    
    private void generateMenu(Treechildren treeChildren, MTreeNode mNode)
    {
        Enumeration<?> nodeEnum = mNode.children();
      
        while(nodeEnum.hasMoreElements())
        {
            MTreeNode mChildNode = (MTreeNode)nodeEnum.nextElement();
            Treeitem treeitem = new Treeitem();
            treeChildren.appendChild(treeitem);
            treeitem.setTooltiptext(mChildNode.getDescription());            
            
            if(mChildNode.getChildCount() != 0)
            {
                treeitem.setOpen(false);
                treeitem.setLabel(mChildNode.getName());
                Treecell cell = (Treecell)treeitem.getTreerow().getFirstChild();
                cell.setSclass("menu-treecell-cnt");
                Treechildren treeItemChildren = new Treechildren();
                treeitem.appendChild(treeItemChildren);
                generateMenu(treeItemChildren, mChildNode);
                if (treeItemChildren.getChildren().size() == 0)
                {
                	treeItemChildren.detach();
                }
                
                treeitem.getTreerow().addEventListener(Events.ON_CLICK, this);
            }
            else
            {
                treeitem.setValue(String.valueOf(mChildNode.getNode_ID()));
                Treerow treeRow = new Treerow();
                treeitem.appendChild(treeRow);
                Treecell treeCell = new Treecell();
                treeCell.setSclass("menu-treecell-cnt");
                treeRow.appendChild(treeCell);
                A link = new A();
                treeCell.appendChild(link);
                if (mChildNode.isReport())
                {
                	if (ThemeManager.isUseFontIconForImage())
                		link.setIconSclass("z-icon-Report");
                	else
                		link.setImage(ThemeManager.getThemeResource("images/mReport.png"));
                	treeitem.setAttribute("menu.type", "report");
                }
                else if (mChildNode.isProcess() || mChildNode.isTask())
                {
                	if (ThemeManager.isUseFontIconForImage())
                		link.setIconSclass("z-icon-Process");
                	else
                		link.setImage(ThemeManager.getThemeResource("images/mProcess.png"));
                	treeitem.setAttribute("menu.type", "process");
                }
                else if (mChildNode.isForm())
                {
                	if (ThemeManager.isUseFontIconForImage())
                		link.setIconSclass("z-icon-Form");
                	else
                		link.setImage(ThemeManager.getThemeResource("images/mWindow.png"));
                	treeitem.setAttribute("menu.type", "form");
                }
                else if (mChildNode.isInfo())
                {
                	if (ThemeManager.isUseFontIconForImage())
                		link.setIconSclass("z-icon-Info");
                	else
                		link.setImage(ThemeManager.getThemeResource("images/mInfo.png"));
                	treeitem.setAttribute("menu.type", "info");
                }
                else // Window
                {
                	if (ThemeManager.isUseFontIconForImage())
                		link.setIconSclass("z-icon-Window");
                	else
                		link.setImage(ThemeManager.getThemeResource("images/mForm.png"));
                	treeitem.setAttribute("menu.type", "window");                	
                }
                treeitem.addEventListener(Events.ON_OK, this);
                link.setLabel(mChildNode.getName());
                
                link.addEventListener(Events.ON_CLICK, this);
                link.setSclass("menu-href");
                treeitem.getTreerow().setDraggable("favourite"); // Elaine 2008/07/24
                treeitem.setAttribute(MenuSearchController.M_TREE_NODE_ATTR, mChildNode);
                if (mChildNode.isSummary()) {
                	treeCell.setStyle("font-weight: bold");
                	//ZkCssHelper.appendStyle(treeCell, "font-weight: bold");
                }
            }
        }
    }
    
    /*
    public Toolbarbutton createNewButton()
    {
    	Toolbarbutton newBtn = new Toolbarbutton(null, ThemeManager.getThemeResource("images/New10.png"));
    	if (ThemeManager.isUseFontIconForImage())
		{
			newBtn.setImage(null);
			newBtn.setIconSclass("z-icon-New");
		}
    	newBtn.setSclass("menu-href-newbtn");
    	return newBtn;
    }
    */
    
    public void onEvent(Event event)
    {
        Component comp = event.getTarget();
        String eventName = event.getName();
        if ((eventName.equals(Events.ON_CLICK)) || (eventName.equals(Events.ON_OK)))
        {
        	doOnClick(comp, event.getData());
        }
    }
    
    private void doOnClick(Component comp, Object eventData) {
    	boolean newRecord = false;
		if (comp instanceof A) {
			comp = comp.getParent().getParent();
		}
		if (comp instanceof Toolbarbutton) {
			comp = comp.getParent().getParent();
			newRecord = true;
		} else if (eventData != null && eventData instanceof Boolean) {
			newRecord = (Boolean)eventData;
		}
		if (comp instanceof Treeitem)
		{
			Treeitem selectedItem = (Treeitem) comp;
			if(selectedItem.getValue() != null)
			{
				if (newRecord)
				{
					onNewRecord(selectedItem);
				}
				else
				{
					fireMenuSelectedEvent(selectedItem);
				}
			}
		}
		if (comp instanceof Treerow) 
		{
			Treeitem selectedItem = (Treeitem) comp.getParent();
		    if(selectedItem.getValue() != null)
		    {
		    	if (newRecord)
		    	{
		    		onNewRecord(selectedItem);
		    	}
		    	else
		    	{
		    		fireMenuSelectedEvent(selectedItem);
		    	}
		    }
		    else
		    	selectedItem.setOpen(!selectedItem.isOpen());
		    selectedItem.setSelected(true);
		    if (selectedItem != null && !"".equals(selectedItem.getId()))
		    	//System.out.println();
		    	EventQueues.lookup(MENU_ITEM_SELECTED_QUEUE, EventQueues.DESKTOP, true).publish(new Event(Events.ON_SELECT, null, selectedItem));
		}
	}
    
    private void onNewRecord(Treeitem selectedItem) {
    	try
        {
			int menuId = Integer.parseInt((String)selectedItem.getValue());
			MMenu menu = new MMenu(Env.getCtx(), menuId, null);
			
    		MQuery query = new MQuery("");
    		query.addRestriction("1=2");
			query.setRecordCount(0);

			if (getParent() instanceof Popup) {
				((Popup)getParent()).close();
			}
			
			SessionManager.getAppDesktop().openWindow(menu.getAD_Window_ID(), query, new Callback<ADWindow>() {				
				@Override
				public void onCallback(ADWindow result) {
					if(result == null)
						return;
		    					
					result.getADWindowContent().onNew();
					ADTabpanel adtabpanel = (ADTabpanel) result.getADWindowContent().getADTab().getSelectedTabpanel();
					adtabpanel.focusToFirstEditor(false);					
				}
			});			
        }
        catch (Exception e)
        {
            throw new ApplicationException(e.getMessage(), e);
        }
		
	}

	protected void fireMenuSelectedEvent(Treeitem selectedItem) {
    	int nodeId = Integer.parseInt((String)selectedItem.getValue());
       
    	try
        {
    		if (getParent() instanceof Popup) {
				((Popup)getParent()).close();
			}
    		SessionManager.getAppDesktop().onMenuSelected(nodeId);
        }
        catch (Exception e)
        {
            throw new ApplicationException(e.getMessage(), e);
        }		
	}

	public Tree getMenuTree() 
	{
		return menuTree;
	}
	
	public Properties getCtx()
	{
		return ctx;
	}
	
	/* (non-Javadoc)
	 * @see org.zkoss.zk.ui.AbstractComponent#onPageDetached(org.zkoss.zk.ui.Page)
	 */
	@Override
	public void onPageDetached(Page page) {
		super.onPageDetached(page);
		if (listener != null) {
			try {
				EventQueue<Event> queue = EventQueues.lookup(MENU_ITEM_SELECTED_QUEUE, EventQueues.DESKTOP, true);
				if (queue != null)
					queue.unsubscribe(listener);
			} finally {
				listener = null;
			}
			
		}
	}
	
	@Override
	public void onPageAttached(Page newpage, Page oldpage) {
		super.onPageAttached(newpage, oldpage);
		if (listener == null) {
			listener = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (event.getName() == Events.ON_SELECT)
					{
						Treeitem selectedItem = (Treeitem) event.getData();
					    	
						if (selectedItem != null)
						{
							Object value = selectedItem.getValue();
							if (value != null)
							{
								if (menuTree.getSelectedItem() != null && menuTree.getSelectedItem().getValue() != null && menuTree.getSelectedItem().getValue().equals(value))
									return;
								
								Collection<Treeitem> items = menuTree.getItems();
								for (Treeitem item : items)
								{
									if (item != null && item.getValue() != null && item.getValue().equals(value))
									{
										TreeSearchPanel.select(item);
										return;
									}
								}
							}
							else
							{
								String label = selectedItem.getLabel();
								if (menuTree.getSelectedItem() != null && menuTree.getSelectedItem().getLabel() != null && menuTree.getSelectedItem().getLabel().equals(label))
									return;
								
								Collection<Treeitem> items = menuTree.getItems();
								for (Treeitem item : items)
								{
									if (item != null && item.getLabel() != null && item.getLabel().equals(label))
									{
										TreeSearchPanel.select(item);								
										return;
									}
								}
							}
						}
					}
				}
			};
		}
		EventQueues.lookup(MENU_ITEM_SELECTED_QUEUE, EventQueues.DESKTOP, true).subscribe(listener);
	}
	
}
