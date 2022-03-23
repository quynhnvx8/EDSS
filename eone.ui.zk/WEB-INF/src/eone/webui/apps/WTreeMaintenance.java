
package eone.webui.apps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.compiere.apps.form.TreeMaintenance;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import eone.base.model.MTree;
import eone.base.model.MTreeNode;
import eone.util.Env;
import eone.util.KeyNamePair;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.ClientInfo;
import eone.webui.component.Checkbox;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Label;
import eone.webui.component.Listbox;
import eone.webui.component.Panel;
import eone.webui.component.SimpleListModel;
import eone.webui.component.SimpleTreeModel;
import eone.webui.panel.ADForm;
import eone.webui.panel.CustomForm;
import eone.webui.panel.IFormController;
import eone.webui.session.SessionManager;
import eone.webui.util.TreeUtils;
import eone.webui.util.ZKUpdateUtil;


public class WTreeMaintenance extends TreeMaintenance implements IFormController, EventListener<Event>
{
	private CustomForm form = new CustomForm();	
	
	private Borderlayout	mainLayout	= new Borderlayout ();
	private Panel 			northPanel	= new Panel ();
	private Label			treeLabel	= new Label ();
	private Listbox			treeField;
	
	private Tree			centerTree;
	private ConfirmPanel panel = new ConfirmPanel(true);
	
