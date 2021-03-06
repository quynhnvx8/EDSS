/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package eone.webui.apps;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.apps.form.TrxMaterial;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;
import org.zkoss.zul.South;

import eone.base.model.MLookup;
import eone.base.model.MLookupFactory;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.Msg;
import eone.webui.ClientInfo;
import eone.webui.LayoutUtils;
import eone.webui.adwindow.ADTabpanel;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Label;
import eone.webui.component.Panel;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.editor.WDateEditor;
import eone.webui.editor.WSearchEditor;
import eone.webui.editor.WTableDirEditor;
import eone.webui.event.ValueChangeEvent;
import eone.webui.event.ValueChangeListener;
import eone.webui.panel.ADForm;
import eone.webui.panel.CustomForm;
import eone.webui.panel.IFormController;
import eone.webui.panel.StatusBarPanel;
import eone.webui.session.SessionManager;
import eone.webui.util.ZKUpdateUtil;

/**
 * Material Transaction History
 *
 * @author Jorg Janke
 * @version $Id: VTrxMaterial.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class WTrxMaterial extends TrxMaterial
	implements IFormController, EventListener<Event>, ValueChangeListener
{
	private CustomForm form = new CustomForm();	

	/** GridController          */
	private ADTabpanel  m_gridController = null;

	//
	private Panel mainPanel = new Panel();
	private Borderlayout mainLayout = new Borderlayout();
	private Panel parameterPanel = new Panel();
	private Label orgLabel = new Label();
	private WTableDirEditor orgField;
	private Label productLabel = new Label();
	private WSearchEditor productField;
	private Label dateFLabel = new Label();
	private WDateEditor dateFField;
	private Label dateTLabel = new Label();
	private WDateEditor dateTField;
	private Label mtypeLabel = new Label();
	private WTableDirEditor mtypeField;
	private Grid parameterLayout = GridFactory.newGridLayout();
	private Panel southPanel = new Panel();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true, true, false, false, false, true, false);
	private StatusBarPanel statusBar = new StatusBarPanel();

	private int noOfColumns;


	/**
	 *	Initialize Panel
	 */
	public WTrxMaterial()
	{
		log.info("");
		try
		{
			m_WindowNo = form.getWindowNo();
			dynParameter();
			zkInit();
			dynInit();		
			if (ClientInfo.isMobile())
				ClientInfo.onClientInfo(form, this::onClientInfo);
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
	}	//	init
	
	/**
	 *  Static Init
	 *  @throws Exception
	 */
	void zkInit() throws Exception
	{
		form.appendChild(mainPanel);
		mainPanel.setStyle("width: 100%; height: 100%; border: none; padding: 0; margin: 0");
		mainPanel.appendChild(mainLayout);
		ZKUpdateUtil.setWidth(mainLayout, "100%");
		ZKUpdateUtil.setHeight(mainLayout, "100%");
		parameterPanel.appendChild(parameterLayout);
		//
		orgLabel.setText(Msg.translate(Env.getCtx(), "AD_Org_ID"));
		productLabel.setText(Msg.translate(Env.getCtx(), "Product"));
		dateFLabel.setText(Msg.translate(Env.getCtx(), "DateFrom"));
		dateTLabel.setText(Msg.translate(Env.getCtx(), "DateTo"));
		mtypeLabel.setText(Msg.translate(Env.getCtx(), "MovementType"));
		//
		North north = new North();
		mainLayout.appendChild(north);
		north.appendChild(parameterPanel);
		north.setSplittable(true);
		north.setCollapsible(true);
		north.setAutoscroll(true);
		LayoutUtils.addSlideSclass(north);
		
		layoutParameters();
		//
		southPanel.appendChild(confirmPanel);
		southPanel.appendChild(statusBar);
		South south = new South();
		south.setStyle("border: none");
		mainLayout.appendChild(south);
		south.appendChild(southPanel);
		ZKUpdateUtil.setHeight(southPanel, "64px");
		ZKUpdateUtil.setHeight(south, "64px");
		ZKUpdateUtil.setHeight(confirmPanel, "32px");
		ZKUpdateUtil.setHeight(statusBar, "32px");
		
		ZKUpdateUtil.setWidth(southPanel, "100%");
		ZKUpdateUtil.setWidth(confirmPanel, "100%");
		ZKUpdateUtil.setWidth(statusBar, "100%");
		
		LayoutUtils.addSclass("status-border", statusBar);
	}   //  jbInit

	protected void layoutParameters() {
		noOfColumns = 6;
		if (ClientInfo.maxWidth(639))
			noOfColumns = 2;
		else if (ClientInfo.maxWidth(ClientInfo.MEDIUM_WIDTH-1))
			noOfColumns = 4;
		
		int childCnt = 0;
		Rows rows = parameterLayout.newRows();
		Row row = rows.newRow();
		row.appendCellChild(orgLabel.rightAlign());
		ZKUpdateUtil.setHflex(orgField.getComponent(), "true");
		row.appendCellChild(orgField.getComponent());
		childCnt += 2;
		if ((childCnt % noOfColumns) ==0 )
			row = rows.newRow();
		row.appendCellChild(mtypeLabel.rightAlign());
		ZKUpdateUtil.setHflex(mtypeField.getComponent(), "true");
		row.appendCellChild(mtypeField.getComponent());
		childCnt += 2;
		if ((childCnt % noOfColumns) ==0 )
			row = rows.newRow();
		if (noOfColumns == 6)
		{
			row.appendCellChild(dateFLabel.rightAlign());
			row.appendCellChild(dateFField.getComponent());
			childCnt += 2;
			if ((childCnt % noOfColumns) ==0 )
				row = rows.newRow();
		}
		
		childCnt += 2;
		if ((childCnt % noOfColumns) ==0 )
			row = rows.newRow();
		row.appendCellChild(productLabel.rightAlign());
		ZKUpdateUtil.setHflex(productField.getComponent(), "true");
		row.appendCellChild(productField.getComponent());
		childCnt +=2;
		if ((childCnt % noOfColumns) ==0 )
			row = rows.newRow();
		if (noOfColumns < 6)
		{
			row.appendCellChild(dateFLabel.rightAlign());
			row.appendCellChild(dateFField.getComponent());
			childCnt += 2;
			if ((childCnt % noOfColumns) ==0 )
				row = rows.newRow();
		}
		row.appendCellChild(dateTLabel.rightAlign());
		row.appendCellChild(dateTField.getComponent());
	}

	/**
	 *  Initialize Parameter fields
	 *  @throws Exception if Lookups cannot be initialized
	 */
	private void dynParameter() throws Exception
	{
		Properties ctx = Env.getCtx();
		//  Organization
		MLookup orgLookup = MLookupFactory.get (ctx, m_WindowNo, 0, 3660, DisplayType.TableDir);
		orgField = new WTableDirEditor("AD_Org_ID", false, false, true, orgLookup);
		//  Product
		MLookup productLookup = MLookupFactory.get (ctx, m_WindowNo, 0, 3668, DisplayType.Search);
		productField = new WSearchEditor("M_Product_ID", false, false, true, productLookup);
		productField.addValueChangeListener(this);
		//  Movement Type
		MLookup mtypeLookup = MLookupFactory.get (ctx, m_WindowNo, 0, 3666, DisplayType.List);
		mtypeField = new WTableDirEditor("MovementType", false, false, true, mtypeLookup);
		//  Dates
		dateFField = new WDateEditor("DateFrom", false, false, true, Msg.getMsg(Env.getCtx(), "DateFrom"));
		dateTField = new WDateEditor("DateTo", false, false, true, Msg.getMsg(Env.getCtx(), "DateTo"));
		//
		confirmPanel.addActionListener(this);
		statusBar.setStatusLine("");
	}   //  dynParameter

	/**
	 *  Dynamic Layout (Grid).
	 * 	Based on AD_Window: Material Transactions
	 */
	private void dynInit()
	{
		super.dynInit(statusBar);
		//
		
		m_gridController = new ADTabpanel();
		m_gridController.init(null, m_WindowNo, m_mTab, m_mWindow);
		if (!m_gridController.isGridView())
			m_gridController.switchRowPresentation();
		Center center = new Center();
		mainLayout.appendChild(center);
		center.appendChild(m_gridController);
		ZKUpdateUtil.setVflex(m_gridController, "1");
		ZKUpdateUtil.setHflex(m_gridController, "1");
	}   //  dynInit


	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	dispose

	
	/**************************************************************************
	 *  Action Listener
	 *  @param e event
	 */
	public void onEvent (Event e)
	{
		if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			dispose();
		else if (e.getTarget().getId().equals(ConfirmPanel.A_REFRESH)
				|| e.getTarget().getId().equals(ConfirmPanel.A_OK))
			refresh();
		else if (e.getTarget().getId().equals(ConfirmPanel.A_ZOOM))
			zoom();
	}   //  actionPerformed

	
	/**************************************************************************
	 *  Property Listener
	 *  @param e event
	 */
	public void valueChange (ValueChangeEvent e)
	{
		if (e.getPropertyName().equals("M_Product_ID"))
			productField.setValue(e.getNewValue());
	}   //  vetoableChange


	
	/**************************************************************************
	 *  Refresh - Create Query and refresh grid
	 */
	private void refresh()
	{
		Object organization = orgField.getValue();
		Object product = productField.getValue();
		Object movementType = mtypeField.getValue();
		Timestamp movementDateFrom = (Timestamp)dateFField.getValue();
		Timestamp movementDateTo = (Timestamp)dateTField.getValue();
		
		refresh(organization, product, movementType, movementDateFrom, movementDateTo, statusBar);
		if (ClientInfo.maxHeight(ClientInfo.MEDIUM_HEIGHT-1))
			mainLayout.getNorth().setOpen(false);
	}   //  refresh

	/**
	 *  Zoom
	 */
	public void zoom()
	{
		super.zoom();

		//  Zoom
		AEnv.zoom(AD_Table_ID, Record_ID);
	}   //  zoom
	
	public ADForm getForm() 
	{
		return form;
	}

	protected void onClientInfo() 
	{
		if (noOfColumns > 0 && parameterLayout.getRows() != null)
		{
			int n = 6;
			if (ClientInfo.maxWidth(639))
				n = 2;
			else if (ClientInfo.maxWidth(ClientInfo.MEDIUM_WIDTH-1))
				n = 4;
			if (n != noOfColumns)
			{
				parameterLayout.getRows().detach();
				layoutParameters();
				form.invalidate();
			}
		}
	}
}   //  VTrxMaterial
