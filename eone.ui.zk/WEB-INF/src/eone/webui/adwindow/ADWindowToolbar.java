
package eone.webui.adwindow;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.zkoss.image.AImage;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.AfterSizeEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.LabelImageElement;

import eone.base.IServiceHolder;
import eone.base.model.GridTab;
import eone.base.model.MToolBarButton;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Msg;
import eone.util.Util;
import eone.util.ValueNamePair;
import eone.webui.ClientInfo;
import eone.webui.LayoutUtils;
import eone.webui.action.Actions;
import eone.webui.action.IAction;
import eone.webui.component.FToolbar;
import eone.webui.component.Menupopup;
import eone.webui.component.Tabpanel;
import eone.webui.component.ToolBarButton;
import eone.webui.event.ToolbarListener;
import eone.webui.part.WindowContainer;
import eone.webui.session.SessionManager;
import eone.webui.theme.ITheme;
import eone.webui.theme.ThemeManager;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;

public class ADWindowToolbar extends FToolbar implements EventListener<Event>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8494819673584541046L;

	public static final String BTNPREFIX = "Btn";
	
	public static final String MNITMPREFIX = "Mnitm";

    private static final CLogger log = CLogger.getCLogger(ADWindowToolbar.class);
    
    //private Combobox fQueryName;
    /*
	private MUserQuery[] userQueries;
	private MUserQuery selectedUserQuery;
	*/
    private ToolBarButton btnIgnore;

    private ToolBarButton btnNew, btnCopy, btnDelete, btnSave;//btnHelp, 

    private ToolBarButton btnSaveAndCreate; // Elaine 2009/03/02 - Save & Create

    private ToolBarButton btnRefresh, btnFind, btnAttachment;//, btnLock

    private ToolBarButton btnGridToggle;
    
    private ToolBarButton btnComplete, btnPost, btnReActivate;

    private ToolBarButton btnParentRecord, btnDetailRecord;
    
    private ToolBarButton btnAppendSign;
    
   // private ToolBarButton btnReport; 

    //private ToolBarButton btnPrint;//, btnArchive, btnReport, 

    //private ToolBarButton btnActiveWorkflows;//btnZoomAcross, , btnProductInfo, btnRequests

    //private ToolBarButton btnChat;
    
    //private ToolBarButton btnPostIt;

    private ToolBarButton btnCustomize;

    private ToolBarButton btnExport;
    private ToolBarButton btnFileImport;
   // private ToolBarButton btnCSVImport;

    private ToolBarButton btnProcess;
    
    private ToolBarButton btnQuickForm;

    private ToolBarButton btnShowMore;
    private Menupopup menupopup;

    private HashMap<String, ToolBarButton> buttons = new HashMap<String, ToolBarButton>();
    private HashMap<ToolBarButton, Menuitem> menuItems = new HashMap<ToolBarButton, Menuitem>();
	private ArrayList<ToolBarButton> mobileShowMoreButtons = new ArrayList<ToolBarButton>();
	
//    private ToolBarButton btnExit;

    private ArrayList<ToolbarListener> listeners = new ArrayList<ToolbarListener>();

    private Event event;

    private Map<Integer, ToolBarButton> keyMap = new HashMap<Integer, ToolBarButton>();
    private Map<Integer, ToolBarButton> altKeyMap = new HashMap<Integer, ToolBarButton>();
    private Map<Integer, ToolBarButton> ctrlKeyMap = new HashMap<Integer, ToolBarButton>();

    private List<ToolbarCustomButton> toolbarCustomButtons = new ArrayList<ToolbarCustomButton>();

	// Elaine 2008/12/04
	/** Show Personal Lock								*/
	//public boolean isPersonalLock = MRole.getDefault().isPersonalLock();
	//private boolean isAllowProductInfo = MRole.getDefault().canAccess_Info_Product();

	private int windowNo = 0;

	private long prevKeyEventTime = 0;

	private KeyEvent prevKeyEvent;
	
	// Maintain hierarchical Quick form by its parent-child tab while open leaf
	// tab once & dispose and doing same action
	private int							quickFormTabHrchyLevel		= 0;

	private A overflowButton;

	private ArrayList<ToolBarButton> overflows;

	private Popup overflowPopup;
	
	private int prevWidth;

	private AbstractADWindowContent windowContent;

	/**	Last Modifier of Action Event					*/
