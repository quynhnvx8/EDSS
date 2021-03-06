/******************************************************************************
 * Copyright (C) 2012 Trek Global                                             *
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
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

package eone.webui.apps;

import static eone.base.model.SystemIDs.COLUMN_AD_FIELD_AD_FIELDGROUP_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.compiere.apps.form.TabEditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;

import eone.base.model.GridField;
import eone.base.model.MField;
import eone.base.model.MLookup;
import eone.base.model.MLookupFactory;
import eone.base.model.X_AD_FieldGroup;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.KeyNamePair;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.LayoutUtils;
import eone.webui.component.Column;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.EditorBox;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Group;
import eone.webui.component.Label;
import eone.webui.component.ListHead;
import eone.webui.component.ListHeader;
import eone.webui.component.ListItem;
import eone.webui.component.Listbox;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.editor.WEditor;
import eone.webui.editor.WImageEditor;
import eone.webui.editor.WNumberEditor;
import eone.webui.editor.WStringEditor;
import eone.webui.editor.WTableDirEditor;
import eone.webui.editor.WYesNoEditor;
import eone.webui.editor.WebEditorFactory;
import eone.webui.event.ValueChangeEvent;
import eone.webui.event.ValueChangeListener;
import eone.webui.panel.ADForm;
import eone.webui.panel.IFormController;
import eone.webui.panel.WTabEditorForm;
import eone.webui.session.SessionManager;
import eone.webui.util.ZKUpdateUtil;

/**
 *
 * @author Juan David Arboleda
 * @author Carlos Ruiz
 *
 */
public class WTabEditor extends TabEditor implements IFormController, EventListener<Event>, ValueChangeListener
{
	// TODO: create messages Property, VisibleFields, NonVisibleField

	private WTabEditorForm   tabform =null;

	private Borderlayout	mainLayout	= new Borderlayout();

	/**	Window No			*/
	public int            	m_WindowNo = 0;

	private Label			displayedLabel	= new Label();
	private Label			nodisplayedLabel = new Label();

	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	WEditor editorName = null;
	WEditor editorAD_FieldGroup_ID = null;
	WEditor editorIsDisplayed = null;
	WEditor editorSeqNo = null;
	WEditor editorXPosition = null;
	WEditor editorColumnSpan = null;
	WEditor editorNumLines = null;

	private Listbox visible =  new Listbox();
	private Listbox invisible =  new Listbox();

	// The grid components
	Group currentGroup;
	ArrayList<Row> rowList;

	Map<Cell, GridField> mapCellField = new HashMap<Cell, GridField>();
	Map<Cell, Integer> mapEmptyCellField = new HashMap<Cell, Integer>();

	Grid form;
	Vlayout centerVLayout;
	Vlayout westVLayout ;

	private static final int POSSEQMULTIPLIER = 10000000;
	
	public WTabEditor()
	{
		tabform = new WTabEditorForm(this);
		LayoutUtils.addSclass("tab-editor-form", tabform);
	}

