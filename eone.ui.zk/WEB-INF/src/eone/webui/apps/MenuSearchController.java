package eone.webui.apps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.ListSubModel;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.ListitemRendererExt;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.impl.LabelElement;

import eone.base.model.MTreeNode;
import eone.util.Env;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.component.ListItem;
import eone.webui.component.Listbox;
import eone.webui.component.ToolBarButton;
import eone.webui.theme.ThemeManager;
import eone.webui.util.TreeItemAction;
import eone.webui.util.TreeNodeAction;
import eone.webui.util.TreeUtils;
import eone.webui.util.ZKUpdateUtil;

/**
 * @author Admin
 *
 */
public class MenuSearchController implements EventListener<Event>{

	public static final String M_TREE_NODE_ATTR = "MTreeNode";
	
	private static final String ON_SEARCH_ECHO = "onSearchEcho";
	private static final String ON_LOAD_MORE = "onLoadMore";
	private static final String ONSELECT_TIMESTAMP = "onselect.timestamp";
	private static final String STAR_BUTTON_NAME = "Star";
	private static final String NEW_BUTTON_NAME = "New";
	private Tree tree;
	private Listbox listbox;
	private ListModelList<MenuItem> model;
	private Hbox layout;
	private ListModelList<MenuItem> fullModel;

	private static final String ON_POST_SELECT_TREEITEM_EVENT = "onPostSelectTreeitem";
	
	/**
	 * 
	 */
	public MenuSearchController(Tree tree) {
		this.tree = tree;
	}
	