//	public int 				lastModifiers;
	//

    public ADWindowToolbar()
    {
    	this(null, 0);
    }

    public ADWindowToolbar(AbstractADWindowContent windowContent, int windowNo) {
    	this.windowContent = windowContent;
    	setWindowNo(windowNo);
        init();
        if (ClientInfo.isMobile()) {
        	mobileInit();
        }
	}

	private void init()
    {
    	LayoutUtils.addSclass("adwindow-toolbar", this);

    	//Show more menu pop up
        menupopup = new Menupopup();
        this.appendChild(menupopup);
      
        btnIgnore = createButton("Ignore", "Ignore", "Ignore");
        btnIgnore.setTooltiptext(btnIgnore.getTooltiptext()+ "    Alt+Z");
        //btnHelp = createButton("Help", "Help","Help");
        //btnHelp.setTooltiptext(btnHelp.getTooltiptext()+ "    Alt+H");
        btnNew = createButton("New", "New", "New");
        btnNew.setTooltiptext(btnNew.getTooltiptext()+ "    Alt+N");
        btnCopy = createButton("Copy", "Copy", "Copy");
        btnCopy.setTooltiptext(btnCopy.getTooltiptext()+ "    Alt+C");
        btnDelete = createButton("Delete", "Delete", "Delete");
        btnDelete.setTooltiptext(btnDelete.getTooltiptext()+ "    Alt+D");
        btnSave = createButton("Save", "Save", "Save");
        btnSave.setTooltiptext(btnSave.getTooltiptext()+ "    Alt+S");
        btnSaveAndCreate = createButton("SaveCreate", "SaveCreate", "SaveCreate");
        btnSaveAndCreate.setTooltiptext(btnSaveAndCreate.getTooltiptext()+ "    Alt+A");
        btnRefresh = createButton("Refresh", "Refresh", "Refresh");
        btnRefresh.setTooltiptext(btnRefresh.getTooltiptext()+ "    Alt+E");
        btnFind = createButton("Find", "Find", "Find");
        btnFind.setTooltiptext(btnFind.getTooltiptext()+ "    Alt+F");
        btnAttachment = createButton("Attachment", "Attachment", "Attachment");
        //btnPostIt = createButton("PostIt", "PostIt", "PostIt");
        //btnChat = createButton("Chat", "Chat", "Chat");
        btnGridToggle = createButton("Toggle", "Multi", "Multi");
        btnGridToggle.setTooltiptext(btnGridToggle.getTooltiptext()+ "    Alt+T");
        
        btnComplete = createButton("Complete","Complete","Complete");
        btnComplete.setTooltiptext(btnComplete.getTooltiptext()+ "    Alt+Enter");
        //btnComplete.setDisabled(false);
        //btnComplete.setVisible(false);
        
        
        btnPost = createButton("Posted","Posted","Posted");
        btnPost.setTooltiptext(btnPost.getTooltiptext()+ "    Alt+P");
        //btnPost.setDisabled(false);
        //btnPost.setVisible(false);
        
        btnReActivate = createButton("ReActivate","ReActivate","ReActivate");
        btnReActivate.setTooltiptext(btnReActivate.getTooltiptext()+ "    Alt+R");
        //btnReActivate.setDisabled(false);
        //btnReActivate.setVisible(false);
        
        btnAppendSign = createButton("AppendSign","AppendSign","AppendSign");
        btnAppendSign.setTooltiptext(btnAppendSign.getTooltiptext()+ "    Alt+A");
        
        
        btnParentRecord = createButton("ParentRecord", "Parent", "Parent");
        btnParentRecord.setTooltiptext(btnParentRecord.getTooltiptext()+ "   Alt+Up");
        btnDetailRecord = createButton("DetailRecord", "Detail", "Detail");
        btnDetailRecord.setTooltiptext(btnDetailRecord.getTooltiptext()+ "   Alt+Down");
        
        btnCustomize= createButton("Customize", "Customize", "Customize");
        btnCustomize.setDisabled(false);

        btnProcess= createButton("Process", "Process", "Process");
        btnProcess.setTooltiptext(btnProcess.getTooltiptext()+ "    Alt+O");
        btnProcess.setDisabled(false);
        
        

        btnQuickForm = createButton("QuickForm", "QuickForm", "QuickForm");
        btnQuickForm.setDisabled(false);

        btnGridToggle.setDisabled(false);
       

        if ("Y".equalsIgnoreCase(Env.getContext(Env.getCtx(), "#IsCanExport")))
        {
        	btnExport = createButton("Export", "Export", "Export");
        }
        btnFileImport = createButton("FileImport", "FileImport", "FileImport");
        
        btnShowMore = createButton("ShowMore", "ShowMore", "ShowMore");
        btnShowMore.setDisabled(true);
        btnShowMore.setVisible(false);
        
        MToolBarButton[] officialButtons = MToolBarButton.getToolbarButtons("W", null);
        for (MToolBarButton button : officialButtons) {
        	if (! button.isActive()) {
        		buttons.remove(button.getComponentName());
        	} else {
        		if (button.isCustomization()) {
        			String actionId = button.getActionClassName();
        			IServiceHolder<IAction> serviceHolder = Actions.getAction(actionId);
        			IAction action = serviceHolder.getService();
        			if (serviceHolder != null && action != null) {
        				String labelKey = actionId + ".label";
        				String tooltipKey = actionId + ".tooltip";
        				String label = Msg.getMsg(Env.getCtx(), labelKey);
        				String tooltiptext = Msg.getMsg(Env.getCtx(), tooltipKey);
        				if (labelKey.equals(label)) {
        					label = button.getName();
        				}
        				if (tooltipKey.equals(tooltiptext)) {
        					tooltipKey = null;
        				}
        				ToolBarButton btn = createButton(button.getComponentName(), null, tooltipKey);
        				btn.removeEventListener(Events.ON_CLICK, this);
        				btn.setId(button.getName());
        				btn.setDisabled(false);

        				AImage aImage = Actions.getActionImage(actionId);
        				if (aImage != null) {
        					btn.setImageContent(aImage);
        				} else {
        					btn.setLabel(label);
        				}

        				ToolbarCustomButton toolbarCustomBtn = new ToolbarCustomButton(button, btn, actionId, windowNo);
        				toolbarCustomButtons.add(toolbarCustomBtn);

        				if (ClientInfo.isMobile() && button.isShowMore()) 
        					mobileShowMoreButtons.add(btn);
        				else if (!button.isShowMore()) {
            				this.appendChild(btn);
            				action.decorate(btn);        					
        				}
        			}
        		}
        		if (buttons.get(button.getComponentName()) != null) {
        			if (ClientInfo.isMobile() && button.isShowMore()) 
    					mobileShowMoreButtons.add(buttons.get(button.getComponentName()));
    				else if (button.isShowMore())
        				createMenuitem(buttons.get(button.getComponentName()));
        			else {
            			this.appendChild(buttons.get(button.getComponentName()));
            			if (button.isAddSeparator()) {
            				this.appendChild(new Separator("vertical"));
            			}
        			}
        		}
        	}
        }
        
        if (!ClientInfo.isMobile() && !menuItems.isEmpty()) {
            this.appendChild(btnShowMore);
            btnShowMore.setDisabled(false);
            btnShowMore.setVisible(true);
        }

    	configureKeyMap();

        ZKUpdateUtil.setWidth(this, "100%");
    }


    private ToolBarButton createButton(String name, String image, String tooltip)
    {
    	ToolBarButton btn = new ToolBarButton("");
        btn.setName(BTNPREFIX+name);
        btn.setId(btn.getName());
        if (image != null) 
        {
        	if (ThemeManager.isUseFontIconForImage()) 
        	{
        		String iconSclass = "z-icon-" + image;
        		btn.setIconSclass(iconSclass);
        		LayoutUtils.addSclass("font-icon-toolbar-button", btn);
        	}
        	else
        	{
	        	Executions.createComponents(ThemeManager.getPreference(), this, null);
	        	String size = Env.getContext(Env.getCtx(), ITheme.ZK_TOOLBAR_BUTTON_SIZE);
	        	String suffix = "24.png";
	        	if (!Util.isEmpty(size)) 
	        	{
	        		suffix = size + ".png";
	        	}
	        	btn.setImage(ThemeManager.getThemeResource("images/"+image + suffix));
        	}
        }
        btn.setTooltiptext(Msg.getMsg(Env.getCtx(),tooltip));
        LayoutUtils.addSclass("toolbar-button", btn);
        
        buttons.put(name, btn);
        //make toolbar button last to receive focus
        btn.setTabindex(0);
        btn.addEventListener(Events.ON_CLICK, this);
        //btn.setDisabled(true);

        return btn;
    }
    
    /**
     * Create Menu Item based on ToolBar button
     * @param button
     * @return
     */
    private Menuitem createMenuitem(ToolBarButton button){
		Menuitem item = new Menuitem(button.getTooltiptext());
		if (button.getImage() != null)
			item.setImage(button.getImage());
		else if (button.getImageContent() != null)
			item.setImageContent(button.getImageContent());
		else if (ThemeManager.isUseFontIconForImage()) { 
			item.setIconSclass(button.getIconSclass());
    		LayoutUtils.addSclass("font-icon-menuitem", item);
		}
		item.setId(MNITMPREFIX+button.getName());
		item.setValue(button.getName());
		item.addEventListener(Events.ON_CLICK, evt -> Events.sendEvent(new Event(Events.ON_CLICK, button)));
		menupopup.appendChild(item);
		menuItems.put(button, item);
		return item;
    }    

    public ToolBarButton getButton(String name)
    {
    	return buttons.get(name);
    }
    
    public LabelImageElement getToolbarItem(String name)
    {
    	return menuItems.get(buttons.get(name)) != null ? buttons.get("ShowMore") : 
    		buttons.get(name);
    }

    /** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */
    public static final int VK_A              = 0x41;
    public static final int VK_B              = 0x42;
    public static final int VK_C              = 0x43;
    public static final int VK_D              = 0x44;
    public static final int VK_E              = 0x45;
    public static final int VK_F              = 0x46;
    public static final int VK_G              = 0x47;
    public static final int VK_H              = 0x48;
    public static final int VK_I              = 0x49;
    public static final int VK_J              = 0x4A;
    public static final int VK_K              = 0x4B;
    public static final int VK_L              = 0x4C;
    public static final int VK_M              = 0x4D;
    public static final int VK_N              = 0x4E;
    public static final int VK_O              = 0x4F;
    public static final int VK_P              = 0x50;
    public static final int VK_Q              = 0x51;
    public static final int VK_R              = 0x52;
    public static final int VK_S              = 0x53;
    public static final int VK_T              = 0x54;
    public static final int VK_U              = 0x55;
    public static final int VK_V              = 0x56;
    public static final int VK_W              = 0x57;
    public static final int VK_X              = 0x58;
    public static final int VK_Y              = 0x59;
    public static final int VK_Z              = 0x5A;

    private void configureKeyMap()
    {
    	altKeyMap.put(KeyEvent.F2, btnComplete);
        altKeyMap.put(KeyEvent.F3, btnReActivate);
        altKeyMap.put(KeyEvent.F4, btnPost);
		//altKeyMap.put(KeyEvent., btnHelp);
		altKeyMap.put(VK_N, btnNew);
		altKeyMap.put(VK_D, btnDelete);
		altKeyMap.put(VK_S, btnSave);
		altKeyMap.put(VK_A, btnSaveAndCreate);
		altKeyMap.put(VK_C, btnCopy);
		altKeyMap.put(VK_E, btnRefresh);
		altKeyMap.put(VK_T, btnGridToggle);
		altKeyMap.put(KeyEvent.UP, btnParentRecord);
		altKeyMap.put(KeyEvent.DOWN, btnDetailRecord);
		altKeyMap.put(VK_F, btnFind);
		altKeyMap.put(VK_Z, btnIgnore);
		//altKeyMap.put(VK_R, btnReport);		
		//altKeyMap.put(VK_P, btnPrint);
		altKeyMap.put(VK_O, btnProcess);
		altKeyMap.put(VK_L, btnCustomize);
	}

	protected void addSeparator()
    {
		Space s = new Space();
		s.setSpacing("6px");
		s.setOrient("vertical");
		this.appendChild(s);
    }

    public void addListener(ToolbarListener toolbarListener)
    {
        listeners.add(toolbarListener);
    }

    public void removeListener(ToolbarListener toolbarListener)
    {
        listeners.remove(toolbarListener);
    }

    public void onEvent(Event event)
    {
        String eventName = event.getName();

        if(eventName.equals(Events.ON_CLICK))
        {
            if(event.getTarget() instanceof ToolBarButton)
            {
            	if (!event.getTarget().getId().contentEquals(BTNPREFIX+"ShowMore"))
            		doOnClick(event);
            	else
            		menupopup.open(btnShowMore, "after_start");
            }
        } else if (eventName.equals(Events.ON_CTRL_KEY))
        {
			KeyEvent keyEvent = (KeyEvent) event;

			// If Quick form is opened then prevent toolbar shortcut key events.
			if (!(keyEvent.getKeyCode() == KeyEvent.F2) && windowContent != null && windowContent.getOpenQuickFormTabs().size() > 0)
				return;

        	if (LayoutUtils.isReallyVisible(this)) {
	        	//filter same key event that is too close
	        	//firefox fire key event twice when grid is visible
	        	long time = System.currentTimeMillis();
	        	if (prevKeyEvent != null && prevKeyEventTime > 0 &&
	        			prevKeyEvent.getKeyCode() == keyEvent.getKeyCode() &&
	    				prevKeyEvent.getTarget() == keyEvent.getTarget() &&
	    				prevKeyEvent.isAltKey() == keyEvent.isAltKey() &&
	    				prevKeyEvent.isCtrlKey() == keyEvent.isCtrlKey() &&
	    				prevKeyEvent.isShiftKey() == keyEvent.isShiftKey()) {
	        		if ((time - prevKeyEventTime) <= 300) {
	        			return;
	        		}
	        	}
	        	this.onCtrlKeyEvent(keyEvent);
        	}
        } 
        /*Bo phan query tren thanh cong cu
        else if (Events.ON_SELECT.equals(eventName)) 
        {
        	int index = fQueryName.getSelectedIndex();
        	if (index < 0) return;
        	if (index == 0) // no query - refresh
        		setSelectedUserQuery(null);
        	else
				setSelectedUserQuery(userQueries[index-1]);

			doOnClick(event);
        }
        */
    }

	private void doOnClick(Event event) {
		this.event = event;
		String compName;
		String methodName;
		/*
		if (event.getTarget() == fQueryName) {
			methodName = "onSearchQuery";
		} else {
			ToolBarButton cComponent = (ToolBarButton) event.getTarget();
			compName = cComponent.getName();
			methodName = "on" + compName.substring(3);
		}*/
		ToolBarButton cComponent = (ToolBarButton) event.getTarget();
		compName = cComponent.getName();
		methodName = "on" + compName.substring(3);
		
		Iterator<ToolbarListener> listenerIter = listeners.iterator();
		while(listenerIter.hasNext())
		{
		    try
		    {
		        ToolbarListener tListener = listenerIter.next();
		        Method method = tListener.getClass().getMethod(methodName, (Class[]) null);
		        method.invoke(tListener, (Object[]) null);
		    }
		    catch (Exception e)
		    {
		    	String msg = null;
		    	ValueNamePair vp = CLogger.retrieveError();
		    	if (vp != null) {
		    		msg = vp.getName();
		    	}
		    	if (msg == null) {
		    		Throwable cause = e.getCause();
		    		if (cause != null) {
		    			msg = cause.getLocalizedMessage();
		    		}
		    	}
		    	if (msg == null) {
		    		msg = "Could not invoke Toolbar listener method: " + methodName + "()";
		    	}
		    	FDialog.error(windowNo, this, "Error", msg);
		    	log.log(Level.SEVERE, msg, e);
		    }
		}
		this.event = null;
	}

    public void enableTabNavigation(boolean enabled)
    {
        enableTabNavigation(enabled, enabled);
    }

    public void enableTabNavigation(boolean enableParent, boolean enableDetail)
    {
        this.btnParentRecord.setDisabled(!enableParent);
        this.btnDetailRecord.setDisabled(!enableDetail);
        enableMenuitem(btnParentRecord, enableParent);
        enableMenuitem(btnDetailRecord, enableDetail);
    }

    public void enableRefresh(boolean enabled)
    {
        this.btnRefresh.setDisabled(!enabled);
        enableMenuitem(btnRefresh, enabled);
    }

    public void enableSave(boolean enabled)
    {
        this.btnSave.setDisabled(!enabled);
    	this.btnSaveAndCreate.setDisabled(!enabled);
        enableMenuitem(btnSave, enabled);
        enableMenuitem(btnSaveAndCreate, enabled);
    }

    public boolean isSaveEnable() {
    	return !btnSave.isDisabled();
    }

