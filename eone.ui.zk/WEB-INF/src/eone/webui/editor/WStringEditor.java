
package eone.webui.editor;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

import eone.base.model.GridField;
import eone.util.DisplayType;
import eone.webui.LayoutUtils;
import eone.webui.ValuePreference;
import eone.webui.adwindow.ADWindow;
import eone.webui.adwindow.AbstractADWindowContent;
import eone.webui.component.Combobox;
import eone.webui.component.Textbox;
import eone.webui.event.ContextMenuEvent;
import eone.webui.event.ContextMenuListener;
import eone.webui.event.DialogEvents;
import eone.webui.event.ValueChangeEvent;
import eone.webui.session.SessionManager;
import eone.webui.window.WFieldRecordInfo;
import eone.webui.window.WTextEditorDialog;

/**
 *
 * @author  QUYNHNV.X8 Mod 15/09/2021
 */
public class WStringEditor extends WEditor implements ContextMenuListener
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CHANGE, Events.ON_OK};

    private String oldValue;

	private AbstractADWindowContent adwindowContent;

    /**
     * to ease porting of swing form
     */
    public WStringEditor()
    {
    	this("String", false, false, true, 30, 30, "", null);
    }

    /**
     * 
     * @param gridField
     */
    public WStringEditor(GridField gridField) {
    	this(gridField, false);
    }

    /**
     * 
     * @param gridField
     * @param tableEditor
     */
    public WStringEditor(GridField gridField, boolean tableEditor)
    {
    	this(gridField, tableEditor, null);
    }
    
    /**
     * 
     * @param gridField
     * @param tableEditor
     * @param editorConfiguration
     */
    public WStringEditor(GridField gridField, boolean tableEditor, IEditorConfiguration editorConfiguration)
    {
        super(gridField.isAutocomplete() ? new Combobox() : new Textbox(), gridField, tableEditor, editorConfiguration);

        if (gridField.getVFormat() != null && !gridField.getVFormat().isEmpty())
        	getComponent().setWidgetListener("onBind", "jq(this).mask('" + gridField.getVFormat() + "');");

        init(null);//gridField.getObscureType(). B??? c??i n??y
    }

    /**
     * to ease porting of swing form
     * @param columnName
     * @param mandatory
     * @param isReadOnly
     * @param isUpdateable
     * @param displayLength
     * @param fieldLength
     * @param wVFormat
     * @param obscureType
     */
    public WStringEditor(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
    		int displayLength, int fieldLength, String wVFormat, String obscureType)
    {
    	super(new Textbox(), columnName, null, null, mandatory, isReadOnly,isUpdateable);

    	if (wVFormat != null &&  !wVFormat.isEmpty())
    		getComponent().setWidgetListener("onBind", "jq(this).mask('" + wVFormat + "');");

    	init(obscureType);
    }

    @Override
    public org.zkoss.zul.Textbox getComponent() {
    	return (org.zkoss.zul.Textbox) component;
    }

    @Override
	public boolean isReadWrite() {
		return !getComponent().isReadonly();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setReadonly(!readWrite);
	}

	private void init(String obscureType)
    {
		setChangeEventWhenEditing (true);
		if (gridField != null)
		{
	        getComponent().setMaxlength(gridField.getFieldLength());
	        int displayLength = gridField.getDisplayLength();
	        if (displayLength <= 0 || displayLength > MAX_DISPLAY_LENGTH)
	        {
	            displayLength = MAX_DISPLAY_LENGTH;
	        }
	        if (!tableEditor)
	        	getComponent().setCols(displayLength);
	        if (tableEditor)
	        	getComponent().setMultiline(false);
	        else if (gridField.getDisplayType() == DisplayType.Text)
	        {
	            getComponent().setMultiline(true);
	        }
	        else if (gridField.getDisplayType() == DisplayType.TextLong)
	        {
	            getComponent().setMultiline(true);
	        }
	        else if (gridField.getDisplayType() == DisplayType.Memo)
	        {
	            getComponent().setMultiline(true);
	        }
	        else
	            getComponent().setMultiline(false);
	        if (! gridField.isAutocomplete()) // avoid -> Combobox doesn't support multiple rows
	        	getComponent().setRows(gridField.getNumLines() <= 0 || tableEditor ? 1 : gridField.getNumLines());

	        if (getComponent() instanceof Textbox)
	        	((Textbox)getComponent()).setObscureType(obscureType);


	        if(!(this instanceof WPasswordEditor)){ // check password field
	        	popupMenu = new WEditorPopupMenu(false, gridField.isAutocomplete(), isShowPreference());
	        	addTextEditorMenu(popupMenu);
	        	addChangeLogMenu(popupMenu);
	        }

	        if (gridField.isAutocomplete()) {
	        	Combobox combo = (Combobox)getComponent();
	        	combo.setAutodrop(true);
	        	combo.setAutocomplete(true);
	        	combo.setButtonVisible(false);
	        	List<String> items = gridField.getEntries();
	        	for(String s : items) {
	        		combo.appendItem(s);
	        	}
	        }
	        if ("email".equalsIgnoreCase(gridField.getColumnName()))
	        	getComponent().setClientAttribute("type", "email");
	        if (gridField != null)
	        	getComponent().setPlaceholder(gridField.getPlaceholder());
		}
    }

	public void onEvent(Event event)
    {
		boolean isStartEdit = INIT_EDIT_EVENT.equalsIgnoreCase (event.getName());
    	if (Events.ON_CHANGE.equals(event.getName()) || Events.ON_OK.equals(event.getName()) || isStartEdit)
    	{
	        String newValue = getComponent().getValue();
	        if (!isStartEdit && oldValue != null && newValue != null && oldValue.equals(newValue)) {
	    	    return;
	    	}
	        if (!isStartEdit && oldValue == null && newValue == null) {
	        	return;
	        }
	        ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
	        
	        changeEvent.setIsInitEdit(isStartEdit);
	        
	        super.fireValueChange(changeEvent);
	        oldValue = getComponent().getValue(); // IDEMPIERE-963 - check again the value could be changed by callout
    	}
    }

    @Override
    public String getDisplay()
    {
        return getComponent().getValue();
    }

    @Override
    public Object getValue()
    {
        return getComponent().getValue();
    }

    @Override
    public void setValue(Object value)
    {
        if (value != null)
        {
            getComponent().setValue(value.toString());
        }
        else
        {
            getComponent().setValue("");
        }
        oldValue = getComponent().getValue();
    }

    protected void setTypePassword(boolean password)
    {
        if (password)
        {
            getComponent().setType("password");
        }
        else
        {
            getComponent().setType("text");
        }
    }

    @Override
    public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

    public void onMenu(ContextMenuEvent evt)
	{
		if (WEditorPopupMenu.PREFERENCE_EVENT.equals(evt.getContextEvent()))
		{
			if (isShowPreference())
				ValuePreference.start (getComponent(), this.getGridField(), getValue());
			return;
		}
		else if (WEditorPopupMenu.EDITOR_EVENT.equals(evt.getContextEvent()))
		{
			adwindowContent = findADWindowContent();
			boolean isHtml = false;
			if ((   gridField.getDisplayType() == DisplayType.Text 
				 || gridField.getDisplayType() == DisplayType.TextLong
				 || gridField.getDisplayType() == DisplayType.Memo)
				&& adwindowContent != null
				&& adwindowContent.getActiveGridTab() != null) {
				isHtml = adwindowContent.getActiveGridTab().getValueAsBoolean("IsHtml");
			}
			final WTextEditorDialog dialog = new WTextEditorDialog(gridField.getVO().Header, getDisplay(),
					isReadWrite(), gridField.getFieldLength(), isHtml);
			dialog.addEventListener(DialogEvents.ON_WINDOW_CLOSE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (adwindowContent != null) {
						adwindowContent.hideBusyMask();
					}
					if (!dialog.isCancelled()) {
						getComponent().setText(dialog.getText());
						String newText = getComponent().getValue();
				        ValueChangeEvent changeEvent = new ValueChangeEvent(WStringEditor.this, WStringEditor.this.getColumnName(), oldValue, newText);
				        WStringEditor.super.fireValueChange(changeEvent);
				        oldValue = newText;
					}
				}
			});
			if (adwindowContent != null) 
			{
				adwindowContent.getComponent().getParent().appendChild(dialog);
				adwindowContent.showBusyMask(dialog);
				LayoutUtils.openOverlappedWindow(adwindowContent.getComponent().getParent(), dialog, "middle_center");
			}
			else
			{
				SessionManager.getAppDesktop().showWindow(dialog);
			}			
			dialog.focus();
		}
		else if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
		else if (WEditorPopupMenu.REQUERY_EVENT.equals(evt.getContextEvent()))
		{
			actionRefresh();
		}
	}
    
	@Override
	public void dynamicDisplay() {
		super.dynamicDisplay();
		actionRefresh();
	}

	public void actionRefresh() {
		//refresh auto complete list
		if (gridField.isAutocomplete()) {
			Combobox combo = (Combobox)getComponent();
			List<String> items = gridField.getEntries();
			combo.removeAllItems();
			for(String s : items) {
				combo.appendItem(s);
			}
		}
	}

	private AbstractADWindowContent findADWindowContent() {
		Component parent = getComponent().getParent();
		while(parent != null) {
			if (parent.getAttribute(ADWindow.AD_WINDOW_ATTRIBUTE_KEY) != null) {
				ADWindow adwindow = (ADWindow) parent.getAttribute(ADWindow.AD_WINDOW_ATTRIBUTE_KEY);
				return adwindow.getADWindowContent();
			}
			parent = parent.getParent();
		}
		return null;
	}

}