	public void refreshModel() {
		final List<MenuItem> list = new ArrayList<MenuItem>();
		if (tree.getModel() == null) {
	    	TreeUtils.traverse(tree, new TreeItemAction() {
				public void run(Treeitem treeItem) {
					if (treeItem.isVisible())
						addTreeItem(list, treeItem);
				}
	    	});
		} else {
			TreeUtils.traverse(tree.getModel(), new TreeNodeAction() {
				public void run(DefaultTreeNode<?> treeNode) {
					addTreeItem(list, treeNode);
				}
	    	});
		}
		model = new ListModelList<MenuItem>(list, true);
		model.sort(new Comparator<MenuItem>() {
			
			@Override
			public int compare(MenuItem o1, MenuItem o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		}, true);
	}
	
	private void addTreeItem(List<MenuItem> list, DefaultTreeNode<?> treeNode) {
		MTreeNode mNode = (MTreeNode) treeNode.getData();
		if (!mNode.isLeaf())
			return;
		
		MenuItem item = new MenuItem();
		item.setLabel(mNode.getName());
		item.setDescription(mNode.getDescription());
		item.setImage(mNode.getImagePath());
		item.setData(treeNode);		
		list.add(item);		
	}

	private boolean isFolder(Treeitem treeItem) {
		List<Component> list = treeItem.getChildren();
		for (Component c : list) {
			if (c instanceof Treechildren && ((Treechildren)c).getChildren().size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	private void addTreeItem(List<MenuItem> list, Treeitem treeItem) {
		if (isFolder(treeItem))
			return;
		
		MenuItem item = new MenuItem();
		item.setLabel(getLabel(treeItem));
		item.setDescription(treeItem.getTooltiptext());
		
		item.setData(treeItem);
		list.add(item);		
		item.setType((String) treeItem.getAttribute("menu.type"));
	}
	
	private String getLabel(Treeitem treeItem) {
		String label = treeItem.getLabel();
        if (label == null || label.trim().length() == 0) 
        {
        	if (treeItem.getTreerow().getFirstChild().getFirstChild() != null &&
        		treeItem.getTreerow().getFirstChild().getFirstChild() instanceof LabelElement)
        	{
        		LabelElement element = (LabelElement) treeItem.getTreerow().getFirstChild().getFirstChild();
        		label = element.getLabel();
        	}
        }
        return label;
	}
	
	public void create(Component parent) {
		refreshModel();
		
		layout = new Hbox();
		layout.setStyle("overflow:none;");
		ZKUpdateUtil.setHeight(layout, "95%");
		ZKUpdateUtil.setWidth(layout, "95%");
		
		parent.appendChild(layout);
		
		listbox = new Listbox();
		listbox.setEmptyMessage(Util.cleanAmp(Msg.getMsg(Env.getCtx(), "FindZeroRecords")));
		listbox.setStyle("border: none");
		
		ZKUpdateUtil.setWidth(listbox, "99%");
		ZKUpdateUtil.setVflex(listbox, "true");
		layout.appendChild(listbox);
		listbox.setItemRenderer(new MenuItemRenderer());
		listbox.addEventListener(Events.ON_SELECT, this);
		listbox.addEventListener(ON_POST_SELECT_TREEITEM_EVENT, this);
		
		layout.addEventListener(ON_SEARCH_ECHO, this);
		layout.addEventListener(ON_LOAD_MORE, this);
		updateListboxModel(model);
		layout.appendChild(listbox);
		//FavouriteController controller = FavouriteController.getInstance(Executions.getCurrent().getSession());
		//controller.addDeletedCallback(t -> onDeletedCallback(t));
		//controller.addInsertedCallback(t -> onInsertedCallback(t));
	}

	@Override
	public void onEvent(Event event) throws Exception {
		if (Events.ON_SELECT.equals(event.getName())) {
			ListItem selected = listbox.getSelectedItem();
			if (selected == null) return;
			onSelect(selected, Boolean.FALSE);
		} else if (event.getName().equals(ON_POST_SELECT_TREEITEM_EVENT)) {
        	onPostSelectTreeitem((Boolean) event.getData());
		} else if (Events.ON_CLICK.equals(event.getName())) {
        	if (event.getTarget() instanceof ListItem) {
	        	ListItem item = (ListItem) event.getTarget();
	        	Long onSelect = (Long) item.getAttribute(ONSELECT_TIMESTAMP);
	        	if (onSelect == null) {
	        		onSelect(item, Boolean.FALSE);
	        	} else if (System.currentTimeMillis() - onSelect.longValue() > 1000) {
	        		onSelect(item, Boolean.FALSE);
	        	}
        	} else if (event.getTarget() instanceof ToolBarButton) {
        		ToolBarButton btn = (ToolBarButton) event.getTarget();
        		if (NEW_BUTTON_NAME.equals(btn.getName())) { 
        			ListItem item = null;
        			Component parent = event.getTarget();
        			while (parent != null) {
        				if (parent instanceof ListItem) {
        					item = (ListItem) parent;
        					break;
        				}
        				parent = parent.getParent();
        			}
        			if (item != null) {
        				onSelect(item, Boolean.TRUE);
        			}
        		}
        	}
        } else if (event.getName().equals(ON_SEARCH_ECHO)) {
        	onSearchEcho((String) event.getData());
        } else if (event.getName().equals(ON_LOAD_MORE)) {
        	loadMore();
        }	
	}
	
	private void onSelect(ListItem selected, Boolean newRecord) {
		MenuItem item = selected.getValue();
		if (item == null) return;
		if ("...".equals(item.getType())) {
			selected.setAttribute(ONSELECT_TIMESTAMP, System.currentTimeMillis());
			Clients.showBusy(selected, null);
			Events.echoEvent(ON_LOAD_MORE, layout, null);
		} else {
			selectTreeitem(item.getData(), newRecord);
			selected.setAttribute(ONSELECT_TIMESTAMP, System.currentTimeMillis());
		}
	}

	private void loadMore() {
		ListModel<MenuItem> listModel = listbox.getModel();
		ListModelList<MenuItem> lml = (ListModelList<MenuItem>) listModel;
		lml.remove(lml.size()-1);
		List<MenuItem> subList = fullModel.subList(50, fullModel.size());
		lml.addAll(subList);
		fullModel = null;
		listbox.setSelectedIndex(50);
		Clients.scrollIntoView(listbox.getSelectedItem());
	}
	
	private void selectTreeitem(Object node, Boolean newRecord) {
		if (Executions.getCurrent().getAttribute(listbox.getUuid()+".selectTreeitem") != null)
			return;
		
		Treeitem treeItem = null;
		if (node == null) {
			return;
		} else if (node instanceof Treeitem) {
		    treeItem = (Treeitem) node;
		} else {
			DefaultTreeNode<?> sNode = (DefaultTreeNode<?>) node;
			int[] path = tree.getModel().getPath(sNode);
			treeItem = tree.renderItemByPath(path);
			tree.setSelectedItem(treeItem);
		}
		if (treeItem != null) {
			Executions.getCurrent().setAttribute(listbox.getUuid()+".selectTreeitem", Boolean.TRUE);
		    
		    select(treeItem);		    
		    Events.postEvent(ON_POST_SELECT_TREEITEM_EVENT, listbox, newRecord);
		}
	}
	
	private void select(Treeitem selectedItem) {
		Treeitem parent = selectedItem.getParentItem();
		while (parent != null) {
			if (!parent.isOpen())
				parent.setOpen(true);

			parent = parent.getParentItem();
		}
		selectedItem.getTree().setSelectedItem(selectedItem);
	}
	
	private void onPostSelectTreeitem(Boolean newRecord) {
    	Event event = null;
		if (tree.getSelectedItem().getTreerow().getFirstChild().getFirstChild() instanceof A)
		{
			event = new Event(Events.ON_CLICK, tree.getSelectedItem().getTreerow().getFirstChild().getFirstChild(), newRecord);
		}
		else
		{
			event = new Event(Events.ON_CLICK, tree.getSelectedItem().getTreerow(), newRecord);
		}
    	Events.postEvent(event);
    }
	
	public void search(String value) {
		listbox.setModel((ListModel<?>)null);
		Events.echoEvent(ON_SEARCH_ECHO, layout, value);
	}
	
	public void onSearchEcho(String value) {
		ListModelList<MenuItem> newModel = null;
		if (Util.isEmpty(value)) {
			newModel = model;
		} else {
			@SuppressWarnings("unchecked")
			ListSubModel<MenuItem> subModel = (ListSubModel<MenuItem>) ListModels.toListSubModel(model, new MenuListComparator(value), model.size());
			newModel = (ListModelList<MenuItem>) subModel.getSubModel(null, -1);
		}
		updateListboxModel(newModel);
	}

	private void updateListboxModel(ListModelList<MenuItem> newModel) {
		fullModel = null;
		if (newModel.size() > 50) {
			List<MenuItem> list = newModel.getInnerList();
			List<MenuItem> subList = list.subList(0, 50);
			fullModel = newModel;
			newModel = new ListModelList<MenuItem>(subList.toArray(new MenuItem[0]));
			MenuItem more = new MenuItem();
			more.setLabel("...");
			more.setType("...");
			newModel.add(more);
		}
		listbox.setModel(newModel);
	}
	
	private class MenuListComparator implements Comparator<MenuItem> {

		private String compare;

		private MenuListComparator(String compare) {
			this.compare = Util.deleteAccents(compare.toLowerCase().trim());
		}
		
		@Override
		public int compare(MenuItem o1, MenuItem o2) {			
			String label2 = Util.deleteAccents(o2.getLabel().toLowerCase());
			boolean match = false;
			if (compare.length() < 3)
			{
				match = label2.startsWith(compare);
			}
			else
			{
				match = label2.contains(compare);
			} 
			return match ? 0 : -1;
		}
		
	}
	
	public MenuItem selectPrior() {
		int i = listbox.getSelectedIndex();
		if (i > 0) {
			listbox.setSelectedIndex(i-1);
			ListItem selected = listbox.getSelectedItem();
			if (selected == null) return null;
			Clients.scrollIntoView(selected);
			MenuItem item = selected.getValue();
			return item;
		}
		return null;
	}
	
	public MenuItem selectNext() {
		int i = listbox.getSelectedIndex();
		if (i < 0 && listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
			ListItem selected = listbox.getSelectedItem();
			if (selected == null) return null;
			MenuItem item = selected.getValue();			
			return item;
		} else if (i+1 < listbox.getItemCount()) {
			listbox.setSelectedIndex(i+1);
			ListItem selected = listbox.getSelectedItem();
			if (selected == null) return null;
			MenuItem item = selected.getValue();
			if (item == null) return null;
			if ("...".equals(item.getType())) {
				onSelect(selected, Boolean.FALSE);
			}
			Clients.scrollIntoView(selected);
			return item;
		}
		return null;
	}
	
	public boolean onOk(Textbox textbox) {
		String text = textbox.getText();
		if (Util.isEmpty(text))
			return false;
		text = text.toLowerCase();
		ListItem exact = null;
		ListItem firstStart = null;
		int count = listbox.getItemCount();
		for(int i = 0; i < count; i++) {
			ListItem item = listbox.getItemAtIndex(i);
			String label = item.getLabel();
			if (Util.isEmpty(label)) continue;
			if (label.equalsIgnoreCase(text)) {
				exact = item;
				break;
			} else if (firstStart == null && label.toLowerCase().startsWith(text) && text.length() >= 3) {
				firstStart = item;
			}
		}
		if (exact != null) {
			textbox.setText(exact.getLabel());
			onSelect(exact, false);
			return true;
		} else if (firstStart != null) {
			textbox.setText(firstStart.getLabel());
			onSelect(firstStart, false);
			return true;
		}
		return false;
	}
	
	private class MenuItemRenderer implements ListitemRenderer<MenuItem>, ListitemRendererExt {

		@Override
		public Listitem newListitem(org.zkoss.zul.Listbox listbox) {
			return new ListItem();
		}

		@Override
		public Listcell newListcell(Listitem item) {
			return new Listcell(item.getLabel());
		}

		@Override
		public int getControls() {
			return ListitemRendererExt.DETACH_ON_RENDER;
		}

		@Override
		public void render(Listitem item, MenuItem data, int index)
				throws Exception {
			Listcell cell = new Listcell(data.getLabel(), data.getImage());
			if (ThemeManager.isUseFontIconForImage() && data.getImage() != null && data.getImage().startsWith("z-icon")) {
				cell.setImage(null);
				cell.setIconSclass(data.getImage());
			}
			item.appendChild(cell);
			cell.setTooltip(data.getDescription());
			item.setValue(data);
			item.addEventListener(Events.ON_CLICK, MenuSearchController.this);
			
			
			MTreeNode node = null;
			if (data.getData() instanceof MTreeNode) {
				node = (MTreeNode) data.getData();
			} else if (data.getData() instanceof Treeitem) {
				Treeitem value = (Treeitem) data.getData();
				if (value != null) {
					node = (MTreeNode) value.getAttribute(M_TREE_NODE_ATTR);
				}
			}
			if (node != null) {
				ToolBarButton starBtn = new ToolBarButton();
				starBtn.setAttribute(M_TREE_NODE_ATTR, node);
				starBtn.setName(STAR_BUTTON_NAME);
				
			}			
		}			
	}	
}