	public WTreeMaintenance()
	{
		try
		{
			m_WindowNo = form.getWindowNo();
			panel.addActionListener(this);
			preInit();
			jbInit ();
			action_loadTree();
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "VTreeMaintenance.init", ex);
		}
	}	//	init
	
	/**
	 * 	Fill Tree Combo
	 */
	private void preInit()
	{
		treeField = new Listbox(getTreeData());
		treeField.setMold("select");
		treeField.addActionListener(this);
		treeField.setSelectedIndex(0);
		//
		centerTree = new Tree();
		centerTree.addEventListener(Events.ON_SELECT, this);
		centerTree.addEventListener(Events.ON_CLICK, this);
	}	//	preInit
	
	/**
	 * 	Static init
	 *	@throws Exception
	 */
	private void jbInit () throws Exception
	{
		
		ZKUpdateUtil.setWidth(form,"100%");
		ZKUpdateUtil.setHeight(form, "100%");
		form.setStyle("position: relative; padding: 0; margin: 0");
		form.appendChild (mainLayout);
		ZKUpdateUtil.setWidth(mainLayout, "100%");
		ZKUpdateUtil.setHeight(mainLayout, "100%");
		
		treeLabel.setText (Msg.translate(Env.getCtx(), "AD_Tree_ID"));
		
		North north = new North();
		mainLayout.appendChild(north);
		north.appendChild(northPanel);
		ZKUpdateUtil.setHflex(north, "1");
		ZKUpdateUtil.setVflex(north, "min");
		ZKUpdateUtil.setWidth(northPanel, "100%");
		ZKUpdateUtil.setVflex(northPanel, "min");
		//
		Hbox hbox = new Hbox();
		hbox.setStyle("padding: 3px;");
		hbox.setAlign("center");
		ZKUpdateUtil.setHflex(hbox, "1");
		ZKUpdateUtil.setVflex(hbox, "1");
		northPanel.appendChild(hbox);
		
		if (ClientInfo.maxWidth(ClientInfo.EXTRA_SMALL_WIDTH-1))
			treeField.setStyle("max-width: 200px");
		hbox.appendChild (treeLabel);
		hbox.appendChild (treeField);
		
		if (ClientInfo.maxWidth(ClientInfo.SMALL_WIDTH-1))
		{
			hbox = new Hbox();
			hbox.setAlign("center");
			hbox.setStyle("padding-top: 3px; padding-bottom: 3px;");
			ZKUpdateUtil.setWidth(hbox, "100%");
			ZKUpdateUtil.setVflex(hbox, "min");
			northPanel.appendChild(hbox);
		}
		else
		{
			hbox.appendChild (new Space());
		}
		
		
		Center center = new Center();
		mainLayout.appendChild(center);	
		center.appendChild(centerTree);
		ZKUpdateUtil.setVflex(centerTree, "1");
		ZKUpdateUtil.setHflex(centerTree, "1");
		center.setAutoscroll(true);
		
		South south = new South();
		mainLayout.appendChild(south);
		south.appendChild(panel);
		ZKUpdateUtil.setVflex(panel, "1");
		ZKUpdateUtil.setHflex(panel, "1");
		
		
	}	//	jbInit

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	dispose

	private Map<Integer, String> data = new HashMap<Integer, String>();
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void onEvent (Event e)
	{
		if (e.getTarget() == treeField)
		{
			action_loadTree();
		}
		else if (e.getTarget() == centerTree)
			onTreeSelection(e);
		
		else if (e.getTarget().getId().equals(ConfirmPanel.A_OK)) {
			KeyNamePair tree = treeField.getSelectedItem().toKeyNamePair();
			updateTreeByRole(data, tree.getKey());
			this.dispose();
		}
		else if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL)) {
			this.dispose();
		}
		
	}	//	actionPerformed


	private void action_loadTree() {
		action_loadTree(null);
	}

	/**
	 * 	Action: Fill Tree with all nodes
	 * @param filter 
	 */
	private void action_loadTree(String filter)
	{
		KeyNamePair tree = treeField.getSelectedItem().toKeyNamePair();
		
		m_tree = new MTree (Env.getCtx(), tree.getKey(), null);
		
		//	List
		SimpleListModel model = new SimpleListModel();
		ArrayList<ListItem> items = getTreeItemData();
		for (ListItem item : items) {
			if (Util.isEmpty(filter)) {
				model.addElement(item);
			} else {
				String valueItem = item.toString() == null ? "" : Util.deleteAccents(item.toString().toUpperCase());
				if (valueItem.contains(filter)) {
					model.addElement(item);
				}
			}
		}
		
		if (log.isLoggable(Level.CONFIG)) log.config("#" + model.getSize());
		
		//	Tree
		try {
			centerTree.setModel(null);
		} catch (Exception e) {
		}
		if (centerTree.getTreecols() != null)
			centerTree.getTreecols().detach();
		if (centerTree.getTreefoot() != null)
			centerTree.getTreefoot().detach();
		if (centerTree.getTreechildren() != null)
			centerTree.getTreechildren().detach();

		SimpleTreeModel.initADTree(centerTree, m_tree.getAD_Tree_ID(), m_WindowNo, true, "" + m_tree.getAD_Tree_ID());
		//if (m_tree.isLoadAllNodesImmediately())
		TreeUtils.collapseTree(centerTree, true);

	}	//	action_fillTree
	
	
	private void onTreeSelection (Event e)
	{
		Treeitem ti = centerTree.getSelectedItem();
		if (ti == null)
			return;
		Treerow tr = (Treerow) ti.getTreerow();
		Treecell tc = (Treecell) tr.getFirstChild();
		Checkbox cb = (Checkbox) tc.getFirstChild();
		
		if (cb.isChecked())
			cb.setChecked(false);
		else
		{
			cb.setChecked(true);			
		}
		
		
		DefaultTreeNode<?> stn = (DefaultTreeNode<?>) ti.getValue();
		MTreeNode tn = (MTreeNode)stn.getData();
		if (tn == null)
			return;
		
		//Add tree to data map update AD_TreeNode
		String checknode = "N";
		if (cb.isChecked()) {
			checknode = "Y";
		}
		data.put(tn.getNode_ID(), checknode);
		
		log.info(tn.toString());
		
		/*
		if (parent_ID > 0) {
			SimpleTreeModel modelTree = (SimpleTreeModel)(TreeModel<?>) centerTree.getModel();
			DefaultTreeNode<Object> mN = modelTree.find(modelTree.getRoot(), parent_ID);
			Treeitem it = centerTree.renderItemByPath(modelTree.getPath(mN));
			tr = (Treerow) it.getTreerow();
			tc = (Treecell) tr.getFirstChild();
			cb = (Checkbox) tc.getFirstChild();
			
			if (!checkCurrent)
				cb.setChecked(false);
			else
				cb.setChecked(true);
		}
		*/
	}	//	propertyChange

	
	public ADForm getForm() 
	{
		return form;
	}

}	//	VTreeMaintenance