//    public void enableExit(boolean enabled)
//    {
//    	this.btnExit.setDisabled(!enabled);
//    }

    public void enableDelete(boolean enabled)
    {
        this.btnDelete.setDisabled(!enabled);        
        enableMenuitem(btnDelete, enabled);
    }
    
    public boolean isDeleteEnable()
    {
    	return !btnDelete.isDisabled();
    }
    
	public boolean isNewEnabled() {
    	return !btnNew.isDisabled();
	}

    public void enableIgnore(boolean enabled)
    {
        this.btnIgnore.setDisabled(!enabled);
        enableMenuitem(btnIgnore, enabled);
    }

    public void enableNew(boolean enabled)
    {
        this.btnNew.setDisabled(!enabled);
        enableMenuitem(btnNew, enabled);
    }

    public void enableCopy(boolean enabled)
    {
        this.btnCopy.setDisabled(!enabled);
        enableMenuitem(btnCopy, enabled);
    }
    
    /*
    public void enableComplete(boolean enabled)
    {
        this.btnComplete.setDisabled(!enabled);
        enableMenuitem(btnComplete, enabled);
    }
    
    public void enablePost(boolean enabled)
    {
        this.btnPost.setDisabled(!enabled);
        enableMenuitem(btnPost, enabled);
    }
    
    public void enableReActivate(boolean enabled)
    {
        this.btnReActivate.setDisabled(!enabled);
        enableMenuitem(btnReActivate, enabled);
    }
    
    public void enableAppendSign(boolean enabled)
    {
        this.btnAppendSign.setDisabled(!enabled);
        enableMenuitem(btnAppendSign, enabled);
    }
    */
	
    public void enableAttachment(boolean enabled)
    {
        this.btnAttachment.setDisabled(!enabled);
        enableMenuitem(btnAttachment, enabled);
    }
    /*
    public void enableChat(boolean enabled)
    {
        //this.btnChat.setDisabled(!enabled);
        //enableMenuitem(btnChat, enabled);
    }
    */
    /*
    public void enablePrint(boolean enabled)
    {
    	this.btnPrint.setDisabled(!enabled);
        enableMenuitem(btnPrint, enabled);
    }
    */
    /*
    public void enableReport(boolean enabled)
    {
    	this.btnReport.setDisabled(!enabled);
        enableMenuitem(btnReport, enabled);
    }
    */
	
    public void enableFind(boolean enabled)
    {
        this.btnFind.setDisabled(!enabled);
        enableMenuitem(btnFind, enabled);
    }

    public void enableGridToggle(boolean enabled)
    {
    	btnGridToggle.setDisabled(!enabled);
    	enableMenuitem(btnGridToggle, enabled);
    }

    public void enableCustomize(boolean enabled)
    {
    	btnCustomize.setDisabled(!enabled);
    	enableMenuitem(btnCustomize, enabled);
    }
    /*
    public void enableArchive(boolean enabled)
    {
    	btnArchive.setDisabled(!enabled);
    	enableMenuitem(btnArchive, enabled);
    }
    
    public void enableZoomAcross(boolean enabled)
    {
    	btnZoomAcross.setDisabled(!enabled);
    	enableMenuitem(btnZoomAcross, enabled);
    }
    
    public void enableActiveWorkflows(boolean enabled)
    {
    	btnActiveWorkflows.setDisabled(!enabled);
    	enableMenuitem(btnActiveWorkflows, enabled);
    }
    
    public void enableRequests(boolean enabled)
    {
    	btnRequests.setDisabled(!enabled);
    	enableMenuitem(btnRequests, enabled);
    }
    */
    public void enableMenuitem(Toolbarbutton button, boolean enabled) {
        if (menuItems.get(button) != null)
			menuItems.get(button).setDisabled(!enabled);
    }

	public void enableQuickForm(boolean enabled)
	{
		btnQuickForm.setDisabled(!enabled);
		//enableMenuitem(btnQuickForm, enabled);
	}

	/*
    public void lock(boolean locked)
    {
    	setPressed("Lock", locked);

      	if (ThemeManager.isUseFontIconForImage())
      	{
      		String iconSclass = "z-icon-" + (this.btnLock.isPressed() ? "lock" : "unlock") ;
      		this.btnLock.setIconSclass(iconSclass);
      		LayoutUtils.addSclass("font-icon-toolbar-button", this.btnLock);
      		if (menuItems.get(btnLock) != null) {
    			menuItems.get(btnLock).setIconSclass(iconSclass);
    			LayoutUtils.addSclass("font-icon-menuitem", menuItems.get(btnLock));
    		}
      	}
      	else
      	{
      		String size = Env.getContext(Env.getCtx(), ITheme.ZK_TOOLBAR_BUTTON_SIZE);
      		String suffix = "24.png";
      		if (!Util.isEmpty(size))
      		{
      			suffix = size + ".png";
      		}
      		String imgURL = "images/"+ (this.btnLock.isPressed() ? "LockX" : "Lock") + suffix;
        	imgURL = ThemeManager.getThemeResource(imgURL);
    		this.btnLock.setImage(imgURL);
    		if (menuItems.get(btnLock) != null) {
    			menuItems.get(btnLock).setImage(imgURL);
    		}
      	}
    }
    */
    /*
    public void enablePostIt(boolean enabled)
    {
        this.btnPostIt.setDisabled(!enabled);
    	enableMenuitem(btnPostIt, enabled);
    }
	*/
    public Event getEvent()
    {
    	return event;
    }

	private void onCtrlKeyEvent(KeyEvent keyEvent) {
		if (windowContent != null && windowContent.isBlock())
			return;
		ToolBarButton btn = null;
		if (keyEvent.isAltKey() && !keyEvent.isCtrlKey() && !keyEvent.isShiftKey())
		{
			if (keyEvent.getKeyCode() == VK_X)
			{
				if (windowNo > 0)
				{
					prevKeyEventTime = System.currentTimeMillis();
		        	prevKeyEvent = keyEvent;
					keyEvent.stopPropagation();
					SessionManager.getAppDesktop().closeWindow(windowNo);
				}
			}
			else
			{
				btn = altKeyMap.get(keyEvent.getKeyCode());
			}
		}
		else if (!keyEvent.isAltKey() && keyEvent.isCtrlKey() && !keyEvent.isShiftKey())
		{
			if (keyEvent.getKeyCode() == KeyEvent.F2)
			{
				quickFormTabHrchyLevel = quickFormTabHrchyLevel + 1;
				fireButtonClickEvent(keyEvent, btnDetailRecord);
				if (!btnQuickForm.isDisabled() && btnQuickForm.isVisible())
				{
					fireButtonClickEvent(keyEvent, btnQuickForm);
				}
				else if (!btnParentRecord.isDisabled() && btnParentRecord.isVisible())
				{
					fireButtonClickEvent(keyEvent, btnParentRecord);
					quickFormTabHrchyLevel = quickFormTabHrchyLevel - 1;
				}
				return;
			}
			else
			{
				btn = ctrlKeyMap.get(keyEvent.getKeyCode());
			}
		}
		else if (!keyEvent.isAltKey() && !keyEvent.isCtrlKey() && !keyEvent.isShiftKey())
			btn = keyMap.get(keyEvent.getKeyCode());
		else if (!keyEvent.isAltKey() && !keyEvent.isCtrlKey() && keyEvent.isShiftKey())
		{
			if (keyEvent.getKeyCode() == KeyEvent.F2)
			{
				btn = btnQuickForm;
			}
		}
		fireButtonClickEvent(keyEvent, btn);
	}

	private void fireButtonClickEvent(KeyEvent keyEvent, ToolBarButton btn)
	{
		if (btn != null) {
			prevKeyEventTime = System.currentTimeMillis();
        	prevKeyEvent = keyEvent;
			keyEvent.stopPropagation();
			if (!btn.isDisabled() && btn.isVisible()) {
				Events.sendEvent(btn, new Event(Events.ON_CLICK, btn));
				//client side script to close combobox popup
				String script = "var w=zk.Widget.$('#" + btn.getUuid()+"'); " +
						"zWatch.fire('onFloatUp', w);";
				Clients.response(new AuScript(script));
			}
		}
	}

	/**
	 *
	 * @param visible
	 */
	public void setVisibleAll(boolean visible)
	{
		for (ToolBarButton btn : buttons.values())
		{
			btn.setVisible(visible);
		}
		for (Menuitem mn : menuItems.values())
		{
			mn.setVisible(visible);
		}
	}

	/**
	 *
	 * @param buttonName
	 * @param visible
	 */
	public void setVisible(String buttonName, boolean visible)
	{
		ToolBarButton btn = buttons.get(buttonName);
		if (btn != null)
		{
			btn.setVisible(visible);
		}
		Menuitem mn = menuItems.get(btn);
		if (mn != null)
		{
			mn.setVisible(visible);
		}
	}

	/**
	 *
	 * @param windowNo
	 */
	public void setWindowNo(int windowNo) {
		this.windowNo = windowNo;
	}

	/**
	 * Enable/disable export button
	 * @param b
	 */
	public void enableExport(boolean b) {
		if (btnExport != null)
			btnExport.setDisabled(!b);
    	enableMenuitem(btnExport, b);
	}

	/**
	 * Enable/disable file import button
	 * @param b
	 */
	public void enableFileImport(boolean b) {
		if (btnFileImport != null)
			btnFileImport.setDisabled(!b);
    	enableMenuitem(btnFileImport, b);
	}

	/**
	 * Enable/disable CSV import button
	 * @param b
	 */
	/*
	public void enableCSVImport(boolean b) {
		if (btnCSVImport != null)
			btnCSVImport.setDisabled(!b);
    	enableMenuitem(btnCSVImport, b);
	}
	*/
	
	private boolean ToolBarMenuRestictionLoaded = false;

	public void updateToolbarAccess(int xAD_Window_ID) {
		if (ToolBarMenuRestictionLoaded)
			return;
		
		ADWindow adwindow = ADWindow.findADWindow(this);
		
		if ("N".equalsIgnoreCase(Env.getContext(Env.getCtx(), "#ShowAdvanced")))
		{
			List<String> advancedList = adwindow.getWindowAdvancedButtonList();
			for (String advancedName : advancedList)
			{
				for (Component p = this.getFirstChild(); p != null; p = p.getNextSibling()) {
					if (p instanceof ToolBarButton) {
						if ( advancedName.equals(((ToolBarButton)p).getName()) ) {
							this.removeChild(p);
							break;
						}
					} else if (p instanceof Menupopup) {
						for (Component p1 = p.getFirstChild(); p1 != null; p1 = p1.getNextSibling()) {
							if ( p1 instanceof Menuitem && advancedName.equals((((Menuitem)p1).getValue())) ) {
								p.removeChild(p1);
								break;
							}					
						}					
					}
				}

			}	// All advanced btn
		}

		dynamicDisplay();
		
		ToolBarMenuRestictionLoaded = true;
	}

	

	public void enableProcessButton(boolean b) {
		if (btnProcess != null) {
			btnProcess.setDisabled(!b);
			enableMenuitem(btnProcess, b);
		}
	}

	public void dynamicDisplay() {
		List<Toolbarbutton> customButtons = new ArrayList<Toolbarbutton>();
		for(ToolbarCustomButton toolbarCustomBtn : toolbarCustomButtons) {
			toolbarCustomBtn.dynamicDisplay(menuItems.get(toolbarCustomBtn.getToolbarbutton()) != null);
			customButtons.add(toolbarCustomBtn.getToolbarbutton());
			if (menuItems.get(toolbarCustomBtn.getToolbarbutton()) != null)
				menuItems.get(toolbarCustomBtn.getToolbarbutton()).setVisible(toolbarCustomBtn.getToolbarbutton().isVisible());
		}
		
		ADWindow adwindow = ADWindow.findADWindow(this);
		GridTab gridTab = adwindow.getADWindowContent().getActiveGridTab();
		if (gridTab != null) {
			for (Component p = this.getFirstChild(); p != null; p = p.getNextSibling()) {
				if (p instanceof ToolBarButton) {
					if (!customButtons.contains(p) && !p.isVisible())
						p.setVisible(true);
				}
			}
			
			
		}
	}

	@Override
	public void onPageDetached(Page page) {
		super.onPageDetached(page);
		try {
			SessionManager.getSessionApplication().getKeylistener().removeEventListener(Events.ON_CTRL_KEY, this);
		} catch (Exception e) {}
	}

	@Override
	public void onPageAttached(Page newpage, Page oldpage) {
		super.onPageAttached(newpage, oldpage);
		if (newpage != null) {
			SessionManager.getSessionApplication().getKeylistener().addEventListener(Events.ON_CTRL_KEY, this);
		}
	}
	
	private void mobileInit() {	
		LayoutUtils.addSclass("mobile", this);
		addEventListener("onOverflowButton", evt -> onOverflowButton(evt));
		this.setWidgetOverride("toolbarScrollable", "function (wgt) {\n" + 
				"	var total = jq(wgt.$n()).width();\n" + 
				"	var w = wgt.firstChild;\n" + 
				"	var a = " + !mobileShowMoreButtons.isEmpty() + ";\n" + 
				"\n" + 
				"	// make sure all images are loaded.\n" + 
				"	if (zUtl.isImageLoading()) {\n" + 
				"		var f = arguments.callee;\n" + 
				"		setTimeout(function () {\n" + 
				"			return f(wgt);\n" + 
				"		}, 20);\n" + 
				"		return;\n" + 
				"	}\n" + 
				"	for (; w; w = w.nextSibling) {\n" + 
				"		var ow = jq(w.$n()).outerWidth(true);\n" +
				"		if (typeof ow != 'undefined') {total -= ow;}\n" + 
				"		if (total < 0 && w.className == 'zul.wgt.Toolbarbutton') {\n" + 
				"			break;\n" + 
				"		}\n" + 
				"	}\n" + 
				"	if (w && total < 0) {\n" + 
				"       var event = new zk.Event(wgt, 'onOverflowButton', w.uuid, {toServer: true}); \n" +
				"       zAu.send(event); \n" +
				"	}\n" +
				"	else if (a) {\n" + 
				"       var event = new zk.Event(wgt, 'onOverflowButton', null, {toServer: true}); \n" +
				"       zAu.send(event); \n" +
				"	}\n" +
				"}");
		addEventListener(Events.ON_AFTER_SIZE, (AfterSizeEvent evt) -> onAfterSize(evt));
		
		addCallback(AFTER_PAGE_ATTACHED, t -> afterPageAttached());
	}

	private void afterPageAttached() {
		Component p = getParent();
		while (p != null) {
			if (p instanceof Tabpanel) {
				p.addEventListener(WindowContainer.ON_MOBILE_SET_SELECTED_TAB, evt -> this.invalidate());
				break;
			}
			p = p.getParent();
		}
	}

	private void onAfterSize(AfterSizeEvent evt) {
		int width = evt.getWidth();
		if (width != prevWidth) {
			prevWidth = width;
			if (overflowButton != null)
				overflowButton.detach();	
			if (overflowPopup != null)
				overflowPopup.detach();
			if (overflows != null) {
				for (ToolBarButton btn : overflows) {
					appendChild(btn);
				}
				overflows = null;
			}
			Events.postEvent("onPostAfterSize", this, null);
		}
	}

	private void onOverflowButton(Event evt) {
		overflows = new ArrayList<>();
		String uuid = (String) evt.getData();
		if (uuid != null) {
			boolean overflowStarted = false;
			for(Component comp : getChildren()) {
				if (comp instanceof ToolBarButton) {
					if (overflowStarted) {
						overflows.add((ToolBarButton) comp);
					} else if (comp.getUuid().equals(uuid)) {
						overflows.add((ToolBarButton) comp);
						overflowStarted = true;
					}
				}
			}
		}
		//Add at the end of the overflow those buttons marked as isShowMore
		for (ToolBarButton toolbarButton : mobileShowMoreButtons)
			overflows.add(toolbarButton);
		if (overflows.size() > 0) {
			overflowButton = new A();
			overflowButton.setIconSclass("z-icon-ShowMore");
			overflowButton.setSclass("font-icon-toolbar-button toolbar-button mobile-overflow-link");
			appendChild(overflowButton);
			overflowPopup = new Popup();
			overflowPopup.addEventListener(Events.ON_OPEN, (OpenEvent oe) -> {
				if (!oe.isOpen()) {
					overflowPopup.setAttribute("popup.close", System.currentTimeMillis());
				}
			});
			appendChild(overflowPopup);
			for(ToolBarButton btn : overflows) {
				overflowPopup.appendChild(btn);
			}			
			overflowButton.addEventListener(Events.ON_CLICK, e -> {
				Long ts = (Long) overflowPopup.removeAttribute("popup.close");
				if (ts != null) {
					if (System.currentTimeMillis() - ts.longValue() < 500) {
						return;
					}
				}
				overflowPopup.open(overflowButton, "after_end");
			});
			
			int cnt = 0;
			for(Component c : getChildren()) {
				if (c instanceof ToolBarButton)
					cnt++;
			}
			if (overflows.size() >= cnt) {
				String script = "var e = jq('#" + getUuid() + "');";
				script = script + "var b=zk.Widget.$('#" + overflowPopup.getUuid() + "'); ";
				script = script + "b.setWidth(e.css('width'));";
				Clients.evalJavaScript(script);
			} else {
				overflowPopup.setWidth(null);
			}
		}
	}
	
	public void onPostAfterSize() {
		if (this.getPage() != null) {
			String script = "var w = zk.Widget.$('#" + getUuid() + "'); w.toolbarScrollable(w);";
			Clients.evalJavaScript(script);
		}
	}
	
    public void setPressed(String buttonName, boolean pressed) {
    	getButton(buttonName).setPressed(pressed);
    	if (menuItems.get(getButton(buttonName)) != null) {
    		if (pressed)
    			menuItems.get(getButton(buttonName)).setSclass("z-toolbarbutton-checked font-icon-menuitem");
    		else {
    			menuItems.get(getButton(buttonName)).setClass("");
    			menuItems.get(getButton(buttonName)).setClass("font-icon-menuitem z-menu-item");
    		}
    	}
    }

	/**
	 * @return
	 */
	public int getQuickFormTabHrchyLevel()
	{
		return quickFormTabHrchyLevel;
	}

	/**
	 * @param quickFormHrchyTabLevel
	 */
	public void setQuickFormTabHrchyLevel(int quickFormHrchyTabLevel)
	{
		this.quickFormTabHrchyLevel = quickFormHrchyTabLevel;
	}
    
	/*
    public void refreshUserQuery(int AD_Tab_ID, int AD_UserQuery_ID) {
    	fQueryName.getItems().clear();
        userQueries = MUserQuery.get(Env.getCtx(), AD_Tab_ID);
        fQueryName.appendItem("");
        for (int i = 0; i < userQueries.length; i++) {
	       	Comboitem li = fQueryName.appendItem(userQueries[i].getName());
	       	li.setValue(userQueries[i].getAD_UserQuery_ID());
	       	if (AD_UserQuery_ID == userQueries[i].getAD_UserQuery_ID())
	       		fQueryName.setSelectedItem(li);
        }
        if (AD_UserQuery_ID <= 0 || fQueryName.getItemCount() <= 1)
        	fQueryName.setValue("");
    }
    
	public void setSelectedUserQuery(MUserQuery selectedUserQuery) {
		this.selectedUserQuery = selectedUserQuery;
		if (selectedUserQuery != null)
			fQueryName.setValue(selectedUserQuery.getName());
	}

	public int getAD_UserQuery_ID() {
		if (selectedUserQuery == null)
			return 0;
		return selectedUserQuery.getAD_UserQuery_ID();
	}
	*/
	//Quynhnv.x8: add print
	/*
	private Menupopup popPrint;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void enablePrint(GridTab curTab, boolean enabled)
    {
        //boolean enablePrint = false;
        
        if(popPrint == null)
            return;
        
        this.removeChild(popPrint);
        
        popPrint = new Menupopup();
        
        this.appendChild(popPrint);
        //btnPrint.setPopup(popPrint);
        
        int AD_Process_ID = curTab.getAD_Process_ID();
        
        
        if (AD_Process_ID > 0)
        {
        	MProcess process = MProcess.get(Env.getCtx(), AD_Process_ID);
            Menuitem mItem = new Menuitem(process.get_Translation(MProcess.COLUMNNAME_Name));
            popPrint.appendChild(mItem);
            mItem.setTooltiptext(process.get_Translation(MProcess.COLUMNNAME_Description));
            mItem.setAttribute("AD_Process_ID", process.getAD_Process_ID());
            mItem.addEventListener(Events.ON_CLICK, new EventListener()
            {
                public void onEvent(Event event) throws Exception
                {
                    doOnMenuClick(event);
                }
            });
            
            //enablePrint = true;
        }
        
        //this.btnPrint.setDisabled(!enablePrint);
        Env.setContext(Env.getCtx(), "#Active_AD_Table_ID", curTab.getAD_Table_ID());
    	Env.setContext(Env.getCtx(), "#Active_Record_ID", curTab.getRecord_ID());
    }
	
	private void doOnMenuClick(Event event)
    {
        this.event = event;
        Menuitem cComponent = (Menuitem) event.getTarget();
        if (cComponent != null)
        {
            int AD_Process_ID = Util.getInt(cComponent.getAttribute("AD_Process_ID"));
            if (AD_Process_ID > 0)
            {
                String methodName = "onPrint";
                
                Iterator<ToolbarListener> listenerIter = listeners.iterator();
                while(listenerIter.hasNext())
                {
                    try
                    {
                        ToolbarListener tListener = listenerIter.next();
                        Method method = tListener.getClass().getMethod(methodName, new Class[] { int.class } );
                        method.invoke(tListener, new Object[]{ AD_Process_ID });
                    }
                    catch(SecurityException e)
                    {
                        log.log(Level.SEVERE, "Could not invoke Toolbar listener method: " + methodName + "()", e);
                    }
                    catch(NoSuchMethodException e)
                    {
                        log.log(Level.SEVERE, "Could not invoke Toolbar listener method: " + methodName + "()", e);
                    }
                    catch(IllegalArgumentException e)
                    {
                        log.log(Level.SEVERE, "Could not invoke Toolbar listener method: " + methodName + "()", e);
                    }
                    catch(IllegalAccessException e)
                    {
                        log.log(Level.SEVERE, "Could not invoke Toolbar listener method: " + methodName + "()", e);
                    }
                    catch(InvocationTargetException e)
                    {
                        log.log(Level.SEVERE, "Could not invoke Toolbar listener method: " + methodName + "()", e);
                    }
                }
            }
        }
        
        this.event = null;
    }
	*/
}