	public void initForm() {
		try
		{
			initMFields(tabform.getWindowNo(), tabform.getProcessInfo().getRecord_ID());
			dynList();
			jbInit();
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "WTabEditor.preInit", ex);
		}
	}

	/**
	 *  Initialize List of visible and non visible Fields
	 */
	private void dynList()
	{
		visible.addEventListener(Events.ON_SELECT, this);
		ZKUpdateUtil.setVflex(visible, "70");
		visible.setItemDraggable(true);
		visible.setDroppable("true");
		visible.addOnDropListener(this);

		invisible.addEventListener(Events.ON_SELECT, this);
		ZKUpdateUtil.setVflex(invisible, "30");
		invisible.setItemDraggable(true);
		invisible.setDroppable("true");
		invisible.addOnDropListener(this);

		updateLists(null);
	}   //  dynList

	/**
	 *  Initialize Tab panel editor
	 *  Same createUI algorithm used on ADTabPanel
	 */
	private void createUI() {
		mapCellField.clear();
		mapEmptyCellField.clear();

		currentGroup = null;
		rowList = null;
	    
		form = new Grid();
		form.makeNoStrip();
		ZKUpdateUtil.setHflex(form, "1");
        ZKUpdateUtil.setHeight(form, null);
        form.setVflex(false);

    	int numCols = getNumColumns();
    	if (numCols <= 0) {
    		numCols=4;
    	}

    	// set size in percentage per column leaving a MARGIN on right
    	Columns columns = new Columns();
    	form.appendChild(columns);
    	int equalWidth = 98 / numCols;

    	for (int h=0;h<numCols;h++){
    		Column col = new Column();
    		ZKUpdateUtil.setWidth(col, equalWidth + "%");
    		columns.appendChild(col);
    	}

    	Rows rows = form.newRows();
        Row row = new Row();
        int actualxpos = 0;
        int lastseq  = 0;
        String currentFieldGroup = null;
        for (GridField gridField : getGridFields())
        {
        	MField field = getMField(gridField.getAD_Field_ID());

        	if (!gridField.isDisplayed() || gridField.isToolbarOnlyButton())
        		continue;

        	// field group
        	String fieldGroup = gridField.getFieldGroup();
        	if (!Util.isEmpty(fieldGroup) && !fieldGroup.equals(currentFieldGroup)) // group changed
        	{
        		currentFieldGroup = fieldGroup;
  
          		while(numCols - actualxpos + 1 > 0) {
        			  row.appendCellChild(createSpacer(), 1);
        			  setLastCellProps(row.getLastCell(), actualxpos, field.getSeqNo());
        			  actualxpos++;
        		}
        		row.setGroup(currentGroup);
        		rows.appendChild(row);
                if (rowList != null)
        			rowList.add(row);

        		rowList = new ArrayList<Row>();

        		if (X_AD_FieldGroup.FIELDGROUPTYPE_Label.equals(gridField.getFieldGroupType()))
        		{
        			row = new Row();
        			Label groupLabel = new Label(fieldGroup);
        			row.appendCellChild(groupLabel, numCols);
        			rows.appendChild(row);

        			row = new Row();
        			Separator separator = new Separator();
        			separator.setBar(true);
        			row.appendCellChild(separator, numCols);
        			rows.appendChild(row);
        			currentGroup = null;
        		}
        		else
        		{
        			Group rowg = new Group(fieldGroup);
        			Cell cell = (Cell) rowg.getFirstChild();
        			cell.setSclass("z-group-inner");
        			cell.setColspan(numCols+1);
//        			rowg.appendChild(cell);
        			if (X_AD_FieldGroup.FIELDGROUPTYPE_Tab.equals(gridField.getFieldGroupType()) || gridField.getIsCollapsedByDefault())
        			{
        				rowg.setOpen(false);
        			}
        			currentGroup = rowg;
        			rows.appendChild(rowg);
        		}

        		row = new Row();
        		actualxpos = 0;
        	}

			//normal field
        	if (gridField.getXPosition() <= actualxpos) {
        		// Fill right part of the row with spacers until number of columns
        		while(numCols - actualxpos + 1 > 0) {
        			  row.appendCellChild(createSpacer(), 1);
        			  setLastCellProps(row.getLastCell(), actualxpos, field.getSeqNo());
        			  actualxpos++;
        		}
        		row.setGroup(currentGroup);
        		rows.appendChild(row);
                if (rowList != null)
        			rowList.add(row);
        		row=new Row();
        		actualxpos = 0;
        	}
    		// Fill left part of the field
        	if (gridField.getXPosition()-1 - actualxpos > 0) {
        		row.appendCellChild(createSpacer(), gridField.getXPosition()-1 - actualxpos);
    			setLastCellProps(row.getLastCell(), actualxpos, field.getSeqNo());
        	}
        	boolean paintLabel = ! (gridField.getDisplayType() == DisplayType.Button || gridField.getDisplayType() == DisplayType.YesNo || gridField.isFieldOnly()); 
        	if (gridField.isHeading())
        		actualxpos = gridField.getXPosition();
        	else
        		actualxpos = gridField.getXPosition() + gridField.getColumnSpan()-1 + (paintLabel ? 1 : 0);

        	if (! gridField.isHeading()) {

        		WEditor editor = WebEditorFactory.getEditor(null, gridField, false);

        		if (editor != null) // Not heading
        		{
        			editor.setReadWrite(true);

        			if (editor.getComponent() instanceof EditorBox) {
            			// disable button on URL editor
        				EditorBox editorbox = (EditorBox) editor.getComponent();
        				editorbox.getButton().setDisabled(true);
        			} else if (editor instanceof WImageEditor) {
            			// disable button on Image editor
        				editor.setReadWrite(false);
        			}
        			if (paintLabel) {
        				Div div = new Div();
        				div.setStyle("text-align: right;");
        				Label label = editor.getLabel();
        				div.appendChild(label);
        				if (label.getDecorator() != null)
        					div.appendChild(label.getDecorator());
        				row.appendCellChild(div,1);
        				setLastCellProps(row.getLastCell(), gridField);
        			}
        			row.appendCellChild(editor.getComponent(), gridField.getColumnSpan());
    				setLastCellProps(row.getLastCell(), gridField);

        			//stretch component to fill grid cell
        			editor.fillHorizontal();

        		}
        	}
        	else // just heading
        	{
        		//display just a label if we are "heading only"
        		Label label = new Label(gridField.getHeader());
        		Div div = new Div();
        		div.setStyle("text-align: center;");
        		row.appendCellChild(createSpacer());
        		div.appendChild(label);
        		row.appendCellChild(div);
				setLastCellProps(row.getLastCell(), gridField);
        	}
        	lastseq = field.getSeqNo();
        }

  		while(numCols - actualxpos + 1 > 0) {
  			  row.appendCellChild(createSpacer(), 1);
			  lastseq = lastseq + 10;
		      // make every empty space droppable at the end 
			  setLastCellProps(row.getLastCell(), actualxpos, lastseq);
			  actualxpos++;
		}
		row.setGroup(currentGroup);
		rows.appendChild(row);
        if (rowList != null)
			rowList.add(row);
	}

	private void setLastCellProps(Cell lastCell, GridField field) {
		lastCell.setDraggable("true");
		lastCell.setDroppable("true");
		lastCell.addEventListener(Events.ON_DROP, this);
		lastCell.addEventListener(Events.ON_CLICK, this);
		lastCell.addEventListener(Events.ON_DOUBLE_CLICK, this);
		mapCellField.put(lastCell, field);
	}

	private void setLastCellProps(Cell lastCell, int actualxpos, int seqNo) {
		lastCell.setDroppable("true");
		lastCell.addEventListener(Events.ON_DROP, this);
		int value = (actualxpos+1) * POSSEQMULTIPLIER + seqNo;
		mapEmptyCellField.put(lastCell, value);
	}

	private Component createSpacer() {
		return new Space();
	}

	private int getNumColumns() {
		int maxcol=0;
        for (GridField gridField : getGridFields())
        {
        	if (!gridField.isDisplayed() || gridField.isToolbarOnlyButton())
        		continue;
        	int col = gridField.getXPosition() + gridField.getColumnSpan();
        	if (gridField.isFieldOnly()
        		|| (gridField.getDisplayType() == DisplayType.Button && !gridField.isToolbarOnlyButton())
    			|| gridField.getDisplayType() == DisplayType.YesNo) {
        		col--;
        	}
        	if (col > maxcol) {
        		maxcol = col;
        	}
        }
		return maxcol;
	}

	/**
	 *  Initialize Grid of Field's Properties
	 *  return @Grid
	 */
	private Grid createPropertiesGrid()
	{
		Grid gridView = GridFactory.newGridLayout();
		//
		Columns columns = new Columns();
		gridView.appendChild(columns);
    	//
    	Column  column = new Column();
    	columns.appendChild(column);
    	ZKUpdateUtil.setHflex(column, "min");
    	column.setAlign("right");

    	column = new Column();
    	columns.appendChild(column);
    	ZKUpdateUtil.setHflex(column, "1");
		Rows rows = new Rows();
		gridView.appendChild(rows);
		
		Row row = null;
		
		row = new Row();
		Group group = new Group(Msg.getMsg(Env.getCtx(), "Property"));
		Cell cell = (Cell) group.getFirstChild();
		cell.setSclass("z-group-inner");
		cell.setColspan(2);
		group.setOpen(true);
		rows.appendChild(group);
		
		row = new Row();
		Label labelName =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_Name));
		editorName = new WStringEditor(MField.COLUMNNAME_Name, false, true, false, 0, 0, null, null);
		ZKUpdateUtil.setHflex(((WStringEditor) editorName).getComponent(), "1");
		row.appendChild(labelName.rightAlign());
		row.appendChild(editorName.getComponent());
		// editorName.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelIsDisplayed =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_IsDisplayed));
		editorIsDisplayed = new WYesNoEditor(MField.COLUMNNAME_IsDisplayed, "", labelIsDisplayed.getValue(), false, true, false);
		row.appendChild(labelIsDisplayed.rightAlign());
		row.appendChild(editorIsDisplayed.getComponent());
		// editorIsDisplayed.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelSeqNo =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_SeqNo));
		editorSeqNo = new WNumberEditor(MField.COLUMNNAME_SeqNo, false, true, false, DisplayType.Integer, labelSeqNo.getValue());
		row.appendChild(labelSeqNo.rightAlign());
		row.appendChild(editorSeqNo.getComponent());
		// editorSeqNo.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelAD_FieldGroup_ID =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_AD_FieldGroup_ID));
		MLookup lookup = MLookupFactory.get (Env.getCtx(), tabform.getWindowNo(), 0, COLUMN_AD_FIELD_AD_FIELDGROUP_ID, DisplayType.TableDir);
		editorAD_FieldGroup_ID = new WTableDirEditor(MField.COLUMNNAME_AD_FieldGroup_ID, false, false, true, lookup);
		ZKUpdateUtil.setHflex(((WTableDirEditor) editorAD_FieldGroup_ID).getComponent(), "1");
		row.appendChild(labelAD_FieldGroup_ID.rightAlign());
		row.appendChild(editorAD_FieldGroup_ID.getComponent());
		editorAD_FieldGroup_ID.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelXPosition =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_XPosition));
		editorXPosition = new WNumberEditor(MField.COLUMNNAME_XPosition, true, false, true, DisplayType.Integer, labelXPosition.getValue());
		row.appendChild(labelXPosition.rightAlign());
		row.appendChild(editorXPosition.getComponent());
		editorXPosition.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelColumnSpan =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_ColumnSpan));
		editorColumnSpan = new WNumberEditor(MField.COLUMNNAME_ColumnSpan, true, false, true, DisplayType.Integer, labelColumnSpan.getValue());
		row.appendChild(labelColumnSpan.rightAlign());
		row.appendChild(editorColumnSpan.getComponent());
		editorColumnSpan.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelNumLines =  new Label(Msg.getElement(Env.getCtx(), MField.COLUMNNAME_NumLines));
		editorNumLines = new WNumberEditor(MField.COLUMNNAME_NumLines, true, false, true, DisplayType.Integer, labelNumLines.getValue());
		row.appendChild(labelNumLines.rightAlign());
		row.appendChild(editorNumLines.getComponent());
		editorNumLines.addValueChangeListener(this);
		row.setGroup(group);
		rows.appendChild(row);
		
		row = new Row();
		Separator esep = new Separator("horizontal");
		esep.setSpacing("10px");
		row.appendCellChild(esep, 2);
		row.setGroup(group);
		rows.appendChild(row);
		
		row = new Row();
		row.appendCellChild(confirmPanel, 2);
		row.setGroup(group);
		rows.appendChild(row);

	    return gridView;
	}

	/**
	 * 	Static init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		tabform.setSizable(true);
		tabform.setClosable(true);
		tabform.setMaximizable(true);
		ZKUpdateUtil.setWidth(tabform, "95%");
		ZKUpdateUtil.setHeight(tabform, "95%");
		tabform.appendChild (mainLayout);
		LayoutUtils.addSclass("tab-editor-form-content", mainLayout);
		tabform.setBorder("normal");

		confirmPanel.addActionListener(Events.ON_CLICK, this);

		Grid propGrid = createPropertiesGrid();

		East east = new East();
		LayoutUtils.addSclass("tab-editor-form-east-panel", east);
		mainLayout.appendChild(east);
		east.appendChild(propGrid);
		ZKUpdateUtil.setWidth(east, "320px");

		ListHead visibleHead = new ListHead();
		visibleHead.setParent(visible);
		ListHeader visibleHeader = new ListHeader();
		displayedLabel.setText(Msg.getMsg(Env.getCtx(), "VisibleFields"));
		visibleHeader.appendChild(displayedLabel);
		visibleHeader.setParent(visibleHead);

		ListHead invisibleHead = new ListHead();
		invisibleHead.setParent(invisible);
		ListHeader invisibleHeader = new ListHeader();
		nodisplayedLabel.setText(Msg.getMsg(Env.getCtx(), "NonVisibleFields"));
		invisibleHeader.appendChild(nodisplayedLabel);
		invisibleHeader.setParent(invisibleHead);

		westVLayout = new Vlayout();
		ZKUpdateUtil.setHeight(westVLayout, "100%");
		westVLayout.appendChild(visible);
		westVLayout.appendChild(invisible);

		createUI();

		centerVLayout = new Vlayout();
		ZKUpdateUtil.setHeight(centerVLayout, "100%");
		centerVLayout.appendChild(form);
		centerVLayout.setStyle("overflow:auto");

		Center center = new Center();
		LayoutUtils.addSclass("tab-editor-form-center-panel", center);
		mainLayout.appendChild(center);
		center.appendChild(centerVLayout);

		West west = new West();
		LayoutUtils.addSclass("tab-editor-form-west-panel", west);
		mainLayout.appendChild(west);
		west.appendChild(westVLayout);
		west.setCollapsible(true);
		west.setSplittable(true);
		ZKUpdateUtil.setWidth(west, "200px");
	}	//	jbInit

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	dispose

	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void onEvent (Event e) throws Exception 
	{
		// select an item within the list -- set it active and show the properties
		if (Events.ON_SELECT.equals(e.getName()) && e.getTarget() instanceof Listbox) {
			Listbox list = (Listbox) e.getTarget();
			if (list.getSelectedItem() != null) {
				int fieldid = list.getSelectedItem().getValue();
				MField field = getMField(fieldid);
				if (field != null) {
					setActiveMField(field);
					setProperties(field);
					setBackgroundField(field);
				}
			}
		}

		// Check event ONCLICK on a cell -- set it active and show the properties
		else if (Events.ON_CLICK.equals(e.getName()) && (e.getTarget() instanceof Cell)) {
			GridField gridField = mapCellField.get(e.getTarget());
			if (gridField != null) {
				MField field = getMField(gridField.getAD_Field_ID());
				if (field != null) {
					setActiveMField(field);
					setProperties(field);
					setBackgroundField(field);
					// select the field on the visible list and set focus
					for (Listitem item : visible.getItems()) {
						if (field.getAD_Field_ID() == (Integer) item.getValue()) {
							visible.setSelectedItem(item);
							break;
						}
					}
				}
			}
		}

		else if (Events.ON_DOUBLE_CLICK.equals(e.getName()) && (e.getTarget() instanceof Cell)) {
			MouseEvent me = (MouseEvent) e;
			// by default expand column span by 1 on double click
			int mult = 1;
			if ((me.getKeys() & MouseEvent.CTRL_KEY) > 0) {
				// control key pressed - shrink
				mult = -1;
			}
			GridField gridField = mapCellField.get(e.getTarget());
			if (gridField != null) {
				MField field = getMField(gridField.getAD_Field_ID());
				if (mult == -1 && field.getColumnSpan() <= 1) {
					// shrinking when colspan = 1 is not valid
				} else {
					field.setColumnSpan(field.getColumnSpan()+mult);
				}
				// select the field on the visible list and set focus
				for (Listitem item : visible.getItems()) {
					if (field.getAD_Field_ID() == (Integer) item.getValue()) {
						visible.setSelectedItem(item);
						break;
					}
				}
				setActiveMField(field);
				setProperties(field);
				repaintGrid();
				setBackgroundField(field);
			}
		}

		else if (e instanceof DropEvent ) {
			DropEvent me = (DropEvent) e;
			ListItem startItem = null;
			if (me.getDragged() instanceof ListItem) {
				startItem = (ListItem) me.getDragged();
			} else if (me.getDragged() instanceof Cell) {
				startItem = getItemFromCell((Cell) me.getDragged());
			}
			ListItem endItem = null;
			if (me.getTarget() instanceof ListItem) {
				endItem = (ListItem) me.getTarget();
			} else if (me.getTarget() instanceof Cell) {
				endItem = getItemFromCell((Cell) me.getTarget());
				if (endItem == null) {
					// check empty cells
					Integer posseq = mapEmptyCellField.get(me.getTarget());
					if (posseq != null) {
						int actualxpos = posseq / POSSEQMULTIPLIER;
						int seqno = posseq - (actualxpos * POSSEQMULTIPLIER);
						MField field = getMField((Integer) startItem.getValue());
						field.setSeqNo(seqno-5);
						field.setIsDisplayed(true);
						field.setXPosition(actualxpos);
						resortArrays();
						setProperties(field); // seqno could change
						updateLists(field);
						repaintGrid();
						setActiveMField(field);
						setBackgroundField(field);
						return;
					}
				}
			}
			if (startItem.getListbox() == visible && endItem.getListbox() == invisible)
			{
				// item moved from visible to invisible
				MField field = getMField((Integer) startItem.getValue());
				setActiveMField(field);
				field.setSeqNo(0);
				field.setIsDisplayed(false);
				field.setXPosition(1);
				resortArrays();
				setProperties(field); // seqno could change
				updateLists(field);
				repaintGrid();
				setBackgroundField(field);
			}
			else if (startItem.getListbox() == invisible && endItem.getListbox() == visible && (Integer) startItem.getValue() != 0)
			{
				// item moved from invisible to visible
				MField field = getMField((Integer) startItem.getValue());
				MField fieldTo = getMField((Integer) endItem.getValue());
				field.setSeqNo(fieldTo.getSeqNo()-5);
				field.setIsDisplayed(true);
				field.setXPosition(fieldTo.getXPosition());
				resortArrays();
				setProperties(field); // seqno could change
				updateLists(field);
				repaintGrid();
				setActiveMField(field);
				setBackgroundField(field);
			}
			else if (startItem.getListbox() == visible && endItem.getListbox() == visible)
			{
				// item moved from visible to visible - reorder
				MField field = getMField((Integer) startItem.getValue());
				MField fieldTo = getMField((Integer) endItem.getValue());
				field.setSeqNo(fieldTo.getSeqNo()-5);
				field.setIsDisplayed(true);
				field.setXPosition(fieldTo.getXPosition());
				resortArrays();
				setProperties(field); // seqno could change
				updateLists(field);
				repaintGrid();
				setActiveMField(field);
				setBackgroundField(field);
			}
		}

		else if (e.getTarget().getId().equals("Cancel"))
		{
			tabform.detach();
		}

		//	OK - Save
		else if (e.getTarget().getId().equals("Ok"))
		{
			if (cmd_save())
				tabform.detach();
		}
	}	//	actionPerformed

	private ListItem getItemFromCell(Cell cell) {
		GridField field = mapCellField.get(cell);
		if (field != null) {
			for (int idx = 0; idx < visible.getItemCount(); idx++) {
				ListItem item = visible.getItemAtIndex(idx);
				if (item != null && field.getAD_Field_ID() == (Integer) item.getValue()) {
					return item;
				}
			}
		}
		return null;
	}

	private void setBackgroundField(MField field) {
	    Iterator<Entry<Cell, GridField>> it = mapCellField.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Cell, GridField> pairs = (Map.Entry<Cell, GridField>)it.next();
	        Cell cell = pairs.getKey();
	        GridField gridField = pairs.getValue();
	        if (gridField.getAD_Field_ID() == field.getAD_Field_ID()) {
				cell.setStyle("background-color: #BBC2DB;");
	        } else {
				cell.setStyle("");
	        }
		}
		
		
	}

	private void setProperties(MField field) {
		editorName.setValue(field.getName());
		editorSeqNo.setValue(field.getSeqNo());
		editorIsDisplayed.setValue(field.isDisplayed());
		if (field.getAD_FieldGroup_ID() > 0)
			editorAD_FieldGroup_ID.setValue(field.getAD_FieldGroup_ID());
		else
			editorAD_FieldGroup_ID.setValue(null);
		editorXPosition.setValue(field.getXPosition());
		editorColumnSpan.setValue(field.getColumnSpan());
		editorNumLines.setValue(field.getNumLines());

		GridField gridField = getGridField(field);
    	String fieldGroup = "";
    	String fieldGroupType = "";
    	if (field.getAD_FieldGroup_ID() > 0) {
    		fieldGroup = field.getAD_FieldGroup().getName();
    		fieldGroupType = field.getAD_FieldGroup().getFieldGroupType();
    	}
    	gridField.getVO().IsDisplayed = field.isDisplayed();
    	gridField.getVO().FieldGroup = fieldGroup;
    	gridField.getVO().FieldGroupType = fieldGroupType;
		gridField.getVO().XPosition = field.getXPosition();
		gridField.getVO().ColumnSpan = field.getColumnSpan();
		gridField.getVO().NumLines = field.getNumLines();
	
	}

	public ADForm getForm()
	{
		return tabform;
	}

	private void updateLists(MField focusField){
		visible.removeAllItems();
		invisible.removeAllItems();

		for (MField field : getMFields())
		{
			GridField gridField = getGridField(field);
			if (!field.isActive() || gridField.isToolbarOnlyButton())
				continue;
			KeyNamePair pair = new KeyNamePair(field.getAD_Field_ID(), field.getName());
			if (field.isDisplayed()) {
				visible.addItem(pair);
				if (field == focusField)
					visible.setSelectedKeyNamePair(pair);
			} else {
				invisible.addItem(pair);
				if (field == focusField)
					invisible.setSelectedKeyNamePair(pair);
			}
		} 
		if  (invisible.getItems().isEmpty()){
			 invisible.addItem(new KeyNamePair(0, "..."));
		}
	}
	
	@Override
	public void valueChange(ValueChangeEvent e) {
		// changed a value on the properties editors
		MField field = getActiveMField();
		if (field != null) {
			String propertyName = e.getPropertyName();
			int intvalue = 0;
			if (e.getNewValue() != null)
				intvalue = (Integer) e.getNewValue();
			if (MField.COLUMNNAME_AD_FieldGroup_ID.equals(propertyName)) {
				field.setAD_FieldGroup_ID(intvalue);
			} else if (MField.COLUMNNAME_XPosition.equals(propertyName)) {
				field.setXPosition(intvalue);
			} else if (MField.COLUMNNAME_ColumnSpan.equals(propertyName)) {
				int prevcolspan = field.getColumnSpan();
				if (!field.set_ValueOfColumnReturningBoolean(MField.COLUMNNAME_ColumnSpan, intvalue))
					field.setColumnSpan(prevcolspan);
			} else if (MField.COLUMNNAME_NumLines.equals(propertyName)) {
				field.setNumLines(intvalue);
			}
			setProperties(field);
			repaintGrid();
		}
	}

	private void repaintGrid() {
		centerVLayout.removeChild(form);
		if (form.getRows() != null)
			form.removeChild(form.getRows());

		createUI();
		centerVLayout.appendChild(form);
	}

} // WTabEditor
