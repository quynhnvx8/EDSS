
package eone.webui.panel;

import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

import eone.base.model.GridTab;
import eone.base.model.MForm;
import eone.base.process.ProcessInfo;
import eone.webui.Extensions;
import eone.webui.component.Window;
import eone.webui.exception.ApplicationException;
import eone.webui.part.WindowContainer;
import eone.webui.session.SessionManager;
import eone.webui.util.ZKUpdateUtil;

/**
 *
 * @author QUYNHNV.X8
 */
public abstract class ADForm extends Window implements EventListener<Event>, IHelpContext
{
	private static final long serialVersionUID = -5183711788893823434L;
	protected static final CLogger logger;
    
    static
    {
        logger = CLogger.getCLogger(ADForm.class);
    }

    private int m_adFormId;
    protected int m_WindowNo;


	private String m_name;
	private String zoomLogic = null;
	private int zoomWindow = 0;

	private ProcessInfo m_pi;

	private IFormController m_customForm;

    protected ADForm()
    {
         m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);

         ZKUpdateUtil.setWidth(this, "100%");
         ZKUpdateUtil.setHeight(this, "99%");
         this.setStyle("position:relative");
         this.setContentSclass("adform-content");
    }

    public int getWindowNo()
    {
    	return m_WindowNo;
    }

    protected int getAdFormId()
    {
    	return m_adFormId;
    }

    

    protected void init(int adFormId, String name)
    {
        if(adFormId <= 0)
        {
	           throw new IllegalArgumentException("Form Id is invalid");
	   	}

        m_adFormId = adFormId;
        //window title
        setTitle(name);
        m_name = name;

        initForm();
        
        addEventListener(WindowContainer.ON_WINDOW_CONTAINER_SELECTION_CHANGED_EVENT, this);
    }

    abstract protected void initForm();

	/**
     * @return form name
     */
    public String getFormName() {
    	return m_name;
    }

	/**
	 * Create a new form corresponding to the specified identifier
	 *
	 * @param adFormID		The unique identifier for the form type
	 * @return The created form
	 */
	public static ADForm openForm (int adFormID)
	{
        return openForm(adFormID, null, null);
	}
	
    /**
     * Open a form base on it's ID
     *
     * @param adFormID
     * @param gridTab
     * @return
     */
	public static ADForm openForm (int adFormID, GridTab gridTab)
	{
        return openForm(adFormID, gridTab, null);
    }

    /**
     * Open a form base on it's ID and a Process Info parameters
     *
     * @param adFormID
     * @param gridTab
     * @param pi
     * @return
     */
    public static ADForm openForm (int adFormID, GridTab gridTab, ProcessInfo pi)
    {
		ADForm form;
		MForm mform = new MForm(Env.getCtx(), adFormID, null);
    	String formName = mform.getClassname();
    	String name = mform.get_Translation(MForm.COLUMNNAME_Name);

    	if (mform.get_ID() == 0 || formName == null)
    	{
			throw new ApplicationException("There is no form associated with the specified selection");
    	}
    	else
    	{
    		if (logger.isLoggable(Level.INFO)) logger.info("AD_Form_ID=" + adFormID + " - Class=" + formName);

    		form = Extensions.getForm(formName);
    		if (form != null)
    		{
    			form.gridTab = gridTab;
    			
                form.setProcessInfo(pi);
				form.init(adFormID, name);
				return form;
    		}
    		else
    		{
    			throw new ApplicationException("Failed to open " + formName);
    		}
    	}
	}	//	openForm

    /**
     *
     */
	public void onEvent(Event event) throws Exception
    {
		if (event.getName().equals(WindowContainer.ON_WINDOW_CONTAINER_SELECTION_CHANGED_EVENT)) {
    		//SessionManager.getAppDesktop().updateHelpContext("", getAdFormId());
		}
    }

	/**
	 * @param pi
	 */
	public void setProcessInfo(ProcessInfo pi) {
		m_pi = pi;
	}

	/**
	 * @return ProcessInfo
	 */
	public ProcessInfo getProcessInfo()
	{
		return m_pi;
	}

	public void setICustomForm(IFormController customForm)
	{
		m_customForm = customForm;
	}

	public IFormController getICustomForm()
	{
		return m_customForm;
	}
	
	
	public Mode getWindowMode() {
		return Mode.EMBEDDED;
	}
	
	private GridTab gridTab;
	
	public GridTab getGridTab()
	{
		return gridTab;
	}

	public String getZoomLogic() {
		return zoomLogic;
	}

	public void setZoomLogic(String zoomLogic) {
		this.zoomLogic = zoomLogic;
	}

	public int getZoomWindow() {
		return zoomWindow;
	}

	public void setZoomWindow(int zoomWindow) {
		this.zoomWindow = zoomWindow;
	}
}
