/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package eone.webui.editor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

import eone.base.model.GridField;
import eone.util.CLogger;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.Language;
import eone.webui.ValuePreference;
import eone.webui.apps.AEnv;
import eone.webui.component.Timebox;
import eone.webui.event.ContextMenuEvent;
import eone.webui.event.ContextMenuListener;
import eone.webui.event.ValueChangeEvent;
import eone.webui.window.WFieldRecordInfo;

/**
 *
 * @author Low Heng Sin
 */
public class WTimeEditor extends WEditor implements ContextMenuListener
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CHANGE, Events.ON_OK};
    private static final CLogger logger;

    static
    {
        logger = CLogger.getCLogger(WDateEditor.class);
    }

    private Timestamp oldValue = new Timestamp(0);

    /**
    *
    * @param gridField
    */
   public WTimeEditor(GridField gridField)
   {
	   this(gridField, false, null);
	   this.displayType = gridField.getDisplayType();
   }
   
    /**
     * 
     * @param gridField
     * @param tableEditor
     * @param editorConfiguration
     */
    public WTimeEditor(GridField gridField, boolean tableEditor, IEditorConfiguration editorConfiguration)
    {
        super(new Timebox(), gridField, tableEditor, editorConfiguration);
        init();
    }

    /**
	 *
	 * @param columnName
	 * @param mandatory
	 * @param readonly
	 * @param updateable
	 * @param title
	 */
	public WTimeEditor(String columnName, boolean mandatory, boolean readonly, boolean updateable,
			String title)
	{
		super(new Timebox(), columnName, title, null, mandatory, readonly, updateable);
		this.displayType = gridField.getDisplayType();
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
	public WTimeEditor (String label, String description, boolean mandatory, boolean readonly, boolean updateable)
	{
		super(new Timebox(), label, description, mandatory, readonly, updateable);
		setColumnName("Time");
		this.displayType = gridField.getDisplayType();
		init();
	}

	public WTimeEditor()
	{
		this("Time", "Time", false, false, true);
		this.displayType = gridField.getDisplayType();
		init();
	}   // VDate

	private int displayType;
	
	private void init()
	{
		getComponent().setCols(10);
		popupMenu = new WEditorPopupMenu(false, false, isShowPreference());
		popupMenu.addMenuListener(this);
		addChangeLogMenu(popupMenu);
		if (gridField != null)
			getComponent().setPlaceholder(gridField.getPlaceholder());
		
		Language lang = AEnv.getLanguage(Env.getCtx());
		SimpleDateFormat format = DisplayType.getDateFormat(displayType, lang);
		if (gridField != null && gridField.getFormatPattern() != null)
			getComponent().setFormat(gridField.getFormatPattern());
		else
			getComponent().setFormat(format.toPattern());
	}
	
	public void onEvent(Event event)
    {
		if (Events.ON_CHANGE.equalsIgnoreCase(event.getName()) || Events.ON_OK.equalsIgnoreCase(event.getName()))
		{
	        Date date = getComponent().getValue();
	        Timestamp newValue = null;

	        if (date != null)
	        {
	            newValue = new Timestamp(date.getTime());
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
    	return new Timestamp(getComponent().getValue().getTime());
    	//
    }

    @Override
    public void setValue(Object value)
    {
    	if (value == null)
    	{
    		oldValue = null;
    		getComponent().setValue(null);
    	}
    	else if (value instanceof Timestamp)
        {
            getComponent().setValue((Timestamp)value);
            oldValue = (Timestamp)value;
        }
        else
        {
            logger.log(Level.SEVERE, "New field value is not of type timestamp");
        }
    }

	@Override
	public Timebox getComponent() {
		return (Timebox) component;
	}

	@Override
	public boolean isReadWrite() {
		return !getComponent().isReadonly();
	}


	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setReadonly(!readWrite);
	}

	@Override
	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

	@Override
	public void fillHorizontal() {
		//do nothing, can't stretch a timebox
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
			return;
		}
	}

}
