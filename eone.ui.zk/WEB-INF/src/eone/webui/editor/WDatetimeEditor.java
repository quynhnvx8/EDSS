
package eone.webui.editor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

import eone.base.model.GridField;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.Msg;
import eone.webui.ValuePreference;
import eone.webui.component.DatetimeBox;
import eone.webui.event.ContextMenuEvent;
import eone.webui.event.ContextMenuListener;
import eone.webui.event.ValueChangeEvent;
import eone.webui.window.WFieldRecordInfo;


public class WDatetimeEditor extends WEditor implements ContextMenuListener
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CHANGE, Events.ON_OK};
    @SuppressWarnings("unused")
	private static final CLogger logger;

    static
    {
        logger = CLogger.getCLogger(WDatetimeEditor.class);
    }

    private Timestamp oldValue = new Timestamp(0);

    /**
    *
    * @param gridField
    */
    public WDatetimeEditor(GridField gridField)
    {
    	this(gridField, false, null);
    }
    
    /**
     *
     * @param gridField
     */
    public WDatetimeEditor(GridField gridField, boolean tableEditor, IEditorConfiguration editorConfiguration)
    {
        super(new DatetimeBox(), gridField, tableEditor, editorConfiguration);
        init();
    }


	/**
	 * Constructor for use if a grid field is unavailable
	 *
	 * @param label
	 *            column name (not displayed)
	 * @param description
	 *            description of component
	 * @param mandatory
	 *            whether a selection must be made
	 * @param readonly
	 *            whether or not the editor is read only
	 * @param updateable
	 *            whether the editor contents can be changed
	 */
	public WDatetimeEditor (String label, String description, boolean mandatory, boolean readonly, boolean updateable)
	{
		super(new DatetimeBox(), label, description, mandatory, readonly, updateable);
		setColumnName("Datetime");
		init();
	}

	public WDatetimeEditor()
	{
		this(Msg.getMsg(Env.getCtx(), "DateTime"), Msg.getMsg(Env.getCtx(), "DateTime"), false, false, true);
	}   // WDatetimeEditor

	/**
	 *
	 * @param columnName
	 * @param mandatory
	 * @param readonly
	 * @param updateable
	 * @param title
	 */
	public WDatetimeEditor(String columnName, boolean mandatory, boolean readonly, boolean updateable,
			String title)
	{
		super(new DatetimeBox(), columnName, title, null, mandatory, readonly, updateable);
		init();
	}

	private void init()
	{
		popupMenu = new WEditorPopupMenu(false, false, isShowPreference());
		popupMenu.addMenuListener(this);
		addChangeLogMenu(popupMenu);
		if (gridField != null)
			getComponent().getDatebox().setPlaceholder(gridField.getPlaceholder());
		
		if (gridField != null && gridField.getFormatPattern() != null) 
			getComponent().getDatebox().setFormat(gridField.getFormatPattern());//
		else {
			getComponent().getDatebox().setFormat("dd-MM-yyyy");
		}
	}

	public void onEvent(Event event)
    {
		if (Events.ON_CHANGE.equalsIgnoreCase(event.getName()) || Events.ON_OK.equalsIgnoreCase(event.getName()))
		{
	        Date date = getComponent().getValue();
	        Timestamp newValue = null;

	        if (date != null)
	        {
	            newValue = Timestamp.valueOf(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	        }
	        if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
	    	    return;
	    	}
	        if (oldValue == null && newValue == null) {
	        	return;
	        }
	        ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
	        super.fireValueChange(changeEvent);
	        oldValue = newValue;
		}
    }

    @Override
    public String getDisplay()
    {
    	// Elaine 2008/07/29
    	return getComponent().getText();
    	//
    }

    @Override
    public Object getValue()
    {
    	// Elaine 2008/07/25
    	if(getComponent().getValue() == null) return null;
    	return Timestamp.valueOf(getComponent().getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    	//
    }

    @Override
    public void setValue(Object value)
    {
    	if (value == null || value.toString().trim().length() == 0)
    	{
    		oldValue = null;
    		getComponent().setValue(null);
    	}
    	else if (value instanceof Timestamp)
        {
    		LocalDateTime localTime =((Timestamp)value).toLocalDateTime();
    		getComponent().getDatebox().setValueInLocalDateTime(localTime);
    		getComponent().getTimebox().setValueInLocalDateTime(localTime);
    		getComponent().getTimebox().setFormat("HH:mm");
            oldValue = (Timestamp)value;
        }
    	else
    	{
    		try
    		{
    			getComponent().setText(value.toString());
    		} catch (Exception e) {}
    		if (getComponent().getValue() != null)
    			oldValue = Timestamp.valueOf(getComponent().getDatebox().getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    		else
    			oldValue = null;
    	}
    }

	@Override
	public DatetimeBox getComponent() {
		return (DatetimeBox) component;
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}


	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }


	@Override
	public void onMenu(ContextMenuEvent evt) {
		if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		} 
		else if (WEditorPopupMenu.PREFERENCE_EVENT.equals(evt.getContextEvent()))
		{
			if (isShowPreference())
				ValuePreference.start (getComponent(), this.getGridField(), getValue());
		}
	}


	/* (non-Javadoc)
	 */
	@Override
	protected void setFieldStyle(String style) {
		getComponent().getDatebox().setStyle(style);
		getComponent().getTimebox().setStyle(style);
	}
	
}
